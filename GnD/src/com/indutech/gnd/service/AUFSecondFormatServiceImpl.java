package com.indutech.gnd.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.didisoft.pgp.PGPException;
import com.didisoft.pgp.PGPLib;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.indutech.gnd.bo.BankBO;
import com.indutech.gnd.bo.CreditCardDetailsBO;
import com.indutech.gnd.bo.drool.DroolRecordBO;
import com.indutech.gnd.dao.AUFSecondFormatDAOImpl;
import com.indutech.gnd.dao.FileDAOImpl;
import com.indutech.gnd.dao.GNDDAOImpl;
import com.indutech.gnd.dto.CoreFiles;
import com.indutech.gnd.dto.CreditCardDetails;
import com.indutech.gnd.enumTypes.FileStatus;
import com.indutech.gnd.enumTypes.FileType;
import com.indutech.gnd.util.DataInitialization;
import com.indutech.gnd.util.StringUtil;

@Component("aufformat2service")
public class AUFSecondFormatServiceImpl implements AUFSecondFormatService {
	
	Logger logger = Logger.getLogger(AUFSecondFormatServiceImpl.class);
	
	common.Logger log = common.Logger.getLogger(AUFSecondFormatServiceImpl.class);
	
	private static final String PGP_EXTENSION = ".pgp";
	
	private Properties properties = PropertiesLoader.getInstance().loadProperties();
	
	@Autowired
	private DroolRecordBO droolRecord;
	
	@Autowired
	private GNDDAOImpl gndDAO;
	
	@Autowired
	private FileEncryptionService encryptionService;
	
	@Autowired
	private AUFSecondFormatDAOImpl aufsecondformatdao;	
	
	@Autowired
	private FileDAOImpl fileDAO;
	
	

	public FileDAOImpl getFileDAO() {
		return fileDAO;
	}

	public void setFileDAO(FileDAOImpl fileDAO) {
		this.fileDAO = fileDAO;
	}

	public GNDDAOImpl getGndDAO() {
		return gndDAO;
	}

	public void setGndDAO(GNDDAOImpl gndDAO) {
		this.gndDAO = gndDAO;
	}

	public AUFSecondFormatDAOImpl getAufsecondformatdao() {
		return aufsecondformatdao;
	}

	public void setAufsecondformatdao(AUFSecondFormatDAOImpl aufsecondformatdao) {
		this.aufsecondformatdao = aufsecondformatdao;
	}

	public DroolRecordBO getDroolRecord() {
		return droolRecord;
	}

	public void setDroolRecord(DroolRecordBO droolRecord) {
		this.droolRecord = droolRecord;
	}

	public FileEncryptionService getEncryptionService() {
		return encryptionService;
	}

	public void setEncryptionService(FileEncryptionService encryptionService) {
		this.encryptionService = encryptionService;
	}

	private StringBuilder cbsSerialNo;
	private StringBuilder vendorSerialNo;
	private StringBuilder institutionId;
	private StringBuilder customerId;
	private StringBuilder homeBranchCode1;
	private StringBuilder issueBranchCode2;
	private StringBuilder lcpcCode;
	private StringBuilder primaryAcctNo;
	private StringBuilder primaryAcctType;
	private StringBuilder primaryAcctOrSurvivor;
	private StringBuilder secondaryAcct1;
	private StringBuilder secondaryAcct1Type;
	private StringBuilder secondaryAcct1OrSurvivor;
	private StringBuilder secondaryAcct2;
	private StringBuilder secondaryAcct2Type;
	private StringBuilder secondaryAcct2OrSurvivor;
	private StringBuilder embossName;
	private StringBuilder customerFirstName;
	private StringBuilder customerMiddleName;
	private StringBuilder customerSurName;
	private StringBuilder addr1;
	private StringBuilder addr2;
	private StringBuilder addr3;
	private StringBuilder addr4;
	private StringBuilder city;
	private StringBuilder pin;
	private StringBuilder officePhone;
	private StringBuilder homePhone;
	private StringBuilder product;
	private StringBuilder cardStatus;
	private StringBuilder registrationDate;
	private StringBuilder qcDate;
	private StringBuilder fax;
	private StringBuilder email;
	private StringBuilder fatherFirstName;
	private StringBuilder motherMaidenName;
	private StringBuilder dateOfBirth;
	private StringBuilder yearOfSSCPass;
	private StringBuilder yearofMarriage;
	private StringBuilder forthLinePrintingData;
	private StringBuilder isdCode;
	private StringBuilder mobileNo;
	private StringBuilder primaryAcctProductCode;
	private StringBuilder secondaryAcct1ProductCode;
	private StringBuilder secondaryAcct2ProductCode;
	private StringBuilder homeBranchCircleCode;
	private StringBuilder issueBranchCircleCode;
	private StringBuilder aadharCardNumber;
	private StringBuilder greenPinFlag;
	private StringBuilder coreFileName;
	private StringBuilder welkitLetterIdentificationFlag;
	private StringBuilder imageCode;
	private StringBuilder reservedForFuturePurpose1;
	private StringBuilder reservedForFuturePurpose2;
	private StringBuilder reservedForFuturePurpose3;
	private StringBuilder reservedForFuturePurpose4;
	private StringBuilder reservedForFuturePurpose5;
	
	private File file = null;
	private FileWriter fw = null;
	private BufferedWriter bw = null;
	
	private SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat outDateFormat = new SimpleDateFormat("ddMMyyyy");
	
	private String val;
	
	private Date dayAfterCore;
	
	private String seperator = "^";
	
	private StringBuilder batchSize;
	
	private StringBuilder batchName;
	
	private List<String> fileList;
	
	List<CoreFiles> list= new ArrayList<CoreFiles>();
	
	@Override
	@Transactional
	public synchronized void FormatAUF() {
		logger.info("In auf generation............. ");
		Integer groupSize = 0;
		String coreFileName = "";
		try {
			String aufFileName = null;
			String bankShortCode = null;
			Long bankId = null;
			Multimap<String, CreditCardDetails> map = null;
			int fileSize = 0;
			coreFileName = getDroolRecord().getFileName();
			Long fileId = getDroolRecord().getFileId();
			if(!StringUtil.isEMptyOrNull(coreFileName)) {
				String prefix = coreFileName.substring(0, 1);
				List<BankBO> bankList = DataInitialization.getInstance().getBankInfo();
				if(bankList != null && bankList.size() > 0) {
					for(BankBO bankBO : bankList) {
						if(bankBO.getPrefix() != null && bankBO.getPrefix().equals(prefix)) {
							bankShortCode = bankBO.getShortCode();
							bankId = bankBO.getBankId();
							break;
						}
					}
				}
			}
			
			List<CreditCardDetails> creditcarddetailsList = getAufsecondformatdao().getCreditCardDetails(bankId, fileId, (long)CardStateManagerService.CARD_STATUS_AWBASSIGNED);
			if(creditcarddetailsList != null && creditcarddetailsList.size() > 0) {
				map = ArrayListMultimap.create();
				for(CreditCardDetails details : creditcarddetailsList) {
					map.put(details.getProcessedBranchCode(), details);
				}
			}
			
			if(map != null && map.size() > 0) {
				Set<String> branchGroup = map.keySet();
				groupSize = branchGroup.size();
				batchSize = new StringBuilder(getPattern(groupSize.toString(), 5));
				for(String branchCode : branchGroup) {
					List<CreditCardDetails> detailsList = (List<CreditCardDetails>) map.get(branchCode);
					if(detailsList != null && detailsList.size() > 0) {
						Long alufileId = null;
						fileSize = detailsList.size();
						int request = 1;
						for(CreditCardDetails creditCardDetails : detailsList) {
							CreditCardDetailsBO details = buildCreditCardDetailsBO(creditCardDetails);
							if(request == 1) {
								aufFileName = generateFileName(details.getProcessedBranchCode(),coreFileName, bankShortCode, fileId);
								CoreFiles coreFile = new CoreFiles();
								coreFile.setFilename(aufFileName);
								coreFile.setFileType(Long.parseLong(FileType.valueOf("AUF").getFileType()));
								coreFile.setDescription("AUF_CONVERTED");
								coreFile.setReceivedDate(dayAfterCore);
								coreFile.setLineCount((long)fileSize);
								coreFile.setCoreFileId(fileId);						
								coreFile.setProcessedDate(new Date());
								coreFile.setLcpcGroup(aufFileName.substring(3,5));
								coreFile.setStatus(Long.parseLong(FileStatus.valueOf("AUF_CONVERTED").getFileStatus()));
								 getFileDAO().saveFile(coreFile);
							//	executeBatchCreate(coreFile);
							}
							bw.write(body( details,coreFileName));
							if(request < fileSize) {
								bw.newLine();
							}
							request++;
						}
						
//						getGndDAO().saveCardDetailsForAUF(alufileId, branchCode, fileId,(long)CardStateManagerService.CARD_STATUS_AUFCONVERTED, CardStateManagerService.CARD_STATUS_AUFCONVERTED_STRING);
					}
					bw.close();
				}
				/*logger.info("before flush");
				flushCreate();
				logger.info("after flush");*/
				
				getGndDAO().updateRecordsToAUFState(fileId, (long)CardStateManagerService.CARD_STATUS_AUFCONVERTED, CardStateManagerService.CARD_STATUS_AUFCONVERTED_STRING);
				
				getGndDAO().saveTxnEventForAUF(fileId, (long)CardStateManagerService.CARD_STATUS_AUFCONVERTED);
				if(groupSize > 0 ) {
					String zipfile = prepateBatchFile(batchName.toString(), coreFileName, bankShortCode);
					encrypt(coreFileName, bankShortCode, zipfile);
				}
				logger.info("auf generated successfully");
			} else {
				logger.info("No records found to generate auf files ");
			}
		} catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		

	}
	
	@Async
	public  void encrypt(String corefilename, String bankShortCode, String zipFilepath){
		
		
		  PGPLib pgp = new PGPLib();
		 
		  // is output ASCII or binary
		  boolean asciiArmor = true; 
		 
		  // should integrity check information be added
		  // set to false for compatibility with older versions of PGP such as 6.5.8.
		  boolean withIntegrityCheck = false; 
		 
		  // obtain the streams
		 
		  // Here "INPUT.txt" is just a string to be written in the
		  // OpenPGP packet which contains:
		  // file name string, timestamp, and the actual data bytes
		  try {
			  File inputFile = new File(zipFilepath);
			  String split[] = corefilename.split("\\.");
//			  if(inputFolder.exists() && inputFolder.isDirectory()) {
			  fileList =  new ArrayList<String>();
				  String aufOutputFile = properties.getProperty("aufOutputFolder")+"\\"+corefilename.substring(2, split[0].length())+"\\"+bankShortCode;
				  File fileDir  = new File(aufOutputFile);
				  fileDir.mkdirs();
//				  File fileList[] = inputFolder.listFiles();
//				  for(File file : fileList) {
					 InputStream keyStream = new FileInputStream(properties.getProperty("encryptionFilePath"));
		//			  InputStream inStream = new FileInputStream(file);
					  
		//			  File outputFile = new File(fileDir.getPath()+"/"+file.getName()+PGP_EXTENSION);
		//			  OutputStream outStream = new FileOutputStream(outputFile);
//					  String outputfile = inputFolder.getName()+PGP_EXTENSION;

					 pgp.encryptFile( inputFile.getAbsolutePath(),
					                    keyStream,
					                    fileDir.getPath()+"/"+inputFile.getName()+PGP_EXTENSION,
					                    asciiArmor,
					                    withIntegrityCheck);
					
					 /*cr no 44 starts*/
					 try
					 {
					 	SimpleDateFormat sdf = new SimpleDateFormat("ddMMy");
					 	Date date = DateUtils.addDays(sdf.parse(corefilename.substring(2, split[0].length())), 1);
					 	String qcDate = sdf.format(date);
					 	//System.out.println(qcDate);
					 	File folder = new File(properties.getProperty("aluZipPath"));
					 	if(folder.exists() == false)
					 	{
					 		folder.mkdir();
					 	}
				
					 	String OUTPUT_ZIP_FILE = properties.getProperty("aluZipPath")+"\\"+bankShortCode+"_AUF_"+qcDate+".zip";
					    String SOURCE_FOLDER = properties.getProperty("aufOutputFolder")+"\\"+corefilename.substring(2, split[0].length())+"\\"+bankShortCode;
					    generateFileList1(new File(SOURCE_FOLDER), SOURCE_FOLDER);
					    zip(OUTPUT_ZIP_FILE, SOURCE_FOLDER);
					 	
					 }catch(Exception e)
					 {
						e.printStackTrace();
					 }
					  /*cr no 44 ends*/
		//			generateZip(outputfile,corefilename);
//				  }
//			  }
		} catch (PGPException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	
	private String prepateBatchFile(String batchName, String coreFileName, String bankShortCode) {
		String split[] = coreFileName.split("\\.");
		fileList =  new ArrayList<String>();
		File file = new File(properties.getProperty("csv.aufEncriptedZIPPath")+"\\"+coreFileName.substring(2, split[0].length())+"\\"+bankShortCode);
		file.mkdirs();
	    String OUTPUT_ZIP_FILE = file.getAbsolutePath()+"/"+batchName+".zip";
	    String SOURCE_FOLDER = properties.getProperty("aluPath")+"/"+coreFileName.substring(2, split[0].length())+"/"+bankShortCode+"/"+coreFileName;
	    generateFileList(new File(SOURCE_FOLDER), SOURCE_FOLDER);
	    zipIt(OUTPUT_ZIP_FILE, SOURCE_FOLDER);
	    return OUTPUT_ZIP_FILE;
	    }
	    
	    /**
	     * Zip it
	     * @param zipFile output ZIP file location
	     */
	    public void zipIt(String zipFile, String SOURCE_FOLDER){

	     byte[] buffer = new byte[1024];
	    	
	     try{
	    		
	    	FileOutputStream fos = new FileOutputStream(zipFile);
	    	ZipOutputStream zos = new ZipOutputStream(fos);
	    		
	    		
	    	for(String file : this.fileList){
	    			
	    		ZipEntry ze= new ZipEntry(file);
	        	zos.putNextEntry(ze);
	               
	        	FileInputStream in = new FileInputStream(SOURCE_FOLDER + File.separator + file);
	       	   
	        	int len;
	        	while ((len = in.read(buffer)) > 0) {
	        		zos.write(buffer, 0, len);
	        	}
	               
	        	in.close();
	    	}
	    		
	    	zos.closeEntry();
	    	//remember close it
	    	zos.close();
	          
	    }catch(IOException ex){
	       ex.printStackTrace();   
	    }
	   }
	/*cr no 44 starts*/    
    public void zip(String zipFile, String SOURCE_FOLDER){

		     byte[] buffer = new byte[1024];
		    	
		     try{
		    		
		    	FileOutputStream fos = new FileOutputStream(zipFile);
		    	ZipOutputStream zos = new ZipOutputStream(fos);
		    	
		    		
		    	for(String file : this.fileList){
		    			
		    		ZipEntry ze= new ZipEntry(file);
		        	zos.putNextEntry(ze);
		               
		        	FileInputStream in = new FileInputStream(SOURCE_FOLDER+"\\"+file);
		       	   
		        	int len;
		        	while ((len = in.read(buffer)) > 0) {
		        		zos.write(buffer, 0, len);
		        	}
		               
		        	in.close();
		    	}
		    		
		    	zos.closeEntry();
		    	//remember close it
		    	zos.close();
	    	logger.info("Done");
	    }catch(IOException ex){
	       ex.printStackTrace();   
	    }
	   }
	    
	    /**
	     * Traverse a directory and get all files,
	     * and add the file into fileList  
	     * @param node file or directory
	     */
	    public void generateFileList(File node, String SOURCE_FOLDER){

	    	//add file only
		if(node.isFile()){
			fileList.add(generateZipEntry(node.getAbsoluteFile().toString(), SOURCE_FOLDER));
		}
			
		if(node.isDirectory()){
			String[] subNote = node.list();
			for(String filename : subNote){
				generateFileList(new File(node, filename), SOURCE_FOLDER);
			}
		}
	 
	    }
/*cr no 44 starts */
	    public void generateFileList1(File node, String SOURCE_FOLDER){
	    	if(node.isFile()){
				fileList.add(generateZipEntry1(node.getAbsoluteFile().toString(), SOURCE_FOLDER));
			}
				
			if(node.isDirectory()){
				String[] subNote = node.list();
				for(String filename : subNote){
					generateFileList1(new File(node, filename), SOURCE_FOLDER);
				}
			}
	    }
	    private String generateZipEntry1(String file, String SOURCE_FOLDER ){
	    	return file.substring(SOURCE_FOLDER.length(), file.length());
	    }

/*cr no 44 ends*/
	    /**
	     * Format the file path for zip
	     * @param file file path
	     * @return Formatted file path
	     */
	    private String generateZipEntry(String file, String SOURCE_FOLDER ){
	    	return file.substring(SOURCE_FOLDER.length()+1, file.length());
	    }

	public String body(CreditCardDetailsBO details, String fileName) {
		StringBuilder record = new StringBuilder();
		try {
			cbsSerialNo = new StringBuilder(!(details.getSerialNo() == null || details.getSerialNo().trim().isEmpty()) ? details.getSerialNo().toString() : details.getRsn().toString());
			cbsSerialNo.setLength(10);
			val = cbsSerialNo.toString().replace('\0',' ');
			record.append(val).append(seperator);
//			bw.write(val);
//			bw.write(seperator);
			
			vendorSerialNo = new StringBuilder(details.getRsn().toString());
			vendorSerialNo.setLength(12);
			val = vendorSerialNo.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			institutionId = new StringBuilder(details.getInstitutionId());
			institutionId.setLength(6);
			val = institutionId.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			customerId = new StringBuilder(!(details.getCustomerId() == null || details.getCustomerId().trim().isEmpty())? details.getCustomerId() : "00000000000000000");
			customerId.setLength(17);
			val = customerId.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			homeBranchCode1 = new StringBuilder(details.getProcessedBranchCode());
			homeBranchCode1.setLength(5);
			val = homeBranchCode1.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			issueBranchCode2 = new StringBuilder(details.getProcessedBranchCode() != null ? details.getProcessedBranchCode() : "");
			issueBranchCode2.setLength(5);
			val = issueBranchCode2.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			lcpcCode = new StringBuilder(details.getLcpcBranch() != null?details.getLcpcBranch():"00000");
			lcpcCode.setLength(5);
			val = lcpcCode.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			primaryAcctNo = new StringBuilder(details.getPrimaryAcctNo());
			primaryAcctNo.setLength(17);
			val = primaryAcctNo.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			primaryAcctType = new StringBuilder(details.getPrimaryAcctType());
			if(primaryAcctType != null && primaryAcctType.toString().trim().equals("10")) {
				primaryAcctType = new StringBuilder("11");
			} else if(primaryAcctType != null && primaryAcctType.toString().trim().equals("20")) {
				primaryAcctType = new StringBuilder("01");
			}
			primaryAcctType.setLength(2);
			val = primaryAcctType.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			primaryAcctOrSurvivor = new StringBuilder(details.getPrimaryAcctSurviour() != null ? details.getPrimaryAcctSurviour() : "");
			primaryAcctOrSurvivor.setLength(1);
			val = primaryAcctOrSurvivor.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			secondaryAcct1 = new StringBuilder(details.getSecondaryAcct1() != null ? details.getSecondaryAcct1() : "");
			secondaryAcct1.setLength(17);
			val = secondaryAcct1.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			secondaryAcct1Type = new StringBuilder(details.getSecondaryAcct1Type());
			if(secondaryAcct1Type != null && secondaryAcct1Type.toString().trim().equals("10")) {
				secondaryAcct1Type = new StringBuilder("11");
			} else if(secondaryAcct1Type != null && secondaryAcct1Type.toString().trim().equals("20")) {
				secondaryAcct1Type = new StringBuilder("01");
			}
			secondaryAcct1Type.setLength(2);
			val = secondaryAcct1Type.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			secondaryAcct1OrSurvivor = new StringBuilder(details.getSecondaryAcctSurviour());
			secondaryAcct1OrSurvivor.setLength(1);
			val = secondaryAcct1OrSurvivor.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			secondaryAcct2 = new StringBuilder(details.getSecondaryAcct2()!= null ? details.getSecondaryAcct2() : "");
			secondaryAcct2.setLength(17);
			val = secondaryAcct2.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			secondaryAcct2Type = new StringBuilder(details.getSecondaryAcct2Type());
			if(secondaryAcct2Type != null && secondaryAcct2Type.toString().trim().equals("10")) {
				secondaryAcct2Type = new StringBuilder("11");
			} else if(secondaryAcct2Type != null && secondaryAcct2Type.toString().trim().equals("20")) {
				secondaryAcct2Type = new StringBuilder("01");
			}
			secondaryAcct2Type.setLength(2);
			val = secondaryAcct2Type.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			secondaryAcct2OrSurvivor = new StringBuilder(details.getSecondarySurviour());
			secondaryAcct2OrSurvivor.setLength(1);
			val = secondaryAcct2OrSurvivor.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			
			
			embossName = new StringBuilder(details.getEmbossName());
			embossName.setLength(25);
			val = embossName.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			if(!fileName.toString().contains(".ncf")) {
				customerFirstName = new StringBuilder(details.getCustomerFirstName());
			} else {
				customerFirstName = new StringBuilder(details.getEmbossName());
			}
			customerFirstName.setLength(40);
			val = customerFirstName.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			customerMiddleName = new StringBuilder(details.getCustomerMiddleName());
			customerMiddleName.setLength(40);
			val = customerMiddleName.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			customerSurName = new StringBuilder(details.getCustomerSurName());
			customerSurName.setLength(40);
			val = customerSurName.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			addr1 = new StringBuilder(details.getAddr1());
			addr1.setLength(40);
			val = addr1.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			addr2 = new StringBuilder(details.getAddr2());
			addr2.setLength(40);
			val = addr2.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			addr3 = new StringBuilder(details.getAddr3());
			addr3.setLength(40);
			val = addr3.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			addr4 = new StringBuilder(details.getAddr4());
			addr4.setLength(40);
			val = addr4.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			city = new StringBuilder(details.getCity());
			city.setLength(30);
			val = city.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			pin = new StringBuilder(details.getPin());
			pin.setLength(8);
			val = pin.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			officePhone = new StringBuilder(details.getOfficePhone());
			officePhone.setLength(12);
			val = officePhone.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			homePhone = new StringBuilder(details.getHomePhone());
			homePhone.setLength(12);
			val = homePhone.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			product = new StringBuilder(details.getProduct());
			product.setLength(5);
			val = product.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			cardStatus = new StringBuilder(details.getCardStatus());
			cardStatus.setLength(3);
			val = cardStatus.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			if(details.getRegistrationDate() != null && details.getRegistrationDate().isEmpty() == false) {
				SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
				Date regdDate = null;
				try {
					regdDate = sdf.parse(details.getRegistrationDate());
				} catch(ParseException pe) {
					regdDate = inputDateFormat.parse(details.getRegistrationDate());
				}
				registrationDate = new StringBuilder(outDateFormat.format(regdDate));
			}
			else {
				registrationDate = new StringBuilder(details.getRegistrationDate());
			}
			registrationDate.setLength(10);
			val = registrationDate.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
//			if(details.getCreationDate() != null) {
//				qcDate = new StringBuilder(outDateFormat.format(details.getCreationDate()));
//			}
//			else {
//				qcDate = new StringBuilder(" ");
//			}
			qcDate.setLength(10);
			val = qcDate.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			fax = new StringBuilder(details.getFax());
			fax.setLength(12);
			val = fax.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			email = new StringBuilder(details.getEmail());
			email.setLength(50);
			val = email.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			fatherFirstName = new StringBuilder(details.getFathersFirstName());
			fatherFirstName.setLength(25);
			val = fatherFirstName.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			motherMaidenName = new StringBuilder(details.getMotherMaidenName());
			motherMaidenName.setLength(25);
			val = motherMaidenName.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			if(details.getDateOfBirth() != null && details.getDateOfBirth().isEmpty() == false) {
				SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
				Date date = null;
				try {
				date = sdf.parse(details.getDateOfBirth());
				} catch(ParseException pe) {
					date = inputDateFormat.parse(details.getDateOfBirth());
				}
				dateOfBirth = new StringBuilder(outDateFormat.format(date));
			}
			else {
				dateOfBirth = new StringBuilder(details.getDateOfBirth());
			}
			dateOfBirth.setLength(8);
			val = dateOfBirth.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			if(details.getYearOfPassingSSC() != null &&  Pattern.compile("\\d+").matcher(details.getYearOfPassingSSC()).matches()) {
				yearOfSSCPass = new StringBuilder(details.getYearOfPassingSSC());
			}
			else {
				yearOfSSCPass = new StringBuilder("");
			}
			yearOfSSCPass.setLength(4);
			val = yearOfSSCPass.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			if(details.getYearOfMarriage() != null &&  Pattern.compile("\\d+").matcher(details.getYearOfMarriage()).matches()) {
				yearofMarriage = new StringBuilder(details.getYearOfMarriage());
			}
			else {
				yearofMarriage = new StringBuilder("");
			}
			yearofMarriage.setLength(4);
			val = yearofMarriage.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			forthLinePrintingData = new StringBuilder(details.getFourthLinePrintingData());
			forthLinePrintingData.setLength(25);
			val = forthLinePrintingData.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			isdCode = new StringBuilder(details.getIsdCode());
			isdCode.setLength(3);
			val = isdCode.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			mobileNo = new StringBuilder(details.getMobileNo());
			mobileNo.setLength(12);
			val = mobileNo.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			primaryAcctProductCode = new StringBuilder(details.getPrimaryAccountProductCode());
			primaryAcctProductCode.setLength(8);
			val = primaryAcctProductCode.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			secondaryAcct1ProductCode = new StringBuilder(details.getSecondaryAccount1ProductCode());
			secondaryAcct1ProductCode.setLength(8);
			val = secondaryAcct1ProductCode.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			secondaryAcct2ProductCode = new StringBuilder(details.getSecondaryAccount2ProductCode());
			secondaryAcct2ProductCode.setLength(8);
			val = secondaryAcct2ProductCode.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			homeBranchCircleCode = new StringBuilder(details.getHomeBranchCircleCode());
			homeBranchCircleCode.setLength(3);
			val = homeBranchCircleCode.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			issueBranchCircleCode = new StringBuilder(details.getIssueBranchCircleCode());
			issueBranchCircleCode.setLength(3);
			val = issueBranchCircleCode.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			aadharCardNumber = new StringBuilder("");
			aadharCardNumber.setLength(12);
			val = aadharCardNumber.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			greenPinFlag = new StringBuilder("");
			greenPinFlag.setLength(1);
			val = greenPinFlag.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			coreFileName = new StringBuilder(fileName);
			coreFileName.setLength(30);
			val = coreFileName.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			welkitLetterIdentificationFlag = new StringBuilder("");
			welkitLetterIdentificationFlag.setLength(2);
			val = welkitLetterIdentificationFlag.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			imageCode = new StringBuilder("");
			imageCode.setLength(10);
			val = imageCode.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			reservedForFuturePurpose1 = new StringBuilder(details.getCardAWB());
			reservedForFuturePurpose1.setLength(30);
			val = reservedForFuturePurpose1.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			reservedForFuturePurpose2 = new StringBuilder("");
			reservedForFuturePurpose2.setLength(30);
			val = reservedForFuturePurpose2.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			reservedForFuturePurpose3 = new StringBuilder("");
			reservedForFuturePurpose3.setLength(30);
			val = reservedForFuturePurpose3.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			reservedForFuturePurpose4 = new StringBuilder("");
			reservedForFuturePurpose4.setLength(50);
			val = reservedForFuturePurpose4.toString().replace('\0',' ');
			record.append(val).append(seperator);
			
			reservedForFuturePurpose5 = new StringBuilder("");
			reservedForFuturePurpose5.setLength(50);
			val = reservedForFuturePurpose5.toString().replace('\0',' ');
			record.append(val);
			
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return record.toString();
	}
	
	public String generateFileName(String homeBranchCode, String coreFileName, String bankShortCode, Long fileId) {
		 
		 StringBuilder LCPCIndicator = null;
		 StringBuilder vendorCode;
		 StringBuilder sequencenumber;
		 StringBuilder branchcode;
		 StringBuilder bankName;
		 StringBuilder auffilename = null;
		try {
			bankName = new StringBuilder(bankShortCode);
			bankName.setLength(3);			
			
			String split[] = coreFileName.split("\\.");
			
			if(split[1].toLowerCase().equals("ncf")) {
				LCPCIndicator = new StringBuilder("LC");
			}
			else if(split[0].charAt(1) == 'W') {
				LCPCIndicator = new StringBuilder("LP");
			}
			else if(split[0].charAt(1) == 'B') {
				LCPCIndicator = new StringBuilder("NC");
			}
			else if(split[0].charAt(1) == 'N') {
				LCPCIndicator = new StringBuilder("CN");
			}
			else if(split[0].charAt(1) == 'R') {
				LCPCIndicator = new StringBuilder("CR");
			}
			LCPCIndicator.setLength(2);
			
			vendorCode = new StringBuilder("GND");
			vendorCode.setLength(3);
			
			branchcode = new StringBuilder(homeBranchCode);
			
			branchcode.setLength(5);
			
			SimpleDateFormat inputdateformat = new SimpleDateFormat("ddMMyy");
			SimpleDateFormat outputdateformat = new SimpleDateFormat("ddMMyyyy");
			
			String date = split[0].substring(2,split[0].length());
			Date fileDate = inputdateformat.parse(date);
			Date dayafter = DateUtils.addDays(fileDate, 1);
			this.dayAfterCore = dayafter;
			StringBuilder filedate = new StringBuilder(outputdateformat.format(fileDate));
			
			// setting this filedate to qcdate(class variable
			
			this.qcDate = filedate;
			filedate.setLength(8);		
			auffilename =new StringBuilder("");
			auffilename.append(bankName);
			auffilename.append(LCPCIndicator);
			auffilename.append(vendorCode);
			auffilename.append(branchcode);
			auffilename.append(filedate);
			
			Long sequenceNumber = getAufsecondformatdao().getSeqenceNumber(auffilename.toString(),fileId ,coreFileName);
			sequencenumber = new StringBuilder(getPattern(sequenceNumber.toString(),2));
			sequencenumber.setLength(2);
			
			auffilename.append(sequencenumber);
			auffilename.append(".txt");
			try {		
			String path = properties.getProperty("aluPath");
			File fileDir = new File(path+"\\"+coreFileName.substring(2, split[0].length())+"\\"+bankShortCode+"\\"+coreFileName);
			fileDir.mkdirs();
			file = new File(fileDir.getPath()+"/"+auffilename);
			if (!file.exists()) 			
				file.createNewFile();						
			fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			
			batchName = new StringBuilder();
			
			batchName.append(bankName);
			batchName.append(LCPCIndicator);
			batchName.append(vendorCode);
			batchName.append(filedate);
			batchName.append(sequencenumber);
			batchName.append("_");
			batchName.append(batchSize);
			
		}catch(IOException e) {
			logger.error(e);
			e.printStackTrace();
		} 
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return auffilename.toString();
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

	
public CreditCardDetailsBO buildCreditCardDetailsBO(CreditCardDetails creditCardDetails) {
		
		CreditCardDetailsBO details = new CreditCardDetailsBO();
		
		details.setSerialNo(creditCardDetails.getSerialNo() != null? creditCardDetails.getSerialNo() : "");
		details.setInstitutionId(creditCardDetails.getInstitutionId() != null ? creditCardDetails.getInstitutionId() : "");
		details.setCustomerId(creditCardDetails.getCustomerId() != null ? creditCardDetails.getCustomerId() : "");
		details.setHomeBranchCode(creditCardDetails.getHomeBranchCode() != null? creditCardDetails.getHomeBranchCode() : "");
		details.setIssueBranchCode(creditCardDetails.getIssueBranchCode() != null ? creditCardDetails.getIssueBranchCode() : "");
		details.setPrimaryAcctNo(creditCardDetails.getPrimaryAcctNo() != null ? creditCardDetails.getPrimaryAcctNo() : "");
		details.setCreationDate(creditCardDetails.getCreatedDate());
		details.setPrimaryAcctType(creditCardDetails.getPrimaryAcctType() != null ? creditCardDetails.getPrimaryAcctType() : "");
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
		details.setProcessedBranchCode(creditCardDetails.getProcessedBranchCode());
		details.setCardAWB(creditCardDetails.getCardAWB());
		details.setLcpcBranch(creditCardDetails.getLcpcBranch());
		return details;
	}

private void executeBatchCreate(CoreFiles file)
{

	list.add(file);
	if(list.size() == 20)
	{
		for(CoreFiles fileDet:list)
		{
			
			 getFileDAO().saveFile(fileDet);
			
		}
		list.clear();
	//	System.out.println("executeBatch transactions....");
	}
	
}

public void flushCreate()
{
	if(list.size() > 0)
	{
		for(CoreFiles fileDet:list)
		{
			
			 getFileDAO().saveFile(fileDet);
			
		}
		
		list.clear();
		 getFileDAO().flush();
		System.out.println("flushing file transactions....");
	}
}



}
