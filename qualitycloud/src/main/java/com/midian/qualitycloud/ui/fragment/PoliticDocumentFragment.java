package com.midian.qualitycloud.ui.fragment;

import java.util.ArrayList;

import midian.baselib.base.BaseListFragment;
import midian.baselib.shizhefei.view.listviewhelper.IDataSource;

import com.midian.qualitycloud.bean.GeoProPoliciesBean.ContentGeoProPolicies;
import com.midian.qualitycloud.bean.GeoProReportsBean.ContentReport;
import com.midian.qualitycloud.datasource.PoliticDocumentDatasource;
import com.midian.qualitycloud.itemview.PoliticDocumentItemTpl;

/**
 * 政策文件
 * @author Administrator
 *
 */
public class PoliticDocumentFragment extends
		BaseListFragment<ContentGeoProPolicies> {

	@Override
	protected IDataSource<ArrayList<ContentGeoProPolicies>> getDataSource() {
		// TODO Auto-generated method stub
		return new PoliticDocumentDatasource(_activity);
	}

	@Override
	protected Class getTemplateClass() {
		// TODO Auto-generated method stub
		return PoliticDocumentItemTpl.class;
	}

}
