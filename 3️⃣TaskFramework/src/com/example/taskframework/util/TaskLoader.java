package com.example.taskframework.util;
import com.example.taskframework.task.Task;
import java.lang.reflect.Constructor;

public class TaskLoader {
    private static final String TASK_PACKAGE = "com.example.taskframework.task";
    private TaskLoader() {}
    public static Task loadTask(String taskClassName, Object... args) throws Exception {
        String fullClassName = TASK_PACKAGE + "." + taskClassName;
        Class<?> clazz = Class.forName(fullClassName);
        Class<?>[] paramTypes = new Class<?>[args.length];
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (arg instanceof Integer) { paramTypes[i] = int.class; }
            else if (arg instanceof Long) { paramTypes[i] = long.class; }
            else if (arg instanceof Boolean) { paramTypes[i] = boolean.class; }
            else if (arg instanceof Character) { paramTypes[i] = char.class; }
            else if (arg instanceof Byte) { paramTypes[i] = byte.class; }
            else if (arg instanceof Short) { paramTypes[i] = short.class; }
            else if (arg instanceof Float) { paramTypes[i] = float.class; }
            else if (arg instanceof Double) { paramTypes[i] = double.class; }
            else { paramTypes[i] = arg.getClass(); }
        }
        Constructor<?> constructor = clazz.getConstructor(paramTypes);
        return (Task) constructor.newInstance(args);
    }
}