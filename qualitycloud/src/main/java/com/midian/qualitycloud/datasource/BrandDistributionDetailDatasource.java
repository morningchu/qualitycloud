package com.midian.qualitycloud.datasource;

import java.util.ArrayList;

import com.midian.qualitycloud.bean.AreaBrandsBean;
import com.midian.qualitycloud.bean.AreaBrandsBean.ContentAreaBrands;
import com.midian.qualitycloud.itemview.InformationItem;
import com.midian.qualitycloud.utils.AppUtil;

import midian.baselib.app.AppContext;
import midian.baselib.base.BaseListDataSource;
import android.content.Context;

public class BrandDistributionDetailDatasource extends
		BaseListDataSource<InformationItem> {

	String count;
	String area_name;

	public BrandDistributionDetailDatasource(Context context, String count,
			String area_name) {
		super(context);
		this.count = count;
		this.area_name = area_name;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ArrayList<InformationItem> load(int page) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<InformationItem> morelist = new ArrayList<InformationItem>();
		AreaBrandsBean areaStatsBean = AppUtil
				.getQualityCloudApiClient(ac)
				.getAreaBrands(area_name, (page + 1) + "", AppContext.PAGE_SIZE);
		if (areaStatsBean.isOK()) {
			if (areaStatsBean != null) {
				for (AreaBrandsBean.Brands brands : areaStatsBean.getContent()
						.getBrands()) {
					InformationItem mInformationItem = new InformationItem();
					mInformationItem.banners = brands;
					morelist.add(mInformationItem);
				}
				if (areaStatsBean.getContent().getBrands().size() == 20
						&& areaStatsBean.getContent() != null
						&& areaStatsBean.getContent().getBrands() != null) {
					hasMore = true;
					this.page = page;
				} else {
					hasMore = false;
				}
			}
		} else {
			ac.handleErrorCode(context, areaStatsBean.error_code);
		}
		return morelist;
	}
}
