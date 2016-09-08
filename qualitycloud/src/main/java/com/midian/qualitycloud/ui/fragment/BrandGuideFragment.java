package com.midian.qualitycloud.ui.fragment;

import java.util.ArrayList;

import com.midian.qualitycloud.bean.BrandReportsBean.ContentBrand;
import com.midian.qualitycloud.datasource.BrandGuideDatasource;
import com.midian.qualitycloud.itemview.BrandGuideItemTpl;

import midian.baselib.base.BaseListFragment;
import midian.baselib.bean.NetResult;
import midian.baselib.shizhefei.view.listviewhelper.IDataSource;

/**
 * 申报指南
 * 
 * @author MIDIAN
 * 
 */
public class BrandGuideFragment extends BaseListFragment<ContentBrand> {

	@Override
	protected IDataSource<ArrayList<ContentBrand>> getDataSource() {
		// TODO Auto-generated method stub
		return new BrandGuideDatasource(_activity);
	}

	@Override
	protected Class getTemplateClass() {
		// TODO Auto-generated method stub
		return BrandGuideItemTpl.class;
	}

}
