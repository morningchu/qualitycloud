package com.midian.login.bean;

import java.util.ArrayList;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

import com.google.gson.JsonSyntaxException;

/**
 * 省份、直辖市列表.
 * 
 * @author chu
 * 
 */
public class ProvincesBean extends NetResult {

	private ArrayList<ProvincesContent> content;
	private String total_count;

	public static ProvincesBean parse(String json) throws AppException {
		ProvincesBean res = new ProvincesBean();
		try {
			res = gson.fromJson(json, ProvincesBean.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			throw AppException.json(e);
		}
		// System.out.println(res);
		return res;
	}

	public ArrayList<ProvincesContent> getContent() {
		return content;
	}

	public void setContent(ArrayList<ProvincesContent> content) {
		this.content = content;
	}

	public String getTotal_count() {
		return total_count;
	}

	public void setTotal_count(String total_count) {
		this.total_count = total_count;
	}

	public static class ProvincesContent extends NetResult {

		private String province_id;// 省id
		private String province_name;// 省名称

		public String getProvince_id() {
			return province_id;
		}

		public void setProvince_id(String province_id) {
			this.province_id = province_id;
		}

		public String getProvince_name() {
			return province_name;
		}

		public void setProvince_name(String province_name) {
			this.province_name = province_name;
		}

	}
}
