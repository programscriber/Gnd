package com.indutech.gnd.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.indutech.gnd.bo.BankBO;
import com.indutech.gnd.bo.CoreFileSummaryBO;
import com.indutech.gnd.bo.RecordEventBO;
import com.indutech.gnd.bo.Response;
import com.indutech.gnd.dao.FileDAOImpl;
import com.indutech.gnd.dto.CoreFiles;
import com.indutech.gnd.jasper.reports.JasperReportGenerator;
import com.indutech.gnd.service.CardStateManagerService;
import com.indutech.gnd.service.QcProcessService;

@Controller
public class QCProcessController {

	Logger logger = Logger.getLogger(QCProcessController.class);
	common.Logger log = common.Logger.getLogger(QCProcessController.class);

	@Autowired
	private FileDAOImpl fileDAO;
	@Autowired	
	private QcProcessService qcprocesService;
	@Autowired
	private JasperReportGenerator jasperReport;

	@Autowired
	private CardStateManagerService stateManager;
	
	

	public JasperReportGenerator getJasperReport() {
		return jasperReport;
	}

	public void setJasperReport(JasperReportGenerator jasperReport) {
		this.jasperReport = jasperReport;
	}

	public CardStateManagerService getStateManager() {
		return stateManager;
	}

	public void setStateManager(CardStateManagerService stateManager) {
		this.stateManager = stateManager;
	}

	public FileDAOImpl getFileDAO() {
		return fileDAO;
	}

	public void setFileDAO(FileDAOImpl fileDAO) {
		this.fileDAO = fileDAO;
	}

	@RequestMapping(value="/qcprocess")
	public String qcprocess(Principal principal, ModelMap model) {
		String username = null;
		try {
			logger.info(principal.getName());
			username = principal.getName();		
			model.addAttribute("username", username);
		} catch(NullPointerException ne) {
			return "login";
		}
		model.addAttribute("username", username);
			return "qcprocess";
	}
	
	@RequestMapping(value="/process")
	public String process(Principal principal, ModelMap model) {
		String username = null;
		try {
		logger.info(principal.getName());
		username = principal.getName();		
		model.addAttribute("username", username);
	} catch(NullPointerException ne) {
		return "login";
	}
	model.addAttribute("username", username);

		
			return "process";
	}

	@RequestMapping(value="/reprocess")
	public String reprocess(Principal principal, ModelMap model) {
		String username = null;
		try {
		logger.info(principal.getName());
		username = principal.getName();		
		model.addAttribute("username", username);
	} catch(NullPointerException ne) {
		return "login";
	}
	model.addAttribute("username", username);

			return "reprocess";
	}
	
	@RequestMapping(value = "/getfilesbydate", method = RequestMethod.POST)
	public @ResponseBody List<CoreFiles> getFilesList(
			@RequestParam(value = "receiveddate", required = false) String receivedDate) {
		List<CoreFiles> list = null;
		ModelAndView mav = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		try {
			logger.info("date is : " + receivedDate);
			list = getFileDAO().getFilesByDate(sdf.parse(receivedDate));
			mav = new ModelAndView("reprocess");
			logger.info("list size is : " + list.size());
			mav.addObject("filelist", list);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}

		return list;
	}

	@RequestMapping(value = "/reprocessfile")
	public @ResponseBody Response ureprocessReject(ModelMap model,
			Principal principal, @RequestParam(value = "fileid") String fileId,
			@RequestParam(value = "remark") String remarks,
			@RequestParam(value = "status") String status) {
//		 String username = null;
		 Response response = null;
//		ModelAndView mav = null;
//		 try {
//		 username = principal.getName();
//		 model.addAttribute("username", username);
//		 } catch(NullPointerException ne) {
//		 return new ModelAndView("login");
//		 }
		logger.info("fileId is : " + fileId + " and status is : " + status
				+ " and remarks are : " + remarks);
		String result = getStateManager().reprocess(Long.parseLong(fileId),
				remarks, status);
		response = new Response();
		response.setResult(result);
//		mav = new ModelAndView("reprocess");
//		mav.addObject("msg", result);
		return response;
	}

	@RequestMapping(value = "/qcsummary")
	public @ResponseBody ModelAndView qcProcess(ModelMap model,
			Principal principal) {
		String username="";
		try {
			 username = principal.getName();
			 model.addAttribute("username", username);
			 } catch(NullPointerException ne) {
			 return new ModelAndView("login");
			 }
		List<BankBO>  bankBo=getQcprocesService().getBank();
		ModelAndView view=new ModelAndView("qcsummary");
		view.addObject("MasBank",bankBo);
		return view;
	}

	@RequestMapping(value="/getqcSummary")
	public @ResponseBody ModelAndView getqcSummary(@RequestParam(value="bankId") String bankid,@RequestParam(value="date") String date){
		Map<String, List<CoreFileSummaryBO>> qcsummary=getQcprocesService().getFileSummaryService(bankid,date);
		List<BankBO> sendBack=new ArrayList<BankBO>();
		List<BankBO>  bankBo=getQcprocesService().getBank();
		String bankLable=null;
		ModelAndView view=new ModelAndView("qcsummary");
		view.addObject("listCoreSumBO",qcsummary);
		if(bankBo!=null){
		for(int i=0;i<bankBo.size();i++){
			if(Long.valueOf(bankid)==bankBo.get(i).getBankId()){
				bankLable=bankBo.get(i).getShortCode();
				bankBo.remove(i);
			}
			sendBack.add(bankBo.get(i));
		}
		}
		view.addObject("MasBank",sendBack);
		view.addObject("bank",bankid);
		view.addObject("bankLabel",bankLable);
		view.addObject("dateFrom",date);
		
		return view;
	}
	@RequestMapping(value="/sendemail")
	public @ResponseBody List<RecordEventBO> sendMail(@RequestParam(value="bankVal") String bankVal,@RequestBody List<CoreFileSummaryBO> listcoreFile){
		System.out.println("bank val"+bankVal);
		System.out.println(listcoreFile);
		 Gson gson = new Gson();
		 Type type = new TypeToken<List<CoreFileSummaryBO>>() {}.getType();
		 List<CoreFileSummaryBO> corefileList = gson.fromJson(gson.toJson(listcoreFile), type);
		 List<RecordEventBO> recordEventBo =getQcprocesService().sendEmail(corefileList,bankVal);
		return recordEventBo;
	}
	
	@RequestMapping(value="/download", method = RequestMethod.POST, consumes="application/json")
	public @ResponseBody File downloadFile(@RequestBody Map<String, List<CoreFileSummaryBO>> map){
//		String filepath=getQcprocesService().downloadFile(map);
//		File file=new File(filepath);
//		return file;
		logger.info(map.get("Embossa_Summary_Report"));
		
		return null;
	}
	@RequestMapping(value = "/corefileSuma")
	public @ResponseBody void rsnReport(@RequestParam("filepath") String fileloc,HttpServletResponse response){
		File file = new File(fileloc);
		InputStream is;
		try {
			is = new FileInputStream(file);
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/pincoveringnote")
	public @ResponseBody ModelAndView pincoveringnote(Principal principal, ModelMap model) {
		String username = null;
		try {
			logger.info(principal.getName());
			username = principal.getName();		
			model.addAttribute("username", username);
		} catch(NullPointerException ne) {
			return new ModelAndView("login");
		}
		model.addAttribute("username", username);
		List<BankBO>  bankBo=getQcprocesService().getBank();
		ModelAndView view=new ModelAndView("pincoveringnote");
		view.addObject("MasBank",bankBo);
		
		
			return view;
	}
	
	@RequestMapping(value="/assignawb", method = RequestMethod.POST)
	public @ResponseBody Response awbAssignment(Principal principal, ModelMap model,
											@RequestParam(value="bankid") String bankId,
											@RequestParam(value="qcdate") String qcdate,
											@RequestParam(value="isawbassign") String isawbassign) {
		String username = null;
		String msg = null;
		Response response = null;
		logger.info("In awb assignment");
//		try {
//			logger.info(principal.getName());
//			username = principal.getName();		
//			model.addAttribute("username", username);
//		} catch(NullPointerException ne) {
//			return new ModelAndView("login");
//		}
		try {
		model.addAttribute("username", username);
		logger.info("qc date is : "+qcdate);
		if(Integer.parseInt(isawbassign) ==  1) {
			getQcprocesService().awbAssignment(Long.parseLong(bankId), new SimpleDateFormat("MM/dd/yyyy").parse(qcdate));
		}
		msg = getQcprocesService().generatePinCoveringNoteForAllFiles(Long.parseLong(bankId), new SimpleDateFormat("MM/dd/yyyy").parse(qcdate));
		logger.info(msg);
		response = new Response();
		response.setResult(msg);
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return response;
	}
	@RequestMapping(value="/pingeneration", method = RequestMethod.POST)
	public@ResponseBody Response pinGeneration(Principal principal, ModelMap model,
											@RequestParam(value="bankid") String bankId,
											@RequestParam(value="qcdate") String qcdate) {
		String username = null;
		String msg = null;
		Response response = null;
//		try {
//			logger.info(principal.getName());
//			username = principal.getName();		
//			model.addAttribute("username", username);
//		} catch(NullPointerException ne) {
//			return new ModelAndView("login");
//		}
		try {
			logger.info("In pin covering note generation");
			model.addAttribute("username", username);
			logger.info("qc date is : "+qcdate);
			logger.info(msg);
			msg = getQcprocesService().generatePinCoveringNoteForAllFiles(Long.parseLong(bankId), new SimpleDateFormat("MM/dd/yyyy").parse(qcdate));
			response = new Response();
			response.setResult(msg);
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
			return response;
	}
	
	public QcProcessService getQcprocesService() {
		return qcprocesService;
	}

	public void setQcprocesService(QcProcessService qcprocesService) {
		this.qcprocesService = qcprocesService;
	}
}
