package SQLpack.imlSQL;

import SQLpack.TransactionManager;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.concurrent.Callable;

/**
 * Created by Vlad on 15.02.2015.
 */
public class TransactionManagerImpl extends BaseDataSource implements TransactionManager {
    public static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/shop?user=root&password=Lytghj12";
    private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();

    @Override
    public <T> T doInTransaction(Callable<T> unitOfWork) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(JDBC_URL);
        con.setAutoCommit(false);
        connectionHolder.set(con);
        try {
            T result = unitOfWork.call();
            con.commit();
            return result;
        } catch (Exception e) {
            con.rollback();
            throw e;
        } finally {
           con.close();
            connectionHolder.remove();
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return connectionHolder.get();
    }
}
