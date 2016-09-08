package com.midian.qualitycloud.bean;

import com.google.gson.JsonSyntaxException;
import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 2.1.获取最新版本信息
 * 
 * @author Administrator
 * 
 */
public class VersionBean extends NetResult {
	public static VersionBean parse(String json) throws AppException {
		VersionBean res = new VersionBean();
		try {
			res = gson.fromJson(json, VersionBean.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			throw AppException.json(e);
		}
		return res;

	}

	private Content content;

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public class Content extends NetResult{
		private String file_name; // APK文件
		private String file_suffix; // APK文件后缀
		private String version; // 最新版本号
		private String detail; // 描述
		private String force_status; // 是否强制更新

		public String getFile_name() {
			return file_name;
		}

		public void setFile_name(String file_name) {
			this.file_name = file_name;
		}

		public String getFile_suffix() {
			return file_suffix;
		}

		public void setFile_suffix(String file_suffix) {
			this.file_suffix = file_suffix;
		}

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}

		public String getDetail() {
			return detail;
		}

		public void setDetail(String detail) {
			this.detail = detail;
		}

		public String getForce_status() {
			return force_status;
		}

		public void setForce_status(String force_status) {
			this.force_status = force_status;
		}
	}
}
