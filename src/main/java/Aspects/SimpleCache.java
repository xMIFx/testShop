package Aspects;

/**
 * Created by Vlad on 11.02.2015.
 */

import org.aspectj.lang.ProceedingJoinPoint;

import java.util.Arrays;


public class SimpleCache {
    public Object saveCache(ProceedingJoinPoint call, int id) throws Throwable {
        try {
            Object[] arg = call.getArgs();
            String className = call.getClass().getName();
            String methodName = call.getSignature().getName();
            String key = className + methodName + Arrays.toString(arg);
          if ( Cache.getCache().cacheMap.containsKey(key)){
              System.out.println("We find "+Cache.getCache().cacheMap.get(key)+" in cache on this key "+key);
              return Cache.getCache().cacheMap.get(key);

          } Object o = call.proceed();
            Cache.getCache().cacheMap.put(key,o);
            System.out.println("We put "+o+" in cache on this key "+key);
            return o;


        } finally {

        }
    }
}
