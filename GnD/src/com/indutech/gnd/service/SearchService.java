package com.indutech.gnd.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.indutech.gnd.bo.CoreFilesBO;
import com.indutech.gnd.bo.CreditCardDetailsBO;

public interface SearchService {


	HashMap<String, Object> getRecordEvent(Long creditCardDetailsId);

	List<CoreFilesBO> getFiles(Map<String,String> parametermap );

	List<CreditCardDetailsBO> getRecords(Map<String, String> reqMap);
}
