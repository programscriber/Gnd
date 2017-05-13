package com.indutech.gnd.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.indutech.gnd.service.NotificationService;

@RequestMapping(value = "/notification")
@Controller("notificationController")
public class NotificationController {
	@Autowired
	private NotificationService notificationService;

	@RequestMapping(value = "/daily")
	public ModelAndView dailyNotificationCon(ModelMap model, Principal principal,
			@RequestParam(value = "enableReject", required = false) Long enableReject,
			@RequestParam(value = "emailfieldReject", required = false) String emailfieldReject,
			@RequestParam(value = "enableHold", required = false) Long enableHold,
			@RequestParam(value = "emailfieldHold", required = false) String emailfieldHold,
			@RequestParam(value = "enableApproved", required = false) Long enableApproved,
			@RequestParam(value = "emailfieldApproved", required = false) String emailfieldApproved) {
		String username = null;
		try {
			username = principal.getName();		
			model.addAttribute("username", username);
		} catch(NullPointerException ne) {
			return new ModelAndView("login");
		}
	
		
		final long REJECTED = 1;
		final long HOLD = 2;
		final long APPROVED = 3;

		String result = null;
		boolean resultReject = false;
		boolean resultHold = false;
		boolean resultApproved = false;
		ModelAndView view=null;
		// reject
		if ((enableReject == null) && (emailfieldReject == null)) {

		} else if ((enableReject == null) && (emailfieldReject != null)) {
			resultReject = getNotificationService().dailyNotificationSerReject(
					REJECTED, emailfieldReject);

		} else {
			if ((enableReject != null) && (emailfieldReject != null)
					&& (enableReject == REJECTED)) {
				resultReject = getNotificationService()
						.dailyNotificationSerReject(enableReject,
								emailfieldReject);
			}
		}
		// hold
		if ((enableHold == null) && (emailfieldHold == null)) {

		} else if ((enableHold == null) && (emailfieldHold != null)) {
			resultHold = getNotificationService().dailyNotificationSerHold(
					HOLD, emailfieldHold);
		} else {
			if ((enableHold != null) && (emailfieldHold != null)
					&& (enableHold == HOLD)) {
				resultHold = getNotificationService().dailyNotificationSerHold(
						enableHold, emailfieldHold);
			}
		}

		// apporve

		if ((enableApproved == null) && (emailfieldApproved == null)) {

		}
		if ((enableApproved == null) && (emailfieldApproved != null)) {
			resultApproved = getNotificationService()
					.dailyNotificationSerApproved(APPROVED, emailfieldApproved);
		} else {
			if ((enableApproved != null) && (emailfieldApproved != null)
					&& (enableApproved == APPROVED)) {
				resultApproved = getNotificationService()
						.dailyNotificationSerApproved(enableApproved,
								emailfieldApproved);
			}
		}
		if (resultReject && resultHold && resultApproved) {
			
			result = "SUCESSFULLY ADDED";
		}
		else {
			result = "Failed to save Emails";
		}
			view=new ModelAndView("daily-notifications");
			view.addObject("result", result);
			
			
		return view;
	}

	@RequestMapping(value = "/weekly")
	public ModelAndView weeklyNotificationCon(ModelMap model, Principal principal,
			@RequestParam(value = "enableReject", required = false) Long enableReject,
			@RequestParam(value = "emailfieldReject", required = false) String emailfieldReject,
			@RequestParam(value = "enableHold", required = false) Long enableHold,
			@RequestParam(value = "emailfieldHold", required = false) String emailfieldHold,
			@RequestParam(value = "enableApproved", required = false) Long enableApproved,
			@RequestParam(value = "emailfieldApproved", required = false) String emailfieldApproved) {
		String username = null;
		try {
			username = principal.getName();		
			model.addAttribute("username", username);
		} catch(NullPointerException ne) {
			return new ModelAndView("login");
		}
		
		
		
		
		ModelAndView view=null;
		String result = null;
		final long WEEK_REJECT = 4;
		final long WEEK_HOLD = 5;
		final long WEEK_APPROVED = 6;

		boolean resultReject = false;
		boolean resultHold = false;
		boolean resultApproved = false;
		// reject
		if ((enableReject == null) && (emailfieldReject == null)) {

		} else if ((enableReject == null) && (emailfieldReject != null)) {
			resultReject = getNotificationService().dailyNotificationSerReject(
					WEEK_REJECT, emailfieldReject);

		} else {
			if ((enableReject != null) && (emailfieldReject != null)
					&& (enableReject == WEEK_REJECT)) {
				resultReject = getNotificationService()
						.dailyNotificationSerReject(enableReject,
								emailfieldReject);
			}
		}
		// hold
		if ((enableHold == null) && (emailfieldHold == null)) {

		} else if ((enableHold == null) && (emailfieldHold != null)) {
			resultHold = getNotificationService().dailyNotificationSerHold(
					WEEK_HOLD, emailfieldHold);
		} else {
			if ((enableHold != null) && (emailfieldHold != null)
					&& (enableHold == WEEK_HOLD)) {
				resultHold = getNotificationService().dailyNotificationSerHold(
						enableHold, emailfieldHold);
			}
		}

		// apporve

		if ((enableApproved == null) && (emailfieldApproved == null)) {

		}
		if ((enableApproved == null) && (emailfieldApproved != null)) {
			resultApproved = getNotificationService()
					.dailyNotificationSerApproved(WEEK_APPROVED,
							emailfieldApproved);
		} else {
			if ((enableApproved != null) && (emailfieldApproved != null)
					&& (enableApproved == WEEK_APPROVED)) {
				resultApproved = getNotificationService()
						.dailyNotificationSerApproved(enableApproved,
								emailfieldApproved);
			}
		}
		if (resultReject && resultHold && resultApproved) {
			result = "SUCESSFULLY ADDED";
		}
		else {
			result = "Failed to add emails";
		}
			
			view=new ModelAndView("weekly-notifications");
			view.addObject("result", result);
			
			
		
		return view;
	}

	@RequestMapping(value = "/exception")
	public ModelAndView exceptionyNotificationCon(ModelMap model, Principal principal,
			@RequestParam(value = "enableReject", required = false) Long enableReject,
			@RequestParam(value = "emailfieldReject", required = false) String emailfieldReject,
			@RequestParam(value = "enableHold", required = false) Long enableHold,
			@RequestParam(value = "emailfieldHold", required = false) String emailfieldHold,
			@RequestParam(value = "enableApproved", required = false) Long enableApproved,
			@RequestParam(value = "emailfieldApproved", required = false) String emailfieldApproved,
			@RequestParam(value="label",required=false) Long label,
			@RequestParam(value="threshold",required=false) Long threshold) {
		String username = null;
		try {
			username = principal.getName();		
			model.addAttribute("username", username);
		} catch(NullPointerException ne) {
			return new ModelAndView("login");
		}	
		
		
		
		
		
		ModelAndView view=null;
		String result = null;
		final long India_post_AWB = 7;
		final long Blue_dart_AWB = 8;
		final long missing_Branch = 9;

		boolean resultReject = false;
		boolean resultHold = false;
		boolean resultApproved = false;
		// reject
		if ((enableReject == null) && (emailfieldReject == null)) {

		} else if ((enableReject == null) && (emailfieldReject != null)) {
			resultReject = getNotificationService().excepNotificationSerReject(label,
					India_post_AWB, emailfieldReject);

		} else {
			if ((enableReject != null) && (emailfieldReject != null)
					&& (enableReject == India_post_AWB)) {
				resultReject = getNotificationService()
						.excepNotificationSerReject(label,enableReject,
								emailfieldReject);
			}
		}
		// hold
		if ((enableHold == null) && (emailfieldHold == null)) {

		} else if ((enableHold == null) && (emailfieldHold != null)) {
			resultHold = getNotificationService().excepNotificationSerHold(threshold,
					Blue_dart_AWB, emailfieldHold);
		} else {
			if ((enableHold != null) && (emailfieldHold != null)
					&& (enableHold == Blue_dart_AWB)) {
				resultHold = getNotificationService().excepNotificationSerHold(threshold,
						enableHold, emailfieldHold);
			}
		}

		// apporve

		if ((enableApproved == null) && (emailfieldApproved == null)) {

		}
		if ((enableApproved == null) && (emailfieldApproved != null)) {
			resultApproved = getNotificationService()
					.excepNotificationSerApproved(missing_Branch,
							emailfieldApproved);
		} else {
			if ((enableApproved != null) && (emailfieldApproved != null)
					&& (enableApproved == missing_Branch)) {
				resultApproved = getNotificationService()
						.excepNotificationSerApproved(enableApproved,
								emailfieldApproved);
			}
		}
		if (resultReject && resultHold && resultApproved) {
			result = "SUCESSFULLY ADDED";
		}
		else {
			result = "Failed to add emails";
		}
			view=new ModelAndView("exceptional-notifications");
			view.addObject("result", result);
			
			
		
		return view;
	}
	
	
	@RequestMapping(value="/getdailymails")
	public ModelAndView getDailyMails(ModelMap model, Principal principal) {
		
		List<String> list = getNotificationService().getDailyEmails();
		
		String username = null;
		try {
			username = principal.getName();		
			model.addAttribute("username", username);
		} catch(NullPointerException ne) {
			return new ModelAndView("login");
		}
		
		return new ModelAndView("daily-notifications","emails",list);
	}
	
	@RequestMapping(value="/getweeklymails")
	public ModelAndView getWeeklyMails(ModelMap model, Principal principal) {
		
		List<String> list = getNotificationService().getWeeklyEmails();
		
		String username = null;
		try {
			username = principal.getName();		
			model.addAttribute("username", username);
		} catch(NullPointerException ne) {
			return new ModelAndView("login");
		}
		
		return new ModelAndView("weekly-notifications","emails",list);
	}
	
	@RequestMapping(value="/getexceptionalmails")
	public ModelAndView getExceptionalMails(ModelMap model, Principal principal) {

		String username = null;
		try {
			username = principal.getName();		
			model.addAttribute("username", username);
		} catch(NullPointerException ne) {
			return new ModelAndView("login");
		}
		List<String> list = getNotificationService().getExceptionalEmails();
		
		
		
		
		return new ModelAndView("exceptional-notifications","emails",list);
	}
	
	public NotificationService getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}
}
