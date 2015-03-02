package inject;

import java.lang.annotation.*;

/**
 * Created by Vlad on 09.02.2015.
 */
@Documented // write in JavaDoc
@Target(ElementType.FIELD) // We can use annotation on field
@Retention(RetentionPolicy.RUNTIME) // annotation was in JVM, we can use reflection
public @interface Inject {
public String value();
}

