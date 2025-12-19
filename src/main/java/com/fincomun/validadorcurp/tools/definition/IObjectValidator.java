package com.fincomun.validadorcurp.tools.definition;

import com.fincomun.validadorcurp.dto.common.SimpleResponse;
import org.springframework.validation.annotation.Validated;

@Validated
public interface IObjectValidator {

    <T> SimpleResponse validateObject(T request);

    boolean isValidEmailAddress(String email);

    boolean isValidPhone(String phone);
}
