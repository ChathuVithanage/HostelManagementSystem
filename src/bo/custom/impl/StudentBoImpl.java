package bo.custom.impl;

import bo.custom.StudentBo;
import dao.DaoFactory;
import dao.custom.StudentDAO;
import dto.studentDto;
import entity.Student;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentBoImpl implements StudentBo {

    private final StudentDAO studentDAO;
    private final Transaction transaction;

    public StudentBoImpl() throws IOException {
        studentDAO = (StudentDAO) DaoFactory.getDaoFactory().getDAO(DaoFactory.DAOTypes.STUDENT);
        transaction = studentDAO.getSession().getTransaction();
    }

    @Override
    public ArrayList<studentDto> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<studentDto> dtoS = new ArrayList<>();
        transaction.begin();
        // transaction
        List<Student> roomList = null;
        try{
            roomList = studentDAO.getAll();
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
        }
        if (roomList != null) {
            for (Student st : roomList) {
                dtoS.add(new studentDto(st.getStudentId(),st.getName(),st.getAddress(),st.getContactNo(),st.getDob(),st.getGender()));
            }
        }
        // returns an empty arraylist if none found
        return dtoS;
    }

    @Override
    public boolean save(studentDto dto) throws SQLException, ClassNotFoundException {
        Student student = new Student(dto.getStudentId(),dto.getName(),dto.getAddress(),dto.getContactNo(),dto.getDob(),dto.getGender());
        transaction.begin();
        try{
            studentDAO.save(student);
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
        }
        return transaction.getStatus() == TransactionStatus.COMMITTED;
    }

    @Override
    public boolean update(studentDto dto) throws SQLException, ClassNotFoundException {
        Student student = new Student(dto.getStudentId(),dto.getName(),dto.getAddress(),dto.getContactNo(),dto.getDob(),dto.getGender());
        transaction.begin();
        try{
            studentDAO.update(student);
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
        }
        return transaction.getStatus() == TransactionStatus.COMMITTED;
    }

    @Override
    public studentDto search(String s) throws SQLException, ClassNotFoundException {
        transaction.begin();
        Student student = null;
        try{
            student = studentDAO.search(s);
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
        }
        if(transaction.getStatus() == TransactionStatus.COMMITTED){
            if (student != null) {
                return new studentDto(student.getStudentId(),student.getName(),student.getAddress(),student.getContactNo(),student.getDob(),student.getGender());
            }
        }
        return null;
    }

    @Override
    public boolean exist(String s) throws SQLException, ClassNotFoundException {
        transaction.begin();
        boolean isFound = false;
        try{
            isFound = studentDAO.exist(s);
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
            studentDAO.delete(s);
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
        }
        return transaction.getStatus() == TransactionStatus.COMMITTED;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return null;
    }
}
