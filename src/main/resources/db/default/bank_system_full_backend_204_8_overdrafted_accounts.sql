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
-- Table structure for table `overdrafted_accounts`
--

DROP TABLE IF EXISTS `overdrafted_accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `overdrafted_accounts` (
  `overdrafted_id` binary(16) NOT NULL,
  `transaction_id` binary(16) DEFAULT NULL,
  `account_id` binary(16) NOT NULL,
  `amount` decimal(19,4) NOT NULL,
  `converted_amount` decimal(19,4) NOT NULL,
  `tracked_amount` decimal(19,4) NOT NULL,
  `tracked_converted_amount` decimal(19,4) NOT NULL,
  `currency` varchar(45) NOT NULL,
  `fx_id` binary(16) DEFAULT NULL,
  `on_fx_direction` varchar(45) NOT NULL,
  `overdrafted_at` timestamp NOT NULL,
  `paid` tinyint(1) NOT NULL,
  `status` varchar(45) NOT NULL,
  PRIMARY KEY (`overdrafted_id`),
  KEY `fk_acc_id_t1_ovd` (`account_id`),
  KEY `fk_fxrate_id_t1_ovd` (`fx_id`),
  KEY `fk_transaction_id_t1_ovd` (`transaction_id`),
  CONSTRAINT `fk_acc_id_t1_ovd` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`account_id`),
  CONSTRAINT `fk_fxrate_id_t1_ovd` FOREIGN KEY (`fx_id`) REFERENCES `fx_rates` (`fx_id`),
  CONSTRAINT `fk_transaction_id_t1_ovd` FOREIGN KEY (`transaction_id`) REFERENCES `transactions` (`transaction_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `overdrafted_accounts`
--

LOCK TABLES `overdrafted_accounts` WRITE;
/*!40000 ALTER TABLE `overdrafted_accounts` DISABLE KEYS */;
INSERT INTO `overdrafted_accounts` VALUES (_binary '\√tE7\ÃNM;∏Oo#\Ìb',_binary '\Í\‡\’\ÕL\Â∂\‹\ÁkJa',_binary 'Åm±\ƒ@¸Ñ`†/\'d',99.0000,99.0000,99.0000,99.0000,'USD',NULL,'NONE','2026-05-23 14:50:27',0,'ACTIVE'),(_binary 'ŸüIK\'ïOyáI\⁄\‰S∞{',_binary 'kº¸O,ItàÇ	Ÿå\ﬁ2∂',_binary 'v\Èë\ÁgA\‚ç\∆I\ÌMÒ2\‡',80.0000,2000.0000,80.0000,200.0000,'THB',_binary 'áºM’®èL£≤Wg≥\€Oo','BID','2026-05-24 01:29:27',0,'ACTIVE');
/*!40000 ALTER TABLE `overdrafted_accounts` ENABLE KEYS */;
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
