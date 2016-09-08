package com.midian.qualitycloud.bean;

import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;
import com.midian.qualitycloud.bean.MyCollectsBean.CollectContent;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;
/**
 * 4.2.地图设备列表
 * @author Administrator
 *
 */
public class MapFacilitiesBean extends NetResult {
	public static MapFacilitiesBean parse(String json) throws AppException {
		MapFacilitiesBean res = new MapFacilitiesBean();
		try {
			res = gson.fromJson(json, MapFacilitiesBean.class);
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
		private String facility_id;//设备id
		private String use_company;//使用单位
		private String lon;//经度
		private String lat;// 纬度

		public String getFacility_id() {
			return facility_id;
		}

		public void setFacility_id(String facility_id) {
			this.facility_id = facility_id;
		}

		public String getUse_company() {
			return use_company;
		}

		public void setUse_company(String use_company) {
			this.use_company = use_company;
		}

		public String getLon() {
			return lon;
		}

		public void setLon(String lon) {
			this.lon = lon;
		}

		public String getLat() {
			return lat;
		}

		public void setLat(String lat) {
			this.lat = lat;
		}
	}
}
