package com.indutech.gnd.records;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.indutech.gnd.vali.FileRuleSet1;
import com.jramoyo.io.IndexedFileReader;

import org.apache.log4j.Logger;

@Component("fileObj")
public class CoreFile {
	Logger logger = Logger.getLogger(CoreFile.class);
	
	private static String LastLine;
	private String fname="";
	private static String LastButONeLine;
	private static int count,s2,y;
	private static String strCompare="END OF FILE";
	private int ls,lsb,su;
	private String ss,line;
	private String[] sa;

	private File fi;
	private IndexedFileReader fr;
	private BufferedReader br;
	
	private String checksum;
	private Map<Integer,String> FileContents;
	final int MAX_SUM_VALUE = 999999;
	final int NORMALIZE_VALUE = 10000;
	
	@Autowired
	public FileRuleSet1 fileRule1;
	
	
	public static final int FILE_TYPE_CR  = 1;
	public static final int FILE_TYPE_CW  = 2;
	public static final int FILE_TYPE_CB  = 3;
//	static final int FILE_TYPE_NCF = 4;	
	
	public IndexedFileReader getFileReader() {
		return fr;
	}
	
	public File getFile() {
		return fi;
	}
	
//	public String applyRules(String FileName) {
//		
//		fi = new File(FileName);
//		getFileName();	
//		processFile();
//		loadFileContents();
//		y=getTypeOfFile ();
//		loadFileChecksum();
		// the reference is created
		//fileRule1=new FileRuleSet1();
		//fileRule1.setCoreFile(this);
//		getFileRule1().setCoreFile(this);
//		String fileFilter = fileRule1.applyRulesSet1();
		
//		boolean b3 = isFileNameLengthValid();	
		
//		boolean b1 = isLastLineValid();
//		boolean b2 = isValidRecordCount();
//		if(fileFilter == true) {
//			int sum=getCaluclatedChecksum();
//		}
//		return fileFilter;
//	}
	
	public String getFileName()
	{
		fname=fi.getName();
		return fname;
	}
	
//	public boolean isFileNameLengthValid() {
//		//TODO: example file name = CB010817.186896
//		// 1 - read upto first dot in right side = CB010817
//		// 2 - the lenght of the result string shold be 8
//		// if so, return true... else false
//	
//		sa=fname.split("\\.");
//		ss=sa[sa.length-sa.length];
//		if(ss.length()==8)
//			return true;
//		return false;
//	}
	
	public int getTypeOfFile () {
		//TODO - exmple file name = CB010817.186896
		//1 - get first two characters of file name = CB
		
		y=fname.length();
		ss=fname.substring(0, fname.length()-(y-2));	
		if(ss.equals("CB"))
			return FILE_TYPE_CB;
		else if(ss.equals("CR"))
			return FILE_TYPE_CR;
		else if(ss.equals("CW"))
			return FILE_TYPE_CW;
		else		
			return 0;
	}
	
	public void loadFileChecksum(){
		//TODO - example file name = CB010817.186896
		// 1 - read from right to left.. first dot = 186896
		// 2 - load this value to private variable checksum
		
		sa=fname.split("\\.");
		ss=sa[sa.length-(sa.length-1)];
		checksum=ss;
	}
	
	
    //this method  the number of lines in the file
	public void processFile()	{		
		
		try {
			fr = new IndexedFileReader(fi);
			count=fr.getLineCount();
			ls=count-1;
			lsb=count-2;
			
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
				
	}
	
	public int getCaluclatedChecksum() {
		//TODO
		int sum=0;
		try  {
			br=new BufferedReader(new FileReader(fi) );
			while((line=br.readLine())!=null)  {
				char[] c=line.toCharArray();
				
				for(int i=0; i<=c.length-1; i++)  {
					sum+=c[i]* (i+1);
					if(sum>MAX_SUM_VALUE)  {
						su=sum % NORMALIZE_VALUE;
						sum=su;
					}
				}		
			}	
		}
		
		catch(IOException e) {
			logger.error(e);
			e.printStackTrace();
			}
		finally	{
			if(br!=null) {
				try {
					br.close();
				} 
				catch (IOException e) {
					logger.error(e);
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}				
	return sum;
		
	}

	public int getLineCount() {
		return count;
	}
	
//	public boolean isValidRecordCount() {
//		//TODO:
//		//TOTAL :   35
//		
//		String[] s;
//		s=LastButONeLine.split(":");
//		ss=s[s.length-1];
//		if(ss.length()==3){
//			s2=Integer.parseInt(ss.substring(ss.length()-1, ss.length()));
//		}
//		else if(ss.length()==4)  {
//			s2=Integer.parseInt(ss.substring(ss.length()-2, ss.length()));
//		}
//		else if(ss.length()==5)  {
//			s2=Integer.parseInt(ss.substring(ss.length()-3, ss.length()));
//		}
//		else if(ss.length()==6)  {
//			s2=Integer.parseInt(ss.substring(ss.length()-4, ss.length()));
//		}
//		if(s2==(count-3))   {
//				return true;
//		}
//		return false;
//	}
	
	//this method do a plain string compare, and return result
//	private boolean isLastLineValid()   {
//		//TODO: for later, it shld b string EQUAL, not oompare
//				if(LastLine.contains(strCompare)){
//					return true;			
//				}
//	
//		return false;
//			
//	}
	
    //this method print the last line of the given file
	public String getLastLine1() {
		
		return LastLine;
	}
	
	//this method print the last but one line of the given file
	public String getLastLine2() {

		return LastButONeLine;
	}
	
	public String getLine(int pos){
		String strLine=FileContents.get(pos+1);
		return strLine;
	}

	public void loadFileContents() {
		
		
		FileContents = new HashMap<Integer, String>();
		
		
		try {
			
			FileContents=fr.readLines(1,count);
			LastLine=FileContents.get(ls);
			LastButONeLine=FileContents.get(lsb);
			
			}
		catch (IOException e1) {
			logger.error(e1);
			e1.printStackTrace();
		}
	}

	public final FileRuleSet1 getFileRule1() {
		return fileRule1;
	}

	public final void setFileRule1(FileRuleSet1 fileRule1) {
		this.fileRule1 = fileRule1;
	}

	public void moveToApproved(File fname) {
//		fileRule1.moveToApprove(fname);
	}
	
}
