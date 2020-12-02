/*
 Navicat Premium Data Transfer

 Source Server         : remote_mysql
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : 47.107.72.240:3306
 Source Schema         : siseoj_test

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 02/12/2020 09:33:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `gender` smallint(0) NULL DEFAULT NULL COMMENT '性别，0为保密，1为男，2为女',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `register_time` datetime(0) NULL DEFAULT NULL COMMENT '注册时间',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `motto` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '个性签名',
  `rating` int(0) NULL DEFAULT NULL COMMENT '排名分数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (1, 'root', 0, 'saykuray@foxmail.com', '88888888888', '2020-11-16 22:11:33', '2020-11-16 22:11:38', '世界那么大，我想去看看~', 2020);

SET FOREIGN_KEY_CHECKS = 1;
