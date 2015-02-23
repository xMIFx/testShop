package SQLpack;

import java.sql.SQLException;
import java.util.concurrent.Callable;

/**
 * Created by Vlad on 15.02.2015.
 */
public interface TransactionManager1_0 {
    public <T,E extends Exception> T doInTransaction(UnitOfWork<T,E> unitOfWork) throws E, ClassNotFoundException, SQLException;


}
