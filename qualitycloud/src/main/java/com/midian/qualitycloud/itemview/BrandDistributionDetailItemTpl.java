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
import com.midian.qualitycloud.ui.guizhoubrand.BrandDetailActivity;

public class BrandDistributionDetailItemTpl extends BaseTpl<InformationItem>
		implements OnClickListener {

	private TextView name_tv;
	InformationItem informationItem;
	private String brand_id;
	private String name;
	public BrandDistributionDetailItemTpl(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public BrandDistributionDetailItemTpl(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initView() {
		name_tv = (TextView) findViewById(R.id.name_brand);
	}

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.item_brand_catalogue_page;
	}

	@Override
	public void setBean(InformationItem bean, int position) {
		// TODO Auto-generated method stub
		this.informationItem = bean;
		if (position % 2 == 0) {
			setBackgroundResource(R.drawable.default_button_gray);
		} else {
			setBackgroundResource(R.drawable.default_button_white);

		}
		name_tv.setText(bean.banners.getCompany_name()+"("+bean.banners.getBrand_name()+")");
		name = bean.banners.getBrand_name();
		brand_id = bean.banners.getBrand_id();
		setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Bundle bundle = new Bundle();
		bundle.putString("brand_id", brand_id);
		bundle.putString("name", name);
		UIHelper.jump(_activity, BrandDetailActivity.class,bundle);

	}

}
