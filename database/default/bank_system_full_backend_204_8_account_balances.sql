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
-- Table structure for table `account_balances`
--

DROP TABLE IF EXISTS `account_balances`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account_balances` (
  `account_id` binary(16) NOT NULL,
  `ledger_balance` decimal(19,4) NOT NULL,
  `available_balance` decimal(19,4) NOT NULL,
  `reserve_balance` decimal(19,4) NOT NULL,
  `overdraft_balance` decimal(19,4) NOT NULL,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `version` bigint NOT NULL,
  PRIMARY KEY (`account_id`),
  UNIQUE KEY `account_id_UNIQUE` (`account_id`),
  CONSTRAINT `account_balances_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_balances`
--

LOCK TABLES `account_balances` WRITE;
/*!40000 ALTER TABLE `account_balances` DISABLE KEYS */;
INSERT INTO `account_balances` VALUES (_binary 'ó\ZAd\ìHÝ•g\êFm\Â%]',12500.0000,12500.0000,0.0000,0.0000,'2026-05-24 20:10:10',62),(_binary '\"ý˜YAa£qø¨zz\ß',20000.0000,20000.0000,0.0000,0.0000,'2026-05-19 12:53:58',2),(_binary 'A0(ö\âRM@ˆ\ä”\ã\n',0.0000,0.0000,0.0000,0.0000,'2026-04-28 12:25:48',0),(_binary 'Vvê­yG|¥ú£Ö³2ð',0.0000,0.0000,0.0000,0.0000,'2026-04-23 12:07:12',1),(_binary 'v\é‘\çgA\â\ÆI\íMñ2\à',0.0000,200.0000,0.0000,200.0000,'2026-06-06 13:33:42',31),(_binary 'm±\Ä@ü„` /\'d',-99.0000,0.0000,0.0000,99.0000,'2026-05-23 14:50:27',12),(_binary '¡>¹\'¦«Loµ§IýGr\Ö',15.0000,15.0000,0.0000,0.0000,'2026-04-29 11:24:02',3);
/*!40000 ALTER TABLE `account_balances` ENABLE KEYS */;
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
