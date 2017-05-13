package com.indutech.gnd.dao;

import java.util.List;

import com.indutech.gnd.dto.Bank;
import com.indutech.gnd.dto.CreditCardDetails;

public interface AUFSecondFormatDAO {

	Bank getBankShortCodeByPrefix(String prefix);

	List getCreditCardDetailsByGroup(String bankShortCode, Long fileId,
			long cardStatusAwbassigned);

	List<CreditCardDetails> getDetailsList(String homeBranchCode, Long fileId,
			long cardStatusAwbassigned);

	List<CreditCardDetails> getCreditCardDetails(Long bankId, Long fileId,
			long cardStatusAwbassigned);

}
