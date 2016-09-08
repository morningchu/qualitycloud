package com.midian.qualitycloud.itemview;

import com.midian.qualitycloud.R;
import com.midian.qualitycloud.bean.CheckOrgDetailV2Bean;
import com.midian.qualitycloud.bean.CheckOrgListBean.ContentCheckOrg;
import com.midian.qualitycloud.ui.guizhoubrand.BrandDetailActivity;
import android.view.View.OnClickListener;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import midian.baselib.utils.UIHelper;
import midian.baselib.view.BaseTpl;

import com.midian.qualitycloud.ui.testorganization.TestTypeListActivity;
import com.midian.qualitycloud.ui.testorganization.UseStandardDetailActivity;

public class TestOrganizationTypeTpl extends BaseTpl<CheckOrgDetailV2Bean.CheckProTypes> implements
		OnClickListener {
	TextView name;

	CheckOrgDetailV2Bean.CheckProTypes checkOrg;
	private String check_pro_name;


	public TestOrganizationTypeTpl(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public TestOrganizationTypeTpl(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		name = (TextView) findViewById(R.id.name_item_testing);
//		standard_tv = (TextView) findViewById(R.id.standard_item_testing);
	}

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.item_testing_capability_type;
	}

	@Override
	public void setBean(CheckOrgDetailV2Bean.CheckProTypes bean, int position) {
		// TODO Auto-generated method stub
		this.checkOrg = bean;
		check_pro_name = checkOrg.getName();
		name.setText(check_pro_name);
		setBackgroundColor(Color.WHITE);
	
		// standard_tv.setText(standard);
		setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Bundle bundle = new Bundle();
//		bundle.putString("standard", standard);
		bundle.putString("check_org_id",checkOrg.getCheck_org_id());
		bundle.putString("check_pro_type_id",checkOrg.getCheck_pro_type_id());
		UIHelper.jump(_activity, TestTypeListActivity.class, bundle);

	}

}
