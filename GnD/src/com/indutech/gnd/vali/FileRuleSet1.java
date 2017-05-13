package com.indutech.gnd.vali;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.camel.Exchange;
import org.apache.camel.component.file.GenericFile;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.indutech.gnd.bo.drool.DroolRecordBO;
import com.indutech.gnd.dao.FileDAOImpl;
import com.indutech.gnd.dao.FindPathImpl;
import com.indutech.gnd.dao.GNDDAOImpl;
import com.indutech.gnd.dto.Bank;
import com.indutech.gnd.dto.CoreFiles;
import com.indutech.gnd.enumTypes.FileStatus;
import com.indutech.gnd.enumTypes.FileType;
import com.indutech.gnd.records.CoreFile;
import com.indutech.gnd.service.FileStateManager;
import com.indutech.gnd.service.PropertiesLoader;
import com.indutech.gnd.service.emboss.EmbossService;
import com.jramoyo.io.IndexedFileReader;

@Component("fileRule1")
public class FileRuleSet1 {
	
	Logger logger = Logger.getLogger(FileRuleSet1.class);
	
	common.Logger log = common.Logger.getLogger(FileRuleSet1.class);
	
	Properties properties = PropertiesLoader.getInstance().loadProperties();
	
	@Autowired
	private DroolRecordBO  droolRecord;

	@Autowired
	private FindPathImpl findPath;	
	
	@Autowired
	private FileDAOImpl fileDAO;
	
	@Autowired
	private GNDDAOImpl gndDAO;
	
	@Autowired
	private EmbossService embossService;  /*cr no 42*/
	

//	@Autowired
//	private CoreFilesBO fileBO;	
	
	
	IndexedFileReader fileReader;
	Map<Integer, String> fileContent;
	private String ss,checksum;
	private String[] sa;
	private CoreFile cf;
	private int lineCount = -1 ;
	
	@SuppressWarnings("rawtypes")
	private GenericFile file=null;
	private String fileName = null;
	private String description = null;
	private Long fileId;

	
	FileStatus stat1 = FileStatus.valueOf("REJECT");
	String rejectedStatus = stat1.getFileStatus();
	
	FileStatus stat2 = FileStatus.valueOf("HOLD");
	String HoldStatus = stat2.getFileStatus();
	
	FileStatus stat3 = FileStatus.valueOf("APPROVED");
	String approvedStatus = stat3.getFileStatus();
	
	private String status = rejectedStatus;
	
	
	/*cr no 42 starts*/
	public EmbossService getEmbossService() {
		return embossService;
	}

	public void setEmbossService(EmbossService embossService) {
		this.embossService = embossService;
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

	public FileDAOImpl getFileDAO() {
		return fileDAO;
	}

	public void setFileDAO(FileDAOImpl fileDAO) {
		this.fileDAO = fileDAO;
	}

	
	@SuppressWarnings("rawtypes")
	public boolean fileRuleStart(Exchange exchange) {
		try {
						
			this.file = (GenericFile) exchange.getIn().getBody();	
			fileName = this.file.getFileNameOnly();
			lineCount = getLineCount(this.file);
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return true;
	}
	
	
	@Transactional
	public boolean fileCheck(Exchange exchange) {
		boolean result = false;
		try {
			lineCount = -1;
			logger.info("file processing has started");
			GenericFile file = (GenericFile) exchange.getIn().getBody();
			List<CoreFiles> files = getFileDAO().getFileList(file.getFileNameOnly());
			if(files.size() == 0) {				
				result = true;						
			}
			else {
				
				status = rejectedStatus;
				description = "File already processed";
				logger.info("File already processed");				
			}
			
			
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean isFileNameValid_rule_3a1()
	{

		try {
			String fileName = file.getFileNameOnly();
			String prefix[] = fileName.split("\\.");
			if(prefix[0].length()==8) {
				return true;
			}
			status = rejectedStatus;
			description = "Invalid filename prefix length";
			logger.info(fileName+" length is invalid... so moved to rejected folder....");
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean isFileNameValid_rule_3a2(String str)
	{
		return true;
	}
	public boolean isFileNameValid_rule_3a3(String str)
	{
		return true;
	}
	public boolean isFileNameProcess15_rule_3b(String str)
	{
		//TODO:find file name in db for last 15days
		ss=cf.getFileName();
		return true;
	}
	public boolean isFileChecksumValid_rule_3c(String str)
	{
		try {
			sa=str.split("\\.");
			ss=sa[sa.length-(sa.length-1)];
			checksum=ss;
			
			if(checksum.equals(cf.getCaluclatedChecksum()))
			{
				return true;
			}
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		description = "Invalid checksum";
		status = rejectedStatus;
		return false;
	}
	
	public boolean isTotalRecord_rule_3d() 
	{
		
		boolean result = false;
		try {
			if(lineCount == -1) {
				lineCount = getLineCount(file);
			}
			String recordCountLine =fileContent.get(lineCount-1);
			String split[] = recordCountLine.split("\\:");
			if(Integer.parseInt(split[1].trim()) == (lineCount-2)) {
				result = true;
			}
			else {
				description = "Invalid total record count";
				status = rejectedStatus;
			}
		} catch(Exception e) {
			logger.error(e);
//			e.printStackTrace();
			description = "Invalid total record count";
			status = rejectedStatus;
			return result;
		}
		return result;	

	}
	
	public boolean isTotalRecord_exceed_3d() 
	{
		boolean result = false;
		try {
//			int linecount = getLineCount(file);
			if((lineCount-2) <= 5000) {
				result = true;
			}
			else {
				description = "total records exceeds 5000";
				status = rejectedStatus;
			}
		} catch(Exception e) {
			logger.error(e);
			logger.info("hi catch block record exceed");
			e.printStackTrace();
			description = "Invalid total record count";
			status = rejectedStatus;
			return result;
		}
		return result;	

	}
	
	public boolean isFileDateValidRule(Exchange exchange) {
		boolean result = false;
		try {
			GenericFile file = (GenericFile) exchange.getIn().getBody();
			String filename[] = file.getFileNameOnly().split("\\.");
			String name = filename[0];
			String dateoffset = name.substring(2, name.length());
			if(Pattern.compile("\\d+").matcher(dateoffset.trim()).matches()) {
				SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
				Date date = sdf.parse(dateoffset);
				if(date.compareTo(new Date()) <= 0) {
					result = true;
				}
				else {
					description = "Invalid date in filename";
					status = rejectedStatus;
				}
			}
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
			description = "Invalid date in filename";
			status = rejectedStatus;
			return result;
		}
		return result;
	}
	
	public boolean isEOFMarking_rule_3e() throws ValidationException
	{
		boolean result = false;
		try {
			String eofLine =fileContent.get(lineCount);
			fileContent.clear();
			if(eofLine.trim().equals("END OF FILE")) {
				description = "Valid file";
				status = approvedStatus;
				result = true;
			}
			else {
				description ="Invalid END OF FILE mark";
				status = rejectedStatus;
				logger.info(file.getFileNameOnly()+" having invalid END OF FILE marking at the end ..... so moved to rejected folder.....");
			}
		} catch(Exception e) {
			logger.error(e);
			description ="Invalid END OF FILE mark";
			status = rejectedStatus;
			logger.info(file.getFileNameOnly()+" having invalid END OF FILE marking at the end ..... so moved to rejected folder.....");
			e.printStackTrace();
		}
		return result;
	}	

	public boolean isValidTypeOfFile () {
		
		try {
			String fname = file.getFileNameOnly();
			String prefix[] = fname.split("\\.");
			
			int y=prefix[0].length();
			ss=prefix[0].substring(0, prefix[0].length()-(y-2));	
			if(ss.startsWith("C") || ss.startsWith("B") || ss.startsWith("H") || ss.startsWith("M") || ss.startsWith("P") || ss.startsWith("T")) {
					if(ss.endsWith("B") || ss.endsWith("N") || ss.endsWith("W") || ss.endsWith("R")) {
						return true;
					}
			}
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		description = "Invalid file prefix";
		status = rejectedStatus;
		return false;		
	}	
	
	@SuppressWarnings("rawtypes")
	public int getLineCount(GenericFile file) throws InterruptedException {
		
		try {
			fileReader = new IndexedFileReader(new File(file.getAbsoluteFilePath()));
			lineCount = fileReader.getLineCount();
			fileContent = (Map<Integer, String>) fileReader.readLines(1,lineCount);
			for(int i=fileContent.size(); i > 0 ; i--) {
				String lastline = fileContent.get(i);
				if(lastline == null || lastline.trim().isEmpty()) {
					fileContent.remove(i);
				}
				else {
					break;
				}
			}
			lineCount = fileContent.size();
		} catch (Exception e) {
			logger.info("Seems the file is not yet copied.....we ll try again after 3 seconds");
			Thread.sleep(3000);
			getLineCount(file);
		} finally	{
			if(fileReader!=null) {
				try {
					fileReader.close();
				} 
				catch (IOException e) {
					logger.error(e);
					e.printStackTrace();
				}
			}
		}
		
		return lineCount;
	}
	
	public boolean fileTypeCheck(GenericFile file) {
		try {
			String fileName = file.getFileNameOnly();
			logger.info("fileName is : "+fileName);
			String fileSuffix[] = fileName.split("\\.");
			if(fileSuffix[1].equalsIgnoreCase("ncf") == false) {
				return true;
			}
			
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return false;
	}
	
	@SuppressWarnings("rawtypes")
	@Transactional
	public synchronized void saveFile(GenericFile file) {
		try {
			String filename = file.getFileNameOnly();
//			if(filename.contains(".zip") || filename.contains(".gz") || filename.contains(".pgp")) {
//				FileUtils.moveFile((File)file.getFile(), new File(properties.getProperty("csv.rejectedPath")+"/"+filename));
//				return;
//			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
			CoreFiles fileDetails = new CoreFiles();
			fileDetails.setFilename(filename);
			fileDetails.setReceivedDate(DateUtils.addDays(sdf.parse(filename.substring(2,8)),1));
			fileDetails.setFileType(Long.parseLong(FileType.valueOf("CORE").getFileType()));
			fileDetails.setStatus(Long.parseLong(status));
			if(lineCount == -1) {
				lineCount = getLineCount(file)-2;
			}
			fileDetails.setLineCount((long)lineCount);
			fileDetails.setDescription(description);
			String split[] = filename.split("\\.");
			String lcpcGroup = null;
			Bank bank = getGndDAO().getBankCodeByPrefix(filename.substring(0,1));
			if(bank != null) {
				if(bank.getAufFormat() == FileStateManager.AUF_FORMAT_2) {
					if(split[1].toLowerCase().equals("ncf")) {
						lcpcGroup ="LC";
					}
					else if(split[0].charAt(1) == 'W') {
						lcpcGroup = "LP";
					}
					else if(split[0].charAt(1) == 'B') {
						lcpcGroup = "NC";
					}
					else if(split[0].charAt(1) == 'N') {
						lcpcGroup = "CN";
					}
					else if(split[0].charAt(1) == 'R') {
						lcpcGroup = "CR";
					}
				} else if(bank.getAufFormat() == FileStateManager.AUF_FORMAT_1){
					if(split[1].toLowerCase().equals("ncf")) {
						lcpcGroup = "LN";
					}
					else if(split[0].charAt(1) == 'W') {
						lcpcGroup = "LP";
					}
					else {
						lcpcGroup = "NC";
					}
				}
			}
			
			fileDetails.setLcpcGroup(lcpcGroup);
			fileDetails.setProcessedDate(new Date());
			Long fileId = getFileDAO().saveFile(fileDetails);
//			getDroolRecord().setFileId(fileId);
			logger.info("File saved successfully");
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}			
	}
	
	@Transactional
	public synchronized boolean saveEmbossFile(GenericFile file) {
		boolean result = false;
		CoreFiles fileDetails = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
			String embossFileName = file.getFileNameOnly();
//			if(embossFileName.contains(".zip") || embossFileName.contains(".gz") || embossFileName.contains(".pgp")) {
//				FileUtils.moveFile((File)file.getFile(), new File(properties.getProperty("csv.embossNotProcessed")+"/"+embossFileName));
//				return result;
//			}
			
			if(embossFileName.substring(0,3).equalsIgnoreCase("EMB")) {
				List<CoreFiles> list = getFileDAO().getFileList(embossFileName);
				if(list != null && list.size() >0) {
					fileDetails = new CoreFiles();
					fileDetails.setFilename(file.getFileNameOnly());
					fileDetails.setFileType(Long.parseLong(FileType.valueOf("EMBOSS").getFileType()));
					fileDetails.setStatus(Long.parseLong(FileStatus.valueOf("RECEIVED").getFileStatus()));
					fileDetails.setReceivedDate(new Date());
					fileDetails.setDescription("Embossa Already Processed");
					logger.info("Embossa with same name Already Processed");
					fileDetails.setLineCount((long)getLineCount(file)); 
					fileDetails.setProcessedDate(new Date());
					//fileDetails.setCoreFileId(emboss.fileId);/*CR no 42*/
					fileDetails.setCoreFileId(getEmbossService().getEmbossIdService(embossFileName));/*CR no 42*/
					Long coreFileId = getFileDAO().saveFile(fileDetails);
					getEmbossService().setPgpLineCount(fileDetails.getLineCount(),coreFileId);/*cr no 42*/
					return result;
				}
				
				String split[] = embossFileName.split("\\.");
				if(split[0].length() == 27) {
					String embossSubstring = embossFileName.substring(3, split[0].length());
					fileDetails = new CoreFiles();
					fileDetails.setFilename(file.getFileNameOnly());
					fileDetails.setReceivedDate(sdf.parse(embossFileName.substring(21, 27)));
					fileDetails.setFileType(Long.parseLong(FileType.valueOf("EMBOSS").getFileType()));
					fileDetails.setStatus(Long.parseLong(FileStatus.valueOf("RECEIVED").getFileStatus()));
					fileDetails.setDescription("Embossa File Not Processed");
					fileDetails.setLineCount((long)getLineCount(file)); 
					fileDetails.setProcessedDate(new Date());
					fileDetails.setLcpcGroup(embossFileName.substring(9,11));
					List<CoreFiles> auffile = getFileDAO().getAUFFile(embossSubstring,split[1]);
					
					if(auffile != null && auffile.size() > 0) {
					Iterator itr = auffile.iterator();
					while(itr.hasNext()) {
						CoreFiles coreFiles = (CoreFiles) itr.next();
							fileDetails.setStatus(Long.parseLong(FileStatus.valueOf("MAPPED").getFileStatus()));
							fileDetails.setCoreFileId(coreFiles.getCoreFileId());
							fileDetails.setAufFileId(coreFiles.getId());
							fileDetails.setDescription("Embossa Mapped with AUF");
							coreFiles.setStatus(Long.parseLong(FileStatus.valueOf("MAPPED").getFileStatus()));
							coreFiles.setDescription("AUF Mapped with Embossa");
							coreFiles.setProcessedDate(new Date());
							getFileDAO().saveFile(coreFiles);
							
						}					
						result = true;
					}
				
					Long fileId = getFileDAO().saveFile(fileDetails);
				} else {
					fileDetails = new CoreFiles();
					fileDetails.setFilename(file.getFileNameOnly());
					fileDetails.setReceivedDate(new SimpleDateFormat("ddMMyyyy").parse(embossFileName.substring(14, 22)));
					fileDetails.setFileType(Long.parseLong(FileType.valueOf("EMBOSS").getFileType()));
					fileDetails.setStatus(Long.parseLong(FileStatus.valueOf("MAPPED").getFileStatus()));
					fileDetails.setDescription("Embossa Received");
					fileDetails.setLineCount((long)getLineCount(file)); 
					fileDetails.setProcessedDate(new Date());
					fileDetails.setLcpcGroup(embossFileName.substring(6,8));
				//	fileDetails.setCoreFileId(getFileBO().getCoreFileId());
					fileDetails.setCoreFileId(getEmbossService().getEmbossIdService(embossFileName));/*CR no 42*/
				Long coreFileId = 	getFileDAO().saveFile(fileDetails);
					getEmbossService().setPgpLineCount(fileDetails.getLineCount(),coreFileId);/*cr no 42*/
					result = true;
				}
			}
		}

		 catch(Exception e) {
			logger.error(e);
			result = false;
			e.printStackTrace();
		}	
		return result;
	}
	
	
	public boolean isVIPCheck(GenericFile<?> file) {
		boolean isVIP = false;
		try {
			logger.info("in emb/vip check");
			String filename = file.getFileNameOnly();
			String split[] = filename.split("\\.");
			if(split[0].length() == 25 && !(split[1].equalsIgnoreCase("txt"))) {
				isVIP = true;
			}
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return isVIP;
	}
	
	@Transactional
	public boolean saveVIP(GenericFile<?> file) {
		boolean result = false;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
			String filename = file.getFileNameOnly();
//			if(filename.contains(".zip") || filename.contains(".gz") || filename.contains(".pgp")) {
//				FileUtils.moveFile((File)file.getFile(), new File(properties.getProperty("csv.embossNotProcessed")+"/"+filename));
//				return result;
//			}
			List<CoreFiles> files = getFileDAO().getFileList(filename);
			if(files.size() == 0) {	
				description ="VIP File Received";
				status = FileStatus.valueOf("MAPPED").getFileStatus();
				result = true;						
			}
			else {
				
				status = FileStatus.valueOf("VIP_RECEIVED").getFileStatus();
				description = "VIP File already processed";
				logger.info("VIP File already processed");				
			}
			CoreFiles coreFile = new CoreFiles();
			coreFile.setFilename(filename);
			coreFile.setDescription(description);
			coreFile.setFileType(Long.parseLong(FileType.valueOf("VIP").getFileType()));
			coreFile.setStatus(Long.parseLong(status));
			coreFile.setReceivedDate(sdf.parse(filename.substring(19, 25)));
			coreFile.setLineCount((long) getLineCount(file));
			coreFile.setProcessedDate(new Date());
			coreFile.setCoreFileId(getEmbossService().getEmbossIdService(filename));/*CR no 42*/
		//	coreFile.setCoreFileId(emboss.fileId);/*CR no 42*/
		//	coreFile.setCoreFileId(getFileBO().getCoreFileId());
			Long vipFileId = getFileDAO().saveFile(coreFile);
			getEmbossService().setPgpLineCount(coreFile.getLineCount(),vipFileId);/*cr no 42*/			
			getDroolRecord().setVipFileId(vipFileId);
			logger.info("VIP file saved successfully");
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean encryptCheck(Exchange exchange) {
		
		try {
			GenericFile<?> file = (GenericFile<?>) exchange.getIn().getBody();
			File file1 = (File) file.getFile();
			String fileName = file1.getName();
			String extension[] = fileName.split("\\.");
			if(extension[extension.length-1].equalsIgnoreCase("pgp")) {
				return true;
			}
			
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean zipCheck(Exchange exchange) {
		
		try {
			GenericFile<?> file = (GenericFile<?>) exchange.getIn().getBody();
			File file1 = (File) file.getFile();
			String fileName = file1.getName();
			String extension[] = fileName.split("\\.");
			if(extension[extension.length-1].equalsIgnoreCase("zip") || extension[extension.length-1].equalsIgnoreCase("gz")) {
				return true;
			}
			
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean isCoreOrEmbCheck(Exchange exchange) {
		
		try {
			File file = (File) exchange.getIn().getBody();
			if(file.getName().substring(0,3).equalsIgnoreCase("EMB")) {
				logger.info("given file is embossa file : "+file.getName());
				return false;
			}
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return true;
	}
	
	
	
}
