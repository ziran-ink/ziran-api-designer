package com.github.ziran_ink.ziran_api_designer.cmd;

public class HelloWorld extends Cmd {

	public static final String key_cmd = "hello-world";
	public static final String key_param_arg1 = "arg1";
	public static final String key_param_arg2 = "arg2";

	@Override
	public String getName() {
		return key_cmd;
	}

	@Override
	public void execute() throws Exception {
		String arg1 = getParamStringValue(key_param_arg1);
		String arg2 = getParamStringValue(key_param_arg2);
		System.out.println(String.format("hello-world: arg1=%s, arg2=%s", arg1, arg2));
	}
}
