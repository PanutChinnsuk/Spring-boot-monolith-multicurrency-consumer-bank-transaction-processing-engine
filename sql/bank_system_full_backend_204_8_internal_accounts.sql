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
-- Table structure for table `internal_accounts`
--

DROP TABLE IF EXISTS `internal_accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `internal_accounts` (
  `internal_account_id` binary(16) NOT NULL,
  `account_id` binary(16) NOT NULL,
  `branch_id` binary(16) NOT NULL,
  `internal_code` varchar(255) NOT NULL,
  `account_purpose` varchar(45) NOT NULL,
  `label` varchar(45) NOT NULL,
  `version` bigint NOT NULL,
  PRIMARY KEY (`internal_account_id`),
  UNIQUE KEY `label_UNIQUE` (`label`),
  UNIQUE KEY `uk_branch_purpose` (`branch_id`,`account_purpose`),
  UNIQUE KEY `uk_branch_account` (`branch_id`,`account_id`),
  UNIQUE KEY `uk_branch_account_purpose` (`branch_id`,`account_id`,`account_purpose`),
  UNIQUE KEY `internal_account_id_UNIQUE` (`internal_account_id`),
  UNIQUE KEY `internal_code_UNIQUE` (`internal_code`),
  KEY `branch_id_fk_internal_idx` (`branch_id`),
  KEY `fk_acc_id_internal` (`account_id`),
  CONSTRAINT `branch_id_fk_internal` FOREIGN KEY (`branch_id`) REFERENCES `branches` (`branch_id`),
  CONSTRAINT `fk_acc_id_internal` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `internal_accounts`
--

LOCK TABLES `internal_accounts` WRITE;
/*!40000 ALTER TABLE `internal_accounts` DISABLE KEYS */;
INSERT INTO `internal_accounts` VALUES (_binary 'yz8	ÛÜG´ïπ¸•º\—\Ê',_binary 'A0(ˆ\‚RM@à\‰î\„\n',_binary 'π˚X\÷s±Kìâ–¢\‰é','TCB_OVERDRAFT_OVERPAID_CLEARING','OVERDRAFT_OVERPAID_CLEARING','TCB_MAIN_OVERDRAFT_OVERPAID_CLEARING',0),(_binary '∑\«p©cMŒÆl¥µê®~N',_binary 'Û\ZAd\ÏH›ïg\ÍFm\¬%]',_binary 'π˚X\÷s±Kìâ–¢\‰é','TCB_LIDQUIDITY','BANK_LIQUIDITY','TCB_MAIN_LIDQUIDITY',0),(_binary '\ƒ{Z\’\ÕVGıΩg.&ëmS\ﬂ',_binary '°>π\'¶´LoµßI˝Gr\÷',_binary 'π˚X\÷s±Kìâ–¢\‰é','TCB_FX_REVENUE','FX_REVENUE','TCB_MAIN_FX_REVENUE',0),(_binary '\ËtπÉ§A¢Éúpó\Ã\‹x',_binary 'Åm±\ƒ@¸Ñ`†/\'d',_binary 'π˚X\÷s±Kìâ–¢\‰é','TCB_FX_CLEARING','FX_CLEARING','TCB_MAIN_FX_CLEARING',0);
/*!40000 ALTER TABLE `internal_accounts` ENABLE KEYS */;
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
