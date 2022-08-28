SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for forum_article_type
-- ----------------------------
DROP TABLE IF EXISTS `forum_article_type`;
CREATE TABLE `forum_article_type` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'Primary Key',
  `audit_state` varchar(64) NOT NULL COMMENT 'Audit Status',
  `name` varchar(64) NOT NULL COMMENT 'Name',
  `description` varchar(1024) NOT NULL COMMENT 'Description',
  `ref_count` bigint(11) NOT NULL DEFAULT '0' COMMENT 'Reference Count',
  `scope` varchar(32) NOT NULL COMMENT 'Scope',
  `creator_id` bigint(11) NOT NULL COMMENT 'Creator',
  `is_delete` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete Label（0:Not deleted、1:deleted）',
  `create_at` datetime NOT NULL COMMENT 'Record the creation time',
  `update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record the modification time',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_name_state` (`name`,`audit_state`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Article type table';

-- ----------------------------
-- Table structure for forum_cache
-- ----------------------------
DROP TABLE IF EXISTS `forum_cache`;
CREATE TABLE `forum_cache` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'Primary Key',
  `key` varchar(100) NOT NULL COMMENT 'Cache Key',
  `value` longtext NOT NULL COMMENT 'Cache Value',
  `type` varchar(64) NOT NULL COMMENT 'Business Type',
  `is_delete` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete Label（0:Not deleted、1:deleted）',
  `create_at` datetime NOT NULL COMMENT 'Record the creation time',
  `update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record the modification time',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_key` (`key`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Cache table';

-- ----------------------------
-- Table structure for forum_comment
-- ----------------------------
DROP TABLE IF EXISTS `forum_comment`;
CREATE TABLE `forum_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Primary Key',
  `user_id` bigint(11) NOT NULL COMMENT 'Reviewer ID',
  `reply_id` bigint(11) DEFAULT NULL COMMENT 'Reviewed ID',
  `reply_reply_id` bigint(11) DEFAULT NULL COMMENT 'Second reviewed ID',
  `posts_id` bigint(11) NOT NULL COMMENT 'Post ID',
  `content` longtext NOT NULL COMMENT 'Content',
  `is_delete` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete Label（0:Not deleted、1:deleted）',
  `create_at` datetime NOT NULL COMMENT 'Record the creation time',
  `update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record the modification time',
  PRIMARY KEY (`id`),
  KEY `idx_uid_pid` (`user_id`,`posts_id`),
  KEY `idx_postsid` (`posts_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Post comment table';

-- ----------------------------
-- Table structure for forum_message
-- ----------------------------
DROP TABLE IF EXISTS `forum_message`;
CREATE TABLE `forum_message` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'Primary Key',
  `channel` varchar(64) NOT NULL COMMENT 'Send Channels',
  `type` varchar(64) NOT NULL COMMENT 'Message Type',
  `read` varchar(64) NOT NULL COMMENT 'Read/Unread',
  `sender_type` varchar(64) NOT NULL COMMENT 'Sender Type',
  `sender` varchar(64) NOT NULL COMMENT 'Sender',
  `receiver_type` varchar(64) NOT NULL COMMENT 'Receiver Type',
  `receiver` varchar(64) NOT NULL COMMENT 'Receiver',
  `title` varchar(256) NOT NULL COMMENT 'Title',
  `content_type` varchar(64) NOT NULL COMMENT 'Content Type',
  `content` longtext NOT NULL COMMENT 'Content',
  `is_delete` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete Label（0:Not deleted、1:deleted）',
  `create_at` datetime NOT NULL COMMENT 'Record the creation time',
  `update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record the modification time',
  PRIMARY KEY (`id`),
  KEY `idx_sender` (`sender`),
  KEY `idx_receiver_type` (`receiver`,`type`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Message table';

-- ----------------------------
-- Table structure for forum_opt_log
-- ----------------------------
DROP TABLE IF EXISTS `forum_opt_log`;
CREATE TABLE `forum_opt_log` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'Primary Key',
  `type` varchar(64) NOT NULL COMMENT 'Operation Type',
  `operator_id` bigint(11) NOT NULL COMMENT 'Operator ID',
  `content` longtext NOT NULL COMMENT 'Operation Content',
  `is_delete` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete Label（0:Not deleted、1:deleted）',
  `create_at` datetime NOT NULL COMMENT 'Record the creation time',
  `update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record the modification time',
  PRIMARY KEY (`id`),
  KEY `idx_operator_id` (`operator_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Operation log table';

-- ----------------------------
-- Table structure for forum_posts
-- ----------------------------
DROP TABLE IF EXISTS `forum_posts`;
CREATE TABLE `forum_posts` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'Primary Key',
  `audit_state` varchar(64) NOT NULL COMMENT 'Audit Status',
  `category` varchar(64) NOT NULL COMMENT 'Category',
  `author_id` bigint(11) NOT NULL COMMENT 'Author ID',
  `title` varchar(256) NOT NULL COMMENT 'Title',
  `content_type` varchar(64) NOT NULL COMMENT 'Content Type',
  `markdown_content` longtext NOT NULL COMMENT 'Markdown Content',
  `html_content` longtext NOT NULL COMMENT 'Html Content',
  `views` bigint(11) NOT NULL DEFAULT '0' COMMENT 'Page View',
  `approvals` bigint(11) NOT NULL DEFAULT '0' COMMENT 'Number of likes/favorites',
  `comments` bigint(11) NOT NULL DEFAULT '0' COMMENT 'Comment Count',
  `type_id` bigint(11) NOT NULL DEFAULT '0' COMMENT 'Article Type ID',
  `head_img` varchar(8192) NOT NULL DEFAULT '' COMMENT 'Article Head Image',
  `official` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT 'Official',
  `top` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT 'TOP',
  `sort` int(4) NOT NULL DEFAULT '1000' COMMENT 'Sort',
  `marrow` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT 'Marrow',
  `comment_id` bigint(11) NOT NULL COMMENT 'Excellent Answer ID',
  `is_delete` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete Label（0:Not deleted、1:deleted）',
  `create_at` datetime NOT NULL COMMENT 'Record the creation time',
  `update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record the modification time',
  PRIMARY KEY (`id`),
  KEY `idx_author` (`author_id`),
  KEY `idx_category_state_views` (`category`,`audit_state`,`views`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Post table';

-- ----------------------------
-- Table structure for forum_search
-- ----------------------------
DROP TABLE IF EXISTS `forum_search`;
CREATE TABLE `forum_search` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'Primary Key',
  `type` varchar(64) NOT NULL COMMENT 'Type',
  `entity_id` bigint(11) NOT NULL COMMENT 'Entity ID',
  `title` varchar(256) NOT NULL COMMENT 'Title',
  `content` longtext NOT NULL COMMENT 'Content',
  `is_delete` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete Label（0:Not deleted、1:deleted）',
  `create_at` datetime NOT NULL COMMENT 'Record the creation time',
  `update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record the modification time',
  PRIMARY KEY (`id`),
  KEY `idx_type_title` (`type`,`title`(191)),
  KEY `idx_type_create` (`type`,`create_at`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Search content table';

-- ----------------------------
-- Table structure for forum_tag
-- ----------------------------
DROP TABLE IF EXISTS `forum_tag`;
CREATE TABLE `forum_tag` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'Primary Key',
  `audit_state` varchar(64) NOT NULL COMMENT 'Audit Status',
  `group_name` varchar(255) CHARACTER SET utf8mb4 NOT NULL COMMENT 'Group',
  `name` varchar(64) NOT NULL COMMENT 'Name',
  `description` varchar(1024) NOT NULL COMMENT 'Description',
  `ref_count` bigint(11) NOT NULL DEFAULT '0' COMMENT 'Reference Count',
  `creator_id` bigint(11) NOT NULL COMMENT 'Creator',
  `is_delete` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete Label（0:Not deleted、1:deleted）',
  `create_at` datetime NOT NULL COMMENT 'Record the creation time',
  `update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record the modification time',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_name_state` (`name`,`audit_state`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Tag table';

-- ----------------------------
-- Table structure for forum_tag_posts_mapping
-- ----------------------------
DROP TABLE IF EXISTS `forum_tag_posts_mapping`;
CREATE TABLE `forum_tag_posts_mapping` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'Primary Key',
  `tag_id` bigint(11) NOT NULL COMMENT 'Tag ID',
  `posts_id` bigint(11) NOT NULL COMMENT 'Post ID',
  `is_delete` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete Label（0:Not deleted、1:deleted）',
  `create_at` datetime NOT NULL COMMENT 'Record the creation time',
  `update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record the modification time',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_posts_tag` (`posts_id`,`tag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Tag-Post table';

-- ----------------------------
-- Table structure for forum_user
-- ----------------------------
DROP TABLE IF EXISTS `forum_user`;
CREATE TABLE `forum_user` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'Primary Key',
  `email` varchar(64) NOT NULL COMMENT 'Email',
  `nickname` varchar(64) NOT NULL COMMENT 'Nickname',
  `password` varchar(128) NOT NULL COMMENT 'Password',
  `role` varchar(32) NOT NULL COMMENT 'Role',
  `state` varchar(64) NOT NULL COMMENT 'Status',
  `sex` varchar(32) NOT NULL COMMENT 'Sex',
  `avatar` varchar(256) NOT NULL DEFAULT '' COMMENT 'Avatar',
  `signature` varchar(1024) NOT NULL DEFAULT '' COMMENT 'Profile',
  `last_login_time` datetime NOT NULL COMMENT 'Last login time',
  `is_delete` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete Label（0:Not deleted、1:deleted）',
  `create_at` datetime NOT NULL COMMENT 'Record the creation time',
  `update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record the modification time',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='User table';

-- ----------------------------
-- Table structure for forum_user_follow
-- ----------------------------
DROP TABLE IF EXISTS `forum_user_follow`;
CREATE TABLE `forum_user_follow` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Primary Key',
  `followed` bigint(11) NOT NULL COMMENT 'Followed ',
  `followed_type` varchar(64) NOT NULL COMMENT 'Followed Type',
  `follower` bigint(11) NOT NULL COMMENT 'Follower',
  `is_delete` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete Label（0:Not deleted、1:deleted）',
  `create_at` datetime NOT NULL COMMENT 'Record the creation time',
  `update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record the modification time',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_followed_follower` (`followed`,`followed_type`,`follower`) USING BTREE,
  KEY `idx_follower` (`follower`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Follow table';

-- ----------------------------
-- Table structure for forum_user_food
-- ----------------------------
DROP TABLE IF EXISTS `forum_user_food`;
CREATE TABLE `forum_user_food` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'Primary Key',
  `user_id` bigint(11) NOT NULL COMMENT 'User ID',
  `posts_id` bigint(11) NOT NULL COMMENT 'Post ID',
  `is_delete` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete Label（0:Not deleted、1:deleted）',
  `create_at` datetime NOT NULL COMMENT 'Record the creation time',
  `update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record the modification time',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_uid_pid` (`user_id`,`posts_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Follow table';

-- ----------------------------
-- Table structure for forum_config
-- ----------------------------
DROP TABLE IF EXISTS `forum_config`;
CREATE TABLE `forum_config` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'Primary Key',
  `state` varchar(64) NOT NULL COMMENT 'Status',
  `type` varchar(64) NOT NULL COMMENT 'Type',
  `name` varchar(1024) NOT NULL COMMENT 'Name',
  `content` longtext NOT NULL COMMENT 'Content',
  `start_at` datetime NOT NULL COMMENT 'Start time',
  `end_at` datetime NOT NULL COMMENT 'End Time',
  `creator` bigint(11) NOT NULL COMMENT 'CreatorID',

  `is_delete` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT 'Delete Label（0:Not deleted、1:deleted）',
  `create_at` datetime NOT NULL COMMENT 'Record the creation time',
  `update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record the modification time',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Config table';

ALTER TABLE forum_user ADD `source` varchar(64) default 'REGISTER' COMMENT 'Source' AFTER `sex`;
ALTER TABLE forum_user ADD `ext` blob COMMENT 'Extended infomation' AFTER `signature`;

SET FOREIGN_KEY_CHECKS = 1;
