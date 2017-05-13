package com.indutech.gnd.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.indutech.gnd.bo.BankBO;
import com.indutech.gnd.bo.CoreFilesBO;
import com.indutech.gnd.bo.CreditCardDetailsBO;
import com.indutech.gnd.bo.ReportsBO;
import com.indutech.gnd.jasper.reports.JasperReportGenerator;
import com.indutech.gnd.service.CardStateManagerService;
import com.indutech.gnd.service.SearchServiceImpl;

@Controller
@RequestMapping("/search")
public class SearchController {

	Logger logger = Logger.getLogger(SearchController.class);
	common.Logger log = common.Logger.getLogger(SearchController.class);
	
	@Autowired
	private SearchServiceImpl searchService;

	@Autowired
	private CardStateManagerService stateManager;

	@Autowired
	private JasperReportGenerator jasperReports;

	public JasperReportGenerator getJasperReports() {
		return jasperReports;
	}

	public void setJasperReports(JasperReportGenerator jasperReports) {
		this.jasperReports = jasperReports;
	}

	public CardStateManagerService getStateManager() {
		return stateManager;
	}

	public void setStateManager(CardStateManagerService stateManager) {
		this.stateManager = stateManager;
	}

	public SearchServiceImpl getSearchService() {
		return searchService;
	}

	public void setSearchService(SearchServiceImpl searchService) {
		this.searchService = searchService;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getRecords", method = RequestMethod.POST)
	public @ResponseBody ModelAndView getRecords(ModelMap model, Principal principal,
			@RequestParam(value = "cardawb", required = false) String cardawb,
			@RequestParam(value = "pinawb", required = false) String pinawb,
			@RequestParam(value = "rsn", required = false) String rsn,
			@RequestParam(value = "applNo", required = false) String applNo,
			@RequestParam(value = "product", required = false) String product,
			@RequestParam(value = "bank", required = false) String bank,
			@RequestParam(value = "branch", required = false) String branch,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "mobile", required = false) String mobile,
			@RequestParam(value = "acctNo", required = false) String acctNo,
			@RequestParam(value = "customerId", required = false) String customerId,
			@RequestParam(value = "issuebranch", required = false) String issueBranchCode,
			@RequestParam(value = "dateFrom", required = false) String dateFrom,
			@RequestParam(value = "dateTo", required = false) String dateTo) {
		try {
			String name = principal.getName();
			model.addAttribute("username", name);
		} catch(NullPointerException ne) {
			return new ModelAndView("login");
		}
		Map parametermap = new HashMap();

		parametermap.put("CARD_AWB", cardawb);
		parametermap.put("PIN_AWB", pinawb);
		parametermap.put("RSN", rsn);
		parametermap.put("PRODUCT", product);
		parametermap.put("APPL_NO", applNo);
		parametermap.put("BANK", bank);
		parametermap.put("BRANCH", branch);
		parametermap.put("STATUS", status);
		parametermap.put("MOBILE", mobile);
		parametermap.put("ACCT_NO", acctNo);
		parametermap.put("DATE_FROM", dateFrom);
		parametermap.put("DATE_TO", dateTo);
		parametermap.put("ISSUE_BRANCH_CODE", issueBranchCode);
		parametermap.put("CUSTOMER_ID", customerId);
		
		logger.info(parametermap.get("BRANCH"));
		
		try {
			String name = principal.getName();
			model.addAttribute("username", name);
		} catch(NullPointerException ne) {
			return new ModelAndView("login");
		}
		
		String result = null;

		List<CreditCardDetailsBO> list = getSearchService().getRecords(
				parametermap);
		
		if(list.size() == 0 ) {
			result = "No records found";
		}
		
		ModelAndView mav = new ModelAndView("recordsearch","msg", result);
		mav.addObject("records", list);

		return mav;
	}

	@RequestMapping(value = "/getFiles", method = RequestMethod.POST)
	public @ResponseBody ModelAndView getFiles(ModelMap model, Principal principal,
			@RequestParam(value = "filename", required = false) String fileName,
			@RequestParam(value = "bank", required = false) String bank,
			@RequestParam(value = "branch", required = false) String branch,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "dateFrom", required = false) String dateFrom,
			@RequestParam(value = "dateTo", required = false) String dateTo) {
		// getSearchService().postQueryMessage();
		try {
			String name = principal.getName();
			model.addAttribute("username", name);
		} catch(NullPointerException ne) {
			return new ModelAndView("login");
		}
		
		
		
		Map<String, String> parametermap = new HashMap<String, String>();
		parametermap.put("FILE_NAME", fileName);
		parametermap.put("BANK", bank);
		parametermap.put("BRANCH", branch);
		parametermap.put("STATUS", status.toString());
		parametermap.put("DATE_FROM", dateFrom);
		parametermap.put("DATE_TO", dateTo);
		String result = null;
		List<CoreFilesBO> list = getSearchService().getFiles(parametermap);
		if(list.size() == 0) {
			result = "No Files Found";
		}
		logger.info("list size is " + list.size());
		ModelAndView mav =  new ModelAndView("filesearch", "files", list);
		mav.addObject("msg", result);
		
		return mav;
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

		HashMap<String, Object> recordDetails = getSearchService()
				.getRecordEvent(creditCardDetailsId);
		
		return new ModelAndView("recorddetails", "record", recordDetails);
	}

	@RequestMapping(value = "/statereject", method = RequestMethod.GET)
	public @ResponseBody ModelAndView setStateReject(ModelMap model, Principal principal,
			@RequestParam(value = "creditCardDetailsId", required = true) String creditCardDetailsId,
			@RequestParam(value = "remarkval", required = true) String remark) {
		String name = null;
		try {
			name = principal.getName();
			model.addAttribute("username", name);
		} catch(NullPointerException ne) {
			return new ModelAndView("login");
		}	
		remark = remark+" ("+name+")";
		getStateManager().toStateReject(creditCardDetailsId, remark);
		
		
	
		return new ModelAndView("recordsearch");
	}

	@RequestMapping(value = "/statehold", method = RequestMethod.GET)
	public @ResponseBody ModelAndView setStateHold(ModelMap model, Principal principal,
			@RequestParam(value = "creditCardDetailsId", required = true) String creditCardDetailsId,
			@RequestParam(value = "remarkval", required = true) String remark) {
		String username = null;
		try {
			username = principal.getName();		
			model.addAttribute("username", username);
		} catch(NullPointerException ne) {
			return new ModelAndView("login");
		}
		remark = remark+" ("+username+")";
		getStateManager().toStateHold(creditCardDetailsId, remark);
		
				

		return new ModelAndView("recordsearch");
	}
	
	@RequestMapping(value="/holdReprocess")
	public @ResponseBody ModelAndView holdReprocess(ModelMap model, Principal principal,
			@RequestParam(value = "creditCardDetailsId", required = true) String creditCardDetailsId, 
			@RequestParam(value = "remarkval", required = true) String remark) {
		
		String username = null;
		try {
			username = principal.getName();		
			model.addAttribute("username", username);
		} catch(NullPointerException ne) {
			return new ModelAndView("login");
		}
		remark = remark+" ("+username+")";
		
		getStateManager().toCardHoldReprocess(creditCardDetailsId, remark);
		
		
		
		return new ModelAndView("recordsearch");
	}
	
	@RequestMapping(value="/rejectReprocess")
	public @ResponseBody ModelAndView rejectReprocess(ModelMap model, Principal principal,
			@RequestParam(value = "creditCardDetailsId", required = true) String creditCardDetailsId, 
			@RequestParam(value = "remarkval", required = true) String remark) {
		
		String username = null;
		try {
			username = principal.getName();		
			model.addAttribute("username", username);
		} catch(NullPointerException ne) {
			return new ModelAndView("login");
		}		
		remark = remark+" ("+username+")";
		
		getStateManager().toCardRejectReprocess(creditCardDetailsId, remark);
		
		
		
		return new ModelAndView("recordsearch");
	}
	
	@RequestMapping(value = "/getReport", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getReportsList(ModelMap model, Principal principal) {
		String username = null;
		try {
			username = principal.getName();		
			model.addAttribute("username", username);
		} catch(NullPointerException ne) {
			return new ModelAndView("login");
		}
		logger.info("In report generation class");

		List<ReportsBO> list = getJasperReports().getReports(username);
		List<BankBO>  bankList = getJasperReports().getBankList();

		
		
		return new ModelAndView("reports", "reports", list).addObject("bankList", bankList);
	}
	
	@RequestMapping(value = "/generateReports", method = RequestMethod.POST)
	public @ResponseBody void generateReports(ModelMap model, Principal principal,
													  @RequestParam(value = "reportname") String reportName,
													  @RequestParam(value = "sourcePath") String sourcePath,
													  @RequestParam(value="destinationPath") String destinationPath,
													  @RequestParam(value="fromdate") String fromdate,
													  @RequestParam(value="todate") String todate,
													  @RequestParam(value="bankId") String bankId,
													  HttpServletResponse response) {//,

//													  @RequestParam(value="fileType", required=true) String fileType) {
		String username = null;
		

		ModelAndView view = null;
		try {
		String result = null;

		List<ReportsBO> list = getJasperReports().getReports(username);

	
		logger.info("received Date is : "+fromdate+" and todate is : "+todate+ " and bankId is : "+bankId);
		result = getJasperReports().generateReports(reportName, sourcePath, destinationPath, fromdate, todate, bankId);//, fileType);

		File file = new File(result);
		InputStream is = new FileInputStream(file);


		// MIME type of the file
		response.setContentType("application/octet-stream");
		// Response header
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ file.getName() + "\"");
		// Read from the file and write into the response

		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=\""	+ file.getName() + "\"");

		OutputStream os = response.getOutputStream();
		byte[] buffer = new byte[1024];
		int len;
		while ((len = is.read(buffer)) != -1) {
			os.write(buffer, 0, len);
		}
		response.getOutputStream().flush();
		response.getOutputStream().close();
		is.close();
		file.delete();

		
		} catch(Exception e) {
			e.printStackTrace();			
		}
		

	}
	
	@RequestMapping(value = "/filesearch")
	public String getFiles(ModelMap model, Principal principal) {		
		
		String username = null;
		try {
			username = principal.getName();		
			model.addAttribute("username", username);
		} catch(NullPointerException ne) {
			return "login";
		}
		
		return "filesearch";
	}
	
	
	@RequestMapping(value="/generateCustomerReport", method = RequestMethod.POST)
public @ResponseBody ModelAndView generateCustomerReports(HttpServletResponse response,
										ModelMap model, Principal principal,
										@RequestParam(value="fromdate") String fromdate,
										@RequestParam(value="todate") String todate,
										@RequestParam(value="bankId") String bankId) {
		String username = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			username = principal.getName();		
			model.addAttribute("username", username);
		} catch(NullPointerException ne) {
			return new ModelAndView("login");
		}
		ModelAndView view = null;
		try {
			String result = null;
			logger.info("received Date is : "+fromdate+" and todate is : "+todate);
			List<ReportsBO> list = getJasperReports().getReports(username);
			List<BankBO>  bankList = getJasperReports().getBankList();
			result = getJasperReports().generateReports(fromdate,todate, Long.parseLong(bankId), response);//, fileType);
			view=new ModelAndView("reports","msg",result);
			view.addObject("reports",list);
			view.addObject("bankList", bankList);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return view;

	}
	
	
	
	@RequestMapping(value="/generatePinMailerReport", method = RequestMethod.POST)
	public @ResponseBody ModelAndView generatePinMailerReports(HttpServletResponse response,
											ModelMap model, Principal principal,
											@RequestParam(value="fromdate") String fromdate,
											@RequestParam(value="todate") String todate,
											@RequestParam(value="bankId") String bankId,
											@RequestParam(value="vip") String vip) {
			String username = null;
			try {
				username = principal.getName();		
				model.addAttribute("username", username);
			} catch(NullPointerException ne) {
				return new ModelAndView("login");
			}
			ModelAndView view = null;
			try {
				String result = null;
				List<ReportsBO> list = getJasperReports().getReports(username);
				result = getJasperReports().generatePinMailerFromUI(fromdate,todate, Long.parseLong(bankId), response,vip);
				view=new ModelAndView("reports","msg",result);
				view.addObject("reports",list);
				
				
				
			} catch(Exception e) {
				e.printStackTrace();
			}
			return view;

		}
	
	@RequestMapping(value="/searchcard")
	public String search(ModelMap model, Principal principal) {
			String username = null;
			try {
				username = principal.getName();		
				model.addAttribute("username", username);
			} catch(NullPointerException ne) {
				return "login";
			}
			return "search";

		}
	
	

}
