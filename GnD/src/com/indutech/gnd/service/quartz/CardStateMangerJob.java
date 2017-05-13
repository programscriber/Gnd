package com.indutech.gnd.service.quartz;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.indutech.gnd.jasper.reports.CardStateManagerJobInvoker;
import com.indutech.gnd.service.CardStateManagerService;
import com.indutech.gnd.service.emboss.EmbossService;

public class CardStateMangerJob extends QuartzJobBean {
	
	Logger logger = Logger.getLogger(CardStateMangerJob.class);
	
	@Autowired
	private CardStateManagerJobInvoker cardStateManagerJobInvoker;
	

	private int timeout;
	  
	  /**
	   * Setter called after the ExampleJob is instantiated
	   * with the value from the JobDetailBean (5)
	   */ 
	  public void setTimeout(int timeout) {
	    this.timeout = timeout;
	  }

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		logger.info("Inside executeInternal ");
		cardStateManagerJobInvoker = (CardStateManagerJobInvoker) context.getMergedJobDataMap().get("cardStateManagerJobInvoker");
		cardStateManagerJobInvoker.chanageHoldToReject();
		cardStateManagerJobInvoker.chanageRejectToMarkDelete();

	}

	public CardStateManagerJobInvoker getCardStateManagerJobInvoker() {
		return cardStateManagerJobInvoker;
	}

	public void setCardStateManagerJobInvoker(
			CardStateManagerJobInvoker cardStateManagerJobInvoker) {
		this.cardStateManagerJobInvoker = cardStateManagerJobInvoker;
	}

	

}
