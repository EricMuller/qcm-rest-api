package com.emu.tools.generators;

public interface ClassTestGenerator {
    String generateClassCode(Class <?> targetClass, String packageName);
}
