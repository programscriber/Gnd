package com.indutech.gnd.dao;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;



import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.indutech.gnd.dto.Bank;
import com.indutech.gnd.dto.CreditCardDetails;
import com.indutech.gnd.dto.MasterAWB;
import com.indutech.gnd.dto.MasterBank;
import com.indutech.gnd.dto.RecordEvent;
import com.indutech.gnd.util.StringUtil;
//SbisearchServiceImpl
public class SbisearchDAOImpl {
	
	Logger logger = Logger.getLogger(SbisearchDAOImpl.class);
	common.Logger log = common.Logger.getLogger(SbisearchDAOImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	private static final Integer PAGE_MAX_SIZE = 100;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public List<CreditCardDetails> cardawbdao(String cardawb, Integer offset)
			throws Exception {

		List<CreditCardDetails> list = null;
		Criteria criteria = getSessionFactory().getCurrentSession()
				.createCriteria(CreditCardDetails.class)
				.addOrder(Order.desc("createdDate"));
		criteria.setFirstResult(offset != null ? offset : 0);
		criteria.setMaxResults(PAGE_MAX_SIZE);
		//if (!StringUtil.isEMptyOrNull(cardawb)) {
			criteria.add(Restrictions.eq("cardAWB", cardawb));
		//}
		list = criteria.list();

		//logger.info("the quet list"+list+"card awb"+cardawb);

		return list;
	}

	public int cardabdcountdao(String cardawb) throws Exception {
		Criteria criteria = getSessionFactory().getCurrentSession()
				.createCriteria(CreditCardDetails.class)
				.addOrder(Order.desc("createdDate"));
		if (!StringUtil.isEMptyOrNull(cardawb)) {
			criteria.add(Restrictions.eq("cardAWB",cardawb));
		}
		return criteria.list().size();
	}

	public List<CreditCardDetails> pinawbdao(String pinawb, Integer offset)
			throws Exception {
		List<CreditCardDetails> list = null;
		Criteria criteria = getSessionFactory().getCurrentSession()
				.createCriteria(CreditCardDetails.class)
				.addOrder(Order.desc("createdDate"));
		criteria.setFirstResult(offset != null ? offset : 0);
		criteria.setMaxResults(PAGE_MAX_SIZE);
		if (!StringUtil.isEMptyOrNull(pinawb)) {
			criteria.add(Restrictions.eq("pinAWB", pinawb));
		}
		list = criteria.list();
		return list;
	}

	public int pinawbcountdao(String pinawb) throws Exception {
		Criteria criteria = getSessionFactory().getCurrentSession()
				.createCriteria(CreditCardDetails.class)
				.addOrder(Order.desc("createdDate"));
		if (!StringUtil.isEMptyOrNull(pinawb)) {
			criteria.add(Restrictions.eq("pinAWB", pinawb));
		}
		return criteria.list().size();
	}

	public List<CreditCardDetails> mobiledao(String mobile, Integer offset)
			throws Exception {
//		logger.info("i am in mobile number DAO");
//		log.info("i am in mobile number DAO");
		List<CreditCardDetails> list = null;
		Criteria criteria = getSessionFactory().getCurrentSession()
				.createCriteria(CreditCardDetails.class)
				.addOrder(Order.desc("createdDate"));
		criteria.setFirstResult(offset != null ? offset : 0);
		criteria.setMaxResults(PAGE_MAX_SIZE);
		if (!StringUtil.isEMptyOrNull(mobile)) {
			criteria.add(Restrictions.like("mobileNo", "%" + mobile + "%"));
		}
		list = criteria.list();
//		logger.info("i am out mobile number DAO");
//		log.info("i am out mobile number DAO");
		return list;
	}

	public int mobileCountdao(String mobile) throws Exception {
		Criteria criteria = getSessionFactory().getCurrentSession()
				.createCriteria(CreditCardDetails.class)
				.addOrder(Order.desc("createdDate"));
		criteria.add(Restrictions.like("mobileNo", "%" + mobile + "%"));
		int x = criteria.list().size();
		return x;
	}

	public List<CreditCardDetails> rsndao(String rsn, Integer offset)
			throws Exception {
		List<CreditCardDetails> list = null;
		Criteria criteria = getSessionFactory().getCurrentSession()
				.createCriteria(CreditCardDetails.class)
				.addOrder(Order.desc("createdDate"));
		criteria.setFirstResult(offset != null ? offset : 0);
		criteria.setMaxResults(PAGE_MAX_SIZE);
		if (!StringUtil.isEMptyOrNull(rsn)) {
			criteria.add(Restrictions.eq("rsn", Long.parseLong(rsn)));
		}
		list = criteria.list();
		return list;
	}
	
	
	public List<CreditCardDetails> getCustId(String customerId, Integer offset)
			throws Exception {
		List<CreditCardDetails> list = null;
		Criteria criteria = getSessionFactory().getCurrentSession()
				.createCriteria(CreditCardDetails.class)
				.addOrder(Order.desc("createdDate"));
		criteria.setFirstResult(offset != null ? offset : 0);
		criteria.setMaxResults(PAGE_MAX_SIZE);
		if (!StringUtil.isEMptyOrNull(customerId)) {
			criteria.add(Restrictions.like("customerId", "%"+customerId+"%"));
		}
		list = criteria.list();
		return list;
	}

	public int rsndaoCount(String rsn) throws Exception {
		Criteria criteria = getSessionFactory().getCurrentSession()
				.createCriteria(CreditCardDetails.class)
				.addOrder(Order.desc("createdDate"));
		if (!StringUtil.isEMptyOrNull(rsn)) {
			criteria.add(Restrictions.eq("rsn", Long.parseLong(rsn)));
		}
		return criteria.list().size();
	}
	
	
	public int custIddaoCount(String customerId) throws Exception {
		Criteria criteria = getSessionFactory().getCurrentSession()
				.createCriteria(CreditCardDetails.class)
				.addOrder(Order.desc("createdDate"));
		if (!StringUtil.isEMptyOrNull(customerId)) {
			criteria.add(Restrictions.like("customerId", "%"+customerId+"%"));
		}
		return criteria.list().size();
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> searchbybankDao(String accNo, Integer offset)
			throws Exception {
	//	logger.info("in dao");
		
		List<Object[]> list = null;
		try {
			Query hql = getSessionFactory()
					.getCurrentSession()
					.createQuery(
							"select customer.creditCardDetailsId,customer.primaryAcctNo,customer.embossName,customer.homeBranchCode,customer.product,customer.status,customer.status,customer.pinstatus,customer.customerId from MasterBank bank,Branch branch,CreditCardDetails customer where bank.bankId=branch.bankId and branch.shortCode=customer.homeBranchCode and customer.primaryAcctNo='"
									+ accNo.trim() + "'");
			hql.setFirstResult(offset != null ? offset : 0);
			hql.setMaxResults(PAGE_MAX_SIZE);
			list = hql.list();

			//logger.info("the list0" + list);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public int searchbybankCountDao(String accNo) throws Exception {
		//logger.info("in dao");
		List<Object[]> list = null;
		try {
			Query hql = getSessionFactory()
					.getCurrentSession()
					.createQuery(
							"select customer.creditCardDetailsId,customer.primaryAcctNo,customer.embossName,customer.homeBranchCode,customer.product,customer.status,customer.status,customer.pinstatus,customer.customerId from MasterBank bank,Branch branch,CreditCardDetails customer where bank.bankId=branch.bankId and branch.shortCode=customer.homeBranchCode and customer.primaryAcctNo='"
									+ accNo.trim() + "'");
			list = hql.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list.size();
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> searchbybankaccDao(String bank, String accNo,
			Integer offset) throws Exception {

		log.info("in dao");


		List<Object[]> list = null;
		try {
			Query hql = getSessionFactory()
					.getCurrentSession()
					.createSQLQuery(
		/*CR no 40*/					"select customer.CREDIT_CARD_DETAILS_ID, customer.PRIMARY_ACCT_NO,customer.EMBOSS_NAME,customer.HOME_BRANCH_CODE,customer.PRODUCT,customer.STATUS,customer.STATUS,customer.PIN_STATUS,customer.CUSTOMER_ID,customer.rsn, branch.IS_NON_CARD_ISSUE_BRANCH from master_bank_t bank, master_branch_t branch,CUSTOMER_RECORDS_T customer where bank.bank_id=branch.bank_id and  branch.short_code=customer.HOME_BRANCH_CODE and bank.bank_id="
									+ bank
									+ " and customer.PRIMARY_ACCT_NO like '%"
									+ accNo.trim()
									+ "%' ORDER BY customer.CREATED_DATE desc");
			hql.setFirstResult(offset != null ? offset : 0);
			hql.setMaxResults(PAGE_MAX_SIZE);
			list = hql.list();

			log.info("the list0" + list);


		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public int searchbybankaccCountDao(String bank, String accNo)
			throws Exception {

		//logger.info("in dao");

		List<Object[]> list = null;
		try {
			Query hql = getSessionFactory()
					.getCurrentSession()
					.createSQLQuery(
/*cr no 40*/							"select customer.CREDIT_CARD_DETAILS_ID, customer.PRIMARY_ACCT_NO,customer.EMBOSS_NAME,customer.HOME_BRANCH_CODE,customer.PRODUCT,customer.STATUS,customer.STATUS,customer.PIN_STATUS,customer.CUSTOMER_ID,customer.rsn, branch.IS_NON_CARD_ISSUE_BRANCH  from master_bank_t bank, master_branch_t branch,his.HISTORY_CUSTOMER_RECORDS_T customer where bank.bank_id=branch.bank_id and  branch.short_code=customer.HOME_BRANCH_CODE and bank.bank_id='"
									+ bank
									+ "' and customer.PRIMARY_ACCT_NO like '%"
									+ accNo.trim()
									+ "%' ORDER BY customer.CREATED_DATE desc");

			list = hql.list();


		} catch (Exception e) {
			e.printStackTrace();
		}
		//logger.info("in dao out");
		return list.size();
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> searchbybankaccDao(String bank, Integer offset)
			throws Exception {

		//logger.info("in dao");

		List<Object[]> list = null;
		try {
			Query hql = getSessionFactory()
					.getCurrentSession()
					.createQuery(
							"select customer.creditCardDetailsId,customer.primaryAcctNo,customer.embossName,customer.homeBranchCode,customer.product,customer.status,customer.status,customer.pinstatus,customer.customerId from MasterBank bank,Branch branch,CreditCardDetails customer where bank.bankId=branch.bankId and  branch.shortCode=customer.homeBranchCode and bank.bankId='"
									+ bank
									+ "' and customer.primaryAcctNo='"
									+ "' ORDER BY customer.createdDate desc");
			hql.setFirstResult(offset != null ? offset : 0);
			hql.setMaxResults(PAGE_MAX_SIZE);
			list = hql.list();


			//log.info("the list0" + list);


		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public int searchbybankCountDaoa(String bank) throws Exception {
		List<Object[]> list = null;
		try {
			Query hql = getSessionFactory()
					.getCurrentSession()
					.createQuery(
							"select customer.creditCardDetailsId,customer.primaryAcctNo,customer.embossName,customer.homeBranchCode,customer.product,customer.status,customer.status,customer.pinstatus,customer.customerId from MasterBank bank,Branch branch,CreditCardDetails customer where bank.bankId=branch.bankId and  branch.shortCode=customer.homeBranchCode and bank.bankId='"
									+ bank
									+ "' ORDER BY customer.createdDate desc");

			list = hql.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list.size();
	}

	public void branchwiseDaoNull(String branch, String dateFrom)
			throws Exception {
		getSessionFactory()
				.getCurrentSession()
				.createQuery(
						"select customer.creditCardDetailsId,customer.primaryAcctNo,customer.embossName,customer.homeBranchCode,customer.product,customer.status,customer.status,customer.pinstatus,customer.customerId from MasterBank bank,Branch branch,CreditCardDetails customer where bank.bankId=branch.bankId and branch.shortCode=customer.homeBranchCode");
	}

	public List<Object[]> branchwiseDao(String bank, String branch, String branchType,
			String dateFrom, Integer offset) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfc = new SimpleDateFormat("MM/dd/yyyy");
		
		List<Object[]> obj = null;
		try {
			Date dd = sdfc.parse(dateFrom);


			Date added=DateUtils.addDays(dd, 1);
			//System.out.println("date " + dd +" the value is "+added);
			String dateString = sdf.format(added);
			//System.out.println(dateString);

			log.info("date " + dd);
			


			// Query
			// hel=getSessionFactory().getCurrentSession().createQuery("select customer.creditCardDetailsId,customer.primaryAcctNo,customer.embossName,customer.homeBranchCode,customer.product,customer.status,customer.status,customer.pinstatus,customer.customerId "
			// +
			// "from MasterBank bank,Branch branch,CreditCardDetails customer where bank.bankId=branch.bankId and branch.shortCode=customer.homeBranchCode "
			// +
			// "and cast(customer.createdDate as DAte)='"+dateString+"' and bank.bankId="+bank+" and customer.homeBranchCode="+branch);
			
	/*cr no 40*/		String qry = "select  customer.CREDIT_CARD_DETAILS_ID, customer.PRIMARY_ACCT_NO,customer.EMBOSS_NAME,customer.HOME_BRANCH_CODE,customer.PRODUCT,customer.STATUS,customer.STATUS,customer.PIN_STATUS,customer.CUSTOMER_ID,customer.rsn, branch.IS_NON_CARD_ISSUE_BRANCH from master_bank_t bank, master_branch_t branch, CUSTOMER_RECORDS_T customer,MASTER_CORE_FILES mascorefile where  bank.BANK_ID = branch.BANK_ID and branch.SHORT_CODE=customer.HOME_BRANCH_CODE and mascorefile.ID=customer.FILE_ID and cast(mascorefile.RECEIVED_DATE as DAte) = '"
					+ dateString
					+ "' and bank.BANK_ID="
					+ bank;
				
			if(branchType.equals("0")) {
				qry += " and customer.HOME_BRANCH_CODE="+ branch;
			} else if(branchType.equals("1")) {
				qry += " and customer.ISSUE_BRANCH_CODE="+ branch;
			}
			qry += " ORDER BY mascorefile.RECEIVED_DATE desc";
			SQLQuery hel = getSessionFactory()
					.getCurrentSession()
					.createSQLQuery(qry);
			hel.setFirstResult(offset != null ? offset : 0);
			hel.setMaxResults(PAGE_MAX_SIZE);
			obj = hel.list();

			//logger.info("the object arta " + obj);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}

	public int branchwiseDaoCount(String bank, String branch, String branchType, String dateFrom)
			throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfc = new SimpleDateFormat("MM/dd/yyyy");
		List<Object[]> obj = null;
		SQLQuery hel = null;
		try {
			Date dd = sdfc.parse(dateFrom);


			Date added=DateUtils.addDays(dd, 1);
			//System.out.println("date " + dd);
			String dateString = sdf.format(added);

			log.info("date " + dd);
			String qry = "select  customer.CREDIT_CARD_DETAILS_ID, customer.PRIMARY_ACCT_NO,customer.EMBOSS_NAME,customer.HOME_BRANCH_CODE,customer.PRODUCT,customer.STATUS,customer.STATUS,customer.PIN_STATUS,customer.CUSTOMER_ID from master_bank_t bank, master_branch_t branch, CUSTOMER_RECORDS_T customer,MASTER_CORE_FILES mascorefile where  bank.BANK_ID = branch.BANK_ID and branch.SHORT_CODE=customer.HOME_BRANCH_CODE and mascorefile.ID=customer.FILE_ID and cast(mascorefile.RECEIVED_DATE as DAte) = '"
					+ dateString
					+ "' and bank.BANK_ID="
					+ bank;
			if(branchType.equals("0")) {
				qry += " and customer.HOME_BRANCH_CODE="+ branch;
			} else if(branchType.equals("1")) {
				qry += " and customer.ISSUE_BRANCH_CODE="+ branch;
			}

			hel = getSessionFactory()
					.getCurrentSession()
					.createSQLQuery(qry);

		} catch (Exception e) {

		}
		return hel.list().size();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Bank> getdetailsToaddproductDao() throws Exception {
		List<Bank> masBank=null;
		try {

			Criteria critMasBank = getSessionFactory().getCurrentSession()
					.createCriteria(Bank.class);
			masBank = critMasBank.list();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return masBank;
	}

	public CreditCardDetails getRecord(Long creditCardDetailsId) {
		CreditCardDetails details = (CreditCardDetails) getSessionFactory()
				.getCurrentSession()
				.createCriteria(CreditCardDetails.class)
				.add(Restrictions
						.eq("creditCardDetailsId", creditCardDetailsId)).list()
				.get(0);

		return details;
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


	@SuppressWarnings("unchecked")
	public List<RecordEvent> getRecordEvent(Long creditCardDetailsId) {
		List<RecordEvent> event = new ArrayList<RecordEvent>();
		event = (List<RecordEvent>) getSessionFactory().getCurrentSession()
				.createCriteria(RecordEvent.class)
				.add(Restrictions.eq("recordId", creditCardDetailsId))
				.addOrder(Order.asc("eventId")).list();
		return event;
	}

	
	public String getCardAWB(Long creditCardDetailsId) {
		MasterAWB masterAWB = (MasterAWB) getSessionFactory()
				.getCurrentSession()
				.createQuery(
						"from MasterAWB ma"
								+ " where ma.awbId in (select cr.cardAWB from CustomerHistory cr where cr.creditCardDetailsId="
								+ creditCardDetailsId + ")").list().get(0);
		return masterAWB.getAwbName();
	}


	public String getPinAWB(Long creditCardDetailsId) {
		MasterAWB masterAWB = (MasterAWB) getSessionFactory()
				.getCurrentSession()
				.createQuery(
						"from MasterAWB ma"
								+ " where ma.awbId in (select cr.pinAWB from CustomerHistory cr where cr.creditCardDetailsId="
								+ creditCardDetailsId + ")").list().get(0);
		return masterAWB.getAwbName();
	}

	
	public List<CreditCardDetails> searchRecords(Map<String, String> reqMap) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
