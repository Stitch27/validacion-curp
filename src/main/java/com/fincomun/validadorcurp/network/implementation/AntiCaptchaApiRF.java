package com.fincomun.validadorcurp.network.implementation;

import com.fincomun.validadorcurp.dto.network.request.anticaptcha.CreateTaskRequest;
import com.fincomun.validadorcurp.dto.network.request.anticaptcha.GetTaskResultRequest;
import com.fincomun.validadorcurp.dto.network.response.BasicNetworkResponse;
import com.fincomun.validadorcurp.dto.network.response.anticaptcha.CreateTaskResponse;
import com.fincomun.validadorcurp.dto.network.response.anticaptcha.GetTaskResultResponse;
import com.fincomun.validadorcurp.network.GenericNetworkCallRF;
import com.fincomun.validadorcurp.network.apicontract.AntiCaptchaApiContract;
import com.fincomun.validadorcurp.network.definition.IAntiCaptchaApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Service
@Validated
public class AntiCaptchaApiRF extends GenericNetworkCallRF implements IAntiCaptchaApi {

    private final AntiCaptchaApiContract antiCaptchaApiContract;

    @Autowired
    public AntiCaptchaApiRF(AntiCaptchaApiContract antiCaptchaApiContract) {
        this.antiCaptchaApiContract = antiCaptchaApiContract;
    }

    @Override
    public BasicNetworkResponse<CreateTaskResponse> createTask(CreateTaskRequest request) {
        BasicNetworkResponse<CreateTaskResponse> response = new BasicNetworkResponse<>();
        try {
            response = execute(antiCaptchaApiContract.createTask(request), CreateTaskResponse.class);
        }catch (Exception e){
            log.error("La petición no se puedo procesar...");
            log.error(e.getMessage());
            log.error(e.getCause().getLocalizedMessage());
        }
        return response;
    }

    @Override
    public BasicNetworkResponse<GetTaskResultResponse> getTaskResult(GetTaskResultRequest request) {
        BasicNetworkResponse<GetTaskResultResponse> response = new BasicNetworkResponse<>();
        try {
            response = execute(antiCaptchaApiContract.getTaskResult(request), GetTaskResultResponse.class);
        }catch (Exception e){
            log.error("La petición no se puedo procesar...");
            log.error(e.getMessage());
            log.error(e.getCause().getLocalizedMessage());
        }
        return response;
    }
}
