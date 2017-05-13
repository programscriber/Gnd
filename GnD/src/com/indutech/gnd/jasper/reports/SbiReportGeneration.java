package com.indutech.gnd.jasper.reports;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;

import com.indutech.gnd.bo.CreditCardDetailsBO;
import com.indutech.gnd.dto.CreditCardDetails;

import org.apache.log4j.Logger;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.query.JRHibernateQueryExecuterFactory;
import net.sf.jasperreports.engine.util.JRSaver;

public class SbiReportGeneration {
	
	Logger logger = Logger.getLogger(SbiReportGeneration.class);
	
	common.Logger log = common.Logger.getLogger(SbiReportGeneration.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}



	@SuppressWarnings("unchecked")
	public void genrateReportForMobileNo(String mobileNum, String homebranchcode,String reportName,Session sess) {
		logger.info(reportName);
		Properties properties = new Properties();
		InputStream inputStream = getClass().getResourceAsStream(
				"/camel-config.properties");
		
		try {
			properties.load(inputStream);
			String imagePath = properties.getProperty("iconpath");
			String source = properties.getProperty("sourceReportsbimobilesearch");
			String dest = properties.getProperty("destinationReport");
			String destination = dest + "/" + reportName+"_"+homebranchcode
					+ ".jrprint";
			String destFileName = dest + "/" +reportName+"_"+homebranchcode
					+ ".pdf";
			JasperReport jasperReport = JasperCompileManager.compileReport(source);
			List aa=new ArrayList();
			CreditCardDetails card=new CreditCardDetails();
			card.setSerialNo("12345");
			card.setInstitutionId("hai");
			card.setPrimaryAcctNo("123453");
			card.setProduct("id");
			card.setHomeBranchCode("hau");
			
			aa.add(card);
			aa.add(card);
			Map parameters = new HashMap();
//			parameters.put("ReportTitle", aa);
//			parameters.put("mobileNo", mobileNum);
//			parameters.put("homeBranchCode", homebranchcode);
			//parameters.put("subReportBeanList", card);
			
			parameters
					.put(JRHibernateQueryExecuterFactory.PARAMETER_HIBERNATE_SESSION,
							sess);
			JasperPrint jasperPrint;
			jasperPrint = JasperFillManager
					.fillReport(jasperReport, parameters);
			File destFile = new File(destination);
			JRSaver.saveObject(jasperPrint, destFile);
			JasperExportManager
			.exportReportToPdfFile(jasperPrint, destFileName);
		} catch (Exception es) {
			es.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public String genrateReportForMobileNo(List<CreditCardDetailsBO> crdeit,String title) throws Exception{
		
		Properties properties = new Properties();
		InputStream inputStream = getClass().getResourceAsStream(
				"/camel-config.properties");
		SimpleDateFormat sdf=new SimpleDateFormat("ddMMyyhhmmss");
		String sd=sdf.format(new Date());
		String destFileName=null;
		try {
			properties.load(inputStream);
			String imagePath = properties.getProperty("iconpath");
			String source = properties.getProperty("sourceReportsbimobilesearch");
			String dest = properties.getProperty("destinationReport");
			String destination = dest + "/" + " downold_result_"+sd
					+ ".jrprint";
			destFileName = dest + "/" +" downold_result_"+sd
					+ ".pdf";
			JasperReport jasperReport = JasperCompileManager.compileReport(source);
			Map parameters = new HashMap();
			parameters.put("title", title);
			logger.info("i am going to take the list"+crdeit.size());
			//parameters.put("titl", crdeit);
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(crdeit);
			JasperPrint jasperPrint = JasperFillManager
					.fillReport(jasperReport, parameters, ds);
			File destFile = new File(destination);
			JRSaver.saveObject(jasperPrint, destFile);
			JasperExportManager
			.exportReportToPdfFile(jasperPrint, destFileName);
			destFile.delete();
		} catch (Exception es) {
			es.printStackTrace();
		}
		return destFileName;
	}
}
