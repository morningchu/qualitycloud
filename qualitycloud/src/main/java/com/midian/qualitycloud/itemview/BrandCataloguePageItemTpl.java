package com.midian.qualitycloud.itemview;

import midian.baselib.bean.NetResult;
import midian.baselib.utils.UIHelper;
import midian.baselib.view.BaseTpl;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.midian.qualitycloud.R;
import com.midian.qualitycloud.bean.BrandsBean.ContentBrand;
import com.midian.qualitycloud.ui.guizhoubrand.BrandDetailActivity;

public class BrandCataloguePageItemTpl extends BaseTpl<ContentBrand> implements
		OnClickListener {

	private TextView name_brand;
	ContentBrand contentBrand;
	private String brand_id;
	private String name;
	public BrandCataloguePageItemTpl(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public BrandCataloguePageItemTpl(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initView() {
		name_brand = (TextView) findViewById(R.id.name_brand);
	}

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.item_brand_catalogue_page;
	}

	@Override
	public void setBean(ContentBrand bean, int position) {
		// TODO Auto-generated method stub
		this.contentBrand = bean;
		if (position % 2 == 0) {
			setBackgroundResource(R.drawable.default_button_gray);
		} else {
			setBackgroundResource(R.drawable.default_button_white);

		}
		name_brand.setText(contentBrand.getCompany_name() + "(" + contentBrand.getName() + ")");
		name = contentBrand.getName();
		brand_id = contentBrand.getBrand_id();
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
