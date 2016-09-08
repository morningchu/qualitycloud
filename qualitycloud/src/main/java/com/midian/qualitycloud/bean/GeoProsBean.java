package com.midian.qualitycloud.bean;

import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;
import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 6.2.地理标志产品列表
 * 
 * @author Administrator
 * 
 */
public class GeoProsBean extends NetResult {
	public static GeoProsBean parse(String json) throws AppException {
		GeoProsBean res = new GeoProsBean();
		try {
			res = gson.fromJson(json, GeoProsBean.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			throw AppException.json(e);
		}
		return res;

	}

	private ArrayList<ContentGeo> content;

	public ArrayList<ContentGeo> getContent() {
		return content;
	}

	public void setContent(ArrayList<ContentGeo> content) {
		this.content = content;
	}

	public class ContentGeo extends NetResult {
		private String geo_pro_id;// 产品id
		private String geo_pro_name;// 产品名称
		private String area;// 所属区域
		private String geo_pro_type_id;// 地理标志产品类型id

		public String getGeo_pro_type_id() {
			return geo_pro_type_id;
		}

		public void setGeo_pro_type_id(String geo_pro_type_id) {
			this.geo_pro_type_id = geo_pro_type_id;
		}

		public String getGeo_pro_id() {
			return geo_pro_id;
		}

		public void setGeo_pro_id(String geo_pro_id) {
			this.geo_pro_id = geo_pro_id;
		}

		public String getGeo_pro_name() {
			return geo_pro_name;
		}

		public void setGeo_pro_name(String geo_pro_name) {
			this.geo_pro_name = geo_pro_name;
		}

		public String getArea() {
			return area;
		}

		public void setArea(String area) {
			this.area = area;
		}
	}
}
