package dao.exception;

/**
 * Created by Vlad on 07.02.2015.
 */
public class DaoBusinessException extends DaoException {
    public DaoBusinessException(String message) {
        super(message);
    }

    public DaoBusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
