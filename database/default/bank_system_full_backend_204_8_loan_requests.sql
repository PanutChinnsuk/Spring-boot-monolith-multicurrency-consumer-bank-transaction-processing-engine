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
-- Table structure for table `loan_requests`
--

DROP TABLE IF EXISTS `loan_requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loan_requests` (
  `loan_request_id` binary(16) NOT NULL,
  `customer_id` binary(16) NOT NULL,
  `account_id` binary(16) NOT NULL,
  `requested_principal` decimal(19,4) NOT NULL,
  `requested_interest_rate` decimal(5,2) NOT NULL,
  `requested_term_months` int NOT NULL,
  `status` varchar(20) NOT NULL,
  `approved_loan_id` binary(16) DEFAULT NULL,
  `reviewed_by` binary(16) DEFAULT NULL,
  `reviewed_at` timestamp NULL DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `version` bigint NOT NULL,
  PRIMARY KEY (`loan_request_id`),
  KEY `fk_loan_request_customer` (`customer_id`),
  KEY `fk_loan_request_approved_loan` (`approved_loan_id`),
  KEY `fk_loan_request_account_id` (`account_id`),
  CONSTRAINT `fk_loan_request_account_id` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`account_id`),
  CONSTRAINT `fk_loan_request_approved_loan` FOREIGN KEY (`approved_loan_id`) REFERENCES `loans` (`loan_id`),
  CONSTRAINT `fk_loan_request_customer` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loan_requests`
--

LOCK TABLES `loan_requests` WRITE;
/*!40000 ALTER TABLE `loan_requests` DISABLE KEYS */;
INSERT INTO `loan_requests` VALUES (_binary ' #wßmBqÄ[{µõ…º\‹',_binary 'æ]=§F\ÎE	ö\"\'Ña3\‰r',_binary 'v\Èë\ÁgA\‚ç\∆I\ÌMÒ2\‡',100.0000,0.10,10,'APPROVED',_binary '\⁄#k¢S∏Gs¶+≥ºÖ,[.',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','2026-05-20 14:39:51','2026-05-20 14:39:01',1);
/*!40000 ALTER TABLE `loan_requests` ENABLE KEYS */;
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
