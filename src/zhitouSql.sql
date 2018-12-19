/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 8.0.11 : Database - zhitou
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`zhitou` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `zhitou`;

/*Table structure for table `documentary_info` */

DROP TABLE IF EXISTS `documentary_info`;

CREATE TABLE `documentary_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '伪ID',
  `userId` int(11) NOT NULL COMMENT '用户ID重要',
  `scanUrl` text NOT NULL COMMENT '扫描网址',
  `tagName` varchar(10) DEFAULT '备注' COMMENT '备注名称',
  `orderType` int(1) NOT NULL DEFAULT '0' COMMENT '指定交易类型0买1卖2all',
  `frequency` int(11) NOT NULL DEFAULT '20' COMMENT '指定扫描频率',
  `canUser` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否可用，会员状态相关',
  `positionRatio` int(11) NOT NULL DEFAULT '100' COMMENT '指定持仓比例',
  `documentaryType` int(1) NOT NULL DEFAULT '0' COMMENT '跟单类型，1闪电+0严格',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

/*Data for the table `documentary_info` */

insert  into `documentary_info`(`id`,`userId`,`scanUrl`,`tagName`,`orderType`,`frequency`,`canUser`,`positionRatio`,`documentaryType`) values (18,44,'www.b22.com','备注2',2,5,0,1,1),(19,44,'www.2b22.com','备注',2,5,0,1,1);

/*Table structure for table `historical_records` */

DROP TABLE IF EXISTS `historical_records`;

CREATE TABLE `historical_records` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '伪id',
  `userId` int(11) NOT NULL COMMENT '用户ID重要',
  `platform` int(1) NOT NULL DEFAULT '0' COMMENT '平台1京东或0雪球',
  `tagName` varchar(10) NOT NULL DEFAULT '备注' COMMENT '备注',
  `orderType` int(1) NOT NULL DEFAULT '0' COMMENT '交易类型0买1卖2all',
  `orderTime` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '交易时间',
  `stockNum` varchar(10) NOT NULL COMMENT '股票代码',
  `stockMoney` varchar(10) NOT NULL COMMENT '成交价',
  `postitionRatio` varchar(10) NOT NULL COMMENT '持仓变化',
  `success` tinyint(1) NOT NULL DEFAULT '1' COMMENT '提示或跟单是否成功',
  `erroMsg` varchar(30) DEFAULT NULL COMMENT '失败原因',
  `scanOrOrder` int(1) NOT NULL DEFAULT '0' COMMENT '是扫描还是跟单0扫描1跟单',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `historical_records` */

/*Table structure for table `pay_info` */

DROP TABLE IF EXISTS `pay_info`;

CREATE TABLE `pay_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `userId` int(11) NOT NULL COMMENT '用户id',
  `qrId` int(11) NOT NULL COMMENT '对应qrcode表id字段',
  `money` int(11) NOT NULL DEFAULT '0' COMMENT '支付金额分',
  `timeFormat` varchar(20) NOT NULL DEFAULT '2018-09-25 19:03' COMMENT '支付时间，格式化的',
  `timeToLong` varchar(20) NOT NULL DEFAULT '1535443577406' COMMENT '支付时间，ms',
  `allData` text NOT NULL COMMENT '有赞推送的支付信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=433 DEFAULT CHARSET=utf8;

/*Data for the table `pay_info` */

/*Table structure for table `qrcode` */

DROP TABLE IF EXISTS `qrcode`;

CREATE TABLE `qrcode` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userId` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `qrId` varchar(11) NOT NULL DEFAULT '0' COMMENT '有赞返回的qrid',
  `qrName` varchar(60) NOT NULL DEFAULT 'def' COMMENT '二维码名称',
  `vipType` int(11) NOT NULL DEFAULT '23' COMMENT '对应会员类型，超级会员年11，6月12,1月13，高级会员，年21,6月22，1月23',
  `timeNum` int(11) NOT NULL DEFAULT '1' COMMENT '对应会员时长，配合unit使用',
  `timeUnit` varchar(2) NOT NULL DEFAULT '月' COMMENT '对应会员时长单位年or月',
  `timeLong` varchar(20) NOT NULL DEFAULT '2678400000' COMMENT '对应会员时长毫秒',
  `allMoney` int(11) NOT NULL DEFAULT '10' COMMENT '支付总价分',
  `monthMoney` int(11) NOT NULL DEFAULT '10' COMMENT '支付月单价分',
  `createTime` varchar(20) NOT NULL DEFAULT '1535443577406' COMMENT '二维码创建时间',
  `saveMoney` int(11) NOT NULL DEFAULT '0' COMMENT '节省了多少钱',
  `qrUrl` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '支付二维码对应的url',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=128 DEFAULT CHARSET=utf8;

/*Data for the table `qrcode` */

insert  into `qrcode`(`id`,`userId`,`qrId`,`qrName`,`vipType`,`timeNum`,`timeUnit`,`timeLong`,`allMoney`,`monthMoney`,`createTime`,`saveMoney`,`qrUrl`) values (110,44,'8852988','尊敬的用户,您正在开通包年超级会员服务',11,1,'年','31536000000',28800,2400,'1539828868448',7200,'https://trade.koudaitong.com/wxpay/confirmQr?qr_id=8852988&kdt_id=41461397'),(111,44,'8852989','尊敬的用户,您正在开通3个月超级会员服务',12,3,'月','7568640000',8100,2700,'1539828871019',900,'https://trade.koudaitong.com/wxpay/confirmQr?qr_id=8852989&kdt_id=41461397'),(112,44,'8852990','尊敬的用户,您正在开通1个月超级会员服务',13,1,'月','2678400000',3000,3000,'1539828872127',0,'https://trade.koudaitong.com/wxpay/confirmQr?qr_id=8852990&kdt_id=41461397'),(113,44,'8852991','尊敬的用户,您正在开通包年高级会员服务',21,1,'年','31536000000',14900,1240,'1539828872504',3100,'https://trade.koudaitong.com/wxpay/confirmQr?qr_id=8852991&kdt_id=41461397'),(114,44,'8852993','尊敬的用户,您正在开通3个月高级会员服务',22,3,'月','7568640000',3900,1300,'1539828872855',600,'https://trade.koudaitong.com/wxpay/confirmQr?qr_id=8852993&kdt_id=41461397'),(115,44,'8852994','尊敬的用户,您正在开通1个月高级会员服务',23,1,'月','2678400000',1500,1500,'1539828873180',0,'https://trade.koudaitong.com/wxpay/confirmQr?qr_id=8852994&kdt_id=41461397'),(116,45,'8924024','尊敬的用户,您正在开通包年超级会员服务',11,1,'年','31536000000',28800,2400,'1540174733105',7200,'https://trade.koudaitong.com/wxpay/confirmQr?qr_id=8924024&kdt_id=41461397'),(117,45,'8924025','尊敬的用户,您正在开通3个月超级会员服务',12,3,'月','7568640000',8100,2700,'1540174734960',900,'https://trade.koudaitong.com/wxpay/confirmQr?qr_id=8924025&kdt_id=41461397'),(118,45,'8924026','尊敬的用户,您正在开通1个月超级会员服务',13,1,'月','2678400000',3000,3000,'1540174735353',0,'https://trade.koudaitong.com/wxpay/confirmQr?qr_id=8924026&kdt_id=41461397'),(119,45,'8924027','尊敬的用户,您正在开通包年高级会员服务',21,1,'年','31536000000',14900,1240,'1540174736098',3100,'https://trade.koudaitong.com/wxpay/confirmQr?qr_id=8924027&kdt_id=41461397'),(120,45,'8924028','尊敬的用户,您正在开通3个月高级会员服务',22,3,'月','7568640000',3900,1300,'1540174736536',600,'https://trade.koudaitong.com/wxpay/confirmQr?qr_id=8924028&kdt_id=41461397'),(121,45,'8924029','尊敬的用户,您正在开通1个月高级会员服务',23,1,'月','2678400000',1500,1500,'1540174736977',0,'https://trade.koudaitong.com/wxpay/confirmQr?qr_id=8924029&kdt_id=41461397'),(122,46,'9064036','尊敬的用户,您正在开通包年超级会员服务',11,1,'年','31536000000',28800,2400,'1540779074669',7200,'https://trade.koudaitong.com/wxpay/confirmQr?qr_id=9064036&kdt_id=41461397'),(123,46,'9064037','尊敬的用户,您正在开通3个月超级会员服务',12,3,'月','7568640000',8100,2700,'1540779075334',900,'https://trade.koudaitong.com/wxpay/confirmQr?qr_id=9064037&kdt_id=41461397'),(124,46,'9064038','尊敬的用户,您正在开通1个月超级会员服务',13,1,'月','2678400000',3000,3000,'1540779075639',0,'https://trade.koudaitong.com/wxpay/confirmQr?qr_id=9064038&kdt_id=41461397'),(125,46,'9064039','尊敬的用户,您正在开通包年高级会员服务',21,1,'年','31536000000',14900,1240,'1540779075954',3100,'https://trade.koudaitong.com/wxpay/confirmQr?qr_id=9064039&kdt_id=41461397'),(126,46,'9064040','尊敬的用户,您正在开通3个月高级会员服务',22,3,'月','7568640000',3900,1300,'1540779076248',600,'https://trade.koudaitong.com/wxpay/confirmQr?qr_id=9064040&kdt_id=41461397'),(127,46,'9064041','尊敬的用户,您正在开通1个月高级会员服务',23,1,'月','2678400000',1500,1500,'1540779076558',0,'https://trade.koudaitong.com/wxpay/confirmQr?qr_id=9064041&kdt_id=41461397');

/*Table structure for table `scan_info` */

DROP TABLE IF EXISTS `scan_info`;

CREATE TABLE `scan_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '伪主键',
  `userId` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '用户ID重要',
  `scanUrl` text NOT NULL COMMENT '扫描网址',
  `tagName` varchar(10) DEFAULT '备注' COMMENT '备注',
  `orderType` int(1) NOT NULL DEFAULT '0' COMMENT '指定扫描类型0买1卖2all',
  `frequency` int(11) NOT NULL DEFAULT '20' COMMENT '指定扫描频率',
  `canUser` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否可用，会员状态相关',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

/*Data for the table `scan_info` */

/*Table structure for table `user_info` */

DROP TABLE IF EXISTS `user_info`;

CREATE TABLE `user_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '伪ID',
  `vipId` int(10) unsigned NOT NULL,
  `phone` varchar(11) NOT NULL COMMENT '手机号+账号',
  `registTime` varchar(20) NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '注册时间',
  `isLogin` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否登录状态',
  `passWord` varchar(128) NOT NULL,
  `token` text,
  `salt` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '.',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;

/*Data for the table `user_info` */

insert  into `user_info`(`id`,`vipId`,`phone`,`registTime`,`isLogin`,`passWord`,`token`,`salt`) values (44,36,'13613571331','2018-10-18 10:14:28',1,'cfcaca23e5a6864579ac513079cd257a54c11bda56a277edccac06db8d49c13f62bf479b30a05e95f8767c01c3b47f7e32fffdcb87fcad1e423f5ea5f008c015','eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxMzYxMzU3MTMzMSIsInBob25lIjoiMTM2MTM1NzEzMzEiLCJtSXNzdWVyIjoiaW1tb3J0YWxIYWxmV3UxNTM5OTMxMTIzMDgxIiwiaXNzIjoiaW1tb3J0YWxIYWxmV3UxNTM5OTMxMTIzMDgxIiwiZXhwIjoxODU1NDQ4MDY4LCJ1c2VySWQiOjQ0fQ.LkCPphikOFADEmfjZKrGms-UM9dKALKddYpVI1vz73o','2728cd3a1eec6f652cf52e1d05a0fa9d'),(45,37,'13613571332','2018-10-22 10:18:52',0,'f6393a45c5da7c503166fd135f2791376ef67decc043eb0b5a4e860af923dd6d55fa187428bac574154c3eea5aad8bed07a44bf0f4c2ffed9880760de33a568a',NULL,'877b342d8994731b86e4b93c1664ed04'),(46,38,'13613571333','2018-10-29 10:11:14',0,'39bee2140a34017224cd4cd734f26e6fb58349d5cfa3d2eba838194233e3250c6686137c0d1537861d61697a97e1b2d1164457ee3c6dc01913ca33b94dc2df14',NULL,'dc3908705f83051abf813cc7c1e4e2da');

/*Table structure for table `vip_info` */

DROP TABLE IF EXISTS `vip_info`;

CREATE TABLE `vip_info` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(10) unsigned NOT NULL DEFAULT '0',
  `startTime` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1535443577406' COMMENT '会员开始时间',
  `endTime` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1635443577406' COMMENT '会员到期时间',
  `vipType` int(1) NOT NULL DEFAULT '0' COMMENT '会员类型0普通，1高级，2超级',
  `startTimeFormat` varchar(19) NOT NULL DEFAULT '2018-09-11 16:40:42' COMMENT '开始时间',
  `endTimeFormat` varchar(19) NOT NULL DEFAULT '2018-09-11 16:40:42' COMMENT '结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

/*Data for the table `vip_info` */

insert  into `vip_info`(`id`,`userId`,`startTime`,`endTime`,`vipType`,`startTimeFormat`,`endTimeFormat`) values (36,44,'1539828868413','1855448068413',0,'2018-10-18','2028-10-18'),(37,45,'1540174733054','1855793933054',0,'2018-10-22','2028-10-22'),(38,46,'1540779074599','1856398274599',0,'2018-10-29','2028-10-29');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
