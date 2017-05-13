package com.indutech.gnd.service;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;












import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import au.com.bytecode.opencsv.CSVWriter;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.indutech.gnd.bo.BankBO;
import com.indutech.gnd.bo.CoreFileSummaryBO;
import com.indutech.gnd.bo.RecordEventBO;
import com.indutech.gnd.dao.GNDDAOImpl;
import com.indutech.gnd.dao.MasterAWBDAOImpl;
import com.indutech.gnd.dao.QcProcessDAO;
import com.indutech.gnd.dto.CreditCardDetails;
import com.indutech.gnd.dto.MasterAWB;
import com.indutech.gnd.dto.MasterBank;
import com.indutech.gnd.dto.MasterCourierService;
import com.indutech.gnd.dto.RecordEvent;
import com.indutech.gnd.emailService.EmailLaunchService;
import com.indutech.gnd.jasper.reports.JasperReportGenerator;

@Component("qcservice")
public class QcProcessService {
	
	Logger logger = Logger.getLogger(QcProcessService.class);
	Properties properties = PropertiesLoader.getInstance().loadProperties();
	
	@Autowired
	private GNDDAOImpl gndDAO;
	
	@Autowired
	private MasterAWBDAOImpl masterAWBDAO;
	
	@Autowired
	private QcProcessDAO qcprocessDao;
	@Autowired
	private JavaMailSender mailSender ;
	@Autowired
	private JasperReportGenerator jasperReport;
	
	
	
	public GNDDAOImpl getGndDAO() {
		return gndDAO;
	}
	public void setGndDAO(GNDDAOImpl gndDAO) {
		this.gndDAO = gndDAO;
	}
	public MasterAWBDAOImpl getMasterAWBDAO() {
		return masterAWBDAO;
	}
	public void setMasterAWBDAO(MasterAWBDAOImpl masterAWBDAO) {
		this.masterAWBDAO = masterAWBDAO;
	}
	public JasperReportGenerator getJasperReport() {
		return jasperReport;
	}
	public void setJasperReport(JasperReportGenerator jasperReport) {
		this.jasperReport = jasperReport;
	}
	public JavaMailSender getMailSender() {
		return mailSender;
	}
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	@Transactional
	public List<BankBO>  getBank() {
		List<MasterBank> masterbank=getQcprocessDao().getBankDao();
		return buildMasterBankBO(masterbank);
	}
	private List<BankBO> buildMasterBankBO(List<MasterBank> masterBank) {
		List<BankBO> listmasterBo = new ArrayList<BankBO>();
		int masterSize = masterBank.size();
		for (int i = 0; i < masterSize; i++) {
			MasterBank masteBank = masterBank.get(i);
			BankBO bankBo = new BankBO();
			bankBo.setId(masteBank.getId());
			bankBo.setBankId(masteBank.getBankId());
			bankBo.setShortCode(masteBank.getShortCode());
			bankBo.setBankName(masteBank.getBankName());
			listmasterBo.add(bankBo);
		}
		return listmasterBo;
	}
	public QcProcessDAO getQcprocessDao() {
		return qcprocessDao;
	}

	public void setQcprocessDao(QcProcessDAO qcprocessDao) {
		this.qcprocessDao = qcprocessDao;
	}
	@Transactional
	public Map<String, List<CoreFileSummaryBO>> getFileSummaryService(String bankid, String date) {
		Map<String, List<CoreFileSummaryBO>> map = new HashMap<String, List<CoreFileSummaryBO>>();
		List<Object[]> embossSumarry=null;
		List<CoreFileSummaryBO> coreFileSummaryBo=null;
		List<CoreFileSummaryBO> pinPrintSummaryBo=null;
		try {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfc = new SimpleDateFormat("MM/dd/yyyy");
		Long bankidl;
		String dateStr=null;
		if(bankid!=null&&date!=null){
			bankidl=Long.valueOf(bankid);
			Date dd = sdfc.parse(date);
			dateStr=sdf.format(dd);
			embossSumarry=getQcprocessDao().getListFileSummarydao(bankidl,dateStr);
			List<Object[]> NoAUFGeneratedList = getQcprocessDao().getNoAufList(bankidl, dateStr);
			List<Object[]> pinPrintSummary = getQcprocessDao().getNoAufListForPinPrint(bankidl, dateStr);
			coreFileSummaryBo= new ArrayList<CoreFileSummaryBO>();
			pinPrintSummaryBo = new ArrayList<CoreFileSummaryBO>();
			if(embossSumarry != null && embossSumarry.size() > 0 ) {
				for(Object[] coreFile:embossSumarry){
					CoreFileSummaryBO coreFileBo=new CoreFileSummaryBO();
					coreFileBo.setCorefileName(String.valueOf(coreFile[0]));
					coreFileBo.setDate(String.valueOf(coreFile[1]));
					coreFileBo.setLinkIndicator(String.valueOf(coreFile[2]));
					coreFileBo.setFileName(String.valueOf(coreFile[3]));
//					coreFileBo.setFileCount(String.valueOf(coreFile[4]));
					coreFileBo.setRecordCount(String.valueOf(coreFile[4]));
					coreFileSummaryBo.add(coreFileBo);
				}
			}
			
			if(pinPrintSummary != null && pinPrintSummary.size() > 0 ) {
				for(Object[] coreFile:pinPrintSummary){
					CoreFileSummaryBO coreFileBo=new CoreFileSummaryBO();
					coreFileBo.setCorefileName(String.valueOf(coreFile[0]));
					coreFileBo.setDate(String.valueOf(coreFile[1]));
					coreFileBo.setLinkIndicator(String.valueOf(coreFile[2]));
//					coreFileBo.setFileName(String.valueOf(coreFile[3]));
					coreFileBo.setFileCount(String.valueOf(coreFile[3]));
					coreFileBo.setRecordCount(String.valueOf(coreFile[4]));
					pinPrintSummaryBo.add(coreFileBo);
				}
			}
			
			if(NoAUFGeneratedList != null && NoAUFGeneratedList.size() > 0 ) {
				for(Object[] coreFile:NoAUFGeneratedList){
					CoreFileSummaryBO coreFileBo=new CoreFileSummaryBO();
					coreFileBo.setCorefileName(String.valueOf(coreFile[0]));
					coreFileBo.setDate(String.valueOf(coreFile[1]));
					coreFileBo.setLinkIndicator(String.valueOf(coreFile[2]));
					coreFileBo.setFileName("_");
					coreFileBo.setFileCount("0");
					coreFileBo.setRecordCount("0");
					coreFileSummaryBo.add(coreFileBo);
					pinPrintSummaryBo.add(coreFileBo);
				}
			}

		}
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		map.put("Embossa_Summary_Report", coreFileSummaryBo);
		map.put("Pin_Print_Summary_Report", pinPrintSummaryBo);
		return map;
	}
	private List<CoreFileSummaryBO> buildCoreFileSummaryBo(List<Object[]> fileSumarry) {
		List<CoreFileSummaryBO> records = new ArrayList<CoreFileSummaryBO>();
		if(fileSumarry.size()>0){
			for(Object[] coreFile:fileSumarry){
				CoreFileSummaryBO coreFileBo=new CoreFileSummaryBO();
				if ((coreFile[0] != null)
						&& (!String.valueOf(coreFile[0]).isEmpty())) {
					coreFileBo.setCorefileName(String.valueOf(coreFile[0]));
				}
				if ((coreFile[1] != null)
						&& (!String.valueOf(coreFile[1]).isEmpty())) {
					coreFileBo.setDate(String.valueOf(coreFile[1]));
				}
				if ((coreFile[2] != null)
						&& (!String.valueOf(coreFile[2]).isEmpty())) {
					coreFileBo.setLinkIndicator(String.valueOf(coreFile[2]));
				}
				if ((coreFile[3] != null)
						&& (!String.valueOf(coreFile[3]).isEmpty())) {
					coreFileBo.setFileName(String.valueOf(coreFile[3]));
				}
				if ((coreFile[4] != null)
						&& (!String.valueOf(coreFile[4]).isEmpty())) {
					coreFileBo.setFileCount(String.valueOf(coreFile[4]));
				}
				if ((coreFile[5] != null)
						&& (!String.valueOf(coreFile[5]).isEmpty())) {
					coreFileBo.setRecordCount(String.valueOf(coreFile[5]));
				}
				records.add(coreFileBo);
			}
			
		}
		return records;
	}
	@Transactional
	public List<RecordEventBO>  sendEmail(List<CoreFileSummaryBO> listcoreFile, String bankVal)  {
		List<RecordEvent> recordEvent=null;
		List<RecordEventBO> recordEventBo=null;
		try{
		String emailString="";
		 emailString+="<html><body><p>"+properties.getProperty("paragraphtxt")+"</p></br><table style='border:1px solid black;'>";
		 emailString+="<tr style='border: 1px solid black;'><th style='border: 1px solid black;'>core_file_name</th><th style='border: 1px solid black;'>QC_Output_Date</th><th style='border: 1px solid black;'>LinkIndicator</th><th style='border: 1px solid black;'>File_name</th><th style='border: 1px solid black;'>File_count</th><th style='border: 1px solid black;'>Record_Counts</th></tr>";
		 for(int i =0;i<listcoreFile.size();i++){
    	CoreFileSummaryBO coreFile=listcoreFile.get(i);
    	System.out.println(coreFile.getCorefileName());
         System.out.println("in results...");
         emailString+="<tr style='border: 1px solid black;'>";
         emailString+="<td style='border: 1px solid black;'>";
        
         emailString+=coreFile.getCorefileName();
         emailString+="</td>";

         emailString+="<td style='border: 1px solid black;'>";
         emailString+=coreFile.getDate();
         emailString+="</td>";

         emailString+="<td style='border: 1px solid black;'>";
         emailString+=coreFile.getLinkIndicator();
         emailString+="</td>";

         emailString+="<td style='border: 1px solid black;'>";
         emailString+=coreFile.getFileName();
         emailString+="</td>";
         
         emailString+="<td style='border: 1px solid black;'>";
         emailString+=coreFile.getFileCount();
         emailString+="</td>";
         
         emailString+="<td style='border: 1px solid black;'>";
         emailString+=coreFile.getRecordCount();
         emailString+="</td>";
         
         emailString+="<tr>";
     }

     emailString+="</table></body></html>";
     

	EmailLaunchService emaillauncher=new EmailLaunchService();
	emaillauncher.sendHtmlEmail(emailString, properties);     
     recordEvent = getQcprocessDao().getAddEvent(bankVal);
    recordEventBo=buildRecordEvent(recordEvent);
	}catch(Exception e){
		e.printStackTrace();
			System.out.println("QC process email");
	}
		return recordEventBo;
	}
	public List<RecordEventBO> buildRecordEvent(List<RecordEvent> eventlist) {
		 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		List<RecordEventBO> eventList=new ArrayList<RecordEventBO>();
		for(RecordEvent event:eventlist){
		RecordEventBO eventBO = new RecordEventBO();
		eventBO.setDescription(event.getDescription());	 
		eventBO.setEventDateStr(formatter.format(event.getEventDate()));
		eventBO.setEventId(event.getEventId());
		eventBO.setId(event.getId());
		eventBO.setRecordId(event.getRecordId());
		eventList.add(eventBO);
		}
		return eventList;
	}
	@SuppressWarnings("rawtypes")
	public String downloadFile(
			List<CoreFileSummaryBO> masterbo) {
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("ddMyyyyhhmmss");
		String filepath=properties.getProperty("csv.filetoawbdownload");
		String fileName="coreFilesum"+sdf.format(date)+".csv";
		String fullpath=filepath+"//"+fileName;
		try {
			 CSVWriter writer = new CSVWriter(new FileWriter(fullpath));
			 List<String[]> data = new ArrayList<String[]>();
			 String header[]="core_file_name#QC_Output_Date#LinkIndicator#File_name#File_count#Record_Counts".split("#");
			 writer.writeNext(header);
			for(int i=0;i<masterbo.size();i++){
				data.add(new String[]{String.valueOf(((Map)masterbo.get(i)).get("core_file_name")+"\t"),
						String.valueOf(((Map)masterbo.get(i)).get("QC_Output_Date")+"\t"),
						String.valueOf(((Map)masterbo.get(i)).get("LinkIndicator")+"\t"),
						String.valueOf(((Map)masterbo.get(i)).get("File_name")+"\t"),
						String.valueOf(((Map)masterbo.get(i)).get("File_count")+"\t"),
						String.valueOf(((Map)masterbo.get(i)).get("Record_Counts")+"\t")});				 
			 }
			writer.writeAll(data);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fullpath;
	}
	
	@Transactional
	public void awbAssignment(long bankId, Date qcdate) {
		try {
		Long pinstatus = (long) PinStateManagerService.PIN_STATUS_UNINITIALIZED;
			assignPinAWB(pinstatus, bankId, qcdate, 0);  //value 0 indicates pin awb assignment for personalized cards
			assignPinAWB(pinstatus, bankId, qcdate, 1); //value 1 indicates pin awb assignment for non personalized cards(ncf)
		
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}
	private void assignPinAWB(Long pinstatus, long bankId, Date qcdate, int isNCF) {
		try {
			List<CreditCardDetails> list = null;
			if(isNCF == 0) {
				list = getQcprocessDao().getDetailsForPinAWB(pinstatus, bankId, qcdate);
			} else if(isNCF == 1) {
				list = getQcprocessDao().getNcfDetailsForPinAWB(pinstatus, bankId, qcdate);
			}
			Multimap<String, CreditCardDetails> map = null;
			if(list != null && list.size() > 0) {
				map = ArrayListMultimap.create();
				Iterator<CreditCardDetails> itr = list.iterator();
				while(itr.hasNext()) {
					CreditCardDetails details = (CreditCardDetails) itr.next();
					map.put(details.getProcessedBranchCode(), details);
				}
				if(map != null && map.size() > 0) {
					MasterCourierService corierServicePin = (MasterCourierService) getMasterAWBDAO().getServiceProvider((long)CorierServiceProviders.INDIAN_POST_MUMBAI);
					Set<String> processedBranchCodeList = map.keySet();
					Iterator<String> branchIterator = processedBranchCodeList.iterator();
					while(branchIterator.hasNext()) {
						String processedBranchCode = (String) branchIterator.next();
						List<CreditCardDetails> creditcardDetailsIdList = (List<CreditCardDetails>) map.get(processedBranchCode);
						if(creditcardDetailsIdList != null && creditcardDetailsIdList.size() > 0) {
	//						if(creditcardDetailsIdList.size() > PinStateManagerService.PIN_STATUS_AWB_RANGE) {
							int awbRange = PinStateManagerService.PIN_STATUS_AWB_RANGE;
								int x = 0;
								if(creditcardDetailsIdList.size() % awbRange > 0) {
									x = 1;
								}
								int k = 0;
								for(int i = 1 ; i <= (creditcardDetailsIdList.size() / awbRange)+x ; i++) {
									MasterAWB awbPin = getMasterAWBDAO().getAWBForChennaiAndMumbai((long)CorierServiceProviders.INDIAN_POST_MUMBAI);
									if(awbPin != null) {
										for(int j = k ; j < creditcardDetailsIdList.size(); j++,k++) {
											if(j > (awbRange * i)-1) {
												break;
											}
											CreditCardDetails details = (CreditCardDetails) creditcardDetailsIdList.get(j);
											details.setPinAWB(awbPin.getAwbName());
											details.setCreatedDate(new Date());
											details.setPinstatus((long)PinStateManagerService.PIN_STATUS_AWB_ASSIGNED);
											details.setRuleStatus(PinStateManagerService.PIN_STATUS_AWB_ASSIGNED_STRING);
											details.setPinServiceProvider(corierServicePin.getServiceProviderName());
											getGndDAO().saveCardDetails(details);
											saveRecordEvent(details.getCreditCardDetailsId(),(long) PinStateManagerService.PIN_STATUS_AWB_ASSIGNED,"PIN"+ RecordEventBO.getStatusInString(RecordEventBO.EVENT_AWB_ASSIGNED));
										}
									}
								}
							}
//						} else {
//							MasterAWB awbPin = getMasterAWBDAO().getAWBForChennaiAndMumbai((long)CorierServiceProviders.INDIAN_POST_MUMBAI);
//							if(awbPin != null) {
//								for(int i = 0 ; i < creditcardDetailsIdList.size(); i++) {
//									CreditCardDetails details = (CreditCardDetails) creditcardDetailsIdList.get(i);
//									details.setPinAWB(awbPin.getAwbName());
//									details.setCreatedDate(new Date());
//									details.setPinstatus((long)PinStateManagerService.PIN_STATUS_AWB_ASSIGNED);
//									details.setRuleStatus(PinStateManagerService.PIN_STATUS_AWB_ASSIGNED_STRING);
//									details.setPinServiceProvider(corierServicePin.getServiceProviderName());
//									getGndDAO().saveCardDetails(details);
//									saveRecordEvent(details.getCreditCardDetailsId(), RecordEventBO.EVENT_AWB_ASSIGNED,"PIN"+ RecordEventBO.getStatusInString(RecordEventBO.EVENT_AWB_ASSIGNED));
//								}
//							}
//							
//						}
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
	}
	
	public void saveRecordEvent(Long recordId, Long eventId, String description) {
		RecordEvent event = new RecordEvent();
		event.setDescription(description);
		event.setEventDate(new Date());
		event.setRecordId(recordId);
		event.setEventId(eventId);
		getGndDAO().saveRecordEvent(event);
	}
	public String generatePinCoveringNoteForAllFiles(long bankId, Date qcdate) {
		String msg = null;
		try {
			msg = getJasperReport().generatePinCoveringNoteForAllFiles(bankId,qcdate,0); //0 indicates pin covering note for the personalized cards
			getJasperReport().generatePinCoveringNoteForAllFiles(bankId,qcdate,1); //1 indicates pin covering note for the non-personalized (ncf) cards
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return msg;
	}
		
}
