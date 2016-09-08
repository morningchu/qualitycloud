package com.midian.qualitycloud.bean;

import com.google.gson.JsonSyntaxException;
import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;
/**
 * 3.2.取消关注
 * @author Administrator
 *
 */
public class CancelCollectBean extends NetResult {
	public static CancelCollectBean parse(String json) throws AppException {
		CancelCollectBean res = new CancelCollectBean();
		try {
			res = gson.fromJson(json, CancelCollectBean.class);
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
