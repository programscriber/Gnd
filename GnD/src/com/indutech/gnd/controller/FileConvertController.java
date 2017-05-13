package com.indutech.gnd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.indutech.gnd.jasper.reports.JasperReportGenerator;
import com.indutech.gnd.service.FileConvertServiceImpl;

@Controller
@RequestMapping("/convert")
public class FileConvertController {
	
	@Autowired
	private JasperReportGenerator jasperReports;
	
	@Autowired
	private FileConvertServiceImpl fileConvertServiceImpl;		

	@RequestMapping(value="/txt", method = RequestMethod.POST)
	public @ResponseBody String convertToTxt() {
		String result = "success";
		try {
			getFileConvertServiceImpl().convertTxt();
		} catch(Exception e) {
			e.printStackTrace();
			result = "failure";
		}
		return result;
	}
	
//	@RequestMapping(value="/report", method = RequestMethod.GET)
//	public @ResponseBody String generateReport() {
//		String result = "success";
//		try {
//			getJasperReports().generateApprovedReport();
//		} catch(Exception e) {
//			e.printStackTrace();
//			result = "failure";
//		}
//		return result;
//	}
//	
	public final FileConvertServiceImpl getFileConvertServiceImpl() {
		return fileConvertServiceImpl;
	}
	public final void setFileConvertServiceImpl(
			FileConvertServiceImpl fileConvertServiceImpl) {
		this.fileConvertServiceImpl = fileConvertServiceImpl;
	}

	public JasperReportGenerator getJasperReports() {
		return jasperReports;
	}

	public void setJasperReports(JasperReportGenerator jasperReports) {
		this.jasperReports = jasperReports;
	}
	
	
	

}
