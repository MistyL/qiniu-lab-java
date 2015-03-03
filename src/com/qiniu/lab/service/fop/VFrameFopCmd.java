package com.qiniu.lab.service.fop;

import com.qiniu.api.net.EncodeUtils;

/**
 * 使用vframe接口截取视频的某一帧
 * 文档：http://developer.qiniu.com/docs/v6/api/reference/fop/av/vframe.html
 **/
public class VFrameFopCmd implements FopCmd {
	private String format;
	private int offset;
	private int width;
	private int height;
	private String rotate;
	private String saveBucket;
	private String saveKey;

	public VFrameFopCmd(String format, int offset) {
		this.format = format;
		this.offset = offset;
		this.saveKey = null;
		this.saveBucket = null;
	}

	public VFrameFopCmd(String format, int offset, String saveBucket,
			String saveKey) {
		this.format = format;
		this.offset = offset;
		this.saveBucket = saveBucket;
		this.saveKey = saveKey;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getRotate() {
		return rotate;
	}

	public void setRotate(String rotate) {
		this.rotate = rotate;
	}

	public void setSaveEntry(String saveBucket, String saveKey) {
		this.saveBucket = saveBucket;
		this.saveKey = saveKey;
	}

	@Override
	public String toFopCmd() {
		StringBuilder fop = new StringBuilder();
		fop.append("vframe/").append(format);
		fop.append("/offset/").append(offset);
		if (width > 0) {
			fop.append("/w/").append(width);
		}
		if (height > 0) {
			fop.append("/h/").append(height);
		}
		if (rotate != null && !rotate.equals("")) {
			fop.append("/rotate/").append(rotate);
		}
		if (saveKey != null && !saveKey.isEmpty()) {
			fop.append("|saveas/").append(
					EncodeUtils.urlsafeEncode(saveBucket + ":" + saveKey));
		}
		return fop.toString();
	}
}
