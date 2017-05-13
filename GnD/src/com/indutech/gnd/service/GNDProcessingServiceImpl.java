package com.indutech.gnd.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.indutech.gnd.bo.CreditCardDetailsBO;
import com.indutech.gnd.bo.drool.DroolRecordBO;
import com.indutech.gnd.dao.GNDDAOImpl;
import com.indutech.gnd.dto.CreditCardDetails;

@Component("gndProcessingService")
public class GNDProcessingServiceImpl implements GNDProcessingService {
	
	Logger logger = Logger.getLogger(GNDProcessingServiceImpl.class);

	@Autowired
	private GNDDAOImpl gndDAO;

	@Override
	@Transactional
	public void processData(Exchange exchange) {
		try {
			Map creditCardDetails = (Map) exchange.getIn().getBody();
			gndDAO.saveCardDetails(buildCreditCardDetails(creditCardDetails));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e);
			e.printStackTrace();
		}
	}
	

	private CreditCardDetails buildCreditCardDetails(Map creditCardDetailsMap) {
		CreditCardDetails creditCardDetails = new CreditCardDetails();
		SimpleDateFormat smdformatter = new SimpleDateFormat("dd/MM/yyyy");
		creditCardDetails.setAddr1((String)creditCardDetailsMap.get("ADDR1"));
		creditCardDetails.setAddr2((String)creditCardDetailsMap.get("ADDR2"));
		creditCardDetails.setAddr3((String)creditCardDetailsMap.get("ADDR3"));
		creditCardDetails.setAddr4((String)creditCardDetailsMap.get("ADDR4"));
		creditCardDetails.setCardStatus((String)creditCardDetailsMap.get("CARD STATUS"));
		creditCardDetails.setCity((String)creditCardDetailsMap.get("CITY"));
		creditCardDetails.setCustomerFirstName((String)creditCardDetailsMap.get("CUSTOMER FIRST NAME"));
		creditCardDetails.setCustomerId((String)creditCardDetailsMap.get("CUSTOMER ID"));
		creditCardDetails.setCustomerMiddleName((String)creditCardDetailsMap.get("CUSTOMER MIDDLE NAME"));
		creditCardDetails.setCustomerSurName((String)creditCardDetailsMap.get("CUSTOMER SUR NAME"));
		String dateOfbirth =(String)creditCardDetailsMap.get("DATE OF BIRTH");
		String registrationDate =(String)creditCardDetailsMap.get("REGISTRATION DATE");
		try {
			if(dateOfbirth != null) {
				if(dateOfbirth.contains("/"))
					creditCardDetails.setDateOfBirth(smdformatter.parse(dateOfbirth));
			}
			if(registrationDate !=null) {
				if(registrationDate.contains("/"))
					creditCardDetails.setRegistrationDate(smdformatter.parse(registrationDate));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.error(e);
			e.printStackTrace();
		}
		creditCardDetails.setEmail((String)creditCardDetailsMap.get("EMAIL"));
		creditCardDetails.setEmbossName((String)creditCardDetailsMap.get("EMBOSS NAME"));
		creditCardDetails.setFathersFirstName((String)creditCardDetailsMap.get("FATHERS FIRST NAME"));
		creditCardDetails.setFax((String)creditCardDetailsMap.get("FAX"));
		creditCardDetails.setFourthLinePrintingData((String)creditCardDetailsMap.get("FOURTH LINE PRINTING DATA"));
		creditCardDetails.setHomeBranchCircleCode((String)creditCardDetailsMap.get("HOME BRANCH CIRCLE CODE"));
		creditCardDetails.setHomeBranchCode((String)creditCardDetailsMap.get("HOME BRANCH CODE"));
		creditCardDetails.setHomePhone((String)creditCardDetailsMap.get("HOME PHONE"));
		creditCardDetails.setInstitutionId((String)creditCardDetailsMap.get("INSTUTION ID"));
		creditCardDetails.setIsdCode((String)creditCardDetailsMap.get("ISD CODE"));
		creditCardDetails.setIssueBranchCircleCode((String)creditCardDetailsMap.get("ISSUE BRANCH CIRCLE CODE"));
		creditCardDetails.setIssueBranchCode((String)creditCardDetailsMap.get("ISSUE BRANCH CODE"));
		creditCardDetails.setMobileNo((String)creditCardDetailsMap.get("MOBILE NO"));
		creditCardDetails.setMotherMaidenName((String)creditCardDetailsMap.get("MOTHER MAIDEN NAME"));
		creditCardDetails.setOfficePhone((String)creditCardDetailsMap.get("OFFICE PHONE"));
		creditCardDetails.setPin((String)creditCardDetailsMap.get("PIN"));
		creditCardDetails.setPrimaryAccountProductCode((String)creditCardDetailsMap.get("PRIMARY ACCOUNT PRODUCT CODE"));
		creditCardDetails.setPrimaryAcctNo((String)creditCardDetailsMap.get("PRIMARY ACCT NO"));
		creditCardDetails.setPrimaryAcctSurviour((String)creditCardDetailsMap.get("PRIMARY ACCT SURVIOUR"));
		creditCardDetails.setPrimaryAcctType((String)creditCardDetailsMap.get("PRIMARY ACCT TYPE"));
		creditCardDetails.setProduct((String)creditCardDetailsMap.get("PRODUCT"));
		creditCardDetails.setSecondaryAccount1ProductCode((String)creditCardDetailsMap.get("SECONDARY ACCOUNT1 PRODUCT CODE"));
		creditCardDetails.setSecondaryAccount2ProductCode((String)creditCardDetailsMap.get("SECONDARY ACCOUNT2 PRODUCT CODE"));
		creditCardDetails.setSecondaryAcct1((String)creditCardDetailsMap.get("SECONDARY ACCT1"));
		creditCardDetails.setSecondaryAcct2((String)creditCardDetailsMap.get("SECONDARY ACCT2"));
		creditCardDetails.setSecondaryAcct2Type((String)creditCardDetailsMap.get("SECONDARY ACCT2 TYPE"));
		creditCardDetails.setSecondaryAcctSurviour((String)creditCardDetailsMap.get("SECONDARY ACCT1 SURVIOUR"));
		creditCardDetails.setSecondarySurviour((String)creditCardDetailsMap.get("SECONDARY_SURVIOUR"));
		creditCardDetails.setSerialNo((String)creditCardDetailsMap.get("SERIAL NO"));
		creditCardDetails.setYearOfMarriage((String)creditCardDetailsMap.get("YEAR OF MARRIAGE"));
		creditCardDetails.setYearOfPassingSSC((String)creditCardDetailsMap.get("YEAR OF PASSING SSC"));
		return creditCardDetails;
	}

	
}
