package com.indutech.gnd.service;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.indutech.gnd.bo.CoreFileSummaryBO;
import com.indutech.gnd.emailService.EmailLaunchService;
import com.indutech.gnd.enumTypes.Status;

public class AWBThreshouldService {
	
	Logger logger = Logger.getLogger(AWBThreshouldService.class);
	
	Properties properties = PropertiesLoader.getInstance().loadProperties();
	
	@Autowired
	private SessionFactory sessionFactory;
	
	

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}



	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}



	@Transactional
	public void getAWBStatus(){
		Long awbLimit = Long.parseLong(properties.getProperty("awbThreshouldLimit"));
		Integer blueDartCheck = Integer.parseInt(properties.getProperty("isBlueDartCheck"));
		boolean emailSend = false;
		String qry  =  "select distinct service_provider_id, SERVICE_PROVIDER_NAME,"
				  +" (select count(*) from master_awb_t where status = 3 and service_provider_id = cs.id) as used, "
				+" (select count(*) from master_awb_t where status = 1 and service_provider_id = cs.id) as unused, "
				+" (select max(awb) from master_awb_t where status = 1 and service_provider_id = cs.id) as max_value, "
				+" (select min(awb) from master_awb_t where status = 1 and service_provider_id = cs.id) as min_value "
				 +" from MASTER_COURIER_SERVICE_T cs, master_awb_t where service_provider_id= cs.id";
		@SuppressWarnings("unchecked")
		List<Object[]> list=getSessionFactory().getCurrentSession().createSQLQuery(qry).list();
		String emailText = "";  
		try {
			String emailString="";
			 emailString+="<html><body><p>"+properties.getProperty("paragraphtxt")+"</p></br><table style='border:1px solid black;'>";
			 emailString+="<tr style='border: 1px solid black;'><th style='border: 1px solid black;'>Service Provider Name</th><th style='border: 1px solid black;'>Number of AWB used</th><th style='border: 1px solid black;'>Number of AWB unused</th><th style='border: 1px solid black;'>Start value of Available</th><th style='border: 1px solid black;'>End value of Available</th></tr>";

			Iterator it = list.iterator();
			   while(it.hasNext()){
				   Object[] obj = (Object[]) it.next();
				   String read = (String) String.valueOf(obj[0]+"  "+String.valueOf(obj[1])+"  "+String.valueOf(obj[2])+"  "+String.valueOf(obj[3]));
				   
			   		 
				   
				   String availableKey = String.valueOf(obj[3]);
				   Long aval = Long.valueOf(availableKey);
				   Long serviceProvider = ((BigDecimal) obj[0]).longValue();
				   if(serviceProvider == (long) CorierServiceProviders.BLUE_DART) {
					   if(blueDartCheck == 1 ) {
						   	if(aval < awbLimit){	
						   			emailSend = true;
						   			
						   			emailString+="<tr style='border: 1px solid black;'>";
							         emailString+="<td style='border: 1px solid black;'>";
							         emailString+=String.valueOf(obj[1]);
							         emailString+="</td>";

							         emailString+="<td style='border: 1px solid black;'>";
							         emailString+=String.valueOf(obj[2]);
							         emailString+="</td>";

							         emailString+="<td style='border: 1px solid black;'>";
							         emailString+=String.valueOf(obj[3]);
							         emailString+="</td>";

							         emailString+="<td style='border: 1px solid black;'>";
							         emailString+=String.valueOf(obj[4]);
							         emailString+="</td>";
							         
							         emailString+="<td style='border: 1px solid black;'>";
							         emailString+=String.valueOf(obj[5]);
							         emailString+="</td>";
							         
							         emailString+="<tr>";
						   	}
					   }
				   } else if(aval < awbLimit){	
					   	emailSend = true;
					   	
					   	emailString+="<tr style='border: 1px solid black;'>";
				         emailString+="<td style='border: 1px solid black;'>";
				         emailString+=String.valueOf(obj[1]);
				         emailString+="</td>";

				         emailString+="<td style='border: 1px solid black;'>";
				         emailString+=String.valueOf(obj[2]);
				         emailString+="</td>";

				         emailString+="<td style='border: 1px solid black;'>";
				         emailString+=String.valueOf(obj[3]);
				         emailString+="</td>";

				         emailString+="<td style='border: 1px solid black;'>";
				         emailString+=String.valueOf(obj[4]);
				         emailString+="</td>";
				         
				         emailString+="<td style='border: 1px solid black;'>";
				         emailString+=String.valueOf(obj[5]);
				         emailString+="</td>";
				         
				         emailString+="<tr>";
				   }
			   }
			   emailString+="</table></body></html>";
			   if(emailSend == true) {
				   EmailLaunchService emaillauncher=new EmailLaunchService();
					emaillauncher.sendHtmlEmail(emailString, properties);
			   }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void sendMail(){
		String to = "sreenu@indutech-labs.com";
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", "465");
		Session session = Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("venky.pothuluri@gmail.com", "9492691931");
				
			}});
		try{
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("venky.pothuluri@gmail.com"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setText("Service Provider Name having less than 25000");
			Transport.send(message);
			System.out.println("message sent successfully");
			
		}catch(MessagingException e){
			throw new RuntimeException(e);
		}
	}
}
