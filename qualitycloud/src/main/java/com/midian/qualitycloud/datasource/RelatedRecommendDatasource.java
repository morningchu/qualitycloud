package com.midian.qualitycloud.datasource;

import java.util.ArrayList;

import midian.baselib.app.AppContext;
import midian.baselib.base.BaseListDataSource;
import midian.baselib.bean.NetResult;
import android.content.Context;

import com.midian.qualitycloud.bean.GeoProRecommendationsBean;
import com.midian.qualitycloud.bean.GeoProRecommendationsBean.ContentRecom;
import com.midian.qualitycloud.utils.AppUtil;

public class RelatedRecommendDatasource extends
		BaseListDataSource<ContentRecom> {
	String geo_pro_type_id;
	String geo_pro_id;

	public RelatedRecommendDatasource(Context context, String geo_pro_type_id,
			String geo_pro_id) {
		super(context);
		this.geo_pro_type_id = geo_pro_type_id;
		this.geo_pro_id = geo_pro_id;
	}

	@Override
	protected ArrayList<ContentRecom> load(int page) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<ContentRecom> morelist = new ArrayList<ContentRecom>();
		
		GeoProRecommendationsBean recommendations = AppUtil
				.getQualityCloudApiClient(ac).getGeoProRecommendations(
						geo_pro_type_id, geo_pro_id, (page+1) + "",
						AppContext.PAGE_SIZE);
		if (recommendations.isOK()) {
			if (recommendations != null) {
				morelist.addAll(recommendations.getContent());
				if (recommendations.getContent().size() == 0||recommendations.getContent().size()<20) {

					hasMore = false;
				} else {
					hasMore = true;
					this.page = page;
				}
				
			}
		}
		return morelist;
	}
}
