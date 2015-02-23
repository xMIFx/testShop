package SQLpack;

import java.sql.SQLException;

/**
 * Created by Vlad on 18.02.2015.
 */
public interface UnitOfWork <T,E extends Exception> {
    public T doInTx() throws E, SQLException;
}
