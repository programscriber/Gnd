package com.indutech.gnd.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.indutech.gnd.dto.CoreFiles;
import com.indutech.gnd.enumTypes.FileStatus;
import com.indutech.gnd.enumTypes.FileType;
import com.indutech.gnd.util.StringUtil;

public class FileDAOImpl implements FileDAO {
	Logger logger = Logger.getLogger(FileDAOImpl.class);
	common.Logger log = common.Logger.getLogger(FileDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Long saveFile(CoreFiles file) {
		
		getSessionFactory().getCurrentSession().saveOrUpdate(file);
		return file.getId();
	}
	
	public void flush()
	{
		getSessionFactory().getCurrentSession().flush();
	}
	
	
	/*cr no 42 start*/
	@Transactional
	public void deletePgpFileLog(CoreFiles coreFile)
	{
		try{
			getSessionFactory().getCurrentSession().delete(coreFile);
		}catch (Exception e){
			
			e.printStackTrace();
		}
	}
	@Transactional
	public Long saveIt(CoreFiles file)
	{
		getSessionFactory().getCurrentSession().save(file);
		return file.getId();
	}
	@Transactional
	public CoreFiles getFile(Long fileId)
	{	
		List<CoreFiles> file = null;
		CoreFiles coreFile = null;
		file = getSessionFactory().getCurrentSession().createCriteria(CoreFiles.class)
				.add(Restrictions.eq("id",fileId)).list();	
			if(file.size()>0)
			{
				coreFile = file.get(0);
			}
		return coreFile;
	}
	/*cr no 42 end*/
	@SuppressWarnings("unchecked")
	@Override
	public List<CoreFiles> getFiles(Map<String, String> reqMap) {
		String fileName = reqMap.get("FILE_NAME");
		String bank = reqMap.get("BANK");
		String branch = reqMap.get("BRANCH");
		String status = reqMap.get("STATUS");
		String dateFrom = reqMap.get("DATE_FROM");
		String dateTo = reqMap.get("DATE_TO");
		Criteria fileSearchCriteria = getSessionFactory().getCurrentSession()
				.createCriteria(CoreFiles.class);
		if (!StringUtil.isEMptyOrNull(fileName)) {
			fileSearchCriteria.add(Restrictions.eq("filename", fileName));
		}
		/*
		 * if (!StringUtil.isEMptyOrNull(bank)) { } if
		 * (!StringUtil.isEMptyOrNull(branch)) { }
		 */
		if (!StringUtil.isEMptyOrNull(status)) {
			if (!status.isEmpty()) {
				if (!status.contains("0")) {
					String[] strStatusArray = status.split(",");
					List<Long> statusList = new ArrayList<Long>();
					for (String string : strStatusArray) {
						statusList.add(Long.parseLong(string));
					}
					fileSearchCriteria.add(Restrictions
							.in("status", statusList));
					logger.info(statusList);
				}
			}
		}
		if (!StringUtil.isEMptyOrNull(dateFrom)
				&& !StringUtil.isEMptyOrNull(dateTo)) {
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date startDate = dateFormat.parse(dateFrom);
				Date endDate = dateFormat.parse(dateTo);
				fileSearchCriteria.add(Restrictions.ge("receivedDate", startDate));
				fileSearchCriteria.add(Restrictions.le("receivedDate", endDate));

			} catch (HibernateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		List<CoreFiles> list = fileSearchCriteria.list();
		return list;
	}

	@Override
	public List<CoreFiles> getAUFList() {
		Long fileType = Long.parseLong(FileType.valueOf("AUF").getFileType());
		Long fileStatus = Long.parseLong(FileStatus.valueOf("AUF_CONVERTED").getFileStatus());
		List<CoreFiles> list = null;
		try {
		list = getSessionFactory().getCurrentSession().createCriteria(CoreFiles.class).add(Restrictions.eq("fileType", fileType))
																	  .add(Restrictions.eq("status", fileStatus)).list();
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}
	
	public List<CoreFiles> getAUFFile(String filename, String extension) {
		List<CoreFiles> list = null;
		try {
//			list = getSessionFactory().getCurrentSession().createSQLQuery(""
//					+ "select * from MASTER_CORE_FILES where filename LIKE '___"+filename+"______."+extension+"' and STATUS="+
//					Long.parseLong(FileStatus.valueOf("AUF_CONVERTED").getFileStatus())+" and file_type="+
//					Long.parseLong(FileType.valueOf("AUF").getFileType())).list();
			
			list = getSessionFactory().getCurrentSession().createQuery(""
					+ "from CoreFiles where filename LIKE '___"+filename+"______."+extension+"' and status="+
					Long.parseLong(FileStatus.valueOf("AUF_CONVERTED").getFileStatus())+" and fileType="+
					Long.parseLong(FileType.valueOf("AUF").getFileType())).list();
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return list;
	}



	public List<CoreFiles> getFileList(String fileName) {
		List<CoreFiles> list = null;
		try {
			 list = getSessionFactory().getCurrentSession().createCriteria(CoreFiles.class).
					add(Restrictions.eq("filename", fileName)).list();
		} catch(Exception e ) {
			logger.error(e);
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return list;
	}
	
	public String getFileName(Long fileId) {
		String fileName = null;
		try {
			logger.info("file id is : "+fileId);
			 fileName = (String) getSessionFactory().getCurrentSession().createCriteria(CoreFiles.class).add(Restrictions.eq("id", fileId))
										.setProjection(Projections.property("filename")).list().get(0);
			 
			 logger.info("file name is : "+fileName);
		}
		catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return fileName;
	}

	@Transactional
	public List<CoreFiles> getFilesByDate(Date receivedDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		List<CoreFiles> list = null;
		try {
			/*cr 48 starts*/
			list = getSessionFactory().getCurrentSession().
					   createSQLQuery("select * from MASTER_CORE_FILES files where ID in ( select ID from MASTER_CORE_FILES files,CUSTOMER_RECORDS_T cust where  files.STATUS ="+Long.parseLong(FileStatus.valueOf("APPROVED").getFileStatus())+" and convert(date, files.RECEIVED_DATE) = '"
							+ sdf.format(receivedDate)+"' and files.FILE_TYPE ="+Long.parseLong(FileType.valueOf("CORE").getFileType())+"and files.ID = cust.FILE_ID and cust.STATUS between 0 and 2)").addEntity(CoreFiles.class).list();
			/*cr 48 starts*/
					 
		logger.info(list);
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
		
		return list;
	}

	/*cr no 42 starts*/ 
	@Transactional
	 @SuppressWarnings("unchecked")
	public List<CoreFiles> getExistingFile(String fileName)
	 {
		 
		Session session = null;
		 List<CoreFiles> fileList = null;
		 try {
			 System.out.println("db check");
			 //session = sessionFactory.getCurrentSession();
				fileList = getSessionFactory().getCurrentSession().createCriteria(CoreFiles.class)
					.add(Restrictions.like("filename","%"+fileName+"%")).list();	
				System.out.println(fileList.size());
			} catch(Exception e) {
				e.printStackTrace();
				System.out.println("NPE");
			}
			return fileList;
		 
	 }
	@Transactional
	 public String getEmboBatchName(Long fileId)
	 {
		 List<CoreFiles> fileList = null;
		 String emboBatchName = null;
		 try {
				fileList = getSessionFactory().getCurrentSession().createCriteria(CoreFiles.class)
					.add(Restrictions.eq("id",fileId)).list();	
				if(fileList.size()>0)
				{
					CoreFiles file = fileList.get(0);
					emboBatchName = file.getFilename();
					
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			return emboBatchName;
	 }
	
	@Transactional  
	public List<CoreFiles> getEmbossFile(String fileName,String qcDate)
	{
		List<CoreFiles> fileList = null;
		 try {
			 Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(CoreFiles.class);
					 criteria.add(Restrictions.like("filename","%"+fileName.substring(0,3)+"%"));	
					 criteria.add(Restrictions.like("filename","%"+fileName.substring(3,11)+"%"));	
					 criteria.add(Restrictions.like("filename","%"+qcDate+"%"));
					 criteria.add(Restrictions.like("filename","%zip.pgp%"));
					 criteria.addOrder(Order.asc("id"));
					 fileList = criteria.list();
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		 return fileList;
	}
/*cr no 42 ends*/
	
}
