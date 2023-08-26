# Spring Batch with Spring Boot3 and MySQL DB


###Overview

Spring Batch required following metadata tables to run the job

    |------------------------------|
    |      Table                   | 
    |------------------------------| 
    | BATCH_JOB_INSTANCE           | 
    | BATCH_JOB_EXECUTION          |
    | BATCH_JOB_EXECUTION_PARAMS   |
    | BATCH_STEP_EXECUTION         |
    | BATCH_JOB_EXECUTION_CONTEXT  |
    | BATCH_STEP_EXECUTION_CONTEXT |
    |------------------------------|
    | BATCH_JOB_EXECUTION_SEQ      |
    | BATCH_JOB_SEQ                |
    | BATCH_STEP_EXECUTION_SEQ     |
    |------------------------------|

We can use below property in application yml file to create above tables automatically.

`spring.batch.jdbc.initialize-schema: ALWAYS`

If we want to create the batch metadata tables but not in runtime then we can create them manually
#### The BATCH_JOB_INSTANCE Table
The BATCH_JOB_INSTANCE table holds all information relevant to a JobInstance.

DDL statement:
```
CREATE TABLE BATCH_JOB_INSTANCE  (
  JOB_INSTANCE_ID BIGINT  PRIMARY KEY ,
  VERSION BIGINT,
  JOB_NAME VARCHAR(100) NOT NULL ,
  JOB_KEY VARCHAR(32) NOT NULL
);
```
#### The BATCH_JOB_EXECUTION Table
The BATCH_JOB_EXECUTION table holds all information relevant to the JobExecution object.

DDL statement:
```
CREATE TABLE BATCH_JOB_EXECUTION  (
  JOB_EXECUTION_ID BIGINT  PRIMARY KEY ,
  VERSION BIGINT,
  JOB_INSTANCE_ID BIGINT NOT NULL,
  CREATE_TIME TIMESTAMP NOT NULL,
  START_TIME TIMESTAMP DEFAULT NULL,
  END_TIME TIMESTAMP DEFAULT NULL,
  STATUS VARCHAR(10),
  EXIT_CODE VARCHAR(20),
  EXIT_MESSAGE VARCHAR(2500),
  LAST_UPDATED TIMESTAMP,
  constraint JOB_INSTANCE_EXECUTION_FK foreign key (JOB_INSTANCE_ID)
  references BATCH_JOB_INSTANCE(JOB_INSTANCE_ID)
) ;
```

#### The BATCH_JOB_EXECUTION_PARAMS Table 
The BATCH_JOB_EXECUTION_PARAMS table holds all information relevant to the JobParameters object.

DDL statement:
```
CREATE TABLE BATCH_JOB_EXECUTION_PARAMS  (
	JOB_EXECUTION_ID BIGINT NOT NULL ,
	PARAMETER_NAME VARCHAR(100) NOT NULL ,
	PARAMETER_TYPE VARCHAR(100) NOT NULL ,
	PARAMETER_VALUE VARCHAR(2500) ,
	IDENTIFYING CHAR(1) NOT NULL ,
	constraint JOB_EXEC_PARAMS_FK foreign key (JOB_EXECUTION_ID)
	references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
);
```
#### The BATCH_STEP_EXECUTION Table
The BATCH_STEP_EXECUTION table holds all information relevant to the StepExecution object.

DDL statement:
``` 
CREATE TABLE BATCH_STEP_EXECUTION  (
  STEP_EXECUTION_ID BIGINT NOT NULL PRIMARY KEY ,
  VERSION BIGINT NOT NULL,
  STEP_NAME VARCHAR(100) NOT NULL,
  JOB_EXECUTION_ID BIGINT NOT NULL,
  CREATE_TIME TIMESTAMP NOT NULL,
  START_TIME TIMESTAMP DEFAULT NULL ,
  END_TIME TIMESTAMP DEFAULT NULL,
  STATUS VARCHAR(10),
  COMMIT_COUNT BIGINT ,
  READ_COUNT BIGINT ,
  FILTER_COUNT BIGINT ,
  WRITE_COUNT BIGINT ,
  READ_SKIP_COUNT BIGINT ,
  WRITE_SKIP_COUNT BIGINT ,
  PROCESS_SKIP_COUNT BIGINT ,
  ROLLBACK_COUNT BIGINT ,
  EXIT_CODE VARCHAR(20) ,
  EXIT_MESSAGE VARCHAR(2500) ,
  LAST_UPDATED TIMESTAMP,
  constraint JOB_EXECUTION_STEP_FK foreign key (JOB_EXECUTION_ID)
  references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ;
```
#### The BATCH_JOB_EXECUTION_CONTEXT Table
The BATCH_JOB_EXECUTION_CONTEXT table holds all information relevant to the ExecutionContext of a Job.

DDL statement:
```
CREATE TABLE BATCH_JOB_EXECUTION_CONTEXT  (
  JOB_EXECUTION_ID BIGINT PRIMARY KEY,
  SHORT_CONTEXT VARCHAR(2500) NOT NULL,
  SERIALIZED_CONTEXT CLOB,
  constraint JOB_EXEC_CTX_FK foreign key (JOB_EXECUTION_ID)
  references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ;
```
#### The BATCH_STEP_EXECUTION_CONTEXT Table
The BATCH_STEP_EXECUTION_CONTEXT table holds all information relevant to the ExecutionContext of a Step.

DDL statement:
```
CREATE TABLE BATCH_STEP_EXECUTION_CONTEXT  (
  STEP_EXECUTION_ID BIGINT PRIMARY KEY,
  SHORT_CONTEXT VARCHAR(2500) NOT NULL,
  SERIALIZED_CONTEXT CLOB,
  constraint STEP_EXEC_CTX_FK foreign key (STEP_EXECUTION_ID)
  references BATCH_STEP_EXECUTION(STEP_EXECUTION_ID)
) ;
```
#### Sequence Tables BATCH_JOB_EXECUTION_SEQ, BATCH_JOB_SEQ and BATCH_STEP_EXECUTION_SEQ
Create and Insert statements:
```
CREATE TABLE BATCH_STEP_EXECUTION_SEQ (ID BIGINT NOT NULL);
INSERT INTO BATCH_STEP_EXECUTION_SEQ values(0);

CREATE TABLE BATCH_JOB_EXECUTION_SEQ (ID BIGINT NOT NULL);
INSERT INTO BATCH_JOB_EXECUTION_SEQ values(0);

CREATE TABLE BATCH_JOB_SEQ (ID BIGINT NOT NULL)B;
INSERT INTO BATCH_JOB_SEQ values(0);
```
#### Table used by the application 
* DDL for my_test_db.user
```
CREATE TABLE `user` (
  `id` int NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `roll` varchar(45) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `class_name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) 
```
Insert SQL Script
```
INSERT INTO `my_test_db`.`user`
(`id`,
`name`,
`roll`,
`amount`,
`class_name`)
VALUES
(1, 'Abhi', 1, 100, '1'),
(2, 'Suman', 2, 200, '1'),
(3, 'Sandip', 3, 200, '2'),
(4, 'Sivan', 4, 200, '1');

```
* Outout
<img width="1306" alt="Screenshot 2023-08-27 at 4 07 09 AM" src="https://github.com/sovanm10/spring-batch-example/assets/26097904/ac2c2926-5ff5-422d-b344-95f52def3dfc">


### Reference Documentation
For further reference, please consider the following sections:

* [Spring Batch](https://spring.io/projects/spring-batch)
* [Spring Batch Reference Documentation](https://docs.spring.io/spring-batch/docs/current/reference/html/)  
The following guides illustrate how to use some features concretely:

* [Creating a Batch Service](https://spring.io/guides/gs/batch-processing/)
