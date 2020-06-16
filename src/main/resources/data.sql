

insert into user (id, name, loginid, password, role, createdOn, modifiedOn, createdBy, modifiedBy) values
(1, 'admin', 'admin', '$2a$10$swK6dfjfSwe9Ql0twZnvCecSYCu2v4Sp659pVg2DdIoWtoDcr/xMq', 'ADMIN',
'2012-06-02 00:00:00','2012-06-02 00:00:00','admin','admin');

insert into user (id, name, loginid, password, role, createdOn, modifiedOn, createdBy, modifiedBy) values
(2, 'guest', 'guest', '$2a$10$mNTOvX7clxxtPcNmk1LI0.Daz5f66RyeBwijmNOT7aEpuDdn2nULK', 'GUEST',
'2019-10-15 00:00:00','2019-10-15 00:00:00','admin','admin');

insert into user (id, name, loginid, password, role, createdOn, modifiedOn, createdBy, modifiedBy) values
(3, 'manager1', 'manager1', '$2a$10$mNTOvX7clxxtPcNmk1LI0.Daz5f66RyeBwijmNOT7aEpuDdn2nULK', 'ADMIN',
'2019-10-15 00:00:00','2019-10-15 00:00:00','admin','admin');

insert into user (id, name, loginid, password, role, createdOn, modifiedOn, createdBy, modifiedBy) values
(4, 'manager2', 'manager2', '$2a$10$mNTOvX7clxxtPcNmk1LI0.Daz5f66RyeBwijmNOT7aEpuDdn2nULK', 'ADMIN',
'2019-10-15 00:00:00','2019-10-15 00:00:00','admin','admin');

insert into companyterms (id, terms, companyAddress, createdOn, modifiedOn, createdBy, modifiedBy) values
(1, 'terms', 'my company','2012-06-02 00:00:00','2012-06-02 00:00:00','admin','admin');

insert into make (id, makeName, description, createdOn, modifiedOn, createdBy, modifiedBy) values
(1, 'Apple', 'Apple Computers','2019-08-01 00:00:00','2019-08-01 06:56:00','admin','admin');

insert into make (id, makeName, description, createdOn, modifiedOn, createdBy, modifiedBy) values
(2, 'Dell', 'Dell Computers','2019-08-01 00:00:00','2019-08-01 09:18:00','admin','admin');

insert into make (id, makeName, description, createdOn, modifiedOn, createdBy, modifiedBy) values
(3, 'Lenovo', 'Lenovo','2019-08-01 00:00:00','2019-08-01 09:19:00','admin','admin');

insert into make (id, makeName, description, createdOn, modifiedOn, createdBy, modifiedBy) values
(4, 'HP', 'Hewlett Packard','2019-08-01 00:00:00','2019-08-01 09:20:00','admin','admin');

insert into make (id, makeName, description, createdOn, modifiedOn, createdBy, modifiedBy) values
(5, 'Toshiba', 'Toshiba','2019-08-01 00:00:00','2019-08-01 09:21:00','admin','admin');

insert into model (id, modelName, makeId, createdOn, modifiedOn, createdBy, modifiedBy) values
(1, 'MacBook Pro', 1 ,'2019-08-01 00:00:00','2019-08-01 06:56:00','admin','admin');

insert into model (id, modelName, makeId, createdOn, modifiedOn, createdBy, modifiedBy) values
(2, 'MacBook Air', 1 ,'2019-08-01 00:00:00','2019-08-01 09:23:00','admin','admin');

insert into model (id, modelName, makeId, createdOn, modifiedOn, createdBy, modifiedBy) values
(3, 'ThinkPad', 3 ,'2019-08-01 00:00:00','2019-08-01 09:25:00','admin','admin');

insert into customer (id, name, address1, address2, phone, mobile, email,createdBy, createdOn, modifiedBy, modifiedOn)
values (1, 'John Wick', '5630 N Sheridan', 'Chicago', '0404 232323', '555888999', 'test@testmail.com',
 'admin', '2020-06-08 00:00:00', 'admin', '2020-06-08 00:00:00')

insert into customer_additional_details (id, customerId, contactPerson1, contactPerson2, contactPhone1, contactPhone2,
 note, createdBy, createdOn, modifiedBy, modifiedOn) values
 (1, 1, 'John', 'Hartigan', '888999777', '333555666', 'Fix in 10 days',
  'admin', '2020-06-08 00:00:00', 'admin', '2020-06-08 00:00:00')

insert into customer (id, name, address1, address2, phone, mobile, email, createdBy, createdOn, modifiedBy, modifiedOn)
 values (2, 'Nick Fury', '500 W Madison', 'Chicago', '0404 343242', '87878789', 'director@shield.com',
  'admin', '2020-06-13 00:00:00', 'admin', '2020-06-13 00:00:00')

insert into customer_additional_details (id, customerId, contactPerson1, contactPerson2, contactPhone1, contactPhone2,
 note, createdBy, createdOn, modifiedBy, modifiedOn) values
 (2, 2, 'Philip', 'Coulson', '3322552233', '123131331', 'Send over for parts',
 'admin', '2020-06-13 00:00:00', 'admin', '2020-06-13 00:00:00')

insert into customer (id, name, address1, address2, phone, mobile, email, createdBy, createdOn, modifiedBy, modifiedOn)
values (3, 'Steve Rogers', '5650 N Clark', 'California', '0424 1978', '5557771978', 'steve@mcu.com',
 'admin', '2020-06-15 00:00:00', 'admin', '2020-06-15 00:00:00')

insert into customer_additional_details (id, customerId, contactPerson1, contactPerson2, contactPhone1, contactPhone2,
 note, createdBy, createdOn, modifiedBy, modifiedOn) values
 (3, 3, 'Tony', 'Stark', '8989898', '2223131', 'Dig legacy pc parts',
 'admin', '2020-06-15 00:00:00', 'admin', '2020-06-15 00:00:00')

insert into transaction (id, tagNo, dateReported, customerId, productCategory, makeId, modelId,
 serialNo, accessories, complaintReported, complaintDiagnosed, engineerRemarks, repairAction, note, status,
 createdBy, createdOn, modifiedBy, modifiedOn) values (1, 'WON2N1', '2020-06-15 00:00:00', 1 , 'LAPTOP', 1, 1,
 'AEPEP88990', 'Charger', 'Startup failure', 'Battery connectivity', 'Power circuit broken',
 'Replace Board', 'Purchase new board', 'ACCEPTED',
 'admin', '2020-06-15 00:00:00', 'admin', '2020-06-15 00:00:00')