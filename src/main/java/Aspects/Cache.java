package Aspects;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Vlad on 11.02.2015.
 */
public class Cache {
    public Map<String, Object> cacheMap = new ConcurrentHashMap<>();
    private static Cache cache;

    private Cache() {

    }

    public static Cache getCache() {
        if (cache == null) cache = new Cache();
        return cache;
    }
}
