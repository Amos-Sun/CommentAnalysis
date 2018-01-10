-- forbid foreign key
SET FOREIGN_KEY_CHECKS=0;
drop table if exists `relation`;
CREATE TABLE `relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `cid` varchar(32) not null COMMENT '电影的唯一标识',
  `user_name` varchar(191) NOT NULL COMMENT '用户名',
  `comment` text(2000) DEFAULT NULL COMMENT '用户的评论',
  `evaluation` tinyint DEFAULT '0' COMMENT '评价类别 -1 负面评价 0 中性 1 正面评价',

  `add_time` DateTime not null,
  `modification_time` TIMESTAMP not null default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_cid` (`cid`,`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
