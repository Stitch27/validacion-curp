package com.fincomun.validadorcurp.network.definition;

import com.fincomun.validadorcurp.dto.network.request.renapo.RenapoValidaCurpRequest;
import com.fincomun.validadorcurp.dto.network.response.BasicNetworkResponse;
import com.fincomun.validadorcurp.dto.network.response.renapo.RenapoValidaCurpResponse;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface IRenapoApi {
    BasicNetworkResponse<RenapoValidaCurpResponse> validaCurp (@Valid RenapoValidaCurpRequest request);
}
