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
-- Table structure for table `loan_schedules`
--

DROP TABLE IF EXISTS `loan_schedules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loan_schedules` (
  `schedule_id` binary(16) NOT NULL,
  `loan_id` binary(16) NOT NULL,
  `due_date` date NOT NULL,
  `amount_due` decimal(19,4) NOT NULL,
  `partial_from_principal` decimal(19,4) NOT NULL,
  `partial_interest` decimal(19,4) NOT NULL,
  `paid_back_amount` decimal(19,4) NOT NULL,
  `currency` char(3) NOT NULL,
  `paid` tinyint(1) NOT NULL DEFAULT '0',
  `version` bigint NOT NULL,
  PRIMARY KEY (`schedule_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loan_schedules`
--

LOCK TABLES `loan_schedules` WRITE;
/*!40000 ALTER TABLE `loan_schedules` DISABLE KEYS */;
INSERT INTO `loan_schedules` VALUES (_binary '9W|\„\ŒA@Ü\Ë∏î§\È*',_binary '\⁄#k¢S∏Gs¶+≥ºÖ,[.','2026-11-19',10.0000,50.0000,0.0100,10.0100,'THB',1,1),(_binary '==Ö\Ì\ÊßM˛é¥\∆Ò£É\‡',_binary '\⁄#k¢S∏Gs¶+≥ºÖ,[.','2026-10-19',10.0000,60.0000,0.0200,10.0200,'THB',1,1),(_binary 'BR\’˜çXF∏ï*\'®\0A',_binary '\⁄#k¢S∏Gs¶+≥ºÖ,[.','2026-08-19',10.0000,80.0000,0.0200,10.0200,'THB',1,1),(_binary 'MG<¶\‚N¶ùrT∑êf',_binary '\⁄#k¢S∏Gs¶+≥ºÖ,[.','2027-01-19',10.0000,30.0000,0.0100,10.0100,'THB',1,1),(_binary 'òl\„J\≈\ A…íH˛9x\Ï',_binary '\⁄#k¢S∏Gs¶+≥ºÖ,[.','2026-12-19',10.0000,40.0000,0.0100,10.0100,'THB',1,1),(_binary '®uÇFU{EvØ\Z◊Ü0ä6Ò',_binary '\⁄#k¢S∏Gs¶+≥ºÖ,[.','2027-02-19',10.0000,20.0000,0.0100,10.0100,'THB',1,1),(_binary 'ƒ∞E7[®G`é\È^;\Z˜\ÿ',_binary '\⁄#k¢S∏Gs¶+≥ºÖ,[.','2026-07-19',10.0000,90.0000,0.0200,10.0200,'THB',1,1),(_binary '\À2p≠wO*êøè0é;\√',_binary '\⁄#k¢S∏Gs¶+≥ºÖ,[.','2026-09-19',10.0000,70.0000,0.0200,10.0200,'THB',1,1),(_binary '\‘\Í_Y∑|GîôC¯z”öå',_binary '\⁄#k¢S∏Gs¶+≥ºÖ,[.','2026-06-19',10.0000,100.0000,0.0300,10.0300,'THB',1,1),(_binary 'Ò-\Í\–<òK¨ïÙ\≈\’Lc\Ì',_binary '\⁄#k¢S∏Gs¶+≥ºÖ,[.','2027-03-19',10.0000,10.0000,0.0000,10.0000,'THB',1,2);
/*!40000 ALTER TABLE `loan_schedules` ENABLE KEYS */;
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
