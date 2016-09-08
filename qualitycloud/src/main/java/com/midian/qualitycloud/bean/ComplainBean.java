package com.midian.qualitycloud.bean;

import com.google.gson.JsonSyntaxException;
import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;
/**
 * 5.1.投诉设备
 * @author Administrator
 *
 */
public class ComplainBean extends NetResult {
	public static ComplainBean parse(String json) throws AppException {
		ComplainBean res = new ComplainBean();
		try {
			res = gson.fromJson(json, ComplainBean.class);
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

	public class Content extends NetResult{

	}
}
