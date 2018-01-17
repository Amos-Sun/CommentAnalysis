SET FOREIGN_KEY_CHECKS=0;
drop table if EXISTS `video`;
CREATE TABLE `video` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `cid` varchar(32) not null COMMENT '电影的唯一标识',
  `name` varchar(128) NOT NULL COMMENT '电影名字',
  `url` varchar(256) not null COMMENT '电影的url',
  `detail` varchar(2048) default null COMMENT '电影简介',
  `picture_path` varchar(256) default null COMMENT '电影剧照存储本地路径',
  `time` varchar(16) DEFAULT NULL COMMENT '上映时间',
  `actors` varchar(256) DEFAULT NULL COMMENT '演员',
  `type` varchar(256) DEFAULT NULL COMMENT '电影的分类',
  `good_percent` varchar(5) DEFAULT '0' COMMENT '好评百分比',
  `bad_percent` varchar(5) DEFAULT '0' COMMENT '差评百分比',
  `evaluate` varchar(256) DEFAULT NULL COMMENT '电影评价信息存储',
  `last_evaluate_time` VARCHAR (10) default null COMMENT '最后一条评论的时间',
  `add_time` DateTime not null,
  `modification_time` TIMESTAMP not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新数据时间',
  PRIMARY KEY (`id`),
  INDEX`c_id`(`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;