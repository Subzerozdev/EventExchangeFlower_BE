-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: eventflowerexchange
-- ------------------------------------------------------
-- Server version	8.0.38

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
-- Table structure for table `application`
--

DROP TABLE IF EXISTS `application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `application` (
  `id` int NOT NULL AUTO_INCREMENT,
  `problem` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  `user_id` char(36) DEFAULT NULL,
  `order_id` bigint DEFAULT NULL,
  `type_id` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `order_id` (`order_id`),
  CONSTRAINT `application_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `application_ibfk_2` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `application_chk_1` CHECK ((`type_id` between 0 and 2))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application`
--

LOCK TABLES `application` WRITE;
/*!40000 ALTER TABLE `application` DISABLE KEYS */;
/*!40000 ALTER TABLE `application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `application_type`
--

DROP TABLE IF EXISTS `application_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `application_type` (
  `id` int NOT NULL,
  `type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application_type`
--

LOCK TABLES `application_type` WRITE;
/*!40000 ALTER TABLE `application_type` DISABLE KEYS */;
INSERT INTO `application_type` VALUES (0,'Khiếu nại'),(1,'Hoàn tiền'),(2,'Hủy đơn hàng');
/*!40000 ALTER TABLE `application_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eventcategories`
--

DROP TABLE IF EXISTS `eventcategories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eventcategories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eventcategories`
--

LOCK TABLES `eventcategories` WRITE;
/*!40000 ALTER TABLE `eventcategories` DISABLE KEYS */;
INSERT INTO `eventcategories` VALUES (1,'Sự kiện đám cưới'),(2,'Sự kiện thôi nôi'),(3,'Sự kiện tân gia'),(4,'Sự kiện khánh thành'),(5,'Sự kiện khai trương');
/*!40000 ALTER TABLE `eventcategories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fee`
--

DROP TABLE IF EXISTS `fee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fee` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(255) DEFAULT NULL,
  `amount` float DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fee`
--

LOCK TABLES `fee` WRITE;
/*!40000 ALTER TABLE `fee` DISABLE KEYS */;
INSERT INTO `fee` VALUES (1,'Flatform Fee',0.1);
/*!40000 ALTER TABLE `fee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedback` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `rating` int NOT NULL,
  `customer_id` char(36) DEFAULT NULL,
  `shop_id` char(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `customer_id` (`customer_id`),
  KEY `shop_id` (`shop_id`),
  CONSTRAINT `feedback_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `users` (`id`),
  CONSTRAINT `feedback_ibfk_2` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback`
--

LOCK TABLES `feedback` WRITE;
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;
INSERT INTO `feedback` VALUES (1,'Hoa còn tươi và chất lượng',5,'3b86edc5-98b5-11ef-8472-2c6dc10e488b','794905cd-a652-11ef-be25-2c6dc10e488b');
/*!40000 ALTER TABLE `feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification` (
  `id` int NOT NULL,
  `type` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
INSERT INTO `notification` VALUES (0,'Lời nhắc'),(1,'Cảnh báo'),(2,'Thông tin');
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_details`
--

DROP TABLE IF EXISTS `order_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_details` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `total_money` bigint DEFAULT NULL,
  `order_id` bigint NOT NULL,
  `post_id` bigint NOT NULL,
  PRIMARY KEY (`id`,`order_id`,`post_id`),
  KEY `order_id` (`order_id`),
  KEY `post_id` (`post_id`),
  CONSTRAINT `order_details_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `order_details_ibfk_2` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`),
  CONSTRAINT `order_details_chk_1` CHECK ((`total_money` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_details`
--

LOCK TABLES `order_details` WRITE;
/*!40000 ALTER TABLE `order_details` DISABLE KEYS */;
INSERT INTO `order_details` VALUES (1,500000,1,8),(2,200000,2,1);
/*!40000 ALTER TABLE `order_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `full_name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT '',
  `order_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `total_money` float DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  `validation_image` varchar(255) DEFAULT NULL,
  `fee_id` int DEFAULT NULL,
  `user_id` char(36) DEFAULT NULL,
  `application_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKiq0687x0yfqwv8t2ghnunf7hx` (`application_id`),
  KEY `fee_id` (`fee_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `FK6gdbf5tlpsdk0nej1jqxdxcrm` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`fee_id`) REFERENCES `fee` (`id`),
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `orders_chk_1` CHECK ((`total_money` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'Người dùng 01','0909987675','user01@gmail.com','789 Phan Đăng Lưu, Phú Nhuận, TP.HCM','','2024-11-19 16:49:38',500000,1,NULL,1,'3b86edc5-98b5-11ef-8472-2c6dc10e488b',NULL),(2,'Người dùng 01','0909987675','user01@gmail.com','789 Phan Đăng Lưu, Phú Nhuận, TP.HCM','','2024-11-19 16:49:38',200000,2,'https://firebasestorage.googleapis.com/v0/b/swphoathanhly.appspot.com/o/l%E1%BA%B5ng%20hoa%20%C4%91%E1%BA%B9p(2).jpg?alt=media&token=9e39fbf2-6d20-4837-a264-9ac300706404',1,'3b86edc5-98b5-11ef-8472-2c6dc10e488b',NULL);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `otp_email`
--

DROP TABLE IF EXISTS `otp_email`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `otp_email` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `otp` bigint DEFAULT NULL,
  `expiry_date` datetime DEFAULT NULL,
  `user_id` char(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `otp_email_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `otp_email`
--

LOCK TABLES `otp_email` WRITE;
/*!40000 ALTER TABLE `otp_email` DISABLE KEYS */;
/*!40000 ALTER TABLE `otp_email` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post_images`
--

DROP TABLE IF EXISTS `post_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post_images` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `post_id` bigint DEFAULT NULL,
  `image_url` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_post_images_post_id` (`post_id`),
  CONSTRAINT `fk_post_images_post_id` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_images`
--

LOCK TABLES `post_images` WRITE;
/*!40000 ALTER TABLE `post_images` DISABLE KEYS */;
/*!40000 ALTER TABLE `post_images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posts`
--

DROP TABLE IF EXISTS `posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `posts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(350) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `thumbnail` varchar(300) DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `price` bigint DEFAULT NULL,
  `user_id` char(36) NOT NULL,
  `status` tinyint DEFAULT NULL,
  `category_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `posts_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `posts_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `eventcategories` (`id`),
  CONSTRAINT `posts_chk_1` CHECK ((`price` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts`
--

LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
INSERT INTO `posts` VALUES (1,'Bó hoa cưới cổ điển','Bó hoa cưới cổ điển với những bông hồng trắng tinh khôi và lá cây xanh tươi, mang đến một vẻ đẹp thanh lịch và sang trọng.','https://firebasestorage.googleapis.com/v0/b/swphoathanhly.appspot.com/o/trang-tri-ban-tho-gia-tien-1024x675.jpg?alt=media&token=686eb01a-d89c-48cd-959c-8d24590b7c3e','TP. Hồ Chí Minh, 234 Cách Mạng Tháng 8, Quận 10','2024-11-21 17:00:00','2024-11-23 17:00:00',200000,'5ab4992a-98b5-11ef-8472-2c6dc10e488b',3,1),(2,'Bó hoa cưới lãng mạn','Bó hoa cưới lãng mạn với những bông hoa hồng đỏ thắm và hoa baby\'s breath trắng tinh, tạo nên một không gian tràn đầy tình yêu và lãng mạn.','https://firebasestorage.googleapis.com/v0/b/swphoathanhly.appspot.com/o/cong-hoa-dam-cuoi-1-1623029834.jpg?alt=media&token=075f337b-fcf2-44a3-b122-1cf5bd67ef0d','TP. Hồ Chí Minh, 234 Cách Mạng Tháng 8, Quận 10','2024-11-29 17:00:00','2024-11-30 17:00:00',300000,'5ab4992a-98b5-11ef-8472-2c6dc10e488b',1,1),(3,'Bó hoa cưới hiện đại','Bó hoa cưới hiện đại với những bông hoa màu sắc tươi sáng và độc đáo, mang đến một vẻ đẹp trẻ trung và cá tính.','https://firebasestorage.googleapis.com/v0/b/swphoathanhly.appspot.com/o/OIP%20(6).jpg?alt=media&token=fd5d501d-3aff-4bc4-9aec-55e27a3e60cb','TP. Hồ Chí Minh, 234 Cách Mạng Tháng 8, Quận 10','2024-11-25 10:00:00','2024-11-29 10:00:00',400000,'5ab4992a-98b5-11ef-8472-2c6dc10e488b',2,1),(4,'Bộ hoa cưới boho','Bó hoa cưới boho với những bông hoa dại và lá cây xanh tươi, mang đến một vẻ đẹp tự nhiên và phóng khoáng.','https://firebasestorage.googleapis.com/v0/b/swphoathanhly.appspot.com/o/img07.jpg?alt=media&token=e9c6fc37-2749-42bb-b218-79a64e5c1b28','TP. Hồ Chí Minh, 234 Cách Mạng Tháng 8, Quận 10','2024-12-05 17:00:00','2024-12-07 17:00:00',500000,'5ab4992a-98b5-11ef-8472-2c6dc10e488b',1,1),(5,'Bộ hoa cưới mùa hè','Bó hoa cưới mùa hè với những bông hoa màu sắc tươi sáng và hoa quả tươi mát, mang đến một vẻ đẹp tươi vui và tràn đầy năng lượng.','https://firebasestorage.googleapis.com/v0/b/swphoathanhly.appspot.com/o/maya_hotel_wedding_12.jpg?alt=media&token=ff80e7e3-e4b4-46db-999f-48d7efdb4fc5','TP. Hồ Chí Minh, 234 Cách Mạng Tháng 8, Quận 10','2024-11-23 17:00:00','2024-11-28 17:00:00',600000,'5ab4992a-98b5-11ef-8472-2c6dc10e488b',0,1),(6,'Bộ hoa cưới mùa thu','Bộ hoa cưới mùa thu với những bông hoa màu sắc ấm áp và lá cây vàng đỏ, mang đến một vẻ đẹp lãng mạn và ấm áp.','https://firebasestorage.googleapis.com/v0/b/swphoathanhly.appspot.com/o/mau-rap-dam-cuoi-dep-moi-nhat-hien-nay%20(1).jpg?alt=media&token=2f68fcd0-c5ca-419c-aa96-b95ef474ff4f','TP. Hồ Chí Minh, 246 Trần Hưng Đạo, Quận 5','2024-12-01 17:00:00','2024-12-04 17:00:00',100000,'5ab548eb-98b5-11ef-8472-2c6dc10e488b',1,1),(7,'Bộ hoa cưới mùa đông','Bộ hoa cưới mùa đông với những bông hoa màu trắng và bạc, mang đến một vẻ đẹp tinh tế và sang trọng.','https://firebasestorage.googleapis.com/v0/b/swphoathanhly.appspot.com/o/5d3666b3e43d9f95af32ed6f47dacb28.jpg?alt=media&token=1d8f71ee-56d5-47ab-9b87-06d8d86afb32','TP. Hồ Chí Minh, 369 Võ Văn Tần, Quận 3','2024-11-27 17:00:00','2024-11-30 17:00:00',900000,'5ab548eb-98b5-11ef-8472-2c6dc10e488b',1,1),(8,'Bộ hoa cưới mùa xuân','Bộ hoa cưới mùa xuân với những bông hoa màu sắc tươi sáng và hoa lá xanh tươi, mang đến một vẻ đẹp tươi mới và tràn đầy hy vọng.','https://firebasestorage.googleapis.com/v0/b/swphoathanhly.appspot.com/o/860587293a2e605328c04abeb9e46f58.jpg?alt=media&token=3cfb247f-a37a-455a-8f9a-f05277f7fb43','TP. Hồ Chí Minh, 369 Võ Văn Tần, Quận 3','2024-12-05 17:00:00','2024-12-07 17:00:00',500000,'5ab548eb-98b5-11ef-8472-2c6dc10e488b',3,1);
/*!40000 ALTER TABLE `posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` tinyint NOT NULL,
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (0,'Admin'),(1,'Seller'),(2,'Customer');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shop`
--

DROP TABLE IF EXISTS `shop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shop` (
  `id` char(36) NOT NULL DEFAULT (uuid()),
  `description` varchar(255) DEFAULT NULL,
  `shop_address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `shop_name` varchar(255) DEFAULT NULL,
  `bank_number` varchar(255) DEFAULT NULL,
  `owner_bank` varchar(255) DEFAULT NULL,
  `bank_name` varchar(255) DEFAULT NULL,
  `user_id` char(36) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `shop_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shop`
--

LOCK TABLES `shop` WRITE;
/*!40000 ALTER TABLE `shop` DISABLE KEYS */;
INSERT INTO `shop` VALUES ('794905cd-a652-11ef-be25-2c6dc10e488b','Shop bán các bộ hoa sự kiện phục vụ khách hàng','234 Cách Mạng Tháng 8, Quận 10, TP.HCM','Cozy Shop','987654321','Trần Thị B','NCB','5ab4992a-98b5-11ef-8472-2c6dc10e488b'),('79577c2b-a652-11ef-be25-2c6dc10e488b','Shop bán các bộ hoa sự kiện phục vụ khách hàng','234 Cách Mạng Tháng 8, Quận 10, TP.HCM','Shop An Bình','567891234','Lê Văn C','NCB','5ab548eb-98b5-11ef-8472-2c6dc10e488b');
/*!40000 ALTER TABLE `shop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transactions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime DEFAULT NULL,
  `amount` float DEFAULT NULL,
  `from_id` char(36) DEFAULT NULL,
  `to_id` char(36) DEFAULT NULL,
  `order_id` bigint DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `from_id` (`from_id`),
  KEY `to_id` (`to_id`),
  KEY `order_id` (`order_id`),
  CONSTRAINT `transactions_ibfk_1` FOREIGN KEY (`from_id`) REFERENCES `users` (`id`),
  CONSTRAINT `transactions_ibfk_2` FOREIGN KEY (`to_id`) REFERENCES `users` (`id`),
  CONSTRAINT `transactions_ibfk_3` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `transactions_chk_1` CHECK ((`amount` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactions`
--

LOCK TABLES `transactions` WRITE;
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
INSERT INTO `transactions` VALUES (1,'2024-11-19 16:50:04',50000,'3b86edc5-98b5-11ef-8472-2c6dc10e488b','52e3c070-aaff-4430-949a-77b86cb46cdc',1,1,'CUSTOMER TO ADMIN'),(2,'2024-11-19 16:50:04',20000,'3b86edc5-98b5-11ef-8472-2c6dc10e488b','52e3c070-aaff-4430-949a-77b86cb46cdc',2,1,'CUSTOMER TO ADMIN'),(3,'2024-11-19 16:56:40',180000,'52e3c070-aaff-4430-949a-77b86cb46cdc','5ab4992a-98b5-11ef-8472-2c6dc10e488b',2,1,'ADMIN TO OWNER');
/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `typeandpost`
--

DROP TABLE IF EXISTS `typeandpost`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `typeandpost` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `type_id` bigint NOT NULL,
  `post_id` bigint NOT NULL,
  PRIMARY KEY (`id`,`type_id`,`post_id`),
  KEY `type_id` (`type_id`),
  KEY `post_id` (`post_id`),
  CONSTRAINT `typeandpost_ibfk_1` FOREIGN KEY (`type_id`) REFERENCES `types` (`id`),
  CONSTRAINT `typeandpost_ibfk_2` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `typeandpost`
--

LOCK TABLES `typeandpost` WRITE;
/*!40000 ALTER TABLE `typeandpost` DISABLE KEYS */;
INSERT INTO `typeandpost` VALUES (1,1,1),(2,1,2),(7,1,4),(13,1,6),(16,1,8),(18,1,5),(21,1,3),(8,2,4),(14,2,6),(17,2,8),(19,2,5),(9,3,4),(15,3,7),(22,3,3),(20,4,5),(23,4,3),(3,5,2);
/*!40000 ALTER TABLE `typeandpost` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `types`
--

DROP TABLE IF EXISTS `types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `types` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `types`
--

LOCK TABLES `types` WRITE;
/*!40000 ALTER TABLE `types` DISABLE KEYS */;
INSERT INTO `types` VALUES (1,'Hoa Hồng Đỏ'),(2,'Hoa Ly Trắng'),(3,'Hoa Cát Tường'),(4,'Hoa Thủy Tiên'),(5,'Hoa Bách Hợp');
/*!40000 ALTER TABLE `types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_notification`
--

DROP TABLE IF EXISTS `user_notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_notification` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `message` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `is_read` bit(1) DEFAULT NULL,
  `sender` varchar(255) DEFAULT NULL,
  `receiver_user` char(36) DEFAULT NULL,
  `notification_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `receiver_user` (`receiver_user`),
  KEY `notification_id` (`notification_id`),
  CONSTRAINT `user_notification_ibfk_1` FOREIGN KEY (`receiver_user`) REFERENCES `users` (`id`),
  CONSTRAINT `user_notification_ibfk_2` FOREIGN KEY (`notification_id`) REFERENCES `notification` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_notification`
--

LOCK TABLES `user_notification` WRITE;
/*!40000 ALTER TABLE `user_notification` DISABLE KEYS */;
INSERT INTO `user_notification` VALUES (1,'Bài đăng số 1 của bạn đã được duyệt và đang được đăng bán','2024-11-19 15:55:14',_binary '','System','5ab4992a-98b5-11ef-8472-2c6dc10e488b',2),(2,'Bài đăng số 2 của bạn đã được duyệt và đang được đăng bán','2024-11-19 16:16:41',_binary '','System','5ab4992a-98b5-11ef-8472-2c6dc10e488b',2),(3,'Bài đăng số 3 của bạn đã được duyệt và đang được đăng bán','2024-11-19 16:19:05',_binary '','System','5ab4992a-98b5-11ef-8472-2c6dc10e488b',2),(4,'Bài đăng số 4 của bạn đã được duyệt và đang được đăng bán','2024-11-19 16:19:07',_binary '','System','5ab4992a-98b5-11ef-8472-2c6dc10e488b',2),(5,'Bài đăng số 5 của bạn đã được duyệt và đang được đăng bán','2024-11-19 16:19:09',_binary '','System','5ab4992a-98b5-11ef-8472-2c6dc10e488b',2),(6,'Bài đăng số 6 của bạn đã được duyệt và đang được đăng bán','2024-11-19 16:24:44',_binary '\0','System','5ab548eb-98b5-11ef-8472-2c6dc10e488b',2),(7,'Bài đăng số 7 của bạn đã được duyệt và đang được đăng bán','2024-11-19 16:24:46',_binary '\0','System','5ab548eb-98b5-11ef-8472-2c6dc10e488b',2),(8,'Bài đăng số 8 của bạn đã được duyệt và đang được đăng bán','2024-11-19 16:26:13',_binary '\0','System','5ab548eb-98b5-11ef-8472-2c6dc10e488b',2),(9,'Bài đăng số 3 của bạn không được duyệt do vi phạm chính sách của trang web','2024-11-19 16:27:11',_binary '\0','System','5ab4992a-98b5-11ef-8472-2c6dc10e488b',2),(10,'Bạn đã thanh toán thành công cho đơn hàng 1. Đơn hàng sẽ được giao sau ngày kết thúc sự kiện của bài đăng','2024-11-19 16:50:04',_binary '\0','System','3b86edc5-98b5-11ef-8472-2c6dc10e488b',2),(11,'Bạn nhận được 1 đơn hàng mới từ người mua với mã đơn: 1','2024-11-19 16:50:04',_binary '\0','System','5ab548eb-98b5-11ef-8472-2c6dc10e488b',2),(12,'Bạn đã thanh toán thành công cho đơn hàng 2. Đơn hàng sẽ được giao sau ngày kết thúc sự kiện của bài đăng','2024-11-19 16:50:04',_binary '\0','System','3b86edc5-98b5-11ef-8472-2c6dc10e488b',2),(13,'Bạn nhận được 1 đơn hàng mới từ người mua với mã đơn: 2','2024-11-19 16:50:04',_binary '\0','System','5ab4992a-98b5-11ef-8472-2c6dc10e488b',2),(14,'Đơn hàng 2 của bạn đã giao thành công','2024-11-19 16:56:40',_binary '\0','System','3b86edc5-98b5-11ef-8472-2c6dc10e488b',2),(15,'Bạn nhận được 180000.0 cho đơn hàng với mã số 2','2024-11-19 17:02:07',_binary '\0','System','5ab4992a-98b5-11ef-8472-2c6dc10e488b',2);
/*!40000 ALTER TABLE `user_notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` char(36) NOT NULL DEFAULT (uuid()),
  `balance` float DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `is_active` bit(1) DEFAULT b'0',
  `role_id` tinyint DEFAULT '3',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `phone` (`phone`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('3b86edc5-98b5-11ef-8472-2c6dc10e488b',0,'user01@gmail.com','$2a$12$EQg.ZMSgAEBMiLHhVh.53e8D4/.Tay.HSyC0SPccFmO5tiu/SFrOi','Người dùng 01','0909987675','789 Phan Đăng Lưu, Phú Nhuận, TP.HCM','2024-11-02 07:43:19',NULL,_binary '',2),('52e3c070-aaff-4430-949a-77b86cb46cdc',70000,'hoaloicuofficial@gmail.com','$2a$10$qa.ZARMxr3dzE0T3iR6TkeYUOoBg8KRj80qf7JGslQseq/7IWtjq.','Admin',NULL,NULL,'2024-11-19 15:44:54',NULL,_binary '',0),('5ab23788-98b5-11ef-8472-2c6dc10e488b',0,'user02@gmail.com','$2a$12$sDz2DImPhdruzlwLAPahE.rOa7B9DIt4SZ8ltA47uANhpB6Yo6fWO','Người dùng 02','0909987671','890 Điện Biên Phủ, Bình Thạnh, TP.HCM','2024-11-02 07:43:19',NULL,_binary '',2),('5ab4992a-98b5-11ef-8472-2c6dc10e488b',180000,'seller01@gmail.com','$2a$12$Su72PtpQFrOKEcFqq1.5VeK5CwejEpRNHrmHL64j8r3MbFqUSEsxm','Người bán hàng 01','0909987672','789 Phan Đăng Lưu, Phú Nhuận, TP.HCM','2024-11-02 07:43:19',NULL,_binary '',1),('5ab548eb-98b5-11ef-8472-2c6dc10e488b',0,'seller02@gmail.com','$2a$12$POP2vZ4faMkKMrkFHeUij.yhY4Z0aePuAUbrKNg69FcUR3xCqT1bW','Người bán hàng 02','0909987673','456 Nguyễn Thị Minh Khai, Quận 3, TP.HCM','2024-11-02 07:43:19',NULL,_binary '',1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-21 10:09:24
