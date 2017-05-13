package com.indutech.gnd.service;


import org.apache.camel.Exchange;
import org.springframework.web.multipart.MultipartFile;

import com.indutech.gnd.bo.Response;

public interface DispatchService {
	
	public static final String  CARD_AWB_MAPPED = "CARD AWB MAPPED";
	public static final String  CARD_AWB_NOT_MAPPED = "CARD AWB NOT MAPPED";
	public static final String  PIN_AWB_MAPPED = "PIN AWB MAPPED";
	public static final String  PIN_AWB_NOT_MAPPED = "PIN AWB NOT MAPPED";

	public void cardMISDispatch(Exchange exchange);

	Response misDispatch(MultipartFile misFile, String dispatchType,
			String statusVal, String remark);
}
