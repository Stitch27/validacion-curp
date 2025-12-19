package com.fincomun.validadorcurp.dto.network.request.renapo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RenapoValidaCurpRequest {
    @SerializedName("curp")
    @Expose
    private String curp;

    @SerializedName("tipoBusqueda")
    @Expose
    private final String tipoBusqueda = "curp";

    @SerializedName("ip")
    @Expose
    private final String ip = "127.0.0.1";

    @SerializedName("response")
    @Expose
    private String response;
}
