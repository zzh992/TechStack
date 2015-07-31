INSERT INTO `PMS_MENU` VALUES ('1', '1', '2013-11-13 13:17:43', 'parent_menu', '##', '001', '0', '1', '0', '#');
INSERT INTO `PMS_MENU` VALUES ('2', '1', '2013-11-13 13:17:43', 'child_menu', 'pmsMenu_pmsMenuList.action', '00101', '1', '2', '1', 'listPmsMenu');

INSERT INTO `PMS_ACTION` VALUES ('1', '0', '2014-12-10 16:36:43', 'menu_list', 'pms:menu:view', 'menu_list', '2', 'child_menu');
INSERT INTO `PMS_ACTION` VALUES ('2', '0', '2014-12-10 16:37:23', 'menu_add', 'pms:menu:add', 'menu_add', '2', 'child_menu');

INSERT INTO `PMS_ROLE` VALUES ('1', '0', '2014-12-16 14:10:55', '1', 'superAdmin', 'superAdmin');
INSERT INTO `PMS_ROLE` VALUES ('2', '0', '2014-12-11 14:11:56', '0', 'admin', 'admin');

INSERT INTO `PMS_USER` VALUES ('1', '1', '2014-12-10 15:47:33', 'admin', 'd033e22ae348aeb5660fc2140aec35850c4da997', 'admin', '1');
INSERT INTO `PMS_USER` VALUES ('2', '1', '2014-12-10 16:12:52', 'guoj', 'a30972b369af4ed53d3aa4a75cc1de7f61d4f063', 'guoj', '0');

INSERT INTO `PMS_ROLE_ACTION` VALUES ('1', '0', '2014-12-18 14:04:33', '1', '2');
INSERT INTO `PMS_ROLE_ACTION` VALUES ('2', '0', '2014-12-18 14:04:33', '2', '1');

INSERT INTO `PMS_ROLE_MENU` VALUES ('1', '2', '2014-12-18 14:04:33', '1', '2');
INSERT INTO `PMS_ROLE_MENU` VALUES ('2', '3', '2014-12-18 14:04:33', '2', '1');

INSERT INTO `PMS_ROLE_USER` VALUES ('1', '0', '2014-12-10 15:47:33', '1', '1');
INSERT INTO `PMS_ROLE_USER` VALUES ('2', '0', '2014-12-10 16:12:52', '2', '2');