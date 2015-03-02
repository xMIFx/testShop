package inject;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vlad on 19.02.2015.
 */
public class ApplicationContextHolder {
    private static final Map<String, ApplicationContext> pathApplicationContext = new HashMap<>();

    private ApplicationContextHolder() {
    }

    static synchronized ApplicationContext getClassPathXmlApplicationContext(String path) {
        if (!pathApplicationContext.containsKey(path)) {
            pathApplicationContext.put(path, new ClassPathXmlApplicationContext(path));
        }
        return pathApplicationContext.get(path);

    }
}
