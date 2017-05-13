package com.indutech.gnd.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import sun.util.logging.resources.logging;

import com.indutech.gnd.dto.CardDispatch;
import com.indutech.gnd.dto.CreditCardDetails;
import com.indutech.gnd.dto.PinDispatch;
import com.indutech.gnd.dto.RecordEvent;

@Repository(value="dispatchDAO")
public class DispatchDAOImpl implements DispatchDAO {
	
	Logger logger = Logger.getLogger(DispatchDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<CreditCardDetails> findCardAWB(Long rsn, String cardAWB) {
		@SuppressWarnings("unchecked")
		List<CreditCardDetails> list = getSessionFactory().getCurrentSession().createCriteria(CreditCardDetails.class)
										.add(Restrictions.eq("rsn", rsn)).add(Restrictions.eq("cardAWB", cardAWB)).list();
		return list;
	}

	@Override
	public List<CreditCardDetails> findPinAWB(Long rsn, String pinAWB) {
		@SuppressWarnings("unchecked")
		List<CreditCardDetails> list = getSessionFactory().getCurrentSession().createCriteria(CreditCardDetails.class)
				.add(Restrictions.eq("rsn", rsn)).add(Restrictions.eq("pinAWB", pinAWB)).list();
		return list;
	}

	@Override
	public void saveCardDispatch(CardDispatch cardDispatch) {
		getSessionFactory().getCurrentSession().saveOrUpdate(cardDispatch);
		
	}

	@Override
	public void savePinDispatch(PinDispatch pinDispatch) {
		getSessionFactory().getCurrentSession().saveOrUpdate(pinDispatch);
		
	}

	@SuppressWarnings("unchecked")
	public CreditCardDetails getCardDetails(Long rsn,
			String awb, String dispatchType, Long status) {
		List<CreditCardDetails> list = null;
		try {
			
			Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(CreditCardDetails.class)
											.add(Restrictions.eq("rsn",rsn));
			if(dispatchType.equalsIgnoreCase("card")) {
				criteria.add(Restrictions.eq("cardAWB", awb));
				criteria.add(Restrictions.eq("status", status));
			} else if(dispatchType.equalsIgnoreCase("pin")) {
				criteria.add(Restrictions.eq("pinAWB", awb));
				criteria.add(Restrictions.eq("pinstatus", status));
			} else {
				criteria = null;
			}
			if(criteria != null) {
				list = criteria.list();
			}
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
			
		}
			
		return list != null && list.size() > 0 ?list.get(0):null;
	}

	public void saveCustomerRecords(CreditCardDetails details) {
		getSessionFactory().getCurrentSession().saveOrUpdate(details);
		
	}

	public void saveRecordEvent(RecordEvent event) {
		getSessionFactory().getCurrentSession().saveOrUpdate(event);
		
	}

}
