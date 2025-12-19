package com.fincomun.validadorcurp.dto.controller.response;

import com.fincomun.validadorcurp.dto.common.SimpleResponse;
import com.fincomun.validadorcurp.dto.network.response.renapo.Registro;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class ConsultaCompletaCurpResponse extends SimpleResponse {

	private Registro expediente = new Registro();
	
}
