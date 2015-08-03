package com.techstack.pms.dao.facade;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.techstack.pms.dao.dto.PmsRoleMenuDTO;
import com.techstack.pms.dao.facade.utils.SpringTransactionalJunit4Test;

@TransactionConfiguration(transactionManager = "transactionManager")
public class PmsRoleMenuDaoFacadeTest extends SpringTransactionalJunit4Test{
	
	@Autowired
	private PmsRoleMenuDaoFacade pmsRoleMenuDaoFacade;
	@Autowired
	private PmsMenuDaoFacade pmsMenuDaoFacade;

	@Test
	@Rollback(true)
	public void testSaveOrUpdate() {
		PmsRoleMenuDTO pmsRoleMenuDTO = new PmsRoleMenuDTO();
		pmsRoleMenuDTO.setRoleId(1L);
		pmsRoleMenuDTO.setMenuId(1L);
		pmsRoleMenuDaoFacade.saveOrUpdate(pmsRoleMenuDTO);
		List<PmsRoleMenuDTO> pmsRoleMenuDTOList = pmsMenuDaoFacade.listRoleMenuByRoleId(1L);
		Assert.assertEquals(2, pmsRoleMenuDTOList.size());
	}

	@Test
	@Rollback(true)
	public void testGetById() {
		//fail("Not yet implemented");
	}

	@Test
	@Rollback(true)
	public void testDeleteById() {
		//fail("Not yet implemented");
	}

	@Test
	@Rollback(true)
	public void testDeleteByModel() {
		PmsRoleMenuDTO pmsRoleMenuDTO = new PmsRoleMenuDTO();
		pmsRoleMenuDTO.setRoleId(1L);
		pmsRoleMenuDTO.setMenuId(2L);
		pmsRoleMenuDaoFacade.deleteByModel(pmsRoleMenuDTO);
		List<PmsRoleMenuDTO> pmsRoleMenuDTOList = pmsMenuDaoFacade.listRoleMenuByRoleId(1L);
		Assert.assertEquals(0, pmsRoleMenuDTOList.size());
	}

}
