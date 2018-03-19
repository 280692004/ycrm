CREATE TABLE `f_capitalflow` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uuid` varchar(50) DEFAULT NULL COMMENT 'uuid',
  `naturalId` varchar(50) DEFAULT NULL COMMENT 'naturalId',
  `version` int(11) DEFAULT NULL COMMENT '版本',
  `createBy_id` varchar(32) DEFAULT NULL,
  `createByName` varchar(50) DEFAULT NULL COMMENT '创建人',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `lastModifyBy_id` varchar(32) DEFAULT NULL,
  `lastModifyByName` varchar(50) DEFAULT NULL COMMENT '最后修改人',
  `lastModifyTime` datetime DEFAULT NULL COMMENT '最后修改时间',

	`billdeta` datetime NOT NULL COMMENT '记账日期',
  `capitalFlowTypeCode` varchar(100) NOT NULL COMMENT '资金流动类型【收入/支出】',
	`unitCode` varchar(100) NOT NULL COMMENT '单位',
	`qty` double(18,2) NOT NULL COMMENT '数量',
	`unitprice` double(18,2) NOT NULL COMMENT '单价',
	`totalamount` double(18,2) NOT NULL COMMENT '总价',
  `user` varchar(50) NOT NULL COMMENT '使用者',
  `status` varchar(255) DEFAULT NULL COMMENT '..',
  `summary` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;