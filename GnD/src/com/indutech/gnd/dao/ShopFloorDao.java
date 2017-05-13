package com.indutech.gnd.dao;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.indutech.gnd.bo.BankBO;
import com.indutech.gnd.bo.ShopFloorDetails;
import com.indutech.gnd.dto.Bank;
import com.indutech.gnd.dto.CoreFiles;
import com.indutech.gnd.controller.CardStateManagerController;
import com.indutech.gnd.dto.CreditCardDetails;
import com.indutech.gnd.dto.MasterAWB;
import com.indutech.gnd.dto.MasterCourierService;
import com.indutech.gnd.dto.MasterDcms;
import com.indutech.gnd.dto.RecordEvent;
import com.indutech.gnd.enumTypes.FileStatus;
import com.indutech.gnd.enumTypes.FileType;
import com.indutech.gnd.enumTypes.Status;
import com.indutech.gnd.service.CardStateManagerService;

@Repository("shopFloorDao")
public class ShopFloorDao {
	
	Logger logger = Logger.getLogger(ShopFloorDao.class);
	common.Logger log = common.Logger.getLogger(ShopFloorDao.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public CreditCardDetails findRsn(Long rsnVal) {
		CreditCardDetails details = null;
		Criteria critRsn = getSessionFactory().getCurrentSession()
				.createCriteria(CreditCardDetails.class);
		List<CreditCardDetails> creditcardDetails = critRsn.add(
				Restrictions.eq("rsn", rsnVal)).list();
		if (!creditcardDetails.isEmpty()) {
			details = creditcardDetails.get(0);
		}

		return details;
	}

	@SuppressWarnings("unchecked")
	public List<CreditCardDetails> findAWB(String awbVal) {
		Criteria critRsn = getSessionFactory().getCurrentSession()
				.createCriteria(CreditCardDetails.class);
		List<CreditCardDetails> creditcardDetails = critRsn.add(
				Restrictions.eq("cardAWB", awbVal)).list();
		return creditcardDetails;
	}

	public List<MasterCourierService> getDCMSDao() {
		List<MasterCourierService> dcmslist = null;
		try {
			dcmslist = getSessionFactory().getCurrentSession()
					.createCriteria(MasterCourierService.class).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dcmslist;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public 	Map getAWBforDcmsIdDao(Integer dcmsId) {
		Map map=new HashMap();
		Status status = Status.valueOf("ACTIVE");
		String statusId = status.getStatus();
		Status statusBlo = Status.valueOf("BLOCKED");
		String statusBlocked = statusBlo.getStatus();
		String awbNumber = null;
		String awb = "";
		int availableAWB=0;
		int totalAWB=0;
		int blockedAWB=0;
		List<MasterDcms> dcmslist = null;
		try {
			Session sess = getSessionFactory().getCurrentSession();
		/*	List list = sess.createQuery(
					"select max(awbName) from MasterAWB where serviceProviderId="
							+ dcmsId + " and status=" + statusId).list();*/
			List list =  sess.createCriteria(MasterAWB.class)
					.add(Restrictions.eq("serviceProviderId", (long)dcmsId))
					.add(Restrictions.eq("status", Long.parseLong(statusId))).addOrder(Order.asc("awbId")).setProjection(Projections.property("awbName")).setFirstResult(0) .setMaxResults(1).list(); /*cr no 41*/
			
			logger.info("the size "+list.size()+"  0 "+list.get(0));
			if((list!=null)&&(list.size()==0)){
				map.put("SIZE", "NO AWB IS PRESENT");
			}
			if (list != null) {
				int count = 0;
				awbNumber = list.get(0) != null ? (String) list.get(0) : null;
				if (awbNumber != null) {
					count = sess.createQuery(
							"update MasterAWB set status=" + statusBlocked
									+ " where awbName='" + awbNumber
									+ "' and serviceProviderId=" + dcmsId).executeUpdate();
					logger.info("count " + count);
					logger.info("status blocjked" + statusBlocked
							+ " status Id" + statusId);
					availableAWB=sess.createQuery("from MasterAWB where serviceProviderId="+dcmsId+" and status=" + statusId).list().size();
					totalAWB=sess.createQuery("from MasterAWB where serviceProviderId="+dcmsId).list().size();
					blockedAWB=sess.createQuery("from MasterAWB where serviceProviderId="+dcmsId+" and status=" + statusBlocked).list().size();
				}
				if (count == 1) {
					map.put("AWB", awbNumber);
					map.put("availableAWB", availableAWB);
					map.put("totalAWB", totalAWB);
					map.put("blockedAWB", blockedAWB);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	public Map getchangeAWBbyRsnDao(String cardpin, String awb, Long rsn) {
	Map map=new HashMap();
	Status status = Status.valueOf("ACTIVE");
	String statusId = status.getStatus();
	Status statusBlo = Status.valueOf("BLOCKED");
	String statusBlocked = statusBlo.getStatus();
		@SuppressWarnings("unchecked")
		List<CreditCardDetails> cardDetails = getSessionFactory()
				.getCurrentSession().createCriteria(CreditCardDetails.class)
				.add(Restrictions.eq("rsn", rsn)).list();
		int count=0;
		if((cardDetails != null)&&(cardDetails.size()==0)){
			getSessionFactory()
			.getCurrentSession().createQuery(
					"update MasterAWB set status=" + statusId
							+ " where awbName='" + awb
							+ "' and status=" + statusBlocked).executeUpdate();	
			map.put("SIZE", "NO RSN IS PRESENT");
		}
		if ((cardDetails != null)&&(cardDetails.size()>0)) {
			if (cardpin.equals("CARD")) {
				count = getSessionFactory()
						.getCurrentSession()
						.createQuery(
								"update CreditCardDetails set cardAWB='" + awb
										+ "' where rsn=" + rsn).executeUpdate();
				logger.info("count is "+count);
				if (count == 1) {
					RecordEvent event = new RecordEvent();
					event.setEventId((long) CardStateManagerService.CARD_STATUS_RTO_INDIVIDUAL_CARD_AWB_ASSIGN);
					event.setRecordId(cardDetails.get(0)
							.getCreditCardDetailsId());
					event.setEventDate(new Date());
					event.setDescription( CardStateManagerService.CARD_STATUS_RTO_CARD_AWB_ASSIGN_STRING
							+ cardDetails.get(0).getCardAWB());
					getSessionFactory().getCurrentSession().save(event);
					map.put("CARD", "CARD AWB IS UPDATED SUCESSFULLY");
				}
				
			} else {

					count =getSessionFactory().getCurrentSession().createQuery(
						"update CreditCardDetails set pinAWB='" + awb
								+ "' where rsn=" + rsn).executeUpdate();
					logger.info("the count is pin"+count);
				if (count == 1) {
					RecordEvent event = new RecordEvent();
					event.setRecordId(cardDetails.get(0)
							.getCreditCardDetailsId());
					event.setEventDate(new Date());
					event.setDescription("PINAWB changed from "
							+ cardDetails.get(0).getPinAWB());
					getSessionFactory().getCurrentSession().save(event);
					map.put("PIN", "PIN AWB IS UPDATED SUCESSFULLY");
				}
			
			}
			
		}
		return map;
	}

	public List<Bank> getBankList() {
		List<Bank> list = null;
		try {
			list = getSessionFactory().getCurrentSession().createCriteria(Bank.class).addOrder(Order.desc("shortCode")).list();
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}

	public List<CoreFiles> getFilesList(String receivedDate, long bankId) {
		List<CoreFiles> list = null;
		try {
//			list = getSessionFactory().getCurrentSession().createQuery("from CoreFiles where convert(date,receivedDate)="
//					+receivedDate+" and fileType="+Long.parseLong(FileType.valueOf("CORE").getFileType())+" and status ="+
//					Long.parseLong(FileStatus.valueOf("APPROVED").getFileStatus())+" and substring(filename,1,1)="
//							+ "(select prefix from Bank where bankId="+bankId+")").list();
			
			list = getSessionFactory().getCurrentSession().createSQLQuery("select *from MASTER_CORE_FILES where convert(date,RECEIVED_DATE)='"
					+receivedDate+"' and FILE_TYPE="+Long.parseLong(FileType.valueOf("CORE").getFileType())+" and STATUS ="+
					Long.parseLong(FileStatus.valueOf("APPROVED").getFileStatus())+" and substring(FILENAME,1,1)="
							+ "(select prefix from master_bank_t where bank_id="+bankId+")").addEntity(CoreFiles.class).list();
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}

	public List<String> getHomeBranchCodeList(String receivedDate, long bankId,
			long fileId, long status) {
		List<String> list = getSessionFactory().getCurrentSession().createQuery("select distinct homeBranchCode from CreditCardDetails where bankId="+bankId+" and fileId ="+fileId+" and status ="+status).list();
		return list;
	}

	public List<CreditCardDetails> getRecordDetails(String receivedDate,
			long bankId, long fileId, long status, String homeBranchCode) {

		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(CreditCardDetails.class).add(Restrictions.eq("bankId", bankId))
									.add(Restrictions.eq("fileId", fileId)).add(Restrictions.eq("status", status));
			if(homeBranchCode != null && !homeBranchCode.isEmpty()) {
				criteria.add(Restrictions.eq("homeBranchCode", homeBranchCode));
			}
			List<CreditCardDetails> list = criteria.list();
		
		return list;
	}

		
		
		
		public List getRecordDetails(String fromDate, String toDate, String status, String bankId) {
			List list = null;
			try {
				list = getSessionFactory().getCurrentSession().
					createSQLQuery("select distinct cust.RSN, cust.PROCESSED_BRANCH_CODE, cust.PRODUCT, cust.RULE_STATUS, bank.short_code, "
							+ "files.FILENAME, cust.CARD_AWB from CUSTOMER_RECORDS_T cust, txn_record_event_log event,"
							+ " master_bank_t bank, MASTER_CORE_FILES files where cust.BANK_ID = bank.bank_id and "
							+ "event.record_id = cust.CREDIT_CARD_DETAILS_ID and event.event_id = cust.STATUS and"
							+ " cust.FILE_ID = files.ID and cust.BANK_ID = "+Long.parseLong(bankId)+" and convert(date, event.event_date) between '"+fromDate+"' and '"+
							toDate+"' and cust.status ="+Long.parseLong(status)+" order by cust.RSN, cust.PROCESSED_BRANCH_CODE").list();
			} catch(Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			return list;
		}

	public 	Object[]  getCardDetails(Long rsn,Long state) {
	Object[] array=null;
	List<Object[]> resultList=getSessionFactory().getCurrentSession().createSQLQuery("select cust.RSN,cust.CARD_AWB ,bank.short_code,fil.FILENAME,tx.info from CUSTOMER_RECORDS_T cust,master_bank_t bank,MASTER_CORE_FILES fil,txn_record_event_log tx where cust.FILE_ID=fil.ID and cust.BANK_ID=bank.bank_id and cust.CREDIT_CARD_DETAILS_ID=tx.record_id and cust.STATUS=tx.event_id and cust.RSN="+Long.valueOf(rsn)+" and tx.event_id="+Long.valueOf(state)+" order by tx.event_date desc").list();
	if(resultList!=null&&resultList.size()>0){
		array=resultList.get(0);
	}
		return array;
	}
	
	public 	List<Object[]>  getCardDetails(List<Long> rsnList,Long state) {
		List<Object[]> resultList = new ArrayList<Object[]>();
		try {
			int i = 1;
			Session session = getSessionFactory().getCurrentSession();
			for(Long rsn : rsnList) {
				logger.info("rsn is : "+rsn +" and count is : "+(i++));
				List<Object[]> list = session.createQuery("select cust.rsn,cust.cardAWB ,bank.shortCode,fil.filename,tx.description from CreditCardDetails cust,Bank bank,CoreFiles fil,RecordEvent tx where cust.fileId=fil.id and cust.bankId=bank.bankId and cust.creditCardDetailsId=tx.recordId and cust.status=tx.eventId and cust.rsn = "+ rsn +" and tx.eventId="+state+" order by tx.eventDate desc").list();
				if(list != null && list.size() > 0) {
					Object[] obj = (Object[]) list.get(0);
					resultList.add(obj);
				}
			}
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
//		List<Object[]> resultList=getSessionFactory().getCurrentSession().createQuery("select cust.rsn,cust.cardAWB ,bank.shortCode,fil.filename,tx.description from CreditCardDetails cust,Bank bank,CoreFiles fil,RecordEvent tx where cust.fileId=fil.id and cust.bankId=bank.bankId and cust.creditCardDetailsId=tx.recordId and cust.status=tx.eventId and cust.rsn in (:rsnList) and tx.eventId="+state+" order by tx.eventDate desc").setParameterList("rsnList", rsnList).list();
		return resultList;
	}
	
	public Object[] getCardDetailsAWB(ShopFloorDetails shopfloordetails) {
		Object[] array=null;//String awb,Long state
		@SuppressWarnings("unchecked")
		List<Object[]> resultList=getSessionFactory().getCurrentSession().createSQLQuery("select cust.RSN,cust.CARD_AWB ,bank.short_code,fil.FILENAME,tx.info from CUSTOMER_RECORDS_T cust,master_bank_t bank,MASTER_CORE_FILES fil,txn_record_event_log tx where cust.FILE_ID=fil.ID and cust.BANK_ID=bank.bank_id and cust.CREDIT_CARD_DETAILS_ID=tx.record_id and cust.STATUS=tx.event_id and cust.CARD_AWB='"+shopfloordetails.getCardAWB()+"' and cust.RSN="+shopfloordetails.getRsn()+" and tx.event_id="+shopfloordetails.getStatus()+" order by tx.event_date desc").list();
		if(resultList!=null&&resultList.size()>0){
			array=resultList.get(0);
		}
			return array;
	}
	
	
	
	
	public int getCountRTO() {
		int count=getSessionFactory().getCurrentSession().createCriteria(CreditCardDetails.class).add(Restrictions.eq("status",Long.valueOf(CardStateManagerController.CARD_STATUS_RETURN) )).list().size();
		return count;
	}

	public Object[] getCardDetailsPin(Long rsn, Long status) {
		Object[] array=null;
		List<Object[]> resultList=getSessionFactory().getCurrentSession().createSQLQuery("select cust.RSN,cust.PIN_AWB ,bank.short_code,fil.FILENAME,tx.info from CUSTOMER_RECORDS_T cust,master_bank_t bank,MASTER_CORE_FILES fil,txn_record_event_log tx where cust.FILE_ID=fil.ID and cust.BANK_ID=bank.bank_id and cust.CREDIT_CARD_DETAILS_ID=tx.record_id and cust.PIN_STATUS=tx.event_id and cust.RSN="+Long.valueOf(rsn)+" and tx.event_id="+Long.valueOf(status)+" order by tx.event_date desc").list();
		if(resultList!=null&&resultList.size()>0){
			array=resultList.get(0);
		}
			return array;
	}

	@SuppressWarnings("unchecked")
	public void getdispatchdetails(Long rsn,ShopFloorDetails shopfloor) {
		List<Object[]> list=getSessionFactory().getCurrentSession().createQuery("select tx.eventId,cust.rsn,tx.eventDate from RecordEvent tx,CreditCardDetails cust "
				+ "where cust.creditCardDetailsId=tx.recordId and tx.eventId="+CardStateManagerService.CARD_STATUS_REDISPATCH+" and cust.rsn="+rsn+" order by tx.eventDate desc").list();
		if(list!=null&&list.size()>0){
			Object[] obj=list.get(0);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			//shopfloor.setEventdate(sdf.format(obj[2]!=null?obj[2]:"not available"));
			shopfloor.setEventdate(obj[2].toString());
		}
	}
	@SuppressWarnings("unchecked")
	public void getdispatchdetailsAWB(String awb,ShopFloorDetails shopfloor) {
		List<Object[]> list=getSessionFactory().getCurrentSession().createQuery("select tx.eventId,cust.cardAWB,tx.eventDate from RecordEvent tx,CreditCardDetails cust "
				+ "where cust.creditCardDetailsId=tx.recordId and tx.eventId="+CardStateManagerService.CARD_STATUS_REDISPATCH+" and cust.cardAWB='"+awb+"' order by tx.eventDate desc").list();
		if(list!=null){
			Object[] obj=list.get(0);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			shopfloor.setEventdate(sdf.format(obj[2]));
		}
	}

	public List<Object[]> getCardDetails(String rsnStr, long status) {
		List<Object[]> list = getSessionFactory().getCurrentSession().createQuery("select cust.rsn,cust.cardAWB ,bank.shortCode,fil.filename,tx.description from CreditCardDetails cust,Bank bank,CoreFiles fil,RecordEvent tx where cust.fileId=fil.id and cust.bankId=bank.bankId and cust.creditCardDetailsId=tx.recordId and cust.status=tx.eventId and cust.rsn in "+ rsnStr +" and tx.eventId="+status+" order by tx.eventDate desc").list();
		return list;
	}
}
