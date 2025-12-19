package com.fincomun.validadorcurp.network.definition;

import com.fincomun.validadorcurp.dto.network.request.anticaptcha.CreateTaskRequest;
import com.fincomun.validadorcurp.dto.network.request.anticaptcha.GetTaskResultRequest;
import com.fincomun.validadorcurp.dto.network.response.BasicNetworkResponse;
import com.fincomun.validadorcurp.dto.network.response.anticaptcha.CreateTaskResponse;
import com.fincomun.validadorcurp.dto.network.response.anticaptcha.GetTaskResultResponse;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
public interface IAntiCaptchaApi {

    @NotNull
    BasicNetworkResponse<CreateTaskResponse> createTask (@Valid CreateTaskRequest request);

    BasicNetworkResponse<GetTaskResultResponse> getTaskResult (@Valid GetTaskResultRequest request);


}
