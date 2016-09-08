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
import com.midian.qualitycloud.bean.GeoProReportsBean.ContentReport;
import com.midian.qualitycloud.ui.geographical.GIGuideDetailActivity;

public class GeographicalIndicationGuideItemTpl extends BaseTpl<ContentReport>
		implements OnClickListener {
	int position;
	private TextView name_tv;
	private TextView time_tv;
	private String report_id;

	public GeographicalIndicationGuideItemTpl(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public GeographicalIndicationGuideItemTpl(Context context,
			AttributeSet attrs) {
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
	public void setBean(ContentReport bean, int position) {
		// TODO Auto-generated method stub
		this.position = position;
		setBackgroundResource(R.drawable.default_button_white);
		name_tv.setText(bean.getTitle());
		time_tv.setText(bean.getAdd_date());
		report_id = bean.getReport_id();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Bundle b = new Bundle();
		b.putInt("type", position % 2);
		// System.out.println("-----report_id------"+report_id);
		b.putString("report_id", report_id);
		UIHelper.jump(_activity, GIGuideDetailActivity.class, b);
	}

}
