package com.indutech.gnd.bo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement( name="AccountRange")
public class AccountRange {

	private String accountRangeFrom;
	private String accountRangeTo;
	@XmlElement( name="AccountRangeFrom")  
	public String getAccountRangeFrom() {
		return accountRangeFrom;
	}
	public void setAccountRangeFrom(String accountRangeFrom) {
		this.accountRangeFrom = accountRangeFrom;
	}
	@XmlElement( name="AccountRangeTo")  
	public String getAccountRangeTo() {
		return accountRangeTo;
	}
	public void setAccountRangeTo(String accountRangeTo) {
		this.accountRangeTo = accountRangeTo;
	}
}
