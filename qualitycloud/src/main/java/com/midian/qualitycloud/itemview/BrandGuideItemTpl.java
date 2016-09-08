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
import com.midian.qualitycloud.bean.BrandReportsBean.ContentBrand;
import com.midian.qualitycloud.ui.geographical.GIGuideDetailActivity;
import com.midian.qualitycloud.ui.guizhoubrand.BrandReportActivity;
import com.midian.qualitycloud.ui.guizhoubrand.GuizhouBrandActivity;

public class BrandGuideItemTpl extends BaseTpl<ContentBrand> implements
		OnClickListener {
	int position;
	private TextView name_tv, content,time;
	private String report_id;

	public BrandGuideItemTpl(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public BrandGuideItemTpl(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initView() {
		setOnClickListener(this);
		name_tv = findView(R.id.name);
		 time = findView(R.id.time);
		content = findView(R.id.content);
	}

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.item_brand_guide;
	}

	@Override
	public void setBean(ContentBrand bean, int position) {
		this.position = position;
		setBackgroundResource(R.drawable.default_button_white);
		name_tv.setText(bean.getTitle());
		 time.setText(bean.getAdd_date());
		// content.setText(bean.getContent());
		report_id = bean.getReport_id();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Bundle b = new Bundle();
		b.putInt("type", position % 2);
		b.putString("report_id", report_id);
		UIHelper.jump(_activity, BrandReportActivity.class, b);
	}

}
