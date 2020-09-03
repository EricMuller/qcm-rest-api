package com.emu.apps.qcm.api.dtos.published;

import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PublishedQuestionnaireDtoTest {
private PublishedQuestionnaireDto pojoObject;
public PublishedQuestionnaireDtoTest() {
this.pojoObject = new com.emu.apps.qcm.api.dtos.published.PublishedQuestionnaireDto();
}
@Test
void testUuid() {
String param = "123";
pojoObject.setUuid(param);
Object result = pojoObject.getUuid();
assertEquals(param, result);
}

@Test
void testTitle() {
String param = "123";
pojoObject.setTitle(param);
Object result = pojoObject.getTitle();
assertEquals(param, result);
}

@Test
void testDescription() {
String param = "123";
pojoObject.setDescription(param);
Object result = pojoObject.getDescription();
assertEquals(param, result);
}

@Test
void testCategory() {
String param = "123";
pojoObject.setCategory(param);
Object result = pojoObject.getCategory();
assertEquals(param, result);
}

@Test
void testStatus() {
String param = "123";
pojoObject.setStatus(param);
Object result = pojoObject.getStatus();
assertEquals(param, result);
}

@Test
void testWebsite() {
String param = "123";
pojoObject.setWebsite(param);
Object result = pojoObject.getWebsite();
assertEquals(param, result);
}

@Test
void testPublished() {
Boolean param = Boolean.valueOf(true);
pojoObject.setPublished(param);
Object result = pojoObject.getPublished();
assertEquals(param, result);
}

@Test
void testCreatedBy() {
String param = "123";
pojoObject.setCreatedBy(param);
Object result = pojoObject.getCreatedBy();
assertEquals(param, result);
}

@Test
void testVersion() {
Long param = Long.valueOf(123);
pojoObject.setVersion(param);
Object result = pojoObject.getVersion();
assertEquals(param, result);
}

@Test
void testDateCreation() {
ZonedDateTime param = ZonedDateTime.now();
pojoObject.setDateCreation(param);
Object result = pojoObject.getDateCreation();
assertEquals(param, result);
}

@Test
void testDateModification() {
ZonedDateTime param = ZonedDateTime.now();
pojoObject.setDateModification(param);
Object result = pojoObject.getDateModification();
assertEquals(param, result);
}

}

