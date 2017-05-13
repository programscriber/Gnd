package com.indutech.gnd.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.util.POILogFactory;
import org.apache.poi.util.POILogger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.indutech.gnd.bo.BankBO;
import com.indutech.gnd.bo.BranchBO;
import com.indutech.gnd.bo.DistrictBO;
import com.indutech.gnd.bo.StateBO;
import com.indutech.gnd.dao.BranchDAOImpl;
import com.indutech.gnd.dto.Bank;
import com.indutech.gnd.dto.Branch;
import com.indutech.gnd.dto.District;
import com.indutech.gnd.dto.State;
import com.indutech.gnd.enumTypes.Status;
import com.indutech.gnd.util.DataInitialization;
import com.indutech.gnd.util.StringUtil;

public class BranchServiceImpl implements BranchService {
	
	Logger logger = Logger.getLogger(BranchServiceImpl.class);
	
	//common.Logger logger.= common.Logger.getLogger(BranchServiceImpl.class);
	
	POILogger log = POILogFactory.getLogger(XSSFWorkbook.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private BranchDAOImpl branchDAO;	
	
	private String shortCode;
	private String branchName;
	private String liveStatus;
	private String ifscCode;
	private String micr;
	private String phoneNumber;
	private String emailAddress;
	private String tan;
	private String address1;
	private String address2;
	private String address3;
	private String address4;
	private String branchType;
	private Long  districtCode;
	private String districtName;
	private String stateName;
	private Long stateCode;
	private String bankId;
	private String bankName;
	private String circleCode;
	private String circle;
	private Long network;
	private Long modCode;
	private String module;
	private Long region;
	private Long popCode;
	private String popGroup;
	private String bpr;
	private String gcc;
	private String lcpcName;
	private String lcpcBranch;
	private String pincode;
	
	
	@Transactional
	@Override
	public String importBranchXls(MultipartFile file) {
		
		String result = null;
		Status stat = Status.valueOf("ACTIVE");
		String status = stat.getStatus();
		
		try {
			File convFile = new File( file.getOriginalFilename());
	        file.transferTo(convFile);
	        InputStream is = new FileInputStream(convFile);
	        Workbook workbook = WorkbookFactory.create(is);
			
			Sheet sheet = workbook.getSheetAt(0);
			if (sheet != null) {
				Iterator<?> rowIterator = sheet.rowIterator();
				rowIterator.next();
				int i = 1;
				int j = 1;
				while (rowIterator.hasNext()) {
					logger.info(i);
					XSSFRow row = (XSSFRow) rowIterator.next(); 
					
					XSSFCell bcode      = row.getCell(0);
					XSSFCell bankname   = row.getCell(1);
					XSSFCell bname      = row.getCell(2);
					XSSFCell scode      = row.getCell(3);
					XSSFCell circleCode = row.getCell(4);
					XSSFCell circle     = row.getCell(5);
					XSSFCell network    = row.getCell(6);
					XSSFCell modCode    = row.getCell(7);
					XSSFCell module     = row.getCell(8);
					XSSFCell region     = row.getCell(9);
					XSSFCell popCode    = row.getCell(10);
					XSSFCell popGroup   = row.getCell(11);
					XSSFCell live       = row.getCell(12);
					XSSFCell ifsc       = row.getCell(13);
					XSSFCell stcode     = row.getCell(14);
					XSSFCell stname     = row.getCell(15);
					XSSFCell dcode      = row.getCell(16);
					XSSFCell dname      = row.getCell(17);
					XSSFCell phone      = row.getCell(18);
					XSSFCell btype      = row.getCell(19);
					XSSFCell micr       = row.getCell(20);   
					XSSFCell bpr        = row.getCell(21); 
					XSSFCell email      = row.getCell(22); 					
					XSSFCell addr1      = row.getCell(23);
					XSSFCell addr2      = row.getCell(24);
					XSSFCell addr3      = row.getCell(25);
					XSSFCell addr4      = row.getCell(26); 
					XSSFCell tan        = row.getCell(27);   
					XSSFCell gcc        = row.getCell(28);
					XSSFCell pinCode    = row.getCell(29);
					XSSFCell LCPCName   = row.getCell(30);
					XSSFCell LCPCBranch = row.getCell(31);
					 
					try {
						scode.setCellType(Cell.CELL_TYPE_STRING);
						shortCode = scode.getStringCellValue();
						
						bname.setCellType(Cell.CELL_TYPE_STRING);
						branchName = bname.getStringCellValue();
						
						live.setCellType(Cell.CELL_TYPE_STRING);
						liveStatus = live.getStringCellValue();

						ifsc.setCellType(Cell.CELL_TYPE_STRING);
						ifscCode = ifsc.getStringCellValue();
						
						micr.setCellType(Cell.CELL_TYPE_STRING);
						this.micr = micr.getStringCellValue(); 
						
						phone.setCellType(Cell.CELL_TYPE_STRING);
						phoneNumber = phone.getStringCellValue();
						
						email.setCellType(Cell.CELL_TYPE_STRING);
						emailAddress = email.getStringCellValue();
						
						tan.setCellType(Cell.CELL_TYPE_STRING);
						this.tan = tan.getStringCellValue();

						addr1.setCellType(Cell.CELL_TYPE_STRING);
						address1 = addr1.getStringCellValue();

						addr2.setCellType(Cell.CELL_TYPE_STRING);
						address2 = addr2.getStringCellValue();

						addr3.setCellType(Cell.CELL_TYPE_STRING);
						address3 = addr3.getStringCellValue();

						addr4.setCellType(Cell.CELL_TYPE_STRING);
						address4 = addr4.getStringCellValue();

						btype.setCellType(Cell.CELL_TYPE_STRING);
						branchType = btype.getStringCellValue();

						dcode.setCellType(Cell.CELL_TYPE_NUMERIC);
						districtCode = (long) dcode.getNumericCellValue();

						dname.setCellType(Cell.CELL_TYPE_STRING);
						districtName = dname.getStringCellValue();

						stcode.setCellType(Cell.CELL_TYPE_NUMERIC);
						stateCode = (long) stcode.getNumericCellValue();

						stname.setCellType(Cell.CELL_TYPE_STRING);
						stateName = stname.getStringCellValue();

						bcode.setCellType(Cell.CELL_TYPE_STRING);
						bankId = bcode.getStringCellValue();

						bankname.setCellType(Cell.CELL_TYPE_STRING);
						bankName = bankname.getStringCellValue();

						circleCode.setCellType(Cell.CELL_TYPE_STRING);
						this.circleCode =  circleCode.getStringCellValue();

						circle.setCellType(Cell.CELL_TYPE_STRING);
						this.circle = circle.getStringCellValue();
						
						network.setCellType(Cell.CELL_TYPE_NUMERIC);
						this.network = (long)network.getNumericCellValue();

						modCode.setCellType(Cell.CELL_TYPE_NUMERIC);
						this.modCode = (long) modCode.getNumericCellValue();

						module.setCellType(Cell.CELL_TYPE_STRING);
						this.module = module.getStringCellValue();
						
						region.setCellType(Cell.CELL_TYPE_NUMERIC);
						this.region = (long) region.getNumericCellValue();

						popCode.setCellType(Cell.CELL_TYPE_NUMERIC);
						this.popCode = (long) popCode.getNumericCellValue();
						
						popGroup.setCellType(Cell.CELL_TYPE_STRING);
						this.popGroup = popGroup.getStringCellValue();
						
						bpr.setCellType(Cell.CELL_TYPE_STRING);
						this.bpr = bpr.getStringCellValue();

						gcc.setCellType(Cell.CELL_TYPE_STRING);
						this.gcc = gcc.getStringCellValue();
						
						LCPCName.setCellType(Cell.CELL_TYPE_STRING);
						lcpcName = LCPCName.getStringCellValue();
						
						LCPCBranch.setCellType(Cell.CELL_TYPE_STRING);
						lcpcBranch = LCPCBranch.getStringCellValue();
						
						pinCode.setCellType(Cell.CELL_TYPE_STRING);
						pincode = pinCode.getStringCellValue();
						
						

						logger.info("In try block");
 						logger.info("short code is : "+shortCode);
						StateBO stateBO = new StateBO();
						stateBO.setStateCode(stateCode);
						stateBO.setStateName(stateName);	
						
						logger.info("short code is : "+shortCode);
						
						if(!StringUtil.isEMptyOrNull(shortCode)) {
							getBranchDAO().saveOrUpdateState(buildState(stateBO));
							
							DistrictBO district = new DistrictBO();
							district.setDistrictCode(districtCode);
							district.setDistrictName(districtName);
							district.setStateId(stateCode);
							
							getBranchDAO().saveOrUpdateDistrict(buildDistrict(district));
							
							BankBO bankBO = new BankBO();
							bankBO.setBankId(Long.parseLong(bankId));
							bankBO.setShortCode(bankName);
							
							getBranchDAO().saveOrUpdateBank(buildBank(bankBO));
							
							BranchBO branchBO = new BranchBO();
							branchBO.setShortCode(shortCode);
							branchBO.setBranchName(branchName);
							branchBO.setLiveStatus(liveStatus);
							branchBO.setIfscCode(ifscCode);
							branchBO.setMicr(this.micr);
							branchBO.setPhoneNumber(phoneNumber);
							branchBO.setEmailAddress(emailAddress);
							branchBO.setTan(this.tan);
							branchBO.setAddress1(address1);
							branchBO.setAddress2(address2);
							branchBO.setAddress3(address3);
							branchBO.setAddress4(address4);
							branchBO.setBranchType(branchType);
							branchBO.setDistrictCode(districtCode);
							branchBO.setStateCode(stateCode);
							branchBO.setBankId(Long.parseLong(bankId));
							branchBO.setModcode(this.modCode);
							branchBO.setModule(this.module);
							branchBO.setCircleCode(this.circleCode);
							branchBO.setCircle(this.circle);
							branchBO.setNetwork(this.network);
							branchBO.setRegion(this.region);
							branchBO.setPopCode(this.popCode);
							branchBO.setPopGroup(this.popGroup);
							branchBO.setStatus(Long.parseLong(status));
							branchBO.setBpr(this.bpr);
							branchBO.setGcc(this.gcc);
							branchBO.setLcpcBranch(lcpcBranch);
							branchBO.setLcpcName(lcpcName);
							branchBO.setPinCode(pincode);
							branchBO.setIsNonCardIssueBranch(BranchService.NON_CARD_ISSUE_BRANCH_ACTIVE);
							Branch branch = buildBranch(branchBO);
							List<Branch> list = getBranchDAO().getBranch(shortCode, Long.parseLong(bankId));
							
							logger.info("row count is : "+i);
							
							if(list.size() > 0) {		
//								getBranchDAO().saveBranch(branch);
//							}
								j++;
								logger.info("branch is already present in DB and its short code is : "+shortCode);
								@SuppressWarnings("rawtypes")
								Iterator itr = list.iterator();
								while(itr.hasNext()) {
									Branch branchCheck = (Branch) itr.next();
									branch.setBranchId(branchCheck.getBranchId());
									getSessionFactory().getCurrentSession().evict(branchCheck);
									getBranchDAO().saveBranch(branch);
									logger.info("Now updated successfully");
								}
							}
							else {
								i++;
								getBranchDAO().saveBranch(branch);
							}
						}
						DataInitialization.branchData = null;
						result = (i-1) + " branches inserted and "+(j-1)+" branches updated into databse" ;
					} 
					
					catch (Exception ex) {
						result = ex.getMessage();
						logger.error(ex);
						ex.printStackTrace();
						continue;
					}
				}
			} else {
			}
		} catch (Exception fne) {
			logger.error("Unable to process the data from Excel " +fne);
			
			fne.printStackTrace();
		} 
		
		return result;
	}
	
	public State buildState(StateBO stateBO) {
		State state = new State();
		state.setStateCode(stateBO.getStateCode());
		state.setStateName(stateBO.getStateName());
		state.setStatus(stateBO.getStatus());
		return state;
	}
	
	public District buildDistrict(DistrictBO districtBO) {
		District district = new District();
		district.setDistrictCode(districtBO.getDistrictCode());
		district.setDistrictName(districtBO.getDistrictName());
		district.setStateId(districtBO.getStateId());
		return district;
	}
	
	public Bank buildBank(BankBO bankBO) {
		Bank bank = new Bank();
		bank.setBankId(bankBO.getBankId());
		bank.setShortCode(bankBO.getShortCode());
		bank.setBankName(bankBO.getBankName());
		bank.setStatus(bankBO.getStatus());
		return bank;
	}
	
	public Branch buildBranch(BranchBO branchBO) {
		
		Branch branch = new Branch();
		
		branch.setShortCode(branchBO.getShortCode());
		branch.setBranchName(branchBO.getBranchName());
		branch.setLiveStatus(branchBO.getLiveStatus());
		branch.setIfscCode(branchBO.getIfscCode());
		branch.setMicr(branchBO.getMicr());
		branch.setPhoneNumber(branchBO.getPhoneNumber());
		branch.setEmailAddress(branchBO.getEmailAddress());
		branch.setTan(branchBO.getTan());
		branch.setAddress1(branchBO.getAddress1());
		branch.setAddress2(branchBO.getAddress2());
		branch.setAddress3(branchBO.getAddress3());
		branch.setAddress4(branchBO.getAddress4());
		branch.setBranchType(branchBO.getBranchType());
		branch.setDistrictCode(branchBO.getDistrictCode());
		branch.setStateCode(branchBO.getStateCode());
		branch.setBankId(branchBO.getBankId());
		branch.setStatus(branchBO.getStatus());
		branch.setModcode(branchBO.getModcode());
		branch.setModule(branchBO.getModule());
		branch.setCircleCode(branchBO.getCircleCode());
		branch.setCircle(branchBO.getCircle());
		branch.setNetwork(branchBO.getNetwork());
		branch.setRegion(branchBO.getRegion());
		branch.setPopCode(branchBO.getPopCode());
		branch.setPopGroup(branchBO.getPopGroup());
		branch.setBpr(branchBO.getBpr());
		branch.setGcc(branchBO.getGcc());
		branch.setLcpcBranch(branchBO.getLcpcBranch());
		branch.setLcpcName(branchBO.getLcpcName());
		branch.setPinCode(branchBO.getPinCode());
		branch.setIsNonCardIssueBranch(branchBO.getIsNonCardIssueBranch());
		return branch;
	}
	@Transactional
	@Override
	public List<Branch> getBankBranchDetails(Long bankId){
		
	List<Branch> branchDetails =	getBranchDAO().getBankBranch(bankId);
		
		return  (List<Branch>) branchDetails;
		
	}
	public final BranchDAOImpl getBranchDAO() {
		return branchDAO;
	}

	public final void setBranchDAO(BranchDAOImpl branchDAO) {
		this.branchDAO = branchDAO;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<Branch> branchNameDetails(Long branchNameCode) {
		List<Branch> branchNames = getBranchDAO().branchNames(branchNameCode);
		return (List<Branch>) branchNames;
	}
	
	
	
}


