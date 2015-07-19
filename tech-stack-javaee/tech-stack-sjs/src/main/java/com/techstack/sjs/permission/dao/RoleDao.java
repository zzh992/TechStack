package com.techstack.sjs.permission.dao;

import com.techstack.sjs.base.repository.BaseRepository;
import com.techstack.sjs.permission.entity.Role;

public interface RoleDao extends BaseRepository<Role, Long> {

	public Role findByRoleName(String roleName);
	
	public Role findByRoleNameAndIdNot(String roleName, Long id);
}
