package com.midian.qualitycloud.bean;

import com.google.gson.JsonSyntaxException;
import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;
/**
 * 5.4.举报设备
 * @author Administrator
 *
 */
public class ReportBean extends NetResult {
	public static ReportBean parse(String json) throws AppException {
		ReportBean res = new ReportBean();
		try {
			res = gson.fromJson(json, ReportBean.class);
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
