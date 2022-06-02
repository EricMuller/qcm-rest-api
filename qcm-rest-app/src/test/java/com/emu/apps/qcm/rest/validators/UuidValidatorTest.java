package com.emu.apps.qcm.rest.validators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UuidValidatorTest {

    private Validator validator;

    @BeforeEach
    public void setUp() throws Exception {
        var validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    void isValid() {

        var violations = validator.validate(new TestUuid("list"));
        // then
        assertEquals(1, violations.size());

    }

    public class TestUuid {
        @ValidUuid
        private String uuid;

        public TestUuid(String uuid) {
            this.uuid = uuid;
        }
    }

}
