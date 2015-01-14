package com.qiniu.lab.service.download;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.TimeZone;

import org.apache.commons.codec.EncoderException;

import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.DigestAuth;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.url.URLEscape;

/**
 * 生成公开/私有空间的资源访问和下载链接
 * */
public class ResourceAccess {
	private Mac mac;
	private String domain;
	private String key;
	private String fops;

	public ResourceAccess(Mac mac, String domain, String key) {
		this.mac = mac;
		this.domain = domain;
		this.key = key;
		this.fops = null;
	}

	public ResourceAccess(Mac mac, String domain, String key, String fops) {
		this.mac = mac;
		this.domain = domain;
		this.key = key;
		this.fops = fops;
	}

	/**
	 * 生成私有空间的资源访问链接
	 * 
	 * @param tokenExpiredInSeconds
	 *            链接在多少秒后过期
	 * */
	public String createPrivateResourceUrl(int tokenExpiredInSeconds)
			throws UnsupportedEncodingException, AuthException,
			EncoderException {
		Calendar now = Calendar.getInstance(TimeZone
				.getTimeZone("Asia/Shanghai"));
		now.add(Calendar.SECOND, tokenExpiredInSeconds);
		long expire = now.getTimeInMillis() / 1000;
		StringBuilder urlToSign = new StringBuilder();
		urlToSign.append(this.domain).append("/")
				.append(URLEscape.escape(this.key));
		if (fops != null) {
			urlToSign.append("?").append(fops).append("&e=").append(expire);
		} else {
			urlToSign.append("?e=").append(expire);
		}
		String downToken = DigestAuth.sign(mac,
				urlToSign.toString().getBytes("utf-8"));
		String downUrl = urlToSign.append("&token=").append(downToken)
				.toString();
		return downUrl;
	}

	/**
	 * 生成私有空间的资源下载链接
	 * 
	 * @param tokenExpiredInSeconds
	 *            链接在多少秒后过期
	 * @param saveFilename
	 *            文件的保存名字
	 * */
	public String createPrivateDownloadUrl(int tokenExpiredInSeconds,
			String saveFilename) throws UnsupportedEncodingException,
			AuthException, EncoderException {
		Calendar now = Calendar.getInstance(TimeZone
				.getTimeZone("Asia/Shanghai"));
		now.add(Calendar.SECOND, tokenExpiredInSeconds);
		long expire = now.getTimeInMillis() / 1000;
		StringBuilder urlToSign = new StringBuilder();
		String urlEncodedSaveFileName = "";
		if (saveFilename != null && !saveFilename.isEmpty()) {
			urlEncodedSaveFileName = URLEscape.escape(saveFilename);
		}
		urlToSign.append(this.domain).append("/")
				.append(URLEscape.escape(this.key));
		if (fops != null) {
			urlToSign.append("?").append(fops).append("&attname=")
					.append(urlEncodedSaveFileName);
		} else {
			urlToSign.append("?attname=").append(urlEncodedSaveFileName);
		}
		urlToSign.append("&e=").append(expire);
		String downToken = DigestAuth.sign(mac,
				urlToSign.toString().getBytes("utf-8"));
		String downUrl = urlToSign.append("&token=").append(downToken)
				.toString();
		return downUrl;
	}

	/**
	 * 生成公开公开资源访问链接
	 * */
	public String createPublicResourceUrl() throws EncoderException {
		StringBuilder url = new StringBuilder();
		url.append(this.domain).append("/").append(URLEscape.escape(this.key));
		if (fops != null) {
			url.append("?").append(fops);
		}
		return url.toString();
	}

	/**
	 * 生成公开空间资源下载链接
	 * 
	 * @param saveFilename
	 *            文件保存的名字
	 * */
	public String createPublicDownloadUrl(String saveFilename)
			throws EncoderException {
		String urlEncodedSaveFileName = "";
		if (saveFilename != null && !saveFilename.isEmpty()) {
			urlEncodedSaveFileName = URLEscape.escape(saveFilename);
		}
		StringBuilder url = new StringBuilder();
		url.append(this.domain).append("/").append(URLEscape.escape(this.key));
		if (fops != null) {
			url.append("?").append(fops).append("&attname=")
					.append(urlEncodedSaveFileName);
		} else {
			url.append("?attname=").append(urlEncodedSaveFileName);
		}
		return url.toString();
	}
}
