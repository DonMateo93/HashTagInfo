package com.donmateo.application;

import java.util.*;

import com.donmateo.tables.HashTag;
import com.donmateo.tables.Info;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

public class ManageInfo{
    private static SessionFactory factory;

    public ManageInfo(SessionFactory factory){
        this.factory = factory;
    }

    public Integer addInfo(String text, String description){
        Session session = factory.openSession();
        Transaction tx = null;
        Integer ID = null;
        try{
            tx = session.beginTransaction();
            Info info = new Info(text,description);
            ID = (Integer) session.save(info);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return ID;
    }

    public Integer addInfo(String text, String description, String clipboard){
        Session session = factory.openSession();
        Transaction tx = null;
        Integer ID = null;
        try{
            tx = session.beginTransaction();
            Info info = new Info(text,description, clipboard);
            ID = (Integer) session.save(info);
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
    public void updateInfo(Integer ID, String text ){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Info info = (Info) session.get(Info.class, ID);
            info.setText( text );
            session.update(info);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    public void updateInfo(Info info){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.update(info);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    /* Method to DELETE an employee from the records */
    public void deleteInfo(Integer ID){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Info info = (Info) session.get(Info.class, ID);
            session.delete(info);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    public List<Integer> getInfoIdsFromTag(List<Integer> ht_id_list){

        List<Integer> info_id_list = null;
        if(ht_id_list != null)
            if(ht_id_list.size() > 0) {
                Session session = factory.openSession();
                Transaction tx = null;
                try {
                    tx = session.beginTransaction();
                    String sql = "SELECT info.id as info_id, COUNT(*) as how_many_hash_tags " +
                            "FROM info " +
                            "LEFT JOIN hashtag_info on hashtag_info.id_info = info.id " +
                            "LEFT JOIN hashtag on hashtag.id = hashtag_info.id_ht " +
                            "WHERE";
                    Integer ht_id;
                    for (int i = 0; i < ht_id_list.size(); i++) {
                        ht_id = ht_id_list.get(i);
                        if (i != 0)
                            sql += " or";
                        sql += " hashtag.id = " + Integer.toString(ht_id);
                    }

                    sql +=  " GROUP BY info_desc" +
                            " ORDER BY how_many_hash_tags desc";

                    SQLQuery query = session.createSQLQuery(sql);
                    query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                    List data = query.list();

                    for(Object object : data)
                    {
                        Map row = (Map)object;
                        info_id_list.add((Integer)row.get("info_id"));
                    }
                    tx.commit();
                } catch (HibernateException e) {
                    if (tx != null) tx.rollback();
                    e.printStackTrace();
                } finally {
                    session.close();
                }
            }
        return info_id_list;
    }

    public List<Info> getInfosFromId(List<Integer> info_id_list){

        List<Info> info_list = null;
        if( info_id_list != null)
            if ( info_id_list.size() > 0) {

                Session session = factory.openSession();
                Transaction tx = null;
                try {
                    tx = session.beginTransaction();
                    String sql = "SELECT * FROM info " +
                            "WHERE";
                    Integer info_id;
                    for (int i = 0; i < info_id_list.size(); i++) {
                        info_id = info_id_list.get(i);
                        if (i != 0)
                            sql += " or";
                        sql += " info.id = " + Integer.toString(info_id);
                    }

                    SQLQuery query = session.createSQLQuery(sql);
                    query.addEntity(Info.class);
                    List infos = query.list();

                    for (Iterator iterator =
                         infos.iterator(); iterator.hasNext();){
                        Info info = (Info) iterator.next();
                        info_list.add(info);
                    }

                    tx.commit();
                } catch (HibernateException e) {
                    if (tx != null) tx.rollback();
                    e.printStackTrace();
                } finally {
                    session.close();
                }
            }

        return info_list;
    }

    public List<Info> getInfoIdsFromTagText(List<String> ht_text_list){

        List<Info> info_list = new ArrayList<Info>();
        List<Integer> info_id_list = new ArrayList<Integer>();
        if(ht_text_list != null)
            if(ht_text_list.size() > 0) {
                Session session = factory.openSession();
                Transaction tx = null;
                try {
                    tx = session.beginTransaction(); //rozwiązanie problemu aliasów w Hibernate
                    String sql = "select info.id as id, info.information as information, info.description as description, info.clipboard as clipboard, COUNT(info.id) as how_many_hash_tags " +
                            "from info " +
                            "left join hashtag_info on info.id = hashtag_info.id_info " +
                            "left join hashtag on hashtag.id = hashtag_info.id_ht " +
                            "WHERE";
                    for (int i = 0; i < ht_text_list.size(); i++) {
                            if (i != 0)
                            sql += " or";
                            sql += " hashtag.text = '" + ht_text_list.get(i) + "'";
                            }

                            sql +=  " GROUP BY id" +
                            " ORDER BY how_many_hash_tags desc";

                    System.out.println("1");
                    SQLQuery query = session.createSQLQuery(sql);
                    System.out.println("2");
                    query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
                    System.out.println("3");
                    List data = query.list();
                    System.out.println(data.size());
                    System.out.println("4");
                    for(Object object : data)
                    {

                        System.out.println("5");
                        Map row = (Map)object;
                        Info info = new Info((String)row.get("information"), (String)row.get("description"), (Integer)row.get("id"), (String)row.get("clipboard"));
                        info_list.add(info);
                        info_id_list.add((Integer)row.get("id"));
                    }

                    tx.commit();
                } catch (HibernateException e) {
                    if (tx != null) tx.rollback();
                    e.printStackTrace();
                } finally {
                    session.close();
                }
            }
        System.out.println("Przeszedłem");
        return info_list;
    }
}

//    SELECT info.id as info_id, info.description as info_desc, count(*) as how_many_hash_tags
//        FROM info
//        left join hashtag_info on hashtag_info.id_info = info.id
//        left join hashtag on hashtag.id = hashtag_info.id_ht
//        WHERE hashtag.id = 13 or hashtag.id = 14 or hashtag.id = 15
//        Group by info_desc
//        order by how_many_hash_tags desc


//ZPAR_NAME
//        ZPAR_VALUE

//działające trzeba wstawić na górze
//    select info.id as info_id, COUNT(*) as how_many_hash_tags
//        from info
//        left join hashtag_info on hashtag_info.id_info = info.id
//        left join hashtag on hashtag.id = hashtag_info.id_ht
//        where hashtag.text = 'dupa'
//        or hashtag.text = 'kupa'
//        or hashtag.text = 'moc'
//        group by info_id
//        order by how_many_hash_tags DESC


//tx = session.beginTransaction();
//String sql = "select info.id as info_id, COUNT(*) as how_many_hash_tags " +
//        "from info " +
//        "left join hashtag_info on info.id = hashtag_info.id_info " +
//        "left join hashtag on hashtag.id = hashtag_info.id_ht " +
//        "WHERE";
//for (int i = 0; i < ht_text_list.size(); i++) {
//        if (i != 0)
//        sql += " or";
//        sql += " hashtag.text = '" + ht_text_list.get(i) + "'";
//        }
//
//        sql +=  " GROUP BY info_id" +
//        " ORDER BY how_many_hash_tags desc";