package com.midian.qualitycloud.bean;

import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

public class BrandReportDetailBean extends NetResult {
	public static BrandReportDetailBean parse(String json) throws AppException {
		BrandReportDetailBean res = new BrandReportDetailBean();
		try {
			res = gson.fromJson(json, BrandReportDetailBean.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			throw AppException.json(e);
		}
		return res;

	}

	private ContentBrand content;

	public ContentBrand getContent() {
		return content;
	}

	public void setContent(ContentBrand content) {
		this.content = content;
	}

	public class ContentBrand extends NetResult {
		private String report_id;
		private String title;
		private String add_date;
		private String content_url;

		public String getContent_url() {
			return content_url;
		}

		public void setContent_url(String content_url) {
			this.content_url = content_url;
		}

		public String getReport_id() {
			return report_id;
		}

		public void setReport_id(String report_id) {
			this.report_id = report_id;
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
