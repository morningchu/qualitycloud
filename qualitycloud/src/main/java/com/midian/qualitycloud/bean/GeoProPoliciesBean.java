package com.midian.qualitycloud.bean;

import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;
import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 6.9.地理标志产品政策文件列表
 * 
 * @author Administrator
 * 
 */
public class GeoProPoliciesBean extends NetResult {
	public static GeoProPoliciesBean parse(String json) throws AppException {
		GeoProPoliciesBean res = new GeoProPoliciesBean();
		try {
			res = gson.fromJson(json, GeoProPoliciesBean.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			throw AppException.json(e);
		}
		return res;

	}

	private ArrayList<ContentGeoProPolicies> content;

	public ArrayList<ContentGeoProPolicies> getContent() {
		return content;
	}

	public void setContent(ArrayList<ContentGeoProPolicies> content) {
		this.content = content;
	}

	public class ContentGeoProPolicies extends NetResult {
		private String policy_id;// 申报指南id
		private String title;// 标题
		private String add_date;// 添加日期
		private String content_url;

		public String getContent_url() {
			return content_url;
		}

		public void setContent_url(String content_url) {
			this.content_url = content_url;
		}

		public String getPolicy_id() {
			return policy_id;
		}

		public void setPolicy_id(String policy_id) {
			this.policy_id = policy_id;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getAdd_date() {
			return add_date;
		}

		public void setAdd_date(String add_date) {
			this.add_date = add_date;
		}

	}
}
