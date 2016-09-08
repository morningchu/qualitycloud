package com.midian.qualitycloud.bean;

import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;
import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * app
 * 
 * @author Administrator
 * 
 */
public class APPBean extends NetResult {
	public static APPBean parse(String json) throws AppException {
		APPBean res = new APPBean();
		try {
			res = gson.fromJson(json, APPBean.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			throw AppException.json(e);
		}
		return res;

	}

	private Content content;
	private String status;
	
	
	
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Content getContent() {
		return content;
	}


	public void setContent(Content content) {
		this.content = content;
	}


	public class Content  {

		private String code;// 名牌数量

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}
		
	}
}
