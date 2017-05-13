package com.indutech.gnd.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Multimap;
import com.indutech.gnd.bo.drool.DroolRecordBO;
import com.indutech.gnd.controller.CardStateManagerController;
import com.indutech.gnd.dto.Bank;
import com.indutech.gnd.dto.Branch;
import com.indutech.gnd.dto.CoreFiles;
import com.indutech.gnd.dto.CreditCardDetails;
import com.indutech.gnd.dto.EmbossaFormat;
import com.indutech.gnd.dto.EmbossaRSN;
import com.indutech.gnd.dto.MasterAWB;
import com.indutech.gnd.dto.MasterCourierService;
import com.indutech.gnd.dto.Network;
import com.indutech.gnd.dto.Product;
import com.indutech.gnd.dto.ProductMapping;
import com.indutech.gnd.dto.RSNSequenceNumber;
import com.indutech.gnd.dto.RecordEvent;
import com.indutech.gnd.dto.TestData;
import com.indutech.gnd.enumTypes.FileType;
import com.indutech.gnd.enumTypes.RecordStatus;
import com.indutech.gnd.enumTypes.Status;
import com.indutech.gnd.service.CardStateManagerService;
import com.indutech.gnd.service.FileStateManager;
import com.indutech.gnd.service.PinStateManagerService;
import com.indutech.gnd.service.PropertiesLoader;

@Repository("gndDAO")
public class GNDDAOImpl implements GNDDAO {

	Logger logger = Logger.getLogger(GNDDAOImpl.class);
	common.Logger log = common.Logger.getLogger(GNDDAOImpl.class);
	
	Properties properties = PropertiesLoader.getInstance().loadProperties();
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private DroolRecordBO droolRecord;
	
	
	
	public DroolRecordBO getDroolRecord() {
		return droolRecord;
	}


	public void setDroolRecord(DroolRecordBO droolRecord) {
		this.droolRecord = droolRecord;
	}


	@Override
	public Long saveCardDetails(CreditCardDetails cardDetails) {
		Long detailsId = null;
		try {
				getSessionFactory().getCurrentSession().saveOrUpdate(cardDetails);
				detailsId = cardDetails.getCreditCardDetailsId();
			//	getSessionFactory().getCurrentSession().flush();
//				logger.info("details id is : "+detailsId);
			}catch(NonUniqueObjectException nu){
				
				getSessionFactory().getCurrentSession().merge(cardDetails);
				detailsId = cardDetails.getCreditCardDetailsId();
			}catch (HibernateException e) {
				logger.error(e);
				e.printStackTrace();
		}
		return detailsId;
	}	
	
	
	public Long saveNpcCardDetails(CreditCardDetails details) {
		Long detailsId = null;
		try {
				getSessionFactory().getCurrentSession().merge(details);
				detailsId = details.getCreditCardDetailsId();
				getSessionFactory().getCurrentSession().flush();
//				logger.info("details id is : "+detailsId);
			} catch (HibernateException e) {
				logger.error(e);
				e.printStackTrace();
		}
		return detailsId;
	}	
	
	@Override
	public Long saveCreditCardDetails(CreditCardDetails cardDetails) {
		Long detailsId = null;
		try {
			detailsId = (Long) getSessionFactory().getCurrentSession().save(cardDetails);
		//	getSessionFactory().getCurrentSession().flush();
//				detailsId = cardDetails.getCreditCardDetailsId();
			//	logger.info("details id is : "+detailsId);
			} catch (HibernateException e) {
				logger.error(e);
				e.printStackTrace();
		}
		return detailsId;
	}
	
	public void flush()
	{
		getSessionFactory().getCurrentSession().flush();
	}
	
	@SuppressWarnings("unchecked")
	public 	List<Object[]>  getCardReturnDao(Long rsn) {
		System.out.println(rsn);
		try{
		List<Object[]> resultList=getSessionFactory().getCurrentSession().createSQLQuery("select cust.RSN,cust.CARD_AWB ,bank.short_code,fil.FILENAME,tx.info from CUSTOMER_RECORDS_T cust,master_bank_t bank,MASTER_CORE_FILES fil,txn_record_event_log tx where cust.FILE_ID=fil.ID and cust.BANK_ID=bank.bank_id and cust.CREDIT_CARD_DETAILS_ID=tx.record_id and cust.STATUS=tx.event_id and cust.RSN="+rsn+" and tx.event_id="+CardStateManagerController.CARD_STATUS_RETURN+" order by tx.event_date desc").list();
		System.out.println("result list is "+resultList);
		return resultList;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
		
		
	}
	@Override
	public Long getRSNSequenceNumber() {
		final RSNSequenceNumber rsnSequenceNumber = new RSNSequenceNumber();
		
		
			
				 getSessionFactory().getCurrentSession().doWork(new Work() {
					
					@Override
					public void execute(Connection arg0) throws SQLException {
						
						PreparedStatement pstmt = arg0.prepareStatement("select next value for RSN_SEQ as seq");
						ResultSet rs= pstmt.executeQuery();
						if(rs.next())
						{
							rsnSequenceNumber.setRsnSequenceId(Long.parseLong(rs.getString(1)));
						}	
						rs.close();
						pstmt.close();
						
					}
				});
				
		
		return rsnSequenceNumber.getRsnSequenceId();
	}
	
	
	
	public Long saveRecordEvent(RecordEvent event) {
		Long eventid=null;
		try {
			eventid = (Long) getSessionFactory().getCurrentSession().save(event);
//			eventid=event.getEventId();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return eventid;
	}
	public void saveRecordEventRTO(RecordEvent event, Long rsn) {
		try {
			getSessionFactory().getCurrentSession().saveOrUpdate(event);
			SQLQuery query=getSessionFactory().getCurrentSession().createSQLQuery("select cust.RSN,cust.CARD_AWB ,bank.short_code,fil.FILENAME,tx.info from CUSTOMER_RECORDS_T cust,master_bank_t bank,MASTER_CORE_FILES fil,txn_record_event_log tx where cust.FILE_ID=fil.ID and cust.BANK_ID=bank.bank_id and cust.CREDIT_CARD_DETAILS_ID=tx.record_id and cust.STATUS=tx.event_id and cust.RSN="+Long.valueOf(rsn)+" and tx.event_id="+Long.valueOf(CardStateManagerController.CARD_STATUS_RETURN)+" order by tx.event_date desc");
			System.out.println(query.list());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public List<CreditCardDetails> getCreditCardDetails() {
		@SuppressWarnings("unchecked")
		List<CreditCardDetails> list = getSessionFactory().getCurrentSession().createCriteria(CreditCardDetails.class).list();
		return list;
	}
	
	@Override
	public List<CreditCardDetails> getHoldStateCreditCardDetails() {
		logger.info("Inside getHoldStateCreditCardDetails GNDDAOImpl");
		logger.info(" getRejectedStateCreditCardDetails getDateBefore(3)"+getDateBefore(3));
		List<CreditCardDetails> list = null;
		try{
			Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(CreditCardDetails.class);
			criteria.add(Restrictions.eq("status", new Long(2)));
			criteria.add(Restrictions.le("createdDate", getDateBefore(3)));
			list = criteria.list();
		}catch(Exception e){
			e.printStackTrace();
			logger.error("error while fetching credit card details", e);
		}
		
		return list;
	}
	
	@Override
	public List<CreditCardDetails> getRejectedStateCreditCardDetails() {
		logger.info("Inside getRejectedStateCreditCardDetails GNDDAOImpl");
		logger.info(" getRejectedStateCreditCardDetails getDateBefore(10)"+getDateBefore(10));
		List<CreditCardDetails> list = null;
		try{
			Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(CreditCardDetails.class);
			criteria.add(Restrictions.eq("status", new Long(1)));
			criteria.add(Restrictions.le("createdDate", getDateBefore(10)));
		 list = criteria.list();
		}catch(Exception e){
			logger.error("error while fetching credit card details", e);
		}													
		return list;
	}
	
	private Date getDateBefore(int numberOfDays) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -numberOfDays);
		Date dateBefore3Days = cal.getTime();
		return dateBefore3Days;
	}
	
	@Override
	public List<String> getHomeBranchCode(String homeBranchCode) {
		// TODO Auto-generated method stub
		
		@SuppressWarnings("unchecked")
		List<String> list = getSessionFactory().getCurrentSession().createQuery("select distinct c.homeBranchCode from CreditCardDetails c where c.homeBranchCode=?").setParameter(0, homeBranchCode).list();
		return list;
	}
	
//	@Override
//	public List<String> getIssueBranchCode(String issueBranchCode) {
//		// TODO Auto-generated method stub
//		@SuppressWarnings("unchecked")
//		List<String> list = getSessionFactory().getCurrentSession().createQuery("select distinct c.issueBranchCode from CreditCardDetails c where c.issueBranchCode=?").setParameter(0, issueBranchCode).list();
//		return list;
//	}
	
	@Override
	public List<String> getCardStatus() {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<String> list = getSessionFactory().getCurrentSession().createQuery("select distinct c.cardStatus from CreditCardDetails c").list();
		return list;
	}

	@Override
	public List<String> getForthLine() {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<String> list = getSessionFactory().getCurrentSession().createQuery("select distinct c.fourthLinePrintingData from CreditCardDetails c").list();
		return list;
	}
	
	public List<CreditCardDetails> checkRecordValidity(String customerId, String primaryAcNo, String product) {
		@SuppressWarnings("unchecked")
		List<CreditCardDetails> list = getSessionFactory().getCurrentSession().
																	createQuery("from CreditCardDetails  where customerId = ? and primaryAcctNo = ?"
																				+ "and product =? and status >="+(long)CardStateManagerService.CARD_STATUS_APPROVED+" order by createdDate desc").setParameter(0, customerId)
																				  .setParameter(1,primaryAcNo).setParameter(2, product).list();
		return list;
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CreditCardDetails> getNCFRecord(String primaryAcctNo, String product) {
		List<CreditCardDetails> ncfDetails = null;
		try {
			Session session = getSessionFactory().getCurrentSession();
			ncfDetails = session.createQuery("from CreditCardDetails where primaryAcctNo ='"+primaryAcctNo+"' and product ='"+product+"' and fileId in (select id from CoreFiles where fileType ="+Long.parseLong(FileType.valueOf("CORE").getFileType())+" and (lcpcGroup='"+FileStateManager.NCF_FILE_TYPE+"' or lcpcGroup='"+FileStateManager.AUF2_NCF_FILE_TYPE+"'))").list();
//			ncfDetails = session.createCriteria(CreditCardDetails.class).
//			add(Restrictions.eq("primaryAcctNo",primaryAcctNo))
//			.add(Restrictions.eq("product", product))
//			.add(Restrictions.eq("isIndividual", FileStateManager.CORE_NCF_INDIVIDUALITY)).list();
//			session.clear();
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return ncfDetails;
	}

	@Override
	public void updateCardDetails(CreditCardDetails details) {
		try {
		Session session = getSessionFactory().getCurrentSession();
		session.update(details);
//		session.clear();
		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public CreditCardDetails getCreditCardDetails(Long creditCardDetailsId) {
		
		CreditCardDetails details = (CreditCardDetails) getSessionFactory().getCurrentSession().createCriteria(CreditCardDetails.class).
										add(Restrictions.eq("creditCardDetailsId",creditCardDetailsId)).list().get(0);
		
		return details;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CreditCardDetails> checkRecordValidity(String primaryAcNo, String productId) {
		List<CreditCardDetails> list = null;
		try  {
			list = getSessionFactory().getCurrentSession().createQuery("from CreditCardDetails c where c.primaryAcctNo = ?"
							+ "and c.product =? and c.status >="+(long)CardStateManagerService.CARD_STATUS_APPROVED).setParameter(0,primaryAcNo).setParameter(1, productId).list();
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
		return list;
	}


	public void changePinStatus(Long rsn) {
		try {
			List<CreditCardDetails> list = getSessionFactory().getCurrentSession().createCriteria(CreditCardDetails.class)
												.add(Restrictions.eq("rsn", rsn)).add(Restrictions.eq("pinstatus", (long)PinStateManagerService.PIN_STATUS_AWB_ASSIGNED)).list();
			if(list.size() > 0) {
				CreditCardDetails details = (CreditCardDetails) list.get(0);
				details.setPinstatus((long)PinStateManagerService.PIN_STATUS_DELIVER);
				getSessionFactory().getCurrentSession().saveOrUpdate(details);
				RecordEvent event = new RecordEvent();
				event.setDescription(PinStateManagerService.PIN_STATUS_DELIVER_STRING);
				event.setEventDate(new Date());
				event.setEventId((long)PinStateManagerService.PIN_STATUS_DELIVER);
				event.setRecordId(details.getCreditCardDetailsId());
				getSessionFactory().getCurrentSession().save(event);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public List<CreditCardDetails> getRecordRSN(Long rsn) {
		List<CreditCardDetails> list = null;
		try {
			list = getSessionFactory().getCurrentSession().createCriteria(CreditCardDetails.class)
				.add(Restrictions.eq("rsn", rsn)).add(Restrictions.eq("status", (long)CardStateManagerService.CARD_STATUS_AUFCONVERTED)).list();			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}


	public void updateCardDetailsGroup(Long creditCardDetailsId,
			String institutionId, String homeBranchCode, String product,
			String awbName, String awbName2, String assgnedStatus) {
		// TODO Auto-generated method stub
		
	}

/*cr no 36*/public List<CreditCardDetails> getDuplicateRecordRSN(Long rsn) {
		List<CreditCardDetails> list = null;
		try {
			list = getSessionFactory().getCurrentSession().createCriteria(CreditCardDetails.class)
				.add(Restrictions.eq("rsn", rsn)).add(Restrictions.between("status", (long)CardStateManagerService.CARD_STATUS_EMBOSSA_RECEIVED,(long)CardStateManagerService.CARD_STATUS_MD_GENERATED)).list();			
		} catch(Exception e) {
			e.printStackTrace();
		}
			return list;
	}


	@Override
	public List<CreditCardDetails> getDetailsListByBranch(String branchCode, Integer limit, Long fileId, Long bankId) {
		
		List<CreditCardDetails> list = (List<CreditCardDetails>) getSessionFactory().getCurrentSession().createCriteria(CreditCardDetails.class)
																.add(Restrictions.eq("processedBranchCode", branchCode))
																.add(Restrictions.eq("status", Long.parseLong(RecordStatus.valueOf("APPROVED").getRecordStatus())))
																.add(Restrictions.eq("fileId", fileId))
																.add(Restrictions.eq("bankId", bankId))
																.addOrder(Order.asc("processedBranchCode"))
																.addOrder(Order.asc("product"))
																.setMaxResults(limit).list();
																
		return list;
	}
	
	@Override
	public List<CreditCardDetails> getDetailsListByLCPCBranch(String branchCode, Integer limit, Long fileId, Long bankId) {
		
		List<CreditCardDetails> list = (List<CreditCardDetails>) getSessionFactory().getCurrentSession().createCriteria(CreditCardDetails.class)
																.add(Restrictions.eq("lcpcBranch", branchCode))
																.add(Restrictions.eq("status", Long.parseLong(RecordStatus.valueOf("APPROVED").getRecordStatus())))
																.add(Restrictions.eq("fileId", fileId))
																.add(Restrictions.eq("bankId", bankId))
																.addOrder(Order.asc("processedBranchCode"))
																.addOrder(Order.asc("product"))
																.setMaxResults(limit).list();
																
		return list;
	}
	
	@Override
	public List<CreditCardDetails> getDetailsListByBranchForPin(String branchCode, Integer limit, Long fileId, Long bankId) {
		
		List<CreditCardDetails> list = (List<CreditCardDetails>) getSessionFactory().getCurrentSession().createCriteria(CreditCardDetails.class)
																.add(Restrictions.eq("processedBranchCode", branchCode))
																.add(Restrictions.eq("status", (long) CardStateManagerService.CARD_STATUS_AWBASSIGNED))
																.add(Restrictions.eq("pinstatus",(long)PinStateManagerService.PIN_STATUS_UNINITIALIZED))
																.add(Restrictions.eq("fileId", fileId))
																.add(Restrictions.eq("bankId", bankId))
																.addOrder(Order.asc("processedBranchCode"))
																.addOrder(Order.asc("product"))
																.setMaxResults(limit).list();
																
		return list;
	}

	@Override
	public List<CreditCardDetails> getEmbossaRecords(Long embId) {
		List<CreditCardDetails> group = null;
		try {
			group = getSessionFactory().getCurrentSession().createCriteria(CreditCardDetails.class)
					.add(Restrictions.eq("status", (long)CardStateManagerService.CARD_STATUS_EMBOSSA_RECEIVED))
					.add(Restrictions.eq("embossaId", embId)).list();
		}		
		catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return group;
	}

	@Override
	public String getBankCode(String institutionId) {
		String bankShortCode = null;
		try {
			logger.info("bank code is : "+institutionId);
			List<Bank> list=  getSessionFactory().getCurrentSession().createCriteria(Bank.class).add(Restrictions.eq("bankCode", institutionId)).list();
			if(list.size() > 0) {
				Bank bank = (Bank) list.get(0);
				bankShortCode = bank.getShortCode();
			}
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}		
			return bankShortCode;
	}

	@Override
	public String getFileName(Long fileId) {
		String filename = null;
		try {
			List<CoreFiles> list =  getSessionFactory().getCurrentSession().createCriteria(CoreFiles.class).add(Restrictions.eq("id", fileId)).list();
			if(list.size() > 0) {
				CoreFiles file = (CoreFiles) list.get(0);
				filename = file.getFilename();
			}
		}
		catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return filename;
	}

	@Override
	public List<EmbossaFormat> getEmbossaFormat(String productCode, String bankCode) {
		List<EmbossaFormat> embossaFormatList = null;
		try {
		embossaFormatList = (List<EmbossaFormat>) getSessionFactory().getCurrentSession()
												.createQuery("from EmbossaFormat format where format.formatId = "
														+ "(select product.networkId from Product product where product.shortCode ='"+productCode+"' and product.bankId = "
																+ "(select bank.bankId from Bank bank where bank.bankCode ='"+bankCode+"'))").list();
		} catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		logger.info("emboss list is : "+embossaFormatList.size());
		return embossaFormatList;
	}

	@Override
	public List<CreditCardDetails> getValidCreditCardDetails(Long fileId) {
		
		List<CreditCardDetails> list = getSessionFactory().getCurrentSession().createCriteria(CreditCardDetails.class)
													.add(Restrictions.eq("status", (long) CardStateManagerService.CARD_STATUS_APPROVED))
													.add(Restrictions.eq("fileId",fileId))
													.addOrder(Order.asc("processedBranchCode"))
													.addOrder(Order.asc("product"))
													.addOrder(Order.asc("primaryAcctNo")).list();
		return list;
	}

	@Override
	public List<Long> getFileId() {

		List<Long> list = (List<Long>) getSessionFactory().getCurrentSession().createQuery("select distinct fileId from CreditCardDetails where status="+(long) CardStateManagerService.CARD_STATUS_APPROVED).list(); 
		return list;
	}
	
	@Override
	public List<CoreFiles> getFileNames() {
		List<CoreFiles> list = null;
		try {
			list = (List<CoreFiles>) getSessionFactory().getCurrentSession().createQuery("from CoreFiles where id in (select fileId from CreditCardDetails where status=?)").setParameter(0, (long) CardStateManagerService.CARD_STATUS_APPROVED).list();
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
		
	}

    @Transactional
	public List<CoreFiles> getFileList(String filename) {
		List<CoreFiles> list = null;
		try {
			list = (List<CoreFiles>) getSessionFactory().getCurrentSession().createCriteria(CoreFiles.class)
					.add(Restrictions.eq("filename", filename)).list();
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
		return list;
	}


	public List getBranchGroupForReport(Long fileId, Long status) {
		
		List list = null;
		try {
			list = getSessionFactory().getCurrentSession().createQuery("select homeBranchCode, fileId, status from CreditCardDetails group by homeBranchCode, fileId, status  having fileId="+fileId+" and status ="+status).list();
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}


	public List<CoreFiles> getFileListByDate(String fromdate, String todate, Long bankId) {
		List<CoreFiles>  list = null;
		try {
		 list =  getSessionFactory().getCurrentSession().
							   createSQLQuery("select * from MASTER_CORE_FILES where cast(RECEIVED_DATE as DAte) >= '"
//				 			   createQuery("from CoreFiles where cast(receivedDate as DAte)='"
									+ fromdate+"' and cast(RECEIVED_DATE as DAte) <= '"+todate+"'"+" and filename not like '%.npc%' and file_type ="+Long.parseLong(FileType.valueOf("CORE").getFileType())+" and substring(filename,1,1) = (select prefix from master_bank_t where bank_id = "+bankId+")").addEntity(CoreFiles.class).list();
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}


	public List getPinMailer(String institutionId, String homeBranchCode,
			String productId, long status) {
		
		List list = getSessionFactory().getCurrentSession().createQuery("select bank.shortCode as backCode,  branch.address1 as address1, branch.address2 as address2, branch.address3 as address3, branch.address4 as address4, cardDet.createdDate as receivedDate,  cardDet.homeBranchCode as homeBranchCode, cardDet.serialNo as serialNo,cardDet.embossName as embossName,cardDet.primaryAcctNo as primaryAcctNo,cardDet.product as product, cardDet.pinAWB as pinAWB, cardDet.officePhone as officePhone,  cardDet.homePhone as homePhone, corefile.filename as filename, bank.bankName as bankName from Bank as bank, CreditCardDetails as cardDet, Branch as branch, CoreFiles as corefile where bank.bankId = branch.bankId and cardDet.fileId = corefile.id and branch.shortCode = cardDet.homeBranchCode and cardDet.institutionId='"+institutionId+"' and cardDet.homeBranchCode='"+homeBranchCode+"' and cardDet.product='"+productId+"' and cardDet.status="+status).list();
		// TODO Auto-generated method stub
		return list;
	}


	public Bank getBankCodeByPrefix(String prefix) { 
		List list = null;
		try {
		 list = getSessionFactory().getCurrentSession().createCriteria(Bank.class)
						.add(Restrictions.eq("prefix", prefix)).list();
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list != null && list.size() > 0 ? (Bank) list.get(0) : null;
	}


	public String getCardAWB(Long serviceProvider) {
		MasterAWB awb = null;
		String cardAWB = null;
		try {
			List<MasterAWB>	list = getSessionFactory().getCurrentSession().createCriteria(MasterAWB.class)
					.add(Restrictions.eq("serviceProviderId", serviceProvider))
							.add(Restrictions.eq("status" , Long.parseLong(Status.valueOf("ACTIVE").getStatus()))).list();
			if(list  != null && list.size() > 0) {
				awb = (MasterAWB) list.get(0);
				cardAWB = awb.getAwbName();
				awb.setStatus(Long.parseLong(Status.valueOf("BLOCKED").getStatus()));
				getSessionFactory().getCurrentSession().update(awb);;
			}
					
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return cardAWB;
	}

	@Override
	public List<CreditCardDetails> getCustomerReport(Long fileId, long status, String homeBranchCode) {
		
		List list = getSessionFactory().getCurrentSession().createCriteria(CreditCardDetails.class)
					.add(Restrictions.eq("status", status)).add(Restrictions.eq("fileId", fileId))
					.add(Restrictions.eq("homeBranchCode", homeBranchCode)).list();
//				.createQuery("select cardDet.serialNo as serialNo,cardDet.institutionId as institutionId ,cardDet.primaryAcctNo as primaryAcctNo,cardDet.product  as product, cardDet.ruleStatus as ruleStatus from CreditCardDetails as cardDet where cardDet.status="+status+" and cardDet.fileId="+fileId+" and cardDet.homeBranchCode='"+homeBranchCode+"'").list();
		return list;
	}


	public Branch getEmail(String shortCode, Long bankId) {
		
		Branch branch = null;
		try {
			List<Branch> list = getSessionFactory().getCurrentSession().createCriteria(Branch.class)
							.add(Restrictions.eq("shortCode", shortCode))
							.add(Restrictions.eq("bankId", bankId)).list();
			if(list != null && list.size() > 0) {
				branch = (Branch) list.get(0);
			}
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return branch;
	}


	public List<EmbossaRSN> getEmbossaRSN(String productCode, String bankCode, Integer embFormat) {
		
		List<EmbossaRSN> list = null;
		try {
			list = getSessionFactory().getCurrentSession().createQuery("from EmbossaRSN where embossaFormat = "+embFormat+" and formatId = (select networkId from Product "
					+ " where shortCode ='"+productCode+"' and bankId = (select bankId from Bank where bankCode='"+bankCode+"'))").list();
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}

	public Long getNetworkId(String productCode, String bankCode) {
		Long networkId = null;
		try {
			List<Network> list = getSessionFactory().getCurrentSession().createQuery("from Network where networkId = (select networkId from Product "
					+ " where shortCode ='"+productCode+"' and bankId = (select bankId from Bank where bankCode='"+bankCode+"'))").list();
			if(list != null && list.size() > 0) {
				Network network = (Network) list.get(0);
				networkId = network.getNetworkId();
			}
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return networkId;
	}


	public Bank getBankDetails(String bankCode) {
		Bank bank = null;
		try {
			List<Bank> list = getSessionFactory().getCurrentSession().createCriteria(Bank.class).add(Restrictions.eq("bankCode", bankCode)).list();
			if(list != null && list.size() > 0) {
				bank = list.get(0);
			}
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return bank;
	}
	
	public Bank getBankDetails(Long bankId) {
		Bank bank = null;
		try {
			List<Bank> list = getSessionFactory().getCurrentSession().createCriteria(Bank.class).add(Restrictions.eq("bankId", bankId)).list();
			if(list != null && list.size() > 0) {
				bank = list.get(0);
			}
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return bank;
	}


	public List getPinMailerGroup(String fromdate, String todate, Long bankId) {
		List list = null;
		try {
			File file = new File(properties.getProperty("pincoveringNoteQuertPath"));
			BufferedReader br = new BufferedReader(new FileReader(file));
			String query = "";
			String line = "";
			while((line = br.readLine()) != null) {
				query += line;
			}
			br.close();
			logger.info("fromdate is : "+fromdate +" and to date : "+todate);
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			
			list = getSessionFactory().getCurrentSession().createSQLQuery(query).setParameter(0, fromdate).setParameter(1, todate).setParameter(2, bankId).list();			
//			list = getSessionFactory().getCurrentSession().createSQLQuery("select c.instution_Id, c.PROCESSED_BRANCH_CODE, c.product, c.file_Id, c.bank_Id, "
//					+ "(select bank.short_Code from master_Bank_t bank where bank.bank_Id=c.bank_Id) as bankName, "
//					+ "files.filename as corefilename, (select LCPC_GROUP from master_core_files where id = c.file_Id) as fileType, "
//					+ "(select bank.bank_Code from master_bank_t bank where bank.bank_Id=c.bank_Id) as bankCode, "
//					+ "(select bank.AUF_FORMAT from master_bank_t bank where bank.bank_Id=c.bank_Id) as format from customer_records_t c, master_core_files files "
//					+ " where c.status ="+(long)CardStateManagerService.CARD_STATUS_AUFCONVERTED+" and cast(files.received_date as DAte) >= '"
//									+ fromdate+"' and cast(files.received_date as DAte) <= '"+todate+"' and c.file_id=files.id"
//											+ " group by c.instution_Id, c.PROCESSED_BRANCH_CODE, files.filename, c.product, c.file_Id, c.bank_Id having c.BANK_ID="+bankId)
//											.list();
			 
		
//			list = getSessionFactory().getCurrentSession().createSQLQuery("select c.pin_awb, core.id from customer_records_t c, master_core_files core wherec.status >="+(long)CardStateManagerService.CARD_STATUS_AUFCONVERTED+" and cast(files.received_date as DAte) >= '"+ fromdate+"' and cast(files.received_date as DAte) <= '"+todate+"' group by c.pin_awb order by c.pin_awb").list();
			
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<CreditCardDetails> getRecordsList(String status, Long fileId) {
		
		List<CreditCardDetails> list = null;
		try {
			
			Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(CreditCardDetails.class);					
					criteria.add(Restrictions.eq("fileId", fileId));
					if(status.equals("Reject")) {
						criteria.add(Restrictions.eq("status", (long)CardStateManagerService.CARD_STATUS_REJECT));
					}
					if(status.equals("Hold")) {
						criteria.add(Restrictions.eq("status", (long)CardStateManagerService.CARD_STATUS_HOLD));
					}
					if(status.equals("Both")) {
						criteria.add(Restrictions.le("status", (long)CardStateManagerService.CARD_STATUS_HOLD));
					}
					list = criteria.list();
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}


	@SuppressWarnings("unchecked")
	public List<Object[]> getBranchGroupForExceedRule(Long fileId, long cardStatusApproved) {
		List<Object[]> list = null;
		try {
			list = getSessionFactory().getCurrentSession().createQuery("select processedBranchCode, count(*) from CreditCardDetails "
					+ "where fileId="+fileId+" and status ="+cardStatusApproved+" group by processedBranchCode").list();
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}


	public void updateCardDetailsToReject(String processedBranchCode, Long fileId,
			long cardStatusRejected, String ruleStatus, String flowStatus) {
		try {
		int count = getSessionFactory().getCurrentSession().createQuery("update CreditCardDetails set status ="+ cardStatusRejected+" "
				+ ", ruleStatus ='"+ruleStatus+"', flowStatus ='"+flowStatus+"' where fileId="+fileId+" and processedBranchCode ='"+processedBranchCode+"' and status ="
						+ ""+(long)CardStateManagerService.CARD_STATUS_APPROVED).executeUpdate();
		logger.info(count+" rows updated");
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
	}


	public List getBranchGroupForReportByProduct(Long fileId, Long status) {
		
		List list = null;
		try {
			list = getSessionFactory().getCurrentSession().createQuery("select homeBranchCode, fileId, record.status from CreditCardDetails details, Product product"
					+ "where product.shortCode = details.product and product.bin=details.institutionId and product.status =1 group by homeBranchCode, fileId, record.status  having fileId="+fileId+" and record.status ="+status).list();
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}
	
	
public List getCustomerReportByProduct(Long fileId, Long status, String homeBranchCode) {
		
		List list = null;
		try {
			list = getSessionFactory().getCurrentSession().createQuery("from CreditCardDetails details, Product product"
					+ " where product.shortCode = details.product and product.bankId=details.bankId and product.status =1 and details.fileId="+fileId+" and details.status ="+status+" and details.homeBranchCode = '"+homeBranchCode+"'").list();
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}


@SuppressWarnings("unchecked")
public List<ProductMapping> getProductMappingList(String bankPrefix) {
	List<ProductMapping> list = null;
	try {
//		list = getSessionFactory().getCurrentSession().createCriteria(ProductMapping.class).list();
		list = getSessionFactory().getCurrentSession().createQuery("from ProductMapping where bankId = (select bankId from Bank where prefix = '"+bankPrefix+"')").list();
	} catch(Exception e) {
		logger.error(e);
		e.printStackTrace();
	}
	return list;
}


public List<EmbossaRSN> getEmbossaRSNForEMB1(String productCode,
		String bankCode, Integer format1) {

	List<EmbossaRSN> list = null;
	try {
		list = getSessionFactory().getCurrentSession().createQuery("from EmbossaRSN where embossaFormat = "+format1+" and formatId = (select networkId from Product "
				+ " where shortCode ='"+productCode+"' and bankId = (select bankId from Bank where bankCode='"+bankCode+"'))").list();
	} catch(Exception e) {
		logger.error(e);
		e.printStackTrace();
	}
	// TODO Auto-generated method stub
	return list;
}


public List<EmbossaRSN> getEmbossaRSNForEMB2(String productCode,
		String bankShortCode, Integer format2) {

	List<EmbossaRSN> list = null;
	try {
		list = getSessionFactory().getCurrentSession().createQuery("from EmbossaRSN where embossaFormat = "+format2+" and formatId = (select networkId from Product "
				+ " where shortCode ='"+productCode+"' and bankId = (select bankId from Bank where shortCode='"+bankShortCode+"'))").list();
	} catch(Exception e) {
		logger.error(e);
		e.printStackTrace();
	}
	// TODO Auto-generated method stub
	return list;
}


public List<String> getDistinctPinAWB(Long fileId, Long bankId, Long pinStatusAwbAssigned) {
	List<String> list = null;
	try {
		list = (List<String>)getSessionFactory().getCurrentSession().createQuery("select distinct pinAWB from CreditCardDetails where fileId = "+fileId+" and bankId "+bankId+" and pinstatus"+pinStatusAwbAssigned).list();
	} catch(Exception e) {
		logger.error(e);
		e.printStackTrace();
	}
		return list;
}


public List getBranchPinGroup(long bankId, Date qcdate, int isNcf) {
	
	List list = null;
	try {
		String query = "select records.PROCESSED_BRANCH_CODE, records.PIN_AWB, "
				+ "records.bank_id, bank.short_code as shortCode, "
				+ " (select  branch.PIN_COVERING_LETTER_COUNT from master_branch_t branch where branch.short_code = records.PROCESSED_BRANCH_CODE and branch.bank_id = records.BANK_ID) as mailer_no "
				+ "from master_bank_t bank, CUSTOMER_RECORDS_T records, MASTER_CORE_FILES files where bank.bank_id = records.bank_id  "
				+ "and records.PIN_STATUS= "+(long) PinStateManagerService.PIN_STATUS_AWB_ASSIGNED+" and records.BANK_ID = "+bankId+" and records.FILE_ID = files.ID and "
				+ "convert(date,files.RECEIVED_DATE)='"+new SimpleDateFormat("yyyy-MM-dd").format(qcdate)+"' and files.FILE_TYPE="+Long.parseLong(FileType.valueOf("CORE").getFileType());
		if(isNcf == 0) {
			query += " and files.LCPC_GROUP <> '"+FileStateManager.AUF2_NCF_FILE_TYPE+"'";
		}
		else {
			query += " and files.LCPC_GROUP = '"+FileStateManager.AUF2_NCF_FILE_TYPE+"'";
		}
			query += " group by PROCESSED_BRANCH_CODE, "
				+ "PIN_AWB,records.bank_id, bank.short_code order by PROCESSED_BRANCH_CODE";
		list = getSessionFactory().getCurrentSession().createSQLQuery(query).list();
//		list = getSessionFactory().getCurrentSession().createSQLQuery("select records.PROCESSED_BRANCH_CODE, records.PIN_AWB, records.bank_id, (select bank.short_code from master_bank_t bank where bank.bank_id = records.bank_id) as shortCode from CUSTOMER_RECORDS_T records where PIN_STATUS="+PinStateManagerService.PIN_STATUS_AWB_ASSIGNED+ " and records.BANK_ID ="+bankId+" and FILE_ID in (select ID from MASTER_CORE_FILES where convert(date,RECEIVED_DATE)='"+new SimpleDateFormat("yyyy-MM-dd").format(qcdate)+"') group by PROCESSED_BRANCH_CODE, PIN_AWB,records.bank_id").list(); 
	} catch(Exception e) {
		logger.error(e);
		e.printStackTrace();
	}
	return list;
}

@Override
public String saveCardDetailsAndEvent(List<CreditCardDetails> detailsToSave, List<RecordEvent> eventList) {
	String result = null;
	try {
		Session session = getSessionFactory().getCurrentSession();
		for(int i = 0; i <detailsToSave.size();i++) {
			CreditCardDetails details = detailsToSave.get(i);
			RecordEvent event = eventList.get(i);
			session.saveOrUpdate(details);
			session.saveOrUpdate(event);
		}
		result = "Success";
		logger.info("data saved successfully");
	} catch(Exception e) {
		logger.error(e);
		e.printStackTrace();
	}
	return result;
}


@SuppressWarnings("unchecked")
public  List<Bank> getBankList() {
	// TODO Auto-generated method stub
	Session session = null;
	List<Bank> list = null;
	try {
	 session = getSessionFactory().openSession();
	 list =  session.createCriteria(Bank.class).list();
	} catch(Exception e) {
		logger.error(e);
		e.printStackTrace();
	}
	finally {
		if(session != null) {
			session.flush();
			session.close();
		}
	}
	return  list;
}


@SuppressWarnings("unchecked")
public List<Branch> getBranchList() {
	Session session = null;
	List<Branch> branchList = null;
	try {
		session = getSessionFactory().openSession();
		branchList = session.createCriteria(Branch.class).addOrder(Order.asc("bankId")).addOrder(Order.asc("shortCode")).list();

	} catch(Exception e) {
		logger.error(e);
		e.printStackTrace();
	} finally {
		if(session != null) {
			session.flush();
			session.close();
		}
	}
	return branchList;
}

@SuppressWarnings("unchecked")
public List<Product> getProductList() {
	Session session = null;
	List<Product> productList = null;
	try {
		session = getSessionFactory().openSession();
		productList = session.createCriteria(Product.class).addOrder(Order.asc("bankId")).addOrder(Order.asc("shortCode")).list();

	} catch(Exception e) {
		logger.error(e);
		e.printStackTrace();
	} finally {
		if(session != null) {
			session.flush();
			session.close();
		}
	}
	return productList;
}

@SuppressWarnings("unchecked")
public List<MasterCourierService> getCourierServiceList() {
	Session session = null;
	List<MasterCourierService> courierServiceList = null;
	try {
		session = getSessionFactory().openSession();
		courierServiceList = session.createCriteria(MasterCourierService.class).addOrder(Order.asc("id")).list();

	} catch(Exception e) {
		logger.error(e);
		e.printStackTrace();
	} finally {
		if(session != null) {
			session.flush();
			session.close();
		}
	}
	return courierServiceList;
}


@SuppressWarnings("unchecked")
public List<ProductMapping> getProductMappingList() {
	Session session = null;
	List<ProductMapping> productMappingList = null;
	try {
		session = getSessionFactory().openSession();
		productMappingList = session.createCriteria(ProductMapping.class).addOrder(Order.asc("bankId")).list();

	} catch(Exception e) {
		logger.error(e);
		e.printStackTrace();
	} finally {
		if(session != null) {
			session.flush();
			session.close();
		}
	}
	return productMappingList;
}

	public List<CoreFiles> getRejectedRecordListByDate(String fromdate, String todate, Long bankId) {
		List<CoreFiles>  list = null;
		String query ="select records.BANK_ID, records.HOME_BRANCH_CODE, records.SERIAL_NO, records.INSTUTION_ID, "
				+ "records.PRIMARY_ACCT_NO,records.PRODUCT,records.RULE_STATUS, branch.email, "
				+ "files.FILENAME,CONVERT(date, files.RECEIVED_DATE) as qcdate from CUSTOMER_RECORDS_T records, MASTER_CORE_FILES files, master_branch_t branch, "
				+ "master_product_t product where records.STATUS = 1 and records.FILE_ID = files.ID and records.bank_id = "+bankId+" and "
				+ "files.FILE_TYPE = 1 and files.FILENAME not like '%.npc%' and CONVERT(date, files.RECEIVED_DATE)"
				+ " between '"+fromdate+"' and '"+todate+"' and records.HOME_BRANCH_CODE = branch.short_code "
				+ " and records.BANK_ID = branch.bank_id and records.PRODUCT = product.short_code and "
				+ "records.BANK_ID = product.bank_id and LTRIM(branch.email) <> '' and branch.email is not null";
		try {
		 list =  getSessionFactory().getCurrentSession().
							   createSQLQuery(query).list();
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	return list;
}


	public void saveTestData(TestData record) {
		getSessionFactory().getCurrentSession().saveOrUpdate(record);
		
	}



	public int updateCompositeRecords(Long fileId) {
		int count = 0;
		int rowCount = 0;
		try {
			Long status = (long) CardStateManagerService.CARD_STATUS_APPROVED;
		/*	cr no 39 changes starts */
			String queryForCount =  "select count(*) from CUSTOMER_RECORDS_T t1,"
					+ "(select CUSTOMER_ID, PRIMARY_ACCT_NO, PRODUCT, COUNT(*) as no_of_records from CUSTOMER_RECORDS_T "
					+ "where STATUS >= "+status+" and CONVERT(date, CREATED_DATE) > GETDATE()-15 group by CUSTOMER_ID, PRIMARY_ACCT_NO, "
					+ "PRODUCT ) as t2 where t1.FILE_ID = "+fileId+" and t1.CUSTOMER_ID = t2.CUSTOMER_ID and t1.PRIMARY_ACCT_NO = t2.PRIMARY_ACCT_NO "
					+ "and t1.PRODUCT = t2.PRODUCT and t2.no_of_records > 1 and t1.STATUS = "+status;
			rowCount = ((Integer)getSessionFactory().getCurrentSession().createSQLQuery(queryForCount).uniqueResult()).intValue();
			/**String qry = "update t1 set t1.status = "+(long) CardStateManagerService.CARD_STATUS_REJECT
					+", t1.FLOW_STATUS = '"+DroolRecordBO.FLOW_ERROR+"', "
					+ "t1.RULE_STATUS = '"+DroolRecordBO.COMPOSITE_KEY_ERROR+" '+t2.CARD_AWB, t1.CREATED_DATE = GETDATE() "
					+ "from CUSTOMER_RECORDS_T t1, CUSTOMER_RECORDS_T t2 where t1.CUSTOMER_ID = t2.CUSTOMER_ID "
					+ "and t1.PRIMARY_ACCT_NO = t2.PRIMARY_ACCT_NO and t1.PRODUCT = t2.PRODUCT and t1.FILE_ID = "+fileId+" and t2.FILE_ID <> "+fileId
					+ "and t1.STATUS = "+(long) CardStateManagerService.CARD_STATUS_APPROVED+ "and t2.STATUS >= "+(long) CardStateManagerService.CARD_STATUS_APPROVED
					+" and CONVERT(date, t2.CREATED_DATE) > GETDATE()-15";**/
			String query = "update t1 set t1.STATUS = "+(long) CardStateManagerService.CARD_STATUS_REJECT+","
					+ " t1.rule_status = '"+DroolRecordBO.COMPOSITE_KEY_ERROR+"', t1.FLOW_STATUS = '"+DroolRecordBO.FLOW_ERROR+"',"
					+ " t1.CREATED_DATE = GETDATE() from CUSTOMER_RECORDS_T t1,"
					+ "(select max(CREDIT_CARD_DETAILS_ID) id, CUSTOMER_ID, PRIMARY_ACCT_NO, PRODUCT, COUNT(*) as no_of_records from CUSTOMER_RECORDS_T "
					+ "where STATUS >= "+status+" and CONVERT(date, CREATED_DATE) > GETDATE()-15 group by CUSTOMER_ID, PRIMARY_ACCT_NO, "
					+ "PRODUCT ) as t2 where t1.FILE_ID = "+fileId+" and t1.CUSTOMER_ID = t2.CUSTOMER_ID and t1.PRIMARY_ACCT_NO = t2.PRIMARY_ACCT_NO "
					+ "and t1.PRODUCT = t2.PRODUCT and t2.no_of_records > 1 and t1.STATUS = "+status+"and t1.CREDIT_CARD_DETAILS_ID = t2.id";
			while(rowCount>0)
			{
				//System.out.println("duplicate cheking");
				count =  getSessionFactory().getCurrentSession().createSQLQuery(query).executeUpdate();
				rowCount = ((Integer)getSessionFactory().getCurrentSession().createSQLQuery(queryForCount).uniqueResult()).intValue();
				
			}
		/*	cr no 39 changes ends*/
		} catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return count;
	}

	public int insertTxnInfo(Long fileId) {
		int count = 0;
		try {
			String qry = "insert into txn_record_event_log(record_id, event_id, event_date, info) "
					+ "select CREDIT_CARD_DETAILS_ID,STATUS,getdate(), RULE_STATUS from CUSTOMER_RECORDS_T where FILE_ID ="+fileId;
		
			count =  getSessionFactory().getCurrentSession().createSQLQuery(qry).executeUpdate();
			
		
		} catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return count;
	}


	public int insertLCPCBranch(Long fileId) {
		int count = 0;
		try {
			String qry = "update records  set records.LCPC_BRANCH = branch.LCPC_NAME from master_branch_t branch, "
					+ "CUSTOMER_RECORDS_T records where branch.short_code = records.PROCESSED_BRANCH_CODE and branch.bank_id = records.BANK_ID "
					+ "and records.STATUS = "+(long) CardStateManagerService.CARD_STATUS_APPROVED+" and records.FILE_ID ="+fileId ;

//			logger.info(qry);
		count =  getSessionFactory().getCurrentSession().createSQLQuery(qry).executeUpdate();
		} catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return count;
		
	}


	public int updateRecordsForBranchGroupForExceedRule(Long fileId,
			long cardStatusApproved, Long branchLimit) {
		int count = 0;
		try {
			String qry = "update t1 set t1.STATUS = "+(long) CardStateManagerService.CARD_STATUS_REJECT+", t1.RULE_STATUS=t2.PROCESSED_BRANCH_CODE+' Branch having more than "+branchLimit +" records', "
					+ "t1.FLOW_STATUS='RECORD_ERROR', t1.CREATED_DATE = GETDATE() from  CUSTOMER_RECORDS_T t1, "
					+ "(select PROCESSED_BRANCH_CODE, COUNT(*) as records_count from CUSTOMER_RECORDS_T where STATUS = "+ (long) CardStateManagerService.CARD_STATUS_APPROVED
					+ " and FILE_ID = "+fileId+" group by PROCESSED_BRANCH_CODE ) as t2 where "
					+ " t1.PROCESSED_BRANCH_CODE = t2.PROCESSED_BRANCH_CODE and t2.records_count > "+branchLimit
					+ " and t1.FILE_ID = "+fileId+" and t1.STATUS = "+(long) CardStateManagerService.CARD_STATUS_APPROVED ;

//			logger.info(qry);
		count =  getSessionFactory().getCurrentSession().createSQLQuery(qry).executeUpdate();
		} catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return count;
	}


	public int updateRecordForAWBNotAvailable(Long fileId,
			long requiredHoldStatus, long currentApprovedStatus, String flowHold,
			String ruleCardAwbAssignedError) {
		int count = 0;
		try {
			String qry = "update CUSTOMER_RECORDS_T set STATUS = "+requiredHoldStatus+", RULE_STATUS='"+ruleCardAwbAssignedError+"',"
					+ " FLOW_STATUS='"+flowHold+"', CREATED_DATE = GETDATE() "
					+ " where FILE_ID = "+fileId+" and STATUS = "+currentApprovedStatus;

//			logger.info(qry);
		count =  getSessionFactory().getCurrentSession().createSQLQuery(qry).executeUpdate();
		} catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return count;
		
	}


	public int insertTXNLogForAWB(Long fileId, String ruleCardAwbAssignedError) {
		int count = 0;
		try {
			String qry = "insert into txn_record_event_log(record_id, event_id, event_date, info) "
					+ " select CREDIT_CARD_DETAILS_ID, STATUS, GETDATE(), 'CARD_'+RULE_STATUS from CUSTOMER_RECORDS_T where (FILE_ID = "+fileId+" and STATUS = "+(long) CardStateManagerService.CARD_STATUS_AWBASSIGNED+") or ( FILE_ID = "+fileId+" and STATUS = "+(long) CardStateManagerService.CARD_STATUS_HOLD+" and RULE_STATUS = '"+ruleCardAwbAssignedError+"')";
//			logger.info(qry);
		count =  getSessionFactory().getCurrentSession().createSQLQuery(qry).executeUpdate();
		getSessionFactory().getCurrentSession().flush();
		
		} catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return count;
		
	}
	
	public int insertTXNLogForAWBNcf(Long fileId) {
		int count = 0;
		try {
			String qry = "insert into txn_record_event_log(record_id, event_id, event_date, info) "
					+ " select CREDIT_CARD_DETAILS_ID, STATUS, GETDATE(), 'CARD_'+RULE_STATUS from CUSTOMER_RECORDS_T where (FILE_ID = "+fileId+" and STATUS = "+(long) CardStateManagerService.CARD_STATUS_AWBASSIGNED + ")";
//			logger.info(qry);
		count =  getSessionFactory().getCurrentSession().createSQLQuery(qry).executeUpdate();
		getSessionFactory().getCurrentSession().flush();
		
		} catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return count;
		
	}


	public int saveCardDetailsForAUF(Long aufId, String branchCode, Long fileId, Long status, String ruleStatus) {
		int count = 0;
		try {
			String qry = "update CUSTOMER_RECORDS_T set AUF_ID = "+aufId +", STATUS = "+status+", RULE_STATUS='"+ruleStatus
					+"', CREATED_DATE = GETDATE() "
					+ " where FILE_ID = "+fileId+" and PROCESSED_BRANCH_CODE = '"+branchCode+"' and STATUS = "+(long) CardStateManagerService.CARD_STATUS_AWBASSIGNED;

//			logger.info(qry);
		count =  getSessionFactory().getCurrentSession().createSQLQuery(qry).executeUpdate();
		} catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return count;
		
	}


	public int saveTxnEventForAUF(Long fileId, long cardStatusAufconverted) {
		int count = 0;
		try {
			String qry = "insert into txn_record_event_log(record_id, event_id, event_date, info) "
					+ " select CREDIT_CARD_DETAILS_ID, STATUS, GETDATE(), RULE_STATUS from CUSTOMER_RECORDS_T where FILE_ID = "+fileId+" and STATUS = "+cardStatusAufconverted;
//			logger.info(qry);
		count =  getSessionFactory().getCurrentSession().createSQLQuery(qry).executeUpdate();
		
		} catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return count;
		
	}


	public int updateCompositeRecordsForNCF(Long fileId) {
		int count = 0;
		int rowCount = 0;
		try {
			Long status = (long) CardStateManagerService.CARD_STATUS_APPROVED;
			/*cr no 39 changes starts*/
			String queryForCount =  "select count(*) from CUSTOMER_RECORDS_T t1,"
					+ "(select  PRIMARY_ACCT_NO, PRODUCT, COUNT(*) as no_of_records from CUSTOMER_RECORDS_T "
					+ "where STATUS >= "+status+" and CONVERT(date, CREATED_DATE) > GETDATE()-15 group by  PRIMARY_ACCT_NO, "
					+ "PRODUCT ) as t2 where t1.FILE_ID = "+fileId+"  and t1.PRIMARY_ACCT_NO = t2.PRIMARY_ACCT_NO "
					+ "and t1.PRODUCT = t2.PRODUCT and t2.no_of_records > 1 and t1.STATUS = "+status;
			rowCount = ((Integer)getSessionFactory().getCurrentSession().createSQLQuery(queryForCount).uniqueResult()).intValue();
			String qry = "update t1 set t1.status = "+(long) CardStateManagerService.CARD_STATUS_REJECT
					+", t1.FLOW_STATUS = '"+DroolRecordBO.FLOW_ERROR+"', "
					+ "t1.RULE_STATUS = '"+DroolRecordBO.COMPOSITE_KEY_ERROR+" '+t2.CARD_AWB, t1.CREATED_DATE = GETDATE() "
					+ "from CUSTOMER_RECORDS_T t1, CUSTOMER_RECORDS_T t2 where  "
					+ " t1.PRIMARY_ACCT_NO = t2.PRIMARY_ACCT_NO and t1.PRODUCT = t2.PRODUCT and t1.FILE_ID = "+fileId+" and t2.FILE_ID <> "+fileId
					+ "and t1.STATUS = "+(long) CardStateManagerService.CARD_STATUS_APPROVED+ "and t2.STATUS >= "+(long) CardStateManagerService.CARD_STATUS_APPROVED
					+" and CONVERT(date, t2.CREATED_DATE) > GETDATE()-15";
			String query = "update t1 set t1.STATUS = "+(long) CardStateManagerService.CARD_STATUS_REJECT+","
					+ " t1.rule_status = '"+DroolRecordBO.COMPOSITE_KEY_ERROR+"', t1.FLOW_STATUS = '"+DroolRecordBO.FLOW_ERROR+"',"
					+ " t1.CREATED_DATE = GETDATE() from CUSTOMER_RECORDS_T t1,"
					+ "(select max(CREDIT_CARD_DETAILS_ID) id, PRIMARY_ACCT_NO, PRODUCT, COUNT(*) as no_of_records from CUSTOMER_RECORDS_T "
					+ "where STATUS >= "+status+" and CONVERT(date, CREATED_DATE) > GETDATE()-15 group by PRIMARY_ACCT_NO, "
					+ "PRODUCT ) as t2 where t1.FILE_ID = "+fileId+"  and t1.PRIMARY_ACCT_NO = t2.PRIMARY_ACCT_NO "
					+ "and t1.PRODUCT = t2.PRODUCT and t2.no_of_records > 1 and t1.STATUS = "+status+"and t1.CREDIT_CARD_DETAILS_ID = t2.id";
			
			while(rowCount>0)
			{
			//	System.out.println("duplicate cheking");
				count =  getSessionFactory().getCurrentSession().createSQLQuery(query).executeUpdate();
				rowCount = ((Integer)getSessionFactory().getCurrentSession().createSQLQuery(queryForCount).uniqueResult()).intValue();
				
			}
			
			/*cr no 39 changes ends*/
		} catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return count;
	}


	public int updateRecordsToAUFState(Long fileId,
			long aufStatus, String info) {
		int count = 0;
		try {
			String query = "update t1 set t1.status = "+aufStatus+", t1.RULE_STATUS = '"+info+"', t1.AUF_ID =t2.id, t1.CREATED_DATE = GETDATE() "
					+ "from CUSTOMER_RECORDS_T t1, MASTER_CORE_FILES t2 where "
					+ "t1.PROCESSED_BRANCH_CODE = substring(t2.FILENAME, 9,5) and "
					+ "t1.FILE_ID = t2.CORE_FILE_ID and t1.FILE_ID = "+fileId+" and t1.STATUS = "+CardStateManagerService.CARD_STATUS_AWBASSIGNED+" and t2.FILE_TYPE = 2";
		count =  getSessionFactory().getCurrentSession().createSQLQuery(query).executeUpdate();
		getSessionFactory().getCurrentSession().flush();
		} catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return count;
		
	}
	
	public Long getCreditCardDetailIdSeq() {
		
		 final List<String> query = new ArrayList<String>();
		try {
			 getSessionFactory().getCurrentSession().doWork(new Work() {
				
				@Override
				public void execute(Connection arg0) throws SQLException {
					
					PreparedStatement pstmt = arg0.prepareStatement("select next value for CUSTOMER_RECORDS_T_ID_SEQ as seq");
					ResultSet rs= pstmt.executeQuery();
					if(rs.next())
					{
						query.add(rs.getString(1));
					}	
					rs.close();
					pstmt.close();
					
				}
			});
			
			 return Long.parseLong(query.get(0));
			
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return 1l;
	}

}