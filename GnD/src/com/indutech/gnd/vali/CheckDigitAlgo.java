package com.indutech.gnd.vali;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckDigitAlgo {

	private int i;
	private int sum = 0, rem, chkDigit;
	private ArrayList<Integer> numlist;
	private ArrayList<Integer> factlist;
	private String st;
	
	public List<Integer> addValues(int a)	{
		
		numlist = new ArrayList<Integer>(8);
		factlist = new ArrayList<Integer>(Arrays.asList(8,6,4,2,3,5,9,7));
		String temp = Integer.toString(a);
		int[] array = new int[temp.length()];
		
		for (int i = 0; i < temp.length(); i++)	{
		    array[i] = temp.charAt(i) - '0';
		}
		
		for(int i=0;i<array.length;i++)	{
			numlist.add(array[i]);
			
		}
		return multiply( numlist, factlist);

	}
	
	public List<Integer> multiply(ArrayList<Integer> numlist,ArrayList<Integer> factlist)	{
		//TODO: for multiplying
		
			List<Integer> multipliedList = new ArrayList<>();
			for (int i = 0; i < numlist.size(); i++) 	{
						multipliedList.add(numlist.get(i) * factlist.get(i));
						
			}		
		return	 multipliedList;
	}

	public int multipledValue(List<Integer> multipliedList)	{
		//TODO: for reading the multiplied values
		
			sum=0;

			for(i = 0; i < multipliedList.size(); i++)	{
			sum += multipliedList.get(i);
			}
			return sum;
			
	}

	public String  divideBy(int sum)	{
		// divide sum by '11'
		
			rem = sum%11;
//			logger.info("Remainder = " + rem);
					
			if (rem == 0)	{
			chkDigit = 5;
			}
			else if (rem == 1)	{
			chkDigit = 0;
			}
			else	{
			chkDigit = (11- rem);
			}
			return checkDigit( numlist, chkDigit);	
	}
	
	public String checkDigit(ArrayList<Integer> numlist,int chkDigit)		{
		//TODO: final string including CHECK DIGIT
		
		StringBuilder str = new StringBuilder();
	    for(int i = 0; i < numlist.size(); i++)	{
	      str.append(numlist.get(i).toString());
	    } 		
		str.append(chkDigit);	
		st=str.toString();
		return st;
		
	}
	
	public String runAlgo(int i)
	{
		List<Integer> li=addValues(i);		
		int sum=multipledValue( li);
		String str=divideBy(sum);		
		return str;
	}

	
}

	
	

