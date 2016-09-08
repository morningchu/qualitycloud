package com.midian.login.bean;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

import com.google.gson.JsonSyntaxException;

/**
 * 1.1.注册
 * 
 * @author Administrator
 * 
 */
public class RegisterBean extends NetResult {

	public static RegisterBean parse(String json) throws AppException {
		RegisterBean res = new RegisterBean();
		try {
			res = gson.fromJson(json, RegisterBean.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			throw AppException.json(e);
		}
		return res;
	}

	private ContentRegis content;

	public ContentRegis getContent() {
		return content;
	}

	public void setContent(ContentRegis content) {
		this.content = content;
	}

	public class ContentRegis extends NetResult {
		private String access_token;
		private String userid;

		public String getAccess_token() {
			return access_token;
		}

		public void setAccess_token(String access_token) {
			this.access_token = access_token;
		}

		public String getUserid() {
			return userid;
		}

		public void setUserid(String userid) {
			this.userid = userid;
		}
	}
}
