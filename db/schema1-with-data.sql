-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: church_website
-- ------------------------------------------------------
-- Server version	8.4.2

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
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address` (
  `address_id` int NOT NULL AUTO_INCREMENT,
  `street` varchar(255) NOT NULL,
  `building_no` varchar(20) DEFAULT NULL,
  `house_no` varchar(20) DEFAULT NULL,
  `city` varchar(255) NOT NULL,
  `state` varchar(100) DEFAULT NULL,
  `country` varchar(100) NOT NULL,
  `post_code` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`address_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (1,'53 Strathmore Avenue','','','Leeds','West Yorkshire','United Kingdom','LS9 6AY'),(2,'53 Strathmore Avenue','','','Leeds','West Yorkshire','United Kingdom','LS9 6AY'),(3,'53 Strathmore Avenue','','','Leeds','West Yorkshire','United Kingdom','LS9 6AY'),(4,'243A','','','Birmingham','West Midlands','United Kingdom','B16 8UZ'),(5,'ARMLEY MANOR MISTRESS LANE','','','Black – African','','United Kingdom','LS12 2HL'),(6,'243A','','','Birmingham','West Midlands','United Kingdom','B16 8UZ'),(7,'243A','','','Birmingham','West Midlands','United Kingdom','B16 8UZ'),(8,'243A','','','Birmingham','West Midlands','United Kingdom','B16 8UZ'),(9,'ARMLEY MANOR MISTRESS LANE','','','Black – African','','United Kingdom','LS12 2HL'),(10,'243A','','','Birmingham','West Midlands','United Kingdom','B16 8UZ'),(11,'ARMLEY MANOR MISTRESS LANE','','','Black – African','','United Kingdom','LS12 2HL'),(12,'ARMLEY MANOR MISTRESS LANE','','','Black – African','','United Kingdom','LS12 2HL'),(14,'ARMLEY MANOR MISTRESS LANE','','','Black – African','','United Kingdom','LS12 2HL');
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `album`
--

DROP TABLE IF EXISTS `album`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `album` (
  `album_id` int NOT NULL AUTO_INCREMENT,
  `album_name` varchar(255) NOT NULL,
  `album_description` text,
  `creation_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `is_archived` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`album_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `album`
--

LOCK TABLES `album` WRITE;
/*!40000 ALTER TABLE `album` DISABLE KEYS */;
INSERT INTO `album` VALUES (1,'Blog','Files for blogs post','2024-12-08 20:07:45',0),(2,'News','Files for News','2024-12-08 20:07:45',0),(3,'Information','Files for Information','2024-12-08 20:07:45',0),(4,'General','General files','2024-12-08 20:07:45',0),(5,'ALBUM 1','ALBUM 1 ALBUM 1 ALBUM 1','2024-12-10 23:46:01',0),(6,'Album 2','Album 2','2024-12-16 22:24:54',0),(7,'Brand New Album','Brand New Album','2024-12-16 22:51:00',0);
/*!40000 ALTER TABLE `album` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attachment`
--

DROP TABLE IF EXISTS `attachment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attachment` (
  `attachment_id` int NOT NULL AUTO_INCREMENT,
  `attachment_name` varchar(100) DEFAULT NULL,
  `attachment_path` varchar(255) NOT NULL,
  `attachment_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `attachment_type_id` int NOT NULL,
  `uploaded_by` int DEFAULT NULL,
  `is_archived` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`attachment_id`),
  KEY `attachment_type_id` (`attachment_type_id`),
  KEY `uploaded_by` (`uploaded_by`),
  CONSTRAINT `attachment_ibfk_1` FOREIGN KEY (`attachment_type_id`) REFERENCES `attachment_type` (`attachment_type_id`),
  CONSTRAINT `attachment_ibfk_2` FOREIGN KEY (`uploaded_by`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attachment`
--

LOCK TABLES `attachment` WRITE;
/*!40000 ALTER TABLE `attachment` DISABLE KEYS */;
INSERT INTO `attachment` VALUES (1,'Cover Letter','/media/attachments/ce37d4f3-dea8-4411-b948-b6957eabe71c_Cover-letter.pdf','2024-12-12 02:42:45',1,1,0),(2,'My CV','/media/attachments/25acd05b-dab0-49bb-bcf2-706b6b995e94_CV.pdf','2024-12-18 01:06:23',1,1,0);
/*!40000 ALTER TABLE `attachment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attachment_type`
--

DROP TABLE IF EXISTS `attachment_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attachment_type` (
  `attachment_type_id` int NOT NULL AUTO_INCREMENT,
  `attachment_type_name` varchar(255) NOT NULL,
  `attachment_type_description` text,
  PRIMARY KEY (`attachment_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attachment_type`
--

LOCK TABLES `attachment_type` WRITE;
/*!40000 ALTER TABLE `attachment_type` DISABLE KEYS */;
INSERT INTO `attachment_type` VALUES (1,'PDF','PDF files'),(2,'Word','Voice Only files'),(3,'Other','Other files, such as other text files, executables, etc.');
/*!40000 ALTER TABLE `attachment_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `baptisim`
--

DROP TABLE IF EXISTS `baptisim`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `baptisim` (
  `request_id` int NOT NULL AUTO_INCREMENT,
  `child_father_fullname` varchar(100) NOT NULL,
  `child_mother_fullname` varchar(100) NOT NULL,
  `child_fullname` varchar(100) NOT NULL,
  `child_god_parent_fullname` varchar(100) NOT NULL,
  `child_dob` datetime DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `phone_number` varchar(20) NOT NULL,
  `address_id` int NOT NULL,
  `required_service` varchar(50) DEFAULT 'BAPTISM',
  `service_status` varchar(30) DEFAULT 'REQUEST',
  `request_date` date DEFAULT NULL,
  `message` text,
  PRIMARY KEY (`request_id`),
  UNIQUE KEY `child_father_fullname` (`child_father_fullname`),
  UNIQUE KEY `child_mother_fullname` (`child_mother_fullname`),
  UNIQUE KEY `child_fullname` (`child_fullname`),
  UNIQUE KEY `child_god_parent_fullname` (`child_god_parent_fullname`),
  KEY `address_id` (`address_id`),
  CONSTRAINT `baptisim_ibfk_1` FOREIGN KEY (`address_id`) REFERENCES `address` (`address_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `baptisim`
--

LOCK TABLES `baptisim` WRITE;
/*!40000 ALTER TABLE `baptisim` DISABLE KEYS */;
/*!40000 ALTER TABLE `baptisim` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `blog`
--

DROP TABLE IF EXISTS `blog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `blog` (
  `blog_id` int NOT NULL AUTO_INCREMENT,
  `blog_title` varchar(255) NOT NULL,
  `blog_text` longtext NOT NULL,
  `blog_time` datetime DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '1',
  `is_archived` tinyint(1) DEFAULT '0',
  `blog_category_id` int NOT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`blog_id`),
  KEY `blog_category_id` (`blog_category_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `blog_ibfk_1` FOREIGN KEY (`blog_category_id`) REFERENCES `blog_category` (`blog_category_id`),
  CONSTRAINT `blog_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blog`
--

LOCK TABLES `blog` WRITE;
/*!40000 ALTER TABLE `blog` DISABLE KEYS */;
INSERT INTO `blog` VALUES (1,'What is a blog? Definition, types, benefits and why you need one','<div data-breakout=\"normal\">\r\n<div id=\"viewer-ejq7j\" class=\"zVy-A\">\r\n<div class=\"Fq2UF _37nZt\">\r\n<figure class=\"_50Z1v\">\r\n<div class=\"jVVLO\" tabindex=\"0\" role=\"button\" data-hook=\"imageViewer\" aria-haspopup=\"true\">\r\n<div id=\"ejq7j\" class=\"bgiSs Q91Ne S9iZU\"><img draggable=\"false\" src=\"https://static.wixstatic.com/media/84b06e_d4fd1bcc50d24af0984e62d07dcbcef1~mv2.png/v1/fill/w_925,h_529,al_c,q_90,usm_0.66_1.00_0.01,enc_auto/84b06e_d4fd1bcc50d24af0984e62d07dcbcef1~mv2.png\" alt=\"blog post showing what is a blog with best lime daiquiri recipes\" data-pin-url=\"https://www.wix.com/blog/what-is-a-blog\" data-pin-media=\"https://static.wixstatic.com/media/84b06e_d4fd1bcc50d24af0984e62d07dcbcef1~mv2.png/v1/fill/w_1064,h_608,al_c,q_90/84b06e_d4fd1bcc50d24af0984e62d07dcbcef1~mv2.png\" data-ssr-src-done=\"true\" data-load-done=\"\"></div>\r\n</div>\r\n</figure>\r\n</div>\r\n</div>\r\n</div>\r\n<div data-breakout=\"normal\">&nbsp;</div>\r\n<div data-breakout=\"normal\">\r\n<p id=\"viewer-eav4c\" class=\"Is4xI aaZkV rIsue QMtOy\" dir=\"auto\" style=\"text-align: center; padding-left: 80px;\"><span class=\"vsfWl\">With over<a class=\"CUBeV _2xrqk\" href=\"https://growthbadger.com/blog-stats/\" target=\"_blank\" rel=\"noopener\" data-hook=\"WebLink\"> 600 million blogs</a> on the internet, you&rsquo;ve likely encountered one or two blogs&mdash;you&rsquo;re even on one right now. But you may still wonder what exactly is a blog? How does it differ from a website? Why does every business seem to have one? You may even ask yourself, how can I start my own blog?</span></p>\r\n</div>\r\n<div style=\"text-align: center; padding-left: 80px;\" data-breakout=\"normal\">&nbsp;</div>\r\n<div style=\"padding-left: 80px;\" data-breakout=\"normal\">\r\n<p id=\"viewer-cbd3t\" class=\"Is4xI aaZkV rIsue QMtOy\" dir=\"auto\" style=\"text-align: center; padding-left: 80px;\"><span class=\"vsfWl\">In short, many individuals and businesses<a class=\"CUBeV _2xrqk\" href=\"https://www.wix.com/start/blog\" target=\"_blank\" rel=\"noopener\" data-hook=\"WebLink\"> create a blog</a> to share their ideas and expertise as well as boost their online presence. This article will answer your most pressing blogging questions and help you understand how and why blogs succeed, plus show how you can utilize them.</span></p>\r\n</div>\r\n<div data-breakout=\"normal\">&nbsp;</div>\r\n<div data-breakout=\"normal\">\r\n<div id=\"viewer-2ubjt32395\" class=\"zVy-A\">\r\n<div class=\"Fq2UF _37nZt\">\r\n<figure class=\"_50Z1v\">\r\n<div class=\"jVVLO\" tabindex=\"0\" role=\"button\" data-hook=\"imageViewer\" aria-haspopup=\"true\">\r\n<div id=\"2ubjt32395\" class=\"bgiSs Q91Ne S9iZU\"><img draggable=\"false\" src=\"https://static.wixstatic.com/media/4b9d2b_2c368161aa454e99912d895d1d2baa70~mv2.png/v1/fill/w_925,h_640,al_c,q_90,usm_0.66_1.00_0.01,enc_auto/4b9d2b_2c368161aa454e99912d895d1d2baa70~mv2.png\" alt=\"wix make a blog \" data-pin-url=\"https://www.wix.com/blog/what-is-a-blog\" data-pin-media=\"https://static.wixstatic.com/media/4b9d2b_2c368161aa454e99912d895d1d2baa70~mv2.png/v1/fill/w_1386,h_959,al_c,q_90/4b9d2b_2c368161aa454e99912d895d1d2baa70~mv2.png\" data-ssr-src-done=\"true\" data-load-done=\"\"></div>\r\n</div>\r\n</figure>\r\n</div>\r\n</div>\r\n</div>\r\n<div data-breakout=\"normal\">&nbsp;</div>\r\n<div style=\"padding-left: 80px;\" data-breakout=\"normal\">&nbsp;</div>\r\n<div style=\"padding-left: 80px;\" data-breakout=\"normal\">\r\n<p id=\"viewer-2sbv7\" class=\"Is4xI aaZkV rIsue QMtOy\" dir=\"auto\" style=\"padding-left: 80px;\"><span class=\"vsfWl\">Starting a blog should feel exciting, not overwhelming, right? With the Wix Blog Maker, you get everything you need to create, design and grow your blog from scratch&ndash;without needing a tech degree. Use Wix&rsquo;s intuitive tools to share your ideas with the world.&nbsp;</span></p>\r\n</div>\r\n<div style=\"padding-left: 80px;\" data-breakout=\"normal\">\r\n<h2 id=\"viewer-cm92b\" class=\"_6Aw8R NfA7j rIsue QMtOy\" dir=\"auto\" style=\"padding-left: 80px;\"><span class=\"KfHkb\">What is a blog?</span></h2>\r\n</div>\r\n<div style=\"padding-left: 80px;\" data-breakout=\"normal\">\r\n<p id=\"viewer-as242\" class=\"Is4xI aaZkV rIsue QMtOy\" dir=\"auto\" style=\"padding-left: 80px;\"><span class=\"vsfWl\">Blogs are a <a class=\"CUBeV _2xrqk\" href=\"https://www.wix.com/blog/types-of-websites\" target=\"_blank\" rel=\"noopener\" data-hook=\"WebLink\">type of regularly updated websites</a> that provide insight into a certain topic. The word blog is a combined version of the words &ldquo;web&rdquo; and &ldquo;log.&rdquo; At their inception, blogs were simply an online diary where people could keep a log about their daily lives on the web. They have since morphed into an essential forum for individuals and businesses alike to share information and updates. In fact, many people even<a class=\"CUBeV _2xrqk\" href=\"https://www.wix.com/blog/how-to-make-money-blogging\" target=\"_blank\" rel=\"noopener\" data-hook=\"WebLink\"> make money blogging</a> as professional full-time bloggers.&nbsp;</span></p>\r\n</div>\r\n<div style=\"padding-left: 80px;\" data-breakout=\"normal\">&nbsp;</div>\r\n<div style=\"padding-left: 80px;\" data-breakout=\"normal\">\r\n<p id=\"viewer-93s0c\" class=\"Is4xI aaZkV rIsue QMtOy\" dir=\"auto\" style=\"padding-left: 80px;\"><span class=\"vsfWl\">As the publishing world has evolved, and more of it has moved online, blogs have come to occupy a central position in this digital content world. Blogs are a source of knowledge, opinion and concrete advice. While not yet posed to replace journalism as an art form, people increasingly look to trusted blogs to find answers to their questions, or to learn how to do something.&nbsp;</span></p>\r\n</div>\r\n<div style=\"padding-left: 80px;\" data-breakout=\"normal\">&nbsp;</div>\r\n<div style=\"padding-left: 80px;\" data-breakout=\"normal\">\r\n<p id=\"viewer-9cvd9\" class=\"Is4xI aaZkV rIsue QMtOy\" dir=\"auto\" style=\"padding-left: 80px;\"><span class=\"vsfWl\">Blogs are always evolving both in terms of how they\'re created and what they are used for. They can be a vehicle for creativity and for marketing. They\'re also increasingly created and read on mobile apps, as mobile blogging also comes into its own. </span></p>\r\n</div>','2024-12-09 23:16:00',1,0,1,1),(2,'የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ታሪክ ','<h3><strong>የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ታሪክ&nbsp;</strong></h3>\r\n<h3>ከመጽሐፍ ቅዱስ ምንጮች እንደምንረዳው የክርስትና ሃይማኖት ወደ ኢትዮጵያ ከመግባቱ በፊት የአይሁድ እምነት ገብቶ ለረጅም ዘመናት ቆይቷል፡፡ ከመረጃ ምንጮችም አንዱ &ldquo;ኢትዮጵያ እጆቿን ወደ እግዚአብሔር ትዘረጋለችን&rdquo; የሚለው ጥቅስ ነው/መዝ 67:31/</h3>\r\n<h3>ስለ ኢትዮጵያና ስለ ኢትዮጵያውያን የሚናገሩ ሌሎች መረጃዎች በመጽሐፍ ቅዱስ በብዙ ቦታዎች ይገኛሉ:: እነዚህም በብሉይ ኪዳን ዘመን ኢትዮጵያውያን የእስራኤል አምላክ የሆነውን አንድ እግዚአብሔርን ያመልኩ እንደነበረ ያስረዳል::&nbsp;ከዚህም በተጨማሪ ኢትዮጵያዊቷ ንግሥተ ሳባ የንጉሥ ሰሎሞንን ጥበብ ለመረዳት ወደ ኢየሩሳሌም መሄዷና የእግዚአብሔር የቃል ኪዳን ታቦት ወደ ኢትዮጵያ መምጣት ኢትዮጵያ ከ982 ቅደመ ልደተ ክርስቶስ ጀምሮ የብሉይ ኪዳንን እምነት ተቀብላ እንደ ነበር በግልጽ ያሳያሉ:: /1ኛ ነገሥት 10:1-9/የብሉይ ኪዳን እምነት ወደ ኢትዮጵያ መግባት ከኢየሩሳሌም ውጭ መስፋፋት እንደ ጀመረ ለክርስትና ሃይማኖት ወደ ኢትዮጵያ መግባት አመቺ ሁኔታን ፈጥሯል፡፡</h3>\r\n<h3>በሠላሳ አራት /34/ ዓመተ ምሕረት ለአምልኮ ወደ ኢየሩሳሌም ሄዶ የነበረውና በወንጌላዊው ፊልጶስ የተጠመቀው የኢትዮጵያ ንግሥት ሕንደኬ የገንዘብ ሚኒስትር ክርስትናን ለኢትዮጵያ አስተዋወቀ /የሐ.ሥ. 8:26-40/:: ይሁንና በመንበረ ጵጵስና ደረጃ የቤተ ክርስቲያን መመሥረትና ሥጋ ወደሙን ማቀበል የተጀመረው በ4ኛው መቶ ክፍለ ዘመን ነው፡፡ ይኸውም ፍሬምናጦስ በእስክንድርያው ፓትርያርክ አትናቴዎስ የመጀመሪያው የኢትዮጵያ ጳጳስ ሆኖ የተሾመበት 33ዐ ዓመተ ምሕረት ነበር፡፡ ፍሬምናጦስም ወንጌልን መስበክ ሲጀምር ወደ ክርስትና ሃይማኖት የመለሳቸው የመጀመሪያቹ አማኞች ያንጊዜ መንበረ መንግሥታቸውን በአክሱም አድርገው ኢትዮጵያን ያስተዳድሩ የነበሩት አብርሃ ወአጽብሐ ነበሩ፡፡ በመሆኑም የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን የእስክንድርያን ቤተክርስቲያን ፈለግ በመከተል የአፍሪካ ቀንድ ሀገራት የኦርቶዶክስ እምነት ማዕከል ሆነች:: የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን የኬልቄዶንን ጉባኤ ከማይቀበሉት የኦሪየንታል ኦርቶዶክስ አቢያተ ክርስቲያናት አንዷ እንደመሆኗ</h3>','2024-12-10 01:13:14',1,0,1,1),(3,'የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ታሪክ ','<p>የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ታሪክ የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ታሪክ የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ታሪክ የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ታሪክ የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ታሪክ የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ታሪክ የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ታሪክ የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ታሪክ የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ታሪክ የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ታሪክ የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ታሪክ የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ታሪክ የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ታሪክ የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ታሪክ&nbsp;</p>\r\n<p>የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ታሪክ የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ታሪክ የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ታሪክ የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ታሪክ የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ታሪክ የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ታሪክ የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ታሪክ የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ታሪክ የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ታሪክ የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ታሪክ የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ታሪክ የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ታሪክ የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ታሪክ የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ታሪክ&nbsp;</p>\r\n<p>የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ታሪክ የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ታሪክ የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ታሪክ የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ታሪክ የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ታሪክ የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ታሪክ የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ታሪክ የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ታሪክ የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ታሪክ የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ታሪክ የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ታሪክ የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ታሪክ የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ታሪክ የኢትዮጵያ ኦርቶዶክስ ተዋሕዶ ቤተ ክርስቲያን ታሪክ&nbsp;</p>','2024-12-11 19:04:39',1,0,2,1);
/*!40000 ALTER TABLE `blog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `blog_category`
--

DROP TABLE IF EXISTS `blog_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `blog_category` (
  `blog_category_id` int NOT NULL AUTO_INCREMENT,
  `blog_category_name` varchar(255) NOT NULL,
  `blog_category_description` text,
  PRIMARY KEY (`blog_category_id`),
  UNIQUE KEY `blog_category_name` (`blog_category_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blog_category`
--

LOCK TABLES `blog_category` WRITE;
/*!40000 ALTER TABLE `blog_category` DISABLE KEYS */;
INSERT INTO `blog_category` VALUES (1,'Spritual Education','<p>My Spiritual Education</p>'),(2,'My second blog','<p><br></p>');
/*!40000 ALTER TABLE `blog_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `blog_comment`
--

DROP TABLE IF EXISTS `blog_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `blog_comment` (
  `comment_id` int NOT NULL AUTO_INCREMENT,
  `comment` mediumtext NOT NULL,
  `comment_time` datetime NOT NULL,
  `blog_id` int NOT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `blog_id` (`blog_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `blog_comment_ibfk_1` FOREIGN KEY (`blog_id`) REFERENCES `blog` (`blog_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `blog_comment_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blog_comment`
--

LOCK TABLES `blog_comment` WRITE;
/*!40000 ALTER TABLE `blog_comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `blog_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `blog_comment_reply`
--

DROP TABLE IF EXISTS `blog_comment_reply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `blog_comment_reply` (
  `reply_id` int NOT NULL AUTO_INCREMENT,
  `blog_comment_id` int NOT NULL,
  `comment_reply_id` int NOT NULL,
  `user_id` int DEFAULT NULL,
  `reply_text` mediumtext NOT NULL,
  `reply_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`reply_id`),
  KEY `blog_comment_id` (`blog_comment_id`),
  KEY `comment_reply_id` (`comment_reply_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `blog_comment_reply_ibfk_1` FOREIGN KEY (`blog_comment_id`) REFERENCES `blog_comment` (`comment_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `blog_comment_reply_ibfk_2` FOREIGN KEY (`comment_reply_id`) REFERENCES `blog_comment_reply` (`reply_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `blog_comment_reply_ibfk_3` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blog_comment_reply`
--

LOCK TABLES `blog_comment_reply` WRITE;
/*!40000 ALTER TABLE `blog_comment_reply` DISABLE KEYS */;
/*!40000 ALTER TABLE `blog_comment_reply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `church`
--

DROP TABLE IF EXISTS `church`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `church` (
  `church_id` int NOT NULL AUTO_INCREMENT,
  `church_name` varchar(255) NOT NULL,
  `church_description` text,
  `church_logo` varchar(255) DEFAULT NULL,
  `whatsApp` varchar(100) DEFAULT NULL,
  `telegram` varchar(100) DEFAULT NULL,
  `youtube` varchar(255) DEFAULT NULL,
  `facebook` varchar(255) DEFAULT NULL,
  `tiktok` varchar(100) DEFAULT NULL,
  `xAccount` varchar(100) DEFAULT NULL,
  `googlePlus` varchar(100) DEFAULT NULL,
  `pInterest` varchar(100) DEFAULT NULL,
  `linkedIn` varchar(100) DEFAULT NULL,
  `reddit` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `address_id` int NOT NULL,
  PRIMARY KEY (`church_id`),
  KEY `address_id` (`address_id`),
  CONSTRAINT `church_ibfk_1` FOREIGN KEY (`address_id`) REFERENCES `address` (`address_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `church`
--

LOCK TABLES `church` WRITE;
/*!40000 ALTER TABLE `church` DISABLE KEYS */;
INSERT INTO `church` VALUES (1,'ደብረ ስብሐት መድኋኔአለም በተክርስቲያን','ደብረ ስብሐት መድኋኔአለም በተክርስቲያን','/media/logo/e02f70d2-6864-451c-afb3-55ce522893c2_botb.JPG','','','','','','','','','','','',3);
/*!40000 ALTER TABLE `church` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `church_banner`
--

DROP TABLE IF EXISTS `church_banner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `church_banner` (
  `church_banner_id` int NOT NULL AUTO_INCREMENT,
  `church_banner` varchar(255) NOT NULL,
  `church_id` int NOT NULL,
  PRIMARY KEY (`church_banner_id`),
  KEY `church_id` (`church_id`),
  CONSTRAINT `church_banner_ibfk_1` FOREIGN KEY (`church_id`) REFERENCES `church` (`church_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `church_banner`
--

LOCK TABLES `church_banner` WRITE;
/*!40000 ALTER TABLE `church_banner` DISABLE KEYS */;
INSERT INTO `church_banner` VALUES (2,'/media/banners/7f9ab7fe-036f-4532-be54-5b4e084be4fb_banner2.jpg',1),(4,'/media/banners/b2acb904-bb65-4f22-92cb-a905aa0a092b_1111.JPG',1),(5,'/media/banners/7a7d0378-bbaf-4cf0-b39b-8c8253e3abf8_banner3.jpg',1);
/*!40000 ALTER TABLE `church_banner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `church_contact`
--

DROP TABLE IF EXISTS `church_contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `church_contact` (
  `contact_id` int NOT NULL AUTO_INCREMENT,
  `contact_full_name` varchar(255) DEFAULT NULL,
  `contact_phone` varchar(50) DEFAULT NULL,
  `contact_email` varchar(255) DEFAULT NULL,
  `contact_description` text,
  PRIMARY KEY (`contact_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `church_contact`
--

LOCK TABLES `church_contact` WRITE;
/*!40000 ALTER TABLE `church_contact` DISABLE KEYS */;
/*!40000 ALTER TABLE `church_contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `church_staff`
--

DROP TABLE IF EXISTS `church_staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `church_staff` (
  `church_staff_id` int NOT NULL AUTO_INCREMENT,
  `church_staff_title` varchar(255) NOT NULL,
  `church_staff_description` text,
  `church_id` int NOT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`church_staff_id`),
  KEY `church_id` (`church_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `church_staff_ibfk_1` FOREIGN KEY (`church_id`) REFERENCES `church` (`church_id`),
  CONSTRAINT `church_staff_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `church_staff`
--

LOCK TABLES `church_staff` WRITE;
/*!40000 ALTER TABLE `church_staff` DISABLE KEYS */;
/*!40000 ALTER TABLE `church_staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `donation`
--

DROP TABLE IF EXISTS `donation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `donation` (
  `donation_id` int NOT NULL AUTO_INCREMENT,
  `donation_amount` decimal(10,2) DEFAULT '0.00',
  `donation_time` datetime NOT NULL,
  `agreed_to_terms` tinyint(1) DEFAULT '0',
  `donation_payment_method_id` int NOT NULL,
  `donation_type_id` int NOT NULL,
  `user_id` int DEFAULT NULL,
  `donor_id` int DEFAULT NULL,
  PRIMARY KEY (`donation_id`),
  KEY `donation_type_id` (`donation_type_id`),
  KEY `user_id` (`user_id`),
  KEY `donor_id` (`donor_id`),
  KEY `donation_payment_method_id` (`donation_payment_method_id`),
  CONSTRAINT `donation_ibfk_1` FOREIGN KEY (`donation_type_id`) REFERENCES `donation_type` (`donation_type_id`),
  CONSTRAINT `donation_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `donation_ibfk_3` FOREIGN KEY (`donor_id`) REFERENCES `donor` (`donor_id`),
  CONSTRAINT `donation_ibfk_4` FOREIGN KEY (`donation_payment_method_id`) REFERENCES `payment_method` (`payment_method_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donation`
--

LOCK TABLES `donation` WRITE;
/*!40000 ALTER TABLE `donation` DISABLE KEYS */;
/*!40000 ALTER TABLE `donation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `donation_type`
--

DROP TABLE IF EXISTS `donation_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `donation_type` (
  `donation_type_id` int NOT NULL AUTO_INCREMENT,
  `donation_type_name` varchar(255) NOT NULL,
  `donation_type_description` text,
  PRIMARY KEY (`donation_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donation_type`
--

LOCK TABLES `donation_type` WRITE;
/*!40000 ALTER TABLE `donation_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `donation_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `donor`
--

DROP TABLE IF EXISTS `donor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `donor` (
  `donor_id` int NOT NULL AUTO_INCREMENT,
  `donor_first_name` varchar(100) DEFAULT NULL,
  `donor_last_name` varchar(100) DEFAULT NULL,
  `donor_email` varchar(100) DEFAULT NULL,
  `donor_address_id` int DEFAULT NULL,
  PRIMARY KEY (`donor_id`),
  KEY `donor_address_id` (`donor_address_id`),
  CONSTRAINT `donor_ibfk_1` FOREIGN KEY (`donor_address_id`) REFERENCES `address` (`address_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donor`
--

LOCK TABLES `donor` WRITE;
/*!40000 ALTER TABLE `donor` DISABLE KEYS */;
/*!40000 ALTER TABLE `donor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event` (
  `event_id` int NOT NULL AUTO_INCREMENT,
  `event_title` varchar(255) NOT NULL,
  `event_description` mediumtext,
  `event_date` date DEFAULT NULL,
  `event_time` time DEFAULT NULL,
  `is_featured` tinyint(1) DEFAULT '0',
  `is_active` tinyint(1) DEFAULT '1',
  `is_archived` tinyint(1) DEFAULT '0',
  `event_location_id` int NOT NULL,
  PRIMARY KEY (`event_id`),
  KEY `event_location_id` (`event_location_id`),
  CONSTRAINT `event_ibfk_1` FOREIGN KEY (`event_location_id`) REFERENCES `address` (`address_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event`
--

LOCK TABLES `event` WRITE;
/*!40000 ALTER TABLE `event` DISABLE KEYS */;
INSERT INTO `event` VALUES (1,'Sunday Mass','In Thymeleaf, the correct way to access query parameters is through the #param object, but the way you\'re using it might not be correct due to the context or missing parameters. The expression #param.blogCategory should work as long as the blogCategory parameter is present in the request.','2024-12-12','21:34:00',1,1,0,10),(2,'Saturday school','\r\nYou\'re correct! The issue may be related to how #param is being accessed in the Thymeleaf template. By default, #param is used to access query parameters, but in some contexts, it may not be working as expected. This could happen for several reasons, including improper use in Thymeleaf or the parameter not being passed correctly in the request.','2024-12-18','22:35:00',0,1,0,14);
/*!40000 ALTER TABLE `event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `holy_matrimony`
--

DROP TABLE IF EXISTS `holy_matrimony`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `holy_matrimony` (
  `request_id` int NOT NULL AUTO_INCREMENT,
  `groom_fullname` varchar(100) NOT NULL,
  `bride_fullname` varchar(100) NOT NULL,
  `spiritual_father_fullname` varchar(100) NOT NULL,
  `matrimony_date` date DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `phone_number` varchar(20) NOT NULL,
  `address_id` int NOT NULL,
  `required_service` varchar(50) DEFAULT 'MARRIAGE',
  `service_status` varchar(30) DEFAULT 'REQUEST',
  `request_date` datetime DEFAULT NULL,
  `message` text,
  PRIMARY KEY (`request_id`),
  UNIQUE KEY `groom_fullname` (`groom_fullname`),
  UNIQUE KEY `bride_fullname` (`bride_fullname`),
  UNIQUE KEY `spiritual_father_fullname` (`spiritual_father_fullname`),
  KEY `address_id` (`address_id`),
  CONSTRAINT `holy_matrimony_ibfk_1` FOREIGN KEY (`address_id`) REFERENCES `address` (`address_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `holy_matrimony`
--

LOCK TABLES `holy_matrimony` WRITE;
/*!40000 ALTER TABLE `holy_matrimony` DISABLE KEYS */;
/*!40000 ALTER TABLE `holy_matrimony` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `image`
--

DROP TABLE IF EXISTS `image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `image` (
  `image_id` int NOT NULL AUTO_INCREMENT,
  `image_name` varchar(100) DEFAULT NULL,
  `image_path` varchar(255) NOT NULL,
  `upload_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `album_id` int NOT NULL,
  `uploaded_by` int DEFAULT NULL,
  `is_archived` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`image_id`),
  KEY `album_id` (`album_id`),
  KEY `uploaded_by` (`uploaded_by`),
  CONSTRAINT `image_ibfk_1` FOREIGN KEY (`album_id`) REFERENCES `album` (`album_id`),
  CONSTRAINT `image_ibfk_2` FOREIGN KEY (`uploaded_by`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `image`
--

LOCK TABLES `image` WRITE;
/*!40000 ALTER TABLE `image` DISABLE KEYS */;
INSERT INTO `image` VALUES (2,'Holy Trinity','/media/centre/ce4774ba-2b7e-4991-a35f-66778c1e8318_Holy trinity.jpg','2024-12-16 22:23:35',5,1,0),(3,'Miscellaneous','/media/centre/44f68080-bffd-452c-afcc-9855950da56f_banner2.jpg','2024-12-16 22:24:54',6,1,0),(4,'Miscellaneous','/media/centre/09621035-c398-47b1-94c7-dec57224a0bd_Maryam.jpeg','2024-12-16 22:24:54',6,1,0),(5,'Holy Trinity','/media/centre/922a93cc-5911-4606-8b64-ad37f916f945_banner2.jpg','2024-12-16 22:25:14',5,1,0);
/*!40000 ALTER TABLE `image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `landing_content`
--

DROP TABLE IF EXISTS `landing_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `landing_content` (
  `landing_content_id` int NOT NULL AUTO_INCREMENT,
  `landing_content_title` varchar(255) NOT NULL,
  `content` mediumtext,
  `content_creation_time` datetime DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '1',
  `is_featured` tinyint(1) DEFAULT '0',
  `is_archived` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`landing_content_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `landing_content`
--

LOCK TABLES `landing_content` WRITE;
/*!40000 ALTER TABLE `landing_content` DISABLE KEYS */;
INSERT INTO `landing_content` VALUES (1,'','<h2 id=\"h1\" class=\"heading-nth-1\">Eastern Orthodox Church</h2>\r\n<h3>Orthodox Churches</h3>\r\n<p><span class=\"image_with_caption float_right\"><img src=\"https://www.bbc.co.uk/staticarchive/7fb75c5254d319ea225ffb5d11240a25fdb933df.jpg\" alt=\"Orthodox prayer rope\" width=\"170\" height=\"140\"><span class=\"caption\">Orthodox prayer rope&nbsp;<a href=\"https://www.bbc.co.uk/religion/copyright.shtml\">&copy;</a></span></span></p>\r\n<p>The Orthodox Church is one of the three main Christian groups (the others being Roman Catholic and Protestant). Around 200 million people follow the Orthodox tradition.</p>\r\n<p>It is made up of a number of self-governing Churches which are either \'autocephalous\' (meaning having their own head) or \'autonomous\' (meaning self-governing).</p>\r\n<p>The Orthodox Churches are united in faith and by a common approach to theology, tradition, and worship. They draw on elements of Greek, Middle-Eastern, Russian and Slav culture.</p>\r\n<p>Each Church has its own geographical (rather than a national) title that usually reflects the cultural traditions of its believers.</p>\r\n<p>The word \'Orthodox\' takes its meaning from the Greek words&nbsp;<em>orthos</em>&nbsp;(\'right\') and&nbsp;<em>doxa</em>&nbsp;(\'belief\'). Hence the word Orthodox means correct belief or right thinking.</p>\r\n<p>The Orthodox tradition developed from the Christianity of the Eastern Roman Empire and was shaped by the pressures, politics and peoples of that geographical area. Since the Eastern capital of the Roman Empire was Byzantium, this style of Christianity is sometimes called \'Byzantine Christianity\'.</p>\r\n<p>The Orthodox Churches share with the other Christian Churches the belief that God revealed himself in&nbsp;<a href=\"https://www.bbc.co.uk/religion/religions/christianity/history/jesus_1.shtml\">Jesus Christ</a>, and a belief in the incarnation of Christ, his crucifixion and resurrection. The Orthodox Church differs substantially from the other Churches in the way of life and worship, and in certain aspects of theology.</p>\r\n<p>The Holy Spirit is seen as present in and as the guide to the Church working through the whole body of the Church, as well as through priests and bishops.</p>\r\n<h3>Are Orthodox Churches the same as Eastern Orthodox Churches?</h3>\r\n<p>Not all Orthodox Churches are \'Eastern Orthodox\'. The \'Oriental Orthodox Churches\' have theological differences with the Eastern Orthodox and form a separate group, while a few Orthodox Churches are not \'in communion\' with the others.</p>\r\n<p>Not all Churches in the Eastern tradition are Orthodox - Eastern Churches that are not included in the Orthodox group include the Eastern Catholic Churches.</p>','2024-12-09 00:31:47',1,0,0),(2,'','<div class=\"flex-shrink-0 flex flex-col relative items-end\">\r\n<div>\r\n<div class=\"pt-0\">\r\n<div class=\"gizmo-bot-avatar flex h-8 w-8 items-center justify-center overflow-hidden rounded-full\">\r\n<div class=\"relative p-1 rounded-sm flex items-center justify-center bg-token-main-surface-primary text-token-text-primary h-8 w-8\">&nbsp;</div>\r\n</div>\r\n</div>\r\n</div>\r\n</div>\r\n<div class=\"group/conversation-turn relative flex w-full min-w-0 flex-col agent-turn\">\r\n<div class=\"flex-col gap-1 md:gap-3\">\r\n<div class=\"flex max-w-full flex-col flex-grow\">\r\n<div class=\"min-h-8 text-message flex w-full flex-col items-end gap-2 whitespace-normal break-words text-start [.text-message+&amp;]:mt-5\" dir=\"auto\" data-message-author-role=\"assistant\" data-message-id=\"2b1c7daa-37ee-4ffa-9839-6b5c25749ce4\" data-message-model-slug=\"gpt-4o\">\r\n<div class=\"flex w-full flex-col gap-1 empty:hidden first:pt-[3px]\">\r\n<div class=\"markdown prose w-full break-words dark:prose-invert light\">\r\n<p>Here&rsquo;s a professional and celebratory LinkedIn post for your achievement:</p>\r\n<hr>\r\n<p>? <strong>Milestone Achieved!</strong> ?</p>\r\n<p>I am thrilled to share that I have successfully completed my <strong>Master&rsquo;s degree in Advanced Computer Science</strong> from the <strong>University of Leeds</strong> with <strong>Distinction</strong>! ?</p>\r\n<p>This journey has been both challenging and rewarding, providing me with in-depth knowledge and advanced skills in cutting-edge areas of computer science. From exploring innovative technologies to collaborating with brilliant minds, every step has been an opportunity to grow both academically and personally.</p>\r\n<p>I am deeply grateful to my professors, peers, and family for their support throughout this journey. A special thanks to the <strong>University of Leeds</strong> for fostering an environment of excellence and innovation. ?</p>\r\n<p>Looking ahead, I am excited to leverage this achievement and contribute to the ever-evolving tech world, driving impactful solutions and meaningful change.</p>\r\n<p>#MasterDegree #ComputerScience #UniversityOfLeeds #Distinction #Achievement #TechInnovation #Gratitude #NewBeginnings</p>\r\n</div>\r\n</div>\r\n</div>\r\n</div>\r\n</div>\r\n</div>','2024-12-09 19:58:28',1,0,0);
/*!40000 ALTER TABLE `landing_content` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `membership`
--

DROP TABLE IF EXISTS `membership`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `membership` (
  `membership_id` int NOT NULL AUTO_INCREMENT,
  `membership_date` datetime NOT NULL,
  `contact_email` varchar(255) DEFAULT NULL,
  `contact_phone` varchar(20) DEFAULT NULL,
  `spouse_email` varchar(255) DEFAULT NULL,
  `spouse_phone` varchar(50) DEFAULT NULL,
  `spiritual_father_name` varchar(255) DEFAULT NULL,
  `spiritual_name` varchar(255) DEFAULT NULL,
  `spouse_spiritual_name` varchar(255) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '1',
  `membership_category_id` int NOT NULL,
  `user_id` int DEFAULT NULL,
  `spouse_user_id` int DEFAULT NULL,
  `address_id` int DEFAULT NULL,
  PRIMARY KEY (`membership_id`),
  KEY `membership_category_id` (`membership_category_id`),
  KEY `user_id` (`user_id`),
  KEY `spouse_user_id` (`spouse_user_id`),
  KEY `address_id` (`address_id`),
  CONSTRAINT `membership_ibfk_1` FOREIGN KEY (`membership_category_id`) REFERENCES `membership_category` (`membership_category_id`),
  CONSTRAINT `membership_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `membership_ibfk_3` FOREIGN KEY (`spouse_user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `membership_ibfk_4` FOREIGN KEY (`address_id`) REFERENCES `address` (`address_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `membership`
--

LOCK TABLES `membership` WRITE;
/*!40000 ALTER TABLE `membership` DISABLE KEYS */;
/*!40000 ALTER TABLE `membership` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `membership_category`
--

DROP TABLE IF EXISTS `membership_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `membership_category` (
  `membership_category_id` int NOT NULL AUTO_INCREMENT,
  `membership_name` varchar(255) NOT NULL,
  `membership_description` text,
  `membership_amount` decimal(15,2) DEFAULT '0.00',
  PRIMARY KEY (`membership_category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `membership_category`
--

LOCK TABLES `membership_category` WRITE;
/*!40000 ALTER TABLE `membership_category` DISABLE KEYS */;
/*!40000 ALTER TABLE `membership_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `message` (
  `message_id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `message` text NOT NULL,
  `message_time` datetime DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`message_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `message_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `news`
--

DROP TABLE IF EXISTS `news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `news` (
  `news_id` int NOT NULL AUTO_INCREMENT,
  `news_title` varchar(255) NOT NULL,
  `news_text` mediumtext,
  `news_post_time` datetime DEFAULT NULL,
  `is_featured` tinyint(1) DEFAULT '0',
  `is_active` tinyint(1) DEFAULT '1',
  `is_archived` tinyint(1) DEFAULT '0',
  `posted_by` int NOT NULL,
  PRIMARY KEY (`news_id`),
  KEY `posted_by` (`posted_by`),
  CONSTRAINT `news_ibfk_1` FOREIGN KEY (`posted_by`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `news`
--

LOCK TABLES `news` WRITE;
/*!40000 ALTER TABLE `news` DISABLE KEYS */;
INSERT INTO `news` VALUES (1,'Putin personally granted asylum to Syria’s Assad, Kremlin confirms','<p style=\"box-sizing: border-box; margin: 0px auto; max-width: var(--width-content-base); color: rgb(13, 15, 22); font-family: &quot;Martina Plantijn&quot;, &quot;Martina Plantijn fallback&quot;, serif; font-size: 20px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial;\">Russian President Vladimir Putin personally decided to grant asylum to Syrian dictator Bashar Assad and his family, Kremlin spokesperson Dmitry Peskov<span>&nbsp;</span><a href=\"https://tass.com/politics/1884473\" data-type=\"link\" data-id=\"https://tass.com/politics/1884473\" target=\"_blank\" style=\"box-sizing: border-box; color: var(--button-plain-text-color,var(--color-text-primary,inherit)); font-weight: var(--font-weight-medium); outline-color: var(--button-outline-color); outline-offset: 0px; outline-width: 0px; text-decoration-line: underline; text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: inherit; text-underline-offset: var(--wp--custom--stroke--medium); transition: color var(--transition-style-link-out),background-color var(--transition-style-link-out),text-decoration-color var(--transition-style-link-out),box-shadow var(--transition-style-button-out); margin-bottom: 0px;\">told</a><span>&nbsp;</span>reporters Monday.</p><p style=\"box-sizing: border-box; margin-top: var(--layout-flow-default); margin-right: auto; margin-bottom: 0px; margin-left: auto; max-width: var(--width-content-base); color: rgb(13, 15, 22); font-family: &quot;Martina Plantijn&quot;, &quot;Martina Plantijn fallback&quot;, serif; font-size: 20px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial;\"><br></p><p style=\"box-sizing: border-box; margin-top: var(--layout-flow-default); margin-right: auto; margin-bottom: 0px; margin-left: auto; max-width: var(--width-content-base); color: rgb(13, 15, 22); font-family: &quot;Martina Plantijn&quot;, &quot;Martina Plantijn fallback&quot;, serif; font-size: 20px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial;\">“Such decisions certainly cannot be made without the head of state. It was his decision,” Peskov said, while declining to officially comment on Assad’s whereabouts. Peskov said he had nothing further to add.</p><p style=\"box-sizing: border-box; margin-top: var(--layout-flow-default); margin-right: auto; margin-bottom: 0px; margin-left: auto; max-width: var(--width-content-base); color: rgb(13, 15, 22); font-family: &quot;Martina Plantijn&quot;, &quot;Martina Plantijn fallback&quot;, serif; font-size: 20px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial;\"><br></p><p style=\"box-sizing: border-box; margin-top: var(--layout-flow-default); margin-right: auto; margin-bottom: 0px; margin-left: auto; max-width: var(--width-content-base); color: rgb(13, 15, 22); font-family: &quot;Martina Plantijn&quot;, &quot;Martina Plantijn fallback&quot;, serif; font-size: 20px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial;\">Assad<span>&nbsp;</span><a href=\"https://www.politico.eu/article/bashar-assad-fled-syria-as-rebels-stormed-damascus-russia-says/\" style=\"box-sizing: border-box; color: var(--button-plain-text-color,var(--color-text-primary,inherit)); font-weight: var(--font-weight-medium); outline-color: var(--button-outline-color); outline-offset: 0px; outline-width: 0px; text-decoration-line: underline; text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: inherit; text-underline-offset: var(--wp--custom--stroke--medium); transition: color var(--transition-style-link-out),background-color var(--transition-style-link-out),text-decoration-color var(--transition-style-link-out),box-shadow var(--transition-style-button-out);\">fled to Moscow</a><span>&nbsp;</span>as Syrian opposition forces<span>&nbsp;</span><a href=\"https://www.politico.eu/article/syrian-rebel-forces-storm-into-damascus-claim-assad-has-fled/\" style=\"box-sizing: border-box; color: var(--button-plain-text-color,var(--color-text-primary,inherit)); font-weight: var(--font-weight-medium); outline-color: var(--button-outline-color); outline-offset: 0px; outline-width: 0px; text-decoration-line: underline; text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: inherit; text-underline-offset: var(--wp--custom--stroke--medium); transition: color var(--transition-style-link-out),background-color var(--transition-style-link-out),text-decoration-color var(--transition-style-link-out),box-shadow var(--transition-style-button-out);\">took control of the capital</a>, Russian state media reported Sunday, adding that he was<span>&nbsp;</span><a href=\"https://www.politico.eu/article/russia-asylum-bashar-assad-kremlin-syria-bloody-civil-war-middle-east-moscow/\" style=\"box-sizing: border-box; color: var(--button-plain-text-color,var(--color-text-primary,inherit)); font-weight: var(--font-weight-medium); outline-color: var(--button-outline-color); outline-offset: 0px; outline-width: 0px; text-decoration-line: underline; text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: inherit; text-underline-offset: var(--wp--custom--stroke--medium); transition: color var(--transition-style-link-out),background-color var(--transition-style-link-out),text-decoration-color var(--transition-style-link-out),box-shadow var(--transition-style-button-out); margin-bottom: 0px;\">granted asylum</a><span>&nbsp;</span>by Russia on humanitarian grounds.</p>','2024-12-09 21:04:37',0,1,0,1),(2,'UK and other European states suspend Syrians’ asylum applications','<p class=\"dcr-1eu361v\" style=\"box-sizing: border-box; margin: 0px 0px 0.75rem; padding: 0px; border: 0px; font-style: normal; font-variant-ligatures: common-ligatures; font-variant-caps: normal; font-variant-numeric: inherit; font-variant-east-asian: inherit; font-variant-alternates: inherit; font-variant-position: inherit; font-variant-emoji: inherit; font-weight: 400; font-stretch: inherit; font-size: 1.0625rem; line-height: 1.4; font-family: GuardianTextEgyptian, &quot;Guardian Text Egyptian Web&quot;, Georgia, serif; font-optical-sizing: inherit; font-size-adjust: inherit; font-kerning: inherit; font-feature-settings: inherit; font-variation-settings: inherit; vertical-align: baseline; word-break: break-word; --source-text-decoration-thickness: 2px; color: rgb(18, 18, 18); letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial;\">The UK and other European countries have said they will suspend the processing of asylum applications from Syrians after the fall of the Assad regime in Damascus, with<span>&nbsp;</span><a href=\"https://www.theguardian.com/world/austria\" data-link-name=\"in body link\" data-component=\"auto-linked-tag\" style=\"box-sizing: border-box; margin: 0px; padding: 0px; border-top: 0px; border-right: 0px; border-bottom: 1px solid var(--article-link-border); border-left: 0px; border-image: initial; font: inherit; vertical-align: baseline; text-decoration: none; color: var(--article-link-text);\">Austria</a><span>&nbsp;</span>already preparing a “repatriation and deportation” programme to the country.</p><p class=\"dcr-1eu361v\" style=\"box-sizing: border-box; margin: 0px 0px 0.75rem; padding: 0px; border: 0px; font-style: normal; font-variant-ligatures: common-ligatures; font-variant-caps: normal; font-variant-numeric: inherit; font-variant-east-asian: inherit; font-variant-alternates: inherit; font-variant-position: inherit; font-variant-emoji: inherit; font-weight: 400; font-stretch: inherit; font-size: 1.0625rem; line-height: 1.4; font-family: GuardianTextEgyptian, &quot;Guardian Text Egyptian Web&quot;, Georgia, serif; font-optical-sizing: inherit; font-size-adjust: inherit; font-kerning: inherit; font-feature-settings: inherit; font-variation-settings: inherit; vertical-align: baseline; word-break: break-word; --source-text-decoration-thickness: 2px; color: rgb(18, 18, 18); letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial;\">In London, a<span>&nbsp;</span><a href=\"https://www.theguardian.com/politics/home-office\" data-link-name=\"in body link\" data-component=\"auto-linked-tag\" style=\"box-sizing: border-box; margin: 0px; padding: 0px; border-top: 0px; border-right: 0px; border-bottom: 1px solid var(--article-link-border); border-left: 0px; border-image: initial; font: inherit; vertical-align: baseline; text-decoration: none; color: var(--article-link-text);\">Home Office</a><span>&nbsp;</span>spokesperson said it had “temporarily paused decisions on Syrian asylum claims whilst we assess the current situation”.</p><div id=\"sign-in-gate\" style=\"box-sizing: border-box; margin: 0px; padding: 0px; border: 0px; font-style: normal; font-variant-ligatures: common-ligatures; font-variant-caps: normal; font-variant-numeric: inherit; font-variant-east-asian: inherit; font-variant-alternates: inherit; font-variant-position: inherit; font-variant-emoji: inherit; font-weight: 400; font-stretch: inherit; font-size: medium; line-height: inherit; font-family: &quot;Times New Roman&quot;; font-optical-sizing: inherit; font-size-adjust: inherit; font-kerning: inherit; font-feature-settings: inherit; font-variation-settings: inherit; vertical-align: baseline; color: rgb(18, 18, 18); letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial;\"><gu-island name=\"SignInGateSelector\" priority=\"feature\" deferuntil=\"visible\" props=\"{&quot;contentType&quot;:&quot;Article&quot;,&quot;sectionId&quot;:&quot;uk-news&quot;,&quot;tags&quot;:[{&quot;id&quot;:&quot;uk/immigration&quot;,&quot;type&quot;:&quot;Keyword&quot;,&quot;title&quot;:&quot;Immigration and asylum&quot;},{&quot;id&quot;:&quot;uk/uk&quot;,&quot;type&quot;:&quot;Keyword&quot;,&quot;title&quot;:&quot;UK news&quot;},{&quot;id&quot;:&quot;world/syria&quot;,&quot;type&quot;:&quot;Keyword&quot;,&quot;title&quot;:&quot;Syria&quot;},{&quot;id&quot;:&quot;world/world&quot;,&quot;type&quot;:&quot;Keyword&quot;,&quot;title&quot;:&quot;World news&quot;},{&quot;id&quot;:&quot;world/austria&quot;,&quot;type&quot;:&quot;Keyword&quot;,&quot;title&quot;:&quot;Austria&quot;},{&quot;id&quot;:&quot;world/germany&quot;,&quot;type&quot;:&quot;Keyword&quot;,&quot;title&quot;:&quot;Germany&quot;},{&quot;id&quot;:&quot;world/middleeast&quot;,&quot;type&quot;:&quot;Keyword&quot;,&quot;title&quot;:&quot;Middle East and north Africa&quot;},{&quot;id&quot;:&quot;campaign/email/morning-briefing&quot;,&quot;type&quot;:&quot;Campaign&quot;,&quot;title&quot;:&quot;First Edition (newsletter signup)&quot;},{&quot;id&quot;:&quot;politics/home-office&quot;,&quot;type&quot;:&quot;Keyword&quot;,&quot;title&quot;:&quot;Home Office&quot;},{&quot;id&quot;:&quot;type/article&quot;,&quot;type&quot;:&quot;Type&quot;,&quot;title&quot;:&quot;Article&quot;},{&quot;id&quot;:&quot;tone/news&quot;,&quot;type&quot;:&quot;Tone&quot;,&quot;title&quot;:&quot;News&quot;},{&quot;id&quot;:&quot;profile/ashifa-kassam&quot;,&quot;type&quot;:&quot;Contributor&quot;,&quot;title&quot;:&quot;Ashifa Kassam&quot;,&quot;twitterHandle&quot;:&quot;ashifa_k&quot;,&quot;bylineImageUrl&quot;:&quot;https://i.guim.co.uk/img/uploads/2023/07/12/Ashifa_Kassam.jpg?width=300&amp;quality=85&amp;auto=format&amp;fit=max&amp;s=ab2d7a38473ca06b6315721d1631287d&quot;,&quot;bylineLargeImageUrl&quot;:&quot;https://i.guim.co.uk/img/uploads/2023/07/12/Ashifa_Kassam.png?width=300&amp;quality=85&amp;auto=format&amp;fit=max&amp;s=ff172f912f8e68b803e525b18b2437ae&quot;},{&quot;id&quot;:&quot;publication/theguardian&quot;,&quot;type&quot;:&quot;Publication&quot;,&quot;title&quot;:&quot;The Guardian&quot;},{&quot;id&quot;:&quot;theguardian/mainsection&quot;,&quot;type&quot;:&quot;NewspaperBook&quot;,&quot;title&quot;:&quot;Main section&quot;},{&quot;id&quot;:&quot;theguardian/mainsection/topstories&quot;,&quot;type&quot;:&quot;NewspaperBookSection&quot;,&quot;title&quot;:&quot;Top stories&quot;},{&quot;id&quot;:&quot;tracking/commissioningdesk/uk-foreign&quot;,&quot;type&quot;:&quot;Tracking&quot;,&quot;title&quot;:&quot;UK Foreign&quot;}],&quot;isPaidContent&quot;:false,&quot;isPreview&quot;:false,&quot;host&quot;:&quot;https://www.theguardian.com&quot;,&quot;pageId&quot;:&quot;uk-news/2024/dec/09/uk-and-other-european-states-suspend-syrians-asylum-applications&quot;,&quot;idUrl&quot;:&quot;https://profile.theguardian.com&quot;,&quot;switches&quot;:{&quot;externalVideoEmbeds&quot;:true,&quot;abSignInGateMainVariant&quot;:true,&quot;lightbox&quot;:true,&quot;abGpidPrebidAdUnits&quot;:true,&quot;prebidAppnexusUkRow&quot;:true,&quot;prebidMagnite&quot;:true,&quot;commercialMetrics&quot;:true,&quot;prebidTrustx&quot;:true,&quot;scAdFreeBanner&quot;:false,&quot;adaptiveSite&quot;:true,&quot;prebidPermutiveAudience&quot;:true,&quot;compareVariantDecision&quot;:false,&quot;enableSentryReporting&quot;:true,&quot;lazyLoadContainers&quot;:true,&quot;ampArticleSwitch&quot;:true,&quot;remarketing&quot;:true,&quot;articleEndSlot&quot;:true,&quot;keyEventsCarousel&quot;:true,&quot;registerWithPhone&quot;:false,&quot;darkModeWeb&quot;:true,&quot;targeting&quot;:true,&quot;remoteHeader&quot;:true,&quot;slotBodyEnd&quot;:true,&quot;prebidImproveDigitalSkins&quot;:false,&quot;ampPrebidOzone&quot;:true,&quot;extendedMostPopularFronts&quot;:true,&quot;emailInlineInFooter&quot;:true,&quot;showNewPrivacyWordingOnEmailSignupEmbeds&quot;:true,&quot;prebidAnalytics&quot;:true,&quot;extendedMostPopular&quot;:true,&quot;ampContentAbTesting&quot;:false,&quot;prebidCriteo&quot;:true,&quot;okta&quot;:true,&quot;imrWorldwide&quot;:true,&quot;acast&quot;:true,&quot;twitterUwt&quot;:true,&quot;prebidAppnexusInvcode&quot;:true,&quot;ampPrebidPubmatic&quot;:true,&quot;abUsaExpandableMarketingCard&quot;:true,&quot;a9HeaderBidding&quot;:true,&quot;prebidAppnexus&quot;:true,&quot;abOnwardsContentArticle&quot;:false,&quot;enableDiscussionSwitch&quot;:true,&quot;prebidXaxis&quot;:true,&quot;stickyVideos&quot;:true,&quot;interactiveFullHeaderSwitch&quot;:true,&quot;discussionAllPageSize&quot;:true,&quot;prebidUserSync&quot;:true,&quot;audioOnwardJourneySwitch&quot;:true,&quot;brazeTaylorReport&quot;:false,&quot;callouts&quot;:true,&quot;sentinelLogger&quot;:true,&quot;geoMostPopular&quot;:true,&quot;weAreHiring&quot;:false,&quot;relatedContent&quot;:true,&quot;thirdPartyEmbedTracking&quot;:true,&quot;prebidOzone&quot;:true,&quot;ampLiveblogSwitch&quot;:true,&quot;ampAmazon&quot;:true,&quot;prebidAdYouLike&quot;:true,&quot;mostViewedFronts&quot;:true,&quot;optOutAdvertising&quot;:true,&quot;abSignInGateMainControl&quot;:true,&quot;googleSearch&quot;:true,&quot;disableFrontContainerShowHide&quot;:true,&quot;abOptOutFrequencyCap&quot;:true,&quot;brazeSwitch&quot;:true,&quot;prebidKargo&quot;:true,&quot;consentManagement&quot;:true,&quot;personaliseSignInGateAfterCheckout&quot;:true,&quot;prebidSonobi&quot;:true,&quot;idProfileNavigation&quot;:true,&quot;confiantAdVerification&quot;:true,&quot;discussionAllowAnonymousRecommendsSwitch&quot;:false,&quot;absoluteServerTimes&quot;:false,&quot;permutive&quot;:true,&quot;comscore&quot;:true,&quot;ampPrebidCriteo&quot;:true,&quot;newsletterOnwards&quot;:false,&quot;youtubeIma&quot;:true,&quot;webFonts&quot;:true,&quot;liveBlogTopSponsorship&quot;:true,&quot;prebidImproveDigital&quot;:false,&quot;abAdBlockAsk&quot;:false,&quot;ophan&quot;:true,&quot;crosswordSvgThumbnails&quot;:true,&quot;prebidTriplelift&quot;:true,&quot;weather&quot;:true,&quot;prebidPubmatic&quot;:true,&quot;serverShareCounts&quot;:false,&quot;autoRefresh&quot;:true,&quot;enhanceTweets&quot;:true,&quot;prebidIndexExchange&quot;:true,&quot;prebidOpenx&quot;:true,&quot;prebidHeaderBidding&quot;:true,&quot;idCookieRefresh&quot;:true,&quot;discussionPageSize&quot;:true,&quot;smartAppBanner&quot;:false,&quot;historyTags&quot;:true,&quot;brazeContentCards&quot;:true,&quot;remoteBanner&quot;:true,&quot;emailSignupRecaptcha&quot;:true,&quot;prebidSmart&quot;:true,&quot;shouldLoadGoogletag&quot;:true,&quot;inizio&quot;:true,&quot;europeBetaFront&quot;:true}}\" data-island-status=\"hydrated\" style=\"box-sizing: border-box;\"></gu-island><br></div><p class=\"dcr-1eu361v\" style=\"box-sizing: border-box; margin: 0px 0px 0.75rem; padding: 0px; border: 0px; font-style: normal; font-variant-ligatures: common-ligatures; font-variant-caps: normal; font-variant-numeric: inherit; font-variant-east-asian: inherit; font-variant-alternates: inherit; font-variant-position: inherit; font-variant-emoji: inherit; font-weight: 400; font-stretch: inherit; font-size: 1.0625rem; line-height: 1.4; font-family: GuardianTextEgyptian, &quot;Guardian Text Egyptian Web&quot;, Georgia, serif; font-optical-sizing: inherit; font-size-adjust: inherit; font-kerning: inherit; font-feature-settings: inherit; font-variation-settings: inherit; vertical-align: baseline; word-break: break-word; --source-text-decoration-thickness: 2px; color: rgb(18, 18, 18); letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial;\">“We keep all country guidance relating to asylum claims under constant review so we can respond to emerging issues,” the spokesperson added.</p><p class=\"dcr-1eu361v\" style=\"box-sizing: border-box; margin: 0px 0px 0.75rem; padding: 0px; border: 0px; font-style: normal; font-variant-ligatures: common-ligatures; font-variant-caps: normal; font-variant-numeric: inherit; font-variant-east-asian: inherit; font-variant-alternates: inherit; font-variant-position: inherit; font-variant-emoji: inherit; font-weight: 400; font-stretch: inherit; font-size: 1.0625rem; line-height: 1.4; font-family: GuardianTextEgyptian, &quot;Guardian Text Egyptian Web&quot;, Georgia, serif; font-optical-sizing: inherit; font-size-adjust: inherit; font-kerning: inherit; font-feature-settings: inherit; font-variation-settings: inherit; vertical-align: baseline; word-break: break-word; --source-text-decoration-thickness: 2px; color: rgb(18, 18, 18); letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial;\">The moves come despite a lack of clarity over what lies ahead for<span>&nbsp;</span><a href=\"https://www.theguardian.com/world/syria\" data-link-name=\"in body link\" data-component=\"auto-linked-tag\" style=\"box-sizing: border-box; margin: 0px; padding: 0px; border-top: 0px; border-right: 0px; border-bottom: 1px solid var(--article-link-border); border-left: 0px; border-image: initial; font: inherit; vertical-align: baseline; text-decoration: none; color: var(--article-link-text);\">Syria</a>, one day after rebel forces seized the capital and the president fled to Russia.</p><p class=\"dcr-1eu361v\" style=\"box-sizing: border-box; margin: 0px 0px 0.75rem; padding: 0px; border: 0px; font-style: normal; font-variant-ligatures: common-ligatures; font-variant-caps: normal; font-variant-numeric: inherit; font-variant-east-asian: inherit; font-variant-alternates: inherit; font-variant-position: inherit; font-variant-emoji: inherit; font-weight: 400; font-stretch: inherit; font-size: 1.0625rem; line-height: 1.4; font-family: GuardianTextEgyptian, &quot;Guardian Text Egyptian Web&quot;, Georgia, serif; font-optical-sizing: inherit; font-size-adjust: inherit; font-kerning: inherit; font-feature-settings: inherit; font-variation-settings: inherit; vertical-align: baseline; word-break: break-word; --source-text-decoration-thickness: 2px; color: rgb(18, 18, 18); letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; white-space: normal; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial;\">Among the first in Europe to react was Germany, home to Europe’s largest Syrian diaspora after taking in<span>&nbsp;</span><a href=\"https://www.theguardian.com/world/2020/aug/30/angela-merkel-great-migrant-gamble-paid-off\" data-link-name=\"in body link\" style=\"box-sizing: border-box; margin: 0px; padding: 0px; border-top: 0px; border-right: 0px; border-bottom: 1px solid var(--article-link-border); border-left: 0px; border-image: initial; font: inherit; vertical-align: baseline; text-decoration: none; color: var(--article-link-text);\">nearly a million Syrians</a><span>&nbsp;</span>fleeing the country’s devastating war.</p>','2024-12-09 21:05:46',0,1,0,1);
/*!40000 ALTER TABLE `news` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment_method`
--

DROP TABLE IF EXISTS `payment_method`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment_method` (
  `payment_method_id` int NOT NULL AUTO_INCREMENT,
  `payment_method_name` varchar(255) NOT NULL,
  `payment_method_description` text,
  PRIMARY KEY (`payment_method_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_method`
--

LOCK TABLES `payment_method` WRITE;
/*!40000 ALTER TABLE `payment_method` DISABLE KEYS */;
INSERT INTO `payment_method` VALUES (1,'Credit card','Credit card paymnet'),(2,'PayPal','PayPal paymnet'),(3,'Offline','Offline paymnet via transfer');
/*!40000 ALTER TABLE `payment_method` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `remembrance_prayers`
--

DROP TABLE IF EXISTS `remembrance_prayers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `remembrance_prayers` (
  `request_id` int NOT NULL AUTO_INCREMENT,
  `requestor_fullname` varchar(100) NOT NULL,
  `christian_name_of_the_prayer_is_for` varchar(100) NOT NULL,
  `spiritual_father_fullname` varchar(100) NOT NULL,
  `prayer_for_date` date DEFAULT NULL,
  `request_date` datetime DEFAULT NULL,
  `message` text,
  PRIMARY KEY (`request_id`),
  UNIQUE KEY `requestor_fullname` (`requestor_fullname`),
  UNIQUE KEY `christian_name_of_the_prayer_is_for` (`christian_name_of_the_prayer_is_for`),
  UNIQUE KEY `spiritual_father_fullname` (`spiritual_father_fullname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `remembrance_prayers`
--

LOCK TABLES `remembrance_prayers` WRITE;
/*!40000 ALTER TABLE `remembrance_prayers` DISABLE KEYS */;
/*!40000 ALTER TABLE `remembrance_prayers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) NOT NULL,
  `role_description` text,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ROLE_ADMIN','Admin of the website'),(2,'ROLE_CLERGY','Clergy of the church'),(3,'ROLE_MEMBER','People attending the church');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service`
--

DROP TABLE IF EXISTS `service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service` (
  `service_id` int NOT NULL AUTO_INCREMENT,
  `service_name` varchar(255) NOT NULL,
  `service_description` text,
  PRIMARY KEY (`service_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service`
--

LOCK TABLES `service` WRITE;
/*!40000 ALTER TABLE `service` DISABLE KEYS */;
/*!40000 ALTER TABLE `service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `settings`
--

DROP TABLE IF EXISTS `settings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `settings` (
  `setting_id` int NOT NULL AUTO_INCREMENT,
  `setting_name` varchar(100) NOT NULL,
  `setting_description` text,
  `setting_value_int` int DEFAULT '0',
  `setting_value_char` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`setting_id`),
  UNIQUE KEY `setting_name` (`setting_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `settings`
--

LOCK TABLES `settings` WRITE;
/*!40000 ALTER TABLE `settings` DISABLE KEYS */;
INSERT INTO `settings` VALUES (1,'DEFAULT_PAGE_SIZE','The number of items listed in a page',8,NULL);
/*!40000 ALTER TABLE `settings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `user_profile_id` int DEFAULT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `registration_time` datetime DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '1',
  `is_blocked` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`),
  KEY `user_profile_id` (`user_profile_id`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`user_profile_id`) REFERENCES `user_profile` (`user_profile_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,NULL,'admin','$2y$10$zwsA0q6vRXXyyPeYZ/6HyOCCt.hpDdJreguANYy2hqZpW2WdAaQia','2024-12-08 00:00:00',1,0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_profile`
--

DROP TABLE IF EXISTS `user_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_profile` (
  `user_profile_id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(100) DEFAULT NULL,
  `last_name` varchar(100) DEFAULT NULL,
  `spiritual_father_name` varchar(100) DEFAULT NULL,
  `christian_name` varchar(100) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  `profile_photo` varchar(255) DEFAULT NULL,
  `description` mediumtext,
  PRIMARY KEY (`user_profile_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_profile`
--

LOCK TABLES `user_profile` WRITE;
/*!40000 ALTER TABLE `user_profile` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `user_role_id` int NOT NULL AUTO_INCREMENT,
  `role_id` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`user_role_id`),
  UNIQUE KEY `UK_user_role_1` (`role_id`,`user_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`),
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,1,1);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `video`
--

DROP TABLE IF EXISTS `video`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `video` (
  `video_id` int NOT NULL AUTO_INCREMENT,
  `video_name` varchar(100) DEFAULT NULL,
  `youtube_video_id` varchar(255) NOT NULL,
  `upload_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `uploaded_by` int DEFAULT NULL,
  `is_archived` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`video_id`),
  KEY `uploaded_by` (`uploaded_by`),
  CONSTRAINT `video_ibfk_1` FOREIGN KEY (`uploaded_by`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `video`
--

LOCK TABLES `video` WRITE;
/*!40000 ALTER TABLE `video` DISABLE KEYS */;
INSERT INTO `video` VALUES (1,'Mezmure 1','5QqraXsEpRc','2024-12-18 00:16:35',1,0),(2,'Anonymous','CqldQijqA-o','2024-12-18 00:17:25',1,0),(3,'Donkey tube','8dcxyyFlnQU','2024-12-18 00:17:47',1,0);
/*!40000 ALTER TABLE `video` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-18 19:35:52
