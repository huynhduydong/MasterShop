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
-- Table structure for table `product_options`
--

DROP TABLE IF EXISTS `product_options`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_options` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `value` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `product_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8vv4f8fru80wxocwgxwsrow61` (`product_id`),
  CONSTRAINT `FK8vv4f8fru80wxocwgxwsrow61` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=181 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_options`
--

LOCK TABLES `product_options` WRITE;
/*!40000 ALTER TABLE `product_options` DISABLE KEYS */;
INSERT INTO `product_options` VALUES (1,'Thương hiệu','Logitech',1),(2,'Xuất xứ thương hiệu','Thụy Sĩ',1),(3,'Kích thước','Chiều cao: 105,4 mm - Chiều rộng: 67,9 mm - Chiều dày: 38,4 mm',1),(4,'Độ phân giải quang học','1000DPI',1),(5,'Tính năng','Silent không tiếng ồn',1),(6,'Model','M331',1),(7,'Xuất xứ','Trung Quốc',1),(8,'Trọng lượng','0.2',1),(9,'Màu','đỏ',2),(10,'Dung lượng','64GB',2),(11,'Dung lượng','128GB',2),(12,'Dung lượng','256GB',2),(13,'Dung lượng','512GB',2),(14,'Màu','Xanh Dương',3),(15,'Màu','Đen',3),(16,'Màu','Bạc',3),(17,'Màu','Đen',4),(18,'Màu','Đen',4),(19,'Màu','Đen',4),(20,'Màu','Đen',5),(21,'Màu','Bạc',5),(22,'Màu','Xanh Dương',6),(23,'Màu','Đen',6),(24,'Màu','Bạc',6),(25,'Màu','Silver',9),(26,'Màu','Đen',12),(27,'Màu','Trắng',12),(28,'Màu','Hồng',12),(29,'Dung lượng','256GB',18),(30,'Dung lượng','512GB',18),(31,'Dung lượng','1TB',18),(32,'Màu','Titan xanh',18),(33,'Màu','Titan tự nhiên',18),(34,'Màu','Titan đen',18),(35,'Màu','Titan trắng',18),(36,'Dung lượng','256GB',19),(37,'Dung lượng','512GB',19),(38,'Dung lượng','1TB',19),(39,'Màu','Xám',19),(40,'Màu','Tím',19),(41,'Màu','Đen',19),(42,'Màu','Vàng',19),(43,'Màu','Đen',21),(44,'Màu','Xám',22),(45,'Màu','Bạc',22),(46,'Màu','Trắng',22),(47,'Màu','Đen',22),(48,'Màu','Xám',23),(49,'Màu','Trắng',24),(50,'Màu','Bạc',25),(51,'Màu','Đen',25),(52,'Màu','Titan Đen',26),(53,'Màu','Titan Trắng',26),(54,'Màu','Titan Xanh',26),(55,'Màu','Titan Tự Nhiên',26),(56,'Màu','Vàng',27),(57,'Màu','Xám',27),(58,'Màu','Đen',27),(59,'Màu','Tím',27),(60,'Màu','Trắng',28),(61,'Màu','Vàng',28),(62,'Màu','Đen',28),(63,'Màu','Bạc',28),(64,'Màu','Bạc',29),(65,'Màu','Đen',29),(66,'Màu','Trắng vàng',31),(67,'Màu','Tím',31),(68,'Màu','Hồng',31),(69,'Màu','Xám',31),(70,'Dung lượng','64GB',31),(71,'Dung lượng','128GB',31),(72,'Dung lượng','256GB',31),(73,'Màu','Trắng',32),(74,'Màu','Đen',32),(75,'Màu','Bạc',33),(76,'Màu','Đen',33),(77,'Màu','Bạc',34),(78,'Màu','Đen',34),(79,'Màu','Đỏ',35),(80,'Màu','Xanh dương',35),(81,'Màu','Đen',35),(82,'Màu','Camo',35),(83,'Màu','Xám',35),(84,'Màu','Hồng',35),(85,'Màu','Xanh ngọc',35),(86,'Màu','Xanh lá',35),(87,'Màu','Đen',36),(88,'Màu','Xám',36),(89,'Màu','Trắng',36),(90,'Màu','Xanh',36),(91,'Màu','Đen',37),(92,'Màu','Vàng',37),(93,'Màu','Trắng',37),(94,'Màu','Kem',39),(95,'Màu','Xám',39),(96,'Dung lượng','16GB 1TB',39),(97,'Dung lượng','12GB 512GB',39),(98,'Dung lượng','12GB 256GB',39),(99,'Màu','Cam',40),(100,'Màu','Xanh dương',40),(101,'Màu','Đen',40),(102,'Màu','Bạc',40),(103,'Màu','Xám',40),(104,'Màu','Bạc',3),(105,'Màu','Xanh Dương',3),(106,'Màu','Đen',3),(173,'Màu','Trắng ',6),(174,'Màu','Trắng',4),(175,'Màu','Xanh',4);
/*!40000 ALTER TABLE `product_options` ENABLE KEYS */;
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
