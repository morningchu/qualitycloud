package com.midian.qualitycloud.bean;

import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;
import com.midian.qualitycloud.bean.CheckAreasBean.ContentCheckAreas;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 8.7.检测机构资质条件列表
 * 
 * @author Administrator
 * 
 */
public class CheckQualitiesBean extends NetResult {
	public static CheckQualitiesBean parse(String json) throws AppException {
		CheckQualitiesBean res = new CheckQualitiesBean();
		try {
			res = gson.fromJson(json, CheckQualitiesBean.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			throw AppException.json(e);
		}
		return res;

	}

	private ArrayList<ContentCheckQualities> content;

	public ArrayList<ContentCheckQualities> getContent() {
		return content;
	}

	public void setContent(ArrayList<ContentCheckQualities> content) {
		this.content = content;
	}

	public class ContentCheckQualities extends NetResult {
		private String check_quality_id;//
		private String check_quality_name;//

		public String getCheck_quality_id() {
			return check_quality_id;
		}

		public void setCheck_quality_id(String check_quality_id) {
			this.check_quality_id = check_quality_id;
		}

		public String getCheck_quality_name() {
			return check_quality_name;
		}

		public void setCheck_quality_name(String check_quality_name) {
			this.check_quality_name = check_quality_name;
		}
	}
}
