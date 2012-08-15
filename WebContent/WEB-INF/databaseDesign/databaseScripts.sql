-- expecting to use mysql
-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.5.8


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema poseidon
--

CREATE DATABASE IF NOT EXISTS poseidon;
USE poseidon;

--
-- Definition of table `companyterms`
--

DROP TABLE IF EXISTS `companyterms`;
CREATE TABLE `companyterms` (
  `Id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Terms` varchar(500) DEFAULT NULL,
  `CompanyDetails` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `companyterms`
--

/*!40000 ALTER TABLE `companyterms` DISABLE KEYS */;
/*!40000 ALTER TABLE `companyterms` ENABLE KEYS */;


--
-- Definition of table `customer`
--

DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `Id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `Name` varchar(150) NOT NULL,
  `Address1` varchar(200) NOT NULL,
  `address2` varchar(200) NOT NULL,
  `Phone` varchar(20) NOT NULL,
  `Mobile` varchar(20) NOT NULL,
  `email` varchar(20) NOT NULL,
  `ContactPerson1` varchar(150) NOT NULL,
  `ContactPh1` varchar(20) NOT NULL,
  `ContactPerson2` varchar(150) NOT NULL,
  `ContactPh2` varchar(20) NOT NULL,
  `Note` varchar(500) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customer`
--

/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;


--
-- Definition of table `make`
--

DROP TABLE IF EXISTS `make`;
CREATE TABLE `make` (
  `Id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `MakeName` varchar(45) NOT NULL,
  `Description` varchar(200) DEFAULT NULL,
  `createdOn` datetime NOT NULL,
  `modifiedOn` datetime DEFAULT NULL,
  `createdBy` varchar(45) NOT NULL,
  `modifiedBy` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `make`
--

/*!40000 ALTER TABLE `make` DISABLE KEYS */;
/*!40000 ALTER TABLE `make` ENABLE KEYS */;


--
-- Definition of table `model`
--

DROP TABLE IF EXISTS `model`;
CREATE TABLE `model` (
  `Id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `ModelName` varchar(145) NOT NULL,
  `makeId` bigint(20) unsigned NOT NULL,
  `createdOn` datetime NOT NULL,
  `modifiedOn` datetime DEFAULT NULL,
  `createdBy` varchar(45) NOT NULL,
  `modifiedBy` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Id`,`makeId`),
  KEY `FK_model_MakeId` (`makeId`),
  CONSTRAINT `FK_model_MakeId` FOREIGN KEY (`makeId`) REFERENCES `make` (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `model`
--

/*!40000 ALTER TABLE `model` DISABLE KEYS */;
/*!40000 ALTER TABLE `model` ENABLE KEYS */;


--
-- Definition of table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
CREATE TABLE `transaction` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `TagNo` varchar(45) NOT NULL,
  `DateReported` datetime NOT NULL,
  `CustomerId` bigint(20) unsigned NOT NULL,
  `ProductCategory` varchar(45) NOT NULL,
  `MakeId` bigint(20) unsigned NOT NULL,
  `ModelId` bigint(20) unsigned NOT NULL,
  `SerialNo` varchar(45) NOT NULL,
  `Accessories` varchar(200) DEFAULT NULL,
  `ComplaintReported` varchar(200) DEFAULT NULL,
  `ComplaintDiagonsed` varchar(200) DEFAULT NULL,
  `EnggRemark` varchar(200) DEFAULT NULL,
  `RepairAction` varchar(200) DEFAULT NULL,
  `Note` varchar(500) DEFAULT NULL,
  `Status` varchar(45) NOT NULL,
  `createdOn` datetime NOT NULL,
  `modifiedOn` datetime NOT NULL,
  `createdBy` varchar(45) NOT NULL,
  `ModifiedBy` varchar(45) NOT NULL,
  PRIMARY KEY (`id`,`CustomerId`,`MakeId`,`ModelId`),
  KEY `FK_transaction_Customer` (`CustomerId`),
  KEY `FK_transaction_Make` (`MakeId`),
  KEY `FK_transaction_Model` (`ModelId`,`MakeId`),
  CONSTRAINT `FK_transaction_Customer` FOREIGN KEY (`CustomerId`) REFERENCES `customer` (`Id`),
  CONSTRAINT `FK_transaction_Make` FOREIGN KEY (`MakeId`) REFERENCES `make` (`Id`),
  CONSTRAINT `FK_transaction_Model` FOREIGN KEY (`ModelId`, `MakeId`) REFERENCES `model` (`Id`, `makeId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transaction`
--

/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;


--
-- Definition of table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) NOT NULL,
  `LogId` varchar(45) NOT NULL,
  `Pass` varchar(45) NOT NULL,
  `Role` varchar(45) NOT NULL,
  `createdOn` datetime NOT NULL,
  `modifiedOn` datetime DEFAULT NULL,
  `createdBy` varchar(45) NOT NULL,
  `modifiedBy` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`,`Name`,`LogId`,`Pass`,`Role`,`createdOn`,`modifiedOn`,`createdBy`,`modifiedBy`) VALUES
 (1,'admin','admin','admin','ADMIN','2012-06-02 00:00:00','2012-06-02 00:00:00','admin','admin'),
 (2,'guest','guest','guest','GUEST','2012-06-02 00:00:00','2012-06-02 00:00:00','admin','admin');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
INSERT INTO companyterms (Id, Terms, CompanyDetails) VALUES
(1, 'terms', 'my company');



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
-- grant insert on companyterms to localuser@localhost;
-- grant insert on customer to localuser@localhost;
-- grant insert on make to localuser@localhost;
-- grant insert on model to localuser@localhost;
-- grant insert on transaction to localuser@localhost;
-- grant insert on user to localuser@localhost;
-- grant update on companyterms to localuser@localhost;
-- grant update on customer to localuser@localhost;
-- grant update on make to localuser@localhost;
-- grant update on model to localuser@localhost;
-- grant update on transaction to localuser@localhost;
-- grant update on user to localuser@localhost;
-- grant delete on companyterms to localuser@localhost;
-- grant delete on customer to localuser@localhost;
-- grant delete on make to localuser@localhost;
-- grant delete on model to localuser@localhost;
-- grant delete on transaction to localuser@localhost;
-- grant delete on user to localuser@localhost;

-- UPGRADES--
ALTER TABLE `poseidon`.`transaction` MODIFY COLUMN `TagNo` VARCHAR(45) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL;

ALTER TABLE `poseidon`.`customer` ADD COLUMN `createdOn` DATETIME NOT NULL AFTER `Note`,
 ADD COLUMN `modifiedOn` DATETIME NOT NULL AFTER `createdOn`,
 ADD COLUMN `createdBy` VARCHAR(45) NOT NULL AFTER `modifiedOn`,
 ADD COLUMN `modifiedBy` VARCHAR(45) NOT NULL AFTER `createdBy`;

 DROP TABLE IF EXISTS `poseidon`.`invoice`;
CREATE TABLE  `poseidon`.`invoice` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `TranId` int(10) unsigned NOT NULL,
  `Description1` varchar(150) NOT NULL,
  `Description2` varchar(150) NOT NULL,
  `Amount` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


ALTER TABLE `poseidon`.`transaction` CHANGE COLUMN `TagNo` `tagNo` VARCHAR(45) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
 CHANGE COLUMN `DateReported` `dateReported` DATETIME NOT NULL,
 CHANGE COLUMN `CustomerId` `customerId` BIGINT(20) UNSIGNED NOT NULL,
 CHANGE COLUMN `ProductCategory` `productCategory` VARCHAR(45) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
 CHANGE COLUMN `MakeId` `makeId` BIGINT(20) UNSIGNED NOT NULL,
 CHANGE COLUMN `ModelId` `modelId` BIGINT(20) UNSIGNED NOT NULL,
 CHANGE COLUMN `SerialNo` `serialNo` VARCHAR(45) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
 CHANGE COLUMN `Accessories` `accessories` VARCHAR(200) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
 CHANGE COLUMN `ComplaintReported` `complaintReported` VARCHAR(200) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
 CHANGE COLUMN `ComplaintDiagonsed` `complaintDiagnosed` VARCHAR(200) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
 CHANGE COLUMN `EnggRemark` `engineerRemarks` VARCHAR(200) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
 CHANGE COLUMN `RepairAction` `repairAction` VARCHAR(200) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
 CHANGE COLUMN `Note` `note` VARCHAR(500) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
 CHANGE COLUMN `Status` `status` VARCHAR(45) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
 CHANGE COLUMN `ModifiedBy` `modifiedBy` VARCHAR(45) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
 DROP PRIMARY KEY,
 ADD PRIMARY KEY  USING BTREE(`id`, `customerId`, `makeId`, `modelId`),
 DROP INDEX `FK_transaction_Customer`,
 ADD INDEX `FK_transaction_Customer` USING BTREE(`customerId`),
 DROP INDEX `FK_transaction_Make`,
 ADD INDEX `FK_transaction_Make` USING BTREE(`makeId`),
 DROP INDEX `FK_transaction_Model`,
 ADD INDEX `FK_transaction_Model` USING BTREE(`modelId`, `makeId`);

ALTER TABLE `poseidon`.`companyterms` CHANGE COLUMN `Terms` `terms` VARCHAR(500) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
 CHANGE COLUMN `CompanyDetails` `companyDetails` VARCHAR(500) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL;


ALTER TABLE `poseidon`.`customer` CHANGE COLUMN `Id` `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
 CHANGE COLUMN `Name` `name` VARCHAR(150) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
 CHANGE COLUMN `Address1` `address1` VARCHAR(200) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
 CHANGE COLUMN `Phone` `phone` VARCHAR(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
 CHANGE COLUMN `Mobile` `mobile` VARCHAR(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
 CHANGE COLUMN `ContactPerson1` `contactPerson1` VARCHAR(150) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
 CHANGE COLUMN `ContactPh1` `contactPhone1` VARCHAR(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
 CHANGE COLUMN `ContactPerson2` `contactPerson2` VARCHAR(150) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
 CHANGE COLUMN `ContactPh2` `contactPhone2` VARCHAR(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
 CHANGE COLUMN `Note` `note` VARCHAR(500) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
 DROP PRIMARY KEY,
 ADD PRIMARY KEY  USING BTREE(`id`);

ALTER TABLE `poseidon`.`invoice` CHANGE COLUMN `TranId` `tranId` INT(10) UNSIGNED NOT NULL,
 CHANGE COLUMN `Description1` `description1` VARCHAR(150) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
 CHANGE COLUMN `Description2` `description2` VARCHAR(150) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
 CHANGE COLUMN `Amount` `amount` VARCHAR(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL;

 ALTER TABLE `poseidon`.`make` CHANGE COLUMN `Id` `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
 CHANGE COLUMN `MakeName` `makeName` VARCHAR(45) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
 CHANGE COLUMN `Description` `description` VARCHAR(200) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
 DROP PRIMARY KEY,
 ADD PRIMARY KEY  USING BTREE(`id`);

 ALTER TABLE `poseidon`.`model` CHANGE COLUMN `Id` `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
 CHANGE COLUMN `ModelName` `modelName` VARCHAR(145) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
 DROP PRIMARY KEY,
 ADD PRIMARY KEY  USING BTREE(`id`, `makeId`);

 ALTER TABLE `poseidon`.`user` CHANGE COLUMN `Name` `name` VARCHAR(45) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
 CHANGE COLUMN `LogId` `logInId` VARCHAR(45) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
 CHANGE COLUMN `Pass` `password` VARCHAR(45) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
 CHANGE COLUMN `Role` `role` VARCHAR(45) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL;

ALTER TABLE `poseidon`.`invoice` ADD COLUMN `quantity` VARCHAR(45) AFTER `amount`,
 ADD COLUMN `rate` VARCHAR(45) AFTER `quantity`;

ALTER TABLE `poseidon`.`invoice` MODIFY COLUMN `tranId` INT(10) UNSIGNED DEFAULT NULL;

ALTER TABLE `poseidon`.`invoice` CHANGE COLUMN `description1` `description` VARCHAR(150) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
 CHANGE COLUMN `description2` `serialNo` VARCHAR(150) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL;

ALTER TABLE `poseidon`.`invoice` ADD COLUMN `customerId` INTEGER UNSIGNED NOT NULL AFTER `rate`,
 ADD COLUMN `customerName` VARCHAR(45) NOT NULL AFTER `customerId`;

 ALTER TABLE `poseidon`.`invoice` ADD COLUMN `createDate` DATETIME NOT NULL AFTER `customerName`,
 ADD COLUMN `createdBy` VARCHAR(45) NOT NULL AFTER `createDate`,
 ADD COLUMN `modifyDate` DATETIME NOT NULL AFTER `createdBy`,
 ADD COLUMN `modifiedBy` VARCHAR(45) NOT NULL AFTER `modifyDate`;

ALTER TABLE `poseidon`.`invoice` CHANGE COLUMN `createDate` `createdOn` DATETIME NOT NULL,
 CHANGE COLUMN `modifyDate` `modifiedOn` DATETIME NOT NULL;

 ALTER TABLE `poseidon`.`invoice` CHANGE COLUMN `tranId` `transactionId` INT(10) UNSIGNED DEFAULT NULL;

ALTER TABLE `poseidon`.`invoice` ADD COLUMN `tagNo` VARCHAR(45) NOT NULL AFTER `modifiedBy`;
 ALTER TABLE `poseidon`.`invoice` MODIFY COLUMN `transactionId` VARCHAR(45) DEFAULT NULL;


ALTER TABLE `poseidon`.`companyterms` CHANGE COLUMN `Id` `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
 ADD COLUMN `companyName` VARCHAR(45) AFTER `companyDetails`,
 ADD COLUMN `companyPhone` VARCHAR(45) AFTER `companyName`,
 ADD COLUMN `companyEmail` VARCHAR(45) AFTER `companyPhone`,
 ADD COLUMN `companyWebsite` VARCHAR(45) AFTER `companyEmail`,
 ADD COLUMN `createdOn` DATETIME AFTER `companyWebsite`,
 ADD COLUMN `modifiedOn` DATETIME AFTER `createdOn`,
 ADD COLUMN `createdBy` VARCHAR(45) AFTER `modifiedOn`,
 ADD COLUMN `modifiedBy` VARCHAR(45) AFTER `createdBy`,
 DROP PRIMARY KEY,
 ADD PRIMARY KEY  USING BTREE(`id`);

 ALTER TABLE `poseidon`.`companyterms` CHANGE COLUMN `companyDetails` `companyAddress` VARCHAR(500) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL;

 ALTER TABLE `poseidon`.`companyterms` ADD COLUMN `vat_tin` VARCHAR(45) AFTER `modifiedBy`,
 ADD COLUMN `cst_tin` VARCHAR(45) AFTER `vat_tin`;