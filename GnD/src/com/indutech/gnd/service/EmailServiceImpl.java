package com.indutech.gnd.service;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component("emailService")
public class EmailServiceImpl implements EmailService {
	
	Logger logger = Logger.getLogger(EmailService.class);

	common.Logger log = common.Logger.getLogger(EmailServiceImpl.class);
	
	@Autowired
	private JavaMailSender mailSender;


	@Autowired
	private SimpleMailMessage templateMessage;

	
	public void sendPreConfiguredMail(String body, String filePath) {
		
		MimeMessage message = mailSender.createMimeMessage();
		
		   try{
			   logger.info("email is preparing");
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
				
			helper.setFrom(templateMessage.getFrom());
			helper.setTo(templateMessage.getTo());
			helper.setSubject(templateMessage.getSubject());
			helper.setText(body);
				//attachment
			FileSystemResource file = new FileSystemResource(new File(filePath));
			if(file.exists()) {
				helper.addAttachment(file.getFilename(), file);
			}
			mailSender.send(message);
			logger.info("email sent successfully");
		     }catch (Exception e) {
		    	 logger.error(e);
		    	 e.printStackTrace();
			throw new MailParseException(e);
		     }
		
	}

}
