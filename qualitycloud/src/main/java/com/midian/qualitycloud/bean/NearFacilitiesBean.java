package com.midian.qualitycloud.bean;

import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;
import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 4.3.附近设备列表
 * 
 * @author Administrator
 * 
 */
public class NearFacilitiesBean extends NetResult {
	public static NearFacilitiesBean parse(String json) throws AppException {
		NearFacilitiesBean res = new NearFacilitiesBean();
		try {
			res = gson.fromJson(json, NearFacilitiesBean.class);
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

	public class Content extends NetResult {
		private String facility_id;// 设备id
		private String type;// 类型
		private String name;// 名称
		private String status;// 状态
		private String distance;// 距离
		private String use_company;
		private String run_years;// 运行年数
		private String address;// 使用单位地址
		private String logo_pic_thumb_name;
		private String logo_pic_thumb_suffix;

		public String getUse_company() {
			return use_company;
		}

		public void setUse_company(String use_company) {
			this.use_company = use_company;
		}

		public String getLogo_pic_thumb_name() {
			return logo_pic_thumb_name;
		}

		public void setLogo_pic_thumb_name(String logo_pic_thumb_name) {
			this.logo_pic_thumb_name = logo_pic_thumb_name;
		}

		public String getLogo_pic_thumb_suffix() {
			return logo_pic_thumb_suffix;
		}

		public void setLogo_pic_thumb_suffix(String logo_pic_thumb_suffix) {
			this.logo_pic_thumb_suffix = logo_pic_thumb_suffix;
		}

		public String getFacility_id() {
			return facility_id;
		}

		public void setFacility_id(String facility_id) {
			this.facility_id = facility_id;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getDistance() {
			return distance;
		}

		public void setDistance(String distance) {
			this.distance = distance;
		}

		public String getRun_years() {
			return run_years;
		}

		public void setRun_years(String run_years) {
			this.run_years = run_years;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}
	}
}
