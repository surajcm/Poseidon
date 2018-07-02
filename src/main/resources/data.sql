

insert into user (id, name, loginid, password, role, createdOn, modifiedOn, createdBy, modifiedBy) values
(1, 'admin', 'admin', '$2a$10$swK6dfjfSwe9Ql0twZnvCecSYCu2v4Sp659pVg2DdIoWtoDcr/xMq', 'ROLE_ADMIN',
'2012-06-02 00:00:00','2012-06-02 00:00:00','admin','admin');

insert into companyterms (id, terms, companyAddress, createdOn, modifiedOn, createdBy, modifiedBy) values
(1, 'terms', 'my company','2012-06-02 00:00:00','2012-06-02 00:00:00','admin','admin');
