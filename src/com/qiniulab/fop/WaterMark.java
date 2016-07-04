package com.qiniulab.fop;

import com.qiniu.common.QiniuException;
import com.qiniu.processing.OperationManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import com.qiniulab.config.LabConfig;

public class WaterMark {
	public static void main(String[] args) {
		// 原文件信息
		String bucket = "if-pbl";
		String key = "mmm.png";

		// 持久化结果保存信息
		String saveBucket = bucket;
		// 该结果名称不可以和原文件名称相同
		String saveKey = "water.png";
		String saveEntry = saveBucket + ":" + saveKey;

		Auth auth = Auth.create(LabConfig.ACCESS_KEY, LabConfig.SECRET_KEY);
		OperationManager m = new OperationManager(auth);
		
		String imgUrl = UrlSafeBase64.encodeToString("http://o6oxmaj3v.bkt.clouddn.com/qiniu_logo.png");

		// 持久化动作信息
		String fops = "watermark/1/image/"+imgUrl + "/dissolve/40/gravity/Center|saveas/"
				+ UrlSafeBase64.encodeToString(saveEntry);
		// 私有队列名称，没有去后台创建
		String pipeline = "jemy";

		StringMap extraParams = new StringMap();
		// 持久化结果自动通知接收地址，可以不填
		extraParams.put("notifyURL", "http://www.abc.com/fop/notify");
		// 为了保障处理效率，请使用私有队列
		extraParams.put("pipeline", pipeline);
		try {
			String persistentId = m.pfop(bucket, key, fops, extraParams);
			// 然后根据persistentId查询结果
			System.out.println(persistentId);
		} catch (QiniuException e) {
			System.out.println(e.code());
			System.out.println(e.response.error);
		}
	}
}
