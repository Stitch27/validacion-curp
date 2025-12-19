package com.fincomun.validadorcurp.controller;

import com.fincomun.validadorcurp.dto.common.SimpleResponse;
import com.fincomun.validadorcurp.dto.controller.request.ValidaCurpRequest;
import com.fincomun.validadorcurp.dto.controller.response.ConsultaCompletaCurpResponse;
import com.fincomun.validadorcurp.service.definition.IValidadorCurpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@Validated
public class ValidaCurpController {

    

    private IValidadorCurpService validaCurpService;

    @Autowired
    public ValidaCurpController(IValidadorCurpService validaCurpService) {
        this.validaCurpService = validaCurpService;
    }

    @Validated
    @PostMapping("validaCurp")
    public SimpleResponse validaCurp (@Valid @RequestBody ValidaCurpRequest curp){

        try{
            return validaCurpService.validaCurp(curp).getSimpleResponse();
        }catch (Exception e){
            log.error("Ocurrio un error procesar la petición");
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
        }
        return new SimpleResponse();
    }
    
    @Validated
    @PostMapping("validaCurpExpediente")
    public ConsultaCompletaCurpResponse validaCurpExpediente (@Valid @RequestBody ValidaCurpRequest curp){

        try{
            return validaCurpService.validaCurp(curp).getConsultaCompletaCurp();
        }catch (Exception e){
            log.error("Ocurrio un error procesar la petición");
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
        }
        return new ConsultaCompletaCurpResponse();
    }




}
