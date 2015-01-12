package com.qiniu.lab.service.resource;

import com.qiniu.api.auth.DigestAuthClient;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.net.CallRet;
import com.qiniu.api.net.EncodeUtils;
import com.qiniu.lab.config.LabConfig;

/**
 * 使用七牛的Fetch接口抓取网络上的公开资源并保持在空间中。
 * 文档：http://developer.qiniu.com/docs/v6/api/reference/rs/fetch.html
 * */
public class Fetch {

	public static void fetch(String remoteResUrl, String bucket, String key) {
		Mac mac = new Mac(LabConfig.ACCESS_KEY, LabConfig.SECRET_KEY);
		DigestAuthClient digestAuthClient = new DigestAuthClient(mac);
		StringBuilder fetchUrl = new StringBuilder();
		String encodedUrl = EncodeUtils.urlsafeEncode(remoteResUrl);
		String encodedEntry = EncodeUtils.urlsafeEncode(bucket + ":" + key);
		fetchUrl.append("http://iovip.qbox.me/fetch/").append(encodedUrl)
				.append("/to/").append(encodedEntry);
		CallRet ret = digestAuthClient.call(fetchUrl.toString());
		// Success is 200
		System.out.println(ret.statusCode);
	}

	public static void main(String[] args) {
		fetch("http://www.baidu.com/img/bdlogo.png", "if-pbl", "bdlogo.png");
	}
}
