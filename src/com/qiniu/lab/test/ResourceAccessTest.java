package com.qiniu.lab.test;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.EncoderException;

import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.lab.config.LabConfig;
import com.qiniu.lab.service.download.ResourceAccess;

public class ResourceAccessTest {

	public static void main(String[] args) throws UnsupportedEncodingException,
			AuthException, EncoderException {
		// 私有空间
		Mac mac = new Mac(LabConfig.ACCESS_KEY, LabConfig.SECRET_KEY);
		String resUrl = new ResourceAccess(mac,
				LabConfig.PRIVATE_BUCKET_DOMAIN, "mp444.mp4")
				.createPrivateResourceUrl(3600);
		System.out.println(resUrl);
		String downUrl = new ResourceAccess(mac,
				LabConfig.PRIVATE_BUCKET_DOMAIN, "mp444.mp4")
				.createPrivateDownloadUrl(3600, null);
		System.out.println(downUrl);
		// 公开空间
		resUrl = new ResourceAccess(null, LabConfig.PUBLIC_BUCKET_DOMAIN,
				"bdlogo.png").createPublicResourceUrl();
		System.out.println(resUrl);
		downUrl = new ResourceAccess(null, LabConfig.PUBLIC_BUCKET_DOMAIN,
				"bdlogo.png").createPublicDownloadUrl(null);
		System.out.println(downUrl);
	}

}
