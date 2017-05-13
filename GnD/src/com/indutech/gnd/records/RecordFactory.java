package com.indutech.gnd.records;

import java.io.BufferedReader;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

public class RecordFactory {
	
	@Autowired
	private Record rec;

	private BufferedReader br;
	private String line;
	private ArrayList <Record> recArray;
	private CoreFile fname;
		
	public final Record getRec() {
		return rec;
	}
	public final void setRec(Record rec) {
		this.rec = rec;
	}
	public void readFile(CoreFile fi) {		
			
		fname= fi;
		recArray=new ArrayList<Record>();
		int recCount = fname.getLineCount()-1;
		
		for (int i = 0; i < recCount; i++){
			String curLine = fname.getLine(i);
//			rec.getRecord(curLine);
//			rec.getNoOfFields();
//			rec.processRawRecord(null);
			recArray.add(rec);
				
		}				
	}
	
}
