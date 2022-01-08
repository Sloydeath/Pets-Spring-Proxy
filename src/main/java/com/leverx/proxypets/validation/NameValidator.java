package com.leverx.proxypets.validation;

import com.leverx.proxypets.annotations.ValidName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

public class NameValidator implements ConstraintValidator<ValidName, String> {

    private static final String NAME_PATTERN = "[A-Z][a-z]*";

    @Override
    public void initialize(ValidName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return validateName(name);
    }

    private boolean validateName(String name) {
        Pattern pattern = compile(NAME_PATTERN);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }
}
