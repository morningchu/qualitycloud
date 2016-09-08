package com.midian.login.bean;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

import com.google.gson.JsonSyntaxException;

/**
 * 1.6验证原密码
 * 
 * @author Administrator
 * 
 */
public class ValidatePwdBean extends NetResult {

	public static ValidatePwdBean parse(String json) throws AppException {
		ValidatePwdBean res = new ValidatePwdBean();
		try {
			res = gson.fromJson(json, ValidatePwdBean.class);
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
