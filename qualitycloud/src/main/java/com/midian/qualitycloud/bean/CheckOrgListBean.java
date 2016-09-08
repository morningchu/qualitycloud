package com.midian.qualitycloud.bean;

import java.util.ArrayList;
import java.util.List;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

import com.google.gson.JsonSyntaxException;
import com.midian.qualitycloud.bean.CheckAreasBean.ContentCheckAreas;

/**
 * 8.5.检测机构项目列表
 * 
 * @author Administrator
 * 
 */
public class CheckOrgListBean extends NetResult {
	public static CheckOrgListBean parse(String json) throws AppException {
		CheckOrgListBean res = new CheckOrgListBean();
		try {
			res = gson.fromJson(json, CheckOrgListBean.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			throw AppException.json(e);
		}
		return res;

	}

	private ArrayList<ContentCheckOrg> content;

	public ArrayList<ContentCheckOrg> getContent() {
		return content;
	}

	public void setContent(ArrayList<ContentCheckOrg> content) {
		this.content = content;
	}

	public class ContentCheckOrg extends NetResult {
		private String check_pro_id;
		private String check_pro_name;
		private String price;
		private String standard;

		public String getCheck_pro_id() {
			return check_pro_id;
		}

		public void setCheck_pro_id(String check_pro_id) {
			this.check_pro_id = check_pro_id;
		}

		public String getCheck_pro_name() {
			return check_pro_name;
		}

		public void setCheck_pro_name(String check_pro_name) {
			this.check_pro_name = check_pro_name;
		}

		public String getPrice() {
			return price;
		}

		public void setPrice(String price) {
			this.price = price;
		}

		public String getStandard() {
			return standard;
		}

		public void setStandard(String standard) {
			this.standard = standard;
		}
	}

}
