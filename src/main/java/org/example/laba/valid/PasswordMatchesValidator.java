package org.example.laba.valid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.laba.dto.PasswordDto;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
        //
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final PasswordDto passwordDto = (PasswordDto) obj;
        return passwordDto.getNewPassword().equals(passwordDto.getConfirmPassword());
    }

}
