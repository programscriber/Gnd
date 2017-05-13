package com.indutech.gnd.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.apache.activemq.ActiveMQConnection;
import org.apache.camel.Exchange;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.indutech.gnd.bo.CardSearchQuery;
import com.indutech.gnd.bo.CardSearchResult;
import com.indutech.gnd.bo.CardSearchResultList;
import com.indutech.gnd.bo.CoreFilesBO;
import com.indutech.gnd.bo.CreditCardDetailsBO;
import com.indutech.gnd.bo.CrunchifyInMemoryCache;
import com.indutech.gnd.bo.RecordDetailsBO;
import com.indutech.gnd.bo.RecordEventBO;
import com.indutech.gnd.dao.FileDAOImpl;
import com.indutech.gnd.dao.GNDDAOImpl;
import com.indutech.gnd.dao.SearchDAOImpl;
import com.indutech.gnd.dto.CoreFiles;
import com.indutech.gnd.dto.CreditCardDetails;
import com.indutech.gnd.dto.RecordEvent;
import com.indutech.gnd.jaxb.CardSearchQueryMarshaller;
import com.indutech.gnd.jaxb.CardSearchQueryUnMarshaller;
import com.indutech.gnd.jaxb.CardSearchResultMarshaller;
import com.indutech.gnd.jaxb.CardSearchResultUnMarshaller;
import com.indutech.gnd.util.CustomEncryption;

@Component("searchService")
public class SearchServiceImpl implements SearchService {
	
	Logger logger = Logger.getLogger(SearchServiceImpl.class);

	private static final String CARD_SEARCH_RESULT = "CARD_SEARCH_RESULT";

	private static final String CARD_SEARCH_QUERY_QUEUE = "CARD_SEARCH_QUERY_QUEUE";

	private static final String CARD_SEARCH_RESULT_QUEUE = "CARD_SEARCH_RESULT_QUEUE";

	@Autowired
	private SearchDAOImpl searchDAO;

	@Autowired
	private FileDAOImpl fileDAO;

	@Autowired
	private CardSearchQueryMarshaller cardSearchQueryMarshaller;
	
	@Autowired
	private GNDDAOImpl gndDAO;
	
	@Autowired
	private CardSearchQueryUnMarshaller cardSearchQueryUnMarshaller;

	@Autowired
	private CardSearchResultMarshaller cardSearchResultMarshaller;

	@Autowired
	private CardSearchResultUnMarshaller cardSearchResultUnMarshaller;

	@Autowired
	private MessageProducerService messageProducerService;
	
	

	public GNDDAOImpl getGndDAO() {
		return gndDAO;
	}

	public void setGndDAO(GNDDAOImpl gndDAO) {
		this.gndDAO = gndDAO;
	}

	public FileDAOImpl getFileDAO() {
		return fileDAO;
	}

	public void setFileDAO(FileDAOImpl fileDAO) {
		this.fileDAO = fileDAO;
	}

	public SearchDAOImpl getSearchDAO() {
		return searchDAO;
	}

	public void setSearchDAO(SearchDAOImpl searchDAO) {
		this.searchDAO = searchDAO;
	}

	@Override
	@Transactional
	public List<CreditCardDetailsBO> getRecords(Map<String, String> reqMap) {
		List<CreditCardDetails> list = getSearchDAO().searchRecords(reqMap);
		return buildCreditCardDetailsBO(list);
	}

	public void postQueryMessage() {
		// TODO
		// ...need to map search query to this object

		CardSearchQuery cardSearchQuery = new CardSearchQuery();
		String cardSearchQueryXML = null;
		try {
			cardSearchQueryXML = cardSearchQueryMarshaller
					.marshallCardSearchQuery(cardSearchQuery);
			getMessageProducerService().produceMessage(cardSearchQueryXML,
					CARD_SEARCH_QUERY_QUEUE,
					ActiveMQConnection.DEFAULT_BROKER_URL);
		} catch (JAXBException e) { // TODO Auto-generated catch block
			logger.error(e);
			e.printStackTrace();
		}

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

	public void postResultMessage(Exchange exchnage) {
		String cardSearchResultXML = null;
		try {
			cardSearchResultXML = (String) exchnage.getIn().getBody();
			getMessageProducerService().produceMessage(cardSearchResultXML,
					CARD_SEARCH_RESULT_QUEUE,
					ActiveMQConnection.DEFAULT_BROKER_URL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
	@Override
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
		record.setIssueBranch(details.getIssueBranchCode());
		record.setProcessedBranch(details.getProcessedBranchCode());
		record.setCardNumber(details.getPanMasked() != null ? CustomEncryption.decryptMaskFirst4Last4(details.getPanMasked()) : "");
		record.setBank(details.getInstitutionId());
		record.setProduct(details.getProduct());
		record.setCity(details.getCity());
		record.setAddress(details.getAddr1());
		record.setAddress2(details.getAddr2());
        record.setAddress3(details.getAddr3());
        record.setAddress4(details.getAddr4());
		record.setPincode(details.getPin());
		record.setRsn(details.getRsn());
		record.setMobileNo(details.getMobileNo() != null ? details.getMobileNo() : "");
		record.setCustomerId(details.getCustomerId() != null ? details.getCustomerId() : "");
		record.setRecordStatus(CardStateManagerService.buildStatus(details.getStatus()));
		record.setCardDispatchAWB(details.getCardAWB() != null ? details.getCardAWB() : " ");
		record.setPinDispatchAWB(details.getPinAWB() != null ? details.getPinAWB() : " ");
		record.setBankName((getGndDAO().getBankDetails(details.getBankId())).getShortCode());
/*
		if (details.getStatus() > 3) {
			masterAWB = getSearchDAO().getCardAWB(
					details.getCreditCardDetailsId());
			pinAWB = getSearchDAO().getPinAWB(details.getCreditCardDetailsId());
			record.setCardDispatchAWB(masterAWB);
			record.setPinDispatchAWB(pinAWB);
		} 
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
		if(list.size() > 0){
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
		 details.setRecordStatus(CardStateManagerService.buildRecordStatus(cards.getStatus()));
		 details.setCardStatus(CardStateManagerService.buildCardStatus(cards.getStatus()));
		 details.setPinStatusString(PinStateManagerService.buildPinStatus(cards.getPinstatus()));
		 records.add(details);
		 
		 }}
		return records;
	}

	@Override
	@Transactional
	public List<CoreFilesBO> getFiles(Map<String,String> parametermap ) {
		List<CoreFiles> list = getFileDAO().getFiles(parametermap);
		List<CoreFilesBO> fileList = new ArrayList<CoreFilesBO>();
		if (list.size() > 0) {
			Iterator itr = list.iterator();
			while (itr.hasNext()) {
				CoreFiles file = (CoreFiles) itr.next();
				fileList.add(buildCoreFileBO(file));

			}
		}

		return fileList;
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

	public CardSearchQueryMarshaller getCardSearchQueryMarshaller() {
		return cardSearchQueryMarshaller;
	}

	public void setCardSearchQueryMarshaller(
			CardSearchQueryMarshaller cardSearchQueryMarshaller) {
		this.cardSearchQueryMarshaller = cardSearchQueryMarshaller;
	}

	public CardSearchQueryUnMarshaller getCardSearchQueryUnMarshaller() {
		return cardSearchQueryUnMarshaller;
	}

	public void setCardSearchQueryUnMarshaller(
			CardSearchQueryUnMarshaller cardSearchQueryUnMarshaller) {
		this.cardSearchQueryUnMarshaller = cardSearchQueryUnMarshaller;
	}

	public CardSearchResultMarshaller getCardSearchResultMarshaller() {
		return cardSearchResultMarshaller;
	}

	public void setCardSearchResultMarshaller(
			CardSearchResultMarshaller cardSearchResultMarshaller) {
		this.cardSearchResultMarshaller = cardSearchResultMarshaller;
	}

	public CardSearchResultUnMarshaller getCardSearchResultUnMarshaller() {
		return cardSearchResultUnMarshaller;
	}

	public void setCardSearchResultUnMarshaller(
			CardSearchResultUnMarshaller cardSearchResultUnMarshaller) {
		this.cardSearchResultUnMarshaller = cardSearchResultUnMarshaller;
	}

	public MessageProducerService getMessageProducerService() {
		return messageProducerService;
	}

	public void setMessageProducerService(
			MessageProducerService messageProducerService) {
		this.messageProducerService = messageProducerService;
	}

}
