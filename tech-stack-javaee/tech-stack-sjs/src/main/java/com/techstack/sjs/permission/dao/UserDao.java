package com.techstack.sjs.permission.dao;

import com.techstack.sjs.base.repository.BaseRepository;
import com.techstack.sjs.permission.entity.User;

public interface UserDao extends BaseRepository<User, Long> {

	public User findByLoginName(String loginName);
}
