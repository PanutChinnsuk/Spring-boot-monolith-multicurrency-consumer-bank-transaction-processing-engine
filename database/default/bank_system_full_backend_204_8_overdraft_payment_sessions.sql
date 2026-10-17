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
-- Table structure for table `overdraft_payment_sessions`
--

DROP TABLE IF EXISTS `overdraft_payment_sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `overdraft_payment_sessions` (
  `payment_session_id` binary(16) NOT NULL,
  `user_id` binary(16) NOT NULL,
  `token_type` varchar(45) NOT NULL,
  `payback_amount` decimal(19,4) NOT NULL,
  `account_id` binary(16) NOT NULL,
  `overdrafted_id` binary(16) NOT NULL,
  `expires_at` timestamp NOT NULL,
  `device_ip` varchar(100) NOT NULL,
  `revoked` tinyint(1) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `version` bigint NOT NULL,
  PRIMARY KEY (`payment_session_id`),
  UNIQUE KEY `payment_session_id_UNIQUE` (`payment_session_id`),
  KEY `fk_user_id` (`user_id`),
  KEY `fk_optional_account_number` (`account_id`),
  KEY `od_d_id2_idx` (`overdrafted_id`),
  CONSTRAINT `od_d_id2` FOREIGN KEY (`overdrafted_id`) REFERENCES `overdrafted_accounts` (`overdrafted_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `overdraft_payment_sessions`
--

LOCK TABLES `overdraft_payment_sessions` WRITE;
/*!40000 ALTER TABLE `overdraft_payment_sessions` DISABLE KEYS */;
INSERT INTO `overdraft_payment_sessions` VALUES (_binary '*lo+Ω§F¢†•é_\Ô\·',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','OVERDRAFT_ACCOUNT_PAYBACK',500.0000,_binary 'Åm±\ƒ@¸Ñ`†/\'d',_binary '\√tE7\ÃNM;∏Oo#\Ìb','2026-05-24 01:06:43','0:0:0:0:0:0:0:1',1,'2026-05-24 00:56:43',1),(_binary 'G%%ZŸëL_ÆÒ\–∆Öf?\€',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','OVERDRAFT_ACCOUNT_PAYBACK',100.0000,_binary 'Åm±\ƒ@¸Ñ`†/\'d',_binary '\√tE7\ÃNM;∏Oo#\Ìb','2026-05-24 01:24:30','0:0:0:0:0:0:0:1',1,'2026-05-24 01:14:30',1),(_binary 'XCÀñ∫ˇF#û\ﬁ\‚\„Uµ',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','OVERDRAFT_ACCOUNT_PAYBACK',2000.0000,_binary 'v\Èë\ÁgA\‚ç\∆I\ÌMÒ2\‡',_binary 'ŸüIK\'ïOyáI\⁄\‰S∞{','2026-05-24 01:43:00','0:0:0:0:0:0:0:1',1,'2026-05-24 01:33:00',1),(_binary 'jJ\Z8\”LøèØöN\Á£\·',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','OVERDRAFT_ACCOUNT_PAYBACK',500.0000,_binary 'Åm±\ƒ@¸Ñ`†/\'d',_binary '\√tE7\ÃNM;∏Oo#\Ìb','2026-05-24 00:55:35','0:0:0:0:0:0:0:1',0,'2026-05-24 00:54:35',0),(_binary 'u¿Éb˘JF±\›`\«\∆\0ß9',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','OVERDRAFT_ACCOUNT_PAYBACK',2000.0000,_binary 'Åm±\ƒ@¸Ñ`†/\'d',_binary '\√tE7\ÃNM;∏Oo#\Ìb','2026-05-24 01:40:58','0:0:0:0:0:0:0:1',1,'2026-05-24 01:30:58',1),(_binary 'we\›NÛÑ≥\·\“,9\Ô',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','OVERDRAFT_ACCOUNT_PAYBACK',100.0000,_binary 'Åm±\ƒ@¸Ñ`†/\'d',_binary '\√tE7\ÃNM;∏Oo#\Ìb','2026-05-24 01:14:01','0:0:0:0:0:0:0:1',0,'2026-05-24 01:04:01',0),(_binary 'â\≈V\Õ\"¸M\‚òí|ﬁß\–',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','OVERDRAFT_ACCOUNT_PAYBACK',500.0000,_binary 'Åm±\ƒ@¸Ñ`†/\'d',_binary '\√tE7\ÃNM;∏Oo#\Ìb','2026-05-24 00:53:32','0:0:0:0:0:0:0:1',0,'2026-05-24 00:52:32',0),(_binary 'íæ!9@\ÃOﬂâ%>˛¶\'\Í',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','OVERDRAFT_ACCOUNT_PAYBACK',500.0000,_binary 'Åm±\ƒ@¸Ñ`†/\'d',_binary '\√tE7\ÃNM;∏Oo#\Ìb','2026-05-24 00:52:06','0:0:0:0:0:0:0:1',0,'2026-05-24 00:51:06',0),(_binary 'µ ©VH\ŸONâ/á\Îy\'6',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','OVERDRAFT_ACCOUNT_PAYBACK',50.0000,_binary 'Åm±\ƒ@¸Ñ`†/\'d',_binary '\√tE7\ÃNM;∏Oo#\Ìb','2026-05-24 00:51:14','0:0:0:0:0:0:0:1',1,'2026-05-24 00:50:14',1);
/*!40000 ALTER TABLE `overdraft_payment_sessions` ENABLE KEYS */;
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
