package be.brickbit.lpm.catering.controller.validator;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LocalDateFutureValidator implements ConstraintValidator<LocalDateFuture, LocalDate>{
    @Override
    public void initialize(LocalDateFuture constraintAnnotation) {
        // This is Empty Ö
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        return value.isAfter(LocalDate.now());
    }
}
