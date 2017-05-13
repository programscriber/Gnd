package com.indutech.gnd.dao;

import java.util.List;

import com.indutech.gnd.dto.CardDispatch;
import com.indutech.gnd.dto.CreditCardDetails;
import com.indutech.gnd.dto.PinDispatch;

public interface DispatchDAO {
	
	List<CreditCardDetails> findCardAWB(Long rsn, String cardAWB);
	List<CreditCardDetails> findPinAWB(Long rsn, String pinAWB);
	
	void saveCardDispatch(CardDispatch cardDispatch);
	void savePinDispatch(PinDispatch pinDispatch);
	
}
