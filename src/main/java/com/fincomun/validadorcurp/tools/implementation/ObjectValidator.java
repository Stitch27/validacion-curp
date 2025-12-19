package com.fincomun.validadorcurp.tools.implementation;

import com.fincomun.validadorcurp.dto.common.SimpleResponse;
import com.fincomun.validadorcurp.tools.definition.IObjectValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import java.util.regex.Pattern;

@Slf4j
@Validated
@Component
public class ObjectValidator implements IObjectValidator {


    private final Validator validator;
    private final EmailValidator emailValidator;

    public ObjectValidator() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        emailValidator = EmailValidator.getInstance();
    }

    @Override
    public <T> SimpleResponse validateObject(T request) {
        SimpleResponse response = new SimpleResponse();
        try {
            Set<ConstraintViolation<T>> constraintViolations = validator.validate(request);
            if(constraintViolations.size() == 0){
                response.setCodigo(0);
                response.setMensaje("El objeto válido");
            }else{
                log.error("El objeto contiene valores inválidos");
                constraintViolations.forEach(i -> log.error(i.getMessage()));
                response.setCodigo(-30);
                response.setMensaje("El objeto contiene valores invalidos");
            }
        }catch (Exception e){
            log.error("El objeto no se pudo validar");
            log.error(e.getMessage());
            log.error(e.getCause().toString());
            log.error(e.getStackTrace().toString());
        }
        return response;
    }

    @Override
    public boolean isValidEmailAddress(String email) {
        return emailValidator.isValid(email);
    }

    @Override
    public boolean isValidPhone(String phone) {
        return Pattern.matches("^[0-9]{10}$", phone);
    }
}
