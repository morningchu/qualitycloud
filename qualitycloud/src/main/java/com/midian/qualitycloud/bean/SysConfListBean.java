package com.midian.qualitycloud.bean;

import com.google.gson.JsonSyntaxException;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 2.2.获取系统配置信息
 * 
 * @author Administrator
 * 
 */
public class SysConfListBean extends NetResult {
	public static SysConfListBean parse(String json) throws AppException {
		SysConfListBean res = new SysConfListBean();
		try {
			res = gson.fromJson(json, SysConfListBean.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			throw AppException.json(e);
		}
		return res;

	}

	private ContentSys content;

	public ContentSys getContent() {
		return content;
	}

	public void setContent(ContentSys content) {
		this.content = content;
	}

	public class ContentSys extends NetResult {
		private String service_term_url;// 服务条款页面链接
		private String contact_phone;// 联系电话
		private String link_standard;// 地方标准
		private String link_geo_pro;// 地理标志关于
		private String link_brand;// 名牌关于
		private String link_help;// 帮助中心
		private String link_app_about;//app关于
		
		
		public String getLink_app_about() {
			return link_app_about;
		}

		public void setLink_app_about(String link_app_about) {
			this.link_app_about = link_app_about;
		}

		public String getLink_brand() {
			return link_brand;
		}

		public void setLink_brand(String link_brand) {
			this.link_brand = link_brand;
		}

		public String getLink_geo_pro() {
			return link_geo_pro;
		}

		public void setLink_geo_pro(String link_geo_pro) {
			this.link_geo_pro = link_geo_pro;
		}

		public String getLink_standard() {
			return link_standard;
		}

		public void setLink_standard(String link_standard) {
			this.link_standard = link_standard;
		}

		public String getLink_help() {
			return link_help;
		}

		public void setLink_help(String link_help) {
			this.link_help = link_help;
		}

		public String getService_term_url() {
			return service_term_url;
		}

		public void setService_term_url(String service_term_url) {
			this.service_term_url = service_term_url;
		}

		public String getContact_phone() {
			return contact_phone;
		}

		public void setContact_phone(String contact_phone) {
			this.contact_phone = contact_phone;
		}

	}
}
