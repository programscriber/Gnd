package com.indutech.gnd.records;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.indutech.gnd.dao.FindPathImpl;
import com.indutech.gnd.service.FileConvertServiceImpl;

@Component("filedir")
public class FileDirectory {
	
	List<String> list = new ArrayList<String>();
	
	@Autowired
	private CoreFile fileObj;
	
	@Autowired
	private FileConvertServiceImpl fileConvertServiceImpl;
	
	@Autowired
	private RecordFactory rf;
	
	@Autowired
	private FindPathImpl findPath;			
		
	public final FindPathImpl getFindPath() {
		return findPath;
	}
	public final void setFindPath(FindPathImpl findPath) {
		this.findPath = findPath;
	}
	public final FileConvertServiceImpl getFileConvertServiceImpl() {
		return fileConvertServiceImpl;
	}
	public final void setFileConvertServiceImpl(
			FileConvertServiceImpl fileConvertServiceImpl) {
		this.fileConvertServiceImpl = fileConvertServiceImpl;
	}
	
	public final RecordFactory getRf() {
		return rf;
	}

	public final void setRf(RecordFactory rf) {
		this.rf = rf;
	}

	public final CoreFile getFileObj() {
		return fileObj;
	}

	public final void setFileObj(CoreFile fileObj) {
		this.fileObj = fileObj;
	}

	private static File fname;
	//private String InputFilePath = "D:/GD_FOLDER/WORK/INPUTS"; 
	
//	//@Transactoinal
//	public List<String> isFileDirectory() {
//		list.clear();
//		String InputFilePath =  getFindPath().getPath("INPUTS");
//		fname=new File(InputFilePath);
//		if(fname.exists() && fname.isDirectory()) {
//			File[] flist=fname.listFiles();
//			for (File file : flist) {
//				
//			    if (file.isFile()) {
//			    	String fname = file.getAbsolutePath();
//			    	String result = fileObj.applyRules(fname);	
//			    	list.add(result);
//			    	//todo- move into file	
//			    	if(result.contains("Success")) {
//			    		rf.readFile(fileObj);
//			    		fileObj.moveToApproved(file);
//			    	}
//			       
//			    }
//			}			
//		}
//		fileConvertServiceImpl.convertTxt();
//		return list;
//	
//	}
		
	
}
