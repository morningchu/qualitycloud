package com.midian.login.bean;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

import com.google.gson.JsonSyntaxException;

/**
 * 1.2登录
 * 
 * @author Administrator
 * 
 */
public class LoginBean extends NetResult {

	public static LoginBean parse(String json) throws AppException {
		LoginBean res = new LoginBean();
		try {
			res = gson.fromJson(json, LoginBean.class);
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
		private String access_token;
		private String user_id;
		private String name;
		private String head_pic;
		private String head_pic_suffix;

		public String getAccess_token() {
			return access_token;
		}

		public void setAccess_token(String access_token) {
			this.access_token = access_token;
		}

		public String getUser_id() {
			return user_id;
		}

		public void setUser_id(String user_id) {
			this.user_id = user_id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

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
