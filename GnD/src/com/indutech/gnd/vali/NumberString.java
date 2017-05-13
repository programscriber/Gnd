package com.indutech.gnd.vali;

import java.util.ArrayList;
import java.util.List;





public class NumberString {
	
	private static CheckDigitAlgo a;

	private String startVar1,startVar2;
	private String endVar1,endVar2;
	private int oNum;
	
	public void setAlgo1()	{
		 a=new CheckDigitAlgo();
	}
	
	public boolean compare(int start,int end)		{
		
		if(end>start)	{
			return true;
		}
		return false;
	}
	
	public boolean compareVar(String startVar1, String startVar2,String endVar1,String endVar2)		{
		
		if((startVar1.equals(startVar2)) && (endVar1.equals(endVar2)))	{
			return true;
		}
		
		return false;
	}
	
	public String appendPQAB(String st)	{
		
		st=startVar1.concat(st.concat(endVar1));
		return st;
	}
	public int obtainNum(String num)	{
		
		num=num.substring(2, num.length()-2);
		oNum=Integer.parseInt(num);
		return oNum;
	}
	public String obtainstartVar(String startVar)	{
		
		startVar=startVar.substring(0, startVar.length()-10);
		return startVar;
	}
	public String obtainendVar(String endVar)	{
		
		endVar=endVar.substring( endVar.length()-2, endVar.length());
		return endVar;
	}
	
	public List<String> generateAWB(String stStart,String stEnd)	{
		
		List<String> generatedList = null;
		int start=0,end=0;
		boolean b,b1;		
		startVar1=obtainstartVar(stStart);
		endVar1=obtainendVar(stStart);
		startVar2=obtainstartVar(stEnd);
		endVar2=obtainendVar(stEnd);
		b=compareVar(startVar1,startVar2,endVar1,endVar2);		
		start=obtainNum(stStart);
		end=obtainNum(stEnd);
		b1=compare(start, end);
		
		if(b)	{
			if(b1)	{	
			setAlgo1();
			generatedList = new ArrayList<String>();
			for(int i=start;i<=end;i++)	{
				String st=a.runAlgo(i);				
				String generatedString = appendPQAB(st);
				generatedList.add(generatedString);
				}
			}
		}		
		return generatedList;
	}
}

