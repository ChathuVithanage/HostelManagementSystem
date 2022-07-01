package dao.custom.impl;

import dao.custom.ReservationDAO;
import entity.Reservation;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.factoryConfiguration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

public class ReservationDaoImpl implements ReservationDAO {
    private final Session session;

    public Session getSession() {
        return session;
    }

    public ReservationDaoImpl() throws IOException {
        session = factoryConfiguration.getInstance().getSession();
    }

    @Override
    public List<Reservation> getAll() throws SQLException, ClassNotFoundException {
        Query query = session.createQuery("from Reservation");
        return query.list();
    }

    @Override
    public void save(Reservation entity) throws SQLException, ClassNotFoundException {
        session.save(entity);
    }

    @Override
    public void update(Reservation entity) throws SQLException, ClassNotFoundException {
        Reservation res = session.get(Reservation.class,entity.getReservationId());
        res.setPaid(entity.isPaid());
        res.setStatus(entity.getStatus());
        res.setStudent(entity.getStudent());
        res.setRoom(entity.getRoom());
    }

    @Override
    public Reservation search(String s) throws SQLException, ClassNotFoundException {
        return session.get(Reservation.class,s);
    }

    @Override
    public boolean exist(String s) throws SQLException, ClassNotFoundException {
        return session.get(Reservation.class,s) != null;
    }

    @Override
    public void delete(String s) throws SQLException, ClassNotFoundException {
        Reservation reservation = session.get(Reservation.class,s);
        session.delete(reservation);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        Query query = session.createQuery("SELECT reservationId AS id FROM Reservation ORDER BY id DESC");
        if(query.list()==null){
            System.out.println("null query");
        }
        query.setMaxResults(1);
        Iterator it =  query.list().iterator();
        if(it.hasNext()){
            String id = (String) it.next();
            int newCustomerId = Integer.parseInt(id.replace("RS-", "")) + 1;
            return String.format("RS-%03d", newCustomerId);
        }else {
            return "RS-001";
        }
    }

}
