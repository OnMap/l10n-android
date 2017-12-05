package onmap.co.il.remote.localization;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dev on 09.11.17.
 */

public class Reflection {
    public static List<String> getFields(Class clazz) {
        List<String> fieldNames = new ArrayList<>();
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            String name = field.getName();
            fieldNames.add(name);
        }
        return fieldNames;
    }
}
