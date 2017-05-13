package com.indutech.gnd.service.emboss;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.camel.Exchange;
import org.apache.camel.component.file.GenericFile;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.indutech.gnd.bo.CreditCardDetailsBO;
import com.indutech.gnd.bo.drool.DroolRecordBO;
import com.indutech.gnd.dao.FileDAO;
import com.indutech.gnd.dao.FileDAOImpl;
import com.indutech.gnd.dao.GNDDAOImpl;
import com.indutech.gnd.dao.MasterAWBDAOImpl;
import com.indutech.gnd.dao.ProductDAOImpl;
import com.indutech.gnd.dto.Bank;
import com.indutech.gnd.dto.CoreFiles;
import com.indutech.gnd.dto.CreditCardDetails;
import com.indutech.gnd.dto.EmbossaFormat;
import com.indutech.gnd.dto.EmbossaRSN;
import com.indutech.gnd.dto.MasterCourierService;
import com.indutech.gnd.dto.RecordEvent;
import com.indutech.gnd.enumTypes.FileStatus;
import com.indutech.gnd.enumTypes.FileType;
import com.indutech.gnd.jasper.reports.JasperReportGenerator;
import com.indutech.gnd.service.CardStateManagerService;
import com.indutech.gnd.service.CorierServiceProviders;
import com.indutech.gnd.service.FileStateManager;
import com.indutech.gnd.service.PropertiesLoader;
import com.indutech.gnd.util.CustomEncryption;

@Component("embossService")
public class EmbossService {
	
	Logger logger = Logger.getLogger(EmbossService.class);
	
	common.Logger log = common.Logger.getLogger(EmbossService.class);
	
//	private Properties properties = new Properties();
//	private InputStream inputStream =getClass().getResourceAsStream("/camel-config.properties");
	private Properties properties = PropertiesLoader.getInstance().loadProperties();
	
	@Autowired
	private GNDDAOImpl gndDAO;
	
	@Autowired
	private DroolRecordBO droolRecord;
	
	@Autowired
	private ProductDAOImpl productDAO;
	
	@Autowired
	private MasterAWBDAOImpl masterAWBDAO;
	
	@Autowired	
	private JasperReportGenerator jasperReports;
	
	@Autowired
	private FileDAOImpl fileDAO;
	
	

	public FileDAOImpl getFileDAO() {
		return fileDAO;
	}


	public void setFileDAO(FileDAOImpl fileDAO) {
		this.fileDAO = fileDAO;
	}


	public JasperReportGenerator getJasperReports() {
		return jasperReports;
	}


	public void setJasperReports(JasperReportGenerator jasperReports) {
		this.jasperReports = jasperReports;
	}


	public MasterAWBDAOImpl getMasterAWBDAO() {
		return masterAWBDAO;
	}


	public void setMasterAWBDAO(MasterAWBDAOImpl masterAWBDAO) {
		this.masterAWBDAO = masterAWBDAO;
	}


	public ProductDAOImpl getProductDAO() {
		return productDAO;
	}


	public void setProductDAO(ProductDAOImpl productDAO) {
		this.productDAO = productDAO;
	}


	public DroolRecordBO getDroolRecord() {
		return droolRecord;
	}


	public void setDroolRecord(DroolRecordBO droolRecord) {
		this.droolRecord = droolRecord;
	}


		public GNDDAOImpl getGndDAO() {
		return gndDAO;
	}


	public void setGndDAO(GNDDAOImpl gndDAO) {
		this.gndDAO = gndDAO;
	}


		private static final int key = 10;
	
		private static String rawString;//"5576290000000018 SBM VALIDITY              %B5576290000000018^SBM VALIDITY             ^200722600559000000? ;5576290000000018=20072265590000000? 5576290000000018D20072268220000000 00822000000 2007 JUL2015 SBM VALIDITY              5576 2900 0000 0018 JUL2020 559 481 822 5576290000000018 481                           5046451521               GITC BELAPUR                             NAVI MUMBAI                                                                                                                MUMBAI                    412405                    00007 00000011111111111                   ";
		private String panNumber;
		private String cardHolderName5F20;
		private String track1;
		private String track2;
		private String track2EquivData57;
		private String track1Discretionary9F1F;
		private String applicationExpirationDate5F24;
		private String applicationEffectiveDate5F25;
		private String embossName;
		private String embossCardNumber;
		private String embossExpiryDate;	
		private String CVV1;
		private String CVV2;
		private String ICVV;
		private String rearIndentPANCVV2;
		private String fourthLinePrintingdata;
		private String recordReferenceNumber;
		private String speedPostBarCode;
		private String mailAddress1;
		private String mailAddress2;
		private String mailAddress3;
		private String mailAddress4;
		private String city;
		private String pinCode;
		private String branchCode;
		private String accountNumber;
		private String ICC_PIN;
		
		
		private BufferedWriter bw;
		private FileWriter fw;
		
		private StringBuilder rsn;
		private StringBuilder product;
		private StringBuilder bankshortCode;
		private StringBuilder firstname;
		private StringBuilder lastname;
		private StringBuilder phonenumber;
		private StringBuilder cardStatus;
		
		
		@Transactional
		public synchronized GenericFile processEmbossRecord(Exchange  exchange) throws InterruptedException {
			
			GenericFile gfile = null;
			String networkName = null;
			Integer rsnFrom = null;
			Integer rsnTo = null;
			List<EmbossaRSN> embossaRSN = null;
			BufferedReader br = null;
			try {
				logger.info("Embossa file processing has started");
				 gfile = (GenericFile) exchange.getIn().getBody();
				 String filename = gfile.getFileNameOnly();
				 String[] split = filename.split("\\.");
				 List<CoreFiles> filelist = getFileDAO().getFileList(filename);
				 Long fileId = null;
				 if(filelist != null && filelist.size() > 0) {
					 CoreFiles file = (CoreFiles) filelist.get(0);
					 fileId  = file.getId();
				 }
				 String productCode = null;
				 
				 if(split[0].substring(0, 3).equalsIgnoreCase("EMB") && split[0].length() == 27 && !split[1].equalsIgnoreCase("txt")) {
					 String bankCode = filename.substring(3,9);
					 productCode =  filename.substring(16, 19);
					 embossaRSN = getGndDAO().getEmbossaRSNForEMB1(productCode, bankCode,FileStateManager.AUF_FORMAT_1);
				 }
				 else if(split[0].substring(0, 3).equalsIgnoreCase("EMB") && split[0].length() == 25 && split[1].equalsIgnoreCase("txt")) {
					 String bankShortCode = split[0].substring(3, 6);
					 productCode = split[0].substring(11, 14);
					 embossaRSN = getGndDAO().getEmbossaRSNForEMB2(productCode, bankShortCode,FileStateManager.AUF_FORMAT_2);
				 }
				 if(embossaRSN != null && embossaRSN.size() > 0) {
					 EmbossaRSN embossa = (EmbossaRSN)embossaRSN.get(0);
					 rsnFrom = embossa.getRsnFrom();
					 rsnTo = embossa.getRsnTo();
				 }
					 
				File file = (File) gfile.getFile();
				br =new BufferedReader(new FileReader(file));
				String line = null;
				int i = 0;
				while ((line = br.readLine())!= null) {	  
					i++;
					rawString = line;					
					panNumber = rawString.substring(0,16);
					String maskedPan = CustomEncryption.encrypt(panNumber);
					
					recordReferenceNumber = rawString.substring(rsnFrom, rsnTo);
					
					if(recordReferenceNumber != null && recordReferenceNumber.trim().isEmpty() == false && Pattern.compile("\\d+").matcher(recordReferenceNumber.trim()).matches()) {
						List<CreditCardDetails> list = getGndDAO().getRecordRSN(Long.parseLong(recordReferenceNumber.trim()));
						List<CreditCardDetails> duplicateList = getGndDAO().getDuplicateRecordRSN(Long.parseLong(recordReferenceNumber.trim()));
						if(list.size() > 0 && duplicateList.size() == 0) {
							CreditCardDetails details = (CreditCardDetails) list.get(0);
							details.setStatus((long)CardStateManagerService.CARD_STATUS_EMBOSSA_RECEIVED);
							details.setPanMasked(maskedPan);
							details.setCreatedDate(new Date());
							details.setRuleStatus(CardStateManagerService.CARD_STATUS_EMBOSSA_RECEIVED_STRING);
							details.setEmbossaId(fileId);
/*cr no 42*/				details.setEmboBatchName(getEmboBatchNameService(filename));
							getGndDAO().saveCardDetails(details);
							saveRecordEvent(details.getCreditCardDetailsId(),(long)CardStateManagerService.CARD_STATUS_EMBOSSA_RECEIVED, CardStateManagerService.CARD_STATUS_EMBOSSA_RECEIVED_STRING);
						}
						/*CR no 36 starts*/	
						else if(duplicateList.size()>0)
						{
							try{
					logger.info("duplicate occured emboss file please check ...");
					//		System.out.println(line);
							String duplicateFileLine = null;
							File duplicateRecFile = new File(properties.getProperty("duplicateEmbossPath"));
							if(!duplicateRecFile.exists())
								{
								duplicateRecFile.mkdirs();
								}
							File duplicateFile = new File(duplicateRecFile , filename);
								if(!duplicateFile.exists())
								{
									duplicateFile.createNewFile();
								}
								BufferedReader br1 = new BufferedReader(new FileReader(duplicateFile));
								BufferedWriter bw = new BufferedWriter(new FileWriter(duplicateFile,true));
								int duplicateCheck = 0;
								while((duplicateFileLine = br1.readLine())!=null)
								{
									if(duplicateFileLine.equals(line))
									{
										duplicateCheck++;
										break;
									}
								
								}
								if(duplicateCheck == 0)
								{
								bw.write(line);
								bw.newLine();
								bw.flush();
								}
								bw.close();
						}catch(Exception e)
							{
								e.printStackTrace();
							}
							
						}
						/*CR no 36 ends*/	
				} 
				logger.info(i+ " Embossa records has been processed ");
			}
			}
				catch(FileNotFoundException e) {
				logger.info("Seems the file is not yet copied.....we ll try again after 3 seconds");
				Thread.sleep(3000);
				processEmbossRecord(exchange);
			}
			catch(Exception e)
			{
				logger.info(e);
			}
			 finally {
				try {
		         br.close();
				} catch(Exception e) {
					logger.error(e);
					e.printStackTrace();
				}
		    }	
			
			return  gfile;
			
		}			
		
		@Transactional
		public synchronized void generateEmbossMDfile(Exchange exchange) {
			
				try {
					GenericFile gfile = (GenericFile) exchange.getIn().getBody();
					String filename = gfile.getFileNameOnly();
					List<CoreFiles> filelist = getFileDAO().getFileList(filename);
					 Long fileId = null;
					 if(filelist != null && filelist.size() > 0) {
						 CoreFiles file = (CoreFiles) filelist.get(0);
						 fileId  = file.getId();
					 }
					List<CreditCardDetails> list= getGndDAO().getEmbossaRecords(fileId);
						if(list != null && list.size() > 0) {
							logger.info("In MD file generation ");
							int request = 1;				
							for(CreditCardDetails creditCardDetails : list) {
								CreditCardDetailsBO details = buildCreditCardDetailsBO(creditCardDetails);				
								if(request == 1) {
									generateFileName(filename);
								}
								body(details, bw, filename);							
								request++;
								creditCardDetails.setStatus((long)CardStateManagerService.CARD_STATUS_MD_GENERATED);
								creditCardDetails.setRuleStatus(CardStateManagerService.CARD_STATUS_MD_GENERATED_STRING);
								creditCardDetails.setCreatedDate(new Date());
								getGndDAO().saveCardDetails(creditCardDetails);
								saveRecordEvent(creditCardDetails.getCreditCardDetailsId(),(long)CardStateManagerService.CARD_STATUS_MD_GENERATED, CardStateManagerService.CARD_STATUS_MD_GENERATED_STRING);
								}
							logger.info(" MD file generated successfully ");
							}
								

						} catch(Exception io) {
							logger.error(io.getMessage());
							io.printStackTrace();
						} finally {
							try {
								if(bw != null) {
									bw.close();
								}
							} catch(Exception e) {
								logger.error(e);
								e.printStackTrace();
							}
							
							
						}
					}
		
		public void body(CreditCardDetailsBO details, BufferedWriter bw, String embfilename) {
			String bankcode = embfilename.substring(3, 9);
			String seperator = ",";
			try {
				rsn = new StringBuilder(getPattern(details.getRsn().toString(),10));
				rsn.setLength(10);
				bw.write(rsn.toString());
				
				bw.write(seperator);
				
				product = new StringBuilder(details.getProduct());
				product.setLength(3);
				bw.write(product.toString());
				
				bw.write(seperator);
				String split1[] = embfilename.split("\\.");
				if(split1[0].length() == 25) {
					bankshortCode = new StringBuilder(split1[0].substring(3, 6));
				}
				else {
					String bankCode = getGndDAO().getBankCode(bankcode);
					bankshortCode = new StringBuilder(bankCode != null?bankCode : "");
				}
				bankshortCode.setLength(10);
				bw.write(bankshortCode.toString());				
				
				bw.write(seperator);
				
				String filename = getGndDAO().getFileName(details.getFildId());
				String split[] = filename.split("\\.");
				if(!split[1].equalsIgnoreCase("ncf")) {
						
					firstname = new StringBuilder(details.getCustomerFirstName());				
					lastname = new StringBuilder(details.getCustomerSurName());					
				}
				else {
					firstname = new StringBuilder("");
					lastname = new StringBuilder("");
				}
				
				firstname.setLength(40);
				bw.write(firstname.toString());
				
				bw.write(seperator);
				
				lastname.setLength(40);
				bw.write(lastname.toString());
				
				bw.write(seperator);
				
				phonenumber = new StringBuilder(details.getMobileNo());
				phonenumber.setLength(12);
				bw.write(phonenumber.toString());
				
				bw.write(seperator);
				
				cardStatus = new StringBuilder(details.getCardStatus());
				cardStatus.setLength(3);
				bw.write(cardStatus.toString());
				
				bw.write(seperator);
				
				bw.newLine();
			}
			catch(Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			
			
			
		}
		
		public void generateFileName(String filename) {
			try {
//				properties.load(inputStream);
				String filePath = properties.getProperty("csv.embossMDGenerated");
				
				String split[] = filename.split("\\.");
				StringBuilder embfile = new StringBuilder(split[0]);
				embfile.append("_MD.");
				embfile.append(split[1]);
				embfile.append(".csv");
				File file = new File(filePath+"/"+embfile);
				if (!file.exists()) 			
					file.createNewFile();						
				 fw = new FileWriter(file.getAbsoluteFile());
				bw = new BufferedWriter(fw);
			} catch(Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			
		}
		
		public String getPattern(String text, int length) {
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
		
		public void saveRecordEvent(Long recordId, Long eventId, String desc) {
			
			RecordEvent event = new RecordEvent();
			event.setEventDate(new Date());
			event.setDescription(desc);
			event.setEventId(eventId);
			event.setRecordId(recordId);
			
			getGndDAO().saveRecordEvent(event);
		}

		@Transactional
		public synchronized void processVIPFile(Exchange exchange) {
			GenericFile<?> gfile = null;
			File outputfile =null;
			BufferedReader br = null;
			BufferedWriter bw = null;
			CreditCardDetails details = null;
			List<EmbossaFormat> embossaformat = null;
			MasterCourierService corierServiceCard = null;
			MasterCourierService corierServicePin = null;
			
			try {
				
//				properties.load(inputStream);
				 gfile = (GenericFile<?>) exchange.getIn().getBody();
				 String filename = gfile.getFileNameOnly();
				 List<CoreFiles> filelist = getFileDAO().getFileList(filename);
				 Long fileId = null;
				 if(filelist != null && filelist.size() > 0) {
					 CoreFiles file = (CoreFiles) filelist.get(0);
					 fileId  = file.getId();
				 }
				 logger.info("filename is : "+filename);
				 String bankCode = filename.substring(3,9);
				 logger.info("bank code is : "+bankCode);
				 
				 
				 String homeBranchCode = filename.substring(9,14);
				 logger.info("home branch code is : "+homeBranchCode);
				 
				 String productCode = filename.substring(14, 17);
				 logger.info("product code is : "+productCode);
				 
				 Long networkId = getGndDAO().getNetworkId(productCode, bankCode);
				 if(networkId != null) {
					 Bank bank = getGndDAO().getBankDetails(filename.substring(3, 9));
					 String bin = getProductDAO().getBin(filename.substring(14, 17), bank.getBankId());
					 corierServiceCard = (MasterCourierService) getMasterAWBDAO().getServiceProvider((long)CorierServiceProviders.INDIAN_POST_CHENNAI);
					 corierServicePin = (MasterCourierService) getMasterAWBDAO().getServiceProvider((long)CorierServiceProviders.INDIAN_POST_MUMBAI);
					 switch (networkId.intValue()) {
					case 1:
					case 2:
						
						 embossaformat = getGndDAO().getEmbossaFormat(productCode, bankCode);
						 if(embossaformat != null && embossaformat.size() > 0) {
							EmbossaFormat format = null;
							outputfile = new File(properties.getProperty("csv.vipprocessed")+"/"+filename);
							bw = new BufferedWriter(new FileWriter(outputfile));
							File file = (File) gfile.getFile();
							br =new BufferedReader(new FileReader(file));
							String line = null;
							int i = 0;
							while ((line = br.readLine())!= null) {	  
								details = new CreditCardDetails();
								i++;
								rawString = line;
								format = (EmbossaFormat) embossaformat.get(0);
								panNumber = rawString.substring(format.getFieldLocation(),format.getFieldLocation()+format.getLength());
								
								format = (EmbossaFormat) embossaformat.get(1);
								cardHolderName5F20 = rawString.substring(format.getFieldLocation(),format.getFieldLocation()+format.getLength());

								format = (EmbossaFormat) embossaformat.get(2);
								embossName = rawString.substring(format.getFieldLocation(),format.getFieldLocation()+format.getLength());
								
								format = (EmbossaFormat) embossaformat.get(3);
								fourthLinePrintingdata = rawString.substring(format.getFieldLocation(),format.getFieldLocation()+format.getLength());
								
								format = (EmbossaFormat) embossaformat.get(4);
								details.setRsn(getGndDAO().getRSNSequenceNumber());
								
								StringBuilder rsn = new StringBuilder(details.getRsn().toString());
								rsn.setLength(10);
								recordReferenceNumber=rsn.toString().replace('\0',' ');
								format = (EmbossaFormat) embossaformat.get(5);
								details.setCardAWB(getGndDAO().getCardAWB((long)CorierServiceProviders.INDIAN_POST_CHENNAI));
								
								speedPostBarCode = details.getCardAWB();
								StringBuilder cardAWB = new StringBuilder(speedPostBarCode);
								cardAWB.setLength(13);
								String cardAWBVal = cardAWB.toString().replace('\0',' ');
								
								format = (EmbossaFormat) embossaformat.get(6);
								mailAddress1 = rawString.substring(format.getFieldLocation(),format.getFieldLocation()+format.getLength());
								
								format = (EmbossaFormat) embossaformat.get(7);					
								mailAddress2 = rawString.substring(format.getFieldLocation(),format.getFieldLocation()+format.getLength());
								
								format = (EmbossaFormat) embossaformat.get(8);
								mailAddress3 = rawString.substring(format.getFieldLocation(),format.getFieldLocation()+format.getLength());
								
								format = (EmbossaFormat) embossaformat.get(9);
								mailAddress4 = rawString.substring(format.getFieldLocation(),format.getFieldLocation()+format.getLength());
								
								format = (EmbossaFormat) embossaformat.get(10);
								city = rawString.substring(format.getFieldLocation(),format.getFieldLocation()+format.getLength());
								
								format = (EmbossaFormat) embossaformat.get(11);
								pinCode = rawString.substring(format.getFieldLocation(),format.getFieldLocation()+format.getLength());
								
								format = (EmbossaFormat) embossaformat.get(12);
								branchCode = rawString.substring(format.getFieldLocation(),format.getFieldLocation()+format.getLength());
								
								format = (EmbossaFormat) embossaformat.get(13);
								accountNumber = rawString.substring(format.getFieldLocation(),format.getFieldLocation()+format.getLength());
								
								details.setPanMasked(CustomEncryption.encrypt(panNumber));
								details.setSerialNo(" ");
								details.setOfficePhone(" ");
								details.setHomePhone(" ");
								details.setCustomerFirstName(cardHolderName5F20);
								details.setEmbossName(embossName);
								details.setFourthLinePrintingData(fourthLinePrintingdata);
								details.setAddr1(mailAddress1);
								details.setAddr2(mailAddress2);
								details.setAddr3(mailAddress3);
								details.setAddr4(mailAddress4);
								details.setCity(city);
								details.setPin(pinCode);
								details.setProduct(filename.substring(14, 17));
								details.setHomeBranchCode(branchCode);
								details.setPrimaryAcctNo(accountNumber);
								details.setIsVIP(1);
								details.setPinAWB(getGndDAO().getCardAWB((long)CorierServiceProviders.INDIAN_POST_MUMBAI));
								details.setFileId(fileId);
								details.setInstitutionId(bin);
								details.setProcessedBranchCode(branchCode);
								details.setBankId(bank.getBankId());
//								getMasterAWBDAO().getServiceProvider(cardAWB.)
//								details.setCardServiceProvider(getGndDAO().
								details.setCreatedDate(new Date());
								details.setStatus((long) CardStateManagerService.CARD_STATUS_EMBOSSA_RECEIVED);
								details.setRuleStatus(CardStateManagerService.CARD_STATUS_EMBOSSA_RECEIVED_STRING);
								details.setCardServiceProvider(corierServiceCard.getServiceProviderName());
								details.setPinServiceProvider(corierServicePin.getServiceProviderName());
				/*cr no 42*/	details.setEmboBatchName(getEmboBatchNameService(filename));
								Long vipId = getGndDAO().saveCardDetails(details);
								saveRecordEvent(vipId,(long)CardStateManagerService.CARD_STATUS_EMBOSSA_RECEIVED, CardStateManagerService.CARD_STATUS_EMBOSSA_RECEIVED_STRING);
								StringBuilder record = new StringBuilder(line);
								logger.info("record length is : "+record.length());
								record.replace(318, 328, recordReferenceNumber);
								record.replace(329, 342, cardAWBVal);
								bw.write(record.toString());
								bw.newLine();
							}
							bw.close();
							br.close();
							
							callPinMailer(filename, bin, details.getFileId());
						 }

						 break;
						
					case 3:
						embossaformat = getGndDAO().getEmbossaFormat(productCode, bankCode);
						if(embossaformat != null && embossaformat.size() > 0) {
							 EmbossaFormat format = null;
							 outputfile = new File(properties.getProperty("csv.vipprocessed")+"/"+filename);
							 bw = new BufferedWriter(new FileWriter(outputfile));
							File file = (File) gfile.getFile();
							br =new BufferedReader(new FileReader(file));
							String line = null;
							int i = 0;
							while ((line = br.readLine())!= null) {	  
								
								details = new CreditCardDetails();
								i++;
								rawString = line;		
								StringBuilder record = new StringBuilder(rawString);
								
								format = (EmbossaFormat) embossaformat.get(0);
								panNumber = rawString.substring(format.getFieldLocation(),format.getFieldLocation()+format.getLength());

								format = (EmbossaFormat) embossaformat.get(1);
								embossName = rawString.substring(format.getFieldLocation(),format.getFieldLocation()+format.getLength());

								format = (EmbossaFormat) embossaformat.get(2);
								cardHolderName5F20 = rawString.substring(format.getFieldLocation(),format.getFieldLocation()+format.getLength());
								
								format = (EmbossaFormat) embossaformat.get(3);
								mailAddress1 = rawString.substring(format.getFieldLocation(),format.getFieldLocation()+format.getLength());
								
								format = (EmbossaFormat) embossaformat.get(4);
								mailAddress2 = rawString.substring(format.getFieldLocation(),format.getFieldLocation()+format.getLength());
								
								format = (EmbossaFormat) embossaformat.get(5);
								mailAddress3 = rawString.substring(format.getFieldLocation(),format.getFieldLocation()+format.getLength());
								
								format = (EmbossaFormat) embossaformat.get(6);
								mailAddress4 = rawString.substring(format.getFieldLocation(),format.getFieldLocation()+format.getLength());
								
								format = (EmbossaFormat) embossaformat.get(7);
								city = rawString.substring(format.getFieldLocation(),format.getFieldLocation()+format.getLength());
								
								format = (EmbossaFormat) embossaformat.get(8);
								pinCode = rawString.substring(format.getFieldLocation(),format.getFieldLocation()+format.getLength());
								
								format = (EmbossaFormat) embossaformat.get(9);
								branchCode = rawString.substring(format.getFieldLocation(),format.getFieldLocation()+format.getLength());
								
								format = (EmbossaFormat) embossaformat.get(10);
								accountNumber = rawString.substring(format.getFieldLocation(),format.getFieldLocation()+format.getLength());
															
								format = (EmbossaFormat) embossaformat.get(11);
								details.setRsn(getGndDAO().getRSNSequenceNumber());
								recordReferenceNumber = details.getRsn().toString();
								StringBuilder rsn = new StringBuilder(recordReferenceNumber);
								rsn.setLength(10);
								String rsnValue = rsn.toString().replace('\0', ' ');
								record.replace(format.getFieldLocation(), format.getFieldLocation()+format.getLength(), rsnValue);
								
								format = (EmbossaFormat) embossaformat.get(12);
								details.setCardAWB(getGndDAO().getCardAWB((long)CorierServiceProviders.INDIAN_POST_CHENNAI));
								speedPostBarCode = details.getCardAWB();
								StringBuilder cardAWB = new StringBuilder(speedPostBarCode);
								cardAWB.setLength(13);
								String val = cardAWB.toString().replace('\0',' ');
								record.replace(format.getFieldLocation(), format.getFieldLocation()+format.getLength(), val);
								
								format = (EmbossaFormat) embossaformat.get(13);
								details.setFourthLinePrintingData(rawString.substring(format.getFieldLocation(),format.getFieldLocation()+format.getLength()));
								
								details.setPanMasked(CustomEncryption.encrypt(panNumber));
								details.setProcessedBranchCode(branchCode);
								details.setCustomerFirstName(cardHolderName5F20);
								details.setSerialNo(" ");
								details.setOfficePhone(" ");
								details.setHomePhone(" ");
								details.setEmbossName(embossName);
//								details.setFourthLinePrintingData(fourthLinePrintingdata);
								details.setAddr1(mailAddress1);
								details.setAddr2(mailAddress2);
								details.setAddr3(mailAddress3);
								details.setAddr4(mailAddress4);
								details.setCity(city);
								details.setPin(pinCode);
								details.setHomeBranchCode(branchCode);
								details.setPrimaryAcctNo(accountNumber);
								details.setProduct(filename.substring(14, 17));
								details.setIsVIP(1);
								details.setFileId(getDroolRecord().getVipFileId());
								details.setCardServiceProvider(corierServiceCard.getServiceProviderName());
								details.setPinAWB(getGndDAO().getCardAWB((long)CorierServiceProviders.INDIAN_POST_MUMBAI));
								details.setPinServiceProvider(corierServicePin.getServiceProviderName());
								details.setInstitutionId(bin);
			  /*cr no 42*/	   details.setEmboBatchName(getEmboBatchNameService(filename));
//								getMasterAWBDAO().getServiceProvider(cardAWB.)
//								details.setCardServiceProvider(getGndDAO().
								details.setCreatedDate(new Date());
								details.setStatus((long) CardStateManagerService.CARD_STATUS_EMBOSSA_RECEIVED);
								details.setRuleStatus(CardStateManagerService.CARD_STATUS_EMBOSSA_RECEIVED_STRING);
								details.setBankId(bank.getBankId());
								Long vipId = getGndDAO().saveCardDetails(details);
								saveRecordEvent(vipId,(long)CardStateManagerService.CARD_STATUS_EMBOSSA_RECEIVED, CardStateManagerService.CARD_STATUS_EMBOSSA_RECEIVED_STRING);
								bw.write(record.toString());
								
								bw.newLine();
							}
							bw.close();
							 br.close();
							 
							 callPinMailer(filename, bin,details.getFileId());	
							
						 }

						
						
						break;
					default:
						break;
					}
								 }
				 
			} catch(Exception e) {
				logger.error(e);
				e.printStackTrace();
			} 
		}
		
		public CreditCardDetailsBO buildCreditCardDetailsBO(CreditCardDetails details) {
			CreditCardDetailsBO detailsBO = new CreditCardDetailsBO();
			detailsBO.setFildId(details.getFileId());
			detailsBO.setInstitutionId(details.getInstitutionId());
			detailsBO.setRsn(details.getRsn());
			detailsBO.setProduct(details.getProduct());
			detailsBO.setCustomerFirstName(details.getCustomerFirstName());
			detailsBO.setCustomerSurName(details.getCustomerSurName());
			detailsBO.setMobileNo(details.getMobileNo());
			detailsBO.setCardStatus(details.getCardStatus());
			return detailsBO;
		}
		
		public void callPinMailer(String filename, String bin, Long fileId) {
			try {
				
				String format = properties.getProperty("pinmailerformat");
				Bank bank = getGndDAO().getBankDetails(filename.substring(3,9));
				SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
				Date date = sdf.parse(filename.substring(19, 25));
				Date dayafter = DateUtils.addDays(date, 1);
				Integer isVIP = 1;
				String pinMailerName = bank.getShortCode()+"_"+filename.substring(9, 14)+"_"+filename.substring(14, 17)+"_"+filename.substring(19, 25)+"_"+sdf.format(dayafter);
				getJasperReports().pinMailerReport(bank, bin, filename.substring(9, 14), filename.substring(14, 17), (long) CardStateManagerService.CARD_STATUS_EMBOSSA_RECEIVED, pinMailerName, filename, isVIP, filename,fileId,format);
			} catch(Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
		}

		/*cr 42 starts*/
		public void deletePgpFileLogService(CoreFiles coreFile)
		{
			try{
				getFileDAO().deletePgpFileLog(coreFile);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
	//	@Transactional
		public boolean isNewFile(String fileName)
		{
			boolean isNewFile = false;
			try{
			
				//FileDAOImpl fileDao = new FileDAOImpl();
				List<CoreFiles> list = getFileDAO().getExistingFile(fileName);
				if(list.size() == 0 && list!= null)
				{
				//	System.out.println("new file");
					isNewFile = true;
				}
			
			}catch(NullPointerException e1)
			{
			
				e1.printStackTrace();
			}
			
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return isNewFile;
		}
		//@Transactional
		public CoreFiles saveFile(String fileName,boolean isNewFile)
		{
			CoreFiles file = new CoreFiles();
			try
			{
			
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
			
			//FileDAOImpl fileDao = new FileDAOImpl();
			if(isNewFile == true)
			{
			//	System.out.println("new emboss file");
				
				file.setFilename(fileName);
				file.setFileType(Long.parseLong(FileType.valueOf("EMBOSS").getFileType()));
				file.setDescription("pgp file");
				file.setReceivedDate(new SimpleDateFormat("ddMMyyyy").parse(fileName.substring(12, 20)));
				file.setProcessedDate(new Date());
				file.setStatus(Long.parseLong(FileStatus.valueOf("PGP_RECEIVED").getFileStatus()));
				file.setLcpcGroup(fileName.substring(7,9));
			//	fileId = getFileDAO().saveIt(file);
				getFileDAO().saveIt(file);
				
			}
			else
			{
				file.setFilename(fileName);
				file.setFileType(Long.parseLong(FileType.valueOf("EMBOSS").getFileType()));
				file.setStatus(Long.parseLong(FileStatus.valueOf("PGP_RECEIVED").getFileStatus()));
				file.setReceivedDate(new SimpleDateFormat("ddMMyyyy").parse(fileName.substring(12, 20)));
				file.setDescription("pgp file Already Processed");
				file.setProcessedDate(new Date());
				file.setLcpcGroup(fileName.substring(7,9));
		//		fileId =  getFileDAO().saveIt(file);
				getFileDAO().saveIt(file);
				
			}
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		//	emboBatchName =  getFileDAO().getEmboBatchName(fileId);
			return file;
		}
		@Transactional
		public void setPgpLineCount(Long lineCount,Long fileId)
		{
		
		//	CoreFiles file = getFileDAO().getFile(fileId);
			CoreFiles embossTxtFile = getFileDAO().getFile(fileId);
			CoreFiles embossPgpfile = getFileDAO().getFile(embossTxtFile.getCoreFileId());
			Long existingLineCount = (long)0;
			if(embossPgpfile!=null)
			{
				if(embossPgpfile.getLineCount() == null)
				{
					existingLineCount = (long)0;
				}else
				{
					existingLineCount = embossPgpfile.getLineCount();
				}
			Long totalLineCount = lineCount + existingLineCount;
			embossPgpfile.setLineCount(totalLineCount);
			getFileDAO().saveFile(embossPgpfile);
			}
			
			
		}
	/*cr no 42 starts*/
		@Transactional
	public Long getEmbossIdService(String fileName) throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMy");
	 	Date date = DateUtils.addDays(sdf.parse(fileName.substring(14,22)), -1);
	 	String qcDate = sdf.format(date);
	 	System.out.println(qcDate);
		List<CoreFiles> file = getFileDAO().getEmbossFile(fileName,qcDate);
		Long fileId = null;
		if(file.size()>0 && !(file == null))
		{
			 fileId = file.get(file.size()-1).getId();
		}
		return fileId;
	}
		@Transactional
	public String getEmboBatchNameService(String fileName) throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMy");
	 	Date date = DateUtils.addDays(sdf.parse(fileName.substring(14,22)), -1);
	 	String qcDate = sdf.format(date);
	 	System.out.println(qcDate);
		List<CoreFiles> file = getFileDAO().getEmbossFile(fileName,qcDate);
		System.out.println("embobatch" + file.size());
		String emboBatchName = null;
		if(file.size()>0 && !(file == null))
		{
			 emboBatchName = file.get(file.size()-1).getFilename();
		}
		return emboBatchName;
	}
}
