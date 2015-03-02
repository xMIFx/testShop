package Aspects;

/**
 * Created by Vlad on 11.02.2015.
 */
import org.aspectj.lang.JoinPoint;
public class SimpleExceptionLogger {
    public void logException(JoinPoint joinPoint, Throwable t){
        System.out.println("ASPECT.Exception-LOGGER: "+t.getMessage());
    }
}
