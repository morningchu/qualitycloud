package com.midian.qualitycloud.api;

import midian.baselib.api.ApiCallback;
import midian.baselib.api.BaseApiClient;
import midian.baselib.app.AppContext;
import midian.baselib.bean.NetResult;
import android.text.TextUtils;

import com.midian.configlib.LoginConstant;
import com.midian.fastdevelop.afinal.http.AjaxParams;
import com.midian.fastdevelop.afinal.http.HttpHandler;
import com.midian.login.bean.UpdateReceiveStatusBean;
import com.midian.qualitycloud.app.Constant;
import com.midian.qualitycloud.app.UrlConstant;
import com.midian.qualitycloud.bean.APPBean;
import com.midian.qualitycloud.bean.AreaBrandsBean;
import com.midian.qualitycloud.bean.AreaStatsBean;
import com.midian.qualitycloud.bean.BetaStatusBean;
import com.midian.qualitycloud.bean.BrandDetailBean;
import com.midian.qualitycloud.bean.BrandReportDetailBean;
import com.midian.qualitycloud.bean.BrandReportsBean;
import com.midian.qualitycloud.bean.BrandsBean;
import com.midian.qualitycloud.bean.CheckAreasBean;
import com.midian.qualitycloud.bean.CheckOrgDetailBean;
import com.midian.qualitycloud.bean.CheckOrgDetailV2Bean;
import com.midian.qualitycloud.bean.CheckOrgListBean;
import com.midian.qualitycloud.bean.CheckOrgsBean;
import com.midian.qualitycloud.bean.CheckQualitiesBean;
import com.midian.qualitycloud.bean.CheckTypesBean;
import com.midian.qualitycloud.bean.ComplainBean;
import com.midian.qualitycloud.bean.ComplainReasonsBean;
import com.midian.qualitycloud.bean.FacilityComplainBean;
import com.midian.qualitycloud.bean.FacilityDetailBean;
import com.midian.qualitycloud.bean.FacilityForbiddensBean;
import com.midian.qualitycloud.bean.FacilityReportsBean;
import com.midian.qualitycloud.bean.GeoProFirstDetailBean;
import com.midian.qualitycloud.bean.GeoProPoliciesBean;
import com.midian.qualitycloud.bean.GeoProRecommendationsBean;
import com.midian.qualitycloud.bean.GeoProReportDetailBean;
import com.midian.qualitycloud.bean.GeoProReportsBean;
import com.midian.qualitycloud.bean.GeoProSecondDetailBean;
import com.midian.qualitycloud.bean.GeoProTypesBean;
import com.midian.qualitycloud.bean.GeoProUseCompaniesBean;
import com.midian.qualitycloud.bean.GeoProsBean;
import com.midian.qualitycloud.bean.MapCheckOrgsBean;
import com.midian.qualitycloud.bean.MapFacilitiesBean;
import com.midian.qualitycloud.bean.MyCollectsBean;
import com.midian.qualitycloud.bean.NearFacilitiesBean;
import com.midian.qualitycloud.bean.SearchBrandsBean;
import com.midian.qualitycloud.bean.SysConfListBean;
import com.midian.qualitycloud.bean.VersionBean;

/**
 * 网络请求客户端
 * 
 * @author moshouguan
 * 
 */
public class QualityCloudApiClient extends BaseApiClient {

	public QualityCloudApiClient(AppContext api) {
		// TODO Auto-generated constructor stub
		super(api);
	}

	static public void init(AppContext appcontext) {
		if (appcontext == null)
			return;
		appcontext.api.addApiClient(new QualityCloudApiClient(appcontext));
	}

	/**
	 * 1.11更改会员推送接收状态
	 * 
	 * @param user_id
	 * @param pwd
	 * @param callback
	 */
	public void getUpdateReceiveStatus(String receive_status,
			ApiCallback callback) {
		AjaxParams params = ac.getCAjaxParams();
		params.put("receive_status", receive_status);
		post(callback, LoginConstant.UPDATE_RECEIVE_STATUS, params,
				UpdateReceiveStatusBean.class, getMethodName(Thread
						.currentThread().getStackTrace()));
	}

	/**
	 * 2.1.获取最新版本信息
	 * 
	 * @author Administrator
	 * 
	 */
	public void getVersion(String force_status, ApiCallback callback) {
		AjaxParams params = ac.getCAjaxParams();
		params.put("force_status", force_status);
		get(callback, UrlConstant.VERSION, params, VersionBean.class,
				getMethodName(Thread.currentThread().getStackTrace()));
	}

	/**
	 * 2.2.获取系统配置信息
	 * 
	 * @author Administrator
	 * 
	 */
	public void getSysConfList(ApiCallback callback) {
		AjaxParams params = new AjaxParams();
		params.put("client_key", ac.getClientKey());
		get(callback, UrlConstant.SYSCONFLIST, params, SysConfListBean.class,
				getMethodName(Thread.currentThread().getStackTrace()));
	}

	/**
	 * 2.3.获取测试包状态
	 * 
	 * @author Administrator
	 * 
	 */
	public void getBetaStatus(String status, ApiCallback callback) {
		AjaxParams params = ac.getCAjaxParams();
		params.put("status", status);
		get(callback, UrlConstant.BETASTATUS, params, BetaStatusBean.class,
				getMethodName(Thread.currentThread().getStackTrace()));
	}

	/**
	 * 3.1.关注
	 * 
	 * @author Administrator
	 * 
	 */
	public void postCollect(String record_id, String type, ApiCallback callback) {
		AjaxParams params = ac.getCAjaxParams();
		ac.ischange = true;
		params.put("record_id", record_id);
		params.put("type", type);
		post(callback, UrlConstant.COLLECT, params, NetResult.class,
				getMethodName(Thread.currentThread().getStackTrace()));
	}

	/**
	 * 3.2.取消关注
	 * 
	 * @author Administrator
	 * 
	 */
	public void postCancelCollect(String record_id, String type,
			ApiCallback callback) {
		ac.ischange = true;
		AjaxParams params = ac.getCAjaxParams();
		params.put("record_id", record_id);
		params.put("type", type);
		post(callback, UrlConstant.CANCELCOLLECT, params, NetResult.class,
				getMethodName(Thread.currentThread().getStackTrace()));
	}

	/**
	 * 3.3.我的关注
	 * 
	 * @author Administrator
	 * @return
	 * 
	 */
	public MyCollectsBean getMyCollects(String type, String page, String size)
			throws Exception {
		AjaxParams params = ac.getCAjaxParams();
		params.put("type", type);
		params.put("page", page);
		params.put("size", size);
		return (MyCollectsBean) getSync(UrlConstant.MYCOLLECTS, params,
				MyCollectsBean.class);
	}

	/**
	 * 4.1.设备详情
	 * 
	 * @author Administrator
	 * 
	 */
	public void getFacilityDetail(String facility_id, ApiCallback callback) {
		AjaxParams params = ac.getCAjaxParams();
		get(callback, UrlConstant.FACILITYDETAIL + facility_id, params,
				FacilityDetailBean.class, getMethodName(Thread.currentThread()
						.getStackTrace()));
	}

	/**
	 * 4.2.地图设备列表
	 * 
	 * @author Administrator
	 * 
	 */
	public HttpHandler<Object> getMapFacilities(String type,
			String left_top_lon, String left_top_lat, String right_bottom_lon,
			String right_bottom_lat, ApiCallback callback) {
		AjaxParams params = ac.getClientKeyParams();
		params.put("type", type);
		params.put("left_top_lon", left_top_lon);
		params.put("left_top_lat", left_top_lat);
		params.put("right_bottom_lon", right_bottom_lon);
		params.put("right_bottom_lat", right_bottom_lat);
		return getForCloseHttp(callback, UrlConstant.MAPFACILITIES, params,
				MapFacilitiesBean.class, getMethodName(Thread.currentThread()
						.getStackTrace()));
	}

	/**
	 * 4.3.附近设备列表
	 * 
	 * @author Administrator
	 * 
	 */
	public NearFacilitiesBean getNearFacilities(String type, String keywords,
			String lon, String lat, String facility_ids, String page,
			String size) throws Exception {
		AjaxParams params = ac.getCAjaxParams();
		params.put("type", type);
		params.put("keywords", keywords);
		params.put("lon", lon);
		params.put("lat", lat);
		params.put("facility_ids", facility_ids);
		params.put("page", page);
		params.put("size", size);
		return (NearFacilitiesBean) postSync(UrlConstant.NEARFACILITIES,
				params, NearFacilitiesBean.class);
	}

	/**
	 * 4.4.扫描二维码获取设备详情
	 * 
	 * @author Administrator
	 * 
	 */
	public void getScanFacility(String code, ApiCallback callback) {
		AjaxParams params = ac.getCAjaxParams();
		get(callback, UrlConstant.SCANFACILITY + code, params,
				FacilityDetailBean.class, getMethodName(Thread.currentThread()
						.getStackTrace()));
	}

	/**
	 * 4.5.禁用设备说明列表
	 * 
	 * @author Administrator
	 * 
	 */
	public void getFacilityForbiddens(String facility_id, ApiCallback callback) {
		AjaxParams params = ac.getCAjaxParams();
		params.put("client_key", ac.getClientKey());
		params.put("facility_id", facility_id);
		get(callback, UrlConstant.FACILITYFORBIDDENS, params,
				FacilityForbiddensBean.class, getMethodName(Thread
						.currentThread().getStackTrace()));
	}

	/**
	 * 5.1.投诉设备
	 * 
	 * @author Administrator
	 * 
	 */
	public void postComplain(String record_id, String reason_id,
			ApiCallback callback) {
		AjaxParams params = ac.getCAjaxParams();
		params.put("record_id", record_id);
		params.put("reason_ids", reason_id);
		post(callback, UrlConstant.COMPLAIN, params, ComplainBean.class,
				getMethodName(Thread.currentThread().getStackTrace()));
	}

	/**
	 * 5.2.投诉原因列表
	 * 
	 * @author Administrator
	 * 
	 */
	public void getComplainRaeson(String type,ApiCallback callback) {
		AjaxParams params = new AjaxParams();
		params.put("client_key", ac.getClientKey());
		params.put("type", type);
		get(callback, UrlConstant.COMPLAINREASONS, params,
				ComplainReasonsBean.class, getMethodName(Thread.currentThread()
						.getStackTrace()));
	}

	/**
	 * 5.3.曝光台
	 * 
	 * @author Administrator
	 * 
	 */
	public FacilityComplainBean getFacilityComplain(String facility_id,
			ApiCallback callback) throws Exception {
		AjaxParams params = ac.getCAjaxParams();
		params.put("facility_id", facility_id);
		return (FacilityComplainBean) getSync(UrlConstant.FACILITYCOMPLAIN,
				params, FacilityComplainBean.class);
	}

	/**
	 * 5.4.举报设备
	 * 
	 * @author Administrator
	 * 
	 */
	public void postComplainReportComplain(String record_id,
			ApiCallback callback) {
		AjaxParams params = ac.getCAjaxParams();
		params.put("record_id", record_id);
		post(callback, UrlConstant.FACILITYREPORT, params, NetResult.class,
				getMethodName(Thread.currentThread().getStackTrace()));
	}

	/**
	 * 5.5.设备举报原因列表
	 * 
	 * @author Administrator
	 * 
	 */
	public void getFacilityReports(String facility_id, ApiCallback callback) {
		AjaxParams params = ac.getClientKeyParams();
		params.put("facility_id", facility_id);
		get(callback, UrlConstant.FACILITYREPORTREASONS, params,
				FacilityReportsBean.class, getMethodName(Thread.currentThread()
						.getStackTrace()));
	}

	/**
	 * 6.1.地理标志分类列表
	 * 
	 * @author Administrator
	 * 
	 */
	public void getGeoProTypes(ApiCallback callback) {
		AjaxParams params = ac.getCAjaxParams();
		get(callback, UrlConstant.GEOPROTYPES, params, GeoProTypesBean.class,
				getMethodName(Thread.currentThread().getStackTrace()));
	}

	/**
	 * 6.2.地理标志产品列表
	 * 
	 * @author Administrator
	 * 
	 */
	public GeoProsBean getGeoPros(String keywords, String geo_pro_type_id,
			String page, String size) throws Exception {
		AjaxParams params = ac.getCAjaxParams();
		if (!TextUtils.isEmpty(keywords))
			params.put("keywords", keywords);
		if (!TextUtils.isEmpty(geo_pro_type_id))
			params.put("geo_pro_type_id", geo_pro_type_id);
		params.put("page", page);
		params.put("size", size);
		return (GeoProsBean) getSync(UrlConstant.GEOPROS, params,
				GeoProsBean.class);
	}

	/**
	 * 6.3.地理标志产品一级详情
	 * 
	 * @author Administrator
	 * 
	 */
	public void getGeoProFirstDetail(String geo_pro_id, ApiCallback callback) {
		AjaxParams params = ac.getCAjaxParams();
		params.put("geo_pro_id", geo_pro_id);
		get(callback, UrlConstant.GEOPROFIRSTDETAIL, params,
				GeoProFirstDetailBean.class, getMethodName(Thread
						.currentThread().getStackTrace()));
	}

	/**
	 * 6.4.地理标志产品二级详情
	 * 
	 * @author Administrator
	 * 
	 */
	public void getGeoProSecondDetail(String geo_pro_id, ApiCallback callback) {
		AjaxParams params = ac.getCAjaxParams();
		params.put("geo_pro_id", geo_pro_id);
		get(callback, UrlConstant.GEOPROSECONDDETAIL, params,
				GeoProSecondDetailBean.class, getMethodName(Thread
						.currentThread().getStackTrace()));
	}

	/**
	 * 6.5.地理标志产品使用企业列表
	 * 
	 * @author Administrator
	 * 
	 */
	public GeoProUseCompaniesBean getGeoProUseCompanies(String geo_pro_id)
			throws Exception {
		AjaxParams params = ac.getCAjaxParams();
		params.put("geo_pro_id", geo_pro_id);
		return (GeoProUseCompaniesBean) getSync(UrlConstant.GEOPROUSECOMPANIES,
				params, GeoProUseCompaniesBean.class);
	}

	/**
	 * 6.6.地理标志产品相关推荐列表
	 * 
	 * @author Administrator
	 * 
	 */
	public GeoProRecommendationsBean getGeoProRecommendations(
			String geo_pro_type_id, String geo_pro_id, String page, String size)
			throws Exception {
		AjaxParams params = ac.getCAjaxParams();
		params.put("geo_pro_type_id", geo_pro_type_id);
		params.put("geo_pro_id", geo_pro_id);
		params.put("page", page);
		params.put("size", size);
		return (GeoProRecommendationsBean) getSync(
				UrlConstant.GEOPRORECOMMENDATIONS, params,
				GeoProRecommendationsBean.class);
	}

	/**
	 * 6.7.地理标志产品申报指南列表
	 * 
	 * @author Administrator
	 * 
	 */
	public GeoProReportsBean getGeoProReports(String page, String size)
			throws Exception {
		AjaxParams params = ac.getCAjaxParams();
		params.put("page", page);
		params.put("size", size);
		return (GeoProReportsBean) getSync(UrlConstant.GEOPROREPORTS, params,
				GeoProReportsBean.class);
	}

	/**
	 * 6.8.地理标志产品申报指南详情
	 * 
	 * @author Administrator
	 * 
	 */
	public void getGeoProReportDetail(String id, ApiCallback callback) {
		AjaxParams params = new AjaxParams();
		params.put("client_key", ac.getClientKey());
		get(callback, UrlConstant.GEOPROREPORTDETAIL + id, params,
				GeoProReportDetailBean.class, getMethodName(Thread
						.currentThread().getStackTrace()));
	}

	/**
	 * 6.9.地理标志产品政策文件列表
	 * 
	 * @author Administrator
	 * 
	 */
	public GeoProPoliciesBean getGeoProPolicies(String page, String size)
			throws Exception {
		AjaxParams params = new AjaxParams();
		params.put("client_key", ac.getClientKey());
		params.put("page", page);
		params.put("size", size);
		return (GeoProPoliciesBean) getSync(UrlConstant.GEOPROPOLICIES, params,
				GeoProPoliciesBean.class);
	}

	/**
	 * 7.1.名牌列表
	 * 
	 * @author Administrator
	 * 
	 */
	public BrandsBean getBrands(String year,String keywords, String page, String size)
			throws Exception {
		AjaxParams params = new AjaxParams();
		params.put("client_key", ac.getClientKey());
		params.put("year", year);
		params.put("keywords", keywords);
		params.put("page", page);
		params.put("size", size);
		return (BrandsBean) getSync(UrlConstant.BRANDS, params,
				BrandsBean.class);
	}

	/**
	 * 7.2.名牌分布列表
	 * 
	 * @author Administrator
	 * 
	 */
	public AreaStatsBean getAreaStats() throws Exception {
		AjaxParams params = new AjaxParams();
		params.put("client_key", ac.getClientKey());
		return (AreaStatsBean) getSync(UrlConstant.AREASTATS, params,
				AreaStatsBean.class);
	}

	public void getAreaStats(ApiCallback callback) {
		AjaxParams params = new AjaxParams();
		params.put("client_key", ac.getClientKey());
		get(callback, UrlConstant.AREASTATS, params, AreaStatsBean.class,
				getMethodName(Thread.currentThread().getStackTrace()));
	}

	/**
	 * 7.3.区域名牌列表
	 * 
	 * @author Administrator
	 * 
	 */
	public AreaBrandsBean getAreaBrands(String area_name, String page,
			String size) throws Exception {
		AjaxParams params = ac.getCAjaxParams();
		params.put("area_name", area_name);
		params.put("page", page);
		params.put("size", size);
		return (AreaBrandsBean) getSync(UrlConstant.AREABRANDS, params,
				AreaBrandsBean.class);
	}

	/**
	 * 7.4.搜索名牌列表
	 * 
	 * @author Administrator
	 * 
	 */
	public SearchBrandsBean getSearchBrands(String keywords, String page,
			String size) throws Exception {
		AjaxParams params = ac.getCAjaxParams();
		params.put("keywords", keywords);
		params.put("page", page);
		params.put("size", size);
		return (SearchBrandsBean) getSync(UrlConstant.SEARCHBRANDS, params,
				SearchBrandsBean.class);
	}

	/**
	 * 7.5.名牌申报指南列表
	 * 
	 * @author Administrator
	 * 
	 */
	public BrandReportsBean getBrandReports(String page, String size)
			throws Exception {
		AjaxParams params = ac.getCAjaxParams();
		params.put("page", page);
		params.put("size", size);
		return (BrandReportsBean) getSync(UrlConstant.BRANDREPORTS, params,
				BrandReportsBean.class);
	}

	/**
	 * 7.6.名牌申报指南详情
	 * 
	 * @author Administrator
	 * 
	 */
	public void getBrandReportDetail(String id, ApiCallback callback) {
		AjaxParams params = new AjaxParams();
		params.put("client_key", ac.getClientKey());
		get(callback, UrlConstant.BRANDREPORTDETAIL + id, params,
				BrandReportDetailBean.class, getMethodName(Thread
						.currentThread().getStackTrace()));
	}

	/**
	 * 7.7.名牌详情
	 * 
	 * @author Administrator
	 * 
	 */
	public void getBrandDetail(String brand_id, ApiCallback callback) {
		AjaxParams params = ac.getCAjaxParams();
		params.put("brand_id", brand_id);
		get(callback, UrlConstant.BRANDDETAIL, params, BrandDetailBean.class,
				getMethodName(Thread.currentThread().getStackTrace()));
	}

	/**
	 * 8.1.检测机构类型列表
	 * 
	 * @author Administrator
	 * 
	 */
	public void getCheckFields(ApiCallback callback) {
		AjaxParams params = new AjaxParams();
		params.put("client_key", ac.getClientKey());
		get(callback, UrlConstant.CHECKFIELDS, params, CheckTypesBean.class,
				getMethodName(Thread.currentThread().getStackTrace()));
	}

	// /**
	// * 8.2.检测机构列表
	// *
	// * @author Administrator
	// *
	// */
	// public CheckOrgsBean getCheckOrgs(String keywords, String field_id,
	// String area_id, String quality_id, String lon, String lat)
	// throws Exception {
	// AjaxParams params = ac.getCAjaxParams();
	// params.put("keywords", keywords);
	// params.put("field_id", field_id);
	// params.put("area_id", area_id);
	// params.put("quality_id", quality_id);
	// params.put("lon", lon);
	// params.put("lat", lat);
	// return (CheckOrgsBean) getSync(UrlConstant.CHECKORGS, params,
	// CheckOrgsBean.class);
	// }
	/**
	 * 8.2.检测机构列表
	 * 
	 * @author Administrator
	 * 
	 */
	public CheckOrgsBean getCheckOrgs(String keywords, String field_id,
			String area_id, String quality_id, String check_org_ids,
			String lon, String lat, String type, String page, String size)
			throws Exception {
		AjaxParams params = new AjaxParams();
		params.put("client_key", ac.getClientKey());
		params.put("keywords", keywords);
		params.put("field_id", field_id);
		params.put("area_id", area_id);
		params.put("quality_id", quality_id);
		params.put("check_org_ids", check_org_ids);
		params.put("lon", lon);
		params.put("lat", lat);
		params.put("type", type);
		params.put("page", page);
		params.put("size", size);
		return (CheckOrgsBean) postSync(UrlConstant.CHECKORGS, params,
				CheckOrgsBean.class);
	}

	/**
	 * 8.3.地图检测机构列表
	 * 
	 * @author Administrator
	 * 
	 */
	public HttpHandler<Object> getmAPCheckOrgs(String left_top_lon,
			String left_top_lat, String right_bottom_lon,
			String right_bottom_lat, ApiCallback callback) {
		AjaxParams params = ac.getCAjaxParams();
		params.put("left_top_lon", left_top_lon);
		params.put("left_top_lat", left_top_lat);
		params.put("right_bottom_lon", right_bottom_lon);
		params.put("right_bottom_lat", right_bottom_lat);

		return getForCloseHttp(callback, UrlConstant.MAPCHECKORGS, params,
				MapCheckOrgsBean.class, getMethodName(Thread.currentThread()
						.getStackTrace()));
	}

	/**
	 * 8.4.检测机构详情
	 * 
	 * @author Administrator
	 * 
	 */
	public void getCheckOrgDetail(String id, ApiCallback callback) {
		AjaxParams params = ac.getCAjaxParams();
		get(callback, UrlConstant.CHECKORGDETAIL + id, params,
				CheckOrgDetailBean.class, getMethodName(Thread.currentThread()
						.getStackTrace()));
	}
	
	public void getCheckOrgDetail_V2(String id,String lon,String lat, ApiCallback callback) {
		AjaxParams params = ac.getCAjaxParams();
		params.put("lon", lon);
		params.put("lat", lat);
		get(callback, UrlConstant.CHECKORGDETAIL_V2 + id, params,
				CheckOrgDetailV2Bean.class, getMethodName(Thread.currentThread()
						.getStackTrace()));
	}

	/**
	 * 8.5.检测机构项目列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public CheckOrgListBean getCheckOrgsPros(String check_org_id,
			String keywords, String page) throws Exception {
		AjaxParams params = ac.getCAjaxParams();
		params.put("check_org_id", check_org_id);
		if (!TextUtils.isEmpty(keywords))
			params.put("keywords", keywords);
		params.put("page", page);
		params.put("size", Constant.SIZE);
		return (CheckOrgListBean) getSync(UrlConstant.CHECK_ORGS_PROS, params,
				CheckOrgListBean.class);
	}

	/**
	 * 8.6.检测机构地区列表
	 * 
	 * @author Administrator
	 * 
	 */
	public void getCheckAreas(ApiCallback callback) {
		AjaxParams params = new AjaxParams();
		params.put("client_key", ac.getClientKey());
		get(callback, UrlConstant.CHECKAREAS, params, CheckAreasBean.class,
				getMethodName(Thread.currentThread().getStackTrace()));
	}

	/**
	 * 8.7.检测机构资质条件列表
	 * 
	 * @author Administrator
	 * 
	 */
	public void getCheckQualities(ApiCallback callback) {
		AjaxParams params = new AjaxParams();
		params.put("client_key", ac.getClientKey());
		get(callback, UrlConstant.CHECKQUALITIES, params,
				CheckQualitiesBean.class, getMethodName(Thread.currentThread()
						.getStackTrace()));
	}
	
	/**
	 * 8.8.检测机构项目列表
	 * @param check_org_id
	 * @param check_pro_type_id
	 * @param page
	 * @return
	 * @throws Exception
	 */
	
	public CheckOrgListBean getCheckOrgsProsContent(String check_org_id,
			String check_pro_type_id, String page) throws Exception {
		AjaxParams params = ac.getCAjaxParams();
		params.put("check_org_id", check_org_id);
		params.put("check_pro_type_id", check_pro_type_id);
		params.put("page", page);
		params.put("size", "20");
		return (CheckOrgListBean) getSync(UrlConstant.CHECK_ORGS_PROS, params,
				CheckOrgListBean.class);
	}

	/**
	 * 9.1.添加反馈
	 * 
	 * @param content
	 * @param callback
	 */
	public void postAdvice(String content, ApiCallback callback) {
		AjaxParams params = ac.getCAjaxParams();
		params.put("content", content);
		post(callback, UrlConstant.ADVICE, params, NetResult.class,
				getMethodName(Thread.currentThread().getStackTrace()));
	}
	
	
}
