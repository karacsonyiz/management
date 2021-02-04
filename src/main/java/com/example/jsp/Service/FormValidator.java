package com.example.jsp.Service;

import com.example.jsp.GeneratedEntity.GeneratedUserEntity;
import com.example.jsp.GeneratedEntityRepository.UserEntityRepository;
import com.example.jsp.Model.UserForm;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;
/**
 * This class is using BeanValidation to validate a submitted form.
 */
@Service
public class FormValidator {

    private UserEntityRepository userEntityRepository;

    public FormValidator(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    /**
     *
     * This method decides if the action is add or update.
     *
     * @param userForm The incoming userForm from request.
     * @param errors Errors class to store possible error messages.
     */
    public void checkConstraints(UserForm userForm, Errors errors){
        if(userForm.getUserid() != null){
            queryWithoutSelf(userForm,errors);
        } else {
            queryForUniqueConstraints(userForm,errors);
        }
    }

    /**
     *
     * This method performs check on unique fields if the action is update.
     *
     * @param userForm The incoming userForm from request.
     * @param errors Errors class to store possible error messages.
     */
    public void queryWithoutSelf(UserForm userForm, Errors errors){
        List<GeneratedUserEntity> usersByName = userEntityRepository.getUsersForExclusionByName(userForm.getName(),userForm.getUserid());
        List<GeneratedUserEntity> usersByEmail = userEntityRepository.getUsersForExclusionByEmail(userForm.getEmail(),userForm.getUserid());
        if(usersByName.size() != 0){
            errors.rejectValue("name","This name already exists!","This name already exists!");
        }
        if(usersByEmail.size() != 0){
            errors.rejectValue("email","This email already exists!","This email already exists!");
        }
    }

    /**
     *
     * This method performs check on unique fields is the action is add.
     *
     * @param userForm The incoming userForm from request.
     * @param errors Errors class to store possible error messages.
     */
    public void queryForUniqueConstraints(UserForm userForm,Errors errors){
        List<GeneratedUserEntity> usersByName = userEntityRepository.findByName(userForm.getName());
        List<GeneratedUserEntity> usersByEmail = userEntityRepository.findByEmail(userForm.getEmail());
        if(usersByName.size() != 0){
            errors.rejectValue("name","This name already exists!","This name already exists!");
        }
        if(usersByEmail.size() != 0){
            errors.rejectValue("email","This email already exists!","This email already exists!");
        }
    }

    /**
     * This method sets the error messages if any violation happens during BeanValidation.
     *
     * @param userForm The incoming userForm from request.
     * @param errors Errors class to store possible error messages.
     */
    public void validateForm(UserForm userForm, Errors errors){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<UserForm>> violations = validator.validate(userForm);
        for (ConstraintViolation<UserForm> violation : violations) {
            errors.rejectValue(violation.getPropertyPath().toString(), violation.getMessage(), violation.getMessage());
        }
    }
}
