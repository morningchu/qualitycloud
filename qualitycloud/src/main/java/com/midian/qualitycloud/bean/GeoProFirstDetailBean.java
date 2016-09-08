package com.midian.qualitycloud.bean;

import com.google.gson.JsonSyntaxException;
import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 6.3.地理标志产品一级详情
 * 
 * @author Administrator
 * 
 */
public class GeoProFirstDetailBean extends NetResult {
	public static GeoProFirstDetailBean parse(String json) throws AppException {
		GeoProFirstDetailBean res = new GeoProFirstDetailBean();
		try {
			res = gson.fromJson(json, GeoProFirstDetailBean.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			throw AppException.json(e);
		}
		return res;

	}

	private Content content;

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public class Content extends NetResult {
		private String geo_pro_id;// 产品id
		private String geo_pro_type_id;// 类型id
		private String logo_pic_thumb_name;// 产品logo缩略图名称
		private String logo_pic_thumb_suffix;// 产品logo缩略图后缀
		private String geo_pro_name;// 产品名称
		private String protect_notice_num;// 保护公告号
		private String protect_range;// 保护范围
		private String product_num;// 产品标准编号
		private String area;// 所属区域
		private String intro;// 介绍
		private String intro_url;// 介绍
		private String is_collected;//

		public String getIs_collected() {
			return is_collected;
		}

		public String getIntro_url() {
			return intro_url;
		}

		public void setIntro_url(String intro_url) {
			this.intro_url = intro_url;
		}

		public void setIs_collected(String is_collected) {
			this.is_collected = is_collected;
		}

		public String getGeo_pro_id() {
			return geo_pro_id;
		}

		public void setGeo_pro_id(String geo_pro_id) {
			this.geo_pro_id = geo_pro_id;
		}

		public String getGeo_pro_type_id() {
			return geo_pro_type_id;
		}

		public void setGeo_pro_type_id(String geo_pro_type_id) {
			this.geo_pro_type_id = geo_pro_type_id;
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

		public String getProtect_range() {
			return protect_range;
		}

		public void setProtect_range(String protect_range) {
			this.protect_range = protect_range;
		}

		public String getProduct_num() {
			return product_num;
		}

		public void setProduct_num(String product_num) {
			this.product_num = product_num;
		}

		public String getArea() {
			return area;
		}

		public void setArea(String area) {
			this.area = area;
		}

		public String getIntro() {
			return intro;
		}

		public void setIntro(String intro) {
			this.intro = intro;
		}
	}
}
