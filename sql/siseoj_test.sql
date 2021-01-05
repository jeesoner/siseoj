/*
 Navicat Premium Data Transfer

 Source Server         : 47.107.72.240
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : 47.107.72.240:3306
 Source Schema         : siseoj_test

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 20/12/2020 22:44:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
                         `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
                         `role_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
                         `remark` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色描述',
                         `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                         `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'root', '超级管理员', '2020-12-20 22:40:34', '2020-12-20 22:40:37');
INSERT INTO `role` VALUES (2, 'admin', '管理员', '2020-12-20 22:40:55', '2020-12-20 22:40:59');

-- ----------------------------
-- Table structure for user_auth
-- ----------------------------
DROP TABLE IF EXISTS `user_auth`;
CREATE TABLE `user_auth`  (
                              `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
                              `username` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
                              `salt` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '盐值',
                              `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
                              `is_locked` bit(1) NOT NULL COMMENT '1为锁定，0为启用',
                              `login_time` datetime(0) NULL DEFAULT NULL COMMENT '最后一次登录时间',
                              `unlock_time` datetime(0) NULL DEFAULT NULL COMMENT '账户解锁时间',
                              `login_fail_count` tinyint(0) NULL DEFAULT NULL COMMENT '登录失败次数，超过3次锁定账户',
                              `user_info_id` bigint(0) NULL DEFAULT NULL COMMENT '用户信息表外键',
                              PRIMARY KEY (`id`) USING BTREE,
                              UNIQUE INDEX `username_unique`(`username`) USING BTREE,
                              INDEX `user_info_refpk_1`(`user_info_id`) USING BTREE,
                              CONSTRAINT `user_info_refpk_1` FOREIGN KEY (`user_info_id`) REFERENCES `user_info` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_auth
-- ----------------------------
INSERT INTO `user_auth` VALUES (1, 'root', NULL, 'root', b'0', NULL, NULL, NULL, 1);

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
                              `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
                              `nickname` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '昵称',
                              `username` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
                              `gender` tinyint(0) NULL DEFAULT NULL COMMENT '性别，0为保密，1为男，2为女',
                              `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
                              `phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
                              `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                              `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
                              `motto` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '个性签名',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (1, 'root', 'root', 0, 'saykuray@foxmail.com', '88888888888', '2020-11-16 22:11:33', '2020-11-16 22:11:38', '世界那么大，我想去看看~');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
                              `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
                              `user_info_id` bigint(0) NULL DEFAULT NULL COMMENT '用户信息表主键',
                              `role_id` bigint(0) NULL DEFAULT NULL COMMENT '角色表主键',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
