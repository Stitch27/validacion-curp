package com.fincomun.validadorcurp.dto.network.response.anticaptcha;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Solution{

	@SerializedName("gRecaptchaResponse")
	private String gRecaptchaResponse;
}