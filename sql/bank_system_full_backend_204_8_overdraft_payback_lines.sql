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
-- Table structure for table `overdraft_payback_lines`
--

DROP TABLE IF EXISTS `overdraft_payback_lines`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `overdraft_payback_lines` (
  `line_id` binary(16) NOT NULL,
  `account_id` binary(16) NOT NULL,
  `ledger_account_id` binary(16) NOT NULL,
  `monetary_amount` decimal(19,4) NOT NULL,
  `converted_amount` decimal(19,4) NOT NULL,
  `final_amount` decimal(19,4) NOT NULL,
  `payback_direction` varchar(45) NOT NULL,
  `payback_role` varchar(45) NOT NULL,
  `request_id` binary(16) NOT NULL,
  PRIMARY KEY (`line_id`),
  KEY `r_id_fk5_idx` (`request_id`),
  KEY `acc_id_fk6` (`account_id`),
  KEY `fk_ledger_a_id` (`ledger_account_id`),
  CONSTRAINT `acc_id_fk6` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`account_id`),
  CONSTRAINT `fk_ledger_a_id` FOREIGN KEY (`ledger_account_id`) REFERENCES `ledger_accounts` (`ledger_account_id`),
  CONSTRAINT `r_id_fk5` FOREIGN KEY (`request_id`) REFERENCES `overdraft_payback_requests` (`request_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `overdraft_payback_lines`
--

LOCK TABLES `overdraft_payback_lines` WRITE;
/*!40000 ALTER TABLE `overdraft_payback_lines` DISABLE KEYS */;
INSERT INTO `overdraft_payback_lines` VALUES (_binary 'áˆs\rKûª\€\·ºS»ÉX',_binary 'v\Èë\ÁgA\‚ç\∆I\ÌMÒ2\‡',_binary '<#Ç¿\nºJ!†Æ+\‡\“ ≤á',2000.0000,80.0000,2000.0000,'PAYBACK','PAYBACK_PRINCIPAL',_binary 'k˜%\ hHÅú~R7ä\«\È'),(_binary '>œÉ´4BvªPDÅÑÆ\Á|',_binary 'Û\ZAd\ÏH›ïg\ÍFm\¬%]',_binary '<#Ç¿\nºJ!†Æ+\‡\“ ≤á',2000.0000,80.0000,80.0000,'GAIN','PAYBACK_COLLECTOR',_binary 'k˜%\ hHÅú~R7ä\«\È');
/*!40000 ALTER TABLE `overdraft_payback_lines` ENABLE KEYS */;
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
