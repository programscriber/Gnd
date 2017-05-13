package com.indutech.gnd.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.indutech.gnd.bo.BankBO;
import com.indutech.gnd.dao.GNDDAOImpl;
import com.indutech.gnd.dto.Bank;
import com.indutech.gnd.dto.Branch;
import com.indutech.gnd.dto.MasterCourierService;
import com.indutech.gnd.dto.Product;
import com.indutech.gnd.dto.ProductMapping;

public class DataInitialization {
	
	static Logger logger = Logger.getLogger(DataInitialization.class);
	
	@Autowired
	private GNDDAOImpl gndDAO;
	
	public static List<BankBO> bankdata = null;
//	private static Multimap<Long, Branch> branchData = null;
	public static Multimap<Long, Product> productData = null;
	public static Multimap<Long, ProductMapping> productMappingData = null;
	public static Map<Long, Map<String, Branch>> branchData  = null;
	public static Multimap<Long, MasterCourierService> courierServiceData = null;
	
	
	private static DataInitialization initialize = null;
	
	
	public static DataInitialization getInstance() {
		if(initialize == null) {
			logger.info("first time instance method calling");
			initialize = new DataInitialization();
		}
		return initialize;
	}

	@Transactional
	public List<BankBO> getBankInfo() {
		try {
			if(bankdata == null) {
				logger.info("first time bank object initialization");
				List<Bank> bankList = getGndDAO().getBankList();
				if(bankList != null && bankList.size() > 0) {
					bankdata = new ArrayList<BankBO>();
					for(Bank bank : bankList) {
						bankdata.add(buildBank(bank));
					}
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return bankdata;
	}
	
	private BankBO buildBank(Bank bank) {
		BankBO bankBO = new BankBO();
		bankBO.setAufFormat(bank.getAufFormat());
		bankBO.setBankCode(bank.getBankCode());
		bankBO.setBankId(bank.getBankId());
		bankBO.setId(bank.getId());
		bankBO.setBankName(bank.getBankName());
		bankBO.setPrefix(bank.getPrefix());
		bankBO.setRTOBranchGroup(bank.getRTOBranchGroup());
		bankBO.setShortCode(bank.getShortCode());
		bankBO.setStatus(bank.getStatus());
		bankBO.setLPBranchGroup(bank.getLPAWBBranchGroup());
		bankBO.setCentralizedBranch(bank.getCentralizedBranch());
		return bankBO;
	}

	@Transactional
	public Map<Long, Map<String, Branch>> getBranchInfo() {
		try {
			if(branchData == null) {
				logger.info("first time branch object initialization");
				List<Branch>  list = getGndDAO().getBranchList();
				if(list != null && list.size() > 0) {
					Multimap<Long, Branch> bankWiseData = ArrayListMultimap.create();
					for(Branch branch : list) {
						bankWiseData.put(branch.getBankId(), branch);
					}
					if(bankWiseData.size() > 0) {
						Set<Long> banks = bankWiseData.keySet();
						if(banks != null && banks.size() > 0) {
							branchData = new HashMap<Long, Map<String, Branch>>();
							for(Long bankId : banks) {
								List<Branch> branchList = (List<Branch>) bankWiseData.get(bankId);
								if(branchList != null && branchList.size() > 0) {
									Map<String, Branch> branchInfo = new HashMap<String, Branch>();
									for(Branch branch : branchList) {
										branchInfo.put(branch.getShortCode(), branch);
									}
									branchData.put(bankId, branchInfo);
								}
							}
						}
					}
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return branchData;
	}
	
	@Transactional
	public Multimap<Long, Product> getProductInfo() {
		try {
			if(productData == null) {
				logger.info("first time product object initialization");
				productData = ArrayListMultimap.create();
				List<Product>  list = getGndDAO().getProductList();
				if(list != null && list.size() > 0) {
					for(Product product : list) {
						productData.put(product.getBankId(), product);
					}
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return productData;
	}
	
	@Transactional
	public Multimap<Long, MasterCourierService> getCorierServiceInfo() {
		try {
			if(courierServiceData == null) {
				courierServiceData = ArrayListMultimap.create();
				List<MasterCourierService>  list = getGndDAO().getCourierServiceList();
				if(list != null && list.size() > 0) {
					for(MasterCourierService courierService : list) {
						courierServiceData.put(courierService.getId(), courierService);
					}
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return courierServiceData;
	}
	
	@Transactional
	public Multimap<Long, ProductMapping> getProductMappingInfo() {
		try {
			if(productMappingData == null) {
				logger.info("first time product mapping object initialization");
				productMappingData = ArrayListMultimap.create();
				List<ProductMapping>  list = getGndDAO().getProductMappingList();
				if(list != null && list.size() > 0) {
					for(ProductMapping productMapping : list) {
						productMappingData.put(productMapping.getBankId(), productMapping);
					}
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return productMappingData;
	}

	public GNDDAOImpl getGndDAO() {
		return gndDAO;
	}

	public void setGndDAO(GNDDAOImpl gndDAO) {
		this.gndDAO = gndDAO;
	}

	public static List<BankBO> getBankdata() {
		return bankdata;
	}

	public static void setBankdata(List<BankBO> bankdata) {
		DataInitialization.bankdata = bankdata;
	}

	public static Multimap<Long, Product> getProductData() {
		return productData;
	}

	public static void setProductData(Multimap<Long, Product> productData) {
		DataInitialization.productData = productData;
	}

	public static Multimap<Long, ProductMapping> getProductMappingData() {
		return productMappingData;
	}

	public static void setProductMappingData(
			Multimap<Long, ProductMapping> productMappingData) {
		DataInitialization.productMappingData = productMappingData;
	}


	public static Multimap<Long, MasterCourierService> getCourierServiceData() {
		return courierServiceData;
	}

	public static void setCourierServiceData(
			Multimap<Long, MasterCourierService> courierServiceData) {
		DataInitialization.courierServiceData = courierServiceData;
	}
	
	
}
