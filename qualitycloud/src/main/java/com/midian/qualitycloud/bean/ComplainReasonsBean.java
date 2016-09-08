package com.midian.qualitycloud.bean;

import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;
import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 5.2.投诉原因列表
 * 
 * @author Administrator
 * 
 */
public class ComplainReasonsBean extends NetResult {
	public static ComplainReasonsBean parse(String json) throws AppException {
		ComplainReasonsBean res = new ComplainReasonsBean();
		try {
			res = gson.fromJson(json, ComplainReasonsBean.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			throw AppException.json(e);
		}
		return res;

	}

	private ArrayList<Content> content;

	public ArrayList<Content> getContent() {
		return content;
	}

	public void setContent(ArrayList<Content> content) {
		this.content = content;
	}

	public class Content extends NetResult {
		private String reason_id;// 原因id
		private String reason_name;// 原因名称

		public String getReason_id() {
			return reason_id;
		}

		public void setReason_id(String reason_id) {
			this.reason_id = reason_id;
		}

		public String getReason_name() {
			return reason_name;
		}

		public void setReason_name(String reason_name) {
			this.reason_name = reason_name;
		}
	}
}
