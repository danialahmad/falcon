-- MySQL dump 10.13  Distrib 5.6.12, for Win64 (x86_64)
--
-- Host: localhost    Database: falcon
-- ------------------------------------------------------
-- Server version	5.6.12-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `falcon_appointment`
--

DROP TABLE IF EXISTS `falcon_appointment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `falcon_appointment` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `service` int(11) unsigned DEFAULT NULL,
  `appointment_date` datetime DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `create_by` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `update_by` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `location` int(11) unsigned DEFAULT NULL,
  `staff` int(11) unsigned DEFAULT NULL,
  `appointment_date_end` datetime DEFAULT NULL,
  `notified` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  KEY `FK_falcon_appointment_falcon_location` (`location`),
  KEY `staff` (`staff`),
  KEY `service` (`service`),
  KEY `notified` (`notified`),
  CONSTRAINT `falcon_appointment_ibfk_1` FOREIGN KEY (`staff`) REFERENCES `falcon_staff` (`id`),
  CONSTRAINT `falcon_appointment_ibfk_2` FOREIGN KEY (`service`) REFERENCES `falcon_service` (`id`),
  CONSTRAINT `FK_falcon_appointment_falcon_location` FOREIGN KEY (`location`) REFERENCES `falcon_location` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `falcon_appointment_patron`
--

DROP TABLE IF EXISTS `falcon_appointment_patron`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `falcon_appointment_patron` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `appointment` int(11) unsigned NOT NULL,
  `patron` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_falcon_patron_falcon_appointment` (`appointment`),
  KEY `patron` (`patron`),
  CONSTRAINT `falcon_appointment_patron_ibfk_1` FOREIGN KEY (`patron`) REFERENCES `falcon_patron` (`id`),
  CONSTRAINT `FK_falcon_patron_falcon_appointment` FOREIGN KEY (`appointment`) REFERENCES `falcon_appointment` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `falcon_feedback`
--

DROP TABLE IF EXISTS `falcon_feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `falcon_feedback` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `feedback_type` varchar(15) DEFAULT NULL,
  `content` varchar(1000) DEFAULT NULL,
  `email_from` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `falcon_location`
--

DROP TABLE IF EXISTS `falcon_location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `falcon_location` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 DEFAULT '',
  `admin` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '',
  `valid` tinyint(1) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `admin` (`admin`),
  CONSTRAINT `falcon_location_ibfk_1` FOREIGN KEY (`admin`) REFERENCES `falcon_user` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `falcon_message_log`
--

DROP TABLE IF EXISTS `falcon_message_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `falcon_message_log` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `sender` varchar(100) COLLATE utf8_bin NOT NULL,
  `destination` varchar(100) COLLATE utf8_bin NOT NULL,
  `message_type` varchar(10) COLLATE utf8_bin NOT NULL,
  `message` varchar(1024) COLLATE utf8_bin NOT NULL,
  `sent_time` datetime NOT NULL,
  `error_message` varchar(1024) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `falcon_password_change`
--

DROP TABLE IF EXISTS `falcon_password_change`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `falcon_password_change` (
  `nric` varchar(50) DEFAULT NULL,
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `random_string` varchar(50) DEFAULT NULL,
  `executed` tinyint(1) unsigned DEFAULT NULL,
  `date_request` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `falcon_patron`
--

DROP TABLE IF EXISTS `falcon_patron`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `falcon_patron` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `patron` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '',
  `admin` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `patron` (`patron`),
  KEY `admin` (`admin`),
  CONSTRAINT `falcon_patron_ibfk_2` FOREIGN KEY (`patron`) REFERENCES `falcon_user` (`username`),
  CONSTRAINT `falcon_patron_ibfk_3` FOREIGN KEY (`admin`) REFERENCES `falcon_user` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `falcon_role`
--

DROP TABLE IF EXISTS `falcon_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `falcon_role` (
  `role_name` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `falcon_service`
--

DROP TABLE IF EXISTS `falcon_service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `falcon_service` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '',
  `admin` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '',
  `valid` tinyint(1) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `admin` (`admin`),
  CONSTRAINT `falcon_service_ibfk_1` FOREIGN KEY (`admin`) REFERENCES `falcon_user` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `falcon_staff`
--

DROP TABLE IF EXISTS `falcon_staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `falcon_staff` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '',
  `nric` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '',
  `admin` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '',
  `hp_tel` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `send_sms` tinyint(1) unsigned DEFAULT NULL,
  `send_email` tinyint(1) unsigned DEFAULT NULL,
  `valid` tinyint(1) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `admin` (`admin`),
  CONSTRAINT `falcon_staff_ibfk_1` FOREIGN KEY (`admin`) REFERENCES `falcon_user` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `falcon_user`
--

DROP TABLE IF EXISTS `falcon_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `falcon_user` (
  `username` varchar(100) COLLATE utf8_bin NOT NULL DEFAULT '',
  `password` char(64) COLLATE utf8_bin NOT NULL,
  `name` varchar(200) COLLATE utf8_bin NOT NULL,
  `email` varchar(100) COLLATE utf8_bin NOT NULL DEFAULT '',
  `phone` varchar(25) COLLATE utf8_bin NOT NULL DEFAULT '',
  `nric` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT '',
  `send_sms` tinyint(1) unsigned DEFAULT NULL,
  `send_email` tinyint(1) unsigned DEFAULT NULL,
  `valid` tinyint(1) unsigned DEFAULT NULL,
  `sms_remaining` int(10) DEFAULT '0',
  `sms_sent_lifetime` int(10) DEFAULT '0',
  PRIMARY KEY (`username`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `phone` (`phone`),
  UNIQUE KEY `nric` (`nric`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `falcon_user_role`
--

DROP TABLE IF EXISTS `falcon_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `falcon_user_role` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) COLLATE utf8_bin NOT NULL,
  `rolename` varchar(50) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_rolename` (`username`,`rolename`),
  KEY `FK_falcon_user_role_falcon_user` (`username`),
  KEY `rolename` (`rolename`),
  CONSTRAINT `FK_falcon_user_role_falcon_role` FOREIGN KEY (`rolename`) REFERENCES `falcon_role` (`role_name`),
  CONSTRAINT `FK_falcon_user_role_falcon_user` FOREIGN KEY (`username`) REFERENCES `falcon_user` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-07-23  0:27:48
