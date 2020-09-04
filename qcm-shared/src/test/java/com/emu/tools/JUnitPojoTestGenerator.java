package com.emu.tools;

import org.apache.commons.lang3.reflect.FieldUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;

public class JUnitPojoTestGenerator {

    public static final String SERIAL_VERSION_UID = "serialVersionUID";

    private final List <Package> packages;

    public static class Package {
        String moduleName;

        String packageName;

        public Package(String moduleName, String packageName) {
            this.moduleName = moduleName;
            this.packageName = packageName;
        }

        public String getModuleName() {
            return moduleName;
        }

        public String getPackageName() {
            return packageName;
        }
    }


    private enum Version {
        JUNIT, JUPITER
    }

    private void log(String message) {
        System.out.println(message);
    }

    public JUnitPojoTestGenerator(List <Package> packages) {
        this.packages = packages;
    }

    public void run() throws IOException, ClassNotFoundException {

        for (Package aPackage : packages) {
            writePackageTest(aPackage.getModuleName(), aPackage.getPackageName());
        }
    }


    private void writePackageTest(String moduleName, String pojoPackage) throws ClassNotFoundException, IOException {

        String sourceFileDirectory = "./" + moduleName + "/src/main/java";
        String testFileDirectory = "./" + moduleName + "/src/test/java";

        File sourceFiles = getSourcesFiles(sourceFileDirectory, pojoPackage);
        File destFiles = getSourcesFiles(testFileDirectory, pojoPackage);

        if (sourceFiles.exists()) {
            for (File javaFile : sourceFiles.listFiles(new JavaFileFilter())) {
                Class <?> targetClass = Class.forName(getClassName(pojoPackage, javaFile.getName()));
                if (!shouldExcludeClass(targetClass)) {
                    writeJunitTest(targetClass, destFiles, pojoPackage);
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
        //        || String.class.getName().equals(clz.getName()) || Integer.class.getName().equals(clz.getName())
        return !clz.isPrimitive() && !Modifier.isAbstract(clz.getModifiers()) && !clz.isInterface() && !clz.isEnum() && !clz.isArray();
    }

    private void writeJunitTest(Class <?> targetClass, File destFiles, String packageName) throws IOException {
        List <Field> fields = new ArrayList <>();

        for (Field field : FieldUtils.getAllFields(targetClass)) {

            if (SERIAL_VERSION_UID.equals(field.getName())) {
                continue;
            }
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            if (isInstantiable(field.getType())) {
                fields.add(field);
            } else {
                log(targetClass + " :" + field.getName() + " is excluded --> " + field.getDeclaringClass() + " is not Instantiable!");
            }

        }

        writeJunitTestCode(targetClass, destFiles, fields, packageName, Version.JUPITER);
    }

    private void writeJunitTestCode(Class <?> targetClass, File destFiles, List <Field> fields, String packageName, Version version) throws IOException {
        Set <String> importedClass = new HashSet <>();
        StringBuilder builder = new StringBuilder();

        String testFileName = targetClass.getSimpleName() + "Test.java";
        File outputFile = new File(destFiles, testFileName);

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
            writeFieldTest(builder, field, version, pojoObjectName);
            builder.append("\t}\n\n");
        }
        builder.append("}\n\n");

        outputFile.getParentFile().mkdirs();
        try (FileWriter fileWriter = new FileWriter(outputFile, StandardCharsets.UTF_8, false)) {
            fileWriter.write(builder.toString());
        }
        log(testFileName + " Ok");
    }

    private void writeFieldTest(StringBuilder builder, Field field, Version version, String pojoObjectName) {
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
