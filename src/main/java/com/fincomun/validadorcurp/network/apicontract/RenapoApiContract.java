package com.fincomun.validadorcurp.network.apicontract;

import com.fincomun.validadorcurp.dto.network.request.renapo.RenapoValidaCurpRequest;
import com.fincomun.validadorcurp.dto.network.response.renapo.RenapoValidaCurpResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RenapoApiContract {

    @Headers(value = {"Content-Type: application/json" , "Accept:application/json" , "X-Requested-With:XMLHttpRequest" , "Referer:https://www.gob.mx/"})
    @POST("consulta")
    Call<RenapoValidaCurpResponse> validaCurp (@Body RenapoValidaCurpRequest request);
}
