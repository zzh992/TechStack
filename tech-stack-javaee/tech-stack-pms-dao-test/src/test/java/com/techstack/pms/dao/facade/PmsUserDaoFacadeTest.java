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

import com.techstack.pms.dao.dto.PmsRoleUserDTO;
import com.techstack.pms.dao.dto.PmsUserDTO;
import com.techstack.pms.dao.facade.utils.SpringTransactionalJunit4Test;

@TransactionConfiguration(transactionManager = "transactionManager")
public class PmsUserDaoFacadeTest extends SpringTransactionalJunit4Test{
	
	@Autowired
	private PmsUserDaoFacade pmsUserDaoFacade;

	@Test
	@Rollback(true)
	public void testFindUserByLoginName() {
		PmsUserDTO pmsUserDTO = pmsUserDaoFacade.findUserByLoginName("guoj");
		Assert.assertEquals("guoj", pmsUserDTO.getRemark());
		Assert.assertEquals(Integer.valueOf(0), pmsUserDTO.getType());
	}

	@Test
	@Rollback(true)
	public void testListUserByRoleId() {
		List<PmsUserDTO> pmsUserDTOList = pmsUserDaoFacade.listUserByRoleId(1L);
		Assert.assertEquals(1, pmsUserDTOList.size());
		Assert.assertEquals("admin", pmsUserDTOList.get(0).getRemark());
		Assert.assertEquals(Integer.valueOf(1), pmsUserDTOList.get(0).getType());
	}

	@Test
	@Rollback(true)
	public void testListRoleUserByUserId() {
		List<PmsRoleUserDTO> pmsRoleUserDTOList = pmsUserDaoFacade.listRoleUserByUserId(1L);
		Assert.assertEquals(1, pmsRoleUserDTOList.size());
		Assert.assertEquals(Long.valueOf(1), pmsRoleUserDTOList.get(0).getRoleId());
		Assert.assertEquals(Long.valueOf(1), pmsRoleUserDTOList.get(0).getUserId());
	}

	@Test
	@Rollback(true)
	public void testListRoleUserByRoleId() {
		List<PmsRoleUserDTO> pmsRoleUserDTOList = pmsUserDaoFacade.listRoleUserByRoleId(2L);
		Assert.assertEquals(1, pmsRoleUserDTOList.size());
		Assert.assertEquals(Long.valueOf(2), pmsRoleUserDTOList.get(0).getRoleId());
		Assert.assertEquals(Long.valueOf(2), pmsRoleUserDTOList.get(0).getUserId());
	}

	@Test
	@Rollback(true)
	public void testListPage() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("loginName", "admin");
		Page<PmsUserDTO> pageResult = pmsUserDaoFacade.listPage(1, 2, paramMap);
		Assert.assertEquals(1, pageResult.getContent().size());
	}

	@Test
	@Rollback(true)
	public void testSaveOrUpdate() {
		PmsUserDTO pmsUserDTO = new PmsUserDTO();
		pmsUserDTO.setLoginName("userTest");
		pmsUserDTO.setLoginPwd("userTest");
		pmsUserDTO.setRemark("userTest");
		pmsUserDTO.setType(1);
		pmsUserDTO = pmsUserDaoFacade.saveOrUpdate(pmsUserDTO);
		Assert.assertNotNull(pmsUserDTO.getId());
		
		PmsUserDTO pmsUser1 = pmsUserDaoFacade.getById(1L);
		pmsUser1.setRemark("userTest");
		pmsUser1 = pmsUserDaoFacade.saveOrUpdate(pmsUser1);
		PmsUserDTO pmsUserDTO2 = pmsUserDaoFacade.getById(1L);
		Assert.assertEquals(pmsUserDTO2.getRemark(), pmsUser1.getRemark());
	}

	@Test
	@Rollback(true)
	public void testGetById() {
		PmsUserDTO pmsUserDTO = pmsUserDaoFacade.getById(2L);
		Assert.assertEquals("guoj", pmsUserDTO.getRemark());
		Assert.assertEquals(Integer.valueOf(0), pmsUserDTO.getType());
	}

	@Test
	@Rollback(true)
	public void testDeleteById() {
		pmsUserDaoFacade.deleteById(1L);
		PmsUserDTO pmsUserDTO = pmsUserDaoFacade.getById(1L);
		Assert.assertNull(pmsUserDTO);
	}

	@Test
	@Rollback(true)
	public void testDeleteByModel() {
		PmsUserDTO pmsUserDTO = new PmsUserDTO();
		pmsUserDTO.setLoginName("admin");
		pmsUserDaoFacade.deleteByModel(pmsUserDTO);
		PmsUserDTO pmsUserDTO1 = pmsUserDaoFacade.getById(1L);
		Assert.assertNull(pmsUserDTO1);
	}

}
