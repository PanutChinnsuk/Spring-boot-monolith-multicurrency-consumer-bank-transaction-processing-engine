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
-- Table structure for table `two_factor_codes`
--

DROP TABLE IF EXISTS `two_factor_codes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `two_factor_codes` (
  `code_id` binary(16) NOT NULL,
  `user_id` binary(16) NOT NULL,
  `code` varchar(255) NOT NULL,
  `device_ip` varchar(255) NOT NULL,
  `revoked` tinyint(1) NOT NULL,
  `expires_at` timestamp NOT NULL,
  `created_at` timestamp NOT NULL,
  `email_id` binary(16) NOT NULL,
  `two_factor_purpose` varchar(45) NOT NULL,
  PRIMARY KEY (`code_id`),
  UNIQUE KEY `un1` (`user_id`,`code`),
  UNIQUE KEY `email_id_UNIQUE` (`email_id`),
  KEY `fk_keyt_t1_idx` (`user_id`),
  KEY `fk_keyt_t2_idx` (`email_id`),
  CONSTRAINT `fk_keyt_t1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fk_keyt_t2` FOREIGN KEY (`email_id`) REFERENCES `email_objects` (`email_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `two_factor_codes`
--

LOCK TABLES `two_factor_codes` WRITE;
/*!40000 ALTER TABLE `two_factor_codes` DISABLE KEYS */;
INSERT INTO `two_factor_codes` VALUES (_binary '\0XÄz∞\ÔNAá¥\„\ﬁ	§6',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','{bcrypt}$2a$10$1T4MhCxiLgCt3gsuUN50GuaRFCQeTJN1UwUc3oqh0YUeUzgVOMDNK','0:0:0:0:0:0:0:1',0,'2026-05-27 19:31:55','2026-05-27 16:11:56',_binary '_UéìjJ†ï∏_7\‰ûA','EMAIL_ADDRESS_VERIFICATION'),(_binary 'éØˆçLöú\·{¯<`å\0',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','{bcrypt}$2a$10$YVxF21pi6zNoXjCNomp.yO0vr4SyAxZM.H6jc3M5ThEaD3nX2OAvK','0:0:0:0:0:0:0:1',1,'2026-04-14 10:36:05','2026-04-14 10:34:05',_binary 'Ç˚m∏%I¨àP=Xå\»1h','EMAIL_ADDRESS_VERIFICATION'),(_binary '’¶Ø7\÷Oâå+ßbö',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','{bcrypt}$2a$10$4jAQG90ClRfDXR1chwt8J.VIBKJdHACL.YQ2sdkfPGt.Uh7WMvBFS','0:0:0:0:0:0:0:1',0,'2026-04-14 10:36:12','2026-04-14 10:34:12',_binary '1\'ÁΩíπAFòäg©ºÜ','EMAIL_ADDRESS_VERIFICATION'),(_binary 'Ø¢\·\ÃmIçs\«≈Ø∑.',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','{bcrypt}$2a$10$A2KMYyiOXLFoMdt6rtEkNOm35smcIA.5E/hbKx6yOtZFB3OzoY5xS','0:0:0:0:0:0:0:1',1,'2026-04-16 13:37:08','2026-04-16 13:35:08',_binary '/g\Á\ ¸õ@_ò<y˛ü°','AUTHENTICATION'),(_binary '\0˘u4∞M⁄™Fáu¨\’}ô',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','{bcrypt}$2a$10$AfIWi7tbKX3Mk86yG7cjLOVs/91f3mJJJBIa7.jGusA.UmRYGZrPy','0:0:0:0:0:0:0:1',1,'2026-05-27 19:31:03','2026-05-27 16:11:03',_binary '\√zBwê\ n\∆/W_','EMAIL_ADDRESS_VERIFICATION'),(_binary '/±^\«¿N√∞\ﬂ[ª\·ö\Ë',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','{bcrypt}$2a$10$W1rBpuZXPv40i7pgQZeWTuZ39HuzMFTekKG7qs/3/9nyLBMJa2lgW','0:0:0:0:0:0:0:1',1,'2026-04-14 11:10:19','2026-04-14 11:08:19',_binary 'w/Sä≠ÆEúEa2∑Ú','AUTHENTICATION'),(_binary '=ˇ.5ØDR∏a9fïøã',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','{bcrypt}$2a$10$w5Wy39/93erDGGTOq6I04.uF5hu0LxGEKNVUeQ6ivk7CtSAGiCpS.','0:0:0:0:0:0:0:1',1,'2026-04-16 13:40:27','2026-04-16 13:38:27',_binary '-\‚\‰nªDƒî\Ì\ÿ˙\„oª','AUTHENTICATION'),(_binary 'IõLûØwDO∂Ex`¡}\'|',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','{bcrypt}$2a$10$7xmti2vgdtDb3TaxY/JXDOHAZApC8.ge7nIgujU2Kyss0/IPjujTG','0:0:0:0:0:0:0:1',0,'2026-04-17 10:30:43','2026-04-17 10:28:43',_binary '{µ¢Ú\È\ŒOTä˚\◊bPï','AUTHENTICATION'),(_binary 'UÑ˘\√oO≥\—._W\Ã%x',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','{bcrypt}$2a$10$Ks2RCNcF69AWINPUbeDwn.zWkS.bSPUEBcmJzCvCV6gFQF3FMnQJq','0:0:0:0:0:0:0:1',0,'2026-05-27 16:03:22','2026-05-27 16:01:22',_binary 'gçJ\Í\‰MÕÜ\ÀKŒÅ«ì','EMAIL_ADDRESS_VERIFICATION'),(_binary 'YK+î=N&àó\⁄=B\\∞\€',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','{bcrypt}$2a$10$8.ddgVmb5eIQZDDgG6Vq1eDPoZ/1DLBjr362N9Wzz.XSLO0bXu5Ni','0:0:0:0:0:0:0:1',1,'2026-04-14 10:35:32','2026-04-14 10:33:32',_binary 't!ˆK}≥A]∑∆ΩFúJ\–','EMAIL_ADDRESS_VERIFICATION'),(_binary 'g5{O˛ÛF±a:π∞!D5',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','{bcrypt}$2a$10$L7MZjsuWjWdo.4EgT2eaDOARwt4Dil1hzWdcU2oAefCxdfLGOknA.','0:0:0:0:0:0:0:1',1,'2026-04-14 10:31:18','2026-04-14 10:29:18',_binary '\Ÿl\‚+HKî\·!`,.\ƒ;','AUTHENTICATION'),(_binary 'tFëú˛\“JQøãûí(@x\'',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','{bcrypt}$2a$10$QtvVUUh0AVtGbdbI9RvHjObIwbE2HDcZegdwpyMdoaMLYnFaIu/ca','0:0:0:0:0:0:0:1',1,'2026-04-14 10:24:55','2026-04-14 10:22:55',_binary '5\”nÅ†Kä©ØìTß\Á','AUTHENTICATION'),(_binary 'Ö\Ÿ\‹\nCF†ut4K\–',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','{bcrypt}$2a$10$gdkgnvmP0S7.C/Y5nhwWQO7vJ3sm60Wxnv0uw4HzQ5Sh/yTzwauEO','0:0:0:0:0:0:0:1',1,'2026-05-27 19:27:40','2026-05-27 16:07:40',_binary '∫ás(òD]ù≠qˇ\ÁB\…z','EMAIL_ADDRESS_VERIFICATION'),(_binary '•u≠±E\"ñ3]\ﬂ \‡5\ﬂ',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','{bcrypt}$2a$10$wyoJojqPZ.3N5weUeJMubOQTZE/4a0dGjDfIJx67..Kzv0EIftvQO','0:0:0:0:0:0:0:1',1,'2026-05-27 19:23:23','2026-05-27 16:03:23',_binary 'ıäÎ°ê,O.ógÂåô/¡Ú','EMAIL_ADDRESS_VERIFICATION'),(_binary '±\\s˛@ªBÛ6\À',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','{bcrypt}$2a$10$UVDSgWCa4f52AQoeR2vMo.BTLyV7hFSPEEw9haE0UlXBFo.Fm80hS','0:0:0:0:0:0:0:1',1,'2026-05-27 19:24:41','2026-05-27 16:04:42',_binary '•7∂]&´D©¨\Î;¡Q˚ç','EMAIL_ADDRESS_VERIFICATION'),(_binary '¥ÒhR2N¯°\'Yª\‰a≠m',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','{bcrypt}$2a$10$gW7F5PMux8uYqYr3xPdXUOacUBN4WlH2KMhZP3IMR3Eg6jrj6oYf.','0:0:0:0:0:0:0:1',1,'2026-05-27 19:25:29','2026-05-27 16:05:29',_binary 'Ø\⁄˜,kÚJ™@˚˛∑˛÷è','EMAIL_ADDRESS_VERIFICATION'),(_binary 'º\Óm\—,@öp“≠i)',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','{bcrypt}$2a$10$jI33BLlE0peX4qkN8u13s.C7gCuqrTDiY62qaktoBw0lbf.JjObfq','0:0:0:0:0:0:0:1',0,'2026-04-14 11:08:09','2026-04-14 11:06:09',_binary 'ø∏\‡éëL7èTΩÆX\’','AUTHENTICATION'),(_binary 'øåÇ\ÂHßH¿πhJÚ<∫¡',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','{bcrypt}$2a$10$Kdwp22owZ3xyYTnHn3j0IuRSOdNFeeuX7PyW43C8rxYNJS/ygxdLm','0:0:0:0:0:0:0:1',1,'2026-04-14 10:34:55','2026-04-14 10:32:55',_binary '\∆Œæs˚ìAÖX\Zu§\ÍØ','AUTHENTICATION'),(_binary '\œgQ9E4B•±\À\Â*{\€G',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','{bcrypt}$2a$10$4ipnXYtwYqkdnLP02q9xJuPmRqp0MZToqZCZWBE/OVURHrWNWxk0G','0:0:0:0:0:0:0:1',1,'2026-04-17 10:15:35','2026-04-17 10:13:35',_binary '\€W\Óö˚ıK\Îâ\“e\'|6±†','EMAIL_ADDRESS_VERIFICATION'),(_binary '\⁄Ò˝ô\…CO±\√\nLj5\Ë',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','{bcrypt}$2a$10$IkcedzQLH2lqQNzyfY1ilebIgRMYDgwJ7ebztFnQAOO/D.26VOGHG','0:0:0:0:0:0:0:1',1,'2026-04-14 10:24:12','2026-04-14 10:22:12',_binary 'N&\‰og;LgßîF(\Ô]W','AUTHENTICATION'),(_binary '\„ÂêâpAM¯êã´CûLm',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','{bcrypt}$2a$10$n9k1KTR2/421zXTFjOFZJO3zQSaOQDPyipHwLefQ1.eXcw80blr5W','0:0:0:0:0:0:0:1',1,'2026-04-16 13:31:09','2026-04-16 13:29:09',_binary 'Mb\‡zXAÅî\ÃQ^b/\‰\'','AUTHENTICATION'),(_binary '\ÈNû˜üOsØµ(/ÑOz¢',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','{bcrypt}$2a$10$4IORsxHpa2eWgMSZnZ2Jcuqz656skyWNSDDzSDobX06FGL4sJVOgy','0:0:0:0:0:0:0:1',0,'2026-04-14 10:25:24','2026-04-14 10:23:24',_binary '\Ïé/ìEBí\’¸3º0wg','AUTHENTICATION');
/*!40000 ALTER TABLE `two_factor_codes` ENABLE KEYS */;
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
