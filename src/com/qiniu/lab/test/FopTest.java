package com.qiniu.lab.test;

import com.qiniu.api.auth.digest.Mac;
import com.qiniu.lab.config.LabConfig;
import com.qiniu.lab.service.fop.Pfop;
import com.qiniu.lab.service.fop.Prefop;
import com.qiniu.lab.service.fop.VFrameFopCmd;
import com.qiniu.lab.service.fop.VSampleFopCmd;

public class FopTest {
	// 测试fop结果查询
	public static void testPrefop() throws Exception {
		String persistentId = "54af3b157823de40689d647f";
		Prefop.FopResult fopResult = new Prefop().prefop(persistentId);
		System.out.println(fopResult.toString());
		for (Prefop.FopCmdResult r : fopResult.items) {
			System.out.println(r.toString());
		}
	}

	// 测试视频截图，并自定义结果名称
	public static void testVFrame() {
		Mac mac = new Mac(LabConfig.ACCESS_KEY, LabConfig.SECRET_KEY);
		String bucket = "if-pbl";
		String key = "qiniu.mp4";
		String format = "jpg";
		int offset = 0;
		Pfop pfop = new Pfop(mac, bucket, key);
		VFrameFopCmd fopCmd = new VFrameFopCmd(format, offset);
		String persistentId;
		try {
			pfop.addFopCmd(fopCmd);
			persistentId = pfop.pfop();
			System.out.println(persistentId);
			// 自定义截图名称
			String saveKey = "qiniu_vframe_01.jpg";
			fopCmd.setSaveEntry(bucket, saveKey);
			persistentId = pfop.pfop();
			System.out.println(persistentId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 测试视频截多个图
	public static void testMultiVFrame() {
		Mac mac = new Mac(LabConfig.ACCESS_KEY, LabConfig.SECRET_KEY);
		String bucket = "if-pbl";
		String key = "qiniu.mp4";
		String format = "jpg";
		Pfop pfop = new Pfop(mac, bucket, key);
		for (int i = 0; i < 10; i++) {
			int offset = i;
			String saveKey = "qiniu_vframe_" + i + ".jpg";
			VFrameFopCmd fopCmd = new VFrameFopCmd(format, offset, bucket,
					saveKey);
			pfop.addFopCmd(fopCmd);
		}
		String persistentId = null;
		try {
			persistentId = pfop.pfop();
			System.out.println(persistentId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 测试视频截图和取样功能
	public static void testVFrameAndVSample() {
		Mac mac = new Mac(LabConfig.ACCESS_KEY, LabConfig.SECRET_KEY);
		String bucket = "if-pbl";
		String key = "qiniu.mp4";
		String format = "jpg";
		Pfop pfop = new Pfop(mac, bucket, key);
		int offset = 10;
		VFrameFopCmd vframeFop = new VFrameFopCmd(format, offset, bucket,
				"qiniu_vframe_offset_10.jpg");
		int startSecond = 0;
		int duration = 180;
		String pattern = "qiniu_vsample_480x480_i10_$(count).jpg";
		VSampleFopCmd vsampleFop = new VSampleFopCmd(format, startSecond,
				duration, pattern);
		vsampleFop.setRotate(90);
		vsampleFop.setResolution("480x480");
		vsampleFop.setInterval(10);
		pfop.addFopCmd(vsampleFop);
		pfop.addFopCmd(vframeFop);
		String persistentId = null;
		try {
			persistentId = pfop.pfop();
			System.out.println(persistentId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		// testPrefop();
		// testVFrame();
		// testMultiVFrame();
		testVFrameAndVSample();
	}

}
