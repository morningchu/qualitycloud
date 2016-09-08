package com.midian.qualitycloud.bean;

import com.google.gson.JsonSyntaxException;
import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 4.1.设备详情
 * 
 * @author Administrator
 * 
 */
public class FacilityDetailBean extends NetResult {
	public static FacilityDetailBean parse(String json) throws AppException {
		FacilityDetailBean res = new FacilityDetailBean();
		try {
			res = gson.fromJson(json, FacilityDetailBean.class);
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
		private String facility_id;// 设备id
		private String type;
		private String status;// 状态
		private String update_time;// 更新时间
		private String is_collected;
		private String lat;
		private String lon;
		private String report_url;

		private BaseFacility base;//
		private CheckFacility check;//
		private RepairFacility repair;//
		private String share_url;//

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getIs_collected() {
			return is_collected;
		}

		public void setIs_collected(String is_collected) {
			this.is_collected = is_collected;
		}

		public String getLat() {
			return lat;
		}

		public void setLat(String lat) {
			this.lat = lat;
		}

		public String getLon() {
			return lon;
		}

		public void setLon(String lon) {
			this.lon = lon;
		}

		public String getReport_url() {
			return report_url;
		}

		public void setReport_url(String report_url) {
			this.report_url = report_url;
		}

		public BaseFacility getBase() {
			return base;
		}

		public void setBase(BaseFacility base) {
			this.base = base;
		}

		public CheckFacility getCheck() {
			return check;
		}

		public void setCheck(CheckFacility check) {
			this.check = check;
		}

		public RepairFacility getRepair() {
			return repair;
		}

		public void setRepair(RepairFacility repair) {
			this.repair = repair;
		}

		public String getFacility_id() {
			return facility_id;
		}

		public void setFacility_id(String facility_id) {
			this.facility_id = facility_id;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getUpdate_time() {
			return update_time;
		}

		public void setUpdate_time(String update_time) {
			this.update_time = update_time;
		}

		public String getShare_url() {
			return share_url;
		}

		public void setShare_url(String share_url) {
			this.share_url = share_url;
		}
	}

	public class BaseFacility extends NetResult {
		private String name;// 设备识别码
		private String num;// 设备编号
		private String type;// 设备类型
		private String make_company;// 制造单位
		private String use_company;// 使用单位
		private String regist_office;// 登记机关
		private String years;// 使用年限
		private String install_address;

		public String getInstall_address() {
			return install_address;
		}

		public void setInstall_address(String install_address) {
			this.install_address = install_address;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getNum() {
			return num;
		}

		public void setNum(String num) {
			this.num = num;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getMake_company() {
			return make_company;
		}

		public void setMake_company(String make_company) {
			this.make_company = make_company;
		}

		public String getUse_company() {
			return use_company;
		}

		public void setUse_company(String use_company) {
			this.use_company = use_company;
		}

		public String getRegist_office() {
			return regist_office;
		}

		public void setRegist_office(String regist_office) {
			this.regist_office = regist_office;
		}

		public String getYears() {
			return years;
		}

		public void setYears(String years) {
			this.years = years;
		}

	}

	public class CheckFacility extends NetResult {
		private String pre_check_date;// 上次检验日期
		private String next_check_date;// 下次检验日期
		private String check_result;// 检验结论
		private String check_company;

		public String getCheck_company() {
			return check_company;
		}

		public void setCheck_company(String check_company) {
			this.check_company = check_company;
		}

		public String getPre_check_date() {
			return pre_check_date;
		}

		public void setPre_check_date(String pre_check_date) {
			this.pre_check_date = pre_check_date;
		}

		public String getNext_check_date() {
			return next_check_date;
		}

		public void setNext_check_date(String next_check_date) {
			this.next_check_date = next_check_date;
		}

		public String getCheck_result() {
			return check_result;
		}

		public void setCheck_result(String check_result) {
			this.check_result = check_result;
		}
	}

	public class RepairFacility extends NetResult {
		private String repair_company;// 维保单位
		private String repair_stars;// 维保单位星级
		private String pre_repair_date;// 上次维保日期
		private String next_repair_date;// 下次维保日期
		private String is_repair_plan;// 是否按计划维保

		public String getRepair_company() {
			return repair_company;
		}

		public void setRepair_company(String repair_company) {
			this.repair_company = repair_company;
		}

		public String getRepair_stars() {
			return repair_stars;
		}

		public void setRepair_stars(String repair_stars) {
			this.repair_stars = repair_stars;
		}

		public String getPre_repair_date() {
			return pre_repair_date;
		}

		public void setPre_repair_date(String pre_repair_date) {
			this.pre_repair_date = pre_repair_date;
		}

		public String getNext_repair_date() {
			return next_repair_date;
		}

		public void setNext_repair_date(String next_repair_date) {
			this.next_repair_date = next_repair_date;
		}

		public String getIs_repair_plan() {
			return is_repair_plan;
		}

		public void setIs_repair_plan(String is_repair_plan) {
			this.is_repair_plan = is_repair_plan;
		}
	}
}
