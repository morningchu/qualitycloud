package com.midian.qualitycloud.bean;

import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;
import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 7.2.名牌分布列表
 * 
 * @author Administrator
 * 
 */
public class AreaStatsBean extends NetResult {
	public static AreaStatsBean parse(String json) throws AppException {
		AreaStatsBean res = new AreaStatsBean();
		try {
			res = gson.fromJson(json, AreaStatsBean.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			throw AppException.json(e);
		}
		return res;

	}

	private ArrayList<ContentAreaStats> content;

	public ArrayList<ContentAreaStats> getContent() {
		return content;
	}

	public void setContent(ArrayList<ContentAreaStats> content) {
		this.content = content;
	}

	public class ContentAreaStats extends NetResult {
		private String area_name;// 区域名称
		private String count;// 名牌数量

		public String getArea_name() {
			return area_name;
		}

		public void setArea_name(String area_name) {
			this.area_name = area_name;
		}

		public String getCount() {
			return count;
		}

		public void setCount(String count) {
			this.count = count;
		}

	}
}
