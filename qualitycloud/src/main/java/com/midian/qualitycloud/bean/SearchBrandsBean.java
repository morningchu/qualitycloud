package com.midian.qualitycloud.bean;

import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;
import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 7.4.搜索名牌列表
 * 
 * @author Administrator
 * 
 */
public class SearchBrandsBean extends NetResult {
	public static SearchBrandsBean parse(String json) throws AppException {
		SearchBrandsBean res = new SearchBrandsBean();
		try {
			res = gson.fromJson(json, SearchBrandsBean.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			throw AppException.json(e);
		}
		return res;

	}

	private ArrayList<Content> content;

	public ArrayList<Content> getContent() {
		return content;
	}

	public void setContent(ArrayList<Content> content) {
		this.content = content;
	}

	public class Content extends NetResult{
		private String company_name;//所属企业名称
		private String brand_name;// 名牌名称

		public String getCompany_name() {
			return company_name;
		}

		public void setCompany_name(String company_name) {
			this.company_name = company_name;
		}

		public String getBrand_name() {
			return brand_name;
		}

		public void setBrand_name(String brand_name) {
			this.brand_name = brand_name;
		}
	}
}
