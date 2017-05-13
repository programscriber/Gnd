package com.indutech.gnd.service;

public interface CardStateManager {



	void toStatecardDeliver(String creditCardDetailsId, String remark);

	void toStatecardRTO(String creditCardDetailsId, String remark);

	void toCardRejectReprocess(String creditCardDetailsId, String remark);

	void changeRejectStateToMarkDelete();

	void changeHoldStateToReject();

	void toCardManualDestory(String creditCardDetailsId, String remark);

	void toStatecardRedispatch(String creditCardDetailsId, String remark);

	void toCardHoldReprocess(String creditCardDetailsId, String remark);

	void toStateReject(String creditCardDetailsId, String remark);

	void toStateHold(String creditCardDetailsId, String remark);

	void toStatecardDispatch(String creditCardDetailsId, String remark);

}
