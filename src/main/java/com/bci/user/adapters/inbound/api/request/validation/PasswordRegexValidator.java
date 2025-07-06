package com.bci.user.adapters.inbound.api.request.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class PasswordRegexValidator implements ConstraintValidator<ValidPassword, String> {

    @Value("${validation.password.pattern}")
    private String regex;

    @Value("${validation.password.message}")
    private String errorMessage;

    private Pattern pattern;

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        pattern = Pattern.compile(regex);
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) return true;

        boolean matches = pattern.matcher(password).matches();

        if (!matches) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(errorMessage)
                    .addConstraintViolation();
        }

        return matches;
    }
}
