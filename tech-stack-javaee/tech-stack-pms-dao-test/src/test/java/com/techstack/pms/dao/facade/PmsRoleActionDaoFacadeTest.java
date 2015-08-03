package com.techstack.pms.dao.facade;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.techstack.pms.dao.dto.PmsRoleActionDTO;
import com.techstack.pms.dao.facade.utils.SpringTransactionalJunit4Test;

@TransactionConfiguration(transactionManager = "transactionManager")
public class PmsRoleActionDaoFacadeTest extends SpringTransactionalJunit4Test{
	
	@Autowired
	private PmsRoleActionDaoFacade pmsRoleActionDaoFacade;
	@Autowired
	private PmsActionDaoFacade pmsActionDaoFacade;

	@Test
	@Rollback(true)
	public void testSaveOrUpdate() {
		PmsRoleActionDTO pmsRoleActionDTO = new PmsRoleActionDTO();
		pmsRoleActionDTO.setRoleId(1L);
		pmsRoleActionDTO.setActionId(1L);
		pmsRoleActionDaoFacade.saveOrUpdate(pmsRoleActionDTO);
		List<PmsRoleActionDTO> pmsRoleActionDTOList = pmsActionDaoFacade.listRoleActionByRoleId(1L);
		Assert.assertEquals(2, pmsRoleActionDTOList.size());
	}

	@Test
	@Rollback(true)
	public void testGetById() {
		//fail("Have not this method");
	}

	@Test
	@Rollback(true)
	public void testDeleteById() {
		//fail("Have not this method");
	}

	@Test
	@Rollback(true)
	public void testDeleteByModel() {
		PmsRoleActionDTO pmsRoleActionDTO = new PmsRoleActionDTO();
		pmsRoleActionDTO.setRoleId(1L);
		pmsRoleActionDTO.setActionId(2L);
		pmsRoleActionDaoFacade.deleteByModel(pmsRoleActionDTO);
		List<PmsRoleActionDTO> pmsRoleActionDTOList = pmsActionDaoFacade.listRoleActionByRoleId(1L);
		Assert.assertEquals(0, pmsRoleActionDTOList.size());
	}

}
