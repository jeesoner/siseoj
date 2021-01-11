/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Host           : localhost:3306
 Source Schema         : siseoj_test

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : 65001

 Date: 05/01/2021 23:58:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
                             `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                             `pid` bigint(20) NULL DEFAULT NULL COMMENT '上级菜单ID',
                             `sub_count` int(11) NULL DEFAULT 0 COMMENT '子菜单数目',
                             `type` int(11) NULL DEFAULT NULL COMMENT '菜单类型',
                             `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单标题',
                             `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组件名称',
                             `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组件',
                             `menu_sort` int(11) NULL DEFAULT NULL COMMENT '排序',
                             `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
                             `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '链接地址',
                             `i_frame` bit(1) NULL DEFAULT NULL COMMENT '是否外链',
                             `cache` bit(1) NULL DEFAULT b'0' COMMENT '缓存',
                             `hidden` bit(1) NULL DEFAULT b'0' COMMENT '隐藏',
                             `permission` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限',
                             `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
                             `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
                             `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
                             `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
                             PRIMARY KEY (`menu_id`) USING BTREE,
                             UNIQUE INDEX `uniq_title`(`title`) USING BTREE,
                             UNIQUE INDEX `uniq_name`(`name`) USING BTREE,
                             INDEX `nx_pid`(`pid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统菜单' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
                             `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                             `role_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
                             `remark` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色描述',
                             `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                             `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, 'root', '超级管理员', '2020-12-20 22:40:34', '2020-12-20 22:40:37');
INSERT INTO `sys_role` VALUES (2, 'admin', '管理员', '2020-12-20 22:40:55', '2020-12-20 22:40:59');

-- ----------------------------
-- Table structure for sys_roles_menus
-- ----------------------------
DROP TABLE IF EXISTS `sys_roles_menus`;
CREATE TABLE `sys_roles_menus`  (
                                    `menu_id` bigint(20) NOT NULL COMMENT '菜单外键',
                                    `role_id` bigint(20) NOT NULL COMMENT '角色外键',
                                    PRIMARY KEY (`menu_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
                             `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
                             `nickname` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
                             `username` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
                             `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
                             `gender` tinyint(0) NULL DEFAULT NULL COMMENT '性别，0保密、1男、2女',
                             `email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
                             `phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
                             `motto` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个性签名',
                             `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                             `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
                             `enabled` bit(1) NULL DEFAULT NULL COMMENT '状态：1启用、0禁用',
                             `last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '最后一次登录时间',
                             `unlock_time` datetime(0) NULL DEFAULT NULL COMMENT '解锁时间',
                             `login_fail_count` tinyint(0) NULL DEFAULT NULL COMMENT '登录失败次数',
                             PRIMARY KEY (`id`) USING BTREE,
                             UNIQUE INDEX `uniq_username`(`username`) USING BTREE COMMENT '用户名唯一索引'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'root', 'root', 'root', 0, 'saykuray@foxmail.com', '88888888888', '世界那么大，我想去看看~', '2020-11-16 22:11:33', '2020-11-16 22:11:38', b'1', NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'root', 'root', 0, 'saykuray@foxmail.com', '88888888888', '世界那么大，我想去看看~', '2020-11-16 22:11:33', '2020-11-16 22:11:38');

-- ----------------------------
-- Table structure for sys_user_auth
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_auth`;
CREATE TABLE `sys_user_auth`  (
                                  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                  `username` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
                                  `salt` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '盐值',
                                  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
                                  `is_locked` bit(1) NULL DEFAULT NULL COMMENT '1为锁定，0为启用',
                                  `login_time` datetime(0) NULL DEFAULT NULL COMMENT '最后一次登录时间',
                                  `unlock_time` datetime(0) NULL DEFAULT NULL COMMENT '账户解锁时间',
                                  `login_fail_count` tinyint(4) NULL DEFAULT NULL COMMENT '登录失败次数，超过3次锁定账户',
                                  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户信息表外键',
                                  PRIMARY KEY (`id`) USING BTREE,
                                  UNIQUE INDEX `uniq_name`(`username`) USING BTREE,
                                  INDEX `user_info_refpk_1`(`user_id`) USING BTREE,
                                  CONSTRAINT `user_info_refpk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_auth
-- ----------------------------
INSERT INTO `sys_user_auth` VALUES (1, 'root', NULL, 'root', b'0', NULL, NULL, NULL, 1);

-- ----------------------------
-- Table structure for sys_users_roles
-- ----------------------------
DROP TABLE IF EXISTS `sys_users_roles`;
CREATE TABLE `sys_users_roles`  (
                                    `user_id` bigint(20) NOT NULL COMMENT '用户信息表主键',
                                    `role_id` bigint(20) NOT NULL COMMENT '角色表主键',
                                    PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_users_roles
-- ----------------------------
INSERT INTO `sys_users_roles` VALUES (1, 1);

SET FOREIGN_KEY_CHECKS = 1;
