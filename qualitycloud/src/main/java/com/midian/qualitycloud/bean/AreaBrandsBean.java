package com.midian.qualitycloud.bean;

import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;
import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 7.3.区域名牌列表
 * 
 * @author Administrator
 * 
 */
public class AreaBrandsBean extends NetResult {
	public static AreaBrandsBean parse(String json) throws AppException {
		AreaBrandsBean res = new AreaBrandsBean();
		try {
			res = gson.fromJson(json, AreaBrandsBean.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			throw AppException.json(e);
		}
		return res;

	}

	private ContentAreaBrands content;

	public ContentAreaBrands getContent() {
		return content;
	}

	public void setContent(ContentAreaBrands content) {
		this.content = content;
	}

	public class ContentAreaBrands extends NetResult {
		private String total_count;// 总数
		private ArrayList<Brands> brands;

		public String getTotal_count() {
			return total_count;
		}

		public void setTotal_count(String total_count) {
			this.total_count = total_count;
		}

		public ArrayList<Brands> getBrands() {
			return brands;
		}

		public void setBrands(ArrayList<Brands> brands) {
			this.brands = brands;
		}

	}

	public static class Brands extends NetResult {
		private String company_name;// 所属企业名称
		private String brand_name;// 名牌名称
		private String brand_id;

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

		public String getBrand_name() {
			return brand_name;
		}

		public void setBrand_name(String brand_name) {
			this.brand_name = brand_name;
		}
	}
}
