package com.indutech.gnd.jaxb;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import com.indutech.gnd.bo.CardSearchQuery;

@Component("cardSearchQueryUnMarshaller")
public class CardSearchQueryUnMarshaller {

	
	public CardSearchQuery unMarshallCardSearchQueryForCamel(Exchange exchnage){
	     CardSearchQuery cardSearchQuery=null;
		try {
			String cardSearchQueryXML= (String)exchnage.getIn().getBody();
			 
			cardSearchQuery= unMarshallCardSearchQuery(cardSearchQueryXML);
		} catch (JAXBException e) {
			e.printStackTrace();
		}  
	     
	     return cardSearchQuery;
	}
	
	public CardSearchQuery unMarshallCardSearchQuery(String cardSearchQueryXML)throws JAXBException{
	     JAXBContext jaxbContext;
	     CardSearchQuery cardSearchQuery=null;
		try {
			StringReader reader = new StringReader(cardSearchQueryXML);
			jaxbContext = JAXBContext.newInstance(CardSearchQuery.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();  
			cardSearchQuery= (CardSearchQuery) jaxbUnmarshaller.unmarshal(reader);
		} catch (JAXBException e) {
			e.printStackTrace();
		}  
	     
	     return cardSearchQuery;
	}
}
