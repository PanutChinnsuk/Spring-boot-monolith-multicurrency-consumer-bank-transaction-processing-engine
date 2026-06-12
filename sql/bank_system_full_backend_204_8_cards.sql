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
-- Table structure for table `cards`
--

DROP TABLE IF EXISTS `cards`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cards` (
  `card_id` binary(16) NOT NULL DEFAULT (uuid_to_bin(uuid())),
  `account_id` binary(16) NOT NULL,
  `pan_encrypt` varchar(255) NOT NULL,
  `masked_pan` char(16) NOT NULL,
  `expiry` date NOT NULL,
  `delivered_at` timestamp NULL DEFAULT NULL,
  `delivered_expires_at` timestamp NULL DEFAULT NULL,
  `status` varchar(20) NOT NULL,
  `key_version` int NOT NULL,
  `version` bigint NOT NULL,
  `replaced_of` binary(16) DEFAULT NULL,
  PRIMARY KEY (`card_id`),
  UNIQUE KEY `pan_encrypt_UNIQUE` (`pan_encrypt`),
  KEY `account_id` (`account_id`),
  KEY `fk_replaced_of_card_id` (`replaced_of`),
  CONSTRAINT `cards_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`account_id`),
  CONSTRAINT `fk_replaced_of_card_id` FOREIGN KEY (`replaced_of`) REFERENCES `cards` (`card_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cards`
--

LOCK TABLES `cards` WRITE;
/*!40000 ALTER TABLE `cards` DISABLE KEYS */;
INSERT INTO `cards` VALUES (_binary 'Xo¶5îD;≤ùpàıùH',_binary 'VvÍ≠ùyG|•˙£÷≥2','ey5cBtKBWCzYNfT2I+HF0TT5km6irWhCrpCttmPwEJU=','************7387','2028-03-17','2026-03-15 12:34:01','2026-03-15 12:34:01','ACTIVE',1,0,NULL),(_binary 'h=≥}P\ËIœô\ÏP1ì\n\‡',_binary '\"˝òYAa£q¯®zz\ﬂ','+J9CIqBn9W+ixrPffy6MVDT5km6irWhCrpCttmPwEJU=','************3587','2028-03-17','2026-03-15 12:34:01','2026-03-15 12:34:01','ACTIVE',1,0,_binary '√¢HhøDÖü#êªôn'),(_binary 'Ç\ÔÆußOıá¯n,∆â¿',_binary '\"˝òYAa£q¯®zz\ﬂ','rvrSFQS8McWbmWxYhNMHYjT5km6irWhCrpCttmPwEJU=','************4106','2028-03-14','2026-03-15 12:34:01','2026-03-15 12:34:01','ACTIVE',1,19,NULL),(_binary '¥*∫F∂FFò¿Úú“†?<',_binary '°>π\'¶´LoµßI˝Gr\÷','CRk7bRTG6uf0EzW+ekupMDT5km6irWhCrpCttmPwEJU=','************6745','2028-03-17','2026-03-15 12:34:01','2026-03-15 12:34:01','ACTIVE',1,0,NULL),(_binary '∏dÿõâÙJzß˝/9)\Z',_binary 'Û\ZAd\ÏH›ïg\ÍFm\¬%]','gnUSbTuDPaaaatRM52SUOzT5km6irWhCrpCttmPwEJU=','************7067','2028-03-17',NULL,NULL,'ACTIVE',1,2,NULL),(_binary '√¢HhøDÖü#êªôn',_binary '\"˝òYAa£q¯®zz\ﬂ','oreg+yQIPP3JtQwBiJ62JTT5km6irWhCrpCttmPwEJU=','************9182','2028-03-17',NULL,NULL,'REPLACED',1,1,NULL);
/*!40000 ALTER TABLE `cards` ENABLE KEYS */;
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
