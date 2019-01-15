/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50051
Source Host           : localhost:3306
Source Database       : book_db

Target Server Type    : MYSQL
Target Server Version : 50051
File Encoding         : 65001

Date: 2014-10-05 17:13:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  PRIMARY KEY  (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('a', 'a');

-- ----------------------------
-- Table structure for `t_book`
-- ----------------------------
DROP TABLE IF EXISTS `t_book`;
CREATE TABLE `t_book` (
  `barcode` varchar(20) NOT NULL,
  `bookName` varchar(20) NOT NULL,
  `bookType` int(11) default NULL,
  `price` float NOT NULL,
  `count` int(11) NOT NULL,
  `publish` varchar(20) default NULL,
  `publishDate` varchar(10) NOT NULL,
  `photoBook` varchar(50) default NULL,
  PRIMARY KEY  (`barcode`),
  KEY `FKCB4C8FF42CA4B7B0` (`bookType`),
  CONSTRAINT `FKCB4C8FF42CA4B7B0` FOREIGN KEY (`bookType`) REFERENCES `t_booktype` (`bookTypeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_book
-- ----------------------------
INSERT INTO `t_book` VALUES ('TS001', '安卓程序设计', '2', '25', '12', '四川大学出版社', '2014-10-01', 'upload/5a1232b6-e778-423f-8216-ff5aab31ff5c.jpg');
INSERT INTO `t_book` VALUES ('TS002', 'delphi程序设计', '2', '25.5', '12', '人民教育出版社', '2014-10-02', 'upload/492a0cba-2469-4599-82b4-3ea4ad29619c.jpg');
INSERT INTO `t_book` VALUES ('TS003', '中国近代史', '4', '23.3', '12', '北京邮电出版社', '2014-10-14', 'upload/4f264633-c116-49da-a57a-a1cb77822e2d.jpg');
INSERT INTO `t_book` VALUES ('TS004', '中国古代史', '4', '18.5', '12', '四川大学出版社', '2014-10-10', 'upload/d4ad73a8-d8d0-4085-b6b6-369283f6b51e.jpg');
INSERT INTO `t_book` VALUES ('TS005', 'div+css网站设计', '2', '38.5', '19', '成都理工出版社', '2014-10-06', 'upload/1d430354-656c-4dce-b394-33427b33e3dd.jpg');
INSERT INTO `t_book` VALUES ('TS006', '手机ios编程', '2', '42.3', '18', '电子科技大学出版社', '2014-10-12', 'upload/eaf4e981-7e58-4185-915f-a08e7086bada.jpg');

-- ----------------------------
-- Table structure for `t_booktype`
-- ----------------------------
DROP TABLE IF EXISTS `t_booktype`;
CREATE TABLE `t_booktype` (
  `bookTypeId` int(11) NOT NULL auto_increment,
  `bookTypeName` varchar(18) NOT NULL,
  `days` int(11) NOT NULL,
  PRIMARY KEY  (`bookTypeId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_booktype
-- ----------------------------
INSERT INTO `t_booktype` VALUES ('2', '计算机类', '25');
INSERT INTO `t_booktype` VALUES ('4', '历史类', '25');
