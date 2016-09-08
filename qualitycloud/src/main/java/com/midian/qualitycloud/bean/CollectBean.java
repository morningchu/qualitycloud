package com.midian.qualitycloud.bean;

import com.google.gson.JsonSyntaxException;
import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;
/**
 * 3.1.关注
 * @author Administrator
 *
 */
public class CollectBean extends NetResult {
	public static CollectBean parse(String json) throws AppException {
		CollectBean res = new CollectBean();
		try {
			res = gson.fromJson(json, CollectBean.class);
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
