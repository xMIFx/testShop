package dao.impl;

import Entity.Product;
import dao.ProductDao;
import dao.exception.DaoSystemException;
import dao.exception.NoSuchEntityException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Vlad on 15.02.2015.
 */
public class ProductDaoJdbcExternalTxImpl implements ProductDao {
    //public static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/shop?user=root&password=Lytghj12";
    public static final String SELECT_ALL = "Select id, name from products";
    public static final String SELECT_BY_ID = "Select id, name from products where id = ";
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Product selectById(int id) throws DaoSystemException, NoSuchEntityException, SQLException {


        Connection con = dataSource.getConnection();
        try (Statement st = con.createStatement();
             ResultSet res = st.executeQuery((SELECT_BY_ID + id))) {

            int idPr = 0;
            String namePr = null;
            while (res.next()) {
                idPr = res.getInt("id");
                namePr = res.getString("name");
                break;
            }
            if (namePr == null) {
                throw new NoSuchEntityException("No such id " + id);
            }
            return new Product(idPr, namePr);

        } catch (SQLException e) {
            throw e;

        }

    }

    @Override
    public List<Product> selectAll() throws DaoSystemException, SQLException, NoSuchEntityException {
        try {
            List<Product> list = new ArrayList<>();
            Connection con = dataSource.getConnection();
            Statement st = con.createStatement();
            ResultSet res = st.executeQuery((SELECT_ALL));
            while (res.next()) {
                list.add(new Product(res.getInt("id"), res.getString("name")));

            }
            if (list.size() == 0) {
                throw new NoSuchEntityException("No products ");
            }
            return list;

        } catch (SQLException e) {
            throw e;

        } finally {

        }
    }
}
