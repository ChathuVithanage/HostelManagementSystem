package bo;

import dto.roomDto;
import dto.userDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SuperBo <T,ID>{
    ArrayList<T> getAll() throws SQLException, ClassNotFoundException;

    boolean  save(T dto) throws SQLException, ClassNotFoundException;

    boolean update(T dto) throws SQLException, ClassNotFoundException;

    T search(ID id)throws SQLException,ClassNotFoundException;

    boolean exist(ID id) throws SQLException, ClassNotFoundException;

    boolean delete(ID id) throws SQLException, ClassNotFoundException;

    String generateNewID() throws SQLException, ClassNotFoundException;

}
