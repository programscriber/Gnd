package com.indutech.gnd.service;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.indutech.gnd.jasper.reports.SbiReportGeneration;


@Component("messageProducerService")
public class MessageProducerService {
	
	Logger logger = Logger.getLogger(MessageProducerService.class);
	
	common.Logger log = common.Logger.getLogger(MessageProducerService.class);

	
	public  void produceMessage(String message,String queueName, String brokerURL){
		try {
            // Create a ConnectionFactory
            ConnectionFactory connectionFactory=new ActiveMQConnectionFactory("admin", "admin", brokerURL);
            // Create a Connection
            Connection connection = connectionFactory.createConnection();
            connection.start();
            // Create a Session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // Create the destination
            Destination destination = session.createQueue(queueName);
            // Create a MessageProducer from the Session to the Queue
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            // Create a messages
            TextMessage txtmessage = session.createTextMessage(message);
            producer.send(txtmessage);
            session.close();
            connection.close();
            logger.info("Message sent");
            
        }
        catch (Exception e) {
        	logger.error(e);
            e.printStackTrace();
        }
	}
}
