package com.qiniu.lab.service.rsf;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.rsf.ListItem;
import com.qiniu.api.rsf.ListPrefixRet;
import com.qiniu.api.rsf.RSFClient;

public class ListBucket {
	private String saveFile;
	private Mac mac;

	public ListBucket(Mac mac, String saveFile) {
		this.mac = mac;
		this.saveFile = saveFile;
	}

	public void listAll(String bucket) throws Exception {
		this.list(bucket, "");
	}

	public void list(String bucket, String prefix) throws Exception {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(this.saveFile)));
		RSFClient rsfClient = new RSFClient(mac);
		String marker = "";
		boolean run = true;
		ListPrefixRet ret = null;
		int maxRetryTimes = 5;
		int retryTime = 0;
		while (run) {
			ret = rsfClient.listPrifix(bucket, prefix, marker, 0);
			int code = ret.getStatusCode();
			if (code == 200) {
				retryTime = 0;
				marker = ret.marker;
				if (marker == null || marker.isEmpty()) {
					run = false;
				}
				this.record(ret.results, bw);
			} else {
				// retry
				retryTime++;
				if (retryTime < maxRetryTimes) {
					continue;
				} else {
					run = false;
					throw new Exception("list failed too many times for marker"
							+ marker);
				}
			}
		}
		bw.flush();
		bw.close();
	}

	private void record(List<ListItem> results, BufferedWriter bw)
			throws IOException {
		for (ListItem item : results) {
			bw.write(item.key);
			bw.write('\t');
			bw.write(item.fsize + "");
			bw.write('\t');
			bw.write(item.hash);
			bw.write('\t');
			bw.write(item.putTime + "");
			bw.write('\t');
			bw.write(item.mimeType);
			if (item.endUser != null) {
				bw.write('\t');
				bw.write(item.endUser);
			}
			bw.write("\r\n");
		}
	}
}
