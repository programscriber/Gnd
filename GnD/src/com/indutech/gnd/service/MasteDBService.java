package com.indutech.gnd.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.drools.command.GetSessionClockCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.indutech.gnd.bo.BankBO;
import com.indutech.gnd.bo.BranchBO;
import com.indutech.gnd.bo.DistrictBO;
import com.indutech.gnd.bo.EmailConfigarationBO;
import com.indutech.gnd.bo.MasterAWBBO;
import com.indutech.gnd.bo.MasterCourierServiceBO;
import com.indutech.gnd.bo.ProductBO;
import com.indutech.gnd.bo.StateBO;
import com.indutech.gnd.dao.MasterDBdao;
import com.indutech.gnd.dto.Bank;
import com.indutech.gnd.dto.Branch;
import com.indutech.gnd.dto.District;
import com.indutech.gnd.dto.EmailConfigaration;
import com.indutech.gnd.dto.MasterAWB;
import com.indutech.gnd.dto.MasterBank;
import com.indutech.gnd.dto.MasterCourierService;
import com.indutech.gnd.dto.Product;
import com.indutech.gnd.dto.State;
import com.indutech.gnd.emailService.EmailLaunchService;
import com.indutech.gnd.enumTypes.AufFormat;
import com.indutech.gnd.enumTypes.Status;
import com.indutech.gnd.statusenum.FileStatusEnum;
import com.indutech.gnd.util.DataInitialization;

@Component("masteDBService")
public class MasteDBService {
public static int STATE_STATUS_ACTIVE=1;
	@Autowired
	public MasterDBdao masterDBdao;
	
	
	@Transactional
	public List getdistictAndState() {
		List resultBO = new ArrayList();
		List result = getMasterDBdao().getdistctAndStateDao();
		List<BankBO> bankbo = buildMasterBankBO((List<Bank>) result.get(0));
		List<StateBO> statebo = buildStateBo((List<State>) result.get(1));
		resultBO.add(bankbo);
		resultBO.add(statebo);
		return resultBO;
	}
	
	@Transactional
	public List getdistictAndState(Long district,Long state,Long status) {
		
		StateBO stateboadd=new StateBO();
		DistrictBO distbo=new DistrictBO();
		List resultBO = new ArrayList();
		List result = getMasterDBdao().getdistctAndStateDao();
		List<BankBO> bankbo = buildMasterBankBO((List<Bank>) result.get(0));

		List<StateBO> statebo = buildStateBo((List<State>) result.get(1));
		for(int i=0;i<statebo.size();i++){
			if((long)statebo.get(i).getStateCode()==(long)Long.valueOf(state)){
				stateboadd.setStateName(statebo.get(i).getStateName());
				stateboadd.setStateCode(statebo.get(i).getStateCode());
				statebo.remove(i);
			}
		}
		statebo.add(0, stateboadd);
		List<DistrictBO> dist=getDistrictervice(Long.valueOf(state));	
		DistrictBO distBo=builddist(district,state);
		for(int i=0;i<dist.size();i++){
			if((long)dist.get(i).getDistrictCode()==(long)distBo.getDistrictCode()){
				distbo.setDistrictName(dist.get(i).getDistrictName());
				distbo.setDistrictCode(dist.get(i).getDistrictCode());
				dist.remove(i);
			}
		}
	
		dist.add(0, distbo);
		resultBO.add(bankbo);
		resultBO.add(statebo);
		resultBO.add(dist);
		return resultBO;
	}

	@Transactional
	public List<DistrictBO> getDistrictervice(Long stateId) {
		System.out.println("getDistrictervice");
		List<District> result=getMasterDBdao().getDistrictDAO(stateId);
		List<DistrictBO> districtbo = buildDristictBo((List<District>) result);
		
		return districtbo;

	}
	private List<DistrictBO> buildDristictBo(List<District> list) {
		List<DistrictBO> districtBOlist = new ArrayList<DistrictBO>();
		int count = list.size();
		for (int i = 0; i < count; i++) {
			District district = list.get(i);
			DistrictBO districtBo=new DistrictBO();
			districtBo.setId(district.getId());
			districtBo.setDistrictCode(district.getDistrictCode());
			districtBo.setDistrictName(district.getDistrictName());
			districtBo.setStateId(district.getStateId());
			districtBOlist.add(districtBo);
		}
		
		return districtBOlist;
	}

	private List<StateBO> buildStateBo(List<State> list) {
		List<StateBO> statelistBO = new ArrayList<StateBO>();
		if(list!=null){
		int count = list.size();
		for (int i = 0; i < count; i++) {
			State state = list.get(i);
			StateBO stateBo=new StateBO();
			stateBo.setId(state.getId());
			stateBo.setStateCode(state.getStateCode());
			stateBo.setStateName(state.getStateName());
			stateBo.setStatus(state.getStatus());
			statelistBO.add(stateBo);
		}
		}
		return statelistBO;
	}
	@Transactional
	public boolean addBranchTo(String branchshortcode, Long bankID, String lcpcCode,
			String address1, String addres3, Long stateCode, String pincode,
			String branchName, Long status, String lcpcBranch, String address2,
			String addres4, Long district, String phone, String email, String isNonCardIssueBranch) {
		Branch branch=new Branch();
		branch.setShortCode(branchshortcode);
		branch.setBankId(bankID);
		branch.setAddress1(address1);
		branch.setAddress2(address2);
		branch.setAddress3(addres3);
		branch.setAddress4(addres4);
		branch.setStateCode(stateCode);
		branch.setPinCode(pincode);
		branch.setBranchName(branchName);
		branch.setStatus(status);
		branch.setLcpcName(lcpcCode!=null?String.valueOf(lcpcCode):null);
		branch.setLcpcBranch(lcpcBranch);
		branch.setDistrictCode(district);
		branch.setPhoneNumber(phone);
		branch.setEmailAddress(email);
		branch.setIsNonCardIssueBranch(Integer.valueOf(isNonCardIssueBranch));
		boolean addBrachBool=getMasterDBdao().getCheckForExistingBranchAndSave(branch);
		DataInitialization.branchData = null;
		return addBrachBool;
		
		
	}
	@Transactional
	public boolean editBranchTo(String branchshortcode, Long bankID, String lcpcCode,
			String address1, String addres3, Long stateCode, String pincode,
			String branchName, Long status, String lcpcBranch, String address2,
			String addres4, Long district, String phone, String email, String isNonCardIssueBranch) {
		Branch branch=new Branch();
		branch.setShortCode(branchshortcode);
		branch.setBankId(bankID);
		branch.setAddress1(address1);
		branch.setAddress2(address2);
		branch.setAddress3(addres3);
		branch.setAddress4(addres4);
		branch.setStateCode(stateCode);
		branch.setPinCode(pincode);
		branch.setBranchName(branchName);
		branch.setStatus(status);
		branch.setLcpcName(lcpcCode!=null?String.valueOf(lcpcCode):null);
		branch.setLcpcBranch(lcpcBranch);
		branch.setDistrictCode(district);
		branch.setPhoneNumber(phone);
		branch.setEmailAddress(email);
		branch.setIsNonCardIssueBranch(Integer.valueOf(isNonCardIssueBranch));
		boolean addBrachBool=getMasterDBdao().getCheckForExistingBranchAndSaveedit(branch);
		return addBrachBool;
		
		
	}
	
	
	
	
	
	
	@SuppressWarnings("rawtypes")
	@Transactional
	public List addproductSer(Map parmMapService) {
		Product product = new Product();
		if (parmMapService.get("productCode").toString() != null) {
			product.setShortCode(parmMapService.get("productCode").toString());
		}
		if (parmMapService.get("productName").toString() != null) {
			product.setProductName(parmMapService.get("productName").toString());
		}

		if (parmMapService.get("bank").toString() != null) {
			product.setBankId(Long.valueOf(parmMapService.get("bank")
					.toString()));
		}

		if (parmMapService.get("bin").toString() != null) {
			product.setBin(parmMapService.get("bin").toString());
		}

		if (parmMapService.get("cardType").toString() != null) {
			product.setTypeId(Long.valueOf(parmMapService.get("cardType")
					.toString()));
		}

		if (parmMapService.get("OperationGnD").toString() != null) {
			product.setStatus(Long.valueOf(parmMapService.get("OperationGnD")
					.toString()));
			;
		}

		if (parmMapService.get("dcms").toString() != null) {
			product.setDcmsId(Long.valueOf(parmMapService.get("dcms")
					.toString()));
		}
		if (parmMapService.get("network").toString() != null) {
			product.setNetworkId(Long.valueOf(parmMapService.get("network")
					.toString()));
		}

		if (parmMapService.get("fourthApplicable").toString() != null) {
			product.setForthLineRequired(Long.valueOf(parmMapService.get(
					"fourthApplicable").toString()));
		}

		if (parmMapService.get("photoCard").toString() != null) {
			product.setPhotoCard(Long.valueOf(parmMapService.get("photoCard")
					.toString()));
		}
		List result = getMasterDBdao().addproductDao(product);
		DataInitialization.productData = null;
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Transactional
	public List getdetailsToaddproductServ() {
		List resList = getMasterDBdao().getdetailsToaddproductDao();
		return resList;
	}

	@SuppressWarnings("rawtypes")
	@Transactional
	public List productSearchServ(Map produServi) {
		List buildResult = new ArrayList();
		List resList = getMasterDBdao().productSearchDao(produServi);
		if (resList != null && resList.size() > 0) {
			for (int i = 0; i <= resList.size() - 1; i++) {
				Object product = resList.get(i);
				Product pro = (Product) product;
				ProductBO productbo = new ProductBO();
				productbo.setShortCode(pro.getShortCode());
				productbo.setProductName(pro.getProductName());
				productbo.setBin(pro.getBin());
				productbo.setStatusString(buildStatus(pro.getStatus()));
				productbo.setFourthLineReqString(buildStatus(pro
						.getForthLineRequired()));
				productbo.setPhotoCardString(buildStatus(pro.getPhotoCard()));
				buildResult.add(productbo);
			}
		}
		return buildResult;
	}

	private String buildStatus(Long id) {
		String result = " ";
		if (id == 1) {
			result = "YES";
		}else{
			result = "NO";
		}
		return result;
	}

	public MasterDBdao getMasterDBdao() {
		return masterDBdao;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public List branchSearchServ() {
		List resultReturn = new ArrayList();
		List result = getMasterDBdao().branchSearchDao();
		if (result != null && !result.isEmpty()) {
			List<Branch> branch = (List<Branch>) result.get(0);
			List<Bank> masterBank = (List<Bank>) result.get(1);

			List<BranchBO> branchBoList = buildBranch(branch);
			List<BankBO> bankBoList = buildMasterBankBO(masterBank);
			resultReturn.add(branchBoList);
			resultReturn.add(bankBoList);
			
		}

		/*
		 * if (!result.isEmpty()) { for (int i = 0; i < result.size(); i++) {
		 * Object obj[] = result.get(i); BranchSearchBo branchBo = new
		 * BranchSearchBo(); branchBo.setBankCode(obj[0] !=
		 * null?obj[0].toString():"");
		 * branchBo.setBranchName(obj[1]!=null?obj[1].toString():"");
		 * branchBo.setBranchCode(obj[2]!=null?obj[2].toString():"");
		 * branchBo.setIfscCode(obj[3]!=null?obj[3].toString():"");
		 * resultReturn.add(branchBo); } }
		 */
		return resultReturn;
	}
	
	@Transactional
	public List branchSearch() {
		List<BankBO> bankBoList = null;
		List result = getMasterDBdao().branchSearchDaoImpl();
		if (result != null && !result.isEmpty()) {
			 bankBoList = buildMasterBankBO(result);
		}
		return bankBoList;
	}


	private List<BankBO> buildMasterBankBO(List<Bank> masterBank) {
		List<BankBO> listmasterBo = new ArrayList<BankBO>();
		int masterSize = masterBank.size();
		if(masterBank!=null){
		for (int i = 0; i < masterSize; i++) {
			Bank masteBank = masterBank.get(i);
			BankBO bankBo = new BankBO();
			bankBo.setId(masteBank.getId());
			bankBo.setBankId(masteBank.getBankId());
			bankBo.setShortCode(masteBank.getShortCode());
			bankBo.setBankName(masteBank.getBankName());
			bankBo.setShortCode(masteBank.getShortCode());
			bankBo.setBankCode(masteBank.getBankCode());
			bankBo.setStatusString(buildBranchStatusString(masteBank.getStatus()));
			bankBo.setPrefix(masteBank.getPrefix());
			bankBo.setLPAWBBranchGroup(buildStatus(masteBank.getLPAWBBranchGroup()!=null?masteBank.getLPAWBBranchGroup():-1));
			bankBo.setAufFormat(buildFileStatus(masteBank.getAufFormat()));
			listmasterBo.add(bankBo);
		}
		}
		return listmasterBo;
	}

	private String buildFileStatus(Integer aufFormat) {
		String result=null;
		for(AufFormat sta:AufFormat.values()){
			if(aufFormat==Integer.valueOf(sta.getFileStatus())){
				result=String.valueOf(sta);
				break;
			}
		}
		return result;
	}

	@Transactional
	public List searchbranchSer(Map branchMapSer) {
		List sendClient = new ArrayList();
		// List resultReturn = new ArrayList();

		List<Branch> result1 = getMasterDBdao().searchbranchSer(branchMapSer);

		List result = getMasterDBdao().branchSearchDaoImpl();
		if (result != null && result.size() > 0) {
//			List<Branch> branch = (List<Branch>) result1.get(0);
//			List<Bank> masterBank = (List<Bank>) result.get(1);

			List<BranchBO> branchBoList = buildBranch(result1);
			List<BankBO> bankBoList = buildMasterBankBO(result);
			sendClient.add(branchBoList);
			sendClient.add(bankBoList);
		}

		List<BranchBO> branchbo = buildBranch(result1);

		sendClient.add(branchbo);
		return sendClient;

	}

	private List<BranchBO> buildBranch(List<Branch> result) {
		List<BranchBO> listBO = new ArrayList<BranchBO>();
		if(result!=null){
		for (int i = 0; i < result.size(); i++) {
			Branch branch = result.get(i);
			BranchBO branchBO = new BranchBO();
			branchBO.setShortCode(branch.getShortCode());
			branchBO.setBranchName(branch.getBranchName());
			branchBO.setLiveStatus(branch.getLiveStatus());
			branchBO.setIfscCode(branch.getIfscCode());
			branchBO.setStatus(branch.getStatus());
			branchBO.setMicr(branch.getMicr());
			branchBO.setPhoneNumber(branch.getPhoneNumber());
			branchBO.setEmailAddress(branch.getEmailAddress());
			branchBO.setTan(branch.getTan());
			branchBO.setBankId(branch.getBankId());
			branchBO.setDistrictCode(branch.getDistrictCode());
			branchBO.setStateCode(branch.getStateCode());
			branchBO.setAddress1(branch.getAddress1());
			branchBO.setAddress2(branch.getAddress2());
			branchBO.setAddress3(branch.getAddress3());
			branchBO.setAddress4(branch.getAddress4());
			branchBO.setBranchType(branch.getBranchType());
			branchBO.setDistrictString(builddist(branch.getDistrictCode(),branch.getStateCode()).getDistrictName());
			branchBO.setStateString(buildState(branch.getStateCode()));
			branchBO.setBankNameString(buildBankdto(branch.getBankId()));
			branchBO.setModcode(branch.getModcode());
			branchBO.setModule(branch.getModule());
			branchBO.setCircleCode(branch.getCircleCode());
			branchBO.setCircle(branch.getCircle());
			branchBO.setNetwork(branch.getNetwork());
			branchBO.setRegion(branch.getRegion());
			branchBO.setPopCode(branch.getPopCode());
			branchBO.setPopGroup(branch.getPopGroup());
			branchBO.setStringStatus(buildStatus(branch.getStatus()));
			branchBO.setBranchStatusString(buildBranchStatusString(branch.getStatus()));
			branchBO.setLcpcName(branch.getLcpcName());
			branchBO.setLcpcBranch(branch.getLcpcBranch());
			branchBO.setPinCode(branch.getPinCode());
			branchBO.setIsNonCardIssueBranch(branch.getIsNonCardIssueBranch());
			// branchBO.setBpr(this.bpr);
			// branchBO.setGcc(this.gcc);
			listBO.add(branchBO);
		}
		}
		return listBO;
	}

	private String buildBranchStatusString(Long status) {
		String result=null;
		for(Status sta:Status.values()){
			if(status==Long.valueOf(sta.getStatus())){
				result=String.valueOf(sta);
				break;
			}
		}
		return result;
	}
	private String buildBankdto(Long bankId) {
		String bankShortCode=null;
		List<Bank> bank=getMasterDBdao().getBankdto(bankId);
		List<BankBO> bankbo=buildMasterBankBO(bank);
		if(bankbo!=null){
			bankShortCode=bankbo.get(0).getShortCode();
		}
		return bankShortCode;
	}
	private String buildState(Long stateCode) {
		StateBO stateBo=null;
		String State=null;
		List<State>  statedto=getMasterDBdao().getState(stateCode);
		List<StateBO> statebolis=buildStateBo(statedto);
		if(statebolis!=null&&statebolis.size()>0){
			stateBo=statebolis.get(0);
			State=stateBo.getStateName();
		}
		return State;
	}
	private DistrictBO builddist(Long districtCode,Long statecode) {
		DistrictBO districtBo=null;
		String district=null;
		List<District> districtdto=getMasterDBdao().getDistrictsend(districtCode,statecode);
		List<DistrictBO> districtbolis=buildDristictBo(districtdto);
		if(districtbolis!=null&&districtbolis.size()>0){
			districtBo=districtbolis.get(0);
			district=districtBo.getDistrictName();
		}
		return districtBo;
	}
	public void setMasterDBdao(MasterDBdao masterDBdao) {
		this.masterDBdao = masterDBdao;
	}
	@Transactional
	public BranchBO getBranch(String branchshortcode, String bank) {
		BranchBO branchbo=null;
		List<Branch> branch=getMasterDBdao().getMasterBranch(branchshortcode,bank);
		List<BranchBO> branchBo=buildBranch(branch);
		if(branchBo!=null){
			branchbo=branchBo.get(0);
		}
		return branchbo;
	}
	
	@Transactional
	public boolean addstateser(String stateNamemodel) {
		Random rnd = new Random();
		int n = 100+ rnd.nextInt(900);
		State state=new State();
		state.setStateCode(Long.valueOf(n));
		state.setStateName(stateNamemodel);
		state.setStatus(Long.valueOf(STATE_STATUS_ACTIVE));
		boolean result=getMasterDBdao().addStateTo(state);
		return result;
	}
	@Transactional
	public List<StateBO> getState() {
		List<State>  staetlit=getMasterDBdao().getState();
		List<StateBO>  stateBo=buildStateBo(staetlit);
		return stateBo;
	}
	@Transactional
	public boolean addDistrict(String stateDistrict, String districtval) {
		Random rnd = new Random();
		int n = 10+ rnd.nextInt(500);
		District district=new District();
		district.setStateId(Long.valueOf(stateDistrict));
		district.setDistrictName(districtval);
		district.setDistrictCode(Long.valueOf(n));
		boolean result=getMasterDBdao().addDistrict(district);
		return result;
	}
	@Transactional
	public List<MasterCourierServiceBO> getServiceProvider() {
		List<MasterCourierService> courierServicelist=getMasterDBdao().getServiceProvider();
		List<MasterCourierServiceBO> mastercourielistBO=buildCourierServiceList(courierServicelist);
		/*List<MasterCourierServiceBO> masterCourierBolist=null;
		if(courierServicelist!=null&&courierServicelist.size()>=1){
			masterCourierBolist=buildCourierServiceList((List<MasterCourierService>)courierServicelist.get(0));
			sendlist.add(masterCourierBolist);
			sendlist.add(courierServicelist.get(1));
		}
		*/
		
		return mastercourielistBO;
	}

	private List<MasterCourierServiceBO>  buildCourierServiceList(
			List<MasterCourierService> courierServicelist) {
		List<MasterCourierServiceBO> masterCourierBolist=new ArrayList<MasterCourierServiceBO>();
		if(courierServicelist!=null){
			for(int i=0;i<courierServicelist.size();i++){
				MasterCourierServiceBO masterCourierBO=new MasterCourierServiceBO();
				MasterCourierService masterCourier=courierServicelist.get(i);
				masterCourierBO.setId(masterCourier.getId());
				masterCourierBO.setServiceProviderName(masterCourier.getServiceProviderName());
				masterCourierBO.setEmail(masterCourier.getEmail());
				masterCourierBO.setPhone(masterCourier.getPhone());
				masterCourierBO.setContactPersionName(masterCourier.getContactPersionName());
				masterCourierBolist.add(masterCourierBO);
			}
		}
		return masterCourierBolist;
	}
	@Transactional
	public List<MasterAWBBO> getListAvailAWB(Long serviceid, Integer count, Boolean awbblock) {
		List<MasterAWB> masterAWB=getMasterDBdao().getListAWBDAO(serviceid,count,awbblock);
		List<MasterAWBBO> masterBo=buildMaserAWB(masterAWB);
		
		return masterBo;
	}

	private List<MasterAWBBO> buildMaserAWB(List<MasterAWB> masterAWB) {
		List<MasterAWBBO> masterAWBBolist=new ArrayList<MasterAWBBO>();	
		List<MasterCourierServiceBO> masterCourierBolist=getServiceProvider();
		if(masterAWB!=null){
		for(int i=0;i<masterAWB.size();i++){
			  	//for(int j=0;j<masterAWB.size();j++){
			  		MasterAWB master=masterAWB.get(i);
					//if((long)masterCourierBolist.get(i).getId()==(long)master.getServiceProviderId()&&(long)master.getStatus()==(long)Long.valueOf(Status.valueOf("ACTIVE").getStatus())){
						MasterAWBBO masterAWBBO=new MasterAWBBO();
						masterAWBBO.setAwbId(master.getAwbId());
						
						masterAWBBO.setServiceProviderId(master.getServiceProviderId());
						masterAWBBO.setServiceProviderName(buildServiceProviderName(masterCourierBolist,master.getServiceProviderId()));
						masterAWBBO.setStatus(master.getStatus());
						masterAWBBO.setStatusString(buildStatusServiceProvider(master.getStatus()));
						
						masterAWBBO.setAwbName(master.getAwbName());
						masterAWBBolist.add(masterAWBBO);
					}
				}
			  	
		//	}
		//}
		return masterAWBBolist;
	}

	private String buildStatusServiceProvider(Long status) {
		String statusString="";
		for(Status sta:Status.values()){
			if((long)Long.valueOf(sta.getStatus())==(long)status){
				statusString=sta.toString();
				break;
			}
		}
		return statusString;
	}

	private String buildServiceProviderName(List<MasterCourierServiceBO> masterCourierBolist,Long serviceProviderId) {
		String serviceProviderName="";
		for(int i=0;i<masterCourierBolist.size();i++){
			MasterCourierServiceBO masterBo=masterCourierBolist.get(i);
			if((long)masterBo.getId()==(long)serviceProviderId){
				serviceProviderName=masterBo.getServiceProviderName();
				break;
			}	
		}
		return serviceProviderName;
	}

	@Transactional
	public long getCountAWB(Long valueOf) {
		long count=getMasterDBdao().countService(valueOf);
		return count;
	}
	@Transactional
	public Map<String,String> getEmailFiles() {
		Map<String,String> fileMap=new HashMap<String, String>();
		String destinationPath=null;
		String toEmail=null;
		String fileName=null;
		String toemail=null;
		List<EmailConfigaration> emaildto=getMasterDBdao().getEmailProperties();
		if(emaildto!=null){
			destinationPath=emaildto.get(0).getDestinationPath();
			if(destinationPath!=null){
				File folder = new File(destinationPath);
				File[] listOfFiles = folder.listFiles();
				if(listOfFiles!=null){
				for (File file : listOfFiles) {
					if (file.isFile()) {
						String[] tokens =file.getName().split("_");
						fileName=tokens[tokens.length-5]+"_"+tokens[tokens.length-4]+"_"+tokens[tokens.length-3]+"_"+tokens[tokens.length-2];
						toEmail = tokens[tokens.length-1];
						toEmail = toEmail.substring(0, toEmail.length()-4);
					}
					fileMap.put(fileName, toEmail);
				}
			}
		}
	  }
		return fileMap;
	}
	@Transactional
	public void getSendEmail(String unselectemail) {
		List<EmailConfigaration> emailConfig=getMasterDBdao().getEmailProperties();
		HashMap<String, String> emailProperties=new HashMap<String, String>();
		List<String> unselectedlist=new ArrayList<String>();
		String unselectedstr[]=unselectemail.split(",");
		for(String unselectfileName:unselectedstr){
			unselectedlist.add(unselectfileName);
		}
		if(emailConfig!=null&&emailConfig.size()>0){
			for(int i=0;i<emailConfig.size();i++){
				EmailConfigaration emailConfigclass=emailConfig.get(i);
				emailProperties.put("bccEmail" ,emailConfigclass.getBccEmail());
				emailProperties.put("destinationPath" ,emailConfigclass.getDestinationPath());
				emailProperties.put("username" ,emailConfigclass.getUsername());
				emailProperties.put("password" ,emailConfigclass.getPassword());
				emailProperties.put("fromEmail" ,emailConfigclass.getFromEmail());				
				emailProperties.put("ccEmail" ,emailConfigclass.getCcEmail());
				emailProperties.put("host" ,emailConfigclass.getHost());
				emailProperties.put("port" ,emailConfigclass.getPort());
				emailProperties.put("subject",emailConfigclass.getSubject());
				}
			}
		EmailLaunchService email=new EmailLaunchService(emailProperties,unselectedlist);
		email.readFilesSendEmail();
	  }
	@Transactional
	public boolean geteditEmailConfig(String userName, String password,
			String subject, String fromemail, String ccemail, String bccemail,
			String destinationpath, String host, String port, String id) {
		
		EmailConfigaration emailconfig=new  EmailConfigaration();
		emailconfig.setUsername(userName);
		emailconfig.setPassword(password);
		emailconfig.setSubject(subject);
		emailconfig.setFromEmail(fromemail);
		emailconfig.setCcEmail(ccemail);
		emailconfig.setBccEmail(bccemail);
		emailconfig.setDestinationPath(destinationpath);
		emailconfig.setHost(host);
		emailconfig.setPort(port);
		emailconfig.setId(Long.valueOf(id));
	boolean	result=getMasterDBdao().updateemailField(emailconfig);
	return result;
		
	}
	@Transactional
	public List<EmailConfigarationBO> getEmailFields() {
		List<EmailConfigaration> emailconfig=getMasterDBdao().getEmailProperties();
		return buildEmailConfig(emailconfig);
		
		
	}

	private List<EmailConfigarationBO> buildEmailConfig(List<EmailConfigaration> emailconfig) {
		List<EmailConfigarationBO> listemailBo=new ArrayList<EmailConfigarationBO>();
		if(emailconfig!=null){
			for(int a=0;a<emailconfig.size();a++){
				EmailConfigaration emailConf=emailconfig.get(a);
				EmailConfigarationBO email=new EmailConfigarationBO();
				email.setId(emailConf.getId());
				email.setUsername(emailConf.getUsername());
				email.setPassword(email.getPassword());
				email.setHost(emailConf.getHost());
				email.setPort(emailConf.getPort());
				email.setCcEmail(emailConf.getCcEmail());
				email.setBccEmail(emailConf.getBccEmail());
				email.setDestinationPath(emailConf.getDestinationPath());
				email.setSubject(emailConf.getSubject());
				email.setFromEmail(emailConf.getFromEmail());
				listemailBo.add(email);
			}
		}
		return listemailBo;
	 }
	@Transactional
	public List<BankBO> getBank() {
		List<Bank> returnBnak=getMasterDBdao().getBankList();
		return	buildMasterBankBO(returnBnak);
		
	}


	@Transactional
	public List<BankBO> getBanklistid(String bank) {
		List<BankBO> returnBnak=null;
		if(bank.trim().equals("true")){
			returnBnak=buildMasterBankBO(getMasterDBdao().getBankList());
		}else if(Long.valueOf(bank) != null){
			returnBnak=	buildMasterBankBO(getMasterDBdao().getBankdto(Long.valueOf(bank)));
		}
		return returnBnak;
	}
	@Transactional
	public boolean getAddBank(String bank, String bankName, String shortcode,
			String status, String bankcode, String prefix, String lpawb,
			String aufformat) {
		Bank bankdto=new Bank();
		bankdto.setBankId(Long.valueOf(bank));
		bankdto.setBankName(bankName);
		bankdto.setShortCode(shortcode);
		bankdto.setStatus(Long.valueOf(status));
		bankdto.setBankCode(bankcode);
		bankdto.setPrefix(prefix);
		bankdto.setLPAWBBranchGroup(Long.valueOf(lpawb));
		bankdto.setAufFormat(Integer.valueOf(aufformat));
		boolean result=getMasterDBdao().getCheckAndSave(bankdto);
		return result;
	}
}
	
