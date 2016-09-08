package com.midian.qualitycloud.bean;

import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;
import com.midian.qualitycloud.bean.NearFacilitiesBean.Content;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 4.1.设备详情
 * 
 * @author Administrator
 * 
 */
public class FacilityForbiddensBean extends NetResult {
	public static FacilityForbiddensBean parse(String json) throws AppException {
		FacilityForbiddensBean res = new FacilityForbiddensBean();
		try {
			res = gson.fromJson(json, FacilityForbiddensBean.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			throw AppException.json(e);
		}
		return res;

	}

	private ArrayList<ContentFacilityForbiddens> content;

	public ArrayList<ContentFacilityForbiddens> getContent() {
		return content;
	}

	public void setContent(ArrayList<ContentFacilityForbiddens> content) {
		this.content = content;
	}

	public class ContentFacilityForbiddens extends NetResult {
		private String forbidden_id;// 设备id
		private String name;

		public String getForbidden_id() {
			return forbidden_id;
		}

		public void setForbidden_id(String forbidden_id) {
			this.forbidden_id = forbidden_id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}
}
