package com.fincomun.validadorcurp.network.apicontract;

import com.fincomun.validadorcurp.dto.network.request.anticaptcha.CreateTaskRequest;
import com.fincomun.validadorcurp.dto.network.request.anticaptcha.GetTaskResultRequest;
import com.fincomun.validadorcurp.dto.network.response.anticaptcha.CreateTaskResponse;
import com.fincomun.validadorcurp.dto.network.response.anticaptcha.GetTaskResultResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AntiCaptchaApiContract {

    @Headers(value = {"Content-Type: application/json"})
    @POST("createTask")
    Call<CreateTaskResponse> createTask (@Body CreateTaskRequest request);

    @Headers(value = {"Content-Type: application/json"})
    @POST("getTaskResult")
    Call<GetTaskResultResponse> getTaskResult (@Body GetTaskResultRequest request);

}
