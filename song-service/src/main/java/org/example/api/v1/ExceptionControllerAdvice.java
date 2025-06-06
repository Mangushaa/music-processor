package org.example.api.v1;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.example.dto.ErrorDto;
import org.example.service.exception.SongNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorDto> handleException(SongNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorDto("Song with id " + exception.getSongId() + "  not found", "404"));
    }

    @ExceptionHandler(exception = {ConstraintViolationException.class})
    public ResponseEntity<ErrorDto> handleConstraintViolationException(ConstraintViolationException exception) {
        Map<Object, Object> violationExceptions = exception.getConstraintViolations().stream().collect(Collectors.toMap(ConstraintViolation::getPropertyPath, ConstraintViolation::getMessage));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDto("Constraint violation exception", "400", violationExceptions));
    }
}
