package com.indutech.gnd.vali;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.camel.Exchange;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.indutech.gnd.bo.RecordEventBO;
import com.indutech.gnd.dao.BranchDAOImpl;
import com.indutech.gnd.dao.GNDDAOImpl;
import com.indutech.gnd.dto.Branch;
import com.indutech.gnd.dto.CreditCardDetails;
import com.indutech.gnd.enumTypes.RecordStatus;
import com.indutech.gnd.records.Record;

public class RecordRuleSet2 {
	
	Logger logger = Logger.getLogger(RecordRuleSet2.class);
	
	common.Logger log = common.Logger.getLogger(RecordRuleSet2.class);
	
	@Autowired
	private GNDDAOImpl gndDAO;	
	
	@Autowired
	private BranchDAOImpl branchDAO;
	
	@Autowired
	private Record rec;
	
	private HashMap<String, Object> record = null;
	
	RecordStatus stat2 = RecordStatus.valueOf("REJECT");
	String rejectedStatus = stat2.getRecordStatus();
	
	private int slen;
	private static int currYear,fromYear=1990;
	
	private String embossName;
	private String pincode;
	private Exchange exchange;

	
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

	public final GNDDAOImpl getGndDAO() {
		return gndDAO;
	}

	public final void setGndDAO(GNDDAOImpl gndDAO) {
		this.gndDAO = gndDAO;
	}	
	
	@SuppressWarnings("unchecked")
	public void recordRuleSet2Start(Exchange exchange) {
		this.exchange = exchange;
		record = (HashMap<String, Object>) exchange.getIn().getBody();
	}
	public boolean isSerialNull_rule_5a1()	{
		
		String serialNumber = (String) record.get("SERIAL NO");
		if(!(serialNumber.trim().isEmpty()))	{
			return true;
		}
		processRecordAndEvent("Serial Number is empty");
		logger.info("Serial Number is empty ..... so status assigned as rejected");
		return false;
	}
	public boolean isInstitutionIdNull_rule_5b1()	{
	
		String instituteId = (String) record.get("INSTUTION ID");
		if(!(instituteId.trim().isEmpty()))	{
			return true;
		}
		processRecordAndEvent("Institute id is empty");
		logger.info("Institute id is empty ..... so status assigned as rejected");
		return false;
	}
	public boolean isHomeBranchCodeNull_rule_5c1()	{

		String homeBranchCode = (String) record.get("HOME BRANCH CODE");
		if(!(homeBranchCode.trim().isEmpty()))	{
			return true;
		}
		processRecordAndEvent("HOME BRANCH CODE is empty");
		logger.info("HOME BRANCH CODE is empty ..... so status assigned as rejected");
		return false;		
	}
//	@Transactional
	public boolean isHomeBranchCodeValid_rule_5c2()	{
//		boolean branchCompare = false;
//		//TODO:sreenu check from database
//		String homeBranchCode = (String) record.get("HOME BRANCH CODE");
//		List<String> list = getGndDAO().getHomeBranchCode(homeBranchCode.trim());
//		if(list.size() == 0) {
//				branchCompare = true;
//		}			
//		else {
//			logger.info("Record with home branch code is already present in the database");
//		}
//		return branchCompare;
		return true;
	}
	public boolean isIssueBranchCodeNull_rule_5d1()	{

		String issueBranchCode = (String) record.get("ISSUE BRANCH CODE");
		if(!(issueBranchCode.trim().isEmpty()))	{
			return true;
		}
		processRecordAndEvent("ISSUE BRANCH CODE is empty");
		logger.info("ISSUE BRANCH CODE is empty ..... so status assigned as rejected");		
		return false;
	
	}
	
//	//@Transactoinal
	public boolean isIssueBranchCodeValid_rule_5e2() {
//		//TODO: Sreenu check with database
//		boolean issueCodeCompare = false;
//		String issueBranchCode = (String) record.get("ISSUE BRANCH CODE");
//		List<String> branchCode = getGndDAO().getIssueBranchCode(issueBranchCode.trim());
//		logger.info("hi issuebranch code values are : "+branchList);
//		if(branchCode.size() == 0) {
//			issueCodeCompare = true;
//		}
//		else {
//			logger.info("issue branch code : "+issueBranchCode.trim()+" is already present in Database");
//			logger.info("issue branch code : "+issueBranchCode.trim()+" is already present in Database");
//			
//		}
//		return issueCodeCompare;
		return true;
	}
	public boolean isPrimAcctNoNull_rule_5f1()	{

		String primaryAcNo = (String) record.get("PRIMARY ACCT NO");
		if(!(primaryAcNo.trim().isEmpty()))	{
			return true;
		}
		processRecordAndEvent("PRIMARY ACCT NO is empty");
		logger.info("PRIMARY ACCT NO is empty ..... so status assigned as rejected");
		return false;
	}
	public boolean isPrimaryAcctTypeNull_rule_5g1()	{

		String primaryAcType = (String) record.get("PRIMARY ACCT TYPE");
		if(!(primaryAcType.trim().isEmpty()))	{
			return true;
		}	
		processRecordAndEvent("PRIMARY ACCOUNT TYPE is empty");
		logger.info("PRIMARY ACCT TYPE is empty ..... so status assigned as rejected");
		return false;
	}
	public boolean isPrimaryAcctTypeValid_rule_5g2()	{
	
		String primaryAcType = (String) record.get("PRIMARY ACCT TYPE");
		if(primaryAcType.equals("10") || primaryAcType.equals("20"))
		{
			return true;
		}
		processRecordAndEvent("PRIMARY ACCOUNT TYPE is not valid");
		logger.info("PRIMARY ACCT TYPE is not valid ..... so status assigned as rejected");
		return false;
	}
	public boolean isE_S_FlagValid_rule_5h()	{
		//need to be comfirm with sir
		return true;
	}
	public boolean isPrimarySecondaryAccSame_rule_5i()	{
		//need to be comfirm with sir
		return true;
	}
	public boolean isSecondaryAcctTypeValid_rule_5j()	{
		
		String secondaryAcType = (String) record.get("SECONDARY ACCT1 TYPE");
		if(secondaryAcType.equals("SA") || secondaryAcType.equals("CA"))
		{
			return true;
		}
		processRecordAndEvent("SECONDARY ACCOUNT1 TYPE is not valid");
		logger.info("SECONDARY ACCT1 TYPE is not valid ..... so status assigned as rejected");
		return false;
	}
	public boolean isEmbossaNameValidLength_rule_5k1()	{
	
		embossName = (String) record.get("EMBOSS NAME");
		
		String firstName = (String) record.get("CUSTOMER FIRST NAME");
		String surName = (String) record.get("CUSTOMER SUR NAME");
		
		StringBuffer resStr = new StringBuffer();
		String resultantString = resStr.append(surName+firstName.charAt(0)).toString();
		resultantString =resultantString.substring(0, 27);
		
		int length = embossName.trim().length();
		
		if(length==0)
		{
			embossName = resultantString;
			return true;
		}
		
		if(length>3 && length<=25)	{
			return true;
		}
		processRecordAndEvent("EMBOSS NAME length is not valid");
		logger.info("EMBOSS NAME length is not valid ..... so status assigned as rejected");
		return false;
	}
	public boolean isEmbossaNameInValid_rule_5k2()	{
	
		embossName = (String) record.get("EMBOSS NAME");		
		Pattern pattern=Pattern.compile("^[A-Za-z0-9\\s]*$");
		
		if((pattern.matcher(embossName.trim()).matches()))	{
			return true;
		}	
		processRecordAndEvent("EMBOSS NAME is not valid");
		logger.info("EMBOSS NAME is not valid ..... so status assigned as rejected");
		return false;
	}
	public boolean isEmbossaNameStartSpecial_rule_5k3()	{

		embossName = (String) record.get("EMBOSS NAME");	
		char prefix=embossName.charAt(0);
		Pattern p=Pattern.compile("^[0-9]$");
		
		if(!(p.matcher(prefix+"").matches()))	{
			return true;
		}
		processRecordAndEvent("EMBOSS NAME prefix is not valid");
		logger.info("EMBOSS NAME prefix is not valid ..... so status assigned as rejected");
		return false;
	}
	public boolean isFirstNameNull_rule_5l1()	{
		String firstName = (String) record.get("CUSTOMER FIRST NAME");
		if(!(firstName.trim().isEmpty()))	{
			return true;
		}
		processRecordAndEvent("Customer first name is empty");
		logger.info("First Name is null ..... so status assigned as rejected");
		return false;
	}
	public boolean isMiddleNameNull_rule_5m1()	{
		
		String middleName = (String) record.get("CUSTOMER MIDDLE NAME");
		if(!(middleName.trim().isEmpty()))	{
			return true;
		}
		processRecordAndEvent("Customer middle name is empty");
		logger.info("Middle Name is null ..... so status assigned as rejected");
		return false;
	}
	public boolean isSurNameNull_rule_5n1()	{
		
		String surName = (String) record.get("CUSTOMER SUR NAME");
		if(!(surName.trim().isEmpty()))	{
			return true;
		}
		processRecordAndEvent("customer surname is empty");
		logger.info("surName is null ..... so status assigned as rejected");
		return false;
	}
	public boolean isAddres1Null_rule_5o1()	{

		String address1 = (String) record.get("ADDR1");
		if(!(address1.trim().isEmpty()))	{
			return true;
		}
		processRecordAndEvent("Address part1 is empty");
		logger.info("Address1 is null ..... so status assigned as rejected");
		return false;
	}
	public boolean isAddres2_3_4Valid_rule_5p1()	{
		
		String address2 = (String) record.get("ADDR2");
		String address3 = (String) record.get("ADDR3");
		String address4 = (String) record.get("ADDR4");
		if((!(address2.trim().isEmpty())) || (!(address3.trim().isEmpty())) || (!(address4.trim().isEmpty())))	{
			return true;
		}
		processRecordAndEvent("Address fileds are empty");
		logger.info("Address fields are null ..... so status assigned as rejected");
		return false;	
	}
	public boolean isAddres1_2_3_4Valid_rule_5p2()	{
		
//		if((as1.trim().isEmpty()) && (as2.trim().isEmpty()) &&(as3.trim().isEmpty()) && (as4.trim().isEmpty()))	{
//			return false;
//		}
//		return true;
		return true;
	}
	public boolean isAddres1_2_3_4ValidForiegn_rule_5p3()	{
		return true;
	}
	public boolean isPinMissing_rule_5q1()	{
		
		pincode = (String) record.get("PIN");
		if(!(pincode.trim().isEmpty()))	{
			return true;
		}
		processRecordAndEvent("pincode is empty");
		logger.info("pincode is empty ..... so status assigned as rejected");
		return false;
	}
	public boolean isPinLength_rule_5q2()	{		
		
		//TODO:only for india
		Pattern p=Pattern.compile("^[1-9]\\d{5}$");		
		pincode = (String) record.get("PIN");
		Matcher matcher = p.matcher(pincode.trim());
		slen=pincode.trim().length();
		if( slen == 6 && matcher.matches())	{
			return true;
		}	
		processRecordAndEvent("pincode is invalid");
		logger.info("pincode is not valid ..... so status assigned as rejected......................");
		return false;
	}
	public boolean isPinNumeric_rule_5q3()	{
		//TODO:check number
		pincode = (String) record.get("PIN");
		Pattern p=Pattern.compile("^[1-9]\\d{5}$");	
		if((p.matcher(pincode.trim()).matches()))	{
			return true;
		}
		processRecordAndEvent("invalid pincode");
		logger.info("you entered invaild pincode ..... so status assigned as rejected......................");

		return false;
	
		
	}
	//Number not requried
	public boolean isPH_OFFNull_rule_5r1()	{
		//TODO:also check numeric
		
		Pattern p=Pattern.compile("^\\d{10}$");	
		String phoneNo = (String) record.get("OFFICE PHONE");
		
		if((phoneNo.trim().isEmpty()))	{
			return true;
		}
		
		if(p.matcher(phoneNo.trim()).matches())	{
			return true;
		}
		processRecordAndEvent("invalid phone number");
		logger.info("invalid phone number ..... so status assigned as rejected......................");
		return false;
	}
	
	public boolean isPH_HOMNull_rule_5s1()	{
		//TODO:same as 5r1
		String homePhone = (String) record.get("HOME PHONE");
		Pattern p=Pattern.compile("^[0-9]*$");	
		if((!(homePhone.trim().isEmpty())) || p.matcher(homePhone.trim()).matches())	{
			return true;
		}
		processRecordAndEvent("invalid home phone number");
		logger.info("invalid home phone number ..... so status assigned as rejected......................");

		return false;
	}
	public boolean isProductNull_rule_5t()	{

		String product = (String) record.get("PRODUCT");
		if(!(product.trim().isEmpty()))	{
			return true;
		}
		processRecordAndEvent("product code is empty");
		logger.info("product is empty ..... so status assigned as rejected......................");

		return false;
	}
	public boolean isCardStatusNull_rule_5u1()	{

		String cardStatus =(String) record.get("CARD STATUS");
		if(!(cardStatus.trim().isEmpty()))	{
			return true;
		}
		processRecordAndEvent("card status is empty");
		logger.info("card status is empty ..... so status assigned as rejected......................");		
		return false;
	}

	public boolean isYrRegistrationValid_rule_5v1()	{
		//todo
		
		boolean validateYear = false;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String regdDate = (String) record.get("REGISTRATION DATE");
				if(regdDate.trim().isEmpty())	{
			return true;
		}
		Date date = null;
		try {
			 date = sdf.parse(regdDate.trim());
		} catch (ParseException e) {
			processRecordAndEvent("Invalid Registration Date");
			logger.info("invalid Invalid Registration Date");
			return false;
		}
		if(date != null) {
			String split[] = regdDate.trim().split("/");
			int year = Integer.parseInt(split[2]);
			validateYear = validateYear(year);
			if(validateYear == false) {
				processRecordAndEvent("Registration year is not in range");
				logger.info("Registration year is not valid");
			}
		}
		
			return validateYear;		

	}
	//FaxNumber not requried
	public boolean isPH_FaxNull_rule_5w1()	{
		
		Pattern p=Pattern.compile("^\\d{10}$");	
		String fax = (String) record.get("FAX");
		if((fax.trim().isEmpty()))	{
			return true;
		}

		if(p.matcher(fax.trim()).matches())	{
			return true;
		}
		
		processRecordAndEvent("Fax is empty");
		logger.info("Fax is empty ..... so status assigned as rejected......................");		

		return false;
	}
	
	@SuppressWarnings("unused")
	public boolean isCardStatusValid_rule_5x2()	{
		//TODO:sreenu check from database
//		boolean cardStatusCompare = false;
//		String cardstatus = (String) record.get("CARD STATUS");
//		List<String> list = getGndDAO().getCardStatus();                            
//		logger.info("hi card status list is : "+list);
//		if(list.size() != 0){
//			for(String cardString : this.cardStatus) {
//				if(cardStatus.equals(cardstatus.trim())) {
//					cardStatusCompare = true;
//				}
//			}
//		}
//		else {
//			cardStatusCompare = true;
//		}
		return true;
		
	}
	public boolean isDOBValidRange_rule_5y1()	{
		boolean validateDoB = false;
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		String dobDate = (String) record.get("DATE OF BIRTH");
		if(dobDate.trim().isEmpty())	{
			return true;
		}
		Date date = null;
		try {
			 date = sdf.parse(dobDate.trim());
		} catch (ParseException e) {
			processRecordAndEvent("Invalid Date of birth");
			logger.info("invalid date of birth");
			return false;
		}
	
		if(date != null) {
			int year = Integer.parseInt(dobDate.trim().substring(4,8));
			validateDoB = validateYear(year);
			if(validateDoB == false) {
				processRecordAndEvent("Date of birth year is not in range");
				logger.info("Date of birth year is not in range");
			}
		}
		
		return validateDoB;
	}
	
	public boolean isYrSSCValidRange_rule_5z1()	{
		boolean sscyear = false;
		String sscPassYear = (String) record.get("YEAR OF PASSING SSC");
		if(sscPassYear.trim().isEmpty())	{
			return true;
		}
		if(sscPassYear.trim() != null && sscPassYear.trim().length() == 4) {
			sscyear = validateYear(Integer.parseInt(sscPassYear.trim()));
			if(sscyear == false) {
				processRecordAndEvent("SSC passing year is invalid");
				logger.info("SSC passing year is invalid ..... so status assigned as rejected......................");		
			}
		}
		
		return sscyear;
		
	}
	public boolean isrMarriageValidRange_rule_5aa1()	{
		boolean marriedYear = false;
		String marriageYear = (String) record.get("YEAR OF MARRIAGE");
		if(marriageYear.trim().isEmpty())	{
			return true;
		}
		if(marriageYear.trim() != null && marriageYear.trim().length() == 4) {
			marriedYear = validateYear(Integer.parseInt(marriageYear.trim()));
			if(marriedYear == false) {
				processRecordAndEvent("Marriage year is invalid");
				logger.info("Marriage year is invalid ..... so status assigned as rejected......................");		
			}
		}
		return marriedYear;
	
	}
	
//	//@Transactoinal
	public boolean isProduct4thLine_rule_5ab()	{
//		//TODO:sreenu check from database
//		boolean forthLineRuleCompare = false;
//		String forthLine=(String) record.get("FOURTH LINE PRINTING DATA");
//		List<String> list = getGndDAO().getForthLine();
//		logger.info("hi forth line values are : "+ list);
//		if(list.size() != 0) {
//			for(String forthline : this.forthLineRule) {
//				if(forthline.equals(forthLine.trim())) {
//					forthLineRuleCompare = true;
//					break;
//				}
//			}
//		}
//		else {
//			
//		}
		return true;
	}
	public boolean isBINMissing_rule_5ac1()	{
		return true;
	}
	public boolean isBINMismatch_rule_5ad1()	{
		return true;
	}
	
	public boolean validateYear(int year) {				
		currYear= Calendar.getInstance().get(Calendar.YEAR);		
		if((year >fromYear) && (year< currYear)) {
			return true;
		}	
		return false;
	}
	
	public void processRecordAndEvent(String info) {
		RecordEventBO event = new RecordEventBO();
		getRec().setRecordRejectedStatus();
//		Long recordId = getRec().processRawRecord(exchange);
//		event.setRecordId(recordId);
		event.setEventId((long)2);
//		event.setEventDate(new Date());
		event.setDescription(info);
		
		getRec().processEvent(event);
	}
	
}
