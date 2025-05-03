package org.example.validation.validator;

import jakarta.validation.ConstraintValidator;
import lombok.RequiredArgsConstructor;
import org.example.repository.SongRepository;
import org.example.validation.annotation.NotExistedSongId;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotExistedSongIdValidator implements ConstraintValidator<NotExistedSongId, Integer> {

    private SongRepository songRepository;

    @Override
    public boolean isValid(Integer id, jakarta.validation.ConstraintValidatorContext context) {
        return id != null && songRepository.findById(id).isEmpty();
    }
}
