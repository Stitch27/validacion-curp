package com.fincomun.validadorcurp.service.implementation;

import com.fincomun.validadorcurp.dto.controller.request.ValidaCurpRequest;
import com.fincomun.validadorcurp.dto.network.request.renapo.RenapoValidaCurpRequest;
import com.fincomun.validadorcurp.dto.network.response.BasicNetworkResponse;
import com.fincomun.validadorcurp.dto.network.response.renapo.RenapoValidaCurpResponse;
import com.fincomun.validadorcurp.dto.service.out.GeneraTokenByPass;
import com.fincomun.validadorcurp.network.definition.IRenapoApi;
import com.fincomun.validadorcurp.service.definition.IGeneraTokenByPassService;
import com.fincomun.validadorcurp.service.definition.IValidadorCurpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.context.WebApplicationContext;

@Slf4j
@Validated
@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ValidadorCurpService implements IValidadorCurpService {


    private final IGeneraTokenByPassService generaTokenByPassService;
    private final IGeneraTokenByPassService generaToken2c;
    private final IRenapoApi  renapoApi;

    @Autowired
    public ValidadorCurpService(@Qualifier("antiCaptcha") IGeneraTokenByPassService generaTokenByPassService, @Qualifier("twoCaptchaService") IGeneraTokenByPassService generaToken2c, IRenapoApi renapoApi) {
        this.generaTokenByPassService = generaTokenByPassService;
        this.generaToken2c = generaToken2c;
        this.renapoApi = renapoApi;
    }

    @Override
    public RenapoValidaCurpResponse validaCurp(ValidaCurpRequest request) {
        RenapoValidaCurpResponse response = new RenapoValidaCurpResponse();
        try {
            GeneraTokenByPass tokenByPass = generaTokenByPassService.generaToken();

            switch (tokenByPass.getCodigo()){
                case 2 :
                        tokenByPass = generaToken2c.generaToken();
                    break;
            }


            if(tokenByPass.getCodigo() == 0){
                RenapoValidaCurpRequest renapoRequest = new RenapoValidaCurpRequest();
                renapoRequest.setCurp(request.getCurp());
                renapoRequest.setResponse(tokenByPass.getTokenGenerado());
                BasicNetworkResponse<RenapoValidaCurpResponse> renapoResponse = renapoApi.validaCurp(renapoRequest);
                if (renapoResponse.getExitosa()){
                    return renapoResponse.getResponse();
                }else{
                    response.setCodigo(renapoResponse.getCodigo());
                    response.setMensaje(renapoResponse.getMensaje());
                }

            }else {
                response.setCodigo(tokenByPass.getCodigo());
                response.setMensaje(tokenByPass.getMensaje());
            }

        }catch (Exception e){
            log.error("La petición no se pudo procesar");
            log.error(e.getMessage());
            log.error(e.getCause().getLocalizedMessage());
        }

        return response;
    }

}
