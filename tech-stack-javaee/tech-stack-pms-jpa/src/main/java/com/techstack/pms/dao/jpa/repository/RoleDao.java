package com.techstack.pms.dao.jpa.repository;

import com.techstack.component.jpa.BaseRepository;
import com.techstack.pms.dao.jpa.entity.Role;

public interface RoleDao extends BaseRepository<Role, Long> {

	public Role findByRoleName(String roleName);
	
	public Role findByRoleNameAndIdNot(String roleName, Long id);
}
