package com.indutech.gnd.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jxl.common.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.indutech.gnd.dto.CreditCardDetails;
import com.indutech.gnd.dto.MasterAWB;
import com.indutech.gnd.dto.RecordEvent;
import com.indutech.gnd.enumTypes.Status;
import com.indutech.gnd.service.CardStateManagerService;
import com.indutech.gnd.service.FileStateManager;

@Repository(value="rtoDAO")
public class RTODAOImpl implements RTODAO {
	
	Logger logger = Logger.getLogger(RTODAOImpl.class);
	
	Status status = Status.valueOf("ACTIVE");
	String statusId = status.getStatus();
	
	Status blockedStatus = Status.valueOf("BLOCKED");
	String blockedStatusId = blockedStatus.getStatus();
	
	@Autowired
	private SessionFactory sessionFactory;
	
	

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}



	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getHomeBranchList(List<Long> rsnList) {
		List<Object[]> homeBranchList = null;
		try {
			homeBranchList = getSessionFactory().getCurrentSession().createQuery("select  processedBranchCode, bankId "
					+ "from CreditCardDetails where rsn in (:rsnlist) and status = "
							+(long)CardStateManagerService.CARD_STATUS_RETURN
							+" group by processedBranchCode, bankId").setParameterList("rsnlist", rsnList).list();
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return homeBranchList;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getHomeBranchListDummy(List<Long> rsnList) {
		List<Object[]> homeBranchList = null;
		try {
			homeBranchList = getSessionFactory().getCurrentSession().createQuery("select  homeBranchCode, bankId, rsn "
					+ "from CreditCardDetails where rsn in (:rsnlist) and status = "
							+(long)CardStateManagerService.CARD_STATUS_RETURN+" order by homeBranchCode").setParameterList("rsnlist", rsnList).list();
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return homeBranchList;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<MasterAWB> getAWBList(long indianPostChennai, int limit) {
		List<MasterAWB> awbList = null;
		try {
			awbList = getSessionFactory().getCurrentSession().createCriteria(MasterAWB.class)
						.add(Restrictions.eq("serviceProviderId", indianPostChennai))
						.add(Restrictions.eq("status", Long.parseLong(statusId))).addOrder(Order.asc("awbId")) /*cr no 41*/
						.setMaxResults(limit).list();
			if(awbList != null && awbList.size() > 0) {
				Iterator<MasterAWB> itr = awbList.iterator();
				while(itr.hasNext()) {
					MasterAWB awb = (MasterAWB) itr.next();
					awb.setStatus(Long.parseLong(blockedStatusId));
					getSessionFactory().getCurrentSession().update(awb);
				}
			}
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return awbList;
	}
	
	@Override
	public int updateCardAWB(String processedBranchCode, Long bankId, String cardAWB, List<Long> rsnList, Long status, String ruleStatus) {
		int count = 0;
		try {
			count = getSessionFactory().getCurrentSession().createSQLQuery("update CUSTOMER_RECORDS_T set CARD_AWB='"+cardAWB+"', "
					+ " CREATED_DATE = getDate(), RULE_STATUS='"+ruleStatus+"', status="+status+"  where PROCESSED_BRANCH_CODE ='"+processedBranchCode+"' and "
					+ "bank_id ="+bankId+" and status ="+(long)CardStateManagerService.CARD_STATUS_RETURN+" and rsn in (:rsnlist)").setParameterList("rsnlist", rsnList).executeUpdate();
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return count;
	}


	@Override
	@SuppressWarnings("unchecked")
	public List<CreditCardDetails> getCardDetails(String homeBranchCode, Long bankId, List<Long> rsnList) {

		List<CreditCardDetails> list = null;
		try {
			list = getSessionFactory().getCurrentSession().createQuery("from CreditCardDetails where processedBranchCode = '"+homeBranchCode+"' and bankId ="+bankId
															+" and rsn in (:rsnlist) and status ="+(long)CardStateManagerService.CARD_STATUS_RETURN).setParameterList("rsnlist", rsnList).list();
//			list = getSessionFactory().getCurrentSession().createCriteria(CreditCardDetails.class)
//							.add(Restrictions.eq("homeBranchCode", homeBranchCode))
//							.add(Restrictions.eq("bankId",bankId))
//							.add(Restrictions.eq("status", (long) CardStateManagerService.CARD_STATUS_RETURN))
//							.add(Restrictions.).list();
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}


	@Override
	public void saveRecordEvent(RecordEvent event) {
		getSessionFactory().getCurrentSession().saveOrUpdate(event);
		
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<CreditCardDetails> getCustomerRecords(List<Long> rsnList) {
		List<CreditCardDetails> list = null;
		try {
			list = getSessionFactory().getCurrentSession().createQuery("from CreditCardDetails where rsn in (:rsnList) and status ="+(long)CardStateManagerService.CARD_STATUS_RETURN).setParameterList("rsnList", rsnList).list();
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public void saveCustomerRecord(CreditCardDetails details) {
		getSessionFactory().getCurrentSession().saveOrUpdate(details);
	}



	public List<CreditCardDetails> getCardDetails(Long bankId,
		List<Long> rsnList) {
		List<CreditCardDetails> list = null;
		try {
			list = getSessionFactory().getCurrentSession().createCriteria(CreditCardDetails.class)
					.add(Restrictions.in("rsn", rsnList)).add(Restrictions.eq("bankId", bankId))
					.add(Restrictions.eq("status", (long)CardStateManagerService.CARD_STATUS_RETURN)).addOrder(Order.asc("rsn")).list();
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}



	public List<CreditCardDetails> getCardDetailsByFileId(Set<Long> fileIdList,
			List<Long> rsnList) {
		List<CreditCardDetails> list = null;
		try {
			list = getSessionFactory().getCurrentSession().createQuery("from CreditCardDetails where rsn in (:rsnList) and fileId in "
					+ "(select id from CoreFiles where id in (:fileIdList) and lcpcGroup <> '"+FileStateManager.LCPC_FILE_TYPE+"')")
					.setParameterList("fileIdList", fileIdList).setParameterList("rsnList", rsnList).list();
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}
	
	public List<CreditCardDetails> getLPCardDetailsByFileId(Set<Long> fileIdList,
			List<Long> rsnList) {
		List<CreditCardDetails> list = null;
		try {
			list = getSessionFactory().getCurrentSession().createQuery("from CreditCardDetails where rsn in (:rsnList) and fileId in "
					+ "(select id from CoreFiles where id in (:fileIdList) and lcpcGroup = '"+FileStateManager.LCPC_FILE_TYPE+"')")
					.setParameterList("fileIdList", fileIdList).setParameterList("rsnList", rsnList).list();
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}



	public void saveRecordAndEventBulk(List<CreditCardDetails> detailsList,
			List<RecordEvent> eventList) {
			
		try {
			Session session = getSessionFactory().getCurrentSession();
			for(int i = 0 ; i < detailsList.size() ; i ++) {
				CreditCardDetails details  = (CreditCardDetails) detailsList.get(i);
				RecordEvent event = (RecordEvent) eventList.get(i);
				session.saveOrUpdate(details);
				session.saveOrUpdate(event);
			}
		} catch(Exception e) {
			logger.error(e); 
			e.printStackTrace();
		}
		
	}



	public List<CreditCardDetails> getCardDetails(Long bankId, String rsnStr) {
		// TODO Auto-generated method stub
		return getSessionFactory().getCurrentSession().createQuery("from CreditCardDetails where rsn in "+rsnStr+" and bankId ="+bankId).list();
	}



	public List<CreditCardDetails> getCardDetailsByFileId(Set<Long> fileIdList,
			String rsnList) {
		List<CreditCardDetails> list = null;
		try {
			list = getSessionFactory().getCurrentSession().createQuery("from CreditCardDetails where rsn in "+rsnList+" and fileId in "
					+ "(select id from CoreFiles where id in (:fileIdList) and lcpcGroup <> '"+FileStateManager.LCPC_FILE_TYPE+"')")
					.setParameterList("fileIdList", fileIdList).list();
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}



	public List<CreditCardDetails> getLPCardDetailsByFileId(Set<Long> fileIdList,
			String rsnList) {
		List<CreditCardDetails> list = null;
		try {
			list = getSessionFactory().getCurrentSession().createQuery("from CreditCardDetails where rsn in "+rsnList+" and fileId in "
					+ "(select id from CoreFiles where id in (:fileIdList) and lcpcGroup = '"+FileStateManager.LCPC_FILE_TYPE+"')")
					.setParameterList("fileIdList", fileIdList).list();
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}





}
