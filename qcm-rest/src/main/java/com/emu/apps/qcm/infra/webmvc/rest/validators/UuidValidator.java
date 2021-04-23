package com.emu.apps.qcm.infra.webmvc.rest.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

public class UuidValidator implements ConstraintValidator <ValidUuid, String> {

    @Override
    public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext) {

        boolean valid = true;
        try {
            UUID.fromString(str);
        } catch (IllegalArgumentException e) {
            valid = false;
        }
        return valid;
    }
}
