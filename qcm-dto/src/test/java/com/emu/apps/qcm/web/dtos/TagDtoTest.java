package com.emu.apps.qcm.web.dtos;

import com.emu.apps.qcm.domain.dtos.TagDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TagDtoTest {
private TagDto pojoObject;
public TagDtoTest() throws Exception {
this.pojoObject = new TagDto();
}
@Test
public void testLibelle() {
String param = "123";
pojoObject.setLibelle(param);
Object result = pojoObject.getLibelle();
assertEquals(param, result);
}

}

