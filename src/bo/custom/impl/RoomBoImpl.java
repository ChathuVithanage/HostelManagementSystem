package bo.custom.impl;

import bo.custom.RoomBo;
import dao.DaoFactory;
import dao.custom.RoomDAO;
import dto.roomDto;
import entity.Room;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomBoImpl implements RoomBo {
    private final RoomDAO roomDAO;
    private final Transaction transaction;

    public RoomBoImpl() throws IOException {
        roomDAO = (RoomDAO) DaoFactory.getDaoFactory().getDAO(DaoFactory.DAOTypes.ROOM);
        transaction = roomDAO.getSession().getTransaction();
    }

    @Override
    public ArrayList<roomDto> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<roomDto> dtoS = new ArrayList<>();
        transaction.begin();
        // transaction
        List<Room> roomList = null;
        try{
            roomList = roomDAO.getAll();
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
        }
        if (roomList != null) {
            for (Room room : roomList) {
                dtoS.add(new roomDto(room.getRoomTypeId(),room.getType(),room.getKeyMoney(),room.getQty()));
            }
        }

        return dtoS;

    }

    @Override
    public boolean save(roomDto dto) throws SQLException, ClassNotFoundException {
        Room room = new Room(dto.getRoomTypeId(),dto.getRoomtype(),dto.getKeyMoney(),dto.getQty());
        transaction.begin();
        try{
            roomDAO.save(room);
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
        }
        return transaction.getStatus() == TransactionStatus.COMMITTED;
    }

    @Override
    public boolean update(roomDto dto) throws SQLException, ClassNotFoundException {
        Room room = new Room(dto.getRoomTypeId(),dto.getRoomtype(),dto.getKeyMoney(),dto.getQty());
        transaction.begin();
        try{
            roomDAO.update(room);
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
        }
        return transaction.getStatus() == TransactionStatus.COMMITTED;
    }

    @Override
    public roomDto search(String s) throws SQLException, ClassNotFoundException {
        transaction.begin();
        Room room = null;
        try{
            room = roomDAO.search(s);
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
        }
        if(transaction.getStatus() == TransactionStatus.COMMITTED){
            if (room != null) {
                return new roomDto(room.getRoomTypeId(),room.getType(),room.getKeyMoney(),room.getQty());
            }
        }
        return null;
    }

    @Override
    public boolean exist(String s) throws SQLException, ClassNotFoundException {
        transaction.begin();
        boolean isFound = false;
        try{
            isFound = roomDAO.exist(s);
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
        }
        return isFound;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        transaction.begin();
        boolean isDeleted = false;
        try{
            roomDAO.delete(s);
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
        }
        if(transaction.getStatus() == TransactionStatus.COMMITTED){
            isDeleted = true;
        }
        return isDeleted;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return null;
    }
}
