package com.bestkitchen.dao;

import com.bestkitchen.entity.*;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LoadDataManager {


    public List<WaiterResult> loadWaiterResult(){
        List<WaiterResult>  waiterResults = new ArrayList<>();
        try (Session session = SessionFactoryManager.buildSessionFactory().openSession()) {
            Query query = session.getNamedQuery("waiterResult");
            query.setParameter("date", new SimpleDateFormat("MM").format(Calendar.getInstance().getTime()));
            List results = query.getResultList();
            if (results != null && !results.isEmpty()) {
                for (Object result : results) {
                    String name = (String) ( (Object[]) result)[0];
                    String lastName = (String) ( (Object[]) result)[1];
                    Double invoicedAmount = (Double) ( (Object[]) result)[2];
                    WaiterResult waiterResult = new WaiterResult();
                    waiterResult.setName(name);
                    waiterResult.setLastName(lastName);
                    waiterResult.setInvoicedAmount(invoicedAmount);
                    waiterResults.add(waiterResult);
                }
            }
        } catch (Exception ignored) {

        }
        return waiterResults;
    }

    public List<ClientResult> loadClientResult(){
        List<ClientResult>  clientResults = new ArrayList<>();
        try (Session session = SessionFactoryManager.buildSessionFactory().openSession()) {
            Query query = session.getNamedQuery("clientsHQL");
            query.setParameter("minAmount", 100000D);
            List results = query.getResultList();
            if (results != null && !results.isEmpty()) {
                for (Object result : results) {
                    String name = (String) ( (Object[]) result)[0];
                    String lastName = (String) ( (Object[]) result)[1];
                    Double invoicedAmount = (Double) ( (Object[]) result)[2];
                    ClientResult clientResult = new ClientResult();
                    clientResult.setName(name);
                    clientResult.setLastName(lastName);
                    clientResult.setTotal(invoicedAmount);
                    clientResults.add(clientResult);
                }
            }
        } catch (Exception ignored) {

        }
        return clientResults;
    }

    public  <T> List<T> loadAllData(Class<T> type) {
        try (Session session = SessionFactoryManager.buildSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> criteria = builder.createQuery(type);
            criteria.from(type);
            return session.createQuery(criteria).getResultList();
        } catch (Exception ignored) {

        }
        return new ArrayList<>();
    }

}
