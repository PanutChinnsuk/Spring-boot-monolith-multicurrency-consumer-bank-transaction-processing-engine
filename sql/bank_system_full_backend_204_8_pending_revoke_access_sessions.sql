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
-- Table structure for table `pending_revoke_access_sessions`
--

DROP TABLE IF EXISTS `pending_revoke_access_sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pending_revoke_access_sessions` (
  `pending_id` bigint NOT NULL AUTO_INCREMENT,
  `cache_key` varchar(100) NOT NULL,
  `completed` tinyint(1) NOT NULL,
  `expires_at` timestamp NOT NULL,
  PRIMARY KEY (`pending_id`),
  UNIQUE KEY `key_UNIQUE` (`cache_key`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pending_revoke_access_sessions`
--

LOCK TABLES `pending_revoke_access_sessions` WRITE;
/*!40000 ALTER TABLE `pending_revoke_access_sessions` DISABLE KEYS */;
INSERT INTO `pending_revoke_access_sessions` VALUES (73,'9afb911a-2c55-4d52-aafa-47f68dd1dd2d',0,'2026-04-10 18:18:33'),(74,'301ae211-0efd-42b2-ad53-1a9c40d1851b',0,'2026-04-17 10:49:14'),(75,'c13c7193-0704-4853-bcbf-2c3efdea49be',0,'2026-04-17 10:51:19'),(76,'5c33b0f6-6959-46bc-8449-b76d8a19b6a6',0,'2026-04-25 15:07:46'),(77,'e84cb273-7ec7-43e1-b6d6-bc723f185c3e',1,'2026-04-28 11:37:52');
/*!40000 ALTER TABLE `pending_revoke_access_sessions` ENABLE KEYS */;
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
