package com.midian.qualitycloud.app;

import com.midian.configlib.ServerConstant;

/**
 * 接口链接
 * 
 * @author MIDIAN
 * 
 */
public class UrlConstant {
	public static final String BASEURL = ServerConstant.BASEURL;// 社区
	public static final String BASEFILEURL = ServerConstant.BASEFILEURL;// 社区

	/**
	 * 2.1.获取最新版本信息
	 */
	public static final String VERSION = BASEURL + "version";
	/**
	 * 2.2.获取系统配置信息
	 */
	public static final String SYSCONFLIST = BASEURL + "sys_conf_list";
	/**
	 * 2.3.获取测试包状态
	 */
	public static final String BETASTATUS = BASEURL + "beta_status";
	/**
	 * 3.1.关注
	 */
	public static final String COLLECT = BASEURL + "collect";
	/**
	 * 3.2.取消关注
	 */
	public static final String CANCELCOLLECT = BASEURL + "cancel_collect";
	/**
	 * 3.3.我的关注
	 */
	public static final String MYCOLLECTS = BASEURL + "my_collects";
	/**
	 * 4.1.设备详情
	 */
	public static final String FACILITYDETAIL = BASEURL + "facility_detail/";
	/**
	 * 4.2.地图设备列表
	 */
	public static final String MAPFACILITIES = BASEURL + "map_facilities";
	/**
	 * 4.3.附近设备列表
	 */
	public static final String NEARFACILITIES = BASEURL + "near_facilities";
	/**
	 * 4.4.扫描二维码获取设备详情
	 */
	public static final String SCANFACILITY = BASEURL + "scan_facility/";
	/**
	 * 4.5.禁用设备说明列表
	 */
	public static final String FACILITYFORBIDDENS = BASEURL + "facility_forbiddens/";
	/**
	 * 5.1.投诉设备
	 */
	public static final String COMPLAIN = BASEURL + "facility_complain";
	/**
	 * 5.2.投诉原因列表
	 */
	public static final String COMPLAINREASONS = BASEURL
			+ "facility_complain_reasons";
	/**
	 * 5.3.曝光台
	 */
	public static final String FACILITYCOMPLAIN = BASEURL + "facility_complain";
	/**
	 * 5.4.举报设备
	 */
	public static final String FACILITYREPORT = BASEURL + "facility_report";
	/**
	 * 5.5.设备举报原因列表
	 */
	public static final String FACILITYREPORTREASONS = BASEURL
			+ "facility_report_reasons";
	/**
	 * 6.1.地理标志分类列表
	 */
	public static final String GEOPROTYPES = BASEURL + "geo_pro_types";
	/**
	 * 6.2.地理标志产品列表
	 */
	public static final String GEOPROS = BASEURL + "geo_pros";
	/**
	 * 6.3.地理标志产品一级详情
	 */
	public static final String GEOPROFIRSTDETAIL = BASEURL
			+ "geo_pro_first_detail";
	/**
	 * 6.4.地理标志产品二级详情
	 */
	public static final String GEOPROSECONDDETAIL = BASEURL
			+ "geo_pro_second_detail";
	/**
	 * 6.5.地理标志产品使用企业列表
	 */
	public static final String GEOPROUSECOMPANIES = BASEURL
			+ "geo_pro_use_companies";
	/**
	 * 6.6.地理标志产品相关推荐列表
	 */
	public static final String GEOPRORECOMMENDATIONS = BASEURL
			+ "geo_pro_recommendations";
	/**
	 * 6.7.地理标志产品申报指南列表
	 */
	public static final String GEOPROREPORTS = BASEURL + "geo_pro_reports";
	/**
	 * 6.8.地理标志产品申报指南详情
	 */
	public static final String GEOPROREPORTDETAIL = BASEURL
			+ "geo_pro_report_detail/";
	/**
	 * 6.9.地理标志产品政策文件列表
	 */
	public static final String GEOPROPOLICIES = BASEURL + "geo_pro_policies";
	/**
	 * 7.1.名牌列表
	 */
	public static final String BRANDS = BASEURL + "brands";
	/**
	 * 7.2.名牌分布列表
	 */
	public static final String AREASTATS = BASEURL + "area_stats";
	/**
	 * 7.3.区域名牌列表
	 */
	public static final String AREABRANDS = BASEURL + "area_brands";
	/**
	 * 7.4.搜索名牌列表
	 */
	public static final String SEARCHBRANDS = BASEURL + "search_brands";
	/**
	 * 7.5.名牌申报指南列表
	 */
	public static final String BRANDREPORTS = BASEURL + "brand_reports";
	/**
	 * 7.6.名牌申报指南详情
	 */
	public static final String BRANDREPORTDETAIL = BASEURL
			+ "brand_report_detail/";
	/**
	 * 7.7.名牌详情
	 */
	public static final String BRANDDETAIL = BASEURL + "brand_detail";
	/**
	 * 8.1.检测机构类型列表
	 */
	public static final String CHECKFIELDS = BASEURL + "check_fields";
	/**
	 * 8.2.检测机构列表
	 */
	public static final String CHECKORGS = BASEURL + "check_orgs";
	/**
	 * 8.3.地图检测机构列表
	 */
	public static final String MAPCHECKORGS = BASEURL + "check_orgsMap";
	/**
	 * 8.4.检测机构详情check_areas
	 */
	public static final String CHECKORGDETAIL = BASEURL + "check_org_detail/";
	public static final String CHECKORGDETAIL_V2 = BASEURL + "check_org_detail/V2/";
	
	/**
	 * 8.5.检测机构项目列表
	 */
	public static final String CHECK_ORGS_PROS = BASEURL + "check_orgs_pros/";

	/**
	 * 8.6.检测机构地区列表
	 */
	public static final String CHECKAREAS = BASEURL + "check_areas";
	/**
	 * 8.7.检测机构资质条件列表
	 */
	public static final String CHECKQUALITIES = BASEURL + "check_qualities";
	
	/**
	 * 8.8.检测指标项目列表(新增)
	 */
	public static final String CHECK_PROS = BASEURL + "check_pros";
	/**
	 * 9.1.添加反馈
	 */
	public static final String ADVICE = BASEURL + "advice";

	/**
	 * 帮助中心
	 */
	public static final String HELP_URL = BASEURL + "advice";
	
	
}
