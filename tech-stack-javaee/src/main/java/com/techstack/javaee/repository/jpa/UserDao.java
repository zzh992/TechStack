package com.techstack.javaee.repository.jpa;

import com.techstack.javaee.entity.jpa.User;

public interface UserDao extends BaseRepository<User, Long>{

	User findByName(String name);

	User findByLoginName(String loginName);
}
