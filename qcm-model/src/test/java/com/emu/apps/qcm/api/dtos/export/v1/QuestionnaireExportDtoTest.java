package com.emu.apps.qcm.api.dtos.export.v1;

import com.emu.apps.qcm.api.dtos.export.v1.CategoryExportDto;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionnaireExportDtoTest {
private QuestionnaireExportDto pojoObject;
public QuestionnaireExportDtoTest() {
this.pojoObject = new com.emu.apps.qcm.api.dtos.export.v1.QuestionnaireExportDto();
}
@Test
public void testTitle() {
String param = "123";
pojoObject.setTitle(param);
Object result = pojoObject.getTitle();
assertEquals(param, result);
}

@Test
public void testDescription() {
String param = "123";
pojoObject.setDescription(param);
Object result = pojoObject.getDescription();
assertEquals(param, result);
}

@Test
public void testCategory() {
CategoryExportDto param = new CategoryExportDto();
pojoObject.setCategory(param);
Object result = pojoObject.getCategory();
assertEquals(param, result);
}

@Test
public void testStatus() {
String param = "123";
pojoObject.setStatus(param);
Object result = pojoObject.getStatus();
assertEquals(param, result);
}

@Test
public void testWebsite() {
String param = "123";
pojoObject.setWebsite(param);
Object result = pojoObject.getWebsite();
assertEquals(param, result);
}

@Test
public void testUuid() {
String param = "123";
pojoObject.setUuid(param);
Object result = pojoObject.getUuid();
assertEquals(param, result);
}

@Test
public void testVersion() {
Long param = Long.valueOf(123);
pojoObject.setVersion(param);
Object result = pojoObject.getVersion();
assertEquals(param, result);
}

@Test
public void testDateCreation() {
ZonedDateTime param = ZonedDateTime.now();
pojoObject.setDateCreation(param);
Object result = pojoObject.getDateCreation();
assertEquals(param, result);
}

@Test
public void testDateModification() {
ZonedDateTime param = ZonedDateTime.now();
pojoObject.setDateModification(param);
Object result = pojoObject.getDateModification();
assertEquals(param, result);
}

}

