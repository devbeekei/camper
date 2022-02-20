package com.ss.camper.store.ui.payload;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.*;

@Inherited
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MultipartFileCountValidator.class)
@ReportAsSingleViolation
public @interface MultipartFileCountValid {

    String message() default "MultipartFileCountValid Validate Fail";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int min() default 1;

    int max() default 0;

}
