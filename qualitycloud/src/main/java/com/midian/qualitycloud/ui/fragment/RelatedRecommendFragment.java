package com.midian.qualitycloud.ui.fragment;

import java.util.ArrayList;

import midian.baselib.base.BaseListFragment;
import midian.baselib.shizhefei.view.listviewhelper.IDataSource;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.midian.qualitycloud.bean.GeoProRecommendationsBean.ContentRecom;
import com.midian.qualitycloud.datasource.RelatedRecommendDatasource;
import com.midian.qualitycloud.itemview.RelatedRecommendItemTpl;

/**
 * 相关推荐
 * 
 * @author MIDIAN
 * 
 */
public class RelatedRecommendFragment extends BaseListFragment<ContentRecom> {
	private String geo_pro_type_id,geo_pro_id;
	RelatedRecommendDatasource mDatasource;
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		geo_pro_type_id = getArguments().getString("geo_pro_type_id");
		geo_pro_id = getArguments().getString("geo_id");
		mDatasource=new RelatedRecommendDatasource(_activity,geo_pro_type_id,geo_pro_id);
		super.onCreate(savedInstanceState);
	}
	@Override
	protected IDataSource<ArrayList<ContentRecom>> getDataSource() {
		// TODO Auto-generated method stub
		return mDatasource;
	}

	@Override
	protected Class getTemplateClass() {
		// TODO Auto-generated method stub
		return RelatedRecommendItemTpl.class;
	}

}
