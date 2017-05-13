package com.indutech.gnd.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.indutech.gnd.dto.Bank;
import com.indutech.gnd.dto.MasterBank;
import com.indutech.gnd.dto.MasterDcms;
import com.indutech.gnd.dto.MasterType;
import com.indutech.gnd.dto.Network;
import com.indutech.gnd.dto.Product;
import com.indutech.gnd.enumTypes.Status;

@Repository("productDAO")
public class ProductDAOImpl implements ProductDAO {
	
	Logger logger = Logger.getLogger(ProductDAOImpl.class);
	common.Logger log = common.Logger.getLogger(ProductDAOImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	Status stat = Status.valueOf("ACTIVE");
	String status = stat.getStatus();
	
	

	public final SessionFactory getSessionFactory() {
		return sessionFactory;
	}



	public final void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void saveProduct(Product product) {	
			
				getSessionFactory().getCurrentSession().saveOrUpdate(product);
			
		}		
	
	@Override
	public List<Product> getProduct(Product product) {
		
		@SuppressWarnings("unchecked")
		List<Product> list = (List<Product>) getSessionFactory().getCurrentSession().createCriteria(Product.class).add(Restrictions.eq("shortCode", product.getShortCode())).list();;
		
		return list;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MasterBank> getBankId(String shortCode) {
		List<MasterBank> list = (List<MasterBank>)getSessionFactory().getCurrentSession().createQuery("from MasterBank where shortCode ='"+shortCode+"'").list();
		
		return list;
	}
	
	@Override
	public Long getNetworkId(String networkName) {
				
			logger.info("network name is : "+networkName);
				Long id = (Long)getSessionFactory().getCurrentSession().createQuery("select networkId from Network where networkName ='"+networkName+"'").list().get(0);
		return id;
	}


	@Override
	public List<Product> getProduct(String shortCode, String bin, Long bankId) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<Product> list = getSessionFactory().getCurrentSession().createCriteria(Product.class).
								add(Restrictions.eq("shortCode", shortCode)).add(Restrictions.eq("bin", bin))
								.add(Restrictions.eq("bankId", bankId)).list();
		
		
		return list;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<MasterDcms> getDcmsId(String dcmsName) {
	
		List<MasterDcms> list = getSessionFactory().getCurrentSession().createQuery("from MasterDcms md where md.dcmsName='"+dcmsName+"'").list();
		return list;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<MasterType> getTypeId(String typeName) {
		List<MasterType> list = getSessionFactory().getCurrentSession().createQuery("from MasterType mt where mt.typeDescription='"+typeName+"'").list();
		return list;
		
	}


	@SuppressWarnings("unchecked")
	@Override
	public String getBankCode(String shortCode) {
		
		String bankCode = null;
		List<Bank> list = (List<Bank>) getSessionFactory().getCurrentSession().createQuery("from Bank bank where bank.bankId in (select product.bankId from Product product where product.shortCode='"+shortCode+"')").list();
		if(list.size() > 0) {
			Bank bank = list.get(0);
			bankCode = bank.getBankCode();
		}
		return bankCode;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getProductList(String productCode) {

		List<Product> list = (List<Product>) getSessionFactory().getCurrentSession().createCriteria(Product.class).
							add(Restrictions.eq("shortCode", productCode)).
							add(Restrictions.eq("status", Long.parseLong(status))).list();
		return list;
	}

	@Override
	public List<Product> getProducts(String productCode) {

		List<Product> list = (List<Product>) getSessionFactory().getCurrentSession().createCriteria(Product.class).
							add(Restrictions.eq("shortCode", productCode)).list();
							
		return list;
	}
	
	@Override
	public List<Network> getNetworkList(Long networkId) {
	  List<Network> networkList = (List<Network>) getSessionFactory().getCurrentSession().createCriteria(Network.class).
			  									 add(Restrictions.eq("networkId", networkId)).list();
	  return networkList;
	}

	@Override
	public String getBin(String productCode, Long bankId) {
		 
			List<String> list = (List<String>) getSessionFactory().getCurrentSession().createQuery("select product.bin from Product product where product.bankId="+bankId+" and product.shortCode='"+productCode+"'").list();
			return list.size()>0?list.get(0):null;
	}


	@Override
	public List<Product> getProductList(String product, Long bankId) {
		List<Product> productList = null;
		try {
			productList = getSessionFactory().getCurrentSession().createCriteria(Product.class)
							.add(Restrictions.eq("shortCode", product)).add(Restrictions.eq("bankId",bankId)).list();
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return productList;
	}
}
