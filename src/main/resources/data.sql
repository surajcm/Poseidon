
insert into companyterms (id, name, code, address, phone, email, website,
                          vatTin, cstTin, terms,createdOn, modifiedOn, createdBy, modifiedBy) values
    (1, 'Queen Consolidated', 'QC01' ,'Star City; Washington', '9999993939','oliver@queen.com',
     'www.queen-industries.com','VAT12345TT','CST12345TT',
     'Queen Consolidated was a company founded by Robert Queen and run primarily by the Queen family.',
     '2012-06-02 00:00:00','2012-06-02 00:00:00','admin','admin');

insert into companyterms (id, name, code, address, phone, email, website,
                          vatTin, cstTin, terms,createdOn, modifiedOn, createdBy, modifiedBy) values
    (2, 'Wayne Enterprises', 'WE01' ,'Gotham City; New Jersey', '9999993940','bruce@wayne.com',
     'www.wayne-enterprises.com','VAT12567TT','CST12567TT',
     'Wayne Enterprises and the Wayne Foundation are largely run by Bruce Waynes business manager, Lucius Fox.',
     '2012-06-02 00:00:00','2012-06-02 00:00:00','admin','admin');


insert into member (id, name, email, password, enabled, createdOn, modifiedOn, createdBy, modifiedBy) values
(1, 'admin', 'admin@admin.com', '$2a$10$swK6dfjfSwe9Ql0twZnvCecSYCu2v4Sp659pVg2DdIoWtoDcr/xMq', true,
'2012-06-02 00:00:00','2012-06-02 00:00:00','admin','admin');

insert into member (id, name, email, password, companyCode, enabled, createdOn, modifiedOn, createdBy, modifiedBy) values
(2, 'guest', 'guest@guest.com', '$2a$10$mYqHoY9ku7MfbKBzrLBkT.NOwbFkXlYDDdns6XwFY0nRA1EdJPWTi', 'QC01', true,
'2019-10-15 00:00:00','2019-10-15 00:00:00','admin','admin');

insert into member (id, name, email, password, companyCode, enabled, createdOn, modifiedOn, createdBy, modifiedBy) values
(3, 'manager1', 'manager@manager.com', '$2a$10$sOirKTjm2JSRLlkOTik6FeGnXJJG1PH5UtQtYUfHccRo0xB086YiC', 'WE01', true,
'2019-10-15 00:00:00','2019-10-15 00:00:00','admin','admin');

insert into member (id, name, email, password, companyCode, enabled, createdOn, modifiedOn, createdBy, modifiedBy) values
(4, 'manager2', 'manager.2@manager.com', '$2a$10$f6GSOdoeKUsVobCrb4dVn.RqWNXps.fQOyE5IWxHFld5nsxwQjnhS', 'WE01', true,
'2019-10-15 00:00:00','2019-10-15 00:00:00','admin','admin');

insert into member (id, name, email, password, companyCode, enabled, createdOn, modifiedOn, createdBy, modifiedBy) values
(5, 'manager3', 'manager.3@manager.com', '$2a$10$f6GSOdoeKUsVobCrb4dVn.RqWNXps.fQOyE5IWxHFld5nsxwQjnhS', 'WE01', true,
'2024-03-02 00:00:00','2024-03-02 00:00:00','admin','admin');

insert into member (id, name, email, password, companyCode, enabled, createdOn, modifiedOn, createdBy, modifiedBy) values
(6, 'manager4', 'manager.4@manager.com', '$2a$10$f6GSOdoeKUsVobCrb4dVn.RqWNXps.fQOyE5IWxHFld5nsxwQjnhS', 'QC01', true,
'2024-03-02 00:00:00','2024-03-02 00:00:00','admin','admin');

insert into member (id, name, email, password, companyCode, enabled, createdOn, modifiedOn, createdBy, modifiedBy) values
(7, 'manager5', 'manager.5@manager.com', '$2a$10$f6GSOdoeKUsVobCrb4dVn.RqWNXps.fQOyE5IWxHFld5nsxwQjnhS', 'QC01', true,
'2024-03-02 00:00:00','2024-03-02 00:00:00','admin','admin');

insert into member (id, name, email, password, companyCode, enabled, createdOn, modifiedOn, createdBy, modifiedBy) values
(8, 'manager6', 'manager.6@manager.com', '$2a$10$f6GSOdoeKUsVobCrb4dVn.RqWNXps.fQOyE5IWxHFld5nsxwQjnhS', 'QC01', true,
'2024-03-02 00:00:00','2024-03-02 00:00:00','admin','admin');

insert into member (id, name, email, password, companyCode, enabled, createdOn, modifiedOn, createdBy, modifiedBy) values
(9, 'manager7', 'manager.7@manager.com', '$2a$10$f6GSOdoeKUsVobCrb4dVn.RqWNXps.fQOyE5IWxHFld5nsxwQjnhS', 'QC01', true,
'2024-03-02 00:00:00','2024-03-02 00:00:00','admin','admin');

insert into member (id, name, email, password, companyCode, enabled, createdOn, modifiedOn, createdBy, modifiedBy) values
(10, 'manager8', 'manager.8@manager.com', '$2a$10$f6GSOdoeKUsVobCrb4dVn.RqWNXps.fQOyE5IWxHFld5nsxwQjnhS', 'QC01', true,
'2024-03-02 00:00:00','2024-03-02 00:00:00','admin','admin');

insert into member (id, name, email, password, companyCode, enabled, createdOn, modifiedOn, createdBy, modifiedBy) values
(11, 'manager9', 'manager.9@manager.com', '$2a$10$f6GSOdoeKUsVobCrb4dVn.RqWNXps.fQOyE5IWxHFld5nsxwQjnhS', 'QC01', true,
'2024-03-02 00:00:00','2024-03-02 00:00:00','admin','admin');

insert into member (id, name, email, password, companyCode, enabled, createdOn, modifiedOn, createdBy, modifiedBy) values
(12, 'manager10', 'manager.10@manager.com', '$2a$10$f6GSOdoeKUsVobCrb4dVn.RqWNXps.fQOyE5IWxHFld5nsxwQjnhS', 'QC01', true,
'2024-03-02 00:00:00','2024-03-02 00:00:00','admin','admin');

insert into member (id, name, email, password, companyCode, enabled, createdOn, modifiedOn, createdBy, modifiedBy) values
(13, 'manager11', 'manager.11@manager.com', '$2a$10$f6GSOdoeKUsVobCrb4dVn.RqWNXps.fQOyE5IWxHFld5nsxwQjnhS', 'QC01', true,
     '2024-03-02 00:00:00','2024-03-02 00:00:00','admin','admin');

insert into member (id, name, email, password, companyCode, enabled, createdOn, modifiedOn, createdBy, modifiedBy) values
(14, 'manager12', 'manager.12@manager.com', '$2a$10$f6GSOdoeKUsVobCrb4dVn.RqWNXps.fQOyE5IWxHFld5nsxwQjnhS', 'QC01', true,
     '2024-03-02 00:00:00','2024-03-02 00:00:00','admin','admin');

insert into member (id, name, email, password, companyCode, enabled, createdOn, modifiedOn, createdBy, modifiedBy) values
(15, 'manager13', 'manager.13@manager.com', '$2a$10$f6GSOdoeKUsVobCrb4dVn.RqWNXps.fQOyE5IWxHFld5nsxwQjnhS', 'QC01', true,
     '2024-03-02 00:00:00','2024-03-02 00:00:00','admin','admin');

insert into roles (id, name, description, createdOn, modifiedOn, createdBy, modifiedBy) values
(1, 'ADMIN', 'Administrator of the poseidon','2022-12-12 00:00:00','2022-12-12 06:56:00','admin','admin');

insert into roles (id, name, description, createdOn, modifiedOn, createdBy, modifiedBy) values
(2, 'MANAGER', 'Manager for a single company','2022-12-12 00:00:00','2022-12-12 06:56:00','admin','admin');

insert into roles (id, name, description, createdOn, modifiedOn, createdBy, modifiedBy) values
(3, 'FRONT_DESK', 'Front desk executive for a single company','2022-12-12 00:00:00','2022-12-12 06:56:00','admin','admin');

insert into roles (id, name, description, createdOn, modifiedOn, createdBy, modifiedBy) values
(4, 'CUSTOMER', 'Customer for a single company','2022-12-12 00:00:00','2022-12-12 06:56:00','admin','admin');

insert into users_roles(user_id, role_id) values (1, 1);
insert into users_roles(user_id, role_id) values (2, 2);
insert into users_roles(user_id, role_id) values (3, 3);
insert into users_roles(user_id, role_id) values (4, 4);
insert into users_roles(user_id, role_id) values (5, 4);
insert into users_roles(user_id, role_id) values (6, 4);
insert into users_roles(user_id, role_id) values (7, 4);
insert into users_roles(user_id, role_id) values (8, 4);
insert into users_roles(user_id, role_id) values (9, 4);
insert into users_roles(user_id, role_id) values (10, 4);
insert into users_roles(user_id, role_id) values (11, 4);
insert into users_roles(user_id, role_id) values (12, 4);
insert into users_roles(user_id, role_id) values (13, 4);
insert into users_roles(user_id, role_id) values (14, 4);
insert into users_roles(user_id, role_id) values (15, 4);

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

insert into make (id, makeName, description, createdOn, modifiedOn, createdBy, modifiedBy) values
(6, 'Asus', 'Asus','2024-03-07 00:00:00','2024-03-07 09:21:00','admin','admin');

insert into make (id, makeName, description, createdOn, modifiedOn, createdBy, modifiedBy) values
(7, 'Acer', 'Acer','2024-03-07 00:00:00','2024-03-07 09:21:00','admin','admin');

insert into make (id, makeName, description, createdOn, modifiedOn, createdBy, modifiedBy) values
(8, 'Microsoft', 'Microsoft','2024-03-07 00:00:00','2024-03-07 09:21:00','admin','admin');

insert into make (id, makeName, description, createdOn, modifiedOn, createdBy, modifiedBy) values
(9, 'LG', 'LG','2024-03-07 00:00:00','2024-03-07 09:21:00','admin','admin');

insert into make (id, makeName, description, createdOn, modifiedOn, createdBy, modifiedBy) values
(10, 'Razer', 'Razer','2024-03-07 00:00:00','2024-03-07 09:21:00','admin','admin');

insert into make (id, makeName, description, createdOn, modifiedOn, createdBy, modifiedBy) values
(11, 'MSI', 'MSI','2024-03-07 00:00:00','2024-03-07 09:21:00','admin','admin');

insert into model (id, modelId, modelName, productType, makeId, createdOn, modifiedOn, createdBy, modifiedBy) values
(1, 1, 'MacBook Pro', 'Laptop Computer',1 ,'2019-08-01 00:00:00','2019-08-01 06:56:00','admin','admin');

insert into model (id, modelId, modelName, productType, makeId, createdOn, modifiedOn, createdBy, modifiedBy) values
(2, 2, 'MacBook Air', 'Laptop Computer',1 ,'2019-08-01 00:00:00','2019-08-01 09:23:00','admin','admin');

insert into model (id, modelId, modelName, productType, makeId, createdOn, modifiedOn, createdBy, modifiedBy) values
(3, 3, 'HP Spectre x360', 'Laptop Computer',2 ,'2019-08-01 00:00:00','2019-08-01 09:23:00','admin','admin');

insert into model (id, modelId, modelName, productType, makeId, createdOn, modifiedOn, createdBy, modifiedBy) values
(4, 4, 'Lenovo ThinkPad X1 Carbon', 'Laptop Computer',3 ,'2019-08-01 00:00:00','2019-08-01 09:25:00','admin','admin');

insert into model (id, modelId, modelName, productType, makeId, createdOn, modifiedOn, createdBy, modifiedBy) values
(5, 5, 'XPS 13', 'Laptop Computer',2 ,'2024-03-07 00:00:00','2024-03-07 09:25:00','admin','admin');

insert into model (id, modelId, modelName, productType, makeId, createdOn, modifiedOn, createdBy, modifiedBy) values
(6, 6, 'Inspiron 14 5000', 'Laptop Computer',2 ,'2024-03-07 00:00:00','2024-03-07 09:25:00','admin','admin');

insert into model (id, modelId, modelName, productType, makeId, createdOn, modifiedOn, createdBy, modifiedBy) values
(7, 7, 'HP Spectre x360', 'Laptop Computer',4 ,'2024-03-07 00:00:00','2024-03-07 09:25:00','admin','admin');

insert into model (id, modelId, modelName, productType, makeId, createdOn, modifiedOn, createdBy, modifiedBy) values
(8, 8, 'Acer Swift 3', 'Laptop Computer',7 ,'2024-03-07 00:00:00','2024-03-07 09:25:00','admin','admin');

insert into model (id, modelId, modelName, productType, makeId, createdOn, modifiedOn, createdBy, modifiedBy) values
(9, 9, 'Lenovo Yoga C940', 'Laptop Computer',3 ,'2024-03-07 00:00:00','2024-03-07 09:25:00','admin','admin');

insert into model (id, modelId, modelName, productType, makeId, createdOn, modifiedOn, createdBy, modifiedBy) values
(10, 10, 'Acer Predator Helios 300', 'Laptop Computer',7 ,'2024-03-07 00:00:00','2024-03-07 09:25:00','admin','admin');

insert into model (id, modelId, modelName, productType, makeId, createdOn, modifiedOn, createdBy, modifiedBy) values
(11, 11, 'Asus ZenBook 14', 'Laptop Computer',6 ,'2024-03-07 00:00:00','2024-03-07 09:25:00','admin','admin');

insert into model (id, modelId, modelName, productType, makeId, createdOn, modifiedOn, createdBy, modifiedBy) values
(12, 12, 'Asus ROG Zephyrus G14', 'Laptop Computer',6 ,'2024-03-07 00:00:00','2024-03-07 09:25:00','admin','admin');

insert into model (id, modelId, modelName, productType, makeId, createdOn, modifiedOn, createdBy, modifiedBy) values
(13, 13, 'Surface Laptop 4', 'Laptop Computer',8 ,'2024-03-07 00:00:00','2024-03-07 09:25:00','admin','admin');

insert into model (id, modelId, modelName, productType, makeId, createdOn, modifiedOn, createdBy, modifiedBy) values
(14, 14, 'Surface Pro 7', 'Laptop Computer',8 ,'2024-03-07 00:00:00','2024-03-07 09:25:00','admin','admin');

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

insert into customer (id, name, address, phone, mobile, email, createdBy, createdOn, modifiedBy, modifiedOn)
values (6, 'Bruce Wayne', 'Wayne Manor, Gotham City', '0808 2021', '20112020', 'batman@dcu.com',
        'admin', CURRENT_DATE, 'admin', CURRENT_DATE);

insert into customer_additional_details (id, customerId, contactPerson, contactPhone,
                                         note, createdBy, createdOn, modifiedBy, modifiedOn) values
    (6, 6, 'Alfred Pennyworth', '929203', 'Fix Batmobile','admin', CURRENT_DATE, 'admin', CURRENT_DATE);

insert into customer (id, name, address, phone, mobile, email, createdBy, createdOn, modifiedBy, modifiedOn)
values (7, 'Clark Kent', 'Smallville, Kansas', '0808 2021', '20112020', 'super.man@dcu.com',
        'admin', CURRENT_DATE, 'admin', CURRENT_DATE);

insert into customer_additional_details (id, customerId, contactPerson, contactPhone,
                                         note, createdBy, createdOn, modifiedBy, modifiedOn) values
    (7, 7, 'Lois Lane', '929203', 'Fix Daily Planet','admin', CURRENT_DATE, 'admin', CURRENT_DATE);

insert into customer (id, name, address, phone, mobile, email, createdBy, createdOn, modifiedBy, modifiedOn)
values (8, 'Diana Prince', 'Themyscira', '0808 2021', '20112020', 'wonder.woman@dcu.com',
        'admin', CURRENT_DATE, 'admin', CURRENT_DATE);

insert into customer_additional_details (id, customerId, contactPerson, contactPhone,
                                         note, createdBy, createdOn, modifiedBy, modifiedOn) values
    (8, 8, 'Steve Trevor', '929203', 'Fix Invisible Jet','admin', CURRENT_DATE, 'admin', CURRENT_DATE);

insert into customer (id, name, address, phone, mobile, email, createdBy, createdOn, modifiedBy, modifiedOn)
values (9, 'Barry Allen', 'Central City', '0808 2021', '20112020', 'flash@dcu.com',
        'admin', CURRENT_DATE, 'admin', CURRENT_DATE);

insert into customer_additional_details (id, customerId, contactPerson, contactPhone,
                                         note, createdBy, createdOn, modifiedBy, modifiedOn) values
    (9, 9, 'Iris West', '929203', 'Fix Speed Force','admin', CURRENT_DATE, 'admin', CURRENT_DATE);

insert into customer (id, name, address, phone, mobile, email, createdBy, createdOn, modifiedBy, modifiedOn)
values (10, 'Hal Jordan', 'Coast City', '0808 2021', '20112020', 'green.lantern@dcu.com',
        'admin', CURRENT_DATE, 'admin', CURRENT_DATE);

insert into customer_additional_details (id, customerId, contactPerson, contactPhone,
                                         note, createdBy, createdOn, modifiedBy, modifiedOn) values
    (10, 10, 'Carol Ferris', '929203', 'Fix Power Ring','admin', CURRENT_DATE, 'admin', CURRENT_DATE);

insert into customer (id, name, address, phone, mobile, email, createdBy, createdOn, modifiedBy, modifiedOn)
values (11, 'Arthur Curry', 'Atlantis', '0808 2021', '20112020', 'aqua.man@dcu.com',
        'admin', CURRENT_DATE, 'admin', CURRENT_DATE);

insert into customer_additional_details (id, customerId, contactPerson, contactPhone,
                                         note, createdBy, createdOn, modifiedBy, modifiedOn) values
    (11, 11, 'Mera', '929203', 'Fix Trident','admin', CURRENT_DATE, 'admin', CURRENT_DATE);

insert into customer (id, name, address, phone, mobile, email, createdBy, createdOn, modifiedBy, modifiedOn)
values (12, 'Victor Stone', 'S.T.A.R. Labs', '0808 2021', '20112020', 'cyborg@dcu.com',
        'admin', CURRENT_DATE, 'admin', CURRENT_DATE);

insert into customer_additional_details (id, customerId, contactPerson, contactPhone,
                                         note, createdBy, createdOn, modifiedBy, modifiedOn) values
    (12, 12, 'Silas Stone', '929203', 'Fix Cyborg','admin', CURRENT_DATE, 'admin', CURRENT_DATE);


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
