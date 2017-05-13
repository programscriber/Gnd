package com.indutech.gnd.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;


public class MessageProducerServiceTest {
	
	
	Logger logger = Logger.getLogger(MessageProducerServiceTest.class);

	public static void main(String[] args) {
		produceMessage("<CardSearchQuery><AccountRange>	<From></From><To></To> </AccountRange>"
				+ "<AWB></AWB><Mobile></Mobile><RSN></RSN><ProcessingDate><From></From><To></To><ProcessingDate>"
				+ "<ApplicationNo></ApplicationNo><Bank></Bank><Branch></Branch>"
				+ "<ProductCode></ProductCode><RecordStatus></RecordStatus>"
				+ "</CardSearchQuery>", "CARD_SEARCH_QUERY_QUEUE");
	}
	
	public static void produceMessage(String message,String queueName){
		try {
            // Create a ConnectionFactory
            ConnectionFactory connectionFactory=new ActiveMQConnectionFactory("admin", "admin", ActiveMQConnection.DEFAULT_BROKER_URL);
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
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
	}
}
