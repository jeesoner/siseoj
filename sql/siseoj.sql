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

 Date: 22/04/2021 10:50:47
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ct_contest
-- ----------------------------
DROP TABLE IF EXISTS `ct_contest`;
CREATE TABLE `ct_contest`  (
                               `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '比赛表主键',
                               `uid` bigint(20) UNSIGNED NOT NULL COMMENT '比赛创建者id',
                               `author` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '比赛创建者的用户名',
                               `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '比赛标题',
                               `type` int(11) NOT NULL DEFAULT 0 COMMENT '比赛类型：0普通比赛，1rating比赛',
                               `description` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '比赛说明',
                               `source` bigint(20) NULL DEFAULT 0 COMMENT '比赛来源，原创为0，克隆赛为比赛id',
                               `auth` int(11) NOT NULL COMMENT '0为公开赛，1为私有赛（访问有密）',
                               `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '比赛密码',
                               `start_time` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
                               `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
                               `duration` int(11) NULL DEFAULT NULL COMMENT '比赛时长(s)',
                               `seal_rank` bit(1) NULL DEFAULT b'0' COMMENT '是否开启封榜',
                               `seal_rank_time` datetime(0) NULL DEFAULT NULL COMMENT '封榜起始时间，一直到比赛结束，不刷新榜单',
                               `status` int(11) NULL DEFAULT NULL COMMENT '比赛状态：-1未开始，0进行中，1已结束',
                               `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                               `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
                               PRIMARY KEY (`id`) USING BTREE,
                               INDEX `uid`(`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ct_contest_problem
-- ----------------------------
DROP TABLE IF EXISTS `ct_contest_problem`;
CREATE TABLE `ct_contest_problem`  (
                                       `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
                                       `display_id` int(11) NULL DEFAULT NULL COMMENT '该题目在比赛中显示的id',
                                       `cid` bigint(20) NULL DEFAULT NULL COMMENT '比赛主键',
                                       `pid` bigint(20) NULL DEFAULT NULL COMMENT '题目主键',
                                       `display_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '该题目在比赛中的标题',
                                       `score` int(11) NULL DEFAULT 0 COMMENT '题目得分',
                                       `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                                       `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
                                       PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ct_contest_record
-- ----------------------------
DROP TABLE IF EXISTS `ct_contest_record`;
CREATE TABLE `ct_contest_record`  (
                                      `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
                                      `judge_id` bigint(20) UNSIGNED NOT NULL COMMENT '提交id，用于可重判',
                                      `cid` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '比赛id',
                                      `uid` bigint(20) NOT NULL COMMENT '用户id',
                                      `pid` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '题目id',
                                      `contest_pid` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '比赛中的题目的id',
                                      `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
                                      `display_id` int(11) NULL DEFAULT NULL COMMENT '比赛中展示的id',
                                      `status` int(11) NULL DEFAULT NULL COMMENT '提交结果，0表示未AC通过不罚时，1表示AC通过，-1为未AC通过算罚时',
                                      `submit_time` datetime(0) NOT NULL COMMENT '具体提交时间',
                                      `time` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '提交时间，为提交时间减去比赛时间',
                                      `score` int(11) NULL DEFAULT NULL COMMENT 'rating比赛的得分',
                                      `first_blood` bit(1) NULL DEFAULT b'0' COMMENT '是否为一血AC',
                                      `checked` bit(1) NULL DEFAULT b'0' COMMENT 'AC是否已校验',
                                      `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
                                      `modify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
                                      PRIMARY KEY (`id`, `judge_id`) USING BTREE,
                                      INDEX `uid`(`uid`) USING BTREE,
                                      INDEX `pid`(`pid`) USING BTREE,
                                      INDEX `contest_pid`(`contest_pid`) USING BTREE,
                                      INDEX `judge_id`(`judge_id`) USING BTREE,
                                      INDEX `index_cid`(`cid`) USING BTREE,
                                      INDEX `index_time`(`time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ct_contest_register
-- ----------------------------
DROP TABLE IF EXISTS `ct_contest_register`;
CREATE TABLE `ct_contest_register`  (
                                        `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
                                        `cid` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '比赛id',
                                        `uid` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '用户id',
                                        `status` int(11) NULL DEFAULT 0 COMMENT '注册状态：0正常， 1失效',
                                        `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                                        `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
                                        PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for discuss
-- ----------------------------
DROP TABLE IF EXISTS `discuss`;
CREATE TABLE `discuss`  (
                            `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '帖子主键',
                            `uid` bigint(20) NOT NULL COMMENT '用户表外键',
                            `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标题',
                            `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
                            `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '帖子内容',
                            `views` int(11) UNSIGNED NULL DEFAULT 0 COMMENT '浏览数',
                            `likes` int(11) UNSIGNED NULL DEFAULT 0 COMMENT '点赞数',
                            `enable` bit(1) NULL DEFAULT b'1' COMMENT '是否禁用：默认1，禁用0',
                            `priority` int(11) NULL DEFAULT 0 COMMENT '优先级：默认为0',
                            `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                            `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_judge
-- ----------------------------
DROP TABLE IF EXISTS `oj_judge`;
CREATE TABLE `oj_judge`  (
                             `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '评测表主键',
                             `pid` bigint(20) UNSIGNED NOT NULL COMMENT '题目表外键',
                             `cid` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '比赛表外键',
                             `uid` bigint(20) UNSIGNED NOT NULL COMMENT '用户表外键',
                             `contest_pid` bigint(20) NULL DEFAULT 0 COMMENT '比赛题目外键，不是比赛提交默认为0',
                             `username` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
                             `status` tinyint(4) NULL DEFAULT NULL COMMENT '评测结果码',
                             `error_message` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '错误信息',
                             `language` tinyint(4) NOT NULL COMMENT '代码语言，1：C/C++，2：Java',
                             `code` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '提交的代码',
                             `score` int(11) NULL DEFAULT 0 COMMENT '得分',
                             `time` int(11) NULL DEFAULT NULL COMMENT '运行时间（ms）',
                             `memory` int(11) NULL DEFAULT NULL COMMENT '运行内存（MB）',
                             `submit_time` datetime(0) NULL DEFAULT NULL COMMENT '提交时间',
                             `code_length` int(11) NULL DEFAULT NULL COMMENT '代码长度',
                             `share` bit(1) NULL DEFAULT NULL COMMENT '0：仅自己可见，1：所有人可见',
                             `create_time` datetime(0) NULL DEFAULT NULL,
                             `modify_time` datetime(0) NULL DEFAULT NULL,
                             PRIMARY KEY (`id`, `pid`, `cid`, `uid`) USING BTREE,
                             INDEX `pid`(`pid`) USING BTREE,
                             INDEX `uid`(`uid`) USING BTREE,
                             INDEX `username`(`username`) USING BTREE,
                             INDEX `cid`(`cid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 57 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_judge_case
-- ----------------------------
DROP TABLE IF EXISTS `oj_judge_case`;
CREATE TABLE `oj_judge_case`  (
                                  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '评测样例表主键',
                                  `judge_id` bigint(20) UNSIGNED NOT NULL COMMENT '评测表主键',
                                  `uid` bigint(20) UNSIGNED NOT NULL COMMENT '用户表外键',
                                  `pid` bigint(20) UNSIGNED NOT NULL COMMENT '题目表外键',
                                  `case_id` bigint(20) NOT NULL COMMENT '题目样例表外键',
                                  `status` int(11) NULL DEFAULT NULL COMMENT '评测结果码',
                                  `time` int(11) NULL DEFAULT NULL COMMENT '样例运行时间（ms）',
                                  `memory` int(11) NULL DEFAULT NULL COMMENT '样例运行内存（MB）',
                                  `score` int(11) NULL DEFAULT NULL COMMENT '样例得分',
                                  `create_time` datetime(0) NULL DEFAULT NULL,
                                  `modify_time` datetime(0) NULL DEFAULT NULL,
                                  PRIMARY KEY (`id`, `judge_id`, `uid`, `pid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pb_problem
-- ----------------------------
DROP TABLE IF EXISTS `pb_problem`;
CREATE TABLE `pb_problem`  (
                               `pid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '题目主键',
                               `source_id` bigint(20) NULL DEFAULT NULL COMMENT '题目来源表外键',
                               `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '题目标题',
                               `author` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作者',
                               `difficulty` int(11) NULL DEFAULT NULL COMMENT '题目难度，0简单，1中等，2困难',
                               `auth` int(11) NULL DEFAULT NULL COMMENT '默认为1公开，2为比赛题目，3私有',
                               `time_limit` int(11) NULL DEFAULT NULL COMMENT '时间限制（ms）',
                               `memory_limit` int(11) NULL DEFAULT NULL COMMENT '空间限制（MB）',
                               `description` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '题目描述',
                               `input` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '输入描述',
                               `output` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '输出描述',
                               `examples` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '题面样例',
                               `hint` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '提示',
                               `special_code` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '特判程序代码，为空则不是特判',
                               `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `modify_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
                               PRIMARY KEY (`pid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pb_problem_case
-- ----------------------------
DROP TABLE IF EXISTS `pb_problem_case`;
CREATE TABLE `pb_problem_case`  (
                                    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '题目样例表主键',
                                    `pid` bigint(20) NOT NULL COMMENT '题目表外键',
                                    `input_case` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '输入样例',
                                    `output_case` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '输出样例',
                                    `score` int(11) NULL DEFAULT NULL COMMENT '得分',
                                    `status` bit(1) NULL DEFAULT b'1' COMMENT '是否可用',
                                    `create_time` datetime(0) NULL DEFAULT NULL,
                                    `modify_time` datetime(0) NULL DEFAULT NULL,
                                    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pb_problem_count
-- ----------------------------
DROP TABLE IF EXISTS `pb_problem_count`;
CREATE TABLE `pb_problem_count`  (
                                     `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '题目统计表主键',
                                     `pid` bigint(20) NOT NULL COMMENT '题目表主键',
                                     `total` int(11) NULL DEFAULT 0 COMMENT '总次数',
                                     `accept` int(11) NULL DEFAULT 0 COMMENT '通过次数',
                                     `version` bigint(20) NULL DEFAULT 0 COMMENT '版本号',
                                     `create_time` datetime(0) NULL DEFAULT NULL,
                                     `modify_time` datetime(0) NULL DEFAULT NULL,
                                     PRIMARY KEY (`id`) USING BTREE,
                                     UNIQUE INDEX `uniq_pid`(`pid`) USING BTREE COMMENT '题目ID唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pb_problems_tags
-- ----------------------------
DROP TABLE IF EXISTS `pb_problems_tags`;
CREATE TABLE `pb_problems_tags`  (
                                     `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
                                     `pid` bigint(20) UNSIGNED NOT NULL COMMENT '题目表主键',
                                     `tid` bigint(20) UNSIGNED NOT NULL COMMENT '标签表主键',
                                     `create_time` datetime(0) NULL DEFAULT NULL,
                                     `modify_time` datetime(0) NULL DEFAULT NULL,
                                     PRIMARY KEY (`id`) USING BTREE,
                                     INDEX `pid`(`pid`) USING BTREE,
                                     INDEX `tid`(`tid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pb_source
-- ----------------------------
DROP TABLE IF EXISTS `pb_source`;
CREATE TABLE `pb_source`  (
                              `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '来源表主键',
                              `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '来源OJ',
                              `create_time` datetime(0) NULL DEFAULT NULL,
                              `modify_time` datetime(0) NULL DEFAULT NULL,
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pb_source
-- ----------------------------
INSERT INTO `pb_source` VALUES (1, 'siseoj', '2021-03-08 01:00:16', '2021-03-08 01:00:28');
INSERT INTO `pb_source` VALUES (2, 'HDU', '2021-03-08 01:00:16', '2021-03-08 01:00:28');
INSERT INTO `pb_source` VALUES (3, 'POJ', '2021-03-08 01:00:16', '2021-03-08 01:00:28');
INSERT INTO `pb_source` VALUES (4, 'UVA', '2021-03-08 01:00:16', '2021-03-08 01:00:28');
INSERT INTO `pb_source` VALUES (5, 'BZOJ', '2021-03-08 01:00:16', '2021-03-08 01:00:28');
INSERT INTO `pb_source` VALUES (6, 'UOJ', '2021-03-08 01:00:16', '2021-03-08 01:00:28');
INSERT INTO `pb_source` VALUES (7, '51nod', '2021-03-08 01:00:16', '2021-03-08 01:00:28');
INSERT INTO `pb_source` VALUES (8, 'codeforces', '2021-03-08 01:00:16', '2021-03-08 01:00:28');
INSERT INTO `pb_source` VALUES (9, 'Topcoder', '2021-03-08 01:00:16', '2021-03-08 01:00:28');
INSERT INTO `pb_source` VALUES (10, 'SPOJ', '2021-03-08 01:00:16', '2021-03-08 01:00:28');
INSERT INTO `pb_source` VALUES (11, 'leetcode', '2021-03-08 01:00:16', '2021-03-08 01:00:28');
INSERT INTO `pb_source` VALUES (12, 'ZOJ', '2021-03-08 01:00:16', '2021-03-08 01:00:28');
INSERT INTO `pb_source` VALUES (13, '洛谷', '2021-03-08 01:00:16', '2021-03-08 01:00:28');

-- ----------------------------
-- Table structure for pb_tag
-- ----------------------------
DROP TABLE IF EXISTS `pb_tag`;
CREATE TABLE `pb_tag`  (
                           `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '标签表主键',
                           `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标签名称',
                           `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                           `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
                             `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                             `pid` bigint(20) NULL DEFAULT NULL COMMENT '上级菜单ID',
                             `sub_count` int(11) NULL DEFAULT 0 COMMENT '子菜单数目',
                             `type` int(11) NULL DEFAULT NULL COMMENT '菜单类型',
                             `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单标题',
                             `component_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组件名称',
                             `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组件',
                             `redirect` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '重定向路径',
                             `menu_sort` int(11) NULL DEFAULT NULL COMMENT '排序',
                             `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
                             `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '链接地址',
                             `i_frame` bit(1) NULL DEFAULT b'0' COMMENT '是否外链',
                             `cache` bit(1) NULL DEFAULT b'0' COMMENT '缓存',
                             `hidden` bit(1) NULL DEFAULT b'0' COMMENT '隐藏',
                             `permission` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限',
                             `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
                             `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
                             PRIMARY KEY (`id`) USING BTREE,
                             UNIQUE INDEX `uniq_title`(`title`) USING BTREE,
                             UNIQUE INDEX `uniq_name`(`component_name`) USING BTREE,
                             INDEX `nx_pid`(`pid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统菜单' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, NULL, 3, 0, '系统管理', NULL, NULL, '/system/user', 1, 'system', 'system', b'0', b'0', b'0', NULL, '2021-03-08 02:26:51', '2021-03-08 02:26:53');
INSERT INTO `sys_menu` VALUES (2, 1, 3, 1, '用户管理', 'User', 'system/user/index', NULL, 2, 'peoples', 'user', b'0', b'0', b'0', 'user:list', '2021-03-08 02:28:36', '2021-03-08 02:28:38');
INSERT INTO `sys_menu` VALUES (3, 1, 3, 1, '角色管理', 'Role', 'system/role/index', NULL, 3, 'role', 'role', b'0', b'0', b'0', 'role:list', '2021-03-08 02:29:28', '2021-03-08 02:29:30');
INSERT INTO `sys_menu` VALUES (4, 1, 3, 1, '菜单管理', 'Menu', 'system/menu/index', NULL, 4, 'menu', 'menu', b'0', b'0', b'0', 'menu:list', '2021-03-08 02:29:32', '2021-03-08 02:29:34');
INSERT INTO `sys_menu` VALUES (5, NULL, 4, 0, '题库管理', NULL, NULL, '/problemset/problems', 10, 'icon', 'problemset', b'0', b'0', b'0', '', '2021-03-08 02:26:51', '2021-04-21 10:52:01');
INSERT INTO `sys_menu` VALUES (6, 5, 2, 1, '题目管理', 'Problem', 'problemset/problems/edit', NULL, 11, 'education', 'problems', b'0', b'0', b'0', 'problem:list', '2021-03-08 02:26:51', '2021-04-21 11:18:10');
INSERT INTO `sys_menu` VALUES (7, 5, 3, 1, '标签管理', 'Tag', 'problemset/tags/index', NULL, 13, 'tab', 'tags', b'0', b'0', b'0', 'tag:list', '2021-03-08 02:26:51', '2021-04-21 11:24:31');
INSERT INTO `sys_menu` VALUES (8, 5, 3, 1, '来源管理', 'Source', 'problemset/sources/index', NULL, 14, 'source', 'sources', b'0', b'0', b'0', 'source:list', '2021-03-08 02:26:51', '2021-04-21 11:18:26');
INSERT INTO `sys_menu` VALUES (9, NULL, 2, 0, '比赛管理', NULL, NULL, '/contest/list', 20, 'chart', 'contest', b'0', b'0', b'0', '', '2021-03-08 02:26:51', '2021-04-21 10:50:24');
INSERT INTO `sys_menu` VALUES (10, 9, 2, 1, '比赛列表', 'ContestList', 'contest/contest', NULL, 21, 'list', 'list', b'0', b'0', b'0', 'contest:list', '2021-03-08 02:26:51', '2021-04-21 11:16:40');
INSERT INTO `sys_menu` VALUES (11, 6, 0, 2, '题目编辑', NULL, NULL, NULL, 2, '', '', b'0', b'0', b'0', 'problem:edit', '2021-03-08 02:26:51', '2021-04-21 21:40:35');
INSERT INTO `sys_menu` VALUES (12, 6, 0, 2, '题目删除', NULL, NULL, NULL, 3, '', '', b'0', b'0', b'0', 'problem:del', '2021-03-08 02:26:51', '2021-04-21 21:40:04');
INSERT INTO `sys_menu` VALUES (14, 9, 0, 1, '创建比赛', 'ContestCreate', 'contest/contestEdit', NULL, 22, 'edit', 'create', b'0', b'0', b'0', 'contest:add', '2021-04-21 11:14:44', '2021-04-21 11:17:19');
INSERT INTO `sys_menu` VALUES (15, 5, 0, 1, '创建题目', 'ProblemEdit', 'problemset/problems/edit', NULL, 12, 'edit', 'problems/edit/:pid?', b'0', b'0', b'0', 'problem:add', '2021-04-21 11:24:14', '2021-04-21 11:24:14');
INSERT INTO `sys_menu` VALUES (16, 2, 0, 2, '用户新增', NULL, NULL, NULL, 2, NULL, NULL, b'0', b'0', b'0', 'user:add', '2021-04-21 11:26:08', '2021-04-21 11:26:08');
INSERT INTO `sys_menu` VALUES (17, 2, 0, 2, '用户编辑', NULL, NULL, NULL, 3, NULL, NULL, b'0', b'0', b'0', 'user:edit', '2021-04-21 11:26:36', '2021-04-21 11:26:36');
INSERT INTO `sys_menu` VALUES (18, 2, 0, 2, '用户删除', NULL, NULL, NULL, 4, NULL, NULL, b'0', b'0', b'0', 'user:del', '2021-04-21 11:27:30', '2021-04-21 11:27:30');
INSERT INTO `sys_menu` VALUES (19, 3, 0, 2, '角色新增', NULL, NULL, NULL, 2, NULL, NULL, b'0', b'0', b'0', 'user:add', '2021-04-21 11:42:16', '2021-04-21 11:42:16');
INSERT INTO `sys_menu` VALUES (20, 3, 0, 2, '角色编辑', NULL, NULL, NULL, 3, NULL, NULL, b'0', b'0', b'0', 'user:edit', '2021-04-21 11:42:38', '2021-04-21 11:42:38');
INSERT INTO `sys_menu` VALUES (21, 3, 0, 2, '角色删除', NULL, NULL, NULL, 4, NULL, NULL, b'0', b'0', b'0', 'user:del', '2021-04-21 11:42:56', '2021-04-21 11:42:56');
INSERT INTO `sys_menu` VALUES (22, 4, 0, 2, '菜单新增', NULL, NULL, NULL, 2, NULL, NULL, b'0', b'0', b'0', 'menu:add', '2021-04-21 11:43:42', '2021-04-21 11:43:42');
INSERT INTO `sys_menu` VALUES (23, 4, 0, 2, '菜单编辑', NULL, NULL, NULL, 3, NULL, NULL, b'0', b'0', b'0', 'menu:edit', '2021-04-21 11:44:09', '2021-04-21 11:44:09');
INSERT INTO `sys_menu` VALUES (24, 4, 0, 2, '菜单删除', NULL, NULL, NULL, 4, NULL, NULL, b'0', b'0', b'0', 'menu:del', '2021-04-21 11:44:24', '2021-04-21 11:44:24');
INSERT INTO `sys_menu` VALUES (25, 7, 0, 2, '标签新增', NULL, NULL, NULL, 2, NULL, NULL, b'0', b'0', b'0', 'tag:add', '2021-04-22 00:23:21', '2021-04-22 00:23:21');
INSERT INTO `sys_menu` VALUES (26, 7, 0, 2, '标签编辑', NULL, NULL, NULL, 3, NULL, NULL, b'0', b'0', b'0', 'tag:edit', '2021-04-22 00:23:51', '2021-04-22 00:23:51');
INSERT INTO `sys_menu` VALUES (27, 7, 0, 2, '标签删除', NULL, NULL, NULL, 4, '', '', b'0', b'0', b'0', 'tag:del', '2021-04-22 00:24:14', '2021-04-22 00:25:45');
INSERT INTO `sys_menu` VALUES (28, 8, 0, 2, '来源新增', NULL, NULL, NULL, 2, NULL, NULL, b'0', b'0', b'0', 'source:add', '2021-04-22 00:26:18', '2021-04-22 00:26:18');
INSERT INTO `sys_menu` VALUES (29, 8, 0, 2, '来源编辑', NULL, NULL, NULL, 3, NULL, NULL, b'0', b'0', b'0', 'source:edit', '2021-04-22 00:26:43', '2021-04-22 00:26:43');
INSERT INTO `sys_menu` VALUES (30, 8, 0, 2, '来源删除', NULL, NULL, NULL, 4, NULL, NULL, b'0', b'0', b'0', 'source:del', '2021-04-22 00:27:11', '2021-04-22 00:27:11');
INSERT INTO `sys_menu` VALUES (32, 10, 0, 2, '比赛编辑', NULL, NULL, NULL, 3, '', '', b'0', b'0', b'0', 'contest:edit', '2021-04-22 00:27:57', '2021-04-22 00:30:07');
INSERT INTO `sys_menu` VALUES (33, 10, 0, 2, '比赛删除', NULL, NULL, NULL, 2, '', '', b'0', b'0', b'0', 'contest:del', '2021-04-22 00:28:11', '2021-04-22 00:30:10');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
                             `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                             `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
                             `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色描述',
                             `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                             `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_roles_menus
-- ----------------------------
DROP TABLE IF EXISTS `sys_roles_menus`;
CREATE TABLE `sys_roles_menus`  (
                                    `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
                                    `role_id` bigint(20) NOT NULL COMMENT '角色ID',
                                    PRIMARY KEY (`menu_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
                             `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                             `nickname` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
                             `username` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
                             `is_admin` bit(1) NULL DEFAULT b'0' COMMENT '是否管理员，默认不是',
                             `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
                             `gender` tinyint(4) NULL DEFAULT NULL COMMENT '性别，0保密、1男、2女',
                             `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
                             `phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
                             `motto` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '个性签名',
                             `school` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学校',
                             `course` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '专业',
                             `github` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'github地址',
                             `blog` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '博客地址',
                             `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像地址',
                             `avatar_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像真实路径',
                             `enabled` bit(1) NOT NULL COMMENT '状态：1启用、0禁用',
                             `last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '最后一次登录时间',
                             `unlock_time` datetime(0) NULL DEFAULT NULL COMMENT '解锁时间',
                             `login_fail_count` tinyint(4) NULL DEFAULT NULL COMMENT '登录失败次数',
                             `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                             `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
                             PRIMARY KEY (`id`) USING BTREE,
                             UNIQUE INDEX `uniq_username`(`username`) USING BTREE COMMENT '用户名唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, '系统管理员', 'root', b'1', '$2a$10$rh7XO.20V3ZVaUyyCTH39u0kNeWL230gZ70tiJOsjjqYASomQPuz2', 0, 'saykuray@foxmail.com', '88888888888', '世界那么大，我想去看看~', '广州软件学院', '网络工程', 'https://github.com/cijee', NULL, 'avatar-20210330025638479.png', 'D:\\siseoj\\avatar\\avatar-20210330025638479.png', b'1', NULL, NULL, NULL, '2020-11-16 22:11:33', '2021-04-21 22:43:22');

-- ----------------------------
-- Table structure for sys_user_accept
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_accept`;
CREATE TABLE `sys_user_accept`  (
                                    `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户通过题目表',
                                    `uid` bigint(20) UNSIGNED NOT NULL COMMENT '用户外键',
                                    `pid` bigint(20) UNSIGNED NOT NULL COMMENT '题目外键',
                                    `judge_id` bigint(20) UNSIGNED NOT NULL COMMENT '评测外键',
                                    `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                                    `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
                                    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_record
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_record`;
CREATE TABLE `sys_user_record`  (
                                    `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户记录表主键',
                                    `uid` bigint(20) NOT NULL COMMENT '用户表主键',
                                    `submissions` int(11) NULL DEFAULT 0 COMMENT '总提交数',
                                    `rating` int(11) NULL DEFAULT 0 COMMENT 'rank分数',
                                    `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                                    `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
                                    PRIMARY KEY (`id`, `uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
-- Table structure for tool_email_config
-- ----------------------------
DROP TABLE IF EXISTS `tool_email_config`;
CREATE TABLE `tool_email_config`  (
                                      `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
                                      `host` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮件服务器SMTP地址',
                                      `port` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮件服务器SMTP端口',
                                      `user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发件者用户名',
                                      `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
                                      `from_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收件人',
                                      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
