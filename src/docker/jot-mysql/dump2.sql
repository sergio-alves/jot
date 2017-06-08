CREATE DATABASE  IF NOT EXISTS `jot` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `jot`;
-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: dev2.utopix.ch    Database: jot
-- ------------------------------------------------------
-- Server version	5.6.33-0ubuntu0.14.04.1

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
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
) ENGINE=InnoDB AUTO_INCREMENT=232 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mcq`
--

LOCK TABLES `mcq` WRITE;
/*!40000 ALTER TABLE `mcq` DISABLE KEYS */;
INSERT INTO `mcq` VALUES (1,1,6,60,2),(2,12,18,60,3),(3,19,20,60,3),(4,27,32,60,2),(5,33,38,60,2),(6,39,44,60,2),(7,45,50,60,3),(8,51,56,60,3),(9,57,62,60,3),(10,63,68,60,3),(11,69,74,60,3),(12,75,80,60,3),(13,81,86,60,3),(14,87,92,60,3),(15,93,98,60,3),(16,99,104,60,3),(17,105,110,60,3),(18,111,116,60,3),(19,117,122,60,3),(20,123,128,60,3);
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
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mcq_answers`
--

LOCK TABLES `mcq_answers` WRITE;
/*!40000 ALTER TABLE `mcq_answers` DISABLE KEYS */;
INSERT INTO `mcq_answers` VALUES (1,1,2,0),(2,1,3,1),(3,1,4,0),(4,1,5,0),(5,2,13,0),(6,2,14,1),(7,2,15,0),(8,2,16,0),(9,2,17,0),(10,3,21,1),(11,3,22,0),(12,3,23,0),(13,3,24,0),(15,4,28,0),(16,4,29,1),(17,4,30,0),(18,4,31,0),(19,5,34,1),(20,5,35,0),(21,5,36,0),(22,5,37,0),(23,6,40,0),(24,6,41,0),(25,6,42,1),(26,6,43,0),(27,7,46,0),(28,7,47,0),(29,7,48,1),(30,7,49,0),(31,8,52,1),(32,8,53,0),(33,8,54,0),(34,8,55,0),(35,9,58,0),(36,9,59,0),(37,9,60,1),(38,9,61,0),(39,10,64,1),(40,10,65,0),(41,10,66,0),(42,10,67,0),(43,11,70,0),(44,11,71,0),(45,11,72,1),(46,11,73,0),(47,12,76,1),(48,12,77,0),(49,12,78,0),(50,12,79,0),(51,13,82,0),(52,13,83,1),(53,13,84,0),(54,13,85,0),(55,14,88,0),(56,14,89,0),(57,14,90,0),(58,14,91,1),(59,15,94,0),(60,15,95,0),(61,15,96,0),(62,15,97,1),(63,16,100,0),(64,16,101,0),(65,16,102,1),(66,16,103,0),(67,17,106,0),(68,17,107,0),(69,17,108,0),(70,17,109,1),(71,18,112,0),(72,18,113,1),(73,18,114,0),(74,18,115,0),(75,19,118,0),(76,19,119,0),(77,19,120,0),(78,19,121,1),(79,20,124,0),(80,20,125,0),(81,20,126,1),(82,20,127,0);
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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pre_registered_applications`
--

LOCK TABLES `pre_registered_applications` WRITE;
/*!40000 ALTER TABLE `pre_registered_applications` DISABLE KEYS */;
/*!40000 ALTER TABLE `pre_registered_applications` ENABLE KEYS */;
UNLOCK TABLES;

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
INSERT INTO `questions_in_questionnaires` VALUES (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),(1,10),(1,11),(1,12),(1,13),(1,14),(1,15),(1,16),(1,17),(1,18),(1,19),(1,20);
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
) ENGINE=InnoDB AUTO_INCREMENT=129 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resources`
--

LOCK TABLES `resources` WRITE;
/*!40000 ALTER TABLE `resources` DISABLE KEYS */;
INSERT INTO `resources` VALUES (1,'<span class=\"question\">Which four options describe the correct default values for array elements of the types indicated?</span>\n<ol class=\"java-ol-1234\">\n<li>int -&gt; 0</li>\n<li>String -&gt; \"null\"</li>\n<li>Dog -&gt; null</li>\n<li>char -&gt; \'\\u0000\'</li>\n<li>float -&gt; 0.0f</li>\n<li>boolean -&gt; true</li>\n</ol>'),(2,'<span class=\"answer\">A</span> 1, 2, 3, 4'),(3,'<span class=\"answer\">B</span> 1, 3, 4, 5'),(4,'<span class=\"answer\">C</span> 2, 4, 5, 6'),(5,'<span class=\"answer\">D</span> 3, 4, 5, 6'),(6,'<span class=\"explanation\">(1), (3), (4), (5) are the correct statements.<br /> (2) is wrong because the default value for a String (and any other object reference) is null, with no quotes.<br />(6) is wrong because the default value for boolean elements is false.</span>'),(7,'Beginner'),(8,'Junior'),(9,'Intermediate'),(10,'Senior'),(11,'Guru'),(12,'<span class=\"question\">Which one of these lists contains only Java programming language keywords?</span>'),(13,'<span class=\"answer\">A</span> class, if, void, long, Int, continue'),(14,'<span class=\"answer\">B</span> goto, instanceof, native, finally, default, throws'),(15,'<span class=\"answer\">C</span> try, virtual, throw, final, volatile, transient'),(16,'<span class=\"answer\">D</span> strictfp, constant, super, implements, do'),(17,'<span class=\"answer\">E</span> byte, break, assert, switch, include'),(18,'<span class=\"explanation\">All the words in option B are among the 49 Java keywords. Although goto reserved as a keyword in Java, goto is not used and has no function.</span>'),(19,'<span class=\"question\">\r\nWhich three are legal array declarations?\r\n<ol class=\"java-ol-1234\">\r\n<li>int [] myScores [];</li>\r\n<li>char [] myChars;</li>\r\n<li>int [6] myScores;</li>\r\n<li>Dog myDogs [];</li>\r\n<li>Dog myDogs [7];</li>\r\n</ol>\r\n</span>'),(20,'<span class=\"explanation\">(1), (2), and (4) are legal array declarations. With an array declaration, you can place the brackets to the right or left of the identifier. Option A looks strange, but it\'s perfectly legal to split the brackets in a multidimensional array, and place them on both sides of the identifier. Although coding this way would only annoy your fellow programmers, for the exam, you need to know it\'s legal.</p>\r <p>(3) and (5) are wrong because you can\'t declare an array with a size. The size is only needed when the array is actually instantiated (and the JVM needs to know how much space to allocate for the array, based on the type of array and the size).</span>'),(21,'<span class=\"answer\">A</span> 1, 2, 4'),(22,'<span class=\"answer\">B</span> 2, 4, 5'),(23,'<span class=\"answer\">C</span> 2, 3, 4'),(24,'<span class=\"answer\">D</span> All are correct.'),(25,'Fundamentals'),(26,'Java'),(27,'<span class=\"question\">Which specification that provides runtime environment in which java byte code can be executed?</span>'),(28,'<span class=\"answer\">A</span> JDK'),(29,'<span class=\"answer\">B</span> JVM'),(30,'<span class=\"answer\">C</span> JRE'),(31,'<span class=\"answer\">D</span> None of the above'),(32,'<span class=\"explanation\">It\'s B. The JDK and JRE contain an implementation while B is the specification.</span>'),(33,'<span class=\"question\">Which are used to produce a programming environment that supports the development of far more robust and scalable programs than does the process-oriented model?</span>'),(34,'<span class=\"answer\">A</span> Polymorphism, Encapsulation, and Inheritance'),(35,'<span class=\"answer\">B</span> Encapsulation and Inheritance'),(36,'<span class=\"answer\">C</span> Polymorphism and Encapsulation'),(37,'<span class=\"answer\">D</span> Inheritance and Polymorphism'),(38,'<span class=\"explanation\">The right answer is A. The others are only subsets of the answer A.</span>'),(39,'<span class=\"question\">Which is nothing but a blueprint or a template for creating different objects which defines its properties and behaviours?</span>'),(40,'<span class=\"answer\">A</span> An Array'),(41,'<span class=\"answer\">B</span> A class'),(42,'<span class=\"answer\">C</span> Interface'),(43,'<span class=\"answer\">D</span> None of the above'),(44,'<span class=\"explanation\">The right answer is C.</span>'),(45,'<span class=\"question\">Which data type has this Minimum and Maximum value 3.4e-038 to 3.4e+038</span>'),(46,'<span class=\"answer\">A</span> long'),(47,'<span class=\"answer\">B</span> int'),(48,'<span class=\"answer\">C</span> float'),(49,'<span class=\"answer\">D</span> double'),(50,'<span class=\"explanation\">The right answer is C. A and B are integers while question shows clearly a floating point number, and a double value spans between 1.7976931348623157E308 and  2.2250738585072014E-308;</span>'),(51,'<span class=\"question\">What output you will get if you run this program?\n<span class=\"java-ol-1234\">\nclass Modulus {\n	public static void main(String args[]) {\n	int x = 42;\n	double y = 42.25;\n	System.out.println(\"x mod 10 = \" + x % 10);\n	System.out.println(\"y mod 10 = \" + y % 10);\n	}\n}</span></span>'),(52,'<span class=\"answer\">A</span> x mod 10 = 2 and y mod 10 = 2.25'),(53,'<span class=\"answer\">B</span> x mod 10 = 4 and y mod 10 = 2.50'),(54,'<span class=\"answer\">C</span> x mod 10 = 6 and y mod 10 = 3.25'),(55,'<span class=\"answer\">D</span> x mod 10 = 2 and y mod 10 = 4.25'),(56,'<span class=\"explanation\">The right answer is the A.</span>'),(57,'<span class=\"question\">Which field cannot be changed after the object has been constructed?</span>'),(58,'<span class=\"answer\">A</span> Static field'),(59,'<span class=\"answer\">B</span> Non-static field'),(60,'<span class=\"answer\">C</span> Final field'),(61,'<span class=\"answer\">D</span> Naming field'),(62,'<span class=\"explanation\">The right answer if C.</span>'),(63,'<span class=\"question\">Which is a technique in Java in which a class can have any number of constructors that differ in parameter lists?</span>'),(64,'<span class=\"answer\">A</span> Constructor overloading'),(65,'<span class=\"answer\">B</span> Method overloading'),(66,'<span class=\"answer\">C</span> Operator overloading'),(67,'<span class=\"answer\">D</span> None of the above'),(68,'<span class=\"explanation\">The right answer is A.</span>'),(69,'<span class=\"question\">Which allows you define one interface and have multiple implementations?</span>'),(70,'<span class=\"answer\">A</span> Encapsulation'),(71,'<span class=\"answer\">B</span> Inheritance'),(72,'<span class=\"answer\">C</span> Polymorphism'),(73,'<span class=\"answer\">D</span> None of the above'),(74,'<span class=\"explanation\">The right answer is C.</span>'),(75,'<span class=\"question\">Which method cannot be overridden?</span>'),(76,'<span class=\"answer\">A</span> Final Method'),(77,'<span class=\"answer\">B</span> Final class'),(78,'<span class=\"answer\">C</span> Final Variable'),(79,'<span class=\"answer\">D</span> Both A & C'),(80,'<span class=\"explanation\">The right answer is A.</span>'),(81,'<span class=\"question\">Which extends the AbstractList class and implements List and Deque interfaces?</span>'),(82,'<span class=\"answer\">A</span> AbstractList'),(83,'<span class=\"answer\">B</span> LinkedList'),(84,'<span class=\"answer\">C</span> HashSet'),(85,'<span class=\"answer\">D</span> None of the above'),(86,'<span class=\"explanation\">The right answer is B.</span>'),(87,'<span class=\"question\">Which of the following aspects of a project can be managed using Maven?</span>'),(88,'<span class=\"answer\">A</span> Dependencies'),(89,'<span class=\"answer\">B</span> SCMs'),(90,'<span class=\"answer\">C</span> Releases'),(91,'<span class=\"answer\">D</span> All of them'),(92,'<span class=\"explanation\">The right answer is D. All of the above aspects of a project can be managed using Maven.</span>'),(93,'<span class=\"question\">Which of the following phase in maven life cycle takes the compiled code and package it in its distributable format, such as a JAR?</span>'),(94,'<span class=\"answer\">A</span> validate'),(95,'<span class=\"answer\">B</span> compile'),(96,'<span class=\"answer\">C</span> test'),(97,'<span class=\"answer\">D</span> package'),(98,'<span class=\"explanation\">The right answer is D. package phase takes the compiled code and package it in its distributable format, such as a JAR.</span>'),(99,'<span class=\"question\">Which of the following is correct about super POM?</span>'),(100,'<span class=\"answer\">A</span> Maven use the effective pom (configuration from super pom plus project configuration) to execute relevant goal.'),(101,'<span class=\"answer\">B</span> It helps developer to specify minimum configuration detail in his/her pom.xml. Although configurations can be overridden easily.'),(102,'<span class=\"answer\">C</span> Both of the above.'),(103,'<span class=\"answer\">D</span> None of the above.'),(104,'<span class=\"explanation\">The right answer is C. Maven use the effective pom (configuration from super pom plus project configuration) to execute relevant goal. It helps developer to specify minimum configuration detail in his/her pom.xml. Although configurations can be overridden easily.</span>'),(105,'<span class=\"question\">Which of the following features of UNIX may be used for inter process communication?</span>'),(106,'<span class=\"answer\">A</span> Signals.'),(107,'<span class=\"answer\">B</span> Pipes.'),(108,'<span class=\"answer\">C</span> Semaphore.'),(109,'<span class=\"answer\">D</span> All of these.'),(110,'<span class=\"explanation\">The right answer is D.</span>'),(111,'<span class=\"question\"> The system identifies the end of a file by the</span>'),(112,'<span class=\"answer\">A</span> EOF character'),(113,'<span class=\"answer\">B</span> file size'),(114,'<span class=\"answer\">C</span> i-node number'),(115,'<span class=\"answer\">D</span> Both (a) and (b)'),(116,'<span class=\"explanation\">The right answer is B.</span>'),(117,'<span class=\"question\"> Which of the following shell script\'s looping features does not recognize the break command?</span>'),(118,'<span class=\"answer\">A</span> while'),(119,'<span class=\"answer\">B</span> until'),(120,'<span class=\"answer\">C</span> for'),(121,'<span class=\"answer\">D</span> none of above'),(122,'<span class=\"explanation\">The right answer is D.</span>'),(123,'<span class=\"question\"> How can you ensure code quality?</span>'),(124,'<span class=\"answer\">A</span> Unit tests'),(125,'<span class=\"answer\">B</span> Integration tests'),(126,'<span class=\"answer\">C</span> Both unit and integration tests'),(127,'<span class=\"answer\">D</span> No tests, my code is 100% bulletproof'),(128,'<span class=\"explanation\">The right answer is C.</span>');
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='	';
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

-- Dump completed on 2017-06-08 12:11:20
