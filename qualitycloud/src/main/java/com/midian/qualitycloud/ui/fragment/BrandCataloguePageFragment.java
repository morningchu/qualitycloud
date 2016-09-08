package com.midian.qualitycloud.ui.fragment;

import java.util.ArrayList;
import midian.baselib.base.BaseListFragment;
import midian.baselib.shizhefei.view.listviewhelper.IDataSource;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.midian.qualitycloud.bean.BrandsBean.ContentBrand;
import com.midian.qualitycloud.datasource.BrandCataloguePageDatasource;
import com.midian.qualitycloud.datasource.QueryGIResultDatasource;
import com.midian.qualitycloud.itemview.BrandCataloguePageItemTpl;
import com.midian.qualitycloud.ui.geographical.QueryGIResultActivity;

/**
 * 名牌目录
 * 
 * @author MIDIAN
 * 
 */
public class BrandCataloguePageFragment extends BaseListFragment<ContentBrand> {
	BrandCataloguePageDatasource mDatasource;
	String type;
	String keywords;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		type = getArguments().getString("year");
		keywords = getArguments().getString("keywords");

		// search(keywords);
		mDatasource = new BrandCataloguePageDatasource(_activity, type,
				keywords);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	protected IDataSource<ArrayList<ContentBrand>> getDataSource() {
		// TODO Auto-generated method stub

		return mDatasource;
	}

	public void search(String key) {
		
		if(mDatasource==null||listViewHelper==null){
			return;
		}
		mDatasource.setKeyWords(key);
		listViewHelper.refresh();
	}

	@Override
	protected Class getTemplateClass() {
		// TODO Auto-generated method stub
		return BrandCataloguePageItemTpl.class;
	}

}
