package com.emu.apps.qcm.rest.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {UuidValidator.class})
@Documented
public @interface ValidUuid {

    String message() default "{uuid.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
