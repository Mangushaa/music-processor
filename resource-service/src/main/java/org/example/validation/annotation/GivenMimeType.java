package org.example.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.validation.validator.CorrectMimeTypeValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = {ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = {CorrectMimeTypeValidator.class})
@Retention(RetentionPolicy.RUNTIME)
public @interface GivenMimeType {
    String mimeType();

    String message() default "Invalid mime type";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
