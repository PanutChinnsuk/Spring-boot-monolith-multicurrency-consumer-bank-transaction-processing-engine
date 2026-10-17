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
-- Table structure for table `activation_codes`
--

DROP TABLE IF EXISTS `activation_codes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `activation_codes` (
  `activation_code_id` binary(16) NOT NULL,
  `user_id` binary(16) NOT NULL,
  `code_hash` varchar(255) NOT NULL,
  `revoked` tinyint(1) NOT NULL,
  `expires_at` timestamp NOT NULL,
  `version` bigint NOT NULL,
  PRIMARY KEY (`activation_code_id`),
  KEY `user_id_fk_act` (`user_id`),
  CONSTRAINT `user_id_fk_act` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activation_codes`
--

LOCK TABLES `activation_codes` WRITE;
/*!40000 ALTER TABLE `activation_codes` DISABLE KEYS */;
INSERT INTO `activation_codes` VALUES (_binary '+ùs¯\Â@¸´—ÄA\“#\Ó',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','{bcrypt}$2a$10$Sihty7buopkZZe8ifXFrFuzKeWPGmVyVErBAlTJFR4oqXxpRVYfRO',1,'2026-02-20 10:13:24',0),(_binary 'PbªYQuBì†Y\r-πa\Ëf',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','{bcrypt}$2a$10$dKYXq.jHOS90.hQt.DJLZuUxsi448FezgLzFUFGstCtanIrXwTw4q',1,'2026-02-20 10:13:32',0),(_binary 'cO\ÃΩE˘õMÄƒêπªq',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','{bcrypt}$2a$10$HX3q1eYbm1VXElKd09Kt0uiLxi/WhgV0PLBcwwvAGcT0zLlGLxdku',1,'2026-02-23 07:58:43',0);
/*!40000 ALTER TABLE `activation_codes` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-12 14:15:00
