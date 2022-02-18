package com.ss.camper.store.ui.payload;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.*;

@Inherited
@Documented
@Target({
    ElementType.PACKAGE, // 패키지 선언시
    ElementType.TYPE, // 타입 선언시
    ElementType.CONSTRUCTOR, // 생성자 선언시
    ElementType.FIELD, // 멤버 변수 선언시
    ElementType.METHOD, // 메소드 선언시
    ElementType.ANNOTATION_TYPE, // 어노테이션 타입 선언시
    ElementType.LOCAL_VARIABLE, // 지역 변수 선언시
    ElementType.PARAMETER, // 매개 변수 선언시
    ElementType.TYPE_PARAMETER, // 매개 변수 타입 선언시
    ElementType.TYPE_USE // 타입 사용시
})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MultipartFileCountValidator.class)
@ReportAsSingleViolation
public @interface MultipartFileCountValid {

    String message() default "DEFAULT_ERROR_MESSAGE";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int min() default 1;

    int max() default 0;

}