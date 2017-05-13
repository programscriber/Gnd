package com.indutech.gnd.service;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.util.POILogFactory;
import org.apache.poi.util.POILogger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.indutech.gnd.bo.ProductBO;
import com.indutech.gnd.dao.ProductDAOImpl;
import com.indutech.gnd.dto.MasterBank;
import com.indutech.gnd.dto.MasterDcms;
import com.indutech.gnd.dto.MasterType;
import com.indutech.gnd.dto.Product;
import com.indutech.gnd.enumTypes.Status;
import com.indutech.gnd.util.DataInitialization;
public class ExcelConverterBeanImpl implements ExcelConvertBean {
	
	private String productName = null;
	private String shortCode = null;
	private String binName = null;
	private String bankName = null;
	private long photoCard = 0;
	private String netName = null;	
	private String typeName = null;
	
	
	Logger logger = Logger.getLogger(ExcelConverterBeanImpl.class);
	POILogger log = POILogFactory.getLogger(XSSFWorkbook.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Autowired
	private ProductDAOImpl productDAO;	
	
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public final ProductDAOImpl getProductDAO() {
		return productDAO;
	}

	public final void setProductDAO(ProductDAOImpl productDAO) {
		this.productDAO = productDAO;
	}
	
	@Override
	@Transactional
	public void readExcel(MultipartFile file, String dcmsName) throws Exception {
		
		
		Status stat = Status.valueOf("ACTIVE");
		String status = stat.getStatus();
		try {
			File convFile = new File( file.getOriginalFilename());
	        file.transferTo(convFile);
	        InputStream is = new FileInputStream(convFile);
	        Workbook workbook = WorkbookFactory.create(is);
			
			Sheet sheet = workbook.getSheetAt(0);
			if (sheet != null) {
				Iterator<?> rowIterator = sheet.rowIterator();
				rowIterator.next();
				while (rowIterator.hasNext()) {
					XSSFRow row = (XSSFRow) rowIterator.next();
					XSSFCell bname = row.getCell(0);
					XSSFCell scode = row.getCell(1);
					XSSFCell pname = row.getCell(2);					
					XSSFCell bin   = row.getCell(3); 	
					XSSFCell nid   = row.getCell(4);					
					XSSFCell typeName = row.getCell(5);
					XSSFCell issueDate   = row.getCell(6);
					
					try {
						pname.setCellType(Cell.CELL_TYPE_STRING);
						productName = pname.getStringCellValue();
						scode.setCellType(Cell.CELL_TYPE_STRING);
						shortCode = scode.getStringCellValue();
						bin.setCellType(Cell.CELL_TYPE_STRING);
						binName =  bin.getStringCellValue();
						bankName =  bname.getStringCellValue();
						netName = nid.getStringCellValue();
						this.typeName = typeName.getStringCellValue();
						
						
//						issueDate.setCellType(Cell.CELL_TYPE_STRING);
						Date issuedate = issueDate.getDateCellValue();
						
						
						ProductBO productBO = new ProductBO();
						
						
						
						List<MasterBank> bankId = getProductDAO().getBankId(bankName);
						if(bankId.size() > 0) {
							MasterBank bank = (MasterBank) bankId.get(0);
							productBO.setBankId(bank.getBankId());
						}	
						
						List<MasterDcms> dcmsId = getProductDAO().getDcmsId(dcmsName);
						if(dcmsId.size() > 0) {
							MasterDcms dcms = (MasterDcms) dcmsId.get(0);
							productBO.setDcmsId(dcms.getDcmsId());
						}
						List<MasterType> typeId = getProductDAO().getTypeId(this.typeName);
						if(typeId.size() > 0) {
							MasterType masterType = (MasterType) typeId.get(0);
							productBO.setTypeId(masterType.getTypeId());
						}

						Long networkId = getProductDAO().getNetworkId(netName);
						
						productBO.setProductName(productName);
						productBO.setShortCode(shortCode);
						productBO.setBin(binName);
						productBO.setPhotoCard((long)0);
						productBO.setIssueDate(issuedate);
						productBO.setPhotoCard(photoCard);
						productBO.setNetworkId(networkId);
						productBO.setForthLineRequired((long) 0);
						productBO.setStatus(Long.parseLong(status));
						
						Product product = buildProduct(productBO);
						List<Product> list = getProductDAO().getProductList(shortCode,product.getBankId());
						if(list.size() > 0) {	
							Iterator<Product> iterator = list.iterator();
							while(iterator.hasNext()) {
								Product productCheck = (Product) iterator.next();
								getSessionFactory().getCurrentSession().evict(productCheck);
								product.setProductId(productCheck.getProductId());
								getProductDAO().saveProduct(product);
							}
						}
						else {
							getProductDAO().saveProduct(product);
						}
						DataInitialization.productData = null;
					} catch (NullPointerException e) {
						logger.error(e);
						continue;
					}
				}
			} 
		} catch (Exception fne) {
			logger.error(fne);
			fne.printStackTrace();
		}
	}
	
	public Product buildProduct(ProductBO productBO) {
		
		Product product = new Product();
		product.setProductId(productBO.getProductId());
		product.setProductName(productBO.getProductName());
		product.setShortCode(productBO.getShortCode());
		product.setBin(productBO.getBin());
		product.setTypeId(productBO.getTypeId());
		product.setBankId(productBO.getBankId());
		product.setPhotoCard(productBO.getPhotoCard());
		product.setDcmsId(productBO.getDcmsId());
		product.setNetworkId(productBO.getNetworkId());
		product.setIssueDate(productBO.getIssueDate());
		product.setForthLineRequired(productBO.getForthLineRequired());
		product.setStatus(productBO.getStatus());
		return product;
	}
}