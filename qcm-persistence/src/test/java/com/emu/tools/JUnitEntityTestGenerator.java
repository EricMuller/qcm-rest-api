package com.emu.tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JUnitEntityTestGenerator {


    public static void main(String[] args) throws IOException, ClassNotFoundException {

        List <JUnitPojoTestGenerator.Package> packages = new ArrayList <>();

        packages.add(new JUnitPojoTestGenerator.Package("qcm-persistence", "com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.category"));
        packages.add(new JUnitPojoTestGenerator.Package("qcm-persistence", "com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.common"));
        packages.add(new JUnitPojoTestGenerator.Package("qcm-persistence", "com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questionnaires"));
        packages.add(new JUnitPojoTestGenerator.Package("qcm-persistence", "com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questions"));
        packages.add(new JUnitPojoTestGenerator.Package("qcm-persistence", "com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags"));
        packages.add(new JUnitPojoTestGenerator.Package("qcm-persistence", "com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.upload"));
        packages.add(new JUnitPojoTestGenerator.Package("qcm-persistence", "com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.events"));

        new JUnitPojoTestGenerator(packages).run();

    }


}
