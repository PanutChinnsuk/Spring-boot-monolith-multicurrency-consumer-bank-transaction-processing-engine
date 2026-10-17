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
-- Table structure for table `email_objects`
--

DROP TABLE IF EXISTS `email_objects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `email_objects` (
  `email_id` binary(16) NOT NULL,
  `email_type` varchar(255) NOT NULL,
  `subject` varchar(255) NOT NULL,
  `sent_to_user_id` binary(16) NOT NULL,
  `sent_to` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL,
  PRIMARY KEY (`email_id`),
  KEY `fk_user_id_idx` (`sent_to_user_id`),
  KEY `fk_user_email_t` (`sent_to_user_id`),
  CONSTRAINT `this_fk_email` FOREIGN KEY (`sent_to_user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `email_objects`
--

LOCK TABLES `email_objects` WRITE;
/*!40000 ALTER TABLE `email_objects` DISABLE KEYS */;
INSERT INTO `email_objects` VALUES (_binary '\0\›m∆°\ÊG∂éçÖ¸Mâ\…','TWO_FACTOR','Login 2-Step Verification',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','jesskill002@gmail.com','2026-04-10 17:16:19'),(_binary '	\—˜MFGÄ\ÃC©™\„•','TWO_FACTOR','Login 2-Step Verification',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','jesskill002@gmail.com','2026-04-10 16:50:01'),(_binary '\√zBwê\ n\∆/W_','VERIFICATION','Email Address 2-Step Verification',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','jesskill002@gmail.com','2026-05-27 16:11:03'),(_binary '\Ÿl\‚+HKî\·!`,.\ƒ;','TWO_FACTOR','Login 2-Step Verification',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','jesskill002@gmail.com','2026-04-14 10:29:18'),(_binary '_UéìjJ†ï∏_7\‰ûA','VERIFICATION','Email Address 2-Step Verification',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','jesskill002@gmail.com','2026-05-27 16:11:55'),(_binary '\Ïé/ìEBí\’¸3º0wg','TWO_FACTOR','Login 2-Step Verification',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','jesskill002@gmail.com','2026-04-14 10:23:24'),(_binary '%å!é¶\'MŒñ\≈\ﬁ›¢ˆ','TWO_FACTOR','Login 2-Step Verification',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','jesskill002@gmail.com','2026-04-10 17:12:17'),(_binary '-\‚\‰nªDƒî\Ì\ÿ˙\„oª','TWO_FACTOR','Login 2-Step Verification',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','jesskill002@gmail.com','2026-04-16 13:38:27'),(_binary '.\Èá\“\›\‡MΩ\÷•$3','TWO_FACTOR','Login 2-Step Verification',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','jesskill002@gmail.com','2026-04-10 17:09:19'),(_binary '/g\Á\ ¸õ@_ò<y˛ü°','TWO_FACTOR','Login 2-Step Verification',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','jesskill002@gmail.com','2026-04-16 13:35:08'),(_binary '0∑\¬F\’!Hòã˘KO\À\Ã(','TWO_FACTOR','Login 2-Step Verification',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','jesskill002@gmail.com','2026-04-12 10:08:13'),(_binary '1\'ÁΩíπAFòäg©ºÜ','TWO_FACTOR','Email Address 2-Step Verification',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','jesskill002@gmail.com','2026-04-14 10:34:12'),(_binary '5\”nÅ†Kä©ØìTß\Á','TWO_FACTOR','Login 2-Step Verification',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','jesskill002@gmail.com','2026-04-14 10:22:55'),(_binary 'Mb\‡zXAÅî\ÃQ^b/\‰\'','TWO_FACTOR','Login 2-Step Verification',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','jesskill002@gmail.com','2026-04-16 13:29:09'),(_binary 'N&\‰og;LgßîF(\Ô]W','TWO_FACTOR','Login 2-Step Verification',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','jesskill002@gmail.com','2026-04-14 10:22:12'),(_binary 'U€ó\"\Ÿ?J¶¥æq&ê/!\‘','TWO_FACTOR','Login 2-Step Verification',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','jesskill002@gmail.com','2026-04-12 10:38:50'),(_binary 'Ysñ\‚N⁄Ø;ˇdxYëT','TWO_FACTOR','Login 2-Step Verification',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','jesskill002@gmail.com','2026-04-10 17:33:07'),(_binary 'gçJ\Í\‰MÕÜ\ÀKŒÅ«ì','VERIFICATION','Email Address 2-Step Verification',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','jesskill002@gmail.com','2026-05-27 16:01:22'),(_binary 't!ˆK}≥A]∑∆ΩFúJ\–','TWO_FACTOR','Email Address 2-Step Verification',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','jesskill002@gmail.com','2026-04-14 10:33:32'),(_binary 'w/Sä≠ÆEúEa2∑Ú','TWO_FACTOR','Login 2-Step Verification',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','jesskill002@gmail.com','2026-04-14 11:08:19'),(_binary '{µ¢Ú\È\ŒOTä˚\◊bPï','TWO_FACTOR','Login 2-Step Verification',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','jesskill002@gmail.com','2026-04-17 10:28:43'),(_binary 'Ç˚m∏%I¨àP=Xå\»1h','TWO_FACTOR','Email Address 2-Step Verification',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','jesskill002@gmail.com','2026-04-14 10:34:05'),(_binary '•7∂]&´D©¨\Î;¡Q˚ç','VERIFICATION','Email Address 2-Step Verification',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','jesskill002@gmail.com','2026-05-27 16:04:41'),(_binary 'Ø\⁄˜,kÚJ™@˚˛∑˛÷è','VERIFICATION','Email Address 2-Step Verification',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','jesskill002@gmail.com','2026-05-27 16:05:29'),(_binary '∞a<I\ÁE`∞\ÁR¡îW','TWO_FACTOR','Login 2-Step Verification',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','jesskill002@gmail.com','2026-04-10 17:19:50'),(_binary '∫ás(òD]ù≠qˇ\ÁB\…z','VERIFICATION','Email Address 2-Step Verification',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','jesskill002@gmail.com','2026-05-27 16:07:40'),(_binary 'ø∏\‡éëL7èTΩÆX\’','TWO_FACTOR','Login 2-Step Verification',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','jesskill002@gmail.com','2026-04-14 11:06:09'),(_binary '\≈a\È\“\‡\∆Fãﬁò,æ!','TWO_FACTOR','Login 2-Step Verification',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','jesskill002@gmail.com','2026-04-12 10:11:22'),(_binary '\∆Œæs˚ìAÖX\Zu§\ÍØ','TWO_FACTOR','Login 2-Step Verification',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','jesskill002@gmail.com','2026-04-14 10:32:55'),(_binary '\€W\Óö˚ıK\Îâ\“e\'|6±†','TWO_FACTOR','Email Address 2-Step Verification',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','jesskill002@gmail.com','2026-04-17 10:13:35'),(_binary '€è\Ì\rÒO$äP\”Sè\„Å\ﬂ','TWO_FACTOR','Login 2-Step Verification',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','jesskill002@gmail.com','2026-04-12 10:09:43'),(_binary 'ıäÎ°ê,O.ógÂåô/¡Ú','VERIFICATION','Email Address 2-Step Verification',_binary '\n¢R\r*RK#£Aö\„±1g\ƒ','jesskill002@gmail.com','2026-05-27 16:03:23');
/*!40000 ALTER TABLE `email_objects` ENABLE KEYS */;
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
