package com.indutech.gnd.dao;

import java.util.List;

import com.indutech.gnd.bo.MasterAWBBO;
import com.indutech.gnd.dto.MasterAWB;
import com.indutech.gnd.dto.MasterCourierService;

public interface MasterAWBDAO {
	
	void saveMasterAWB(MasterAWB masterAWB);

	List<MasterAWB> getMasterAWBId();

	void changeStatus(MasterAWB awb);

	List<MasterCourierService> getServiceProviderList();

	Long getServiceProviderId(String serviceProvicerName);

	MasterCourierService getServiceProvider(Long serviceProviderId);

//	List<MasterAWB> getAWBForChennaiAndMumbai(Long serviceProviderId);

	List<MasterAWB> getAWBForChennaiAndMumbai(Long serviceProviderId,
			int maxlimit);

	MasterAWB getAWBForChennaiAndMumbai(Long serviceProviderId);

	List<Object[]> getAwbCounts();

	List<MasterAWB> getMasterAWB(String awbName);
	

}
