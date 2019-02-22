package com.github.ziran_ink.ziran_api_designer;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.github.ziran_ink.ziran_api_designer.cmd.Cmd;
import com.github.ziran_ink.ziran_api_designer.cmd.GenScratchVmExtension;
import com.github.ziran_ink.ziran_api_designer.cmd.HelloWorld;
import com.github.ziran_ink.ziran_api_designer.cmd.UpdateApiDocumentForShowdoc;
import com.github.ziran_ink.ziran_api_designer.cmd.UpdateApiModel;

public class Main {

	private static final Map<String, Cmd> map = new HashMap<>();
	static {
		registerCmd(new GenScratchVmExtension());
		registerCmd(new HelloWorld());
		registerCmd(new UpdateApiDocumentForShowdoc());
		registerCmd(new UpdateApiModel());
	}

	private static void registerCmd(Cmd cmd) {
		map.put(cmd.getName(), cmd);
	}

	private static Cmd getCmd(String cmdName) {
		return map.get(cmdName);
	}

	private static JSONObject buildParams(String[] args) {
		JSONObject params = new JSONObject();
		for (int i = 1; i < args.length; i++) {
			String[] pair = args[i].replaceFirst("^-D", "").split("=");
			params.put(pair[0], pair[1]);
		}
		return params;
	}

	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			System.err.println("缺少参数");
			return;
		}
		String cmdName = args[0];
		Cmd cmd = getCmd(cmdName);
		if (cmd == null) {
			System.err.println("找不到CMD");
			return;
		}
		cmd.setParams(buildParams(args));
		cmd.execute();
	}
}
