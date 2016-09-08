package com.midian.login.bean;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

import com.google.gson.JsonSyntaxException;

/**
 * 1.10修改手机号
 * 
 * @author Administrator
 * 
 */
public class UpdatePhoneBean extends NetResult {

	public static UpdatePhoneBean parse(String json) throws AppException {
		UpdatePhoneBean res = new UpdatePhoneBean();
		try {
			res = gson.fromJson(json, UpdatePhoneBean.class);
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
