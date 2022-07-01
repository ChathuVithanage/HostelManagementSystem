package dao;

import dao.custom.impl.ReservationDaoImpl;
import dao.custom.impl.RoomDaoImpl;
import dao.custom.impl.StudentDaoImpl;
import dao.custom.impl.UserDaoImpl;

import java.io.IOException;

public class DaoFactory {
    private static DaoFactory daoFactory;

    private DaoFactory() {
    }

    public static DaoFactory getDaoFactory() {
        if (daoFactory == null) {
            daoFactory = new DaoFactory();
        }
        return daoFactory;
    }

    public enum DAOTypes {
        STUDENT,ROOM,RESERVATION,USER
    }

    public SuperDao getDAO(DAOTypes types) throws IOException {
        switch (types) {
            case STUDENT:
                return new StudentDaoImpl();
            case ROOM:
                return new RoomDaoImpl();
            case RESERVATION:
                return  new ReservationDaoImpl();
            case USER:
                return new UserDaoImpl();
            default:
                return null;
        }
    }
}
