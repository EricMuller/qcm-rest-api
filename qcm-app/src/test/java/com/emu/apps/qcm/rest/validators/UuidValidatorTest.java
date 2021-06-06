package com.emu.apps.qcm.rest.validators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UuidValidatorTest {

    private Validator validator;

    @BeforeEach
    public void setUp() throws Exception {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    void isValid() {

        Set <ConstraintViolation <TestUuid>> violations = validator.validate(new TestUuid("list"));
        // then
        assertEquals(1, violations.size());

    }

    public class TestUuid{
        @ValidUuid
        private String uuid;

        public TestUuid(String uuid) {
            this.uuid = uuid;
        }
    }

}
