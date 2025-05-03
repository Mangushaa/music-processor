package org.example.api.v1;

import jakarta.validation.ConstraintViolationException;
import org.example.service.exception.SongNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler
    public void handleException(SongNotFoundException e) {
        System.out.println("Exception occurred: " + e.getMessage());
    }

    @ExceptionHandler
    public void handleException(ConstraintViolationException exception) {
        System.out.println("Exception occurred: " + exception.getMessage());
    }

}
