package SQLpack;

import Entity.Product;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Vlad on 15.02.2015.
 */
public interface TransactionManager {
    public <T> T doInTransaction(Callable<T> unitOfWork)throws Exception;


}
