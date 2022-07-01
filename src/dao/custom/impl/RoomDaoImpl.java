package dao.custom.impl;

import dao.custom.RoomDAO;
import entity.Room;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.factoryConfiguration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RoomDaoImpl  implements RoomDAO {

    private final Session session;

    public Session getSession() {

        return session;
    }

    public RoomDaoImpl() throws IOException {
        session = factoryConfiguration.getInstance().getSession();
    }

    @Override
    public List<Room> getAll() throws SQLException, ClassNotFoundException {
        Query query = session.createQuery("from Room");
        return query.list();
    }

    @Override
    public void save(Room entity) throws SQLException, ClassNotFoundException {
        session.save(entity);
    }

    @Override
    public void update(Room entity) throws SQLException, ClassNotFoundException {
        Room room = session.get(Room.class,entity.getRoomTypeId());
        room.setKeyMoney(entity.getKeyMoney());
        room.setQty(entity.getQty());
        room.setType(room.getType());
    }

    @Override
    public Room search(String s) throws SQLException, ClassNotFoundException {
       /* Query query = session.createQuery("FROM Room AS r WHERE r.roomTypeId = :code");
        query.setParameter("code",s);*/
        return session.get(Room.class,s);
    }

    @Override
    public boolean exist(String s) throws SQLException, ClassNotFoundException {
        return session.get(s, Room.class) != null;
    }

    @Override
    public void delete(String s) throws SQLException, ClassNotFoundException {
        Room room = session.load(Room.class,s);
        session.remove(room);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return null;
    }

}
