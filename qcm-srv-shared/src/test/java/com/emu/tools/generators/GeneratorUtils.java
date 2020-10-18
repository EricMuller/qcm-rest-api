package com.emu.tools.generators;

import java.lang.reflect.Modifier;

public class GeneratorUtils {
    public static boolean isInstantiable(Class <?> clz) {
        //        || String.class.getName().equals(clz.getName()) || Integer.class.getName().equals(clz.getName())
        return !clz.isPrimitive() && !Modifier.isAbstract(clz.getModifiers()) && !clz.isInterface() && !clz.isEnum() && !clz.isArray();
    }

    public static void log(String message) {
        System.out.println(message);
    }
}
