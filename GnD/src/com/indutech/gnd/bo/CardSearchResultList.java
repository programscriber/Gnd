package com.indutech.gnd.bo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="CardSearchResultList")
public class CardSearchResultList {
	
	private List<CardSearchResult> cardSearchResults = null;

	@XmlElement(name="CardSearchResult")
	public List<CardSearchResult> getCardSearchResults() {
		if(null == cardSearchResults){
			return new ArrayList<CardSearchResult>();
		}
		return cardSearchResults;
	}

	public void setCardSearchResults(List<CardSearchResult> cardSearchResults) {
		this.cardSearchResults = cardSearchResults;
	}

}
