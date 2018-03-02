package org.apache.airavata.helix.impl.task.submission.task;

import org.apache.airavata.agents.api.AgentAdaptor;
import org.apache.airavata.agents.api.CommandOutput;
import org.apache.airavata.agents.api.JobSubmissionOutput;
import org.apache.airavata.common.utils.AiravataUtils;
import org.apache.airavata.common.utils.ServerSettings;
import org.apache.airavata.helix.impl.task.AiravataTask;
import org.apache.airavata.helix.impl.task.submission.GroovyMapData;
import org.apache.airavata.helix.impl.task.submission.config.JobFactory;
import org.apache.airavata.helix.impl.task.submission.config.JobManagerConfiguration;
import org.apache.airavata.helix.impl.task.submission.config.RawCommandInfo;
import org.apache.airavata.messaging.core.MessageContext;
import org.apache.airavata.model.appcatalog.appdeployment.ApplicationDeploymentDescription;
import org.apache.airavata.model.appcatalog.computeresource.ComputeResourceDescription;
import org.apache.airavata.model.appcatalog.computeresource.JobSubmissionInterface;
import org.apache.airavata.model.appcatalog.computeresource.JobSubmissionProtocol;
import org.apache.airavata.model.appcatalog.gatewayprofile.ComputeResourcePreference;
import org.apache.airavata.model.appcatalog.gatewayprofile.GatewayResourceProfile;
import org.apache.airavata.model.appcatalog.userresourceprofile.UserComputeResourcePreference;
import org.apache.airavata.model.appcatalog.userresourceprofile.UserResourceProfile;
import org.apache.airavata.model.commons.ErrorModel;
import org.apache.airavata.model.job.JobModel;
import org.apache.airavata.model.messaging.event.JobIdentifier;
import org.apache.airavata.model.messaging.event.JobStatusChangeEvent;
import org.apache.airavata.model.messaging.event.MessageType;
import org.apache.airavata.model.status.JobStatus;
import org.apache.airavata.registry.cpi.*;
import org.apache.commons.io.FileUtils;
import org.apache.helix.HelixManager;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.security.SecureRandom;
import java.util.*;

public abstract class JobSubmissionTask extends AiravataTask {

    private static final Logger logger = LogManager.getLogger(JobSubmissionTask.class);

    @Override
    public void init(HelixManager manager, String workflowName, String jobName, String taskName) {
        super.init(manager, workflowName, jobName, taskName);
    }

    //////////////////////
    protected JobSubmissionOutput submitBatchJob(AgentAdaptor agentAdaptor, GroovyMapData groovyMapData, String workingDirectory) throws Exception {
        JobManagerConfiguration jobManagerConfiguration = JobFactory.getJobManagerConfiguration(JobFactory.getResourceJobManager(
                getAppCatalog(), getTaskContext().getJobSubmissionProtocol(), getTaskContext().getPreferredJobSubmissionInterface()));

        String scriptAsString = groovyMapData.getAsString(jobManagerConfiguration.getJobDescriptionTemplateName());

        int number = new SecureRandom().nextInt();
        number = (number < 0 ? -number : number);
        File tempJobFile = new File(getLocalDataDir(), "job_" + Integer.toString(number) + jobManagerConfiguration.getScriptExtension());

        FileUtils.writeStringToFile(tempJobFile, scriptAsString);
        logger.info("Job submission file for process " + getProcessId() + " was created at : " + tempJobFile.getAbsolutePath());

        logger.info("Copying file form " + tempJobFile.getAbsolutePath() + " to remote path " + workingDirectory +
                " of compute resource " + getTaskContext().getComputeResourceId());
        agentAdaptor.copyFileTo(tempJobFile.getAbsolutePath(), workingDirectory);
        // TODO transfer file
        RawCommandInfo submitCommand = jobManagerConfiguration.getSubmitCommand(workingDirectory, tempJobFile.getPath());

        logger.debug("Submit command for process id " + getProcessId() + " : " + submitCommand.getRawCommand());
        logger.debug("Working directory for process id " + getProcessId() + " : " + workingDirectory);

        CommandOutput commandOutput = agentAdaptor.executeCommand(submitCommand.getRawCommand(), workingDirectory);

        JobSubmissionOutput jsoutput = new JobSubmissionOutput();

        jsoutput.setJobId(jobManagerConfiguration.getParser().parseJobSubmission(commandOutput.getStdOut()));
        if (jsoutput.getJobId() == null) {
            if (jobManagerConfiguration.getParser().isJobSubmissionFailed(commandOutput.getStdOut())) {
                jsoutput.setJobSubmissionFailed(true);
                jsoutput.setFailureReason("stdout : " + commandOutput.getStdOut() +
                        "\n stderr : " + commandOutput.getStdError());
            }
        }
        jsoutput.setExitCode(commandOutput.getExitCode());
        if (jsoutput.getExitCode() != 0) {
            jsoutput.setJobSubmissionFailed(true);
            jsoutput.setFailureReason("stdout : " + commandOutput.getStdOut() +
                    "\n stderr : " + commandOutput.getStdError());
        }
        jsoutput.setStdOut(commandOutput.getStdOut());
        jsoutput.setStdErr(commandOutput.getStdError());
        return jsoutput;
    }

    public File getLocalDataDir() {
        String outputPath = ServerSettings.getLocalDataLocation();
        outputPath = (outputPath.endsWith(File.separator) ? outputPath : outputPath + File.separator);
        return new File(outputPath + getProcessId());
    }

    public JobStatus getJobStatus(AgentAdaptor agentAdaptor, String jobID) throws Exception {
        JobManagerConfiguration jobManagerConfiguration = JobFactory.getJobManagerConfiguration(JobFactory.getResourceJobManager(
                getAppCatalog(), getTaskContext().getJobSubmissionProtocol(), getTaskContext().getPreferredJobSubmissionInterface()));
        CommandOutput commandOutput = agentAdaptor.executeCommand(jobManagerConfiguration.getMonitorCommand(jobID).getRawCommand(), null);

        return jobManagerConfiguration.getParser().parseJobStatus(jobID, commandOutput.getStdOut());

    }

    public String getJobIdByJobName(AgentAdaptor agentAdaptor, String jobName, String userName) throws Exception {
        JobManagerConfiguration jobManagerConfiguration = JobFactory.getJobManagerConfiguration(JobFactory.getResourceJobManager(
                getAppCatalog(), getTaskContext().getJobSubmissionProtocol(), getTaskContext().getPreferredJobSubmissionInterface()));

        RawCommandInfo jobIdMonitorCommand = jobManagerConfiguration.getJobIdMonitorCommand(jobName, userName);
        CommandOutput commandOutput = agentAdaptor.executeCommand(jobIdMonitorCommand.getRawCommand(), null);
        return jobManagerConfiguration.getParser().parseJobId(jobName, commandOutput.getStdOut());
    }

    ////////////////////////////////


    /////////////////////////////////////////////
    public void saveExperimentError(ErrorModel errorModel) throws Exception {
        try {
            errorModel.setErrorId(AiravataUtils.getId("EXP_ERROR"));
            getExperimentCatalog().add(ExpCatChildDataType.EXPERIMENT_ERROR, errorModel, getExperimentId());
        } catch (RegistryException e) {
            String msg = "expId: " + getExperimentId() + " processId: " + getProcessId()
                    + " : - Error while updating experiment errors";
            throw new Exception(msg, e);
        }
    }

    public void saveProcessError(ErrorModel errorModel) throws Exception {
        try {
            errorModel.setErrorId(AiravataUtils.getId("PROCESS_ERROR"));
            getExperimentCatalog().add(ExpCatChildDataType.PROCESS_ERROR, errorModel, getProcessId());
        } catch (RegistryException e) {
            String msg = "expId: " + getExperimentId() + " processId: " + getProcessId()
                    + " : - Error while updating process errors";
            throw new Exception(msg, e);
        }
    }

    public void saveTaskError(ErrorModel errorModel) throws Exception {
        try {
            errorModel.setErrorId(AiravataUtils.getId("TASK_ERROR"));
            getExperimentCatalog().add(ExpCatChildDataType.TASK_ERROR, errorModel, getTaskId());
        } catch (RegistryException e) {
            String msg = "expId: " + getExperimentId() + " processId: " + getProcessId() + " taskId: " + getTaskId()
                    + " : - Error while updating task errors";
            throw new Exception(msg, e);
        }
    }

    public void saveJobModel(JobModel jobModel) throws RegistryException {
        getExperimentCatalog().add(ExpCatChildDataType.JOB, jobModel, getProcessId());
    }

    public void saveJobStatus(JobModel jobModel) throws Exception {
        try {
            // first we save job jobModel to the registry for sa and then save the job status.
            JobStatus jobStatus = null;
            if(jobModel.getJobStatuses() != null)
                jobStatus = jobModel.getJobStatuses().get(0);

            List<JobStatus> statuses = new ArrayList<>();
            statuses.add(jobStatus);
            jobModel.setJobStatuses(statuses);

            if (jobStatus.getTimeOfStateChange() == 0 || jobStatus.getTimeOfStateChange() > 0 ) {
                jobStatus.setTimeOfStateChange(AiravataUtils.getCurrentTimestamp().getTime());
            } else {
                jobStatus.setTimeOfStateChange(jobStatus.getTimeOfStateChange());
            }

            CompositeIdentifier ids = new CompositeIdentifier(jobModel.getTaskId(), jobModel.getJobId());
            getExperimentCatalog().add(ExpCatChildDataType.JOB_STATUS, jobStatus, ids);
            JobIdentifier identifier = new JobIdentifier(jobModel.getJobId(), jobModel.getTaskId(),
                    getProcessId(), getProcessModel().getExperimentId(), getGatewayId());

            JobStatusChangeEvent jobStatusChangeEvent = new JobStatusChangeEvent(jobStatus.getJobState(), identifier);
            MessageContext msgCtx = new MessageContext(jobStatusChangeEvent, MessageType.JOB, AiravataUtils.getId
                    (MessageType.JOB.name()), getGatewayId());
            msgCtx.setUpdatedTime(AiravataUtils.getCurrentTimestamp());
            //getStatusPublisher().publish(msgCtx);
        } catch (Exception e) {
            throw new Exception("Error persisting job status " + e.getLocalizedMessage(), e);
        }
    }

    ///////////// required for groovy map

}