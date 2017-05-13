package com.indutech.gnd.service;

import org.apache.camel.Exchange;


public interface GNDProcessingService {

	void processData(Exchange exchange);
	
}
