-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: t2208e_sem4
-- ------------------------------------------------------
-- Server version	8.4.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Department`
--

DROP TABLE IF EXISTS `Department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Department` (
  `id` int NOT NULL AUTO_INCREMENT,
  `department_name` varchar(255) NOT NULL,
  `room_number` varchar(50) DEFAULT NULL,
  `number_employee` int DEFAULT NULL,
  `chief_id` bigint DEFAULT NULL,
  `deputy_1_id` bigint DEFAULT NULL,
  `deputy_2_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `department_name` (`department_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Department`
--

LOCK TABLES `Department` WRITE;
/*!40000 ALTER TABLE `Department` DISABLE KEYS */;
INSERT INTO `Department` VALUES (1,'Seller','B5',9,1,12,13),(2,'HR','B6',2,8,NULL,NULL),(3,'IT','B7',4,9,10,11);
/*!40000 ALTER TABLE `Department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Employee`
--

DROP TABLE IF EXISTS `Employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Employee` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `full_name` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `base_salary` double DEFAULT NULL,
  `net_salary` double DEFAULT NULL,
  `insurance_base` double DEFAULT NULL,
  `department_id` int DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `is_off` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  KEY `fk_department` (`department_id`),
  CONSTRAINT `fk_department` FOREIGN KEY (`department_id`) REFERENCES `Department` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Employee`
--

LOCK TABLES `Employee` WRITE;
/*!40000 ALTER TABLE `Employee` DISABLE KEYS */;
INSERT INTO `Employee` VALUES (1,'John1','123 Main St','1990-01-01',50000,45000,2000,1,'Chief','john1.doe@example.com',0),(3,'John3','123 Main St','1990-01-01',50000,45000,2000,1,'Employee','john3.doe@example.com',0),(4,'John4','123 Main St','1990-01-01',50000,45000,2000,1,'Employee','john4.doe@example.com',0),(5,'John5','123 Main St','1990-01-01',50000,45000,2000,2,'Employee','john5.doe@example.com',0),(6,'John6','123 Main St','1990-01-01',50000,45000,2000,3,'Employee','john6.doe@example.com',0),(7,'John7','123 Main St','1990-01-01',50000,45000,2000,1,'Employee','john7.doe@example.com',0),(8,'John8','123 Main St','1990-01-01',50000,45000,2000,2,'Chief','john8.doe@example.com',0),(9,'John9','123 Main St','1990-01-01',50000,45000,2000,3,'Chief','john9.doe@example.com',0),(10,'John10','123 Main St','1990-01-01',50000,45000,2000,3,'Deputy','john10.doe@example.com',0),(11,'John11','123 Main St','1990-01-01',50000,45000,2000,3,'Deputy','john11.doe@example.com',0),(12,'John12','123 Main St','1990-01-01',50000,45000,2000,1,'Deputy','john12.doe@example.com',0),(13,'John13','123 Main St','1990-01-01',50000,45000,2000,1,'Deputy','john13.doe@example.com',0),(14,'John14','123 Main St','1990-01-01',50000,45000,2000,1,'Employee','john14.doe@example.com',0),(15,'John15','123 Main St','1990-01-01',50000,45000,2000,1,'Employee','john15.doe@example.com',0);
/*!40000 ALTER TABLE `Employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 't2208e_sem4'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-15 23:35:11
