package com.donmateo.application;

import java.util.List;
import java.util.Date;
import java.util.Iterator;

import com.donmateo.tables.HashTag;
import com.donmateo.tables.HashTag_Info;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

public class ManageHashTag_Info {
    private static SessionFactory factory;

    public ManageHashTag_Info(SessionFactory factory){
        this.factory = factory;
    }

    public Integer addHashTag_Info(Integer id_info, Integer id_hashtag){
        Session session = factory.openSession();
        Transaction tx = null;
        Integer ID = null;
        try{
            tx = session.beginTransaction();
            HashTag_Info ht_info = new HashTag_Info(id_hashtag, id_info);
            ID = (Integer) session.save(ht_info);
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
    public void deleteHashTag_Info(Integer ID){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            HashTag_Info ht_info=
                    (HashTag_Info) session.get(HashTag_Info.class, ID);
            session.delete(ht_info);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }


}


    //wybranie wszystkich tagów dla danego info
//    SELECT  hashtag.text as tekst
//        FROM info
//        left join hashtag_info on hashtag_info.id_info = info.id
//        left join hashtag on hashtag.id = hashtag_info.id_ht
//        WHERE info.id = 8
//        Group by tekst

//    SELECT info.id as info_id, info.description as info_desc, count(*) as how_many_hash_tags
//        FROM info
//        left join hashtag_info on hashtag_info.id_info = info.id
//        left join hashtag on hashtag.id = hashtag_info.id_ht
//        WHERE hashtag.id = 13 or hashtag.id = 14 or hashtag.id = 15
//        Group by info_desc
//        order by how_many_hash_tags desc