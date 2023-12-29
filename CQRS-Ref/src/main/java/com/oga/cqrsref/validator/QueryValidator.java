package com.oga.cqrsref.validator;
import com.oga.cqrsref.queries.BaseQuery;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

class QueryValidator implements ConstraintValidator<Query, Object> {
    public void initialize(Query constraintAnnotation) {
    }

    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return value instanceof BaseQuery;
    }

}
