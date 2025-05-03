package org.example.validation.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(value = ElementType.FIELD)
public @interface NotExistedSongId {
    String message() default "Song not exists";
}
