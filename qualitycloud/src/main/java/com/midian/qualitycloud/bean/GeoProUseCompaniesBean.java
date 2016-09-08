package com.midian.qualitycloud.bean;

import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;
import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 6.5.地理标志产品使用企业列表
 * 
 * @author Administrator
 * 
 */
public class GeoProUseCompaniesBean extends NetResult {
	public static GeoProUseCompaniesBean parse(String json) throws AppException {
		GeoProUseCompaniesBean res = new GeoProUseCompaniesBean();
		try {
			res = gson.fromJson(json, GeoProUseCompaniesBean.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			throw AppException.json(e);
		}
		return res;

	}

	private ArrayList<ContentUse> content;

	public ArrayList<ContentUse> getContent() {
		return content;
	}

	public void setContent(ArrayList<ContentUse> content) {
		this.content = content;
	}

	public class ContentUse extends NetResult{
		private String company_id;//使用企业id
		private String logo_thumb_pic_name;//使用企业logo名称
		private String logo_thumb_pic_suffix;//使用企业logo后缀
		private String company_name;//使用企业名称
		private String address;// 使用企业地址

		public String getCompany_id() {
			return company_id;
		}

		public void setCompany_id(String company_id) {
			this.company_id = company_id;
		}

		public String getLogo_thumb_pic_name() {
			return logo_thumb_pic_name;
		}

		public void setLogo_thumb_pic_name(String logo_thumb_pic_name) {
			this.logo_thumb_pic_name = logo_thumb_pic_name;
		}

		public String getLogo_thumb_pic_suffix() {
			return logo_thumb_pic_suffix;
		}

		public void setLogo_thumb_pic_suffix(String logo_thumb_pic_suffix) {
			this.logo_thumb_pic_suffix = logo_thumb_pic_suffix;
		}

		public String getCompany_name() {
			return company_name;
		}

		public void setCompany_name(String company_name) {
			this.company_name = company_name;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}
	}
}
