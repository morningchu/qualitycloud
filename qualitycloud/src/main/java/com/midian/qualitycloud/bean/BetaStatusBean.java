package com.midian.qualitycloud.bean;

import com.google.gson.JsonSyntaxException;
import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 2.3.获取测试包状态
 * 
 * @author Administrator
 * 
 */
public class BetaStatusBean extends NetResult {
	public static BetaStatusBean parse(String json) throws AppException {
		BetaStatusBean res = new BetaStatusBean();
		try {
			res = gson.fromJson(json, BetaStatusBean.class);
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
		private String status;// 测试包状态

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

	}
}
