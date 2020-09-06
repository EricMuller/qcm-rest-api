package com.emu.tools.generators;


import com.emu.tools.FieldResolver;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;

import static com.emu.tools.generators.GeneratorUtils.log;


public class JunitClassTestGenerator implements ClassTestGenerator {

    private final Version version;

    private FieldResolver fieldResolver = new PojoFieldResolver();

    public enum Version {
        JUNIT, JUPITER
    }

    public JunitClassTestGenerator() {
        version = Version.JUPITER;
    }

    public JunitClassTestGenerator(Version version) {
        this.version = version;
    }

    private boolean isExcludedClass(Class <?> targetClass) {
        boolean exclude = false;

        if (targetClass.getSimpleName().startsWith("Abstract")) {
            log(" Abstract class " + targetClass + " is excluded!");
            exclude = true;
        }

        if (targetClass.isEnum()) {
            log(" enum class " + targetClass + " is excluded!");
            exclude = true;
        }

        if (!GeneratorUtils.isInstantiable(targetClass)) {
            log(targetClass + " is not Instantiable!");
            exclude = true;
        }

        return exclude;
    }

    /**
     * @param targetClass
     * @param packageName
     * @return
     */
    @Override
    public String generateClassCode(Class <?> targetClass, String packageName) {

        StringBuilder builder = new StringBuilder();

        if (!isExcludedClass(targetClass)) {

            List <Field> fields = fieldResolver.resolveFields(targetClass);

            Set <String> importedClass = new HashSet <>();

            builder.append(String.format("package %s;%n%n", packageName));
            for (Field field : fields) {
                String typeFullname = field.getType().getCanonicalName();
                if (importedClass.contains(typeFullname)) {
                    continue;
                }
                if (!typeFullname.contains(".")) {
                    // Type primitif
                    continue;
                }
                if (typeFullname.startsWith("java.lang.")) {
                    continue;
                }
                builder.append(String.format("import %s;%n", typeFullname));
                importedClass.add(typeFullname);
            }

            if (Version.JUPITER.equals(version)) {
                builder.append(String.format("import org.junit.jupiter.api.Test;%n%n"));
                builder.append(String.format("import static org.junit.jupiter.api.Assertions.*;%n%n"));
            } else {
                builder.append(String.format("import org.junit.*;%n%n"));
            }

            builder.append(String.format("class %sTest {%n", targetClass.getSimpleName()));

            String className = targetClass.getSimpleName();
            String pojoObjectName = "a" + className;

            builder.append(String.format("%n\tprivate final %s %s;%n", className, pojoObjectName));
            builder.append(String.format("%n\tpublic %sTest() {%n", className));
            builder.append(String.format("\t\tthis.%s = new %s();%n", pojoObjectName, className));
            builder.append("\t}\n");

            for (Field field : fields) {
                builder.append("\t@Test\n");
                builder.append(String.format("\tvoid test" + getMethodName(field.getName()) + "() {%n"));
                generateFieldTest(builder, field, version, pojoObjectName);
                builder.append("\t}\n\n");
            }
            builder.append("}\n\n");
        }
        return builder.toString();
    }

    private void generateFieldTest(StringBuilder builder, Field field, Version version, String pojoObjectName) {
        Class <?> parameterType = field.getType();
        builder.append("\t\t");
        if (parameterType.isPrimitive()) {
            builder.append(parameterType.getName()).append(" param = 123;");
        } else if (parameterType.isArray()) {
            builder.append(String.format("%s param = new %s{};", parameterType.getSimpleName(), parameterType.getSimpleName()));
        } else if (parameterType.equals(BigDecimal.class)) {
            builder.append("BigDecimal param = new BigDecimal(\"123\");");
        } else if (parameterType.equals(Boolean.class)) {
            builder.append("Boolean param = Boolean.TRUE;");
        } else if (parameterType.equals(Double.class)) {
            builder.append("Double param = Double.valueOf(123);");
        } else if (parameterType.equals(Integer.class)) {
            builder.append("Integer param = Integer.valueOf(123);");
        } else if (parameterType.equals(List.class)) {
            builder.append(String.format("@SuppressWarnings({\"rawtypes\"})%nList param = new java.util.ArrayList();"));
        } else if (parameterType.equals(Collection.class)) {
            builder.append(String.format("@SuppressWarnings({\"rawtypes\"})%nCollection param = new java.util.ArrayList();"));
        } else if (parameterType.equals(Timestamp.class)) {
            builder.append("Timestamp param = new Timestamp(123);");
        } else if (parameterType.equals(Long.class)) {
            builder.append("Long param = Long.valueOf(123);");
        } else if (parameterType.equals(Map.class)) {
            builder.append("Map param = new java.util.HashMap();");
        } else if (parameterType.equals(Float.class)) {
            builder.append("Float param = Float.valueOf(123);");
        } else if (parameterType.equals(Set.class)) {
            builder.append("Set param = new java.util.HashSet<>();");
        } else if (parameterType.equals(LocalDateTime.class)) {
            builder.append("LocalDateTime param = LocalDateTime.now();");
        } else if (parameterType.equals(String.class)) {
            builder.append("String param = \"123\";");
        } else if (parameterType.equals(ZonedDateTime.class)) {
            builder.append("ZonedDateTime param = ZonedDateTime.now();");
        } else if (parameterType.equals(UUID.class)) {
            builder.append("UUID param = UUID.randomUUID();");
        } else {
            builder.append(String.format("%s param = new %s();", parameterType.getSimpleName(), parameterType.getSimpleName()));
        }

        builder.append("\n");
        builder.append("\t\t").append(String.format("%s.%s(param);%n", pojoObjectName, getSetterName(field.getName())));
        builder.append("\t\t").append(String.format("Object result = %s.%s();%n", pojoObjectName, getGetterName(field.getName())));
        if (Version.JUPITER.equals(version)) {
            builder.append("\t\t").append(String.format("assertEquals(param, result);%n"));
        } else {
            builder.append("\t\t").append(String.format("Assert.assertEquals(param, result);%n"));
        }
    }

    private String getMethodName(String name) {
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }

    private String getGetterName(String name) {
        return "get" + getMethodName(name);
    }

    private String getSetterName(String name) {
        return "set" + getMethodName(name);
    }
}
