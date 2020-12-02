/*
 Navicat Premium Data Transfer

 Source Server         : remote_mysql
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : 47.107.72.240:3306
 Source Schema         : siseoj

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 02/12/2020 09:33:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_acb_record
-- ----------------------------
DROP TABLE IF EXISTS `t_acb_record`;
CREATE TABLE `t_acb_record`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `acb_change` double(255, 0) NOT NULL COMMENT 'AC币改变值，负数为减，正数为加',
  `current_acb` double(11, 0) NOT NULL COMMENT '记录时的AC币数量',
  `reason` int(0) NOT NULL DEFAULT 0 COMMENT 'AC币变更原因：0：默认；',
  `time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录时间',
  `mark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '原因描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_border_honor_rank
-- ----------------------------
DROP TABLE IF EXISTS `t_border_honor_rank`;
CREATE TABLE `t_border_honor_rank`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `username_one` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `real_name_one` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `username_two` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `real_name_two` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `username_three` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `real_name_three` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `reward_date` date NULL DEFAULT NULL,
  `register_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登记该条记录的时间',
  `contest_level` int(0) NULL DEFAULT NULL COMMENT '比赛等级',
  `award_level` int(0) NULL DEFAULT NULL COMMENT '获奖等级',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '获奖描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 78 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_bug_report
-- ----------------------------
DROP TABLE IF EXISTS `t_bug_report`;
CREATE TABLE `t_bug_report`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名，如果为空则为匿名',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'bug标题',
  `current_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '报告页面',
  `type` int(0) NOT NULL COMMENT '0：其他 1：系统漏洞 2：功能异常 3：逻辑错误 4：界面问题 ',
  `text` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'bug内容',
  `report_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '反馈时间',
  `is_fixed` smallint(0) NULL DEFAULT 0 COMMENT '是否解决',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `username`(`username`) USING BTREE,
  INDEX `type`(`type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_challenge_block
-- ----------------------------
DROP TABLE IF EXISTS `t_challenge_block`;
CREATE TABLE `t_challenge_block`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '模块名称',
  `block_type` int(0) NOT NULL COMMENT '模块类型',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '模块描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 612 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_challenge_block_condition
-- ----------------------------
DROP TABLE IF EXISTS `t_challenge_block_condition`;
CREATE TABLE `t_challenge_block_condition`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `block_id` int(0) NOT NULL COMMENT '挑战模式模块ID',
  `precondition_block_id` int(0) NOT NULL COMMENT '前置挑战模式模块ID',
  `precondition_unlock_score` int(0) NOT NULL COMMENT '前置模块需要解锁的积分',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 158 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_challenge_block_problem
-- ----------------------------
DROP TABLE IF EXISTS `t_challenge_block_problem`;
CREATE TABLE `t_challenge_block_problem`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `block_id` int(0) NOT NULL COMMENT '挑战模式模块ID',
  `problem_order` int(0) NOT NULL COMMENT '模块内的题目顺序',
  `problem_id` int(0) NOT NULL COMMENT '题目ID',
  `score` int(0) NOT NULL DEFAULT 0 COMMENT '题目分值',
  `reward_acb` int(0) NOT NULL DEFAULT 0 COMMENT '解锁后奖励ACB',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 552 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_challenge_user_open_block
-- ----------------------------
DROP TABLE IF EXISTS `t_challenge_user_open_block`;
CREATE TABLE `t_challenge_user_open_block`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `block_id` int(0) NOT NULL,
  `unlock_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '模块解锁时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16402 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_contest_info
-- ----------------------------
DROP TABLE IF EXISTS `t_contest_info`;
CREATE TABLE `t_contest_info`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `contest_id` int(0) NULL DEFAULT NULL COMMENT '比赛编号',
  `contest_kind` int(0) NULL DEFAULT NULL COMMENT '比赛类型： 0：练习；1：积分；2：趣味；3：正式；4：隐藏；5：DIY',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '比赛标题',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '比赛介绍',
  `create_user` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '比赛开设人',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '比赛创建时间',
  `begin_time` datetime(0) NOT NULL COMMENT '比赛开始时间',
  `end_time` datetime(0) NOT NULL COMMENT '比赛结束时间',
  `permission_type` int(0) NOT NULL DEFAULT 0 COMMENT '权限类型： 0：公开；1：密码；2：私有；3：注册；4：正式；5：组队',
  `password` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '需要密码时的密码',
  `register_begin_time` datetime(0) NULL DEFAULT NULL COMMENT '需要注册参赛信息时的注册开始时间',
  `register_end_time` datetime(0) NULL DEFAULT NULL COMMENT '需要注册参赛信息时的注册结束时间',
  `computer_rating` tinyint(1) NULL DEFAULT 0 COMMENT '是否计算积分',
  `rank_type` int(0) NOT NULL COMMENT '计分方式',
  `problem_put_tag` tinyint(1) NULL DEFAULT 1 COMMENT '题目是否可以贴标签： 0：不可以，1：可以，定义同下',
  `status_read_out` tinyint(1) NULL DEFAULT 1 COMMENT '是否可以查看评测列表',
  `show_register_list` tinyint(1) NULL DEFAULT 1 COMMENT '展示注册名单',
  `show_border_list` tinyint(1) NULL DEFAULT 1 COMMENT '展示比赛版单',
  `show_other_status` tinyint(1) NULL DEFAULT 1 COMMENT '展示别人的代码',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `contest_id`(`contest_id`) USING BTREE,
  INDEX `create_user`(`create_user`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 400 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_contest_problem
-- ----------------------------
DROP TABLE IF EXISTS `t_contest_problem`;
CREATE TABLE `t_contest_problem`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `contest_id` int(0) NULL DEFAULT NULL COMMENT '比赛编号',
  `problem_id` int(0) NULL DEFAULT NULL COMMENT '题目的题号',
  `problem_order` int(0) NULL DEFAULT NULL COMMENT '题目在比赛内的顺序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_contest_register_user
-- ----------------------------
DROP TABLE IF EXISTS `t_contest_register_user`;
CREATE TABLE `t_contest_register_user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `contest_id` int(0) NOT NULL,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `register_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `review_status` smallint(0) NULL DEFAULT 0 COMMENT '审核是否通过: 0:还未审核；1：审核通过；2：审核失败',
  `review_info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '审核反馈信息',
  `review_time` datetime(0) NULL DEFAULT NULL COMMENT '审核时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `contest_id`(`contest_id`) USING BTREE,
  INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_discuss_post
-- ----------------------------
DROP TABLE IF EXISTS `t_discuss_post`;
CREATE TABLE `t_discuss_post`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '帖子建立时间',
  `author` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `priority` double NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1132 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_discuss_reply_post
-- ----------------------------
DROP TABLE IF EXISTS `t_discuss_reply_post`;
CREATE TABLE `t_discuss_reply_post`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `discuss_id` int(0) NOT NULL COMMENT '回帖帖子编号',
  `reply_order` int(0) NOT NULL COMMENT '回帖楼层顺序',
  `time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `author` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `text` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '帖子内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_judge_result
-- ----------------------------
DROP TABLE IF EXISTS `t_judge_result`;
CREATE TABLE `t_judge_result`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `judge_id` int(0) NULL DEFAULT NULL COMMENT '评测ID',
  `info` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评测返回的结果',
  `time` datetime(0) NULL DEFAULT NULL COMMENT '反馈时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `judge_id`(`judge_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 180 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_judge_status
-- ----------------------------
DROP TABLE IF EXISTS `t_judge_status`;
CREATE TABLE `t_judge_status`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `problem_id` int(0) NOT NULL,
  `contest_id` int(0) NULL DEFAULT -1,
  `language` int(0) NOT NULL COMMENT '使用语言',
  `submit_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '提交时间',
  `result` int(0) NOT NULL COMMENT '评测结果',
  `score` int(0) NULL DEFAULT NULL COMMENT '如果是spj类型的题目，这里显示得分',
  `time_used` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评测耗时',
  `memory_used` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '内存消耗',
  `code` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评测代码',
  `code_length` int(0) NOT NULL COMMENT '代码长度',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `problem_id`(`problem_id`) USING BTREE,
  INDEX `contest_id`(`contest_id`) USING BTREE,
  INDEX `username`(`username`) USING BTREE,
  INDEX `result`(`result`) USING BTREE,
  INDEX `language`(`language`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 181 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_mall_goods
-- ----------------------------
DROP TABLE IF EXISTS `t_mall_goods`;
CREATE TABLE `t_mall_goods`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称',
  `cost` int(0) NOT NULL DEFAULT 0 COMMENT '所需价格',
  `goods_type` int(0) NULL DEFAULT NULL COMMENT '商品大类类型：0：默认；1：实体物品；2：虚拟物品',
  `stock` int(0) NOT NULL DEFAULT -1 COMMENT '库存数量，-1为不限量',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '商品介绍',
  `picture_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '图片url地址',
  `visible` tinyint(0) NULL DEFAULT 1 COMMENT '普通用户是否可见：0：不可见；1：可见',
  `shelf_user` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '上传用户',
  `shelf_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上架时间',
  `buy_limit` int(0) NULL DEFAULT NULL COMMENT '购买限制',
  `buy_verify_limit` int(0) NULL DEFAULT 0 COMMENT '购买权限限制：0：登录后不限制；',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 46 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_mall_order
-- ----------------------------
DROP TABLE IF EXISTS `t_mall_order`;
CREATE TABLE `t_mall_order`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `goods_id` int(0) NOT NULL,
  `origin_cost` int(0) NOT NULL COMMENT '商品初始价格',
  `real_cost` int(0) NOT NULL COMMENT '实际付出价格',
  `order_user` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '购买用户',
  `order_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '下单时间',
  `review_user` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '审核订单用户',
  `order_status` smallint(0) NULL DEFAULT NULL COMMENT '订单状态: 0:无需审核；1：等待审核；2：订单失效；3：订单成交',
  `order_cancel` tinyint(0) NULL DEFAULT 0 COMMENT '用户是否主动取消：0：未取消订单；1：取消订单',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_permission_type
-- ----------------------------
DROP TABLE IF EXISTS `t_permission_type`;
CREATE TABLE `t_permission_type`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `permission_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 114 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_permission_type
-- ----------------------------
INSERT INTO `t_permission_type` VALUES (0, '管理员认定');
INSERT INTO `t_permission_type` VALUES (11, '用户列表查看');
INSERT INTO `t_permission_type` VALUES (12, '奖励');
INSERT INTO `t_permission_type` VALUES (13, '重置密码');
INSERT INTO `t_permission_type` VALUES (14, '用户称号管理');
INSERT INTO `t_permission_type` VALUES (15, '授予称号');
INSERT INTO `t_permission_type` VALUES (16, '认证审核');
INSERT INTO `t_permission_type` VALUES (17, '用户签到记录查询');
INSERT INTO `t_permission_type` VALUES (21, '管理员列表查看');
INSERT INTO `t_permission_type` VALUES (22, '修改权限');
INSERT INTO `t_permission_type` VALUES (23, '移除管理员');
INSERT INTO `t_permission_type` VALUES (31, '本地题库管理');
INSERT INTO `t_permission_type` VALUES (32, '爬取暂存题库管理');
INSERT INTO `t_permission_type` VALUES (33, '远程题库管理');
INSERT INTO `t_permission_type` VALUES (34, '题目标签管理');
INSERT INTO `t_permission_type` VALUES (41, '商品管理');
INSERT INTO `t_permission_type` VALUES (42, '订单管理');
INSERT INTO `t_permission_type` VALUES (51, '比赛管理');
INSERT INTO `t_permission_type` VALUES (52, '报名审核');
INSERT INTO `t_permission_type` VALUES (61, '排名管理');
INSERT INTO `t_permission_type` VALUES (71, '评测管理');
INSERT INTO `t_permission_type` VALUES (81, '挑战管理');
INSERT INTO `t_permission_type` VALUES (91, '论坛管理');
INSERT INTO `t_permission_type` VALUES (101, '爬虫服务器管理');
INSERT INTO `t_permission_type` VALUES (102, '爬虫列表管理');
INSERT INTO `t_permission_type` VALUES (103, '全站爬取');
INSERT INTO `t_permission_type` VALUES (104, '范围爬取');
INSERT INTO `t_permission_type` VALUES (105, '题目查重');
INSERT INTO `t_permission_type` VALUES (106, '题目本地化');
INSERT INTO `t_permission_type` VALUES (111, '系统设置');
INSERT INTO `t_permission_type` VALUES (112, '新增通知');
INSERT INTO `t_permission_type` VALUES (113, '查看系统反馈');

-- ----------------------------
-- Table structure for t_problem_difficult
-- ----------------------------
DROP TABLE IF EXISTS `t_problem_difficult`;
CREATE TABLE `t_problem_difficult`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `problem_id` int(0) NULL DEFAULT NULL,
  `difficult_level` smallint(0) NULL DEFAULT NULL COMMENT '难度级别 1~3 ，越高越难',
  `problem_type` int(0) NULL DEFAULT 0 COMMENT '题目基本类型，0~6',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `problem_id`(`problem_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2615 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_problem_info
-- ----------------------------
DROP TABLE IF EXISTS `t_problem_info`;
CREATE TABLE `t_problem_info`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `problem_id` int(0) NULL DEFAULT NULL COMMENT '题目的题号',
  `title` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '题目标题',
  `belong_oj_id` int(0) NOT NULL DEFAULT 0 COMMENT '题目来源哪个OJ, 0为本地',
  `belong_problem_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '题目在来源OJ里的ID',
  `author` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'spider' COMMENT '出题人',
  `total_submit` int(0) NOT NULL DEFAULT 0 COMMENT '总共提交次数',
  `total_ac` int(0) NOT NULL DEFAULT 0 COMMENT '提交次数内总共AC次数',
  `total_submit_user` int(0) NOT NULL DEFAULT 0 COMMENT '总共提交用户人数',
  `total_ac_user` int(0) NOT NULL DEFAULT 0 COMMENT '提交用户内总共AC人数',
  `visible` tinyint(1) NULL DEFAULT 1 COMMENT '是否普通用户可见，0： 不可见， 1：可见',
  `judge_option` smallint(0) NULL DEFAULT NULL COMMENT '评测机制选择： 0：默认，1：本地评测优先；2：第三方评测优先',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `problem_id`(`problem_id`) USING BTREE,
  INDEX `problem_id_2`(`problem_id`) USING BTREE,
  INDEX `belong_oj_id`(`belong_oj_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2623 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_problem_sample
-- ----------------------------
DROP TABLE IF EXISTS `t_problem_sample`;
CREATE TABLE `t_problem_sample`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `problem_id` int(0) NULL DEFAULT NULL,
  `case_order` int(0) NULL DEFAULT NULL COMMENT '样例顺序',
  `input_case` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '输入样例',
  `output_case` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '输出样例',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3244 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_problem_star
-- ----------------------------
DROP TABLE IF EXISTS `t_problem_star`;
CREATE TABLE `t_problem_star`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `problem_id` int(0) NOT NULL COMMENT '收藏的题目ID',
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间',
  `mark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_problem_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_problem_tag`;
CREATE TABLE `t_problem_tag`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标签名称',
  `tag_type` int(0) NULL DEFAULT -1 COMMENT '标签隶属类型',
  `create_user` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'SYSTEM' COMMENT '标签创建者',
  `priority` int(0) NULL DEFAULT 0 COMMENT '优先级',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 48 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_problem_tag_record
-- ----------------------------
DROP TABLE IF EXISTS `t_problem_tag_record`;
CREATE TABLE `t_problem_tag_record`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `problem_id` int(0) NOT NULL COMMENT '题目ID',
  `tag_id` int(0) NOT NULL,
  `time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '贴标签时间',
  `confidence` double NULL DEFAULT 0 COMMENT '标签置信度',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 57405 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_problem_view
-- ----------------------------
DROP TABLE IF EXISTS `t_problem_view`;
CREATE TABLE `t_problem_view`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `problem_id` int(0) NULL DEFAULT NULL,
  `time_limit` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '时间限制',
  `memory_limit` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '内存限制',
  `int_format` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '64位int使用的格式化字符串',
  `spj` tinyint(1) NOT NULL COMMENT '特判标记, 0：不是，1：是',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '题目描述',
  `input` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '输入',
  `output` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '输出',
  `hint` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '提示',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `problem_id`(`problem_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2621 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_rating_record
-- ----------------------------
DROP TABLE IF EXISTS `t_rating_record`;
CREATE TABLE `t_rating_record`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `current_rating` int(0) NOT NULL COMMENT '记录时的积分值',
  `reason` int(0) NOT NULL DEFAULT 0 COMMENT '积分变更原因：0：默认；',
  `time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录时间',
  `mark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '原因标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_spider_get_problem_info
-- ----------------------------
DROP TABLE IF EXISTS `t_spider_get_problem_info`;
CREATE TABLE `t_spider_get_problem_info`  (
  `id` bigint unsigned NOT NULL COMMENT '自增ID',
  `spider_job` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '爬虫任务ID',
  `spider_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '爬虫任务名',
  `insert_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '爬取时间',
  `from_website` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '来自网站名称',
  `problem_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '题目所在url',
  `problem_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '题目原始id',
  `problem_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '题目标题',
  `problem_time_limit` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '题目时间限制',
  `problem_memory_limit` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '题目内存限制',
  `problem_description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT ' 题目描述',
  `problem_input` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT ' 题目输入提示',
  `problem_output` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT ' 题目输出提示',
  `problem_sample_input` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT ' 题目输入样例',
  `problem_sample_output` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '题目输出样例',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8221 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_spider_item_info
-- ----------------------------
DROP TABLE IF EXISTS `t_spider_item_info`;
CREATE TABLE `t_spider_item_info`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `spider_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '爬虫名称',
  `target_website_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '目标站点URL',
  `target_website_logo_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '目标站点logo',
  `target_website_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '目标站点名称',
  `spider_type` int(0) NULL DEFAULT NULL COMMENT '爬虫类型',
  `anti_measures` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '反反爬虫措施',
  `deploy_status` int(0) NULL DEFAULT NULL COMMENT '部署状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_spider_item_job
-- ----------------------------
DROP TABLE IF EXISTS `t_spider_item_job`;
CREATE TABLE `t_spider_item_job`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '自增任务ID',
  `job_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '爬虫服务器任务ID',
  `spider_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '爬虫名称',
  `plan_start_time` datetime(0) NULL DEFAULT NULL COMMENT '计划开始时间',
  `plan_end_time` datetime(0) NULL DEFAULT NULL COMMENT '计划结束时间',
  `actual_start_time` datetime(0) NULL DEFAULT NULL COMMENT '实际开始时间',
  `actual_end_time` datetime(0) NULL DEFAULT NULL COMMENT '实际结束时间',
  `problem_range` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '爬取范围',
  `current_status` int(0) NULL DEFAULT NULL COMMENT '当前状态',
  `create_user` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建用户',
  `force_cancel` int(0) NULL DEFAULT NULL COMMENT '到达计划结束时间时自动关闭',
  `result` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '结果',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_spider_localized_record
-- ----------------------------
DROP TABLE IF EXISTS `t_spider_localized_record`;
CREATE TABLE `t_spider_localized_record`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `spider_get_problem_id` bigint(0) NULL DEFAULT NULL COMMENT '爬取临时题题目ID',
  `local_problem_id` int(0) NULL DEFAULT NULL COMMENT '对应的本地题目ID',
  `create_user` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作用户',
  `operate_record` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '操作记录',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_spider_sim_record
-- ----------------------------
DROP TABLE IF EXISTS `t_spider_sim_record`;
CREATE TABLE `t_spider_sim_record`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `spider_get_problem_id` bigint(0) NULL DEFAULT NULL COMMENT '爬取临时题题目ID',
  `local_problem_id` int(0) NULL DEFAULT NULL COMMENT '本地题目ID',
  `create_user` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作用户',
  `operate_record` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '操作记录',
  `sim_record` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '查重报告内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_system_info
-- ----------------------------
DROP TABLE IF EXISTS `t_system_info`;
CREATE TABLE `t_system_info`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '属性名',
  `value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '属性值',
  `insert_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_system_info
-- ----------------------------
INSERT INTO `t_system_info` VALUES (4, 'vj_remote_ojs', '{\"SGU\":{\"languages\":{},\"name\":\"SGU\"},\"FZU\":{\"languages\":{\"0\":\"GNU C++\",\"1\":\"GNU C\",\"2\":\"Pascal\",\"3\":\"Java\",\"4\":\"Visual C++\",\"5\":\"Visual C\"},\"name\":\"FZU\"},\"UVA\":{\"languages\":{\"1\":\"ANSI C 5.3.0\",\"2\":\"JAVA 1.8.0\",\"3\":\"C++ 5.3.0\",\"4\":\"PASCAL 3.0.0\",\"5\":\"C++11 5.3.0\",\"6\":\"PYTH3 3.5.1\"},\"name\":\"UVA\"},\"HRBUST\":{\"languages\":{\"1\":\"GCC\",\"2\":\"G++\",\"3\":\"JAVA\",\"4\":\"PHP\",\"5\":\"Python2\",\"7\":\"Haskell\"},\"name\":\"HRBUST\"},\"SCU\":{\"languages\":{\"Java\":\"Java\",\"C++\":\"C++\",\"C\":\"C\",\"Pascal\":\"Pascal\"},\"name\":\"SCU\"},\"51Nod\":{\"languages\":{\"11\":\"C++\",\"12\":\"C++ 11\",\"45\":\"PyPy2\",\"46\":\"PyPy3\",\"131\":\"Pascal\",\"121\":\"Objective C\",\"1\":\"C\",\"111\":\"Visual C++\",\"2\":\"C 11\",\"101\":\"Go\",\"91\":\"Javascript\",\"81\":\"Scala\",\"71\":\"Haskell\",\"61\":\"Php\",\"51\":\"Ruby\",\"41\":\"Python2\",\"31\":\"Java\",\"42\":\"Python3\",\"21\":\"C#\"},\"name\":\"51Nod\"},\"TopCoder\":{\"languages\":{\"1\":\"Java\",\"3\":\"C++\",\"4\":\"C#\",\"5\":\"VB\",\"6\":\"Python\"},\"name\":\"TopCoder\"},\"Z_trening\":{\"languages\":{\"1\":\"Pascal fpc 3.0.0\",\"2\":\"C gcc 6.3.1\",\"3\":\"C99 gcc 6.3.1\",\"4\":\"C++98 gcc 6.3.1\",\"5\":\"C++11 gcc 6.3.1\",\"6\":\"C++14 gcc 6.3.1\",\"7\":\"Java gcc-gcj 6.3.1\"},\"name\":\"Z_trening\"},\"HUST\":{\"languages\":{\"0\":\"C\",\"1\":\"C++\",\"2\":\"Pascal\",\"3\":\"Java\"},\"name\":\"HUST\"},\"EOlymp\":{\"languages\":{\"haskell\":\"Haskell\",\"rust\":\"Rust\",\"csharp\":\"C#\",\"python\":\"Python\",\"java\":\"Java\",\"gpp\":\"C++\",\"go\":\"Go\",\"kotlin\":\"Kotlin\",\"fpc\":\"Pascal\",\"js\":\"JavaScript\",\"php\":\"PHP\",\"ruby\":\"Ruby\"},\"name\":\"EOlymp\"},\"HackerRank\":{\"languages\":{\"pypy3\":\"pypy3\",\"elixir\":\"elixir\",\"scala\":\"scala\",\"clojure\":\"clojure\",\"pascal\":\"pascal\",\"erlang\":\"erlang\",\"racket\":\"racket\",\"sbcl\":\"sbcl\",\"python3\":\"python3\",\"rust\":\"rust\",\"csharp\":\"csharp\",\"java\":\"java\",\"java8\":\"java8\",\"julia\":\"julia\",\"fortran\":\"fortran\",\"fsharp\":\"fsharp\",\"perl\":\"perl\",\"tcl\":\"tcl\",\"lolcode\":\"lolcode\",\"swift\":\"swift\",\"ada\":\"ada\",\"haskell\":\"haskell\",\"cpp14\":\"cpp14\",\"cpp\":\"cpp\",\"python\":\"python\",\"coffeescript\":\"coffeescript\",\"c\":\"c\",\"octave\":\"octave\",\"d\":\"d\",\"visualbasic\":\"visualbasic\",\"ocaml\":\"ocaml\",\"pypy\":\"pypy\",\"go\":\"go\",\"objectivec\":\"objectivec\",\"javascript\":\"javascript\",\"ruby\":\"ruby\",\"r\":\"r\",\"groovy\":\"groovy\",\"php\":\"php\",\"lua\":\"lua\",\"bash\":\"bash\",\"whitespace\":\"whitespace\",\"smalltalk\":\"smalltalk\"},\"name\":\"HackerRank\"},\"LibreOJ\":{\"languages\":{\"haskell\":\"Haskell GHC 8.6.2\",\"cpp\":\"C++ GCC 8.2.0\",\"c\":\"C Clang 7.0.1\",\"cpp11\":\"C++ 11 GCC 8.2.0\",\"c-noilinux\":\"C (NOI) GCC 4.8.4 (NOILinux 1.4.1)\",\"cpp11-noilinux\":\"C++ 11 (NOI) GCC 4.8.4 (NOILinux 1.4.1)\",\"pascal\":\"Pascal Free Pascal 3.0.4\",\"nodejs\":\"Node.js 10.14.0\",\"cpp17\":\"C++ 17 GCC 8.2.0\",\"cpp17-clang\":\"C++ 17 (Clang) Clang 7.0.1\",\"ruby\":\"Ruby 2.5.1\",\"python3\":\"Python 3 PyPy 6.0.0 (Python 3.5.3)\",\"python2\":\"Python 2 PyPy 6.0.0 (Python 2.7.13)\",\"csharp\":\"C# Mono 5.16.0.220\",\"java\":\"Java OpenJDK 10.0.2\",\"cpp-noilinux\":\"C++ (NOI) GCC 4.8.4 (NOILinux 1.4.1)\",\"cpp11-clang\":\"C++ 11 (Clang) Clang 7.0.1\"},\"name\":\"LibreOJ\"},\"OpenJ_Bailian\":{\"languages\":{\"C#\":\"C#(mono4.2)\",\"Java\":\"Java(OpenJDK9)\",\"Racket\":\"Racket(6.5)\",\"G++\":\"G++(5.3)\",\"GCC\":\"GCC(5.3)\",\"Python2\":\"Python2(2.7)\",\"Python3\":\"Python3(3.5)\",\"Pascal\":\"Pascal(FreePascal)\"},\"name\":\"OpenJ_Bailian\"},\"HDU\":{\"languages\":{\"0\":\"G++\",\"1\":\"GCC\",\"2\":\"C++\",\"3\":\"C\",\"4\":\"Pascal\",\"5\":\"Java\",\"6\":\"C#\"},\"name\":\"HDU\"},\"UESTC_old\":{\"languages\":{},\"name\":\"UESTC_old\"},\"AtCoder\":{\"languages\":{},\"name\":\"AtCoder\"},\"HYSBZ\":{\"languages\":{\"0\":\"C\",\"1\":\"C++\",\"2\":\"Pascal\",\"3\":\"Java\",\"6\":\"Python\"},\"name\":\"HYSBZ\"},\"Gym\":{\"languages\":{},\"name\":\"Gym\"},\"Aizu\":{\"languages\":{\"JAVA\":\"JAVA\",\"C#\":\"C#\",\"C++\":\"C++\",\"C++14\":\"C++14\",\"C\":\"C\",\"Rust\":\"Rust\",\"D\":\"D\",\"Scala\":\"Scala\",\"C++11\":\"C++11\",\"Go\":\"Go\",\"Kotlin\":\"Kotlin\",\"OCaml\":\"OCaml\",\"Python3\":\"Python3\",\"JavaScript\":\"JavaScript\",\"PHP\":\"PHP\",\"Haskell\":\"Haskell\",\"Ruby\":\"Ruby\",\"Python\":\"Python\"},\"name\":\"Aizu\"},\"SPOJ\":{\"languages\":{},\"name\":\"SPOJ\"},\"CodeForces\":{\"languages\":{},\"name\":\"CodeForces\"},\"ACdream\":{\"languages\":{\"1\":\"C\",\"2\":\"C++\",\"3\":\"Java\"},\"name\":\"ACdream\"},\"CSU\":{\"languages\":{\"0\":\"C\",\"1\":\"C++\",\"3\":\"Java\",\"6\":\"Python\"},\"name\":\"CSU\"},\"CodeChef\":{\"languages\":{},\"name\":\"CodeChef\"},\"UVALive\":{\"languages\":{\"1\":\"ANSI C 5.3.0\",\"2\":\"JAVA 1.8.0\",\"3\":\"C++ 5.3.0\",\"4\":\"PASCAL 3.0.0\",\"5\":\"C++11 5.3.0\",\"6\":\"PYTH3 3.5.1\"},\"name\":\"UVALive\"},\"OpenJ_POJ\":{\"languages\":{\"Java\":\"Java(OpenJDK9)\",\"G++\":\"G++(5.3)\",\"GCC\":\"GCC(5.3)\",\"Pascal\":\"Pascal(FreePascal)\"},\"name\":\"OpenJ_POJ\"},\"Kattis\":{\"languages\":{\"C#\":\"C#\",\"Python 3\":\"Python 3\",\"Java\":\"Java\",\"Python 2\":\"Python 2\",\"C++\":\"C++\",\"JavaScript (Node.js)\":\"JavaScript (Node.js)\",\"C\":\"C\",\"Rust\":\"Rust\",\"COBOL\":\"COBOL\",\"Go\":\"Go\",\"Common Lisp\":\"Common Lisp\",\"Prolog\":\"Prolog\",\"JavaScript (SpiderMonkey)\":\"JavaScript (SpiderMonkey)\",\"Kotlin\":\"Kotlin\",\"OCaml\":\"OCaml\",\"Haskell\":\"Haskell\",\"Objective-C\":\"Objective-C\",\"PHP\":\"PHP\",\"Pascal\":\"Pascal\",\"F#\":\"F#\",\"Ruby\":\"Ruby\"},\"name\":\"Kattis\"},\"POJ\":{\"languages\":{\"0\":\"G++\",\"1\":\"GCC\",\"2\":\"Java\",\"3\":\"Pascal\",\"4\":\"C++\",\"5\":\"C\",\"6\":\"Fortran\"},\"name\":\"POJ\"},\"HihoCoder\":{\"languages\":{\"C#\":\"C#\",\"Java\":\"Java\",\"GCC\":\"GCC\",\"G++\":\"G++\",\"Python2\":\"Python2\"},\"name\":\"HihoCoder\"},\"URAL\":{\"languages\":{},\"name\":\"URAL\"},\"HIT\":{\"languages\":{\"Java\":\"Java\",\"C++\":\"C++\",\"C89\":\"C89\",\"Pascal\":\"Pascal\"},\"name\":\"HIT\"},\"LightOJ\":{\"languages\":{\"JAVA\":\"JAVA\",\"C++\":\"C++\",\"PASCAL\":\"PASCAL\",\"C\":\"C\"},\"name\":\"LightOJ\"},\"ZOJ\":{\"languages\":{\"JAVAC\":\"Java (java 1.8.0)\",\"PYTHON3\":\"Python (python 3.6.5)\",\"PYTHON2\":\"Python (python 2.7.12)\",\"GCC\":\"C (gcc 4.7.2)\",\"GXX\":\"C++ (g++ 6.4.0)\"},\"name\":\"ZOJ\"},\"Minieye\":{\"languages\":{\"Golang\":\"Go 1.11\",\"Java\":\"OpenJDK 1.8\",\"C++\":\"G++ 5.4\",\"C\":\"GCC 5.4\",\"Python3\":\"Python 3.5\"},\"name\":\"Minieye\"},\"NBUT\":{\"languages\":{\"1\":\"GCC\",\"2\":\"G++\",\"4\":\"FPC\"},\"name\":\"NBUT\"},\"EIJudge\":{\"languages\":{\"GNU C++\":\"GNU C++ 3.3.3\",\"Java\":\"java 1.5.0\",\"Scheme\":\"mzScheme 301 Swindle\",\"Kylix\":\"Kylix 14.5\",\"OCaml\":\"Objective Caml 3.10.2\",\"Haskell\":\"Haskell GC 6.8.2\",\"Lua\":\"Lua 5.1.3\",\"Perl\":\"Perl 5.8.5\",\"Free Pascal\":\"Free Pascal 1.8.2\",\"GNU C\":\"GNU C 3.3.3\",\"Ruby\":\"Ruby 1.8.6\",\"Python\":\"Python 2.1.3\"},\"name\":\"EIJudge\"},\"response_time\":1603178776000,\"计蒜客\":{\"languages\":{\"python3\":\"Python 3.5\",\"c++14\":\"C++14\",\"c++\":\"C++11\",\"python\":\"Python 2.7\",\"c\":\"C\",\"c_noi\":\"C (NOI)\",\"java\":\"Java\",\"octave\":\"Octave\",\"pascal\":\"Pascal\",\"c++_noi\":\"C++ (NOI)\",\"go\":\"Go\",\"ruby\":\"Ruby\"},\"name\":\"计蒜客\"},\"UESTC\":{\"languages\":{\"1\":\"C\",\"2\":\"C++\",\"3\":\"Java\"},\"name\":\"UESTC\"}}', '2020-10-20 15:26:16');

-- ----------------------------
-- Table structure for t_system_log
-- ----------------------------
DROP TABLE IF EXISTS `t_system_log`;
CREATE TABLE `t_system_log`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `request_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'default' COMMENT '请求url路径',
  `http_method` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '请求方法',
  `ip_address` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求的IP地址',
  `java_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '请求的Java方法名称',
  `params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '请求参数的json字符串',
  `response_body` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '返回的内容',
  `time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '请求时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_system_setting
-- ----------------------------
DROP TABLE IF EXISTS `t_system_setting`;
CREATE TABLE `t_system_setting`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '设置名',
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_active
-- ----------------------------
DROP TABLE IF EXISTS `t_user_active`;
CREATE TABLE `t_user_active`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `login_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_auth
-- ----------------------------
DROP TABLE IF EXISTS `t_user_auth`;
CREATE TABLE `t_user_auth`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `salt` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '加密盐值',
  `password` char(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `attempt_login_fail_count` smallint(0) NULL DEFAULT 0 COMMENT '尝试登录失败次数',
  `locked` smallint(0) NULL DEFAULT 0 COMMENT '0为未锁定，1为锁定',
  `unlock_time` datetime(0) NULL DEFAULT NULL COMMENT '账号解锁时间',
  `last_login_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  CONSTRAINT `t_user_auth_ibfk_1` FOREIGN KEY (`username`) REFERENCES `t_user_base_info` (`username`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user_auth
-- ----------------------------
INSERT INTO `t_user_auth` VALUES (26, 'admin', 'v-+v', 'e8275e32c4969f52b3991513248cc2dcabdf5870', 0, 0, '2020-10-20 10:20:35', '2020-10-23 16:55:42');

-- ----------------------------
-- Table structure for t_user_base_info
-- ----------------------------
DROP TABLE IF EXISTS `t_user_base_info`;
CREATE TABLE `t_user_base_info`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '16个英文字母的登录名',
  `gender` smallint(0) NULL DEFAULT 0 COMMENT '性别，0为保密，1为男，2为女',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '邮箱',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '电话',
  `motto` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '个性签名',
  `register_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `rating` int(0) NOT NULL DEFAULT 0 COMMENT '评分',
  `acb` int(0) NOT NULL DEFAULT 0 COMMENT 'ACB数量',
  `ac_num` int(0) NOT NULL COMMENT 'AC题目数量',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user_base_info
-- ----------------------------
INSERT INTO `t_user_base_info` VALUES (29, 'admin', 0, 'saykuray@foxmail.com', '13592873036', NULL, '2020-10-20 02:21:21', 0, 0, 0);

-- ----------------------------
-- Table structure for t_user_captcha
-- ----------------------------
DROP TABLE IF EXISTS `t_user_captcha`;
CREATE TABLE `t_user_captcha`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '唯一身份名称',
  `captcha_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '验证码值',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '验证码创建时间',
  `expire_time` datetime(0) NULL DEFAULT NULL COMMENT '验证码过期时间',
  `is_guest` tinyint(0) NULL DEFAULT NULL COMMENT '是否为游客。0：不是游客；1：是游客',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_check_in
-- ----------------------------
DROP TABLE IF EXISTS `t_user_check_in`;
CREATE TABLE `t_user_check_in`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `check_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `info` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'normal' COMMENT '签到状态',
  `ip_address` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '签到ip地址',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 55 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_custom_info
-- ----------------------------
DROP TABLE IF EXISTS `t_user_custom_info`;
CREATE TABLE `t_user_custom_info`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '昵称',
  `username` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `avatar_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '用户头像url地址',
  `adjective_id` int(0) NULL DEFAULT NULL COMMENT '形容词头衔ID',
  `article_id` int(0) NULL DEFAULT NULL COMMENT '名词头衔ID',
  `seal_id` int(0) NULL DEFAULT NULL COMMENT '印章ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  INDEX `nickname`(`nickname`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_message
-- ----------------------------
DROP TABLE IF EXISTS `t_user_message`;
CREATE TABLE `t_user_message`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `to_username` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '接收人用户名',
  `from_username` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '发送人用户名',
  `status` smallint(0) NOT NULL DEFAULT 0 COMMENT '0未读，1已读，2标记',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标题',
  `text` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '内容',
  `time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_user_permission`;
CREATE TABLE `t_user_permission`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `permission_id` int(0) NOT NULL,
  `granter` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '授权人',
  `grant_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '授予时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user_permission
-- ----------------------------
INSERT INTO `t_user_permission` VALUES (3, 'admin', 0, 'root', '2020-10-20 02:54:36');
INSERT INTO `t_user_permission` VALUES (4, 'admin', 22, 'root', '2020-10-20 07:15:07');
INSERT INTO `t_user_permission` VALUES (5, 'admin', 21, 'root', '2020-10-20 07:16:18');
INSERT INTO `t_user_permission` VALUES (6, 'admin', 11, 'admin', '2020-10-20 07:17:22');
INSERT INTO `t_user_permission` VALUES (7, 'admin', 14, 'admin', '2020-10-20 07:17:22');
INSERT INTO `t_user_permission` VALUES (8, 'admin', 12, 'admin', '2020-10-20 07:17:22');
INSERT INTO `t_user_permission` VALUES (9, 'admin', 15, 'admin', '2020-10-20 07:17:22');
INSERT INTO `t_user_permission` VALUES (10, 'admin', 13, 'admin', '2020-10-20 07:17:22');
INSERT INTO `t_user_permission` VALUES (11, 'admin', 16, 'admin', '2020-10-20 07:17:22');
INSERT INTO `t_user_permission` VALUES (12, 'admin', 17, 'admin', '2020-10-20 07:17:22');
INSERT INTO `t_user_permission` VALUES (13, 'admin', 23, 'admin', '2020-10-20 07:17:22');
INSERT INTO `t_user_permission` VALUES (14, 'admin', 31, 'admin', '2020-10-20 07:17:22');
INSERT INTO `t_user_permission` VALUES (15, 'admin', 32, 'admin', '2020-10-20 07:17:22');
INSERT INTO `t_user_permission` VALUES (16, 'admin', 33, 'admin', '2020-10-20 07:17:22');
INSERT INTO `t_user_permission` VALUES (17, 'admin', 34, 'admin', '2020-10-20 07:17:22');
INSERT INTO `t_user_permission` VALUES (18, 'admin', 41, 'admin', '2020-10-20 07:17:22');
INSERT INTO `t_user_permission` VALUES (19, 'admin', 42, 'admin', '2020-10-20 07:17:22');
INSERT INTO `t_user_permission` VALUES (20, 'admin', 51, 'admin', '2020-10-20 07:17:22');
INSERT INTO `t_user_permission` VALUES (21, 'admin', 52, 'admin', '2020-10-20 07:17:22');
INSERT INTO `t_user_permission` VALUES (22, 'admin', 61, 'admin', '2020-10-20 07:17:22');
INSERT INTO `t_user_permission` VALUES (23, 'admin', 71, 'admin', '2020-10-20 07:17:22');
INSERT INTO `t_user_permission` VALUES (24, 'admin', 81, 'admin', '2020-10-20 07:17:22');
INSERT INTO `t_user_permission` VALUES (25, 'admin', 91, 'admin', '2020-10-20 07:17:22');
INSERT INTO `t_user_permission` VALUES (26, 'admin', 101, 'admin', '2020-10-20 07:17:22');
INSERT INTO `t_user_permission` VALUES (27, 'admin', 102, 'admin', '2020-10-20 07:17:22');
INSERT INTO `t_user_permission` VALUES (28, 'admin', 103, 'admin', '2020-10-20 07:17:22');
INSERT INTO `t_user_permission` VALUES (29, 'admin', 104, 'admin', '2020-10-20 07:17:22');
INSERT INTO `t_user_permission` VALUES (30, 'admin', 105, 'admin', '2020-10-20 07:17:22');
INSERT INTO `t_user_permission` VALUES (31, 'admin', 106, 'admin', '2020-10-20 07:17:22');
INSERT INTO `t_user_permission` VALUES (32, 'admin', 111, 'admin', '2020-10-20 07:17:22');
INSERT INTO `t_user_permission` VALUES (33, 'admin', 113, 'admin', '2020-10-20 07:17:22');
INSERT INTO `t_user_permission` VALUES (34, 'admin', 112, 'admin', '2020-10-20 07:17:22');

-- ----------------------------
-- Table structure for t_user_problem_solved
-- ----------------------------
DROP TABLE IF EXISTS `t_user_problem_solved`;
CREATE TABLE `t_user_problem_solved`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `problem_id` int(0) NULL DEFAULT NULL,
  `try_count` int(0) NULL DEFAULT 0 COMMENT '尝试次数',
  `solved_count` int(0) NULL DEFAULT 0 COMMENT '解决次数',
  `last_try_time` datetime(0) NULL DEFAULT NULL COMMENT '最后尝试日期',
  `first_solved_time` datetime(0) NULL DEFAULT NULL COMMENT '第一次解锁日期',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `username`(`username`) USING BTREE,
  INDEX `problem_id`(`problem_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_seal
-- ----------------------------
DROP TABLE IF EXISTS `t_user_seal`;
CREATE TABLE `t_user_seal`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `type` int(0) NULL DEFAULT 0 COMMENT '目前未定义属性',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '印章名称',
  `picture_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '印章图片url',
  `life_time` int(0) NULL DEFAULT -1 COMMENT '印章有效时间，单位：天',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_seal_record
-- ----------------------------
DROP TABLE IF EXISTS `t_user_seal_record`;
CREATE TABLE `t_user_seal_record`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `seal_id` int(0) NOT NULL COMMENT '印章ID',
  `obtain_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '获得时间',
  `expired_time` datetime(0) NULL DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  INDEX `seal_id`(`seal_id`) USING BTREE,
  CONSTRAINT `t_user_seal_record_ibfk_2` FOREIGN KEY (`seal_id`) REFERENCES `t_user_seal` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_setting
-- ----------------------------
DROP TABLE IF EXISTS `t_user_setting`;
CREATE TABLE `t_user_setting`  (
  `id` int unsigned NOT NULL,
  `username` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `settings` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '设置',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_title
-- ----------------------------
DROP TABLE IF EXISTS `t_user_title`;
CREATE TABLE `t_user_title`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `type` int(0) NULL DEFAULT NULL COMMENT '头衔类型：0：未定义；1：形容词；2：名词头衔',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '头衔名称',
  `picture_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '头衔图片url，如果有',
  `life_time` int(0) NULL DEFAULT -1 COMMENT '头衔有效时间，单位：天',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_title_record
-- ----------------------------
DROP TABLE IF EXISTS `t_user_title_record`;
CREATE TABLE `t_user_title_record`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `title_id` int(0) NOT NULL COMMENT '头衔ID',
  `obtain_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '获得时间',
  `expired_time` datetime(0) NULL DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  INDEX `title_id`(`title_id`) USING BTREE,
  CONSTRAINT `t_user_title_record_ibfk_2` FOREIGN KEY (`title_id`) REFERENCES `t_user_title` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_vj_judge_result
-- ----------------------------
DROP TABLE IF EXISTS `t_vj_judge_result`;
CREATE TABLE `t_vj_judge_result`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '提交评测用户名',
  `run_id` bigint(0) NOT NULL COMMENT '评测ID',
  `remote_run_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '远程评测ID',
  `oj` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评测oj名',
  `prob_num` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '题目ID',
  `author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评测执行作者，这里指的是vj登录账号',
  `author_id` bigint(0) NULL DEFAULT NULL COMMENT '作者ID，这里指的是vj登录账号',
  `submit_time` datetime(0) NULL DEFAULT NULL COMMENT '提交评测时间',
  `processing` tinyint(1) NULL DEFAULT NULL COMMENT '是否正在评测中',
  `status_type` int(0) NULL DEFAULT NULL COMMENT '评测结果类型',
  `status_canonical` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评测结果简写',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评测结果',
  `language` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评测语言',
  `language_canonical` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '语言简写',
  `code` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '代码',
  `length` int(0) NULL DEFAULT NULL COMMENT '代码长度',
  `additional_info` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '反馈额外信息',
  `runtime` bigint(0) NULL DEFAULT NULL COMMENT '运行消耗时间',
  `memory` bigint(0) NULL DEFAULT NULL COMMENT '消耗内存',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_vj_problem_info
-- ----------------------------
DROP TABLE IF EXISTS `t_vj_problem_info`;
CREATE TABLE `t_vj_problem_info`  (
  `id` int unsigned NOT NULL,
  `oj_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'OJId，OJ名',
  `prob_num` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'probNum，题目号',
  `data` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '数据内容',
  `time` datetime(0) NULL DEFAULT NULL COMMENT '获取时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_vj_user_problem_solved
-- ----------------------------
DROP TABLE IF EXISTS `t_vj_user_problem_solved`;
CREATE TABLE `t_vj_user_problem_solved`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `oj_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'OJ名',
  `prob_num` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '题目号',
  `try_count` int(0) NULL DEFAULT 0 COMMENT '尝试次数',
  `solved_count` int(0) NULL DEFAULT 0 COMMENT '解决次数',
  `last_try_time` datetime(0) NULL DEFAULT NULL COMMENT '最后尝试日期',
  `first_solved_time` datetime(0) NULL DEFAULT NULL COMMENT '第一次解锁日期',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- View structure for v_judge_status
-- ----------------------------
DROP VIEW IF EXISTS `v_judge_status`;
CREATE ALGORITHM = MERGE DEFINER = `root`@`%` SQL SECURITY DEFINER VIEW `v_judge_status` AS select `t_judge_status`.`id` AS `id`,`t_judge_status`.`username` AS `username`,`t_judge_status`.`problem_id` AS `problem_id`,`t_judge_status`.`contest_id` AS `contest_id`,`t_judge_status`.`language` AS `language`,`t_judge_status`.`submit_time` AS `submit_time`,`t_judge_status`.`result` AS `result`,`t_judge_status`.`score` AS `score`,`t_judge_status`.`time_used` AS `time_used`,`t_judge_status`.`memory_used` AS `memory_used`,`t_judge_status`.`code_length` AS `code_length`,`t_user_custom_info`.`nickname` AS `nickname` from (`t_judge_status` left join `t_user_custom_info` on((`t_judge_status`.`username` = `t_user_custom_info`.`username`)));

-- ----------------------------
-- View structure for v_problem_info
-- ----------------------------
DROP VIEW IF EXISTS `v_problem_info`;
CREATE ALGORITHM = UNDEFINED DEFINER = `root`@`%` SQL SECURITY DEFINER VIEW `v_problem_info` AS select `tpi`.`id` AS `id`,`tpi`.`problem_id` AS `problem_id`,`tpi`.`title` AS `title`,`tpi`.`belong_oj_id` AS `belong_oj_id`,`tpi`.`belong_problem_id` AS `belong_problem_id`,`tpi`.`author` AS `author`,`tpi`.`total_submit` AS `total_submit`,`tpi`.`total_ac` AS `total_ac`,`tpi`.`total_submit_user` AS `total_submit_user`,`tpi`.`total_ac_user` AS `total_ac_user`,`tpi`.`visible` AS `visible`,`tpi`.`judge_option` AS `judge_option`,`tpd`.`problem_type` AS `problem_type`,`tpd`.`difficult_level` AS `difficult_level` from (`t_problem_info` `tpi` left join `t_problem_difficult` `tpd` on((`tpi`.`problem_id` = `tpd`.`problem_id`)));

-- ----------------------------
-- View structure for v_vj_judge_result
-- ----------------------------
DROP VIEW IF EXISTS `v_vj_judge_result`;
CREATE ALGORITHM = UNDEFINED DEFINER = `root`@`%` SQL SECURITY DEFINER VIEW `v_vj_judge_result` AS select `t_vj_judge_result`.`id` AS `id`,`t_vj_judge_result`.`username` AS `username`,`t_vj_judge_result`.`run_id` AS `run_id`,`t_vj_judge_result`.`remote_run_id` AS `remote_run_id`,`t_vj_judge_result`.`oj` AS `oj`,`t_vj_judge_result`.`prob_num` AS `prob_num`,`t_vj_judge_result`.`author` AS `author`,`t_vj_judge_result`.`author_id` AS `author_id`,`t_vj_judge_result`.`submit_time` AS `submit_time`,`t_vj_judge_result`.`processing` AS `processing`,`t_vj_judge_result`.`status_type` AS `status_type`,`t_vj_judge_result`.`status_canonical` AS `status_canonical`,`t_vj_judge_result`.`status` AS `status`,`t_vj_judge_result`.`language` AS `language`,`t_vj_judge_result`.`language_canonical` AS `language_canonical`,`t_vj_judge_result`.`code` AS `code`,`t_vj_judge_result`.`length` AS `length`,`t_vj_judge_result`.`additional_info` AS `additional_info`,`t_vj_judge_result`.`runtime` AS `runtime`,`t_vj_judge_result`.`memory` AS `memory`,`t_user_custom_info`.`nickname` AS `nickname` from (`t_vj_judge_result` left join `t_user_custom_info` on((`t_vj_judge_result`.`username` = `t_user_custom_info`.`username`)));

-- ----------------------------
-- Event structure for e_del_captcha
-- ----------------------------
DROP EVENT IF EXISTS `e_del_captcha`;
delimiter ;;
CREATE DEFINER = `root`@`%` EVENT `e_del_captcha`
ON SCHEDULE
EVERY '1' DAY STARTS '2020-01-01 00:00:00'
COMMENT '自动删除无效验证码记录'
DO delete from t_user_captcha where expire_time < NOW()
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
