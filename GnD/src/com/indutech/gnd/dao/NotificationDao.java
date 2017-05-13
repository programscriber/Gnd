package com.indutech.gnd.dao;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.indutech.gnd.dto.Notification;

@Repository("notificationDao") 
public class NotificationDao {
	
	
	Logger logger = Logger.getLogger(NotificationDao.class);
	common.Logger log = common.Logger.getLogger(NotificationDao.class);
	

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public boolean updateDailyNotification(Long decison, String emailToUpdate) {
		boolean flag=false;
		final long REJECTED = 1;
		final long HOLD = 2;
		final long APPROVED = 3;
		
		final long WEEK_REJECT=4;
		final long WEEK_HOLD=5;
		final long WEEK_APPROVED=6;
		
		final long India_post_AWB = 7;
		final long Blue_dart_AWB = 8;
		final long missing_Branch = 9;
		List<Notification> notifcritPrecon = null;

		notifcritPrecon = getSessionFactory().getCurrentSession()
				.createCriteria(Notification.class).list();
		if (notifcritPrecon.isEmpty()) {
			for (int i = 1; i <= 9; i++) {
				Notification notadd = new Notification();
				notadd.setDate_update(new Date());
				if (i == REJECTED) {
					notadd.setNotification_type(REJECTED);
				}
				if (i == HOLD) {
					notadd.setNotification_type(HOLD);
				}
				if (i == APPROVED) {
					notadd.setNotification_type(APPROVED);
				}
				if(i==WEEK_REJECT){
					notadd.setNotification_type(WEEK_REJECT);
				}
				if(i==WEEK_HOLD){
					notadd.setNotification_type(WEEK_HOLD);
					
				}
				if(i==WEEK_APPROVED){
					notadd.setNotification_type(WEEK_APPROVED);
				}
				if(i==India_post_AWB){
					notadd.setNotification_type(India_post_AWB);
				}
				if(i==Blue_dart_AWB){
					notadd.setNotification_type(Blue_dart_AWB);
				}
				if(i==missing_Branch){
					notadd.setNotification_type(missing_Branch);
				}
				getSessionFactory().getCurrentSession().setFlushMode(
						FlushMode.AUTO);
				getSessionFactory().getCurrentSession().save(notadd);
				getSessionFactory().getCurrentSession().flush();
				getSessionFactory().getCurrentSession().refresh(notadd);
			}
		}
		List<Notification> notifcrit = getSessionFactory().getCurrentSession()
				.createCriteria(Notification.class)
				.add(Restrictions.eq("notification_type", decison)).list();

		Query query = getSessionFactory()
				.getCurrentSession()
				.createQuery(
						"update Notification set date_update = :date,enable=:ena,email=:emai where notification_type=:not");
		query.setParameter("date", new Date());
		query.setParameter("ena", decison);
		query.setParameter("emai", emailToUpdate);
		query.setParameter("not", notifcrit.get(0).getNotification_type());
		logger.info("update quert");
		int result = query.executeUpdate();
		if(result==1){
			flag=true;
		}
		return flag;
	}

	public boolean updateExcepNotification(Long thresh,Long decison, String emailToUpdate) {
		boolean flag=false;
		final long REJECTED = 1;
		final long HOLD = 2;
		final long APPROVED = 3;
		
		final long WEEK_REJECT=4;
		final long WEEK_HOLD=5;
		final long WEEK_APPROVED=6;
		
		final long India_post_AWB = 7;
		final long Blue_dart_AWB = 8;
		final long missing_Branch = 9;
		List<Notification> notifcritPrecon = null;

		notifcritPrecon = getSessionFactory().getCurrentSession()
				.createCriteria(Notification.class).list();
		if (notifcritPrecon.isEmpty()) {
			for (int i = 1; i <= 9; i++) {
				Notification notadd = new Notification();
				notadd.setDate_update(new Date());
				if (i == REJECTED) {
					notadd.setNotification_type(REJECTED);
				}
				if (i == HOLD) {
					notadd.setNotification_type(HOLD);
				}
				if (i == APPROVED) {
					notadd.setNotification_type(APPROVED);
				}
				if(i==WEEK_REJECT){
					notadd.setNotification_type(WEEK_REJECT);
				}
				if(i==WEEK_HOLD){
					notadd.setNotification_type(WEEK_HOLD);
					
				}
				if(i==WEEK_APPROVED){
					notadd.setNotification_type(WEEK_APPROVED);
				}
				if(i==India_post_AWB){
					notadd.setNotification_type(India_post_AWB);
				}
				if(i==Blue_dart_AWB){
					notadd.setNotification_type(Blue_dart_AWB);
				}
				if(i==missing_Branch){
					notadd.setNotification_type(missing_Branch);
				}
				getSessionFactory().getCurrentSession().setFlushMode(
						FlushMode.AUTO);
				getSessionFactory().getCurrentSession().save(notadd);
				getSessionFactory().getCurrentSession().flush();
				getSessionFactory().getCurrentSession().refresh(notadd);
			}
		}
		List<Notification> notifcrit = getSessionFactory().getCurrentSession()
				.createCriteria(Notification.class)
				.add(Restrictions.eq("notification_type", decison)).list();

		Query query = getSessionFactory()
				.getCurrentSession()
				.createQuery(
						"update Notification set date_update = :date,enable=:ena,email=:emai,threshold=:thre where notification_type=:not");
		query.setParameter("date", new Date());
		query.setParameter("ena", decison);
		query.setParameter("emai", emailToUpdate);
		query.setParameter("not", notifcrit.get(0).getNotification_type());
		query.setParameter("thre", thresh);
		int result = query.executeUpdate();
		if(result==1){
			flag=true;
		}
		return flag;
	}
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public List<Notification> getDailyEmail() {
		List<Notification> list = getSessionFactory().getCurrentSession().createCriteria(Notification.class).add(Restrictions.le("notification_type", (long)3)).list();
		return list;
	}

	public List<Notification> getWeeklyEmail() {
		List<Notification> list = getSessionFactory().getCurrentSession().createCriteria(Notification.class).add(Restrictions.between("notification_type", (long)4,(long)6)).list();
		return list;
	}
	
	public List<Notification> getExceptionalEmail() {
		List<Notification> list = getSessionFactory().getCurrentSession().createCriteria(Notification.class).add(Restrictions.between("notification_type", (long)7,(long)9)).list();
		return list;
	}
}