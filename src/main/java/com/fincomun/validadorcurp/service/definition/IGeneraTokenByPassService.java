package com.fincomun.validadorcurp.service.definition;

import com.fincomun.validadorcurp.dto.service.out.GeneraTokenByPass;
import org.springframework.validation.annotation.Validated;

@Validated
public interface IGeneraTokenByPassService {

    GeneraTokenByPass generaToken() throws InterruptedException;

}
