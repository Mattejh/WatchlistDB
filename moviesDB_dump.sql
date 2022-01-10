-- MariaDB dump 10.19  Distrib 10.6.5-MariaDB, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: MoviesDB
-- ------------------------------------------------------
-- Server version	10.6.5-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Cast`
--

DROP TABLE IF EXISTS `Cast`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Cast` (
  `cast_id` int(11) NOT NULL,
  `movie_id` int(20) NOT NULL,
  `actor` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `role_character` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `sex` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `cast_order` int(11) DEFAULT NULL,
  PRIMARY KEY (`cast_id`,`movie_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Cast`
--

LOCK TABLES `Cast` WRITE;
/*!40000 ALTER TABLE `Cast` DISABLE KEYS */;
/*!40000 ALTER TABLE `Cast` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Genre`
--

DROP TABLE IF EXISTS `Genre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Genre` (
  `genre_id` int(11) NOT NULL,
  `genre` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`genre_id`),
  UNIQUE KEY `genre_genre_id_uindex` (`genre_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Genre`
--

LOCK TABLES `Genre` WRITE;
/*!40000 ALTER TABLE `Genre` DISABLE KEYS */;
/*!40000 ALTER TABLE `Genre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Movie_genre`
--

DROP TABLE IF EXISTS `Movie_genre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Movie_genre` (
  `movie_id` int(20) NOT NULL,
  `genre_id` int(11) NOT NULL,
  KEY `movie_genre_genre_genre_id_fk` (`genre_id`),
  KEY `Movie_genre_Movies_id_fk` (`movie_id`),
  CONSTRAINT `Movie_genre_Movies_id_fk` FOREIGN KEY (`movie_id`) REFERENCES `Movies` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `movie_genre_genre_genre_id_fk` FOREIGN KEY (`genre_id`) REFERENCES `Genre` (`genre_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Movie_genre`
--

LOCK TABLES `Movie_genre` WRITE;
/*!40000 ALTER TABLE `Movie_genre` DISABLE KEYS */;
/*!40000 ALTER TABLE `Movie_genre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Movie_production`
--

DROP TABLE IF EXISTS `Movie_production`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Movie_production` (
  `movie_id` int(20) NOT NULL,
  `cast_id` int(11) NOT NULL,
  KEY `Movie_cast_Movies_id_fk` (`movie_id`),
  KEY `Movie_cast_Cast_cast_id_movie_id_fk` (`cast_id`,`movie_id`),
  CONSTRAINT `Movie_cast_Cast_cast_id_movie_id_fk` FOREIGN KEY (`cast_id`, `movie_id`) REFERENCES `Cast` (`cast_id`, `movie_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Movie_cast_Movies_id_fk` FOREIGN KEY (`movie_id`) REFERENCES `Movies` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Movie_production`
--

LOCK TABLES `Movie_production` WRITE;
/*!40000 ALTER TABLE `Movie_production` DISABLE KEYS */;
/*!40000 ALTER TABLE `Movie_production` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Movies`
--

DROP TABLE IF EXISTS `Movies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Movies` (
  `id` int(20) NOT NULL,
  `title` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `overview` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `vote_avg` decimal(4,2) DEFAULT NULL,
  `vote_count` int(11) DEFAULT NULL,
  `runtime` int(11) DEFAULT NULL,
  `release_date` date DEFAULT NULL,
  `url` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Movies`
--

LOCK TABLES `Movies` WRITE;
/*!40000 ALTER TABLE `Movies` DISABLE KEYS */;
/*!40000 ALTER TABLE `Movies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `password` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `User_name_uindex` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` (`user_id`, `password`, `name`) VALUES (22,'1234','Stina'),(23,'1234','Kent'),(24,'derp','Hasse'),(25,'1234','Lena');
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User_ratings`
--

DROP TABLE IF EXISTS `User_ratings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User_ratings` (
  `movie_id` int(11) NOT NULL,
  `rating` decimal(4,2) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`movie_id`),
  KEY `User_ratings_User_user_id_fk` (`user_id`),
  CONSTRAINT `User_ratings_User_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User_ratings`
--

LOCK TABLES `User_ratings` WRITE;
/*!40000 ALTER TABLE `User_ratings` DISABLE KEYS */;
/*!40000 ALTER TABLE `User_ratings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Watchlist`
--

DROP TABLE IF EXISTS `Watchlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Watchlist` (
  `user_id` int(11) NOT NULL,
  `movie_id` int(20) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`user_id`,`movie_id`),
  CONSTRAINT `Watchlist_User_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Watchlist`
--

LOCK TABLES `Watchlist` WRITE;
/*!40000 ALTER TABLE `Watchlist` DISABLE KEYS */;
/*!40000 ALTER TABLE `Watchlist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `castView`
--

DROP TABLE IF EXISTS `castView`;
/*!50001 DROP VIEW IF EXISTS `castView`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `castView` (
  `movie_id` tinyint NOT NULL,
  `actor` tinyint NOT NULL,
  `role_character` tinyint NOT NULL,
  `sex` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `watchlistView`
--

DROP TABLE IF EXISTS `watchlistView`;
/*!50001 DROP VIEW IF EXISTS `watchlistView`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `watchlistView` (
  `user_id` tinyint NOT NULL,
  `movie_id` tinyint NOT NULL,
  `title` tinyint NOT NULL,
  `runtime` tinyint NOT NULL,
  `release_date` tinyint NOT NULL,
  `timestamp` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `castView`
--

/*!50001 DROP TABLE IF EXISTS `castView`*/;
/*!50001 DROP VIEW IF EXISTS `castView`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_unicode_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `castView` AS select `Cast`.`movie_id` AS `movie_id`,`Cast`.`actor` AS `actor`,`Cast`.`role_character` AS `role_character`,`Cast`.`sex` AS `sex` from (`Cast` join `Movies` `M` on(`Cast`.`movie_id` = `M`.`id`)) order by `Cast`.`cast_order` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `watchlistView`
--

/*!50001 DROP TABLE IF EXISTS `watchlistView`*/;
/*!50001 DROP VIEW IF EXISTS `watchlistView`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_unicode_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `watchlistView` AS select `Watchlist`.`user_id` AS `user_id`,`Watchlist`.`movie_id` AS `movie_id`,`M`.`title` AS `title`,`M`.`runtime` AS `runtime`,`M`.`release_date` AS `release_date`,`Watchlist`.`timestamp` AS `timestamp` from (`Watchlist` join `Movies` `M` on(`Watchlist`.`movie_id` = `M`.`id`)) group by `Watchlist`.`timestamp` desc */;
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

-- Dump completed on 2022-01-10 21:12:06
