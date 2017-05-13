package com.indutech.gnd.vali;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.camel.Exchange;
import org.apache.camel.component.file.GenericFile;
import org.apache.log4j.Logger;
import org.drools.KnowledgeBase;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.indutech.gnd.bo.RecordEventBO;
import com.indutech.gnd.bo.drool.DroolRecordBO;
import com.indutech.gnd.dao.GNDDAOImpl;
import com.indutech.gnd.dto.CreditCardDetails;
import com.indutech.gnd.dto.TestData;
import com.indutech.gnd.enumTypes.RecordStatus;
import com.indutech.gnd.records.Record;
import com.indutech.gnd.service.PropertiesLoader;
import com.indutech.gnd.service.drool.DroolsKnowledgeBase;



public class RecordRuleSet1 {
	
	Properties properties = PropertiesLoader.getInstance().loadProperties();
	
	Logger logger = Logger.getLogger(RecordRuleSet1.class);
	
	@Autowired
	private GNDDAOImpl gndDAO;
	
	@Autowired
	private Record rec;
	
	@Autowired
	private DroolsKnowledgeBase droolsKnowledgeBase;
	
	
	
	public DroolsKnowledgeBase getDroolsKnowledgeBase() {
		return droolsKnowledgeBase;
	}

	public void setDroolsKnowledgeBase(DroolsKnowledgeBase droolsKnowledgeBase) {
		this.droolsKnowledgeBase = droolsKnowledgeBase;
	}

	RecordStatus stat2 = RecordStatus.valueOf("REJECT");
	String rejectedStatus = stat2.getRecordStatus();

	private int NoOfFields,recordLen;
	private String customerId;
	private String primaryAcNo;
	private String product;
	private StringBuffer buffer;	
	private Exchange exchange;
	HashMap<String, Object> record = null;
	
	
	
	public Record getRec() {
		return rec;
	}

	public void setRec(Record rec) {
		this.rec = rec;
	}

	public final GNDDAOImpl getGndDAO() {
		return gndDAO;
	}

	public final void setGndDAO(GNDDAOImpl gndDAO) {
		this.gndDAO = gndDAO;
	}	
 
	@SuppressWarnings("unchecked")
	public boolean recordRuleSet1Start(Exchange exchange) {
		this.exchange = exchange;
		record =(HashMap<String, Object>)exchange.getIn().getBody();
		
		return true;
	}
	
	public boolean isRecordLength_rule_4a() throws ValidationException	{
		buffer = new StringBuffer();		
		try {
//			HashMap<String, Object> record =(HashMap<String, Object>)exchange.getIn().getBody();
			@SuppressWarnings("rawtypes")
			Iterator itr = record.values().iterator();
			while(itr.hasNext()) {
				String str = (String) itr.next();
				buffer.append(str + "^");
			}
			//TODO:733
			recordLen=buffer.length();
			
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		if(recordLen==728)	{
			return true;
		}
		processRecordAndEvent("Record length : "+recordLen+" is not valid");
		return false;
	}
	
	public boolean isRecordField_rule_4b() throws ValidationException	{	
//		HashMap<String, Object> record =(HashMap<String, Object>)exchange.getIn().getBody();
        NoOfFields=record.size();
		if(NoOfFields == 44)	{
			return true;
		} 
		processRecordAndEvent("No of Fields : "+NoOfFields+" is not valid");
		return false;
	}

	public boolean isCIFNull_rule_4c() {
		customerId=(String)record.get("CUSTOMER ID");
		if(!(customerId.trim().isEmpty()))	{
			return true;
		}
		processRecordAndEvent("customer id is empty");
		return false;
	}
	
	public boolean isAccountNull_rule_4d()	{
//		HashMap<String, Object> record =(HashMap<String, Object>)exchange.getIn().getBody();
		primaryAcNo= (String) record.get("PRIMARY ACCT NO");
		if(!(primaryAcNo.trim().isEmpty()))	{
			return true;
		}
		processRecordAndEvent("Primary account number is empty");
		return false;		
	}
	
	public boolean isProductNull_rule_4e()	{
//		HashMap<String, Object> record =(HashMap<String, Object>)exchange.getIn().getBody();
		product= (String) record.get("PRODUCT");
		if(!(product.trim().isEmpty()))	{
			return true;
		}
		processRecordAndEvent("Product value is empty");
		return false;		
	}
	
	public boolean isCIF_Acc_ProUnique_rule_4f1(String str)	{
		return true;
	}
	
	@Transactional
	public boolean isCIF_Acc_ProUnique15_rule_4f2()	{
		//to do : last 15 days
		
		//TODO: if entry exists n DB
		//TODO: if found entry - FAIL


		List<CreditCardDetails> list = getGndDAO().checkRecordValidity(customerId, primaryAcNo, product);
		if(list.size() == 0) {
			return true;
		}
		processRecordAndEvent("the customerId : "+ customerId+" and the primaryAcNo : "+primaryAcNo+" and the product : "+product+" is already exist in the database");
		return false;
	}
	
	public void processRecordAndEvent(String info) {
		RecordEventBO event = new RecordEventBO();
		getRec().setRecordRejectedStatus();
//		Long recordId = getRec().processRawRecord(exchange);
//		event.setRecordId(recordId);
		event.setEventId((long) 1);
//		event.setEventDate(new Date());
		event.setDescription(info);
		
		getRec().processEvent(event);
	}
	
	@Transactional
	public void testing(Exchange exchange) {
		try {
			System.out.println("process has started : "+new Date());
			Map<Integer,String> map = new HashMap<Integer, String>();
			GenericFile<?> gfile = (GenericFile<?>) exchange.getIn().getBody();
			File file = (File) gfile.getFile();
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = "";
			String filename = file.getName();
			int i = 0;
			while((line = br.readLine()) != null) {
				map.put(i,	line);
				i++;
			}
			br.close();
			if(map.size() > 0) {
				System.out.println("In drool object creation "+new Date());
				String droolsFilePath= properties.getProperty("droolsFilePath");
				KnowledgeBase kbase = droolsKnowledgeBase.readKnowledgeBase(droolsFilePath);
				StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
				KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "test");
				System.out.println("Drool object created successfully : "+new Date());
				new File("D:/gndtest/output").mkdirs();
				int k = 0;
				BufferedWriter bw = null;
				int q = 3;
				int r = 0;
				if(map.size() % q > 0) {
					r = 1;
				}
				for(int x = 1 ; x <= (map.size() / q)+r; x++) {
					bw = new BufferedWriter(new FileWriter(new File("D:/gndtest/output/"+filename+String.valueOf(x)+".txt")));
					for(int j = k; j < map.size() ; j++,k++) {
						if(j > (x*q)-1) {
							break;
						}
						String record = map.get(j);
						String split[] = record.split("\\^");
						DroolRecordBO db = new DroolRecordBO();
						db.setSerialNo(split[0]);
						db.setInstitutionId(split[1]);
						db.setCustomerId(split[2]);
						db.setHomeBranchCode(split[3]);
						db.setIssueBranchCode(split[4]);
						db.setPrimaryAcctNo(split[5]);
						db.setPrimaryAcctType(split[6]);
						db.setPrimaryAcctSurviour(split[7]);
						db.setSecondaryAcct1(split[8]);
						db.setSecondaryAcct1Type(split[9]);
						db.setSecondaryAcctSurviour(split[10]);
						db.setSecondaryAcct2(split[11]);
						db.setSecondaryAcct2Type(split[12]);
						db.setSecondarySurviour(split[13]);
						db.setEmbossName(split[14]);
						db.setCustomerFirstName(split[15]);
						db.setCustomerMiddleName(split[16]);
						db.setCustomerSurName(split[17]);
						db.setAddr1(split[18]);
						db.setAddr2(split[19]);
						db.setAddr3(split[20]);
						db.setAddr4(split[21]);
						db.setCity(split[22]);
						db.setPin(split[23]);
						db.setOfficePhone(split[24]);
						db.setHomePhone(split[25]);
						db.setProduct(split[26]);
						db.setCardStatus(split[27]);
						db.setRegistrationDate(split[28] != null && !split[28].trim().isEmpty()? new SimpleDateFormat("dd/MM/yyyy").parse(split[28]) : null);
						db.setFax(split[29]);
						db.setEmail(split[30]);
						db.setFathersFirstName(split[31]);
						db.setMotherMaidenName(split[32]);
						db.setDateOfBirth(split[33] != null && !split[33].trim().isEmpty()? new SimpleDateFormat("ddMMyyyy").parse(split[33]) : null);
						db.setYearOfPassingSSC(split[34]);
						db.setYearOfMarriage(split[35]);
						db.setFourthLinePrintingData(split[36]);
						db.setIsdCode(split[37]);
						db.setMobileNo(split[38]);
						db.setPrimaryAccountProductCode(split[39]);
						db.setSecondaryAccount1ProductCode(split[40]);
						db.setSecondaryAccount2ProductCode(split[41]);
						db.setHomeBranchCircleCode(split[42]);
						db.setIssueBranchCircleCode(split[43]);
						
						ksession.insert(db);
						ksession.fireAllRules();

						TestData td = new TestData();
						
						if(db.getFlowStatus() == DroolRecordBO.FLOW_ERROR) {
							td.setStatus(0);
						}
						else {
							td.setStatus(1);
						}
						td.setRuleStatus(db.getRuleStatus());
						td.setRecord(record);
						
						getGndDAO().saveTestData(td);
						
						bw.write(record);
						bw.newLine();
					}
					bw.close();
				}
			}
			System.out.println("process has ended : "+new Date());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
