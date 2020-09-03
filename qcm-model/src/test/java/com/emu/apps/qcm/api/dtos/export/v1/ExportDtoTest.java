package com.emu.apps.qcm.api.dtos.export.v1;

import com.emu.apps.qcm.api.dtos.export.v1.QuestionnaireExportDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExportDtoTest {
private ExportDto pojoObject;
public ExportDtoTest() {
this.pojoObject = new com.emu.apps.qcm.api.dtos.export.v1.ExportDto();
}
@Test
public void testName() {
String param = "123";
pojoObject.setName(param);
Object result = pojoObject.getName();
assertEquals(param, result);
}

@Test
public void testVersionExport() {
String param = "123";
pojoObject.setVersionExport(param);
Object result = pojoObject.getVersionExport();
assertEquals(param, result);
}

@Test
public void testQuestionnaire() {
QuestionnaireExportDto param = new QuestionnaireExportDto();
pojoObject.setQuestionnaire(param);
Object result = pojoObject.getQuestionnaire();
assertEquals(param, result);
}

}

