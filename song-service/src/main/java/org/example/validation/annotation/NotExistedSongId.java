package org.example.validation.annotation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.validation.validator.ExistedSongIdValidator;
import org.example.validation.validator.NotExistedSongIdValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.FIELD)
@Constraint(validatedBy = {NotExistedSongIdValidator.class})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotExistedSongId {
    String message() default "Song not exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
