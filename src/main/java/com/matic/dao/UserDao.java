package com.matic.dao;

import java.util.List;

import com.matic.vo.User;

public interface UserDao {
	
	public List<User> getAllUser() throws Exception;

	public User getOneById(Integer id) throws Exception;
}
