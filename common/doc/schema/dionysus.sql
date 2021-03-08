/*
 Navicat Premium Data Transfer

 Source Server         : 47.93.246.239
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : 47.93.246.239:3306
 Source Schema         : dionysus

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 14/03/2020 10:32:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for config_info
-- ----------------------------
DROP TABLE IF EXISTS `config_info`;
CREATE TABLE `config_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) DEFAULT NULL,
  `group_id` varchar(255) DEFAULT NULL,
  `content` longtext NOT NULL COMMENT 'content',
  `md5` varchar(32) DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
  `src_user` mediumtext,
  `src_ip` varchar(20) DEFAULT NULL,
  `app_name` varchar(128) DEFAULT NULL,
  `tenant_id` varchar(128) DEFAULT NULL,
  `c_desc` varchar(256) DEFAULT NULL,
  `c_use` varchar(64) DEFAULT NULL,
  `effect` varchar(64) DEFAULT NULL,
  `type` varchar(64) DEFAULT NULL,
  `c_schema` mediumtext,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_configinfo_datagrouptenant` (`data_id`,`group_id`,`tenant_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COMMENT='config_info';

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(16) DEFAULT NULL COMMENT 'M目录 C菜单 F按钮',
  `name` varchar(64) DEFAULT NULL COMMENT '名称',
  `title` varchar(64) DEFAULT NULL COMMENT '标题',
  `parent_id` varchar(64) DEFAULT NULL COMMENT '父级id',
  `level` int(11) DEFAULT NULL COMMENT '层级',
  `url` varchar(255) DEFAULT NULL,
  `method` varchar(16) DEFAULT NULL COMMENT '请求方法:  * GET POST PUT',
  `visible` varchar(4) DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `server_id` varchar(64) DEFAULT NULL COMMENT '所属微服务',
  `order_num` int(11) DEFAULT NULL COMMENT '显示顺序',
  `perms` varchar(128) DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(255) DEFAULT NULL COMMENT '菜单图标',
  `create_by` varchar(64) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资源表';

-- ----------------------------
-- Table structure for sys_oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `sys_oauth_client_details`;
CREATE TABLE `sys_oauth_client_details` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `client_id` varchar(64) DEFAULT NULL,
  `client_secret` varchar(64) DEFAULT NULL,
  `resource_ids` varchar(64) DEFAULT NULL,
  `scope` varchar(64) DEFAULT NULL COMMENT '客户受限范围',
  `authorized_grant_type` varchar(64) DEFAULT NULL COMMENT '授权客户端使用的授权类型',
  `web_server_redirect_uri` varchar(64) DEFAULT NULL,
  `authority` varchar(64) DEFAULT NULL COMMENT '授予客户端的权限',
  `access_token_validity` int(11) DEFAULT NULL COMMENT 'token过期时间',
  `refresh_token_validity` int(11) DEFAULT NULL COMMENT 'token 刷新时间',
  `additional_information` varchar(64) DEFAULT NULL,
  `autoapprove` varchar(64) DEFAULT NULL,
  `del_flag` varchar(4) DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(64) NOT NULL COMMENT '角色码',
  `name` varchar(64) DEFAULT NULL COMMENT '角色名',
  `level` int(11) DEFAULT NULL COMMENT '角色等级',
  `data_scope` varchar(32) DEFAULT NULL COMMENT 'ALL-全部 DEPT-部门 OWN-自己 ',
  `create_by` varchar(64) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` varchar(4) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `role_code_index` (`code`) USING BTREE COMMENT '角色码唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- ----------------------------
-- Table structure for sys_role_menu_rel
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu_rel`;
CREATE TABLE `sys_role_menu_rel` (
  `id` varchar(64) NOT NULL DEFAULT '',
  `role_id` int(11) DEFAULT NULL,
  `menu_id` int(11) DEFAULT NULL,
  `type` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色资源关联表';

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `user_name` varchar(64) DEFAULT NULL COMMENT '登录名',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `nick_name` varchar(64) DEFAULT NULL COMMENT '昵称',
  `phone` varchar(32) DEFAULT NULL COMMENT '手机号',
  `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `type` varchar(16) DEFAULT NULL COMMENT '0-超级管理员 1-系统内部管理员  2-用户',
  `status` varchar(4) DEFAULT NULL COMMENT '0-待审核 1-正常 2-审核失败 9-已冻结锁定 ',
  `created_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_by` varchar(64) DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` varchar(2) DEFAULT NULL COMMENT '0-正常 1-已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
-- Table structure for sys_user_dept_rel
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_dept_rel`;
CREATE TABLE `sys_user_dept_rel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `dept_id` int(11) NOT NULL,
  `status` char(1) DEFAULT NULL COMMENT '2-已过时或无效',
  `of_ruler` char(1) DEFAULT NULL COMMENT '是否部门管理员',
  `created_by` int(11) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_time` datetime DEFAULT NULL,
  `del_flag` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户部门关联表';

-- ----------------------------
-- Table structure for sys_user_role_rel
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role_rel`;
CREATE TABLE `sys_user_role_rel` (
  `id` varchar(64) NOT NULL DEFAULT '',
  `user_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `rel_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

SET FOREIGN_KEY_CHECKS = 1;
