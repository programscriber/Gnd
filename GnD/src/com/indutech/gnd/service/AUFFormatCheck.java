package com.indutech.gnd.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.indutech.gnd.bo.drool.DroolRecordBO;
import com.indutech.gnd.dao.GNDDAOImpl;
import com.indutech.gnd.dto.Bank;

public class AUFFormatCheck {
	
	Logger logger = Logger.getLogger(AUFFormatCheck.class);
	
	@Autowired
	private AUFSecondFormatServiceImpl aufformat2service;
	
	@Autowired
	private FileConvertServiceImpl fileConvertServiceImpl;
	
	@Autowired
	private DroolRecordBO droolRecord;
	
	@Autowired
	private GNDDAOImpl gndDAO;
	
	
	
	public GNDDAOImpl getGndDAO() {
		return gndDAO;
	}



	public void setGndDAO(GNDDAOImpl gndDAO) {
		this.gndDAO = gndDAO;
	}



	public DroolRecordBO getDroolRecord() {
		return droolRecord;
	}



	public void setDroolRecord(DroolRecordBO droolRecord) {
		this.droolRecord = droolRecord;
	}



	public AUFSecondFormatServiceImpl getAufformat2service() {
		return aufformat2service;
	}



	public void setAufformat2service(AUFSecondFormatServiceImpl aufformat2service) {
		this.aufformat2service = aufformat2service;
	}



	public FileConvertServiceImpl getFileConvertServiceImpl() {
		return fileConvertServiceImpl;
	}



	public void setFileConvertServiceImpl(
			FileConvertServiceImpl fileConvertServiceImpl) {
		this.fileConvertServiceImpl = fileConvertServiceImpl;
	}


	@Transactional
	public synchronized void  aufFormatCheckAndProcess() {
		try {
			String fileName = getDroolRecord().getFileName();
			Bank bank = getGndDAO().getBankCodeByPrefix(fileName.substring(0,1));
			if(bank != null) {
				int format = bank.getAufFormat();
				switch(format) {
				case 1: 
					getFileConvertServiceImpl().convertTxt();
					break;
				case 2:
					getAufformat2service().FormatAUF();
					break;
				default:
				}
			}
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}
	
}
