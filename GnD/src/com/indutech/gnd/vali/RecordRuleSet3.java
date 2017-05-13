package com.indutech.gnd.vali;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.indutech.gnd.bo.drool.DroolRecordBO;
import com.indutech.gnd.dao.BranchDAOImpl;
import com.indutech.gnd.dao.FileConverterDAOImpl;
import com.indutech.gnd.dao.FindPathImpl;
import com.indutech.gnd.dao.ProductDAOImpl;
import com.indutech.gnd.dto.Branch;
import com.indutech.gnd.dto.Product;
import com.indutech.gnd.enumTypes.RecordStatus;
import com.indutech.gnd.records.Record;

public class RecordRuleSet3 {
	
	Logger logger = Logger.getLogger(RecordRuleSet3.class);	
	
	common.Logger log = common.Logger.getLogger(RecordRuleSet3.class);
	
	@Autowired
	private BranchDAOImpl branchDAO;
	
	@Autowired
	private Record rec;
	
	@Autowired
	private ProductDAOImpl productDAO;
	@Autowired
	private FileConverterDAOImpl fileConverterDAO;
	
	@Autowired
	private FindPathImpl findPath;
	
	RecordStatus stat2 = RecordStatus.valueOf("HOLD");
	String holdStatus = stat2.getRecordStatus();
	RecordStatus stat3 = RecordStatus.valueOf("APPROVED");
	String approvedStatus = stat3.getRecordStatus();
	
	private DroolRecordBO record;
	private String product;
	private List<Product> productList = null;
	private List<Branch> branchList = null;
	private String imageDir = null;
	
	public boolean recordRuleSet3Start(DroolRecordBO record) {
		
				
		return true;
	}
	
	@Transactional
	public boolean isBranchCodePresenrt_rule_6a1(DroolRecordBO record)	{
	
		branchList = getBranchDAO().searchShortCode(record.getHomeBranchCode().trim());
		if(branchList.size() != 0) {
			return true;
		}
		
//		record.setFlowStatus(DroolRecordBO.FLOW_HOLD);
//		record.setRuleStatus("branch code "+branchCode.trim()+" is not present in the master branch");
//		processRecordAndEvent("branch code "+branchCode.trim()+" is not present in the master branch",holdStatus);
		logger.info("branch code "+record.getHomeBranchCode()+" is not present in the master branch");
		return false;
	}
	
	public boolean isBranchAddressCompeleted_rule_6a2(DroolRecordBO record)	{
		return true;
	}
	
	public boolean isBranchActivated_rule_6a3(DroolRecordBO record)	{		
		if(branchList.size() != 0) {			
			@SuppressWarnings("rawtypes")
			Iterator itr = branchList.iterator();			
			Branch	branch = (Branch) itr.next();	
			if(branch.getStatus() == 1) {
				return true;
			}
		}
		
		record.setFlowStatus(DroolRecordBO.FLOW_HOLD);
		record.setRuleStatus("branch status is not in active");
//		processRecordAndEvent("branch status is not in active",holdStatus);
//		logger.info("branch status is not in active");
		logger.info("branch status is not in active");
		return false;
	}
	
	@Transactional
	public boolean isProductIdentifierPresent_rule_6b1(DroolRecordBO record)	{
		productList = getProductDAO().getProduct(record.getProduct().trim(), record.getInstitutionId().trim(), record.getBankId());//why you declare class level variables
		if(productList.size()>0) {
			return true;
		}
		record.setFlowStatus(DroolRecordBO.FLOW_HOLD);
		record.setRuleStatus("Product code : "+product.trim()+" is not exist in master product");
//		processRecordAndEvent("Product code : "+product.trim()+" is not exist in master product",holdStatus);
		logger.info("Product code : "+product.trim()+" is not exist in master product");
		return false;
	}
	@SuppressWarnings("rawtypes")
	public boolean isProductIdentifierActivated_rule_6b2(DroolRecordBO recordDetailsBO)	{
		if(productList.size()>0) {
			Product product = null;
			Iterator itr = productList.iterator();
			while(itr.hasNext()) {
				 product = (Product) itr.next();
			}
			if(null!=product && product.getStatus() == 1) {
				return true;
			}
		}
		record.setFlowStatus(DroolRecordBO.FLOW_HOLD);
		record.setRuleStatus("Product code : "+product.trim()+" is not in active status");
//		processRecordAndEvent("Product code : "+product.trim()+" is not exist in master product",holdStatus);
		logger.info("Product code :"+this.product.trim()+" is not in active status");
		return false;
	}
	
	@Transactional
	public boolean isPhotoFileMissing_rule_6c1(DroolRecordBO record)	{
		boolean result = false;
		productList = getProductDAO().getProduct(record.getProduct().trim(),record.getInstitutionId().trim(), record.getBankId());//why again..DB call? sir this is written by you
		Product product = null;
		if(productList.size()>0){
			product =productList.get(0);
		}
		
		String primaryAcNo = (String) record.getPrimaryAcctNo();
		
		if (null!=product && product.getPhotoCard() == 1) {
			if(imageDir == null) {
				imageDir = getFindPath().getPath("IMAGES");
			}
			File fileDir = new File(imageDir);
			if(fileDir.exists() && fileDir.isDirectory()) {
			File[] fileList=fileDir.listFiles();
			for (File file : fileList) {				
			    if (file.isFile()) {
			    	String fname = file.getName();
			    	String fnameonly[] = fname.split("\\.");
			    	String str1 = fnameonly[0];
			    	String str2 = fnameonly[1];
			    	if(str2.equalsIgnoreCase("JPG") || str2.equalsIgnoreCase("JPEG")) {

							if (str1.equals(primaryAcNo.trim())) {
								result = true;
								break;
							}

				    	
			    	}
			    }
			}
		}
		} 
		else  { // product.getPhotoCard() == 0
			record.setFlowStatus(DroolRecordBO.FLOW_PASS);
			result = true;
		}
		if(result == false) {
			record.setFlowStatus(DroolRecordBO.FLOW_HOLD);
			record.setRuleStatus(primaryAcNo.trim()+".jpg is not exist in the directory ");
//			processRecordAndEvent(primaryAcNo.trim()+".jpg is not exist in the directory ",holdStatus);
//			logger.info(primaryAcNo.trim()+".jpg is not exist in the directory ");
			logger.info(primaryAcNo.trim()+".jpg is not exist in the directory ");
		}
		else if(result == true) {
			record.setFlowStatus(DroolRecordBO.FLOW_PASS);
//			record.setFlowStatus(DroolRecordBO.FLOW_PASS);
//				processRecordAndEvent("Valid Record", approvedStatus);
//				logger.info(primaryAcNo.trim()+".jpg is not exist in the directory ");
				logger.info("non photo card");
			
		}
		return result;
	}	
	
	
	

	public Record getRec() {
		return rec;
	}

	public void setRec(Record rec) {
		this.rec = rec;
	}

	public final BranchDAOImpl getBranchDAO() {
		return branchDAO;
	}

	public final void setBranchDAO(BranchDAOImpl branchDAO) {
		this.branchDAO = branchDAO;
	}

	public final ProductDAOImpl getProductDAO() {
		return productDAO;
	}

	public final void setProductDAO(ProductDAOImpl productDAO) {
		this.productDAO = productDAO;
	}

	public final FindPathImpl getFindPath() {
		return findPath;
	}

	public final void setFindPath(FindPathImpl findPath) {
		this.findPath = findPath;
	}

	public FileConverterDAOImpl getFileConverterDAO() {
		return fileConverterDAO;
	}

	public void setFileConverterDAO(FileConverterDAOImpl fileConverterDAO) {
		this.fileConverterDAO = fileConverterDAO;
	}
	
//	public void processRecordAndEvent(String info,String status) {
//		logger.info("In process and record event");
//		RecordEventBO event = new RecordEventBO();
//		rec.setFinalRecordStatus(status);
//		Long recordId = getRec().processRawRecord(exchange);
//		logger.info("record id is : "+recordId);
//		event.setRecordId(recordId);
//		event.setEventId((long)3);
//		event.setEventDate(new Date());
//		event.setDescription(info);
//		getRec().processEvent(event);
//	}
	
}
