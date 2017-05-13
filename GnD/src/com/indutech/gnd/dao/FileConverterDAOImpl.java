package com.indutech.gnd.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.indutech.gnd.dto.Bank;

import org.hibernate.Query;

import com.indutech.gnd.dto.CreditCardDetails;
import com.indutech.gnd.enumTypes.RecordStatus;
import com.indutech.gnd.service.CardStateManagerService;
import com.indutech.gnd.service.FileStateManager;

public class FileConverterDAOImpl implements FileConvertDAO {
	
	Logger logger = Logger.getLogger(FileConverterDAOImpl.class);
	common.Logger log = common.Logger.getLogger(FileConverterDAOImpl.class);
	
	
	@Autowired
	private SessionFactory sessionFactory;

	RecordStatus stat1 = RecordStatus.valueOf("AWB_ASSIGNED");
	String status = stat1.getRecordStatus();
	RecordStatus stat2 = RecordStatus.valueOf("AUF_CONVERTED");
	String convertedStatus = stat2.getRecordStatus();
	RecordStatus stat3 = RecordStatus.valueOf("REJECT");
	String rstatus = stat3.getRecordStatus();
	RecordStatus stat4 = RecordStatus.valueOf("HOLD");
	String hstatus = stat4.getRecordStatus();
	RecordStatus stat5 = RecordStatus.valueOf("APPROVED");
	String approvedStatus = stat5.getRecordStatus();

	@SuppressWarnings("unchecked")
	@Override
	public List<CreditCardDetails> getCreditCardDetails(Long recordStatus, Long fileId) {

		@SuppressWarnings("rawtypes")
		List list = getSessionFactory().getCurrentSession().createQuery("select c.institutionId, c.processedBranchCode, c.product, c.fileId, c.status from CreditCardDetails c group by c.institutionId, c.processedBranchCode, c.product, c.fileId, c.status having status ="+recordStatus +" and fileId ="+fileId).list();		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CreditCardDetails> getApprovedCreditCardDetails() {

		@SuppressWarnings("rawtypes")
		List list = getSessionFactory().getCurrentSession().createQuery("select c.institutionId, c.homeBranchCode, c.product, c.status from CreditCardDetails c group by c.institutionId, c.homeBranchCode, c.product, c.status having status ="+Long.parseLong(convertedStatus)).list();		
		return list;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CreditCardDetails> getRejectedCreditCardDetails() {

		@SuppressWarnings("rawtypes")
		List list = getSessionFactory().getCurrentSession().createQuery("select c.institutionId, c.homeBranchCode, c.product, c.status from CreditCardDetails c group by c.institutionId, c.homeBranchCode, c.product, c.status having status ="+Long.parseLong(rstatus)).list();		
		return list;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CreditCardDetails> getHoldCreditCardDetails() {

		@SuppressWarnings("rawtypes")
		List list = getSessionFactory().getCurrentSession().createQuery("select c.institutionId, c.homeBranchCode, c.product, c.status from CreditCardDetails c group by c.institutionId, c.homeBranchCode, c.product, c.status having status ="+Long.parseLong(hstatus)).list();		
		return list;
	}
	@Override
	public String getPhotoIndicator(String product) {

		@SuppressWarnings("rawtypes")
		List list = getSessionFactory().getCurrentSession().createQuery("select p.photoCard from Product p where p.shortCode ='"+product+"'").list();		
		return list.get(0).toString();
	}
	

	@Override
	public String getAwbId(Long creditCardDetailsId) {

		@SuppressWarnings("rawtypes")
		List list = getSessionFactory().getCurrentSession().createQuery("select m.awbName from MasterAWB m,CustomerHistory c where c.cardAWB=m.awbId   and c.creditCardDetailsId ="+creditCardDetailsId).list();		
		return list != null && list.size() >0 ? list.get(0).toString() : "";
	}

	@Override
	public List<CreditCardDetails> getGroup(String instituteId, String branchCode, String productCode, Long fileid) {
			
			
			@SuppressWarnings("unchecked")
			List<CreditCardDetails> list = getSessionFactory().getCurrentSession().createQuery("from CreditCardDetails c where c.institutionId ='"+instituteId+"' and c.processedBranchCode='"+branchCode+"' and c.product='"+productCode+"' and c.fileId ="+fileid+" and c.status="+Long.parseLong(status)).list();
			return list;
	}
	@Override
	public List<CreditCardDetails> getAppGroup(String instituteId, String branchCode, String productCode) {
			
			
			@SuppressWarnings("unchecked")
			List<CreditCardDetails> list = getSessionFactory().getCurrentSession().createQuery("from CreditCardDetails c where c.institutionId ='"+instituteId+"' and c.homeBranchCode='"+branchCode+"' and c.product='"+productCode+"' and c.status="+Long.parseLong(convertedStatus)).list();
			return list;
	}
	
	@Override
	public List<CreditCardDetails> getRejGroup(String instituteId, String branchCode, String productCode) {
			
			
			@SuppressWarnings("unchecked")
			List<CreditCardDetails> list = getSessionFactory().getCurrentSession().createQuery("from CreditCardDetails c where c.institutionId ='"+instituteId+"' and c.homeBranchCode='"+branchCode+"' and c.product='"+productCode+"' and c.status="+Long.parseLong(rstatus)).list();
			return list;
	}
	
	@Override
	public List<CreditCardDetails> getHoldGroup(String instituteId, String branchCode, String productCode) {
			
			
			@SuppressWarnings("unchecked")
			List<CreditCardDetails> list = getSessionFactory().getCurrentSession().createQuery("from CreditCardDetails c where c.institutionId ='"+instituteId+"' and c.homeBranchCode='"+branchCode+"' and c.product='"+productCode+"' and c.status="+Long.parseLong(hstatus)).list();
			return list;
	}
	
	@Override
	public void changeStatusToAUFConverted(Long creditCardDetailsId) {
		
		
		getSessionFactory().getCurrentSession().createQuery("update CreditCardDetails c set c.status =? where c.creditCardDetailsId =?").setParameter(0, Long.parseLong(convertedStatus)).setParameter(1, creditCardDetailsId).executeUpdate();
	}
	public final SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public final void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public long getRecordCount(String homeBranchCode) {
		long count =0;
		try {
		count = (long) getSessionFactory().getCurrentSession()//.createCriteria(CreditCardDetails.class).setProjection(Projections.rowCount()).add(Restrictions.eq("isIndividual",FileStateManager.CORE_NCF_INDIVIDUALITY)).add(Restrictions.eq("status", (long)CardStateManagerService.CARD_STATUS_APPROVED)).list().get(0);
		.createQuery("select count(*) from CreditCardDetails where homeBranchCode ='"+homeBranchCode+"' and status = "+(long)CardStateManagerService.CARD_STATUS_APPROVED+" and isIndividual="+FileStateManager.CORE_NCF_INDIVIDUALITY).list().get(0);
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List<?> getBranchGroup(Long status, Long fileId) {
		List<?> list = null;
		try {
				Query query = getSessionFactory().getCurrentSession().createQuery("select processedBranchCode, status, bankId, fileId from CreditCardDetails group by processedBranchCode, status, bankId, fileId having status="+status+" and fileId="+fileId+" order by processedBranchCode, bankId");
					list = query.list();
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
			return list;
	}
	
	@Override
	public List<?> getLCPCBranchGroup(Long status, Long fileId) {
		List<?> list = null;
		try {
				Query query = getSessionFactory().getCurrentSession().createQuery("select lcpcBranch, status, bankId, fileId from CreditCardDetails group by lcpcBranch, status, bankId, fileId having status="+status+" and fileId="+fileId+" order by lcpcBranch, bankId");
					list = query.list();
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
			return list;
	}

	@Override
	public Bank getBankCodeByPrefix(String prefix) {
		@SuppressWarnings("unchecked")
		List<Bank>	bank = (List<Bank>) getSessionFactory().getCurrentSession().createCriteria(Bank.class).add(Restrictions.eq("prefix", prefix)).list();
//				createQuery("from Bank bank where bank.bankId in (select branch.bankId from Branch branch where branch.shortCode='"+homeBranchCode+"')").list(); 
		return bank != null && bank.size()>0? bank.get(0):null;
	}

	public Long getSeqenceNumber(String filename, Long fileId) {
		String sequence = null;
		try {
			logger.info("file name is : "+filename);
			sequence = (String) getSessionFactory().getCurrentSession().createSQLQuery("select max(substring(FILENAME,35,3)) as seqval from master_core_files where FILENAME like '"+filename+"%' and core_file_Id ="+fileId+" order by seqval").list().get(0);
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return sequence !=null ? Long.parseLong(sequence)+1 : 001;
	}
	
	
	
	
	

}
