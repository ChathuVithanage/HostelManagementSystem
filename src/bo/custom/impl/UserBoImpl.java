package bo.custom.impl;

import bo.custom.UserBo;
import dao.DaoFactory;
import dao.custom.UserDAO;
import dto.userDto;
import entity.User;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserBoImpl implements UserBo {

    private final UserDAO userDAO;
    private final Transaction transaction;

    public UserBoImpl() throws IOException {
        userDAO = (UserDAO) DaoFactory.getDaoFactory().getDAO(DaoFactory.DAOTypes.USER);
        transaction = userDAO.getSession().getTransaction();
    }

    @Override
    public ArrayList<userDto> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(userDto dto) throws SQLException, ClassNotFoundException {
        User user = new User(dto.getUsername(),dto.getPassword());
        transaction.begin();
        try{
            userDAO.save(user);
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
        }
        return transaction.getStatus() == TransactionStatus.COMMITTED;
    }

    @Override
    public boolean update(userDto dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public userDto search(String s) throws SQLException, ClassNotFoundException {
        User user = userDAO.search(s);
        if(user!=null){
            return new userDto(user.getUserName(), user.getPassword());
        }else return null;
    }

    @Override
    public boolean exist(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return null;
    }
}
