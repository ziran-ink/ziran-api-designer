package com.github.ziran_ink.ziran_api_designer.cmd;

import com.github.microprograms.micro_api_sdk.MicroApiSdk;
import com.github.microprograms.micro_api_sdk.model.ApiServerDefinition;
import com.github.microprograms.micro_api_sdk.utils.ApiDocumentForShowdocUtils;

public class UpdateApiDocumentForShowdoc extends Cmd {

	public static final String key_cmd = "update-api-document-for-showdoc";
	public static final String key_param_apiServerConfigFilePath = "apiServerConfigFilePath";

	@Override
	public String getName() {
		return key_cmd;
	}

	@Override
	public void execute() throws Exception {
		String apiServerConfigFilePath = getParamStringValue(key_param_apiServerConfigFilePath);
		ApiServerDefinition apiServerDefinition = MicroApiSdk.buildApiServerDefinition(apiServerConfigFilePath);
		ApiDocumentForShowdocUtils.update(apiServerDefinition);
	}
}
