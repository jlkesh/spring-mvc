package uz.jl.annotations.validators;

import org.springframework.util.StringUtils;
import uz.jl.annotations.ValidLocalDateTime;
import uz.jl.util.Utils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Validator;
import java.time.Clock;
import java.time.LocalDateTime;

public class ValidLocalDateTimeValidator implements ConstraintValidator<ValidLocalDateTime, String> {
    private String message;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Utils.isParsable(value)) {
            LocalDateTime localDateTime = Utils.toLocalDateTime(value);
            return !localDateTime.isBefore(LocalDateTime.now(Clock.systemDefaultZone()));
        }
        return !StringUtils.hasText(value);
    }

    @Override
    public void initialize(ValidLocalDateTime constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }
}
