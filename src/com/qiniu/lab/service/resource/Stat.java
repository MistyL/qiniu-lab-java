package com.qiniu.lab.service.resource;

import org.json.JSONException;
import org.json.JSONObject;

import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.rs.Entry;
import com.qiniu.api.rs.RSClient;
import com.qiniu.lab.config.LabConfig;

/**
 * 使用Stat接口查询文件的基本信息
 * 文档：http://developer.qiniu.com/docs/v6/api/reference/rs/stat.html
 */
public class Stat {

	public static void main(String[] args) {
		Mac mac = new Mac(LabConfig.ACCESS_KEY, LabConfig.SECRET_KEY);
		RSClient client = new RSClient(mac);
		String bucket = "if-pbl";
		String key = "qiniu.mp4";
		Entry entry = client.stat(bucket, key);
		if (entry.ok()) {
			System.out
					.printf("Fsize: %d\r\nHash: %s\r\nKey: %s\r\nMimeType: %s\r\nPutTime: %d\r\n",
							entry.getFsize(), entry.getHash(), entry.getHash(),
							entry.getMimeType(), entry.getPutTime());
		} else {
			if (entry.exception != null) {
				// System.err.println(entry.exception.toString());
			} else {
				// parse error
				JSONObject error;
				try {
					error = new JSONObject(entry.response);
					String message = error.getString("error");
					System.err.println(message);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
