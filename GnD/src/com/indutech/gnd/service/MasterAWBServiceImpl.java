package com.indutech.gnd.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//import org.apache.jasper.compiler.AntCompiler.JasperAntLogger;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.indutech.gnd.bo.AwbBO;
import com.indutech.gnd.bo.MasterAWBBO;
import com.indutech.gnd.bo.MasterCourierServiceBO;
import com.indutech.gnd.dao.MasterAWBDAOImpl;
import com.indutech.gnd.dto.MasterAWB;
import com.indutech.gnd.dto.MasterCourierService;
import com.indutech.gnd.enumTypes.Status;
import com.indutech.gnd.jasper.reports.JasperReportGenerator;
import com.indutech.gnd.util.StringUtil;
import com.indutech.gnd.vali.NumberString;

public class MasterAWBServiceImpl implements MasterAWBService {
	
	Logger logger = Logger.getLogger(MasterAWBDAOImpl.class);

	@Autowired
	private MasterAWBDAOImpl masterAWBDAO;
	


	@Autowired
	private NumberString series;
	
	private String awbName;
	private Long serviceId;
	
	Status activeStatus = Status.valueOf("ACTIVE");
	String activeStatusID = activeStatus.getStatus();
	
	List<MasterCourierServiceBO> courier = null;
	
	@Override
	@Transactional
	public String importMasterAMB(MultipartFile file, String service) { 
		String result = "Failed to import AWB values";
		try {
		serviceId = getMasterAWBDAO().getServiceProviderId(service);
		File convFile = new File( file.getOriginalFilename());
        file.transferTo(convFile);
        InputStream is = new FileInputStream(convFile);
        Workbook workbook = WorkbookFactory.create(is);
		
		Sheet sheet = workbook.getSheetAt(0);
		if (sheet != null) {
			Iterator<?> rowIterator = sheet.rowIterator();
			rowIterator.next();
			int i = 0;
			int j = 0; 
			while (rowIterator.hasNext()) {
				XSSFRow row = (XSSFRow) rowIterator.next();
				XSSFCell awb = row.getCell(0);   awb.setCellType(1);
				
				awbName = awb.getStringCellValue(); 
				//System.out.println(" service starts");
		if(!StringUtil.isEMptyOrNull(awbName) && serviceId == 1 && awbName.startsWith("ET")||!StringUtil.isEMptyOrNull(awbName) && serviceId == 1 && awbName.startsWith("EX") ||!StringUtil.isEMptyOrNull(awbName) && serviceId == 1 && awbName.startsWith("EB"))
		{
					//System.out.println("chennai service");
//					List<MasterAWB> list = getMasterAWBDAO().getMasterAWB(awbName);
//					if(list == null || list.size() == 0) {
						MasterAWB masterAWB = new MasterAWB();
						masterAWB.setAwbName(awbName);				
						masterAWB.setServiceProviderId(serviceId);				
						masterAWB.setStatus(Long.parseLong(activeStatusID));
						getMasterAWBDAO().saveMasterAWB(masterAWB);
						i++; 
//					} else {
//						j++;
//					}
				if(j == 0) {
					result = i + " new awb keys are successfully added to "+service;
				} else {
					result = i + " new awb keys inserted and "+j+" keys already present for the service provider "+service;
				}
				}
		else if(!StringUtil.isEMptyOrNull(awbName) && serviceId == 3 && awbName.startsWith("EA") )
		{
			
//				List<MasterAWB> list = getMasterAWBDAO().getMasterAWB(awbName);
//				if(list == null || list.size() == 0) {
					MasterAWB masterAWB = new MasterAWB();
					masterAWB.setAwbName(awbName);				
					masterAWB.setServiceProviderId(serviceId);				
					masterAWB.setStatus(Long.parseLong(activeStatusID));
					getMasterAWBDAO().saveMasterAWB(masterAWB);
					i++; 
//				} else {
//					j++;
//				}
			
				if(j == 0) {
					result = i + " new awb keys are successfully added to "+service;
				} else {
					result = i + " new awb keys inserted and "+j+" keys already present for the service provider "+service;
			}
		}
		else
		{
			if(serviceId == 1)
			{
			result = "Upload awb starts with 'ET','EX','EB' for India Post Chennai";
			break;
			}
			else if(serviceId == 3)
			{
				result = "Upload awb starts with 'EA' for India Post Mumbai ";
			break;
			}
			else
			{
				result = "BlueDart service not available";
				break;
			}
				}
			}
		}
		} catch(Exception io) {
			logger.error(io);
			io.printStackTrace();
		} 
		return result;
	}
	
	@Override
	@Transactional
	public String importMasterAMB(String fromValue, String toValue, String service) {
		String result = "Failed to import AWB values";
		try {
		List<String> awbList = series.generateAWB(fromValue, toValue);
		if(awbList != null) {
			serviceId = getMasterAWBDAO().getServiceProviderId(service);
			int i = 0;
			int j = 0; 
			for(String awbName : awbList) {
				List<MasterAWB> list = getMasterAWBDAO().getMasterAWB(awbName);
				if(list == null || list.size() == 0) {
					MasterAWB masterAWB = new MasterAWB();
					masterAWB.setAwbName(awbName);				
					masterAWB.setServiceProviderId(serviceId);				
					masterAWB.setStatus(Long.parseLong(activeStatusID));
					getMasterAWBDAO().saveMasterAWB(masterAWB);
					i++;
				}
				else {
					j++;
				}
			}
			if(j == 0) {
				result = i + " new awb keys are successfully added to "+service;
			} else {
				result = i + " new awb keys inserted and "+j+" keys already present for the service provider "+service;
			}
		}
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	@Transactional
	public List<AwbBO> getAwbStatistics() {
		
		
		
		List<Object[]>  masterlist =getMasterAWBDAO().getAwbCounts();
		List<AwbBO> awblist =new ArrayList<AwbBO>();
		
		
		for(int i=0;i<masterlist.size();i++){
			Object[] master=masterlist.get(i);
			AwbBO bo = new AwbBO();
			bo.setServiceprovidername((String)master[0]);
			bo.setUsed((int)master[1]);
			bo.setUnused((int)master[2]);
			bo.setStartvalue((String)master[3]);
			bo.setEndvalue((String)master[4]);
			awblist.add(bo);
			System.out.println(awblist);
		}
		return awblist;
	
		
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional
	public List<MasterCourierServiceBO> getServiceProvider() {
		
		courier = new ArrayList<MasterCourierServiceBO>();
		try {
		List<MasterCourierService> list =getMasterAWBDAO().getServiceProviderList();
		Iterator iterator = list.iterator();
		while(iterator.hasNext()) {
			MasterCourierServiceBO service = buildMasterCourierServiceBO((MasterCourierService) iterator.next());
			courier.add(service);
		}
		}
		catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return courier;
		
	}
	
			
	public MasterAWB buildMasterAWB(MasterAWBBO masterAWBBO) {
		
	MasterAWB masterAWB = new MasterAWB();
	masterAWB.setAwbName(masterAWBBO.getAwbName());
	masterAWB.setServiceProviderId(masterAWBBO.getServiceProviderId());
	masterAWB.setStatus(masterAWBBO.getStatus());
	
	return masterAWB;
	}
	
	public MasterCourierServiceBO buildMasterCourierServiceBO(MasterCourierService mcs) {
		MasterCourierServiceBO service = new MasterCourierServiceBO();
		service.setId(mcs.getId());
		service.setServiceProviderName(mcs.getServiceProviderName());
		service.setContactPersionName(mcs.getContactPersionName());
		service.setPhone(mcs.getPhone());
		service.setEmail(mcs.getEmail());
		
		return service;
	}

	@Transactional
	public List getproductServ(){
		List result=getMasterAWBDAO().getproductDao();
		return result;
	}
	
	
	public final MasterAWBDAOImpl getMasterAWBDAO() {
		return masterAWBDAO;
	}

	public final void setMasterAWBDAO(MasterAWBDAOImpl masterAWBDAO) {
		this.masterAWBDAO = masterAWBDAO;
	}

	public final NumberString getSeries() {
		return series;
	}

	public final void setSeries(NumberString series) {
		this.series = series;
	}

	

	
}
