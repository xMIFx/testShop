package inject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Vlad on 09.02.2015.
 */
public class FieldReflector {
    public static List<Field> collectUpTo(Class<?> clazz, Class<?> upperBound) {
        ArrayList<Field> result = new ArrayList<>();
        Class<?> current = clazz;
        while (current != upperBound) {
            result.addAll(Arrays.asList(current.getDeclaredFields()));
            current = current.getSuperclass();
        }
        return result;
    }

    public static List<Field> filterInject(List<Field> allFields) {
        ArrayList<Field> result = new ArrayList<>();
        for (Field field : allFields) {
            Inject annotation = field.getAnnotation(Inject.class);
            if (annotation != null) {
                result.add(field);
            }
        }
        return result;
    }
}
