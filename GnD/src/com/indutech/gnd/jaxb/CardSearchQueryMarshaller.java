package com.indutech.gnd.jaxb;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import com.indutech.gnd.bo.CardSearchQuery;

@Component("cardSearchQueryMarshaller")
public class CardSearchQueryMarshaller {

	
	public String marshallCardSearchQueryForCamel(Exchange exchnage){
	     String resultString =null;
		try {
			CardSearchQuery cardSearchQuery= (CardSearchQuery)exchnage.getIn().getBody();
			resultString = marshallCardSearchQuery(cardSearchQuery);
		} catch (JAXBException e) {
			e.printStackTrace();
		}  
	     
	     return resultString;
	}
	
	
	public String marshallCardSearchQuery(CardSearchQuery cardSearchQuery) throws JAXBException{
	     JAXBContext jaxbContext;
	     StringWriter writer =null;
		try {
			jaxbContext = JAXBContext.newInstance(CardSearchQuery.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			writer = new StringWriter();
			jaxbMarshaller.marshal(cardSearchQuery, writer);
		} catch (JAXBException e) {
			e.printStackTrace();
		}  
	     
	     return writer.toString();
	}
}
