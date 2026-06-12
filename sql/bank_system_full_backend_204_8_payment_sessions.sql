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
-- Table structure for table `payment_sessions`
--

DROP TABLE IF EXISTS `payment_sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment_sessions` (
  `payment_session_id` binary(16) NOT NULL,
  `user_id` binary(16) NOT NULL,
  `token_type` varchar(45) NOT NULL,
  `amount` decimal(19,4) NOT NULL,
  `card_id` binary(16) DEFAULT NULL,
  `account_id` binary(16) NOT NULL,
  `target_card_id` binary(16) DEFAULT NULL,
  `target_account_id` binary(16) NOT NULL,
  `expires_at` timestamp NOT NULL,
  `device_ip` varchar(100) NOT NULL,
  `revoked` tinyint(1) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `version` bigint NOT NULL,
  PRIMARY KEY (`payment_session_id`),
  UNIQUE KEY `payment_session_id_UNIQUE` (`payment_session_id`),
  KEY `fk_user_id` (`user_id`),
  KEY `fk_ass_so_ass` (`target_account_id`),
  KEY `fk_ass_so_ass3` (`card_id`),
  KEY `so_ass_Ass_4` (`target_card_id`),
  KEY `fk_ass_so_ass2` (`account_id`),
  CONSTRAINT `fk_ass_so_ass` FOREIGN KEY (`target_account_id`) REFERENCES `accounts` (`account_id`),
  CONSTRAINT `fk_ass_so_ass2` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`account_id`),
  CONSTRAINT `fk_ass_so_ass3` FOREIGN KEY (`card_id`) REFERENCES `cards` (`card_id`),
  CONSTRAINT `so_ass_Ass_4` FOREIGN KEY (`target_card_id`) REFERENCES `cards` (`card_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_sessions`
--

LOCK TABLES `payment_sessions` WRITE;
/*!40000 ALTER TABLE `payment_sessions` DISABLE KEYS */;
INSERT INTO `payment_sessions` VALUES (_binary '\ÓôÀ®\‡I]¥Ö\œ\Î—ß˚\Ó',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','CARD_PAYMENT',100.0000,_binary '∏dÿõâÙJzß˝/9)\Z',_binary 'Û\ZAd\ÏH›ïg\ÍFm\¬%]',_binary '¥*∫F∂FFò¿Úú“†?<',_binary '°>π\'¶´LoµßI˝Gr\÷','2026-05-18 11:18:55','0:0:0:0:0:0:0:1',1,'2026-05-18 11:17:55',0),(_binary '9\":∫ˆπK≤ÖÑáö¸pY',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','ACCOUNT_PAYMENT',100.0000,NULL,_binary 'Û\ZAd\ÏH›ïg\ÍFm\¬%]',NULL,_binary 'Åm±\ƒ@¸Ñ`†/\'d','2026-05-18 10:49:16','0:0:0:0:0:0:0:1',0,'2026-05-18 10:48:16',0),(_binary 'ÅWL˙äMAıúµég(¸™\Ì',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','CARD_PAYMENT',100.0000,_binary '∏dÿõâÙJzß˝/9)\Z',_binary 'Û\ZAd\ÏH›ïg\ÍFm\¬%]',_binary '¥*∫F∂FFò¿Úú“†?<',_binary '°>π\'¶´LoµßI˝Gr\÷','2026-05-18 10:50:17','0:0:0:0:0:0:0:1',0,'2026-05-18 10:49:17',0),(_binary 'ã \÷\»BøNΩº]Ú\0\√;\€',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','ACCOUNT_PAYMENT',100.0000,NULL,_binary 'Û\ZAd\ÏH›ïg\ÍFm\¬%]',NULL,_binary 'Åm±\ƒ@¸Ñ`†/\'d','2026-05-18 11:19:06','0:0:0:0:0:0:0:1',0,'2026-05-18 11:18:06',0),(_binary '\Ìˇ±õ]ïIU∫\«8x,\–π',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','ACCOUNT_PAYMENT',100.0000,NULL,_binary 'Û\ZAd\ÏH›ïg\ÍFm\¬%]',NULL,_binary 'Åm±\ƒ@¸Ñ`†/\'d','2026-05-19 14:39:10','0:0:0:0:0:0:0:1',0,'2026-05-19 14:38:10',0),(_binary '¯æö+ñàNèÖ`ˆ_8',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','ACCOUNT_PAYMENT',100.0000,NULL,_binary 'Û\ZAd\ÏH›ïg\ÍFm\¬%]',NULL,_binary 'Åm±\ƒ@¸Ñ`†/\'d','2026-05-19 14:41:39','0:0:0:0:0:0:0:1',0,'2026-05-19 14:40:39',0);
/*!40000 ALTER TABLE `payment_sessions` ENABLE KEYS */;
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
