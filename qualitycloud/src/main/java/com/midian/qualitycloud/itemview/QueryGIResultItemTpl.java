package com.midian.qualitycloud.itemview;

import midian.baselib.utils.UIHelper;
import midian.baselib.view.BaseTpl;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.baidu.a.a.a.b;
import com.midian.qualitycloud.R;
import com.midian.qualitycloud.bean.GeoProsBean.ContentGeo;
import com.midian.qualitycloud.ui.geographical.GIDetailActivity;

public class QueryGIResultItemTpl extends BaseTpl<ContentGeo> implements
		OnClickListener {

	private TextView name_tv,content_tv;
	ContentGeo contentGeo;
	String geo_id;
	private String geo_pro_type_id;
	private String name;
	public QueryGIResultItemTpl(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public QueryGIResultItemTpl(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initView() {
		name_tv = (TextView) findViewById(R.id.name_query);
		content_tv = (TextView) findViewById(R.id.content_query);
	}

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.item_query_giresult;
	}
	@Override
	public void setBean(ContentGeo bean, int position) {
		// TODO Auto-generated method stub
		this.contentGeo = bean;
		if (position % 2 == 0) {
			setBackgroundResource(R.drawable.default_button_gray);
		} else {
			setBackgroundResource(R.drawable.default_button_white);

		}
		geo_pro_type_id = bean.getGeo_pro_type_id();
		name_tv.setText(bean.getGeo_pro_name());
		content_tv.setText(bean.getArea());
		name = bean.getGeo_pro_name();
		geo_id = bean.getGeo_pro_id();
		setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Bundle bundle = new Bundle();
		bundle.putString("id", geo_id);
		bundle.putString("name", name);
		bundle.putString("geo_pro_type_id", geo_pro_type_id);
		UIHelper.jump(_activity, GIDetailActivity.class,bundle);

	}

}
