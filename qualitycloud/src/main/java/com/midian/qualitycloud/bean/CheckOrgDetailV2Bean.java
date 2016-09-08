package com.midian.qualitycloud.bean;

import java.util.List;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

import com.google.gson.JsonSyntaxException;

/**
 * 8.4.检测机构详情
 * 
 * @author Administrator
 * 
 */
public class CheckOrgDetailV2Bean extends NetResult {
	public static CheckOrgDetailV2Bean parse(String json) throws AppException {
		CheckOrgDetailV2Bean res = new CheckOrgDetailV2Bean();
		try {
			res = gson.fromJson(json, CheckOrgDetailV2Bean.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			throw AppException.json(e);
		}
		return res;

	}

	private ContentCheckDetail content;

	public ContentCheckDetail getContent() {
		return content;
	}

	public void setContent(ContentCheckDetail content) {
		this.content = content;
	}

	public class ContentCheckDetail extends NetResult {
		private String check_org_id;//检测机构id”,
		private String check_org_name;//检测机构名称”,
		private String area_name;//区域名称”,
		private String distance;//距离”,
		private String address;//地址”,
		private String tel;//电话号码”,
		private String lon;//经度”,
		private String lat;//纬度”,
		private String share_url;//分享链接”,
		private String is_collected;//是否收藏”,
		private String effective ;//资质是否有效”,
		private List<CheckProTypes>check_pro_types;
		
		public List<CheckProTypes> getCheck_pro_types() {
			return check_pro_types;
		}
		public void setCheck_pro_types(List<CheckProTypes> check_pro_types) {
			this.check_pro_types = check_pro_types;
		}
		public String getCheck_org_id() {
			return check_org_id;
		}
		public void setCheck_org_id(String check_org_id) {
			this.check_org_id = check_org_id;
		}
		public String getCheck_org_name() {
			return check_org_name;
		}
		public void setCheck_org_name(String check_org_name) {
			this.check_org_name = check_org_name;
		}
		public String getArea_name() {
			return area_name;
		}
		public void setArea_name(String area_name) {
			this.area_name = area_name;
		}
		public String getDistance() {
			return distance;
		}
		public void setDistance(String distance) {
			this.distance = distance;
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
		public String getLon() {
			return lon;
		}
		public void setLon(String lon) {
			this.lon = lon;
		}
		public String getLat() {
			return lat;
		}
		public void setLat(String lat) {
			this.lat = lat;
		}
		public String getShare_url() {
			return share_url;
		}
		public void setShare_url(String share_url) {
			this.share_url = share_url;
		}
		public String getIs_collected() {
			return is_collected;
		}
		public void setIs_collected(String is_collected) {
			this.is_collected = is_collected;
		}
		public String getEffective() {
			return effective;
		}
		public void setEffective(String effective) {
			this.effective = effective;
		}


	
	}
 static public class CheckProTypes extends NetResult{
	public CheckProTypes() {
		super();
	}
	public CheckProTypes(String check_org_id, String check_pro_type_id,
			String name) {
		super();
		this.check_org_id = check_org_id;
		this.check_pro_type_id = check_pro_type_id;
		this.name = name;
	}
	private String check_org_id;//检测机构id”,
	private String check_pro_type_id;//项目类别id”,
	private String name;//项目类别名称”,
	public String getCheck_org_id() {
		return check_org_id;
	}
	public void setCheck_org_id(String check_org_id) {
		this.check_org_id = check_org_id;
	}
	public String getCheck_pro_type_id() {
		return check_pro_type_id;
	}
	public void setCheck_pro_type_id(String check_pro_type_id) {
		this.check_pro_type_id = check_pro_type_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

}
}
