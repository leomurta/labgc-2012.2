insert into LABGC.T_USER (id,name,username,password) values 
(1,'nome1','username1','password1');

insert into LABGC.T_PROJECT (id,name) values 
(1,'projeto1');

insert into LABGC.T_PROJECT_USER (project_id,user_id,permission,token) values 
(1,1,11111,'nvfdovhfdoivbiofdvf');

insert into LABGC.T_REVISION (id,date,description,number,user_id,project_id) values 
(1,'13/10/2012','revisao 1','1.0',1,1);

insert into LABGC.T_CONFIGURATION_ITEM (id,name,type,is_dir,size,revision_id,number) values 
(1,'raiz/','A',1,15,1,1);

insert into LABGC.T_CONFIGURATION_ITEM (id,name,hash,type,is_dir,size,revision_id,number) values 
(2,'raiz/arquivo1.txt','vnfdovh9e0h0','A',0,10,1,1);

insert into LABGC.T_CONFIGURATION_ITEM (id,name,hash,type,is_dir,size,revision_id,number) values 
(3,'raiz/arquivo2.txt','vcdfsniovfbiov','A',0,5,1,1);

insert into LABGC.T_CHILDREN (parent_id,child_id) values (1,2);
insert into LABGC.T_CHILDREN (parent_id,child_id) values (1,3);



