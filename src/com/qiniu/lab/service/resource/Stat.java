package com.qiniu.lab.service.resource;

import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.rs.Entry;
import com.qiniu.api.rs.RSClient;
import com.qiniu.lab.service.error.QiniuError;

/**
 * 使用Stat接口查询文件的基本信息
 * 文档：http://developer.qiniu.com/docs/v6/api/reference/rs/stat.html
 */
public class Stat {
	public Entry stat(Mac mac, String bucket, String key) throws QiniuError {
		RSClient client = new RSClient(mac);
		Entry entry = null;
		entry = client.stat(bucket, key);
		if (entry.ok()) {
			return entry;
		} else {
			throw new QiniuError(entry.response, entry.exception);
		}
	}
}
