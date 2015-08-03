package com.techstack.pms.dao.facade;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.techstack.pms.dao.dto.PmsActionDTO;
import com.techstack.pms.dao.dto.PmsRoleActionDTO;
import com.techstack.pms.dao.facade.utils.SpringTransactionalJunit4Test;

@TransactionConfiguration(transactionManager = "transactionManager")
public class PmsActionDaoFacadeTest extends SpringTransactionalJunit4Test{

	@Autowired
	private PmsActionDaoFacade pmsActionDaoFacade;
	
	@Autowired
	private PmsMenuDaoFacade pmsMenuDaoFacade;
	
	@Test
	@Rollback(true)
	public void testFindActionsByIds() {
		/*PmsActionDTO pmsActionDTO = new PmsActionDTO();
		pmsActionDTO.setAction("pms:test:insert");
		pmsActionDTO.setActionName("test");
		pmsActionDTO.setMenuId(1L);
		pmsActionDTO.setMenuName("menutest");
		pmsActionDTO.setRemark("remarktest");
		pmsActionDTO = pmsActionDaoFacade.saveOrUpdate(pmsActionDTO);
		
		PmsActionDTO pmsActionDTO1 = new PmsActionDTO();
		pmsActionDTO1.setAction("pms:test1:insert");
		pmsActionDTO1.setActionName("test");
		pmsActionDTO1.setMenuId(2L);
		pmsActionDTO1.setMenuName("menutest1");
		pmsActionDTO1.setRemark("remarktest1");
		pmsActionDTO1 = pmsActionDaoFacade.saveOrUpdate(pmsActionDTO1);*/
		
		List<Long> ids = new ArrayList<Long>();
		ids.add(1L);
		ids.add(2L);
		List<PmsActionDTO> pmsActionDTOList = pmsActionDaoFacade.findActionsByIds(ids);
		for(PmsActionDTO pmsActionDTOResult : pmsActionDTOList){
			if(pmsActionDTOResult.getId() == 1L){
				Assert.assertEquals(Long.valueOf(1), pmsActionDTOResult.getId());
				Assert.assertEquals("menu_list", pmsActionDTOResult.getActionName());
				Assert.assertEquals(Integer.valueOf(0), pmsActionDTOResult.getVersion());
				Assert.assertEquals(Long.valueOf(2), pmsActionDTOResult.getMenuId());
				break;
			}
			if(pmsActionDTOResult.getId() == 2L){
				Assert.assertEquals(Long.valueOf(2), pmsActionDTOResult.getId());
				Assert.assertEquals("menu_add", pmsActionDTOResult.getActionName());
				Assert.assertEquals(Integer.valueOf(0), pmsActionDTOResult.getVersion());
				Assert.assertEquals(Long.valueOf(2), pmsActionDTOResult.getMenuId());
				break;
			}
			fail("testFindActionsByIds fail");
		}
	}

	@Test
	@Rollback(true)
	public void testGetActionByAction() {
		PmsActionDTO pmsActionDTO = new PmsActionDTO();
		pmsActionDTO.setAction("pms:test:insert");
		pmsActionDTO.setActionName("test");
		pmsActionDTO.setMenuId(1L);
		pmsActionDTO.setMenuName("menutest");
		pmsActionDTO.setRemark("remarktest");
		pmsActionDTO = pmsActionDaoFacade.saveOrUpdate(pmsActionDTO);
		PmsActionDTO pmsActionDTOResult = pmsActionDaoFacade.getActionByAction(pmsActionDTO.getAction());
		Assert.assertEquals(pmsActionDTO.getId(), pmsActionDTOResult.getId());
		Assert.assertEquals(pmsActionDTO.getActionName(), pmsActionDTOResult.getActionName());
		Assert.assertEquals(pmsActionDTO.getVersion(), pmsActionDTOResult.getVersion());
	}

	@Test
	@Rollback(true)
	public void testGetActionByActionName() {
		PmsActionDTO pmsActionDTO = new PmsActionDTO();
		pmsActionDTO.setAction("pms:test:insert");
		pmsActionDTO.setActionName("test");
		pmsActionDTO.setMenuId(1L);
		pmsActionDTO.setMenuName("menutest");
		pmsActionDTO.setRemark("remarktest");
		pmsActionDTO = pmsActionDaoFacade.saveOrUpdate(pmsActionDTO);
		PmsActionDTO pmsActionDTOResult = pmsActionDaoFacade.getActionByActionName(pmsActionDTO.getActionName());
		Assert.assertEquals(pmsActionDTO.getId(), pmsActionDTOResult.getId());
		Assert.assertEquals(pmsActionDTO.getActionName(), pmsActionDTOResult.getActionName());
		Assert.assertEquals(pmsActionDTO.getVersion(), pmsActionDTOResult.getVersion());
	}

	@Test
	@Rollback(true)
	public void testGetActionByActionNameNotEqId() {
		/*PmsActionDTO pmsActionDTO = new PmsActionDTO();
		pmsActionDTO.setAction("pms:test:insert");
		pmsActionDTO.setActionName("test");
		pmsActionDTO.setMenuId(1L);
		pmsActionDTO.setMenuName("menutest");
		pmsActionDTO.setRemark("remarktest");
		pmsActionDTO = pmsActionDaoFacade.saveOrUpdate(pmsActionDTO);
		
		PmsActionDTO pmsActionDTO1 = new PmsActionDTO();
		pmsActionDTO1.setAction("pms:test1:insert");
		pmsActionDTO1.setActionName("test");
		pmsActionDTO1.setMenuId(2L);
		pmsActionDTO1.setMenuName("menutest1");
		pmsActionDTO1.setRemark("remarktest1");
		pmsActionDTO1 = pmsActionDaoFacade.saveOrUpdate(pmsActionDTO1);*/
		
		PmsActionDTO pmsActionDTOResult = pmsActionDaoFacade.getActionByActionNameNotEqId("menu_add", 1L);
		Assert.assertEquals(Long.valueOf(2), pmsActionDTOResult.getId());
		Assert.assertEquals("menu_add", pmsActionDTOResult.getRemark());
		Assert.assertEquals(Integer.valueOf(0), pmsActionDTOResult.getVersion());
	}

	@Test
	@Rollback(true)
	public void testGetActionByActionNotEqId() {
		/*PmsActionDTO pmsActionDTO = new PmsActionDTO();
		pmsActionDTO.setAction("pms:test:insert");
		pmsActionDTO.setActionName("test");
		pmsActionDTO.setMenuId(1L);
		pmsActionDTO.setMenuName("menutest");
		pmsActionDTO.setRemark("remarktest");
		pmsActionDTO = pmsActionDaoFacade.saveOrUpdate(pmsActionDTO);
		
		PmsActionDTO pmsActionDTO1 = new PmsActionDTO();
		pmsActionDTO1.setAction("pms:test:insert");
		pmsActionDTO1.setActionName("test1");
		pmsActionDTO1.setMenuId(2L);
		pmsActionDTO1.setMenuName("menutest1");
		pmsActionDTO1.setRemark("remarktest1");
		pmsActionDTO1 = pmsActionDaoFacade.saveOrUpdate(pmsActionDTO1);*/
		
		PmsActionDTO pmsActionDTOResult = pmsActionDaoFacade.getActionByActionNotEqId("pms:menu:view", 2L);
		Assert.assertEquals(Long.valueOf(1), pmsActionDTOResult.getId());
		Assert.assertEquals("menu_list", pmsActionDTOResult.getActionName());
		Assert.assertEquals(Integer.valueOf(0), pmsActionDTOResult.getVersion());
	}

	@Test
	@Rollback(true)
	public void testListActionByMenuId() {
		List<PmsActionDTO> pmsActionDTOList = pmsActionDaoFacade.listActionByMenuId(2L);
		Assert.assertEquals(2, pmsActionDTOList.size());
	}

	@Test
	@Rollback(true)
	public void testListRoleActionByRoleId() {
		List<PmsRoleActionDTO> pmsRoleActionDTOList = pmsActionDaoFacade.listRoleActionByRoleId(1L);
		Assert.assertEquals(1, pmsRoleActionDTOList.size());
	}

	@Test
	@Rollback(true)
	public void testListRoleActionByRoleIds() {
		List<Long> roleIds = new ArrayList<Long>();
		roleIds.add(2L);
		List<PmsRoleActionDTO> pmsRoleActionDTOList = pmsActionDaoFacade.listRoleActionByRoleIds(roleIds);
		Assert.assertEquals(1, pmsRoleActionDTOList.size());
	}

	@Test
	@Rollback(true)
	public void testListPage() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("actionName", "list");
		paramMap.put("action", "pms:menu:view");
		Page<PmsActionDTO> pageResult = pmsActionDaoFacade.listPage(1, 2, paramMap);
		Assert.assertEquals(1, pageResult.getContent().size());
	}

	@Test
	@Rollback(true)
	public void testSaveOrUpdate() {
		PmsActionDTO pmsActionDTO = new PmsActionDTO();
		pmsActionDTO.setAction("pms:test:insert");
		pmsActionDTO.setActionName("test");
		pmsActionDTO.setMenuId(1L);
		pmsActionDTO.setMenuName("menutest");
		pmsActionDTO.setRemark("remarktest");
		pmsActionDTO = pmsActionDaoFacade.saveOrUpdate(pmsActionDTO);
		Assert.assertNotNull(pmsActionDTO.getId());
		
		PmsActionDTO pmsActionDTO1 = pmsActionDaoFacade.getById(1L);
		pmsActionDTO1.setRemark("updateTest");
		pmsActionDTO1 = pmsActionDaoFacade.saveOrUpdate(pmsActionDTO1);
		PmsActionDTO pmsActionDTO2 = pmsActionDaoFacade.getById(1L);
		Assert.assertEquals(pmsActionDTO2.getRemark(), pmsActionDTO1.getRemark());
	}

	@Test
	@Rollback(true)
	public void testGetById() {
		/*PmsMenuDTO pmsMenuDTO = new PmsMenuDTO();
		pmsMenuDTO.setIsLeaf(1);
		pmsMenuDTO.setLevel(1);
		pmsMenuDTO.setName("menutest");
		pmsMenuDTO.setNumber("00101");
		pmsMenuDTO.setParentId(0L);
		pmsMenuDTO.setTargetName("targettest");
		pmsMenuDTO.setUrl("menu_get.action");
		pmsMenuDTO = pmsMenuDaoFacade.saveOrUpdate(pmsMenuDTO);
		PmsActionDTO pmsActionDTO = new PmsActionDTO();
		pmsActionDTO.setAction("pms:test:insert");
		pmsActionDTO.setActionName("test");
		pmsActionDTO.setMenuId(pmsMenuDTO.getId());
		pmsActionDTO.setMenuName("menutest");
		pmsActionDTO.setRemark("remarktest");
		pmsActionDTO = pmsActionDaoFacade.saveOrUpdate(pmsActionDTO);*/
		
		PmsActionDTO pmsActionDTOResult = pmsActionDaoFacade.getById(2L);
		Assert.assertEquals(Long.valueOf(2), pmsActionDTOResult.getId());
		Assert.assertEquals("menu_add", pmsActionDTOResult.getActionName());
		Assert.assertEquals(Integer.valueOf(0), pmsActionDTOResult.getVersion());
	}

	@Test
	@Rollback(true)
	public void testDeleteById() {
		pmsActionDaoFacade.deleteById(1L);
		PmsActionDTO pmsActionDTOResult = pmsActionDaoFacade.getById(1L);
		Assert.assertNull(pmsActionDTOResult);
	}

	@Test
	@Rollback(true)
	public void testDeleteByModel() {
		PmsActionDTO pmsActionDTO = new PmsActionDTO();
		pmsActionDTO.setAction("pms:menu:add");
		pmsActionDTO.setRemark("menu_add");
		pmsActionDaoFacade.deleteByModel(pmsActionDTO);
		PmsActionDTO pmsActionDTOResult = pmsActionDaoFacade.getById(2L);
		Assert.assertNull(pmsActionDTOResult);
	}

}
