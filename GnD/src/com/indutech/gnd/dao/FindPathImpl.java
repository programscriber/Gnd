package com.indutech.gnd.dao;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.indutech.gnd.dto.Path;

public class FindPathImpl implements FindPath {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public String getPath(String type) {
		String path = (String) getSessionFactory().getCurrentSession().createCriteria(Path.class).add(Restrictions.eq("type", type)).setProjection(Projections.property("path")).list().get(0);
		//String path = (String) getSessionFactory().getCurrentSession().createQuery("select p.path from Path p where p.type ='"+type+"'").list().get(0);		
		//String path = (String) getSessionFactory().getCurrentSession().createSQLQuery("select path from folder_paths where path_type='"+type+"'").list().get(0);
		return path;
	}

	public final SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public final void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
}
