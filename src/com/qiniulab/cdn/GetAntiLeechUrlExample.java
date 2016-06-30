package com.qiniulab.cdn;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;

public class GetAntiLeechUrlExample {

	public static void main(String[] args) {
		// cdn 配置的基于时间戳防盗链的加密字符串，cdn 配置完成后会得到
		String encryptKey = "";
		// 待加密链接
		String urlToSign = "http://img.example.com/cloud/0000111944686aeb5b228c58721cd93a/1.jpg";
		// 有效期
		int duration = 3600;

		try {
			String signedUrl = CdnAntiLeech.getAntiLeechAccessUrlBasedOnTimestamp(urlToSign, encryptKey, duration);
			System.out.println(signedUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

}
