package com.indutech.gnd.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.indutech.gnd.service.PinStateManagerService;


@Controller
@RequestMapping(value = "/pinstate")
public class PinstateManagerController {
	
	public static final int PIN_STATUS_MAILER       = 70;
	public static final int PIN_STATUS_DISPATCH     = 71;
	public static final int PIN_STATUS_DELIVER      = 72;
	public static final int PIN_STATUS_RTO          = 73;
	public static final int PIN_STATUS_REDISPATCH   = 74;
	public static final int PIN_STATUS_TODESTROY    = 75;	
	public static final int PIN_STATUS_DESTROY      = 76;
	
	
	
	@Autowired
	private PinStateManagerService pinstateService;

	@RequestMapping(value = "/pinmakedecision")
	public ModelAndView makedecision(ModelMap model, Principal principal,
			@RequestParam(value = "creditCardDetailsId", required = true) String creditCardDetailsId,
			@RequestParam(value = "decison") Integer decision,
			@RequestParam(value="remarkval",required=true) String remark) {
		
		String username = null;
		try {
			username = principal.getName();		
			model.addAttribute("username", username);
		} catch(NullPointerException ne) {
			return new ModelAndView("login");
		}
		
		remark = remark+" ("+username+")";
		
		switch (decision) {
		case PIN_STATUS_DISPATCH:
			getPinstateser().toStatePinDispatch(creditCardDetailsId,remark);
			break;
		case PIN_STATUS_DELIVER:
			getPinstateser().toStatePinDeliver(creditCardDetailsId,remark);
			break;
		case PIN_STATUS_RTO:
			getPinstateser().toStatePinRTO(creditCardDetailsId,remark);
			break;
		case PIN_STATUS_REDISPATCH:
			getPinstateser().toStatePinRedispatch(creditCardDetailsId,remark);
			break;
		case PIN_STATUS_DESTROY:
			getPinstateser().toStateToDestroy(creditCardDetailsId,remark);
			break;
		default:
			break;
		}
		
		
		
		return new ModelAndView("recordsearch");
	}

	public PinStateManagerService getPinstateser() {
		return pinstateService;
	}

	public void setPinstateser(PinStateManagerService pinstateService) {
		this.pinstateService = pinstateService;
	}
}
