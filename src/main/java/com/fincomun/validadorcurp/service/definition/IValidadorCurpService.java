package com.fincomun.validadorcurp.service.definition;

import com.fincomun.validadorcurp.dto.controller.request.ValidaCurpRequest;
import com.fincomun.validadorcurp.dto.network.response.renapo.RenapoValidaCurpResponse;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface IValidadorCurpService {

    RenapoValidaCurpResponse validaCurp (@Valid ValidaCurpRequest request);

}
