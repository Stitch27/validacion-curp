package com.fincomun.validadorcurp.service.implementation;

import com.fincomun.validadorcurp.dto.properties.CurpProperties;
import com.fincomun.validadorcurp.dto.service.out.GeneraTokenByPass;
import com.fincomun.validadorcurp.service.definition.IGeneraTokenByPassService;
import com.twocaptcha.TwoCaptcha;
import com.twocaptcha.captcha.ReCaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Slf4j
@Service("twoCaptchaService")
public class GeneraTokenByPassTwoCaptchaImp implements IGeneraTokenByPassService {




    private final TwoCaptcha twoCaptcha;
    private final CurpProperties curpProperties;

    @Autowired
    public GeneraTokenByPassTwoCaptchaImp(TwoCaptcha twoCaptcha, CurpProperties curpProperties) {
        this.twoCaptcha = twoCaptcha;
        this.curpProperties = curpProperties;
    }

    @Override
    public GeneraTokenByPass generaToken() {
        GeneraTokenByPass generaTokenByPass = new GeneraTokenByPass();
        try{
            log.info("Obtiendo token by pass ....");
            ReCaptcha reCaptcha = new ReCaptcha();
            reCaptcha.setSiteKey(curpProperties.gTokenRenapo);
            reCaptcha.setUrl(curpProperties.urlRenapo);
            reCaptcha.setAction("verify");
            twoCaptcha.solve(reCaptcha);
            String tokenObtenido = reCaptcha.getCode();
            if(tokenObtenido != null && !tokenObtenido.isEmpty()){
                generaTokenByPass.setTokenGenerado(tokenObtenido);
                generaTokenByPass.setCodigo(0);
                generaTokenByPass.setMensaje("El token se obtuvo correctamente");
            }else{
                generaTokenByPass.setTokenGenerado("-");
                generaTokenByPass.setCodigo(-2);
                generaTokenByPass.setMensaje("No se pudo conectar, favor de reintentar");
            }

        }catch (Exception e){
            log.error("Ocurrio un error al obtener el token");
            log.error(Arrays.toString(e.getStackTrace()));
            log.error(e.getMessage());
            generaTokenByPass.setTokenGenerado("-");
            generaTokenByPass.setCodigo(-3);
            generaTokenByPass.setMensaje("No se pudo conectar, favor de reintentar");
        }
        return generaTokenByPass;
    }
}
