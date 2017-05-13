package com.indutech.gnd.dao;

import java.util.List;

import com.indutech.gnd.dto.CoreFiles;
import com.indutech.gnd.dto.CreditCardDetails;
import com.indutech.gnd.dto.EmbossaFormat;
import com.indutech.gnd.dto.RecordEvent;

public interface GNDDAO {
	
	Long saveCardDetails(CreditCardDetails cardDetaisl);


	List<String> getHomeBranchCode(String homeBranchCode);


	List<String> getCardStatus();

	List<String> getForthLine();

	List<CreditCardDetails> getCreditCardDetails();




	Long getRSNSequenceNumber();

	List<CreditCardDetails> checkRecordValidity(String primaryAcNo,
			String productId);

	List<CreditCardDetails> getHoldStateCreditCardDetails();

	List<CreditCardDetails> getRejectedStateCreditCardDetails();

	void updateCardDetails(CreditCardDetails details);

	List<CreditCardDetails> getRecordRSN(Long rsn);


	

	CreditCardDetails getCreditCardDetails(Long creditCardDetailsId);


	String getBankCode(String institutionId);


	List<Long> getFileId();

	List<CreditCardDetails> getValidCreditCardDetails(Long fileId);


	String getFileName(Long fileId);


	List<CoreFiles> getFileNames();


	List<EmbossaFormat> getEmbossaFormat(String productCode, String bankCode);

	List<CreditCardDetails> getCustomerReport(Long fileId, long status,
			String homeBranchCode);

	List<CreditCardDetails> getNCFRecord(String primaryAcctNo, String product);


	List<CreditCardDetails> getDetailsListByBranch(String branchCode,
			Integer limit, Long fileId, Long bankId);


	List<CreditCardDetails> getDetailsListByBranchForPin(String branchCode,
			Integer limit, Long fileId, Long bankId);


	List<CreditCardDetails> getEmbossaRecords(Long embId);


	List<CreditCardDetails> getDetailsListByLCPCBranch(String branchCode,
			Integer limit, Long fileId, Long bankId);


	String saveCardDetailsAndEvent(List<CreditCardDetails> detailsToSave,
			List<RecordEvent> eventList);


	Long saveCreditCardDetails(CreditCardDetails cardDetails);

	

}
