/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50730
 Source Host           : localhost:3306
 Source Schema         : mybatis-boot

 Target Server Type    : MySQL
 Target Server Version : 50730
 File Encoding         : 65001

 Date: 25/07/2024 10:34:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for isolation_test
-- ----------------------------
DROP TABLE IF EXISTS `isolation_test`;
CREATE TABLE `isolation_test`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `audit` tinyint(4) NOT NULL COMMENT '是否审核',
  `age` int(11) NOT NULL COMMENT '年龄',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of isolation_test
-- ----------------------------
INSERT INTO `isolation_test` VALUES (1, 'jack', 1, 12, '2024-07-25 10:25:28', '2024-07-25 10:25:28');
INSERT INTO `isolation_test` VALUES (2, 'rose', 1, 12, '2024-07-25 10:25:44', '2024-07-25 10:25:44');

SET FOREIGN_KEY_CHECKS = 1;
