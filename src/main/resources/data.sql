

insert into member (id, name, email, password, role, expired, createdOn, modifiedOn, createdBy, modifiedBy) values
(1, 'admin', 'admin@admin.com', '$2a$10$swK6dfjfSwe9Ql0twZnvCecSYCu2v4Sp659pVg2DdIoWtoDcr/xMq', 'ADMIN', false,
'2012-06-02 00:00:00','2012-06-02 00:00:00','admin','admin');

insert into member (id, name, email, password, role, expired, createdOn, modifiedOn, createdBy, modifiedBy) values
(2, 'guest', 'guest@guest.com', '$2a$10$mNTOvX7clxxtPcNmk1LI0.Daz5f66RyeBwijmNOT7aEpuDdn2nULK', 'GUEST', false,
'2019-10-15 00:00:00','2019-10-15 00:00:00','admin','admin');

insert into member (id, name, email, password, role, expired, createdOn, modifiedOn, createdBy, modifiedBy) values
(3, 'manager1', 'manager@manager.com', '$2a$10$mNTOvX7clxxtPcNmk1LI0.Daz5f66RyeBwijmNOT7aEpuDdn2nULK', 'ADMIN', false,
'2019-10-15 00:00:00','2019-10-15 00:00:00','admin','admin');

insert into member (id, name, email, password, role, expired, createdOn, modifiedOn, createdBy, modifiedBy) values
(4, 'manager2', 'manager.2@manager.com', '$2a$10$mNTOvX7clxxtPcNmk1LI0.Daz5f66RyeBwijmNOT7aEpuDdn2nULK', 'ADMIN', false,
'2019-10-15 00:00:00','2019-10-15 00:00:00','admin','admin');

insert into companyterms (id, companyName, companyCode, companyAddress, companyPhone, companyEmail, companyWebsite,
	vatTin, cstTin, terms,createdOn, modifiedOn, createdBy, modifiedBy) values
(1, 'Queen Consolidated', 'QC01' ,'Star City; Washington', '9999993939','oliver@queen.com',
	'www.queen-industries.com','VAT12345TT','CST12345TT',
	'Queen Consolidated was a company founded by Robert Queen and run primarily by the Queen family.',
	'2012-06-02 00:00:00','2012-06-02 00:00:00','admin','admin');

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

insert into model (id, modelId, modelName, makeId, createdOn, modifiedOn, createdBy, modifiedBy) values
(1, 1, 'MacBook Pro', 1 ,'2019-08-01 00:00:00','2019-08-01 06:56:00','admin','admin');

insert into model (id, modelId, modelName, makeId, createdOn, modifiedOn, createdBy, modifiedBy) values
(2, 2, 'MacBook Air', 1 ,'2019-08-01 00:00:00','2019-08-01 09:23:00','admin','admin');

insert into model (id, modelId, modelName, makeId, createdOn, modifiedOn, createdBy, modifiedBy) values
(3, 3, 'Inspiron', 2 ,'2019-08-01 00:00:00','2019-08-01 09:23:00','admin','admin');

insert into model (id, modelId, modelName, makeId, createdOn, modifiedOn, createdBy, modifiedBy) values
(4, 4, 'ThinkPad', 3 ,'2019-08-01 00:00:00','2019-08-01 09:25:00','admin','admin');

insert into customer (id, name, address, phone, mobile, email,createdBy, createdOn, modifiedBy, modifiedOn)
values (1, 'John Wick', '5630 N Sheridan, Chicago', '0404 232323', '555888999', 'test@testmail.com',
 'admin', '2020-06-08 00:00:00', 'admin', '2020-06-08 00:00:00');

insert into customer_additional_details (id, customerId, contactPerson, contactPhone,
 note, createdBy, createdOn, modifiedBy, modifiedOn) values
 (1, 1, 'John Hartigan', '888999777', 'Fix in 10 days',
  'admin', '2020-06-08 00:00:00', 'admin', '2020-06-08 00:00:00');

insert into customer (id, name, address, phone, mobile, email, createdBy, createdOn, modifiedBy, modifiedOn)
 values (2, 'Nick Fury', '500 W Madison, Chicago', '0404 343242', '87878789', 'director@shield.com',
  'admin', '2020-06-13 00:00:00', 'admin', '2020-06-13 00:00:00');

insert into customer_additional_details (id, customerId, contactPerson, contactPhone,
 note, createdBy, createdOn, modifiedBy, modifiedOn) values
 (2, 2, 'Philip Coulson', '3322552233', 'Send over for parts',
 'admin', '2020-06-13 00:00:00', 'admin', '2020-06-13 00:00:00');

insert into customer (id, name, address, phone, mobile, email, createdBy, createdOn, modifiedBy, modifiedOn)
values (3, 'Steve Rogers', '5650 N Clark, California', '0424 1978', '5557771978', 'steve@mcu.com',
 'admin', '2020-06-15 00:00:00', 'admin', '2020-06-15 00:00:00');

insert into customer_additional_details (id, customerId, contactPerson, contactPhone,
 note, createdBy, createdOn, modifiedBy, modifiedOn) values
 (3, 3, 'Tony Stark', '8989898', 'Dig legacy pc parts',
 'admin', '2020-06-15 00:00:00', 'admin', '2020-06-15 00:00:00');

insert into customer (id, name, address, phone, mobile, email, createdBy, createdOn, modifiedBy, modifiedOn)
values (4, 'Ras al Ghul', 'League of Assassins, Nanda Parbat', '0444 1999', '90909090', 'ras@dcu.com',
 'admin', CURRENT_DATE, 'admin', CURRENT_DATE);

insert into customer_additional_details (id, customerId, contactPerson, contactPhone,
 note, createdBy, createdOn, modifiedBy, modifiedOn) values
 (4, 4, 'Oliver Queen', '70707070', 'Find Lazarus Pit',
 'admin', CURRENT_DATE, 'admin', CURRENT_DATE);


insert into customer (id, name, address, phone, mobile, email, createdBy, createdOn, modifiedBy, modifiedOn)
values (5, 'Vision', 'Brooklyn, New York', '0808 2021', '20112020', 'vis@mcu.com',
        'admin', CURRENT_DATE, 'admin', CURRENT_DATE);

insert into customer_additional_details (id, customerId, contactPerson, contactPhone,
                                         note, createdBy, createdOn, modifiedBy, modifiedOn) values
    (5, 5, 'Wanda Maximoff', '929203', 'Become Scarlet Witch',
     'admin', CURRENT_DATE, 'admin', CURRENT_DATE);

insert into transaction (id, tagNo, dateReported, customerId, productCategory, makeId, modelId,
serialNumber, accessories, complaintReported, complaintDiagnosed, engineerRemarks, repairAction, note, status,
 createdBy, createdOn, modifiedBy, modifiedOn) values (1, 'WON2N1', '2020-06-15 00:00:00', 1 , 'LAPTOP', 1, 1,
 'AEPEP88990', 'Charger', 'Startup failure', 'Battery connectivity', 'Power circuit broken',
 'Replace Board', 'Purchase new board', 'ACCEPTED',
 'admin', '2020-06-15 00:00:00', 'admin', '2020-06-15 00:00:00');

insert into transaction (id, tagNo, dateReported, customerId, productCategory, makeId, modelId,
serialNumber, accessories, complaintReported, complaintDiagnosed, engineerRemarks, repairAction, note, status,
 createdBy, createdOn, modifiedBy, modifiedOn) values (2, 'WON2N2', '2020-06-16 00:00:00', 2 , 'LAPTOP', 1, 2,
 'PQRS12345', 'Charger', 'Display failure', 'Display connectivity', 'Display circuit broken',
 'Replace Board', 'Purchase new board', 'VERIFIED',
 'admin', '2020-06-16 00:00:00', 'admin', '2020-06-16 00:00:00');

insert into transaction (id, tagNo, dateReported, customerId, productCategory, makeId, modelId,
serialNumber, accessories, complaintReported, complaintDiagnosed, engineerRemarks, repairAction, note, status,
 createdBy, createdOn, modifiedBy, modifiedOn) values (3, 'WON2N3', CURRENT_DATE, 3 , 'LAPTOP', 2, 3,
 'ZZZ345345', 'Dock', 'Unresponsive OS', 'RAM issues', 'Clean up RAM',
 'RUN RAM cleaner', 'Run memtest', 'VERIFIED',
 'admin', CURRENT_DATE, 'admin', CURRENT_DATE);

insert into transaction (id, tagNo, dateReported, customerId, productCategory, makeId, modelId,
serialNumber, accessories, complaintReported, complaintDiagnosed, engineerRemarks, repairAction, note, status,
 createdBy, createdOn, modifiedBy, modifiedOn) values (4, 'WON2N4', CURRENT_DATE, 4 , 'TABLET', 1, 1,
 'III67676', 'Charger', 'Broken screen', 'Broken screen', 'Replace screen',
 'Replace screen', 'Purchase screen', 'INVOICED',
 'admin', CURRENT_DATE, 'admin', CURRENT_DATE);

insert into transaction (id, tagNo, dateReported, customerId, productCategory, makeId, modelId,
serialNumber, accessories, complaintReported, complaintDiagnosed, engineerRemarks, repairAction, note, status,
 createdBy, createdOn, modifiedBy, modifiedOn) values (5, 'WON2N5', CURRENT_DATE, 5 , 'PHONE', 1, 2,
 'III67676', 'Charger', 'Broken screen', 'Broken screen', 'Replace screen',
 'Broken screen', 'Purchase screen', 'INVOICED',
 'admin', CURRENT_DATE, 'admin', CURRENT_DATE);

insert into invoice (id, transactionId, description, serialNumber ,amount ,quantity , rate ,
 customerId, customerName, tagNumber ,  createdBy, createdOn, modifiedBy, modifiedOn) values
  (1, 4, 'SERVICE CHARGES FOR Apple MacBook Pro', 'III67676', 1000, 2, 500,
 4, 'Ras al Ghul', 'WON2N4', 'admin', CURRENT_DATE, 'admin', CURRENT_DATE);

insert into invoice (id, transactionId, description, serialNumber ,amount ,quantity , rate ,
 customerId, customerName, tagNumber ,  createdBy, createdOn, modifiedBy, modifiedOn) values
  (2, 5, 'SERVICE CHARGES FOR Apple MacBook Air', 'III67677', 1200, 20, 60,
 5, 'Vision', 'WON2N5', 'admin', CURRENT_DATE, 'admin', CURRENT_DATE);
