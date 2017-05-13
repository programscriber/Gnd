package com.indutech.gnd.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.impl.xb.xsdschema.RestrictionDocument.Restriction;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.indutech.gnd.dto.CreditCardDetails;
import com.indutech.gnd.dto.MasterAWB;
import com.indutech.gnd.dto.RecordEvent;
import com.indutech.gnd.util.StringUtil;

public class SearchDAOImpl implements SearchDAO {
	
	Logger logger = Logger.getLogger(SearchDAOImpl.class);
	common.Logger log = common.Logger.getLogger(SearchDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<CreditCardDetails> searchRecords(Map<String, String> reqMap) {
		List<CreditCardDetails> list = null;
		List statuslist=new ArrayList();
		try {
			String cardAwb = reqMap.get("CARD_AWB");
			String pinAwb = reqMap.get("PIN_AWB");
			String rsn = reqMap.get("RSN");
			String applNo = reqMap.get("APPL_NO");
			String product = reqMap.get("PRODUCT");
			String bank = reqMap.get("BANK");
			String branch = reqMap.get("BRANCH");
			String status=reqMap.get("STATUS");
			String mobile = reqMap.get("MOBILE");
			String acctNo = reqMap.get("ACCT_NO");
			// String acctTo = reqMap.get("ACCT_TO");
			String dateFrom = reqMap.get("DATE_FROM");
			String dateTo = reqMap.get("DATE_TO");
			String customerId = reqMap.get("CUSTOMER_ID");
			String issueBranchCode = reqMap.get("ISSUE_BRANCH_CODE");
			logger.info("in dao    "+status.isEmpty());
			logger.info(branch);
			if(!status.isEmpty()){
			for (String retval: status.split(",")){
				 statuslist.add(Long.valueOf(retval));
		         
		      }
			}
			logger.info("list "+statuslist);
			  Criteria criteria = getSessionFactory().getCurrentSession()
			  .createCriteria(CreditCardDetails.class); 			 
			  if (!StringUtil.isEMptyOrNull(applNo)) {
					criteria.add(Restrictions.eq("serialNo", applNo));
				}
				if (!StringUtil.isEMptyOrNull(product)) {
					criteria.add(Restrictions.eq("product", product));
				}
				if (!StringUtil.isEMptyOrNull(bank)) {
					criteria.add(Restrictions.eq("institutionId", bank));
					
				}
				if (!StringUtil.isEMptyOrNull(branch)) {
					criteria.add(Restrictions.eq("homeBranchCode", branch));
					
				}

				if (!StringUtil.isEMptyOrNull(pinAwb)) {
					criteria.add(Restrictions.eq("pinAWB", pinAwb));
				}
				if (!StringUtil.isEMptyOrNull(cardAwb)) {
					criteria.add(Restrictions.eq("cardAWB", cardAwb));
				}
				if (!StringUtil.isEMptyOrNull(customerId)) {
					criteria.add(Restrictions.eq("customerId", customerId));
				}
				if (!StringUtil.isEMptyOrNull(issueBranchCode)) {
					criteria.add(Restrictions.eq("issueBranchCode", issueBranchCode));
				}
				if (!StringUtil.isEMptyOrNull(rsn)) {// && Pattern.compile("\\d+").matcher(rsn.trim()).matches()) {
					criteria.add(Restrictions.eq("rsn", Long.parseLong(rsn)));
				}
				logger.info("jhgjh"+StringUtil.isEMptyOrNull(status));
				
				if (!StringUtil.isEMptyOrNull(status)) {
					if (!status.contains("0")) {

						if(!statuslist.isEmpty()) {
							criteria.add(Restrictions.in("status",statuslist));
						}

					}

				}
				if (!StringUtil.isEMptyOrNull(mobile)) {
					criteria.add(Restrictions.eq("mobileNo", mobile));
				}
			
				if (!StringUtil.isEMptyOrNull(acctNo)) {
					criteria.add(Restrictions.like("primaryAcctNo", "%"+acctNo));

				}
				logger.info(dateFrom+" "+dateTo);
				if (!StringUtil.isEMptyOrNull(dateFrom)
						&& !StringUtil.isEMptyOrNull(dateTo)) {
					try {
						SimpleDateFormat dateFormat = new SimpleDateFormat(
								"yyyy-MM-dd");
						SimpleDateFormat sdf = new SimpleDateFormat(
								"dd-MMM-yy");
						Date frmDt = dateFormat.parse(dateFrom);
						Date toDt = dateFormat.parse(dateTo);
						
						
						
						String from = sdf.format(frmDt);
						String to = sdf.format(toDt);
						logger.info("from date is : "+from+" and to date is : "+to);
						if(from.equals(to)) {
							criteria.add(Restrictions.eq("createdDate", sdf.parseObject(from)));
						}
						else {
							criteria.add(Restrictions.between("createdDate", sdf.parseObject(from), sdf.parseObject(to)));
//							criteria.add(Restrictions.ge("createdDate", sdf.parseObject(from)));
//							criteria.add(Restrictions.le("createdDate", sdf.parseObject(to)));
						}
						
					} catch (HibernateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			list = criteria.list();
			//list = sq.list();
			logger.info("t the object atr " + list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public CreditCardDetails getRecord(Long creditCardDetailsId) {
		CreditCardDetails details = (CreditCardDetails) getSessionFactory()
				.getCurrentSession()
				.createCriteria(CreditCardDetails.class)
				.add(Restrictions
						.eq("creditCardDetailsId", creditCardDetailsId)).list()
				.get(0);

		return details;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<RecordEvent> getRecordEvent(Long creditCardDetailsId) {
		List<RecordEvent> event = new ArrayList<RecordEvent>();
		event = (List<RecordEvent>) getSessionFactory().getCurrentSession()
				.createCriteria(RecordEvent.class)
				.add(Restrictions.eq("recordId", creditCardDetailsId))
				.addOrder(Order.asc("eventDate")).list();
		return event;
	}

	@Override
	public String getCardAWB(Long creditCardDetailsId) {
		MasterAWB masterAWB = (MasterAWB) getSessionFactory()
				.getCurrentSession()
				.createQuery(
						"from MasterAWB ma"
								+ " where ma.awbId in (select cr.cardAWB from CustomerHistory cr where cr.creditCardDetailsId="
								+ creditCardDetailsId + ")").list().get(0);
		return masterAWB.getAwbName();
	}

	@Override
	public String getPinAWB(Long creditCardDetailsId) {
		MasterAWB masterAWB = (MasterAWB) getSessionFactory()
				.getCurrentSession()
				.createQuery(
						"from MasterAWB ma"
								+ " where ma.awbId in (select cr.pinAWB from CustomerHistory cr where cr.creditCardDetailsId="
								+ creditCardDetailsId + ")").list().get(0);
		return masterAWB.getAwbName();
	}

}
