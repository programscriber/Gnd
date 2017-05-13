package com.indutech.gnd.jasper.reports;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.query.JRHibernateQueryExecuterFactory;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.logging.log4j.core.LogEvent;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;














import au.com.bytecode.opencsv.CSVWriter;

import com.barcodelib.barcode.a.g.m.m;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.indutech.gnd.bo.BankBO;
import com.indutech.gnd.bo.CustomerReport;
import com.indutech.gnd.bo.MasterAWBBO;
import com.indutech.gnd.bo.PinMailerReportBO;
import com.indutech.gnd.bo.ReportsBO;
import com.indutech.gnd.bo.drool.DroolRecordBO;
import com.indutech.gnd.dao.GNDDAOImpl;
import com.indutech.gnd.dao.ProductDAOImpl;
import com.indutech.gnd.dbCon.DBConnection;
import com.indutech.gnd.dto.Bank;
import com.indutech.gnd.dto.Branch;
import com.indutech.gnd.dto.CoreFiles;
import com.indutech.gnd.dto.CreditCardDetails;
import com.indutech.gnd.dto.Reports;
import com.indutech.gnd.service.CardStateManagerService;
import com.indutech.gnd.service.EmailServiceImpl;
import com.indutech.gnd.service.PinStateManagerService;
import com.indutech.gnd.service.PropertiesLoader;

public class JasperReportGenerator {
	
	Logger logger = Logger.getLogger(JasperReportGenerator.class);
	
	
	Properties properties = PropertiesLoader.getInstance().loadProperties();
//	InputStream inputStream =getClass().getResourceAsStream("/camel-config.properties");

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private EmailServiceImpl emailService;
	
	@Autowired
	private DroolRecordBO droolRecord;
	
	@Autowired
	private GNDDAOImpl gndDAO;
	
	@Autowired
	private ProductDAOImpl productDAO;
	
	@Autowired 
	private com.indutech.gnd.dao.QcProcessDAO QcProcessDAO;
	
	
	public com.indutech.gnd.dao.QcProcessDAO getQcProcessDAO() {
		return QcProcessDAO;
	}

	public void setQcProcessDAO(com.indutech.gnd.dao.QcProcessDAO qcProcessDAO) {
		QcProcessDAO = qcProcessDAO;
	}

	public ProductDAOImpl getProductDAO() {
		return productDAO;
	}

	public void setProductDAO(ProductDAOImpl productDAO) {
		this.productDAO = productDAO;
	}

	public GNDDAOImpl getGndDAO() {
		return gndDAO;
	}

	public void setGndDAO(GNDDAOImpl gndDAO) {
		this.gndDAO = gndDAO;
	}

	public DroolRecordBO getDroolRecord() {
		return droolRecord;
	}

	public void setDroolRecord(DroolRecordBO droolRecord) {
		this.droolRecord = droolRecord;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
	
	public void emailReport() {
		try {
			logger.info("in email report");
//			properties.load(inputStream);
			logger.info("email config is enabled : "+properties.getProperty("isEmailConfig"));
			if(properties.getProperty("isEmailConfig").equals("1")) {
				logger.info("email config is enabled : "+properties.getProperty("isEmailConfig"));
				getEmailService().sendPreConfiguredMail("Test Mail",properties.getProperty("destinationReport")+"/TB080316.ncf.203724_71211_Reject_18032016034233.txt");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
	}
	
	
	public String pinMailerReport(Bank bank, String institutionId, String homeBranchCode, String productId, long status, String fileName, String embfilename, Integer isVIP, String corefilename, Long fileId, String format) {
	
		String result = "Failed to generate report";
		String reportTitle = "Pin Covering Note";
		String auffilename = fileName;
		
		try {		
			String split[] = corefilename.split("\\.");
			String reportName = fileName;
			logger.info("inst id is : "+institutionId);

			String imagePath = properties.getProperty("iconpath");
			String source = properties.getProperty("aufsourcereport_auf1");
			String dest = properties.getProperty("aufdestinationReport");
			File fileDir = new File(dest+"\\"+corefilename.substring(2, split[0].length())+"\\"+bank.getShortCode()+"\\"+corefilename+"\\"+productId);
			fileDir.mkdirs();
			logger.info("format is : "+format);
			String destFileName = fileDir+"/"+reportName+"."+format;
			
			logger.info("destination file name is : "+destFileName);
			JasperReport jasperReport = JasperCompileManager.compileReport(source);
			// Second, create a map of parameters to pass to the report.
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("ReportTitle", reportTitle);
			parameters.put("institutionId", institutionId);
			parameters.put("homeBranchCode", homeBranchCode);
			parameters.put("product", productId);
			parameters.put("Status", status);
			parameters.put("iconpath", imagePath);
			parameters.put("auffileName", auffilename);
			parameters.put("embfilename", embfilename);
			parameters.put("isVIP", isVIP);
			parameters.put("corefilename",corefilename);
			parameters.put("fileId", fileId);
			parameters.put("bankId", bank.getBankId());
			
			parameters
			.put(JRHibernateQueryExecuterFactory.PARAMETER_HIBERNATE_SESSION,
					sessionFactory.getCurrentSession());
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters);
			if(format.equals("pdf")) { 
			JasperExportManager
			.exportReportToPdfFile(jasperPrint, destFileName);
			} 
			else if(format.equals("docx")) {
				JRDocxExporter exporter = new JRDocxExporter();
	            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));

	            File exportReportFile = new File(destFileName);

	            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(exportReportFile));
			}
			result = "Report generated sucessfully";
			logger.info(result);
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
    public void pinMailerReportDocs(String institutionId, String homeBranchCode, String productId, long status, String auffileName, String embfilename, Integer isVIP, String corefilename) {
    
        String reportTitle = "Pin Covering Note";
        String auffilename = auffileName;
        List<PinMailerReportBO> pinList = null;
        
        try {        
            
//            String reportname[] = fileName.split("\\.");
//            String reportName = reportname[0];
            String reportName = auffileName;
            Properties properties = new Properties();
            InputStream inputStream =getClass().getResourceAsStream("/camel-config.properties");
            properties.load(inputStream);

            String imagePath = properties.getProperty("iconpath");
            String source = properties.getProperty("aufsourcereport");
            String dest = properties.getProperty("aufdestinationReport");
//            String destination = dest+"/"+reportName+".jrprint";
            File filedir = new File(dest+"\\"+corefilename);
            filedir.mkdir();
            String destFileName = filedir.getPath()+"/"+reportName+".docx";
            
            JasperReport jasperReport = JasperCompileManager.compileReport(source);
            // Second, create a map of parameters to pass to the report.
            @SuppressWarnings("rawtypes")
            Map parameters = new HashMap();
            parameters.put("ReportTitle", reportTitle);
            parameters.put("institutionId", institutionId);
            parameters.put("homeBranchCode", homeBranchCode);
            parameters.put("product", productId);
            parameters.put("Status", status);
            parameters.put("iconpath", imagePath);
            parameters.put("auffileName", auffilename);
            parameters.put("embfilename", embfilename);
            parameters.put("isVIP", isVIP);
            

            parameters
            .put(JRHibernateQueryExecuterFactory.PARAMETER_HIBERNATE_SESSION,
                    sessionFactory.getCurrentSession());
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters);

            JRDocxExporter exporter = new JRDocxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));

            File exportReportFile = new File(destFileName);

            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(exportReportFile));

            exporter.exportReport();

        } catch(Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
    }

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void generateApprovedReport(String institutionId, String homeBranchCode,	String productId, long status, String fileName, String embfilename) {
		
		String reportTitle = "Pin Covering Note";
		logger.info("filename is : "+fileName);
		String auffilename = fileName;
		
		try {		
			
			String reportname[] = fileName.split("\\.");
			String reportName = reportname[0];
			Properties properties = new Properties();
			InputStream inputStream =getClass().getResourceAsStream("/camel-config.properties");
			properties.load(inputStream);

			String imagePath = properties.getProperty("iconpath");
			String source = properties.getProperty("aufsourcereport");
			String dest = properties.getProperty("aufdestinationReport");
//			String destination = dest+"/"+reportName+".jrprint";
			String destFileName = dest+"/"+reportName+".pdf";
			
			//File sourceFile = new File("D:/repositories/gnd_drools/GnD/src/com/indutech/gnd/jasper/reports/jrxml/customerReport.jrxml");
//			JasperReport jasperReport = (JasperReport) JRLoader
//					.loadObject(sourceFile);
			JasperReport jasperReport = JasperCompileManager.compileReport(source);
			// Second, create a map of parameters to pass to the report.
			Map parameters = new HashMap();
			parameters.put("ReportTitle", reportTitle);
			parameters.put("institutionId", institutionId);
			parameters.put("homeBranchCode", homeBranchCode);
			parameters.put("product", productId);
			parameters.put("Status", status);
			parameters.put("iconpath", imagePath);
			parameters.put("auffileName", auffilename);
			parameters.put("embfilename", embfilename);
			
			parameters
					.put(JRHibernateQueryExecuterFactory.PARAMETER_HIBERNATE_SESSION,
							sessionFactory.getCurrentSession());
			// Fourth, create JasperPrint using fillReport() method
			JasperPrint jasperPrint;
			jasperPrint = JasperFillManager
					.fillReport(jasperReport, parameters);

			JasperExportManager
					.exportReportToPdfFile(jasperPrint, destFileName);

		} catch (Exception e) { 
			e.printStackTrace();
		}
		

	}

	public EmailServiceImpl getEmailService() {
		return emailService;
	}

	public void setEmailService(EmailServiceImpl emailService) {
		this.emailService = emailService;
	}

	@Transactional
	public List<ReportsBO> getReports(String username) {
		List<ReportsBO> reportList = null;
		logger.info("in generate pdf report class");
		try {
			int x=0;
			List<Reports> list = (List<Reports>)getSessionFactory().getCurrentSession().createCriteria(Reports.class).list();
			logger.info("No. of reports are : "+list.size());	
			logger.info("Username is : "+username);
			x=list.size();
			if(x>0){
				
				reportList = new ArrayList<ReportsBO>();
				for(int i=0;i<x;i++){
					Reports report=list.get(i);
					if(report.getAllowedroles()!=null){
						String arryallowed=report.getAllowedroles();
						String splitarr[]=arryallowed.split(",");
						for(String arr:splitarr){
							if(username != null && arr != null  &&  arr.length() > 0 && username.trim().equals(arr.trim())){
								reportList.add(buildReport(report));
							}
						}
					}else{
						reportList.add(buildReport(report));
						
					}
					
				}
			}
			
		/*	if(list.size() > 0) {
				reportList = new ArrayList<ReportsBO>();
				Iterator itr = list.iterator();
				while(itr.hasNext()) {
					Reports report = (Reports) itr.next();
					reportList.add(buildReport(report));
				}
			}*/
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return reportList;
	}
	
	public ReportsBO buildReport(Reports report) {
		ReportsBO reportBO = new ReportsBO();
		reportBO.setId(report.getId());
		reportBO.setReportName(report.getReportName());
		reportBO.setSourcePath(report.getSourcePath());
		reportBO.setDestinationPath(report.getDestinationPath());
		reportBO.setSequence(report.getSequence());
//		reportBO.setFileType(report.getFileType());
		
		return reportBO;
		
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	@Transactional
	public String generateReports(String reportName, String sourcePath, String destinationPath, String fromdate, String todate, String bankIdName) { //, String extension) {
//		String result = "Report Generated Successfully";
		String result = null;
		Connection connection = null;
		String destFileName=null;
		try {
			SimpleDateFormat sdformat = new SimpleDateFormat("MM/dd/yyyy");
			logger.info("source path is : "+sourcePath);
			logger.info("destination path is : "+destinationPath);
			logger.info("report name is : "+reportName);
			
//			String banks = "";
//			if(bankId.equalsIgnoreCase("ALL")) {
//				List<BankBO> list = getBankList();
//				Iterator<BankBO> itr = list.iterator();
//				int i = 1;
//				while(itr.hasNext()) {
//					BankBO bank= (BankBO) itr.next();
//					banks += bank.getBankId().toString();
//					if(i == list.size()) {
//						banks+="";
//					}
//					else {
//						banks+=",";
//					}
//					i++;
//				}
// 			}
//			else {
//				banks = bankId;
//			}
			
			String bank[] = bankIdName.split("\\^");
			Long bankId = Long.parseLong(bank[0]);
			String bankName = bank[1];
			logger.info("finally banks input is : "+bankId+" "+bankName);
			
			if(reportName.equalsIgnoreCase("PIN_MAILER_MIS")) {
				reportName = reportName+"_"+bankName;
			}
			
			JasperReport jasperReport = JasperCompileManager.compileReport(sourcePath);
			Map parameters = new HashMap();
			connection = DBConnection.getInstance().getConnection();
//			parameters.put(JRHibernateQueryExecuterFactory.PARAMETER_HIBERNATE_SESSION,
//							sessionFactory.getCurrentSession());
			parameters.put("REPORT_CONNECTION", connection);
			parameters.put("StartDate", sdformat.parse(fromdate));
			parameters.put("EndDate", sdformat.parse(todate));
			parameters.put("bankId", bankId);
			logger.info("bank id list is : "+parameters.get("bankId"));
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters);
			
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
			
			
			File file = new File(sourcePath);
			String sourcefilename = file.getName();
			logger.info("filename is : "+sourcefilename);
			
			String fileType = sourcefilename.substring(0, 3);
			logger.info("file type is : "+fileType);
			
			String date = sdf.format(new Date());
			
			if(fileType.equalsIgnoreCase("pdf")) {				
	

				
				 destFileName = destinationPath+"/"+reportName+"_"+date+"."+fileType;			
				File destFile = new File(destFileName);			
				JRSaver.saveObject(jasperPrint, destFile);	

					
				

				JasperExportManager
						.exportReportToPdfFile(jasperPrint, destFileName);
//				JasperPrintManager
//				.printReport(destination, true);
				result = destFileName;
			}
			
			else if(fileType.equalsIgnoreCase("txt")) {

				 destFileName = destinationPath+"/"+reportName+"_"+date+"."+fileType;
				
				

//				File destFile=new File(destination);

//				JasperPrintManager
//				.printReport(destination, true);
//	            File destFile = new File(destination);
				// You can use JasperPrint to create PDF
//				JRSaver.saveObject(jasperPrint, destFile);
				
				JRTextExporter exporterTxt = new JRTextExporter();
				exporterTxt.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);

				exporterTxt.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFileName);
//				exporterTxt.setParameter(JRTextExporterParameter.CHARACTER_WIDTH, new Integer(7));
//				exporterTxt.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT, new Integer(11));
				exporterTxt.exportReport();
				result = destFileName;
				
				
			}
			else if(fileType.equalsIgnoreCase("csv")) {

				
				 destFileName = destinationPath+"/"+reportName+"_"+date+"."+fileType;

				

				JRCsvExporter exporterCSV = new JRCsvExporter();
				exporterCSV.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);

				exporterCSV.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFileName);
				exporterCSV.exportReport();
				result = destFileName;
			}
			else if(fileType.equalsIgnoreCase("xls")) {

				
				destFileName= destinationPath+"/"+reportName+"_"+date+"."+fileType;

			
				JRXlsExporter exporter = new JRXlsExporter(); 
				 
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
				exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
				exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
				exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
				exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFileName); 
				exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE); 
				exporter.exportReport(); 
				 result = destFileName;
				
			}
			
		} catch (Exception e) { 
			result = "Failed to Generate report";
			logger.error(e);
			e.printStackTrace();
		}


		return result;


	}

	@Transactional
	public String generateReports(String fromdate, String todate, Long bankId, HttpServletResponse response) {
		String result = "Report Generated Successfully";
		
		try {
			
			logger.info("todays date is : "+new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
//			properties.load(inputStream);
			logger.info("now we came to customer report generation");
//			List<CoreFiles> fileList = getGndDAO().getFileListByDate(fromdate, todate, bankId);		
			List<CoreFiles> fileList = getGndDAO().getRejectedRecordListByDate(fromdate, todate, bankId);
			Multimap<String, CustomerReport> map = null;
			Multimap<String, Object> commonData = null;
			if(fileList != null && fileList.size() > 0) {
				logger.info("total no of records are : "+fileList.size());
				map = ArrayListMultimap.create();
				commonData = ArrayListMultimap.create();
				Iterator<?> itr = fileList.iterator();
				while(itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					CustomerReport report = new CustomerReport();
					report.setBankId(bankId);
					report.setHomeBranchCode((String) obj[1]);
					report.setSerialNo((String)obj[2]);
					report.setInstitutionId((String) obj[3]);
					report.setPrimaryAcctNo((String)obj[4]);
					report.setProduct((String)obj[5]);
					report.setRuleStatus((String) obj[6]); 
					report.setEmail((String) obj[7]);
					report.setCoreFileName((String)obj[8]);
					report.setQcDate((Date) obj[9]);
					map.put(report.getHomeBranchCode(), report);
					commonData.put(report.getHomeBranchCode(), report.getCoreFileName());
					commonData.put(report.getHomeBranchCode(), report.getQcDate());
					commonData.put(report.getHomeBranchCode(), report.getEmail().trim());
				}
			}
			if(map != null && map.size() > 0 && commonData != null && commonData.size() > 0 && map.size() > 0) {
				String imagePath = properties.getProperty("iconpath");
			    String source = properties.getProperty("sourceReport");
			    String dest = properties.getProperty("destinationReport");
			    String reportTitle = "Rejected Records List";
			    String reportName = "Reject_"+new SimpleDateFormat("ddMMyyyhhmmss").format(new Date());
				Set<String> homeBranchCodeList = commonData.keySet();
				Iterator<String> branchIterator = homeBranchCodeList.iterator();
				while(branchIterator.hasNext()) {
					String branchCode = (String) branchIterator.next();
					List<CustomerReport> customerReport = (List<CustomerReport>) map.get(branchCode);
					List<Object> commonList = (List<Object>) commonData.get(branchCode);
					String corefilename = (String) commonList.get(0);
					Date coreqcdate = (Date) commonList.get(1);
					String branchEmailAddress = (String) commonList.get(2);
					JasperReport jasperReport = JasperCompileManager.compileReport(source);
					File fileDir = new File(dest+"\\"+corefilename);
					String destFileName = fileDir.getPath()+"/"+corefilename+"_"+branchCode+"_"+reportName+"_"+branchEmailAddress+".txt";
					fileDir.mkdir();
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("email", branchEmailAddress);
					parameters.put("ReportTitle", reportTitle);
					parameters.put("iconpath", imagePath);
					parameters.put("fileName",corefilename);
					parameters.put("receivedDate",coreqcdate);
					parameters.put("processedBranchCode", (String) branchCode);
					JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(customerReport);
				      JasperPrint jasperPrint = JasperFillManager
				        .fillReport(jasperReport, parameters, ds);
				      JRTextExporter exporterTxt = new JRTextExporter();
				      exporterTxt.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);  
				      exporterTxt.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFileName); 
				      exporterTxt.exportReport();
				      

				}
				
//			if(fileList != null && fileList.size() > 0) {
//				logger.info("list size is : "+fileList.size());
//				Iterator<CoreFiles> iterator = fileList.iterator();
//				while(iterator.hasNext()) {
//					CoreFiles file = (CoreFiles) iterator.next();
//					generateCustomerReportsAndMail(file, (long) CardStateManagerService.CARD_STATUS_REJECT, "Rejected Records List", "Reject_"+new SimpleDateFormat("ddMMyyyhhmmss").format(new Date()), fromdate, todate, response);
//				}
			} else {
				result = "No data found to generate reports on selected dates";
			}
		} catch (Exception e) { 
			result = "Failed to Generate report";
			logger.error(e);
			e.printStackTrace();
		}
		

		
		return result;
	}

	@SuppressWarnings("deprecation")
	private void generateCustomerReports(CoreFiles file, long status, String reportTitle, String reportName, String fromdate, String todate, HttpServletResponse response) {
		try {
			Long bankId = null;
			logger.info("status is : "+status);
			String corefilename = file.getFilename();
			
			List<?> group = getGndDAO().getBranchGroupForReport(file.getId(), status);
			if(group != null && group.size() > 0) {
				Iterator<?> iterator = group.iterator();
				while(iterator.hasNext()) {		
					Object[] obj = (Object[]) iterator.next();
					String imagePath = properties.getProperty("iconpath");
					String source = properties.getProperty("sourceReport");
					String dest = properties.getProperty("destinationReport");
					File fileDir = new File(dest+"\\"+corefilename);
					fileDir.mkdir();
					String destFileName = fileDir.getPath()+"/"+file.getFilename()+"_"+(String)obj[0]+"_"+reportName+".txt";
					JasperReport jasperReport = JasperCompileManager.compileReport(source); 
					List<CreditCardDetails> list = getGndDAO().getCustomerReport(file.getId(), status, (String) obj[0]);
					if(list != null && list.size() > 0) {
						List<CustomerReport> customerReportList = new ArrayList<CustomerReport>();
						Iterator<CreditCardDetails> itr = list.iterator();
						while(itr.hasNext()) {
							CreditCardDetails details = (CreditCardDetails) itr.next();
							CustomerReport report = new CustomerReport();
							report.setBankId(details.getBankId());
							bankId = report.getBankId();
							report.setSerialNo(details.getSerialNo());
							report.setInstitutionId(details.getInstitutionId());
							report.setPrimaryAcctNo(details.getPrimaryAcctNo());
							report.setProduct(details.getProduct());
							report.setRuleStatus(details.getRuleStatus()); 
							customerReportList.add(report);
						}
						Branch branch = getGndDAO().getEmail((String) obj[0],bankId);
						Map<String, Object> parameters = new HashMap<String, Object>();
						parameters.put("email", branch != null ? branch.getEmailAddress() : "");
						parameters.put("ReportTitle", reportTitle);
						parameters.put("iconpath", imagePath);
						parameters.put("homeBranchCode", (String) obj[0]);
						parameters.put(JRHibernateQueryExecuterFactory.PARAMETER_HIBERNATE_SESSION,	sessionFactory.getCurrentSession());
						JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(customerReportList);
						JasperPrint jasperPrint = JasperFillManager
								.fillReport(jasperReport, parameters, ds);
						
						JRTextExporter exporterTxt = new JRTextExporter();
						exporterTxt.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);		
						exporterTxt.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFileName);	
						exporterTxt.exportReport();
					}
					
				}
			}
		}  catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	@Transactional
	public String generatePinMailerFromUI(String fromdate, String todate, Long bankId,
			HttpServletResponse response, String vip) {
		String result = null;
		try {
			
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
			String instId = null;
			String branchCode = null;
			String productCode = null;
			BigDecimal fileId = null;
			String bankName = null;
			BigDecimal status = null;
			String coreFileName = null;
			String reportName = null;
			String embossaFileName = null;
			String fileType = null;
			List<?> group = getGndDAO().getPinMailerGroup(fromdate, todate, bankId);
			if(group != null && group.size() > 0) {
				Iterator<?> iterator = group.iterator();
				logger.info("group size is : "+group.size());
				while(iterator.hasNext()) {		
					Object[] obj = (Object[]) iterator.next();
					instId = (String)obj[0];
					branchCode = (String)obj[1];
					productCode = (String)obj[2];
					fileId = (BigDecimal) obj[3];
					bankName = (String) obj[5];
					coreFileName = (String) obj[6];
					fileType = (String) obj[7];
					String bankCode = (String) obj[8];
					Integer aufFormat = ((BigDecimal) obj[9]).intValue();
					Bank bank = new Bank();
					bank.setShortCode(bankName);
					bank.setBankCode(bankCode);
					bank.setBankId(bankId);
					
					String split[] = coreFileName.split("\\.");
					String format = properties.getProperty("pinmailerformat");
					
					String date = null;
					if(Integer.parseInt(vip) == 0) {
						SimpleDateFormat sdf1 = new SimpleDateFormat("ddMMyy");
						date = sdf1.format(DateUtils.addDays(sdf1.parse(split[0].substring(2,split[0].length())), 1));
					}
					else if(Integer.parseInt(vip) == 1) {
						date = split[0].substring(19,25);
					}
					reportName = bankName+"_"+branchCode+"_"+productCode+"_"+date+"_"+sdf.format(DateUtils.addDays(sdf.parse(date), 1));
					embossaFileName = "EMB"+bankCode+fileType+branchCode+productCode+date+new SimpleDateFormat("HHmmss").format(new Date())+".001";
						result = pinMailerReport(bank, instId, branchCode, productCode,(long) CardStateManagerService.CARD_STATUS_AUFCONVERTED, reportName, embossaFileName, Integer.parseInt(vip), coreFileName, fileId.longValue(), format);
				}
			}
			else {
				result = "No data found on selected dates";
			}
		}  catch(Exception e) {
			result = "Error while generating report";
			logger.error(e);
			e.printStackTrace();
		}
		
		return result;

	}
	
	
	@SuppressWarnings("deprecation")
	private void generateCustomerReportsAndMail(CoreFiles file, long status, String reportTitle, String reportName, String fromdate, String todate, HttpServletResponse response) {
		  try {
		   Long bankId = null;
		   logger.info("status is : "+status);
		   String corefilename = file.getFilename();
		   String branchEmailAddress = null;
		   List<?> group = getGndDAO().getBranchGroupForReport(file.getId(), status);
		   if(group != null && group.size() > 0) {
		    Iterator<?> iterator = group.iterator();
		    while(iterator.hasNext()) {  
		     Object[] obj = (Object[]) iterator.next();
		     String imagePath = properties.getProperty("iconpath");
		     String source = properties.getProperty("sourceReport");
		     String dest = properties.getProperty("destinationReport");
		     File fileDir = new File(dest+"\\"+corefilename);
		     fileDir.mkdir();
		     JasperReport jasperReport = JasperCompileManager.compileReport(source); 
		     List<CreditCardDetails> list = getGndDAO().getCustomerReportByProduct(file.getId(), status, (String) obj[0]);
		     if(list != null && list.size() > 0) {
		      List<CustomerReport> customerReportList = new ArrayList<CustomerReport>();
		      Iterator<?> itr = list.iterator();
		      while(itr.hasNext()) {
		    	   Object[] obj1 = (Object[]) itr.next();
			       CreditCardDetails details = (CreditCardDetails) obj1[0];
			       CustomerReport report = new CustomerReport();
			       report.setBankId(details.getBankId());
			       bankId = report.getBankId();
			       report.setSerialNo(details.getSerialNo());
			       report.setInstitutionId(details.getInstitutionId());
			       report.setPrimaryAcctNo(details.getPrimaryAcctNo());
			       report.setProduct(details.getProduct());
			       report.setRuleStatus(details.getRuleStatus()); 

			       customerReportList.add(report);			      
		      }
		      if(customerReportList.size() > 0) {
			      Branch branch = getGndDAO().getEmail((String) obj[0],bankId);
			      if(branch != null) {
				      branchEmailAddress = branch.getEmailAddress() != null && !branch.getEmailAddress().isEmpty() ? branch.getEmailAddress().trim() : "";
				      String destFileName = fileDir.getPath()+"/"+file.getFilename()+"_"+(String)obj[0]+"_"+reportName+"_"+branchEmailAddress+".txt";
				      Map<String, Object> parameters = new HashMap<String, Object>();
				      parameters.put("email", branch != null ?branchEmailAddress : "");
				      parameters.put("ReportTitle", reportTitle);
				      parameters.put("iconpath", imagePath);
				      parameters.put("fileName",file.getFilename());
				      parameters.put("receivedDate",file.getReceivedDate());
				      parameters.put("homeBranchCode", (String) obj[0]);
				      parameters.put(JRHibernateQueryExecuterFactory.PARAMETER_HIBERNATE_SESSION, sessionFactory.getCurrentSession());
				      JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(customerReportList);
				      JasperPrint jasperPrint = JasperFillManager
				        .fillReport(jasperReport, parameters, ds);
				      
				      JRTextExporter exporterTxt = new JRTextExporter();
				      exporterTxt.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);  
				      exporterTxt.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFileName); 
				      exporterTxt.exportReport();
				      
				      //generating report at client side
				      
		//		      File reportfile = new File(destFileName);
		//				InputStream is = new FileInputStream(reportfile);
		//				response.setContentType("application/octet-stream");
		//				response.setHeader("Content-Disposition", "attachment; filename=\""	+ reportfile.getName() + "\"");
		//				OutputStream os = response.getOutputStream();
		//				byte[] buffer = new byte[1024];
		//				int len;
		//				FileUtils.copyFile(reportfile, os);
		//				os.flush();
		//				os.close();
		//				logger.info("report generated ");
		//				is.close();
		//				reportfile.delete();
		      		}
		      	}
		     }
		     
		    }
		   }
		  }  catch(Exception e) {
		   logger.error(e);
		   e.printStackTrace();
		  }
		 }


	public String getCsv(List<MasterAWBBO> masterbo) {
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("ddMyyyyhhmmss");
		String filepath=properties.getProperty("csv.filetoawbdownload");
		String fileName="awb"+sdf.format(date)+".csv";
		String fullpath=filepath+"//"+fileName;
		//File file=new File(filepath+fileName);
		try {
			//boolean val=file.createNewFile();
			//if(val){
			 //FileWriter writer = new FileWriter(fullpath);
			 CSVWriter writer = new CSVWriter(new FileWriter(fullpath));
			 List<String[]> data = new ArrayList<String[]>();
			 String header[]="serviceProviderName#AWB#status".split("#");
			 writer.writeNext(header);
			 
			/* writer.append("serviceProviderName"+"\t");
			 writer.append("AWB"+"\t");
			 writer.append("status"+"\t");
			 writer.append('\n');*/
			
			for(int i=0;i<masterbo.size();i++){
				data.add(new String[]{String.valueOf(((Map)masterbo.get(i)).get("serviceProviderName")+"\t"),String.valueOf(((Map)masterbo.get(i)).get("awbName")+"\t"),String.valueOf(((Map)masterbo.get(i)).get("status")+"\t")});
				 /*writer.append(String.valueOf(((Map)masterbo.get(i)).get("serviceProviderName")+"\t"));
				 writer.append(String.valueOf(((Map)masterbo.get(i)).get("awbName")+"\t"));
				 writer.append(String.valueOf(((Map)masterbo.get(i)).get("status")+"\t"));
				 writer.append('\n');*/
				 
			 }
			
			writer.writeAll(data);
			 writer.flush();
			 writer.close();
				
			//}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return fullpath;
	}
	@Transactional
	public List<BankBO> getBankList() {
		
		List<BankBO> reportList = null;
		logger.info("in generate pdf report class");
		try {
			
			List<Bank> list = (List<Bank>)getSessionFactory().getCurrentSession().createCriteria(Bank.class).addOrder(Order.desc("shortCode")).list();
			logger.info("No. of reports are : "+list.size());
			if(list.size() > 0) {
				reportList = new ArrayList<BankBO>();
				Iterator itr = list.iterator();
				while(itr.hasNext()) {
					Bank bank = (Bank) itr.next();
					reportList.add(buildBank(bank));
				}
			}
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return reportList;
		
	}

	private BankBO buildBank(Bank bank) {
		BankBO bankBO = new BankBO();
		bankBO.setId(bank.getId());
		bankBO.setBankCode(bank.getBankCode());
		bankBO.setBankId(bank.getBankId());
		bankBO.setBankName(bank.getBankName());
		bankBO.setShortCode(bank.getShortCode());
		bankBO.setPrefix(bank.getPrefix());
		bankBO.setStatus(bank.getStatus());
		return bankBO;

	}

	public String pinMailerReport(String awbName, Bank bank, String homeBranchCode,long status, String fileName,Integer isVIP, String corefilename, Long fileId, String format) {
		
		String result = "Failed to generate report";
		String reportTitle = "Pin Covering Note";
		String auffilename = fileName;
		
		try {		
			
			String reportName = fileName;
			String split[] = corefilename.split("\\.");
			String imagePath = properties.getProperty("iconpath");
			String source = properties.getProperty("aufsourcereport_auf2");
			String dest = properties.getProperty("aufdestinationReport");
			File fileDir = new File(dest+"\\"+corefilename.substring(2,split[0].length())+"\\"+bank.getShortCode()+"\\"+corefilename+"\\"+homeBranchCode);
			fileDir.mkdirs();
			

			String destFileName = fileDir+"/"+reportName+"."+format;
			if(new File(destFileName).exists()) {
				SimpleDateFormat sdf = new SimpleDateFormat("ss");
				destFileName = fileDir+"/"+reportName+"_"+sdf.format(new Date())+"."+format;
			} 
			logger.info("destination file name is : "+destFileName);
			JasperReport jasperReport = JasperCompileManager.compileReport(source);
			// Second, create a map of parameters to pass to the report.
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("ReportTitle", reportTitle);
//			parameters.put("institutionId", institutionId);
			parameters.put("homeBranchCode", homeBranchCode);
//			parameters.put("product", productId);
			parameters.put("Status", status);
			parameters.put("iconpath", imagePath);
			parameters.put("auffileName", auffilename);
//			parameters.put("embfilename", embfilename);
			parameters.put("isVIP", isVIP);
			parameters.put("corefilename",corefilename);
			parameters.put("fileId", fileId);
			parameters.put("pinAWB", awbName);
			parameters.put("bankId", bank.getBankId());
			parameters
			.put(JRHibernateQueryExecuterFactory.PARAMETER_HIBERNATE_SESSION,
					sessionFactory.getCurrentSession());
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters);
			if(format.equals("pdf")) { 
			JasperExportManager
			.exportReportToPdfFile(jasperPrint, destFileName);
			} 
			else if(format.equals("docx")) {
				JRDocxExporter exporter = new JRDocxExporter();
	            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));

	            File exportReportFile = new File(destFileName);

	            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(exportReportFile));
			}
			result = "Report generated sucessfully";
			logger.info(result);
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return result;
	}
		
	@Transactional
	public String generatePinCoveringNoteForAllFiles(long bankId, Date qcdate, int isNcf) {
		String result = "";
		try {
			List list = getGndDAO().getBranchPinGroup(bankId, qcdate,isNcf);
			if(list != null && list.size() > 0 ) {
				logger.info("No. of reports are "+list.size());
				Iterator itr = list.iterator();
				while(itr.hasNext()) {
					Object obj[] = (Object[]) itr.next();
					String processedBranchCode = (String) obj[0];
					String pinAWB = (String) obj[1];
					String bankShortCode = (String) obj[3];
					BigDecimal mailer = (BigDecimal) obj[4];
					long mailerno=mailer!=null?mailer.longValue()+1:1;//changes
					logger.info(mailer);
					SimpleDateFormat timeformat = new SimpleDateFormat("HHmmss");
					SimpleDateFormat dateformat = new SimpleDateFormat("ddMMyy");
					Date daybefore = DateUtils.addDays(qcdate, -1);
					logger.info("milli seconds time is : "+System.currentTimeMillis());
					String folderName = "";
					String fileTypeName = "";
					if(isNcf == 1) {
						folderName = "Non-Personalized";
						fileTypeName = "NP";
					}
					else {
						folderName = "Personalized";
						fileTypeName = "P";
					}
					
					String reportName = bankShortCode+"_"+fileTypeName+"_"+processedBranchCode+"_"+dateformat.format(daybefore)+"_"+dateformat.format(qcdate);//+"_"+random.substring(random.length()-3, random.length());
					String format = properties.getProperty("pinmailerformat");
					Integer isVIP = 0;
					String dest = properties.getProperty("aufdestinationReport");
					File fileDir = new File(dest+"\\"+dateformat.format(daybefore)+"\\"+bankShortCode+"\\"+folderName);
					fileDir.mkdirs();
					String destFileName = fileDir+"/"+reportName+"."+format;
					if(new File(destFileName).exists()) {
						logger.info(reportName +" already exists");
						SimpleDateFormat sdf = new SimpleDateFormat("SSS");
						destFileName = fileDir+"/"+reportName+"_"+sdf.format(new Date())+"."+format;
					} 
					result = pinMailerReportForBankWise(processedBranchCode,pinAWB,reportName, bankId, qcdate, isVIP, destFileName, format,mailerno);  //changes done
					
				}
			} else {
				result = "No reports to be generated on selected date "+qcdate;
			}
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return result;
	}

	@Transactional
	public String pinMailerReportForBankWise(String processedBranchCode, String pinAWB, String reportName, Long bankId, Date qcDate, Integer isVIP, String destFileName, String format,long mailerno) {  //changes done
		
		String result = "Failed to generate report";
		String reportTitle = "Pin Covering Note";
		try {		
			
			String imagePath = properties.getProperty("iconpath");
			String source = properties.getProperty("aufsourcereport_auf2_bank");
			logger.info("destination file name is : "+destFileName);
			JasperReport jasperReport = JasperCompileManager.compileReport(source);
			// Second, create a map of parameters to pass to the report.
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("ReportTitle", reportTitle);
//			parameters.put("institutionId", institutionId);
			parameters.put("processedBranchCode", processedBranchCode);
//			parameters.put("product", productId);
			parameters.put("iconpath", imagePath);
//			parameters.put("embfilename", embfilename);
			parameters.put("qcDate", qcDate);
			parameters.put("isVIP", isVIP);
			parameters.put("pinAWB", pinAWB);
			parameters.put("bankId", bankId);
			parameters.put("mailer", mailerno);
			parameters.put("Status", (long)PinStateManagerService.PIN_STATUS_AWB_ASSIGNED);
			
			logger.info("mailer number is : "+parameters.get("mailer"));
			parameters
			.put(JRHibernateQueryExecuterFactory.PARAMETER_HIBERNATE_SESSION,
					sessionFactory.getCurrentSession());
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters);
			if(format.equals("pdf")) { 
			JasperExportManager
			.exportReportToPdfFile(jasperPrint, destFileName);
			} 
			else if(format.equals("docx")) {
				JRDocxExporter exporter = new JRDocxExporter();
	            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));

	            File exportReportFile = new File(destFileName);

	            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(exportReportFile));
			}
			result = "Report generated sucessfully";
//			getQcProcessDAO().updatemailer(mailerno,bankId,processedBranchCode);			
			logger.info(result);
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return result;
	}


	
	
	}
