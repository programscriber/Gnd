package com.indutech.gnd.service.quartz;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.indutech.gnd.jasper.reports.CardStateManagerJobInvoker;
import com.indutech.gnd.service.AWBThreshouldService;

public class AWBThreshouldCheckJob extends QuartzJobBean{
	
	Logger logger = Logger.getLogger(CardStateMangerJob.class);
	private int timeout;
	
	@Autowired
	private AWBThreshouldService awbThreshouldCheck;
	
	 public void setTimeout(int timeout) {
		    this.timeout = timeout;
	 }

		@Override
		protected void executeInternal(JobExecutionContext context)
				throws JobExecutionException {
			logger.info("Inside executeInternal ");
			awbThreshouldCheck = (AWBThreshouldService) context.getMergedJobDataMap().get("awbThreshould");
			awbThreshouldCheck.getAWBStatus();

		}

		public AWBThreshouldService getAwbThreshouldCheck() {
			return awbThreshouldCheck;
		}

		public void setAwbThreshouldCheck(AWBThreshouldService awbThreshouldCheck) {
			this.awbThreshouldCheck = awbThreshouldCheck;
		}

		
		
}
