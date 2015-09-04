/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */
CREATE TABLE GATEWAY
(
        GATEWAY_ID VARCHAR(255),
        GATEWAY_NAME VARCHAR(255),
	      DOMAIN VARCHAR(255),
	      EMAIL_ADDRESS VARCHAR(255),
        PRIMARY KEY (GATEWAY_ID)
);

CREATE TABLE USERS
(
        USER_NAME VARCHAR(255),
        PASSWORD VARCHAR(255),
        PRIMARY KEY(USER_NAME)
);

CREATE TABLE GATEWAY_WORKER
(
        GATEWAY_ID VARCHAR(255),
        USER_NAME VARCHAR(255),
        PRIMARY KEY (GATEWAY_ID, USER_NAME),
        FOREIGN KEY (GATEWAY_ID) REFERENCES GATEWAY(GATEWAY_ID) ON DELETE CASCADE,
        FOREIGN KEY (USER_NAME) REFERENCES USERS(USER_NAME) ON DELETE CASCADE
);

CREATE TABLE PROJECT
(
         GATEWAY_ID VARCHAR(255),
         USER_NAME VARCHAR(255),
         PROJECT_NAME VARCHAR(255),
         PROJECT_ID VARCHAR(255),
         DESCRIPTION VARCHAR(255),
         CREATION_TIME TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
         PRIMARY KEY (PROJECT_ID),
         FOREIGN KEY (GATEWAY_ID) REFERENCES GATEWAY(GATEWAY_ID) ON DELETE CASCADE,
         FOREIGN KEY (USER_NAME) REFERENCES USERS(USER_NAME) ON DELETE CASCADE
);

CREATE TABLE PROJECT_USER
(
    PROJECT_ID VARCHAR(255),
    USER_NAME VARCHAR(255),
    PRIMARY KEY (PROJECT_ID,USER_NAME),
    FOREIGN KEY (PROJECT_ID) REFERENCES PROJECT(PROJECT_ID) ON DELETE CASCADE,
    FOREIGN KEY (USER_NAME) REFERENCES USERS(USER_NAME) ON DELETE CASCADE
);

CREATE TABLE EXPERIMENT (
  EXPERIMENT_ID varchar(255),
  PROJECT_ID varchar(255),
  GATEWAY_ID varchar(255),
  EXPERIMENT_TYPE varchar(255),
  USER_NAME varchar(255),
  EXPERIMENT_NAME varchar(255),
  CREATION_TIME timestamp DEFAULT CURRENT_TIMESTAMP,
  DESCRIPTION varchar(255),
  EXECUTION_ID varchar(255),
  GATEWAY_EXECUTION_ID varchar(255),
  ENABLE_EMAIL_NOTIFICATION SMALLINT,
  EMAIL_ADDRESSES CLOB,
  PRIMARY KEY (EXPERIMENT_ID),
  FOREIGN KEY (USER_NAME) REFERENCES USERS(USER_NAME) ON DELETE CASCADE,
  FOREIGN KEY (PROJECT_ID) REFERENCES PROJECT(PROJECT_ID) ON DELETE CASCADE
);


CREATE TABLE EXPERIMENT_INPUT
(
    EXPERIMENT_ID varchar(255),
    INPUT_NAME varchar(255),
    INPUT_VALUE CLOB,
    DATA_TYPE varchar(255),
    APPLICATION_ARGUMENT varchar(255),
    STANDARD_INPUT SMALLINT,
    USER_FRIENDLY_DESCRIPTION varchar(255),
    METADATA varchar(255),
    INPUT_ORDER INT,
    IS_REQUIRED SMALLINT,
    REQUIRED_TO_ADDED_TO_CMD SMALLINT,
    DATA_STAGED SMALLINT,
    PRIMARY KEY(EXPERIMENT_ID,INPUT_NAME),
    FOREIGN KEY (EXPERIMENT_ID) REFERENCES EXPERIMENT(EXPERIMENT_ID) ON DELETE CASCADE
);

CREATE TABLE EXPERIMENT_OUTPUT
(
    EXPERIMENT_ID varchar(255),
    OUTPUT_NAME varchar(255),
    OUTPUT_VALUE CLOB,
    DATA_TYPE varchar(255),
    APPLICATION_ARGUMENT varchar(255),
    IS_REQUIRED SMALLINT,
    REQUIRED_TO_ADDED_TO_CMD SMALLINT,
    DATA_MOVEMENT SMALLINT,
    LOCATION varchar(255),
    SEARCH_QUERY varchar(255),
    PRIMARY KEY(EXPERIMENT_ID,OUTPUT_NAME),
    FOREIGN KEY (EXPERIMENT_ID) REFERENCES EXPERIMENT(EXPERIMENT_ID) ON DELETE CASCADE
);


CREATE TABLE EXPERIMENT_STATUS (
  STATUS_ID varchar(255),
  EXPERIMENT_ID varchar(255),
  STATE varchar(255),
  TIME_OF_STATE_CHANGE timestamp DEFAULT '0000-00-00 00:00:00',
  REASON CLOB,
  PRIMARY KEY (STATUS_ID, EXPERIMENT_ID),
  FOREIGN KEY (EXPERIMENT_ID) REFERENCES EXPERIMENT(EXPERIMENT_ID) ON DELETE CASCADE
);


CREATE TABLE EXPERIMENT_ERROR (
  ERROR_ID varchar(255),
  EXPERIMENT_ID varchar(255),
  CREATION_TIME timestamp DEFAULT CURRENT_TIMESTAMP,
  ACTUAL_ERROR_MESSAGE CLOB,
  USER_FRIENDLY_MESSAGE CLOB,
  TRANSIENT_OR_PERSISTENT SMALLINT,
  ROOT_CAUSE_ERROR_ID_LIST CLOB,
  PRIMARY KEY (ERROR_ID, EXPERIMENT_ID),
  FOREIGN KEY (EXPERIMENT_ID) REFERENCES EXPERIMENT(EXPERIMENT_ID) ON DELETE CASCADE
);

CREATE TABLE USER_CONFIGURATION_DATA (
  EXPERIMENT_ID varchar(255),
  AIRAVATA_AUTO_SCHEDULE SMALLINT,
  OVERRIDE_MANUAL_SCHEDULED_PARAMS SMALLINT,
  SHARE_EXPERIMENT_PUBLICALLY SMALLINT,
  THROTTLE_RESOURCES SMALLINT,
  USER_DN varchar(255),
  GENERATE_CERT SMALLINT,
  RESOURCE_HOST_ID varchar(255),
  TOTAL_CPU_COUNT INT,
  NODE_COUNT INT,
  NUMBER_OF_THREADS INT,
  QUEUE_NAME varchar(255),
  WALL_TIME_LIMIT INT,
  TOTAL_PHYSICAL_MEMORY INT,
  PRIMARY KEY (EXPERIMENT_ID),
  FOREIGN KEY (EXPERIMENT_ID) REFERENCES EXPERIMENT(EXPERIMENT_ID) ON DELETE CASCADE
);

CREATE VIEW LATEST_EXPERIMENT_STATUS AS
  select ES1.EXPERIMENT_ID AS EXPERIMENT_ID, ES1.STATE AS STATE, ES1.TIME_OF_STATE_CHANGE AS TIME_OF_STATE_CHANGE
  from EXPERIMENT_STATUS ES1 LEFT JOIN EXPERIMENT_STATUS ES2 ON (ES1.EXPERIMENT_ID = ES2.EXPERIMENT_ID
    AND ES1.TIME_OF_STATE_CHANGE < ES2.TIME_OF_STATE_CHANGE)  WHERE ES2.TIME_OF_STATE_CHANGE is NULL;

CREATE VIEW EXPERIMENT_SUMMARY AS
  select E.EXPERIMENT_ID AS EXPERIMENT_ID, E.PROJECT_ID AS PROJECT_ID, E.GATEWAY_ID AS GATEWAY_ID,
  E.USER_NAME AS USER_NAME, E.EXECUTION_ID AS EXECUTION_ID, E.EXPERIMENT_NAME AS EXPERIMENT_NAME,
  E.CREATION_TIME AS CREATION_TIME, E.DESCRIPTION AS DESCRIPTION, ES.STATE AS STATE, UD.RESOURCE_HOST_ID
  AS RESOURCE_HOST_ID, ES.TIME_OF_STATE_CHANGE AS TIME_OF_STATE_CHANGE
    from ((EXPERIMENT E left join LATEST_EXPERIMENT_STATUS ES on((E.EXPERIMENT_ID = ES.EXPERIMENT_ID)))
    left join USER_CONFIGURATION_DATA UD on((E.EXPERIMENT_ID = UD.EXPERIMENT_ID))) where true;

CREATE TABLE PROCESS (
  PROCESS_ID varchar(255),
  EXPERIMENT_ID varchar(255),
  CREATION_TIME timestamp DEFAULT CURRENT_TIMESTAMP,
  LAST_UPDATE_TIME timestamp DEFAULT CURRENT_TIMESTAMP,
  PROCESS_DETAIL CLOB,
  APPLICATION_INTERFACE_ID varchar(255),
  TASK_DAG varchar(255),
  APPLICATION_DEPLOYMENT_ID varchar(255),
  COMPUTE_RESOURCE_ID varchar(255),
  GATEWAY_EXECUTION_ID varchar(255),
  ENABLE_EMAIL_NOTIFICATION SMALLINT,
  EMAIL_ADDRESSES CLOB,
  PRIMARY KEY (PROCESS_ID),
  FOREIGN KEY (EXPERIMENT_ID) REFERENCES EXPERIMENT(EXPERIMENT_ID) ON DELETE CASCADE
);

CREATE TABLE PROCESS_INPUT
(
    PROCESS_ID varchar(255),
    INPUT_NAME varchar(255),
    INPUT_VALUE CLOB,
    DATA_TYPE varchar(255),
    APPLICATION_ARGUMENT varchar(255),
    STANDARD_INPUT SMALLINT,
    USER_FRIENDLY_DESCRIPTION varchar(255),
    METADATA varchar(255),
    INPUT_ORDER INT,
    IS_REQUIRED SMALLINT,
    REQUIRED_TO_ADDED_TO_CMD SMALLINT,
    DATA_STAGED SMALLINT,
    PRIMARY KEY(PROCESS_ID,INPUT_NAME),
    FOREIGN KEY (PROCESS_ID) REFERENCES PROCESS(PROCESS_ID) ON DELETE CASCADE
);

CREATE TABLE PROCESS_OUTPUT
(
    PROCESS_ID varchar(255),
    OUTPUT_NAME varchar(255),
    OUTPUT_VALUE CLOB,
    DATA_TYPE varchar(255),
    APPLICATION_ARGUMENT varchar(255),
    IS_REQUIRED SMALLINT,
    REQUIRED_TO_ADDED_TO_CMD SMALLINT,
    DATA_MOVEMENT SMALLINT,
    LOCATION varchar(255),
    SEARCH_QUERY varchar(255),
    PRIMARY KEY(PROCESS_ID,OUTPUT_NAME),
    FOREIGN KEY (PROCESS_ID) REFERENCES PROCESS(PROCESS_ID) ON DELETE CASCADE
);


CREATE TABLE PROCESS_STATUS (
  STATUS_ID varchar(255),
  PROCESS_ID varchar(255),
  STATE varchar(255),
  TIME_OF_STATE_CHANGE timestamp DEFAULT '0000-00-00 00:00:00',
  REASON CLOB,
  PRIMARY KEY (STATUS_ID, PROCESS_ID),
  FOREIGN KEY (PROCESS_ID) REFERENCES PROCESS(PROCESS_ID) ON DELETE CASCADE
);


CREATE TABLE PROCESS_ERROR (
  ERROR_ID varchar(255),
  PROCESS_ID varchar(255),
  CREATION_TIME timestamp DEFAULT CURRENT_TIMESTAMP,
  ACTUAL_ERROR_MESSAGE CLOB,
  USER_FRIENDLY_MESSAGE CLOB,
  TRANSIENT_OR_PERSISTENT SMALLINT,
  ROOT_CAUSE_ERROR_ID_LIST CLOB,
  PRIMARY KEY (ERROR_ID, PROCESS_ID),
  FOREIGN KEY (PROCESS_ID) REFERENCES PROCESS(PROCESS_ID) ON DELETE CASCADE
);

CREATE TABLE PROCESS_RESOURCE_SCHEDULE (
  PROCESS_ID varchar(255),
  RESOURCE_HOST_ID varchar(255),
  TOTAL_CPU_COUNT INT,
  NODE_COUNT INT,
  NUMBER_OF_THREADS INT,
  QUEUE_NAME varchar(255),
  WALL_TIME_LIMIT INT,
  TOTAL_PHYSICAL_MEMORY INT,
  PRIMARY KEY (PROCESS_ID),
  FOREIGN KEY (PROCESS_ID) REFERENCES PROCESS(PROCESS_ID) ON DELETE CASCADE
);

CREATE TABLE TASK (
  TASK_ID varchar(255),
  TASK_TYPE varchar(255),
  PARENT_PROCESS_ID varchar(255),
  CREATION_TIME timestamp DEFAULT CURRENT_TIMESTAMP,
  LAST_UPDATE_TIME timestamp DEFAULT CURRENT_TIMESTAMP,
  TASK_DETAIL CLOB,
  TASK_INTERNAL_STORE CHAR,
  PRIMARY KEY (TASK_ID),
  FOREIGN KEY (PARENT_PROCESS_ID) REFERENCES PROCESS(PROCESS_ID) ON DELETE CASCADE
);

CREATE TABLE TASK_STATUS (
  STATUS_ID varchar(255),
  TASK_ID varchar(255),
  STATE varchar(255),
  TIME_OF_STATE_CHANGE timestamp DEFAULT '0000-00-00 00:00:00',
  REASON CLOB,
  PRIMARY KEY (STATUS_ID, TASK_ID),
  FOREIGN KEY (TASK_ID) REFERENCES TASK(TASK_ID) ON DELETE CASCADE
);


CREATE TABLE TASK_ERROR (
  ERROR_ID varchar(255),
  TASK_ID varchar(255),
  CREATION_TIME timestamp DEFAULT CURRENT_TIMESTAMP,
  ACTUAL_ERROR_MESSAGE CLOB,
  USER_FRIENDLY_MESSAGE CLOB,
  TRANSIENT_OR_PERSISTENT SMALLINT,
  ROOT_CAUSE_ERROR_ID_LIST CLOB,
  PRIMARY KEY (ERROR_ID, TASK_ID),
  FOREIGN KEY (TASK_ID) REFERENCES TASK(TASK_ID) ON DELETE CASCADE
);

CREATE TABLE JOB (
  JOB_ID varchar(255),
  TASK_ID varchar(255),
  PROCESS_ID varchar(255),
  JOB_DESCRIPTION CLOB NOT NULL,
  CREATION_TIME  timestamp DEFAULT '0000-00-00 00:00:00',
  COMPUTE_RESOURCE_CONSUMED varchar(255),
  JOB_NAME varchar(255),
  WORKING_DIR varchar(255),
  PRIMARY KEY (JOB_ID, PROCESS_ID),
  FOREIGN KEY (PROCESS_ID) REFERENCES PROCESS(PROCESS_ID) ON DELETE CASCADE
);

CREATE TABLE JOB_STATUS (
  STATUS_ID varchar(255),
  JOB_ID varchar(255),
  PROCESS_ID varchar(255),
  STATE varchar(255),
  TIME_OF_STATE_CHANGE  timestamp DEFAULT '0000-00-00 00:00:00',
  REASON CLOB,
  PRIMARY KEY (STATUS_ID, JOB_ID, PROCESS_ID),
  FOREIGN KEY (JOB_ID, PROCESS_ID) REFERENCES JOB(JOB_ID, PROCESS_ID) ON DELETE CASCADE
);

CREATE TABLE CONFIGURATION
(
        CONFIG_KEY VARCHAR(255),
        CONFIG_VAL VARCHAR(255),
        EXPIRE_DATE TIMESTAMP DEFAULT '0000-00-00 00:00:00',
        CATEGORY_ID VARCHAR (255),
        PRIMARY KEY(CONFIG_KEY, CONFIG_VAL, CATEGORY_ID)
);

INSERT INTO CONFIGURATION (CONFIG_KEY, CONFIG_VAL, EXPIRE_DATE, CATEGORY_ID) VALUES('registry.version', '0.16', CURRENT_TIMESTAMP ,'SYSTEM');

CREATE TABLE COMMUNITY_USER
(
        GATEWAY_ID VARCHAR(256) NOT NULL,
        COMMUNITY_USER_NAME VARCHAR(256) NOT NULL,
        TOKEN_ID VARCHAR(256) NOT NULL,
        COMMUNITY_USER_EMAIL VARCHAR(256) NOT NULL,
        PRIMARY KEY (GATEWAY_ID, COMMUNITY_USER_NAME, TOKEN_ID)
);

CREATE TABLE CREDENTIALS
(
        GATEWAY_ID VARCHAR(256) NOT NULL,
        TOKEN_ID VARCHAR(256) NOT NULL,
        CREDENTIAL BLOB NOT NULL,
        PORTAL_USER_ID VARCHAR(256) NOT NULL,
        TIME_PERSISTED TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        PRIMARY KEY (GATEWAY_ID, TOKEN_ID)
);