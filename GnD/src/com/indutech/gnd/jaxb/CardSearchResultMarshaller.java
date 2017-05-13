package com.indutech.gnd.jaxb;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import com.indutech.gnd.bo.CardSearchResult;
import com.indutech.gnd.bo.CardSearchResultList;

@Component("cardSearchResultMarshaller")
public class CardSearchResultMarshaller {

	
	public String marshallCardSearchResultForCamel(Exchange exchange){
	     String resultSTring =null;
		try {
			CardSearchResultList cardSearchResultList =(CardSearchResultList)exchange.getIn().getBody();
			resultSTring = marshallCardSearchResult(cardSearchResultList);
		} catch (JAXBException e) {
			e.printStackTrace();
		}  
		exchange.getIn().setBody(resultSTring.toString());
	     return resultSTring.toString();
	}
	
	
	public String marshallCardSearchResult(CardSearchResultList cardSearchResultList) throws JAXBException{
	     JAXBContext jaxbContext;
	     StringWriter writer =null;
		try {
			jaxbContext = JAXBContext.newInstance(CardSearchResultList.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			writer = new StringWriter();
			jaxbMarshaller.marshal(cardSearchResultList, writer);
		} catch (JAXBException e) {
			e.printStackTrace();
		}  
	     return writer.toString();
	}
	 
}
