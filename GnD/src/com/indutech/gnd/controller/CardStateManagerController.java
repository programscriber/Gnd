package com.indutech.gnd.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.indutech.gnd.service.CardStateManagerService;

@Controller("cardstateCont")
@RequestMapping(value = "/cardstate")
public class CardStateManagerController {

	public static final int CARD_STATUS_DISPATCH = 20;
	public static final int CARD_STATUS_DELIVER = 21;
	public static final int CARD_STATUS_RETURN = 22;
	public static final int CARD_STATUS_REDISPATCH = 23;
	public static final int CARD_STATUS_DESTROY = 24;
	public static final int CARD_STATUS_HOLD_REPROCESS =25;
	public static final int CARD_STATUS_REJECT_REPROCESS=26;
	public static final int CARD_STATUS_MANUALREJECT = 91;
	public static final int CARD_STATUS_DEVILVEY_BY_AWB=18;
	public static final int CARD_STATUS_RTO_BY_AWB=19;

	@Autowired
	private CardStateManagerService cardstateman;

	@RequestMapping(value = "/makedecision")
	public ModelAndView makedecision(ModelMap model, Principal principal,
			@RequestParam(value = "creditCardDetailsId", required = true) String creditCardDetailsId,
			@RequestParam(value = "decison", required = true) Integer decision
			,@RequestParam(value="remarkval",required=true) String remark) {
		
		String username = null;
		try {
			username = principal.getName();		
			model.addAttribute("username", username);
		} catch(NullPointerException ne) {
			return new ModelAndView("login");
		}
		
		remark = remark+" ("+username+")";
		switch (decision) {
		case CARD_STATUS_DISPATCH:
			getCardstateman().toStatecardDispatch(creditCardDetailsId,remark);
			break;
		case CARD_STATUS_DELIVER:
			getCardstateman().toStatecardDeliver(creditCardDetailsId,remark);
			break;
		case CARD_STATUS_RETURN:
			getCardstateman().toStatecardRTO(creditCardDetailsId,remark);
			break;
		case CARD_STATUS_REDISPATCH:
			getCardstateman().toStatecardRedispatch(creditCardDetailsId,remark);
			break;

		case CARD_STATUS_MANUALREJECT:
			getCardstateman().toCardManualDestory(creditCardDetailsId,remark);
		
		case CARD_STATUS_HOLD_REPROCESS:			
			getCardstateman().toCardHoldReprocess(creditCardDetailsId, remark);
		
		case CARD_STATUS_REJECT_REPROCESS:			
			getCardstateman().toCardRejectReprocess(creditCardDetailsId, remark);
			
		default:
			break;
		}
		
		return new ModelAndView("recordsearch");
	}

	public CardStateManagerService getCardstateman() {
		return cardstateman;
	}

	public void setCardstateman(CardStateManagerService cardstateman) {
		this.cardstateman = cardstateman;
	}

}
