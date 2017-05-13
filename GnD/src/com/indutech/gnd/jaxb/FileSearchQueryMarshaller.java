package com.indutech.gnd.jaxb;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.indutech.gnd.bo.FileSearchQuery;

public class FileSearchQueryMarshaller {

	public String marshallFileSearchQuery(FileSearchQuery fileSearchQueryt){
	     JAXBContext jaxbContext;
	     StringWriter writer =null;
		try {
			jaxbContext = JAXBContext.newInstance(FileSearchQuery.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			writer = new StringWriter();
			jaxbMarshaller.marshal(fileSearchQueryt, writer);
		} catch (JAXBException e) {
			e.printStackTrace();
		}  
	     
	     return writer.toString();
	}
	
}
