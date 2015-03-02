package dao;

import Entity.Product;
import dao.exception.DaoSystemException;
import dao.exception.NoSuchEntityException;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Vlad on 07.02.2015.
 */
public interface ProductDao {
    /**
     * Never return null
     * @param id
     * @return
     * @throws DaoSystemException
     * @throws NoSuchEntityException
     */
    public Product selectById(int id) throws DaoSystemException, NoSuchEntityException, SQLException;
    public List<Product> selectAll() throws DaoSystemException, SQLException, NoSuchEntityException;
}
