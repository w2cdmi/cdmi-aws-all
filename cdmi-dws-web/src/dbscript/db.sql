/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50720
Source Host           : localhost:3308
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-04-09 17:07:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for p_activity
-- ----------------------------
DROP TABLE IF EXISTS `p_activity`;
CREATE TABLE `p_activity` (
  `id` varchar(128) NOT NULL,
  `activity_aid` varchar(255) DEFAULT NULL,
  `activity_arranges` varchar(255) DEFAULT NULL,
  `activity_cost` int(11) DEFAULT NULL,
  `activity_describe` varchar(255) DEFAULT NULL,
  `activity_name` varchar(255) NOT NULL,
  `activity_state` varchar(255) DEFAULT NULL,
  `activity_tid` varchar(255) DEFAULT NULL,
  `activity_type` varchar(255) NOT NULL,
  `activity_uid` varchar(255) NOT NULL,
  `app_id` varchar(255) NOT NULL,
  `apply_cost` int(11) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `end_time` datetime DEFAULT NULL,
  `guests` varchar(255) DEFAULT NULL,
  `history_silhouettes` varchar(255) DEFAULT NULL,
  `is_activity_cost` bit(1) DEFAULT NULL,
  `is_apply` bit(1) DEFAULT NULL,
  `is_apply_cost` bit(1) DEFAULT NULL,
  `is_limit_number` bit(1) DEFAULT NULL,
  `limit_number` int(11) DEFAULT NULL,
  `list_news` varchar(255) DEFAULT NULL,
  `number_of_applicants` int(11) DEFAULT NULL,
  `number_of_followers` int(11) DEFAULT NULL,
  `participation` int(11) DEFAULT NULL,
  `site_id` varchar(255) DEFAULT NULL,
  `sponsor_id` varchar(255) DEFAULT NULL,
  `sponsor_names` varchar(255) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `tenant_id` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for p_apply
-- ----------------------------
DROP TABLE IF EXISTS `p_apply`;
CREATE TABLE `p_apply` (
  `id` varchar(128) NOT NULL,
  `activity_aid` varchar(255) DEFAULT NULL,
  `activity_id` varchar(255) NOT NULL,
  `activity_tid` varchar(255) DEFAULT NULL,
  `activity_uid` varchar(255) NOT NULL,
  `app_id` varchar(255) NOT NULL,
  `creat_time` datetime NOT NULL,
  `is_cost` bit(1) DEFAULT NULL,
  `site_id` varchar(255) DEFAULT NULL,
  `tenant_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for p_participate
-- ----------------------------
DROP TABLE IF EXISTS `p_participate`;
CREATE TABLE `p_participate` (
  `id` varchar(128) NOT NULL,
  `activity_aid` varchar(255) DEFAULT NULL,
  `activity_id` varchar(255) NOT NULL,
  `activity_tid` varchar(255) DEFAULT NULL,
  `activity_uid` varchar(255) NOT NULL,
  `app_id` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `site_id` varchar(255) DEFAULT NULL,
  `tenant_id` varchar(255) DEFAULT NULL,
  `type` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for p_photo_album
-- ----------------------------
DROP TABLE IF EXISTS `p_photo_album`;
CREATE TABLE `p_photo_album` (
  `id` varchar(128) NOT NULL,
  `activity_id` varchar(255) NOT NULL,
  `app_id` varchar(255) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `site_id` varchar(255) DEFAULT NULL,
  `tenant_id` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `url_photo_album` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for soc_message
-- ----------------------------
DROP TABLE IF EXISTS `soc_message`;
CREATE TABLE `soc_message` (
  `id` varchar(128) NOT NULL,
  `app_id` varchar(255) NOT NULL,
  `content` varchar(2000) NOT NULL,
  `create_date` datetime DEFAULT NULL,
  `messaage_send_state` bit(1) DEFAULT NULL,
  `message_type` varchar(255) DEFAULT NULL,
  `site_id` varchar(255) DEFAULT NULL,
  `tenant_id` varchar(255) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `user_aid` varchar(255) DEFAULT NULL,
  `user_tid` varchar(255) DEFAULT NULL,
  `user_uid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for soc_notify_user_message
-- ----------------------------
DROP TABLE IF EXISTS `soc_notify_user_message`;
CREATE TABLE `soc_notify_user_message` (
  `id` varchar(128) NOT NULL,
  `app_id` varchar(255) NOT NULL,
  `content` varchar(3000) DEFAULT NULL,
  `event_object_id` varchar(255) DEFAULT NULL,
  `event_object_operations` varchar(255) DEFAULT NULL,
  `event_oject_type` varchar(255) DEFAULT NULL,
  `is_broadcast` bit(1) DEFAULT NULL,
  `message_id` varchar(255) DEFAULT NULL,
  `message_status` varchar(255) DEFAULT NULL,
  `notify_aid` varchar(255) DEFAULT NULL,
  `notify_date` datetime DEFAULT NULL,
  `notify_tid` varchar(255) DEFAULT NULL,
  `notify_uid` varchar(255) DEFAULT NULL,
  `site_id` varchar(255) DEFAULT NULL,
  `targer_id` varchar(255) DEFAULT NULL,
  `tenant_id` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_comment
-- ----------------------------
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment` (
  `id` varchar(128) NOT NULL,
  `app_id` varchar(255) NOT NULL,
  `content` varchar(255) NOT NULL,
  `create_time` datetime NOT NULL,
  `head_image` varchar(255) DEFAULT NULL,
  `owner_id` varchar(255) DEFAULT NULL,
  `praise_number` int(11) DEFAULT NULL,
  `site_id` varchar(255) DEFAULT NULL,
  `target_id` varchar(255) NOT NULL,
  `target_type` varchar(255) NOT NULL,
  `tenant_id` varchar(255) DEFAULT NULL,
  `commentator_aid` varchar(255) NOT NULL,
  `commentator_name` varchar(255) DEFAULT NULL,
  `commentator_tid` varchar(255) DEFAULT NULL,
  `commentator_type` varchar(255) DEFAULT NULL,
  `commentator_uid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_complain
-- ----------------------------
DROP TABLE IF EXISTS `t_complain`;
CREATE TABLE `t_complain` (
  `id` varchar(128) NOT NULL,
  `app_id` varchar(255) DEFAULT NULL,
  `complainant_id` varchar(255) DEFAULT NULL,
  `complainant_sid` varchar(255) DEFAULT NULL,
  `content` varchar(255) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `deal_reply` varchar(255) DEFAULT NULL,
  `finish_time` datetime DEFAULT NULL,
  `liableman_id` varchar(255) DEFAULT NULL,
  `liableman_name` varchar(255) DEFAULT NULL,
  `question_type` varchar(255) NOT NULL,
  `receiver_id` varchar(255) DEFAULT NULL,
  `receiver_name` varchar(255) DEFAULT NULL,
  `receiver_type` varchar(255) DEFAULT NULL,
  `site_id` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `target_id` varchar(255) NOT NULL,
  `target_sid` varchar(255) DEFAULT NULL,
  `target_type` varchar(255) NOT NULL,
  `tenant_id` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_praise
-- ----------------------------
DROP TABLE IF EXISTS `t_praise`;
CREATE TABLE `t_praise` (
  `id` varchar(128) NOT NULL,
  `app_id` varchar(255) NOT NULL,
  `create_time` datetime NOT NULL,
  `head_image` varchar(255) DEFAULT NULL,
  `owner_id` varchar(255) DEFAULT NULL,
  `site_id` varchar(255) DEFAULT NULL,
  `target_id` varchar(255) NOT NULL,
  `target_type` varchar(255) NOT NULL,
  `tenant_id` varchar(255) DEFAULT NULL,
  `praiser_aid` varchar(255) NOT NULL,
  `praiser_name` varchar(255) DEFAULT NULL,
  `praiser_tid` varchar(255) DEFAULT NULL,
  `praiser_type` varchar(255) DEFAULT NULL,
  `praiser_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
