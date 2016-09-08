package com.midian.qualitycloud.bean;

import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;
import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;
/**
 * 8.2.检测机构列表
 * @author Administrator
 *
 */
public class CheckOrgsBean extends NetResult {
	public static CheckOrgsBean parse(String json) throws AppException {
		CheckOrgsBean res = new CheckOrgsBean();
		try {
			res = gson.fromJson(json, CheckOrgsBean.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			throw AppException.json(e);
		}
		return res;

	}

	private ArrayList<ContentCheckOrgs> content;
	
	public ArrayList<ContentCheckOrgs> getContent() {
		return content;
	}

	public void setContent(ArrayList<ContentCheckOrgs> content) {
		this.content = content;
	}

	public class ContentCheckOrgs extends NetResult{
		private String check_org_id;//检测机构id
		private String check_org_name;//检测机构名称
		private String area_name;//区域名称
		private String distance;//距离
		private String address;//地址
		private String tel;// 电话号码
		private String lon;//
		private String lat;//
		private String effective ;//资质是否有效”
		
		
		public String getEffective() {
			return effective;
		}

		public void setEffective(String effective) {
			this.effective = effective;
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

		public String getDistance() {
			return distance;
		}

		public void setDistance(String distance) {
			this.distance = distance;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getTel() {
			return tel;
		}

		public void setTel(String tel) {
			this.tel = tel;
		}
	}
}
