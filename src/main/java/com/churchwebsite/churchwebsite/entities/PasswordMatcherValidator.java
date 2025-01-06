package com.churchwebsite.churchwebsite.entities;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatcherValidator implements ConstraintValidator<PasswordMatcher, User> {
    @Override
    public void initialize(PasswordMatcher constraintAnnotation) {
    }

    @Override
    public boolean isValid(User user, ConstraintValidatorContext context) {
        if (user == null) {
            return true; // You can modify this depending on your logic
        }
        return user.getPassword() != null && user.getPasswordConfirm() != null &&
                user.getPassword().equals(user.getPasswordConfirm());
    }
}
