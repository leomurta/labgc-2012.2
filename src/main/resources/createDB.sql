/*no mysql, não tem sequence, tem que usar auto_increment*/
/*esse sql funciona no postgres*/
CREATE SEQUENCE "LABGC".SEQ_USER;
CREATE SEQUENCE "LABGC".SEQ_REVISION;
CREATE SEQUENCE "LABGC".SEQ_PROJECT;
CREATE SEQUENCE "LABGC".SEQ_CONFIGITEM;



CREATE TABLE "LABGC".T_USER(
    id NUMERIC(5),
    name  VARCHAR(50),
    username  VARCHAR(10),
    password  VARCHAR(50),
    CONSTRAINT USER_PK PRIMARY KEY (id));

CREATE TABLE "LABGC".T_PROJECT(
    id NUMERIC(5),
    name  VARCHAR(50),
    CONSTRAINT PROJECT_PK PRIMARY KEY (id));

CREATE TABLE "LABGC".T_PROJECT_USER(
    project_id NUMERIC(5),
    user_id NUMERIC(5),
    permission  NUMERIC(5),
    token  VARCHAR(50),  
    CONSTRAINT PROJECT_USER_PK PRIMARY KEY (project_id,user_id),
    CONSTRAINT PU_USER_FK FOREIGN KEY(user_id) REFERENCES "LABGC".T_USER(id),
    CONSTRAINT PU_PROJECT_FK FOREIGN KEY(project_id) REFERENCES "LABGC".T_PROJECT(id));

CREATE TABLE "LABGC".T_REVISION(
    id NUMERIC(10),
    date timestamp,
    description VARCHAR(200),
    number VARCHAR(50),
    user_id NUMERIC(5),
    project_id NUMERIC(5),
    CONSTRAINT REVISION_PK PRIMARY KEY(id),
    CONSTRAINT REV_PU_FK FOREIGN KEY(project_id,user_id) REFERENCES "LABGC".T_PROJECT_USER(project_id,user_id)); 

CREATE TABLE "LABGC".T_CONFIGURATION_ITEM(
    id NUMERIC(20),
    name VARCHAR(300),
    hash VARCHAR(20),
    type CHAR,
    next_id NUMERIC(20),
    previous_id NUMERIC(20),
    number NUMERIC(10),
    CONSTRAINT CONFIGURATION_ITEM_PK PRIMARY KEY(id),
    CONSTRAINT REVISION_FK FOREIGN KEY(revision_id) REFERENCES "LABGC".T_REVISION(id),
    CONSTRAINT NEXT_FK FOREIGN KEY(next_id) REFERENCES "LABGC".T_CONFIGURATION_ITEM(id),
    CONSTRAINT PREVIOUS_FK FOREIGN KEY(previous_id) REFERENCES "LABGC".T_CONFIGURATION_ITEM(id));

CREATE TABLE "LABGC".T_REVISION_CI(
    revision_id NUMERIC(10),
    ci_id NUMERIC(20), 
    CONSTRAINT RCI_PK PRIMARY KEY (revision_id,ci_id),
    CONSTRAINT RCI_REVISION_FK FOREIGN KEY(revision_id) REFERENCES "LABGC".T_REVISION(id),
    CONSTRAINT RCI_CONFIG_ITEM_FK FOREIGN KEY(ci_id) REFERENCES "LABGC".T_CONFIGURATION_ITEM(id));