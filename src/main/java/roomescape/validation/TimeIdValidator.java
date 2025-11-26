package roomescape.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import roomescape.exception.TimeNotFoundException;
import roomescape.service.TimeService;

@Component
public class TimeIdValidator implements ConstraintValidator<ValidTimeId, String> {
    private final TimeService timeService;

    public TimeIdValidator(TimeService timeService) {
        this.timeService = timeService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!StringUtils.hasText(value)) {
            return false;
        }

        int id;

        // 숫자 여부 확인
        try {
            id = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return false;
        }

        // 실제 존재하는 ID인지 확인
        try {
            timeService.getById(id);
            return true;
        } catch (TimeNotFoundException e) {
            return false;
        }
    }
}