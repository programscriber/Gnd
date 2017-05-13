package com.indutech.gnd.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.indutech.gnd.bo.RecordEventBO;
import com.indutech.gnd.dto.Bank;
import com.indutech.gnd.dto.Branch;
import com.indutech.gnd.dto.District;
import com.indutech.gnd.dto.EmailConfigaration;
import com.indutech.gnd.dto.MasterAWB;
import com.indutech.gnd.dto.MasterBank;
import com.indutech.gnd.dto.MasterCourierService;
import com.indutech.gnd.dto.MasterDcms;
import com.indutech.gnd.dto.MasterType;
import com.indutech.gnd.dto.Network;
import com.indutech.gnd.dto.Product;
import com.indutech.gnd.dto.RecordEvent;
import com.indutech.gnd.dto.State;
import com.indutech.gnd.enumTypes.Status;

@Repository("masterDBdao")
public class MasterDBdao {
	Logger logger = Logger.getLogger(MasterDBdao.class);
	@Autowired
	private SessionFactory sessionFactory;
	public final static int MAX_RESULT=5000;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List addproductDao(Product product) {
		List resultList = new ArrayList();
		List masType;
		List masBank;
		List masDcms;
		List network;
		try {
			List<Product> list = getSessionFactory().getCurrentSession().createCriteria(Product.class).
					add(Restrictions.eq("shortCode", product.getShortCode())).
					add(Restrictions.eq("bankId", product.getBankId())).list();
			if(list.size() > 0) {
				Iterator itr = list.iterator();
				while(itr.hasNext()) {
					Product productList = (Product) itr.next();
					product.setProductId(productList.getProductId());
					getSessionFactory().getCurrentSession().evict(productList);
					getSessionFactory().getCurrentSession().saveOrUpdate(product);
					resultList.add("PRODUCT ALREADY EXISTS AND UPDATED SUCESSFULLY");
				}				
			}
			else {
				getSessionFactory().getCurrentSession().saveOrUpdate(product);
				resultList.add("PRODUCT ADDED SUCESSFULLY");
			}
			
				Criteria critMasType = getSessionFactory().getCurrentSession()
						.createCriteria(MasterType.class);
				masType = critMasType.list();

				Criteria critMasBank = getSessionFactory().getCurrentSession()
						.createCriteria(Bank.class);
				masBank = critMasBank.list();

				Criteria critMasDcms = getSessionFactory().getCurrentSession()
						.createCriteria(MasterDcms.class);
				masDcms = critMasDcms.list();

				Criteria critNetwork = getSessionFactory().getCurrentSession()
						.createCriteria(Network.class);
				network = critNetwork.list();

				resultList.add(masType);
				resultList.add(masBank);
				resultList.add(masDcms);
				resultList.add(network);
				
		} catch (Exception e) {
			e.printStackTrace();
			resultList.add("FAILED TO ADDED");
		}
		return resultList;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getdetailsToaddproductDao() {
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

		Criteria critNetwork = getSessionFactory().getCurrentSession()
				.createCriteria(Network.class);
		List network = critNetwork.list();

		resultList.add(masType);
		resultList.add(masBank);
		resultList.add(masDcms);
		resultList.add(network);

		return resultList;

	}

	@SuppressWarnings("rawtypes")
	public List productSearchDao(Map productDao) {

		Criteria crit = getSessionFactory().getCurrentSession().createCriteria(
				Product.class);
		if (StringUtils.isNotEmpty(productDao.get("bank").toString())) {
			if ((productDao.get("bank").toString() != null)
					&& (!productDao.get("bank").toString().equals("%%"))) {
				crit.add(Restrictions.eq("bankId",
						Long.valueOf(productDao.get("bank").toString())));
			}
		}
		if (StringUtils.isNotEmpty(productDao.get("cardType").toString())) {
			if ((productDao.get("cardType").toString() != null)
					&& (!productDao.get("cardType").toString().equals("%%"))) {
				crit.add(Restrictions.eq("typeId",
						Long.valueOf(productDao.get("cardType").toString())));
			}
		}
		if (StringUtils.isNotEmpty(productDao.get("dcms").toString())) {
			if ((productDao.get("dcms").toString() != null)
					&& (!productDao.get("dcms").toString().equals("%%"))) {
				crit.add(Restrictions.eq("dcmsId",
						Long.valueOf(productDao.get("dcms").toString())));
			}
		}
		if (StringUtils.isNotEmpty(productDao.get("productName").toString())) {
			crit.add(Restrictions.eq("productName",
					productDao.get("productName").toString()));
		}

		List resultlist = crit.list();
		return resultlist;
	}

	@SuppressWarnings("rawtypes")
	public List<Object[]> branchSearchDao() {
		List resultList = new ArrayList();
		Criteria branch = getSessionFactory().getCurrentSession()
				.createCriteria(Branch.class);
		List branchList = branch.list();

		Criteria MasBank = getSessionFactory().getCurrentSession()
				.createCriteria(Bank.class);
		List masBank = MasBank.list();
		resultList.add(branchList);
		resultList.add(masBank);
	
		/*Query hql = getSessionFactory()
				.getCurrentSession()
				.createQuery(
						"select masBan.shortCode,masBran.branchName,masBran.shortCode,masBran.ifscCode from MasterBank masBan,Branch masBran where masBan.bankId=masBran.bankId");
		List<Object[]> resultList = hql.list();*/
		return resultList;
	}
	@SuppressWarnings("rawtypes")
	public List<Object[]> branchSearchDaoImpl() {
		
		Criteria MasBank = getSessionFactory().getCurrentSession()
				.createCriteria(Bank.class);
	       List masBank = MasBank.list();
		return masBank;
		}

	public List<Branch> searchbranchSer(Map branchMapDao) {
		Criteria crit = getSessionFactory().getCurrentSession().createCriteria(
				Branch.class);
		
		if (StringUtils.isNotEmpty(branchMapDao.get("bankCode").toString())) {
			if ((branchMapDao.get("bankCode").toString() != null)
					&& (!branchMapDao.get("bankCode").toString().equals("%%"))) {
				crit.add(Restrictions.eq("bankId",Long.valueOf(branchMapDao.get("bankCode").toString())));
			}
		}
	
		if (StringUtils.isNotEmpty(branchMapDao.get("branchName").toString())) {
			if ((branchMapDao.get("branchName").toString() != null)
					&& (!branchMapDao.get("branchName").toString().equals("%%"))) {
				crit.add(Restrictions.eq("branchName", branchMapDao.get("branchName").toString()));
			}
		}
		if (StringUtils.isNotEmpty(branchMapDao.get("branchCode").toString())) {
			if ((branchMapDao.get("branchCode").toString() != null)
					&& (!branchMapDao.get("branchCode").toString().equals("%%"))) {
				crit.add(Restrictions.eq("shortCode", branchMapDao.get("branchCode").toString()));
			}
		}
		if (StringUtils.isNotEmpty(branchMapDao.get("ifscCode").toString())) {
			if ((branchMapDao.get("ifscCode").toString() != null)
					&& (!branchMapDao.get("ifscCode").toString().equals("%%"))) {
				crit.add(Restrictions.eq("ifscCode", branchMapDao.get("ifscCode").toString()));
			}
		}
		
		List<Branch> result=crit.list();
				logger.info("records display "+result.size());
					return result;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public List getdistctAndStateDao() {
		List result=new ArrayList();
		List<Bank> masterbank=(List<Bank>)getSessionFactory().getCurrentSession().createCriteria(Bank.class).list();
		List<State> state=(List<State>)getSessionFactory().getCurrentSession().createCriteria(State.class).list();
	
		result.add(masterbank);
		result.add(state);
		
		return result;
	}

	public List<District> getDistrictDAO(Long stateId) {
		
		List<District> district=(List<District>)getSessionFactory().getCurrentSession().createCriteria(District.class).add(Restrictions.eq("stateId", stateId)).list();
		
		
		return district;
	}

	public void addBranch(Branch branch) {
		
	getSessionFactory().getCurrentSession().save(branch);
		
	}

	public boolean getCheckForExistingBranchAndSave(Branch branch) {
		boolean addbranch=false;
		List<Branch> branc=getSessionFactory().getCurrentSession().createCriteria(Branch.class).add(Restrictions.eq("shortCode", branch.getShortCode())).add(Restrictions.eq("bankId", branch.getBankId())).list();	
		if(branc!=null&&branc.size()>0){
			addbranch=false;
		}else{
			getSessionFactory().getCurrentSession().save(branch);
			addbranch=true;
		}
		return addbranch;
	}
	public boolean getCheckForExistingBranchAndSaveedit(Branch branch) {
		boolean addbranch=false;
		List<Branch> branc=getSessionFactory().getCurrentSession().createCriteria(Branch.class).add(Restrictions.eq("shortCode", branch.getShortCode())).add(Restrictions.eq("bankId", branch.getBankId())).list();	
		if(branc!=null&&branc.size()>0){
			Branch branchup=branc.get(0);
			branchup.setShortCode(branch.getShortCode());
			branchup.setBankId(branch.getBankId());
			branchup.setAddress1(branch.getAddress1());
			branchup.setAddress2(branch.getAddress2());
			branchup.setAddress3(branch.getAddress3());
			branchup.setAddress4(branch.getAddress4());
			branchup.setStateCode(branch.getStateCode());
			branchup.setPinCode(branch.getPinCode());
			branchup.setBranchName(branch.getBranchName());
			branchup.setStatus(branch.getStatus());
			branchup.setLcpcName(branch.getLcpcName());
			branchup.setLcpcBranch(branch.getLcpcBranch());
			branchup.setDistrictCode(branch.getDistrictCode());
			branchup.setPhoneNumber(branch.getPhoneNumber());
			branchup.setEmailAddress(branch.getEmailAddress());
			branchup.setIsNonCardIssueBranch(branch.getIsNonCardIssueBranch());
			getSessionFactory().getCurrentSession().update(branchup);
			addbranch=true;
		}else{
			addbranch=false;
		}
		return addbranch;
	}
	public List<State>  getState(Long stateCode) {
		
		List<State> statedto=getSessionFactory().getCurrentSession().createCriteria(State.class).add(Restrictions.eq("stateCode", stateCode)).list();
		
	return	statedto;
	}

	public List<District>  getDistrictsend(Long districtCode,Long stateCode) {
		
		List<District> districtdtolis=getSessionFactory().getCurrentSession().createCriteria(District.class).add(Restrictions.eq("districtCode", districtCode)).add(Restrictions.eq("stateId", stateCode)).list();
		
		return districtdtolis;
	}

	public List<Bank> getBankdto(Long bankId) {
		List<Bank> bank=getSessionFactory().getCurrentSession().createCriteria(Bank.class).add(Restrictions.eq("bankId",bankId)).list();
		return bank;
	}

	public List<Branch> getMasterBranch(String branchshortcode, String bank) {
		List<Branch> branch=getSessionFactory().getCurrentSession().createCriteria(Branch.class).add(Restrictions.eq("shortCode", branchshortcode)).add(Restrictions.eq("bankId",Long.valueOf(bank))).list();
		return branch;
	}

	public boolean addStateTo(State state) {
		boolean result=false;
		try{
			getSessionFactory().getCurrentSession().save(state);
			result=true;
		}catch(Exception e){
			result=false;
		}
		return result;
	}

	public List<State>  getState() {
		List<State> state=(List<State>)getSessionFactory().getCurrentSession().createCriteria(State.class).list();
		return state;
	}

	public boolean addDistrict(District district) {
		boolean result=false;
		try{
			getSessionFactory().getCurrentSession().save(district);
			result=true;
		}catch(Exception e){
			result=false;
		}
		return result;
		
	}

	public 	List<MasterCourierService> getServiceProvider() {
		List courierService=new  ArrayList();
		Map hashmap=new HashMap();
		List<MasterCourierService> courierServiceList=getSessionFactory().getCurrentSession().createCriteria(MasterCourierService.class).list();
	/*	courierService.add(courierServiceList);
		for(int i=0;i<courierServiceList.size();i++){
			int count=getSessionFactory().getCurrentSession().createCriteria(MasterAWB.class).add(Restrictions.eq("serviceProviderId", courierServiceList.get(i).getId())).add(Restrictions.eq("status",Long.valueOf(Status.valueOf("ACTIVE").getStatus()))).list().size();
			hashmap.put(courierServiceList.get(i).getServiceProviderName(), count);
		}
		courierService.add(hashmap);*/
		return courierServiceList;
		
	}
	public long countService(Long id ){
		int count=getSessionFactory().getCurrentSession().createCriteria(MasterAWB.class).add(Restrictions.eq("serviceProviderId", id)).add(Restrictions.eq("status",Long.valueOf(Status.valueOf("ACTIVE").getStatus()))).list().size();
		return(long)count;
	}

	public List<MasterAWB> getListAWBDAO(Long serviceid, Integer count, Boolean awbblock) {
		List<MasterAWB> masterAWbList=null;
		if((boolean)awbblock){
			masterAWbList=getSessionFactory().getCurrentSession().createCriteria(MasterAWB.class).add(Restrictions.eq("serviceProviderId", serviceid)).add(Restrictions.eq("status",Long.valueOf(Status.valueOf("ACTIVE").getStatus()))).setMaxResults(count).list();
			for(int i=0;i<masterAWbList.size();i++){
				MasterAWB mas=masterAWbList.get(i);
				mas.setStatus(Long.valueOf(Status.valueOf("BLOCKED").getStatus()));
				getSessionFactory().getCurrentSession().update(mas);
				/*RecordEvent event=new RecordEvent();
				event.setEventId(mas.getAwbId());
				event.setEventDate(new Date());
				event.setDescription("AWB is BLOCKED");*/
				
				
			}
		}else{
			masterAWbList=getSessionFactory().getCurrentSession().createCriteria(MasterAWB.class).add(Restrictions.eq("serviceProviderId", serviceid)).add(Restrictions.eq("status",Long.valueOf(Status.valueOf("ACTIVE").getStatus()))).setMaxResults(count).list();
		}
		return masterAWbList;
	}

	public List<EmailConfigaration> getEmailProperties() {
		List<EmailConfigaration> emaildto=getSessionFactory().getCurrentSession().createCriteria(EmailConfigaration.class).list();
	return emaildto;
	}

	public boolean updateemailField(EmailConfigaration emailconfig) {
		boolean result=false;
		try{
		getSessionFactory().getCurrentSession().update(emailconfig);
		result=true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
		
	}

	public List<Bank> getBankList() {
		List<Bank> masterbank=(List<Bank>)getSessionFactory().getCurrentSession().createCriteria(Bank.class).list();
		return masterbank;
	}

	public boolean getCheckAndSave(Bank bankdto) {
		
		boolean result=false;
		try{
		Criteria crit=getSessionFactory().getCurrentSession().createCriteria(Bank.class);
		Disjunction or = Restrictions.disjunction();
		or.add(Restrictions.eq("bankId", bankdto.getBankId()));
		or.add(Restrictions.eq("bankName",bankdto.getBankName().trim()));
		or.add(Restrictions.eq("shortCode",bankdto.getShortCode().trim()));
		or.add(Restrictions.eq("bankCode",bankdto.getBankCode().trim()));
		or.add(Restrictions.eq("prefix", bankdto.getPrefix().trim()));
		List<Bank> bandto=(List<Bank>)crit.add(or).list();
		System.out.println("The list of values are "+crit.list());
		if((bandto!=null)&&(bandto.size()>0)){
			result=false;
		}else{
			getSessionFactory().getCurrentSession().save(bankdto);
			System.out.println(result);
			result=true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	

	
}
