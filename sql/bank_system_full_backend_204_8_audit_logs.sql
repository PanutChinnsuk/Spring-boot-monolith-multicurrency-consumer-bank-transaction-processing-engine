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
-- Table structure for table `audit_logs`
--

DROP TABLE IF EXISTS `audit_logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `audit_logs` (
  `audit_id` binary(16) NOT NULL DEFAULT (uuid_to_bin(uuid())),
  `user_actor_id` binary(16) DEFAULT NULL,
  `action` varchar(100) NOT NULL,
  `entity` varchar(100) NOT NULL,
  `entity_id` varchar(200) NOT NULL,
  `success` tinyint(1) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`audit_id`),
  KEY `idx_audit_entity` (`entity`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audit_logs`
--

LOCK TABLES `audit_logs` WRITE;
/*!40000 ALTER TABLE `audit_logs` DISABLE KEYS */;
INSERT INTO `audit_logs` VALUES (_binary '*7iµ:Ba‘\È!/$m',_binary '\n¢R\r*RK#£Aš\ã±1g\Ä','Testing','Zero','4b06b69e-3e6c-41df-b47d-9d37c3f599d5',0,'2026-04-03 13:35:36'),(_binary '0~NL£.Nw™ö¬ù\Ö\ÊcS',_binary '\n¢R\r*RK#£Aš\ã±1g\Ä','Testing','Zero','c37bda2c-7230-4f31-84ff-306b28609a34',1,'2026-04-03 13:50:08'),(_binary 'T%ðdB¹D)œ-U\âC\í+',_binary '\n¢R\r*RK#£Aš\ã±1g\Ä','Testing','Zero','959c55ee-b932-4a4a-a8cc-8589a2e64dfc',1,'2026-05-23 14:15:53'),(_binary 'Uxd\áL*A®g¢\ØÈ¤¢“',_binary '\n¢R\r*RK#£Aš\ã±1g\Ä','Testing','Zero','047b8db0-4e28-4326-ad8f-b918d8cf73b9',1,'2026-04-16 13:40:50'),(_binary 'm^\\1Gs\×\ëf”Œf',_binary '\n¢R\r*RK#£Aš\ã±1g\Ä','Testing','Zero','fbb737db-2742-403f-81ff-c08d13594925',1,'2026-04-03 13:56:47'),(_binary 'ou\Î\Ù\"¦I”±-5>„\ËÁI',_binary '\n¢R\r*RK#£Aš\ã±1g\Ä','Testing','Zero','2e4410cf-00b8-4999-befd-ab6abafb349e',1,'2026-04-16 13:40:49'),(_binary '‚P­\æ6IÈ“]\0¸Cg™',_binary '\n¢R\r*RK#£Aš\ã±1g\Ä','Testing','Zero','b11312c9-57a4-4dd5-9056-abc7de9d8312',1,'2026-04-07 11:23:27'),(_binary 'ŠrijªI‹ Oxk—\'6…',_binary '\n¢R\r*RK#£Aš\ã±1g\Ä','Testing','Zero','a8dc1a38-ab7d-4341-861d-78be7094e743',1,'2026-04-07 11:21:38'),(_binary '“9¥ƒµ\ÈC]ƒ«tïƒÀ\Ê',_binary '\n¢R\r*RK#£Aš\ã±1g\Ä','Testing','Zero','037a4502-ac9a-420e-aede-72c052c4dabc',1,'2026-04-07 11:53:32'),(_binary '•\ÍÂ€}J­5\ÉzZ:\Õ',_binary '\n¢R\r*RK#£Aš\ã±1g\Ä','Testing','Zero','fc98cd70-61f9-4dcb-a4e9-f4d49c9aa7a6',1,'2026-04-07 11:23:18'),(_binary 'œºjxDö‡©[(7¢‰\Ü',_binary '\n¢R\r*RK#£Aš\ã±1g\Ä','Testing','Zero','140dd36c-59c6-45fe-95db-c89e27435724',1,'2026-04-07 11:03:19'),(_binary 'ž8ªG\ÔDo¥\ë2„‹BH',_binary '\n¢R\r*RK#£Aš\ã±1g\Ä','Testing','Zero','676e0df7-f5a1-4e49-9df9-54b102de1eb0',1,'2026-04-04 22:15:55'),(_binary '¢¦BÓšCˆ­½³gß',_binary '\n¢R\r*RK#£Aš\ã±1g\Ä','Testing','Zero','7fcf802c-65fe-4370-acad-d74bf48f13cb',0,'2026-04-03 13:50:30'),(_binary '¹DNF‘65?#ý',_binary '\n¢R\r*RK#£Aš\ã±1g\Ä','Testing','Zero','b1582587-ae65-4677-90ec-c7961a6a6e50',1,'2026-04-14 10:33:29'),(_binary '¾C³\\5IY–$Ÿ¸4ðB>',_binary '\n¢R\r*RK#£Aš\ã±1g\Ä','Testing','Zero','12f0e406-9eb4-4db7-b531-3097d36cfbe8',1,'2026-04-10 17:17:52'),(_binary 'Á\ngû½†Kü\ÔÀ\ë,+¦‘',_binary '\n¢R\r*RK#£Aš\ã±1g\Ä','Testing','Zero','3d3cc701-1002-4dbf-919c-cf211299a9ee',1,'2026-04-14 10:34:36'),(_binary 'Ñµ\è[¾ƒI›(gøÞŠ\Ô:',_binary '\n¢R\r*RK#£Aš\ã±1g\Ä','Testing','Zero','c659a59a-8a9f-4d0b-bd60-a20cef4a283c',1,'2026-05-23 14:15:12'),(_binary 'ç²·h\ÑOò \ä$A…\rœ>',_binary '\n¢R\r*RK#£Aš\ã±1g\Ä','Testing','Zero','c691a94e-ef48-4fec-9d8b-6b29e674011d',1,'2026-04-03 13:35:00'),(_binary 'ýöóyQ&E=µ\Åz²º	\Ñ',_binary '\n¢R\r*RK#£Aš\ã±1g\Ä','Testing','Zero','97eaebc9-e502-4c4b-b7c9-69ca7b536906',1,'2026-04-07 09:03:25');
/*!40000 ALTER TABLE `audit_logs` ENABLE KEYS */;
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
