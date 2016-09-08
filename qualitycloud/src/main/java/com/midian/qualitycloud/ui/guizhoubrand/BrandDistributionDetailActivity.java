package com.midian.qualitycloud.ui.guizhoubrand;

import java.util.ArrayList;

import midian.baselib.base.BaseListActivity;
import midian.baselib.shizhefei.view.listviewhelper.IDataSource;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;
import android.graphics.Color;
import android.os.Bundle;

import com.midian.qualitycloud.R;
import com.midian.qualitycloud.bean.AreaBrandsBean.ContentAreaBrands;
import com.midian.qualitycloud.datasource.BrandDistributionDetailDatasource;
import com.midian.qualitycloud.itemview.BrandDistributionDetailItemTpl;
import com.midian.qualitycloud.itemview.InformationItem;

/**
 * 贵州名牌分布地区详情
 * 
 * @author MIDIAN
 * 
 */

public class BrandDistributionDetailActivity extends
		BaseListActivity<InformationItem> {
	BaseLibTopbarView mBaseLibTopbarView;
	String title = "";
	String count;
	BrandDistributionDetailDatasource mDatasource;
	private String area_name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		count = getIntent().getStringExtra("count");
		area_name = getIntent().getStringExtra("name");

		mDatasource = new BrandDistributionDetailDatasource(
				BrandDistributionDetailActivity.this, count, area_name);
		super.onCreate(savedInstanceState);

		// title = area_name.replace("\n", "").trim();
		initTitle();
	}

	public void initTitle() {
		mBaseLibTopbarView = (BaseLibTopbarView) findViewById(R.id.topbar);
		mBaseLibTopbarView
				.setLeftImageButton(R.drawable.icon_back,
						UIHelper.finish(_activity)).setLeftText("返回", null)
				.setTitle(area_name).setMode(BaseLibTopbarView.MODE_1)
				.getTitle_tv().setTextColor(Color.parseColor("#FFFFFF"));
		// mBaseLibTopbarView.setBackgroundResource(R.color.white);
		mBaseLibTopbarView.getLeft_tv().setTextColor(
				Color.parseColor("#ffffff"));
	}

	@Override
	protected IDataSource<ArrayList<InformationItem>> getDataSource() {
		// TODO Auto-generated method stub
		return mDatasource;
	}

	@Override
	protected Class getTemplateClass() {
		// TODO Auto-generated method stub
		return BrandDistributionDetailItemTpl.class;
	}

}
