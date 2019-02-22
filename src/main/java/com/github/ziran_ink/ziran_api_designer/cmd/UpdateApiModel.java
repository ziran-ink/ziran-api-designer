package com.github.ziran_ink.ziran_api_designer.cmd;

import com.github.microprograms.micro_api_sdk.MicroApiSdk;
import com.github.microprograms.micro_api_sdk.model.ApiServerDefinition;

public class UpdateApiModel extends Cmd {

	public static final String key_cmd = "update-api-model";
	public static final String key_param_apiServerConfigFilePath = "apiServerConfigFilePath";
	public static final String key_param_srcFolder = "srcFolder"; // defaultValue = "src/main/java"

	@Override
	public String getName() {
		return key_cmd;
	}

	@Override
	public void execute() throws Exception {
		String apiServerConfigFilePath = getParamStringValue(key_param_apiServerConfigFilePath);
		String srcFolder = getParamStringValue(key_param_srcFolder);
		ApiServerDefinition apiServerDefinition = MicroApiSdk.buildApiServerDefinition(apiServerConfigFilePath);
		MicroApiSdk.deletePlainEntityJavaSourceFiles(srcFolder, apiServerDefinition);
		MicroApiSdk.updatePlainEntityJavaSourceFiles(srcFolder, apiServerDefinition);
		MicroApiSdk.updateErrorCodeJavaFile(srcFolder, apiServerDefinition);
	}
}
