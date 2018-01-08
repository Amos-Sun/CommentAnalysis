drop table if exists `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) NOT NULL COMMENT '用户名',
  `age` int(3) NOT NULL DEFAULT '0' COMMENT '用户年龄',
  `sex` char(2) DEFAULT NULL COMMENT '用户性别',
  `add_time` DateTime not null,
  `modification_time` TIMESTAMP not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;