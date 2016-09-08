package com.midian.qualitycloud.bean;

import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;
import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 8.1.检测机构类型列表
 * 
 * @author Administrator
 * 
 */
public class CheckTypesBean extends NetResult {
	public static CheckTypesBean parse(String json) throws AppException {
		CheckTypesBean res = new CheckTypesBean();
		try {
			res = gson.fromJson(json, CheckTypesBean.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			throw AppException.json(e);
		}
		return res;

	}

	private ArrayList<ContentCheckTypes> content;

	public ArrayList<ContentCheckTypes> getContent() {
		return content;
	}

	public void setContent(ArrayList<ContentCheckTypes> content) {
		this.content = content;
	}

	public class ContentCheckTypes extends NetResult {
		private String check_field_id;// 检测类型id
		private String check_field_name;// 检测类型名称

		public String getCheck_field_id() {
			return check_field_id;
		}

		public void setCheck_field_id(String check_field_id) {
			this.check_field_id = check_field_id;
		}

		public String getCheck_field_name() {
			return check_field_name;
		}

		public void setCheck_field_name(String check_field_name) {
			this.check_field_name = check_field_name;
		}
	}
}
