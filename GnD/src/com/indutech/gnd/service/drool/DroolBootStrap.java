package com.indutech.gnd.service.drool;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.drools.KnowledgeBase;
import org.drools.runtime.StatefulKnowledgeSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.indutech.gnd.bo.drool.DroolRecordBO;
import com.indutech.gnd.service.PropertiesLoader;

@Component("droolBootStrapService")
public class DroolBootStrap {
	
	Logger logger = Logger.getLogger(DroolBootStrap.class);
	
	private Properties properties = PropertiesLoader.getInstance().loadProperties();

	String droolsFilePath=null;
	String droolsCNFFilePath=null;
	String dbDroolsFilePath=null;
	
	StatefulKnowledgeSession ksession = null;
	KnowledgeBase kbase = null;
//	KnowledgeRuntimeLogger logger = null;
	
	StatefulKnowledgeSession ksessionCNF=null;
	KnowledgeBase CNFkbase = null;
//	KnowledgeRuntimeLogger loggerCNF=null;
	
	StatefulKnowledgeSession ksessionDB=null;
	KnowledgeBase dBKbase = null;
//	KnowledgeRuntimeLogger loggerDB=null;
	
	
	
	@Autowired
	private DroolsKnowledgeBase droolsKnowledgeBase;
	
	public void fire() {
		
		try {
			logger.info("drool files loading......");
			droolsFilePath= properties.getProperty("droolsFilePath");
			kbase = droolsKnowledgeBase.readKnowledgeBase(droolsFilePath);
			this.ksession = kbase.newStatefulKnowledgeSession();
//			this.logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "test");
			
			
			droolsCNFFilePath = properties.getProperty("droolsCNFFilePath");
			CNFkbase = droolsKnowledgeBase.readKnowledgeBase(droolsCNFFilePath);
			ksessionCNF=CNFkbase.newStatefulKnowledgeSession();
//			loggerCNF=KnowledgeRuntimeLoggerFactory.newFileLogger(ksessionCNF, "test");
			
			dbDroolsFilePath = properties.getProperty("dbDroolsFilePath");
			dBKbase=droolsKnowledgeBase.readKnowledgeBase(dbDroolsFilePath);
			ksessionDB=dBKbase.newStatefulKnowledgeSession();
//			loggerDB=KnowledgeRuntimeLoggerFactory.newFileLogger(ksessionDB, "test");
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
	}

	public DroolRecordBO bootStrap(DroolRecordBO record){
		 try {
			 
	            ksession.insert(record);

					ksession.fireAllRules();
				
//	            logger.close();
				
	        } catch (Throwable t) {
	            t.printStackTrace();
	            logger.error(t);
	        }
		 return record;
	}
	
	public DroolRecordBO bootStrapCNF(DroolRecordBO record) {
		
		try {

			ksessionCNF.insert(record);

			ksessionCNF.fireAllRules();
				
//	            logger.close();
				
	        } catch (Throwable t) {
	            t.printStackTrace();
	            logger.error(t);
	        }
		 return record;
		
	}
	
	public DroolRecordBO bootStrapDBDrools(DroolRecordBO record){
		 try {

			 ksessionDB.insert(record);

			 ksessionDB.fireAllRules();
				
//	            logger.close();
				
	        } catch (Throwable t) {
	            t.printStackTrace();
	            logger.error(t);
	            //logger
	        }
		 return record;
	}
	
	
	
	
	
	
}



