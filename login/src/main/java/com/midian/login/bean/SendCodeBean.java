package com.midian.login.bean;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

import com.google.gson.JsonSyntaxException;

/**
 * 1.4发送验证码
 * 
 * @author Administrator
 * 
 */
public class SendCodeBean extends NetResult {

	public static SendCodeBean parse(String json) throws AppException {
		SendCodeBean res = new SendCodeBean();
		try {
			res = gson.fromJson(json, SendCodeBean.class);
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

	}
}
