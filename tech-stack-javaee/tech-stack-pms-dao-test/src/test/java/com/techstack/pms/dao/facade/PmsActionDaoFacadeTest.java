package com.techstack.pms.dao.facade;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.techstack.pms.dao.dto.PmsActionDTO;
import com.techstack.pms.dao.dto.PmsMenuDTO;
import com.techstack.pms.dao.facade.utils.SpringTransactionalJunit4Test;

public class PmsActionDaoFacadeTest extends SpringTransactionalJunit4Test{

	@Autowired
	private PmsActionDaoFacade pmsActionDaoFacade;
	
	@Autowired
	private PmsMenuDaoFacade pmsMenuDaoFacade;
	
	@Test
	@Rollback(true)
	public void testFindActionsByIds() {
		PmsActionDTO pmsActionDTO = new PmsActionDTO();
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
		pmsActionDTO1 = pmsActionDaoFacade.saveOrUpdate(pmsActionDTO);
		
		List<Long> ids = new ArrayList<Long>();
		ids.add(pmsActionDTO.getId());
		ids.add(pmsActionDTO1.getId());
		List<PmsActionDTO> pmsActionDTOList = pmsActionDaoFacade.findActionsByIds(ids);
		for(PmsActionDTO pmsActionDTOResult : pmsActionDTOList){
			if(pmsActionDTOResult.getId() == pmsActionDTO.getId()){
				Assert.assertEquals(pmsActionDTO.getId(), pmsActionDTOResult.getId());
				Assert.assertEquals(pmsActionDTO.getActionName(), pmsActionDTOResult.getActionName());
				Assert.assertEquals(pmsActionDTO.getVersion(), pmsActionDTOResult.getVersion());
			}
			if(pmsActionDTOResult.getId() == pmsActionDTO1.getId()){
				Assert.assertEquals(pmsActionDTO1.getId(), pmsActionDTOResult.getId());
				Assert.assertEquals(pmsActionDTO1.getActionName(), pmsActionDTOResult.getActionName());
				Assert.assertEquals(pmsActionDTO1.getVersion(), pmsActionDTOResult.getVersion());
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
		PmsActionDTO pmsActionDTO = new PmsActionDTO();
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
		pmsActionDTO1 = pmsActionDaoFacade.saveOrUpdate(pmsActionDTO);
		
		PmsActionDTO pmsActionDTOResult = pmsActionDaoFacade.getActionByActionNameNotEqId(pmsActionDTO.getActionName(), pmsActionDTO1.getId());
		Assert.assertEquals(pmsActionDTO.getId(), pmsActionDTOResult.getId());
		Assert.assertEquals(pmsActionDTO.getActionName(), pmsActionDTOResult.getActionName());
		Assert.assertEquals(pmsActionDTO.getVersion(), pmsActionDTOResult.getVersion());
	}

	@Test
	@Rollback(true)
	public void testGetActionByActionNotEqId() {
		PmsActionDTO pmsActionDTO = new PmsActionDTO();
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
		pmsActionDTO1 = pmsActionDaoFacade.saveOrUpdate(pmsActionDTO);
		
		PmsActionDTO pmsActionDTOResult = pmsActionDaoFacade.getActionByActionNotEqId(pmsActionDTO.getAction(), pmsActionDTO1.getId());
		Assert.assertEquals(pmsActionDTO.getId(), pmsActionDTOResult.getId());
		Assert.assertEquals(pmsActionDTO.getActionName(), pmsActionDTOResult.getActionName());
		Assert.assertEquals(pmsActionDTO.getVersion(), pmsActionDTOResult.getVersion());
	}

	@Test
	@Rollback(true)
	public void testListActionByMenuId() {
		fail("Not yet implemented");
	}

	@Test
	@Rollback(true)
	public void testListRoleActionByRoleId() {
		fail("Not yet implemented");
	}

	@Test
	@Rollback(true)
	public void testListRoleActionByRoleIds() {
		fail("Not yet implemented");
	}

	@Test
	@Rollback(true)
	public void testListPage() {
		fail("Not yet implemented");
	}

	@Test
	@Rollback(true)
	public void testSaveOrUpdate() {
		fail("Not yet implemented");
	}

	@Test
	@Rollback(true)
	public void testGetById() {
		PmsMenuDTO pmsMenuDTO = new PmsMenuDTO();
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
		
		pmsActionDTO = pmsActionDaoFacade.saveOrUpdate(pmsActionDTO);
		PmsActionDTO pmsActionDTOResult = pmsActionDaoFacade.getById(pmsActionDTO.getId());
		Assert.assertEquals(pmsActionDTO.getId(), pmsActionDTOResult.getId());
		Assert.assertEquals(pmsActionDTO.getActionName(), pmsActionDTOResult.getActionName());
		Assert.assertEquals(pmsActionDTO.getVersion(), pmsActionDTOResult.getVersion());
	}

	@Test
	@Rollback(true)
	public void testDeleteById() {
		fail("Not yet implemented");
	}

	@Test
	@Rollback(true)
	public void testDeleteByModel() {
		fail("Not yet implemented");
	}

}
