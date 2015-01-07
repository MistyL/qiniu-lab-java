package com.qiniu.lab.service.resource;

import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.rs.Entry;
import com.qiniu.api.rs.RSClient;
import com.qiniu.lab.config.LabConfig;

public class Stat {

	public static void main(String[] args) {
		Mac mac = new Mac(LabConfig.ACCESS_KEY, LabConfig.SECRET_KEY);
		RSClient client = new RSClient(mac);
		Entry entry=client.stat("if-pbl", "中国人.mp4");
		System.err.println(entry.toString());
	}

}
