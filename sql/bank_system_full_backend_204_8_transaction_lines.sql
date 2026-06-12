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
-- Table structure for table `transaction_lines`
--

DROP TABLE IF EXISTS `transaction_lines`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction_lines` (
  `line_id` binary(16) NOT NULL DEFAULT (uuid_to_bin(uuid())),
  `transaction_id` binary(16) NOT NULL,
  `account_id` binary(16) NOT NULL,
  `ledger_account_id` binary(16) NOT NULL,
  `balance_impact` varchar(45) NOT NULL,
  `base_currency_used` char(3) NOT NULL,
  `amount` decimal(19,4) NOT NULL,
  `converted_amount` decimal(19,4) NOT NULL,
  `direction` varchar(10) NOT NULL,
  `fx_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`line_id`),
  KEY `transaction_lines_ibfk_2` (`account_id`),
  KEY `transaction_lines_ibfk_3` (`ledger_account_id`),
  KEY `transaction_lines_ibfk_1` (`transaction_id`),
  KEY `tl_fk4_idx` (`fx_id`),
  CONSTRAINT `tl_fk4` FOREIGN KEY (`fx_id`) REFERENCES `fx_rates` (`fx_id`),
  CONSTRAINT `transaction_lines_ibfk_1` FOREIGN KEY (`transaction_id`) REFERENCES `transactions` (`transaction_id`),
  CONSTRAINT `transaction_lines_ibfk_2` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`account_id`),
  CONSTRAINT `transaction_lines_ibfk_3` FOREIGN KEY (`ledger_account_id`) REFERENCES `ledger_accounts` (`ledger_account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction_lines`
--

LOCK TABLES `transaction_lines` WRITE;
/*!40000 ALTER TABLE `transaction_lines` DISABLE KEYS */;
INSERT INTO `transaction_lines` VALUES (_binary '\nQ:gOM\"≤Up‰à°8',_binary 'ﬁΩD\ÍÙEçÑâ_˜\À…ü',_binary 'Åm±\ƒ@¸Ñ`†/\'d',_binary '\Z∂t*ø\ÔN(ï}<~;—ºî','NO_CHECK','',100.0000,100.0000,'CREDIT',NULL),(_binary '-ôÿ¨ºVJaÄ$∞l\œœÆ',_binary 'kº¸O,ItàÇ	Ÿå\ﬁ2∂',_binary 'Û\ZAd\ÏH›ïg\ÍFm\¬%]',_binary '\"(¶\náDÈø•&Æ™A\ﬂ','NO_CHECK','',100.0000,100.0000,'CREDIT',NULL),(_binary ':O:f\ÏAAw∞üQ∏2\ﬁÒ',_binary 'ﬁΩD\ÍÙEçÑâ_˜\À…ü',_binary 'Û\ZAd\ÏH›ïg\ÍFm\¬%]',_binary '\Z∂t*ø\ÔN(ï}<~;—ºî','DIRECT_DEBIT','',100.0000,100.0000,'DEBIT',NULL),(_binary 'Wzßà=ÆIôi\√)	\…\…',_binary 'kº¸O,ItàÇ	Ÿå\ﬁ2∂',_binary 'v\Èë\ÁgA\‚ç\∆I\ÌMÒ2\‡',_binary '\"(¶\náDÈø•&Æ™A\ﬂ','DIRECT_DEBIT','',100.0000,2500.0000,'DEBIT',_binary 'áºM’®èL£≤Wg≥\€Oo'),(_binary 'a\ÿ\  E©H|¨«ì´\Ó	',_binary '\Ó\ry´\ÁGÆû\„%W¶ú\◊H',_binary 'v\Èë\ÁgA\‚ç\∆I\ÌMÒ2\‡',_binary '\"(¶\náDÈø•&Æ™A\ﬂ','NO_CHECK','',100.0000,2500.0000,'CREDIT',_binary 'áºM’®èL£≤Wg≥\€Oo'),(_binary 'bb™\·πCªä#\¬&?ûf3',_binary 'KSæ\œ\ÀN	©\Êp£Eµ',_binary 'Û\ZAd\ÏH›ïg\ÍFm\¬%]',_binary '\Z∂t*ø\ÔN(ï}<~;—ºî','RESERVE_DEBIT','',100.0000,100.0000,'DEBIT',NULL),(_binary '~÷º>l¶K|µ\"\Ê\"\¬',_binary '\Í\‡\’\ÕL\Â∂\‹\ÁkJa',_binary 'Û\ZAd\ÏH›ïg\ÍFm\¬%]',_binary '\Z∂t*ø\ÔN(ï}<~;—ºî','NO_CHECK','',100.0000,100.0000,'CREDIT',NULL),(_binary 'ÄŒéΩD\‰M¶πîn1F',_binary 'œÆ8|7NLbØ˙òD\¬\Ê\‚y',_binary 'Û\ZAd\ÏH›ïg\ÍFm\¬%]',_binary '\Z∂t*ø\ÔN(ï}<~;—ºî','RESERVE_DEBIT','USD',100.0000,100.0000,'DEBIT',NULL),(_binary '†ø\‘}\‚£D≈©Ò\€\‹}\≈n°',_binary '\Ó\ry´\ÁGÆû\„%W¶ú\◊H',_binary 'Û\ZAd\ÏH›ïg\ÍFm\¬%]',_binary '\"(¶\náDÈø•&Æ™A\ﬂ','RESERVE_DEBIT','',100.0000,100.0000,'DEBIT',NULL),(_binary '®\Ì\Êµ=GI™\0\—\‘“≥Ö',_binary 'œÆ8|7NLbØ˙òD\¬\Ê\‚y',_binary 'Åm±\ƒ@¸Ñ`†/\'d',_binary '\Z∂t*ø\ÔN(ï}<~;—ºî','NO_CHECK','USD',100.0000,100.0000,'CREDIT',NULL),(_binary '≤\ÀlU0µLB¥õN*®∑\”',_binary '\Í\‡\’\ÕL\Â∂\‹\ÁkJa',_binary 'Åm±\ƒ@¸Ñ`†/\'d',_binary '\Z∂t*ø\ÔN(ï}<~;—ºî','DIRECT_DEBIT','',100.0000,100.0000,'DEBIT',NULL),(_binary '\“4\Õo\ÀHÒÄC@ﬂï?∆•',_binary 'KSæ\œ\ÀN	©\Êp£Eµ',_binary 'Åm±\ƒ@¸Ñ`†/\'d',_binary '\Z∂t*ø\ÔN(ï}<~;—ºî','NO_CHECK','',100.0000,100.0000,'CREDIT',NULL);
/*!40000 ALTER TABLE `transaction_lines` ENABLE KEYS */;
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
