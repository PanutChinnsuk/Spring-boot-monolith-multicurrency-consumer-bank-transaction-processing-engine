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
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transactions` (
  `transaction_id` binary(16) NOT NULL DEFAULT (uuid_to_bin(uuid())),
  `type` varchar(50) NOT NULL,
  `status` varchar(20) NOT NULL,
  `transaction_principal` varchar(45) NOT NULL,
  `fx_direction` varchar(20) NOT NULL,
  `initiated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `expires_at` timestamp NOT NULL,
  `posted_at` timestamp NULL DEFAULT NULL,
  `reversed_of` binary(16) DEFAULT NULL,
  `version` bigint NOT NULL,
  PRIMARY KEY (`transaction_id`),
  KEY `idx_transactions_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactions`
--

LOCK TABLES `transactions` WRITE;
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
INSERT INTO `transactions` VALUES (_binary 'KSæ\œ\ÀN	©\Êp£Eµ','TRANSFER','REVERSED','ACCOUNT_FOR_TRANSACTION','NONE','2026-05-23 14:16:26','2026-06-06 14:16:26',NULL,NULL,3),(_binary 'kº¸O,ItàÇ	Ÿå\ﬁ2∂','TRANSFER','POSTED','ACCOUNT_FOR_TRANSACTION','BID','2026-05-24 01:25:15','2026-06-07 01:25:15','2026-05-24 01:29:27',_binary '\Ó\ry´\ÁGÆû\„%W¶ú\◊H',2),(_binary 'œÆ8|7NLbØ˙òD\¬\Ê\‚y','TRANSFER','PENDING','ACCOUNT_FOR_TRANSACTION','NONE','2026-05-25 11:54:31','2026-06-08 11:54:31',NULL,NULL,0),(_binary 'ﬁΩD\ÍÙEçÑâ_˜\À…ü','TRANSFER','AUTHORIZED','ACCOUNT_FOR_TRANSACTION','NONE','2026-05-23 14:52:28','2026-06-06 14:52:28',NULL,_binary '\Í\‡\’\ÕL\Â∂\‹\ÁkJa',1),(_binary '\Í\‡\’\ÕL\Â∂\‹\ÁkJa','TRANSFER','REVERSED','ACCOUNT_FOR_TRANSACTION','NONE','2026-05-23 14:18:35','2026-06-06 14:18:35','2026-05-23 14:50:27',_binary 'KSæ\œ\ÀN	©\Êp£Eµ',4),(_binary '\Ó\ry´\ÁGÆû\„%W¶ú\◊H','TRANSFER','REVERSED','ACCOUNT_FOR_TRANSACTION','BID','2026-05-24 01:23:14','2026-06-07 01:23:14','2026-05-24 01:24:25',NULL,3);
/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;
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
