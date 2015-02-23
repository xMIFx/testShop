package Aspects;

/**
 * Created by Vlad on 11.02.2015.
 */
import org.aspectj.lang.ProceedingJoinPoint;
public class SimpleProfiler {
    public Object profile(ProceedingJoinPoint call, int id) throws Throwable
    {long to = System.nanoTime();
        try{
            return call.proceed();
        }
        finally {
            long t1 = System.nanoTime();
            System.out.println("ASPECT PROFILER "+call.toShortString()+", worked by"+(t1-to));
        }

    }
}
