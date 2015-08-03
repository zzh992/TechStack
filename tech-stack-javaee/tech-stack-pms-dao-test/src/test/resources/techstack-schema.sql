DROP TABLE IF EXISTS `PMS_MENU`;
CREATE TABLE `PMS_MENU` (
  `ID` bigint(20) NOT NULL auto_increment,
  `VERSION` int(11) NOT NULL default '0',
  `CREATE_TIME` datetime NOT NULL,
  `NAME` varchar(90) default NULL,
  `URL` varchar(150) default NULL,
  `NUMBER` varchar(20) default NULL,
  `IS_LEAF` smallint(6) default NULL,
  `LEVEL` smallint(6) default NULL,
  `PARENT_ID` bigint(20) NOT NULL default '0',
  `TARGET_NAME` varchar(50) default NULL,
  PRIMARY KEY  (`ID`)
)DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `PMS_ACTION`;
CREATE TABLE `PMS_ACTION` (
  `ID` bigint(20) NOT NULL auto_increment,
  `VERSION` int(11) NOT NULL default '0',
  `CREATE_TIME` datetime NOT NULL,
  `ACTION_NAME` varchar(90) NOT NULL,
  `ACTION` varchar(100) NOT NULL,
  `REMARK` varchar(300) NOT NULL,
  `MENU_ID` bigint(20) NOT NULL,
  `MENU_NAME` varchar(300) NOT NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `A_KEY_2` (`ACTION`),
  UNIQUE KEY `A_KEY_3` (`ACTION_NAME`)
) DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `PMS_ROLE`;
CREATE TABLE `PMS_ROLE` (
  `ID` bigint(20) NOT NULL auto_increment,
  `VERSION` int(11) NOT NULL default '0',
  `CREATE_TIME` datetime NOT NULL,
  `ROLE_TYPE` smallint(50) NOT NULL,
  `ROLE_NAME` varchar(90) NOT NULL,
  `REMARK` varchar(300) default NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `A_KEY_4` (`ROLE_NAME`)
)DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `PMS_USER`;
CREATE TABLE `PMS_USER` (
  `ID` bigint(20) NOT NULL auto_increment,
  `VERSION` int(11) NOT NULL default '0',
  `CREATE_TIME` datetime NOT NULL,
  `LOGIN_NAME` varchar(50) NOT NULL,
  `LOGIN_PWD` varchar(256) NOT NULL,
  `REMARK` varchar(300) default NULL,
  `TYPE` int(11) default NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `A_KEY_5` (`LOGIN_NAME`)
)DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `PMS_ROLE_ACTION`;
CREATE TABLE `PMS_ROLE_ACTION` (
  `ID` bigint(20) NOT NULL auto_increment,
  `VERSION` int(11) NOT NULL,
  `CREATE_TIME` datetime NOT NULL,
  `ROLE_ID` bigint(20) NOT NULL,
  `ACTION_ID` bigint(20) NOT NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `A_KEY_6` (`ROLE_ID`,`ACTION_ID`)
)DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `PMS_ROLE_MENU`;
CREATE TABLE `PMS_ROLE_MENU` (
  `ID` bigint(20) NOT NULL auto_increment,
  `VERSION` int(11) NOT NULL,
  `CREATE_TIME` datetime NOT NULL,
  `ROLE_ID` bigint(20) NOT NULL,
  `MENU_ID` bigint(20) NOT NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `A_KEY_7` (`ROLE_ID`,`MENU_ID`)
)DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `PMS_ROLE_USER`;
CREATE TABLE `PMS_ROLE_USER` (
  `ID` bigint(20) NOT NULL auto_increment,
  `VERSION` int(11) NOT NULL,
  `CREATE_TIME` datetime NOT NULL,
  `ROLE_ID` bigint(20) NOT NULL,
  `USER_ID` bigint(20) NOT NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `A_KEY_8` (`ROLE_ID`,`USER_ID`)
)DEFAULT CHARSET=utf8;






;