package com.homework.task_2;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();
    private static final Logger logger = LoggerFactory.getLogger(HibernateUtil.class);

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
            SessionFactory factory = configuration.buildSessionFactory();
            logger.info("Hibernate SessionFactory initialized successfully");
            return factory;
        } catch (HibernateException ex) {
            logger.error("Initial SessionFactory creation failed");
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }

    public static void shutdown(){
        try {
            if(sessionFactory != null && !sessionFactory.isClosed()){
                sessionFactory.close();
                logger.info("SessionFactory closed successfully");
            }
        } catch (Exception ex) {
            logger.error("Error when closing the SessionFactory", ex);
        }
    }
}
