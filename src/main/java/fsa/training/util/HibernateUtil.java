package fsa.training.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public final class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        Configuration configuration = new Configuration();
        configuration.configure(); // load hibernate.cfg.xml
//        configuration.configure("db.cfg.xml"); // load db.cfg.xml
        sessionFactory = configuration.buildSessionFactory();
    }
    private HibernateUtil() {}

    public static Session getSession() {
        return sessionFactory.openSession();
    }

}
