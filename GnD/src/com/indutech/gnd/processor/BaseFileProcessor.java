package com.indutech.gnd.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class BaseFileProcessor implements Processor   {

    
    @Override
    public void process(Exchange exchange) throws Exception {
        String originalFileContent = (String) exchange.getIn().getBody(String.class);
        String originalFileCont=null;
        if (originalFileContent.contains("\"")){
             originalFileCont = originalFileContent.replaceAll("\"", "\\$");
        }
        exchange.getIn().setBody(originalFileCont);
    }

}
