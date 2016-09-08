package com.midian.qualitycloud.bean;

import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;
import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 7.6.名牌详情
 * 
 * @author Administrator
 * 
 */
public class BrandDetailBean extends NetResult {
	public static BrandDetailBean parse(String json) throws AppException {
		BrandDetailBean res = new BrandDetailBean();
		try {
			res = gson.fromJson(json, BrandDetailBean.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			throw AppException.json(e);
		}
		return res;

	}

	private ContentBrandDetail content;

	public ContentBrandDetail getContent() {
		return content;
	}

	public void setContent(ContentBrandDetail content) {
		this.content = content;
	}

	public class ContentBrandDetail extends NetResult {
		private String brand_id;// 名牌id
		private ArrayList<Brand_pics> brand_pics;
		private String company_name;// 厂商
		private String address;// 地址
		private String tel;// 电话
		private String fax;// 传真
		private String url;// 网址
		private String email;// 邮箱
		private String intro_url;// 企业简介
		private String share_url;//
		private String is_collected;

		public String getIntro_url() {
			return intro_url;
		}

		public void setIntro_url(String intro_url) {
			this.intro_url = intro_url;
		}

		public ArrayList<Brand_pics> getBrand_pics() {
			return brand_pics;
		}

		public void setBrand_pics(ArrayList<Brand_pics> brand_pics) {
			this.brand_pics = brand_pics;
		}

		public String getIs_collected() {
			return is_collected;
		}

		public void setIs_collected(String is_collected) {
			this.is_collected = is_collected;
		}

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

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getTel() {
			return tel;
		}

		public void setTel(String tel) {
			this.tel = tel;
		}

		public String getFax() {
			return fax;
		}

		public void setFax(String fax) {
			this.fax = fax;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getShare_url() {
			return share_url;
		}

		public void setShare_url(String share_url) {
			this.share_url = share_url;
		}
	}

	public class Brand_pics extends NetResult {
		private String brand_pic_name;// 名牌图片名称
		private String brand_pic_suffix;// 名牌图片后缀

		public String getBrand_pic_name() {
			return brand_pic_name;
		}

		public void setBrand_pic_name(String brand_pic_name) {
			this.brand_pic_name = brand_pic_name;
		}

		public String getBrand_pic_suffix() {
			return brand_pic_suffix;
		}

		public void setBrand_pic_suffix(String brand_pic_suffix) {
			this.brand_pic_suffix = brand_pic_suffix;
		}
	}
}
