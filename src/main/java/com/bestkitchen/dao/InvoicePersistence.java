package com.bestkitchen.dao;

import org.hibernate.Session;

public class InvoicePersistence {

    public void save(Object object){
        Session session = SessionFactoryManager.buildSessionFactory().openSession();
        session.beginTransaction();
        session.save(object);
        session.getTransaction().commit();
        session.close();
    }
}
