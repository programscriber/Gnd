package com.indutech.gnd.records;

import com.indutech.gnd.dto.CreditCardDetails;

public class RecordNCF {
	private int recordLen;
	private int NoOfFields;
	private String rawInput;
	private String[] sa;
	private int Sta;

	static final int SUCCESS_RECORD_VALID = 0;
	static final int ERR_RECORD_LENGTH = -1;

	public void getRecordNcf(String inputVal)  {
		rawInput = inputVal;
		NoOfFields = 0;
		recordLen=inputVal.length();
		sa=inputVal.split("\\^");
		NoOfFields = sa.length;

//		RecordRuleSet2 rdd=new RecordRuleSet2();
//		rdd.setRecord(this);
//		rdd.applyRecordRulesSet2();
//		RecordRuleSet3 rd=new RecordRuleSet3();
//		rd.setRecord(this);
//		rd.applyRecordRulesSet3();
//		RecordRuleSet4 rd1=new RecordRuleSet4();
//		rd1.setRecordr(this);
//		rd1.applyRecordRulesSet4();
	}
	
	//TODO: one getter method must be declared
	public String getBranchCode()	{
		return sa[0];
	}
	public String getPrimaryAccountNo()	{
		return sa[1];
	}
	public String getPrimaryAccountNoType()	{
		return sa[2];
	}
	public String getProduct()	{
		return sa[3];
	}
	public String getToDoFieldName()	{
		return sa[4];
	}
	public String getCircle_Code()	{
		return sa[5];
	}
	public String getCircle2()	{
		return sa[6];
	}
	
	
	public void processRawRecordNcf()
	{
	
		isValidRecord();
		if(Sta==SUCCESS_RECORD_VALID)	{
			sa=rawInput.split("\\^");
			CreditCardDetails RecordObj = new CreditCardDetails();
			
			for(int i=0;i<=sa.length-1;)	{
				//TODO: Two setter method must be declared
				//TODO: change this two methods
				RecordObj.setHomeBranchCode(sa[i++]);
				RecordObj.setPrimaryAcctNo(sa[i++]);
				RecordObj.setProduct(sa[i++]);
				RecordObj.setHomeBranchCircleCode(sa[i++]);
				RecordObj.setIssueBranchCircleCode(sa[i++]);	
				RecordObj.setHomeBranchCircleCode(sa[i++]);
				RecordObj.setIssueBranchCircleCode(sa[i++]);
			}
		}
	}
	
	public int getNoOfFields() {	
		return NoOfFields;
	}

	public boolean isValidRecord() {
		boolean b = false;
		if(NoOfFields>=7)	{
			Sta=SUCCESS_RECORD_VALID;
			b = true;
		}
		else if(NoOfFields==1)	{
			Sta=ERR_RECORD_LENGTH;
			b = false;
		}
		return b;
	}
	
	public int getErrorCode() {
		return Sta;
	}
	
}
