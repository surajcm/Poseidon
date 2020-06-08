

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

insert into customer (id, address1, address2, contactPerson1, contactPerson2, contactPhone1, contactPhone2,
createdBy, createdOn, email, mobile, modifiedBy, modifiedOn, name, note, phone) values
(1, '5630 N Sheridan', 'Chicago', 'John', 'Hartigan', '888999777', '333555666', 'admin', '2020-06-08 00:00:00',
 'test@testmail.com', '555888999', 'admin', '2020-06-08 00:00:00', 'John Wick', 'Fix in 10 days', '0404 232323')

insert into customer_additional_details (id, contactPerson1, contactPerson2, contactPhone1, contactPhone2,
 createdBy, createdOn, customerId, modifiedBy, modifiedOn, note) values
 (1, 'John', 'Hartigan', '888999777', '333555666',  'admin', '2020-06-08 00:00:00',
  1,'admin', '2020-06-08 00:00:00', 'Fix in 10 days')