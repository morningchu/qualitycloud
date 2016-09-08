package com.midian.login.bean;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

import com.google.gson.JsonSyntaxException;

/**
 * 1.11更改会员推送接收状态
 * 
 * @author Administrator
 * 
 */
public class UpdateReceiveStatusBean extends NetResult {

	public static UpdateReceiveStatusBean parse(String json) throws AppException {
		UpdateReceiveStatusBean res = new UpdateReceiveStatusBean();
		try {
			res = gson.fromJson(json, UpdateReceiveStatusBean.class);
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
