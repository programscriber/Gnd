package com.indutech.gnd.emailService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.rtf.RTFEditorKit;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class EmailLaunchService {
	 Logger logger = Logger.getLogger(EmailLaunchService.class.getName());
	
	public EmailLaunchService(HashMap<String, String> emailProperties,
			List<String> unselectedlist) {
		// TODO Auto-generated constructor stub
	}

	public EmailLaunchService() {
		// TODO Auto-generated constructor stub
	}

	public void readFilesSendEmail() {
		Properties properties = new Properties();
		InputStream input = null;
		String text = null;
		BufferedReader br = null;
		try {
			
		//	input = new FileInputStream("D:/repository/CIS/email_jar/src/config.properties");
			
			// load a properties file
			//properties.load(input);
			File folder = new File(properties.getProperty("destinationPath"));
			System.out.println(folder);
			
			File[] listOfFiles = folder.listFiles();
			
			for (File file : listOfFiles) {
				StringBuffer textBuffer = new StringBuffer();
				String textfileContent="";
				if (file.isFile()) {
					if(FilenameUtils.getExtension(file.getName().trim()).trim().equals("txt".trim())){
						br = new BufferedReader(new FileReader(file));
						while ((text = br.readLine()) != null) {
							textBuffer.append(text+"\n ");
						}
						emailSender(textBuffer.toString(),getToEmail(file.getName()),
								properties);

					}else if(FilenameUtils.getExtension(file.getName().trim()).trim().equals("doc".trim())){
						HWPFDocument document= new HWPFDocument(new FileInputStream(file));
						textfileContent=document.getDocumentText();
						emailSender(textfileContent,getToEmail(file.getName()),
								properties);

					}else if(FilenameUtils.getExtension(file.getName().trim()).trim().equals("rtf".trim())){
						 RTFEditorKit RTFEditorKit = new RTFEditorKit();
				         Document RTFdoc = RTFEditorKit.createDefaultDocument();
				         RTFEditorKit.read(new BufferedReader(new FileReader(file)), RTFdoc, 0);
				         textfileContent = RTFdoc.getText(0, RTFdoc.getLength()).trim();
				         emailSender(textfileContent,getToEmail(file.getName()),
									properties);
					}else if(FilenameUtils.getExtension(file.getName().trim()).trim().equals("docx".trim())){
						 XWPFDocument docx = new XWPFDocument(new FileInputStream(file));
						 @SuppressWarnings("resource")
						XWPFWordExtractor we = new XWPFWordExtractor(docx);
						 textfileContent =  we.getText();
						 emailSender(textfileContent,getToEmail(file.getName()),
									properties);
					}
					
					
					logger.info("File Name:"+file.getName());
					
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("File Not Found", e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("IO Exception", e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String getToEmail(String name) {
		String toEmail ="";
		System.out.println("email extention"+FilenameUtils.getExtension(name));
		if(FilenameUtils.getExtension(name).trim().equals("doc")||FilenameUtils.getExtension(name).trim().equals("txt")||FilenameUtils.getExtension(name).trim().equals("rtf")){
		String[] tokens =name.split("_");
		toEmail= tokens[tokens.length-1];
		toEmail = toEmail.substring(0, toEmail.length()-4);
		System.out.println("doc"+toEmail);
		}else if(FilenameUtils.getExtension(name).trim().equals("docx")){
			String[] tokens =name.split("_");
			toEmail= tokens[tokens.length-1];
			toEmail = toEmail.substring(0, toEmail.length()-5);
			System.out.println("docx"+ toEmail);
		}
		return toEmail;
	}

	public void emailSender(String text,String toEmail,Properties properties) {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", properties.getProperty("host"));
		props.put("mail.smtp.port",  properties.getProperty("port"));
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(properties.getProperty("username"), properties.getProperty("password"));
					}
				});
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(properties.getProperty("fromEmail")));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(toEmail));
			message.addRecipients(Message.RecipientType.CC,
					InternetAddress.parse(properties.getProperty("ccEmail")));
			message.addRecipients(Message.RecipientType.BCC,
					InternetAddress.parse(properties.getProperty("bccEmail")));
			message.setSubject(properties.getProperty("subject"));
			message.setDisposition(Part.INLINE);
			message.setText(text);
			Transport.send(message);
			logger.info("Email Sent");
		} catch (MessagingException mex) {
			logger.error("Messaging Exception", mex);
			mex.printStackTrace();
		}

	}
	
	public void sendHtmlEmail(String text,Properties properties){
		try {
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", properties.getProperty("host"));
			props.put("mail.smtp.port",  properties.getProperty("port"));
			Session session = Session.getInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(properties.getProperty("uname"), properties.getProperty("pwd"));
						}
					});
    	Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(properties.getProperty("fromEmail")));
		message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(properties.getProperty("toemail")));
		message.addRecipients(Message.RecipientType.CC,
				InternetAddress.parse(properties.getProperty("ccEmail")));
		message.addRecipients(Message.RecipientType.BCC,
				InternetAddress.parse(properties.getProperty("bccEmail")));
		message.setSubject(properties.getProperty("subject"));
		message.setDisposition(Part.INLINE);
		message.setContent(text, "text/html");
        Transport.send(message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
}
