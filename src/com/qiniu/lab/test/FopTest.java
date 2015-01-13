package com.qiniu.lab.test;

import com.qiniu.api.auth.digest.Mac;
import com.qiniu.lab.config.LabConfig;
import com.qiniu.lab.service.fop.Pfop;
import com.qiniu.lab.service.fop.Prefop;
import com.qiniu.lab.service.fop.VFrameFopCmd;

public class FopTest {
	public static void testPrefop() throws Exception {
		String persistentId = "54af3b157823de40689d647f";
		Prefop.FopResult fopResult = new Prefop().prefop(persistentId);
		System.out.println(fopResult.toString());
		for (Prefop.FopCmdResult r : fopResult.items) {
			System.out.println(r.toString());
		}
	}

	public static void testVframe() {
		Mac mac = new Mac(LabConfig.ACCESS_KEY, LabConfig.SECRET_KEY);
		String bucket = "if-pbl";
		String key = "qiniu.mp4";
		String format = "jpg";
		int offset = 0;
		Pfop pfop = new Pfop(mac, bucket, key);
		VFrameFopCmd fopCmd = new VFrameFopCmd(format, offset);
		String persistentId;
		try {
			// add fop cmd
			pfop.addFopCmd(fopCmd);
			persistentId = pfop.pfop();
			System.out.println(persistentId);
			// set saveas name
			String saveKey = "qiniu_vframe_01.jpg";
			fopCmd.setSaveEntry(bucket, saveKey);
			persistentId = pfop.pfop();
			System.out.println(persistentId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testMultiVframe() {
		Mac mac = new Mac(LabConfig.ACCESS_KEY, LabConfig.SECRET_KEY);
		String bucket = "if-pbl";
		String key = "qiniu.mp4";
		String format = "jpg";
		Pfop pfop = new Pfop(mac, bucket, key);
		for (int i = 0; i < 10; i++) {
			int offset = i;
			String saveKey = "qiniu_vframe_" + i + ".jpg";
			VFrameFopCmd fopCmd = new VFrameFopCmd(format, offset, bucket, saveKey);
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

	public static void main(String[] args) throws Exception {
		// testPrefop();
		// testVframe();
		testMultiVframe();
	}

}
