package com.indutech.gnd.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.camel.Exchange;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.indutech.gnd.bo.BankBO;
import com.indutech.gnd.bo.CardSearchQuery;
import com.indutech.gnd.bo.CardSearchResult;
import com.indutech.gnd.bo.CardSearchResultList;
import com.indutech.gnd.bo.CoreFilesBO;
import com.indutech.gnd.bo.CreditCardDetailsBO;
import com.indutech.gnd.bo.CrunchifyInMemoryCache;
import com.indutech.gnd.bo.RecordDetailsBO;
import com.indutech.gnd.bo.RecordEventBO;
import com.indutech.gnd.controller.SbiController;
import com.indutech.gnd.dao.SbisearchDAOImpl;
import com.indutech.gnd.dto.Bank;
import com.indutech.gnd.dto.CoreFiles;
import com.indutech.gnd.dto.CreditCardDetails;
import com.indutech.gnd.dto.MasterBank;
import com.indutech.gnd.dto.RecordEvent;
import com.indutech.gnd.jasper.reports.SbiReportGeneration;
import com.indutech.gnd.util.CustomEncryption;

//SbisearchServiceImpl
public class SbisearchServiceImpl {
	Logger logger = Logger.getLogger(SbisearchServiceImpl.class);
	common.Logger log = common.Logger.getLogger(SbisearchServiceImpl.class);
	private static final String CARD_SEARCH_RESULT = "CARD_SEARCH_RESULT";

	private static final String CARD_SEARCH_QUERY_QUEUE = "CARD_SEARCH_QUERY_QUEUE";

	private static final String CARD_SEARCH_RESULT_QUEUE = "CARD_SEARCH_RESULT_QUEUE";

	@Autowired
	private SbisearchDAOImpl sbisearchDAO;

	public SbisearchDAOImpl getSearchDAO() {
		return sbisearchDAO;
	}

	public void setSearchDAO(SbisearchDAOImpl sbisearchDAO) {
		this.sbisearchDAO = sbisearchDAO;
	}


	@Transactional
	public List<CreditCardDetailsBO> getRecords(Map<String, String> reqMap) {
		List<CreditCardDetails> list = getSearchDAO().searchRecords(reqMap);
		return buildCreditCardDetailsBO(list);
	}

	@Transactional
	public List<Bank> getdetailsToaddproductServ()throws Exception {
		List<Bank> resList = getSearchDAO().getdetailsToaddproductDao();
		List<BankBO> bankbo=buildMasterBankBo(resList);
		return resList;
	}

	private List<BankBO> buildMasterBankBo(List<Bank> resList) {
		List<BankBO> listBankBo=new ArrayList<BankBO>();
		
		for(Bank bank:resList){
			BankBO bankbo=new BankBO();
			bankbo.setBankId(bank.getBankId());
			bankbo.setShortCode(bankbo.getShortCode());
			
		}
		return listBankBo;
	}

	@Transactional
	public List<CreditCardDetailsBO> searchbybankServNull(String accountNo,Integer offset)throws Exception {
		List<CreditCardDetailsBO> listbo = null;
		try {
			List<Object[]> list = getSearchDAO().searchbybankDao(accountNo,offset);
			listbo = buildCreditCardDetailsArrayBO(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listbo;
	}
	@Transactional
	public int searchbybankCountserv(String accountNo) throws Exception{
	return	getSearchDAO().searchbybankCountDao(accountNo);
	}
	@Transactional
	public List<CreditCardDetailsBO> searchbybankServ(String bank,
			String accountNo,Integer offset) throws Exception{
		List<CreditCardDetailsBO> listbo = null;
		try {
			List<Object[]> list = getSearchDAO().searchbybankaccDao(bank,
					accountNo,offset);
			listbo = buildCreditCardDetailsArrayBO(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listbo;
	}
	@Transactional
	public List<CreditCardDetailsBO> searchbybankaccService(String bank,Integer offset) throws Exception{
		List<CreditCardDetailsBO> listbo = null;
		List<Object[]> list = getSearchDAO().searchbybankaccDao(bank,offset);
		listbo = buildCreditCardDetailsArrayBO(list);
		return listbo;
	}
	@Transactional
	public int searchbybankCountServa(String bank)throws Exception{
		return getSearchDAO().searchbybankCountDaoa(bank);
	}
	@Transactional
	public int searchbybankaccCountservice(String bank,
			String accountNo)throws Exception {
				return getSearchDAO().searchbybankaccCountDao(bank, accountNo);
		
		
	}
	
	
	
	@Transactional
	public void branchwiseNull(String branch, String dateFrom)throws Exception {
		getSearchDAO().branchwiseDaoNull(branch, dateFrom);
	}

	@Transactional
	public List<CreditCardDetailsBO> branchwise(String bank, String branch, String branchType,
			String dateFrom,Integer offset) throws Exception {
		List<CreditCardDetailsBO> listbo = null;

		List<Object[]> objj = getSearchDAO().branchwiseDao(bank, branch, branchType,
				dateFrom,offset);
		listbo = buildCreditCardDetailsArrayBO(objj);
		return listbo;
	}
	@Transactional
	public int  branchwisecount(String bank,String branch, String branchType, String dateFrom)throws Exception{
		return getSearchDAO().branchwiseDaoCount(bank, branch, branchType,
				dateFrom);
	}
	
	
	
	
	
	@Transactional
	public List<CreditCardDetailsBO> cardawbser(String cardawb,Integer offset) throws Exception {
		List<CreditCardDetails> list = getSearchDAO().cardawbdao(cardawb,offset);
		return buildCreditCardDetailsBO(list);
	}
	@Transactional
	public int cardabdcountservice(String cardaw) throws Exception{
	return getSearchDAO().cardabdcountdao(cardaw);
	}
	@Transactional
	public List<CreditCardDetailsBO> pinawbser(String pinawb,Integer offse)throws Exception{
		List<CreditCardDetails> list = getSearchDAO().pinawbdao(pinawb,offse);
		return buildCreditCardDetailsBO(list);
	}
	@Transactional
    public int pinawbcountserv(String pinawb) throws Exception{
    	return getSearchDAO().pinawbcountdao(pinawb);
    }
	@Transactional
	public List<CreditCardDetailsBO> mobileser(String mobile,Integer offset)throws Exception{
		//log.info("i am in mobile number Service");
		List<CreditCardDetails> list = getSearchDAO().mobiledao(mobile,offset);
		//log.info("i am out mobile number Service");
		return buildCreditCardDetailsBO(list);
	}
	@Transactional
	public int mobileCount(String mobile) throws Exception{
		int x=getSearchDAO().mobileCountdao(mobile);
		return x;
	}
	
	@Transactional
	public List<CreditCardDetailsBO> rsnSer(String rsn,Integer offset)throws Exception{
		List<CreditCardDetails> list = getSearchDAO().rsndao(rsn,offset);
		return buildCreditCardDetailsBO(list);
	}
	
	@Transactional
	public List<CreditCardDetailsBO> custIdSer(String customerId,Integer offset)throws Exception{
		List<CreditCardDetails> list = getSearchDAO().getCustId(customerId,offset);
		return buildCreditCardDetailsBO(list);
	}
	
	@Transactional
	public int rsnSerCount(String rsn)throws Exception{
		return getSearchDAO().rsndaoCount(rsn);
	}
	
	@Transactional
	public int custIdSerCount(String customerId)throws Exception{
		return getSearchDAO().custIddaoCount(customerId);
	}

	@Transactional
	public CardSearchResultList getDBRecords(Exchange exchange) {
		CardSearchQuery cardSearchQuery = (CardSearchQuery) exchange.getIn()
				.getBody();
		// TODO
		// ..need to map card search query to DAO
		// List<CreditCardDetails> list = getSearchDAO().searchRecords();
		return buildCardSearchResultList(null);
	}

	public void persistCardResultInMemory(Exchange exchange) {
		CardSearchResultList cardSearchResultList = (CardSearchResultList) exchange
				.getIn().getBody();
		CrunchifyInMemoryCache<String, CardSearchResultList> inMemoryCache = new CrunchifyInMemoryCache<String, CardSearchResultList>(
				1000, 100, 100000);
		inMemoryCache.put(CARD_SEARCH_RESULT, cardSearchResultList);
	}



	private CardSearchResultList buildCardSearchResultList(
			List<CreditCardDetails> list) {
		CardSearchResultList cardSearchResultList = new CardSearchResultList();
		for (CreditCardDetails creditCardDetails : list) {
			CardSearchResult cardSearchResult = new CardSearchResult();
			cardSearchResult.setAccountNo(creditCardDetails.getPrimaryAcctNo());
			cardSearchResult.setApplicationNo("");
			cardSearchResult.setApprovedStatus("");
			cardSearchResult.setBankName("");
			// TODO
			cardSearchResultList.getCardSearchResults().add(cardSearchResult);
		}
		// TODO Auto-generated method stub
		return cardSearchResultList;
	}

	@SuppressWarnings({ "rawtypes" })
	
	@Transactional
	public HashMap<String, Object> getRecordEvent(Long creditCardDetailsId) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		RecordDetailsBO record = new RecordDetailsBO();
		String masterAWB = null;
		String pinAWB = null;
		CreditCardDetails details = getSearchDAO().getRecord(
				creditCardDetailsId);
		record.setCreditCardDetailsId(details.getCreditCardDetailsId());
		record.setEmbossName(details.getEmbossName());
		record.setBranch(details.getHomeBranchCode());
		record.setCardNumber(details.getPanMasked());
		record.setBank(details.getInstitutionId());
		record.setProduct(details.getProduct());
		record.setCity(details.getCity());
		record.setBankName((getSearchDAO().getBankDetails(details.getBankId())).getShortCode());	
		record.setAddress(details.getAddr1());
		record.setAddress2(details.getAddr2());
		record.setAddress3(details.getAddr3());
		record.setAddress4(details.getAddr4());
		record.setPincode(details.getPin());
		record.setMobileNo(details.getMobileNo());
		record.setProcessedBranch(details.getProcessedBranchCode());
		record.setCustomerId(details.getCustomerId());
		record.setCardNumber(details.getPanMasked() != null ? CustomEncryption.decryptMaskFirst4Last4(details.getPanMasked()) : "");
		record.setRsn(details.getRsn());
		record.setRecordStatus(CardStateManagerService.buildStatus(details
				.getStatus()));
		record.setCardDispatchAWB(details.getCardAWB() != null ? details
				.getCardAWB() : " ");
		record.setPinDispatchAWB(details.getPinAWB() != null ? details
				.getPinAWB() : " ");

		/*
		 * if (details.getStatus() > 3) { masterAWB = getSearchDAO().getCardAWB(
		 * details.getCreditCardDetailsId()); pinAWB =
		 * getSearchDAO().getPinAWB(details.getCreditCardDetailsId());
		 * record.setCardDispatchAWB(masterAWB);
		 * record.setPinDispatchAWB(pinAWB); }
		 */

		map.put("customer", record);
		List<RecordEvent> list = getSearchDAO().getRecordEvent(
				details.getCreditCardDetailsId());
		List<RecordEventBO> recordList = new ArrayList<RecordEventBO>();
		Iterator itr = list.iterator();
		while (itr.hasNext()) {
			RecordEvent event = (RecordEvent) itr.next();
			RecordEventBO eventBO = buildRecordEvent(event);
			recordList.add(eventBO);
		}
		// list.add(details);
		// recordHistory.add(buildCreditCardDetailsBO(list));
		// recordHistory.add(buildRecordEvent(event));
		map.put("event", recordList);
		return map;
	}

	public RecordEventBO buildRecordEvent(RecordEvent event) {
		RecordEventBO eventBO = new RecordEventBO();
		eventBO.setDescription(event.getDescription());
		eventBO.setEventDate(event.getEventDate());
		eventBO.setEventId(event.getEventId());
		eventBO.setId(event.getId());
		eventBO.setRecordId(event.getRecordId());
		return eventBO;
	}

	public List<CreditCardDetailsBO> buildCreditCardDetailsBO(
			List<CreditCardDetails> list) {

		List<CreditCardDetailsBO> records = new ArrayList<CreditCardDetailsBO>();
		if (list.size() > 0) {
			Iterator iterator = list.iterator();
			while (iterator.hasNext()) {
				CreditCardDetails cards = (CreditCardDetails) iterator.next();
				CreditCardDetailsBO details = new CreditCardDetailsBO();
				details.setCreditCardDetailsId(cards.getCreditCardDetailsId());
				details.setPrimaryAcctNo(cards.getPrimaryAcctNo());
				details.setCustomerId(cards.getCustomerId());
				details.setEmbossName(cards.getEmbossName());
				details.setHomeBranchCode(cards.getHomeBranchCode());
				details.setProduct(cards.getProduct());
				details.setRsn(cards.getRsn());
				details.setRecordStatus(CardStateManagerService
						.buildRecordStatus(cards.getStatus()));
				details.setCardStatus(CardStateManagerService
						.buildCardStatus(cards.getStatus()));
				details.setPinStatusString(PinStateManagerService
						.buildPinStatus(cards.getPinstatus()));
				records.add(details);

			}
		}
		SbiController.creditCardBoHash.put("mobileno", records);
		return records;
	}

	public List<CreditCardDetailsBO> buildCreditCardDetailsArrayBO(
			List<Object[]> list) {

		List<CreditCardDetailsBO> records = new ArrayList<CreditCardDetailsBO>();
		if (list.size() > 0) {
			for (Object[] credit : list) {

				CreditCardDetailsBO details = new CreditCardDetailsBO();
				if ((credit[0] != null)
						&& (!String.valueOf(credit[0]).isEmpty())) {
					details.setCreditCardDetailsId(Long.valueOf(String
							.valueOf(credit[0])));
				}
				if ((credit[1] != null)
						&& (!String.valueOf(credit[1]).isEmpty())) {
					details.setPrimaryAcctNo(String.valueOf(credit[1]));
				}
				if ((credit[2] != null)
						&& (!String.valueOf(credit[2]).isEmpty())) {
					details.setEmbossName(String.valueOf(credit[2]));
				}
				if ((credit[3] != null)
						&& (!String.valueOf(credit[3]).isEmpty())) {
					details.setHomeBranchCode(String.valueOf(credit[3]));
				}
				if ((credit[4] != null)
						&& (!String.valueOf(credit[4]).isEmpty())) {
					details.setProduct(String.valueOf(credit[4]));
				}
				if ((credit[5] != null)
						&& (!String.valueOf(credit[5]).isEmpty())) {
					details.setRecordStatus(CardStateManagerService
							.buildRecordStatus(Long.valueOf(String
									.valueOf(credit[5]))));
				}
				if ((credit[6] != null)
						&& (!String.valueOf(credit[6]).isEmpty())) {
					details.setCardStatus(CardStateManagerService
							.buildCardStatus(Long.valueOf(String
									.valueOf(credit[6]))));
				}
				if ((credit[7] != null)
						&& (!String.valueOf(credit[7]).isEmpty())) {
					details.setPinStatusString(PinStateManagerService
							.buildPinStatus(Long.valueOf(String
									.valueOf(credit[7]))));

				}
				if ((credit[8] != null)
						&& (!String.valueOf(credit[8]).isEmpty())) {

					details.setCustomerId(String.valueOf(credit[8]));
				}
				if ((credit[9] != null)
						&& (!String.valueOf(credit[9]).isEmpty())) {

					details.setRsn(Long.valueOf(String
							.valueOf(credit[9])));
				}
				
				records.add(details);

			}
		}
		SbiController.creditCardBoHash.put("mobileno", records);
		return records;
	}




	public CoreFilesBO buildCoreFileBO(CoreFiles file) {
		CoreFilesBO fileBO = new CoreFilesBO();
		fileBO.setId(file.getId());
		fileBO.setFilename(file.getFilename());
		fileBO.setFileTypeData(buildFileType(file.getFileType()));
		fileBO.setLineCount(file.getLineCount());
		fileBO.setReceivedDate(file.getReceivedDate());

		fileBO.setStatusData(buildFileStatus(file.getStatus()));
		fileBO.setDescription(file.getDescription());

		return fileBO;
	}

	public String getMobileReportSer(List<CreditCardDetailsBO> crdeit,String titl,HttpServletResponse response) throws Exception{
	
			SbiReportGeneration sbireport = new SbiReportGeneration();
			String fileloc=sbireport.genrateReportForMobileNo(crdeit,titl);
			File file = new File(fileloc);
			InputStream is = new FileInputStream(file);

			// MIME type of the file
			response.setContentType("application/octet-stream");
			// Response header
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ file.getName() + "\"");
			// Read from the file and write into the response
			OutputStream os = response.getOutputStream();
			byte[] buffer = new byte[1024];
			int len;
			while ((len = is.read(buffer)) != -1) {
				os.write(buffer, 0, len);
			}
			os.flush();
			os.close();
			is.close();
			file.delete();
			return fileloc;
		
		
	}
	public String buildFileStatus(Long status) {
		String statusName = null;
		if (status == 1)
			statusName = "REJECTED";
		if (status == 2)
			statusName = "HOLD";
		if (status == 3)
			statusName = "APPROVED";
		if (status == 4)
			statusName = "AUF_CONVERTED";
		return statusName;
	}

	public String buildFileType(Long fileType) {
		String fileTypeData = null;
		if (fileType == 1)
			fileTypeData = "CORE FILE";
		if (fileType == 2)
			fileTypeData = "AUF FILE";

		return fileTypeData;

	}
}
