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
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `accounts` (
  `account_id` binary(16) NOT NULL DEFAULT (uuid_to_bin(uuid())),
  `customer_id` binary(16) NOT NULL,
  `account_number` varchar(12) NOT NULL,
  `account_type` varchar(20) NOT NULL,
  `currency` char(3) NOT NULL,
  `status` varchar(20) NOT NULL,
  `opened_at` timestamp NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `version` bigint NOT NULL,
  PRIMARY KEY (`account_id`),
  UNIQUE KEY `accountscol_UNIQUE` (`account_number`),
  KEY `idx_accounts_customer` (`customer_id`),
  CONSTRAINT `accounts_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accounts`
--

LOCK TABLES `accounts` WRITE;
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO `accounts` VALUES (_binary 'ó\ZAd\ìHÝ•g\êFm\Â%]',_binary '¾]=¤F\ëE	š\"\'„a3\är','462372975840','BANK','USD','ACTIVE','2026-02-28 06:17:51','2026-02-28 06:17:51',0),(_binary '\"ý˜YAa£qø¨zz\ß',_binary '¾]=¤F\ëE	š\"\'„a3\är','192592295297','SAVINGS','CAD','ACTIVE','2026-02-24 07:54:06','2026-02-24 07:54:06',0),(_binary 'A0(ö\âRM@ˆ\ä”\ã\n',_binary '¾]=¤F\ëE	š\"\'„a3\är','237996208427','BANK','USD','ACTIVE','2026-04-28 12:25:48','2026-04-28 12:25:48',0),(_binary 'Vvê­yG|¥ú£Ö³2ð',_binary '¾]=¤F\ëE	š\"\'„a3\är','273420118001','CREDIT','USD','ACTIVE','2026-02-28 06:15:03','2026-02-28 06:15:03',0),(_binary 'v\é‘\çgA\â\ÆI\íMñ2\à',_binary '¾]=¤F\ëE	š\"\'„a3\är','574714619895','SAVINGS','THB','ACTIVE','2026-02-28 06:18:06','2026-02-28 06:18:06',0),(_binary 'm±\Ä@ü„` /\'d',_binary '¾]=¤F\ëE	š\"\'„a3\är','683480007033','BANK','USD','ACTIVE','2026-03-04 08:00:02','2026-03-04 08:00:02',0),(_binary '¡>¹\'¦«Loµ§IýGr\Ö',_binary '¾]=¤F\ëE	š\"\'„a3\är','385712420145','BANK','USD','ACTIVE','2026-03-04 07:50:41','2026-03-04 07:50:41',0);
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;
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
