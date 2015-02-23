package SQLpack.imlSQL;

import SQLpack.TransactionManager;
import SQLpack.TransactionManager1_0;
import SQLpack.UnitOfWork;
import inject.Inject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.Callable;

/**
 * Created by Vlad on 15.02.2015.
 */
public class TransactionManagerImpl1_0 extends BaseDataSource implements TransactionManager1_0 {
    public static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/shop?user=root&password=Lytghj12";
    private static DataSource dataSourcePool;

    public void setDataSource(DataSource dataSourcePool) {
        TransactionManagerImpl1_0.dataSourcePool = dataSourcePool;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSourcePool.getConnection();
    }

    @Override
    public <T, E extends Exception> T doInTransaction(UnitOfWork<T, E> unitOfWork) throws E, ClassNotFoundException, SQLException {
        // Class.forName("com.mysql.jdbc.Driver");

        //Connection con = DriverManager.getConnection(JDBC_URL);
        Connection con = dataSourcePool.getConnection();
        con.setAutoCommit(false);

        try {
            T result = unitOfWork.doInTx();
            con.commit();
            return result;
        } catch (Exception e) {
            con.rollback();
            throw e;
        } finally {
            con.close();

        }
    }
}
