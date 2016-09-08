package com.midian.login.bean;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

import com.google.gson.JsonSyntaxException;

/**
 * 1.8个人资料
 * 
 * @author Administrator
 * 
 */
public class UserDetailBean extends NetResult {

	public static UserDetailBean parse(String json) throws AppException {
		UserDetailBean res = new UserDetailBean();
		try {
			res = gson.fromJson(json, UserDetailBean.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			throw AppException.json(e);
		}
		return res;
	}

	private Content content;

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public class Content extends NetResult {
		private String head_pic;// 头像名称
		private String head_suffix;// 头像后缀
		private String name;// 姓名
		private String phone;// 手机号码
		private String sex;// 1
		private String province_id;// 省id
		private String province_name;// 省名称
		private String city_id;// 城市id
		private String city_name;// 城市名称
		private String address;// 家庭住址
		private String area_name;// 区域名称

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getArea_name() {
			return area_name;
		}

		public void setArea_name(String area_name) {
			this.area_name = area_name;
		}

		public String getHead_pic() {
			return head_pic;
		}

		public void setHead_pic(String head_pic) {
			this.head_pic = head_pic;
		}

		public String getHead_suffix() {
			return head_suffix;
		}

		public void setHead_suffix(String head_suffix) {
			this.head_suffix = head_suffix;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getSex() {
			return sex;
		}

		public void setSex(String sex) {
			this.sex = sex;
		}

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

		public String getCity_id() {
			return city_id;
		}

		public void setCity_id(String city_id) {
			this.city_id = city_id;
		}

		public String getCity_name() {
			return city_name;
		}

		public void setCity_name(String city_name) {
			this.city_name = city_name;
		}
	}
}
