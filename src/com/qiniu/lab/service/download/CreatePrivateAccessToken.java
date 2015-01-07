package com.qiniu.lab.service.download;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.TimeZone;

import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.DigestAuth;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.lab.config.LabConfig;

public class CreatePrivateAccessToken {
	private String domain;
	private String key;

	public CreatePrivateAccessToken(String domain, String key) {
		this.domain = domain;
		this.key = key;
	}

	public String generatePrivateDownloadUrl(int tokenExpireInSeconds)
			throws UnsupportedEncodingException, AuthException {
		Mac mac = new Mac(LabConfig.ACCESS_KEY, LabConfig.SECRET_KEY);
		Calendar now = Calendar.getInstance(TimeZone
				.getTimeZone("Asia/Shanghai"));
		now.add(Calendar.SECOND, tokenExpireInSeconds);
		long expire = now.getTimeInMillis() / 1000;
		StringBuilder urlToSign = new StringBuilder();
		urlToSign.append(this.domain).append("/").append(this.key)
				.append("?e=").append(expire);
		String downToken = DigestAuth.sign(mac,
				urlToSign.toString().getBytes("utf-8"));
		String downUrl = urlToSign.append("&token=").append(downToken)
				.toString();
		return downUrl;
	}

	public static void main(String[] args) throws UnsupportedEncodingException,
			AuthException {
		String downUrl = new CreatePrivateAccessToken(
				LabConfig.PRIVATE_BUCKET_DOMAIN, "mp444.mp4")
				.generatePrivateDownloadUrl(3600);
		System.out.println(downUrl);
	}

}
