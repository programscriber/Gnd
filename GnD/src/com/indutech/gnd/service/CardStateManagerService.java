package com.indutech.gnd.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.indutech.gnd.bo.RecordEventBO;
import com.indutech.gnd.bo.drool.DroolRecordBO;
import com.indutech.gnd.dao.BranchDAOImpl;
//import com.indutech.gnd.controller.QCProcessController;
import com.indutech.gnd.dao.FileDAOImpl;
import com.indutech.gnd.dao.GNDDAOImpl;
import com.indutech.gnd.dao.ProductDAOImpl;
import com.indutech.gnd.dto.Branch;
import com.indutech.gnd.dto.CreditCardDetails;
import com.indutech.gnd.dto.RecordEvent;

import com.indutech.gnd.enumTypes.Status;

import com.indutech.gnd.records.Record;
import com.indutech.gnd.service.drool.CreditCardService;
import com.indutech.gnd.service.drool.DroolBootStrap;
import com.indutech.gnd.util.DataInitialization;

@Component("stateManager")
public class CardStateManagerService implements CardStateManager {

	Logger logger = Logger.getLogger(CardStateManagerService.class);
	common.Logger log = common.Logger.getLogger(CardStateManagerService.class);
	
	
  
	public static final int CARD_STATUS_PENDING = 0;
	public static final int CARD_STATUS_REJECT = 1;
	public static final int CARD_STATUS_HOLD = 2;
	public static final int CARD_STATUS_APPROVED = 3;
	public static final int CARD_STATUS_AWBASSIGNED = 4;
	public static final int CARD_STATUS_AUFCONVERTED = 5;
	public static final int CARD_STATUS_EMBOSSA_RECEIVED = 6;
	public static final int CARD_STATUS_MD_GENERATED  =7;
	public static final int CARD_STATUS_MAILER = 70; // TODO: no need of this

	public static final int CARD_STATUS_DISPATCH = 20;
	public static final int CARD_STATUS_DELIVER = 21;
	public static final int CARD_STATUS_RETURN = 22;
	public static final int CARD_STATUS_REDISPATCH = 23;
	public static final int CARD_STATUS_DESTROY = 24;
	public static final int CARD_STATUS_MARK_DELETE = 27;
	public static final int CARD_STATUS_RTO_GROUP_CARD_AWB_ASSIGN = 28;
	public static final int CARD_STATUS_RTO_INDIVIDUAL_CARD_AWB_ASSIGN = 29;
	
	

	public static final int CARD_STATUS_MANUALREJECT = 91;
	public static final int CARD_STATUS_MANUALHOLD = 92;
	public static final int CARD_STATUS_UNKNOWN = 99;
	
	
	public static final int CARD_STATUS_AWB_RANGE = 300;
	public static final int CARD_STATUS_AWB_COUNT = 17;

	public static final String CARD_STATUS_PENDING_STRING = "RECORD PENDING";
	public static final String CARD_STATUS_REJECT_STRING = "RECORD REJECTED";
	public static final String CARD_STATUS_HOLD_STRING = "RECORD HOLD";
	public static final String CARD_STATUS_APPROVED_STRING = "RECORD APPROVED";
	public static final String CARD_STATUS_AWBASSIGNED_STRING = "AWB ASSIGNED";
	public static final String CARD_STATUS_AUFCONVERTED_STRING = "AUF CONVERTED";
	public static final String CARD_STATUS_EMBOSSA_RECEIVED_STRING = "EMBOSSA RECEIVED";
	public static final String CARD_STATUS_MD_GENERATED_STRING  = "MD FILE GENERATED";
	public static final String CARD_STATUS_MAILER_STRING = "MAILER";
	public static final String CARD_STATUS_DISPATCH_STRING = "CARD PRODUCED";
	public static final String CARD_STATUS_DELIVER_STRING = "CARD DELIVERED";
	public static final String CARD_STATUS_RETURN_STRING = "CARD RETURNED";
	public static final String CARD_STATUS_REDISPATCH_STRING = "CARD REDISPATCHED";
	public static final String CARD_STATUS_DESTROY_STRING = "CARD DESTROYED";

	public static final String CARD_STATUS_MANUALREJECT_STRING = "RECORD MANUALLY REJECTED";
	public static final String CARD_STATUS_MANUALHOLD_STRING = "RECORD MANUALLY HOLD";
	public static final String CARD_STATUS_UNKNOWN_STRING = "RECORD UNKNOWN";
	
	public static final String RECORD_VALID_STATUS_STRING = "APPROVED";
	
	public static final String CARD_STATUS_RTO_CARD_AWB_ASSIGN_STRING = "CardAWB changed";
	Status stat = Status.valueOf("ACTIVE");
	String status = stat.getStatus();
	@Autowired
	private GNDDAOImpl gndDAO;
	
	@Autowired
	private DroolBootStrap droolBootStrapService;
	
	@Autowired
	private CreditCardService droolService;
	
	@Autowired
	private ProductDAOImpl productDAO;
	
	@Autowired
	private Record rec;
	
	@Autowired
	private FileDAOImpl fileDAO;
	
	@Autowired
	private FileConvertServiceImpl fileConvertServiceImpl;
	
	@Autowired
	private DroolRecordBO droolRecord;
	
	@Autowired
	private AUFFormatCheck aufformatcheck;
	
	@Autowired
	private BranchDAOImpl branchDAO;
	
	
	
	

	public ProductDAOImpl getProductDAO() {
		return productDAO;
	}

	public void setProductDAO(ProductDAOImpl productDAO) {
		this.productDAO = productDAO;
	}

	public BranchDAOImpl getBranchDAO() {
		return branchDAO;
	}

	public void setBranchDAO(BranchDAOImpl branchDAO) {
		this.branchDAO = branchDAO;
	}

	public AUFFormatCheck getAufformatcheck() {
		return aufformatcheck;
	}

	public void setAufformatcheck(AUFFormatCheck aufformatcheck) {
		this.aufformatcheck = aufformatcheck;
	}

	public DroolRecordBO getDroolRecord() {
		return droolRecord;
	}

	public void setDroolRecord(DroolRecordBO droolRecord) {
		this.droolRecord = droolRecord;
	}

	public FileConvertServiceImpl getFileConvertServiceImpl() {
		return fileConvertServiceImpl;
	}

	public void setFileConvertServiceImpl(
			FileConvertServiceImpl fileConvertServiceImpl) {
		this.fileConvertServiceImpl = fileConvertServiceImpl;
	}

	public FileDAOImpl getFileDAO() {
		return fileDAO;
	}

	public void setFileDAO(FileDAOImpl fileDAO) {
		this.fileDAO = fileDAO;
	}

	public Record getRec() {
		return rec;
	}

	public void setRec(Record rec) {
		this.rec = rec;
	}

	public CreditCardService getDroolService() {
		return droolService;
	}

	public void setDroolService(CreditCardService droolService) {
		this.droolService = droolService;
	}

	public DroolBootStrap getDroolBootStrapService() {
		return droolBootStrapService;
	}

	public void setDroolBootStrapService(DroolBootStrap droolBootStrapService) {
		this.droolBootStrapService = droolBootStrapService;
	}

	public GNDDAOImpl getGndDAO() {
		return gndDAO;
	}

	public void setGndDAO(GNDDAOImpl gndDAO) {
		this.gndDAO = gndDAO;
	}

	@Override
	@Transactional
	public void toStateHold(String creditCardDetailsId, String remark) {

		try {
			for (String val : creditCardDetailsId.split(",")) {
			CreditCardDetails details = getGndDAO().getCreditCardDetails(
					Long.valueOf(val));

			// RecordStatus holdstat = RecordStatus.valueOf("HOLD");
			// String holdStatus = holdstat.getRecordStatus();
			// RecordStatus manualHold = RecordStatus.valueOf("MANUAL_HOLD");
			// String manualHoldStatus = manualHold.getRecordStatus();

			int curStatus = details.getStatus().intValue();

			switch (curStatus) {
			case CARD_STATUS_PENDING:
			case CARD_STATUS_APPROVED:
			case CARD_STATUS_AWBASSIGNED:
			case CARD_STATUS_AUFCONVERTED:

				// details.setStatus(Long.parseLong(holdStatus));
				details.setStatus((long) CARD_STATUS_HOLD);
				details.setFlowStatus(DroolRecordBO.FLOW_MANUAL_HOLD);
				details.setRuleStatus(DroolRecordBO.RULE_MANUAL_HOLD);
				getGndDAO().saveCardDetails(details);
				RecordEvent event = new RecordEvent();
				event.setEventDate(new Date());
				event.setDescription(remark);
				// event.setEventId(Long.parseLong(manualHoldStatus));
				event.setEventId((long) CARD_STATUS_MANUALHOLD);
				event.setRecordId(details.getCreditCardDetailsId());

				getGndDAO().saveRecordEvent(event);
				break;

			default:
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}

	}

	@Override
	@Transactional
	public void toStateReject(String creditCardDetailsId, String remark) {

		try {
			if(remark!=""){
			for (String val : creditCardDetailsId.split(",")) {
				logger.info("the value is " + val);
			
				CreditCardDetails details = getGndDAO().getCreditCardDetails(Long.valueOf(val)
						);
				// RecordStatus stat2 = RecordStatus.valueOf("REJECT");
				// String rejectedStatus = stat2.getRecordStatus();
				// RecordStatus manualReject =
				// RecordStatus.valueOf("MANUAL_REJECT");
				// String manualRejectStatus = stat2.getRecordStatus();

				int curStatus = details.getStatus().intValue();

				switch (curStatus) {
				case CARD_STATUS_PENDING:
				case CARD_STATUS_HOLD:
				case CARD_STATUS_APPROVED:
				case CARD_STATUS_AWBASSIGNED:
				case CARD_STATUS_AUFCONVERTED:

					// details.setStatus(Long.parseLong(rejectedStatus));
					details.setStatus((long) CARD_STATUS_REJECT);
					details.setFlowStatus(DroolRecordBO.FLOW_MANUAL_REJECT);
					details.setRuleStatus(DroolRecordBO.RULE_MANUAL_REJECT);
					getGndDAO().saveCardDetails(details);

					RecordEvent event = new RecordEvent();
					event.setEventDate(new Date());
					event.setDescription(remark);
					// event.setEventId(Long.parseLong(manualRejectStatus));
					event.setEventId((long) CARD_STATUS_MANUALREJECT);
					event.setRecordId(details.getCreditCardDetailsId());

					getGndDAO().saveRecordEvent(event);
					break;

				default:
				}
			}
		} 
			}catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}

	}
	
	@Override
	@Transactional
	public void toStatecardDispatch(String creditCardDetailsId, String remark) {
		try {
			for (String val : creditCardDetailsId.split(",")) {
			CreditCardDetails details = getGndDAO().getCreditCardDetails(
					Long.valueOf(val));

			int curStatus = details.getStatus().intValue();

			switch (curStatus) {
			case CARD_STATUS_DISPATCH:
			case CARD_STATUS_DELIVER:
			case CARD_STATUS_RETURN:
			case CARD_STATUS_REDISPATCH:
			case CARD_STATUS_DESTROY:
				break;
			case CARD_STATUS_EMBOSSA_RECEIVED:
			case CARD_STATUS_MD_GENERATED:
			
				details.setStatus((long) CARD_STATUS_DISPATCH);
				details.setCreatedDate(new Date());
				getGndDAO().saveCardDetails(details);

				RecordEvent event = new RecordEvent();
				event.setEventDate(new Date());
				event.setDescription(remark);
				event.setEventId((long) CARD_STATUS_DISPATCH);
				event.setRecordId(details.getCreditCardDetailsId());

				getGndDAO().saveRecordEvent(event);
				break;

			default:
				
			}
		} }catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional
	public void toStatecardDeliver(String creditCardDetailsId, String remark) {

		try {
			for (String val : creditCardDetailsId.split(",")) {
			CreditCardDetails details = getGndDAO().getCreditCardDetails(
					Long.valueOf(val));

			int curStatus = details.getStatus().intValue();

			switch (curStatus) {
			case CARD_STATUS_DELIVER:
			case CARD_STATUS_RETURN:
			case CARD_STATUS_REDISPATCH:
			case CARD_STATUS_DESTROY:
				break;
			case CARD_STATUS_DISPATCH:

				details.setStatus((long) CARD_STATUS_DELIVER);
				details.setCreatedDate(new Date());
				getGndDAO().saveCardDetails(details);

				RecordEvent event = new RecordEvent();
				event.setEventDate(new Date());
				event.setDescription(remark);
				event.setEventId((long) CARD_STATUS_DELIVER);
				event.setRecordId(details.getCreditCardDetailsId());

				getGndDAO().saveRecordEvent(event);
				break;

			default:
				// TODO error message in UI - not allowed state
			}
		} }catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}

	}

	@Override
	@Transactional
	public void toStatecardRTO(String creditCardDetailsId, String remark) {

		try {
			for (String val : creditCardDetailsId.split(",")) {
			CreditCardDetails details = getGndDAO().getCreditCardDetails(
					Long.valueOf(val));

			int curStatus = details.getStatus().intValue();

			switch (curStatus) {
			case CARD_STATUS_RETURN:
			case CARD_STATUS_DESTROY:
				break;
			case CARD_STATUS_DELIVER:
			case CARD_STATUS_REDISPATCH:

				details.setStatus((long) CARD_STATUS_RETURN);
				details.setCreatedDate(new Date());
				getGndDAO().saveCardDetails(details);

				RecordEvent event = new RecordEvent();
				event.setEventDate(new Date());
				event.setDescription(remark);
				event.setEventId((long) CARD_STATUS_RETURN);
				event.setRecordId(details.getCreditCardDetailsId());

				getGndDAO().saveRecordEvent(event);
				break;

			default:
				// TODO error message in UI - not allowed state
			}
		} }catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}

	}

	@Override
	@Transactional
	public void toStatecardRedispatch(String creditCardDetailsId, String remark) {

		try {
			for (String val : creditCardDetailsId.split(",")) {
			CreditCardDetails details = getGndDAO().getCreditCardDetails(
					Long.valueOf(val));

			int curStatus = details.getStatus().intValue();

			switch (curStatus) {
			
			case CARD_STATUS_REDISPATCH:
			case CARD_STATUS_DESTROY:
				break;
			case CARD_STATUS_RTO_GROUP_CARD_AWB_ASSIGN:
			case CARD_STATUS_RTO_INDIVIDUAL_CARD_AWB_ASSIGN :
				details.setStatus((long) CARD_STATUS_REDISPATCH);
				details.setCreatedDate(new Date());
				getGndDAO().saveCardDetails(details);

				RecordEvent event = new RecordEvent();
				event.setEventDate(new Date());
				event.setDescription(remark);
				event.setEventId((long) CARD_STATUS_REDISPATCH);
				event.setRecordId(details.getCreditCardDetailsId());

				getGndDAO().saveRecordEvent(event);
				break;

			default:
				// TODO error message in UI - not allowed state
			}

		} }catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	@Transactional
	public void toCardManualDestory(String creditCardDetailsId, String remark) {

		try {
			for (String val : creditCardDetailsId.split(",")) {
			CreditCardDetails details = getGndDAO().getCreditCardDetails(
					Long.valueOf(val));

			int curStatus = details.getStatus().intValue();

			switch (curStatus) {
			case CARD_STATUS_RETURN:
				details.setStatus((long) CARD_STATUS_DESTROY);
				details.setCreatedDate(new Date());
				getGndDAO().saveCardDetails(details);

				RecordEvent event = new RecordEvent();
				event.setEventDate(new Date());
				event.setDescription(remark);
				event.setEventId((long) CARD_STATUS_DESTROY);
				event.setRecordId(details.getCreditCardDetailsId());

				getGndDAO().saveRecordEvent(event);

				break;
			default:
				// TODO error message in UI - not allowed state
			}
		} }catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getExternalStatus(int intStatus) {
		int extStatus;
		switch (intStatus) {
		case CARD_STATUS_PENDING:
		case CARD_STATUS_REJECT:
		case CARD_STATUS_HOLD:
			extStatus = intStatus;
			break;

		case CARD_STATUS_APPROVED:
		case CARD_STATUS_AWBASSIGNED:
		case CARD_STATUS_AUFCONVERTED:
			extStatus = CARD_STATUS_APPROVED;
			break;

		default:
			extStatus = CARD_STATUS_UNKNOWN;
		}

		return extStatus;
	}
	
	@Override
	@Transactional
	public void changeHoldStateToReject() {
		try {
			logger.info("Inside changeHoldStateToReject ");
			List<CreditCardDetails> details = getGndDAO()
					.getHoldStateCreditCardDetails();
			logger.info("CreditCardDetails record Size" + details.size());
			for (CreditCardDetails creditCardDetails : details) {
				creditCardDetails.setStatus(new Long(CARD_STATUS_REJECT));
				creditCardDetails.setCreatedDate(new Date());
				getGndDAO().updateCardDetails(creditCardDetails);
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();

		}

	}
	
	@Override
	@Transactional
	public void changeRejectStateToMarkDelete() {
		try {

			logger.info("Inside changeRejectStateToMarkDelete ");
			List<CreditCardDetails> details = getGndDAO()
					.getRejectedStateCreditCardDetails();
			logger.info("CreditCardDetails record Size" + details.size());
			for (CreditCardDetails creditCardDetails : details) {
				creditCardDetails.setStatus(new Long(CARD_STATUS_MARK_DELETE));
				creditCardDetails.setCreatedDate(new Date());
				getGndDAO().updateCardDetails(creditCardDetails);
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();

		}

	}
	
	
	@Override
	@Transactional
	public void toCardRejectReprocess(String creditCardDetailsId, String remark) {
		try {
			String filename = null;
			getDroolBootStrapService().fire();
			logger.info("start reject reprocess");
			for(String val : creditCardDetailsId.split(",")) {
				CreditCardDetails details = getGndDAO().getCreditCardDetails(Long.valueOf(val));
				int curStatus = details.getStatus().intValue();
				logger.info("status is : "+curStatus);
				switch (curStatus) {
				case CARD_STATUS_REJECT:					
					DroolRecordBO droolRecord = buildDroolRecordBO(details);
					droolRecord.setFlowStatus(DroolRecordBO.FLOW_PASS);
					getDroolRecord().setFileName(getFileDAO().getFileName(details.getFileId()));
					getDroolRecord().setFileId(details.getFileId());
					filename = getDroolRecord().getFileName();
					String split[] = filename.split("\\.");
					logger.info("filename is : "+filename);
					if(!split[1].equalsIgnoreCase("ncf")) {
						droolRecord = getDroolBootStrapService().bootStrap(droolRecord);
						if(droolRecord.getFlowStatus().equals(DroolRecordBO.FLOW_PASS)) {
							processHold(droolRecord, details, remark, filename);
						}
						else {
							droolRecord.setStatus((long)CardStateManagerService.CARD_STATUS_REJECT);
							storeDB(droolRecord, remark, details);
							
						}
					}
					else {
						droolRecord = getDroolBootStrapService().bootStrapCNF(droolRecord);
						if(droolRecord.getFlowStatus().equals(DroolRecordBO.FLOW_PASS)) {
							processHold(droolRecord, details, remark, filename);
						}
						else {
							droolRecord.setStatus((long)CardStateManagerService.CARD_STATUS_REJECT);
							storeDB(droolRecord, remark, details);			
							
						}
					}
					
					break;

				default:
					break;
				}
			}	
			String filetype[] =filename.split("\\.");
			if(!filetype[1].equalsIgnoreCase("ncf") && !filetype[1].equalsIgnoreCase("npc")) {
				getRec().assignCardAWB();
				
			}
			
			else if(filetype[1].equalsIgnoreCase("ncf")) {
				getRec().ncfLCPCCardAWBAssign();
			}
			getRec().assignPinAWB();
			getAufformatcheck().aufFormatCheckAndProcess();
//			getFileConvertServiceImpl().convertTxt();
		} catch(Exception e) {
			
			e.printStackTrace();
		}
		
	}
	
	private void storeDB(DroolRecordBO droolRecord, String remark, CreditCardDetails details) {
		
		details.setStatus(droolRecord.getStatus());
		details.setFlowStatus(droolRecord.getFlowStatus());
		details.setRuleStatus(droolRecord.getRuleStatus());
		details.setCreatedDate(new Date());
		getGndDAO().saveCardDetails(details);					
		RecordEvent event = new RecordEvent();
		event.setEventDate(new Date());
		event.setDescription(details.getRuleStatus()+" "+remark);
		event.setEventId((long) RecordEventBO.EVENT_QC_RULES);
		event.setRecordId(details.getCreditCardDetailsId());
		getGndDAO().saveRecordEvent(event);	
		
		
	}

	@Override
	@Transactional
	public void toCardHoldReprocess(String creditCardDetailsId, String remark) {
		try {
			String filename = null;
			getDroolBootStrapService().fire();
			for (String val : creditCardDetailsId.split(",")) {
				CreditCardDetails details = getGndDAO().getCreditCardDetails(Long.valueOf(val));
			int curStatus = details.getStatus().intValue();
			switch (curStatus) {
				case CARD_STATUS_HOLD:
					
					DroolRecordBO droolRecord = buildDroolRecordBO(details);
					droolRecord.setFlowStatus(DroolRecordBO.FLOW_PASS);
					getDroolRecord().setFileName(getFileDAO().getFileName(details.getFileId()));
					getDroolRecord().setFileId(details.getFileId());
					filename = getDroolRecord().getFileName();
					processHold(droolRecord, details, remark, filename);
												
				
				break;

			default:
			}

		} 
			String filetype[] =filename.split("\\.");
			if(!filetype[1].equalsIgnoreCase("ncf") && !filetype[1].equalsIgnoreCase("npc")) {
				getRec().assignCardAWB();				
			}			
			else if(filetype[1].equalsIgnoreCase("ncf")) {
				getRec().ncfLCPCCardAWBAssign();
			}
			getRec().assignPinAWB();
			getAufformatcheck().aufFormatCheckAndProcess();
//			getFileConvertServiceImpl().convertTxt();
		}catch (Exception e) {
			e.printStackTrace();
		}

		
	}
	
	public void processHold(DroolRecordBO droolRecord, CreditCardDetails details, String remark, String filename) {
		try {
			String split[] = filename.split("\\.");
			logger.info("In hold record processing");
			droolRecord = getDroolService().getDBDroolRecordBO(droolRecord);
			droolRecord = getDroolBootStrapService().bootStrapDBDrools(droolRecord);
			if(droolRecord.getFlowStatus().equals(DroolRecordBO.FLOW_ERROR)) {
				droolRecord.setStatus((long) CardStateManagerService.CARD_STATUS_REJECT);
			}
			else if(droolRecord.getFlowStatus().equals(DroolRecordBO.FLOW_HOLD)) {
				droolRecord.setStatus((long) CardStateManagerService.CARD_STATUS_HOLD);
			}
			
			else {
				droolRecord.setStatus((long) CardStateManagerService.CARD_STATUS_APPROVED);
				droolRecord.setRuleStatus(DroolRecordBO.RULE_APPROVED_STATUS);
			}
			
			logger.info("record status is : "+droolRecord.getStatus());
			logger.info("finally record rule status is : "+droolRecord.getRuleStatus());
			
			
				
//				if(droolRecord.getStatus() == (long) CARD_STATUS_APPROVED &&  split[1].equalsIgnoreCase("npc")) {
//					processNPC(details);
//					return;
//				}
				if(!split[1].equalsIgnoreCase("npc")) {
					List<Branch> list = getBranchDAO().getBranch(details.getIssueBranchCode(), details.getBankId());
					List<Branch> list1 = getBranchDAO().getBranch(details.getHomeBranchCode(), details.getBankId());
					if(list.size()>0 && list1.size()>0)
					{
					if(!details.getHomeBranchCode().equals(details.getIssueBranchCode())) {
					if(!(filename.charAt(1) == 'W') && !filename.contains("ncf")) {
					
						if(list != null && list.size() > 0) {
							Branch issueBranch = (Branch) list.get(0);
							Branch homeBranch= (Branch) list1.get(0);
							Integer issueBranchIsNonCardIssue = issueBranch.getIsNonCardIssueBranch();
							Integer homeBranchIsNonCardIssue = homeBranch.getIsNonCardIssueBranch();
							if(issueBranchIsNonCardIssue == BranchService.NON_CARD_ISSUE_BRANCH_ACTIVE) {
								details.setProcessedBranchCode(details.getIssueBranchCode());
							}
							else if(homeBranchIsNonCardIssue == BranchService.NON_CARD_ISSUE_BRANCH_ACTIVE){
								details.setProcessedBranchCode(details.getHomeBranchCode());
							}
							else
							{
								details.setFlowStatus(DroolRecordBO.FLOW_ERROR);
								details.setStatus((long) CardStateManagerService.CARD_STATUS_REJECT);
								details.setRuleStatus(DroolRecordBO.HOMEBRANCH_AND_ISSUEBRANCH_ISNONCARD);
								getGndDAO().saveCardDetails(details);	
								RecordEvent event = new RecordEvent();
								event.setEventDate(new Date());
								
								event.setEventId((long) RecordEventBO.EVENT_QC_RULES);
								event.setRecordId(details.getCreditCardDetailsId());
								getGndDAO().saveRecordEvent(event);
								return;
								//reject++;

							}
						}
					} else if(filename.charAt(1) == 'W' && !filename.contains("ncf")) {
					//	List<Branch> list = getBranchDAO().getBranch(details.getHomeBranchCode(), details.getBankId());
						if(list != null && list.size() > 0) {
							Branch issueBranch = (Branch) list.get(0);
							Branch homeBranch= (Branch) list1.get(0);
							Integer issueBranchIsNonCardIssue = issueBranch.getIsNonCardIssueBranch();
							Integer homeBranchIsNonCardIssue = homeBranch.getIsNonCardIssueBranch();
							if(homeBranchIsNonCardIssue == BranchService.NON_CARD_ISSUE_BRANCH_ACTIVE) {
								details.setProcessedBranchCode(details.getHomeBranchCode());
							}
							else if(issueBranchIsNonCardIssue == BranchService.NON_CARD_ISSUE_BRANCH_ACTIVE) {
								details.setProcessedBranchCode(details.getIssueBranchCode());
							}
							else
							{
								details.setFlowStatus(DroolRecordBO.FLOW_ERROR);
								details.setStatus((long) CardStateManagerService.CARD_STATUS_REJECT);
								details.setRuleStatus(DroolRecordBO.HOMEBRANCH_AND_ISSUEBRANCH_ISNONCARD);
								getGndDAO().saveCardDetails(details);	
								RecordEvent event = new RecordEvent();
								event.setEventDate(new Date());
							
								event.setEventId((long) RecordEventBO.EVENT_QC_RULES);
								event.setRecordId(details.getCreditCardDetailsId());
								getGndDAO().saveRecordEvent(event);
								return;
							//	reject++;
							}
						}
					}
					}
						else
						{
							//System.out.println("both home and issue branch equal");
							Branch homeBranch= (Branch) list1.get(0);
							
							Integer homeBranchCodeIsNonCardIssue = homeBranch.getIsNonCardIssueBranch();
							
								if(homeBranchCodeIsNonCardIssue == BranchService.NON_CARD_ISSUE_BRANCH_INACTIVE) {
									details.setFlowStatus(DroolRecordBO.FLOW_ERROR);
									details.setStatus((long) CardStateManagerService.CARD_STATUS_REJECT);
									if(!filename.contains("ncf")){
									details.setRuleStatus(DroolRecordBO.HOMEBRANCH_AND_ISSUEBRANCH_ISNONCARD);
									}else{
										details.setRuleStatus(DroolRecordBO.HOMEBRANCHCODEISNONCARDISSUE);
									}
									getGndDAO().saveCardDetails(details);	
									RecordEvent event = new RecordEvent();
									event.setEventDate(new Date());
									
									event.setEventId((long) RecordEventBO.EVENT_QC_RULES);
									event.setRecordId(details.getCreditCardDetailsId());
									getGndDAO().saveRecordEvent(event);
									return;
									//reject++;
								}
								else
								{
									//System.out.println("setting processed branch");
									details.setProcessedBranchCode(details.getIssueBranchCode());
								}
			
							
						}
					}
			
					else 
					{
						details.setFlowStatus(DroolRecordBO.FLOW_HOLD);
						details.setStatus((long) CardStateManagerService.CARD_STATUS_HOLD);
						if(list1.size() == 0 && list.size() == 0){
							details.setRuleStatus(DroolRecordBO.BOTH_HOME_AND_ISSUE_CONTENT_ERR);
						}
						else if(list1.size() == 0)
						{
						details.setRuleStatus(DroolRecordBO.HOMEBRANCHCODE_INVALID_ERR);
						}else {
							details.setRuleStatus(DroolRecordBO.ISSUEBRANCHCODE_INVALID_ERR);
						}
						getGndDAO().saveCardDetails(details);	
						RecordEvent event = new RecordEvent();
						event.setEventDate(new Date());
						
						event.setEventId((long) RecordEventBO.EVENT_QC_HOLD);
						event.setRecordId(details.getCreditCardDetailsId());
						getGndDAO().saveRecordEvent(event);
						return;
					}
						
					
					Map<Long, Map<String, Branch>> bankList = DataInitialization.getInstance().getBranchInfo();
					if(bankList != null && bankList.size() > 0 ) {
						Map<String, Branch> branchList = bankList.get(droolRecord.getBankId());
						if(branchList != null && branchList.size() > 0) {
							Branch branch = (Branch) branchList.get(details.getProcessedBranchCode());
							if(branch != null) {
								if(branch.getBankId() == droolRecord.getBankId() && branch.getShortCode().equals(details.getProcessedBranchCode()) && branch.getStatus() == Long.parseLong(status)) {
									if(branch.getLcpcName() != null && !branch.getLcpcName().trim().isEmpty()) {
										details.setLcpcBranch(branch.getLcpcName());
									}
								}
							}
						}
					}
					details.setStatus(droolRecord.getStatus());
					details.setFlowStatus(droolRecord.getFlowStatus());
					details.setRuleStatus(droolRecord.getRuleStatus());
					logger.info("the given record is not npc");
					details.setCreatedDate(new Date());
					getGndDAO().saveCardDetails(details);	
				}
				RecordEvent event = new RecordEvent();
				event.setEventDate(new Date());
				event.setDescription(details.getRuleStatus()+" "+remark);
				event.setEventId((long) RecordEventBO.EVENT_QC_RULES);
				event.setRecordId(details.getCreditCardDetailsId());
				getGndDAO().saveRecordEvent(event);		
				
				
			} catch(Exception e) {
			logger.error(e);
				e.printStackTrace();
			}
		}
	
		public void processNPC(CreditCardDetails details) {
			if(details.getStatus() == (long)CardStateManagerService.CARD_STATUS_APPROVED) {
				List<CreditCardDetails> cardDetails = getGndDAO().getNCFRecord(details.getPrimaryAcctNo().trim(), details.getProduct().trim());
				
				if(cardDetails.size() > 0) {
					Iterator<CreditCardDetails> itr = cardDetails.iterator();
					while(itr.hasNext()) {
						CreditCardDetails ncfCardDetails = (CreditCardDetails) itr.next();						
						details.setCreditCardDetailsId(ncfCardDetails.getCreditCardDetailsId());
						details.setRsn(ncfCardDetails.getRsn());
						details.setStatus(ncfCardDetails.getStatus());
						details.setCreatedDate(new Date());
						getGndDAO().updateCardDetails(details);
						
						RecordEvent event = new RecordEvent();
						event.setDescription(droolRecord.getFlowStatus());
						event.setEventDate(new Date());
						event.setEventId(RecordEventBO.EVENT_QC_RULES);
						event.setRecordId(details.getCreditCardDetailsId());
						getGndDAO().saveRecordEvent(event);
					}
				}
				else {
					details.setStatus((long) CardStateManagerService.CARD_STATUS_HOLD);
					details.setFlowStatus(DroolRecordBO.FLOW_NPC_ERR);
					details.setRuleStatus(DroolRecordBO.NCF_FOUND_ERR);
					details.setCreatedDate(new Date());
					Long recordId = gndDAO.saveCardDetails(details);
					
					RecordEvent event = new RecordEvent();
					event.setDescription(DroolRecordBO.NCF_FOUND_ERR);
					event.setEventDate(new Date());
					event.setEventId(RecordEventBO.EVENT_QC_RULES);
					event.setRecordId(recordId);
					getGndDAO().saveRecordEvent(event);
				}
			}
			
		}

		public static String buildStatus(Long status) {
			String statusName = null;
			if (status == CardStateManagerService.CARD_STATUS_REJECT)
				statusName = CardStateManagerService.CARD_STATUS_REJECT_STRING;
			if (status == CardStateManagerService.CARD_STATUS_HOLD)
				statusName = CardStateManagerService.CARD_STATUS_HOLD_STRING;
			if (status == CardStateManagerService.CARD_STATUS_APPROVED)
				statusName = CardStateManagerService.CARD_STATUS_APPROVED_STRING;
			if (status == CardStateManagerService.CARD_STATUS_AWBASSIGNED)
				statusName = CardStateManagerService.CARD_STATUS_AWBASSIGNED_STRING;
			if (status == CardStateManagerService.CARD_STATUS_AUFCONVERTED)
				statusName = CardStateManagerService.CARD_STATUS_AUFCONVERTED_STRING;
			if (status == CardStateManagerService.CARD_STATUS_EMBOSSA_RECEIVED)
				statusName = CardStateManagerService.CARD_STATUS_EMBOSSA_RECEIVED_STRING;
			if(status == CardStateManagerService.CARD_STATUS_MD_GENERATED)
				statusName = CardStateManagerService.CARD_STATUS_MD_GENERATED_STRING;
			if (status == CardStateManagerService.CARD_STATUS_DISPATCH)
				statusName = CardStateManagerService.CARD_STATUS_DISPATCH_STRING;
			if (status == CardStateManagerService.CARD_STATUS_DELIVER)
				statusName = CardStateManagerService.CARD_STATUS_DELIVER_STRING;
			if (status == CardStateManagerService.CARD_STATUS_RETURN)
				statusName = CardStateManagerService.CARD_STATUS_RETURN_STRING;
			if (status == CardStateManagerService.CARD_STATUS_REDISPATCH)
				statusName = CardStateManagerService.CARD_STATUS_REDISPATCH_STRING;
			if (status == CardStateManagerService.CARD_STATUS_DESTROY)
				statusName = CardStateManagerService.CARD_STATUS_DESTROY_STRING;
			if (status == CardStateManagerService.CARD_STATUS_MANUALREJECT)
				statusName = CardStateManagerService.CARD_STATUS_MANUALREJECT_STRING;
			if (status == CardStateManagerService.CARD_STATUS_MANUALHOLD)
				statusName = CardStateManagerService.CARD_STATUS_MANUALHOLD_STRING;
			if (status == CardStateManagerService.CARD_STATUS_UNKNOWN)
				statusName = CardStateManagerService.CARD_STATUS_UNKNOWN_STRING;
			if(status == CardStateManagerService.CARD_STATUS_RTO_GROUP_CARD_AWB_ASSIGN)
				statusName = CardStateManagerService.CARD_STATUS_RTO_CARD_AWB_ASSIGN_STRING;
			if(status == CardStateManagerService.CARD_STATUS_RTO_INDIVIDUAL_CARD_AWB_ASSIGN)
				statusName = CardStateManagerService.CARD_STATUS_RTO_CARD_AWB_ASSIGN_STRING;
	
			return statusName;
	}
	
	public DroolRecordBO buildDroolRecordBO(CreditCardDetails details) {
		String dateOfBirth = null;
		String registrationDate = null;
		SimpleDateFormat sdf2 = new SimpleDateFormat("ddMMyyyy");
		SimpleDateFormat sdf3 = new SimpleDateFormat("dd/MM/yyyy");
		if(details.getDateOfBirth() != null) {
			dateOfBirth = sdf2.format(details.getDateOfBirth());
			logger.info("date of birth is "+dateOfBirth);
		}
		if(details.getRegistrationDate() != null) {
			registrationDate = sdf3.format(details.getRegistrationDate());
			logger.info("registration date is : "+registrationDate);
		}
		DroolRecordBO droolRecord = new DroolRecordBO();
		
		droolRecord.setCreditCardDetailsId(details.getCreditCardDetailsId());
		droolRecord.setSerialNo(details.getSerialNo());
		droolRecord.setInstitutionId(details.getInstitutionId());
		droolRecord.setCustomerId(details.getCustomerId());
		droolRecord.setHomeBranchCode(details.getHomeBranchCode());
		droolRecord.setIssueBranchCode(details.getIssueBranchCode());
		droolRecord.setPrimaryAcctNo(details.getPrimaryAcctNo());
		droolRecord.setPrimaryAcctType(details.getPrimaryAcctType());
		droolRecord.setPrimaryAcctSurviour(details.getPrimaryAcctSurviour());
		droolRecord.setSecondaryAcct1(details.getSecondaryAcct1());
		droolRecord.setSecondaryAcct1Type(details.getSecondaryAcct1Type());
		droolRecord.setSecondaryAcctSurviour(details.getSecondaryAcctSurviour());
		droolRecord.setSecondaryAcct2(details.getSecondaryAcct2());
		droolRecord.setSecondaryAcct2Type(details.getSecondaryAcct2Type());
		droolRecord.setSecondarySurviour(details.getSecondarySurviour());
		droolRecord.setEmbossName(details.getEmbossName());
		droolRecord.setCustomerFirstName(details.getCustomerFirstName());
		droolRecord.setCustomerMiddleName(details.getCustomerMiddleName());
		droolRecord.setCustomerSurName(details.getCustomerSurName());
		droolRecord.setAddr1(details.getAddr1());
		droolRecord.setAddr2(details.getAddr2());
		droolRecord.setAddr3(details.getAddr3());
		droolRecord.setAddr4(details.getAddr4());
		droolRecord.setCity(details.getCity());
		droolRecord.setPin(details.getPin());
		droolRecord.setOfficePhone(details.getOfficePhone());
		droolRecord.setHomePhone(details.getHomePhone());
		droolRecord.setProduct(details.getProduct());
		droolRecord.setCardStatus(details.getCardStatus());
		droolRecord.setRegistrationDate(details.getRegistrationDate());
		droolRecord.setFax(details.getFax());
		droolRecord.setEmail(details.getEmail());
		droolRecord.setFathersFirstName(details.getFathersFirstName());
		droolRecord.setMotherMaidenName(details.getMotherMaidenName());
		droolRecord.setDateOfBirth(details.getDateOfBirth());
		droolRecord.setYearOfPassingSSC(details.getYearOfPassingSSC());
		droolRecord.setYearOfMarriage(details.getYearOfMarriage());
		droolRecord.setFourthLinePrintingData(details.getFourthLinePrintingData());
		droolRecord.setIsdCode(details.getIsdCode());
		droolRecord.setMobileNo(details.getMobileNo());
		droolRecord.setPrimaryAccountProductCode(details.getPrimaryAccountProductCode());
		droolRecord.setSecondaryAccount1ProductCode(details.getSecondaryAccount1ProductCode());
		droolRecord.setSecondaryAccount2ProductCode(details.getSecondaryAccount2ProductCode());
		droolRecord.setHomeBranchCircleCode(details.getHomeBranchCircleCode());
		droolRecord.setIssueBranchCircleCode(details.getIssueBranchCircleCode());
		droolRecord.setStatus(details.getStatus());
		droolRecord.setFlowStatus(details.getFlowStatus());
		droolRecord.setRuleStatus(details.getRuleStatus());
		droolRecord.setFileId(details.getFileId());
		droolRecord.setCompositeValid(getDroolService().isValidCompositeKey(droolRecord.getCustomerId(), droolRecord.getPrimaryAcctNo(), droolRecord.getProduct()));
		droolRecord.setDobValid(getDroolService().isDOBValidRange_rule_5y1(dateOfBirth));
		droolRecord.setInviduality(details.getIsIndividual());
		droolRecord.setPinStatus(details.getPinstatus());
		droolRecord.setRegistrationDateValid(getDroolService().isYrRegistrationValid(registrationDate));
		droolRecord.setIsVIP(details.getIsVIP());
		droolRecord.setBankId(details.getBankId());
		return droolRecord;
	}
	
	public static String buildRecordStatus(Long status) {
		String statusName = null;
		if (status == CardStateManagerService.CARD_STATUS_REJECT)
			statusName = CardStateManagerService.CARD_STATUS_REJECT_STRING;
		if (status == CardStateManagerService.CARD_STATUS_HOLD)
			statusName = CardStateManagerService.CARD_STATUS_HOLD_STRING;
		if (status == CardStateManagerService.CARD_STATUS_APPROVED)
			statusName = CardStateManagerService.CARD_STATUS_APPROVED_STRING;
		if (status == CardStateManagerService.CARD_STATUS_AWBASSIGNED)
			statusName = CardStateManagerService.CARD_STATUS_AWBASSIGNED_STRING;
		if (status == CardStateManagerService.CARD_STATUS_AUFCONVERTED)
			statusName = CardStateManagerService.CARD_STATUS_AUFCONVERTED_STRING;
		if (status == CardStateManagerService.CARD_STATUS_EMBOSSA_RECEIVED)
			statusName = CardStateManagerService.CARD_STATUS_EMBOSSA_RECEIVED_STRING;
		if(status == CardStateManagerService.CARD_STATUS_MD_GENERATED)
			statusName = CardStateManagerService.CARD_STATUS_MD_GENERATED_STRING;
		
		if(status > CardStateManagerService.CARD_STATUS_MD_GENERATED)
			statusName = CardStateManagerService.RECORD_VALID_STATUS_STRING;
		return statusName;
	}

	public static String buildCardStatus(Long status) {
		String statusName = null;
		if (status == CardStateManagerService.CARD_STATUS_DISPATCH)
			statusName = CardStateManagerService.CARD_STATUS_DISPATCH_STRING;
		if (status == CardStateManagerService.CARD_STATUS_DELIVER)
			statusName = CardStateManagerService.CARD_STATUS_DELIVER_STRING;
		if (status == CardStateManagerService.CARD_STATUS_RETURN)
			statusName = CardStateManagerService.CARD_STATUS_RETURN_STRING;
		if (status == CardStateManagerService.CARD_STATUS_REDISPATCH)
			statusName = CardStateManagerService.CARD_STATUS_REDISPATCH_STRING;
		if (status == CardStateManagerService.CARD_STATUS_DESTROY)
			statusName = CardStateManagerService.CARD_STATUS_DESTROY_STRING;
		if (status == CardStateManagerService.CARD_STATUS_MANUALREJECT)
			statusName = CardStateManagerService.CARD_STATUS_MANUALREJECT_STRING;
		if (status == CardStateManagerService.CARD_STATUS_MANUALHOLD)
			statusName = CardStateManagerService.CARD_STATUS_MANUALHOLD_STRING;
		if (status == CardStateManagerService.CARD_STATUS_UNKNOWN)
			statusName = CardStateManagerService.CARD_STATUS_UNKNOWN_STRING;
		
		return statusName;
	}
	
	
	
	
//	@Override
	@Transactional
	public String reprocess(Long fileId, String remark, String status) {
		int count = 0;
		try {
			
			String filename = null;
			String fileName = getFileDAO().getFileName(fileId);
			getDroolBootStrapService().fire();
			logger.info("start reprocess");
			List<CreditCardDetails> list = getGndDAO().getRecordsList(status, fileId);
			if(list != null && list.size()>0) {
				count = list.size();
				Iterator<CreditCardDetails> itr = list.iterator();
				while(itr.hasNext()) {
					CreditCardDetails details = (CreditCardDetails) itr.next();
					int curStatus = details.getStatus().intValue();
					String split[] = fileName.split("\\.");
					logger.info("status is : "+curStatus);				
					DroolRecordBO droolRecord = buildDroolRecordBO(details);
					logger.info("file type is : "+split[1]);
					if(droolRecord.getHomeBranchCode().trim().isEmpty() == false && split[1].equalsIgnoreCase("ncf")) {

						List<Branch> branchList = getBranchDAO().getBranch(droolRecord.getHomeBranchCode().trim(), droolRecord.getBankId());
						if(branchList != null && branchList.size() > 0) {
							Branch branch = (Branch) branchList.get(0);
							logger.info("branch code is : "+branch.getShortCode());
							droolRecord.setAddr1(branch.getAddress1()!= null && !branch.getAddress1().isEmpty()? branch.getAddress1().length()>40?branch.getAddress1().substring(0, 40):branch.getAddress1() : " ");
							droolRecord.setAddr2(branch.getAddress2() != null && !branch.getAddress2().isEmpty()?branch.getAddress2().length()>40?branch.getAddress2().substring(0, 40):branch.getAddress2() : " ");
							droolRecord.setAddr3(branch.getAddress3() != null && !branch.getAddress3().isEmpty()?branch.getAddress3().length()>40?branch.getAddress3().substring(0, 40):branch.getAddress3() : " ");
							droolRecord.setAddr4(branch.getAddress4() != null && !branch.getAddress4().isEmpty()?branch.getAddress4().length()>40?branch.getAddress4().substring(0, 40):branch.getAddress4() : " ");
							droolRecord.setCity ("          ");
							droolRecord.setPin  (branch.getPinCode());
							
							details.setAddr1(branch.getAddress1()!= null && !branch.getAddress1().isEmpty()? branch.getAddress1().length()>40?branch.getAddress1().substring(0, 40):branch.getAddress1() : " ");
							details.setAddr2(branch.getAddress2() != null && !branch.getAddress2().isEmpty()?branch.getAddress2().length()>40?branch.getAddress2().substring(0, 40):branch.getAddress2() : " ");
							details.setAddr3(branch.getAddress3() != null && !branch.getAddress3().isEmpty()?branch.getAddress3().length()>40?branch.getAddress3().substring(0, 40):branch.getAddress3() : " ");
							details.setAddr4(branch.getAddress4() != null && !branch.getAddress4().isEmpty()?branch.getAddress4().length()>40?branch.getAddress4().substring(0, 40):branch.getAddress4() : " ");
							details.setCity ("          ");
							details.setPin  (branch.getPinCode());
						}
						if(droolRecord.getProduct().trim().isEmpty() == false) {
							String bin = getProductDAO().getBin(droolRecord.getProduct().trim(), droolRecord.getBankId());
							logger.info("bin number is : "+bin);
							droolRecord.setInstitutionId(bin != null?bin:"     ");
							details.setInstitutionId(bin != null?bin:"     ");
						}
					}
					droolRecord.setFlowStatus(DroolRecordBO.FLOW_PASS);
					
					getDroolRecord().setFileName(fileName);
					getDroolRecord().setFileId(details.getFileId());
					filename = getDroolRecord().getFileName();
					
					logger.info("filename is : "+filename);
					logger.info("rule status is : "+details.getRuleStatus());
					if(!split[1].equalsIgnoreCase("ncf") && (details.getStatus().intValue() == CardStateManagerService.CARD_STATUS_REJECT || details.getStatus().intValue() == CardStateManagerService.CARD_STATUS_PENDING)) {
						droolRecord = getDroolBootStrapService().bootStrap(droolRecord);
						if(droolRecord.getFlowStatus().equals(DroolRecordBO.FLOW_PASS)) {
							details.setStatus((long) CardStateManagerService.CARD_STATUS_HOLD); 
						}
						else {
							details.setStatus(droolRecord.getStatus());
							details.setFlowStatus(droolRecord.getFlowStatus());
							details.setRuleStatus(droolRecord.getRuleStatus());
							details.setCreatedDate(new Date());
							getGndDAO().saveCardDetails(details);
							
							RecordEvent event = new RecordEvent();
							event.setEventDate(new Date());
							event.setDescription(details.getRuleStatus()+" "+remark);
							event.setEventId((long) RecordEventBO.EVENT_QC_RULES);
							event.setRecordId(details.getCreditCardDetailsId());
							getGndDAO().saveRecordEvent(event);
						}
						logger.info("after reject reprocess status is : "+details.getStatus()+" and rule status : "+droolRecord.getRuleStatus());
					}
					else if(split[1].equalsIgnoreCase("ncf") && (details.getStatus().intValue() == CardStateManagerService.CARD_STATUS_REJECT || details.getStatus().intValue() == CardStateManagerService.CARD_STATUS_PENDING)) {
						droolRecord = getDroolBootStrapService().bootStrapCNF(droolRecord);
						if(droolRecord.getFlowStatus().equals(DroolRecordBO.FLOW_PASS)) {
							details.setStatus((long) CardStateManagerService.CARD_STATUS_HOLD); 
						}
						else {
							details.setStatus(droolRecord.getStatus());
							details.setFlowStatus(droolRecord.getFlowStatus());
							details.setRuleStatus(droolRecord.getRuleStatus());
							details.setCreatedDate(new Date());
							getGndDAO().saveCardDetails(details);
							
							RecordEvent event = new RecordEvent();
							event.setEventDate(new Date());
							event.setDescription(details.getRuleStatus()+" "+remark);
							event.setEventId((long) RecordEventBO.EVENT_QC_RULES);
							event.setRecordId(details.getCreditCardDetailsId());
							getGndDAO().saveRecordEvent(event);
						}
					}
					if(details.getStatus().intValue() == CardStateManagerService.CARD_STATUS_HOLD) {
						logger.info("reprocessing hold records");
						processHold(droolRecord, details, remark, fileName);
					}
			
				
				}
				getRec().compositeValidationCheck();
				getRec().checkBranchGroupSizeExceedRule();
				getRec().processTXNData();
//				getRec().assignLCPCCode();
				String filetype[] =filename.split("\\.");
				if(!filetype[1].equalsIgnoreCase("ncf") && !filetype[1].equalsIgnoreCase("npc")) {
					getRec().assignCardAWB();
					
				}
				
				else if(filetype[1].equalsIgnoreCase("ncf")) {
					getRec().ncfLCPCCardAWBAssign();
				}
				if(!filetype[1].equalsIgnoreCase("npc")) {
//					if(!filetype[1].equalsIgnoreCase("ncf")) {
						getRec().assignPinAWB();
//					}
					getAufformatcheck().aufFormatCheckAndProcess();
//					getFileConvertServiceImpl().convertTxt();
				}
			} 
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return count+" records reprocessed";
		
	}

}
