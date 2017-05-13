package com.indutech.gnd.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.camel.component.dataset.SimpleDataSet;
import org.apache.log4j.Logger;
import org.drools.command.GetSessionClockCommand;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.indutech.gnd.dto.CreditCardDetails;
import com.indutech.gnd.dto.MasterBank;
import com.indutech.gnd.dto.RecordEvent;
import com.indutech.gnd.enumTypes.FileStatus;
import com.indutech.gnd.enumTypes.FileType;
import com.indutech.gnd.service.CardStateManagerService;
import com.indutech.gnd.service.FileStateManager;

@Repository("QcProcessDAO")
public class QcProcessDAO {
	
	Logger logger = Logger.getLogger(QcProcessDAO.class);
	
	public static final String EMAIL_CONFORMATION="EMAIL SENT TO BANK ";
	public static final Long EVENT_ID= 90L;
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<MasterBank> getBankDao() {
		List<MasterBank> masterbank=(List<MasterBank>)getSessionFactory().getCurrentSession().createCriteria(MasterBank.class).addOrder(Order.desc("shortCode")).list();
	return masterbank;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getListFileSummarydao(Long bankidl, String dateStr) {
		return getSessionFactory().getCurrentSession().createSQLQuery("select M.FILENAME as core_file_name,M.RECEIVED_DATE as QC_Output_Date,M.LCPC_GROUP as LinkIndicator,C.PRODUCT as filename,count(*) as Record_Counts from MASTER_CORE_FILES M ,CUSTOMER_RECORDS_T C where C.FILE_ID  = M.ID AND m.FILE_TYPE= 1 and c.status =5 and C.BANK_ID='"+bankidl+"' and convert(date, m.received_date) = '"+dateStr+"'  GROUP BY M.FILENAME, M.RECEIVED_DATE, M.LCPC_GROUP, M.ID, C.PRODUCT order by M.FILENAME, M.RECEIVED_DATE").list();
	}

	@SuppressWarnings("unchecked")
	public List<RecordEvent> getAddEvent(String bankVal) {
		RecordEvent event=new RecordEvent();
		event.setDescription(EMAIL_CONFORMATION+bankVal.trim());
		event.setEventId(EVENT_ID);
		event.setEventDate(new Date());
		getSessionFactory().getCurrentSession().save(event);
		List<RecordEvent> recordEvent=getSessionFactory().getCurrentSession().createCriteria(RecordEvent.class).add(Restrictions.eq("eventId", EVENT_ID)).addOrder(Order.desc("eventDate")).setMaxResults(20).list();
		return recordEvent;
	}
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getNoAufList(Long bankid, String dateStr) {
		
		List<Object[]> list = (List<Object[]>) getSessionFactory().getCurrentSession().createSQLQuery("select M.FILENAME as core_file_name, M.RECEIVED_DATE as QC_Output_Date, M.LCPC_GROUP as LinkIndicator from MASTER_CORE_FILES M where file_type = "+Long.parseLong(FileType.valueOf("CORE").getFileType())+" and substring(filename,1,1) = (select prefix from master_bank_t where bank_id ="+bankid+") and id not in (select distinct CORE_FILE_ID from MASTER_CORE_FILES where file_type = "+Long.parseLong(FileType.valueOf("AUF").getFileType())+") and convert(date, received_date)='"+dateStr+"'").list();
		
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<CreditCardDetails> getDetailsForPinAWB(Long pinstatus, long bankId,	Date qcdate) {
		List<CreditCardDetails> list = null;
		try {
			list = (List<CreditCardDetails>) getSessionFactory().getCurrentSession().createSQLQuery("select * from CUSTOMER_RECORDS_T where FILE_ID in (select ID from MASTER_CORE_FILES where FILE_TYPE = "+FileType.valueOf("CORE").getFileType()+" and CONVERT(date, RECEIVED_DATE) = '"+ new SimpleDateFormat("yyyy-MM-dd").format(qcdate)+"' and SUBSTRING(filename,1,1)=(select prefix from master_bank_t where bank_id ="+bankId+" and AUF_FORMAT = "+FileStateManager.AUF_FORMAT_2+") and LCPC_GROUP not IN ('"+FileStateManager.AUF2_NCF_FILE_TYPE+"','"+FileStateManager.NCF_FILE_TYPE+"')) and STATUS >= "+ CardStateManagerService.CARD_STATUS_AUFCONVERTED+" and PIN_STATUS = "+pinstatus+" and bank_id = "+bankId).addEntity(CreditCardDetails.class).list();
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<CreditCardDetails> getNcfDetailsForPinAWB(Long pinstatus, long bankId,	Date qcdate) {
		List<CreditCardDetails> list = null;
		try {
			list = (List<CreditCardDetails>) getSessionFactory().getCurrentSession().createSQLQuery("select * from CUSTOMER_RECORDS_T where FILE_ID in (select ID from MASTER_CORE_FILES where FILE_TYPE = "+FileType.valueOf("CORE").getFileType()+" and CONVERT(date, RECEIVED_DATE) = '"+ new SimpleDateFormat("yyyy-MM-dd").format(qcdate)+"' and SUBSTRING(filename,1,1)=(select prefix from master_bank_t where bank_id ="+bankId+" and AUF_FORMAT = "+FileStateManager.AUF_FORMAT_2+") and LCPC_GROUP IN ('"+FileStateManager.AUF2_NCF_FILE_TYPE+"','"+FileStateManager.NCF_FILE_TYPE+"')) and STATUS >= "+ CardStateManagerService.CARD_STATUS_AUFCONVERTED+" and PIN_STATUS = "+pinstatus+" and bank_id = "+bankId).addEntity(CreditCardDetails.class).list();
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}

	public void updatemailer(long mailerno, Long bankId,
			String processedBranchCode) {
			getSessionFactory().getCurrentSession().createQuery("update Branch set pinCoveringLetterCount="+mailerno+" where shortCode = '"+processedBranchCode+"' and bankId ="+bankId).executeUpdate();
	}

	public List<Object[]> getNoAufListForPinPrint(Long bankid, String dateStr) {
		return getSessionFactory().getCurrentSession().createSQLQuery("select M.FILENAME as core_file_name,M.RECEIVED_DATE as QC_Output_Date,M.LCPC_GROUP as LinkIndicator,count(distinct C.AUF_ID) as file_count,count(*) as Record_Counts from MASTER_CORE_FILES M ,CUSTOMER_RECORDS_T C where C.FILE_ID  = M.ID AND m.FILE_TYPE= 1 and c.status =5 and C.BANK_ID='"+bankid+"' and convert(date, m.received_date) = '"+dateStr+"'  GROUP BY M.FILENAME, M.RECEIVED_DATE, M.LCPC_GROUP, M.ID order by M.FILENAME, M.RECEIVED_DATE").list();
		
	}

}
