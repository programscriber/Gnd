package com.indutech.gnd.service;

import org.springframework.web.multipart.MultipartFile;

public interface ExcelConvertBean {

	
	void readExcel(MultipartFile file, String dcms) throws Exception;
}
