package org.example.laba.valid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.laba.dto.UserDto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z]).{5,20}$";
    private static final Pattern PATTERN = Pattern.compile(PASSWORD_PATTERN);

    @Override
    public boolean isValid(final String string, final ConstraintValidatorContext context) {
        return validatePassword(string);
    }
    private boolean validatePassword(final String password) {
        Matcher matcher = PATTERN.matcher(password);
        return matcher.matches();
    }
}
