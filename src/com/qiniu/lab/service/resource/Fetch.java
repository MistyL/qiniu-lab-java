package com.qiniu.lab.service.resource;

import com.qiniu.api.auth.DigestAuthClient;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.net.CallRet;
import com.qiniu.api.net.EncodeUtils;
import com.qiniu.lab.service.error.QiniuError;

/**
 * 使用七牛的Fetch接口抓取网络上的公开资源并保持在空间中。
 * 文档：http://developer.qiniu.com/docs/v6/api/reference/rs/fetch.html
 * */
public class Fetch {
	public boolean fetch(Mac mac, String remoteResUrl, String bucket, String key)
			throws QiniuError {
		boolean success = false;
		DigestAuthClient digestAuthClient = new DigestAuthClient(mac);
		StringBuilder fetchUrl = new StringBuilder();
		String encodedUrl = EncodeUtils.urlsafeEncode(remoteResUrl);
		String encodedEntry = EncodeUtils.urlsafeEncode(bucket + ":" + key);
		fetchUrl.append("http://iovip.qbox.me/fetch/").append(encodedUrl)
				.append("/to/").append(encodedEntry);
		CallRet ret = digestAuthClient.call(fetchUrl.toString());
		// Success is 200
		if (ret.statusCode == 200) {
			success = true;
		} else {
			throw new QiniuError(ret.response, ret.exception);
		}
		return success;
	}
}
