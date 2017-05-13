package com.indutech.gnd.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;

import com.indutech.gnd.bo.MasterAWBBO;
import com.indutech.gnd.dto.MasterAWB;
import com.indutech.gnd.dto.MasterBank;
import com.indutech.gnd.dto.MasterCourierService;
import com.indutech.gnd.dto.MasterDcms;
import com.indutech.gnd.dto.MasterType;
import com.indutech.gnd.enumTypes.Status;

public class MasterAWBDAOImpl implements MasterAWBDAO {
	
	Logger logger = Logger.getLogger(MasterAWBDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	Status status = Status.valueOf("ACTIVE");
	String statusId = status.getStatus();
	
	Status blstatus = Status.valueOf("BLOCKED");
	String blockedStatusId = blstatus.getStatus();
	
	
	@Override
	public List<MasterAWB> getMasterAWB(String awbName) {

		@SuppressWarnings("unchecked")
		List<MasterAWB> list = getSessionFactory().getCurrentSession()
				.createCriteria(MasterAWB.class)
				.add(Restrictions.eq("awbName", awbName)).list();
		
		return list;
	}

	@Override
	public void saveMasterAWB(MasterAWB masterAWB) {

			getSessionFactory().getCurrentSession().saveOrUpdate(masterAWB);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MasterAWB> getMasterAWBId() {

		

		List<MasterAWB> masterAWB = null;
		try {
			masterAWB = getSessionFactory().getCurrentSession().createCriteria(MasterAWB.class).add(Restrictions.eq("status", Long.parseLong(statusId))).list();
		}
		catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
			return masterAWB;
		

	}
	
	@Override
	public List<MasterAWB> getAWBForChennaiAndMumbai(Long serviceProviderId, int maxlimit) {
		List<MasterAWB> masterAWB = null;
//		List<MasterAWBBO> masterAWBList = new ArrayList<MasterAWBBO>();
//		 Map row = null;
//		String query="Select awb.AWB awbName,awb.id awbId,master.SERVICE_PROVIDER_NAME awbServiceProviderName,master.ID masterId  from master_awb_t awb,MASTER_COURIER_SERVICE_T master where awb.service_provider_id=master.id AND awb.service_provider_id="+serviceProviderId+" AND awb.status="+Long.parseLong(statusId);
		try {
			
//			SQLQuery sqlQuery = getSessionFactory().getCurrentSession().createSQLQuery(query);
//			
//			sqlQuery.addScalar("awbName",StringType.INSTANCE)
//	        .addScalar("awbId",LongType.INSTANCE)
//	        .addScalar("awbServiceProviderName",StringType.INSTANCE)
//	        .addScalar("masterId",LongType.INSTANCE);
//			sqlQuery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
//			
//			List<?> data = sqlQuery.setMaxResults(maxlimit).list();
//			if(data != null && data.size() > 0) {
//				int i = 1; 
//		         for(Object object : data)
//		         {
//		        	 MasterAWBBO masterawb = new MasterAWBBO();
//		        	 
//		             row = (Map<?, ?>)object;
//		             masterawb.setServiceProviderName((String)row.get("awbServiceProviderName"));
//		             masterawb.setAwbId((Long)row.get("awbId"));
//		             masterawb.setAwbName((String)row.get("awbName"));
//		             masterawb.setServiceProviderId((Long)row.get("masterId"));
//		             i += getSessionFactory().getCurrentSession().createQuery("update MasterAWB set status = "+Long.parseLong(blockedStatusId)+" where awbId="+masterawb.getAwbId()).executeUpdate();
//		             masterAWBList.add(masterawb);
//		         }
//		         logger.info(i+" awb keys are blocked");
				masterAWB = (List<MasterAWB>) getSessionFactory().getCurrentSession().createCriteria(MasterAWB.class).
						add(Restrictions.eq("serviceProviderId", serviceProviderId)).
						add(Restrictions.eq("status", Long.parseLong(statusId)))
						.addOrder(Order.asc("awbId"))				// .addOrder(Order.asc("awbName")) existing one cr no 41
						.setMaxResults(maxlimit).list();
				if(masterAWB != null && masterAWB.size() > 0) {
					for(MasterAWB awb : masterAWB) {
						awb.setStatus(Long.parseLong(blockedStatusId));
						getSessionFactory().getCurrentSession().saveOrUpdate(awb);
					}
				}
//			}
		}
		catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
			return masterAWB;
		
		
	}
	
	@Override
	public MasterAWB getAWBForChennaiAndMumbai(Long serviceProviderId) {
		
		List<MasterAWB> masterAWB = null;
		MasterAWB awb = null;
		try {
			masterAWB = (List<MasterAWB>) getSessionFactory().getCurrentSession().createCriteria(MasterAWB.class).
					add(Restrictions.eq("serviceProviderId", serviceProviderId)).
					add(Restrictions.eq("status", Long.parseLong(statusId)))
					.addOrder(Order.asc("awbId"))   /*cr no 41*/
					.list();
			if(masterAWB != null && masterAWB.size() > 0) {
				awb = (MasterAWB) masterAWB.get(0);
				awb.setStatus(Long.parseLong(blockedStatusId));
				getSessionFactory().getCurrentSession().update(awb);
			}
		}
		catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
			return awb;
		
		
	}
  
	@Override
	public List<MasterCourierService> getServiceProviderList() {
		@SuppressWarnings("unchecked")
		List<MasterCourierService> list = getSessionFactory()
				.getCurrentSession().createCriteria(MasterCourierService.class)
				.list();

		return list;
	}

	@Override
	public Long getServiceProviderId(String serviceProvicerName) {
		Long id = (Long) getSessionFactory()
				.getCurrentSession()
				.createQuery(
						"select m.id from MasterCourierService m where m.serviceProviderName='"
								+ serviceProvicerName + "'").list().get(0);
		return id;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]>  getAwbCounts() {
		
		List<Object[]> list = getSessionFactory().getCurrentSession().createSQLQuery(
				  "select distinct SERVICE_PROVIDER_NAME,"+ 
				  "(select count(*) from master_awb_t where status = 3 and service_provider_id = cs.id) as used,"+
				 "(select count(*) from master_awb_t where status = 1 and service_provider_id = cs.id) as unused,"+  
				  "(select max(awb) from master_awb_t where status = 1 and service_provider_id = cs.id) as max_value,"+
				  "(select min(awb) from master_awb_t where status = 1 and service_provider_id = cs.id) as min_value"+
				  " from MASTER_COURIER_SERVICE_T cs, master_awb_t where service_provider_id= cs.id" ).list();
		return list;
		
	}


	@Override
	public MasterCourierService getServiceProvider(Long serviceProviderId) {
		MasterCourierService service = null;
		try {
			service = (MasterCourierService) getSessionFactory().getCurrentSession().createCriteria(MasterCourierService.class).add(Restrictions.eq("id", serviceProviderId)).list().get(0);
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return service;
	}
	
	public List getproductDao() {
		
		List resultList = new ArrayList();
		Criteria critMasType = getSessionFactory().getCurrentSession()
				.createCriteria(MasterType.class);
		List masType = critMasType.list();

		Criteria critMasBank = getSessionFactory().getCurrentSession()
				.createCriteria(MasterBank.class);
		List masBank = critMasBank.list();

		Criteria critMasDcms = getSessionFactory().getCurrentSession()
				.createCriteria(MasterDcms.class);
		List masDcms = critMasDcms.list();

		resultList.add(masType);
		resultList.add(masBank);
		resultList.add(masDcms);
		
		return resultList;
	}

	@Override
	public void changeStatus(MasterAWB awb) {
		getSessionFactory().getCurrentSession().saveOrUpdate(awb);
	}

	public final SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public final void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
