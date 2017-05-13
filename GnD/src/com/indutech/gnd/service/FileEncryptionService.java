package com.indutech.gnd.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.didisoft.pgp.PGPException;
import com.didisoft.pgp.PGPLib;

import org.apache.log4j.Logger;

@Component("encryptionService")
public class FileEncryptionService {
	
	private Properties properties = PropertiesLoader.getInstance().loadProperties();
//	private InputStream inputStream =getClass().getResourceAsStream("/camel-config.properties");

	private static final String PGP_EXTENSION = ".pgp";
	private static final String ZIP_EXTENSION = ".zip";
	Logger logger = Logger.getLogger(FileEncryptionService.class);
	
	@Async
	public  void encrypt(File file, String corefilename, String bankName){
		
		
		  PGPLib pgp = new PGPLib();
		 
		  // is output ASCII or binary
		  boolean asciiArmor = true; 
		 
		  // should integrity check information be added
		  // set to false for compatibility with older versions of PGP such as 6.5.8.
		  boolean withIntegrityCheck = false; 
		 
		  // obtain the streams
		 
		  // Here "INPUT.txt" is just a string to be written in the
		  // OpenPGP packet which contains:
		  // file name string, timestamp, and the actual data bytes
		  try {
//			  properties.load(inputStream);
			  String split[] = corefilename.split("\\.");
			  String aufOutputFile = properties.getProperty("aufOutputFolder");
			  File fileDir  = new File(aufOutputFile+"\\"+corefilename.substring(2, split[0].length())+"\\"+bankName+"\\"+corefilename);
			  fileDir.mkdirs();
//			  InputStream inStream = new FileInputStream(file);
			  InputStream keyStream = new FileInputStream(properties.getProperty("encryptionFilePath"));
//			  File outputFile = new File(fileDir.getPath()+"/"+file.getName()+PGP_EXTENSION);
//			  OutputStream outStream = new FileOutputStream(outputFile);
			  String outputfile = file.getName()+PGP_EXTENSION;
			  

			  
			pgp.encryptFile( file.getAbsolutePath(),
			                    keyStream,
			                    fileDir.getPath()+"/"+file.getName()+PGP_EXTENSION,
			                    asciiArmor,
			                    withIntegrityCheck);
			
			generateZip(outputfile,corefilename, bankName);
		} catch (PGPException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	@Async
	public void  generateZip(String filename, String corefilename, String bankName) {
		logger.info("pgp file is : "+filename);
byte[] buffer = new byte[1024];
    	
    	try{
    		String split[] = corefilename.split("\\.");
    		new File(properties.getProperty("csv.aufEncriptedZIPPath")+"/"+corefilename.substring(2, split[0].length())+"\\"+bankName+"\\"+corefilename).mkdirs();
    		FileOutputStream fos = new FileOutputStream(properties.getProperty("csv.aufEncriptedZIPPath")+"/"+corefilename.substring(2, split[0].length())+"/"+bankName+"/"+corefilename+"/"+filename+ZIP_EXTENSION);
    		ZipOutputStream zos = new ZipOutputStream(fos);
    		ZipEntry ze= new ZipEntry(filename);
    		zos.putNextEntry(ze);
    		FileInputStream in = new FileInputStream(properties.getProperty("csv.aufEncriptedPath")+"/"+corefilename.substring(2, split[0].length())+"/"+bankName+"/"+corefilename+"/"+filename);
    		int len;
    		while ((len = in.read(buffer)) > 0) {
    			zos.write(buffer, 0, len);
    		}

    		in.close();
    		zos.closeEntry();
           
    		//remember close it
    		zos.close();
          
    		System.out.println("Done");

    	}catch(IOException ex){
    	   ex.printStackTrace();
    	}
		
	}

}
