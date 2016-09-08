package com.midian.qualitycloud.bean;

import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;
import com.midian.qualitycloud.bean.FacilityReportsBean.Content;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 6.1.地理标志分类列表
 * 
 * @author Administrator
 * 
 */
public class GeoProTypesBean extends NetResult {
	public static GeoProTypesBean parse(String json) throws AppException {
		GeoProTypesBean res = new GeoProTypesBean();
		try {
			res = gson.fromJson(json, GeoProTypesBean.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			throw AppException.json(e);
		}
		return res;

	}

	private ArrayList<ContentGeoTypes> content;

	public ArrayList<ContentGeoTypes> getContent() {
		return content;
	}

	public void setContent(ArrayList<ContentGeoTypes> content) {
		this.content = content;
	}

	public class ContentGeoTypes extends NetResult {
		private String geo_pro_type_id;// 地理标志类型id
		private String geo_pro_type_name;// 地理标志类型名称

		public String getGeo_pro_type_id() {
			return geo_pro_type_id;
		}

		public void setGeo_pro_type_id(String geo_pro_type_id) {
			this.geo_pro_type_id = geo_pro_type_id;
		}

		public String getGeo_pro_type_name() {
			return geo_pro_type_name;
		}

		public void setGeo_pro_type_name(String geo_pro_type_name) {
			this.geo_pro_type_name = geo_pro_type_name;
		}
	}
}
