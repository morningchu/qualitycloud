package com.midian.qualitycloud.ui.fragment;

import java.util.ArrayList;

import com.midian.qualitycloud.bean.GeoProReportsBean.ContentReport;
import com.midian.qualitycloud.datasource.GeographicalIndicationGuideDatasource;
import com.midian.qualitycloud.itemview.GeographicalIndicationGuideItemTpl;

import midian.baselib.base.BaseListFragment;
import midian.baselib.shizhefei.view.listviewhelper.IDataSource;

/**
 * 地理标识申报指南
 * 
 * @author MIDIAN
 * 
 */
public class GIGuideFragment extends BaseListFragment<ContentReport> {

	@Override
	protected IDataSource<ArrayList<ContentReport>> getDataSource() {
		// TODO Auto-generated method stub
		return new GeographicalIndicationGuideDatasource(_activity);
	}

	@Override
	protected Class getTemplateClass() {
		// TODO Auto-generated method stub
		return GeographicalIndicationGuideItemTpl.class;
	}

}
