package com.indutech.gnd.service;

import org.apache.camel.Exchange;

public interface RTOService {
	
	void ChangeAWBByHomeBranchGroup(Exchange exchange);

	void ChangeAWBForIndividualRecord(Exchange exchange);

//	String ChangeAWBByHomeBranchGroup(String detailsIdList);

	String ChangeAWBByHomeBranchGroup(String rsnString, Long bankId);

}
