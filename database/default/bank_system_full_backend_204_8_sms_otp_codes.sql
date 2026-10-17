-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: bank_system_full_backend_204_8
-- ------------------------------------------------------
-- Server version	8.0.42

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `sms_otp_codes`
--

DROP TABLE IF EXISTS `sms_otp_codes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sms_otp_codes` (
  `code_id` binary(16) NOT NULL,
  `user_id` binary(16) NOT NULL,
  `code` varchar(255) NOT NULL,
  `device_ip` varchar(255) NOT NULL,
  `revoked` tinyint(1) NOT NULL,
  `expires_at` timestamp NOT NULL,
  `created_at` timestamp NOT NULL,
  `sms_id` binary(16) NOT NULL,
  `two_factor_purpose` varchar(45) NOT NULL,
  PRIMARY KEY (`code_id`),
  UNIQUE KEY `un1` (`user_id`,`code`),
  KEY `fk_keyt_t1_idx` (`user_id`),
  KEY `fk_otp_id_contact_idx` (`sms_id`),
  CONSTRAINT `fk_otp_id_sms` FOREIGN KEY (`sms_id`) REFERENCES `sms_objects` (`sms_id`),
  CONSTRAINT `fk_otp_id_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sms_otp_codes`
--

LOCK TABLES `sms_otp_codes` WRITE;
/*!40000 ALTER TABLE `sms_otp_codes` DISABLE KEYS */;
INSERT INTO `sms_otp_codes` VALUES (_binary 'Hö˙\—œéJ;ü\Ó^E%ï',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','{bcrypt}$2a$10$lrwtbnM3iGmUn5go93xXKORuHt/ShIVRHlAgD7P/HZv4.Ey37SezG','0:0:0:0:0:0:0:1',1,'2026-05-28 21:05:37','2026-05-28 17:45:37',_binary '[ÅJÉI\ÿO“í\'I®üqΩ','PHONE_NUMBER_VERIFICATION');
/*!40000 ALTER TABLE `sms_otp_codes` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-12 14:14:59
