package com.fincomun.validadorcurp.dto.properties;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ToString
public class CurpProperties {


    @NotNull @NotEmpty @URL
    public String urlApiAntiCaptcha;

    @NotNull @NotEmpty
    public String tokenApiAntiCaptcha;

    @NotNull @NotEmpty
    public String urlSiteApiAntiCaptcha;

    @NotNull @URL
    public String urlRenapo;

    @NotNull @NotEmpty
    public String gTokenRenapo;

    @NotNull @NotEmpty
    public String twoCaptchaKey;



}
