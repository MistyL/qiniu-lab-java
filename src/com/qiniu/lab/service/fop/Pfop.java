package com.qiniu.lab.service.fop;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.qiniu.api.auth.DigestAuthClient;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.net.CallRet;
import com.qiniu.lab.service.error.QiniuError;

/**
 * 手动触发数据持久化处理
 * 文档：http://developer.qiniu.com/docs/v6/api/reference/fop/pfop/pfop.html
 * */
public class Pfop {
	private String bucket;
	private String key;
	private List<FopCmd> fopCmdList;
	private String notifyURL;
	private boolean force;
	private String pipeline;
	private Mac mac;

	public Pfop(Mac mac, String bucket, String key) {
		this.mac = mac;
		this.bucket = bucket;
		this.key = key;
		this.notifyURL = null;
		this.force = false;
		this.pipeline = "";
		this.fopCmdList = new ArrayList<FopCmd>();
	}

	public String getBucket() {
		return bucket;
	}

	public void setBucket(String bucket) {
		this.bucket = bucket;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void addFopCmd(FopCmd fopCmd) {
		this.fopCmdList.add(fopCmd);
	}

	public void removeFopCmd(FopCmd fopCmd) {
		this.fopCmdList.remove(fopCmd);
	}

	public String getNotifyURL() {
		return notifyURL;
	}

	public void setNotifyURL(String notifyURL) {
		this.notifyURL = notifyURL;
	}

	public boolean isForce() {
		return force;
	}

	public void setForce(boolean force) {
		this.force = force;
	}

	public String getPipeline() {
		return pipeline;
	}

	public void setPipeline(String pipeline) {
		this.pipeline = pipeline;
	}

	public Mac getMac() {
		return mac;
	}

	public void setMac(Mac mac) {
		this.mac = mac;
	}

	private String fopsToString() {
		StringBuilder sb = new StringBuilder();
		for (FopCmd fopCmd : this.fopCmdList) {
			sb.append(fopCmd.toFopCmd()).append(";");
		}
		return sb.toString();
	}

	/**
	 * 执行pfop操作，返回persistentId
	 * 
	 * @throws JSONException
	 * @throws QiniuError
	 * 
	 * @throws Exception
	 */
	public String pfop() throws JSONException, QiniuError {
		String persistentId = null;
		DigestAuthClient digestAuthClient = new DigestAuthClient(mac);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("bucket", bucket));
		nvps.add(new BasicNameValuePair("key", key));
		nvps.add(new BasicNameValuePair("fops", fopsToString()));
		nvps.add(new BasicNameValuePair("notifyURL", notifyURL));
		String forceStr = "0";
		if (force) {
			forceStr = "1";
		}
		nvps.add(new BasicNameValuePair("force", forceStr));
		nvps.add(new BasicNameValuePair("pipeline", pipeline));
		CallRet call = digestAuthClient
				.call("http://api.qiniu.com/pfop/", nvps);
		if (call.statusCode == 200) {
			JSONObject resp = new JSONObject(call.response);
			persistentId = resp.getString("persistentId");
		} else {
			throw new QiniuError(call.response, call.exception);
		}
		return persistentId;
	}

}
