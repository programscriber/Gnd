package com.indutech.gnd.dao;

import java.util.List;

import com.indutech.gnd.dto.CreditCardDetails;
import com.indutech.gnd.dto.MasterAWB;
import com.indutech.gnd.dto.RecordEvent;

public interface RTODAO {
	
	List<Object[]> getHomeBranchList(List<Long> rsnList);

	List<MasterAWB> getAWBList(long indianPostChennai, int branchsize);

	void saveRecordEvent(RecordEvent event);

	List<CreditCardDetails> getCardDetails(String homeBranchCode, Long bankId,
			List<Long> rsnList);

//	int updateCardAWB(String homeBranchCode, Long bankId, String cardAWB,
//			List<Long> rsnList);

	List<CreditCardDetails> getCustomerRecords(List<Long> rsnList);

	void saveCustomerRecord(CreditCardDetails details);

	int updateCardAWB(String homeBranchCode, Long bankId, String cardAWB,
			List<Long> rsnList, Long status, String ruleStatus);

}
