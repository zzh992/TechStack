package com.techstack.pms.dao.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.techstack.pms.dao.dto.PmsRoleDTO;
import com.techstack.pms.dao.dto.PmsRoleUserDTO;
import com.techstack.pms.dao.facade.utils.SpringTransactionalJunit4Test;

@TransactionConfiguration(transactionManager = "transactionManager")
public class PmsRoleDaoFacadeTest extends SpringTransactionalJunit4Test{
	
	@Autowired
	private PmsRoleDaoFacade pmsRoleDaoFacade;

	@Test
	@Rollback(true)
	public void testListAllRole() {
		List<PmsRoleDTO> pmsRoleDTOList = pmsRoleDaoFacade.listAllRole();
		Assert.assertEquals(2, pmsRoleDTOList.size());
	}

	@Test
	@Rollback(true)
	public void testGetRoleByRoleName() {
		PmsRoleDTO pmsRoleDTO = pmsRoleDaoFacade.getRoleByRoleName("admin");
		Assert.assertEquals("admin", pmsRoleDTO.getRemark());
		Assert.assertEquals(Integer.valueOf(0), pmsRoleDTO.getRoleType());
	}

	@Test
	@Rollback(true)
	public void testFindRoleByRoleNameNotEqId() {
		PmsRoleDTO pmsRoleDTO = pmsRoleDaoFacade.findRoleByRoleNameNotEqId("superAdmin", 2L);
		Assert.assertEquals("superAdmin", pmsRoleDTO.getRemark());
		Assert.assertEquals(Integer.valueOf(1), pmsRoleDTO.getRoleType());
	}

	@Test
	@Rollback(true)
	public void testListRoleByActionId() {
		List<PmsRoleDTO> pmsRoleDTOList = pmsRoleDaoFacade.listRoleByActionId(2L);
		Assert.assertEquals(1, pmsRoleDTOList.size());
		Assert.assertEquals("superAdmin", pmsRoleDTOList.get(0).getRemark());
		Assert.assertEquals(Integer.valueOf(1), pmsRoleDTOList.get(0).getRoleType());
	}

	@Test
	@Rollback(true)
	public void testListRoleUserByUserId() {
		List<PmsRoleUserDTO> pmsRoleUserDTOList = pmsRoleDaoFacade.listRoleUserByUserId(2L);
		Assert.assertEquals(1, pmsRoleUserDTOList.size());
		Assert.assertEquals(Long.valueOf(2), pmsRoleUserDTOList.get(0).getRoleId());
	}

	@Test
	@Rollback(true)
	public void testListPage() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("roleName", "superAdmin");
		Page<PmsRoleDTO> pageResult = pmsRoleDaoFacade.listPage(1, 2, paramMap);
		Assert.assertEquals(1, pageResult.getContent().size());
	}

	@Test
	@Rollback(true)
	public void testSaveOrUpdate() {
		PmsRoleDTO pmsRoleDTO = new PmsRoleDTO();
		pmsRoleDTO.setRemark("roleTest");
		pmsRoleDTO.setRoleName("roleTest");
		pmsRoleDTO.setRoleType(1);
		pmsRoleDTO = pmsRoleDaoFacade.saveOrUpdate(pmsRoleDTO);
		Assert.assertNotNull(pmsRoleDTO.getId());
		
		PmsRoleDTO pmsRoleDTO1 = pmsRoleDaoFacade.getById(1L);
		pmsRoleDTO1.setRemark("roleTest");
		pmsRoleDTO1 = pmsRoleDaoFacade.saveOrUpdate(pmsRoleDTO1);
		PmsRoleDTO pmsRoleDTO2 = pmsRoleDaoFacade.getById(1L);
		Assert.assertEquals(pmsRoleDTO2.getRemark(), pmsRoleDTO1.getRemark());
	}

	@Test
	@Rollback(true)
	public void testGetById() {
		PmsRoleDTO pmsRoleDTO = pmsRoleDaoFacade.getById(2L);
		Assert.assertEquals("admin", pmsRoleDTO.getRemark());
		Assert.assertEquals(Integer.valueOf(0), pmsRoleDTO.getRoleType());
	}

	@Test
	@Rollback(true)
	public void testDeleteById() {
		pmsRoleDaoFacade.deleteById(1L);
		PmsRoleDTO pmsRoleDTO = pmsRoleDaoFacade.getById(1L);
		Assert.assertNull(pmsRoleDTO);
	}

	@Test
	@Rollback(true)
	public void testDeleteByModel() {
		PmsRoleDTO pmsRoleDTO = new PmsRoleDTO();
		pmsRoleDTO.setRemark("superAdmin");
		pmsRoleDTO.setRoleName("superAdmin");
		pmsRoleDaoFacade.deleteByModel(pmsRoleDTO);
		PmsRoleDTO pmsRoleDTO1 = pmsRoleDaoFacade.getById(1L);
		Assert.assertNull(pmsRoleDTO1);
	}

}
