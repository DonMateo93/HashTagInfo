package com.donmateo.hashtagsap;


import com.donmateo.application.*;
import com.donmateo.tables.HashTag;
import com.donmateo.tables.HashTag_HashTag;
import com.donmateo.tables.HashTag_Info;
import com.donmateo.tables.Info;
import com.donmateo.util.HibernateUtil;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class HashTagSapMain {

    private static SessionFactory factory;
//    private static ApplicationInterface app_intf;
private static ApplicationInterface2 app_intf;
    public static void main(String[] args) throws IOException {

        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

//        app_intf = new ApplicationInterface(factory);
        app_intf = new ApplicationInterface2(factory);
        UtilsClass.clearCL();
        System.out.println("WELCOME TO HASHTAG SAP");
//        app_intf.userPolling();
        app_intf.begin();
        System.out.println("GOOD BYE!\n Author: Mateusz Osypiński");

        return;

    }

}