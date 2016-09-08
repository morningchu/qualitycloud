package com.midian.qualitycloud.bean;

import java.util.ArrayList;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

import com.google.gson.JsonSyntaxException;

/**
 * 8.4.检测机构详情
 * 
 * @author Administrator
 * 
 */
public class CheckOrgDetailBean extends NetResult {
	public static CheckOrgDetailBean parse(String json) throws AppException {
		CheckOrgDetailBean res = new CheckOrgDetailBean();
		try {
			res = gson.fromJson(json, CheckOrgDetailBean.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			throw AppException.json(e);
		}
		return res;

	}

	private ContentCheckDetail content;

	public ContentCheckDetail getContent() {
		return content;
	}

	public void setContent(ContentCheckDetail content) {
		this.content = content;
	}

	public class ContentCheckDetail extends NetResult {
		private String check_org_id;//
		private String phone;//
		private String address;//
		private String lon;//
		private String lat;//
		private String is_collected;//
		private String intro_url;//

		public String getIntro_url() {
			return intro_url;
		}

		public void setIntro_url(String intro_url) {
			this.intro_url = intro_url;
		}

		private ArrayList<Check_pros> check_pros;//

		public String getCheck_org_id() {
			return check_org_id;
		}

		public void setCheck_org_id(String check_org_id) {
			this.check_org_id = check_org_id;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
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

		public String getIs_collected() {
			return is_collected;
		}

		public void setIs_collected(String is_collected) {
			this.is_collected = is_collected;
		}

		public ArrayList<Check_pros> getCheck_pros() {
			return check_pros;
		}

		public void setCheck_pros(ArrayList<Check_pros> check_pros) {
			this.check_pros = check_pros;
		}
	}

}
