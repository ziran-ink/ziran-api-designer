package com.github.ziran_ink.ziran_api_designer.cmd;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.github.microprograms.micro_api_runtime.enums.MicroApiReserveResponseCodeEnum;
import com.github.microprograms.micro_api_sdk.MicroApiSdk;
import com.github.microprograms.micro_api_sdk.model.ApiDefinition;
import com.github.microprograms.micro_api_sdk.model.ApiServerDefinition;
import com.github.microprograms.micro_nested_data_model_sdk.model.NestedEntityDefinition;
import com.github.microprograms.micro_nested_data_model_sdk.model.NestedFieldDefinition;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateNotFoundException;

public class GenScratchVmExtension extends Cmd {
	public static final String default_template_name = "scratch-vm-extension.js.ftl";

	public static final String key_cmd = "gen-scratch-vm-extension";
	public static final String key_param_templateFilePath = "templateFilePath";
	public static final String key_param_apiServerConfigFilePath = "apiServerConfigFilePath";
	public static final String key_param_scratchVmExtensionFilePath = "scratchVmExtensionFilePath";

	@Override
	public String getName() {
		return key_cmd;
	}

	@Override
	public void execute() throws Exception {
		String apiServerConfigFilePath = getParamStringValue(key_param_apiServerConfigFilePath);
		String scratchVmExtensionFilePath = getParamStringValue(key_param_scratchVmExtensionFilePath);
		ApiServerDefinition apiServerDefinition = MicroApiSdk.buildApiServerDefinition(apiServerConfigFilePath);
		_appendCommonResponseFieldDefinitions(apiServerDefinition);
		getTemplate().process(apiServerDefinition, new FileWriter(scratchVmExtensionFilePath));
	}

	private Template getTemplate()
			throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException {
		String templateFilePath = getParamStringValue(key_param_templateFilePath);
		Configuration conf = new Configuration(Configuration.VERSION_2_3_28);
		if (StringUtils.isBlank(templateFilePath)) {
			conf.setClassForTemplateLoading(getClass(), "/");
			return conf.getTemplate(default_template_name);
		} else {
			File templateFile = new File(templateFilePath);
			conf.setDirectoryForTemplateLoading(templateFile.getParentFile());
			return conf.getTemplate(templateFile.getName());
		}
	}

	private static void _appendCommonResponseFieldDefinitions(ApiServerDefinition apiServerDefinition)
			throws IOException {
		List<ApiDefinition> apiDefinitions = apiServerDefinition.getApiDefinitions();
		for (int i = 0; i < apiDefinitions.size(); i++) {
			ApiDefinition apiDefinition = apiDefinitions.get(i);
			_appendCommonResponseFieldDefinitions(apiDefinition);
		}
	}

	private static void _appendCommonResponseFieldDefinitions(ApiDefinition apiDefinition) {
		List<NestedFieldDefinition> commonFieldDefinitions = new ArrayList<>();
//		commonFieldDefinitions.add(_buildFieldDefinition("错误码(0正常,非0错误)", "code", "Integer", true,
//				MicroApiReserveResponseCodeEnum.success.getCode()));
		commonFieldDefinitions.add(_buildFieldDefinition("错误提示", "message", "String", true,
				MicroApiReserveResponseCodeEnum.success.getMessage()));
		if (apiDefinition.getResponseDefinition() == null) {
			NestedEntityDefinition responseDefinition = new NestedEntityDefinition();
			responseDefinition.setFieldDefinitions(commonFieldDefinitions);
			apiDefinition.setResponseDefinition(responseDefinition);
		} else {
			apiDefinition.getResponseDefinition().getFieldDefinitions().addAll(0, commonFieldDefinitions);
		}
	}

	private static NestedFieldDefinition _buildFieldDefinition(String comment, String name, String javaType,
			boolean required, Object example) {
		NestedFieldDefinition fieldDefinition = new NestedFieldDefinition();
		fieldDefinition.setComment(comment);
		fieldDefinition.setJavaType(javaType);
		fieldDefinition.setName(name);
		fieldDefinition.setRequired(required);
		fieldDefinition.setExample(example);
		return fieldDefinition;
	}
}
