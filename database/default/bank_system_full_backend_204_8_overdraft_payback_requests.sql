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
-- Table structure for table `overdraft_payback_requests`
--

DROP TABLE IF EXISTS `overdraft_payback_requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `overdraft_payback_requests` (
  `request_id` binary(16) NOT NULL,
  `overdrafted_id` binary(16) NOT NULL,
  `request_status` varchar(45) NOT NULL,
  `requested_at` timestamp NOT NULL,
  `expires_at` timestamp NOT NULL,
  PRIMARY KEY (`request_id`),
  KEY `fk_ovd_id1_idx` (`overdrafted_id`),
  CONSTRAINT `fk_ovd_id1` FOREIGN KEY (`overdrafted_id`) REFERENCES `overdrafted_accounts` (`overdrafted_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `overdraft_payback_requests`
--

LOCK TABLES `overdraft_payback_requests` WRITE;
/*!40000 ALTER TABLE `overdraft_payback_requests` DISABLE KEYS */;
INSERT INTO `overdraft_payback_requests` VALUES (_binary '!=Fá1óJ®πF≈§{PO\ÿ',_binary '\√tE7\ÃNM;∏Oo#\Ìb','PENDING','2026-05-24 00:56:55','2026-06-07 00:56:55'),(_binary '\"∫ÚÇA\ZÑq\ÈÜ[åp',_binary '\√tE7\ÃNM;∏Oo#\Ìb','PENDING','2026-05-24 01:31:30','2026-06-07 01:31:30'),(_binary 'k˜%\ hHÅú~R7ä\«\È',_binary 'ŸüIK\'ïOyáI\⁄\‰S∞{','PENDING','2026-05-24 01:33:09','2026-06-07 01:33:09'),(_binary '\Á™\÷}\”#I®èı2¶q\È',_binary '\√tE7\ÃNM;∏Oo#\Ìb','PENDING','2026-05-24 01:20:47','2026-06-07 01:20:47');
/*!40000 ALTER TABLE `overdraft_payback_requests` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-12 14:14:58
