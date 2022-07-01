package bo.custom.impl;

import bo.custom.ReservationBo;
import dao.DaoFactory;
import dao.custom.ReservationDAO;
import dto.reservationDto;
import dto.roomDto;
import dto.studentDto;
import entity.Reservation;
import entity.Room;
import entity.Student;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationBoImpl implements ReservationBo {

    private final ReservationDAO reservationDAO;
    private final Transaction transaction;

    public ReservationBoImpl() throws IOException {
        reservationDAO = (ReservationDAO) DaoFactory.getDaoFactory().getDAO(DaoFactory.DAOTypes.RESERVATION);
        transaction = reservationDAO.getSession().getTransaction();
    }

    @Override
    public ArrayList<reservationDto> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<reservationDto> dtoS = new ArrayList<>();
        transaction.begin();
        //transaction
        List<Reservation> reservationList = null;
        try{
            reservationList = reservationDAO.getAll();
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
        }
        if (reservationList != null) {
            for (Reservation reservation : reservationList) {
                roomDto roomDTO = new roomDto();
                studentDto studentDTO = new studentDto();
                roomDTO.setRoomTypeId(reservation.getRoom().getRoomTypeId());
                studentDTO.setStudentId(reservation.getStudent().getStudentId());
                dtoS.add(new reservationDto(reservation.getReservationId(),reservation.getDate(),reservation.getStatus(),reservation.isPaid(),studentDTO,roomDTO));
            }
        }

        return dtoS;
    }

    @Override
    public boolean save(reservationDto dto) throws SQLException, ClassNotFoundException {
        Student student = new Student();
        Room room = new Room();
        student.setStudentId(dto.getStudent().getStudentId());
        room.setRoomTypeId(dto.getRoom().getRoomTypeId());
        Reservation reservation = new Reservation(dto.getResId(),dto.getDate(),dto.getStatus(),dto.isPaid(), student, room);
        transaction.begin();
        try{
            reservationDAO.save(reservation);
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
        }
        return transaction.getStatus() == TransactionStatus.COMMITTED;
    }

    @Override
    public boolean update(reservationDto dto) throws SQLException, ClassNotFoundException {
        Room room = new Room();
        Student student = new Student();
        room.setRoomTypeId(dto.getRoom().getRoomTypeId());
        student.setStudentId(dto.getStudent().getStudentId());
        Reservation reservation = new Reservation(dto.getResId(),dto.getDate(),dto.getStatus(),dto.isPaid(),student,room);
        transaction.begin();
        try{
            reservationDAO.update(reservation);
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
        }
        return transaction.getStatus() == TransactionStatus.COMMITTED;
    }

    @Override
    public reservationDto search(String s) throws SQLException, ClassNotFoundException {
        transaction.begin();
        Reservation reservation = null;
        try{
            reservation = reservationDAO.search(s);
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
        }
        if(transaction.getStatus() == TransactionStatus.COMMITTED){
            if (reservation != null) {
                roomDto roomDTO = new roomDto();
                studentDto studentDTO = new studentDto();
                roomDTO.setRoomTypeId(reservation.getRoom().getRoomTypeId());
                studentDTO.setStudentId(reservation.getStudent().getStudentId());
                return new reservationDto( reservation.getReservationId(),reservation.getDate(),reservation.getStatus(),reservation.isPaid(),studentDTO,roomDTO);
            }
        }
        return null;
    }

    @Override
    public boolean exist(String s) throws SQLException, ClassNotFoundException {
        transaction.begin();
        boolean isFound = false;
        try{
            isFound = reservationDAO.exist(s);
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
        }
        return isFound;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        transaction.begin();
        try{
            reservationDAO.delete(s);
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
        }
        return transaction.getStatus() == TransactionStatus.COMMITTED;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return reservationDAO.generateNewID();
    }
}
