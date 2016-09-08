package com.midian.qualitycloud.bean;

import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;
import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 6.8.地理标志产品申报指南详情
 * 
 * @author Administrator
 * 
 */
public class GeoProReportDetailBean extends NetResult {
	public static GeoProReportDetailBean parse(String json) throws AppException {
		GeoProReportDetailBean res = new GeoProReportDetailBean();
		try {
			res = gson.fromJson(json, GeoProReportDetailBean.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			throw AppException.json(e);
		}
		return res;

	}

	private ContentGeoProReport content;

	public ContentGeoProReport getContent() {
		return content;
	}

	public void setContent(ContentGeoProReport content) {
		this.content = content;
	}

	public class ContentGeoProReport extends NetResult {
		private String report_id;// 申报指南id
		private String title;// 标题
		private String add_date;// 添加日期
		private String content_url;// 内容

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
