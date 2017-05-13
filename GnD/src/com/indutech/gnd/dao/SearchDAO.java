package com.indutech.gnd.dao;

import java.util.List;
import java.util.Map;

import com.indutech.gnd.dto.CreditCardDetails;
import com.indutech.gnd.dto.RecordEvent;

public interface SearchDAO {
	

	String getPinAWB(Long creditCardDetailsId);

	String getCardAWB(Long creditCardDetailsId);

	List<RecordEvent> getRecordEvent(Long creditCardDetailsId);

	CreditCardDetails getRecord(Long creditCardDetailsId);

	List<CreditCardDetails> searchRecords(Map<String, String> reqMap);
}
