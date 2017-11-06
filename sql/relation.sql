CREATE TABLE `relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `v_id` int(11) NOT NULL COMMENT '对应电影的id',
  `u_id` int(11) NOT NULL COMMENT '对应用户id',
  `u_comment` varchar(256) DEFAULT NULL COMMENT '用户的评论',
  PRIMARY KEY (`id`),
  UNIQUE KEY `v_u_id` (`v_id`,`u_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table `relation` drop column `u_comment`;
alter table `relation` add column  `u_comment` text(2000) DEFAULT NULL COMMENT '用户的评论';
alter table `relation` add column `add_time` VARCHAR (16) not null;
alter table `relation` add column `modification_time` TIMESTAMP default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ;

-- alter table `relation` add column `good_percent` varchar(5) DEFAULT '0' COMMENT '好评百分比';
-- alter table `relation` add column `bad_percent` varchar(5) DEFAULT '0' COMMENT '差评百分比';
alter table `relation` drop column `good_percent`;
alter table `relation` drop column `bad_percent`;
alter table `relation` drop column `evaluattion`;

alter table `relation` add column `evaluation` tinyint DEFAULT '0' COMMENT '评价类别 -1 负面评价 0 中性 1 正面评价';

# cid 用来标识电影