package com.midian.login.bean;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

import com.google.gson.JsonSyntaxException;

/**
 * 1.7修改密码
 * 
 * @author Administrator
 * 
 */
public class UpdatePwdBean extends NetResult {

	public static UpdatePwdBean parse(String json) throws AppException {
		UpdatePwdBean res = new UpdatePwdBean();
		try {
			res = gson.fromJson(json, UpdatePwdBean.class);
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
