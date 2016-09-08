package com.midian.qualitycloud.ui.fragment;

import java.util.ArrayList;

import midian.baselib.base.BaseListFragment;
import midian.baselib.shizhefei.view.listviewhelper.IDataSource;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.midian.qualitycloud.bean.GeoProUseCompaniesBean.ContentUse;
import com.midian.qualitycloud.datasource.UseCompanyDatasource;
import com.midian.qualitycloud.itemview.UseCompanyItemTpl;

/**
 * 使用公司
 * 
 * @author MIDIAN
 * 
 */
public class UseCompanyFragment extends BaseListFragment<ContentUse> {
	String geo_id;
	UseCompanyDatasource companyDatasource;
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		geo_id = getArguments().getString("geo_id");
		companyDatasource = new UseCompanyDatasource(_activity, geo_id);
		super.onCreate(savedInstanceState);
	}
	@Override
	protected IDataSource<ArrayList<ContentUse>> getDataSource() {
		// TODO Auto-generated method stub
		
		return companyDatasource;
	}

	@Override
	protected Class getTemplateClass() {
		// TODO Auto-generated method stub
		return UseCompanyItemTpl.class;
	}

}
