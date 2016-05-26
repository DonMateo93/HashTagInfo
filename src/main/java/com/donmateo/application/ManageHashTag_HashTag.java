package com.donmateo.application;

import java.util.List;
import java.util.Date;
import java.util.Iterator;

import com.donmateo.tables.HashTag_HashTag;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ManageHashTag_HashTag {
    private static SessionFactory factory;

    public ManageHashTag_HashTag(SessionFactory factory){
        this.factory = factory;
    }

    public Integer addHashTag_HashTag(Integer id1, Integer id2){
        Session session = factory.openSession();
        Transaction tx = null;
        Integer ID = null;
        try{
            // TODO: 2016-04-30 dodać sprawdzenie czy jest hash tag o takim id ;
            tx = session.beginTransaction();
            HashTag_HashTag ht_ht = new HashTag_HashTag(id1, id2);
            ID = (Integer) session.save(ht_ht);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return ID;
    }

    /* Method to DELETE an employee from the records */
    public void deleteHashTag_HashTag(Integer ID){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            HashTag_HashTag ht_ht=
                    (HashTag_HashTag) session.get(HashTag_HashTag.class, ID);
            session.delete(ht_ht);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
}
