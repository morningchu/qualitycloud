package com.midian.qualitycloud.datasource;

import java.util.ArrayList;

import com.midian.qualitycloud.bean.GeoProPoliciesBean;
import com.midian.qualitycloud.bean.GeoProPoliciesBean.ContentGeoProPolicies;
import com.midian.qualitycloud.bean.GeoProReportsBean;
import com.midian.qualitycloud.bean.GeoProReportsBean.ContentReport;
import com.midian.qualitycloud.utils.AppUtil;

import midian.baselib.app.AppContext;
import midian.baselib.base.BaseListDataSource;
import midian.baselib.bean.NetResult;
import android.content.Context;

public class PoliticDocumentDatasource extends
		BaseListDataSource<ContentGeoProPolicies> {

	public PoliticDocumentDatasource(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ArrayList<ContentGeoProPolicies> load(int page) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<ContentGeoProPolicies> morelist = new ArrayList<ContentGeoProPolicies>();
		
		GeoProPoliciesBean geoProPolicies = AppUtil
				.getQualityCloudApiClient(ac).getGeoProPolicies((page+1) + "",
						AppContext.PAGE_SIZE);
		if (geoProPolicies.isOK()) {
			if (geoProPolicies != null) {
				morelist.addAll(geoProPolicies.getContent());
				if (geoProPolicies.getContent().size() == 0||geoProPolicies.getContent().size()<20) {
					hasMore = false;
				} else {
					hasMore = true;
					this.page = page;
				}
//				this.page++;
			}
		} else {
			ac.handleErrorCode(context, geoProPolicies.error_code);
		}
		return morelist;
	}
}
