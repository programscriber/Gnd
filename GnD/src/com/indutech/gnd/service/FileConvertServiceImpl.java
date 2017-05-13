package com.indutech.gnd.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.didisoft.pgp.PGPException;
import com.didisoft.pgp.PGPLib;
import com.indutech.gnd.bo.CreditCardDetailsBO;
import com.indutech.gnd.bo.RecordEventBO;
import com.indutech.gnd.bo.drool.DroolRecordBO;
import com.indutech.gnd.dao.FileConverterDAOImpl;
import com.indutech.gnd.dao.FileDAOImpl;
import com.indutech.gnd.dao.FindPathImpl;
import com.indutech.gnd.dao.GNDDAOImpl;
import com.indutech.gnd.dto.Bank;
import com.indutech.gnd.dto.CoreFiles;
import com.indutech.gnd.dto.CreditCardDetails;
import com.indutech.gnd.dto.RecordEvent;
import com.indutech.gnd.enumTypes.FileStatus;
import com.indutech.gnd.enumTypes.FileType;
import com.indutech.gnd.enumTypes.RecordStatus;
import com.indutech.gnd.jasper.reports.JasperReportGenerator;
import com.indutech.gnd.util.StringUtil;

import org.apache.log4j.Logger;

@Component("fileConvertServiceImpl")
public class FileConvertServiceImpl  implements FileConvertService {
	

	private static final String PGP_EXTENSION = ".pgp";
//	private static final String PUBLIC_KEY = "D://gnd/CONFIG/keys/vidya_public.asc";
//	private static final String OUTPUT_FOLDER = "D://gnd/WORK/ALU_FILES/";

	private Properties properties = PropertiesLoader.getInstance().loadProperties();
//	private InputStream inputStream =getClass().getResourceAsStream("/camel-config.properties");


	Logger logger = Logger.getLogger(FileConvertServiceImpl.class);
	
	common.Logger log = common.Logger.getLogger(FileConvertServiceImpl.class);
	
	@Autowired
	private DroolRecordBO droolRecord;
	
	@Autowired
	private FileEncryptionService encryptionService;
	
	@Autowired
	private FileConverterDAOImpl fileConverterDAO;	
	
	@Autowired
	private GNDDAOImpl gndDAO;
	
	@Autowired
	private FindPathImpl findPath;
	
	@Autowired
	private FileDAOImpl fileDAO;
	
	@Autowired	
	private JasperReportGenerator jasperReports;
	
	public FileEncryptionService getEncryptionService() {
		return encryptionService;
	}
	public void setEncryptionService(FileEncryptionService encryptionService) {
		this.encryptionService = encryptionService;
	}
	public FileDAOImpl getFileDAO() {
		return fileDAO;
	}
	public void setFileDAO(FileDAOImpl fileDAO) {
		this.fileDAO = fileDAO;
	}
	public DroolRecordBO getDroolRecord() {
		return droolRecord;
	}
	public void setDroolRecord(DroolRecordBO droolRecord) {
		this.droolRecord = droolRecord;
	}			
		
	public final FindPathImpl getFindPath() {
		return findPath;
	}
	public final void setFindPath(FindPathImpl findPath) {
		this.findPath = findPath;
	}	
	
	public final FileConverterDAOImpl getFileConverterDAO() {
		return fileConverterDAO;
	}
	public final void setFileConverterDAO(FileConverterDAOImpl fileConverterDAO) {
		this.fileConverterDAO = fileConverterDAO;
	}
	public JasperReportGenerator getJasperReports() {
		return jasperReports;
	}
	public void setJasperReports(JasperReportGenerator jasperReports) {
		this.jasperReports = jasperReports;
	}	
	
	//body properties
	private StringBuilder actionCode;
	private StringBuilder cardNumber;
	private StringBuilder clientCode;
	private StringBuilder institutionCode;
	private StringBuilder branchCode;
	private StringBuilder vipFlag;
	private StringBuilder ownerCode;
	private StringBuilder basicCardFlag;
	private StringBuilder basicCardNumber;
	private StringBuilder title;
	private StringBuilder familyName;
	private StringBuilder firstName;
	private StringBuilder embossedName;
	private StringBuilder encodedName;
	private StringBuilder maritalStatus;
	private StringBuilder gender;
	private StringBuilder legelId;
	private StringBuilder nationalityCode;
	private StringBuilder noOfChildren;
	private StringBuilder creditLimit;
	private StringBuilder issuersClinet;
	private StringBuilder lodgingPeriod;
	private StringBuilder residenceStatus;
	private StringBuilder netYearlyIncome;
	private StringBuilder noOfDependents;
	private StringBuilder birthDate;
	private StringBuilder birthCity;
	private StringBuilder birthCountry;
	private StringBuilder address1;
	private StringBuilder address2;
	private StringBuilder address3;
	private StringBuilder address4;
	private StringBuilder cityCode;
	private StringBuilder zipCode;
	private StringBuilder countryCode;
	private StringBuilder phoneNo1;
	private StringBuilder phoneNo2;
	private StringBuilder mobilePhone;
	private StringBuilder emailId;
	private StringBuilder employer;
	private StringBuilder emplAddress1;
	private StringBuilder emplAddress2;
	private StringBuilder emplAddress3;
	private StringBuilder emplAddress4;
	private StringBuilder emplCityCode;
	private StringBuilder emplZipCode;
	private StringBuilder emplCountryCode;
	private StringBuilder contractStartDate;
	private StringBuilder employmentStatus;
	private StringBuilder openingDate;
	private StringBuilder startValDate;
	private StringBuilder productCode;
	private StringBuilder tariffCode;
	private StringBuilder deliveryMode;
	private StringBuilder account1;
	private StringBuilder account1Currency;
	private StringBuilder account1Type;
	private StringBuilder limitCashDOM;
	private StringBuilder limitPurchDOM;
	private StringBuilder limitTeDOM;
	private StringBuilder reserved1;
	private StringBuilder limitCashInt;
	private StringBuilder limitPurchInt;
	private StringBuilder limitTeInt;
	private StringBuilder reserved2;
	private StringBuilder authoLimitDOM;
	private StringBuilder authoLimitINT;
	private StringBuilder reserved3;
	private StringBuilder activityCode;
	private StringBuilder socioProfCode;
	private StringBuilder statusCode;
	private StringBuilder staffId;
	private StringBuilder deliveryFlag;
	private StringBuilder deliveryDate;
	private StringBuilder bankOrDSARef;
	private StringBuilder userDefinedFiled1;
	private StringBuilder userDefinedField2;
	private StringBuilder userDefinedField3;
	private StringBuilder userDefinedField4;
	private StringBuilder userDefinedField5;
	private StringBuilder embossLine3;
	private StringBuilder mailingAddress1;
	private StringBuilder mailingAddress2;
	private StringBuilder mailingAddress3;
	private StringBuilder mailingAddress4;
	private StringBuilder mailingZipCode;
	private StringBuilder mailingCityCode;
	private StringBuilder mailingCountryCode;
	private StringBuilder phoneHome;
	private StringBuilder phoneAlternate;
	private StringBuilder phoneMobile;
	private StringBuilder photoIndicator;
	private StringBuilder languageIND;
	private StringBuilder maidenName;
	private StringBuilder secondaryAcct1;
	private StringBuilder acctType1;
	private StringBuilder secondaryAcct2;
	private StringBuilder accType2;
	private StringBuilder applicationDate;
	private StringBuilder fourthLinePrintingData;
	private StringBuilder middleName;
	private StringBuilder homeBranchCode;
	private StringBuilder checkSum;
	private Date fileNameDate;
	
	
	//header properties
	private StringBuilder recordIndicatorHeader;
	private StringBuilder sourcetype;
	private StringBuilder bankOrBranchOrDCACode;
	private StringBuilder sequenceNumber;
	private StringBuilder dateAndTimeOfExtraction;
	private StringBuilder versionNumber;
	
	//footer properties
	private StringBuilder recordIndicatorFooter;
	private StringBuilder noOfRecords;
	
	//fineName properties
	private StringBuilder bankCode;
	private StringBuilder fileDate;
	private StringBuilder filename;
	
	private int fileSize;
	
	private File file = null;
	private FileWriter fw = null;
	private BufferedWriter bw = null;
	
	private SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	
	RecordStatus stat1 = RecordStatus.valueOf("AWB_ASSIGNED");
	String awbStatus = stat1.getRecordStatus();
	
	RecordStatus stat2 = RecordStatus.valueOf("AUF_CONVERTED");
	String convertedStatus = stat2.getRecordStatus();
	
	private String bankName;
	private String corefiledate;
	private String corefiledateplus;
	
	
	private String coreFileName;
	private String val;
	
	private Long fileId;

	
	@SuppressWarnings("rawtypes")
	@Async
	@Transactional
	@Override
	public synchronized void convertTxt() {
		try {
			
//			properties.load(inputStream);
			coreFileName = getDroolRecord().getFileName();
			fileId = getDroolRecord().getFileId();
			Bank bank = null;
			logger.info("file name for bank code is : "+coreFileName);
//			getJasperReports().generateReports(fileName);
			if(!StringUtil.isEMptyOrNull(coreFileName)) {
				String prefix = coreFileName.substring(0, 1);
				bank = getFileConverterDAO().getBankCodeByPrefix(prefix);
				if(bank != null) {
					bankCode = new StringBuilder(bank.getBankCode() != null?bank.getBankCode() : "000000");
					bankName = bank.getShortCode();
				}
			}
			List group= getFileConverterDAO().getCreditCardDetails(Long.parseLong(awbStatus), fileId);
			if(group != null && group.size() > 0) {
				Iterator iterator = group.iterator();
				while(iterator.hasNext()) {
					Object[] obj = (Object[]) iterator.next();
					List<CreditCardDetails> list = getFileConverterDAO().getGroup((String)obj[0], (String)obj[1], (String)obj[2], (Long)obj[3]);
					logger.info("inst id : "+obj[0]);
					logger.info("homeBranchCode is : "+obj[1]);
					logger.info("product is : "+obj[2]);
					fileSize = list.size();
					if(list != null && list.size() > 0) {
						Long alufileId = null;
						Iterator iterator1 =  list.iterator();				
						int request = 1;				
						while(iterator1.hasNext()) {
							CreditCardDetails creditCardDetails = (CreditCardDetails) iterator1.next();
//							creditCardDetails.setRsn(getGndDAO().getRSNSequenceNumber());
							CreditCardDetailsBO details = buildCreditCardDetailsBO(creditCardDetails);				
							if(request == 1) {
								String filename = generateFileName(details.getProcessedBranchCode(), details.getProduct(), coreFileName, bank, fileId);
								CoreFiles coreFile = new CoreFiles();
								coreFile.setFilename(filename);
								coreFile.setFileType(Long.parseLong(FileType.valueOf("AUF").getFileType()));
								coreFile.setDescription("AUF_CONVERTED");
								coreFile.setReceivedDate(fileNameDate);
								coreFile.setLineCount((long)fileSize);
								coreFile.setCoreFileId((Long) obj[3]);						
								coreFile.setProcessedDate(new Date());
								coreFile.setLcpcGroup(filename.substring(9,11));
								coreFile.setStatus(Long.parseLong(FileStatus.valueOf("AUF_CONVERTED").getFileStatus()));
								alufileId = getFileDAO().saveFile(coreFile);

								header( bw);
							}
							body(details, bw);
							if(list.size() == request) {
								footer( bw, list.size());
							}
							
							creditCardDetails.setStatus((long)CardStateManagerService.CARD_STATUS_AUFCONVERTED);
							creditCardDetails.setCreatedDate(new Date());
							creditCardDetails.setAufId(alufileId);
							creditCardDetails.setRuleStatus(CardStateManagerService.CARD_STATUS_AUFCONVERTED_STRING);
							getGndDAO().saveCardDetails(creditCardDetails);
							RecordEvent event = new RecordEvent();
							event.setRecordId(creditCardDetails.getCreditCardDetailsId());
							event.setEventDate(new Date());							
							event.setEventId(RecordEventBO.EVENT_AUF_GENERATED); 
							event.setDescription(RecordEventBO.getStatusInString(event.getEventId()));
							getGndDAO().saveRecordEvent(event);
							request++;
							
						}
					}
						bw.close();
					    
						StringBuilder embfileName = filename;
						String format = (String) properties.getProperty("pinmailerformat");
						Integer isVIP = 0;
						String pinMailerName = bankName+"_"+(String)obj[1]+"_"+(String)obj[2]+"_"+corefiledate+"_"+corefiledateplus;
						getJasperReports().pinMailerReport(bank, (String)obj[0], (String)obj[1], (String)obj[2], Long.parseLong(convertedStatus), pinMailerName, embfileName.replace(0, 3, "EMB").toString(), isVIP, coreFileName, fileId, format);
						getEncryptionService().encrypt(file, coreFileName, bank.getShortCode());
						

				} 
			}
		} catch(Exception io) {
			logger.error(io.getMessage());
			io.printStackTrace();
		}
		

	}
	

	public void body(CreditCardDetailsBO details, BufferedWriter bw) {
		try {
			bw.newLine();
			actionCode = new StringBuilder("I");
			actionCode.setLength(1);
		    val = actionCode.toString().replace('\0',' ');
			bw.write(val);
			
			cardNumber = new StringBuilder("");
			cardNumber.setLength(22);
		    val =cardNumber.toString().replace('\0',' ');
			bw.write(val);

			if(details.getRsn() != null)
				clientCode = new StringBuilder(details.getRsn().toString());
			else
				clientCode = new StringBuilder("");
			clientCode.setLength(24);
			val=clientCode.toString().replace('\0',' ');
			bw.write(val);
			
			bankCode.setLength(6);
			val=bankCode.toString().replace('\0',' ');
			bw.write(val);
			
			if(details.getHomeBranchCode() != null)
				branchCode = new StringBuilder(details.getProcessedBranchCode());
			else
				branchCode = new StringBuilder("");
			branchCode.setLength(6);
			val=branchCode.toString().replace('\0',' ');
			bw.write(val);

			vipFlag = new StringBuilder("0");
			vipFlag.setLength(1);
			val=vipFlag.toString().replace('\0',' ');
			bw.write(val);
			//logger.info("the vipFlag: "+val.length());

			ownerCode = new StringBuilder("1");
			ownerCode.setLength(1);
			val=ownerCode.toString().replace('\0',' ');
			bw.write(val);
			

			basicCardFlag = new StringBuilder("0");
			basicCardFlag.setLength(1);
			val=basicCardFlag.toString().replace('\0',' ');
			bw.write(val);
			

			basicCardNumber = new StringBuilder("");
			basicCardNumber.setLength(22);	
			val=basicCardNumber.toString().replace('\0',' ');
			bw.write(val);

			title = new StringBuilder("");
			title.setLength(4);
			val=title.toString().replace('\0',' ');
			bw.write(val);

			if(details.getCustomerSurName() != null)
				familyName = new StringBuilder(details.getCustomerSurName());
			else
				familyName = new StringBuilder("");
			familyName.setLength(20);
			val=familyName.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the familyName: "+val.length());
			
			if(details.getCustomerFirstName() != null) 
				firstName = new StringBuilder(details.getCustomerFirstName());
			else
				firstName = new StringBuilder("");
			firstName.setLength(20);
			val=firstName.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the firstName: "+val.length());

			if(!details.getEmbossName().trim().isEmpty()) 
				embossedName = new StringBuilder(details.getEmbossName());
			else
				embossedName = new StringBuilder(details.getEmbossName().trim().isEmpty()?details.getCustomerSurName(): "");
			embossedName.setLength(26);
			val=embossedName.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the embossedName "+val.length());

			if(!details.getEmbossName().trim().isEmpty())
				encodedName = new StringBuilder(details.getEmbossName());
			else
				encodedName = new StringBuilder(details.getEmbossName().trim().isEmpty()?details.getCustomerSurName(): "");
			encodedName.setLength(26);
			val=encodedName.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the encodedName:"+val.length());	
			
			maritalStatus = new StringBuilder("0");
			maritalStatus.setLength(1);
			val=maritalStatus.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the maritalStatus: "+val.length());
			
			gender = new StringBuilder("N");
			gender.setLength(1);
			val=gender.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the gender: "+val.length());

			legelId = new StringBuilder(" ");
			legelId.setLength(15);
			val=legelId.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the legelId: "+val.length());

			nationalityCode = new StringBuilder("356");
			nationalityCode.setLength(3);
			val=nationalityCode.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the nationalityCode: "+val.length());

			noOfChildren = new StringBuilder("");
			noOfChildren.setLength(2);
			val=noOfChildren.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the noOfChildren: "+val.length());
			
			creditLimit = new StringBuilder("");
			creditLimit.setLength(12);
			val=creditLimit.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the creditLimit: "+val.length());
			
			issuersClinet = new StringBuilder("0");
			issuersClinet.setLength(1);
			val=issuersClinet.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the issuersClinet: "+val.length());
			

			lodgingPeriod = new StringBuilder("");
			lodgingPeriod.setLength(2);
			val=lodgingPeriod.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the lodgingPeriod: "+val.length());
				
			residenceStatus = new StringBuilder("0");
			residenceStatus.setLength(1);
			val=residenceStatus.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the residenceStatus:"+val.length());
				
			netYearlyIncome = new StringBuilder("");
			netYearlyIncome.setLength(12);
			val=netYearlyIncome.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the netYearlyIncome: "+val.length());

			noOfDependents = new StringBuilder("");
			noOfDependents.setLength(2);
			val=noOfDependents.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the noOfDependents: "+val.length());

			if(!StringUtil.isEMptyOrNull(details.getDateOfBirth())) {
				Date bdate = new SimpleDateFormat("yyyy-MM-dd").parse(details.getDateOfBirth());
				birthDate = new StringBuilder(sdf.format(bdate));
			}
			else  {
				birthDate = new StringBuilder(details.getDateOfBirth());
			}
			birthDate.setLength(8);
			val=birthDate.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the birthDate: "+val.length());

			birthCity = new StringBuilder("");
			birthCity.setLength(5);
			val=birthCity.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the birthCity: "+val.length());

			birthCountry = new StringBuilder("356");
			birthCountry.setLength(3);
			val=birthCountry.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the birthCountry: "+val.length());

			if(details.getAddr1() != null) 
				address1 = new StringBuilder(details.getAddr1());			
			else
				address1 = new StringBuilder("");
			address1.setLength(30);
			val=address1.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the address1 :"+val.length());

			if(details.getAddr2() != null)
				address2 = new StringBuilder(details.getAddr2());
			else
				address2 = new StringBuilder("");
			address2.setLength(30);
			val=address2.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the address2: "+val.length());

			if(details.getAddr3() != null)
				address3 = new StringBuilder(details.getAddr3());
			else
				address3 = new StringBuilder("");
			address3.setLength(30);
			val=address3.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the address3: "+val.length());

			if(details.getAddr4() != null)
				address4 = new StringBuilder(details.getAddr4());
			else
				address4 = new StringBuilder("");
			address4.setLength(30);
			val=address4.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the address4: "+val.length());
			
			cityCode = new StringBuilder("001");
			cityCode.setLength(5);
			val=cityCode.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the cityCode: "+val.length());

			zipCode = new StringBuilder(details.getPin() != null ? details.getPin() : "");
			zipCode.setLength(10);
			val=zipCode.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the zipCode:"+val.length());

			countryCode = new StringBuilder("356");
			countryCode.setLength(3);
			val=countryCode.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the countryCode: "+val.length());

			phoneNo1 = new StringBuilder(details.getOfficePhone() != null ? details.getOfficePhone() : "");
			phoneNo1.setLength(15);
			val=phoneNo1.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the phoneNo1: "+val.length());
			
			phoneNo2 = new StringBuilder(details.getHomePhone() != null ? details.getHomePhone() : "");
			phoneNo2.setLength(15);
			val=phoneNo2.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the phoneNo2: "+val.length());

			mobilePhone = new StringBuilder(details.getMobileNo() != null ? details.getMobileNo() : "");
			mobilePhone.setLength(15);
			val=mobilePhone.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the mobilePhone: "+val.length());

			emailId = new StringBuilder(details.getEmail() != null ? details.getEmail() : "");
			emailId.setLength(50);
			val=emailId.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the emailId: "+val.length());
		
			employer = new StringBuilder("");
			employer.setLength(40);
			val=employer.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the employer: "+val.length());
			
			emplAddress1 = new StringBuilder("");
			emplAddress1.setLength(30);
			val=emplAddress1.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the emplAddress1: "+val.length());

			emplAddress2 = new StringBuilder("");
			emplAddress2.setLength(30);
			val=emplAddress2.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the emplAddress2: "+val.length());
			
			emplAddress3 = new StringBuilder("");
			emplAddress3.setLength(30);
			val=emplAddress3.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the emplAddress3: "+val.length());
			
			emplAddress4 = new StringBuilder("");
			emplAddress4.setLength(30);
			val=emplAddress4.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the emplAddress4: "+val.length());
			
			emplCityCode = new StringBuilder("");
			emplCityCode.setLength(5);
			val=emplCityCode.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the emplCityCode: "+emplCityCode.length());

			emplZipCode = new StringBuilder("");
			emplZipCode.setLength(10);
			val=emplZipCode.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the emplZipCode: "+val.length());
			
			emplCountryCode = new StringBuilder("356");
			emplCountryCode.setLength(3);
			val=emplCountryCode.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the emplCountryCode: "+val.length());

			contractStartDate = new StringBuilder("");
			contractStartDate.setLength(8);
			val=contractStartDate.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the contractStartDate:"+val.length());

			employmentStatus = new StringBuilder("6");
			employmentStatus.setLength(1);
			val=employmentStatus.toString().replace('\0',' ');
			bw.write(val);	

			
			logger.info("regd date is : "+details.getRegistrationDate());
			
			
			
			if(!StringUtil.isEMptyOrNull(details.getRegistrationDate())) {
				SimpleDateFormat sdfformat = new SimpleDateFormat("yyyy-MM-dd");
				Date date = sdfformat.parse(details.getRegistrationDate());
				openingDate = new StringBuilder(sdf.format(date));
			} else {
				openingDate = new StringBuilder(details.getRegistrationDate());
			}
			openingDate.setLength(8);
			val=openingDate.toString().replace('\0',' ');
			bw.write(val);
			
			startValDate = new StringBuilder("");
			startValDate.setLength(8);
			val=startValDate.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the startValDate:"+val.length());

			productCode = new StringBuilder(details.getProduct());             //problem
			productCode.setLength(3);
			val=productCode.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the productCode "+val.length());
	
			tariffCode = new StringBuilder("");
			tariffCode.setLength(3);
			val=tariffCode.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the tariffCode "+val.length());

			deliveryMode = new StringBuilder("0");
			deliveryMode.setLength(1);
			val=deliveryMode.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the deliveryMode "+val.length());

			account1 = new StringBuilder(details.getPrimaryAcctNo() != null ? details.getPrimaryAcctNo() : "");
			account1.setLength(24);
			val=account1.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the account1 "+val.length());

			account1Currency = new StringBuilder("356");
			account1Currency.setLength(3);
			val=account1Currency.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the account1Currency "+val.length());

			account1Type = new StringBuilder(details.getPrimaryAcctType() != null ? details.getPrimaryAcctType() : "");
			account1Type.setLength(2);
			val=account1Type.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the account1Type "+val.length());
		
			limitCashDOM = new StringBuilder("");
			limitCashDOM.setLength(12);
			val=limitCashDOM.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the limitCashDOM "+val.length());

			
			limitPurchDOM = new StringBuilder("");
			limitPurchDOM.setLength(12);
			val=limitCashDOM.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the limitPurchDOM "+val.length());

			limitTeDOM = new StringBuilder("");
			limitTeDOM.setLength(12);
			val=limitTeDOM.toString().replace('\0',' ');	
			bw.write(val);
//			logger.info("the limitTeDOM "+val.length());

			reserved1 = new StringBuilder("");
			reserved1.setLength(12);
			val=reserved1.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the reserved1 "+val.length());

			limitCashInt = new StringBuilder("");
			limitCashInt.setLength(12);
			val=limitCashInt.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the limitCashInt "+val.length());

			limitPurchInt = new StringBuilder("");
			limitPurchInt.setLength(12);
			val=limitPurchInt.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the limitPurchInt "+val.length());
			
			limitTeInt = new StringBuilder("");
			limitTeInt.setLength(12);
			val=limitTeInt.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the limitTeInt "+val.length());

			reserved2 = new StringBuilder("");
			reserved2.setLength(12);
			val=reserved2.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the reserved2 "+val.length());

			authoLimitDOM = new StringBuilder("");
			authoLimitDOM.setLength(12);
			val=authoLimitDOM.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the authoLimitDOM "+val.length());

			authoLimitINT = new StringBuilder("");
			authoLimitINT.setLength(12);
			val=authoLimitINT.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the authoLimitINT "+val.length());

			reserved3 = new StringBuilder("");
			reserved3.setLength(12);
			val=reserved3.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the reserved3 "+val.length());

			activityCode = new StringBuilder("");
			activityCode.setLength(4);
			val=activityCode.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the activityCode "+val.length());
			
			socioProfCode = new StringBuilder("");
			socioProfCode.setLength(4);
			val=socioProfCode.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the socioProfCode "+val.length());

			statusCode = new StringBuilder("00");
			statusCode.setLength(2);
			val=statusCode.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the statusCode "+val.length());
			
			staffId = new StringBuilder("");
			staffId.setLength(10);
			val=staffId.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the staffId "+val.length());
			
			deliveryFlag = new StringBuilder("0");
			deliveryFlag.setLength(1);
			val=deliveryFlag.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the deliveryFlag "+val.length());
			
			deliveryDate = new StringBuilder("");
			deliveryDate.setLength(8);
			val=deliveryDate.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the deliveryDate "+val.length());
		
			bankOrDSARef = new StringBuilder("");
			bankOrDSARef.setLength(14);
			val=bankOrDSARef.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the bankOrDSARef "+val.length());

			userDefinedFiled1 = new StringBuilder(details.getCustomerId() != null ? details.getCustomerId() : "");
			userDefinedFiled1.setLength(50);
			val=userDefinedFiled1.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the userDefinedFiled1 "+val.length());
			
			userDefinedField2 = new StringBuilder(details.getCity() != null ? details.getCity() : "");
			userDefinedField2.setLength(50);
			val=userDefinedField2.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the userDefinedField2 "+val.length());
			
			userDefinedField3 = new StringBuilder(details.getAwbName() != null ? details.getAwbName() : "");
			userDefinedField3.setLength(50);
			val=userDefinedField3.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the userDefinedField3 "+val.length());

			if(details.getFax() != null) 
				userDefinedField4 = new StringBuilder(details.getFax());
			else
				userDefinedField4 = new StringBuilder("");
			userDefinedField4.setLength(50);
			val=userDefinedField4.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the userDefinedField4 "+val.length());
			
			StringBuilder userDefinedField51 = new StringBuilder(details.getPrimaryAcctSurviour() != null ? details.getPrimaryAcctSurviour() : "");
			userDefinedField51.setLength(1);
			
			StringBuilder userDefinedField52 = new StringBuilder(details.getSecondaryAcctSurviour() != null ? details.getSecondaryAcctSurviour() : "");
			userDefinedField52.setLength(1);
			
			StringBuilder userDefinedField53 = new StringBuilder(details.getSecondarySurviour() != null ? details.getSecondarySurviour() : "");
			userDefinedField53.setLength(1);
			
			StringBuilder userDefinedField54 = new StringBuilder(details.getCardStatus() != null ? details.getCardStatus() : "");
			userDefinedField54.setLength(7);
			
			StringBuilder userDefinedField55 = new StringBuilder(details.getYearOfPassingSSC() != null ? details.getYearOfPassingSSC() : "");
			userDefinedField55.setLength(4);
			
			StringBuilder userDefinedField56 ;
			if(details.getYearOfMarriage() != null)
				 userDefinedField56 = new StringBuilder(details.getYearOfMarriage());
			else
				userDefinedField56 = new StringBuilder("");
			userDefinedField56.setLength(4);
			
			StringBuilder userDefinedField57;
			if(details.getFathersFirstName() != null) 
				userDefinedField57 = new StringBuilder(details.getFathersFirstName());
			else
				userDefinedField57 = new StringBuilder("");
			userDefinedField57.setLength(32);
			
			userDefinedField5 = new StringBuilder("");
			userDefinedField5.append(userDefinedField51);
			userDefinedField5.append(userDefinedField52);
			userDefinedField5.append(userDefinedField53);
			userDefinedField5.append(userDefinedField54);
			userDefinedField5.append(userDefinedField55);
			userDefinedField5.append(userDefinedField56);
			userDefinedField5.append(userDefinedField57);
			val=userDefinedField5.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the userDefinedField5 "+val.length());


			embossLine3 = new StringBuilder("");
			embossLine3.setLength(26);
			val=embossLine3.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the embossLine3 "+val.length());

			
			mailingAddress1 = new StringBuilder(details.getAddr1());
			mailingAddress1.setLength(45);
			val=mailingAddress1.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the mailingAddress1 "+val.length());

			mailingAddress2 = new StringBuilder(details.getAddr2());
			mailingAddress2.setLength(45);
			val=mailingAddress2.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the mailingAddress2 "+val.length());
			

			mailingAddress3 = new StringBuilder(details.getAddr3());
			mailingAddress3.setLength(45);
			val=mailingAddress3.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the mailingAddress3 "+val.length());
			

			mailingAddress4 = new StringBuilder(details.getAddr4());
			mailingAddress4.setLength(45);
			val=mailingAddress4.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the mailingAddress4 "+val.length());

			mailingZipCode = new StringBuilder(details.getPin());
			mailingZipCode.setLength(10);
			val=mailingZipCode.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the mailingZipCode "+val.length());
			
			mailingCityCode = new StringBuilder("001");
			mailingCityCode.setLength(5);
			val=mailingCityCode.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the mailingCityCode "+val.length());

			mailingCountryCode = new StringBuilder("356");
			mailingCountryCode.setLength(3);
			val=mailingCountryCode.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the mailingCountryCode "+val.length());

			phoneHome = new StringBuilder("");
			phoneHome.setLength(15);
			val=phoneHome.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the phoneHome "+val.length());

			phoneAlternate = new StringBuilder("");
			phoneAlternate.setLength(15);
			val=phoneAlternate.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the phoneAlternate "+val.length());

			phoneMobile = new StringBuilder("");
			phoneMobile.setLength(15);

			val=phoneMobile.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the phoneMobile "+val.length());
	
//			photoIndicator = new StringBuilder((details.getProduct()));
			photoIndicator = new StringBuilder(getFileConverterDAO().getPhotoIndicator(details.getProduct()));                 //problem
			photoIndicator.setLength(1);
			val=photoIndicator.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the photoIndicator "+val.length());

			languageIND = new StringBuilder("");
			languageIND.setLength(1);
			val=languageIND.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the languageIND "+val.length());

			maidenName = new StringBuilder(details.getMotherMaidenName() != null ? details.getMotherMaidenName() : "");
			maidenName.setLength(25);
			val=maidenName.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the maidenName "+val.length());
			
			secondaryAcct1 = new StringBuilder(details.getSecondaryAcct1() != null ? details.getPrimaryAcctNo().equals(details.getSecondaryAcct1()) ?"": details.getSecondaryAcct1():"");
			secondaryAcct1.setLength(17);
			val=secondaryAcct1.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the secondaryAcct1 "+val.length());

			if(details.getSecondaryAcct1Type() != null)
				acctType1 = new StringBuilder(details.getSecondaryAcct1Type());
			else
				acctType1 = new StringBuilder("");
			acctType1.setLength(2);
			val=acctType1.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the acctType1 "+val.length());

			secondaryAcct2 = new StringBuilder(details.getSecondaryAcct2() != null ? details.getPrimaryAcctNo().equals(details.getSecondaryAcct2())?"":details.getSecondaryAcct2() : "");
			secondaryAcct2.setLength(17);
			val=secondaryAcct2.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the secondaryAcct2 "+val.length());
			

			if(details.getSecondaryAcct2Type() != null) 
				accType2 = new StringBuilder(details.getSecondaryAcct2Type());
			else
				accType2 = new StringBuilder("");
			accType2.setLength(2);
			val=accType2.toString().replace('\0',' ');
			bw.write(val);
			
			logger.info("file date is in alu : "+fileDate);
			
			String split1[] = coreFileName.split("\\.");
			String dateField = split1[0].substring(2,split1[0].length());
			String day = dateField.substring(0,dateField.length()-4);
			String month = dateField.substring(dateField.length()-4, dateField.length()-2);
			String year = dateField.substring(dateField.length()-2,dateField.length());
			StringBuilder yearPrefix = new StringBuilder("20");
			StringBuilder yearSuffix = new StringBuilder(dateField.substring(4, dateField.length()));
			StringBuilder finalYear = yearPrefix.append(yearSuffix);
			StringBuilder finalDate = new StringBuilder(day+"/"+month+"/"+finalYear);
			finalDate.setLength(10);
			logger.info("final date alu is : "+finalDate);
			bw.write(finalDate.toString());
//			logger.info(applicationDate.insert(2, '/').insert(5, '/').toString().replace('\0',' '));
//			logger.info("the	applicationDate "+val.length());
			
			fourthLinePrintingData = new StringBuilder(details.getFourthLinePrintingData() != null ? details.getFourthLinePrintingData() : "");
			fourthLinePrintingData.setLength(25);
			val=fourthLinePrintingData.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the	fourthLinePrintingData "+val.length());

			middleName = new StringBuilder(details.getCustomerMiddleName() != null ? details.getCustomerMiddleName() : "");
			middleName.setLength(26);
			val=middleName.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the	middleName "+val.length());
			
			homeBranchCode = new StringBuilder(details.getProcessedBranchCode() != null ? details.getProcessedBranchCode() : "");
			homeBranchCode.setLength(6);
			val=homeBranchCode.toString().replace('\0',' ');
			bw.write(val);
//			logger.info("the	homeBranchCode "+val.length());

			checkSum = new StringBuilder("");
			checkSum.setLength(8);
			val=checkSum.toString().replace('\0',' ');
			bw.append(val);					
			
			
//			logger.info("the	checkSum "+val.length());

		} catch (IOException e) {
			logger.error(e);
			e.printStackTrace();
		} 
		catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		} 
	}	
	
	public void header( BufferedWriter bw) {
		try {
			
			recordIndicatorHeader = new StringBuilder("HD");
			recordIndicatorHeader.setLength(2);			
			bw.write(recordIndicatorHeader.toString());
	
			sourcetype = new StringBuilder("000");
			sourcetype.setLength(3);
			bw.write(sourcetype.toString());			
						
//			String bobdcValue = getPattern(details.getHomeBranchCode() != null ? details.getHomeBranchCode() : "000000");
			String bobdcValue = "000000";
			bankOrBranchOrDCACode = new StringBuilder(bobdcValue);
			bankOrBranchOrDCACode.setLength(6);
			bw.write(bankOrBranchOrDCACode.toString());				
			
			sequenceNumber = new StringBuilder("000001");
			sequenceNumber.setLength(6);
			bw.write(sequenceNumber.toString());
			
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
			
			SimpleDateFormat timeformat = new SimpleDateFormat("HHmmss");
			String time = timeformat.format(new Date());
			
			String filedate = fileDate.toString();
			String dateField = filedate.substring(0,6);			
			String day = dateField.substring(0,dateField.length()-4);
			String month = dateField.substring(dateField.length()-4, dateField.length()-2);
			String year = dateField.substring(dateField.length()-2,dateField.length());
			
			StringBuilder yearPrefix = new StringBuilder("20");
			StringBuilder yearSuffix = new StringBuilder(dateField.substring(4, dateField.length()));
			StringBuilder finalYear = yearPrefix.append(yearSuffix);
			String filedateafter = day+month+finalYear+time;

			
			logger.info("date in header is : "+filedateafter);
			
			dateAndTimeOfExtraction = new StringBuilder(filedateafter);
			dateAndTimeOfExtraction.setLength(14);
			bw.write(dateAndTimeOfExtraction.toString());
			
			versionNumber = new StringBuilder("2.0");
			versionNumber.setLength(3);
			bw.append(versionNumber.toString());
			
		} catch (IOException e) {
			logger.error(e);
			e.printStackTrace();
		}
		
		
	}
	
	public void footer(BufferedWriter bw, int size) {
		
		try {
			bw.newLine();
			recordIndicatorFooter = new StringBuilder("EOF");
			recordIndicatorFooter.setLength(3);		
			bw.write(recordIndicatorFooter.toString());
			
			noOfRecords = new StringBuilder(getPattern(size > 0 ? ""+size : "000000",6));
			noOfRecords.setLength(6);
			bw.write(noOfRecords.toString());
						
		} catch (IOException e) {
			logger.error(e);
			e.printStackTrace();
		}
		
	}
	
	public String generateFileName(String homeBranchCode, String product, String fileName, Bank bank, Long fileId) {
		 StringBuilder fileType;
		 
		 StringBuilder LCPCIndicator;
		 StringBuilder vendorCode;
		 StringBuilder seperator;
		 StringBuilder sequencenumber;
		 StringBuilder branchcode;
		 StringBuilder productcode;
		try {
		fileType = new StringBuilder("CRD");
		fileType.setLength(3);
		
		bankCode = new StringBuilder(bank.getBankCode());
		bankCode.setLength(6);		
		
		String split[] = fileName.split("\\.");
		
		if(split[1].toLowerCase().equals("ncf")) {
			LCPCIndicator = new StringBuilder("LN");
		}
		else if(split[0].charAt(1) == 'W') {
			LCPCIndicator = new StringBuilder("LP");
		}
		else {
			LCPCIndicator = new StringBuilder("NC");
		}
		LCPCIndicator.setLength(2);
		
		vendorCode = new StringBuilder("GD");
		vendorCode.setLength(2);
		
		branchcode = new StringBuilder(homeBranchCode);
		branchcode.setLength(5);
		
		productcode = new StringBuilder(product);
		productcode.setLength(3);
		
		SimpleDateFormat timeformat = new SimpleDateFormat("HHmmss");
		SimpleDateFormat dateformat = new SimpleDateFormat("ddMMyy");
		
		String split1[] = fileName.split("\\.");
		String date = split1[0].substring(2,split1[0].length());
		String time = timeformat.format(new Date());
		StringBuilder fileTime = new StringBuilder(time.trim());
		fileTime.setLength(6);
		Date fileDate = dateformat.parse(date);
		this.corefiledate = dateformat.format(fileDate);
		Date dayafter = DateUtils.addDays(fileDate, 1);
		this.fileNameDate = dayafter;
		logger.info("day after year is : "+dayafter);
		StringBuilder filedate = new StringBuilder(dateformat.format(dayafter));
		corefiledateplus = dateformat.format(dayafter);
		logger.info("file date is : "+filedate);
		this.fileDate = filedate;
//		filedate.append(fileTime);
		filedate.setLength(6);		
		seperator = new StringBuilder(".");
		seperator.setLength(1);
		
//		sequencenumber = new StringBuilder("001");
//		
		filename =new StringBuilder("");
		filename.append(fileType);
		filename.append(bankCode);
		filename.append(LCPCIndicator);
		filename.append(branchcode);
		filename.append(productcode);
		filename.append(vendorCode);
		filename.append(filedate);
		Long sequenceNumber = getFileConverterDAO().getSeqenceNumber(filename.toString(),fileId);
		logger.info("sequence no is : "+sequenceNumber);
		sequencenumber = new StringBuilder(getPattern(sequenceNumber.toString(),3));
		sequencenumber.setLength(3);
		filename.append(fileTime);
		filename.append(seperator);
		filename.append(sequencenumber);
		try {		
//		properties.load(inputStream);
		String path = properties.getProperty("aluPath");
//		Properties properties = PropertiesLoader.getProperties();
		logger.info("filename is : "+fileName);
		File filedir = new File(path+"\\"+fileName.substring(2, split1[0].length())+"\\"+bank.getShortCode()+"\\"+fileName);
		filedir.mkdirs();
		file = new File(filedir.getPath()+"/"+filename);
		fw = new FileWriter(file.getAbsoluteFile());
		bw = new BufferedWriter(fw);
		 
		}catch(IOException e) {
			logger.error(e);
			e.printStackTrace();
		} 
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return filename.toString();
	}
	
	public String getPattern(String text, int length) {
		logger.info("input is : "+text+" and length is : "+length);
		String sample = "";
		for(int i = 0;i<length;i++) {
			sample = sample+"0";
		}
		int len1 = sample.length();
		int len2 = text.length();
		char c[] = new char[length];
		int i = 0;
		int j = 0;
		while(i < len1) {
			if( i == (len1-len2)) {
				c[i] = text.charAt(j++);
				len2--;
			}
			else {
				c[i] = '0';
			}
			i++;
		}
		return new String(c);
	}
	
	public CreditCardDetailsBO buildCreditCardDetailsBO(CreditCardDetails creditCardDetails) {
		
		CreditCardDetailsBO details = new CreditCardDetailsBO();
		
		details.setSerialNo(creditCardDetails.getSerialNo());
		details.setInstitutionId(creditCardDetails.getInstitutionId());
		details.setCustomerId(creditCardDetails.getCustomerId());
		details.setHomeBranchCode(creditCardDetails.getHomeBranchCode());
		details.setIssueBranchCode(creditCardDetails.getIssueBranchCode());
		details.setPrimaryAcctNo(creditCardDetails.getPrimaryAcctNo());
		details.setPrimaryAcctType(creditCardDetails.getPrimaryAcctType());
		details.setPrimaryAcctSurviour(creditCardDetails.getPrimaryAcctSurviour()!=null?creditCardDetails.getPrimaryAcctSurviour():"");
		details.setSecondaryAcct1(creditCardDetails.getSecondaryAcct1()!=null?creditCardDetails.getSecondaryAcct1():"");
		details.setSecondaryAcct1Type(creditCardDetails.getSecondaryAcct1Type()!=null?creditCardDetails.getSecondaryAcct1Type():"");
		details.setSecondaryAcctSurviour(creditCardDetails.getSecondaryAcctSurviour()!=null?creditCardDetails.getSecondaryAcctSurviour():"");
		details.setSecondaryAcct2(creditCardDetails.getSecondaryAcct2()!=null?creditCardDetails.getSecondaryAcct2():"");
		details.setSecondaryAcct2Type(creditCardDetails.getSecondaryAcct2Type()!=null?creditCardDetails.getSecondaryAcct2Type():"");
		details.setSecondarySurviour(creditCardDetails.getSecondarySurviour()!=null?creditCardDetails.getSecondarySurviour():"");
		details.setEmbossName(creditCardDetails.getEmbossName()!=null?creditCardDetails.getEmbossName():"");
		details.setCustomerFirstName(creditCardDetails.getCustomerFirstName()!=null?creditCardDetails.getCustomerFirstName():"");
		details.setCustomerMiddleName(creditCardDetails.getCustomerMiddleName()!=null?creditCardDetails.getCustomerMiddleName():"");
		details.setCustomerSurName(creditCardDetails.getCustomerSurName()!=null?creditCardDetails.getCustomerSurName():"");
		details.setAddr1(creditCardDetails.getAddr1()!=null?creditCardDetails.getAddr1():"");
		details.setAddr2(creditCardDetails.getAddr2()!=null?creditCardDetails.getAddr2():"");
		details.setAddr3(creditCardDetails.getAddr3()!=null?creditCardDetails.getAddr3():"");
		details.setAddr4(creditCardDetails.getAddr4()!=null?creditCardDetails.getAddr4():"");
		details.setCity(creditCardDetails.getCity()!=null?creditCardDetails.getCity():"");
		details.setPin(creditCardDetails.getPin()!=null?creditCardDetails.getPin():"");
		details.setOfficePhone(creditCardDetails.getOfficePhone()!=null?creditCardDetails.getOfficePhone():"");
		details.setHomePhone(creditCardDetails.getHomePhone()!=null?creditCardDetails.getHomePhone():"");		
		details.setProduct(creditCardDetails.getProduct());		
		details.setCardStatus(creditCardDetails.getCardStatus()!=null?creditCardDetails.getCardStatus():"");
		details.setRegistrationDate(creditCardDetails.getRegistrationDate() != null ? creditCardDetails.getRegistrationDate().toString() : "");
		details.setFax(creditCardDetails.getFax()!=null?creditCardDetails.getFax():"");
		details.setEmail(creditCardDetails.getEmail()!=null?creditCardDetails.getEmail():"");
		details.setFathersFirstName(creditCardDetails.getFathersFirstName()!=null?creditCardDetails.getFathersFirstName():"");
		details.setMotherMaidenName(creditCardDetails.getMotherMaidenName()!=null?creditCardDetails.getMotherMaidenName():"");
		details.setDateOfBirth(creditCardDetails.getDateOfBirth() != null ? creditCardDetails.getDateOfBirth().toString() : "");
		details.setYearOfPassingSSC(creditCardDetails.getYearOfPassingSSC()!=null?creditCardDetails.getYearOfPassingSSC():"");
		details.setYearOfMarriage(creditCardDetails.getYearOfMarriage()!=null?creditCardDetails.getYearOfMarriage():"");
		details.setFourthLinePrintingData(creditCardDetails.getFourthLinePrintingData()!=null?creditCardDetails.getFourthLinePrintingData():"");
		details.setIsdCode(creditCardDetails.getIsdCode()!=null?creditCardDetails.getIsdCode():"");
		details.setMobileNo(creditCardDetails.getMobileNo()!=null?creditCardDetails.getMobileNo():"");
		details.setPrimaryAccountProductCode(creditCardDetails.getPrimaryAccountProductCode()!=null?creditCardDetails.getPrimaryAccountProductCode():"");
		details.setSecondaryAccount1ProductCode(creditCardDetails.getSecondaryAccount1ProductCode()!=null?creditCardDetails.getSecondaryAccount1ProductCode():"");
		details.setSecondaryAccount2ProductCode(creditCardDetails.getSecondaryAccount2ProductCode()!=null?creditCardDetails.getSecondaryAccount2ProductCode():"");
		details.setHomeBranchCircleCode(creditCardDetails.getHomeBranchCircleCode());
		details.setIssueBranchCircleCode(creditCardDetails.getIssueBranchCircleCode());
		details.setRsn(creditCardDetails.getRsn());
		details.setFildId(creditCardDetails.getFileId());;
		
		details.setAwbName(creditCardDetails.getCardAWB());
		
		details.setProcessedBranchCode(creditCardDetails.getProcessedBranchCode());
		
		return details;
	}
	
	
	public final GNDDAOImpl getGndDAO() {
		return gndDAO;
	}

	public final void setGndDAO(GNDDAOImpl gndDAO) {
		this.gndDAO = gndDAO;
	}

	public CreditCardDetailsBO buildCreditCardDetailsBORej(CreditCardDetails creditCardDetails) {
		
		CreditCardDetailsBO details = new CreditCardDetailsBO();
		details.setCustomerId(creditCardDetails.getCustomerId());
		details.setEmbossName(creditCardDetails.getEmbossName());
		return details;
	}
	
	public String bodyRej(CreditCardDetailsBO details, BufferedWriter bw) {
		String result="";
//		try {
//			bw.newLine();
			userDefinedFiled1 = new StringBuilder(details.getCustomerId() != null ? details.getCustomerId() : "");
			userDefinedFiled1.setLength(50);
			val=userDefinedFiled1.toString().replace('\0',' ');
//			bw.write(val);
			result=val;
			
			if(details.getEmbossName() != null) 
				embossedName = new StringBuilder(details.getEmbossName());
			else
				embossedName = new StringBuilder("");
			embossedName.setLength(26);
			val=embossedName.toString().replace('\0',' ');
//			bw.write(val);
			result=result+val;

//		}
//		catch (IOException e) {
//			logger.error(e);
//			e.printStackTrace();
//		} 
		
		return result;
		
	}	
	
	
	

	
	
}
