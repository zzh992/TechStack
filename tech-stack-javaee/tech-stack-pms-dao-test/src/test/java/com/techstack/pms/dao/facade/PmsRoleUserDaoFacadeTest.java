package com.techstack.pms.dao.facade;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.techstack.pms.dao.dto.PmsRoleUserDTO;
import com.techstack.pms.dao.facade.utils.SpringTransactionalJunit4Test;

@TransactionConfiguration(transactionManager = "transactionManager")
public class PmsRoleUserDaoFacadeTest extends SpringTransactionalJunit4Test{
	
	@Autowired
	private PmsRoleUserDaoFacade pmsRoleUserDaoFacade;
	@Autowired
	private PmsUserDaoFacade pmsUserDaoFacade;

	@Test
	@Rollback(true)
	public void testSaveOrUpdate() {
		PmsRoleUserDTO pmsRoleUserDTO = new PmsRoleUserDTO();
		pmsRoleUserDTO.setRoleId(1L);
		pmsRoleUserDTO.setUserId(2L);
		pmsRoleUserDaoFacade.saveOrUpdate(pmsRoleUserDTO);
		List<PmsRoleUserDTO> pmsRoleUserDTOList = pmsUserDaoFacade.listRoleUserByRoleId(1L);
		Assert.assertEquals(2, pmsRoleUserDTOList.size());
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
		PmsRoleUserDTO pmsRoleUserDTO = new PmsRoleUserDTO();
		pmsRoleUserDTO.setRoleId(1L);
		pmsRoleUserDTO.setUserId(1L);
		pmsRoleUserDaoFacade.deleteByModel(pmsRoleUserDTO);
		List<PmsRoleUserDTO> pmsRoleUserDTOList = pmsUserDaoFacade.listRoleUserByRoleId(1L);
		Assert.assertEquals(0, pmsRoleUserDTOList.size());
		
	}

}
