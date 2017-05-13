package com.indutech.gnd.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.indutech.gnd.bo.BankBO;
import com.indutech.gnd.bo.CoreFilesBO;
import com.indutech.gnd.bo.CreditCardDetailsBO;
import com.indutech.gnd.bo.MasterCourierServiceBO;
import com.indutech.gnd.bo.Response;
import com.indutech.gnd.bo.ShopFloorDetails;
import com.indutech.gnd.dto.CreditCardDetails;
import com.indutech.gnd.dto.MasterCourierService;
import com.indutech.gnd.service.RTOServiceImpl;
import com.indutech.gnd.service.ShopFloorService;
import com.indutech.gnd.util.DataInitialization;

@Controller("shopFloorCont")
@RequestMapping(value = "/shopfloor")
public class ShopFloorCont {
	
	Logger logger = Logger.getLogger(ShopFloorCont.class);
	
	@Autowired
	private RTOServiceImpl rtoService;
	@Autowired
	private ShopFloorService shopFloorService;

	Logger log = Logger.getLogger(ShopFloorCont.class);


	public RTOServiceImpl getRtoService() {
		return rtoService;
	}

	public void setRtoService(RTOServiceImpl rtoService) {
		this.rtoService = rtoService;
	}
	

	@SuppressWarnings("unused")
	@RequestMapping(value = "/test",method=RequestMethod.POST,produces={MediaType.APPLICATION_JSON_VALUE},consumes={MediaType.ALL_VALUE})
	public @ResponseBody ShopFloorDetails decisonCont(ModelMap model, Principal principal,
			@RequestParam(value="decision",required=true) int decisoin,
			@RequestParam(value="text",required=true) String text) {
		Long res=null;
		String username = null;
		ShopFloorDetails ShopFloorDetails=null;
		try {
			username = principal.getName();
			model.addAttribute("username", username);
		if((username!=null)&&text!=""){
		switch (decisoin) {
		case CardStateManagerController.CARD_STATUS_DISPATCH:
			ShopFloorDetails = getShopFloorService().cardStatusDispatchSer(Long.valueOf(text.trim()), username);
			if(ShopFloorDetails!=null){
			if(ShopFloorDetails.isBoolRes()){
				ShopFloorDetails=getShopFloorService().gettransaction(ShopFloorDetails);
			}
			}
			
			break;
		case CardStateManagerController.CARD_STATUS_DELIVER:
			ShopFloorDetails = getShopFloorService().cardStatusDeliver(Long.valueOf(text.trim()),username);
			if(ShopFloorDetails!=null){
			if(ShopFloorDetails.isBoolRes()){
				ShopFloorDetails=getShopFloorService().gettransaction(ShopFloorDetails);
			}
			}
			break;
		case CardStateManagerController.CARD_STATUS_RETURN:
			ShopFloorDetails = getShopFloorService().cardStatusReturn(Long.valueOf(text.trim()),username);
			if(ShopFloorDetails!=null){
			if(ShopFloorDetails.isBoolRes()){
				ShopFloorDetails=getShopFloorService().gettransaction(ShopFloorDetails);
			}
			}
			break;
		case CardStateManagerController.CARD_STATUS_REDISPATCH:
			ShopFloorDetails = getShopFloorService().cardStatusRedispatch(Long.valueOf(text.trim()), username);
			if(ShopFloorDetails!=null){
			if(ShopFloorDetails.isBoolRes()){
				ShopFloorDetails=getShopFloorService().gettransaction(ShopFloorDetails);
			}
			}
			break;
			
		case CardStateManagerController.CARD_STATUS_DESTROY:
			ShopFloorDetails = getShopFloorService().cardStatusDestroy(Long.valueOf(text.trim()),username);
			if(ShopFloorDetails!=null){
			if(ShopFloorDetails.isBoolRes()){
				ShopFloorDetails=getShopFloorService().gettransaction(ShopFloorDetails);
			}}
			break;
			
		case CardStateManagerController.CARD_STATUS_MANUALREJECT:
			break;
			
		case CardStateManagerController.CARD_STATUS_DEVILVEY_BY_AWB:
			ShopFloorDetails=getShopFloorService().cardStatusDeliverbyAWB(text.trim(), username);
			
			if(ShopFloorDetails!=null){
				List<ShopFloorDetails> listShopfloor=ShopFloorDetails.getShopFloorDetailsDelivery();
				for(int i=0;i<listShopfloor.size();i++){
					ShopFloorDetails detailsRTO=listShopfloor.get(i);
					ShopFloorDetails shopFloorDetail=getShopFloorService().gettransactionAWB(detailsRTO);
					ShopFloorDetails.getShopFloorDetailsDeliveryResult().add(shopFloorDetail);
				}
				if(ShopFloorDetails.getShopFloorDetailsOtherStatus()!=null&&ShopFloorDetails.getShopFloorDetailsOtherStatus().size()>0){
				String filepath=getShopFloorService().createNotInDeliverystate(ShopFloorDetails.getShopFloorDetailsOtherStatus());
				ShopFloorDetails.setFile(new File(filepath));
				}
			}
			
			break;
			
		case CardStateManagerController.CARD_STATUS_RTO_BY_AWB:
			ShopFloorDetails=getShopFloorService().cardStatusRTObyAWB(text.trim(), username);
			
			if(ShopFloorDetails!=null){
				List<ShopFloorDetails> listShopfloor=ShopFloorDetails.getShopFloorDetailsreturn();
				for(int i=0;i<listShopfloor.size();i++){
					ShopFloorDetails detailsRTO=listShopfloor.get(i);
					ShopFloorDetails shopFloorDetail=getShopFloorService().gettransactionAWB(detailsRTO);
					ShopFloorDetails.getShopFloorDetailsreturnResult().add(shopFloorDetail);
				}
				if(ShopFloorDetails.getShopFloorDetailsOtherStatus()!=null&&ShopFloorDetails.getShopFloorDetailsOtherStatus().size()>0){
				String filepath=getShopFloorService().createNotInReturnedstate(ShopFloorDetails.getShopFloorDetailsOtherStatus());
				ShopFloorDetails.setFile(new File(filepath));
				}
			}
			
			break;
			
		case PinstateManagerController.PIN_STATUS_DISPATCH:
			ShopFloorDetails = getShopFloorService().pinStatusDispatch(Long.valueOf(text.trim()),username);
			if(ShopFloorDetails!=null){
			if(ShopFloorDetails.isBoolRes()){
				ShopFloorDetails=getShopFloorService().gettransactionPin(ShopFloorDetails);
			}
			}
			break;

		
		case PinstateManagerController.PIN_STATUS_DELIVER:
			ShopFloorDetails = getShopFloorService().pinStatusDelivery(Long.valueOf(text.trim()),username);
			if(ShopFloorDetails!=null){
			if(ShopFloorDetails.isBoolRes()){
				ShopFloorDetails=getShopFloorService().gettransactionPin(ShopFloorDetails);
			}
			}
			break;

		case PinstateManagerController.PIN_STATUS_RTO:
			ShopFloorDetails = getShopFloorService().pinStatusReturned(Long.valueOf(text.trim()),username);
			if(ShopFloorDetails!=null){
			if(ShopFloorDetails.isBoolRes()){
				ShopFloorDetails=getShopFloorService().gettransactionPin(ShopFloorDetails);
			}
			}
			break;

		case PinstateManagerController.PIN_STATUS_REDISPATCH:
			ShopFloorDetails = getShopFloorService().pinStatusRedispatch(Long.valueOf(text.trim()), username);
			if(ShopFloorDetails!=null){
			if(ShopFloorDetails.isBoolRes()){
				ShopFloorDetails=getShopFloorService().gettransactionPin(ShopFloorDetails);
			}
			}
			break;
			
		case PinstateManagerController.PIN_STATUS_DESTROY:
			ShopFloorDetails = getShopFloorService().pinStatusDestroy(Long.valueOf(text.trim()),username);
			if(ShopFloorDetails!=null){
			if(ShopFloorDetails.isBoolRes()){
				ShopFloorDetails=getShopFloorService().gettransactionPin(ShopFloorDetails);
			}
			}
			break;
		}
			}
		} catch(NullPointerException ne) {
			//return new ModelAndView("login");
		}
		return ShopFloorDetails;

	}
	@RequestMapping(value = "/savetoFile",method=RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE},consumes={MediaType.ALL_VALUE})
	public @ResponseBody File decisonContSave(ModelMap model, Principal principal,
			@RequestParam(value="decision",required=true) int decisoin,
			@RequestBody List<ShopFloorDetails> masterbo) {
		
		String fileName=null;
		String file=null;
		switch (decisoin) {
		case CardStateManagerController.CARD_STATUS_DISPATCH:
		fileName="card_produced_report";
		file=getShopFloorService().createAndSaveCSV(fileName,masterbo);
			break;
		case CardStateManagerController.CARD_STATUS_DELIVER:
			fileName="card_deliver_report";
			file=getShopFloorService().createAndSaveCSV(fileName,masterbo);
			break;
		case CardStateManagerController.CARD_STATUS_RETURN:
			fileName="card_return_report";
			file=getShopFloorService().createAndSaveCSV(fileName,masterbo);
			break;
		case CardStateManagerController.CARD_STATUS_REDISPATCH:
			fileName="card_redispatch_report";
			file=getShopFloorService().createAndSaveCSV(fileName,masterbo);
			break;
			
		case CardStateManagerController.CARD_STATUS_DESTROY:
			fileName="card_destroy_report";
			file=getShopFloorService().createAndSaveCSV(fileName,masterbo);
			break;
			
		case CardStateManagerController.CARD_STATUS_MANUALREJECT:
			break;
			
		case CardStateManagerController.CARD_STATUS_DEVILVEY_BY_AWB:
			fileName="card_deivery_awb_report";
			file=getShopFloorService().createAndSaveCSV(fileName,masterbo);
			break;
			
		case CardStateManagerController.CARD_STATUS_RTO_BY_AWB:			
			fileName="card_rto_awb_report";	
			file=getShopFloorService().createAndSaveCSV(fileName,masterbo);
			break;
			
		case PinstateManagerController.PIN_STATUS_DISPATCH:
			fileName="pin_dispatch_report";
			file=getShopFloorService().createAndSaveCSVPin(fileName,masterbo);
			break;

		
		case PinstateManagerController.PIN_STATUS_DELIVER:
			fileName="pin_dispatch_report";
			file=getShopFloorService().createAndSaveCSVPin(fileName,masterbo);
			break;

		case PinstateManagerController.PIN_STATUS_RTO:
			fileName="pin_rto_report";
			file=getShopFloorService().createAndSaveCSVPin(fileName,masterbo);
			break;

		case PinstateManagerController.PIN_STATUS_REDISPATCH:
			fileName="pin_redispatch_report";
			file=getShopFloorService().createAndSaveCSVPin(fileName,masterbo);
			break;
			
		case PinstateManagerController.PIN_STATUS_DESTROY:
			fileName="pin_destroy_report";
			file=getShopFloorService().createAndSaveCSVPin(fileName,masterbo);
			break;
		
			
		}

		File sendFile=new File(file);
		return sendFile;

	}
	@RequestMapping(value = "/downloadfile")
	public @ResponseBody void downloadFile(@RequestParam("filepath") String fileloc,HttpServletResponse response){
		File file = new File(fileloc);
		InputStream is;
		try {
			is = new FileInputStream(file);
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ file.getName() + "\"");
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	
	
	@RequestMapping(value="/scanbyRSNtorto")
	public ModelAndView returnscanbyRSNtorto(ModelMap model, Principal principal){
		String username = null;
		try {
			username = principal.getName();
			model.addAttribute("username", username);
		} catch(NullPointerException ne) {
			//return new ModelAndView("login");
		}
		int size=getShopFloorService().getCountofRTo();
		ModelAndView view=new ModelAndView("scanbyRSNtorto");
		view.addObject("rtoCount",size);
		return view;
	} 
	
	@RequestMapping("/crdrtotrkmis")
	public @ResponseBody boolean crdrtotrkmis(@RequestParam("fromDate") String fromDate,@RequestParam("toDate") String toDate){
		System.out.println(fromDate+" "+toDate);
		return true;
	}
	@RequestMapping("/scanbyAWBtorto")
	public @ResponseBody ModelAndView scanbyAWBtoRTo(ModelMap model, Principal principal){
		String username=null;
		try {
			username = principal.getName();
			model.addAttribute("username", username);
		} catch(NullPointerException ne) {
			//return new ModelAndView("login");
		}
		int size=getShopFloorService().getCountofRTo();
		ModelAndView view=new ModelAndView("scanbyAWBtorto");
		view.addObject("rtoCount",size);
		return view;
	}
	@RequestMapping(value = "/home")
	public String shopFloorHome(ModelMap model, Principal principal) {
		
		try {
			String name = principal.getName();		
			model.addAttribute("username", name);
		} catch (NullPointerException ne) {
			return "login";
		}
		return "shopfloor";
		
	}
	
	@RequestMapping(value = "/carddispatch")
	public String shopFloorCardDispatch(ModelMap model, Principal principal) {
		try {
			String name = principal.getName();
			model.addAttribute("username", name);
		} catch(NullPointerException ne) {
			return "login";
		}
		return "carddispatch";
		
	}
	
	@RequestMapping(value = "/carddestroy")
	public String shopFloorCardDestroy(ModelMap model, Principal principal) {
		try {
			String name = principal.getName();
			model.addAttribute("username", name);
		} catch(NullPointerException ne) {
			return "login";
		}
		return "carddestroy";
		
	}
	
	@RequestMapping(value = "/carddilivery")
	public String shopFloorCardDilivery(ModelMap model, Principal principal) {
		try {	
			String name = principal.getName();
			model.addAttribute("username", name);
		} catch(NullPointerException ne) {
			return "login";
		}
		
		return "carddilivery";
		
	}
	
	@RequestMapping(value = "/carddeliverbyAWB")
	public String shopFloorcarddeliverbyAWB(ModelMap model, Principal principal) {
		try {	
			String name = principal.getName();
			model.addAttribute("username", name);
		} catch(NullPointerException ne) {
			return "login";
		}
		
		return "carddeliverbyAWB";
		
	}
	
	@RequestMapping(value = "/cardrtobyAWB")
	public String shopFloorcardrtobyAWB(ModelMap model, Principal principal) {
		try {	
			String name = principal.getName();
			model.addAttribute("username", name);
		} catch(NullPointerException ne) {
			return "login";
		}
		
		return "cardrtobyAWB";
		
	}

	@RequestMapping(value = "/changeAWB")
	public ModelAndView shopFloorchangeAWB(ModelMap model, Principal principal) {
		ModelAndView view = null;
		try {
			
			String name = principal.getName();
			List<MasterCourierService> listDcms = getShopFloorService()
					.getDCMSSer();
			view = new ModelAndView("changeAWB");
			model.addAttribute("username", name);
			view.addObject("dcmslist", listDcms);
		} catch (Exception ne) {
			ne.printStackTrace();
			return new ModelAndView("login");
		}

		return view;
	}
	
	
	

	@RequestMapping(value = "/getAWBbyDcms", method = RequestMethod.POST)
	public ModelAndView getAWBbyDcms(ModelMap model, Principal principal,
			@RequestParam(value = "dcmsId") Integer dcmsId) {
		ModelAndView view = new ModelAndView("changeAWB");
		try {
			String name = principal.getName();
			model.addAttribute("username", name);
		List<MasterCourierService> listDcms = getShopFloorService()
				.getDCMSSer();
		Map awb = getShopFloorService().getAWBforDcmsId(dcmsId);
		if (awb != null) {
			if(awb.get("SIZE")!=null){
				view.addObject("awbrsn", awb.get("SIZE"));
			}
			if(awb.get("AWB")!=null){
				view.addObject("awbName", awb.get("AWB"));
				view.addObject("availableAWB",awb.get("availableAWB"));
				view.addObject("totalAWB",awb.get("totalAWB"));
				view.addObject("blockedAWB",awb.get("blockedAWB"));
			}
			
		}
		view.addObject("dcmslist", listDcms);
		}
		catch (Exception ne) {
			ne.printStackTrace();
			return new ModelAndView("login");
		}
		return view;
	}

	@RequestMapping(value = "/changeAWBbyRsn", method = RequestMethod.POST)
	public ModelAndView changeAWBbyRsn(ModelMap model, Principal principal,
			@RequestParam(value = "cardpin") String cardpin,
			@RequestParam(value = "awb") String awb,
			@RequestParam(value = "rsn") Long rsn) {
		logger.info(cardpin+""+awb+""+rsn);
		
		ModelAndView view=new  ModelAndView("changeAWB");
		try {
			String name = principal.getName();
			model.addAttribute("username", name);
			List<MasterCourierService> listDcms = getShopFloorService()
					.getDCMSSer();
			

		Map map = getShopFloorService().getchangeAWBbyRsnSer(cardpin, awb, rsn);
		if(map!=null){
			if(map.get("PIN")!=null){
			view.addObject("awbrsn",map.get("PIN"));
			}
			if(map.get("CARD")!=null){
				view.addObject("awbrsn",map.get("CARD"));
			}
			if(map.get("SIZE")!=null){
				view.addObject("awbrsn",map.get("SIZE"));
			}
		}
		view.addObject("dcmslist", listDcms);
		} catch(Exception e) {
			e.printStackTrace();
			return new ModelAndView("login");
		}
		return view;

	}

	@RequestMapping(value = "/cardredispatch")
	public String shopFloorCardRedispatch(ModelMap model, Principal principal) {
		try {
			String name = principal.getName();
			model.addAttribute("username", name);
		} catch(NullPointerException ne) {
			return "login";
		}
		return "cardredispatch";
		
	}
	@RequestMapping(value = "/scanbyRSN")
	public String shopFloorscanbyRSN(ModelMap model, Principal principal) {
		try {
			String name = principal.getName();
			model.addAttribute("username", name);
		} catch(NullPointerException ne) {
			return "login";
		}
		return "scanbyRSN";
		
	}
	@RequestMapping(value = "/cardreturned")
	public String shopFloorCardReturned(ModelMap model, Principal principal) {
		try {
			String name = principal.getName();
			model.addAttribute("username", name);
		} catch(NullPointerException ne) {
			return "login";
		}
		
		return "cardreturned";
		
	}

	@RequestMapping(value = "/pindelivery")
	public String shopFloorPinDelivery(ModelMap model, Principal principal) {
		try {
			String name = principal.getName();
			model.addAttribute("username", name);
		} catch(NullPointerException ne) {
			return "login";
		}
		return "pindelivery";
		
	}
	
	@RequestMapping(value = "/pindestroy")
	public String shopFloorPinDestroy(ModelMap model, Principal principal) {
		try {	
			String name = principal.getName();
			model.addAttribute("username", name);
		} catch(NullPointerException ne) {
			return "login";
		}
		
		return "pindestroy";
		
	}
	
	@RequestMapping(value = "/pindispatch")
	public String shopFloorPinDispatch(ModelMap model, Principal principal) {
		try {
			String name = principal.getName();
			model.addAttribute("username", name);
		} catch (NullPointerException ne) {
			return "login";
		}
		return "pindispatch";
		
	}
	
	@RequestMapping(value = "/pinreturned")
	public String shopFloorPinReturned(ModelMap model, Principal principal) {
		try {
			String name = principal.getName();
			model.addAttribute("username", name);
		} catch(NullPointerException ne) {
			return "login";
		}
		return "pinreturned";
		
	}
	
	@RequestMapping(value = "/pinredispatch")
	public String shopFloorPinRedispatch(ModelMap model, Principal principal) {
		try {
			String name = principal.getName();
			model.addAttribute("username", name);
		} catch(NullPointerException ne) {
			return "login";
		}
		return "pinredispatch";
		
	}

	public ShopFloorService getShopFloorService() {
		return shopFloorService;
	}

	public void setShopFloorService(ShopFloorService shopFloorService) {
		this.shopFloorService = shopFloorService;
	}
	
	
	@RequestMapping(value = "/misImoprt")
	public String shopFloorMISImport(ModelMap model, Principal principal) {
		try {
			String name = principal.getName();
			model.addAttribute("username", name);
		} catch(NullPointerException ne) {
			return "login";
		}
		return "misImport";
		
	}
	
	
	@RequestMapping(value = "/getfilesbydate")
	public @ResponseBody List<CoreFilesBO> getFilesByDateAndBank(ModelMap model, Principal principal,
			@RequestParam(value="receiveddate") String receivedDate,
			@RequestParam(value="bankid") String bankId) {
		List<CoreFilesBO> list = null;
		ModelAndView mav = null;
//		try {
//			String name = principal.getName();
//			model.addAttribute("username", name);
//		} catch(NullPointerException ne) {
//			return new ModelAndView("login");
//		}
		try {
			logger.info("date is : "+receivedDate);
			logger.info("bank id is : "+bankId);
			list = getShopFloorService().getFilesList(receivedDate, Long.parseLong(bankId));
//			mav = new ModelAndView("changeAWBbyBranch");
//			mav.addObject("fileList", list);
			
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
		
	}
	
	
	@RequestMapping(value = "/getHomeBranchCode")
	public @ResponseBody List<String> getHomeBranchCodeList(ModelMap model, Principal principal,
			@RequestParam(value="receiveddate") String receivedDate,
			@RequestParam(value="bankid") String bankId,
			@RequestParam(value="fileid") String fileId,
			@RequestParam(value="status") String status) {
		List<String> list = null;
		ModelAndView mav = null;
//		try {
//			String name = principal.getName();
//			model.addAttribute("username", name);
//		} catch(NullPointerException ne) {
//			return new ModelAndView("login");
//		}
		try {
			logger.info("date is : "+receivedDate);
			logger.info("bank id is : "+bankId);
			list = getShopFloorService().getHomeBranchCodeList(receivedDate, Long.parseLong(bankId),Long.parseLong(fileId), Long.parseLong(status));
			
			
			
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
		
	}
	
	
	@RequestMapping(value = "/getRecordsList")
	public @ResponseBody ModelAndView getRecordsList(ModelMap model, Principal principal,
			@RequestParam(value="receiveddate") String receivedDate,
			@RequestParam(value="bankid") String bankId,
			@RequestParam(value="fileid") String fileId,
			@RequestParam(value="status") String status,
			@RequestParam(value="homeBranchCode") String homeBranchCode) {
		List<CreditCardDetailsBO> list = null;
		String msg = null;
		ModelAndView mav = null;
		try {
			String name = principal.getName();
			model.addAttribute("username", name);
		} catch(NullPointerException ne) {
			return new ModelAndView("login");
		}
		try {
			logger.info("date is : "+receivedDate);
			logger.info("bank id is : "+bankId);
			List<BankBO> bankList = getShopFloorService().getBankList();
			
			list = getShopFloorService().getRecordsList(receivedDate, Long.parseLong(bankId),Long.parseLong(fileId), Long.parseLong(status), homeBranchCode);
			if(list == null || list.size() == 0 ) {
				msg= "No Records Found";
			}
			mav = new ModelAndView("changeAWBbyBranch");
			mav.addObject("records",list);
			mav.addObject("msg", msg);
			mav.addObject("bankList",bankList);
			
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return mav;
		
	}
	
	
	
	@RequestMapping(value = "/assignnewawb", method=RequestMethod.POST)
	public @ResponseBody Response changeAWB(ModelMap model, Principal principal,
			@RequestParam(value="detailsid") String rsn,
			@RequestParam(value="bankid") String bankId) {
		List<CreditCardDetails> list = null;
				Response response = null;
//		try {
//			String name = principal.getName();
//			model.addAttribute("username", name);
//		} catch(NullPointerException ne) {
//			return "login";
//		}
				String msg = "";
		try {
			logger.info(rsn);
			
			msg = getRtoService().ChangeAWBByHomeBranchGroup(rsn, Long.parseLong(bankId));
				
			
			 response = new Response();
			response.setResult(msg);
			logger.info("response is : "+response.getResult());
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return response;
	}
	
	@RequestMapping(value = "/assignnewawb1", consumes="application/json")
	public @ResponseBody Response changeAWB(
			@RequestBody List<CreditCardDetailsBO> list) {
//			@RequestParam(value="detailsid") List  detaisId) {
//		List<CreditCardDetails> list = null;
		
//			Gson gson = new Gson();
//		   Type type = new TypeToken<List<CreditCardDetailsBO>>() {}.getType();
//		   List<CreditCardDetailsBO> corefileList = gson.fromJson(gson.toJson(detailsList), type);
//		
				Response response = null;
				
				logger.info("list size is : "+list.size());
//		try {
//			String name = principal.getName();
//			model.addAttribute("username", name);
//		} catch(NullPointerException ne) {
//			return "login";
//		}
//		try {
//			logger.info(detaisId);
//			String msg = getRtoService().ChangeAWBByHomeBranchGroup(detaisId);
//			 response = new Response();
//			response.setResult(msg);
//			logger.info("response is : "+response.getResult());
//		} catch(Exception e) {
//			logger.error(e);
//			e.printStackTrace();
//		}
		return response;
	}
	
	@RequestMapping(value = "/changeAWBByBranch")
	public ModelAndView shopFloorchangeAWBByBranch(ModelMap model, Principal principal) {
		ModelAndView view = null;
		try {
			
			String name = principal.getName();	
			model.addAttribute("username", name);
		} catch (Exception ne) {
			return new ModelAndView("login");
		}
		try {
			List<BankBO> list = getShopFloorService().getBankList();
			view = new ModelAndView("changeAWBbyBranch");
			view.addObject("bankList",list);
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return view;
	}
	
	
	@RequestMapping(value = "/changeRTOAWB")
	public ModelAndView shopFloorchangeRTOAWBByBranch(ModelMap model, Principal principal) {
		ModelAndView view = null;
		try {
			
			String name = principal.getName();	
			model.addAttribute("username", name);
		} catch (Exception ne) {
			return new ModelAndView("login");
		}
		try {
			view = new ModelAndView("changeRTOAWB");
			view.addObject("bank", DataInitialization.getInstance().getBankInfo());
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return view;
	}
	
	@RequestMapping(value = "/gettxnbydate")
	public @ResponseBody ModelAndView getFilesByTxnDate(ModelMap model, Principal principal,
			@RequestParam(value="fromdate") String fromDate,
			@RequestParam(value="todate") String toDate,
			@RequestParam(value="bankid") String bankId,
			@RequestParam(value="status") String status) {
		List<CreditCardDetailsBO> list = null;
		ModelAndView mav = null;
		try {
			String name = principal.getName();
			model.addAttribute("username", name);
		} catch(NullPointerException ne) {
			return new ModelAndView("login");
		}
		try {
			logger.info("from date is : "+fromDate);
			logger.info("to date is : "+fromDate);
			logger.info("status is : "+status);
			list = getShopFloorService().getRecordsList(fromDate, toDate,  status, bankId);
			mav = new ModelAndView("changeRTOAWB");
			List<BankBO> bankBO = DataInitialization.getInstance().getBankInfo();
			BankBO bankObj = null;
			for(BankBO bank : bankBO) {
				if(bank.getBankId() == Long.parseLong(bankId)) {
					bankObj = bank;
					break;
				}
			}
			mav.addObject("bank", bankBO);
			mav.addObject("records", list);
			mav.addObject("bankObj",bankObj);
			
			
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return mav;
		
	}
	
	@RequestMapping(value = "/productionUpload")
	public String cardProductionByRSNUpload(ModelMap model, Principal principal) {
		try {
			String name = principal.getName();
			model.addAttribute("username", name);
		} catch(NullPointerException ne) {
			return "login";
		}
		return "cardProductionUpload";
		
	}
	
	
	@RequestMapping(value = "/productionUploadRSNList")
	public @ResponseBody Response cardProductionByRSNUpload(ModelMap model, Principal principal, @RequestParam("file") MultipartFile file) {
//		try {
//			String name = principal.getName();
//			model.addAttribute("username", name);
//		} catch(NullPointerException ne) {
//			return "login";
//		}	
			Response result = null;
			List<MasterCourierServiceBO> list = null;
			try {
				logger.info("filename is : "+file.getOriginalFilename());
				
				result = getShopFloorService().uploadProductionRSNList(file, principal.getName());

			} catch (Exception e) {
				result.setResult("failed to upload");
				result.setFilepath(null);
				e.printStackTrace();
			
		}
		return result;
		
	}

	
}
