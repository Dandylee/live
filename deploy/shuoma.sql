CREATE DATABASE shuoma;

use shuoma;

CREATE TABLE `shuoma_login_account` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `userName` VARCHAR(80) NOT NULL DEFAULT '',
`account` VARCHAR(80) unique NOT NULL,
  `lastLogin` BIGINT NOT NULL DEFAULT 0,
  `pwd` VARCHAR(80) NOT NULL,
  `level` INT NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `shuoma_user` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `userName` VARCHAR(80) NOT NULL DEFAULT '',
  `password` VARCHAR(80) NOT NULL,
  `email` VARCHAR(80) DEFAULT NULL,
  `mobile` VARCHAR(80) DEFAULT NULL,
`account` VARCHAR(80) unique not null,
  `registerTime` BIGINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `shuoma_verify_code` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
`agentCode` VARCHAR(64) NOT NULL,
`verifyCode` VARCHAR(256) NOT NULL,
`isValid` int not null DEFAULT 0 comment '未使用',
`createTime` BIGINT DEFAULT 0,
`operator` VARCHAR(64)  NULL default '',
`verifyTime` BIGINT NOT NULL DEFAULT 0,
PRIMARY KEY (`id`),
key `index_verify_code` (`verifyCode`),
key `index_agentCode` (`agentCode`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


CREATE TABLE `shuoma_machine_print_log` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `operTime` timestamp not null default CURRENT_TIMESTAMP,
`userId` INT(64) NOT NULL,
`machineId` INT(64) NOT NULL,
`userName` VARCHAR(64) NOT NULL,
  `machineName` VARCHAR(128) DEFAULT NULL,
  `operType` INT not null DEFAULT 1,
`operInfo` VARCHAR(1024) NOT NULL default '',
PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

接入:

公共参数:
data
timestamp
sign(计算方法)
业务参数统一使用data包裹参数,参数格式为json


验签计算问题
key值为1849CB30471FFE4F668250BC78ED69B6
1.获取key+data+timestamp参数
2.做md5运算
3.转为小写字母

比较sign值


