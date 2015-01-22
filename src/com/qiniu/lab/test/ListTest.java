package com.qiniu.lab.test;

import com.qiniu.api.auth.digest.Mac;
import com.qiniu.lab.config.LabConfig;
import com.qiniu.lab.service.rsf.ListBucket;

public class ListTest {

	public static void main(String[] args) throws Exception {
		Mac mac = new Mac(LabConfig.ACCESS_KEY, LabConfig.SECRET_KEY);
		String file = "/Users/jemy/Temp7/qdisk.list.txt";
		String bucket = "qdisk";
		ListBucket lister = new ListBucket(mac, file);
		lister.listAll(bucket);

	}

}
