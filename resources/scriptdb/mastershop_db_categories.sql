-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: mastershop_db
-- ------------------------------------------------------
-- Server version	8.0.35

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
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `parent_id` bigint DEFAULT NULL,
  `thumbnail_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `url_key` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKgaa2nvscyupul5j6vt3xioeay` (`url_key`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Laptop',NULL,'https://cdn2.fptshop.com.vn/unsafe/180x0/filters:quality(100)/laptop_cate_thumb_f8bef6898b.png','laptop'),(2,'Điện thoại',NULL,'https://cdn2.fptshop.com.vn/unsafe/96x0/filters:quality(100)/phone_cate_0d84a35c3a.png','dien-thoai'),(3,'Máy tính bảng',NULL,'https://cdn2.fptshop.com.vn/unsafe/96x0/filters:quality(100)/may_tinh_bang_cate_thumb_00e3b3eefa.png','may-tinh-bang'),(4,'Đồng hồ thông minh',NULL,'https://cdn2.fptshop.com.vn/unsafe/96x0/filters:quality(100)/dong_ho_cate_thumb_fefdd822ba.png','dong-ho-thong-minh'),(5,'Tai nghe',NULL,'https://firebasestorage.googleapis.com/v0/b/lavenshop-c9862.appspot.com/o/category_images%2F1727045591630.webp?alt=media&token=331f0522-73eb-46ae-97d9-cbcbf74a902f','Tai-nghe'),(6,'Chuột máy tính',NULL,'https://firebasestorage.googleapis.com/v0/b/lavenshop-c9862.appspot.com/o/category_images%2F1728662197343.jpeg?alt=media&token=1190b78f-934c-4769-a2a8-e519f6d78dae','Chuột-máy-tính'),(7,'Màn hình',NULL,'https://cdn2.fptshop.com.vn/unsafe/96x0/filters:quality(100)/man_hinh_cate_thumb_f68d362f1e.png','man-hinh'),(8,'Cáp sạc',NULL,'https://cdn2.fptshop.com.vn/unsafe/180x0/filters:quality(100)/2022_11_22_638047060171322262_HASP-CAP-DEVIA-GRACIUOS-USBC-TO-LIGHTNING-1.5-DEN-AVT.jpg','cap-sac'),(9,'Củ sạc',NULL,'https://cdn2.fptshop.com.vn/unsafe/180x0/filters:quality(100)/2023_8_14_638276182011142335_MINI-CUBE-20W-DEN-AVT.jpg','cu-sac'),(10,'Loa',NULL,'https://cdn2.fptshop.com.vn/unsafe/180x0/filters:quality(100)/2023_2_27_638131130416847964_HASP-LOA-JBL-GO-ESSENTIAL-AVT.jpg','loa'),(11,'Bàn phím',NULL,'https://cdn2.fptshop.com.vn/unsafe/180x0/filters:quality(100)/2024_6_25_638549209335557184_logitech-pro-x-tkl-thumb.png','ban-phim'),(14,'Camera',NULL,'https://img.tgdd.vn/imgt/f_webp,fit_outside,quality_100/https://cdn.tgdd.vn//content/icon-camera-128x129.png','camera');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-16 19:11:51
