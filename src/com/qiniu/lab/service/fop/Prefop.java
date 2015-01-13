package com.qiniu.lab.service.fop;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.qiniu.api.net.CallRet;
import com.qiniu.api.net.Client;

/**
 * 查询持久化处理结果
 * 文档：http://developer.qiniu.com/docs/v6/api/reference/fop/pfop/prefop.html
 * */
public class Prefop {
	public class FopResult {
		public String id;
		public int code;
		public String desc;
		public List<FopCmdResult> items;

		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("Id:").append(id).append("\r\n");
			sb.append("Code:").append(code).append("\r\n");
			sb.append("Desc:").append(desc).append("\r\n");
			return sb.toString();
		}
	}

	public class FopCmdResult {
		public String cmd;
		public int code;
		public String desc;
		public String error;
		public String hash;
		public String key;

		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("Cmd:").append(cmd).append("\r\n");
			sb.append("Code:").append(code).append("\r\n");
			sb.append("Desc:").append(desc).append("\r\n");
			if (error != null) {
				sb.append("Error:").append(error).append("\r\n");
			} else {
				sb.append("Hash:").append(hash).append("\r\n");
				sb.append("Key:").append(key).append("\r\n");
			}
			return sb.toString();
		}
	}

	public FopResult prefop(String persistentId) throws Exception {
		FopResult fopResult = null;
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("id", persistentId));
		Client client = new Client();
		CallRet call = client.call("http://api.qiniu.com/status/get/prefop",
				nvps);
		if (call.statusCode / 100 == 2) {
			JSONObject resp = new JSONObject(call.response);
			fopResult = new FopResult();
			fopResult.id = resp.getString("id");
			fopResult.code = resp.getInt("code");
			fopResult.desc = resp.getString("desc");
			fopResult.items = new ArrayList<FopCmdResult>();
			JSONArray itemObjects = resp.getJSONArray("items");
			for (int i = 0; i < itemObjects.length(); i++) {
				JSONObject itemObject = itemObjects.getJSONObject(i);
				FopCmdResult fopCmdResult = new FopCmdResult();
				fopCmdResult.cmd = itemObject.getString("cmd");
				fopCmdResult.code = itemObject.getInt("code");
				fopCmdResult.desc = itemObject.getString("desc");
				if (itemObject.has("error")) {
					fopCmdResult.error = itemObject.getString("error");
				} else {
					fopCmdResult.hash = itemObject.getString("hash");
					fopCmdResult.key = itemObject.getString("key");
				}
				fopResult.items.add(fopCmdResult);
			}
		} else {
			throw call.exception;
		}
		return fopResult;
	}
}
