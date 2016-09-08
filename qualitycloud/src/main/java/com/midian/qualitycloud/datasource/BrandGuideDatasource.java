package com.midian.qualitycloud.datasource;

import java.util.ArrayList;

import com.midian.qualitycloud.bean.BrandReportsBean;
import com.midian.qualitycloud.bean.BrandReportsBean.ContentBrand;
import com.midian.qualitycloud.utils.AppUtil;

import midian.baselib.app.AppContext;
import midian.baselib.base.BaseListDataSource;
import midian.baselib.bean.NetResult;
import android.content.Context;

public class BrandGuideDatasource extends BaseListDataSource<ContentBrand> {

	public BrandGuideDatasource(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ArrayList<ContentBrand> load(int page) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<ContentBrand> moreList = new ArrayList<ContentBrand>();
		
		BrandReportsBean mReportsBean = AppUtil.getQualityCloudApiClient(ac)
				.getBrandReports((page+1) + "", AppContext.PAGE_SIZE);
		if (mReportsBean.isOK()) {
			if (mReportsBean != null) {
				moreList.addAll(mReportsBean.getContent());
				System.out.println("-----"+mReportsBean.getContent().size());
				if (mReportsBean.getContent().size() == 0||mReportsBean.getContent().size()<20) {
					hasMore = false;
				} else {
					this.page = page;
					hasMore = true;
				}

			}
		} else {
			ac.handleErrorCode(context, mReportsBean.error_code);
		}
		// morelist.add(new NetResult());
		// morelist.add(new NetResult());
		return moreList;
	}
}
