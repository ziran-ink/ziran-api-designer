package com.github.ziran_ink.ziran_api_designer.cmd;

import com.alibaba.fastjson.JSONObject;

public abstract class Cmd {
	private JSONObject params;

	protected String getParamStringValue(String paramName) {
		return params.getString(paramName);
	}

	public abstract String getName();

	public abstract void execute() throws Exception;

	public JSONObject getParams() {
		return params;
	}

	public void setParams(JSONObject params) {
		this.params = params;
	}
}
