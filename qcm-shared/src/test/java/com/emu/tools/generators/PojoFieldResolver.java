package com.emu.tools.generators;

import com.emu.tools.FieldResolver;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import static com.emu.tools.generators.GeneratorUtils.log;

public class PojoFieldResolver implements FieldResolver {


    public static final String SERIAL_VERSION_UID = "serialVersionUID";

    @Override
    public List <Field> resolveFields(Class <?> targetClass) {
        List <Field> fields = new ArrayList <>();
        for (Field field : FieldUtils.getAllFields(targetClass)) {

            if (SERIAL_VERSION_UID.equals(field.getName())) {
                continue;
            }
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            if (GeneratorUtils.isInstantiable(field.getType())) {
                fields.add(field);
            } else {
                log(targetClass + " :" + field.getName() + " is excluded --> " + field.getDeclaringClass() + " is not Instantiable!");
            }
        }
        return fields;
    }




}
