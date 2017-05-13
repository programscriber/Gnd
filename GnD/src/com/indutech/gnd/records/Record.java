package com.indutech.gnd.records;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.indutech.gnd.bo.RecordEventBO;
import com.indutech.gnd.bo.drool.DroolRecordBO;
import com.indutech.gnd.dao.BranchDAOImpl;
import com.indutech.gnd.dao.FileConverterDAOImpl;
import com.indutech.gnd.dao.GNDDAOImpl;
import com.indutech.gnd.dao.MasterAWBDAOImpl;
import com.indutech.gnd.dto.Bank;
import com.indutech.gnd.dto.Branch;
import com.indutech.gnd.dto.CreditCardDetails;
import com.indutech.gnd.dto.MasterAWB;
import com.indutech.gnd.dto.MasterCourierService;
import com.indutech.gnd.dto.RecordEvent;
import com.indutech.gnd.enumTypes.RecordStatus;
import com.indutech.gnd.enumTypes.Status;
import com.indutech.gnd.jasper.reports.JasperReportGenerator;
import com.indutech.gnd.service.BranchService;
import com.indutech.gnd.service.CardStateManagerService;
import com.indutech.gnd.service.CorierServiceProviders;
import com.indutech.gnd.service.FileStateManager;
import com.indutech.gnd.service.PinStateManagerService;
import com.indutech.gnd.service.PropertiesLoader;
import com.indutech.gnd.util.DataInitialization;
import com.indutech.gnd.util.StringUtil;

public class Record {
	
	
	Logger logger = Logger.getLogger(Record.class);
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	Properties properties = PropertiesLoader.getInstance().loadProperties();
	
	@Autowired
	private GNDDAOImpl gndDAO;	
	
	@Autowired
	private FileConverterDAOImpl fileConverterDAO;
	
	@Autowired
	private MasterAWBDAOImpl masterAWBDAO;
	
	@Autowired
	private DroolRecordBO droolRecord;	
	
	@Autowired
	private BranchDAOImpl branchDAO;
	
	@Autowired	
	private JasperReportGenerator jasperReports;
	
	
	
	
	public JasperReportGenerator getJasperReports() {
		return jasperReports;
	}

	public void setJasperReports(JasperReportGenerator jasperReports) {
		this.jasperReports = jasperReports;
	}

	public BranchDAOImpl getBranchDAO() {
		return branchDAO;
	}

	public void setBranchDAO(BranchDAOImpl branchDAO) {
		this.branchDAO = branchDAO;
	}

	public FileConverterDAOImpl getFileConverterDAO() {
		return fileConverterDAO;
	}

	public void setFileConverterDAO(FileConverterDAOImpl fileConverterDAO) {
		this.fileConverterDAO = fileConverterDAO;
	}

	public DroolRecordBO getDroolRecord() {
		return droolRecord;
	}

	public void setDroolRecord(DroolRecordBO droolRecord) {
		this.droolRecord = droolRecord;
	}

	public final MasterAWBDAOImpl getMasterAWBDAO() {
		return masterAWBDAO;
	}

	public final void setMasterAWBDAO(MasterAWBDAOImpl masterAWBDAO) {
		this.masterAWBDAO = masterAWBDAO;
	}

	public final GNDDAOImpl getGndDAO() {
		return gndDAO;
	}

	public final void setGndDAO(GNDDAOImpl gndDAO) {
		this.gndDAO = gndDAO;
	}
	
	
	RecordStatus stat1 = RecordStatus.valueOf("APPROVED");
	String approvedStatus = stat1.getRecordStatus();
	RecordStatus stat2 = RecordStatus.valueOf("REJECT");
	String rejectedStatus = stat2.getRecordStatus();
	RecordStatus holdstat = RecordStatus.valueOf("HOLD");
	String holdStatus = holdstat.getRecordStatus();	
	RecordStatus assigned = RecordStatus.valueOf("AWB_ASSIGNED");
	String assgnedStatus = assigned.getRecordStatus();
	Status blockStatus = Status.valueOf("BLOCKED");
	String blockStatusID = blockStatus.getStatus();
	boolean resultSet2Result;
	boolean resultSet3Result;
	boolean resultSet4Result;
	private long finalRecordStatus;
	
	private Long recordId = null;
	List<CreditCardDetails> list= new ArrayList<CreditCardDetails>();
	int listSize =Integer.parseInt( properties.getProperty("listSize"));
		
	
	
	@Transactional
	public synchronized void  processRawRecordAfterDrools(DroolRecordBO droolRecord)  {
		
		
		try {
			Map<Long, Map<String, Branch>> bankList = DataInitialization.getInstance().getBranchInfo();
			Map<String, Branch> branchList = bankList.get(droolRecord.getBankId());
			String fileName = getDroolRecord().getFileName();
			CreditCardDetails details = buildCreditCardDetailsFromDrools(droolRecord);
			finalRecordStatus = details.getStatus();
			if(finalRecordStatus == Long.parseLong(approvedStatus)) {
				details.setRuleStatus(DroolRecordBO.RULE_APPROVED_STATUS);
				if(!details.getHomeBranchCode().equals(details.getIssueBranchCode())) {
					if(!(fileName.charAt(1) == 'W') && !fileName.contains("ncf")) {
						if(bankList != null && bankList.size() > 0 ) {
							Branch issueBranch = (Branch) branchList.get(details.getIssueBranchCode());
							Branch homeBranch = (Branch)branchList.get(details.getHomeBranchCode());
							Integer issueBranchCodeIsNonCardIssueBranch = issueBranch.getIsNonCardIssueBranch();
							Integer homeBranchCodeIsNonCardIssueBranch = homeBranch.getIsNonCardIssueBranch();
								if(issueBranchCodeIsNonCardIssueBranch == BranchService.NON_CARD_ISSUE_BRANCH_INACTIVE) {
								//details.setProcessedBranchCode(details.getIssueBranchCode());
										if(homeBranchCodeIsNonCardIssueBranch == BranchService.NON_CARD_ISSUE_BRANCH_ACTIVE)
											{
												
												
												details.setProcessedBranchCode(details.getHomeBranchCode());
											}
										else
								  			{
											
											//reject if homebranch code is also active
											details.setFlowStatus(DroolRecordBO.FLOW_ERROR);
											details.setStatus((long) CardStateManagerService.CARD_STATUS_REJECT);
											details.setRuleStatus(DroolRecordBO.HOMEBRANCH_AND_ISSUEBRANCH_ISNONCARD);
									
											}
										
								}
								else {
									
									//details.setProcessedBranchCode(details.getHomeBranchCode());
									details.setProcessedBranchCode(details.getIssueBranchCode());
								}
						}
					} else if(fileName.charAt(1) == 'W' && !fileName.contains("ncf")) {
						
						Branch branch = (Branch) branchList.get(details.getHomeBranchCode());
						Branch branch2 = (Branch)branchList.get(details.getIssueBranchCode());
						Integer homeBranchCodeIsNonCardIssueBranch = branch.getIsNonCardIssueBranch();
						Integer issueBranchCodeIsNonCardIssueBranch = branch2.getIsNonCardIssueBranch();
								if(homeBranchCodeIsNonCardIssueBranch == BranchService.NON_CARD_ISSUE_BRANCH_INACTIVE) {
									
											if(issueBranchCodeIsNonCardIssueBranch == BranchService.NON_CARD_ISSUE_BRANCH_ACTIVE)
												{
												
													details.setProcessedBranchCode(details.getIssueBranchCode());
												}
											else
												{ 
												
													//reject if homebranch code is also active
													details.setFlowStatus(DroolRecordBO.FLOW_ERROR);
													details.setStatus((long) CardStateManagerService.CARD_STATUS_REJECT);
													details.setRuleStatus(DroolRecordBO.HOMEBRANCH_AND_ISSUEBRANCH_ISNONCARD);
							
												}
									//details.setProcessedBranchCode(details.getHomeBranchCode());
									//details.setProcessedBranchCode(details.getIssueBranchCode());
									}
								else {
									
									//details.setProcessedBranchCode(details.getIssueBranchCode());
									details.setProcessedBranchCode(details.getHomeBranchCode());
									 }
								}
							} else {
								
					Branch branch = (Branch) branchList.get(details.getIssueBranchCode());
					
					Integer issueBranchCodeIsNonCardIssueBranch = branch.getIsNonCardIssueBranch();
					
						if(issueBranchCodeIsNonCardIssueBranch == BranchService.NON_CARD_ISSUE_BRANCH_INACTIVE) {
							details.setFlowStatus(DroolRecordBO.FLOW_ERROR);
							details.setStatus((long) CardStateManagerService.CARD_STATUS_REJECT);
							if(!fileName.contains("ncf")){
							details.setRuleStatus(DroolRecordBO.HOMEBRANCH_AND_ISSUEBRANCH_ISNONCARD);
							}else{
								details.setRuleStatus(DroolRecordBO.HOMEBRANCHCODEISNONCARDISSUE);
							}
							
						}
						else
						{
							
							details.setProcessedBranchCode(details.getIssueBranchCode());
						}
	
					
				}
			}
			String fileType[] = fileName.split("\\.");
			if(fileType[1].toLowerCase().equals("npc") ) {
				if(finalRecordStatus == Long.parseLong(approvedStatus)){
					//logger.info("inside npc");
				List<CreditCardDetails> cardDetails = getGndDAO().getNCFRecord(details.getPrimaryAcctNo().trim(), details.getProduct().trim());
				//logger.info("inside npc size"+ cardDetails.size());
				if(cardDetails != null && cardDetails.size() > 0) {
					Iterator<CreditCardDetails> itr = cardDetails.iterator();
					while(itr.hasNext()) {
						CreditCardDetails ncfCardDetails = (CreditCardDetails) itr.next();
						details.setCreditCardDetailsId(ncfCardDetails.getCreditCardDetailsId());
						details.setRsn(ncfCardDetails.getRsn());
						details.setStatus(ncfCardDetails.getStatus());
						details.setCardAWB(ncfCardDetails.getCardAWB());
						details.setCardServiceProvider(ncfCardDetails.getCardServiceProvider());
						details.setPinAWB(ncfCardDetails.getPinAWB());
						details.setPinServiceProvider(ncfCardDetails.getPinServiceProvider());
						details.setPanMasked(ncfCardDetails.getPanMasked());
						details.setAufId(ncfCardDetails.getAufId());
						details.setEmbossaId(ncfCardDetails.getEmbossaId());
						details.setPinstatus(ncfCardDetails.getPinstatus());
						details.setLcpcBranch(ncfCardDetails.getLcpcBranch());
						details.setRtoBranch(ncfCardDetails.getRtoBranch());
						executeBatchUpdateNpc(details);
						if(getDroolRecord().getProductMappingFlag() == 1) {
							RecordEventBO eventBO = new RecordEventBO();
							eventBO.setDescription(getDroolRecord().getProductMappingInfo());
							eventBO.setEventDate(new Date());
							eventBO.setEventId(RecordEventBO.EVENT_PRODUCT_CHANGE);
							eventBO.setRecordId(details.getCreditCardDetailsId());
							processEvent(eventBO);
							getDroolRecord().setProductMappingFlag(0);
							getDroolRecord().setProductMappingInfo("");
						}
						
					}
					
					
				}
				else {
					
					finalRecordStatus = Long.parseLong(holdStatus);
					details.setFlowStatus(DroolRecordBO.FLOW_NPC_ERR);
					details.setRuleStatus(DroolRecordBO.NCF_FOUND_ERR);
					details.setStatus(finalRecordStatus);
				Long recordId	= executeBatchUpdateNpc(details);
					if(getDroolRecord().getProductMappingFlag() == 1) {
						RecordEventBO eventBO = new RecordEventBO();
						eventBO.setDescription(getDroolRecord().getProductMappingInfo());
						eventBO.setEventDate(new Date());
						eventBO.setEventId(RecordEventBO.EVENT_PRODUCT_CHANGE);
						eventBO.setRecordId(recordId);
						processEvent(eventBO);
						getDroolRecord().setProductMappingFlag(0);
						getDroolRecord().setProductMappingInfo("");
					}
					
					
				}
			}
				else{
					
					executeBatchUpdateNpc(details);
				}
				return;
			}
		
			else {
			//	recordId = gndDAO.saveCreditCardDetails(details);
				recordId = executeBatchCreate(details);
			}
			if(getDroolRecord().getProductMappingFlag() == 1) {
				RecordEventBO eventBO = new RecordEventBO();
				eventBO.setDescription(getDroolRecord().getProductMappingInfo());
				eventBO.setEventDate(new Date());
				if(!fileType[1].toLowerCase().equals("ncf")) {
					eventBO.setEventId(RecordEventBO.EVENT_PRODUCT_CHANGE);
				} else {
					eventBO.setEventId(RecordEventBO.NCF_EVENT_PRODUCT_CHANGE);
				}
				eventBO.setRecordId(recordId);
				processEvent(eventBO);
				getDroolRecord().setProductMappingFlag(0);
				getDroolRecord().setProductMappingInfo("");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}

	}
	
	@Transactional
	public void compositeValidationCheck() {
		logger.info("composite validation starts");
		Long fileId = getDroolRecord().getFileId();
		int count = getGndDAO().updateCompositeRecords(fileId);
		
		logger.info(count+" records has been updated");
	}
	
	@Transactional
	public void compositeValidationCheckForNCF() {
		logger.info("composite validation starts");
		Long fileId = getDroolRecord().getFileId();
		int count = getGndDAO().updateCompositeRecordsForNCF(fileId);
		
		logger.info(count+" records has been updated");
	}

	@Transactional
	public synchronized void checkBranchGroupSizeExceedRule() {
		try {
			logger.info("In branch exceed rule validation");
			Long fileId = getDroolRecord().getFileId();
			String branchLimit = properties.getProperty("branchLimit");
			getGndDAO().updateRecordsForBranchGroupForExceedRule(fileId, (long) CardStateManagerService.CARD_STATUS_APPROVED, Long.parseLong(branchLimit));
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}
	
	@Transactional
	public synchronized void processTXNData() {
		Long fileId = getDroolRecord().getFileId();
		getGndDAO().insertTxnInfo(fileId);				//now insert all the txn info for each record
	}
	
	
	
	@Transactional
	public synchronized void assignLCPCCode() {                //not using
		try {
			logger.info("In Lcpc branch assignment");
			Long fileId = getDroolRecord().getFileId();
			getGndDAO().insertLCPCBranch(fileId);
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public synchronized void assignCardAWB() {	

		try {
			logger.info("In Card AWB assignment, processing...... ");
			String fileName = getDroolRecord().getFileName();
			Long fileId = getDroolRecord().getFileId();
			if(fileName.substring(1, 2).equals("W")) {
				Bank bank = getFileConverterDAO().getBankCodeByPrefix(fileName.substring(0,1));
				if(bank != null && bank.getLPAWBBranchGroup() == 1) {
					ncfLCPCCardAWBAssign();
					return;
				}
			}
			List<CreditCardDetails> list = getGndDAO().getValidCreditCardDetails(fileId);						
			if(list != null && list.size() > 0) {
				int detailscount = list.size();
				List<MasterAWB> cardAWB = getMasterAWBDAO().getAWBForChennaiAndMumbai((long)CorierServiceProviders.INDIAN_POST_CHENNAI, detailscount);
				if(cardAWB != null && cardAWB.size() > 0 && cardAWB.size() == detailscount) {
					int awbcount = cardAWB.size();
					MasterCourierService corierService = (MasterCourierService) getMasterAWBDAO().getServiceProvider((long)CorierServiceProviders.INDIAN_POST_CHENNAI);
					String serviceProviderName = corierService.getServiceProviderName();
						for(int i = 0; i < awbcount; i++) {
							CreditCardDetails details = (CreditCardDetails) list.get(i);
							MasterAWB awb = (MasterAWB) cardAWB.get(i);
							details.setRsn(getGndDAO().getRSNSequenceNumber());
							details.setRuleStatus(CardStateManagerService.CARD_STATUS_AWBASSIGNED_STRING);
							details.setCardAWB(awb.getAwbName());
							details.setCardServiceProvider(serviceProviderName);
							details.setCreatedDate(new Date());										
							details.setStatus((long)CardStateManagerService.CARD_STATUS_AWBASSIGNED); 
							//getGndDAO().saveCardDetails(details);
							executeBatchUpdate(details);
						}
						flushUpdate();
					logger.info("AWB keys assignment has done successfully");
				} else {
					logger.info("AWB keys not available");
					getGndDAO().updateRecordForAWBNotAvailable(fileId,(long) CardStateManagerService.CARD_STATUS_HOLD,(long) CardStateManagerService.CARD_STATUS_APPROVED,DroolRecordBO.FLOW_HOLD,DroolRecordBO.RULE_CARD_AWB_ASSIGNED_ERROR);
				}
			} else {
				logger.info("No valid records found to assign awb keys");
			}
//			getGndDAO().insertTXNLogForAWB(fileId, DroolRecordBO.RULE_CARD_AWB_ASSIGNED_ERROR);
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}
	
	//@Transactional
	public synchronized void processTXNDataForAWB() {
		try {
			Long fileId = getDroolRecord().getFileId();
			getGndDAO().insertTXNLogForAWB(fileId, DroolRecordBO.RULE_CARD_AWB_ASSIGNED_ERROR);
		} catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
	}
	
	public synchronized void processTXNDataForAWBNcf() {
		try {
			Long fileId = getDroolRecord().getFileId();
			getGndDAO().insertTXNLogForAWBNcf(fileId);
		} catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
	}
	
	@SuppressWarnings("rawtypes")
	@Transactional
	public synchronized void ncfLCPCCardAWBAssign() {
		logger.info("In Card AWB assignment for NCF or LP file, processing...... ");
		try {
			Long fileId = getDroolRecord().getFileId(); 
			List<CreditCardDetails> list = getGndDAO().getValidCreditCardDetails(fileId);
			if(list != null && list.size() > 0) {
				Iterator iterator = list.iterator();
				Multimap<String, CreditCardDetails> map = ArrayListMultimap.create();
				while(iterator.hasNext()) {
					CreditCardDetails details = (CreditCardDetails) iterator.next();
					map.put(details.getLcpcBranch(), details);
				}
				if(map != null && map.size() > 0) {
					MasterCourierService corierService = (MasterCourierService) getMasterAWBDAO().getServiceProvider((long)CorierServiceProviders.INDIAN_POST_CHENNAI);
					Set<String> LCPCBranchCodeList = map.keySet();
					for(String processedBranchCode : LCPCBranchCodeList) {
						List<CreditCardDetails> creditcardDetailsIdList = (List<CreditCardDetails>) map.get(processedBranchCode);
						if(creditcardDetailsIdList != null && creditcardDetailsIdList.size() > 0) {
							int awbRange = CardStateManagerService.CARD_STATUS_AWB_RANGE;
							int x = 0;
							if(creditcardDetailsIdList.size() % awbRange > 0) {
								x = 1;
							}
							int k = 0;
							for(int i = 1 ; i <= (creditcardDetailsIdList.size() / awbRange) + x ; i++) {
								MasterAWB cardAWB = getMasterAWBDAO().getAWBForChennaiAndMumbai((long)CorierServiceProviders.INDIAN_POST_CHENNAI);
								if(cardAWB != null) {
									for(int j = k ; j < creditcardDetailsIdList.size(); j++,k++) {
										if(j > (awbRange * i)-1) {
											break;
										}
										CreditCardDetails details = (CreditCardDetails) creditcardDetailsIdList.get(j);
										details.setCardAWB(cardAWB.getAwbName());
										details.setCreatedDate(new Date());
										details.setRsn(getGndDAO().getRSNSequenceNumber());
										details.setStatus((long)CardStateManagerService.CARD_STATUS_AWBASSIGNED);
										details.setRuleStatus(CardStateManagerService.CARD_STATUS_AWBASSIGNED_STRING);
										details.setCardServiceProvider(corierService.getServiceProviderName());
										//getGndDAO().saveCardDetails(details);
										executeBatchUpdateNcf(details);
										/*saveRecordEvent(details.getCreditCardDetailsId(),(long) CardStateManagerService.CARD_STATUS_AWBASSIGNED,"CARD"+ RecordEventBO.getStatusInString(RecordEventBO.EVENT_AWB_ASSIGNED));*/
									}
									
								}
							}
							
						}
					}
					flushUpdateNcf();
					logger.info("AWB assignment has done");
				} else {
					logger.info("No valid records found to assign awb keys");
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
	}

	@SuppressWarnings("rawtypes")
	@Transactional
	public synchronized void assignPinAWB() {				// For auf format 1
		
		try {
			Long fileId = getDroolRecord().getFileId();
			String fileName = getDroolRecord().getFileName();
			Bank bank = null;
			if(!StringUtil.isEMptyOrNull(fileName)) {
				String prefix = fileName.substring(0, 1);
				bank = getFileConverterDAO().getBankCodeByPrefix(prefix);
			}
			if(bank.getAufFormat() == FileStateManager.AUF_FORMAT_1) {
				List group= getFileConverterDAO().getBranchGroup((long)CardStateManagerService.CARD_STATUS_AWBASSIGNED, fileId);
				if(group != null && group.size() > 0) {
					logger.info("branch group size is : "+group.size());
					Iterator iterator = group.iterator();
					while(iterator.hasNext()) {
						Object[] branchGroup = (Object[]) iterator.next();	
						String branchCode = (String)branchGroup[0];
						Long bankId = (Long) branchGroup[2];
						int pin_count = PinStateManagerService.PIN_STATUS_AWB_COUNT;
						while(pin_count > 0) {					
								List<CreditCardDetails> pinAWBDetailsList = getGndDAO().getDetailsListByBranchForPin(branchCode,  PinStateManagerService.PIN_STATUS_AWB_RANGE, fileId, bankId);
								if(pinAWBDetailsList.size() > 0) {
									logger.info("pin awb size is : "+pinAWBDetailsList.size());
									MasterAWB awbPin = getMasterAWBDAO().getAWBForChennaiAndMumbai((long)CorierServiceProviders.INDIAN_POST_MUMBAI);							
									if(awbPin != null) {
	//									 MasterAWB awbPin = (MasterAWB) masterAWBPinList.get(0);
										 MasterCourierService corierServicePin = (MasterCourierService) getMasterAWBDAO().getServiceProvider(awbPin.getServiceProviderId());
										 Iterator itr = pinAWBDetailsList.iterator();
										 while(itr.hasNext()) {								
											 CreditCardDetails cardDetails = (CreditCardDetails) itr.next();					
											 cardDetails.setPinAWB(awbPin.getAwbName());									
											 if(corierServicePin != null) {
												 cardDetails.setPinServiceProvider(corierServicePin.getServiceProviderName());
											 }
											 finalRecordStatus = Long.parseLong(assgnedStatus);
											 cardDetails.setCreatedDate(new Date());
											 cardDetails.setPinstatus((long)PinStateManagerService.PIN_STATUS_AWB_ASSIGNED);
											 getGndDAO().saveCardDetails(cardDetails);
											 if(cardDetails.getStatus() == Long.parseLong(assgnedStatus)) {
												 saveRecordEvent(cardDetails.getCreditCardDetailsId(), RecordEventBO.EVENT_AWB_ASSIGNED,"PIN"+ RecordEventBO.getStatusInString(RecordEventBO.EVENT_AWB_ASSIGNED));
											 }
										 }
										 //covering note got seperated by auf format
/*										 	if(bank.getAufFormat() == FileStateManager.AUF_FORMAT_2) {
										 		String format = (String) properties.getProperty("pinmailerformat");
										 		Integer isVIP = 0;
										 		Date date = new SimpleDateFormat("ddMMyy").parse(fileName.substring(2, 8));
										 		String qcDate = new SimpleDateFormat("ddMMyy").format(DateUtils.addDays(date, 1));
										 		String pinMailerName = bank.getShortCode()+"_"+branchCode+"_"+fileName.substring(2, 8)+"_"+qcDate;
										 		getJasperReports().pinMailerReport(awbPin.getAwbName(),bank,branchCode,(long)PinStateManagerService.PIN_STATUS_AWB_ASSIGNED, pinMailerName, isVIP, fileName, fileId, format);
										 	}
*/
										 //status already changed
	//									 awbPin.setStatus(Long.parseLong(blockStatusID));
	//									 getMasterAWBDAO().changeStatus(awbPin);									
									} else {	
										logger.info("awb's are not available");
										finalRecordStatus = Long.parseLong(holdStatus);
										Iterator itr = pinAWBDetailsList.iterator();								
										while(itr.hasNext()) {								
											 CreditCardDetails cardDetails = (CreditCardDetails) itr.next();									 
											 cardDetails.setStatus(finalRecordStatus);
											 cardDetails.setFlowStatus(DroolRecordBO.FLOW_HOLD);
											 cardDetails.setRuleStatus(DroolRecordBO.RULE_PIN_AWB_ASSIGNED_ERROR);
											 Long detailsId = gndDAO.saveCardDetails(cardDetails);
											 saveRecordEvent(detailsId, RecordEventBO.EVENT_QC_RULES, DroolRecordBO.RULE_PIN_AWB_ASSIGNED_ERROR);
										}
									}
									logger.info("pin count is : "+pin_count);
								}						
						
						else {
							pin_count = 0; //force exit when the list is complete
						}						
						pin_count--;
						
						}
					}
				}
			}
			
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();			
		}
	}
				
	public void processEvent(RecordEventBO event) {
		try{
			getGndDAO().saveRecordEvent(buildRecordEvent(event));
		} catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public RecordEvent buildRecordEvent(RecordEventBO eventBO) {
		RecordEvent event = new RecordEvent();
		event.setRecordId(eventBO.getRecordId());
		event.setEventId(eventBO.getEventId());
		event.setEventDate(new Date());
		event.setDescription(eventBO.getDescription());
		return event;
	}
		
	public void setRecordApprovedStatus() {
		finalRecordStatus = Long.parseLong(approvedStatus);
	}
	public void setRecordHoldStatus() {
		finalRecordStatus = Long.parseLong(holdStatus);
	}
	public void setRecordRejectedStatus() {
		finalRecordStatus = Long.parseLong(rejectedStatus);
	}	
	
	public void setFinalRecordStatus(String status) {
		finalRecordStatus = Long.parseLong(status);
	}
	
	/**
	 * 
	 */
	
	public void saveRecordEvent(Long recordId, Long eventId, String description) {
		RecordEvent event = new RecordEvent();
		event.setDescription(description);
		event.setEventDate(new Date());
		event.setRecordId(recordId);
		event.setEventId(eventId);
		getGndDAO().saveRecordEvent(event);
	}
	public CreditCardDetails buildCreditCardDetailsFromDrools(DroolRecordBO droolRecordBO) {
		CreditCardDetails creditCardDetails = new CreditCardDetails();
		try {
			creditCardDetails.setCreditCardDetailsId(droolRecordBO.getCreditCardDetailsId());
			String addr1 = droolRecordBO.getAddr1();
			String addr2 = droolRecordBO.getAddr2();
			String addr3 = droolRecordBO.getAddr3();
			String addr4 = droolRecordBO.getAddr4();
			if(droolRecordBO.getStatus() == (long) CardStateManagerService.CARD_STATUS_APPROVED) {
				String specialCharacters = (String) properties.getProperty("specialCharacters");
				String split[] = specialCharacters.split(",");
				int len = split.length-1;
				while(len >= 0) {
					addr1 = addr1 != null ? addr1.replace(split[len], " ") : addr1;
					addr2 = addr2 != null ? addr2.replace(split[len], " ") : addr2;
					addr3 = addr3 != null ? addr3.replace(split[len], " ") : addr3;
					addr4 = addr4 != null ? addr4.replace(split[len], " ") : addr4;
					len--;
				}
			}
			creditCardDetails.setAddr1(addr1);
			creditCardDetails.setAddr2(addr2);
			creditCardDetails.setAddr3(addr3);
			creditCardDetails.setAddr4(addr4);
			creditCardDetails.setCardStatus(droolRecordBO.getCardStatus());
			creditCardDetails.setCity(droolRecordBO.getCity());
			creditCardDetails.setCustomerFirstName(droolRecordBO.getCustomerFirstName());
			creditCardDetails.setCustomerId(droolRecordBO.getCustomerId());
			creditCardDetails.setCustomerMiddleName(droolRecordBO.getCustomerMiddleName());
			creditCardDetails.setCustomerSurName(droolRecordBO.getCustomerSurName());
			creditCardDetails.setDateOfBirth(droolRecordBO.getDateOfBirth());
			creditCardDetails.setRegistrationDate(droolRecordBO.getRegistrationDate());
			creditCardDetails.setEmail(droolRecordBO.getEmail());
			creditCardDetails.setEmbossName(droolRecordBO.getEmbossName());
			creditCardDetails.setFathersFirstName(droolRecordBO.getFathersFirstName());
			creditCardDetails.setFax(droolRecordBO.getFax());
			creditCardDetails.setFourthLinePrintingData(droolRecordBO.getFourthLinePrintingData());
			creditCardDetails.setHomeBranchCircleCode(droolRecordBO.getHomeBranchCircleCode());
			creditCardDetails.setHomeBranchCode(droolRecordBO.getHomeBranchCode());
			creditCardDetails.setHomePhone(droolRecordBO.getHomePhone());
			creditCardDetails.setInstitutionId(droolRecordBO.getInstitutionId());
			creditCardDetails.setIsdCode(droolRecordBO.getIsdCode());
			creditCardDetails.setIssueBranchCircleCode(droolRecordBO.getIssueBranchCircleCode());
			creditCardDetails.setIssueBranchCode(droolRecordBO.getIssueBranchCode());
			creditCardDetails.setMobileNo(droolRecordBO.getMobileNo());
			creditCardDetails.setMotherMaidenName(droolRecordBO.getMotherMaidenName());
			creditCardDetails.setOfficePhone(droolRecordBO.getOfficePhone());
			creditCardDetails.setPin(droolRecordBO.getPin());
			creditCardDetails.setPrimaryAccountProductCode(droolRecordBO.getPrimaryAccountProductCode());
			creditCardDetails.setPrimaryAcctNo(droolRecordBO.getPrimaryAcctNo());
			creditCardDetails.setPrimaryAcctSurviour(droolRecordBO.getPrimaryAcctSurviour());
			creditCardDetails.setPrimaryAcctType(droolRecordBO.getPrimaryAcctType());
			creditCardDetails.setProduct(droolRecordBO.getProduct());
			creditCardDetails.setSecondaryAccount1ProductCode(droolRecordBO.getSecondaryAccount1ProductCode());
			creditCardDetails.setSecondaryAccount2ProductCode(droolRecordBO.getSecondaryAccount2ProductCode());
			creditCardDetails.setSecondaryAcct1(droolRecordBO.getSecondaryAcct1());
			creditCardDetails.setSecondaryAcct1Type(droolRecordBO.getSecondaryAcct1Type());
			creditCardDetails.setSecondaryAcct2(droolRecordBO.getSecondaryAcct2());
			creditCardDetails.setSecondaryAcct2Type(droolRecordBO.getSecondaryAcct2Type());
			creditCardDetails.setSecondaryAcctSurviour(droolRecordBO.getSecondaryAcctSurviour());
			creditCardDetails.setSecondarySurviour(droolRecordBO.getSecondarySurviour());
			creditCardDetails.setSerialNo(droolRecordBO.getSerialNo());
			creditCardDetails.setYearOfMarriage(droolRecordBO.getYearOfMarriage());
			creditCardDetails.setYearOfPassingSSC(droolRecordBO.getYearOfPassingSSC());
			creditCardDetails.setRuleStatus(droolRecordBO.getRuleStatus());
			creditCardDetails.setFlowStatus(droolRecordBO.getFlowStatus());
			creditCardDetails.setStatus(droolRecordBO.getStatus());
			creditCardDetails.setCreatedDate(new Date());
			creditCardDetails.setFileId(getDroolRecord().getFileId());
			creditCardDetails.setPinstatus((long)PinStateManagerService.PIN_STATUS_UNINITIALIZED);
			creditCardDetails.setIsIndividual(droolRecordBO.getInviduality());
			creditCardDetails.setBankId(droolRecordBO.getBankId());
			creditCardDetails.setLcpcBranch(droolRecordBO.getLcpcBranch());
			creditCardDetails.setIsVIP(0);
		} catch(Exception e) {
			
			logger.info("in catch block");
			logger.info("dob is : "+droolRecordBO.getDateOfBirth());
			e.printStackTrace();
			
		}
		return creditCardDetails;
	}
	
	public boolean isRuleFlowPass(DroolRecordBO ruleRecordBO) {
		
		if(ruleRecordBO.getFlowStatus().equals("Pass")) {
			return true;
		}
		return false;
	}
	
	public DroolRecordBO setErrorStatus(DroolRecordBO ruleRecordBO) {
		 ruleRecordBO.setStatus(Long.parseLong(rejectedStatus));
		 return ruleRecordBO;
	}
	
	public DroolRecordBO setHoldStatus(DroolRecordBO ruleRecordBO) {
	
	 ruleRecordBO.setStatus(Long.parseLong(holdStatus));
	 return ruleRecordBO;
}
	
	public DroolRecordBO setPassStatus(DroolRecordBO ruleRecordBO) {
		 ruleRecordBO.setStatus(Long.parseLong(approvedStatus));
		 ruleRecordBO.setFlowStatus(DroolRecordBO.FLOW_PASS);
		 return ruleRecordBO;		 
		
	}
	
	public DroolRecordBO setRecordHoldStatus(DroolRecordBO ruleRecordBO) {
		ruleRecordBO.setFlowStatus(DroolRecordBO.FLOW_HOLD);
		ruleRecordBO.setRuleStatus("branch code "+ruleRecordBO.getHomeBranchCode().trim()+" is not present in the master branch");
		return ruleRecordBO;
	}
	
	public DroolRecordBO setRecordApprovedStatus(DroolRecordBO ruleRecordBO) {
		ruleRecordBO.setFlowStatus(DroolRecordBO.FLOW_PASS);
		ruleRecordBO.setRuleStatus("branch code "+ruleRecordBO.getHomeBranchCode().trim()+" is present in the master branch");
		return ruleRecordBO;
	}
	
//	@Transactional
	public void printQuery() {
//		StringBuilder input = new StringBuilder(query);
//		input.deleteCharAt(input.length()-1);
//		logger.info(input);
//		getGndDAO().insertbulkRecords(input.toString());
		logger.info("process has done successfully");
	}
	
	public void start() {
		logger.info("process started : ");
	}
	
	private Long executeBatchCreate(CreditCardDetails detetail)
	{
		detetail.setCreditCardDetailsId(getGndDAO().getCreditCardDetailIdSeq());
		list.add(detetail);
		if(list.size() == listSize)
		{
			for(CreditCardDetails det:list)
			{
				//System.out.println(det + "saving record");
				getGndDAO().saveCreditCardDetails(det);
				
			}
			list.clear();
		//	System.out.println("executeBatch transactions....");
		}
		return detetail.getCreditCardDetailsId();
	}
	
	
	private Long executeBatchUpdate(CreditCardDetails detetail)
	{
		//detetail.setCreditCardDetailsId(getGndDAO().getCreditCardDetailIdSeq());
		list.add(detetail);
		if(list.size() == listSize)
		{
			for(CreditCardDetails det:list)
			{
				getGndDAO().saveCardDetails(det);
			}
			list.clear();
			//System.out.println("executeBatch transactions....");
		}
		return detetail.getCreditCardDetailsId();
	}
	private Long executeBatchUpdateNpc(CreditCardDetails detetail)
	{
		if((detetail.getRuleStatus().equals(DroolRecordBO.NCF_FOUND_ERR) && detetail.getFlowStatus().equals(DroolRecordBO.FLOW_NPC_ERR)) || (detetail.getStatus() < 3)){
		detetail.setCreditCardDetailsId(getGndDAO().getCreditCardDetailIdSeq());
		
		}
		list.add(detetail);
		if(list.size() == listSize)
		{
			for(CreditCardDetails det:list)
			{
				Long recordId = null;
				if((det.getRuleStatus().equals(DroolRecordBO.NCF_FOUND_ERR) && det.getFlowStatus().equals(DroolRecordBO.FLOW_NPC_ERR)) || (det.getStatus() < 3) )
				{
				//	det.setCreditCardDetailsId(getGndDAO().getCreditCardDetailIdSeq());
				 recordId = getGndDAO().saveCreditCardDetails(det);
				
					if(det.getRuleStatus().equals(DroolRecordBO.NCF_FOUND_ERR) && det.getFlowStatus().equals(DroolRecordBO.FLOW_NPC_ERR))
					{
					RecordEventBO eventBO = new RecordEventBO();
					eventBO.setDescription(DroolRecordBO.NCF_FOUND_ERR);
					eventBO.setEventDate(new Date());
					eventBO.setEventId(RecordEventBO.EVENT_QC_RULES);
					eventBO.setRecordId(recordId);
					processEvent(eventBO);
					}
				}
				else
				{
					recordId = getGndDAO().saveCardDetails(det);
					
					RecordEventBO eventBO = new RecordEventBO();
					eventBO.setDescription(droolRecord.getFlowStatus());
					eventBO.setEventDate(new Date());
					eventBO.setEventId(RecordEventBO.EVENT_QC_RULES);
					eventBO.setRecordId(recordId);
					processEvent(eventBO);
				}
			}
			list.clear();
			//System.out.println("executeBatch transactions....");
		}
		return detetail.getCreditCardDetailsId();
	}
	@Transactional
	public void flushCreate()
	{
		String fileName = getDroolRecord().getFileName();
		String fileType[] = fileName.split("\\.");
		/*System.out.println(fileType[1]);
		System.out.println(fileName);*/
		if(list.size() >0)
		{
		if( (!(fileType[1]).equals("npc")))
		{
		/*System.out.println("inside flushcreate");*/
			for(CreditCardDetails det:list)
			{
				getGndDAO().saveCreditCardDetails(det);
				
			}
			
			list.clear();
			logger.info("Flushing transactions....");
			
		}
		else
		{
			flushUpdateNpc();
		}
		}
		getGndDAO().flush();
	}
	public void flushUpdate()
	{
		if(list.size() >0)
		{
		
			for(CreditCardDetails det:list)
			{
				getGndDAO().saveCardDetails(det);
			}
			
			list.clear();
			logger.info("Flushing transactions....");
			
		}
		getGndDAO().flush();
	}
	
	public void flushUpdateNpc()
	{
		if(list.size() >0)
		{
			Long recordId = null;
			for(CreditCardDetails det:list)
			{
				if((det.getRuleStatus().equals(DroolRecordBO.NCF_FOUND_ERR) && det.getFlowStatus().equals(DroolRecordBO.FLOW_NPC_ERR)) || (det.getStatus() < 3))
				{
					det.setCreditCardDetailsId(getGndDAO().getCreditCardDetailIdSeq());
					 recordId = getGndDAO().saveCreditCardDetails(det);
					 if(det.getRuleStatus().equals(DroolRecordBO.NCF_FOUND_ERR) && det.getFlowStatus().equals(DroolRecordBO.FLOW_NPC_ERR)){
						RecordEventBO eventBO = new RecordEventBO();
						eventBO.setDescription(DroolRecordBO.NCF_FOUND_ERR);
						eventBO.setEventDate(new Date());
						eventBO.setEventId(RecordEventBO.EVENT_QC_RULES);
						eventBO.setRecordId(recordId);
						processEvent(eventBO);
					 }
				}
				else
				{
				 recordId = getGndDAO().saveCardDetails(det);
									
					RecordEventBO eventBO = new RecordEventBO();
					eventBO.setDescription(droolRecord.getFlowStatus());
					eventBO.setEventDate(new Date());
					eventBO.setEventId(RecordEventBO.EVENT_QC_RULES);
					eventBO.setRecordId(recordId);
					processEvent(eventBO);
				}
				
			}
			list.clear();
			}
			getGndDAO().flush();
			
			logger.info("Flushing transactions....");
			
		
	}
	
	private Long executeBatchUpdateNcf(CreditCardDetails detetail)
	{
		//detetail.setCreditCardDetailsId(getGndDAO().getCreditCardDetailIdSeq());
		list.add(detetail);
		
		if(list.size() == listSize)
		{
			for(CreditCardDetails det:list)
			{
				getGndDAO().saveCardDetails(det);
				//saveRecordEvent(det.getCreditCardDetailsId(),(long) CardStateManagerService.CARD_STATUS_AWBASSIGNED,"CARD"+ RecordEventBO.getStatusInString(RecordEventBO.EVENT_AWB_ASSIGNED));
			}
			list.clear();
			//System.out.println("executeBatch transactions....");
		}
		return detetail.getCreditCardDetailsId();
	}
	public void flushUpdateNcf()
	{
		if(list.size() >0)
		{
		
			for(CreditCardDetails det:list)
			{
				getGndDAO().saveCardDetails(det);
			//	saveRecordEvent(det.getCreditCardDetailsId(),(long) CardStateManagerService.CARD_STATUS_AWBASSIGNED,"CARD"+ RecordEventBO.getStatusInString(RecordEventBO.EVENT_AWB_ASSIGNED));
			}
			
			list.clear();
			logger.info("Flushing transactions....");
			
		}
		getGndDAO().flush();
	}
}
