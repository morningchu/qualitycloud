package com.midian.login.bean;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

import com.google.gson.JsonSyntaxException;

/**
 * 1.12找回密码
 * 
 * @author Administrator
 * 
 */
public class FindPwdBean extends NetResult {

	public static FindPwdBean parse(String json) throws AppException {
		FindPwdBean res = new FindPwdBean();
		try {
			res = gson.fromJson(json, FindPwdBean.class);
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
