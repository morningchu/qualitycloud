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
public class Userse extends NetResult {

	private UserContent content = new UserContent();

	public void setContent(UserContent content) {
		this.content = content;
	}

	public UserContent getContent() {
		return content;
	}

	public static Userse parse(String json) throws AppException {
		Userse res = new Userse();
		try {
			res = gson.fromJson(json, Userse.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			throw AppException.json(e);
		}
		// System.out.println(res);
		return res;
	}

	public class UserContent {
		private String access_token;// 授权令牌
		private String user_id;// 用户ID
		private String name;// 姓名
		private String head_pic;// 头像文件名称
		private String head_pic_suffix;// 头像后缀
		// private String phone;// 电话
		// private String age;
		// private String sex;// 性别
		// private String birthday;// 生日
		// private String headportrait;// 头像名字
		// private String headportrait_suffix;// 头像后缀
		// private String signature;// 个性签名

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

		public String getHead_pic() {
			return head_pic;
		}

		public void setHead_pic(String head_pic) {
			this.head_pic = head_pic;
		}

		public String getHead_pic_suffix() {
			return head_pic_suffix;
		}

		public void setHead_pic_suffix(String head_pic_suffix) {
			this.head_pic_suffix = head_pic_suffix;
		}

	}

}

