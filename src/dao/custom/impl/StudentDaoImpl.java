package dao.custom.impl;

import dao.custom.StudentDAO;
import entity.Student;
import org.hibernate.Query;
import org.hibernate.Session;
import util.factoryConfiguration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class StudentDaoImpl  implements StudentDAO {

    private final Session session;

    public Session getSession() {

        return session;
    }

    public StudentDaoImpl() throws IOException {
        session = factoryConfiguration.getInstance().getSession();
    }

    @Override
    public List<Student> getAll() throws SQLException, ClassNotFoundException {
        Query query = session.createQuery("from Student");
        return query.list();
    }

    @Override
    public void save(Student entity) throws SQLException, ClassNotFoundException {
        session.save(entity);
    }

    @Override
    public void update(Student entity) throws SQLException, ClassNotFoundException {
        Student student = session.get(Student.class,entity.getStudentId());
        student.setAddress(entity.getAddress());
        student.setContactNo(entity.getContactNo());
        student.setDob(entity.getDob());
        student.setName(entity.getName());
        student.setGender(entity.getGender());
    }

    @Override
    public Student search(String s) throws SQLException, ClassNotFoundException {
        return session.get(Student.class,s);
    }

    @Override
    public boolean exist(String s) throws SQLException, ClassNotFoundException {
        return session.get(Student.class,s) != null;
    }

    @Override
    public void delete(String s) throws SQLException, ClassNotFoundException {
        Student student = session.load(Student.class,s);
        session.delete(student);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return null;
    }

}
