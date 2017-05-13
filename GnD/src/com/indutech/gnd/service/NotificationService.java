package com.indutech.gnd.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.indutech.gnd.bo.NotificationsBO;
import com.indutech.gnd.dao.NotificationDao;
import com.indutech.gnd.dto.Notification;

@Component("notificationService")
public class NotificationService {
	@Autowired
	private NotificationDao notificationDao;

	@Transactional
	public boolean dailyNotificationSerReject(Long reject, String emailreject) {
		boolean result = getNotificationDao().updateDailyNotification(reject,
				emailreject);

		return result;
	}

	@Transactional
	public boolean dailyNotificationSerApproved(Long approved,
			String emailapproved) {
		boolean result = getNotificationDao().updateDailyNotification(approved,
				emailapproved);
		return result;
	}

	@Transactional
	public boolean dailyNotificationSerHold(Long hold, String emailhold) {
		boolean result = getNotificationDao().updateDailyNotification(hold,
				emailhold);
		return result;
	}
	@Transactional
	public boolean excepNotificationSerReject(Long label,Long reject, String emailreject) {
		
		boolean result = getNotificationDao().updateExcepNotification(label,
				reject,emailreject);
		return result;

	}
	@Transactional
	public boolean excepNotificationSerHold(Long threshold,Long approved,
			String emailapproved) {
		boolean result = getNotificationDao().updateExcepNotification(threshold,
				approved,emailapproved);
		return result;

	}
	@Transactional
	public boolean excepNotificationSerApproved(Long approved,
			String emailapproved) {
		boolean result = getNotificationDao().updateDailyNotification(
				approved,emailapproved);
		return result;

	}
	public NotificationDao getNotificationDao() {
		return notificationDao;
	}

	public void setNotificationDao(NotificationDao notificationDao) {
		this.notificationDao = notificationDao;
	}

	@Transactional
	public List<String> getDailyEmails() { 
		 
		List<Notification> list = getNotificationDao().getDailyEmail();
		List<String> emailList = new ArrayList<String>();
		if(list.size() > 0) {
			Iterator itr = list.iterator();
			while(itr.hasNext()) {
				Notification notification = (Notification) itr.next();
				String email = notification.getEmail();
				emailList.add(email);
				
			}
		}
		return emailList;
	}
	
	@Transactional
	public List<String> getWeeklyEmails() {
		List<Notification> list = getNotificationDao().getWeeklyEmail();
		List<String> emailList = new ArrayList<String>();
		if(list.size() > 0) {
			Iterator itr = list.iterator();
			while(itr.hasNext()) {
				Notification notification = (Notification) itr.next();
				String email = notification.getEmail();
				emailList.add(email);
				
			}
		}
		return emailList;
	}
	
	@Transactional
	public List<String> getExceptionalEmails() {
		List<Notification> list = getNotificationDao().getExceptionalEmail();
		List<String> emailList = new ArrayList<String>();
		if(list.size() > 0) {
			Iterator itr = list.iterator();
			while(itr.hasNext()) {
				Notification notification = (Notification) itr.next();
				String email = notification.getEmail();
				emailList.add(email);
				
			}
		}
		return emailList;
	}

	private NotificationsBO buildNotificationBO(Notification notification) {

		NotificationsBO notificationBO = new NotificationsBO();
		notificationBO.setDate_update(notification.getDate_update());
		notificationBO.setEmail(notification.getEmail());
		notificationBO.setEnable(notification.getEnable());
		notificationBO.setId(notification.getId());
		notificationBO.setThreshold(notification.getThreshold());
		notificationBO.setNotification_type(notification.getNotification_type());
		
		
		return notificationBO;
	}

	
}
