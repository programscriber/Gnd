package com.indutech.gnd.jaxb;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import com.indutech.gnd.bo.CardSearchResultList;

@Component("cardSearchResultUnMarshaller")
public class CardSearchResultUnMarshaller {

	public CardSearchResultList unMarshallCardSearchResultForCamel(Exchange exchange){
	     JAXBContext jaxbContext;
	     CardSearchResultList cardSearchResultList=null;
		try {
			String cardSearchResultXML =(String)exchange.getIn().getBody();
			StringReader reader = new StringReader(cardSearchResultXML);
			jaxbContext = JAXBContext.newInstance(CardSearchResultList.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();  
			cardSearchResultList= (CardSearchResultList) jaxbUnmarshaller.unmarshal(reader);
		} catch (JAXBException e) {
			e.printStackTrace();
		}  
	     
	     return cardSearchResultList;
	}
	 
}
