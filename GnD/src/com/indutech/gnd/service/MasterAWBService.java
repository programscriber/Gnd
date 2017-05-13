package com.indutech.gnd.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.indutech.gnd.bo.AwbBO;
import com.indutech.gnd.bo.MasterCourierServiceBO;


public interface MasterAWBService {
	
	String importMasterAMB(MultipartFile file, String service);

	public List<MasterCourierServiceBO> getServiceProvider();

	List<AwbBO> getAwbStatistics();

	String importMasterAMB(String fromValue, String toValue, String service);
}
