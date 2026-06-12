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
-- Table structure for table `user_login_attempts`
--

DROP TABLE IF EXISTS `user_login_attempts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_login_attempts` (
  `attempt_id` binary(16) NOT NULL,
  `user_id` binary(16) NOT NULL,
  `attempt_result` varchar(25) NOT NULL,
  `device_ip` varchar(100) NOT NULL,
  `expires_at` timestamp NOT NULL,
  PRIMARY KEY (`attempt_id`),
  KEY `user_id_fk_idx` (`user_id`),
  CONSTRAINT `user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_login_attempts`
--

LOCK TABLES `user_login_attempts` WRITE;
/*!40000 ALTER TABLE `user_login_attempts` DISABLE KEYS */;
INSERT INTO `user_login_attempts` VALUES (_binary 'lñäπ\"K±Ç\¬Ú¿Cç\‰\‹',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','FAILED','0:0:0:0:0:0:0:1','2026-04-10 12:45:39'),(_binary 'ê∫\—X\nO:Ñ\Á;N\Ã\Á',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','FAILED','0:0:0:0:0:0:0:1','2026-04-10 13:05:54'),(_binary '\ﬁmÔÄÖN¬ûf£Lp\⁄s\¬',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','FAILED','0:0:0:0:0:0:0:1','2026-04-10 13:05:56'),(_binary 'f;X\Ÿ€ùBbõï4=ª˜º',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','FAILED','0:0:0:0:0:0:0:1','2026-04-10 16:57:10'),(_binary 'i\È5óé\ÀCıâ#≠\À\›\ÂZ*',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','FAILED','0:0:0:0:0:0:0:1','2026-04-10 12:45:42'),(_binary '{\Õ[è\ŒNM∑£†2å\'\Ó\≈˚',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','FAILED','0:0:0:0:0:0:0:1','2026-04-10 13:05:57'),(_binary 'ä2≠™c@HV¥\Î\Ì\≈`\Õ_\”',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','FAILED','0:0:0:0:0:0:0:1','2026-03-26 15:43:55'),(_binary 'ì;\‰\‡\·¨DR≥ªxVVÙ\\',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','FAILED','0:0:0:0:0:0:0:1','2026-04-10 12:45:41'),(_binary '¢DëxçLñèW¬ù6\"\Ô\œ',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','FAILED','0:0:0:0:0:0:0:1','2026-04-10 16:57:05'),(_binary '∏\ÎˆD\…LMùãzg_\»\0Ké',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','FAILED','0:0:0:0:0:0:0:1','2026-04-10 16:57:06');
/*!40000 ALTER TABLE `user_login_attempts` ENABLE KEYS */;
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
