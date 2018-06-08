/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.5.28 : Database - dg-ai
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`dg-ai` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `dg-ai`;

/*Table structure for table `back_info` */

DROP TABLE IF EXISTS `back_info`;

CREATE TABLE `back_info` (
  `bid` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(200) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `source` varchar(200) DEFAULT NULL,
  `back_time` datetime DEFAULT NULL,
  `sub_id` bigint(20) DEFAULT NULL,
  `state` tinyint(4) DEFAULT NULL COMMENT '处理状态 0 表示未处理',
  `handle_time` datetime DEFAULT NULL COMMENT '定时任务处理时间',
  `user_id` varchar(32) DEFAULT NULL,
  `sub_type` int(11) DEFAULT NULL,
  `submit_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `round` int(11) DEFAULT NULL,
  `is_tort` int(11) DEFAULT NULL,
  `briefing` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`bid`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Data for the table `back_info` */

insert  into `back_info`(`bid`,`url`,`title`,`source`,`back_time`,`sub_id`,`state`,`handle_time`,`user_id`,`sub_type`,`submit_time`,`update_time`,`round`,`is_tort`,`briefing`) values (1,'1111','111','111111111','2016-12-04 21:41:31',1,1,'2016-12-05 21:12:00',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2,'www.baidu.com','wwcccc','ccccccccccccccccc','2016-12-04 22:03:48',2,1,'2016-12-05 21:12:00',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(3,'','','','2016-12-11 15:36:12',5,1,'2016-12-11 15:37:00',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(4,'','','','2016-12-11 15:47:47',8,1,'2016-12-11 15:48:00',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(5,'','','','2016-12-11 15:49:11',6,1,'2016-12-11 15:50:00',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(6,'','','','2016-12-11 15:50:10',4,1,'2016-12-11 15:51:26',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(7,'','','','2016-12-11 15:52:52',1,1,'2016-12-11 15:53:00',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(8,'','','','2016-12-11 15:53:33',18,1,'2016-12-11 15:54:09',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(9,'','','','2016-12-11 15:55:23',7,1,'2016-12-11 15:56:00',NULL,NULL,NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `cds` */

DROP TABLE IF EXISTS `cds`;

CREATE TABLE `cds` (
  `CD_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`CD_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `cds` */

/*Table structure for table `consumer` */

DROP TABLE IF EXISTS `consumer`;

CREATE TABLE `consumer` (
  `uid` bigint(20) NOT NULL AUTO_INCREMENT,
  `openid` char(50) DEFAULT NULL,
  `name` char(20) DEFAULT NULL,
  `password` char(50) DEFAULT NULL,
  `mob` char(11) DEFAULT NULL,
  `img` char(1) DEFAULT NULL,
  `code` bigint(20) DEFAULT NULL,
  `level` tinyint(4) DEFAULT NULL,
  `state` tinyint(1) DEFAULT NULL,
  `count` tinyint(5) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `consumer` */

insert  into `consumer`(`uid`,`openid`,`name`,`password`,`mob`,`img`,`code`,`level`,`state`,`count`) values (1,'1212','xxxx',NULL,'15603735436',NULL,NULL,NULL,NULL,0),(2,'1212','xxxx',NULL,'15603735436',NULL,0,0,0,1);

/*Table structure for table `database_params` */

DROP TABLE IF EXISTS `database_params`;

CREATE TABLE `database_params` (
  `DB_ID` bigint(20) NOT NULL,
  `PARAM_KEY` varchar(180) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `PARAM_VALUE` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`DB_ID`,`PARAM_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `database_params` */

/*Table structure for table `dbs` */

DROP TABLE IF EXISTS `dbs`;

CREATE TABLE `dbs` (
  `DB_ID` bigint(20) NOT NULL,
  `DESC` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `DB_LOCATION_URI` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `NAME` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `OWNER_NAME` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `OWNER_TYPE` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`DB_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `dbs` */

/*Table structure for table `order` */

DROP TABLE IF EXISTS `order`;

CREATE TABLE `order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `discount_price` float DEFAULT NULL,
  `describe` varchar(500) DEFAULT NULL,
  `yn` tinyint(1) DEFAULT NULL COMMENT 'yn=1表示有效',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `order_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=254 DEFAULT CHARSET=utf8;

/*Data for the table `order` */

insert  into `order`(`id`,`name`,`price`,`discount_price`,`describe`,`yn`,`create_time`,`update_time`,`count`,`order_type`) values (1,'zhangsan',1,0,'没有121112121惺惺惜惺惺111111111',0,'2016-12-10 22:33:50',NULL,NULL,NULL),(2,'11111111111111111',12,0,'12',0,'2016-12-10 22:34:10',NULL,NULL,NULL),(3,'订单名称过长订单名称过长订单名称过长订单名称过长订单名称过长',0,0,'11111111111111sssssssssss',0,'2016-12-10 22:34:12',NULL,NULL,NULL),(4,'zhangsan',1,0,'没有121112121惺惺惜惺惺111111111',0,'2016-12-10 22:34:17',NULL,NULL,NULL),(5,'11111111111111111',12,0,'121收到离开房间数量的空间福克斯劳动节放假乐山大佛',0,'2016-12-10 22:34:14',NULL,NULL,NULL),(6,'订单名称过长订单名称过长订单名称过长订单名称过长订单名称过长',0,0,'11111111111111sssssssssss',0,'2016-12-10 22:34:19',NULL,NULL,NULL),(7,'zhangsan',1,0,'没有121112121惺惺惜惺惺111111111',0,'2016-12-10 22:34:36',NULL,NULL,NULL),(8,'11111111111111111',12,0,'12',0,'2016-12-10 22:34:21',NULL,NULL,NULL),(9,'订单名称过长订单名称过长订单名称过长订单名称过长订单名称过长',0,0,'11111111111111sssssssssss',0,'2016-12-10 22:34:23',NULL,NULL,NULL),(10,'zhangsan',1,0,'没有121112121惺惺惜惺惺111111111',0,'2016-12-10 22:34:38',NULL,NULL,NULL),(11,'11111111111111111',12,0,'12',0,'2016-12-10 22:34:27',NULL,NULL,NULL),(12,'订单名称过长订单名称过长订单名称过长订单名称过长订单名称过长',0,0,'11111111111111sssssssssss',0,'2016-12-10 22:34:30',NULL,NULL,NULL),(14,'zhangsan',1,0,'没有121112121惺惺惜惺惺111111111',0,'2016-12-10 22:34:40',NULL,NULL,NULL),(15,'11111111111111111',12,0,'12',0,'2016-12-10 22:34:34',NULL,NULL,NULL),(16,'订单名称过长订单名称过长订单名称过长订单名称过长订单名称过长',0,0,'11111111111111sssssssssss',0,'2016-12-10 22:34:32',NULL,NULL,NULL),(17,'zhangsan',1,0,'没有121112121惺惺惜惺惺111111111',1,NULL,NULL,NULL,NULL),(18,'11111111111111111',12,0,'12',0,'2016-12-10 22:34:07',NULL,NULL,NULL),(19,'订单名称过长订单名称过长订单名称过长订单名称过长订单名称过长',0,0,'11111111111111sssssssssss',1,NULL,NULL,NULL,NULL),(20,'zhangsan',1,0,'没有121112121惺惺惜惺惺111111111',1,NULL,NULL,NULL,NULL),(23,'zhangsan',1,0,'没有121112121惺惺惜惺惺111111111',1,NULL,NULL,NULL,NULL),(25,'订单名称过长订单名称过长订单名称过长订单名称过长订单名称过长',0,0,'11111111111111sssssssssss',1,NULL,NULL,NULL,NULL),(31,'订单名称过长订单名称过长订单名称过长订单名称过长订单名称过长',0,0,'11111111111111sssssssssss',1,NULL,NULL,NULL,NULL),(35,'zhangsan',1,0,'没有121112121惺惺惜惺惺111111111',0,'2016-12-10 22:34:44',NULL,NULL,NULL),(40,'订单名称过长订单名称过长订单名称过长订单名称过长订单名称过长',0,0,'11111111111111sssssssssss',1,NULL,NULL,NULL,NULL),(45,'11111111111111111',12,12,'12',1,NULL,NULL,NULL,NULL),(49,'订单名称过长订单名称过长订单名称过长订单名称过长订单名称过长',0,0,'11111111111111sssssssssss',1,NULL,NULL,NULL,NULL),(50,'zhangsan',1,0,'没有121112121惺惺惜惺惺111111111',1,NULL,NULL,NULL,NULL),(51,'11111111111111111',12,12,'12',1,NULL,NULL,NULL,NULL),(71,'订单名称过长订单名称过长订单名称过长订单名称过长订单名称过长',0,0,'11111111111111sssssssssss',1,NULL,NULL,NULL,NULL),(75,'zhangsan',1,0,'没有121112121惺惺惜惺惺111111111',1,NULL,NULL,NULL,NULL),(86,'订单名称过长订单名称过长订单名称过长订单名称过长订单名称过长',0,0,'11111111111111sssssssssss',1,NULL,NULL,NULL,NULL),(87,'zhangsan',1,0,'没有121112121惺惺惜惺惺111111111',1,NULL,NULL,NULL,NULL),(88,'11111111111111111',12,12,'12',1,NULL,NULL,NULL,NULL),(89,'订单名称过长订单名称过长订单名称过长订单名称过长订单名称过长',0,0,'11111111111111sssssssssss',1,NULL,NULL,NULL,NULL),(252,'wwwwccccc',0,0,'wwwwccccc',1,'2016-12-10 22:38:31',NULL,NULL,NULL),(253,'不知道不知道',0,0,'121212',1,'2016-12-11 12:02:58','2016-12-11 12:02:58',NULL,NULL);

/*Table structure for table `organization` */

DROP TABLE IF EXISTS `organization`;

CREATE TABLE `organization` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `parent_id` varchar(10) NOT NULL COMMENT '父级编号',
  `name` varchar(30) NOT NULL COMMENT '机构名称',
  `master` varchar(30) DEFAULT NULL COMMENT '负责人',
  `phone` varchar(20) DEFAULT NULL COMMENT '负责人电话',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

/*Data for the table `organization` */

insert  into `organization`(`id`,`parent_id`,`name`,`master`,`phone`,`remark`,`del_flag`) values (1,'0','汝阳县',NULL,NULL,NULL,'0'),(20,'1','销售一部','','','','0'),(30,'1','销售二','','','','0'),(40,'20','国内','','','','0');

/*Table structure for table `part_col_stats` */

DROP TABLE IF EXISTS `part_col_stats`;

CREATE TABLE `part_col_stats` (
  `CS_ID` bigint(20) NOT NULL,
  `AVG_COL_LEN` double DEFAULT NULL,
  `COLUMN_NAME` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `COLUMN_TYPE` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `DB_NAME` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `BIG_DECIMAL_HIGH_VALUE` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `BIG_DECIMAL_LOW_VALUE` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_HIGH_VALUE` double DEFAULT NULL,
  `DOUBLE_LOW_VALUE` double DEFAULT NULL,
  `LAST_ANALYZED` bigint(20) NOT NULL,
  `LONG_HIGH_VALUE` bigint(20) DEFAULT NULL,
  `LONG_LOW_VALUE` bigint(20) DEFAULT NULL,
  `MAX_COL_LEN` bigint(20) DEFAULT NULL,
  `NUM_DISTINCTS` bigint(20) DEFAULT NULL,
  `NUM_FALSES` bigint(20) DEFAULT NULL,
  `NUM_NULLS` bigint(20) NOT NULL,
  `NUM_TRUES` bigint(20) DEFAULT NULL,
  `PART_ID` bigint(20) DEFAULT NULL,
  `PARTITION_NAME` varchar(767) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `TABLE_NAME` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`CS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `part_col_stats` */

/*Table structure for table `partition_key_vals` */

DROP TABLE IF EXISTS `partition_key_vals`;

CREATE TABLE `partition_key_vals` (
  `PART_ID` bigint(20) NOT NULL,
  `PART_KEY_VAL` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `INTEGER_IDX` int(11) NOT NULL,
  PRIMARY KEY (`PART_ID`,`INTEGER_IDX`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `partition_key_vals` */

/*Table structure for table `partition_keys` */

DROP TABLE IF EXISTS `partition_keys`;

CREATE TABLE `partition_keys` (
  `TBL_ID` bigint(20) NOT NULL,
  `PKEY_COMMENT` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `PKEY_NAME` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `PKEY_TYPE` varchar(767) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `INTEGER_IDX` int(11) NOT NULL,
  PRIMARY KEY (`TBL_ID`,`PKEY_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `partition_keys` */

/*Table structure for table `partitions` */

DROP TABLE IF EXISTS `partitions`;

CREATE TABLE `partitions` (
  `PART_ID` bigint(20) NOT NULL,
  `CREATE_TIME` int(11) NOT NULL,
  `LAST_ACCESS_TIME` int(11) NOT NULL,
  `PART_NAME` varchar(767) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `SD_ID` bigint(20) DEFAULT NULL,
  `TBL_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`PART_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `partitions` */

/*Table structure for table `product` */

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sheet_id` int(11) NOT NULL COMMENT '仓库sheet id',
  `format` varchar(64) DEFAULT NULL COMMENT '规格',
  `original_count` int(11) DEFAULT NULL COMMENT '申请数量',
  `real_count` int(11) DEFAULT NULL COMMENT '实际数量',
  `price` float DEFAULT NULL COMMENT '单价',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_time` time DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `sheet_id` (`sheet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `product` */

/*Table structure for table `sds` */

DROP TABLE IF EXISTS `sds`;

CREATE TABLE `sds` (
  `SD_ID` bigint(20) NOT NULL,
  `CD_ID` bigint(20) DEFAULT NULL,
  `INPUT_FORMAT` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `IS_COMPRESSED` bit(1) NOT NULL,
  `IS_STOREDASSUBDIRECTORIES` bit(1) NOT NULL,
  `LOCATION` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `NUM_BUCKETS` int(11) NOT NULL,
  `OUTPUT_FORMAT` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `SERDE_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`SD_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sds` */

/*Table structure for table `serdes` */

DROP TABLE IF EXISTS `serdes`;

CREATE TABLE `serdes` (
  `SERDE_ID` bigint(20) NOT NULL,
  `NAME` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `SLIB` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`SERDE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `serdes` */

/*Table structure for table `skewed_col_value_loc_map` */

DROP TABLE IF EXISTS `skewed_col_value_loc_map`;

CREATE TABLE `skewed_col_value_loc_map` (
  `SD_ID` bigint(20) NOT NULL,
  `STRING_LIST_ID_KID` bigint(20) NOT NULL,
  `LOCATION` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`SD_ID`,`STRING_LIST_ID_KID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `skewed_col_value_loc_map` */

/*Table structure for table `skewed_string_list` */

DROP TABLE IF EXISTS `skewed_string_list`;

CREATE TABLE `skewed_string_list` (
  `STRING_LIST_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`STRING_LIST_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `skewed_string_list` */

/*Table structure for table `skewed_values` */

DROP TABLE IF EXISTS `skewed_values`;

CREATE TABLE `skewed_values` (
  `SD_ID_OID` bigint(20) NOT NULL,
  `STRING_LIST_ID_EID` bigint(20) DEFAULT NULL,
  `INTEGER_IDX` int(11) NOT NULL,
  PRIMARY KEY (`SD_ID_OID`,`INTEGER_IDX`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `skewed_values` */

/*Table structure for table `sms_code` */

DROP TABLE IF EXISTS `sms_code`;

CREATE TABLE `sms_code` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` int(5) NOT NULL,
  `mob` varchar(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `sms_code` */

insert  into `sms_code`(`id`,`code`,`mob`,`create_time`) values (1,0,NULL,'2016-12-08 21:20:19'),(2,3748,NULL,'2016-12-08 21:24:40'),(3,1801,NULL,'2016-12-08 21:25:44'),(4,6406,NULL,'2016-12-08 21:25:54'),(5,7739,'15603735436','2016-12-08 21:27:44');

/*Table structure for table `sort_cols` */

DROP TABLE IF EXISTS `sort_cols`;

CREATE TABLE `sort_cols` (
  `SD_ID` bigint(20) NOT NULL,
  `COLUMN_NAME` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `ORDER` int(11) NOT NULL,
  `INTEGER_IDX` int(11) NOT NULL,
  PRIMARY KEY (`SD_ID`,`INTEGER_IDX`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sort_cols` */

/*Table structure for table `sub_info` */

DROP TABLE IF EXISTS `sub_info`;

CREATE TABLE `sub_info` (
  `sid` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(200) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `sub_time` datetime DEFAULT NULL,
  `writer` varchar(50) DEFAULT NULL,
  `state` tinyint(1) DEFAULT '0' COMMENT '0 表示没有认领的任务  1表示已经认领没有处理，2 表示任务已经处理过',
  `user_id` bigint(20) DEFAULT NULL,
  `openid` varchar(20) DEFAULT NULL,
  `sub_type` int(11) DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `business_type` int(11) DEFAULT NULL,
  `round` int(11) DEFAULT NULL,
  `order_id` varchar(32) DEFAULT NULL,
  `handle_time` datetime DEFAULT NULL,
  `next_handle_time` datetime DEFAULT NULL,
  `describe` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

/*Data for the table `sub_info` */

insert  into `sub_info`(`sid`,`url`,`title`,`sub_time`,`writer`,`state`,`user_id`,`openid`,`sub_type`,`name`,`business_type`,`round`,`order_id`,`handle_time`,`next_handle_time`,`describe`) values (1,'www.baidu.com','buzhidao','2016-12-03 12:35:12','zhangsan',0,1,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2,'www.tenxun.com','屌丝的自我修养','2016-12-03 16:01:59','lisi',1,5,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(3,'www.baidu.com','ä¸ä¸ªæ¼åçèªæä¿®å»',NULL,'å¼ ä¸',2,1,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(4,'www.baidu.com','ä¸ä¸ªæ¼åçèªæä¿®å»','2016-12-03 17:08:24','å¼ ä¸',0,1,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(5,'www.baidu.com','ä¸ä¸ªæ¼åçèªæä¿®å»','2016-12-03 17:09:17','å¼ ä¸',0,1,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(6,'www.baidu.com','ä¸ä¸ªæ¼åçèªæä¿®å»','2016-12-03 17:10:33','å¼ ä¸',0,1,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(7,NULL,'一个演员的自我修养','2016-12-03 17:14:31','张三',2,1,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(8,'www.baidu.com','一个演员的自我修养','2016-12-03 17:15:07','张三',0,1,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(9,'www.baidu.com','一个演员的自我修养','2016-12-03 17:39:30','张三',2,1,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(10,'www.baidu.com','一个演员的自我修养','2016-12-03 17:39:55','张三',1,1,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(11,'www.baidu.com','一个演员的自我修养','2016-12-04 12:19:34','张三',1,1,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(12,'www.baidu.com','一个演员的自我修养','2016-12-04 12:19:34','张三',1,1,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(13,'www.baidu.com','一个演员的自我修养','2016-12-04 12:23:46','张三',1,1,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(14,'www.baidu.com','一个演员的自我修养','2016-12-04 12:27:58','张三',1,1,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(15,'www.baidu.com','一个演员的自我修养','2016-12-04 12:28:44','张三',1,1,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(16,'www.baidu.com','一个演员的自我修养','2016-12-04 12:29:03','张三',1,1,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(17,'www.baidu.com','一个演员的自我修养','2016-12-04 12:29:03','张三',1,1,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(18,'www.baidu.com','一个演员的自我修养','2016-12-04 12:45:08','张三',0,1,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(19,'www.baidu.com','一个演员的自我修养','2016-12-04 12:45:09','张三',1,5,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(20,'www.baidu.com','一个演员的自我修养','2016-12-04 12:45:09','张三',0,NULL,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(21,'www.baidu.com','一个演员的自我修养','2016-12-04 12:45:09','张三',0,NULL,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(22,'www.baidu.com','一个演员的自我修养','2016-12-04 12:47:53','张三',0,NULL,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(23,'www.baidu.com','一个演员的自我修养','2016-12-04 12:47:53','张三',0,NULL,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(24,'www.baidu.com','一个演员的自我修养','2016-12-04 12:47:54','张三',0,NULL,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(25,'www.baidu.com','一个演员的自我修养','2016-12-04 12:47:54','张三',0,NULL,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(26,'www.baidu.com','一个演员的自我修养','2016-12-04 12:47:54','张三',0,NULL,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(27,'www.baidu.com','一个演员的自我修养','2016-12-04 12:47:54','张三',0,NULL,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(28,'www.baidu.com','一个演员的自我修养','2016-12-04 12:47:54','张三',0,NULL,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(29,'www.baidu.com','一个演员的自我修养','2016-12-04 12:47:54','张三',0,NULL,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(30,'www.baidu.com','一个演员的自我修养','2016-12-04 12:47:54','张三',0,NULL,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(31,'www.baidu.com','一个演员的自我修养','2016-12-04 12:47:55','张三',0,NULL,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(32,'www.baidu.com','一个演员的自我修养','2016-12-04 12:47:59','张三',0,NULL,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(33,'www.baidu.com','一个演员的自我修养','2016-12-04 12:47:59','张三',0,NULL,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(34,'www.baidu.com','一个演员的自我修养','2016-12-04 12:47:59','张三',0,NULL,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(35,'www.baidu.com','一个演员的自我修养','2016-12-04 12:47:59','张三',0,NULL,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(36,'www.baidu.com','一个演员的自我修养','2016-12-04 12:48:00','张三',0,NULL,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(37,'www.baidu.com','一个演员的自我修养','2016-12-04 12:48:00','张三',0,NULL,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(38,'www.baidu.com','一个演员的自我修养','2016-12-04 12:48:00','张三',0,NULL,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(39,'www.baidu.com','一个演员的自我修养','2016-12-11 15:30:17','张三',0,NULL,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `sys_code` */

DROP TABLE IF EXISTS `sys_code`;

CREATE TABLE `sys_code` (
  `code_id` varchar(20) NOT NULL,
  `code_name` varchar(20) NOT NULL,
  `code_type` varchar(20) NOT NULL,
  `code_order` int(11) NOT NULL,
  PRIMARY KEY (`code_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_code` */

insert  into `sys_code`(`code_id`,`code_name`,`code_type`,`code_order`) values ('100','系统管理员','user_type',1),('101','区域负责人','user_type',2),('102','护林员','user_type',3),('201','火灾隐情','CATEGORY',1),('202','乱砍乱伐','CATEGORY',2),('203','病虫害','CATEGORY',3),('204','占用林地','CATEGORY',4),('205','其他','CATEGORY',5);

/*Table structure for table `tab_col_stats` */

DROP TABLE IF EXISTS `tab_col_stats`;

CREATE TABLE `tab_col_stats` (
  `CS_ID` bigint(20) NOT NULL,
  `AVG_COL_LEN` double DEFAULT NULL,
  `COLUMN_NAME` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `COLUMN_TYPE` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `DB_NAME` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `BIG_DECIMAL_HIGH_VALUE` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `BIG_DECIMAL_LOW_VALUE` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_HIGH_VALUE` double DEFAULT NULL,
  `DOUBLE_LOW_VALUE` double DEFAULT NULL,
  `LAST_ANALYZED` bigint(20) NOT NULL,
  `LONG_HIGH_VALUE` bigint(20) DEFAULT NULL,
  `LONG_LOW_VALUE` bigint(20) DEFAULT NULL,
  `MAX_COL_LEN` bigint(20) DEFAULT NULL,
  `NUM_DISTINCTS` bigint(20) DEFAULT NULL,
  `NUM_FALSES` bigint(20) DEFAULT NULL,
  `NUM_NULLS` bigint(20) NOT NULL,
  `NUM_TRUES` bigint(20) DEFAULT NULL,
  `TBL_ID` bigint(20) DEFAULT NULL,
  `TABLE_NAME` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`CS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tab_col_stats` */

/*Table structure for table `tbls` */

DROP TABLE IF EXISTS `tbls`;

CREATE TABLE `tbls` (
  `TBL_ID` bigint(20) NOT NULL,
  `CREATE_TIME` int(11) NOT NULL,
  `DB_ID` bigint(20) DEFAULT NULL,
  `LAST_ACCESS_TIME` int(11) NOT NULL,
  `OWNER` varchar(767) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `RETENTION` int(11) NOT NULL,
  `SD_ID` bigint(20) DEFAULT NULL,
  `TBL_NAME` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `TBL_TYPE` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `VIEW_EXPANDED_TEXT` text,
  `VIEW_ORIGINAL_TEXT` text,
  PRIMARY KEY (`TBL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tbls` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `login_name` varchar(15) NOT NULL COMMENT '登录名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `name` varchar(30) NOT NULL COMMENT '姓名',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `frequency` int(3) NOT NULL COMMENT '定位频率',
  `orgId` int(10) DEFAULT NULL COMMENT '组织结构ID',
  `user_type` varchar(3) NOT NULL COMMENT '用户类型',
  `imei` varchar(20) DEFAULT NULL,
  `deviceType` char(1) DEFAULT '' COMMENT '设备类型  3:android,4:ios',
  `channelId` varchar(30) DEFAULT '' COMMENT '推送通道ID',
  `login_date` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `name` (`name`) USING BTREE,
  KEY `orgId` (`orgId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`login_name`,`password`,`name`,`phone`,`frequency`,`orgId`,`user_type`,`imei`,`deviceType`,`channelId`,`login_date`,`create_date`,`update_date`,`remark`,`del_flag`) values (1,'admin','02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032','管理员','',3,1,'100','','','','2018-06-08 22:35:29',NULL,'2015-05-05 14:07:58','12','0'),(2,'test001','02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032','test001','18530990233',2,20,'102','864103020368632','','','2016-12-10 23:02:27','2015-05-05 11:21:49','2015-08-24 16:23:20','','0'),(3,'test002','02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032','test002','',3,40,'101','866328021587454','3','4508383378934736929','2015-10-30 14:23:36','2015-05-05 11:22:02','2015-10-30 14:23:15','','0'),(4,'test003','02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032','test003','',3,30,'102','352136061858607','','','2015-08-24 16:38:50','2015-05-05 11:22:18','2015-08-24 16:38:50','','0'),(5,'test15','c71991fda82fc933cc4e84aabd2da62bc6c90a1ef5304ae9680447f1','test15','',0,NULL,'101',NULL,'','','2016-12-11 12:54:29','2016-12-11 12:32:38','2016-12-11 12:32:38','test15','0'),(6,'test12','0bef50ef624123561c012a1f4542098fbaf7f4d7a7cba4f2a0b23a25','test12','',0,NULL,'100',NULL,'','',NULL,'2016-12-11 14:44:40','2016-12-11 14:44:40','','1');

/*Table structure for table `version` */

DROP TABLE IF EXISTS `version`;

CREATE TABLE `version` (
  `VER_ID` bigint(20) NOT NULL,
  `SCHEMA_VERSION` varchar(127) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `VERSION_COMMENT` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`VER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `version` */

/*Table structure for table `warehouse_sheet` */

DROP TABLE IF EXISTS `warehouse_sheet`;

CREATE TABLE `warehouse_sheet` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `department` varchar(64) DEFAULT NULL COMMENT '申请部门',
  `No` varchar(32) DEFAULT NULL COMMENT '申请编号（自动生成）',
  `apply_time` time DEFAULT NULL COMMENT '申请时间',
  `yn` tinyint(3) DEFAULT NULL COMMENT '是否有效',
  `state` tinyint(3) DEFAULT NULL COMMENT '表单状态',
  `create_time` time DEFAULT NULL COMMENT '创建时间',
  `update_time` time DEFAULT NULL COMMENT '更新时间',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_user` varchar(32) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `warehouse_sheet` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
