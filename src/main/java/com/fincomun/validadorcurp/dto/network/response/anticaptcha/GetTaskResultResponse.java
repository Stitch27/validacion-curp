package com.fincomun.validadorcurp.dto.network.response.anticaptcha;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GetTaskResultResponse{

	@SerializedName("cost")
	private String cost;

	@SerializedName("solution")
	private Solution solution;

	@SerializedName("createTime")
	private int createTime;

	@SerializedName("solveCount")
	private int solveCount;

	@SerializedName("ip")
	private String ip;

	@SerializedName("errorId")
	private int errorId;

	@SerializedName("endTime")
	private int endTime;

	@SerializedName("status")
	private String status;
}