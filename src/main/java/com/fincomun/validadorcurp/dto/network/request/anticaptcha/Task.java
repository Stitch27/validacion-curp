package com.fincomun.validadorcurp.dto.network.request.anticaptcha;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Data
@ToString
public class Task{

	@NotEmpty
	@SerializedName("websiteURL")
	private String websiteURL;

	@NotEmpty
	@SerializedName("websiteKey")
	private String websiteKey;

	@NotEmpty
	@SerializedName("type")
	private String type;
}