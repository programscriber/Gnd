package com.indutech.gnd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.indutech.gnd.bo.UserBO;
import com.indutech.gnd.dao.GNDAppExpection;
import com.indutech.gnd.dao.LoginDAOImpl;
import com.indutech.gnd.dto.User;

@Component("loginService")
public class LoginServiceImpl implements LoginService{

	@Autowired
	private LoginDAOImpl loginDAO;
	
	public LoginDAOImpl getLoginDAO() {
		return loginDAO;
	}

	public void setLoginDAO(LoginDAOImpl loginDAO) {
		this.loginDAO = loginDAO;
	}

	@Override
	@Transactional
	public boolean validateLoginUser(UserBO userBO) throws GNDAppExpection {

		return getLoginDAO().validateLoginUser(buildUser(userBO));
	}

	private User buildUser(UserBO userBO) {
		User user = new User();
		user.setPassword(userBO.getPassword());
		user.setUserName(userBO.getUserName());
		return user;
	}

	
	
}
