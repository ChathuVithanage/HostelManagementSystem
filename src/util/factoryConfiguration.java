package util;

import entity.Reservation;
import entity.Room;
import entity.Student;
import entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Properties;

public class factoryConfiguration {
    private static factoryConfiguration factoryconfiguration;
    private SessionFactory sessionFactory;

    private factoryConfiguration() throws IOException {
        Properties p = new Properties();
        p.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("hibernate.properties"));
        Configuration configuration = new Configuration().setProperties(p).addAnnotatedClass(Student.class).
                addAnnotatedClass(Reservation.class).addAnnotatedClass(Room.class).addAnnotatedClass(User.class);

        sessionFactory = configuration.buildSessionFactory();
    }

    public static factoryConfiguration getInstance() throws IOException {
        if(factoryconfiguration==null){
            factoryconfiguration=new factoryConfiguration();
        }
        return factoryconfiguration;
    }
    public Session getSession(){
        return sessionFactory.openSession();
    }

}
