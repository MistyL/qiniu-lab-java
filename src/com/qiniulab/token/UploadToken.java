package com.qiniulab.token;

import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniulab.config.QiniuLabConfig;

/**
 * 该例子演示如何使用七牛最新的java sdk来生成各种上传凭证
 * 文档：http://developer.qiniu.com/docs/v6/api/reference/security/put-policy.html
 * */
public class UploadToken {
	// 生成无key文件的上传凭证
	public static void createSimpleUploadWithoutKeyToken() {
		Auth auth = Auth.create(QiniuLabConfig.ACCESS_KEY,
				QiniuLabConfig.SECRET_KEY);
		String uptoken = auth.uploadToken(QiniuLabConfig.PUBLIC_BUCKET);
		System.out.println(uptoken);
	}

	// 生成有key文件的上传凭证(其实和无key文件一样)
	public static void createSimpleUploadWithKeyToken() {
		Auth auth = Auth.create(QiniuLabConfig.ACCESS_KEY,
				QiniuLabConfig.SECRET_KEY);
		String uptoken = auth.uploadToken(QiniuLabConfig.PUBLIC_BUCKET);
		System.out.println(uptoken);
	}

	// 业务服务器指定上传的文件名称
	public static void createSimpleUploadWithSaveKeyToken() {
		Auth auth = Auth.create(QiniuLabConfig.ACCESS_KEY,
				QiniuLabConfig.SECRET_KEY);
		StringMap policy = new StringMap();
		policy.put("saveKey", "camera_upload/2015/03/12/file100.mp4");
		long expires = 3600;// 3600秒＝1小时
		String uptoken = auth.uploadToken(QiniuLabConfig.PUBLIC_BUCKET, null,
				expires, policy);
		System.out.println(uptoken);
	}

	// 带持久化处理的上传凭证
	public static void createSimpleUploadWithPfop() {
		Auth auth = Auth.create(QiniuLabConfig.ACCESS_KEY,
				QiniuLabConfig.SECRET_KEY);
		StringMap policy = new StringMap();
		policy.put("persistentOps", "avthumb/mp4;vframe/png/offset/0");
		policy.put("persistentNotifyUrl", "http://api.abc.com/qiniuFopNotify");
		policy.put("persistentPipeline", "p1");

		long expires = 3600;// 3600秒＝1小时
		String uptoken = auth.uploadToken(QiniuLabConfig.PUBLIC_BUCKET, null,
				expires, policy);
		System.out.println(uptoken);
	}

	// 限制文件的上传大小
	public static void createSimpleUploadWithFsizeLimit() {
		Auth auth = Auth.create(QiniuLabConfig.ACCESS_KEY,
				QiniuLabConfig.SECRET_KEY);
		StringMap policy = new StringMap();
		int fsizeLimit = 4 * 1024 * 1024;// 4MB，可以限定客户端上传的文件大小不超过4MB
		policy.put("fsizeLimit", fsizeLimit + "");

		long expires = 3600;// 3600秒＝1小时
		String uptoken = auth.uploadToken(QiniuLabConfig.PUBLIC_BUCKET, null,
				expires, policy);
		System.out.println(uptoken);
	}

	// 限定文件的上传类型
	public static void createSimpleUploadWithMimeLimit() {
		Auth auth = Auth.create(QiniuLabConfig.ACCESS_KEY,
				QiniuLabConfig.SECRET_KEY);
		StringMap policy = new StringMap();
		policy.put("mimeLimit", "image/png;image/jpeg");

		long expires = 3600;// 3600秒＝1小时
		String uptoken = auth.uploadToken(QiniuLabConfig.PUBLIC_BUCKET, null,
				expires, policy);
		System.out.println(uptoken);
	}

	public static void main(String[] args) {
		createSimpleUploadWithPfop();
	}

}
