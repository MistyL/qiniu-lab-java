package com.qiniu.lab.example;

import org.json.JSONException;

import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.net.EncodeUtils;
import com.qiniu.lab.config.LabConfig;
import com.qiniu.lab.service.error.QiniuError;
import com.qiniu.lab.service.fop.Pfop;

public class JoinPictures {
	public static void main(String args[]) throws JSONException, QiniuError {
		String bucket = "qiniulab-public";
		String bgImageKey = "bg.png";
		String movie1 = "http://7rf31q.com1.z0.glb.clouddn.com/movie1.png";
		String movie2 = "http://7rf31q.com1.z0.glb.clouddn.com/movie2.png";
		String movie3 = "http://7rf31q.com1.z0.glb.clouddn.com/movie3.png";

		String watermark1Cmd = "watermark/1/gravity/NorthWest/image/"
				+ EncodeUtils.urlsafeEncode(movie1);
		String watermark2Cmd = "watermark/1/gravity/West/image/"
				+ EncodeUtils.urlsafeEncode(movie2);
		String watermark3Cmd = "watermark/1/gravity/SouthWest/image/"
				+ EncodeUtils.urlsafeEncode(movie3);
		String cropCmd = "imageMogr2/gravity/NorthWest/crop/!300x900";

		String saveBucket = "if-pbl";
		String saveKey = "joined_pic.jpg";

		StringBuilder fops = new StringBuilder();
		fops.append(watermark1Cmd);
		fops.append("|");
		fops.append(watermark2Cmd);
		fops.append("|");
		fops.append(watermark3Cmd);
		fops.append("|");
		fops.append(cropCmd);
		fops.append("|");
		fops.append("saveas/").append(
				EncodeUtils.urlsafeEncode(saveBucket + ":" + saveKey));

		Mac mac = new Mac(LabConfig.ACCESS_KEY, LabConfig.SECRET_KEY);
		Pfop pfop = new Pfop(mac, bucket, bgImageKey);
		pfop.setFops(fops.toString());
		String pid = pfop.pfop();
		System.out.println(pid);
	}
}
