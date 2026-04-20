package Utilitys;

import annotations.NotNull;

import java.lang.reflect.Field;

public class AnnotationValidator {
    public static void validate(Object o) throws IllegalAccessException {
        Class<?> clazz = o.getClass();

        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(o);

                if (field.isAnnotationPresent(NotNull.class) && value == null) {
                    throw new IllegalArgumentException("Field " + field.getName() + " is required");
                }
            }
            clazz = clazz.getSuperclass();
        }
    }
}
