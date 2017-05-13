package com.indutech.gnd.service;

public interface PinStateManager {
	public void toStatePINMailer(Long creditCardDetailsId,String remark);
//	public void toStatePinDispatch(Long creditCardDetailsId,String remark);
//	public void toStatePinDeliver(Long creditCardDetailsId,String remark);
//	public void toStatePinRTO(Long creditCardDetailsId,String remark);
//	public void toStatePinRedispatch(Long creditCardDetailsId,String remark);
	public void toStateDestroyed(Long creditCardDetailsId,String remark);
	public void toStateManualDestroy(Long creditCardDetailsId,String remark);
	public void toStatePinDispatch(String creditCardDetailsId, String remark);
	public void toStatePinDeliver(String creditCardDetailsId, String remak);
	public void toStatePinRTO(String creditCardDetailsId, String remark);
	public void toStatePinRedispatch(String creditCardDetailsId, String remark);
	void toStateToDestroy(String creditCardDetailsId, String remark);
}
