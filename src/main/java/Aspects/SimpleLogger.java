package Aspects;

/**
 * Created by Vlad on 11.02.2015.
 */
import org.aspectj.lang.ProceedingJoinPoint;
public class SimpleLogger {
    public Object log (ProceedingJoinPoint call) throws Throwable{
        try {
            return call.proceed();
        }
        finally {
            System.out.println("ASPECT LOGGER: "+call.toShortString()+" called. args = "+call.getArgs());
        }
    }
}
