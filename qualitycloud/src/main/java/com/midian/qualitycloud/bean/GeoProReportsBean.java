package com.midian.qualitycloud.bean;

import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;
import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 6.7.地理标志产品申报指南列表
 * 
 * @author Administrator
 * 
 */
public class GeoProReportsBean extends NetResult {
	public static GeoProReportsBean parse(String json) throws AppException {
		GeoProReportsBean res = new GeoProReportsBean();
		try {
			res = gson.fromJson(json, GeoProReportsBean.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			throw AppException.json(e);
		}
		return res;

	}

	private ArrayList<ContentReport> content;

	public ArrayList<ContentReport> getContent() {
		return content;
	}

	public void setContent(ArrayList<ContentReport> content) {
		this.content = content;
	}

	public class ContentReport extends NetResult {
		private String report_id;// 申报指南id
		private String title;// 标题
		private String add_date;// 添加日期

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
