CREATE DATABASE  IF NOT EXISTS `jot` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `jot`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: jot
-- ------------------------------------------------------
-- Server version	5.7.17-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent` int(11) DEFAULT NULL,
  `name` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk-name_idx` (`name`),
  CONSTRAINT `fk-name` FOREIGN KEY (`name`) REFERENCES `resources` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,NULL,26),(2,1,25);
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `levels`
--

DROP TABLE IF EXISTS `levels`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `levels` (
  `id` int(11) NOT NULL,
  `resource_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `jk-l-01_idx` (`resource_id`),
  CONSTRAINT `jk-l-01` FOREIGN KEY (`resource_id`) REFERENCES `resources` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `levels`
--

LOCK TABLES `levels` WRITE;
/*!40000 ALTER TABLE `levels` DISABLE KEYS */;
INSERT INTO `levels` VALUES (1,7),(2,8),(3,9),(4,10),(5,11);
/*!40000 ALTER TABLE `levels` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `log_sessions`
--

DROP TABLE IF EXISTS `log_sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `log_sessions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `level` int(11) NOT NULL,
  `ip` varchar(45) NOT NULL,
  `date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `log_sessions`
--

LOCK TABLES `log_sessions` WRITE;
/*!40000 ALTER TABLE `log_sessions` DISABLE KEYS */;
/*!40000 ALTER TABLE `log_sessions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mcq`
--

DROP TABLE IF EXISTS `mcq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mcq` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_id` int(11) NOT NULL,
  `explanation_res_id` int(11) NOT NULL,
  `seconds_to_answer` int(11) NOT NULL DEFAULT '30',
  `level` int(11) NOT NULL DEFAULT '3',
  PRIMARY KEY (`id`),
  KEY `fk-01-mcq_idx` (`resource_id`),
  KEY `fk-02-mcq_idx` (`explanation_res_id`),
  KEY `fk-03-mcq_idx` (`level`),
  CONSTRAINT `fk-01-mcq` FOREIGN KEY (`resource_id`) REFERENCES `resources` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk-02-mcq` FOREIGN KEY (`explanation_res_id`) REFERENCES `resources` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk-03-mcq` FOREIGN KEY (`level`) REFERENCES `levels` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=233 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mcq`
--

LOCK TABLES `mcq` WRITE;
/*!40000 ALTER TABLE `mcq` DISABLE KEYS */;
INSERT INTO `mcq` VALUES (1,1,6,60,2),(2,12,18,60,3),(231,19,20,60,3);
/*!40000 ALTER TABLE `mcq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mcq_answers`
--

DROP TABLE IF EXISTS `mcq_answers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mcq_answers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question_id` int(11) NOT NULL,
  `resource_id` int(11) NOT NULL,
  `right_answer` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk-01-mcq-answers_idx` (`resource_id`),
  KEY `fk-02-mcq-answers_idx` (`question_id`),
  CONSTRAINT `fk-01-mcq-answers` FOREIGN KEY (`resource_id`) REFERENCES `resources` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk-02-mcq-answers` FOREIGN KEY (`question_id`) REFERENCES `mcq` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mcq_answers`
--

LOCK TABLES `mcq_answers` WRITE;
/*!40000 ALTER TABLE `mcq_answers` DISABLE KEYS */;
INSERT INTO `mcq_answers` VALUES (1,1,2,0),(2,1,3,1),(3,1,4,0),(4,1,5,0),(5,2,13,0),(6,2,14,1),(7,2,15,0),(8,2,16,0);
/*!40000 ALTER TABLE `mcq_answers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pre_registered_applications`
--

DROP TABLE IF EXISTS `pre_registered_applications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pre_registered_applications` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `fullname` varchar(255) NOT NULL,
  `pin` varchar(45) NOT NULL,
  `creation_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `consumption_date` datetime DEFAULT NULL,
  `success` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `questionnaires`
--

DROP TABLE IF EXISTS `questionnaires`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questionnaires` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `level` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk-levels_idx` (`level`),
  CONSTRAINT `fk-levels` FOREIGN KEY (`level`) REFERENCES `levels` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questionnaires`
--

LOCK TABLES `questionnaires` WRITE;
/*!40000 ALTER TABLE `questionnaires` DISABLE KEYS */;
INSERT INTO `questionnaires` VALUES (1,3);
/*!40000 ALTER TABLE `questionnaires` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions_categories`
--

DROP TABLE IF EXISTS `questions_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questions_categories` (
  `question` int(11) NOT NULL,
  `category` int(11) NOT NULL,
  PRIMARY KEY (`question`,`category`),
  KEY `fk_qc_category_idx` (`category`),
  CONSTRAINT `fk_qc_category` FOREIGN KEY (`category`) REFERENCES `categories` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fkquestion` FOREIGN KEY (`question`) REFERENCES `mcq` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions_categories`
--

LOCK TABLES `questions_categories` WRITE;
/*!40000 ALTER TABLE `questions_categories` DISABLE KEYS */;
/*!40000 ALTER TABLE `questions_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions_in_questionnaires`
--

DROP TABLE IF EXISTS `questions_in_questionnaires`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questions_in_questionnaires` (
  `questionnary_id` int(11) NOT NULL,
  `question_id` int(11) NOT NULL,
  PRIMARY KEY (`questionnary_id`,`question_id`),
  KEY `qinq-fk2_idx` (`question_id`),
  CONSTRAINT `qinq-fk2` FOREIGN KEY (`question_id`) REFERENCES `mcq` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `qinq-fkq` FOREIGN KEY (`questionnary_id`) REFERENCES `questionnaires` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions_in_questionnaires`
--

LOCK TABLES `questions_in_questionnaires` WRITE;
/*!40000 ALTER TABLE `questions_in_questionnaires` DISABLE KEYS */;
INSERT INTO `questions_in_questionnaires` VALUES (1,1),(1,2);
/*!40000 ALTER TABLE `questions_in_questionnaires` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resources`
--

DROP TABLE IF EXISTS `resources`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resources` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=488 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resources`
--

LOCK TABLES `resources` WRITE;
/*!40000 ALTER TABLE `resources` DISABLE KEYS */;
INSERT INTO `resources` VALUES (1,'<span class=\"question\">Which four options describe the correct default values for array elements of the types indicated?</span>\n<ol class=\"java-ol-1234\">\n<li>int -&gt; 0</li>\n<li>String -&gt; \"null\"</li>\n<li>Dog -&gt; null</li>\n<li>char -&gt; \'\\u0000\'</li>\n<li>float -&gt; 0.0f</li>\n<li>boolean -&gt; true</li>\n</ol>'),(2,'<span class=\"answer\">A</span> 1, 2, 3, 4'),(3,'<span class=\"answer\">B</span> 1, 3, 4, 5'),(4,'<span class=\"answer\">C</span> 2, 4, 5, 6'),(5,'<span class=\"answer\">D</span> 3, 4, 5, 6'),(6,'(1), (3), (4), (5) are the correct statements.<br /> (2) is wrong because the default value for a String (and any other object reference) is null, with no quotes.<br />(6) is wrong because the default value for boolean elements is false.'),(7,'Beginner'),(8,'Junior'),(9,'Intermediate'),(10,'Senior'),(11,'Guru'),(12,'<span class=\"question\">Which one of these lists contains only Java programming language keywords?</span>'),(13,'<span class=\"answer\">A</span> class, if, void, long, Int, continue'),(14,'<span class=\"answer\">B</span> goto, instanceof, native, finally, default, throws'),(15,'<span class=\"answer\">C</span> try, virtual, throw, final, volatile, transient'),(16,'<span class=\"answer\">D</span> strictfp, constant, super, implements, do'),(17,'<span class=\"answer\">E</span> byte, break, assert, switch, include'),(18,'All the words in option B are among the 49 Java keywords. Although goto reserved as a keyword in Java, goto is not used and has no function.<br />'),(19,'<span class=\"question\">\r\nWhich three are legal array declarations?\r\n<ol class=\"java-ol-1234\">\r\n<li>int [] myScores [];</li>\r\n<li>char [] myChars;</li>\r\n<li>int [6] myScores;</li>\r\n<li>Dog myDogs [];</li>\r\n<li>Dog myDogs [7];</li>\r\n</ol>\r\n</span>'),(20,'(1), (2), and (4) are legal array declarations. With an array declaration, you can place the brackets to the right or left of the identifier. Option A looks strange, but it\'s perfectly legal to split the brackets in a multidimensional array, and place them on both sides of the identifier. Although coding this way would only annoy your fellow programmers, for the exam, you need to know it\'s legal.</p>\r <p>(3) and (5) are wrong because you can\'t declare an array with a size. The size is only needed when the array is actually instantiated (and the JVM needs to know how much space to allocate for the array, based on the type of array and the size).'),(21,'<span class=\"answer\">A</span> 1, 2, 4'),(22,'<span class=\"answer\">B</span> 2, 4, 5'),(23,'<span class=\"answer\">C</span> 2, 3, 4'),(24,'<span class=\"answer\">D</span> All are correct.'),(25,'Fundamentals'),(26,'Java');
/*!40000 ALTER TABLE `resources` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sessions`
--

DROP TABLE IF EXISTS `sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sessions` (
  `id` varchar(50) NOT NULL,
  `authenticated` int(11) NOT NULL DEFAULT '0',
  `login_time` datetime DEFAULT NULL,
  `last_access` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `valid` int(11) NOT NULL DEFAULT '0',
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk-user_idx` (`user_id`),
  CONSTRAINT `fk-user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sessions`
--

LOCK TABLES `sessions` WRITE;
/*!40000 ALTER TABLE `sessions` DISABLE KEYS */;
/*!40000 ALTER TABLE `sessions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `translations`
--

DROP TABLE IF EXISTS `translations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `translations` (
  `resource_id` int(11) NOT NULL,
  `lang` varchar(5) NOT NULL,
  `content` text NOT NULL,
  PRIMARY KEY (`resource_id`,`lang`),
  CONSTRAINT `fkresid` FOREIGN KEY (`resource_id`) REFERENCES `resources` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `translations`
--

LOCK TABLES `translations` WRITE;
/*!40000 ALTER TABLE `translations` DISABLE KEYS */;
/*!40000 ALTER TABLE `translations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='	';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `v_categories`
--

DROP TABLE IF EXISTS `v_categories`;
/*!50001 DROP VIEW IF EXISTS `v_categories`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `v_categories` AS SELECT 
 1 AS `id`,
 1 AS `parent`,
 1 AS `category_name`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `v_levels`
--

DROP TABLE IF EXISTS `v_levels`;
/*!50001 DROP VIEW IF EXISTS `v_levels`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `v_levels` AS SELECT 
 1 AS `id`,
 1 AS `level_name`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `v_mcq`
--

DROP TABLE IF EXISTS `v_mcq`;
/*!50001 DROP VIEW IF EXISTS `v_mcq`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `v_mcq` AS SELECT 
 1 AS `id`,
 1 AS `question`,
 1 AS `explanation`,
 1 AS `seconds_to_answer`,
 1 AS `level_name`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `v_mcq_answers`
--

DROP TABLE IF EXISTS `v_mcq_answers`;
/*!50001 DROP VIEW IF EXISTS `v_mcq_answers`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `v_mcq_answers` AS SELECT 
 1 AS `id`,
 1 AS `question`,
 1 AS `answer`,
 1 AS `explanation`,
 1 AS `isRight`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `v_questionnaires`
--

DROP TABLE IF EXISTS `v_questionnaires`;
/*!50001 DROP VIEW IF EXISTS `v_questionnaires`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `v_questionnaires` AS SELECT 
 1 AS `id`,
 1 AS `level_name`*/;
SET character_set_client = @saved_cs_client;

--
-- Dumping events for database 'jot'
--

--
-- Dumping routines for database 'jot'
--

--
-- Final view structure for view `v_categories`
--

/*!50001 DROP VIEW IF EXISTS `v_categories`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `v_categories` AS (select `c`.`id` AS `id`,`c`.`parent` AS `parent`,`r`.`content` AS `category_name` from (`categories` `c` join `resources` `r` on((`c`.`name` = `r`.`id`)))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_levels`
--

/*!50001 DROP VIEW IF EXISTS `v_levels`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `v_levels` AS (select `l`.`id` AS `id`,`r`.`content` AS `level_name` from (`levels` `l` join `resources` `r` on((`l`.`resource_id` = `r`.`id`)))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_mcq`
--

/*!50001 DROP VIEW IF EXISTS `v_mcq`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `v_mcq` AS (select `m`.`id` AS `id`,`r1`.`content` AS `question`,`r2`.`content` AS `explanation`,`m`.`seconds_to_answer` AS `seconds_to_answer`,`l`.`level_name` AS `level_name` from (((`mcq` `m` join `resources` `r1` on((`m`.`resource_id` = `r1`.`id`))) join `resources` `r2` on((`m`.`explanation_res_id` = `r2`.`id`))) join `v_levels` `l` on((`m`.`level` = `l`.`id`)))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_mcq_answers`
--

/*!50001 DROP VIEW IF EXISTS `v_mcq_answers`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `v_mcq_answers` AS (select `ma`.`id` AS `id`,`v_m`.`question` AS `question`,`r`.`content` AS `answer`,`v_m`.`explanation` AS `explanation`,(case when (`ma`.`right_answer` = 0) then 'false' else 'true' end) AS `isRight` from ((`mcq_answers` `ma` join `resources` `r` on((`ma`.`resource_id` = `r`.`id`))) join `v_mcq` `v_m` on((`ma`.`question_id` = `v_m`.`id`)))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_questionnaires`
--

/*!50001 DROP VIEW IF EXISTS `v_questionnaires`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `v_questionnaires` AS (select `q`.`id` AS `id`,`v`.`level_name` AS `level_name` from (`questionnaires` `q` join `v_levels` `v` on((`q`.`level` = `v`.`id`)))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-04-10 21:57:42
