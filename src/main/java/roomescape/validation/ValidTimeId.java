package roomescape.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TimeIdValidator.class)
public @interface ValidTimeId {
    String message() default "잘못된 시간 ID 입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
