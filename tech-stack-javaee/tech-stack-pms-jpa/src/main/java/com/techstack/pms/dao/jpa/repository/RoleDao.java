package com.techstack.pms.dao.jpa.repository;

import java.util.List;

import com.techstack.component.jpa.BaseRepository;
import com.techstack.pms.dao.jpa.entity.Role;

public interface RoleDao extends BaseRepository<Role, Long> {

	public Role findByRoleName(String roleName);
	
	public Role findByRoleNameAndIdNot(String roleName, Long id);
	
	public List<Role> findByIdIn(List<Long> idList);
}
