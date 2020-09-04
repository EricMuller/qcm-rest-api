package com.emu.tools;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JUnitModelTestGenerator {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        List <JUnitPojoTestGenerator.Package> packages = new ArrayList <>();
        packages.add(new JUnitPojoTestGenerator.Package("qcm-model", "com.emu.apps.qcm.api.models"));
        packages.add(new JUnitPojoTestGenerator.Package("qcm-model", "com.emu.apps.qcm.api.models.question"));
        packages.add(new JUnitPojoTestGenerator.Package("qcm-model", "com.emu.apps.qcm.api.dtos"));
        packages.add(new JUnitPojoTestGenerator.Package("qcm-model", "com.emu.apps.qcm.api.dtos.published"));
        packages.add(new JUnitPojoTestGenerator.Package("qcm-model", "com.emu.apps.qcm.api.dtos.export.v1"));
        packages.add(new JUnitPojoTestGenerator.Package("qcm-model", "com.emu.apps.qcm.api.models.question"));

        new JUnitPojoTestGenerator(packages).run();
    }

}