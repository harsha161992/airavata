package org.apache.airavata.job.monitor;

import org.apache.airavata.common.exception.AiravataException;
import org.apache.airavata.common.utils.ServerSettings;
import org.apache.airavata.job.monitor.parser.EmailParser;
import org.apache.airavata.job.monitor.parser.JobStatusResult;
import org.apache.airavata.job.monitor.parser.ResourceConfig;
import org.apache.airavata.model.appcatalog.computeresource.JobSubmissionProtocol;
import org.apache.airavata.model.appcatalog.computeresource.ResourceJobManagerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;
import javax.mail.search.SearchTerm;
import java.io.FileReader;
import java.io.InputStream;
import java.io.Reader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class EmailBasedMonitor implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(EmailBasedMonitor.class);

    public static final int COMPARISON = 6; // after and equal
    public static final String IMAPS = "imaps";
    public static final String POP3 = "pop3";
    private boolean stopMonitoring = false;

    private Session session ;
    private Store store;
    private Folder emailFolder;
    private Properties properties;
    //private Map<String, TaskContext> jobMonitorMap = new ConcurrentHashMap<>();
    private String host, emailAddress, password, storeProtocol, folderName ;
    private Date monitorStartDate;
    private Map<ResourceJobManagerType, EmailParser> emailParserMap = new HashMap<ResourceJobManagerType, EmailParser>();
    private Map<String, ResourceJobManagerType> addressMap = new HashMap<>();
    private Message[] flushUnseenMessages;
    private Map<String, Boolean> canceledJobs = new ConcurrentHashMap<>();
    private Timer timer;
    private Map<ResourceJobManagerType, ResourceConfig> resourceConfigs = new HashMap<>();


    public EmailBasedMonitor() throws Exception {
        init();
        populateAddressAndParserMap(resourceConfigs);
    }

    private void init() throws Exception {
        loadContext();
        host = ServerSettings.getEmailBasedMonitorHost();
        emailAddress = ServerSettings.getEmailBasedMonitorAddress();
        password = ServerSettings.getEmailBasedMonitorPassword();
        storeProtocol = ServerSettings.getEmailBasedMonitorStoreProtocol();
        folderName = ServerSettings.getEmailBasedMonitorFolderName();
        if (!(storeProtocol.equals(IMAPS) || storeProtocol.equals(POP3))) {
            throw new AiravataException("Unsupported store protocol , expected " +
                    IMAPS + " or " + POP3 + " but found " + storeProtocol);
        }
        properties = new Properties();
        properties.put("mail.store.protocol", storeProtocol);
        timer = new Timer("CancelJobHandler", true);
        long period = 1000 * 60 * 5; // five minute delay between successive task executions.
    }

    private void loadContext() throws Exception {
        Yaml yaml = new Yaml();
        InputStream emailConfigStream = EmailBasedMonitor.class.getClassLoader().getResourceAsStream("email-config.yaml");
        Object load = yaml.load(emailConfigStream);

        if (load == null) {
            throw new Exception("Could not load the configuration");
        }

        if (load instanceof Map) {
            Map<String, Object> loadMap = (Map<String, Object>) load;
            Map<String, Object> configMap = (Map<String, Object>) loadMap.get("config");
            List<Map<String,Object >> resourceObjs = (List<Map<String, Object>>) configMap.get("resources");
            if (resourceObjs != null) {
                resourceObjs.forEach(resource -> {
                    ResourceConfig resourceConfig = new ResourceConfig();
                    String identifier = resource.get("jobManagerType").toString();
                    resourceConfig.setJobManagerType(ResourceJobManagerType.valueOf(identifier));
                    Object emailParser = resource.get("emailParser");
                    if (emailParser != null){
                        resourceConfig.setEmailParser(emailParser.toString());
                    }
                    List<String> emailAddressList = (List<String>) resource.get("resourceEmailAddresses");
                    resourceConfig.setResourceEmailAddresses(emailAddressList);
                    resourceConfigs.put(resourceConfig.getJobManagerType(), resourceConfig);
                });
            }
        }
        populateAddressAndParserMap(resourceConfigs);
    }

    private void populateAddressAndParserMap(Map<ResourceJobManagerType, ResourceConfig> resourceConfigs) throws AiravataException {
        for (Map.Entry<ResourceJobManagerType, ResourceConfig> resourceConfigEntry : resourceConfigs.entrySet()) {
            ResourceJobManagerType type = resourceConfigEntry.getKey();
            ResourceConfig config = resourceConfigEntry.getValue();
            List<String> resourceEmailAddresses = config.getResourceEmailAddresses();
            if (resourceEmailAddresses != null && !resourceEmailAddresses.isEmpty()){
                for (String resourceEmailAddress : resourceEmailAddresses) {
                    addressMap.put(resourceEmailAddress, type);
                }
                try {
                    Class<? extends EmailParser> emailParserClass = Class.forName(config.getEmailParser()).asSubclass(EmailParser.class);
                    EmailParser emailParser = emailParserClass.getConstructor().newInstance();
                    emailParserMap.put(type, emailParser);
                } catch (Exception e) {
                    throw new AiravataException("Error while instantiation email parsers", e);
                }
            }
        }

    }

    public void monitor(String jobId) {
        log.info("[EJM]: Added monitor Id : {} to email based monitor map", jobId);
    }

    public void stopMonitor(String jobId, boolean runOutflow) {

    }

    public boolean isMonitoring(String jobId) {
        return true;
    }

    public void canceledJob(String jobId) {

    }

    private JobStatusResult parse(Message message) throws MessagingException, AiravataException {
        Address fromAddress = message.getFrom()[0];
        String addressStr = fromAddress.toString();
        ResourceJobManagerType jobMonitorType = getJobMonitorType(addressStr);
        EmailParser emailParser = emailParserMap.get(jobMonitorType);
        if (emailParser == null) {
            throw new AiravataException("[EJM]: Un-handle resource job manager type: " + jobMonitorType
                    .toString() + " for email monitoring -->  " + addressStr);
        }
        return emailParser.parseEmail(message);
    }

    private ResourceJobManagerType getJobMonitorType(String addressStr) throws AiravataException {
//        System.out.println("*********** address ******** : " + addressStr);
        for (Map.Entry<String, ResourceJobManagerType> addressEntry : addressMap.entrySet()) {
            if (addressStr.contains(addressEntry.getKey())) {
                return addressEntry.getValue();
            }
        }
        throw new AiravataException("[EJM]: Couldn't identify Resource job manager type from address " + addressStr);
    }

    @Override
    public void run() {
        boolean quite = false;

        while (!stopMonitoring && !ServerSettings.isStopAllThreads()) {
            try {
                session = Session.getDefaultInstance(properties);
                store = session.getStore(storeProtocol);
                store.connect(host, emailAddress, password);
                emailFolder = store.getFolder(folderName);
                // first time we search for all unread messages.
                SearchTerm unseenBefore = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
                while (!(stopMonitoring || ServerSettings.isStopAllThreads())) {
                    Thread.sleep(ServerSettings.getEmailMonitorPeriod());// sleep a bit - get a rest till job finishes
                    if (!store.isConnected()) {
                        store.connect();
                        emailFolder = store.getFolder(folderName);
                    }
                    log.info("[EJM]: Retrieving unseen emails");
                    emailFolder.open(Folder.READ_WRITE);
                    if (emailFolder.isOpen()) {
                        // flush if any message left in flushUnseenMessage
                        if (flushUnseenMessages != null && flushUnseenMessages.length > 0) {
                            try {
                                emailFolder.setFlags(flushUnseenMessages, new Flags(Flags.Flag.SEEN), false);
                                flushUnseenMessages = null;
                            } catch (MessagingException e) {
                                if (!store.isConnected()) {
                                    store.connect();
                                    emailFolder.setFlags(flushUnseenMessages, new Flags(Flags.Flag.SEEN), false);
                                    flushUnseenMessages = null;
                                }
                            }
                        }
                        Message[] searchMessages = emailFolder.search(unseenBefore);
                        if (searchMessages == null || searchMessages.length == 0) {
                            log.info("[EJM]: No new email messages");
                        } else {
                            log.info("[EJM]: " + searchMessages.length + " new email/s received");
                        }
                        processMessages(searchMessages);
                        emailFolder.close(false);
                    }
                }
            } catch (MessagingException e) {
                log.error("[EJM]: Couldn't connect to the store ", e);
            } catch (InterruptedException e) {
                log.error("[EJM]: Interrupt exception while sleep ", e);
            } catch (AiravataException e) {
                log.error("[EJM]: UnHandled arguments ", e);
            } catch (Throwable e)  {
                log.error("[EJM]: Caught a throwable ", e);
            } finally {
                try {
                    emailFolder.close(false);
                    store.close();
                } catch (MessagingException e) {
                    log.error("[EJM]: Store close operation failed, couldn't close store", e);
                } catch (Throwable e) {
                    log.error("[EJM]: Caught a throwable while closing email store ", e);
                }
            }
        }
        log.info("[EJM]: Email monitoring daemon stopped");
    }

    private void processMessages(Message[] searchMessages) throws MessagingException {
        List<Message> processedMessages = new ArrayList<>();
        List<Message> unreadMessages = new ArrayList<>();
        for (Message message : searchMessages) {
            try {
                JobStatusResult jobStatusResult = parse(message);
                log.info(jobStatusResult.getJobId() + ", " + jobStatusResult.getJobName() + ", " + jobStatusResult.getState().getValue());
                //processedMessages.add(message);
                unreadMessages.add(message);
            } catch (Exception e) {
                unreadMessages.add(message);
            }
        }
        if (!processedMessages.isEmpty()) {
            Message[] seenMessages = new Message[processedMessages.size()];
            processedMessages.toArray(seenMessages);
            try {
                emailFolder.setFlags(seenMessages, new Flags(Flags.Flag.SEEN), true);
            } catch (MessagingException e) {
                if (!store.isConnected()) {
                    store.connect();
                    emailFolder.setFlags(seenMessages, new Flags(Flags.Flag.SEEN), true);
                }
            }

        }
        if (!unreadMessages.isEmpty()) {
            Message[] unseenMessages = new Message[unreadMessages.size()];
            unreadMessages.toArray(unseenMessages);
            try {
                emailFolder.setFlags(unseenMessages, new Flags(Flags.Flag.SEEN), false);
            } catch (MessagingException e) {
                if (!store.isConnected()) {
                    store.connect();
                    emailFolder.setFlags(unseenMessages, new Flags(Flags.Flag.SEEN), false);
                    flushUnseenMessages = unseenMessages; // anyway we need to push this update.
                } else {
                    flushUnseenMessages = unseenMessages; // anyway we need to push this update.
                }
            }
        }
    }

    private void process(JobStatusResult jobStatusResult){

    }

    private void writeEnvelopeOnError(Message m) throws MessagingException {
        Address[] a;
        // FROM
        if ((a = m.getFrom()) != null) {
            for (int j = 0; j < a.length; j++)
                log.error("FROM: " + a[j].toString());
        }
        // TO
        if ((a = m.getRecipients(Message.RecipientType.TO)) != null) {
            for (int j = 0; j < a.length; j++)
                log.error("TO: " + a[j].toString());
        }
        // SUBJECT
        if (m.getSubject() != null)
            log.error("SUBJECT: " + m.getSubject());
    }

    public void stopMonitoring() {
        stopMonitoring = true;
    }

    public void setDate(Date date) {
        this.monitorStartDate = date;
    }

    public static void main(String args[]) throws Exception {
        EmailBasedMonitor monitor = new EmailBasedMonitor();
        Thread t = new Thread(monitor);
        t.start();
        t.join();
    }
}