package com.indutech.gnd.dao;

import java.util.List;

import com.indutech.gnd.dto.Bank;
import com.indutech.gnd.dto.CreditCardDetails;

public interface FileConvertDAO {
	
	List<CreditCardDetails> getApprovedCreditCardDetails();
	
	List<CreditCardDetails> getAppGroup(String instituteId, String branchCode, String productCode);
	
	List<CreditCardDetails> getRejectedCreditCardDetails();
	
	List<CreditCardDetails> getRejGroup(String instituteId, String branchCode, String productCode);
	
	List<CreditCardDetails> getHoldCreditCardDetails();

	List<CreditCardDetails> getHoldGroup(String instituteId, String branchCode, String productCode);
	
	void changeStatusToAUFConverted(Long CreditCardDetails);
	
	String getPhotoIndicator(String product);

	String getAwbId(Long creditCardDetailsId);


	List<CreditCardDetails> getGroup(String instituteId, String branchCode,
			String productCode, Long fileid);



	long getRecordCount(String homeBranchCode);


	Bank getBankCodeByPrefix(String prefix);


	List<CreditCardDetails> getCreditCardDetails(Long recordStatus, Long fileId);

	List getBranchGroup(Long status, Long fileId);

	List<?> getLCPCBranchGroup(Long status, Long fileId);

	

}
