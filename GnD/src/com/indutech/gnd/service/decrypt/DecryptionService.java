package com.indutech.gnd.service.decrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.camel.Exchange;
import org.apache.camel.component.file.GenericFile;
import org.apache.commons.io.FileExistsException;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.bouncycastle.openpgp.PGPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.didisoft.pgp.PGPLib;
import com.indutech.gnd.dto.CoreFiles;
import com.indutech.gnd.service.PropertiesLoader;
import com.indutech.gnd.service.emboss.EmbossService;

@Component("decryptionService")
public class DecryptionService {
	
	Logger logger = Logger.getLogger(DecryptionService.class);
	
	common.Logger log = common.Logger.getLogger(DecryptionService.class);
	
	private Properties properties = PropertiesLoader.getInstance().loadProperties();
	
	SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
	/*cr no 42 starts*/
	public int pgpEntryLog = 0;
	
	@Autowired
	private EmbossService embossService;
	
	public EmbossService getEmbossService() {
		return embossService;
	}


	public void setEmbossService(EmbossService embossService) {
		this.embossService = embossService;
	}

/*cr no 42 ends*/
	//Main Logic
	
/*	public File decrypt(Exchange exchange) {
		// initialize the library instance

		// The decrypt method returns the original name of the file
		// that was encrypted. We can use it afterwards,
		// to rename OUTPUT.txt.
		File outputFile = null;
		try {
			GenericFile<?> inputfile = (GenericFile<?>) exchange.getIn().getBody();
			File inputFileObj =(File)inputfile.getFile();
			int lastIndex = inputfile.getFileName().length() - 4;
			outputFile = new File(inputfile.getFileName().substring(0, lastIndex));
			// create instance of the library
			PGPLib pgp = new PGPLib();
			logger.info("new file location is : "+outputFile.getName());
			// obtain an encrypted data stream
			InputStream encryptedStream = new FileInputStream(inputFileObj);

			InputStream privateKeyStream = new FileInputStream(properties.getProperty("decryptionFilePath"));
			
			String privateKeyPassword = properties.getProperty("pgpKey");
			// specify the destination stream of the decrypted data
			OutputStream decryptedStream = new FileOutputStream(outputFile);

			pgp.decryptStream(encryptedStream, privateKeyStream,
					privateKeyPassword, decryptedStream);
		} catch (PGPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("output file is : "+outputFile.getName());
		return outputFile;
	}
*/	
	public synchronized void decrypt(Exchange exchange) {

		
		File outputFile = null;
		try {
			InputStream privateKeyStream = new FileInputStream(properties.getProperty("decryptionFilePath"));
			GenericFile<?> inputfile = (GenericFile<?>) exchange.getIn().getBody();
			File inputFileObj =(File)inputfile.getFile();
			int lastIndex = inputfile.getFileName().length() - 4;
			if(inputfile.getFileName().substring(0, 3).equals("EMB")) {
				outputFile = new File(properties.getProperty("csv.embossDecryptPath")+"/"+inputfile.getFileName().substring(0, lastIndex));
			}
			else {
				outputFile = new File(properties.getProperty("csv.unzipPath")+"/"+inputfile.getFileName().substring(0, lastIndex));
			}
			// create instance of the library
			PGPLib pgp = new PGPLib();
			// obtain an encrypted data stream
			InputStream encryptedStream = new FileInputStream(inputFileObj);

			
			
			String privateKeyPassword = properties.getProperty("pgpKey");
			// specify the destination stream of the decrypted data
			OutputStream decryptedStream = new FileOutputStream(outputFile);

			pgp.decryptStream(encryptedStream, privateKeyStream,
					privateKeyPassword, decryptedStream);
			logger.info("output file is : "+outputFile.getName());
			encryptedStream.close();
			decryptedStream.close();
		} catch (PGPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
public synchronized void unZip(Exchange exchange) {
		
		byte[] buffer = new byte[1024];
		File newFile = null;
		FileInputStream fis = null;
		String outputFolder = properties.getProperty("csv.inputPath");
	     try{
	    	 GenericFile<?> gfile = (GenericFile<?>) exchange.getIn().getBody();
	    	 File file = (File) gfile.getFile();
	    	 String split[] = file.getName().split("\\.");
//	    	if((split[(split.length)-1]).equalsIgnoreCase("zip") || (split[(split.length)-1]).equalsIgnoreCase("gz")) {
	    	if((split[(split.length)-1]).equalsIgnoreCase("gz")) {
	    		gZUnZip(exchange);
	    		return;
	    	}
	    	else if((split[(split.length)-1]).equalsIgnoreCase("zip"))	 {
		    	//get the zip file content
		    	logger.info("file is in "+split[(split.length)-1]+" format");
		    	fis = new FileInputStream(file);
		    	ZipInputStream zis = 
		    		new ZipInputStream(fis);
		    	//get the zipped file list entry
		    	ZipEntry ze = zis.getNextEntry();
		    		
		    	while(ze!=null){
		    			
		    	   String fileName = ze.getName();
		           newFile = new File(outputFolder + File.separator + fileName);               
		            //create all non exists folders
		            //else you will hit FileNotFoundException for compressed folder
		            new File(newFile.getParent()).mkdirs();
		              
		            FileOutputStream fos = new FileOutputStream(newFile);             
	
		            int len;
		            while ((len = zis.read(buffer)) > 0) {
		       		fos.write(buffer, 0, len);
		            }
		        		
		            fos.close();   
		            ze = zis.getNextEntry();
		            
		    	}
		    	fis.close();
		        zis.closeEntry();
		    	zis.close();
		    	System.out.println("Done");
	    	} else {
	    		FileUtils.moveFileToDirectory(file, new File(outputFolder), false);
	    	}
	    		
	    }catch(IOException ex){
	       ex.printStackTrace(); 
	    }
//	     return newFile;
	}

	public synchronized void gZUnZip(Exchange exchange) {
		
		byte[] buffer = new byte[1024];
		File newFile = null;
		FileInputStream fis = null;
		String outputFolder = properties.getProperty("csv.inputPath");
	     try{
	    	 GenericFile<?> gfile = (GenericFile<?>) exchange.getIn().getBody();
	    	 File file = (File) gfile.getFile();
	    	 
	    	//create output directory is not exists
	//    	File folder = new File(outputFolder);
	//    	if(!folder.exists()){
	//    		folder.mkdir();
	//    	}
	    	String split[] = file.getName().split("\\.");
	    	if((split[(split.length)-1]).equalsIgnoreCase("gz")) {
	    	//get the zip file content
	    	logger.info("file is in "+split[(split.length)-1]+" format");
	    	String outputFileName = file.getName().substring(0,file.getName().length()-((split[(split.length)-1].length())+1));
			logger.info("output file name is : "+outputFileName);
	    	fis = new FileInputStream(file);
	    	GZIPInputStream zis = 
	    		new GZIPInputStream(fis);
	    	//get the zipped file list entry
	    		
	    			
	           newFile = new File(outputFolder + File.separator + outputFileName);               
	            //create all non exists folders
	            //else you will hit FileNotFoundException for compressed folder
	            new File(newFile.getParent()).mkdirs();
	              
	            FileOutputStream fos = new FileOutputStream(newFile);             
	
	            int len;
	            while ((len = zis.read(buffer)) > 0) {
	       		fos.write(buffer, 0, len);
	            }
	        		
	            fos.close();   
	            
	    	fis.close();
	    	zis.close();
	    	System.out.println("Done");
	    	} else {
	    		FileUtils.moveFileToDirectory(file, new File(outputFolder), false);
	    	}
	    		
	    }catch(IOException ex){
	       ex.printStackTrace(); 
	    }
	//     return newFile;
	}
	
	@Transactional
	public synchronized void undoFormats(Exchange exchange) throws InterruptedException {
		File file = null;
		CoreFiles coreFile = new CoreFiles();
		try {
			GenericFile<?> gfile = (GenericFile<?>) exchange.getIn().getBody();
			file = (File) gfile.getFile();
			String fileName = file.getName();
			
			if(fileName.contains("EMB")&& pgpEntryLog == 0 && fileName.length() == 36)
			{
				//System.out.println("EMBOSS FILE");
			//	System.out.println(fileName);
				//EmbossService embossService = new EmbossService();
				boolean isNewFile = getEmbossService().isNewFile(fileName);
			
				 coreFile = getEmbossService().saveFile(fileName,isNewFile);
					if(isNewFile == false)
					{
					//	System.out.println("existing file");
						try{
						FileUtils.moveFile(file, new File(properties.getProperty("csv.embossNotProcessed")+"/"+file.getName()));
						logger.info("Emboss File Already processed..Moved to NOT_PROCESSED successfully");
						return;
						}
						catch(FileExistsException e) // If the file already contains in csv.embossNotProcessed this Exception occurs
						{
							logger.info("Emboss File Already processed..Moved to NOT_PROCESSED successfully");
							return;
						}
						catch(Exception e1)
						{
							e1.printStackTrace();
						}
					}
					pgpEntryLog++;
				}
			/*cr no 42 ends*/
			String split[] = fileName.split("\\.");
			int length = split.length;
			logger.info("file name is : "+fileName);
			while(file != null && length > 1 ) {
				String extension = split[length-1];
				switch(extension.toLowerCase()) {
					case "pgp" :
						file = decrypt(file,coreFile);
						break;
					case "zip" :
						file = unZip(file,coreFile);
						break;
					case "gz" :
						file = gZUnZip(file,coreFile);
						break;
					default :
						break;
				}
				length--;
			
			}
			
//			logger.info("finally output file is "+file.getName()); 
			if(file != null && file.getName().substring(0,3).equalsIgnoreCase("emb")) {	
				logger.info("we are processing embossa file now");
				FileUtils.moveFile(file, new File(properties.getProperty("csv.embossPath")+"/"+file.getName()));
			}
			else if(file != null){
				logger.info("we are processing core file now");
				FileUtils.moveFile(file, new File(properties.getProperty("csv.inputPath")+"/"+file.getName()));
			}
		
		} catch(FileNotFoundException e) {
			logger.info("Seems the file is not yet copied.....we ll try again after 3 seconds");
			Thread.sleep(3000);
			undoFormats(exchange);
		}
		catch(Exception e)
		{
			getEmbossService().deletePgpFileLogService(coreFile);
			logger.info(e);
		}
//		return file;
		finally{
		pgpEntryLog = 0;
		}
	}
	
	
	
	public synchronized File decrypt(File inputFileObj,CoreFiles coreFile) {

		
		File outputFile = null;
		String decryptedFailedLoction = null;
		InputStream encryptedStream = null;
		OutputStream decryptedStream = null;
		try {
			InputStream privateKeyStream = new FileInputStream(properties.getProperty("decryptionFilePath"));
			int lastIndex = inputFileObj.getName().length() - 4;
			outputFile = new File(inputFileObj.getParent()+"/"+inputFileObj.getName().substring(0, lastIndex));
//			if(inputFileObj.getName().substring(0, 3).equals("EMB")) {
//				decryptedLoction = properties.getProperty("dump.embossa")+"/"+sdf.format(new Date())+"/"+outputFile.getName();
//			}
//			else {
//				decryptedLoction = properties.getProperty("dump.core")+"/"+sdf.format(new Date())+"/"+outputFile.getName();
//			}
			// create instance of the library
			PGPLib pgp = new PGPLib();
			// obtain an encrypted data stream
			encryptedStream = new FileInputStream(inputFileObj);

			
			
			String privateKeyPassword = properties.getProperty("pgpKey");
			// specify the destination stream of the decrypted data
			decryptedStream = new FileOutputStream(outputFile);

			pgp.decryptStream(encryptedStream, privateKeyStream,
					privateKeyPassword, decryptedStream);
			logger.info("output file is : "+outputFile.getName());
			encryptedStream.close();
			decryptedStream.close();
			boolean deletion = inputFileObj.delete();
			logger.info("file deletion status is : "+deletion);
//			FileUtils.copyFile(outputFile, new File(decryptedLoction));
		} catch (PGPException e) {
			try {
				getEmbossService().deletePgpFileLogService(coreFile);
				encryptedStream.close();
				decryptedStream.close();
				logger.info("unable to decrypt file "+inputFileObj.getName());
				logger.info("input file is : "+inputFileObj.getAbsolutePath());
				decryptedFailedLoction = properties.getProperty("decryptionFailedPath");
				logger.info("decryption failed path is : "+decryptedFailedLoction );
				Files.move(Paths.get(inputFileObj.getAbsolutePath()), Paths.get(decryptedFailedLoction+'/'+inputFileObj.getName()), StandardCopyOption.REPLACE_EXISTING);
				inputFileObj.delete();
				outputFile.delete();
				logger.info("file moved to not decrypted folder successfully");
				return null;
			} catch(Exception ec) {
				getEmbossService().deletePgpFileLogService(coreFile);
				logger.info("failed to move not decrypted file");
				ec.printStackTrace();
			}
		} catch (IOException e) {
				getEmbossService().deletePgpFileLogService(coreFile);
		//	e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			getEmbossService().deletePgpFileLogService(coreFile);
			e.printStackTrace();
		}
		return outputFile;
	}

	
	
public synchronized File unZip(File file,CoreFiles coreFile) {
		
		byte[] buffer = new byte[1024];
		File newFile = null;
		FileInputStream fis = null;
//		String outputFolder = properties.getProperty("csv.inputPath");
	     try{
	    	 String split[] = file.getName().split("\\.");
		    	logger.info("file is in "+split[(split.length)-1]+" format");
		    	fis = new FileInputStream(file);
		    	ZipInputStream zis = 
		    		new ZipInputStream(fis);
		    	ZipEntry ze = zis.getNextEntry();
		    		
		    	while(ze!=null){
		    			
		    	   String fileName = ze.getName();
		           newFile = new File(file.getParent() + File.separator + fileName);               
		            //create all non exists folders
		            //else you will hit FileNotFoundException for compressed folder
		            new File(newFile.getParent()).mkdirs();
		              
		            FileOutputStream fos = new FileOutputStream(newFile);             
	
		            int len;
		            while ((len = zis.read(buffer)) > 0) {
		       		fos.write(buffer, 0, len);
		            }
		        		
		            fos.close();   
		            ze = zis.getNextEntry();
		            
		    	}
		    	fis.close();
		        zis.closeEntry();
		    	zis.close();
		    	file.delete();
		    	System.out.println("Done");
	    		
	    }catch(Exception ex){
	    	getEmbossService().deletePgpFileLogService(coreFile);
	    	logger.error(ex);
	       ex.printStackTrace(); 
	    }
	     return newFile;
	}



public synchronized File gZUnZip(File file,CoreFiles coreFile) {
	
	byte[] buffer = new byte[1024];
	File newFile = null;
	FileInputStream fis = null;
     try{
    	String split[] = file.getName().split("\\.");
    	//get the zip file content
    	logger.info("file is in "+split[(split.length)-1]+" format");
    	String outputFileName = file.getName().substring(0,file.getName().length()-((split[(split.length)-1].length())+1));
		logger.info("output file name is : "+outputFileName);
    	fis = new FileInputStream(file);
    	GZIPInputStream zis = new GZIPInputStream(fis);
    	//get the zipped file list entry
    		
    			
           newFile = new File(file.getParent() + File.separator + outputFileName);               
            //create all non exists folders
            //else you will hit FileNotFoundException for compressed folder
            new File(newFile.getParent()).mkdirs();
              
            FileOutputStream fos = new FileOutputStream(newFile);             

            int len;
            while ((len = zis.read(buffer)) > 0) {
            	fos.write(buffer, 0, len);
            }
        		
            fos.close();   
            
	    	fis.close();
	    	zis.close();
	    	file.delete();
    		
	    }catch(Exception ex){
	    	getEmbossService().deletePgpFileLogService(coreFile);
	    	logger.error(ex);
	       ex.printStackTrace(); 
	    }
	    return newFile;
	}

}
