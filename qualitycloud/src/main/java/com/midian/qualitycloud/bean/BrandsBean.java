package com.midian.qualitycloud.bean;

import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;
import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;
/**
 * 7.1.名牌列表
 * @author Administrator
 *
 */
public class BrandsBean extends NetResult {
	public static BrandsBean parse(String json) throws AppException {
		BrandsBean res = new BrandsBean();
		try {
			res = gson.fromJson(json, BrandsBean.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			throw AppException.json(e);
		}
		return res;

	}

	private ArrayList<ContentBrand> content;
	
	public ArrayList<ContentBrand> getContent() {
		return content;
	}

	public void setContent(ArrayList<ContentBrand> content) {
		this.content = content;
	}

	public class ContentBrand extends NetResult{
		private String brand_id;//名牌id
		private String company_name;//所属企业
		private String name;// 名称

		public String getBrand_id() {
			return brand_id;
		}

		public void setBrand_id(String brand_id) {
			this.brand_id = brand_id;
		}

		public String getCompany_name() {
			return company_name;
		}

		public void setCompany_name(String company_name) {
			this.company_name = company_name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
	}
}
