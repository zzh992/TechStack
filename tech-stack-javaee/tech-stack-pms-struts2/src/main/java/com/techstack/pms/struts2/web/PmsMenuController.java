package com.techstack.pms.struts2.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.techstack.sms2.base.annotation.permission.Permission;
import com.techstack.sms2.base.struts.BaseAction;
import com.techstack.sms2.permission.biz.PmsActionBiz;
import com.techstack.sms2.permission.biz.PmsMenuBiz;
import com.techstack.sms2.permission.entity.PmsAction;
import com.techstack.sms2.permission.entity.PmsMenu;
import com.techstack.sms2.permission.enums.NodeTypeEnum;

/**
 * @Title: PmsMenuAction.java 
 * @Description: 菜单ACTION
 * @author zzh
 */
public class PmsMenuController extends BaseAction implements ModelDriven<PmsMenu> {

	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory.getLog(PmsMenuController.class);
	
	/**	菜单的编辑URL	*/
	private static final String EDIT_MENU_ACTION = "pmsMenu_pmsMenuEdit.action";	

	private PmsMenu pmsMenu = new PmsMenu();

	@Override
	public PmsMenu getModel() {
		return pmsMenu;
	}

	@Autowired
	private PmsMenuBiz pmsMenuBiz;

	@Autowired
	private PmsActionBiz pmsActionBiz;

	/**
	 * 列出要管理的菜单.
	 * @return PmsMenuList .
	 */
	@Permission("pms:menu:view")
	public String pmsMenuList() {
		String str = pmsMenuBiz.getTreeMenu(EDIT_MENU_ACTION);//构建树形菜单的HTML
		this.putData("tree", str);
		return "pmsMenuList";
	}

	/**
	 * 进入新菜单添加页面.
	 * @return PmsMenuAdd .
	 */
	@Permission("pms:menu:add")
	public String pmsMenuAdd() {
		Long pid = getLong("pid");
		if (null != pid) {
			PmsMenu parentMenu = pmsMenuBiz.getById(pid);
			this.putData("parentMenu", parentMenu);
		}
		return "pmsMenuAdd";
	}

	/**
	 * 保存新增菜单.
	 * @return operateSuccess or operateError .
	 */
	@Permission("pms:menu:add")
	public String pmsMenuSave() {
		try {
			String name = getString("name");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isLeaf", NodeTypeEnum.LEAF.getValue());
			map.put("name", name);
			List<PmsMenu> list = pmsMenuBiz.getMenuByNameAndIsLeaf(map);
			if (list.size() > 0) {
				return operateError("同级菜单名称不能重复");
			}
			PmsMenu pmsMenu = new PmsMenu();
			pmsMenu.setName(name);
			pmsMenu.setNumber(getString("number"));
			pmsMenu.setUrl(getString("url"));
			pmsMenu.setTargetName(getString("targetName"));
			if(getLong("parentId") == null){
				pmsMenu.setParentId(0L);
			}else{
				pmsMenu.setParentId(getLong("parentId"));
			}
			pmsMenuBiz.createMenu(pmsMenu);
			log.info("==== info ==== 添加菜单【"+pmsMenu.getName()+"】成功");
		} catch (Exception e) {
			log.error("==== error ==== 添加菜单出错", e);
			return operateError("添加菜单出错");
		}
		return operateSuccess();
	}

	/**
	 * 进入菜单修改页面.
	 * @return
	 */
	@Permission("pms:menu:view")
	public String pmsMenuEdit() {
		Long id = getLong("id");
		if (null != id) {
			PmsMenu currentMenu = pmsMenuBiz.getById(id);
			PmsMenu parentMenu = pmsMenuBiz.getById(currentMenu.getParentId());
			this.putData("currentMenu", currentMenu);
			this.putData("parentMenu", parentMenu);
		}
		return "pmsMenuEdit";
	}

	/**
	 * 保存要修改的菜单.
	 * @return
	 */
	@Permission("pms:menu:edit")
	public String pmsMenuUpdate() {
		try {
			Long id = getLong("menuId");
			PmsMenu pmsMenu = pmsMenuBiz.getById(id);
			pmsMenu.setName(getString("name"));
			pmsMenu.setNumber(getString("number"));
			pmsMenu.setUrl(getString("url"));
			pmsMenu.setTargetName(getString("targetName"));
			pmsMenuBiz.update(pmsMenu);
			log.info("==== info ==== 修改菜单【"+pmsMenu.getName()+"】成功");
			return operateSuccess();
		} catch (Exception e) {
			log.error("==== error ==== 修改菜单出错", e);
			return operateError("保存菜单出错");
		}

	}

	/**
	 * 删除菜单.
	 * @return
	 */
	@Permission("pms:menu:delete")
	public String pmsMenuDel() {
		try {
			Long menuId = getLong("id");
			if (menuId == null || menuId.longValue() == 0) {
				return operateError("无法获取要删除的数据");
			}
			PmsMenu menu = pmsMenuBiz.getById(menuId);
			if (menu == null) {
				return operateError("无法获取要删除的数据");
			}
			Long parentId = menu.getParentId(); // 获取父菜单ID

			// 先判断此菜单下是否有子菜单
			List<PmsMenu> childMenuList = pmsMenuBiz.listByParentId(menuId);
			if (childMenuList != null && !childMenuList.isEmpty()) {
				return operateError("此菜单下关联有【" + childMenuList.size() + "】个子菜单，不能支接删除!");
			}

			// 判断是否有权限关联到此菜单上，如有则不能删除
			List<PmsAction> actionList = pmsActionBiz.listByMenuId(menuId);
			if (actionList != null && !actionList.isEmpty()) {
				return operateError("此菜单下关联有【" + actionList.size() + "】个权限，要先解除关联后才能删除此菜单!");
			}

			// 删除掉菜单
			pmsMenuBiz.delete(menuId);

			// 删除菜单后，要判断其父菜单是否还有子菜单，如果没有子菜单了就要装其父菜单设为叶子节点
			List<PmsMenu> childList = pmsMenuBiz.listByParentId(parentId);
			if (childList == null || childList.isEmpty()) {
				// 此时要将父菜单设为叶子
				PmsMenu parent = pmsMenuBiz.getById(parentId);
				parent.setIsLeaf(NodeTypeEnum.LEAF.getValue());
				pmsMenuBiz.update(parent);
			}
			log.info("==== info ==== 删除菜单【"+menu.getName()+"】成功");
			return operateSuccess();
		} catch (Exception e) {
			log.error("==== error ==== 删除菜单出错", e);
			return operateError("删除菜单出错");
		}
	}

}
