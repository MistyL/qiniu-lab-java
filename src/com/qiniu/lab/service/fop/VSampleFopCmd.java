package com.qiniu.lab.service.fop;

import com.qiniu.api.net.EncodeUtils;

/**
 * 使用vsample接口给视频批量取帧
 * 文档：http://developer.qiniu.com/docs/v6/api/reference/fop/av/vsample.html
 * */
public class VSampleFopCmd implements FopCmd {
	private String format;// format
	private int startSecond;// ss
	private int duration;// t
	private String resolution;// s, 可选
	private int rotate;// rotate, [90,180,270], 可选
	private int interval;// interval, 可选
	private String pattern;// pattern

	public VSampleFopCmd(String format, int startSecond, int duration,
			String pattern) {
		this.format = format;
		this.startSecond = startSecond;
		this.duration = duration;
		this.pattern = pattern;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public int getStartSecond() {
		return startSecond;
	}

	public void setStartSecond(int startSecond) {
		this.startSecond = startSecond;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public int getRotate() {
		return rotate;
	}

	public void setRotate(int rotate) {
		this.rotate = rotate;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	@Override
	public String toFopCmd() {
		StringBuilder sb = new StringBuilder();
		sb.append("vsample/").append(format);
		sb.append("/ss/").append(startSecond);
		sb.append("/t/").append(duration);
		if (resolution != null) {
			sb.append("/s/").append(resolution);
		}
		if (rotate > 0) {
			sb.append("/rotate/").append(rotate);
		}
		if (interval > 0) {
			sb.append("/interval/").append(interval);
		}
		sb.append("/pattern/").append(EncodeUtils.urlsafeEncode(pattern));
		return sb.toString();
	}

}
