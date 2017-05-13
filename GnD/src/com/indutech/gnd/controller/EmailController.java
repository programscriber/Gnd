package com.indutech.gnd.controller;

import java.security.Principal;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.indutech.gnd.bo.EmailConfigarationBO;
import com.indutech.gnd.service.MasteDBService;

@Controller
@RequestMapping(value = "/emailcont")
public class EmailController {
	  
	  
	@Autowired
	private MasteDBService masterdbService;
	
	//@ModelAttribute("fileDir")
	@RequestMapping(value = "/editemailTable")
	public @ResponseBody ModelAndView editemailTable(ModelMap model,
			Principal principal) {
		
		String username = null;
		try {
			username = principal.getName();
			model.addAttribute("username", username);
		} catch (NullPointerException ne) {
			return new ModelAndView("login");
		}
		List<EmailConfigarationBO> listemailBo=getMasterdbService().getEmailFields();
		ModelAndView view=new ModelAndView("editemailTable");
		//view.addObject("emailConfig", listemailBo.get(0));
		//view.addObject("fileDir",fileDir);
		return view;
	}
	
	@RequestMapping(value = "/editemailui")
	public @ResponseBody ModelAndView editemail(@RequestParam("id") String id,
			@RequestParam("userName") String userName,@RequestParam("password") String password,
			@RequestParam("subject") String subject,@RequestParam("fromemail") String fromemail,
			@RequestParam("ccemail") String ccemail,@RequestParam("bccemail") String bccemail,
			@RequestParam("destinationpath") String destinationpath,@RequestParam("host") String host,
			@RequestParam("port") String port) {
		
			boolean result=getMasterdbService().geteditEmailConfig(id,userName,password,subject,fromemail,ccemail,bccemail,destinationpath,host,port);
		return new ModelAndView();
	}
	
	public MasteDBService getMasterdbService() {
		return masterdbService;
	}

	public void setMasterdbService(MasteDBService masterdbService) {
		this.masterdbService = masterdbService;
	}
}