package com.Kapture.Employee.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;
@Component
public class QueryUtil {
    @Autowired
    private SessionFactory sessionFactory;

    public <T> List<T> runQueryHelper(String queryString, Map<String, Object> parametersToSet, Class<T> className, Integer limit, Integer offset) {
        List<T> list = null;
        try (Session session = sessionFactory.openSession()) {
            TypedQuery<T> query = session.createNativeQuery(queryString, className);
            if (parametersToSet != null && !parametersToSet.isEmpty()) {
                parametersToSet.forEach(query::setParameter);
            }

            if (limit != null && offset != null) {
                query.setMaxResults(limit);
                query.setFirstResult(offset);
            }
            list = query.getResultList();
            if (list != null && list.size() == 0) {
                list = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void SaveQueryHelper(String queryString, Map<String, Object> parametersToSet) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction(); // Begin a transaction before executing the insert query
            TypedQuery<?> query = session.createNativeQuery(queryString);
            if (parametersToSet != null && !parametersToSet.isEmpty()) {
                parametersToSet.forEach(query::setParameter);
            }
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public void updateQueryHelper(Map<String, Object> parameterToPass, String queryString){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            NativeQuery<?> query = session.createNativeQuery(queryString);
            if(parameterToPass != null && !parameterToPass.isEmpty()){
                parameterToPass.forEach(query::setParameter);
            }
            query.executeUpdate();
            session.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
