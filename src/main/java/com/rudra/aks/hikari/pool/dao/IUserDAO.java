package com.rudra.aks.hikari.pool.dao;

import com.rudra.aks.hikari.pool.model.UserDTO;

public interface IUserDAO {

	UserDTO	getUser(int userid);

	void getPoolProperties();
	
	int	getNoOfUsers();
}
