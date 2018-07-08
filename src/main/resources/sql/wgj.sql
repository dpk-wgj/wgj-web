/*
Navicat MySQL Data Transfer

Source Server         : 47.106.99.73
Source Server Version : 50639
Source Host           : 47.106.99.73:3306
Source Database       : wgj

Target Server Type    : MYSQL
Target Server Version : 50639
File Encoding         : 65001

Date: 2018-07-08 09:18:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin_group`
-- ----------------------------
DROP TABLE IF EXISTS `admin_group`;
CREATE TABLE `admin_group` (
  `user_group_id` int(30) NOT NULL AUTO_INCREMENT COMMENT '用户组主键',
  `group_name` varchar(30) DEFAULT NULL COMMENT '用户组名',
  `permission` varchar(30) DEFAULT NULL COMMENT '角色',
  PRIMARY KEY (`user_group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of admin_group
-- ----------------------------

-- ----------------------------
-- Table structure for `admin_info`
-- ----------------------------
DROP TABLE IF EXISTS `admin_info`;
CREATE TABLE `admin_info` (
  `user_id` int(30) NOT NULL AUTO_INCREMENT COMMENT '用户主键',
  `username` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `user_group_id` int(30) DEFAULT NULL COMMENT '用户组id',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of admin_info
-- ----------------------------
INSERT INTO `admin_info` VALUES ('1', 'zhoulin', '123456', 'zhoulin', '1');

-- ----------------------------
-- Table structure for `car_info`
-- ----------------------------
DROP TABLE IF EXISTS `car_info`;
CREATE TABLE `car_info` (
  `car_id` int(30) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `car_number` varchar(30) CHARACTER SET utf8 DEFAULT '' COMMENT '车牌',
  `car_type` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '类型',
  PRIMARY KEY (`car_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of car_info
-- ----------------------------

-- ----------------------------
-- Table structure for `comment_info`
-- ----------------------------
DROP TABLE IF EXISTS `comment_info`;
CREATE TABLE `comment_info` (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评价id',
  `comment_content` varchar(100) DEFAULT NULL COMMENT '评论内容',
  `isClear` int(30) DEFAULT NULL COMMENT '整洁与否',
  `isStable` int(30) DEFAULT NULL COMMENT '平稳是否',
  `isKnow` int(30) DEFAULT NULL COMMENT '认路是否正确',
  `isGood` int(30) DEFAULT NULL COMMENT '态度好否',
  `order_id` int(30) DEFAULT NULL COMMENT '订单id',
  PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of comment_info
-- ----------------------------

-- ----------------------------
-- Table structure for `complaint_info`
-- ----------------------------
DROP TABLE IF EXISTS `complaint_info`;
CREATE TABLE `complaint_info` (
  `complaint_id` int(30) NOT NULL AUTO_INCREMENT COMMENT '投诉id',
  `complaint_content` varchar(100) DEFAULT NULL COMMENT '投诉内容',
  `complaint_status` int(30) DEFAULT NULL COMMENT '投诉反馈',
  `order_id` int(30) DEFAULT NULL COMMENT '订单id',
  PRIMARY KEY (`complaint_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of complaint_info
-- ----------------------------

-- ----------------------------
-- Table structure for `driver_info`
-- ----------------------------
DROP TABLE IF EXISTS `driver_info`;
CREATE TABLE `driver_info` (
  `driver_id` int(30) NOT NULL AUTO_INCREMENT COMMENT '司机id',
  `driver_name` varchar(30) DEFAULT NULL COMMENT '司机姓名',
  `driver_wx_id` varchar(100) NOT NULL COMMENT '司机微信号',
  `driver_phone_number` varchar(30) DEFAULT NULL COMMENT '司机手机号',
  `driver_identity` varchar(30) DEFAULT NULL COMMENT '司机身份证',
  `driver_location` varchar(100) DEFAULT NULL COMMENT '司机位置',
  `driver_level_star` int(30) DEFAULT '100' COMMENT '司机信誉积分',
  `driver_licence` varchar(50) DEFAULT NULL COMMENT '司机驾驶证号',
  `driver_status` int(10) NOT NULL DEFAULT '0' COMMENT '司机状态(休息: 0, 上岗: 1)',
  `flag` int(10) NOT NULL DEFAULT '0' COMMENT '司机服务状态(接单前: 0, 接客前: 1, 服务中: 2)',
  `car_id` int(30) NOT NULL COMMENT 'car主键',
  PRIMARY KEY (`driver_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of driver_info
-- ----------------------------

-- ----------------------------
-- Table structure for `log_info`
-- ----------------------------
DROP TABLE IF EXISTS `log_info`;
CREATE TABLE `log_info` (
  `log_id` int(30) NOT NULL AUTO_INCREMENT COMMENT '日志id',
  `action` varchar(255) DEFAULT NULL COMMENT '角色行为',
  `role_id` int(11) DEFAULT NULL COMMENT '角色',
  `log_time` datetime DEFAULT NULL COMMENT '日志时间',
  `order_id` int(30) DEFAULT NULL COMMENT '订单id',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of log_info
-- ----------------------------

-- ----------------------------
-- Table structure for `order_info`
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info` (
  `order_id` int(30) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `passenger_id` int(30) DEFAULT NULL COMMENT '乘客id',
  `driver_id` int(30) DEFAULT NULL COMMENT '司机id',
  `location_info` varchar(255) DEFAULT NULL COMMENT '移动轨迹',
  `order_status` int(30) DEFAULT NULL COMMENT '订单状态',
  `start_time` datetime DEFAULT NULL COMMENT '创建订单时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束订单时间',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of order_info
-- ----------------------------

-- ----------------------------
-- Table structure for `passenger_info`
-- ----------------------------
DROP TABLE IF EXISTS `passenger_info`;
CREATE TABLE `passenger_info` (
  `passenger_id` int(30) NOT NULL AUTO_INCREMENT COMMENT '乘客id',
  `passenger_wx_id` varchar(100) CHARACTER SET utf8 NOT NULL COMMENT '乘客微信号',
  `passenger_phone_number` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '手机号',
  `passenger_location` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '乘客位置',
  `passenger_level_star` int(30) DEFAULT '100' COMMENT '用户信誉积分',
  `passenger_status` int(30) DEFAULT '0' COMMENT '用户状态(服务前: 0,中: 1,后: 2)',
  PRIMARY KEY (`passenger_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of passenger_info
-- ----------------------------
