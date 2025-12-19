package com.fincomun.validadorcurp.dto.network.request.anticaptcha;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ToString
public class GetTaskResultRequest {

	public GetTaskResultRequest() {
	}

	public GetTaskResultRequest(String clientKey, int taskId) {
		this.clientKey = clientKey;
		this.taskId = taskId;
	}

	@NotEmpty
	@SerializedName("clientKey")
	private String clientKey;

	@NotNull
	@SerializedName("taskId")
	private int taskId;
}