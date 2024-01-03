CREATE DATABASE IF NOT EXISTS `douyin_java`;
USE `douyin_java`;

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '姓名',
  `password` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '密码',
  `avatar` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '头像链接',
  `background_image` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '背景链接',
  `signature` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '简介',
  `follow_count` bigint NOT NULL DEFAULT 0 COMMENT '关注数',
  `follower_count` bigint NOT NULL DEFAULT 0 COMMENT '粉丝数',
  `total_favorited` bigint NOT NULL DEFAULT 0 COMMENT '获赞数',
  `favorite_count` bigint NOT NULL DEFAULT 0 COMMENT '点赞数',
  `work_count` bigint NOT NULL DEFAULT 0 COMMENT '作品数',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_name` (`name`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '用户信息表';

DROP TABLE IF EXISTS `videos`;
CREATE TABLE `videos`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` datetime NULL DEFAULT NULL COMMENT '提交时间',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `play_url` varchar(255) NOT NULL COMMENT '视频链接',
  `cover_url` varchar(255) NOT NULL COMMENT '封面链接',
  `title` varchar(255) NOT NULL COMMENT '标题',
  `favorite_count` bigint NULL DEFAULT 0 COMMENT '该视频点赞数',
  `comment_count` bigint NULL DEFAULT 0 COMMENT '视频评论数',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '视频数据表';

DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `video_id` bigint NOT NULL COMMENT '视频id',
  `content` varchar(255) NOT NULL COMMENT '评论内容',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '评论表';

DROP TABLE IF EXISTS `messages`;
CREATE TABLE `messages`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `from_user_id` bigint NOT NULL COMMENT '消息发送者id',
  `to_user_id` bigint NOT NULL COMMENT '消息接受者id',
  `content` varchar(255) NOT NULL COMMENT '消息内容',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '消息表';

DROP TABLE IF EXISTS `favorites`;
CREATE TABLE `favorites`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '点赞用户',
  `video_id` bigint NOT NULL COMMENT '点赞视频',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '点赞表';

DROP TABLE IF EXISTS `relations`;
CREATE TABLE `relations`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `from_user_id` bigint NOT NULL COMMENT '关注者id',
  `to_user_id` bigint NOT NULL COMMENT '被关注者id',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '关注表';

DROP TABLE IF EXISTS `logs`;
CREATE TABLE `logs` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `operation_user_id` varchar(64) NOT NULL COMMENT '操作人id',
  `operation_date` datetime NULL DEFAULT NULL COMMENT '操作时间',
  `class_name` varchar(64) NOT NULL COMMENT '操作类名',
  `method_name` varchar(32) NOT NULL COMMENT '操作方法名',
  `ip_address` varchar(128) NOT NULL COMMENT 'ip地址',
  `url` varchar(128) NOT NULL COMMENT '请求的url',
  `cost_time` bigint NOT NULL COMMENT '操作执行耗时(ms)',
  `description` varchar(255) NOT NULL COMMENT '操作描述',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '操作日志表';
