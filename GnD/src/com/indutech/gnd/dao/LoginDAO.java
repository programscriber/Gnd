package com.indutech.gnd.dao;

import com.indutech.gnd.dto.User;

public interface LoginDAO {

	boolean validateLoginUser(User user) throws GNDAppExpection;
	
}
