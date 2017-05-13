package com.indutech.gnd.jaxb;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.indutech.gnd.bo.FileSearchQuery;

public class FileSearchQueryUnMarshaller {

	public FileSearchQuery unMarshallFileSearchQuery(String fileSearchQueryXML){
	     JAXBContext jaxbContext;
	     FileSearchQuery fileSearchQuery=null;
		try {
			StringReader reader = new StringReader(fileSearchQueryXML);
			jaxbContext = JAXBContext.newInstance(FileSearchQuery.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();  
			fileSearchQuery= (FileSearchQuery) jaxbUnmarshaller.unmarshal(reader);
		} catch (JAXBException e) {
			e.printStackTrace();
		}  
	     
	     return fileSearchQuery;
	}
}
