package com.fincomun.validadorcurp.service.implementation;

import com.fincomun.validadorcurp.dto.network.request.anticaptcha.CreateTaskRequest;
import com.fincomun.validadorcurp.dto.network.request.anticaptcha.GetTaskResultRequest;
import com.fincomun.validadorcurp.dto.network.response.BasicNetworkResponse;
import com.fincomun.validadorcurp.dto.network.response.anticaptcha.CreateTaskResponse;
import com.fincomun.validadorcurp.dto.network.response.anticaptcha.GetTaskResultResponse;
import com.fincomun.validadorcurp.dto.service.out.GeneraTokenByPass;
import com.fincomun.validadorcurp.network.definition.IAntiCaptchaApi;
import com.fincomun.validadorcurp.service.definition.IGeneraTokenByPassService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service("antiCaptcha")
public class GeneraTokenByPassService implements IGeneraTokenByPassService {

    private final IAntiCaptchaApi antiCaptchaApi;

    private final CreateTaskRequest createTaskRequest;

    private final Integer reintentos = 20;

    private final Long tiempoEspera = 3000l;
    @Autowired
    public GeneraTokenByPassService(IAntiCaptchaApi antiCaptchaApi, CreateTaskRequest createTaskRequest) {
        this.antiCaptchaApi = antiCaptchaApi;
        this.createTaskRequest = createTaskRequest;
    }

    @Override
    public GeneraTokenByPass generaToken() throws InterruptedException {
        GeneraTokenByPass response = new GeneraTokenByPass();
        BasicNetworkResponse<CreateTaskResponse> taskResponse = antiCaptchaApi.createTask(createTaskRequest);
        if(taskResponse.getExitosa() && taskResponse.getResponse().getErrorId() == 0){
            GetTaskResultRequest getTaskResultRequest = new GetTaskResultRequest(createTaskRequest.getClientKey(), taskResponse.getResponse().getTaskId());
            for(int i = 0 ; i < reintentos ; i++){
                Thread.sleep(tiempoEspera);
                BasicNetworkResponse<GetTaskResultResponse> getTaskResponse = antiCaptchaApi.getTaskResult(getTaskResultRequest);

                if(     getTaskResponse.getResponse().getErrorId() == 0
                        &&getTaskResponse.getExitosa()
                        && getTaskResponse.getResponse().getStatus() != null
                        && getTaskResponse.getResponse().getStatus().equals("ready")
                        && getTaskResponse.getResponse().getSolution().getGRecaptchaResponse() != null
                        && !getTaskResponse.getResponse().getSolution().getGRecaptchaResponse().isEmpty()){

                    response.setMensaje("Token obtenido");
                    response.setCodigo(0);
                    response.setTokenGenerado(getTaskResponse.getResponse().getSolution().getGRecaptchaResponse());
                    return response;
                }
            }
        }else {
            response.setCodigo(taskResponse.getResponse().getErrorId());
            response.setMensaje(taskResponse.getMensaje());
        }
        return response;
    }
}
