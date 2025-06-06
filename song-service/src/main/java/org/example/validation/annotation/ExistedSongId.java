package org.example.validation.annotation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.validation.validator.ExistedSongIdValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {ExistedSongIdValidator.class})
@Target(value = {ElementType.FIELD, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistedSongId {
    String message() default "Song already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
