-- MySQL dump 10.13  Distrib 5.6.10, for Win64 (x86_64)
--
-- Host: localhost    Database: falcon
-- ------------------------------------------------------
-- Server version	5.6.10

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
-- Dumping data for table `falcon_appointment`
--

LOCK TABLES `falcon_appointment` WRITE;
/*!40000 ALTER TABLE `falcon_appointment` DISABLE KEYS */;
/*!40000 ALTER TABLE `falcon_appointment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `falcon_appointment_patron`
--

LOCK TABLES `falcon_appointment_patron` WRITE;
/*!40000 ALTER TABLE `falcon_appointment_patron` DISABLE KEYS */;
/*!40000 ALTER TABLE `falcon_appointment_patron` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `falcon_feedback`
--

LOCK TABLES `falcon_feedback` WRITE;
/*!40000 ALTER TABLE `falcon_feedback` DISABLE KEYS */;
/*!40000 ALTER TABLE `falcon_feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `falcon_location`
--

LOCK TABLES `falcon_location` WRITE;
/*!40000 ALTER TABLE `falcon_location` DISABLE KEYS */;
INSERT INTO `falcon_location` (`id`, `name`, `admin`, `valid`) VALUES (1,'Room 1','sarawakshank@gmail.com',1);
INSERT INTO `falcon_location` (`id`, `name`, `admin`, `valid`) VALUES (2,'Room 2','sarawakshank@gmail.com',1);
INSERT INTO `falcon_location` (`id`, `name`, `admin`, `valid`) VALUES (3,'Room 3','sarawakshank@gmail.com',1);
/*!40000 ALTER TABLE `falcon_location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `falcon_message_log`
--

LOCK TABLES `falcon_message_log` WRITE;
/*!40000 ALTER TABLE `falcon_message_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `falcon_message_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `falcon_patron`
--

LOCK TABLES `falcon_patron` WRITE;
/*!40000 ALTER TABLE `falcon_patron` DISABLE KEYS */;
INSERT INTO `falcon_patron` (`id`, `patron`, `admin`) VALUES (1,'blacksnow666@gmail.com','sarawakshank@gmail.com');
INSERT INTO `falcon_patron` (`id`, `patron`, `admin`) VALUES (2,'helmy@tabuk-tech.com','sarawakshank@gmail.com');
/*!40000 ALTER TABLE `falcon_patron` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `falcon_role`
--

LOCK TABLES `falcon_role` WRITE;
/*!40000 ALTER TABLE `falcon_role` DISABLE KEYS */;
INSERT INTO `falcon_role` (`role_name`) VALUES ('ROLE_ADMIN');
INSERT INTO `falcon_role` (`role_name`) VALUES ('ROLE_USER');
/*!40000 ALTER TABLE `falcon_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `falcon_service`
--

LOCK TABLES `falcon_service` WRITE;
/*!40000 ALTER TABLE `falcon_service` DISABLE KEYS */;
INSERT INTO `falcon_service` (`id`, `name`, `admin`, `valid`) VALUES (1,'Normal Appointment','sarawakshank@gmail.com',1);
INSERT INTO `falcon_service` (`id`, `name`, `admin`, `valid`) VALUES (2,'Minor Surgery','sarawakshank@gmail.com',1);
/*!40000 ALTER TABLE `falcon_service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `falcon_staff`
--

LOCK TABLES `falcon_staff` WRITE;
/*!40000 ALTER TABLE `falcon_staff` DISABLE KEYS */;
INSERT INTO `falcon_staff` (`id`, `name`, `nric`, `admin`, `hp_tel`, `email`, `send_sms`, `send_email`, `valid`) VALUES (1,'Aeris','100100','sarawakshank@gmail.com','100100','aeris@ff7.com',0,0,1);
/*!40000 ALTER TABLE `falcon_staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `falcon_user`
--

LOCK TABLES `falcon_user` WRITE;
/*!40000 ALTER TABLE `falcon_user` DISABLE KEYS */;
INSERT INTO `falcon_user` (`username`, `password`, `name`, `email`, `phone`, `nric`, `send_sms`, `send_email`, `valid`, `sms_remaining`, `sms_sent_lifetime`) VALUES ('blacksnow666@gmail.com','2cfbd4aacfa9375ab4d81849fdfd1abc26a8119b85a51f8b4d7bb85b788a57b0','Titi Wangsa bin Damhore','blacksnow666@gmail.com','0193012624','800121146053',1,1,1,NULL,NULL);
INSERT INTO `falcon_user` (`username`, `password`, `name`, `email`, `phone`, `nric`, `send_sms`, `send_email`, `valid`, `sms_remaining`, `sms_sent_lifetime`) VALUES ('helmy@tabuk-tech.com','a6789888b1c8e62d48915c0af43523c9202a73480c2992e492c7c32a10895f00','Helmy Iqbal Ambotang','helmy@tabuk-tech.com','0123900958','79',0,1,1,NULL,NULL);
INSERT INTO `falcon_user` (`username`, `password`, `name`, `email`, `phone`, `nric`, `send_sms`, `send_email`, `valid`, `sms_remaining`, `sms_sent_lifetime`) VALUES ('sarawakshank@gmail.com','bf0d746d6cc8cd17d6683ffd4626c64988706a3312266e8fc3b37c4bf6b72304','shankar','sarawakshank@gmail.com','0222222222','999999999999',1,1,1,0,0);
/*!40000 ALTER TABLE `falcon_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `falcon_user_role`
--

LOCK TABLES `falcon_user_role` WRITE;
/*!40000 ALTER TABLE `falcon_user_role` DISABLE KEYS */;
INSERT INTO `falcon_user_role` (`id`, `username`, `rolename`) VALUES (4,'blacksnow666@gmail.com','ROLE_USER');
INSERT INTO `falcon_user_role` (`id`, `username`, `rolename`) VALUES (5,'helmy@tabuk-tech.com','ROLE_USER');
INSERT INTO `falcon_user_role` (`id`, `username`, `rolename`) VALUES (3,'sarawakshank@gmail.com','ROLE_ADMIN');
/*!40000 ALTER TABLE `falcon_user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

