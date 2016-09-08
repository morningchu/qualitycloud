package com.midian.qualitycloud.ui.testorganization;

import java.util.ArrayList;

import midian.baselib.base.BaseListActivity;
import midian.baselib.bean.NetResult;
import midian.baselib.shizhefei.view.listviewhelper.IDataSource;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;
import android.os.Bundle;

import com.midian.qualitycloud.R;
import com.midian.qualitycloud.bean.CheckOrgsBean.ContentCheckOrgs;
import com.midian.qualitycloud.datasource.QueryTestOrganizationDatasource;
import com.midian.qualitycloud.itemview.QueryTestOrganizationItemTpl;

/**
 * 检测机构地图查看列表
 * 
 * @author MIDIAN
 * 
 */
public class TestOrganizationListActivity extends
		BaseListActivity<ContentCheckOrgs> {
	QueryTestOrganizationDatasource organizationDatasource;

	BaseLibTopbarView mBaseLibTopbarView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		organizationDatasource = new QueryTestOrganizationDatasource(
				TestOrganizationListActivity.this, "", "");
		organizationDatasource.setIds(getIntent().getStringExtra("ids"));

		super.onCreate(savedInstanceState);
		mBaseLibTopbarView = (BaseLibTopbarView) findViewById(R.id.topbar);
		mBaseLibTopbarView
				.setLeftImageButton(R.drawable.icon_back,
						UIHelper.finish(_activity)).setLeftText("返回", null)
				.setTitle("检测机构");
	}

	@Override
	protected IDataSource<ArrayList<ContentCheckOrgs>> getDataSource() {
		// TODO Auto-generated method stub
		return organizationDatasource;
	}

	@Override
	protected Class getTemplateClass() {
		// TODO Auto-generated method stub
		return QueryTestOrganizationItemTpl.class;
	}

}
