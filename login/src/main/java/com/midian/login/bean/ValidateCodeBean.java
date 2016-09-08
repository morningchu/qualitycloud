package com.midian.login.bean;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

import com.google.gson.JsonSyntaxException;

/**
 * 1.5验证验证码
 * 
 * @author Administrator
 * 
 */
public class ValidateCodeBean extends NetResult {

	public static ValidateCodeBean parse(String json) throws AppException {
		ValidateCodeBean res = new ValidateCodeBean();
		try {
			res = gson.fromJson(json, ValidateCodeBean.class);
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
