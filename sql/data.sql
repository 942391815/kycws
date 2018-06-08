/*
SQLyog v10.2
MySQL - 5.5.28-log : Database - dg-ai
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
  PRIMARY KEY (`bid`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Data for the table `back_info` */

insert  into `back_info`(`bid`,`url`,`title`,`source`,`back_time`,`sub_id`,`state`,`handle_time`) values (1,'1111','111','111111111','2016-12-04 21:41:31',1,1,'2016-12-05 21:12:00'),(2,'www.baidu.com','wwcccc','ccccccccccccccccc','2016-12-04 22:03:48',2,1,'2016-12-05 21:12:00'),(3,'','','','2016-12-11 15:36:12',5,1,'2016-12-11 15:37:00'),(4,'','','','2016-12-11 15:47:47',8,1,'2016-12-11 15:48:00'),(5,'','','','2016-12-11 15:49:11',6,1,'2016-12-11 15:50:00'),(6,'','','','2016-12-11 15:50:10',4,1,'2016-12-11 15:51:26'),(7,'','','','2016-12-11 15:52:52',1,1,'2016-12-11 15:53:00'),(8,'','','','2016-12-11 15:53:33',18,1,'2016-12-11 15:54:09'),(9,'','','','2016-12-11 15:55:23',7,1,'2016-12-11 15:56:00');

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=254 DEFAULT CHARSET=utf8;

/*Data for the table `order` */

insert  into `order`(`id`,`name`,`price`,`discount_price`,`describe`,`yn`,`create_time`,`update_time`) values (1,'zhangsan',1,0,'没有121112121惺惺惜惺惺111111111',0,'2016-12-10 22:33:50',NULL),(2,'11111111111111111',12,0,'12',0,'2016-12-10 22:34:10',NULL),(3,'订单名称过长订单名称过长订单名称过长订单名称过长订单名称过长',0,0,'11111111111111sssssssssss',0,'2016-12-10 22:34:12',NULL),(4,'zhangsan',1,0,'没有121112121惺惺惜惺惺111111111',0,'2016-12-10 22:34:17',NULL),(5,'11111111111111111',12,0,'121收到离开房间数量的空间福克斯劳动节放假乐山大佛',0,'2016-12-10 22:34:14',NULL),(6,'订单名称过长订单名称过长订单名称过长订单名称过长订单名称过长',0,0,'11111111111111sssssssssss',0,'2016-12-10 22:34:19',NULL),(7,'zhangsan',1,0,'没有121112121惺惺惜惺惺111111111',0,'2016-12-10 22:34:36',NULL),(8,'11111111111111111',12,0,'12',0,'2016-12-10 22:34:21',NULL),(9,'订单名称过长订单名称过长订单名称过长订单名称过长订单名称过长',0,0,'11111111111111sssssssssss',0,'2016-12-10 22:34:23',NULL),(10,'zhangsan',1,0,'没有121112121惺惺惜惺惺111111111',0,'2016-12-10 22:34:38',NULL),(11,'11111111111111111',12,0,'12',0,'2016-12-10 22:34:27',NULL),(12,'订单名称过长订单名称过长订单名称过长订单名称过长订单名称过长',0,0,'11111111111111sssssssssss',0,'2016-12-10 22:34:30',NULL),(14,'zhangsan',1,0,'没有121112121惺惺惜惺惺111111111',0,'2016-12-10 22:34:40',NULL),(15,'11111111111111111',12,0,'12',0,'2016-12-10 22:34:34',NULL),(16,'订单名称过长订单名称过长订单名称过长订单名称过长订单名称过长',0,0,'11111111111111sssssssssss',0,'2016-12-10 22:34:32',NULL),(17,'zhangsan',1,0,'没有121112121惺惺惜惺惺111111111',1,NULL,NULL),(18,'11111111111111111',12,0,'12',0,'2016-12-10 22:34:07',NULL),(19,'订单名称过长订单名称过长订单名称过长订单名称过长订单名称过长',0,0,'11111111111111sssssssssss',1,NULL,NULL),(20,'zhangsan',1,0,'没有121112121惺惺惜惺惺111111111',1,NULL,NULL),(23,'zhangsan',1,0,'没有121112121惺惺惜惺惺111111111',1,NULL,NULL),(25,'订单名称过长订单名称过长订单名称过长订单名称过长订单名称过长',0,0,'11111111111111sssssssssss',1,NULL,NULL),(31,'订单名称过长订单名称过长订单名称过长订单名称过长订单名称过长',0,0,'11111111111111sssssssssss',1,NULL,NULL),(35,'zhangsan',1,0,'没有121112121惺惺惜惺惺111111111',0,'2016-12-10 22:34:44',NULL),(40,'订单名称过长订单名称过长订单名称过长订单名称过长订单名称过长',0,0,'11111111111111sssssssssss',1,NULL,NULL),(45,'11111111111111111',12,12,'12',1,NULL,NULL),(49,'订单名称过长订单名称过长订单名称过长订单名称过长订单名称过长',0,0,'11111111111111sssssssssss',1,NULL,NULL),(50,'zhangsan',1,0,'没有121112121惺惺惜惺惺111111111',1,NULL,NULL),(51,'11111111111111111',12,12,'12',1,NULL,NULL),(71,'订单名称过长订单名称过长订单名称过长订单名称过长订单名称过长',0,0,'11111111111111sssssssssss',1,NULL,NULL),(75,'zhangsan',1,0,'没有121112121惺惺惜惺惺111111111',1,NULL,NULL),(86,'订单名称过长订单名称过长订单名称过长订单名称过长订单名称过长',0,0,'11111111111111sssssssssss',1,NULL,NULL),(87,'zhangsan',1,0,'没有121112121惺惺惜惺惺111111111',1,NULL,NULL),(88,'11111111111111111',12,12,'12',1,NULL,NULL),(89,'订单名称过长订单名称过长订单名称过长订单名称过长订单名称过长',0,0,'11111111111111sssssssssss',1,NULL,NULL),(252,'wwwwccccc',0,0,'wwwwccccc',1,'2016-12-10 22:38:31',NULL),(253,'不知道不知道',0,0,'121212',1,'2016-12-11 12:02:58','2016-12-11 12:02:58');

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
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

/*Data for the table `sub_info` */

insert  into `sub_info`(`sid`,`url`,`title`,`sub_time`,`writer`,`state`,`user_id`,`openid`) values (1,'www.baidu.com','buzhidao','2016-12-03 12:35:12','zhangsan',0,1,'1'),(2,'www.tenxun.com','屌丝的自我修养','2016-12-03 16:01:59','lisi',1,5,'1'),(3,'www.baidu.com','ä¸ä¸ªæ¼åçèªæä¿®å»',NULL,'å¼ ä¸',2,1,'1'),(4,'www.baidu.com','ä¸ä¸ªæ¼åçèªæä¿®å»','2016-12-03 17:08:24','å¼ ä¸',0,1,'1'),(5,'www.baidu.com','ä¸ä¸ªæ¼åçèªæä¿®å»','2016-12-03 17:09:17','å¼ ä¸',0,1,'1'),(6,'www.baidu.com','ä¸ä¸ªæ¼åçèªæä¿®å»','2016-12-03 17:10:33','å¼ ä¸',0,1,'1'),(7,NULL,'一个演员的自我修养','2016-12-03 17:14:31','张三',2,1,'1'),(8,'www.baidu.com','一个演员的自我修养','2016-12-03 17:15:07','张三',0,1,'1'),(9,'www.baidu.com','一个演员的自我修养','2016-12-03 17:39:30','张三',2,1,'1'),(10,'www.baidu.com','一个演员的自我修养','2016-12-03 17:39:55','张三',1,1,'1'),(11,'www.baidu.com','一个演员的自我修养','2016-12-04 12:19:34','张三',1,1,'1'),(12,'www.baidu.com','一个演员的自我修养','2016-12-04 12:19:34','张三',1,1,'1'),(13,'www.baidu.com','一个演员的自我修养','2016-12-04 12:23:46','张三',1,1,'1'),(14,'www.baidu.com','一个演员的自我修养','2016-12-04 12:27:58','张三',1,1,'1'),(15,'www.baidu.com','一个演员的自我修养','2016-12-04 12:28:44','张三',1,1,'1'),(16,'www.baidu.com','一个演员的自我修养','2016-12-04 12:29:03','张三',1,1,'1'),(17,'www.baidu.com','一个演员的自我修养','2016-12-04 12:29:03','张三',1,1,'1'),(18,'www.baidu.com','一个演员的自我修养','2016-12-04 12:45:08','张三',0,1,'1'),(19,'www.baidu.com','一个演员的自我修养','2016-12-04 12:45:09','张三',1,5,'1'),(20,'www.baidu.com','一个演员的自我修养','2016-12-04 12:45:09','张三',0,NULL,'1'),(21,'www.baidu.com','一个演员的自我修养','2016-12-04 12:45:09','张三',0,NULL,'1'),(22,'www.baidu.com','一个演员的自我修养','2016-12-04 12:47:53','张三',0,NULL,'1'),(23,'www.baidu.com','一个演员的自我修养','2016-12-04 12:47:53','张三',0,NULL,'1'),(24,'www.baidu.com','一个演员的自我修养','2016-12-04 12:47:54','张三',0,NULL,'1'),(25,'www.baidu.com','一个演员的自我修养','2016-12-04 12:47:54','张三',0,NULL,'1'),(26,'www.baidu.com','一个演员的自我修养','2016-12-04 12:47:54','张三',0,NULL,'1'),(27,'www.baidu.com','一个演员的自我修养','2016-12-04 12:47:54','张三',0,NULL,'1'),(28,'www.baidu.com','一个演员的自我修养','2016-12-04 12:47:54','张三',0,NULL,'1'),(29,'www.baidu.com','一个演员的自我修养','2016-12-04 12:47:54','张三',0,NULL,'1'),(30,'www.baidu.com','一个演员的自我修养','2016-12-04 12:47:54','张三',0,NULL,'1'),(31,'www.baidu.com','一个演员的自我修养','2016-12-04 12:47:55','张三',0,NULL,'1'),(32,'www.baidu.com','一个演员的自我修养','2016-12-04 12:47:59','张三',0,NULL,'1'),(33,'www.baidu.com','一个演员的自我修养','2016-12-04 12:47:59','张三',0,NULL,'1'),(34,'www.baidu.com','一个演员的自我修养','2016-12-04 12:47:59','张三',0,NULL,'1'),(35,'www.baidu.com','一个演员的自我修养','2016-12-04 12:47:59','张三',0,NULL,'1'),(36,'www.baidu.com','一个演员的自我修养','2016-12-04 12:48:00','张三',0,NULL,'1'),(37,'www.baidu.com','一个演员的自我修养','2016-12-04 12:48:00','张三',0,NULL,'1'),(38,'www.baidu.com','一个演员的自我修养','2016-12-04 12:48:00','张三',0,NULL,'1'),(39,'www.baidu.com','一个演员的自我修养','2016-12-11 15:30:17','张三',0,NULL,'1');

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

insert  into `user`(`id`,`login_name`,`password`,`name`,`phone`,`frequency`,`orgId`,`user_type`,`imei`,`deviceType`,`channelId`,`login_date`,`create_date`,`update_date`,`remark`,`del_flag`) values (1,'admin','02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032','管理员','',3,1,'100','','','','2016-12-11 18:03:47',NULL,'2015-05-05 14:07:58','12','0'),(2,'test001','02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032','test001','18530990233',2,20,'102','864103020368632','','','2016-12-10 23:02:27','2015-05-05 11:21:49','2015-08-24 16:23:20','','0'),(3,'test002','02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032','test002','',3,40,'101','866328021587454','3','4508383378934736929','2015-10-30 14:23:36','2015-05-05 11:22:02','2015-10-30 14:23:15','','0'),(4,'test003','02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032','test003','',3,30,'102','352136061858607','','','2015-08-24 16:38:50','2015-05-05 11:22:18','2015-08-24 16:38:50','','0'),(5,'test15','c71991fda82fc933cc4e84aabd2da62bc6c90a1ef5304ae9680447f1','test15','',0,NULL,'101',NULL,'','','2016-12-11 12:54:29','2016-12-11 12:32:38','2016-12-11 12:32:38','test15','0'),(6,'test12','0bef50ef624123561c012a1f4542098fbaf7f4d7a7cba4f2a0b23a25','test12','',0,NULL,'100',NULL,'','',NULL,'2016-12-11 14:44:40','2016-12-11 14:44:40','','1');

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
