package com.midian.qualitycloud.bean;

import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;
import com.midian.qualitycloud.bean.ComplainReasonsBean.Content;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 5.5.设备举报原因列表
 * 
 * @author Administrator
 * 
 */
public class FacilityReportsBean extends NetResult {
	public static FacilityReportsBean parse(String json) throws AppException {
		FacilityReportsBean res = new FacilityReportsBean();
		try {
			res = gson.fromJson(json, FacilityReportsBean.class);
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

	public class Content extends NetResult{
		private String reason_id;//原因id
		private String reason;//原因
		private String add_date;// 对方入库时间

		public String getReason_id() {
			return reason_id;
		}

		public void setReason_id(String reason_id) {
			this.reason_id = reason_id;
		}

		public String getReason() {
			return reason;
		}

		public void setReason(String reason) {
			this.reason = reason;
		}

		public String getAdd_date() {
			return add_date;
		}

		public void setAdd_date(String add_date) {
			this.add_date = add_date;
		}
	}
}
