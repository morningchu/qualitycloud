package com.midian.qualitycloud.datasource;

import java.util.ArrayList;
import java.util.Calendar;

import com.midian.qualitycloud.bean.BrandsBean;
import com.midian.qualitycloud.bean.BrandsBean.ContentBrand;
import com.midian.qualitycloud.utils.AppUtil;

import midian.baselib.app.AppContext;
import midian.baselib.base.BaseListDataSource;
import android.content.Context;

public class BrandCataloguePageDatasource extends
		BaseListDataSource<ContentBrand> {
	String year;
	String keywords;

	public BrandCataloguePageDatasource(Context context, String type,
			String keywords) {
		super(context);
		this.year = type;
		this.keywords = keywords;
	}

	public void setKeyWords(String keywords) {
		this.keywords = keywords;
	}

	@Override
	protected ArrayList<ContentBrand> load(int page) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<ContentBrand> morelist = new ArrayList<ContentBrand>();
//		Calendar c = Calendar.getInstance();
//		int i = c.get(Calendar.YEAR);
//		if (year.equals(String.valueOf(i-1))) {
			BrandsBean brandsBean = AppUtil.getQualityCloudApiClient(ac)
					.getBrands(year, keywords, (page + 1) + "",
							AppContext.PAGE_SIZE);
			if (brandsBean.isOK()) {
				if (brandsBean != null) {
					morelist.addAll(brandsBean.getContent());
					if (brandsBean.getContent().size() == 20) {
						hasMore = true;
						this.page = page;
					} else {
						hasMore = false;
					}
				}
			} else {
				ac.handleErrorCode(context, brandsBean.error_code);
			}
//		}
//		if (year.equals(String.valueOf(i - 2))) {
//			BrandsBean brandsBean = AppUtil.getQualityCloudApiClient(ac)
//					.getBrands(year, keywords, (page + 1)+"", AppContext.PAGE_SIZE);
//			if (brandsBean.isOK()) {
//				if (brandsBean != null) {
//					morelist.addAll(brandsBean.getContent());
//					if (brandsBean.getContent().size() == 20) {
//						hasMore = true;
//						this.page = page;
//					} else {
//						hasMore = false;
//					}
//				}
//			} else {
//				ac.handleErrorCode(context, brandsBean.error_code);
//			}
//		}
//		if (year.equals(String.valueOf(i - 3))) {
//			BrandsBean brandsBean = AppUtil.getQualityCloudApiClient(ac)
//					.getBrands(year, keywords,(page + 1) +"", AppContext.PAGE_SIZE);
//			if (brandsBean.isOK()) {
//				if (brandsBean != null) {
//					morelist.addAll(brandsBean.getContent());
//					if (brandsBean.getContent().size() == 20) {
//						hasMore = true;
//						this.page = page;
//					} else {
//						hasMore = false;
//					}
//				}
//			} else {
//				ac.handleErrorCode(context, brandsBean.error_code);
//			}
//		}

		return morelist;
	}
}
