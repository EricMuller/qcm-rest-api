package com.emu.apps.qcm.api.dtos.export.v1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionnaireTagExportDtoTest {
private QuestionnaireTagExportDto pojoObject;
public QuestionnaireTagExportDtoTest() {
this.pojoObject = new com.emu.apps.qcm.api.dtos.export.v1.QuestionnaireTagExportDto();
}
@Test
public void testLibelle() {
String param = "123";
pojoObject.setLibelle(param);
Object result = pojoObject.getLibelle();
assertEquals(param, result);
}

}

