package com.emu.tools;

import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;

public class JUnitPojoTestGenerator {

    public static final String SERIAL_VERSION_UID = "serialVersionUID";


    private enum Version {
        JUNIT, JUPITER;
    }

    private void log(String message) {
        System.out.println(message);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        JUnitPojoTestGenerator pojoTestGenerator = new JUnitPojoTestGenerator();

        pojoTestGenerator.writePackageTest("qcm-model", "com.emu.apps.qcm.web.dtos", Version.JUPITER);
        pojoTestGenerator.writePackageTest("qcm-model", "com.emu.apps.qcm.web.dtos.question", Version.JUPITER);

        pojoTestGenerator.writePackageTest("qcm-model", "com.emu.apps.qcm.api.dtos.published", Version.JUPITER);
        pojoTestGenerator.writePackageTest("qcm-model", "com.emu.apps.qcm.api.dtos.export.v1", Version.JUPITER);

        pojoTestGenerator.writePackageTest("qcm-entity", "com.emu.apps.qcm.services.jpa.entity.tags", Version.JUPITER);
        pojoTestGenerator.writePackageTest("qcm-entity", "com.emu.apps.qcm.services.jpa.entity.questions", Version.JUPITER);
        pojoTestGenerator.writePackageTest("qcm-entity", "com.emu.apps.qcm.services.jpa.entity.questionnaires", Version.JUPITER);
        pojoTestGenerator.writePackageTest("qcm-entity", "com.emu.apps.qcm.services.jpa.entity.category", Version.JUPITER);
        pojoTestGenerator.writePackageTest("qcm-entity", "com.emu.apps.qcm.services.jpa.entity.upload", Version.JUPITER);

    }

    private void writePackageTest(String moduleName, String pojoPackage, Version version) throws ClassNotFoundException, IOException {

        String sourceFileDirectory = "./" + moduleName + "/src/main/java";
        String testFileDirectory = "./" + moduleName + "/src/test/java";

        File sourceFiles = getSourcesFiles(sourceFileDirectory, pojoPackage);
        File destFiles = getSourcesFiles(testFileDirectory, pojoPackage);

        if (sourceFiles.exists()) {
            for (File javaFile : sourceFiles.listFiles(new JavaFileFilter())) {
                Class <?> targetClass = Class.forName(getClassName(pojoPackage, javaFile.getName()));
                if (!shouldExcludeClass(targetClass)) {
                    writeJunitTest(targetClass, destFiles, pojoPackage, version);
                }
            }
        } else {
            log(sourceFileDirectory + " not exists!");
        }
    }


    private boolean shouldExcludeClass(Class <?> targetClass) {
        boolean exclude = false;

        if (targetClass.getSimpleName().startsWith("Abstract")) {
            log(" Abstract class " + targetClass + " is excluded!");
            exclude = true;
        }

        if (targetClass.isEnum()) {
            log(" enum class " + targetClass + " is excluded!");
            exclude = true;
        }

        if (!isInstantiable(targetClass)) {
            log(targetClass + " is not Instantiable!");
            exclude = true;
        }

        return exclude;
    }

    public static boolean isInstantiable(Class <?> clz) {
        if (clz.isPrimitive() || Modifier.isAbstract(clz.getModifiers()) || clz.isInterface() || clz.isEnum() || clz.isArray()
            //        || String.class.getName().equals(clz.getName()) || Integer.class.getName().equals(clz.getName())
        ) {
            return false;
        }
        return true;
    }

    private void writeJunitTest(Class <?> targetClass, File destFiles, String packageName, Version version) throws IOException {
        List <Field> fields = new ArrayList();

        for (Field field : targetClass.getDeclaredFields()) {
//            try {
            if (SERIAL_VERSION_UID.equals(field.getType())) {
                continue;
            }
            if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            if (isInstantiable(field.getType())) {
                fields.add(field);
            } else {
                log(targetClass + " :" + field.getName() + " is excluded --> " + field.getDeclaringClass() + " is not Instantiable!");
            }
//                Method methodGet = null;
//
//                if (field.equals("boolean")) {
//                    methodGet = targetClass.getDeclaredMethod(isGetterName(field.getName()));
//                }
//                if (Objects.isNull(methodGet)) {
//                    methodGet = targetClass.getDeclaredMethod(getGetterName(field.getName()));
//                }
//                if (Objects.nonNull(methodGet)) {
//                    if (targetClass.getDeclaredMethod(getSetterName(field.getName()), field.getType()) != null) {
//                        fields.add(field);
//                    }
//                }
//            } catch (NoSuchMethodException e) {
//                log(e.getMessage());
//            }
        }

        writeJunitTestCode(targetClass, destFiles, fields, packageName, version);
    }

    private void writeJunitTestCode(Class <?> targetClass, File destFiles, List <Field> fields, String packageName, Version version) throws IOException {
        Set <String> importedClass = new HashSet();
        StringBuilder builder = new StringBuilder();

        String testFileName = targetClass.getSimpleName() + "Test.java";
        File outputFile = new File(destFiles, testFileName);

        builder.append(String.format("package %s;\n\n", packageName));
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
            builder.append(String.format("import %s;\n", typeFullname));
            importedClass.add(typeFullname);
        }

        if (Version.JUPITER.equals(version)) {
            builder.append("import org.junit.jupiter.api.Test;\n\n");
            builder.append("import static org.junit.jupiter.api.Assertions.*;\n\n");
        } else {
            builder.append("import org.junit.*;\n\n");
        }

        builder.append(String.format("public class %sTest {\n", targetClass.getSimpleName()));

        builder.append(String.format("private %s pojoObject;\n", targetClass.getSimpleName()));
        builder.append(String.format("public %sTest() {\n", targetClass.getSimpleName()));
        builder.append(String.format("this.pojoObject = new %s();\n", targetClass.getName()));
        builder.append("}\n");

        for (Field field : fields) {
            builder.append("@Test\n");
            builder.append("public void test" + getMethodName(field.getName()) + "() {\n");
            writeFieldTest(builder, field, version);
            builder.append("}\n\n");
        }
        builder.append("}\n\n");

        outputFile.getParentFile().mkdirs();
        try (FileWriter fileWriter = new FileWriter(outputFile, Charset.forName("UTF-8"), false)) {
            fileWriter.write(builder.toString());
        }
        log(testFileName + " Ok");
    }

    private void writeFieldTest(StringBuilder builder, Field field, Version version) {
        Class <?> parameterType = field.getType();
        if (parameterType.isPrimitive()) {
            builder.append(parameterType.getName() + " param = 123;");
        } else if (parameterType.isArray()) {
            builder.append(parameterType.getSimpleName() + " param = new " + parameterType.getSimpleName() + "{};");
        } else if (parameterType.equals(BigDecimal.class)) {
            builder.append("BigDecimal param = new BigDecimal(\"123\");");
        } else if (parameterType.equals(Boolean.class)) {
            builder.append("Boolean param = Boolean.valueOf(true);");
        } else if (parameterType.equals(Double.class)) {
            builder.append("Double param = Double.valueOf(123);");
        } else if (parameterType.equals(Integer.class)) {
            builder.append("Integer param = Integer.valueOf(123);");
        } else if (parameterType.equals(List.class)) {
            builder.append("@SuppressWarnings({\"rawtypes\"})\nList param = new java.util.ArrayList();");
        } else if (parameterType.equals(Collection.class)) {
            builder.append("@SuppressWarnings({\"rawtypes\"})\nCollection param = new java.util.ArrayList();");
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
        } else {
            builder.append(String.format("%s param = new %s();", parameterType.getSimpleName(), parameterType.getSimpleName()));
        }
        builder.append("\n");
        builder.append("pojoObject." + getSetterName(field.getName()) + "(param);\n");
        builder.append("Object result = pojoObject." + getGetterName(field.getName()) + "();\n");
        if (Version.JUPITER.equals(version)) {
            builder.append("assertEquals(param, result);\n");
        } else {
            builder.append("Assert.assertEquals(param, result);\n");
        }
    }

    private String getMethodName(String name) {
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }

    private String isGetterName(String name) {
        return "is" + getMethodName(name);
    }

    private String getGetterName(String name) {
        return "get" + getMethodName(name);
    }

    private String getSetterName(String name) {
        return "set" + getMethodName(name);
    }

    private String getClassName(String pojoPackage, String name) {
        return pojoPackage + "." + name.substring(0, name.length() - ".java".length());
    }

    private File getSourcesFiles(String parentDirectory, String packageName) {
        return new File(parentDirectory, packageName.replace('.', File.separatorChar));
    }

    static class JavaFileFilter implements FileFilter {
        public boolean accept(File pathname) {
            return pathname.getName().endsWith(".java");
        }
    }
}
