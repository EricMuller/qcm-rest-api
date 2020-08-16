package com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.category;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CategoryTest {
    private CategoryEntity pojoObject;

    public CategoryTest() throws Exception {
        this.pojoObject = new CategoryEntity();
    }

    @Test
    public void testLibelle() {
        String param = "123";
        pojoObject.setLibelle(param);
        Object result = pojoObject.getLibelle();
        assertEquals(param, result);
    }

}

