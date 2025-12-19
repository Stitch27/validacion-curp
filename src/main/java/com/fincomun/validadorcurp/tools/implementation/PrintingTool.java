package com.fincomun.validadorcurp.tools.implementation;


import com.fincomun.validadorcurp.tools.definition.IObjectConvertor;
import com.fincomun.validadorcurp.tools.definition.IPrintingTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.security.SecureRandom;

@Slf4j
@Validated
@Component
public class PrintingTool implements IPrintingTool {

    private IObjectConvertor objectConvertor;


    @Autowired
    public PrintingTool( @Qualifier("GsonObjectConvertorImp") IObjectConvertor objectConvertor) {
        this.objectConvertor = objectConvertor;
    }

    @Override
    public <T> String prinObjectAsJson(T t) {
        return objectConvertor.objectToJsonConverter(t);
    }

    @Override
    public String randomString() {
        return String.valueOf(new SecureRandom().nextInt(10000000));
    }

    @Override
    public String getPID() {
        return "[" + randomString() + "]";
    }
}