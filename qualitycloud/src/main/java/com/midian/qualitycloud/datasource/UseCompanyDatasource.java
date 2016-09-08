package com.midian.qualitycloud.datasource;

import java.util.ArrayList;

import midian.baselib.base.BaseListDataSource;
import midian.baselib.bean.NetResult;
import android.content.Context;

import com.midian.qualitycloud.bean.GeoProUseCompaniesBean;
import com.midian.qualitycloud.bean.GeoProUseCompaniesBean.ContentUse;
import com.midian.qualitycloud.utils.AppUtil;

public class UseCompanyDatasource extends BaseListDataSource<ContentUse> {
	String id;
	public UseCompanyDatasource(Context context,String geo_id) {
		super(context);
		this.id=geo_id;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ArrayList<ContentUse> load(int page) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<ContentUse> morelist = new ArrayList<ContentUse>();
		GeoProUseCompaniesBean geoProUseCompanies = AppUtil.getQualityCloudApiClient(ac).getGeoProUseCompanies(id);
		if(geoProUseCompanies.isOK()){
			if(geoProUseCompanies!=null){
				morelist.addAll(geoProUseCompanies.getContent());
				hasMore=false;
				this.page = page;
			}
		}else {
			ac.handleErrorCode(context, geoProUseCompanies.error_code);
	}
		
		return morelist;
	}
}
