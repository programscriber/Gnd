package com.indutech.gnd.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.indutech.gnd.bo.AwbBO;
import com.indutech.gnd.bo.MasterCourierServiceBO;
import com.indutech.gnd.bo.Response;
import com.indutech.gnd.records.FileDirectory;
import com.indutech.gnd.service.BranchServiceImpl;
import com.indutech.gnd.service.DispatchServiceImpl;
import com.indutech.gnd.service.ExcelConverterBeanImpl;
import com.indutech.gnd.service.MasterAWBServiceImpl;

@Controller
@RequestMapping("/upload")
public class ProductController {
	
	Logger logger = Logger.getLogger(ProductController.class);

	@Autowired
	private MasterAWBServiceImpl masterAWBService;

	@Autowired
	private ExcelConverterBeanImpl excelConverterBeanImpl;

	@Autowired
	private BranchServiceImpl branchService;

	@Autowired
	private FileDirectory filedir;
	
	@Autowired
	private DispatchServiceImpl dispatchService;
	
	

	public DispatchServiceImpl getDispatchService() {
		return dispatchService;
	}

	public void setDispatchService(DispatchServiceImpl dispatchService) {
		this.dispatchService = dispatchService;
	}

	public final FileDirectory getFiledir() {
		return filedir;
	}

	public final void setFiledir(FileDirectory filedir) {
		this.filedir = filedir;
	}

	public final MasterAWBServiceImpl getMasterAWBService() {
		return masterAWBService;
	}

	public final void setMasterAWBService(MasterAWBServiceImpl masterAWBService) {
		this.masterAWBService = masterAWBService;
	}

	public final BranchServiceImpl getBranchService() {
		return branchService;
	}

	public final void setBranchService(BranchServiceImpl branchService) {
		this.branchService = branchService;
	}

	public final ExcelConverterBeanImpl getExcelConverterBeanImpl() {
		return excelConverterBeanImpl;
	}

	public final void setExcelConverterBeanImpl(
			ExcelConverterBeanImpl excelConverterBeanImpl) {
		this.excelConverterBeanImpl = excelConverterBeanImpl;
	}

	
//chk	@RequestMapping(value="/importmaster", method = RequestMethod.POST)
//chk	public @ResponseBody ModelAndView importXsl(@RequestParam("file")MultipartFile file, @RequestParam("dcms") String dcms) {

	@RequestMapping(value = "/importmaster", method = RequestMethod.POST)
	public @ResponseBody ModelAndView importXsl(ModelMap model, Principal principal,
			@RequestParam("file") MultipartFile file,
			@RequestParam("dcms") String dcms) {
		String username = null;
		try {
			username = principal.getName();		
			model.addAttribute("username", username);
		} catch(NullPointerException ne) {
			return new ModelAndView("login");
		}
		String result = "success";
		try {
			getExcelConverterBeanImpl().readExcel(file, dcms);
		} catch (Exception e) {
			e.printStackTrace();
			result = "failed to import products";
		}
		
		
		
		return new ModelAndView("products","msg",result);
	}

//chk	@RequestMapping(value="/importbranch", method = RequestMethod.POST)
//chk	public @ResponseBody ModelAndView importBranch(@RequestParam("file")MultipartFile file) {
//chk		String result = "Success";		

	@RequestMapping(value = "/importbranch", method = RequestMethod.POST)
	public @ResponseBody ModelAndView importBranch(ModelMap model, Principal principal,
			@RequestParam("file") MultipartFile file) {
		String result = null;
		
		String username = null;
		try {
			username = principal.getName();		
			model.addAttribute("username", username);
		} catch(NullPointerException ne) {
			return new ModelAndView("login");
		}

		try {
			result = getBranchService().importBranchXls(file);
		} catch (Exception e) {
			e.printStackTrace();
			result="failed to import branch";
		}
		
		
		
		return new ModelAndView("branch","msg",result);
	}

	@RequestMapping(value = "/getserviceprovider", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getserviceProvider(ModelMap model, Principal principal) {
		String username = null;
		try {
			username = principal.getName();		
			model.addAttribute("username", username);
		} catch(NullPointerException ne) {
			return new ModelAndView("login");
		}
		
		List<AwbBO> awbCountList = getMasterAWBService().getAwbStatistics();
		List<MasterCourierServiceBO> serviceList = getMasterAWBService()
				.getServiceProvider();
		

		return new ModelAndView("awb", "service", serviceList).addObject("awbcount",awbCountList);
	}

	
//chk	@RequestMapping(value="/importawbmaster", method = RequestMethod.POST)
//chk	public @ResponseBody ModelAndView importMasterAWB(@RequestParam("file") MultipartFile file,
//chk												@RequestParam("service") String service) {
		
//chk		String message = "success";

	@RequestMapping(value = "/importawbmaster", method = RequestMethod.POST)
	public @ResponseBody ModelAndView importMasterAWB(ModelMap model, Principal principal,
			@RequestParam("file") MultipartFile file,
			@RequestParam("service") String service) {
		
		String username = null;
		try {
			username = principal.getName();		
			model.addAttribute("username", username);
		} catch(NullPointerException ne) {
			return new ModelAndView("login");
		}

		String result = "Failed to upload";
		List<MasterCourierServiceBO> list = null;
		List<AwbBO> awbCountList = null;
		try {
			awbCountList = getMasterAWBService().getAwbStatistics();
			result = getMasterAWBService().importMasterAMB(file, service);
			list = getMasterAWBService()
					.getServiceProvider();
		} catch (Exception e) {
			e.printStackTrace();
			result = "failed to import awb";
		}
		
		
		ModelAndView mav = new ModelAndView("awb","msg",result);
		mav.addObject("service",list);
		mav.addObject("awbcount", awbCountList);
		return mav;
	}

	@RequestMapping(value = "/importawbrange", method = RequestMethod.POST)
	public @ResponseBody ModelAndView importAWBRange(ModelMap model, Principal principal,
			@RequestParam("from") String fromValue,
			@RequestParam("to") String toValue,
			@RequestParam("service") String service) {
		String username = null;
		try {
			username = principal.getName();		
			model.addAttribute("username", username);
		} catch(NullPointerException ne) {
			new ModelAndView("login");
		}
		String result = "Failed to import AWB values";
		List<MasterCourierServiceBO> list = null;
		List<AwbBO> awbCountList = null;
		try {
			result = getMasterAWBService().importMasterAMB(fromValue, toValue, service);
			awbCountList = getMasterAWBService().getAwbStatistics();
			list = getMasterAWBService()
					.getServiceProvider();
		} catch (Exception e) {
			e.printStackTrace();
			result = "failure";
		}
		
		
		ModelAndView mav = new ModelAndView("awb","msg",result);
		mav.addObject("service",list);
		mav.addObject("awbcount", awbCountList);
		return mav;
	}



	@RequestMapping(value = "/getProduct")
	public ModelAndView getproductCont(ModelMap model, Principal principal) {
		String username = null;
		try {
			username = principal.getName();		
			model.addAttribute("username", username);
		} catch(NullPointerException ne) {
			return new ModelAndView("login");
		}
		
		
		
		List result=getMasterAWBService().getproductServ();
		ModelAndView sendObj = new ModelAndView("products");
		sendObj.addObject("masType", result.get(0));
		sendObj.addObject("critMasBank", result.get(1));
		sendObj.addObject("masDcms", result.get(2));
		
		
		return sendObj;
	}
	
	@RequestMapping(value="/getexportawb")
	public  ModelAndView getexportawb(ModelMap model, Principal principal){
		String username = null;
		try {
			username = principal.getName();		
			model.addAttribute("username", username);
		} catch(NullPointerException ne) {
			return new ModelAndView("login");
		}
		
		
		return new ModelAndView("exportAWB");

	}
	@RequestMapping(value = "/processMISImport", method = RequestMethod.POST)
	public @ResponseBody Response processMISImport(ModelMap model, Principal principal,
			@RequestParam("file") MultipartFile file,
			@RequestParam("dispatchType") String dispatchType,
			@RequestParam("tostatus") String status,
			@RequestParam("remark") String remark) {
		
		String username = null;
		Response res = null;
//		try {
//			username = principal.getName();		
//			model.addAttribute("username", username);
//		} catch(NullPointerException ne) {
//			return new ModelAndView("login");
//		}
		String result = "failed to process : "+file.getOriginalFilename();
		List<MasterCourierServiceBO> list = null;
		try {
			logger.info("filename is : "+file.getOriginalFilename());
			logger.info("dispatchType is : "+dispatchType);
			logger.info("required status is : "+status);
			logger.info("remarks are : "+remark);
			res = getDispatchService().misDispatch(file, dispatchType, status, remark);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		ModelAndView mav = new ModelAndView("misImport").addObject(res);
		return res;
	}

}
