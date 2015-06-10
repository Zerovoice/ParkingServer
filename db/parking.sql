-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.6.25-log


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema parking
--

CREATE DATABASE IF NOT EXISTS parking;
USE parking;

--
-- Definition of table `bidding`
--

DROP TABLE IF EXISTS `bidding`;
CREATE TABLE `bidding` (
  `BiddingID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `businessID` int(10) unsigned NOT NULL,
  `UserID` int(10) unsigned NOT NULL,
  `TimeStart` varchar(45) NOT NULL,
  `TimeEnd` varchar(45) NOT NULL,
  PRIMARY KEY (`BiddingID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `bidding`
--

/*!40000 ALTER TABLE `bidding` DISABLE KEYS */;
/*!40000 ALTER TABLE `bidding` ENABLE KEYS */;


--
-- Definition of table `business`
--

DROP TABLE IF EXISTS `business`;
CREATE TABLE `business` (
  `BusinessID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Area` varchar(45) NOT NULL,
  `MaxUserCount` int(10) unsigned DEFAULT NULL,
  `MaxTendererCount` int(10) unsigned DEFAULT NULL,
  `Cost` varchar(45) NOT NULL,
  `Earnings` varchar(45) NOT NULL,
  `TimeStart` varchar(45) NOT NULL,
  `TimeEnd` varchar(45) NOT NULL,
  PRIMARY KEY (`BusinessID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `business`
--

/*!40000 ALTER TABLE `business` DISABLE KEYS */;
/*!40000 ALTER TABLE `business` ENABLE KEYS */;


--
-- Definition of table `car_info`
--

DROP TABLE IF EXISTS `car_info`;
CREATE TABLE `car_info` (
  `CarNum` varchar(10) NOT NULL,
  `UserID` int(10) unsigned NOT NULL,
  `BiddingID` int(10) unsigned NOT NULL,
  `State` varchar(45) NOT NULL,
  PRIMARY KEY (`CarNum`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=FIXED;

--
-- Dumping data for table `car_info`
--

/*!40000 ALTER TABLE `car_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `car_info` ENABLE KEYS */;


--
-- Definition of table `parking_info`
--

DROP TABLE IF EXISTS `parking_info`;
CREATE TABLE `parking_info` (
  `ParkingID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `CarNum` varchar(10) NOT NULL,
  `LocationLongitude` varchar(45) NOT NULL,
  `LocationLatitude` varchar(45) NOT NULL,
  `TimeStart` varchar(45) NOT NULL,
  `TimeEnd` varchar(45) NOT NULL,
  `MoneyEarning` varchar(45) NOT NULL,
  `Moneycost` varchar(45) NOT NULL,
  PRIMARY KEY (`ParkingID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `parking_info`
--

/*!40000 ALTER TABLE `parking_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `parking_info` ENABLE KEYS */;


--
-- Definition of table `user_info`
--

DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `UserID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Account` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL,
  `Name` varchar(45) CHARACTER SET gb2312 NOT NULL,
  `IdentityNum` varchar(45) NOT NULL,
  `Sex` int(2) unsigned NOT NULL,
  `PhoneNum` varchar(15) NOT NULL,
  `UserType` varchar(5) NOT NULL,
  `AccountBanlance` int(10) unsigned NOT NULL,
  PRIMARY KEY (`UserID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=FIXED;

--
-- Dumping data for table `user_info`
--

/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
INSERT INTO `user_info` (`UserID`,`Account`,`Password`,`Name`,`IdentityNum`,`Sex`,`PhoneNum`,`UserType`,`AccountBanlance`) VALUES 
 (1,'zxb','123','邹晓波','371081198608249672',1,'18661791091','0_1',100),
 (2,'zxv','123','','',1,'','0_0',0);
/*!40000 ALTER TABLE `user_info` ENABLE KEYS */;


--
-- Definition of table `voting`
--

DROP TABLE IF EXISTS `voting`;
CREATE TABLE `voting` (
  `votingID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `BiddingID` varchar(45) DEFAULT NULL,
  `CarNum` varchar(10) NOT NULL,
  `State` varchar(45) NOT NULL,
  PRIMARY KEY (`votingID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `voting`
--

/*!40000 ALTER TABLE `voting` DISABLE KEYS */;
/*!40000 ALTER TABLE `voting` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
