package com.midian.qualitycloud.bean;

import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;
import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 6.6.地理标志产品相关推荐列表
 * 
 * @author Administrator
 * 
 */
public class GeoProRecommendationsBean extends NetResult {
	public static GeoProRecommendationsBean parse(String json)
			throws AppException {
		GeoProRecommendationsBean res = new GeoProRecommendationsBean();
		try {
			res = gson.fromJson(json, GeoProRecommendationsBean.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			throw AppException.json(e);
		}
		return res;

	}

	private ArrayList<ContentRecom> content;

	public ArrayList<ContentRecom> getContent() {
		return content;
	}

	public void setContent(ArrayList<ContentRecom> content) {
		this.content = content;
	}

	public class ContentRecom extends NetResult {
		private String geo_pro_id;// 产品id
		private String logo_pic_thumb_name;// 产品logo缩略图名称
		private String logo_pic_thumb_suffix;// 产品logo缩略图后缀
		private String geo_pro_name;// 产品名称
		private String protect_notice_num;// 保护公告号
		private String area;// 保护范围

		public String getGeo_pro_id() {
			return geo_pro_id;
		}

		public void setGeo_pro_id(String geo_pro_id) {
			this.geo_pro_id = geo_pro_id;
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

		public String getGeo_pro_name() {
			return geo_pro_name;
		}

		public void setGeo_pro_name(String geo_pro_name) {
			this.geo_pro_name = geo_pro_name;
		}

		public String getProtect_notice_num() {
			return protect_notice_num;
		}

		public void setProtect_notice_num(String protect_notice_num) {
			this.protect_notice_num = protect_notice_num;
		}

		public String getArea() {
			return area;
		}

		public void setArea(String area) {
			this.area = area;
		}

	}
}
