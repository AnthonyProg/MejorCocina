package com.bestkitchen.dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

class SessionFactoryManager {

    static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();
            return configuration.buildSessionFactory();
        }
        catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
}