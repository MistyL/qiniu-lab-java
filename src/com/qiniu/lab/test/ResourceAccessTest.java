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
		String fops = "imageView2/0/w/100/format/jpg";
		// 私有空间
		Mac mac = new Mac(LabConfig.ACCESS_KEY, LabConfig.SECRET_KEY);
		String resUrl = new ResourceAccess(mac,
				LabConfig.PRIVATE_BUCKET_DOMAIN, "bdlogo.png")
				.createPrivateResourceUrl(3600);
		System.out.println(resUrl);
		String downUrl = new ResourceAccess(mac,
				LabConfig.PRIVATE_BUCKET_DOMAIN, "bdlogo.png")
				.createPrivateDownloadUrl(3600, null);
		System.out.println(downUrl);
		// 私有空间，带fop
		resUrl = new ResourceAccess(mac, LabConfig.PRIVATE_BUCKET_DOMAIN,
				"bdlogo.png", fops).createPrivateResourceUrl(3600);
		System.out.println(resUrl);
		downUrl = new ResourceAccess(mac, LabConfig.PRIVATE_BUCKET_DOMAIN,
				"bdlogo.png", fops).createPrivateDownloadUrl(3600, null);
		System.out.println(downUrl);
		// 公开空间
		resUrl = new ResourceAccess(null, LabConfig.PUBLIC_BUCKET_DOMAIN,
				"bdlogo.png").createPublicResourceUrl();
		System.out.println(resUrl);
		downUrl = new ResourceAccess(null, LabConfig.PUBLIC_BUCKET_DOMAIN,
				"bdlogo.png").createPublicDownloadUrl(null);
		System.out.println(downUrl);
		resUrl = new ResourceAccess(null, LabConfig.PUBLIC_BUCKET_DOMAIN,
				"bdlogo.png", fops).createPublicResourceUrl();
		System.out.println(resUrl);
		// 公开空间，带fop
		downUrl = new ResourceAccess(null, LabConfig.PUBLIC_BUCKET_DOMAIN,
				"bdlogo.png", fops).createPublicDownloadUrl(null);
		System.out.println(downUrl);
	}

}
