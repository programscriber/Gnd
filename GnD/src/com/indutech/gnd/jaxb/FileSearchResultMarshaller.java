package com.indutech.gnd.jaxb;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.indutech.gnd.bo.FileSearchResult;

public class FileSearchResultMarshaller {

	public String marshallFileSearchResult(FileSearchResult fileSearchResult){
	     JAXBContext jaxbContext;
	     StringWriter writer =null;
		try {
			jaxbContext = JAXBContext.newInstance(FileSearchResult.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			writer = new StringWriter();
			jaxbMarshaller.marshal(fileSearchResult, writer);
		} catch (JAXBException e) {
			e.printStackTrace();
		}  
	     
	     return writer.toString();
	}
}
