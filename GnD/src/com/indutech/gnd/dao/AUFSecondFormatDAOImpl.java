package com.indutech.gnd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.indutech.gnd.dto.Bank;
import com.indutech.gnd.dto.CreditCardDetails;

@Repository("aufsecondformatdao")
public class AUFSecondFormatDAOImpl implements AUFSecondFormatDAO{
	
	Logger logger = Logger.getLogger(AUFSecondFormatDAOImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	

	
	// SQL  25- return the sequence value for the AUF file.
	
	public Long getSeqenceNumber(String filename, Long fileId, String coreFileName) {
		String sequence = "null";
		
			
			
		if(coreFileName.lastIndexOf("_P") != -1){
			try {
				sequence = (String) getSessionFactory().getCurrentSession().createSQLQuery("select max(substring(FILENAME,22,2)) as seqval from master_core_files where FILENAME like '"+filename+"%' and FILE_TYPE=2 order by seqval").list().get(0);
			}
			catch(Exception e) {
				logger.error(e);
				e.printStackTrace();
			}

		}else{
			try {
				
				sequence = (String) getSessionFactory().getCurrentSession().createSQLQuery("select max(substring(FILENAME,22,2)) as seqval from master_core_files where FILENAME like '"+filename+"%' and core_file_Id ="+fileId+" order by seqval").list().get(0);
			} catch(Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
		}
		return sequence !=null ? Long.parseLong(sequence)+1 : 01;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Bank getBankShortCodeByPrefix(String prefix) {
		List<Bank>	bank = null;
		try {
			bank = (List<Bank>) getSessionFactory().getCurrentSession().createCriteria(Bank.class).add(Restrictions.eq("prefix", prefix)).list();
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return bank != null && bank.size()>0? bank.get(0):null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getCreditCardDetailsByGroup(String bankShortCode, Long fileId,
			long cardStatusAwbassigned) {
		
		List group = null;
		try { 
			
			group = getSessionFactory().getCurrentSession().createQuery("select card.processedBranchCode, card.fileId, card.status, branch.lcpcName "
					+ "from CreditCardDetails card, Branch branch, Bank bank where "
					+ "card.processedBranchCode =branch.shortCode and branch.bankId = bank.bankId and "
					+ "bank.shortCode ='"+bankShortCode+"' group by card.processedBranchCode, card.fileId, card.status, branch.lcpcName having card.status = "+ cardStatusAwbassigned +" and card.fileId ="+fileId).list();
			
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return group;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CreditCardDetails> getDetailsList(String homeBranchCode,
			Long fileId, long cardStatusAwbassigned) {
		
		List<CreditCardDetails> list = null;
		try {
			list = getSessionFactory().getCurrentSession().createCriteria(CreditCardDetails.class)
					.add(Restrictions.eq("processedBranchCode", homeBranchCode))
					.add(Restrictions.eq("fileId", fileId)).add(Restrictions.eq("status", cardStatusAwbassigned))
					.addOrder(Order.asc("product")).list();
			
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();			
		}
		return list;
	}

	public Long getSeqenceNumber(String filename, Long fileId) {
//		String sequence = null;
//		try {
//			sequence = (String) getSessionFactory().getCurrentSession().createSQLQuery("select max(substring(FILENAME,19,2)) as seqval from master_core_files where FILENAME like '"+filename+"%' and core_file_Id ="+fileId+" order by seqval").list().get(0);
//		} catch(Exception e) {
//			logger.error(e);
//			e.printStackTrace();
//		}
//		return sequence !=null ? Long.parseLong(sequence)+1 : 01;
		
		
		 final List<String> query = new ArrayList<String>();
		
			 getSessionFactory().getCurrentSession().doWork(new Work() {
				
				@Override
				public void execute(Connection arg0) throws SQLException {
					
					PreparedStatement pstmt = arg0.prepareStatement("select max(substring(FILENAME,19,2)) as seqval from master_core_files where FILENAME like ? and core_file_Id =? order by seqval");
					pstmt.setString(1,filename+"%");
					pstmt.setLong(2, fileId);
					ResultSet rs= pstmt.executeQuery();
					if(rs.next())
					{
						query.add(rs.getString(1));
					}	
					rs.close();
					pstmt.close();
					
				}
			});
			//System.out.println("filename :"+filename+":fileId:"+fileId+query);
			 return query.size() !=0 && query.get(0)!= null ? Long.parseLong(query.get(0)):0L;
			
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<CreditCardDetails> getCreditCardDetails(Long bankId,
			Long fileId, long cardStatusAwbassigned) {
		List<CreditCardDetails> list = null;
		try {
			list = getSessionFactory().getCurrentSession().createCriteria(CreditCardDetails.class)
							.add(Restrictions.eq("fileId", fileId))
							.add(Restrictions.eq("bankId", bankId))
							.add(Restrictions.eq("status", cardStatusAwbassigned))
							.addOrder(Order.asc("rsn")).list();
		} catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		
		return list;
	}
}
