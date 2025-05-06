package org.example.api.v1;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.example.dto.ErrorDto;
import org.example.service.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(exception = {ResourceNotFoundException.class})
    public ResponseEntity<ErrorDto> handleResourceNotFoundException(ResourceNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorDto("Resource with id " + exception.getNotFoundResourceId() + "  not found exception", "404"));
    }

    @ExceptionHandler(exception = {ConstraintViolationException.class})
    public ResponseEntity<ErrorDto> handleConstraintViolationException(ConstraintViolationException exception) {
        Map<Object, Object> violationExceptions = exception.getConstraintViolations().stream()
                .collect(Collectors.toMap(violation -> violation.getPropertyPath() + ": " + violation.getInvalidValue(), ConstraintViolation::getMessage));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDto("Constraint violation exception", "400", violationExceptions));
    }

    @ExceptionHandler(exception = {MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorDto> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDto(exception.getMessage(), "400"));
    }

}
