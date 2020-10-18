package com.emu.tools;

import com.emu.tools.generators.ClassTestGenerator;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class PackageTestGenerator {


    private final List <PackageToScan> packageToScans;

    private final ClassTestGenerator classTestGenerator;

    public PackageTestGenerator(ClassTestGenerator classTestGenerator, List <PackageToScan> packages) {
        this.packageToScans = packages;
        this.classTestGenerator = classTestGenerator;
    }

    public static class PackageToScan {
        String moduleName;

        String packageName;

        public PackageToScan(String moduleName, String packageName) {
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


    public void run() throws IOException, ClassNotFoundException {
        for (PackageToScan aPackage : packageToScans) {
            writePackageTest(aPackage.getModuleName(), aPackage.getPackageName());
        }
    }


    private void writePackageTest(String moduleName, String packageName) throws ClassNotFoundException, IOException {

        String sourceFileDirectory;
        String testFileDirectory;
        if (StringUtils.isNotBlank(moduleName)) {
            sourceFileDirectory = "./" + moduleName + "/src/main/java";
            testFileDirectory = "./" + moduleName + "/src/test/java";
        } else {
            sourceFileDirectory = "./src/main/java";
            testFileDirectory = "./src/test/java";
        }

        File sourceDirectory = getSourcesFiles(sourceFileDirectory, packageName);
        File destinationDirectory = getSourcesFiles(testFileDirectory, packageName);

        if (sourceDirectory.exists()) {
            for (File javaFile : sourceDirectory.listFiles(new JavaFileFilter())) {
                Class <?> targetClass = Class.forName(getClassName(packageName, javaFile.getName()));
                String source = classTestGenerator.generateClassCode(targetClass, packageName);
                if (StringUtils.isNotBlank(source)) {
                    saveSourceToFile(targetClass, destinationDirectory, source);
                }

            }
        } else {
            log(sourceFileDirectory + " not exists!");
        }
    }


    private void saveSourceToFile(Class <?> targetClass, File destFiles, String source) throws IOException {
        String testFileName = targetClass.getSimpleName() + "Test.java";
        File outputFile = new File(destFiles, testFileName);
        outputFile.getParentFile().mkdirs();
        try (FileWriter fileWriter = new FileWriter(outputFile, StandardCharsets.UTF_8, false)) {
            fileWriter.write(source);
        }
        log(testFileName + " Ok");
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

    private void log(String message) {
        System.out.println(message);
    }
}
