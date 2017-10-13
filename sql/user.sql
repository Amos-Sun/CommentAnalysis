CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `u_name` varchar(64) NOT NULL COMMENT '用户名',
  `u_age` int(3) NOT NULL DEFAULT '0' COMMENT '用户年龄',
  `u_sex` char(2) DEFAULT NULL COMMENT '用户性别',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_name` (`u_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table `user` add column `add_time` VARCHAR (16) not null;
alter table `user` add column `modification_time` TIMESTAMP default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ;