package com.indutech.gnd.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;


import org.apache.camel.Exchange;
import org.apache.camel.component.file.GenericFile;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.indutech.gnd.bo.DispatchBO;
import com.indutech.gnd.bo.Response;
import com.indutech.gnd.dao.DispatchDAOImpl;
import com.indutech.gnd.dto.CardDispatch;
import com.indutech.gnd.dto.CreditCardDetails;
import com.indutech.gnd.dto.RecordEvent;

@Component(value="dispatchService")
public class DispatchServiceImpl implements DispatchService {
	
	Logger logger = Logger.getLogger(DispatchServiceImpl.class);
	common.Logger log = common.Logger.getLogger(DispatchServiceImpl.class);
	
	Properties properties = PropertiesLoader.getInstance().loadProperties();
	
	@Autowired
	private DispatchDAOImpl dispatchDAO;
	
	

	public DispatchDAOImpl getDispatchDAO() {
		return dispatchDAO;
	}

	public void setDispatchDAO(DispatchDAOImpl dispatchDAO) {
		this.dispatchDAO = dispatchDAO;
	}

	Workbook workbook = null;
	
	@Override
	@Transactional
	public void cardMISDispatch(Exchange exchange) {
		try {
			GenericFile<?> gfile = (GenericFile<?>) exchange.getIn().getBody();
			File file = (File)gfile.getFile();
			InputStream is = new FileInputStream(file);
			String fname = file.getName();
			String split[] = fname.split("\\.");
			if(split[((split.length)-1)].equalsIgnoreCase("xls")) {
				workbook = new HSSFWorkbook(is);
			}
			if(split[((split.length)-1)].equalsIgnoreCase("xlsx")) {
				workbook = new XSSFWorkbook(is);
			}
			if(workbook != null) {
				Sheet sheet = workbook.getSheetAt(0);
				if (sheet != null) {
					Iterator<?> rowIterator = sheet.rowIterator();
					rowIterator.next();
					int i = 0;
					while (rowIterator.hasNext()) {
						Row row = (Row) rowIterator.next();
						if(row != null) {
							Cell bank = row.getCell(0);							bank.setCellType(Cell.CELL_TYPE_STRING);
							Cell branchCode = row.getCell(1);					branchCode.setCellType(Cell.CELL_TYPE_STRING);
							Cell product = row.getCell(2);						product.setCellType(Cell.CELL_TYPE_STRING);
							Cell cardCount = row.getCell(3);						cardCount.setCellType(Cell.CELL_TYPE_NUMERIC);
							Cell rsn = row.getCell(4);							rsn.setCellType(Cell.CELL_TYPE_NUMERIC);
							Cell cardAWB = row.getCell(5);						cardAWB.setCellType(Cell.CELL_TYPE_STRING);
							Cell coreFileName = row.getCell(6);					coreFileName.setCellType(Cell.CELL_TYPE_STRING);
							Cell dispatchDate = row.getCell(7);				//	dispatchDate.setCellType(Cell.CELL_TYPE_STRING);
							Cell challanNumber = row.getCell(8);				challanNumber.setCellType(Cell.CELL_TYPE_STRING);
							Cell isIndividual = row.getCell(9); 				isIndividual.setCellType(Cell.CELL_TYPE_NUMERIC);
							
	//						logger.info("dispatch date is : "+dispatchDate.getStringCellValue());
							CardDispatch dispatch = new CardDispatch();
							dispatch.setBankSuffix(bank.getStringCellValue());
							dispatch.setHomeBranchCode(branchCode.getStringCellValue());
							dispatch.setProduct(product.getStringCellValue());
							dispatch.setPinCount((long) cardCount.getNumericCellValue());
							dispatch.setRsn((long)rsn.getNumericCellValue());
							dispatch.setCardAWB(cardAWB.getStringCellValue());
							dispatch.setCorFileName(coreFileName.getStringCellValue());
							dispatch.setDispatchedDate(dispatchDate.getDateCellValue());
							dispatch.setChallanNumber(challanNumber.getStringCellValue());
							dispatch.setIsIndividual((long)isIndividual.getNumericCellValue());
							
							List<CreditCardDetails> list = getDispatchDAO().findPinAWB(dispatch.getRsn(), dispatch.getCardAWB());
							if(list != null && list.size() > 0) {
								dispatch.setInfo(DispatchService.CARD_AWB_MAPPED+" "+dispatch.getCardAWB());
							}
							else {
								dispatch.setInfo(DispatchService.CARD_AWB_NOT_MAPPED+" "+dispatch.getCardAWB());
							}
							getDispatchDAO().saveCardDispatch(dispatch);
							
							logger.info("row count is "+(i++));
						}
					}
				}
			}
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	@Override
	@Transactional
	public Response misDispatch(MultipartFile misFile, String dispatchType, String statusVal, String remark) {
		String result = " Failed to process, Please check the format and try again"; 
		String currentRuleStatus = null;
		List<CreditCardDetails> detailsList = null;
		List<DispatchBO> dispatchList = null;
		List<DispatchBO> rejectionList = null;
		int i = 1;
		int j = 1;
		File rejFile = null;
		Response res = new Response();
		try {
			
			String dcno = " dcno. : ";
			String seperator = "^";
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
			File file = new File(misFile.getOriginalFilename());
			
			misFile.transferTo(file);
			logger.info("parent file is : "+file.getPath());
			InputStream is = new FileInputStream(file);
			String fname = file.getName();
			String split[] = fname.split("\\.");
			if(split[((split.length)-1)].equalsIgnoreCase("xls")) {
				workbook = new HSSFWorkbook(is);
			}
			else if(split[((split.length)-1)].equalsIgnoreCase("xlsx")) {
				workbook = new XSSFWorkbook(is);
			} else {
				is.close();
				result = "Given File "+file.getName()+" is not in either xls or xlsx format";
//				return result;
			}
			if(workbook != null) {
				Sheet sheet = workbook.getSheetAt(0);
				if (sheet != null) {
					
					//Initializing objects
					dispatchList = new ArrayList<DispatchBO>();
					detailsList = new ArrayList<CreditCardDetails>();
					rejectionList =  new ArrayList<DispatchBO>();
					
					Map<String,Object> map = prepareStatus(dispatchType, statusVal);
					Integer status = (Integer) map.get("status");
					String ruleStatus = (String) map.get("ruleStatus");
					currentRuleStatus = (String) map.get("currentRule");
					
					logger.info("required status is : "+statusVal +" and current status is : "+status );
					
					Iterator<?> rowIterator = sheet.rowIterator();
					rowIterator.next();
					
					while (rowIterator.hasNext()) {
						Row row = (Row) rowIterator.next();
						if(row != null) {
							Cell bank = row.getCell(0);		
							if(bank != null) {
								bank.setCellType(Cell.CELL_TYPE_STRING);
								Cell branchCode = row.getCell(1);					branchCode.setCellType(Cell.CELL_TYPE_STRING);
								Cell product = row.getCell(2);						product.setCellType(Cell.CELL_TYPE_STRING);
								Cell pinCount = row.getCell(3);						pinCount.setCellType(Cell.CELL_TYPE_NUMERIC);
								Cell rsn = row.getCell(4);							rsn.setCellType(Cell.CELL_TYPE_NUMERIC);
								Cell awb = row.getCell(5);							awb.setCellType(Cell.CELL_TYPE_STRING);
								Cell coreFileName = row.getCell(6);					coreFileName.setCellType(Cell.CELL_TYPE_STRING);
								Cell dispatchDate = row.getCell(7);					//dispatchDate.setCellType(Cell.CELL_TYPE_STRING);
								Cell challanNumber = row.getCell(8);				challanNumber.setCellType(Cell.CELL_TYPE_STRING);
								
								
								DispatchBO dispatch = new DispatchBO();
								dispatch.setBankSuffix(bank.getStringCellValue());
								dispatch.setHomeBranchCode(branchCode.getStringCellValue());
								dispatch.setProduct(product.getStringCellValue());
								dispatch.setPinCount((long) pinCount.getNumericCellValue());
								dispatch.setRsn((long)rsn.getNumericCellValue());
								dispatch.setPinAWB(awb.getStringCellValue());
								dispatch.setCorFileName(coreFileName.getStringCellValue());
								dispatch.setDispatchedDate(dispatchDate.getDateCellValue());
								dispatch.setChallanNumber(challanNumber.getStringCellValue());
								
								dispatchList.add(dispatch);
								
								
								i++;
								logger.info("rsn is : "+rsn.getNumericCellValue()+" and awb is : "+awb.getStringCellValue());
								CreditCardDetails  details = getDispatchDAO().getCardDetails((long) rsn.getNumericCellValue(), awb.getStringCellValue(), dispatchType, (long)status);
								if(details != null) {
									detailsList.add(details);
								} else {
									j++;
									rejectionList.add(dispatch);
								}
							}
						}
					} 
					logger.info("dispatchList size is : "+dispatchList.size()+" and detailsList size is : "+detailsList.size());
					if(dispatchList.size() == detailsList.size()) {
						result = (i-1)+" records processed and updated to  "+ruleStatus+" status successfully";
						for(int k = 0; k < dispatchList.size(); k++) {
							DispatchBO dispatch = (DispatchBO) dispatchList.get(k);
							CreditCardDetails details = (CreditCardDetails) detailsList.get(k);
							saveDetails(details, Long.parseLong(statusVal), ruleStatus, dispatchType);
							saveRecordEvent(dispatch.getDispatchedDate(), details.getCreditCardDetailsId(), Long.parseLong(statusVal), ruleStatus+dcno+dispatch.getChallanNumber(), fname+seperator+sdf.format(new Date())+seperator+remark);
						}
					} else if(rejectionList.size() > 0) {
						result = (i-1)+" records processed but "+(j-1)+" records are not in "+currentRuleStatus+" state";
						logger.info("rejection list size is : "+rejectionList.size());
						Iterator<DispatchBO> itr = rejectionList.iterator();
						new File(properties.getProperty("txt.dispatchNotProcessed")).mkdirs();
						String split1[] = fname.split("\\.");
						rejFile = new File(properties.getProperty("txt.dispatchNotProcessed")+split1[0]+".txt");
						BufferedWriter bw = new BufferedWriter(new FileWriter(rejFile));
						bw.write("Below are the List of RSN values which are not in "+currentRuleStatus+" state");
						bw.newLine();
						while(itr.hasNext()) {
							DispatchBO dispatch = (DispatchBO) itr.next();
							bw.write(dispatch.getRsn().toString());
							bw.newLine();
						}
						bw.close();
//						InputStream ist = new FileInputStream(rejFile);
//						response.setContentType("application/octet-stream");
//						response.setHeader("Content-Disposition", "attachment; filename=\""	+ file.getName() + "\"");
//						
//						OutputStream os = response.getOutputStream();
//						byte[] buffer = new byte[1024];
//						int len;
//						while ((len = is.read(buffer)) != -1) {
//							os.write(buffer, 0, len);
//						}
//						os.flush();
//						os.close();
//						ist.close();
//						rejFile.delete();
					}
				}
			}
			
			res.setResult(result);
			res.setFilepath(rejFile != null ? rejFile.getAbsolutePath() : null );
			logger.info(res.getFilepath());
		} catch(Exception e) {
			res.setResult(result);
			res.setFilepath(rejFile != null ? rejFile.getAbsolutePath() : null );
			logger.error(e);
			e.printStackTrace();
		}
		return res;

	}

	private Map<String, Object> prepareStatus(String dispatchType, String statusVal) {
		Integer requiredStatus = Integer.parseInt(statusVal);
		Integer currentStatus = null;
		String ruleStatus = null;
		String currentRuleStatus = null;
		Map<String, Object> map = new HashMap<String,Object>();
		switch(requiredStatus) {
		case CardStateManagerService.CARD_STATUS_DISPATCH :
			currentStatus = CardStateManagerService.CARD_STATUS_MD_GENERATED;
			ruleStatus = CardStateManagerService.CARD_STATUS_DISPATCH_STRING;
			currentRuleStatus = CardStateManagerService.CARD_STATUS_MD_GENERATED_STRING;
			map.put("status", currentStatus); 
			map.put("ruleStatus", ruleStatus);
			map.put("currentRule",currentRuleStatus);
			break;
		case CardStateManagerService.CARD_STATUS_DELIVER :
			currentStatus = CardStateManagerService.CARD_STATUS_DISPATCH;
			ruleStatus = CardStateManagerService.CARD_STATUS_DELIVER_STRING;
			currentRuleStatus = CardStateManagerService.CARD_STATUS_DISPATCH_STRING;
			map.put("status", currentStatus); 
			map.put("ruleStatus", ruleStatus);
			map.put("currentRule",currentRuleStatus);
			break;
		case CardStateManagerService.CARD_STATUS_REDISPATCH :
			currentStatus = CardStateManagerService.CARD_STATUS_RTO_GROUP_CARD_AWB_ASSIGN;
			ruleStatus = CardStateManagerService.CARD_STATUS_REDISPATCH_STRING;
			currentRuleStatus = CardStateManagerService.CARD_STATUS_RETURN_STRING;
			map.put("status", currentStatus); 
			map.put("ruleStatus", ruleStatus);
			map.put("currentRule",currentRuleStatus);
			break;
		case PinStateManagerService.PIN_STATUS_DELIVER :
			currentStatus = PinStateManagerService.PIN_STATUS_AWB_ASSIGNED;
			ruleStatus = PinStateManagerService.PIN_STATUS_DELIVER_STRING;
			currentRuleStatus = PinStateManagerService.PIN_STATUS_AWB_ASSIGNED_STRING;
			map.put("status", currentStatus); 
			map.put("ruleStatus", ruleStatus);
			map.put("currentRule",currentRuleStatus);
			break;
		case PinStateManagerService.PIN_STATUS_REDISPATCH :
			currentStatus = PinStateManagerService.PIN_STATUS_RTO;
			ruleStatus = PinStateManagerService.PIN_STATUS_REDISPATCH_STRING;
			currentRuleStatus = PinStateManagerService.PIN_STATUS_RTO_STRING;
			map.put("status", currentStatus); 
			map.put("ruleStatus", ruleStatus);
			map.put("currentRule",currentRuleStatus);
			break;
		default:
			map = null;
			break;
		}
		return map;
	}

	private void saveRecordEvent(Date dispatchDate, Long recordId, Long eventId, String description, String additionalInfo) {
		RecordEvent event = new RecordEvent();
		event.setDescription(description);
		event.setEventDate(dispatchDate);
		event.setEventId(eventId);
		event.setRecordId(recordId);
		event.setAdditionalInfo(additionalInfo);
		
		getDispatchDAO().saveRecordEvent(event);
	}

	private void saveDetails(CreditCardDetails details, Long status, String ruleStatus, String dispatchType) {
		if(dispatchType.equalsIgnoreCase("card")) {
			details.setStatus(status);
		} else if(dispatchType.equalsIgnoreCase("pin")) {
			details.setPinstatus(status);
		}
		details.setRuleStatus(ruleStatus);
		details.setCreatedDate(new Date());
		
		getDispatchDAO().saveCustomerRecords(details);
		
	}
	
	public void notNeeded() {
//		Long currentStatus = details.getStatus();
//		Long requiredStatus = Long.parseLong(statusVal);
//		switch(requiredStatus.intValue()) {
//			case CardStateManagerService.CARD_STATUS_DISPATCH :
//				if(currentStatus == CardStateManagerService.CARD_STATUS_MD_GENERATED || currentStatus == CardStateManagerService.CARD_STATUS_EMBOSSA_RECEIVED) {
//					buildDetails(details,(long) CardStateManagerService.CARD_STATUS_DISPATCH, CardStateManagerService.CARD_STATUS_DISPATCH_STRING+" "+challanNumber.getStringCellValue());
//					saveRecordEvent(dispatchDate.getDateCellValue(), details.getCreditCardDetailsId(), (long) CardStateManagerService.CARD_STATUS_DISPATCH, CardStateManagerService.CARD_STATUS_DISPATCH_STRING+dcno+challanNumber.getStringCellValue(), 
//																	fname+seperator+sdf.format(new Date())+seperator+remark);
//					j++;
//				}
//			break;

//			case CardStateManagerService.CARD_STATUS_DELIVER :
//				if(currentStatus == CardStateManagerService.CARD_STATUS_DISPATCH) {
//					buildDetails(details,(long) CardStateManagerService.CARD_STATUS_DELIVER,CardStateManagerService.CARD_STATUS_DELIVER_STRING+" "+challanNumber.getStringCellValue());
//					saveRecordEvent(dispatchDate.getDateCellValue(), details.getCreditCardDetailsId(), (long) CardStateManagerService.CARD_STATUS_DELIVER, CardStateManagerService.CARD_STATUS_DELIVER_STRING+dcno+challanNumber.getStringCellValue(), 
//																	fname+seperator+sdf.format(new Date())+seperator+remark);
//					j++;
//				}
//			break;

//			case CardStateManagerService.CARD_STATUS_RETURN :
//				if(currentStatus == CardStateManagerService.CARD_STATUS_DELIVER) {
//					buildDetails(details,(long) CardStateManagerService.CARD_STATUS_RETURN,CardStateManagerService.CARD_STATUS_RETURN_STRING+" "+challanNumber.getStringCellValue());
//					saveRecordEvent(dispatchDate.getDateCellValue(), details.getCreditCardDetailsId(), (long) CardStateManagerService.CARD_STATUS_RETURN, CardStateManagerService.CARD_STATUS_RETURN_STRING+dcno+challanNumber.getStringCellValue(), 
//																	fname+seperator+sdf.format(new Date())+seperator+remark);
//					j++;
//				}
//			break;

//			case CardStateManagerService.CARD_STATUS_REDISPATCH :
//				if(currentStatus == CardStateManagerService.CARD_STATUS_RETURN) {
//					buildDetails(details,(long) CardStateManagerService.CARD_STATUS_REDISPATCH,CardStateManagerService.CARD_STATUS_REDISPATCH_STRING+" "+challanNumber.getStringCellValue());
//					saveRecordEvent(dispatchDate.getDateCellValue(), details.getCreditCardDetailsId(), (long) CardStateManagerService.CARD_STATUS_REDISPATCH, CardStateManagerService.CARD_STATUS_REDISPATCH_STRING+dcno+challanNumber.getStringCellValue(), 
//																	fname+seperator+sdf.format(new Date())+seperator+remark);
//					j++;
//				}
//			break;

//		}
//	}
//	if(details != null && dispatchType.equalsIgnoreCase("pin")) {
//		Long currentStatus = details.getPinstatus();
//		Long requiredStatus = Long.parseLong(statusVal);
//		switch(requiredStatus.intValue()) {
//			case PinStateManagerService.PIN_STATUS_DELIVER :
//				if(currentStatus == PinStateManagerService.PIN_STATUS_AWB_ASSIGNED) {
//					buildDetails(details,(long) PinStateManagerService.PIN_STATUS_DELIVER,PinStateManagerService.PIN_STATUS_DELIVER_STRING+" "+challanNumber.getStringCellValue());
//					saveRecordEvent(dispatchDate.getDateCellValue(), details.getCreditCardDetailsId(), (long) PinStateManagerService.PIN_STATUS_DELIVER, PinStateManagerService.PIN_STATUS_DELIVER_STRING+dcno+challanNumber.getStringCellValue(), 
//																	fname+seperator+sdf.format(new Date())+seperator+remark);
//					j++;
//						
//				}
//				break;
//			case PinStateManagerService.PIN_STATUS_RTO :
//				if(currentStatus == PinStateManagerService.PIN_STATUS_DELIVER) {
//					buildDetails(details,(long) PinStateManagerService.PIN_STATUS_RTO,PinStateManagerService.PIN_STATUS_RTO_STRING+" "+challanNumber.getStringCellValue());
//					saveRecordEvent(dispatchDate.getDateCellValue(), details.getCreditCardDetailsId(), (long) PinStateManagerService.PIN_STATUS_RTO, PinStateManagerService.PIN_STATUS_RTO_STRING+dcno+challanNumber.getStringCellValue(), 
//																	fname+seperator+sdf.format(new Date())+seperator+remark);
//					j++;
//				}
//				break;

//			case PinStateManagerService.PIN_STATUS_REDISPATCH :
//				if(currentStatus == PinStateManagerService.PIN_STATUS_RTO) {
//					buildDetails(details,(long) PinStateManagerService.PIN_STATUS_REDISPATCH,PinStateManagerService.PIN_STATUS_REDISPATCH_STRING+" "+challanNumber.getStringCellValue());
//					saveRecordEvent(dispatchDate.getDateCellValue(), details.getCreditCardDetailsId(), (long) PinStateManagerService.PIN_STATUS_REDISPATCH, PinStateManagerService.PIN_STATUS_REDISPATCH_STRING+dcno+challanNumber.getStringCellValue(), 
//																	fname+seperator+sdf.format(new Date())+seperator+remark);
//					j++;
//				}
//				break;

//		}
//
//	}

	}

}
