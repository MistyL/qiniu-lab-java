package com.qiniu.lab.test;

import org.json.JSONException;

import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.rs.Entry;
import com.qiniu.lab.config.LabConfig;
import com.qiniu.lab.service.error.QiniuError;
import com.qiniu.lab.service.resource.Fetch;
import com.qiniu.lab.service.resource.Stat;

public class ResourceManageTest {
	/**
	 * 测试fetch
	 * */
	public static void testFetch() {
		Mac mac = new Mac(LabConfig.ACCESS_KEY, LabConfig.SECRET_KEY);
		try {
			new Fetch().fetch(mac, "http://www.baidu.com/img/bdlogo.png",
					"if-pbl", "bdlogo.png");
		} catch (QiniuError e) {
			if (e.getCause() != null) {
				e.getCause().printStackTrace();
			} else {
				try {
					System.err.println(e.getError());
				} catch (JSONException e1) {
				}
			}
		}
	}

	/**
	 * 测试stat
	 * */
	public static void testStat() {
		Mac mac = new Mac(LabConfig.ACCESS_KEY, LabConfig.SECRET_KEY);
		String bucket = "if-pbl";
		String key = "qiniu.mp4";
		try {
			Entry entry = new Stat().stat(mac, bucket, key);
			System.out.println(entry.toString());
		} catch (QiniuError e) {
			if (e.getCause() != null) {
				e.getCause().printStackTrace();
			} else {
				try {
					System.err.println(e.getError());
				} catch (JSONException e1) {
				}
			}
		}
	}

	public static void main(String[] args) {
		testFetch();
		testStat();
	}
}
