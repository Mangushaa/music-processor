package org.example.validation.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(value = {ElementType.FIELD, ElementType.TYPE_USE})
public @interface ExistedSongId {
    String message() default "Song already exists";
}
