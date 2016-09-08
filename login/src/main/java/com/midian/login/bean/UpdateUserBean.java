package com.midian.login.bean;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

import com.google.gson.JsonSyntaxException;

/**
 * 1.9修改个人资料
 * 
 * @author Administrator
 * 
 */
public class UpdateUserBean extends NetResult {

	public static UpdateUserBean parse(String json) throws AppException {
		UpdateUserBean res = new UpdateUserBean();
		try {
			res = gson.fromJson(json, UpdateUserBean.class);
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

	public class Content extends NetResult {
		private String head_pic;// 头像文件名称
		private String head_pic_suffix;// 头像后缀

		public String getHead_pic() {
			return head_pic;
		}

		public void setHead_pic(String head_pic) {
			this.head_pic = head_pic;
		}

		public String getHead_pic_suffix() {
			return head_pic_suffix;
		}

		public void setHead_pic_suffix(String head_pic_suffix) {
			this.head_pic_suffix = head_pic_suffix;
		}
	}
}
