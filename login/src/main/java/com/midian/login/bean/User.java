package com.midian.login.bean;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

import com.google.gson.JsonSyntaxException;

/**
 * 用户
 * 
 * @author XuYang
 * 
 */
public class User extends NetResult {

	private DpUserContent content = new DpUserContent();

	public void setContent(DpUserContent content) {
		this.content = content;
	}

	public DpUserContent getContent() {
		return content;
	}

	public static User parse(String json) throws AppException {
		User res = new User();
		try {
			res = gson.fromJson(json, User.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			throw AppException.json(e);
		}
		// System.out.println(res);
		return res;
	}

	public class DpUserContent {
		private String access_token;// 授权令牌
		private String user_id;// 用户ID
		private String name;// 姓名
		private String phone;// 电话
		private String age;
		private String sex;// 性别
		private String birthday;// 生日
		private String headportrait;// 头像名字
		private String headportrait_suffix;// 头像后缀
		private String signature;// 个性签名

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getAge() {
			return age;
		}

		public void setAge(String age) {
			this.age = age;
		}

		public void setSignature(String signature) {
			this.signature = signature;
		}

		public String getSignature() {
			return signature;
		}

		public String getAccess_token() {
			return access_token;
		}

		public void setAccess_token(String access_token) {
			this.access_token = access_token;
		}

		public String getUser_id() {
			return user_id;
		}

		public void setUser_id(String user_id) {
			this.user_id = user_id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getHeadportrait() {
			return headportrait;
		}

		public void setHeadportrait(String headportrait) {
			this.headportrait = headportrait;
		}

		public String getSex() {
			return sex;
		}

		public void setSex(String sex) {
			this.sex = sex;
		}

		public String getBirthday() {
			return birthday;
		}

		public void setBirthday(String birthday) {
			this.birthday = birthday;
		}

		public void setHeadportrait_suffix(String headportrait_suffix) {
			this.headportrait_suffix = headportrait_suffix;
		}

		public String getHeadportrait_suffix() {
			return headportrait_suffix;
		}
	}

}
