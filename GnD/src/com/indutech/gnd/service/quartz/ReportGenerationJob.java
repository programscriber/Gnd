package com.indutech.gnd.service.quartz;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.indutech.gnd.jasper.reports.JasperReportGenerator;

public class ReportGenerationJob extends QuartzJobBean {
	
	Logger logger = Logger.getLogger(ReportGenerationJob.class);
	common.Logger log = common.Logger.getLogger(ReportGenerationJob.class);
	

	@Autowired
	private JasperReportGenerator reportGenerator;
	
	
	
	
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
	logger.info("Mailing time came");
	reportGenerator = (JasperReportGenerator) context.getMergedJobDataMap().get("reportGenerator");
	reportGenerator.emailReport();
	
}

public JasperReportGenerator getReportGenerator() {
	return reportGenerator;
}

public void setReportGenerator(JasperReportGenerator reportGenerator) {
	this.reportGenerator = reportGenerator;
}
}
