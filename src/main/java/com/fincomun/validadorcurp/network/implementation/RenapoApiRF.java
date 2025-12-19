package com.fincomun.validadorcurp.network.implementation;

import com.fincomun.validadorcurp.dto.network.request.renapo.RenapoValidaCurpRequest;
import com.fincomun.validadorcurp.dto.network.response.BasicNetworkResponse;
import com.fincomun.validadorcurp.dto.network.response.renapo.RenapoValidaCurpResponse;
import com.fincomun.validadorcurp.network.GenericNetworkCallRF;
import com.fincomun.validadorcurp.network.apicontract.RenapoApiContract;
import com.fincomun.validadorcurp.network.definition.IRenapoApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Service
@Validated
public class RenapoApiRF extends GenericNetworkCallRF implements IRenapoApi {

    private final RenapoApiContract renapoApiContract;

    @Autowired
    public RenapoApiRF(RenapoApiContract renapoApiContract) {
        this.renapoApiContract = renapoApiContract;
    }

    @Override
    public BasicNetworkResponse<RenapoValidaCurpResponse> validaCurp(RenapoValidaCurpRequest request) {
        BasicNetworkResponse<RenapoValidaCurpResponse> response = new BasicNetworkResponse<>();
        try {
            response = execute(renapoApiContract.validaCurp(request), RenapoValidaCurpResponse.class);
        }catch (Exception e){
            log.error("La petición no se puedo procesar...");
            log.error(e.getMessage());
            log.error(e.getCause().getLocalizedMessage());
        }
        return response;
    }
}
