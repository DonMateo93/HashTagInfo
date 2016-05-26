package com.donmateo.application;

import com.donmateo.tables.HashTag;
import com.donmateo.tables.Info;
import org.hibernate.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ManageHashTag {
    private static SessionFactory factory;

    public ManageHashTag(SessionFactory factory){
        this.factory = factory;
    }

    public Integer addHashTag(String text){
        Session session = factory.openSession();
        Transaction tx = null;
        Integer ID = null;
        try{
            tx = session.beginTransaction();
            HashTag ht = new HashTag(text);
            ID = (Integer) session.save(ht);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return ID;
    }

    /* Method to UPDATE salary for an employee */
    public void updateHashTag(Integer ID, String text ){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            HashTag ht = (HashTag) session.get(HashTag.class, ID);
            ht.setText( text );
            session.update(ht);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    /* Method to DELETE an employee from the records */
    public void deleteHashTag(Integer ID){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            HashTag ht=
                    (HashTag) session.get(HashTag.class, ID);
            session.delete(ht);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    public List<String> parseHashTagSequence(String ht){
        ht.toLowerCase();
        List<String> ht_list = new ArrayList<String>(Arrays.asList(ht.split("[,\\s\\-:\\?\\.#]")));
        ht_list.removeAll(Arrays.asList("", null));
        return ht_list;
    }

    public boolean isHashTagInDB(String text){

        boolean answer = false;
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            String sql = "SELECT * FROM hashtag where text = '" + text + "'";
            SQLQuery query = session.createSQLQuery(sql);
            query.addEntity(HashTag.class);
            List ht_list = query.list();
            if ( ht_list.size() > 0)
                answer = true;
//            for (Iterator iterator =
//                 ht_list.iterator(); iterator.hasNext();){
//                HashTag employee = (HashTag) iterator.next();
//                System.out.print("First Name: " + employee.getFirstName());
//                System.out.print("  Last Name: " + employee.getLastName());
//                System.out.println("  Salary: " + employee.getSalary());
//            }
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return answer;
    }

    public HashTag getHashTagFromText(String text){

        HashTag ht = null;

        if ( text.length() > 0 ) {
            Session session = factory.openSession();
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String sql = "SELECT * FROM hashtag where text = '" + text + "'";
                SQLQuery query = session.createSQLQuery(sql);
                query.addEntity(HashTag.class);
                List ht_list = query.list();
                if (ht_list.size() > 0) {
                    ht = (HashTag) ht_list.get(0);
                }
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
        }

        return ht;

    }

    public List<HashTag> getHTFromTXTSequence(String ht_sequence) {

        List<HashTag> ht_list = new ArrayList<HashTag>();
        List<String> ht_text_list = parseHashTagSequence(ht_sequence);

        if ( ht_text_list.size() > 0 ) {

            Session session = factory.openSession();
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String sql = "SELECT * FROM hashtag WHERE";

                for (int i = 0; i < ht_text_list.size(); i++) {
                    if (i != 0)
                        sql += " or";
                    sql += " hashtag.text = " + ht_text_list.get(i);
                }

                SQLQuery query = session.createSQLQuery(sql);
                query.addEntity(HashTag.class);
                List ht_list_obj = query.list();

                for (int i = 0; i < ht_list_obj.size(); i++) {
                    ht_list.add((HashTag) ht_list.get(i));
                }
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
        }

        return ht_list;
    }

    public List<HashTag> getHashTagsConnectedWithInfo(Info info){
        List<HashTag> ht_list = new ArrayList<HashTag>();

        if ( info != null ) {
            Session session = factory.openSession();
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String sql = "SELECT hashtag.id as id, hashtag.text as text " +
                        "FROM hashtag " +
                        "LEFT JOIN hashtag_info " +
                        "ON hashtag.id = hashtag_info.id_ht " +
                        "where hashtag_info.id_info = " + info.getId() + " " +
                        "GROUP BY id";
                SQLQuery query = session.createSQLQuery(sql);
                query.addEntity(HashTag.class);
                List ht_tmp_list = query.list();
                for( int i = 0; i < ht_tmp_list.size(); i++) {
                    ht_list.add((HashTag) ht_tmp_list.get(i));
                }
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
        }

        return ht_list;

    }
}
