package com.midian.login.bean;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

import com.google.gson.JsonSyntaxException;

/**
 * 2.4发送用户设备号/清空设备号
 * 
 * @author Administrator
 * 
 */
public class SaveDevice extends NetResult {

	public static SaveDevice parse(String json) throws AppException {
		SaveDevice res = new SaveDevice();
		try {
			res = gson.fromJson(json, SaveDevice.class);
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
		private String is_receive;

		public String getIs_receive() {
			return is_receive;
		}

		public void setIs_receive(String is_receive) {
			this.is_receive = is_receive;
		}
		
	}
}
