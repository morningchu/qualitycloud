package com.midian.qualitycloud.bean;

import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;
import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 3.3.我的关注
 * 
 * @author Administrator
 * 
 */
public class MyCollectsBean extends NetResult {
	public static MyCollectsBean parse(String json) throws AppException {
		MyCollectsBean res = new MyCollectsBean();
		try {
			res = gson.fromJson(json, MyCollectsBean.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			throw AppException.json(e);
		}
		return res;

	}

	private ArrayList<CollectContent> content;

	public ArrayList<CollectContent> getContent() {
		return content;
	}

	public void setContent(ArrayList<CollectContent> content) {
		this.content = content;
	}

	public class CollectContent extends NetResult {
		private String collect_id;// 收藏id
		private String record_id;// 被收藏对象id
		private String type;// 被收藏对象类型
		private String name;// 被收藏对象名称
		private String logo_pic_thumb_name;
		private String logo_pic_thumb_suffix;
		private String status;// 状态
		private String distance;// 距离
		private String run_years;// 运行年数
		private String address;// 地址
		private String use_company;

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

		public String getCollect_id() {
			return collect_id;
		}

		public void setCollect_id(String collect_id) {
			this.collect_id = collect_id;
		}

		public String getRecord_id() {
			return record_id;
		}

		public void setRecord_id(String record_id) {
			this.record_id = record_id;
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
