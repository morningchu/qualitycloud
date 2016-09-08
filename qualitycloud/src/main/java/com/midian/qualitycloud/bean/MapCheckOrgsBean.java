package com.midian.qualitycloud.bean;

import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;
import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;
/**
 * 8.3.地图检测机构列表
 * @author Administrator
 *
 */
public class MapCheckOrgsBean extends NetResult {
	public static MapCheckOrgsBean parse(String json) throws AppException {
		MapCheckOrgsBean res = new MapCheckOrgsBean();
		try {
			res = gson.fromJson(json, MapCheckOrgsBean.class);
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
		private String check_org_id;//检测机构id
		private String check_org_name;//检测机构名称
		private String area_name;//所属区域名称
		private String lon;//经度
		private String lat;// 纬度

		public String getCheck_org_id() {
			return check_org_id;
		}

		public void setCheck_org_id(String check_org_id) {
			this.check_org_id = check_org_id;
		}

		public String getCheck_org_name() {
			return check_org_name;
		}

		public void setCheck_org_name(String check_org_name) {
			this.check_org_name = check_org_name;
		}

		public String getArea_name() {
			return area_name;
		}

		public void setArea_name(String area_name) {
			this.area_name = area_name;
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
