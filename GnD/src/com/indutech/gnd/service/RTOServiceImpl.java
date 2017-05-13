package com.indutech.gnd.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.camel.Exchange;
import org.apache.camel.component.file.GenericFile;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.indutech.gnd.bo.BankBO;
import com.indutech.gnd.dao.RTODAOImpl;
import com.indutech.gnd.dto.CreditCardDetails;
import com.indutech.gnd.dto.MasterAWB;
import com.indutech.gnd.dto.RecordEvent;
import com.indutech.gnd.util.DataInitialization;


@Component(value="rtoService")
public class RTOServiceImpl implements RTOService{
	
	Logger logger = Logger.getLogger(RTOService.class);
	
	@Autowired
	private RTODAOImpl rtoDAO;
	
	

	public RTODAOImpl getRtoDAO() {
		return rtoDAO;
	}



	public void setRtoDAO(RTODAOImpl rtoDAO) {
		this.rtoDAO = rtoDAO;
	}

	@Override
	@Transactional
	public void ChangeAWBByHomeBranchGroup(Exchange exchange) {
		List<Long> rsnList = null;
		try {
			GenericFile<?> gfile =(GenericFile<?>) exchange.getIn().getBody();
			File file = (File) gfile.getFile();
			BufferedReader br = new BufferedReader(new FileReader(file));
			rsnList = new ArrayList<Long>();
			String rsn = null;
			while((rsn = br.readLine()) != null) {
				if(rsn != null && rsn.trim().isEmpty() == false && Pattern.compile("\\d+").matcher(rsn.trim()).matches()) {
					rsnList.add(Long.parseLong(rsn));
					
				}
			}
			br.close();
			if(rsnList.size() > 0) {
				List<Object[]> homeBranchList = getRtoDAO().getHomeBranchList(rsnList);
				if(homeBranchList != null && homeBranchList.size() > 0) {
					int branchsize = homeBranchList.size();
					logger.info("branch group size is : "+branchsize);
					List<MasterAWB> awbList = getRtoDAO().getAWBList((long)CorierServiceProviders.INDIAN_POST_CHENNAI, branchsize);
					if(awbList != null && awbList.size() > 0) {
						int awbCount = awbList.size();
						logger.info("awb keys are : "+awbCount);
						for(int i = 0; i < awbCount; i++) {
							Object[] obj = (Object[]) homeBranchList.get(i);
							String homeBranchCode = (String) obj[0];
							Long bankId = (Long) obj[1];
							logger.info("home branch code is : "+homeBranchCode+" and bank id is : "+bankId);
							List<CreditCardDetails> list = getRtoDAO().getCardDetails(homeBranchCode,bankId,rsnList);
							if(list != null && list.size() > 0) {
								logger.info("card details size is : "+list.size());
								Iterator<CreditCardDetails> iterator = list.iterator();
								while(iterator.hasNext()) {
									CreditCardDetails details = (CreditCardDetails) iterator.next();
									RecordEvent event = new RecordEvent();
									event.setEventDate(new Date());
									event.setEventId((long)CardStateManagerService.CARD_STATUS_RTO_GROUP_CARD_AWB_ASSIGN);
									event.setDescription(CardStateManagerService.CARD_STATUS_RTO_CARD_AWB_ASSIGN_STRING+details.getCardAWB());
									event.setRecordId(details.getCreditCardDetailsId());
									getRtoDAO().saveRecordEvent(event);
								}
							}
							MasterAWB awb = (MasterAWB) awbList.get(i);
							int updateCount = getRtoDAO().updateCardAWB(homeBranchCode, bankId, awb.getAwbName(),rsnList,(long)CardStateManagerService.CARD_STATUS_RTO_GROUP_CARD_AWB_ASSIGN, CardStateManagerService.CARD_STATUS_RTO_CARD_AWB_ASSIGN_STRING);
							logger.info(awb.getAwbName()+" is assigned for "+updateCount+" homebranches : "+homeBranchCode);
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
	public void ChangeAWBForIndividualRecord(Exchange exchange) {
		List<Long> rsnList = null;
		try {
			GenericFile<?> gfile =(GenericFile<?>) exchange.getIn().getBody();
			File file = (File) gfile.getFile();
			BufferedReader br = new BufferedReader(new FileReader(file));
			rsnList = new ArrayList<Long>();
			String rsn = null;
			while((rsn = br.readLine()) != null) {
				if(rsn != null && rsn.trim().isEmpty() == false && Pattern.compile("\\d+").matcher(rsn.trim()).matches()) {
					rsnList.add(Long.parseLong(rsn));
					
				}
			}
			br.close();
			if(rsnList.size() > 0) {
				List<CreditCardDetails> detailsList = getRtoDAO().getCustomerRecords(rsnList);
				if(detailsList != null && detailsList.size() > 0) {
					int cardsSize = detailsList.size();
					List<MasterAWB> awbList = getRtoDAO().getAWBList((long)CorierServiceProviders.INDIAN_POST_CHENNAI, cardsSize);
					if(awbList != null && awbList.size() > 0) {
						int awbCount = awbList.size();
						logger.info("awb keys are : "+awbCount);
						for(int i = 0; i < awbCount; i++) {
							CreditCardDetails details = (CreditCardDetails) detailsList.get(i);
							
							RecordEvent event = new RecordEvent();
							event.setEventDate(new Date());
							event.setEventId((long)CardStateManagerService.CARD_STATUS_RTO_INDIVIDUAL_CARD_AWB_ASSIGN);
							event.setDescription(CardStateManagerService.CARD_STATUS_RTO_CARD_AWB_ASSIGN_STRING+details.getCardAWB());
							event.setRecordId(details.getCreditCardDetailsId());
							getRtoDAO().saveRecordEvent(event);
							
							MasterAWB awb = (MasterAWB) awbList.get(i);
							
							details.setRuleStatus(CardStateManagerService.CARD_STATUS_RTO_CARD_AWB_ASSIGN_STRING+details.getCardAWB());
							details.setCreatedDate(new Date());
							details.setCardAWB(awb.getAwbName());
							details.setStatus((long)CardStateManagerService.CARD_STATUS_RTO_INDIVIDUAL_CARD_AWB_ASSIGN);
							getRtoDAO().saveCustomerRecord(details);
							
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
	public String ChangeAWBByHomeBranchGroup(String rsnString, Long bankId) {
		
//		List<Long> rsnList = null;
		String result = null;
		Multimap<String, CreditCardDetails> map = null;
		try {
//			String split[] = rsnString.split("\\,");
//			logger.info("list size is : "+split.length);
//			
//			rsnList = new ArrayList<Long>();
//			for(String rsn : split) {
//				if(rsn != null && rsn.trim().isEmpty() == false && Pattern.compile("\\d+").matcher(rsn.trim()).matches()) {
//					rsnList.add(Long.parseLong(rsn));
//					
//				}
//			}
			if(rsnString.length() > 0) {
				StringBuilder rsnList = new StringBuilder("(");
				rsnList.append(rsnString).replace(rsnList.length()-1, rsnList.length(), ")");
				logger.info("rsn list is : "+rsnList);
				logger.info("bank id is : "+bankId);
				List<CreditCardDetails> list = getRtoDAO().getCardDetails(bankId, rsnList.toString());
				if(list != null && list.size() > 0) {
					logger.info("list size is : "+list.size());
					List<BankBO> bankList = DataInitialization.getInstance().getBankInfo();
					BankBO bankBO = null;
					for(BankBO bank : bankList) {
						if(bank.getBankId() == bankId) {
							bankBO = bank;
							break;
						}
					}
					logger.info("bank is : "+bankBO);
					map = ArrayListMultimap.create();
					switch(bankBO.getRTOBranchGroup()) {
					case 1 :     //Considering AWB assignment by home branch 
						for(CreditCardDetails details : list) {
							details.setRtoBranch(details.getHomeBranchCode());
							map.put(details.getHomeBranchCode(), details);
						}
						break;
					case 2 :    //Considering AWB assignment by Issue Branch
						for(CreditCardDetails details : list) {
							details.setRtoBranch(details.getIssueBranchCode());
							map.put(details.getIssueBranchCode(), details);
						}
						break;
					case 3 : // Considering AWB assignment by issue branch and home branch(for LP cards)
						Set<Long> set = new HashSet<Long>();
						for(CreditCardDetails details : list) {
							set.add(details.getFileId());
						}
						List<CreditCardDetails> sbiList = getRtoDAO().getCardDetailsByFileId(set, rsnList.toString());
						if(sbiList != null && sbiList.size() > 0) {
							for(CreditCardDetails details : sbiList) {
								details.setRtoBranch(details.getIssueBranchCode());
								map.put(details.getIssueBranchCode(), details);
							}
						}
						List<CreditCardDetails> sbiLPList = getRtoDAO().getLPCardDetailsByFileId(set, rsnList.toString());
						if(sbiLPList != null && sbiLPList.size() > 0) {
							for(CreditCardDetails details : sbiLPList) {
								details.setRtoBranch(details.getHomeBranchCode());
								map.put(details.getHomeBranchCode(), details);
							}
						}
						break;
					case 4 :  //Considering AWB assignment for centralized branch (1 awb for max 300 cards)
						for(CreditCardDetails details : list) {
							String centralizedBranch = bankBO.getCentralizedBranch() != null ? bankBO.getCentralizedBranch() : "Centralized Branch";
							details.setRtoBranch(centralizedBranch);
							map.put(centralizedBranch, details);
						}
						break;
					}
					if(map != null && map.size() > 0) {
						logger.info("map size is : "+map.size());
						Set<String> branchCodeList = map.keySet();
						logger.info("branch list size is : "+branchCodeList);
						Iterator<String> branchIterator = branchCodeList.iterator();
						int totalAWB = 0;
						int branchSize = branchCodeList.size();
						int recordCount = 0;
						while(branchIterator.hasNext()) {
							String branchCode = (String) branchIterator.next();
							List<CreditCardDetails> creditcardDetailsIdList = (List<CreditCardDetails>) map.get(branchCode);
							recordCount += creditcardDetailsIdList.size();
							int cardRange = CardStateManagerService.CARD_STATUS_AWB_RANGE;
							int x = 0;
							if(creditcardDetailsIdList.size() % cardRange > 0) {
								x = 1;
							}
							int awbSize = (creditcardDetailsIdList.size() / cardRange ) + x;
							List<MasterAWB> awbList = getRtoDAO().getAWBList((long)CorierServiceProviders.INDIAN_POST_CHENNAI, awbSize);
							totalAWB += awbList.size();
							if(awbList != null && awbList.size() == awbSize) {
								int k = 0;
								for(int i = 1 ; i <= awbSize ; i++) {
									MasterAWB awb = awbList.get(i-1);
									for(int j = k ; j < creditcardDetailsIdList.size(); j++,k++) {
										if(j > (cardRange * i)-1) {
											break;
										}
										CreditCardDetails details = (CreditCardDetails) creditcardDetailsIdList.get(j);
										RecordEvent event = new RecordEvent();
										event.setEventDate(new Date());
										event.setEventId((long)CardStateManagerService.CARD_STATUS_RTO_GROUP_CARD_AWB_ASSIGN);
										event.setDescription(CardStateManagerService.CARD_STATUS_RTO_CARD_AWB_ASSIGN_STRING+" from "+details.getCardAWB());
										event.setRecordId(details.getCreditCardDetailsId());

										getRtoDAO().saveRecordEvent(event);
									
										details.setCardAWB(awb.getAwbName());
										details.setCreatedDate(new Date());
										details.setStatus((long)CardStateManagerService.CARD_STATUS_RTO_GROUP_CARD_AWB_ASSIGN);
										details.setRuleStatus(CardStateManagerService.CARD_STATUS_RTO_CARD_AWB_ASSIGN_STRING);
										
										getRtoDAO().saveCustomerRecord(details);
										
									}
								}
							} else {
								result = "AWB keys are not available";
								return result;
							}
						}
						result = totalAWB+" AWB keys are assigned to "+branchSize+" branches having total "+recordCount+" records";
					}
				}
			}
		} catch(Exception e) {
			result = "Failed to assign new AWB";
			logger.error(e);
			e.printStackTrace();
		}
		return result;
	}
}
