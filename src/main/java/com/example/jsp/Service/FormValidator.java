package com.example.jsp.Service;

import com.example.jsp.Model.UserForm;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Service
public class FormValidator {

    public void validateForm(UserForm userForm, Errors errors){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<UserForm>> violations = validator.validate(userForm);
        for (ConstraintViolation<UserForm> violation : violations) {
            errors.rejectValue(violation.getPropertyPath().toString(), violation.getMessage(), violation.getMessage());
        }
    }

}
