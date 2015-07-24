package com.techstack.pms.dao.jpa.repository;

import com.techstack.component.jpa.BaseRepository;
import com.techstack.pms.dao.jpa.entity.User;

public interface UserDao extends BaseRepository<User, Long> {

	public User findByLoginName(String loginName);
}
