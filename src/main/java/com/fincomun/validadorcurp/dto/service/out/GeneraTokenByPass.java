package com.fincomun.validadorcurp.dto.service.out;

import com.fincomun.validadorcurp.dto.common.SimpleResponse;
import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GeneraTokenByPass extends SimpleResponse {

    @Expose(serialize = true, deserialize = true)
    private String tokenGenerado;

}
