package com.midian.qualitycloud.ui.testorganization;

import java.util.ArrayList;

import com.midian.baselib.R;
import com.midian.qualitycloud.bean.CheckOrgListBean.ContentCheckOrg;
import com.midian.qualitycloud.datasource.TestOrganizationDatasource;
import com.midian.qualitycloud.itemview.TestOrganizationTpl;

import android.os.Bundle;

import midian.baselib.base.BaseListActivity;
import midian.baselib.bean.NetResult;
import midian.baselib.shizhefei.view.listviewhelper.IDataSource;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;

public class TestTypeListActivity extends BaseListActivity<ContentCheckOrg>{
	BaseLibTopbarView mBaseLibTopbarView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mBaseLibTopbarView=findView(R.id.topbar);
		mBaseLibTopbarView.setLeftImageButton(R.drawable.icon_back, UIHelper.finish(_activity)).setTitle("检测服务项目");
		mBaseLibTopbarView.setLeftText("返回", null);
	}

	@Override
	protected IDataSource<ArrayList<ContentCheckOrg>> getDataSource() {
		// TODO Auto-generated method stub
		return new TestOrganizationDatasource(_activity, getIntent().getStringExtra("check_org_id"), getIntent().getStringExtra("check_pro_type_id"));
	}

	@Override
	protected Class getTemplateClass() {
		// TODO Auto-generated method stub
		return TestOrganizationTpl.class;
	}

}
