package com.techstack.sjs.permission.biz;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techstack.sjs.permission.dao.ActionDao;
import com.techstack.sjs.permission.dao.MenuDao;
import com.techstack.sjs.permission.dao.RoleDao;
import com.techstack.sjs.permission.entity.Action;
import com.techstack.sjs.permission.entity.Menu;
import com.techstack.sjs.permission.entity.Role;
import com.techstack.sjs.permission.enums.NodeTypeEnum;

@Component("menuBiz")
public class MenuBiz {
	
	@Autowired
	private MenuDao menuDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private ActionDao actionDao;
	
	/**
	 * @Description: 获取用于编制菜单时的树.
	 * @param @param actionUrl
	 * @param @return    
	 * @return String
	 */
	@SuppressWarnings("rawtypes")
	public String getTreeMenu(String actionUrl) {
		Set treeData = getTreeData(null);
		StringBuffer strJson = new StringBuffer();
		recursionTreeMenu(0L, strJson, treeData, actionUrl); //从一级菜单开始递归
		return strJson.toString();
	}

	/**
	 * @Description: 根据父菜单ID获取该菜单下的所有子孙菜单(如果为空，则为获取所有的菜单).
	 * @param @param parentId
	 * @param @return    
	 * @return List
	 */
	@SuppressWarnings("rawtypes")
	private Set getTreeData(Long parentId) {
		Menu parentMenu = new Menu();
		parentMenu.setId(parentId);
		return menuDao.findByParentMenu(parentMenu);
	}

	/**
	 * @Description: 递归输出树形菜单
	 * @param @param pId (父节点ID，若为0则表示一级菜单)
	 * @param @param buffer
	 * @param @param list
	 * @param @param url    
	 * @return void
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void recursionTreeMenu(Long pId, StringBuffer buffer, Set set, String url) {
		if (pId == 0) {	//若为一级菜单
			buffer.append("<ul class=\"tree treeFolder collapse \" >");
		} else {
			buffer.append("<ul>");
		}
		Set<Menu> setMenu = getSonMenuSetByPid(pId, set);
		for (Menu menu : setMenu) {
			//String id = map.get("id").toString();// id
			//String name = map.get("name").toString();// 名称
			//String isLeaf = map.get("isLeaf").toString();// 是否叶子科目
			Long id = menu.getId();
			String name = menu.getName();
			Integer isLeaf = menu.getIsLeaf();
			buffer.append("<li><a onclick=\"onClickMenuNode(" + id + ")\"  href=\"" + url + "?id=" + id + "\" target=\"ajax\" rel=\"jbsxBox\"  value=" + id + ">" + name + "</a>");
			if (isLeaf != NodeTypeEnum.LEAF.getValue()) {	//非叶子节点继续递归
				recursionTreeMenu(id, buffer, set, url);
			}
			buffer.append("</li>");
		}
		buffer.append("</ul>");
	}

	/**
	 * @Description: 根据(pId)获取(menuList)中的所有子菜单集合.
	 * @param @param pId
	 * @param @param menuList
	 * @param @return    
	 * @return List<Map>
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Set<Menu> getSonMenuSetByPid(Long pId, Set<Menu> menuSet) {
		Set sonMenuSet = new HashSet<Menu>();
		for (Menu menu : menuSet) {
			//Map map = (Map) menu;
			if (menu != null) {
				//String parentId = map.get("pId").toString();// 父id
				Long parentId = menu.getParentMenu().getId();
				if (parentId == pId) {
					sonMenuSet.add(menu);
				}
			}
		}
		return sonMenuSet;
	}

	/**
	 * @Description: 根据ID删除菜单
	 * @param @param id    
	 * @return void
	 */
	public void delete(Long id) {
		menuDao.delete(id);
	}

	/**
	 * @Description: 根据角色ID集合获取菜单
	 * @param @param roleIdsStr
	 * @param @return    
	 * @return List
	 */
	@SuppressWarnings("rawtypes")
	public Set listByRoleIds(String roleIdsStr) {
		List<String> roleIds = Arrays.asList(roleIdsStr.split(","));
		Set<Menu> menus = new HashSet<Menu>();
		for(String roleId : roleIds){
			Role role = roleDao.getOne(Long.parseLong(roleId));
			menus.addAll(role.getMenus());
		}
		return menus;
	}

	/**
	 * @Description: 根据用户拥有的角色ID,获得该角色的权限构建树形菜单
	 * @param @param roleIds
	 * @param @return
	 * @param @throws PermissionException    
	 * @return String
	 */
	@SuppressWarnings("rawtypes")
	public String buildPermissionTree(String roleIds){
		Set treeData = null;
		try {
			//treeData = this.pmsMenuDao.listByRoleIds(roleIds);
			//List<String> roldIds = Arrays.asList(roleIds.split(","));
			treeData = listByRoleIds(roleIds);
			if (treeData.isEmpty()) {
				//log.error("用户没有分配菜单权限");
			}
		} catch (Exception e) {
			//log.error("根据角色查询菜单出现错误", e);
		}
		StringBuffer strJson = new StringBuffer();
		buildAdminPermissionTree(0L, strJson, treeData); //从一级菜单开始构建
		return strJson.toString();
	}

	/**
	 * @Description: 构建管理后台的树形权限功能菜单
	 * @param @param pId
	 * @param @param treeBuf
	 * @param @param menuList    
	 * @return void
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void buildAdminPermissionTree(Long pId, StringBuffer treeBuf, Set menuSet) {
		
		Set<Menu> sonMenuSet = getSonMenuSetByPid(pId, menuSet);
		for (Menu sonMenu : sonMenuSet) {
			//String id = map.get("id").toString();// id
			//String name = map.get("name").toString();// 名称
			//String isLeaf = map.get("isLeaf").toString();// 是否叶子
			//String level = map.get("level").toString();// 菜单层级（1、2、3、4）
			//String url = map.get("url").toString(); // ACTION访问地址
			Long id = sonMenu.getId();
			String name = sonMenu.getName();
			Integer isLeaf = sonMenu.getIsLeaf();
			//Integer level = sonMenu.getLevel();
			String url = sonMenu.getUrl();
			
			String navTabId = "";
			if (StringUtils.isNotEmpty(sonMenu.getTargetName())) {
				navTabId = sonMenu.getTargetName(); // 用于刷新查询页面
			}
			
			/*if ("1".equals(level)){ 	//若是一级菜单
				treeBuf.append("<div class='accordionHeader'>");
				treeBuf.append("<h2>" + name + "</h2>");
				treeBuf.append("</div>");
				treeBuf.append("<div class='accordionContent'>");
			}*/
			
			if(sonMenu.getParentMenu() == null) {	//若是一级菜单
				treeBuf.append("<div class='accordionHeader'>");
				treeBuf.append("<h2>" + name + "</h2>");
				treeBuf.append("</div>");
				treeBuf.append("<div class='accordionContent'>");
			}
			
			if (isLeaf == NodeTypeEnum.LEAF.getValue()) {	//若是叶子节点
				treeBuf.append("<li><a href='" + url + "' target='navTab' rel='" + navTabId + "'>" + name + "</a></li>");
			} else {
				
				/*if ("1".equals(level)){
					treeBuf.append("<ul class='tree treeFolder'>");
				}else{
					treeBuf.append("<li><a>" + name + "</a>");
					treeBuf.append("<ul>");
				}*/
				
				if(sonMenu.getParentMenu() == null) {	//若是一级菜单
					treeBuf.append("<ul class='tree treeFolder'>");
				} else {
					treeBuf.append("<li><a>" + name + "</a>");
					treeBuf.append("<ul>");
				}
				
				buildAdminPermissionTree(id, treeBuf, menuSet);
				
				/*if ("1".equals(level)){
					treeBuf.append("</ul>");
				}else{
					treeBuf.append("</ul></li>");
				}*/
				
				if(sonMenu.getParentMenu() == null) {	//若是一级菜单
					treeBuf.append("</ul>");
				} else {
					treeBuf.append("</ul></li>");
				}
				
			}
			
			/*if ("1".equals(level)){
				treeBuf.append("</div>");
			}*/
			if(sonMenu.getParentMenu() == null) {	//若是一级菜单
				treeBuf.append("</div>");
			}
		}

	}

	/**
	 * @Description: 创建菜单
	 * @param @param model    
	 * @return void
	 */
	public void createMenu(Menu model){
		try {
			Menu newPmsMenu = model;
			//PmsMenu parentPmsMenu = pmsMenuDao.getById(PmsMenu.class, newPmsMenu.getParentId());
			Menu parentPmsMenu = menuDao.getOne(model.getId()).getParentMenu();
			if (null == parentPmsMenu) {	//新的一级菜单
				//parentPmsMenu = new PmsMenu();
				//parentPmsMenu.setId(0L);
				newPmsMenu.setIsLeaf(NodeTypeEnum.LEAF.getValue());
				newPmsMenu.setLevel(1);
				newPmsMenu.setParentMenu(null);;
			} else {
				//parentPmsMenu = this.pmsMenuDao.getById(PmsMenu.class,parentPmsMenu.getId());
				//parentPmsMenu = getBaseDao().getById(PmsMenu.class,parentPmsMenu.getId());
				newPmsMenu.setIsLeaf(NodeTypeEnum.LEAF.getValue());
				newPmsMenu.setLevel(parentPmsMenu.getLevel() + 1);
				parentPmsMenu.setIsLeaf(NodeTypeEnum.PARENT.getValue());
				//pmsMenuDao.saveOrUpdate(parentPmsMenu);
				//getBaseDao().saveOrUpdate(parentPmsMenu);
				menuDao.save(parentPmsMenu);
			}
			//pmsMenuDao.saveOrUpdate(newPmsMenu);
			menuDao.save(newPmsMenu);
		} catch (Exception e) {
			//log.error("添加菜单报错", e);
		}
	}

	/**
	 * @Description: 根据角色ID，获取菜单ID集合(逗号分隔的菜单ID字符串)
	 * @param @param roleId
	 * @param @return
	 * @param @throws PermissionException    
	 * @return String
	 */
	public String getMenuIdsByRoleId(Long roleId){
		//List<PmsRoleMenu> menuList = pmsRoleMenuDao.listByRoleId(roleId);
		//List<PmsRoleMenu> menuList = getBaseDao().selectList(getStatement("listRoleMenuByRoleId"), roleId);
		Set<Menu> menuSet= roleDao.getOne(roleId).getMenus();
		StringBuffer menuIds = new StringBuffer("");
		if (menuSet != null && !menuSet.isEmpty()) {
			for (Menu menu : menuSet) {
				menuIds.append(menu.getId()).append(",");
			}
		}
		return menuIds.toString();
	}

	/**
	 * @Description: 根据已有的菜单ID集合、角色的功能点集合，生成菜单权限树
	 * @param @param menuIdsStr
	 * @param @param actionIdsStr
	 * @param @return    
	 * @return String
	 */
	@SuppressWarnings("rawtypes")
	public String buildMenuActionTree(String menuIdsStr, String actionIdsStr) {

		Set allMenuSet = getTreeData(null); // 获取所有的菜单
		StringBuffer treeBuf = new StringBuffer();
		buildPermissionTree(0L, treeBuf, allMenuSet, menuIdsStr, actionIdsStr); //从一级菜单开始构建
		return treeBuf.toString();

	}

	/**
	 * @Description: 创建分配权限的菜单树
	 * @param @param pId
	 * @param @param treeBuf
	 * @param @param allMenuList
	 * @param @param menuIds
	 * @param @param actionIds    
	 * @return void
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void buildPermissionTree(Long pId, StringBuffer treeBuf, Set allMenuSet, String menuIds, String actionIds) {
		if (pId == 0) {  // 为一级菜单
			treeBuf.append("<ul class=\"tree treeFolder treeCheck expand\" >");
		} else {
			treeBuf.append("<ul>");
		}

		Set<Menu> sonMenuSet = getSonMenuSetByPid(pId, allMenuSet);
		for (Menu sonMenu : sonMenuSet) {
			//String menuId = sonMenu.get("id").toString();// id
			//String parentId = sonMenu.get("pId").toString(); // PID
			//String name = sonMenu.get("name").toString();// 名称
			//String isLeaf = sonMenu.get("isLeaf").toString();// 是否叶子
			Long menuId = sonMenu.getId();
			Long parentId = sonMenu.getParentMenu().getId();
			String name = sonMenu.getName();
			Integer isLeaf = sonMenu.getIsLeaf();
			if (menuIds.indexOf("," + menuId + ",") > -1) {
				treeBuf.append("<li><a menuid='" + menuId + "' checked='true' pid='" + parentId + "' isleaf='" + isLeaf + "'>" + name + " (M)</a>");
			} else {
				treeBuf.append("<li><a menuid='" + menuId + "' pid='" + parentId + "' isleaf='" + isLeaf + "'>" + name + " (M)</a>");
			}
			if (isLeaf == NodeTypeEnum.LEAF.getValue()) {  // 如果叶子菜单，则处理挂在此菜单下的权限功能点

				// 获取叶子菜单下所有的功能权限
				//List<PmsAction> actionList = pmsActionDao.listAllByMenuId(Long.valueOf(menuId));
				//Map<String, Object> param = new HashMap<String, Object>();
				//param.put("menuId", Long.valueOf(menuId));
				Menu relevantMenu = new Menu();
				relevantMenu.setId(menuId);
				Set<Action> actionSet = actionDao.findByRelevantMenu(relevantMenu);
				if (null != actionSet && !actionSet.isEmpty()) {
					treeBuf.append("<ul>");
					for (Action action : actionSet) {
						if (actionIds.indexOf("," + action.getId().toString() + ",") > -1) {
							treeBuf.append("<li><a checked='true' actionid='" + action.getId() + "'>" + action.getActionName() + " (A)</a>");
						} else {
							treeBuf.append("<li><a actionid='" + action.getId() + "'>" + action.getActionName() + " (A)</a>");
						}
					}
					treeBuf.append("</ul>");
				}

			} else {
				// 不是叶子菜单，递归
				buildPermissionTree(menuId, treeBuf, allMenuSet, menuIds, actionIds);
			}
			treeBuf.append("</li>");
		}

		treeBuf.append("</ul>");
	}

	/**
	 * @Description: 为角色分配权限
	 * @param @param roleId
	 * @param @param menuIds
	 * @param @param actionIds
	 * @param @throws PermissionException    
	 * @return void
	 */
	public void assignPermission(Long roleId, String menuIds, String actionIds){
		
		if (roleId == null) {
			return;
		}
		
		Role role = roleDao.getOne(roleId);
		
		if (StringUtils.isNotEmpty(menuIds)) {
			String[] menuArray = menuIds.split(",");
			for (String menuId : menuArray) {
				Menu menu = menuDao.getOne(Long.parseLong(menuId));
				if(!role.getMenus().contains(menu)){
					// 防止重复添加菜单权限
					role.getMenus().add(menu);
					// 新增菜单权限
					//pmsRoleMenuDao.saveOrUpdate(entity);
				}
			}
			roleDao.save(role);
		}		
		
		if (StringUtils.isNotEmpty(actionIds)) {
			String[] actionArray = actionIds.split(",");
			for (String actionId : actionArray) {
				Action entity = actionDao.getOne(Long.parseLong(actionId));
				if(!role.getActions().contains(entity)){
					role.getActions().add(entity);
				}
			}
			roleDao.save(role);
		}
	}

	/**
	 * @Description: 构建查找带回
	 * @param @return    
	 * @return String
	 */
	@SuppressWarnings("rawtypes")
	public String buildLookUpMenu() {
		Set treeData = getTreeData(null);
		StringBuffer strJson = new StringBuffer();
		recursionTreeMenuLookUp(0L, strJson, treeData); //从一级菜单开始构建
		return strJson.toString();
	}

	/**
	 * @Description: 查找带回权限树
	 * @param @param pId
	 * @param @param buffer
	 * @param @param list    
	 * @return void
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void recursionTreeMenuLookUp(Long pId, StringBuffer buffer, Set set) {
		if (pId == 0) {	//为一级菜单
			buffer.append("<ul class=\"tree treeFolder\" >");
		} else {
			buffer.append("<ul>");
		}
		Set<Menu> sonMenuSet = getSonMenuSetByPid(pId, set);
		for (Menu sonMenu : sonMenuSet) {
			//String id = map.get("id").toString();// id
			//String parentId = map.get("pId").toString();// 父id
			//String name = map.get("name").toString();// 名称
			//String isLeaf = map.get("isLeaf").toString();// 是否叶子科目
			Long id = sonMenu.getId();
			Long parentId = sonMenu.getParentMenu().getId();
			String name = sonMenu.getName();
			Integer isLeaf = sonMenu.getIsLeaf();

			if (isLeaf == NodeTypeEnum.LEAF.getValue()) {	//若为叶子节点
				buffer.append("<li><a onclick=\"$.bringBack({id:'" + id + "', name:'" + name + "'})\"  href=\"javascript:\"  >" + name + "</a>");
			} else {
				buffer.append("<li><a id='" + id + "' pid='" + parentId + "' isleaf='" + isLeaf + "'>" + name + "</a>");
			}

			if (isLeaf != NodeTypeEnum.LEAF.getValue()) { //非叶子节点继续递归
				recursionTreeMenuLookUp(id, buffer, set);
			}
			buffer.append("</li>");
		}
		buffer.append("</ul>");
	}

	/**
	 * @Description: 根据菜单ID查找菜单（可用于判断菜单下是否还有子菜单）.
	 * @param @param parentId
	 * @param @return    
	 * @return List<PmsMenu>
	 */
	public Set<Menu> listByParentId(Long parentId) {
		Menu parentMenu = new Menu();
		parentMenu.setId(parentId);
		return menuDao.findByParentMenu(parentMenu);
	}

	/**
	 * @Description: 根据名称和是否叶子节点查询数据
	 * @param @param map
	 * @param @return    
	 * @return List<PmsMenu>
	 */
	public Set<Menu> getMenuByNameAndIsLeaf(Map<String, Object> map) {
		//return pmsMenuDao.getMenuByNameAndIsLeaf(map);
		//return getBaseDao().selectList(getStatement("listMenuBy"), map);
		return menuDao.findByNameAndIsLeaf(map.get("name").toString(), Integer.parseInt(map.get("isLeaf").toString()));
	}

	/**
	 * @Description: 根据菜单ID获取菜单
	 * @param @param pid
	 * @param @return    
	 * @return PmsMenu
	 */
	public Menu getById(Long pid) {
		//return pmsMenuDao.getById(PmsMenu.class,pid);
		//return getBaseDao().getById(PmsMenu.class,pid);
		return menuDao.getOne(pid);
	}

	/**
	 * @Description: 更新菜单.
	 * @param @param menu    
	 * @return void
	 */
	public void update(Menu menu) {
		//pmsMenuDao.saveOrUpdate(menu);
		menuDao.save(menu);
	}
	
	/**
	 * @Description: 根据角色ID统计关联到此角色的菜单数
	 * @param @param roleId
	 * @param @return    
	 * @return int
	 */
	public int countMenuByRoleId(Long roleId) {
		//List<PmsRoleMenu> meunList = pmsRoleMenuDao.listByRoleId(roleId);
		/*List<PmsRoleMenu> meunList = getBaseDao().selectList(getStatement("listRoleMenuByRoleId"), roleId);
		if (meunList == null || meunList.isEmpty()) {
			return 0;
		} else {
			return meunList.size();
		}*/
		Role role = roleDao.getOne(roleId);
		return role.getMenus().size();
	}

}
