package com.indutech.gnd.reports;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.indutech.gnd.bo.CreditCardDetailsBO;
import com.indutech.gnd.dao.FileConverterDAOImpl;
import com.indutech.gnd.dto.CreditCardDetails;
import com.indutech.gnd.pdf.GenPdfFile;
import com.indutech.gnd.service.FileConvertServiceImpl;

public class ReportGenerator {
	
	@Autowired
	private FileConverterDAOImpl fileConverterDAO;
	
	@Autowired
	private FileConvertServiceImpl fileconvertserviceimpl;

	private File file;
	private BufferedWriter bw;
	private FileWriter fw;
	
	
	
	public void generateReports()	{
		approvedFileReport();
		holdFileReport();
		rejectedFileReport();
	}
	
	@SuppressWarnings("rawtypes")
	@Transactional
	public void approvedFileReport()	{
		
		try	{
				GenPdfFile genpdf=new GenPdfFile();
				String path="F:/gnd/WORK/REPORT";
				String filename;
				SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmss");//dd/MM/yyyy
				Date now = new Date();
				String strDate = sdfDate.format(now);
				filename="ApprovedRecordReport"+strDate;
//				file=new File(path+"/"+filename+".txt");
				
				
				genpdf.createPdf(path+"/"+filename+".pdf");
				genpdf.openPdf();
				genpdf.writeToPdf("Approved Record Reports");
//				fw = new FileWriter(file.getAbsoluteFile());
//				bw = new BufferedWriter(fw);
				List group= getFileConverterDAO().getApprovedCreditCardDetails();
				if(group.size() > 0) {
					Iterator iterator = group.iterator();
					while(iterator.hasNext()) {
						Object[] obj = (Object[]) iterator.next();
						List<CreditCardDetails> list = getFileConverterDAO().getAppGroup((String)obj[0], (String)obj[1], (String)obj[2]);
						if(list.size() > 0) {
							Iterator iterator1 =  list.iterator();								
							while(iterator1.hasNext()) {
								CreditCardDetails creditCardDetails = (CreditCardDetails) iterator1.next();
								CreditCardDetailsBO details = getFileConvertServiceImpl().buildCreditCardDetailsBORej(creditCardDetails);
								String result=getFileConvertServiceImpl().bodyRej(details, bw);
								genpdf.writeToPdf(result);
			
								}
							}
						}
						
					}
			
//				bw.close();
				genpdf.closePdf();
				
				}
				catch(Exception io){
					io.printStackTrace();
				}
			
			}
			
	@SuppressWarnings("rawtypes")
	@Transactional
	public void rejectedFileReport()	{
		try	{
			GenPdfFile genpdf=new GenPdfFile();
			String path="F:/gnd/WORK/REPORT";
			String filename;
			SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmss");//dd/MM/yyyy
			Date now = new Date();
			String strDate = sdfDate.format(now);
			filename="RejectedRecordReport"+strDate;
//			file=new File(path+"/"+filename+".txt");						
//			fw = new FileWriter(file.getAbsoluteFile());
//			bw = new BufferedWriter(fw);
			genpdf.createPdf(path+"/"+filename+".pdf");
			genpdf.openPdf();
			
			genpdf.writeToPdf("Reject Record Reports");
				List group= getFileConverterDAO().getRejectedCreditCardDetails();
				if(group.size() > 0) {
					Iterator iterator = group.iterator();
					while(iterator.hasNext()) {
						Object[] obj = (Object[]) iterator.next();
						List<CreditCardDetails> list = getFileConverterDAO().getRejGroup((String)obj[0], (String)obj[1], (String)obj[2]);
						if(list.size() > 0) {
							Iterator iterator1 =  list.iterator();								
							while(iterator1.hasNext()) {
								CreditCardDetails creditCardDetails = (CreditCardDetails) iterator1.next();
								CreditCardDetailsBO details = getFileConvertServiceImpl().buildCreditCardDetailsBORej(creditCardDetails);	
								String result=getFileConvertServiceImpl().bodyRej(details, bw);
								genpdf.writeToPdf(result);
					
							}
						}
						
					}
				}
//				bw.close();
				genpdf.closePdf();
			}
			
			catch(Exception io){		
					io.printStackTrace();
			}
		
	}
	
	@SuppressWarnings("rawtypes")
	@Transactional
	public void holdFileReport()	{
	
		try	{
			GenPdfFile genpdf=new GenPdfFile();
			String path="F:/gnd/WORK/REPORT";
			String filename;
			SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmss");//dd/MM/yyyy
			Date now = new Date();
			String strDate = sdfDate.format(now);
			filename="HoldRecordReport"+strDate;
//			file=new File(path+"/"+filename+".txt");						
//			fw = new FileWriter(file.getAbsoluteFile());
//			bw = new BufferedWriter(fw);
			genpdf.createPdf(path+"/"+filename+".pdf");
			genpdf.openPdf();
			genpdf.writeToPdf("Hold Record Reports");		
			List group= getFileConverterDAO().getHoldCreditCardDetails();
			if(group.size() > 0) {
				Iterator iterator = group.iterator();
				while(iterator.hasNext()) {
					Object[] obj = (Object[]) iterator.next();
					List<CreditCardDetails> list = getFileConverterDAO().getHoldGroup((String)obj[0], (String)obj[1], (String)obj[2]);
					if(list.size() > 0) {
						Iterator iterator1 =  list.iterator();				
						
						while(iterator1.hasNext()) {
							CreditCardDetails creditCardDetails = (CreditCardDetails) iterator1.next();
							CreditCardDetailsBO details = getFileConvertServiceImpl().buildCreditCardDetailsBORej(creditCardDetails);
							String result=getFileConvertServiceImpl().bodyRej(details, bw);
							genpdf.writeToPdf(result);
							
						}
					}
					
				}
			}
//			bw.close();
			genpdf.closePdf();
		}
		catch(Exception io){
			io.printStackTrace();
		}
			
	}
	
	public final FileConverterDAOImpl getFileConverterDAO() {
		return fileConverterDAO;
	}
	public final void setFileConverterDAO(FileConverterDAOImpl fileConverterDAO) {
		this.fileConverterDAO = fileConverterDAO;
	}	

	public final FileConvertServiceImpl getFileConvertServiceImpl() {
		return fileconvertserviceimpl;
	}
	public final void setFileConvertServiceImpl(FileConvertServiceImpl fileconvertserviceimpl) {
		this.fileconvertserviceimpl = fileconvertserviceimpl;
	}	

}
