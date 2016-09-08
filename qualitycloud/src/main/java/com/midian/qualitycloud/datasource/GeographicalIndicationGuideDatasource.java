package com.midian.qualitycloud.datasource;

import java.util.ArrayList;

import com.midian.qualitycloud.bean.GeoProReportsBean;
import com.midian.qualitycloud.bean.GeoProReportsBean.ContentReport;
import com.midian.qualitycloud.utils.AppUtil;

import midian.baselib.app.AppContext;
import midian.baselib.base.BaseListDataSource;
import midian.baselib.bean.NetResult;
import android.content.Context;

public class GeographicalIndicationGuideDatasource extends
		BaseListDataSource<ContentReport> {

	public GeographicalIndicationGuideDatasource(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ArrayList<ContentReport> load(int page) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<ContentReport> morelist = new ArrayList<ContentReport>();
		
		GeoProReportsBean geoProReports = AppUtil.getQualityCloudApiClient(ac)
				.getGeoProReports((page+1) + "", AppContext.PAGE_SIZE);
		if (geoProReports.isOK()) {
			if (geoProReports != null) {
				morelist.addAll(geoProReports.getContent());
				if (geoProReports.getContent().size() == 0||geoProReports.getContent().size()<20) {
					hasMore = false;
				} else {
					hasMore = true;
					this.page = page;
				}
			}
		} else {
			ac.handleErrorCode(context, geoProReports.error_code);
		}
		return morelist;
	}
}
