package com.indutech.gnd.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.indutech.gnd.bo.CreditCardDetailsBO;
import com.indutech.gnd.dto.Bank;
import com.indutech.gnd.dto.MasterBank;
import com.indutech.gnd.exception.GnDGenericException;
import com.indutech.gnd.service.SbisearchServiceImpl;

@Controller
public class SbiController {
	Logger logger = Logger.getLogger(SbiController.class);
	common.Logger log = common.Logger.getLogger(SbiController.class);
	public static List<CreditCardDetailsBO> creditCardBo=new ArrayList<CreditCardDetailsBO>();
	public static Map<String,List<CreditCardDetailsBO>> creditCardBoHash=new HashMap<String,List<CreditCardDetailsBO>>();
	@Autowired
	private SbisearchServiceImpl sbisearchService;

	public SbisearchServiceImpl getSearchService() {
		return sbisearchService;
	}

	public void setSearchService(SbisearchServiceImpl searchService) {
		this.sbisearchService = searchService;
	}

	@RequestMapping(value="/frame")
	public ModelAndView frame(){
		return new ModelAndView("framesbi");
	}
	@RequestMapping(value="/frame1")
	public ModelAndView frame1(){
		return new ModelAndView("frame1");
	}
	@RequestMapping(value="/frame2")
	public ModelAndView frame2(){
		return new ModelAndView("frame2");
	}
	
	
	@RequestMapping(value = "/cardawb")
	// , method = RequestMethod.POST
	public @ResponseBody ModelAndView cardqwb(ModelMap model, Principal principal,
			@RequestParam(value = "cardawb", required = true) String cardawb,
			@RequestParam(value = "offset", required = false) Integer offset) {
		try {
			String name = principal.getName();
			model.addAttribute("username", name);
		} catch(NullPointerException ne) {
			return new ModelAndView("login");
		}
		if(SbiController.creditCardBoHash.get("mobileno")!=null){
			SbiController.creditCardBoHash.get("mobileno").clear();
			}
		ModelAndView view = null;
		String res = "No Record Found";
		try {
			if ((!cardawb.isEmpty()) && (cardawb != null)) {

				logger.info(cardawb);

				List<CreditCardDetailsBO> lis = getSearchService().cardawbser(
						cardawb, offset);
				int count = getSearchService().cardabdcountservice(cardawb);
				if ((lis != null) && (lis.size() > 0)) {
					view = new ModelAndView("awbsearch");
					view.addObject("records", lis);
					view.addObject("offset", offset);
					view.addObject("count", count);
					view.addObject("cardawb", cardawb);

				} else {
					view = new ModelAndView("awbsearch");
					view.addObject("msg", res);
					view.addObject("cardawb", cardawb);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			view = new ModelAndView("exception");
		}
		return view;
	}
	@RequestMapping(value = "/cardawbReport")
	public @ResponseBody void cardawbReport(HttpServletResponse response){
		String card="Card AWB Result";
		try{
			List<CreditCardDetailsBO> crdeit=creditCardBoHash.get("mobileno");
			logger.info("the size of the report is "+crdeit.size());
			getSearchService().getMobileReportSer(crdeit,card,response);

		}catch(Exception e){

		}
		
	}
	@RequestMapping(value = "/pinawb")
	//, method = RequestMethod.POST)
	public @ResponseBody ModelAndView pinawb(ModelMap model, Principal principal,
			@RequestParam(value = "pinawb", required = true) String pinawb,
			@RequestParam(value = "offset", required = false) Integer offset) {
		try {
			String name = principal.getName();
			model.addAttribute("username", name);
		} catch(NullPointerException ne) {
			return new ModelAndView("login");
		}
		if(SbiController.creditCardBoHash.get("mobileno")!=null){
			SbiController.creditCardBoHash.get("mobileno").clear();
			}
		ModelAndView view = null;
		String res = "No Record Found";
		try {
			if (!pinawb.isEmpty() && (pinawb != null)) {
				List<CreditCardDetailsBO> lis = getSearchService().pinawbser(
						pinawb, offset);
				int count = getSearchService().pinawbcountserv(pinawb);
				if ((lis != null) && (lis.size() > 0)) {
					view = new ModelAndView("pinawb");
					view.addObject("records", lis);
					view.addObject("offset", offset);
					view.addObject("count", count);
					view.addObject("pinawb", pinawb);

				} else {
					view = new ModelAndView("pinawb");
					view.addObject("msg", res);
					view.addObject("pinawb", pinawb);
				}
			}
		} catch (Exception e) {
			view = new ModelAndView("exception");
		}
		return view;
	}
	@RequestMapping(value = "/pinawbReport")
	public @ResponseBody void pinawbReport(HttpServletResponse response){
		String pin="Pin AWB result";
		ModelAndView view=null;
		try{
			List<CreditCardDetailsBO> crdeit=creditCardBoHash.get("mobileno");
			logger.info("the size of the report is "+crdeit.size());
			getSearchService().getMobileReportSer(crdeit,pin,response);
//			view=new ModelAndView("pinawb");
//			view.addObject("downloadResult", "Downloaded Successfully");
		}catch(Exception e){
//			view=new ModelAndView("pinawb");
//			view.addObject("downloadResult","Failed to download the report");
			e.printStackTrace();
		}
		//return view;
	}
	@RequestMapping(value = "/mobileno")
	// , method = RequestMethod.POST
	public @ResponseBody ModelAndView mobileNo(ModelMap model, Principal principal,
			@RequestParam(value = "mobile", required = true) String mobile,
			@RequestParam(value = "offset", required = false) Integer offset) {
		try {
			String name = principal.getName();
			model.addAttribute("username", name);
		} catch(NullPointerException ne) {
			return new ModelAndView("login");
		}
		//log.info("i am in mobile number Controller");
		//System.out.println("the mobile numbwe ua"+mobile);
		if(SbiController.creditCardBoHash.get("mobileno")!=null){
		SbiController.creditCardBoHash.get("mobileno").clear();
		}
		ModelAndView view = null;
		String res = "No Record Found";
		try {
			if (!mobile.isEmpty() && (mobile != null)) {
				List<CreditCardDetailsBO> lis = getSearchService().mobileser(
						mobile, offset);
				int x = getSearchService().mobileCount(mobile);
				if ((lis != null) && (lis.size() > 0)) {
					view = new ModelAndView("sbirecordsearch");
					view.addObject("records", lis);
					view.addObject("offset", offset);
					view.addObject("count", x);
					view.addObject("mobile", mobile);
				} else {
					view = new ModelAndView("sbirecordsearch");
					view.addObject("msg", res);
					view.addObject("mobile", mobile);
					
				}
			}
		} catch (Exception e) {
			view = new ModelAndView("exception");
		}
		//log.info("i am out mobile number Controller");
		return view;
	}

	@RequestMapping(value = "/mobileReport")
	public @ResponseBody void mobileReport(HttpServletResponse response){
		String title="Mobile search Result";
		ModelAndView view=null;
		try{
			List<CreditCardDetailsBO> crdeit=creditCardBoHash.get("mobileno");
			getSearchService().getMobileReportSer(crdeit,title,response);
			//view=new ModelAndView("sbirecordsearch");
			//view.addObject("downloadResult", "Downloaded Successfully");
			
		}catch(Exception e){
//			view=new ModelAndView("sbirecordsearch");
//			view.addObject("downloadResult","Failed to download the report");
			e.printStackTrace();
		}
	//	return view;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/branchwise")
	//, method = RequestMethod.POST
	public @ResponseBody ModelAndView branchwise(ModelMap model, Principal principal,
			@RequestParam(value = "bank", required = true) String bank,
			@RequestParam(value = "branch", required = true) String branch,
			@RequestParam(value = "branchType", required = true) String branchType,
			@RequestParam(value = "dateFrom", required = true) String dateFrom,
			@RequestParam(value = "offset", required = false) Integer offset) {
		try {
			String name = principal.getName();
			model.addAttribute("username", name);
		} catch(NullPointerException ne) {
			return new ModelAndView("login");
		}
		if(SbiController.creditCardBoHash.get("mobileno")!=null){
			SbiController.creditCardBoHash.get("mobileno").clear();
			}
		ModelAndView view = null;
		List<CreditCardDetailsBO> result = null;
		List<Bank> sendBack=null;
		String res = "No Record Found";
		int count;
		try {
			String bankLable=null;
			if ((!bank.isEmpty()) && (branch != null)
					&& ((dateFrom != null) && (!dateFrom.isEmpty()))) {
				logger.info("branch type is " +branchType);
				result = getSearchService().branchwise(bank, branch, branchType, dateFrom,offset);
				count=getSearchService().branchwisecount(bank, branch, branchType, dateFrom);
				List<Bank> resList = getSearchService().getdetailsToaddproductServ();
				if (result.size() == 0) {
					view = new ModelAndView("branchwise");
					view.addObject("msg", res);
					sendBack=new ArrayList();
					for(int i=0;i<resList.size();i++){
						if(Long.valueOf(bank)==resList.get(i).getBankId()){
							bankLable=resList.get(i).getShortCode();
							resList.remove(i);
						}
						sendBack.add(resList.get(i));
					}
					view.addObject("critMasBank", sendBack);
					view.addObject("bank",bank);
					view.addObject("bankLabel",bankLable);
					view.addObject("branch",branch);
					view.addObject("dateFrom",dateFrom);
					
				} else {
					view = new ModelAndView("branchwise");
					sendBack=new ArrayList();
					for(int i=0;i<resList.size();i++){
						if(Long.valueOf(bank)==resList.get(i).getBankId()){
							bankLable=resList.get(i).getShortCode();
							resList.remove(i);
						}
						sendBack.add(resList.get(i));
					}
					view.addObject("records", result);
					view.addObject("critMasBank", sendBack);
					view.addObject("bank",bank);
					view.addObject("branch",branch);
					view.addObject("bankLabel",bankLable);
					view.addObject("dateFrom",dateFrom);
					view.addObject("count", count);
					view.addObject("offset", offset);
				}
			}
		} catch (Exception e) {
			view = new ModelAndView("exception");
		}
		return view;
	}
	@RequestMapping(value = "/branchwiseReport")
	public @ResponseBody void branchwiseReport(HttpServletResponse response){
		String branch="Branch wise report result";
		ModelAndView view=null;
		List resList=null;
		try{
			resList = getSearchService().getdetailsToaddproductServ();
			List<CreditCardDetailsBO> crdeit=creditCardBoHash.get("mobileno");
			logger.info("the size of the report is "+crdeit.size());
			getSearchService().getMobileReportSer(crdeit,branch,response);
//			view=new ModelAndView("branchwise");
//			view.addObject("downloadResult", "Downloaded Successfully");
//			view.addObject("critMasBank", resList.get(0));
		}catch(Exception e){
//			view=new ModelAndView("branchwise");
//			view.addObject("critMasBank", resList.get(0));
//			view.addObject("downloadResult","Failed to download the report");
		}
		//return view;
	}
	@RequestMapping(value = "/rsn")//, method = RequestMethod.POST
	public @ResponseBody ModelAndView rsnCont(ModelMap model, Principal principal,
			@RequestParam(value = "rsn", required = true) String rsn,
			@RequestParam(value = "offset", required = false) Integer offset) {
		try {
			String name = principal.getName();
			model.addAttribute("username", name);
		} catch(NullPointerException ne) {
			return new ModelAndView("login");
		}
		
		if(SbiController.creditCardBoHash.get("mobileno")!=null){
			SbiController.creditCardBoHash.get("mobileno").clear();
			}
		ModelAndView view = null;
		String res = "No Record Found";
		try {
			if (!rsn.isEmpty() && (rsn != null)) {
				List<CreditCardDetailsBO> lis = getSearchService().rsnSer(rsn,offset);
				int count=getSearchService().rsnSerCount(rsn);
				if ((lis != null) && (lis.size() > 0)) {
					view = new ModelAndView("rsn");
					view.addObject("records", lis);
					view.addObject("offset", offset);
					view.addObject("count", count);
					view.addObject("rsn", rsn);
				} else {
					view = new ModelAndView("rsn");
					view.addObject("msg", res);
					view.addObject("rsn", rsn);
				}
			}
		} catch (Exception e) {
			view = new ModelAndView("exception");
		}
		return view;
	}
	@RequestMapping(value = "/rsnReport")
	public @ResponseBody void rsnReport(HttpServletResponse response){
		String rsn="RSN report";
		ModelAndView view=null;
		try{
			List<CreditCardDetailsBO> crdeit=creditCardBoHash.get("mobileno");
			logger.info("the size of the report is "+crdeit.size());
			getSearchService().getMobileReportSer(crdeit,rsn,response);
//			view=new ModelAndView("rsn");
//			view.addObject("downloadResult", "Downloaded Successfully");
		}catch(Exception e){
//			view=new ModelAndView("rsn");
//			view.addObject("downloadResult","Failed to download the report");
		}
		//return view;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/searchbybank")
	// , method = RequestMethod.POST
	public @ResponseBody ModelAndView searchbybankCont(ModelMap model, Principal principal,
			@RequestParam(value = "bank", required = true) String bank,
			@RequestParam(value = "acctNo", required = true) String accountNo,
			@RequestParam(value = "offset", required = false) Integer offset) {
		try {
			String name = principal.getName();
			model.addAttribute("username", name);
		} catch(NullPointerException ne) {
			return new ModelAndView("login");
		}
		if(SbiController.creditCardBoHash.get("mobileno")!=null){
			SbiController.creditCardBoHash.get("mobileno").clear();
			}
		List<Bank> sendBack=null;
		ModelAndView view = null;
		String msg = "No records found";
		List<CreditCardDetailsBO> result = null;
		try {
			String bankLable=null;
			List<Bank> resList = getSearchService().getdetailsToaddproductServ();

			
			if ((!bank.isEmpty()) && (accountNo != null)) {
				logger.info(bank.isEmpty() + " account " + accountNo);
				result = getSearchService().searchbybankServ(bank, accountNo,
						offset);
				if (result.size() == 0) {
					view = new ModelAndView("bankaccountno");
					sendBack=new ArrayList();
					for(int i=0;i<resList.size();i++){
						if(Long.valueOf(bank)==resList.get(i).getBankId()){
							bankLable=resList.get(i).getShortCode();
							resList.remove(i);
						}
						sendBack.add(resList.get(i));
					}
					view.addObject("bankLabel",bankLable);
					view.addObject("critMasBank", resList);
					view.addObject("msg", msg);
					view.addObject("bank", bank);
					view.addObject("acctNo", accountNo);
				} else {
					int count = getSearchService().searchbybankaccCountservice(
							bank, accountNo);
					
					view = new ModelAndView("bankaccountno");
					sendBack=new ArrayList();
					for(int i=0;i<resList.size();i++){
						if(Long.valueOf(bank)==resList.get(i).getBankId()){
							bankLable=resList.get(i).getShortCode();
							resList.remove(i);
						}
						sendBack.add(resList.get(i));
					}
					view.addObject("bankLabel",bankLable);
					view.addObject("records", result);
					view.addObject("bank", bank);
					view.addObject("acctNo", accountNo);
					view.addObject("offset", offset);
					view.addObject("count", count);
					view.addObject("critMasBank", resList);
				}
			}
		} catch (Exception e) {
			view = new ModelAndView("exception");
		}
		return view;
	}
	@RequestMapping(value = "/searchbybankReport")
	public @ResponseBody void searchbybankReport(HttpServletResponse response){
		String bank="Bank search result";
		ModelAndView view=null;
		List resList =null;
		try{
			resList= getSearchService().getdetailsToaddproductServ();
			List<CreditCardDetailsBO> crdeit=creditCardBoHash.get("mobileno");
			logger.info("the size of the report is "+crdeit.size());
			getSearchService().getMobileReportSer(crdeit,bank,response);
//			view=new ModelAndView("bankaccountno");
//			view.addObject("downloadResult", "Downloaded Successfully");
//			view.addObject("critMasBank", resList.get(0));
		}catch(Exception e){
//			view=new ModelAndView("bankaccountno");
//			view.addObject("downloadResult","Failed to download the report");
		}
		//return view;
	}
	@RequestMapping(value = "/track", method = RequestMethod.GET)
	public ModelAndView sbiCustomers(ModelMap model, Principal principal) {
		try {
			String name = principal.getName();
			model.addAttribute("username", name);
		} catch(NullPointerException ne) {
			return new ModelAndView("login");
		}
		return new ModelAndView("sbirecordsearch");

	}
	@RequestMapping(value = "/awbsearch", method = RequestMethod.GET)
	public ModelAndView cardawb(ModelMap model, Principal principal) {
		try {
			String name = principal.getName();
			model.addAttribute("username", name);
		} catch(NullPointerException ne) {
			return new ModelAndView("login");
		}
		return new ModelAndView("awbsearch");

	}
	@RequestMapping(value = "/pinawbpage", method = RequestMethod.GET)
	public ModelAndView pinawbpage(ModelMap model, Principal principal) {
		try {
			String name = principal.getName();
			model.addAttribute("username", name);
		} catch(NullPointerException ne) {
			return new ModelAndView("login");
		}
		return new ModelAndView("pinawb");

	}
	
	@RequestMapping(value = "/rsnpage", method = RequestMethod.GET)
	public ModelAndView rsnpage(ModelMap model, Principal principal) {
		try {
			String name = principal.getName();
			model.addAttribute("username", name);
		} catch(NullPointerException ne) {
			return new ModelAndView("login");
		}
		return new ModelAndView("rsn");

	}
	
	
	
	@RequestMapping(value = "/prerequesttoadd")
	public ModelAndView getdetailsToaddproductCont(ModelMap model, Principal principal) {
		try {
			String name = principal.getName();
			model.addAttribute("username", name);
		} catch(NullPointerException ne) {
			return new ModelAndView("login");
		}
		ModelAndView sendObj = null;
		try {
			List resList = getSearchService().getdetailsToaddproductServ();
			sendObj = new ModelAndView("bankaccountno");
			sendObj.addObject("critMasBank", resList);
		} catch (Exception e) {
			sendObj = new ModelAndView("exception");
		}
		return sendObj;
	}

	@RequestMapping(value = "/prerequest")
	public ModelAndView getdetailsToaddproductCon(ModelMap model, Principal principal) {
		try {
			String name = principal.getName();
			model.addAttribute("username", name);
		} catch(NullPointerException ne) {
			return new ModelAndView("login");
		}
		ModelAndView sendObj = null;
		try {
			List resList = getSearchService().getdetailsToaddproductServ();
			sendObj = new ModelAndView("branchwise");
			sendObj.addObject("critMasBank", resList);
		} catch (Exception e) {
			sendObj = new ModelAndView("exception");
		}
		return sendObj;
	}

	@RequestMapping(value = "/customerid")
	public ModelAndView customerId(ModelMap model, Principal principal) {
		try {
			String name = principal.getName();
			model.addAttribute("username", name);
		} catch(NullPointerException ne) {
			return new ModelAndView("login");
		}
		return new ModelAndView("customerid");

	}
	
	@RequestMapping(value = "/getcustomerbycustid")//, method = RequestMethod.POST
	public @ResponseBody ModelAndView getCustomersbyCustId(ModelMap model, Principal principal,
			@RequestParam(value = "customerId", required = true) String customerId,
			@RequestParam(value = "offset", required = false) Integer offset) {
		try {
			String name = principal.getName();
			model.addAttribute("username", name);
		} catch(NullPointerException ne) {
			return new ModelAndView("login");
		}
		
		if(SbiController.creditCardBoHash.get("mobileno")!=null){
			SbiController.creditCardBoHash.get("mobileno").clear();
			}
		ModelAndView view = null;
		String res = "No Record Found";
		try {
			if (!customerId.isEmpty() && (customerId != null)) {
				List<CreditCardDetailsBO> lis = getSearchService().custIdSer(customerId,offset);
				int count=getSearchService().custIdSerCount(customerId);
				if ((lis != null) && (lis.size() > 0)) {
					view = new ModelAndView("customerid");
					view.addObject("records", lis);
					view.addObject("offset", offset);
					view.addObject("count", count);
					view.addObject("customerId", customerId);
				} else {
					view = new ModelAndView("customerid");
					view.addObject("msg", res);
					view.addObject("rsn", customerId);
				}
			}
		} catch (Exception e) {
			view = new ModelAndView("exception");
		}
		return view;
	}

	
	@RequestMapping(value = "/record", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getRecord(ModelMap model, Principal principal,
			@RequestParam(value = "creditCardDetailsId", required = true) Long creditCardDetailsId) {
		try {
			String name = principal.getName();
			model.addAttribute("username", name);
		} catch(NullPointerException ne) {
			return new ModelAndView("login");
		}
		ModelAndView sendObj = null;
		try {
			HashMap<String, Object> recordDetails = getSearchService()
					.getRecordEvent(creditCardDetailsId);
			sendObj = new ModelAndView("sbirecorddetails", "record",
					recordDetails);

		} catch (Exception e) {
			sendObj = new ModelAndView("exception");
		}
		return sendObj;
	}


	@ExceptionHandler(GnDGenericException.class)
	public ModelAndView handleCustomException(GnDGenericException ex) {

		ModelAndView model = new ModelAndView("error/generic_error");
		model.addObject("exception", ex);
		return model;

	}

	@ExceptionHandler(Exception.class)
	public ModelAndView handleAllException(Exception ex) {

		ModelAndView model = new ModelAndView("error/exception_error");
		return model;

	}


}
