package com.indutech.gnd.dao;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.indutech.gnd.controller.MasterDBCont;
import com.indutech.gnd.dto.Bank;
import com.indutech.gnd.dto.Branch;
import com.indutech.gnd.dto.District;
import com.indutech.gnd.dto.State;
import com.indutech.gnd.enumTypes.Status;

@Repository("branchDAO")
public class BranchDAOImpl implements BranchDAO {
	
	Logger logger = Logger.getLogger(BranchDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;	

	public final SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public final void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	Status stat = Status.valueOf("ACTIVE");
	String status = stat.getStatus();
	
	@Override
	public List<Branch> getBranch(String shortCode, Long bankId) {
		@SuppressWarnings("unchecked")
		List<Branch> list = getSessionFactory().getCurrentSession().createCriteria(Branch.class).add(Restrictions.eq("shortCode", shortCode))
																   .add(Restrictions.eq("bankId", bankId)).list();
		return list;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Branch> getBankBranch(Long bankId){
		logger.info(bankId+"   ::::::::::::::::::::::::");
	List<Branch> listBranch = (List<Branch>) getSessionFactory().getCurrentSession().createCriteria(Branch.class).add(Restrictions.eq("bankId", bankId)).list();
		
		logger.info(":::::::::::::::::::::::::::::::::::       "+listBranch.size());
		return listBranch;
	}
		
	@Override
	public void saveBranch(Branch branch) {				
		
		getSessionFactory().getCurrentSession().saveOrUpdate(branch);
	}
	
	@Override
	public Long saveOrUpdateDistrict(District district) {
		
		long did = 0;
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(District.class);
		criteria.add(Restrictions.eq("districtCode", district.getDistrictCode()));
		criteria.add(Restrictions.eq("stateId", district.getStateId()));
		@SuppressWarnings("unchecked")
		List<District> list = criteria.list();
		if(list.size() <= 0) {
			did = (Long) getSessionFactory().getCurrentSession().save(district);
		}
		else {
			@SuppressWarnings("rawtypes")
			Iterator iterator = list.iterator();
			while(iterator.hasNext()) {
				District  district1 = (District) iterator.next();
				did = district1.getDistrictCode();
			}
		}
		return did;
	}
	
	@Override
	public Long saveOrUpdateState(State state) {
		
		long sid = 0;
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(State.class);
		criteria.add(Restrictions.eq("stateCode", state.getStateCode()));
//		criteria.add(Restrictions.eq("stateName", state.getStateName()));
		@SuppressWarnings("unchecked")
		List<State> list = criteria.list();
		if(list.size() <= 0) {
			if(!(state.getStateName().isEmpty())) {
				Status stat = Status.valueOf("ACTIVE");
				String status = stat.getStatus();
				state.setStatus(Long.parseLong(status));
			}
			else
			{
				Status stat = Status.valueOf("PENDING_FOR_APPROVAL");
				String status = stat.getStatus();
				state.setStatus(Long.parseLong(status));
			}
			sid = (Long) getSessionFactory().getCurrentSession().save(state);
		}
		else {
			@SuppressWarnings("rawtypes")
			Iterator iterator = list.iterator();
			while(iterator.hasNext()) {
				State state1 = (State) iterator.next();
				sid = state1.getStateCode();
			}
		}
		return sid;
	}
	
	@Override
	public Long saveOrUpdateBank(Bank bank) {
		
		long bid = 0;
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Bank.class);
		criteria.add(Restrictions.eq("bankId", bank.getBankId()));
//		criteria.add(Restrictions.eq("shortCode", bank.getShortCode()));
		@SuppressWarnings("unchecked")
		List<Bank> list = criteria.list();
		if(list.size() == 0) {			
			if(bank.getBankName() == null) {
				Status stat = Status.valueOf("PENDING_FOR_APPROVAL");
				String status = stat.getStatus();
				bank.setStatus(Long.parseLong(status));
			}
			else {
				Status stat = Status.valueOf("ACTIVE");
				String status = stat.getStatus();
				bank.setStatus(Long.parseLong(status));
			}
			bid = (Long) getSessionFactory().getCurrentSession().save(bank);
		}
		
		return bid;
	}

	@Override
	public List<Branch> searchShortCode(String branchCode) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<Branch> list = getSessionFactory().getCurrentSession().createCriteria(Branch.class).add(Restrictions.eq("shortCode", branchCode)).list();
		return list;
	}

	@Override
	public List<Branch> getBranchList(String branchCode) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<Branch> list= (List<Branch>) getSessionFactory().getCurrentSession().createCriteria(Branch.class).
							add(Restrictions.eq("shortCode", branchCode))
						.add(Restrictions.eq("status", Long.parseLong(status))).list();
		return list;
	}
	@Transactional
	public List<Branch> branchNames(Long branchNameCode){
		@SuppressWarnings("unchecked")
		List<Branch> list = (List<Branch>)getSessionFactory().getCurrentSession().createCriteria(Branch.class).add(Restrictions.eq("id", branchNameCode)).list();
		
		return list;
		
	}
}
