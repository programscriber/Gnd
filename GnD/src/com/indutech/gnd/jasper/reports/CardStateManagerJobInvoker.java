package com.indutech.gnd.jasper.reports;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.indutech.gnd.service.CardStateManagerService;
import com.indutech.gnd.service.FileConvertServiceImpl;

public class CardStateManagerJobInvoker {
	Logger logger = Logger.getLogger(CardStateManagerJobInvoker.class);
	
	common.Logger log = common.Logger.getLogger(CardStateManagerJobInvoker.class);
	
	@Autowired
	private CardStateManagerService cardStateManagerService;
	
	public void chanageHoldToReject(){
		logger.info("firing for making hold to reject ");
		logger.info("Inside chanageHoldToReject ");
		cardStateManagerService.changeHoldStateToReject();
	}
	
	public void chanageRejectToMarkDelete(){
		logger.info("Inside chanageRejectToMarkDelete ");
		cardStateManagerService.changeRejectStateToMarkDelete();
	}
	

}
