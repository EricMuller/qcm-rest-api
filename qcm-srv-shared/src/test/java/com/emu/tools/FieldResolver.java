package com.emu.tools;

import java.lang.reflect.Field;
import java.util.List;

public interface FieldResolver {
    List <Field> resolveFields(Class <?> targetClass);
}
