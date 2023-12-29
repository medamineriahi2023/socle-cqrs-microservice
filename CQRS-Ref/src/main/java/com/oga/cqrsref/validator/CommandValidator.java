package com.oga.cqrsref.validator;
import com.oga.cqrsref.commands.BaseCommand;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

class CommandValidator implements ConstraintValidator<Command, Object> {
    public void initialize(Command constraintAnnotation) {
    }

    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return value instanceof BaseCommand;
    }

}
