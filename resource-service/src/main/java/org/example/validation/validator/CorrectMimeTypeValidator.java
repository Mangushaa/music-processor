package org.example.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.tika.Tika;
import org.example.validation.annotation.GivenMimeType;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Component
public class CorrectMimeTypeValidator implements ConstraintValidator<GivenMimeType, byte[]> {

    private String mimeType;
    private Tika tika;

    @Override
    public void initialize(GivenMimeType constraintAnnotation) {
        this.mimeType = constraintAnnotation.mimeType();
        tika = new Tika();
    }

    @Override
    public boolean isValid(byte[] bytes, ConstraintValidatorContext constraintValidatorContext) {
        try {
            String detectedMimeType = tika.detect(new ByteArrayInputStream(bytes));
            return mimeType.equals(detectedMimeType);
        } catch (IOException e) {
            return false;
        }

    }

}
