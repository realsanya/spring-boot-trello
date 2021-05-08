package ru.itis.javalab.trello.api.validation;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NamesValidator implements ConstraintValidator<ValidNames, Object> {

    private String namePropertyName;
    private String surnamePropertyName;

    @Override
    public void initialize(ValidNames constraintAnnotation) {
        this.namePropertyName = constraintAnnotation.name();
        this.surnamePropertyName = constraintAnnotation.surname();

    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        Object name = new BeanWrapperImpl(o).getPropertyValue(namePropertyName);
        Object surname = new BeanWrapperImpl(o).getPropertyValue(surnamePropertyName);
        return name != null && !name.equals(surname);
    }
}
