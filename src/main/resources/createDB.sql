/*no mysql, não tem sequence, tem que usar auto_increment*/
/*esse sql funciona no postgres*/
/*pra ver sequence no derby --> VALUES NEXT VALUE FOR LABGC.SEQ_USER;*/

/*PARTE 1*/
CREATE USER labgc WITH PASSWORD 'labgc';
CREATE SCHEMA LABGC AUTHORIZATION labgc;;

CREATE SEQUENCE LABGC.SEQ_USER START WITH 1;ALTER TABLE labgc.SEQ_USER OWNER TO labgc;
CREATE SEQUENCE LABGC.SEQ_REVISION START WITH 1;ALTER TABLE labgc.SEQ_REVISION OWNER TO labgc;
CREATE SEQUENCE LABGC.SEQ_PROJECT START WITH 1;ALTER TABLE labgc.SEQ_PROJECT OWNER TO labgc;
CREATE SEQUENCE LABGC.SEQ_CONFIGITEM START WITH 1;ALTER TABLE labgc.SEQ_CONFIGITEM OWNER TO labgc;
 
CREATE TABLE LABGC.T_USER(
    id NUMERIC(5),
    name  VARCHAR(50),
    username  VARCHAR(10),
    password  VARCHAR(50),
    CONSTRAINT USER_PK PRIMARY KEY (id));
ALTER TABLE labgc.T_USER OWNER TO labgc;

CREATE TABLE LABGC.T_PROJECT(
    id NUMERIC(5),
    name  VARCHAR(50),
    CONSTRAINT PROJECT_PK PRIMARY KEY (id));
ALTER TABLE labgc.T_PROJECT OWNER TO labgc;
/*FIM PARTE 1*/

/*PARTE 2*/
CREATE TABLE LABGC.T_PROJECT_USER(
    project_id NUMERIC(5),
    user_id NUMERIC(5),
    permission  NUMERIC(5),
    token  VARCHAR(50),  
    CONSTRAINT PROJECT_USER_PK PRIMARY KEY (project_id,user_id),
    CONSTRAINT PU_USER_FK FOREIGN KEY(user_id) REFERENCES LABGC.T_USER(id),
    CONSTRAINT PU_PROJECT_FK FOREIGN KEY(project_id) REFERENCES LABGC.T_PROJECT(id));
ALTER TABLE labgc.T_PROJECT_USER OWNER TO labgc;

CREATE TABLE LABGC.T_REVISION(
    id NUMERIC(10),
    date timestamp,
    description VARCHAR(200),
    number VARCHAR(50),
    user_id NUMERIC(5),
    project_id NUMERIC(5),
    config_item_id NUMERIC(20),
    CONSTRAINT REVISION_PK PRIMARY KEY(id),
    CONSTRAINT REV_PU_FK FOREIGN KEY(project_id,user_id) REFERENCES LABGC.T_PROJECT_USER(project_id,user_id)); 
ALTER TABLE labgc.T_REVISION OWNER TO labgc;
/*FIM PARTE 2*/

/*PARTE 3*/
CREATE TABLE LABGC.T_CONFIGURATION_ITEM(
    id NUMERIC(20),
    name VARCHAR(300),
    hash VARCHAR(20),
    type CHAR,
    is_dir NUMERIC(1),
    size NUMERIC(10),
    next_id NUMERIC(20),
    previous_id NUMERIC(20),
    revision_id NUMERIC(10),
    number NUMERIC(10),
    CONSTRAINT CONFIGURATION_ITEM_PK PRIMARY KEY(id),
    CONSTRAINT REVISION_FK FOREIGN KEY(revision_id) REFERENCES LABGC.T_REVISION(id),
    CONSTRAINT NEXT_FK FOREIGN KEY(next_id) REFERENCES LABGC.T_CONFIGURATION_ITEM(id),
    CONSTRAINT PREVIOUS_FK FOREIGN KEY(previous_id) REFERENCES LABGC.T_CONFIGURATION_ITEM(id));
ALTER TABLE labgc.T_CONFIGURATION_ITEM OWNER TO labgc;
/*FIM PARTE 3*/

/*PARTE 4*/
ALTER TABLE LABGC.T_REVISION ADD CONSTRAINT REV_CI_FK FOREIGN KEY(config_item_id) REFERENCES LABGC.T_CONFIGURATION_ITEM(id);

CREATE TABLE LABGC.T_CHILDREN(
    parent_id NUMERIC(20),
    child_id NUMERIC(20),
    CONSTRAINT CHILDREN_PK PRIMARY KEY (parent_id,child_id),
    CONSTRAINT CHILDREN_P_FK FOREIGN KEY(parent_id) REFERENCES LABGC.T_CONFIGURATION_ITEM(id),
    CONSTRAINT CHILDREN_C_FK FOREIGN KEY(child_id) REFERENCES LABGC.T_CONFIGURATION_ITEM(id));
ALTER TABLE labgc.T_CHILDREN OWNER TO labgc;
/*FIM PARTE 4*/
