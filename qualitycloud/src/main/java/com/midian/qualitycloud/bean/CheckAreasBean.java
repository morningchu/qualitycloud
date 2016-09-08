package com.midian.qualitycloud.bean;

import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;
import com.midian.qualitycloud.bean.CheckTypesBean.ContentCheckTypes;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 8.5.检测机构地区列表
 * 
 * @author Administrator
 * 
 */
public class CheckAreasBean extends NetResult {
	public static CheckAreasBean parse(String json) throws AppException {
		CheckAreasBean res = new CheckAreasBean();
		try {
			res = gson.fromJson(json, CheckAreasBean.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			throw AppException.json(e);
		}
		return res;

	}

	private ArrayList<ContentCheckAreas> content;

	public ArrayList<ContentCheckAreas> getContent() {
		return content;
	}

	public void setContent(ArrayList<ContentCheckAreas> content) {
		this.content = content;
	}

	public class ContentCheckAreas extends NetResult {
		private String city_id;//
		private String city_name;
		private ArrayList<Areas> areas;

		public String getCity_id() {
			return city_id;
		}

		public void setCity_id(String city_id) {
			this.city_id = city_id;
		}

		public String getCity_name() {
			return city_name;
		}

		public void setCity_name(String city_name) {
			this.city_name = city_name;
		}

		public ArrayList<Areas> getAreas() {
			return areas;
		}

		public void setAreas(ArrayList<Areas> areas) {
			this.areas = areas;
		}
	}

	public class Areas extends NetResult {
		private String area_id;//
		private String area_name;

		public String getArea_id() {
			return area_id;
		}

		public void setArea_id(String area_id) {
			this.area_id = area_id;
		}

		public String getArea_name() {
			return area_name;
		}

		public void setArea_name(String area_name) {
			this.area_name = area_name;
		}
	}
}
