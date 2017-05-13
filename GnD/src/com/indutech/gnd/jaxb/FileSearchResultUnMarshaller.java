package com.indutech.gnd.jaxb;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.indutech.gnd.bo.CardSearchResult;
import com.indutech.gnd.bo.FileSearchResult;

public class FileSearchResultUnMarshaller {

	public CardSearchResult unMarshallFileSearchResult(String fileSearchResultXML){
	     JAXBContext jaxbContext;
	     CardSearchResult fileSearchResult=null;
		try {
			StringReader reader = new StringReader(fileSearchResultXML);
			jaxbContext = JAXBContext.newInstance(FileSearchResult.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();  
			fileSearchResult= (CardSearchResult) jaxbUnmarshaller.unmarshal(reader);
		} catch (JAXBException e) {
			e.printStackTrace();
		}  
	     
	     return fileSearchResult;
	}
}
