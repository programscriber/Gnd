package com.indutech.gnd.dao;

import java.util.List;

import javax.security.auth.login.LoginException;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.indutech.gnd.dto.User;

@Repository("loginDAO")
public class LoginDAOImpl implements LoginDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public boolean validateLoginUser(User user) throws GNDAppExpection {
		List users = null;
		try {
			users = getSessionFactory()
					.getCurrentSession()
					.createQuery(
							"from User u where u.userName ='"
									+ user.getUserName()
									+ "' and u.password ='"
									+ user.getPassword()+"'").list();
		} catch (HibernateException e) {
		}
		return users.size()>0;
	}


	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
