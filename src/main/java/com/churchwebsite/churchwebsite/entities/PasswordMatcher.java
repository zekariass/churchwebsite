package com.churchwebsite.churchwebsite.entities;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordMatcherValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordMatcher {

    String message() default "Password does not match!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
