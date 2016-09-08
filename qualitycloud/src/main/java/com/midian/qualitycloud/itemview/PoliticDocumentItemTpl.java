package com.midian.qualitycloud.itemview;

import midian.baselib.utils.UIHelper;
import midian.baselib.view.BaseTpl;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.midian.qualitycloud.R;
import com.midian.qualitycloud.bean.GeoProPoliciesBean.ContentGeoProPolicies;
import com.midian.qualitycloud.bean.GeoProReportsBean.ContentReport;
import com.midian.qualitycloud.ui.geographical.GIGuideDetailActivity;
import com.midian.qualitycloud.ui.geographical.PoliticDocumentActivity;

public class PoliticDocumentItemTpl extends BaseTpl<ContentGeoProPolicies>
		implements OnClickListener {
	int position;
	private TextView name_tv;
	private TextView time_tv;
	private ContentGeoProPolicies policies;
	private String name;
	private String time;
	private String url;

	public PoliticDocumentItemTpl(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public PoliticDocumentItemTpl(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		setOnClickListener(this);
		name_tv = (TextView) findViewById(R.id.name);
		time_tv = (TextView) findViewById(R.id.time);
	}

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.item_geographical_indication_guide;
	}

	@Override
	public void setBean(ContentGeoProPolicies bean, int position) {
		// TODO Auto-generated method stub
		this.position = position;
		this.policies = bean;
		setBackgroundResource(R.drawable.default_button_white);
		name_tv.setText(bean.getTitle());
		time_tv.setText(bean.getAdd_date());
		name = bean.getTitle();
		time = bean.getAdd_date();
		url = bean.getContent_url();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Bundle b = new Bundle();
		b.putInt("type", position % 2);
		b.putString("name", name);
		b.putString("time", time);
		b.putString("url", url);
		UIHelper.jump(_activity, PoliticDocumentActivity.class, b);
	}

}
