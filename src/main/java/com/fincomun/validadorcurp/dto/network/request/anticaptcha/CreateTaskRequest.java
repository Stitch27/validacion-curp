package com.fincomun.validadorcurp.dto.network.request.anticaptcha;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ToString
public class CreateTaskRequest{

	@NotNull
	@SerializedName("task")
	private Task task = new Task();

	@NotEmpty
	@SerializedName("clientKey")
	private String clientKey;
}