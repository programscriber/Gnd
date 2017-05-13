package com.indutech.gnd.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.indutech.gnd.bo.drool.DroolRecordBO;
import com.indutech.gnd.dao.GNDDAOImpl;
import com.indutech.gnd.dto.CreditCardDetails;
import com.indutech.gnd.dto.RecordEvent;

@Component("PINstateManager")
public class PinStateManagerService implements PinStateManager {
	
	public static final int PIN_STATUS_UNINITIALIZED = 0;
	public static final int PIN_STATUS_AWB_ASSIGNED = 69;
	public static final int PIN_STATUS_MAILER       = 70;
	public static final int PIN_STATUS_DISPATCH     = 71;
	public static final int PIN_STATUS_DELIVER      = 72;
	public static final int PIN_STATUS_RTO          = 73;
	public static final int PIN_STATUS_REDISPATCH   = 74;
	public static final int PIN_STATUS_TODESTROY    = 75;	
	public static final int PIN_STATUS_DESTROY      = 76;
	
	
	public static final int PIN_STATUS_AWB_RANGE = 2000;
	public static final int PIN_STATUS_AWB_COUNT = 3;
	
	public static final String PIN_STATUS_UNINITIALIZED_STRING = "PIN UNINITIALIZED";
	public static final String PIN_STATUS_AWB_ASSIGNED_STRING = "PIN AWB ASSIGNED";
	public static final String PIN_STATUS_MAILER_STRING      =  "PIN MAILED";
	public static final String PIN_STATUS_DISPATCH_STRING     = "PIN DISPATCHED";
	public static final String PIN_STATUS_DELIVER_STRING      = "PIN DELIVERED";
	public static final String PIN_STATUS_RTO_STRING          = "PIN RETURNED";
	public static final String PIN_STATUS_REDISPATCH_STRING   = "PIN REDISPATCHED";
	public static final String PIN_STATUS_TODESTROY_STRING    = "PIN TO DESTROY";	
	public static final String PIN_STATUS_DESTROY_STRING      = "PIN DESTROYED";
	
	@Autowired
	private GNDDAOImpl gndDAO;
	
	
	public GNDDAOImpl getGndDAO() {
		return gndDAO;
	}

	public void setGndDAO(GNDDAOImpl gndDAO) {
		this.gndDAO = gndDAO;
	}


	@Override
	@Transactional
	public void toStatePINMailer(Long creditCardDetailsId,String remark) {
		CreditCardDetails details = getGndDAO().getCreditCardDetails(creditCardDetailsId);
		
		int curStatus = details.getPinstatus().intValue();
		
		switch (curStatus){
			case CardStateManagerService.CARD_STATUS_AUFCONVERTED :
			case CardStateManagerService.CARD_STATUS_EMBOSSA_RECEIVED :
			case CardStateManagerService.CARD_STATUS_MD_GENERATED :

				details.setPinstatus((long)PIN_STATUS_MAILER);
				getGndDAO().saveCardDetails(details);
				RecordEvent event = new RecordEvent();
				event.setEventDate(new Date());
				event.setDescription(remark);
				event.setEventId((long)PIN_STATUS_MAILER);
				event.setRecordId(details.getCreditCardDetailsId());
				
				getGndDAO().saveRecordEvent(event);
			break;
			
			default:
			//TODO error message in UI - not allowed state
		}
		
	}

	@Override
	@Transactional
	public void toStatePinDispatch(String creditCardDetailsId,String remark) {
		for(String val:creditCardDetailsId.split(",")){
		CreditCardDetails details = getGndDAO().getCreditCardDetails(Long.valueOf(val));
		
		int curStatus = details.getPinstatus().intValue();
		
		switch (curStatus){
		
		case PIN_STATUS_DISPATCH :
		case PIN_STATUS_DELIVER :
		case PIN_STATUS_RTO :
		case PIN_STATUS_REDISPATCH :
		case PIN_STATUS_DESTROY :
			
			break;
				
		case PIN_STATUS_MAILER    :
			
			details.setPinstatus((long)PIN_STATUS_DISPATCH);
			details.setCreatedDate(new Date());
			getGndDAO().saveCardDetails(details);
			
			RecordEvent event = new RecordEvent();
			event.setEventDate(new Date());
			event.setDescription(remark);
			event.setEventId((long)PIN_STATUS_DISPATCH);
			event.setRecordId(details.getCreditCardDetailsId());
			
			getGndDAO().saveRecordEvent(event);
			break;
			
			default:
			//TODO error message in UI - not allowed state
		}
	}
	}

	@Override
	@Transactional
	public void toStatePinDeliver(String creditCardDetailsId,String remak) {
		for(String val:creditCardDetailsId.split(",")){
		CreditCardDetails details = getGndDAO().getCreditCardDetails(Long.valueOf(val));
		
		int curStatus = details.getPinstatus().intValue();
		
		switch (curStatus){
		
		case PIN_STATUS_DELIVER :
		case PIN_STATUS_RTO :
		case PIN_STATUS_REDISPATCH :
		case PIN_STATUS_DESTROY :
			break;
			
		case PIN_STATUS_DISPATCH    :
			
			details.setPinstatus((long)PIN_STATUS_DELIVER);
			details.setCreatedDate(new Date());
			getGndDAO().saveCardDetails(details);
			
			RecordEvent event = new RecordEvent();
			event.setEventDate(new Date());
			event.setDescription(remak);
			event.setEventId((long)PIN_STATUS_DELIVER);
			event.setRecordId(details.getCreditCardDetailsId());
			
			getGndDAO().saveRecordEvent(event);
			break;
			
			default:
			//TODO error message in UI - not allowed state
		}
		}
	}


	@Override
	@Transactional
	public void toStatePinRTO(String creditCardDetailsId,String remark) {
		for(String val:creditCardDetailsId.split(",")){
		CreditCardDetails details = getGndDAO().getCreditCardDetails(Long.valueOf(val));
		
		int curStatus = details.getPinstatus().intValue();
		
		switch (curStatus){
		
		case PIN_STATUS_RTO :
		case PIN_STATUS_DESTROY :
			
			break;
			
		case PIN_STATUS_DELIVER    :
		case PIN_STATUS_REDISPATCH :
			
			details.setPinstatus((long)PIN_STATUS_RTO);
			details.setCreatedDate(new Date());
			getGndDAO().saveCardDetails(details);
			
			RecordEvent event = new RecordEvent();
			event.setEventDate(new Date());
			event.setDescription(remark);
			event.setEventId((long)PIN_STATUS_RTO);
			event.setRecordId(details.getCreditCardDetailsId());
			
			getGndDAO().saveRecordEvent(event);
			break;
			
			default:
			//TODO error message in UI - not allowed state
		}
		}
	}


	@Override
	@Transactional
	public void toStatePinRedispatch(String creditCardDetailsId,String remark) {
		for(String val:creditCardDetailsId.split(",")){
		CreditCardDetails details = getGndDAO().getCreditCardDetails(Long.valueOf(val));
		
		int curStatus = details.getPinstatus().intValue();
		
		switch (curStatus){
		case PIN_STATUS_REDISPATCH:
		case PIN_STATUS_DESTROY :
			break;
			
		case PIN_STATUS_RTO    :
			
			details.setPinstatus((long)PIN_STATUS_REDISPATCH);
			details.setCreatedDate(new Date());
			getGndDAO().saveCardDetails(details);
			
			RecordEvent event = new RecordEvent();
			event.setEventDate(new Date());
			event.setDescription(remark);
			event.setEventId((long)PIN_STATUS_REDISPATCH);
			event.setRecordId(details.getCreditCardDetailsId());
			
			getGndDAO().saveRecordEvent(event);
			break;
			
			default:
			//TODO error message in UI - not allowed state
		}
		}
	}


	@Override
	@Transactional
	public void toStateToDestroy(String creditCardDetailsId,String remark) {
		for(String val:creditCardDetailsId.split(",")) {
			CreditCardDetails details = getGndDAO().getCreditCardDetails(Long.valueOf(val));
			
		int curStatus = details.getPinstatus().intValue();
		
		switch (curStatus){
			case PIN_STATUS_RTO    :
				
				details.setPinstatus((long)PIN_STATUS_TODESTROY);
				details.setCreatedDate(new Date());
				getGndDAO().saveCardDetails(details);
				
				RecordEvent event = new RecordEvent();
				event.setEventDate(new Date());
				event.setDescription(remark);
				event.setEventId((long)PIN_STATUS_TODESTROY);
				event.setRecordId(details.getCreditCardDetailsId());
				
				getGndDAO().saveRecordEvent(event);
				break;
				
				default:
				//TODO error message in UI - not allowed state
			}
		}

	}


	@Override
	@Transactional
	public void toStateManualDestroy(Long creditCardDetailsId,String remark) {
		
		CreditCardDetails details = getGndDAO().getCreditCardDetails(creditCardDetailsId);
		
		int curStatus = details.getPinstatus().intValue();
		
		switch (curStatus){
		case PIN_STATUS_TODESTROY    :
			
			details.setPinstatus((long)PIN_STATUS_DESTROY);
			details.setCreatedDate(new Date());
			getGndDAO().saveCardDetails(details);
			
			RecordEvent event = new RecordEvent();
			event.setEventDate(new Date());
			event.setDescription(remark);
			event.setEventId((long)PIN_STATUS_DESTROY);
			event.setRecordId(details.getCreditCardDetailsId());
			
			getGndDAO().saveRecordEvent(event);
			break;
			
			default:
			//TODO error message in UI - not allowed state
		}

	}
	
	public int getExternalStatus(int intStatus) {
		int extStatus = intStatus;
		
		return extStatus;
	}

	@Override
	public void toStateDestroyed(Long creditCardDetailsId,String remark) {
		// TODO Auto-generated method stub
		
	}
	
	
	public static String buildPinStatus(Long status) {
		String statusName = null;
		if(status == PinStateManagerService.PIN_STATUS_UNINITIALIZED)
			statusName = PinStateManagerService.PIN_STATUS_UNINITIALIZED_STRING;
		if(status == PinStateManagerService.PIN_STATUS_AWB_ASSIGNED)
			statusName = PinStateManagerService.PIN_STATUS_AWB_ASSIGNED_STRING;
		if (status == PinStateManagerService.PIN_STATUS_MAILER)
			statusName = PinStateManagerService.PIN_STATUS_MAILER_STRING;
		if (status == PinStateManagerService.PIN_STATUS_DISPATCH)
			statusName = PinStateManagerService.PIN_STATUS_DISPATCH_STRING;
		if (status == PinStateManagerService.PIN_STATUS_DELIVER)
			statusName = PinStateManagerService.PIN_STATUS_DELIVER_STRING;
		if (status == PinStateManagerService.PIN_STATUS_DESTROY)
			statusName = PinStateManagerService.PIN_STATUS_DESTROY_STRING;
		if (status == PinStateManagerService.PIN_STATUS_REDISPATCH)
			statusName = PinStateManagerService.PIN_STATUS_REDISPATCH_STRING;
		if (status == PinStateManagerService.PIN_STATUS_RTO)
			statusName = PinStateManagerService.PIN_STATUS_RTO_STRING;
		if (status == PinStateManagerService.PIN_STATUS_TODESTROY)
			statusName = PinStateManagerService.PIN_STATUS_TODESTROY_STRING;
		
		return statusName;
	}

	
}
