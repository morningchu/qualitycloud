package com.midian.qualitycloud.bean;

import java.util.ArrayList;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

import com.google.gson.JsonSyntaxException;

/**
 * 6.4.地理标志产品二级详情
 * 
 * @author Administrator
 * 
 */
public class GeoProSecondDetailBean extends NetResult {
	public static GeoProSecondDetailBean parse(String json) throws AppException {
		GeoProSecondDetailBean res = new GeoProSecondDetailBean();
		try {
			res = gson.fromJson(json, GeoProSecondDetailBean.class);
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
		private String detail;// 网页介绍
		public String getGeo_pro_id() {
			return geo_pro_id;
		}
		public void setGeo_pro_id(String geo_pro_id) {
			this.geo_pro_id = geo_pro_id;
		}
		public String getDetail() {
			return detail;
		}
		public void setDetail(String detail) {
			this.detail = detail;
		}

	}

	public class LogoPics extends NetResult {
		private String logo_pic_name;// 产品logo名称
		private String logo_pic_suffix;// 产品logo后缀

		public String getLogo_pic_name() {
			return logo_pic_name;
		}

		public void setLogo_pic_name(String logo_pic_name) {
			this.logo_pic_name = logo_pic_name;
		}

		public String getLogo_pic_suffix() {
			return logo_pic_suffix;
		}

		public void setLogo_pic_suffix(String logo_pic_suffix) {
			this.logo_pic_suffix = logo_pic_suffix;
		}
	}
}
