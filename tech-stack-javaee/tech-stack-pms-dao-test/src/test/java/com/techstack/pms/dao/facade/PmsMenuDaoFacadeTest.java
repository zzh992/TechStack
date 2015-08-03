package com.techstack.pms.dao.facade;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.techstack.pms.dao.dto.PmsActionDTO;
import com.techstack.pms.dao.dto.PmsMenuDTO;
import com.techstack.pms.dao.dto.PmsRoleMenuDTO;
import com.techstack.pms.dao.facade.utils.SpringTransactionalJunit4Test;

@TransactionConfiguration(transactionManager = "transactionManager")
public class PmsMenuDaoFacadeTest extends SpringTransactionalJunit4Test{

	@Autowired
	private PmsMenuDaoFacade pmsMenuDaoFacade;
	
	@Test
	@Rollback(true)
	public void testListMenuByParent() {
		List<PmsMenuDTO> pmsMenuDTOList = pmsMenuDaoFacade.listMenuByParent(1L);
		Assert.assertEquals(1, pmsMenuDTOList.size());
		Assert.assertEquals("child_menu", pmsMenuDTOList.get(0).getName());
	}

	@Test
	@Rollback(true)
	public void testListMenuByRoleIds() {
		List<Long> roleIds = new ArrayList<Long>();
		roleIds.add(2L);
		List<PmsMenuDTO> pmsMenuDTOList = pmsMenuDaoFacade.listMenuByRoleIds(roleIds);
		Assert.assertEquals(1, pmsMenuDTOList.size());
		Assert.assertEquals("parent_menu", pmsMenuDTOList.get(0).getName());
	}

	@Test
	@Rollback(true)
	public void testListRoleMenuByRoleId() {
		List<PmsRoleMenuDTO> pmsRoleMenuDTOList = pmsMenuDaoFacade.listRoleMenuByRoleId(1L);
		Assert.assertEquals(1, pmsRoleMenuDTOList.size());
		Assert.assertEquals(Long.valueOf(2), pmsRoleMenuDTOList.get(0).getMenuId());
	}

	@Test
	@Rollback(true)
	public void testListAllActionByMenuId() {
		List<PmsActionDTO> pmsActionDTOList = pmsMenuDaoFacade.listAllActionByMenuId(2L);
		Assert.assertEquals(2, pmsActionDTOList.size());
	}

	@Test
	@Rollback(true)
	public void testListMenuByParentId() {
		List<PmsMenuDTO> pmsMenuDTOList = pmsMenuDaoFacade.listMenuByParentId(1L);
		Assert.assertEquals(1, pmsMenuDTOList.size());
		Assert.assertEquals("child_menu", pmsMenuDTOList.get(0).getName());
	}

	@Test
	@Rollback(true)
	public void testListMenuBy() {
		List<PmsMenuDTO> pmsMenuDTOList = pmsMenuDaoFacade.listMenuBy(1, "child_menu", 1L);
		Assert.assertEquals(1, pmsMenuDTOList.size());
		Assert.assertEquals("pmsMenu_pmsMenuList.action", pmsMenuDTOList.get(0).getUrl());
	}

	@Test
	@Rollback(true)
	public void testSaveOrUpdate() {
		PmsMenuDTO pmsMenuDTO = new PmsMenuDTO();
		pmsMenuDTO.setIsLeaf(1);
		pmsMenuDTO.setLevel(1);
		pmsMenuDTO.setName("menutest");
		pmsMenuDTO.setNumber("00101");
		pmsMenuDTO.setParentId(0L);
		pmsMenuDTO.setTargetName("targettest");
		pmsMenuDTO.setUrl("menu_get.action");
		pmsMenuDTO = pmsMenuDaoFacade.saveOrUpdate(pmsMenuDTO);
		Assert.assertNotNull(pmsMenuDTO.getId());
		
		PmsMenuDTO pmsMenuDTO1 = pmsMenuDaoFacade.getById(1L);
		pmsMenuDTO1.setName("menuTest");
		pmsMenuDTO1 = pmsMenuDaoFacade.saveOrUpdate(pmsMenuDTO1);
		PmsMenuDTO pmsMenuDTO2 = pmsMenuDaoFacade.getById(1L);
		Assert.assertEquals(pmsMenuDTO2.getName(), pmsMenuDTO1.getName());
	}

	@Test
	@Rollback(true)
	public void testGetById() {
		PmsMenuDTO pmsMenuDTO1 = pmsMenuDaoFacade.getById(1L);
		Assert.assertEquals(Long.valueOf(1), pmsMenuDTO1.getId());
		Assert.assertEquals("##", pmsMenuDTO1.getUrl());
		Assert.assertEquals(Integer.valueOf(1), pmsMenuDTO1.getVersion());
	}

	@Test
	@Rollback(true)
	public void testDeleteById() {
		pmsMenuDaoFacade.deleteById(2L);
		PmsMenuDTO pmsMenuDTO1 = pmsMenuDaoFacade.getById(2L);
		Assert.assertNull(pmsMenuDTO1);
	}

	@Test
	@Rollback(true)
	public void testDeleteByModel() {
		PmsMenuDTO pmsMenuDTO = new PmsMenuDTO();
		pmsMenuDTO.setUrl("pmsMenu_pmsMenuList.action");
		pmsMenuDTO.setName("child_menu");
		pmsMenuDaoFacade.deleteByModel(pmsMenuDTO);
		PmsMenuDTO pmsMenuDTOResult = pmsMenuDaoFacade.getById(2L);
		Assert.assertNull(pmsMenuDTOResult);
	}

}
