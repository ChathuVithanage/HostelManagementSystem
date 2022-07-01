package bo;

import bo.custom.impl.ReservationBoImpl;
import bo.custom.impl.RoomBoImpl;
import bo.custom.impl.StudentBoImpl;
import bo.custom.impl.UserBoImpl;

import java.io.IOException;

public class BoFactory {
    private static BoFactory boFactory;

    private BoFactory() {
    }

    public static BoFactory getBoFactory() {
        if (boFactory == null) {
            boFactory = new BoFactory();
        }
        return boFactory;
    }

    public SuperBo getBO(BOTypes types) throws IOException {
        switch (types) {
            case RESERVATION:
                return new ReservationBoImpl();
            case STUDENT:
                return new StudentBoImpl();
            case ROOM:
                return new RoomBoImpl();
            case USER:
                return new UserBoImpl();
            default:
                return null;
        }
    }

    public enum BOTypes {
        RESERVATION,STUDENT,ROOM,USER
    }
}
