package com.indutech.gnd.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import au.com.bytecode.opencsv.CSVWriter;

import com.barcodelib.barcode.a.g.m.s;
import com.indutech.gnd.bo.BankBO;
import com.indutech.gnd.bo.CoreFilesBO;
import com.indutech.gnd.bo.CreditCardDetailsBO;
import com.indutech.gnd.bo.Response;
import com.indutech.gnd.bo.ShopFloorDetails;
import com.indutech.gnd.dao.GNDDAOImpl;
import com.indutech.gnd.dao.ShopFloorDao;
import com.indutech.gnd.dto.Bank;
import com.indutech.gnd.dto.CoreFiles;
import com.indutech.gnd.dto.CreditCardDetails;
import com.indutech.gnd.dto.MasterCourierService;
import com.indutech.gnd.dto.RecordEvent;

@Component("shopFloorService")
public class ShopFloorService {
	public static Integer COUNTER_FOR_RTO=0;
	Logger logger = Logger.getLogger(ShopFloorService.class);
	Properties properties = PropertiesLoader.getInstance().loadProperties();
	common.Logger log = common.Logger.getLogger(ShopFloorService.class);
	
	@Autowired
	private ShopFloorDao shopFloorDao;

	@Autowired
	private GNDDAOImpl gndDAO;
	
	
	@Transactional
	public ShopFloorDetails cardStatusDispatchSer(Long rsn, String username) {
		CreditCardDetails details = null;
		ShopFloorDetails ShopFloorDetails=null;
		try {
			details = getShopFloorDao().findRsn(rsn);
			if(details != null) {
				int status = details.getStatus().intValue();
				ShopFloorDetails=new ShopFloorDetails();
				switch (status) {
				case CardStateManagerService.CARD_STATUS_EMBOSSA_RECEIVED:
				case CardStateManagerService.CARD_STATUS_MD_GENERATED:
					details.setStatus((long) CardStateManagerService.CARD_STATUS_DISPATCH);
					details.setRuleStatus(CardStateManagerService.CARD_STATUS_DISPATCH_STRING);
					details.setCreatedDate(new Date());
					Long val=getGndDAO().saveCardDetails(details);
					if((long)val != 0){
						Long eventid=saveRecordEvent(details.getCreditCardDetailsId(),(long) CardStateManagerService.CARD_STATUS_DISPATCH, details.getRuleStatus(), username);
					//resbool=(long)CardStateManagerService.CARD_STATUS_DISPATCH;
						if(eventid!=null){
							ShopFloorDetails.setStatus((long)CardStateManagerService.CARD_STATUS_DISPATCH);
							ShopFloorDetails.setBoolRes(true);
							ShopFloorDetails.setRsn(rsn);
						}
					}
					break;
				default:
					ShopFloorDetails.setBoolRes(false);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			cardStatusDispatchSer(rsn,username);
		}
		
		return ShopFloorDetails;
	}

	@Transactional
	public ShopFloorDetails cardStatusDeliver(Long rsn, String username) {
		CreditCardDetails details = null;
		ShopFloorDetails ShopFloorDetails=null;
		
		try {
			details = getShopFloorDao().findRsn(rsn);
			if(details != null) {
				int status = details.getStatus().intValue();
				ShopFloorDetails=new ShopFloorDetails();
				switch (status) {
				case CardStateManagerService.CARD_STATUS_DISPATCH:
					details.setStatus((long) CardStateManagerService.CARD_STATUS_DELIVER);
					details.setRuleStatus(CardStateManagerService.CARD_STATUS_DELIVER_STRING);
					details.setCreatedDate(new Date());
					Long val=getGndDAO().saveCardDetails(details);
					if((long)val!=0){
					Long eventid=saveRecordEvent(details.getCreditCardDetailsId(), (long) CardStateManagerService.CARD_STATUS_DELIVER, details.getRuleStatus(), username);					
					//resbool=(long) CardStateManagerService.CARD_STATUS_DELIVER;
					if(eventid!=null){
					ShopFloorDetails.setStatus((long) CardStateManagerService.CARD_STATUS_DELIVER);
					ShopFloorDetails.setBoolRes(true);
					ShopFloorDetails.setRsn(rsn);
					}
					}
					break;
				default:
					ShopFloorDetails.setBoolRes(false);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			cardStatusDeliver(rsn,username);
		}
		return ShopFloorDetails;
	}

	@Transactional
	public ShopFloorDetails cardStatusDeliverbyAWB(String awb, String username) {
		ShopFloorDetails ShopFloorDetailsList=new ShopFloorDetails();
		ShopFloorDetails ShopFloorDetails=null;
		List<CreditCardDetails> cardstatuslist=null;
		try {
			cardstatuslist = getShopFloorDao().findAWB(awb);
			if(cardstatuslist != null) {
				for(int i=0;i<cardstatuslist.size();i++){
				CreditCardDetails detailsfor=cardstatuslist.get(i);
				int status = detailsfor.getStatus().intValue();
				ShopFloorDetails=new ShopFloorDetails();
				switch (status) {
				case CardStateManagerService.CARD_STATUS_DISPATCH:
					detailsfor.setStatus((long) CardStateManagerService.CARD_STATUS_DELIVER);
					detailsfor.setRuleStatus(CardStateManagerService.CARD_STATUS_DELIVER_STRING);
					detailsfor.setCreatedDate(new Date());
					Long id=getGndDAO().saveCardDetails(detailsfor);
					if(id!=null&&(long)id!=0){
					Long eventid=saveRecordEvent(detailsfor.getCreditCardDetailsId(), (long) CardStateManagerService.CARD_STATUS_DELIVER, detailsfor.getRuleStatus(), username);					
					if(eventid!=null){
					ShopFloorDetails.setStatus((long) CardStateManagerService.CARD_STATUS_DELIVER);
					ShopFloorDetails.setBoolRes(true);
					ShopFloorDetails.setCardAWB(awb);
					ShopFloorDetails.setRsn(detailsfor.getRsn());
					//resbool=(long)CardStateManagerService.CARD_STATUS_DELIVER;
					ShopFloorDetailsList.getShopFloorDetailsDelivery().add(ShopFloorDetails);
					}
					}
					break;
				default:
					ShopFloorDetails.setBoolRes(false);
					ShopFloorDetails.setCardAWB(detailsfor.getCardAWB());
					ShopFloorDetails.setRsn(detailsfor.getRsn());
					ShopFloorDetails.setPrimaryAccNumber(detailsfor.getPrimaryAcctNo());					
					ShopFloorDetailsList.getShopFloorDetailsOtherStatus().add(ShopFloorDetails);
				}
			}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			cardStatusDeliverbyAWB(awb,username);
		}
		
		return ShopFloorDetailsList;
	}
	
	
	@Transactional
	public ShopFloorDetails cardStatusRTObyAWB(String awb, String username) {
		ShopFloorDetails ShopFloorDetailsList=new ShopFloorDetails();
		ShopFloorDetails ShopFloorDetails=null;
		List<CreditCardDetails> cardstatuslist=null;
		try {
			cardstatuslist = getShopFloorDao().findAWB(awb);
			if(cardstatuslist != null) {
				for(int i=0;i<cardstatuslist.size();i++){
				CreditCardDetails detailsfor=cardstatuslist.get(i);
				int status = detailsfor.getStatus().intValue();
				ShopFloorDetails=new ShopFloorDetails();
				switch (status) {
				case CardStateManagerService.CARD_STATUS_DELIVER:
				case CardStateManagerService.CARD_STATUS_REDISPATCH:
					if(status==CardStateManagerService.CARD_STATUS_REDISPATCH){
						ShopFloorDetails.setRedispatachAgain(true);
						getShopFloorDao().getdispatchdetailsAWB(awb, ShopFloorDetails);
						
					}
					detailsfor.setStatus((long) CardStateManagerService.CARD_STATUS_RETURN);
					detailsfor.setRuleStatus(CardStateManagerService.CARD_STATUS_RETURN_STRING);
					detailsfor.setCreatedDate(new Date());
					Long id=getGndDAO().saveCardDetails(detailsfor);
					if(id!=null&&(long)id!=0){
					Long eventid=saveRecordEvent(detailsfor.getCreditCardDetailsId(),(long) CardStateManagerService.CARD_STATUS_RETURN, detailsfor.getRuleStatus(), username);					
					if(eventid!=null){
					ShopFloorDetails.setStatus((long) CardStateManagerService.CARD_STATUS_RETURN);
					ShopFloorDetails.setBoolRes(true);
					ShopFloorDetails.setCardAWB(awb);
					ShopFloorDetails.setRsn(detailsfor.getRsn());
					ShopFloorDetailsList.getShopFloorDetailsreturn().add(ShopFloorDetails);
					}
					//resbool=(long)CardStateManagerService.CARD_STATUS_RETURN;
					}
					break;
	
				default:
					ShopFloorDetails.setBoolRes(false);
					ShopFloorDetails.setCardAWB(detailsfor.getCardAWB());
					ShopFloorDetails.setRsn(detailsfor.getRsn());
					ShopFloorDetails.setPrimaryAccNumber(detailsfor.getPrimaryAcctNo());					
					ShopFloorDetailsList.getShopFloorDetailsOtherStatus().add(ShopFloorDetails);
					
				}
			}
				}	
			
		} catch (Exception e) {
			e.printStackTrace();
			cardStatusRTObyAWB(awb,username);
		}
		
		return ShopFloorDetailsList;
	}

	@Transactional
	public ShopFloorDetails cardStatusReturn(Long rsn, String username) {
		ShopFloorDetails ShopFloorDetails=null;
		CreditCardDetails details = null;
		try {
			logger.info("rsn number is : "+rsn);
			details = getShopFloorDao().findRsn(rsn);
			ShopFloorDetails=new ShopFloorDetails();
			
			if(details != null) {
				int status = details.getStatus().intValue();
				switch (status) {
				
				case CardStateManagerService.CARD_STATUS_DELIVER:
				case CardStateManagerService.CARD_STATUS_REDISPATCH:
				
					if((long)status==(long)CardStateManagerService.CARD_STATUS_REDISPATCH){
						ShopFloorDetails.setRedispatachAgain(true);
						getShopFloorDao().getdispatchdetails(rsn,ShopFloorDetails);
					}
					details.setStatus((long) CardStateManagerService.CARD_STATUS_RETURN);
					details.setRuleStatus(CardStateManagerService.CARD_STATUS_RETURN_STRING);
					details.setCreatedDate(new Date());
					Long id=getGndDAO().saveCardDetails(details);
					if(id!=null&&(long)id!=0){
					Long eventid=saveRecordEvent(details.getCreditCardDetailsId(),(long) CardStateManagerService.CARD_STATUS_RETURN, details.getRuleStatus(), username);
					if(eventid!=null){
					ShopFloorDetails.setStatus((long) CardStateManagerService.CARD_STATUS_RETURN);
					ShopFloorDetails.setBoolRes(true);
					ShopFloorDetails.setRsn(rsn);
						}
					}
					break;
					
				default:
					ShopFloorDetails.setBoolRes(false);
				}
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
			cardStatusReturn(rsn,username);
		}
		
		return ShopFloorDetails;

	}

	@Transactional
	public ShopFloorDetails cardStatusRedispatch(Long rsn, String username) {
		ShopFloorDetails ShopFloorDetails=null;
		CreditCardDetails details = null;
		try {
			details = getShopFloorDao().findRsn(rsn);
			if(details != null) {
				int status = details.getStatus().intValue();
				ShopFloorDetails=new ShopFloorDetails();
				switch (status) {
				case CardStateManagerService.CARD_STATUS_RTO_GROUP_CARD_AWB_ASSIGN:
				case 	CardStateManagerService.CARD_STATUS_RTO_INDIVIDUAL_CARD_AWB_ASSIGN :
					details.setStatus((long) CardStateManagerService.CARD_STATUS_REDISPATCH);
					details.setRuleStatus(CardStateManagerService.CARD_STATUS_REDISPATCH_STRING);
					details.setCreatedDate(new Date());
					Long id=getGndDAO().saveCardDetails(details);
					if(id!=null&&(long)id!=0){
					Long eventid=saveRecordEvent(details.getCreditCardDetailsId(),(long) CardStateManagerService.CARD_STATUS_REDISPATCH, details.getRuleStatus(), username);
					if(eventid!=null){
					ShopFloorDetails.setStatus((long) CardStateManagerService.CARD_STATUS_REDISPATCH);
					ShopFloorDetails.setBoolRes(true);
					ShopFloorDetails.setRsn(rsn);
					}
					}
					break;
				default:
					ShopFloorDetails.setBoolRes(false);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			cardStatusRedispatch(rsn,username);
		}
		return ShopFloorDetails;

	}
	
	@Transactional
	public ShopFloorDetails cardStatusDestroy(Long rsn, String username) {
		CreditCardDetails details = null;
		ShopFloorDetails ShopFloorDetails=null;
		try {
			details = getShopFloorDao().findRsn(rsn);
			if(details != null) {
				int status = details.getStatus().intValue();
				ShopFloorDetails=new ShopFloorDetails();
				switch (status) {
				case CardStateManagerService.CARD_STATUS_RETURN:
					details.setStatus((long) CardStateManagerService.CARD_STATUS_DESTROY);
					details.setRuleStatus(CardStateManagerService.CARD_STATUS_DESTROY_STRING);
					details.setCreatedDate(new Date());
					Long id=getGndDAO().saveCardDetails(details);
					if(id!=null&&(long)id!=0){
					Long eventid=saveRecordEvent(details.getCreditCardDetailsId(),(long) CardStateManagerService.CARD_STATUS_DESTROY, details.getRuleStatus(), username);
					if(eventid!=null){
					ShopFloorDetails.setStatus((long) CardStateManagerService.CARD_STATUS_DESTROY);
					ShopFloorDetails.setBoolRes(true);
					ShopFloorDetails.setRsn(rsn);
					}
					}
					//resbool=(long)CardStateManagerService.CARD_STATUS_DESTROY;
					break;
	
				default:
					ShopFloorDetails.setBoolRes(false);
				}
			}

			
		} catch (Exception e) {
			e.printStackTrace();
			cardStatusDestroy(rsn,username);
		}
		
		return ShopFloorDetails;
	}
	
	
	@Transactional
	public ShopFloorDetails pinStatusDispatch(Long rsn, String username) {
		ShopFloorDetails ShopFloorDetails=null;
		CreditCardDetails details = null;
		try {
			logger.info("rsn number is : "+rsn);
			details = getShopFloorDao().findRsn(rsn);
			if(details != null) {
				int status = details.getPinstatus().intValue();
				ShopFloorDetails=new ShopFloorDetails();
				switch (status){
				case PinStateManagerService.PIN_STATUS_MAILER    :
					details.setPinstatus((long) PinStateManagerService.PIN_STATUS_DISPATCH);
					details.setRuleStatus(PinStateManagerService.PIN_STATUS_DISPATCH_STRING);
					details.setCreatedDate(new Date());
					Long id=getGndDAO().saveCardDetails(details);	
					if(id!=null&&(long)id!=0){
					Long eventid=saveRecordEvent(details.getCreditCardDetailsId(),(long) PinStateManagerService.PIN_STATUS_DISPATCH, details.getRuleStatus(), username);
				if(eventid!=null){
					ShopFloorDetails.setStatus((long) PinStateManagerService.PIN_STATUS_DISPATCH);
					ShopFloorDetails.setBoolRes(true);
					ShopFloorDetails.setRsn(rsn);
					//resbool=(long)PinStateManagerService.PIN_STATUS_DISPATCH;
				}
					}
					break;
					
				default:
					ShopFloorDetails.setBoolRes(false);
				}
			}

			
		} catch (Exception e) {
			e.printStackTrace();
			pinStatusDispatch(rsn,username);
		}
		
		return ShopFloorDetails;
	}
	
	@Transactional
	public ShopFloorDetails pinStatusDelivery(Long rsn, String username) {
		ShopFloorDetails ShopFloorDetails=null;
		CreditCardDetails details = null;
		try {
			details = getShopFloorDao().findRsn(rsn);
			if(details != null) {
				int status = details.getPinstatus().intValue();
				ShopFloorDetails=new ShopFloorDetails();
				switch (status){
				case PinStateManagerService.PIN_STATUS_DISPATCH:											
						details.setPinstatus((long) PinStateManagerService.PIN_STATUS_DELIVER);
						details.setRuleStatus(PinStateManagerService.PIN_STATUS_DELIVER_STRING);
						details.setCreatedDate(new Date());
						Long id=getGndDAO().saveCardDetails(details);
						if(id!=null&&(long)id!=0){
						Long eventid=saveRecordEvent(details.getCreditCardDetailsId(),(long) PinStateManagerService.PIN_STATUS_DELIVER, details.getRuleStatus(), username); 
						if(eventid!=null){
						ShopFloorDetails.setStatus((long) PinStateManagerService.PIN_STATUS_DELIVER);
						ShopFloorDetails.setBoolRes(true);
						ShopFloorDetails.setRsn(rsn);	
						}
						}
						//resbool=(long)PinStateManagerService.PIN_STATUS_DELIVER;						
					break;				
					default:
						ShopFloorDetails.setBoolRes(false);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			pinStatusDelivery(rsn,username);
		}
	
		return ShopFloorDetails;
	}
	
	@Transactional
	public ShopFloorDetails  pinStatusReturned(Long rsn, String username) {
		ShopFloorDetails ShopFloorDetails=null;
		CreditCardDetails details = null;
		try {
			details = getShopFloorDao().findRsn(rsn);
			ShopFloorDetails=new ShopFloorDetails();
			if(details != null) {
				int status = details.getPinstatus().intValue();
				switch (status){
				case PinStateManagerService.PIN_STATUS_DELIVER :
				case PinStateManagerService.PIN_STATUS_REDISPATCH:
					if(status==PinStateManagerService.PIN_STATUS_REDISPATCH){
						ShopFloorDetails.setRedispatachAgain(true);
					}
					details.setPinstatus((long) PinStateManagerService.PIN_STATUS_RTO);
					details.setRuleStatus(PinStateManagerService.PIN_STATUS_RTO_STRING);
					details.setCreatedDate(new Date());
					Long id=getGndDAO().saveCardDetails(details);	
					if(id!=null&&(long)id!=0){
					Long eventid=saveRecordEvent(details.getCreditCardDetailsId(),(long) PinStateManagerService.PIN_STATUS_RTO, details.getRuleStatus(), username);					
					if(eventid!=null) {
					ShopFloorDetails.setStatus((long) PinStateManagerService.PIN_STATUS_RTO);
					ShopFloorDetails.setBoolRes(true);
					ShopFloorDetails.setRsn(rsn);
					}
					}
					//resbool=(long)PinStateManagerService.PIN_STATUS_RTO;
				break;
					
				default:
					ShopFloorDetails.setBoolRes(false);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			pinStatusReturned(rsn,username);
		}
		
		return ShopFloorDetails;
	}
	
	@Transactional
	public ShopFloorDetails pinStatusRedispatch(Long rsn, String username) {
		ShopFloorDetails ShopFloorDetails=null;
		CreditCardDetails details = null;
		try {
			details = getShopFloorDao().findRsn(rsn);
			if(details != null) {
				int status = details.getPinstatus().intValue();
				ShopFloorDetails=new ShopFloorDetails();
				switch (status){
					case PinStateManagerService.PIN_STATUS_AWB_ASSIGNED  :
						details.setPinstatus((long) PinStateManagerService.PIN_STATUS_REDISPATCH);
						details.setRuleStatus(PinStateManagerService.PIN_STATUS_REDISPATCH_STRING);
						details.setCreatedDate(new Date());
						Long id=getGndDAO().saveCardDetails(details);
						if(id!=null&&(long)id!=0){
						Long eventid=saveRecordEvent(details.getCreditCardDetailsId(),(long) PinStateManagerService.PIN_STATUS_REDISPATCH, details.getRuleStatus(), username);				
						if(eventid!=null){
						ShopFloorDetails.setStatus((long) PinStateManagerService.PIN_STATUS_REDISPATCH);
						ShopFloorDetails.setBoolRes(true);
						ShopFloorDetails.setRsn(rsn);
						//resbool=(long) PinStateManagerService.PIN_STATUS_REDISPATCH;
						}
						}
					break;					
					default:
						ShopFloorDetails.setBoolRes(false);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			pinStatusRedispatch(rsn,username);
		}
		
		return ShopFloorDetails;
	}
	
	@Transactional
	public ShopFloorDetails pinStatusDestroy(Long rsn, String username) {
		CreditCardDetails details = null;
		ShopFloorDetails ShopFloorDetails=null;
		try {
			details = getShopFloorDao().findRsn(rsn);			
			if(details != null) {
				int status = details.getPinstatus().intValue();
				ShopFloorDetails=new ShopFloorDetails();
				switch (status){
					case PinStateManagerService.PIN_STATUS_RTO :
						details.setPinstatus((long) PinStateManagerService.PIN_STATUS_DESTROY);
						details.setRuleStatus(PinStateManagerService.PIN_STATUS_DESTROY_STRING);
						details.setCreatedDate(new Date());
						Long id=getGndDAO().saveCardDetails(details);
						if(id!=null&&(long)id!=0){
						Long eventid=saveRecordEvent(details.getCreditCardDetailsId(),(long) PinStateManagerService.PIN_STATUS_DESTROY, details.getRuleStatus(), username);
						if(eventid!=null){
						ShopFloorDetails.setStatus((long) PinStateManagerService.PIN_STATUS_DESTROY);
						ShopFloorDetails.setBoolRes(true);
						ShopFloorDetails.setRsn(rsn);
						}
						}
						//resbool=(long)PinStateManagerService.PIN_STATUS_DESTROY;
					break;
					
					default:
						ShopFloorDetails.setBoolRes(false);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			pinStatusDestroy(rsn, username);
		}

		return ShopFloorDetails;
	}
	
	
	public Long saveRecordEvent(Long recordId, Long eventId, String desc, String username) {
		
		RecordEvent event = new RecordEvent();
		event.setEventDate(new Date());
		event.setDescription(desc+" ("+username+")");
		event.setEventId(eventId);
		event.setRecordId(recordId);
		
		Long eventid=getGndDAO().saveRecordEvent(event);
		return eventid;
	}


	public ShopFloorDao getShopFloorDao() {
		return shopFloorDao;
	}

	public void setShopFloorDao(ShopFloorDao shopFloorDao) {
		this.shopFloorDao = shopFloorDao;
	}

	public GNDDAOImpl getGndDAO() {
		return gndDAO;
	}

	public void setGndDAO(GNDDAOImpl gndDAO) {
		this.gndDAO = gndDAO;
	}
	@Transactional
	public  List<MasterCourierService>  getDCMSSer() {
		List<MasterCourierService> dcmslist=getShopFloorDao().getDCMSDao();
		return buildDcms(dcmslist);
	}

	private List<MasterCourierService> buildDcms(List<MasterCourierService> dcmslist) {
		int size=dcmslist.size();
		List<MasterCourierService> dcmsBOlist=new ArrayList<MasterCourierService>();
		for(int i=0;i<size;i++){
			MasterCourierService masdcms=dcmslist.get(i);
			MasterCourierService dcmsBO=new MasterCourierService();
			dcmsBO.setId(masdcms.getId());
			dcmsBO.setServiceProviderName(masdcms.getServiceProviderName());
			dcmsBOlist.add(dcmsBO);
		}
		return dcmsBOlist;
	}
	@Transactional
	public 	Map getAWBforDcmsId(Integer dcmsId) {
		Map awbName=getShopFloorDao().getAWBforDcmsIdDao(dcmsId);
		return awbName;
	}
	@Transactional
	public Map getchangeAWBbyRsnSer(String cardpin, String awb, Long rsn) {
	Map map=getShopFloorDao().getchangeAWBbyRsnDao(cardpin,awb,rsn);
		return map;
	}
	@Transactional
	public List<BankBO> getBankList() {
		List<BankBO> list = null;
		List<Bank> bankList = null;
		try {
			bankList = getShopFloorDao().getBankList();
			
			if(bankList != null && bankList.size() > 0) {
				list = new ArrayList<BankBO>();
				logger.info("no of banks are : "+bankList.size());
				Iterator<Bank> itr = bankList.iterator();
				while(itr.hasNext()) {
					Bank bank = (Bank) itr.next();
					list.add(buildBankBO(bank));
				}
			}
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}
	


	/*private void getEventDetails(Long rsn,Object[] resultList,ScanByRsnToRTO scanbyrsnotorto,Integer counter ) {
		resultList=getShopFloorDao().getCardReturnDao(rsn);
		scanbyrsnotorto=buildRSNbyRTO(resultList,scanbyrsnotorto);
		scanbyrsnotorto.setCounter(counter);
		System.out.println("CardStateManagerService.CARD_STATUS_DELIVER"+scanbyrsnotorto+"    "+resultList);
		
	}*/

	private ShopFloorDetails buildRSNbyRTO(Object[] record,ShopFloorDetails scanbyrsnotorto) {
		try{		
			if(record!=null){
				if(record[0]!=null){
					scanbyrsnotorto.setRsn(Long.valueOf(String.valueOf(record[0])));
				}
				if(record[1]!=null){
					scanbyrsnotorto.setCardAWB(String.valueOf(record[1]));
				}
				
				if(record[2]!=null){
					scanbyrsnotorto.setShortCode(String.valueOf(record[2]));
				}
				if(record[3]!=null){
					scanbyrsnotorto.setFileName(String.valueOf(record[3]));
				}
				if(record[4]!=null){
					scanbyrsnotorto.setInformation(String.valueOf(record[4]));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return scanbyrsnotorto;
	}
	
	private ShopFloorDetails buildRSNbyRTOPin(Object[] record,ShopFloorDetails scanbyrsnotorto) {
		try{		
			if(record!=null){
				if(record[0]!=null){
					scanbyrsnotorto.setRsn(Long.valueOf(String.valueOf(record[0])));
				}
				if(record[1]!=null){
					scanbyrsnotorto.setPinAWB(String.valueOf(record[1]));
				}
				
				if(record[2]!=null){
					scanbyrsnotorto.setShortCode(String.valueOf(record[2]));
				}
				if(record[3]!=null){
					scanbyrsnotorto.setFileName(String.valueOf(record[3]));
				}
				if(record[4]!=null){
					scanbyrsnotorto.setInformation(String.valueOf(record[4]));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return scanbyrsnotorto;
	}
	
	
	
	
	
	
	

	private BankBO buildBankBO(Bank bank) {
		BankBO bankBO = new BankBO();
		bankBO.setBankCode(bank.getBankCode());
		bankBO.setBankId(bank.getBankId());
		bankBO.setBankName(bank.getBankName());
		bankBO.setId(bank.getId());
		bankBO.setPrefix(bank.getPrefix());
		bankBO.setShortCode(bank.getShortCode());
		bankBO.setStatus(bank.getStatus());
		return bankBO;
	}

	@Transactional
	public ShopFloorDetails gettransaction(ShopFloorDetails ShopFloorDetails) {
		Object[] resultList=getShopFloorDao().getCardDetails(ShopFloorDetails.getRsn(),ShopFloorDetails.getStatus());
		ShopFloorDetails=buildRSNbyRTO(resultList,ShopFloorDetails);
		return ShopFloorDetails;
	}
	@Transactional
	public ShopFloorDetails gettransactionPin(ShopFloorDetails ShopFloorDetails) {
		Object[] resultList=getShopFloorDao().getCardDetailsPin(ShopFloorDetails.getRsn(),ShopFloorDetails.getStatus());
		ShopFloorDetails=buildRSNbyRTOPin(resultList,ShopFloorDetails);
		return ShopFloorDetails;
	}
	
	
	
	
	
	
	@Transactional
	public ShopFloorDetails gettransactionAWB(ShopFloorDetails ShopFloorDetails) {
		Object[] resultList=getShopFloorDao().getCardDetailsAWB(ShopFloorDetails);
		ShopFloorDetails=buildRSNbyRTO(resultList,ShopFloorDetails);
		return ShopFloorDetails;
	}
	@Transactional
	public int getCountofRTo() {
	int cou	=getShopFloorDao().getCountRTO();
	return cou;
	}


	
	
	@Transactional
	public List<CoreFilesBO> getFilesList(String receivedDate, long bankId) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		List<CoreFilesBO> files = null;
		String outputDate = null;
		try {
			if(receivedDate != null) {
				Date date = sdf1.parse(receivedDate);
				outputDate = sdf2.format(date);
			}
			List<CoreFiles> list = getShopFloorDao().getFilesList(outputDate, bankId);
			if(list != null && list.size() >0) {
				
				files = new ArrayList<CoreFilesBO>();
				Iterator<CoreFiles> itr = list.iterator();
				while(itr.hasNext()) {
					CoreFiles coreFile = (CoreFiles) itr.next();
					files.add(buildCoreFilesBO(coreFile));
				}
				logger.info(files.size());
			}
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return files;
	}

	private CoreFilesBO buildCoreFilesBO(CoreFiles coreFile) {
		CoreFilesBO filesBO = new CoreFilesBO();
		filesBO.setAufFileId(coreFile.getAufFileId());
		filesBO.setCoreFileId(coreFile.getCoreFileId());
		filesBO.setDescription(coreFile.getDescription());
		filesBO.setFilename(coreFile.getFilename());
		filesBO.setFileType(coreFile.getFileType());
		filesBO.setId(coreFile.getId());
		filesBO.setLineCount(coreFile.getLineCount());
		filesBO.setReceivedDate(coreFile.getReceivedDate());
		filesBO.setStatus(coreFile.getStatus());
		filesBO.setProcessedDate(coreFile.getProcessedDate());
		return filesBO;
	}
	@Transactional
	public List<String> getHomeBranchCodeList(String receivedDate,
			long bankId, long fileId, long status) {

		List<String> list = getShopFloorDao().getHomeBranchCodeList(receivedDate, bankId, fileId, status);
		return list;
	}
	
	
	private CreditCardDetailsBO buildCreditCardDetailsBO(CreditCardDetails card) {
		CreditCardDetailsBO details = new CreditCardDetailsBO();
		details.setRsn(card.getRsn());
		details.setHomeBranchCode(card.getHomeBranchCode());
		details.setCardAWB(card.getCardAWB());
		details.setPinAWB(card.getPinAWB());
		details.setProduct(card.getProduct());
		details.setRecordStatus(CardStateManagerService.buildCardStatus(card.getStatus()));
		details.setPinStatusString(PinStateManagerService.buildPinStatus(card.getPinstatus()));
		
		return details;
	}
	

	@Transactional
	public List<CreditCardDetailsBO> getRecordsList(String receivedDate, long bankId, long fileId, long status, String homeBranchCode) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		List<CreditCardDetailsBO> detailsList = null;
		String outputDate = null;
		try {
			if(receivedDate != null) {
				Date date = sdf1.parse(receivedDate);
				outputDate = sdf2.format(date);
			}
			List<CreditCardDetails> list = getShopFloorDao().getRecordDetails(receivedDate, bankId, fileId, status, homeBranchCode);
			if(list != null && list.size() >0) {
				
				detailsList = new ArrayList<CreditCardDetailsBO>();
				Iterator<CreditCardDetails> itr = list.iterator();
				while(itr.hasNext()) {
					CreditCardDetails card = (CreditCardDetails) itr.next();
					detailsList.add(buildCreditCardDetailsBO(card));
				}
				logger.info(detailsList.size());
			}
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return detailsList;
	}

	@Transactional
	public List<CreditCardDetailsBO> getRecordsList(String fromDate, String toDate, String status, String bankId) {
		SimpleDateFormat sdfInput = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat sdfOutput = new SimpleDateFormat("yyyy/MM/dd");
		List<CreditCardDetailsBO> detailsList = null;
		try {
			Date input1 = sdfInput.parse(fromDate);
			Date input2 = sdfInput.parse(toDate);
			String from = sdfOutput.format(input1);
			String to = sdfOutput.format(input2);
			
			List list = getShopFloorDao().getRecordDetails(from, to, status, bankId);
			if(list != null && list.size() > 0) {
				detailsList = new ArrayList<>();
				Iterator itr = list.iterator();
				while(itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					CreditCardDetailsBO details = new CreditCardDetailsBO();
					details.setRsn(((BigDecimal)(obj[0])).longValue());
					details.setProcessedBranchCode((String)obj[1]);
					details.setProduct((String)obj[2]);
					details.setRecordStatus((String) obj[3]);
					details.setBankCode((String)obj[4]);
					details.setFilename((String)obj[5]);
					details.setCardAWB((String)obj[6]);
					detailsList.add(details);
				}
			}
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return detailsList;
	}

	@SuppressWarnings("rawtypes")
	public String createAndSaveCSV(String fileName,List<ShopFloorDetails> masterbo) {
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("ddMyyyyhhmmss");
		String filepath=properties.getProperty("csv.filetoawbdownload");
		fileName+=sdf.format(date)+".xlsx";
		String fullpath=filepath+"//"+fileName;
		
		File file=new File(fullpath);
		 XSSFWorkbook workbook = new XSSFWorkbook(); 
	      XSSFSheet spreadsheet = workbook.createSheet( 
	      fileName);
	      XSSFRow row;	  
	      int m=0;
	      Map < Integer, Object[] > shopfloordetails =   new HashMap<Integer, Object[]> ();
	      shopfloordetails.put(0,new Object[] {"RSN","CARD_AWB","SHORT_CODE","FILE_NAME","INFORMATION"});
	      for(int i=0;i<masterbo.size();i++){
	    	  shopfloordetails.put(++m, new Object[] {String.valueOf(((Map)masterbo.get(i)).get("rsn")),
						String.valueOf(((Map)masterbo.get(i)).get("cardAWB")),
						String.valueOf(((Map)masterbo.get(i)).get("shortCode")),
						String.valueOf(((Map)masterbo.get(i)).get("fileName")),
						String.valueOf(((Map)masterbo.get(i)).get("information"))}); 
	    
	      }
	     
	      Set < Integer > keyid = shopfloordetails.keySet();
	      int rowid = 0;
	      for (Integer key : keyid)
	      {
	         row = spreadsheet.createRow(rowid++);
	         Object [] objectArr = shopfloordetails.get(key);
	         int cellid = 0;
	         for (Object obj : objectArr)
	         {
	            Cell cell = row.createCell(cellid++);
	            cell.setCellValue((String)obj);
	         }
	      }
	      FileOutputStream out = null;
		try {
			out = new FileOutputStream( 
			  file);
			 workbook.write(out);
		      out.close();
		      
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  
		return fullpath;
		
	}

	@SuppressWarnings("rawtypes")
	public String createAndSaveCSVPin(String fileName,List<ShopFloorDetails> masterbo) {
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("ddMyyyyhhmmss");
		String filepath=properties.getProperty("csv.filetoawbdownload");
		fileName+=sdf.format(date)+".xlsx";
		String fullpath=filepath+"//"+fileName;
		
		File file=new File(fullpath);
		 XSSFWorkbook workbook = new XSSFWorkbook(); 
	      XSSFSheet spreadsheet = workbook.createSheet( 
	      fileName);
	      XSSFRow row;	  
	      int m=0;
	      Map < Integer, Object[] > shopfloordetails =   new HashMap<Integer, Object[]> ();
	      shopfloordetails.put(0,new Object[] {"RSN","PIN_AWB","SHORT_CODE","FILE_NAME","INFORMATION"});
	      for(int i=0;i<masterbo.size();i++){
	    	  shopfloordetails.put(++m, new Object[] {String.valueOf(((Map)masterbo.get(i)).get("rsn")),
						String.valueOf(((Map)masterbo.get(i)).get("pinAWB")),
						String.valueOf(((Map)masterbo.get(i)).get("shortCode")),
						String.valueOf(((Map)masterbo.get(i)).get("fileName")),
						String.valueOf(((Map)masterbo.get(i)).get("information"))}); 
	    
	      }
	      System.out.println("++"+shopfloordetails.size());
	      Set < Integer > keyid = shopfloordetails.keySet();
	      int rowid = 0;
	      for (Integer key : keyid)
	      {
	         row = spreadsheet.createRow(rowid++);
	         Object [] objectArr = shopfloordetails.get(key);
	         int cellid = 0;
	         for (Object obj : objectArr)
	         {
	            Cell cell = row.createCell(cellid++);
	            cell.setCellValue((String)obj);
	         }
	      }
	      FileOutputStream out = null;
		try {
			out = new FileOutputStream( 
			  file);
			 workbook.write(out);
		      out.close();
		      
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
		return fullpath;
		
	}

	public String createNotInReturnedstate(
			List<ShopFloorDetails> shopFloorDetailsOtherStatus) {
		String fileName="card_rto_awb_notIn_ReturnedState";
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("ddMyyyyhhmmss");
		String filepath=properties.getProperty("csv.filetoawbdownload");
		fileName+=sdf.format(date)+".csv";
		String fullpath=filepath+"//"+fileName;
		try {
			 CSVWriter writer = new CSVWriter(new FileWriter(fullpath));
			 List<String[]> data = new ArrayList<String[]>();
			 String header[]="cardAWB#rsn#PrimaryAccountNumber".split("#");
			 writer.writeNext(header);
			for(int i=0;i<shopFloorDetailsOtherStatus.size();i++){
				data.add(new String[]{String.valueOf(shopFloorDetailsOtherStatus.get(i).getCardAWB()+"\t"),
						String.valueOf(shopFloorDetailsOtherStatus.get(i).getRsn()+"\t"),
						String.valueOf(shopFloorDetailsOtherStatus.get(i).getPrimaryAccNumber()+"\t"),
					});
			}
			writer.writeAll(data);
			 writer.flush();
			 writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return fullpath;
	}
	public String createNotInDeliverystate(
			List<ShopFloorDetails> shopFloorDetailsOtherStatus) {
		String fileName="card_delivery_awb_notIn_DeliverState";
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("ddMyyyyhhmmss");
		String filepath=properties.getProperty("csv.filetoawbdownload");
		fileName+=sdf.format(date)+".csv";
		String fullpath=filepath+"//"+fileName;
		try {
			 CSVWriter writer = new CSVWriter(new FileWriter(fullpath));
			 List<String[]> data = new ArrayList<String[]>();
			 String header[]="cardAWB#rsn#PrimaryAccountNumber".split("#");
			 writer.writeNext(header);
			for(int i=0;i<shopFloorDetailsOtherStatus.size();i++){
				data.add(new String[]{String.valueOf(shopFloorDetailsOtherStatus.get(i).getCardAWB()+"\t"),
						String.valueOf(shopFloorDetailsOtherStatus.get(i).getRsn()+"\t"),
						String.valueOf(shopFloorDetailsOtherStatus.get(i).getPrimaryAccNumber()+"\t"),
					});
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
	public Response uploadProductionRSNList(MultipartFile mulfile, String username) {
		Response response = new Response();
		String validres = "";
		String rejres = "";
		HSSFWorkbook wb = null;
		HSSFSheet xlssheet = null;
		String excelFileName = null;
		int x = 0;
		try {
			File file = new File(mulfile.getOriginalFilename());
			mulfile.transferTo(file);
			InputStream is = new FileInputStream(file);
			logger.info("filename is : "+file.getAbsolutePath());
			logger.info("prodution excel upload work is processing ...... please wait");
			String fname = file.getName();
			Workbook workbook = null;
			String split[] = fname.split("\\.");
			if(split[((split.length)-1)].equalsIgnoreCase("xls")) {
				workbook = new HSSFWorkbook(is);
			}
			if(split[((split.length)-1)].equalsIgnoreCase("xlsx")) {
				workbook = new XSSFWorkbook(is);
			}
			Sheet sheet = workbook.getSheetAt(0);
			if (sheet != null) {
				wb = new HSSFWorkbook();
				String sheetName = "Sheet1";
				xlssheet = wb.createSheet(sheetName) ;
				new File(properties.getProperty("xls.cardProductionPath")).mkdirs();
				excelFileName = properties.getProperty("xls.cardProductionPath")+"/"+new SimpleDateFormat("ddMMyyyy").format(new Date())+"_productioncardsList.xls";//name of excel file
				response.setFilepath(excelFileName);
				Iterator<?> rowIterator = sheet.rowIterator();
				rowIterator.next();
				List<CreditCardDetails> validlist = new ArrayList<CreditCardDetails>();
				List<Long> rejectlist = new ArrayList<Long>();
				while (rowIterator.hasNext()) {
					Long rsn = null;
					Row row = (Row) rowIterator.next();
					if(row != null) {
						Iterator<Cell> cellIterator = row.cellIterator();
						while (cellIterator.hasNext()) { 
							Cell cell = cellIterator.next(); 
							if(cell != null) {
								switch (cell.getCellType()) { 
									case Cell.CELL_TYPE_STRING: 
										rsn = Long.parseLong(cell.getStringCellValue()); 
										break; 
									case Cell.CELL_TYPE_NUMERIC: 
										rsn  = (long) cell.getNumericCellValue(); 
										break;
								}
							} 
							if(rsn != null) {
								CreditCardDetails details = getShopFloorDao().findRsn(rsn);
								if(details != null) {
									switch(details.getStatus().intValue()) {
										case CardStateManagerService.CARD_STATUS_EMBOSSA_RECEIVED:
										case CardStateManagerService.CARD_STATUS_MD_GENERATED:
											details.setStatus((long) CardStateManagerService.CARD_STATUS_DISPATCH);
											details.setRuleStatus(CardStateManagerService.CARD_STATUS_DISPATCH_STRING);
											details.setCreatedDate(new Date());
											Long val=getGndDAO().saveCardDetails(details);
											if((long)val!=0){
												Long eventid=saveRecordEvent(details.getCreditCardDetailsId(),(long) CardStateManagerService.CARD_STATUS_DISPATCH, details.getRuleStatus(), username);
												if(eventid!=null){
													validlist.add(details);
												}
											}
											break;
										default:
											rejectlist.add(details.getRsn());
											
									}
								}
								else {
									rejectlist.add(rsn);
								}
							} else {
								rejectlist.add(rsn);
							}
						}
					}
				}
				if(validlist.size() > 0) {
					String rsnStr = "(";
					int k = 0;
					for(CreditCardDetails details : validlist) {
						rsnStr += details.getRsn();
						if(k == validlist.size()-1) {
							rsnStr +=")";
						}
						else {
							rsnStr +=",";
						}
						k++;
					}
					validres = validlist.size()+" cards updated to CARD PRODUCED state successfully";
					List<Object[]> outputList = getShopFloorDao().getCardDetails(rsnStr, (long) CardStateManagerService.CARD_STATUS_DISPATCH);
					logger.info("report size is : "+outputList.size());
					if(outputList.size() > 0) {
						HSSFRow firstrow = xlssheet.createRow(0);
						HSSFCell cell1 = firstrow.createCell(0);
						cell1.setCellValue("RSN");
						HSSFCell cell2 = firstrow.createCell(1);
						cell2.setCellValue("CARD AWB");
						HSSFCell cell3 = firstrow.createCell(2);
						cell3.setCellValue("BANK");
						HSSFCell cell4 = firstrow.createCell(3);
						cell4.setCellValue("FILE NAME");
						HSSFCell cell5 = firstrow.createCell(4);
						cell5.setCellValue("INFO");
						
						//iterating r number of rows
						for (int r=0;r < outputList.size(); r++ )
						{
							HSSFRow validrow = xlssheet.createRow(r+1);
							Object[] obj = (Object[]) outputList.get(r);
							//iterating c number of columns
							for (int c=0;c < obj.length; c++ )
							{
								HSSFCell validcell = validrow.createCell(c);
								validcell.setCellValue((obj[c]).toString());
							}
							x=r+1;
						}
					}
				}
				if(rejectlist.size() > 0) {
					rejres = rejectlist.size()+" cards are not updated to CARD PRODUCTION state";
					logger.info("no of valid record rows are : "+x);
					HSSFRow rejrow = xlssheet.createRow(++x);
					HSSFCell rejcell = rejrow.createCell(0);
					rejcell.setCellValue("Below are the list of RSN values which are not present in CARD Production state");
					for (int r=0;r < rejectlist.size(); r++ )
					{
						HSSFRow rejrow1 = xlssheet.createRow(++x);
						Long rejrsn = (Long) rejectlist.get(r);
						//iterating c number of columns
						HSSFCell rejcell1 = rejrow1.createCell(0);
						rejcell1.setCellValue((rejrsn).toString());
					}
				}
				response.setResult(validres+" "+rejres);
				logger.info("result is : "+response.getResult());
				wb.write(new FileOutputStream(new File(excelFileName)));
				wb.close();
			}
		} catch(Exception e) {
			response.setResult("Failed to process");
			response.setFilepath(null);
			e.printStackTrace();
			logger.error(e);
		} 
		return response;
	}
	

}
