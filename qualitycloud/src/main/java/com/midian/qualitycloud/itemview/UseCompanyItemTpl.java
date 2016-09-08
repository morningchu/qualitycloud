package com.midian.qualitycloud.itemview;

import midian.baselib.view.BaseTpl;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.midian.qualitycloud.R;
import com.midian.qualitycloud.bean.GeoProUseCompaniesBean.ContentUse;

public class UseCompanyItemTpl extends BaseTpl<ContentUse> implements
		OnClickListener {

	private ImageView img_use;
	private TextView name_use, address_use;

	public UseCompanyItemTpl(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public UseCompanyItemTpl(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initView() {
		img_use = (ImageView) findViewById(R.id.img_use);
		name_use = (TextView) findViewById(R.id.name_use);
		address_use = (TextView) findViewById(R.id.address_use);
	}

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.item_use_company;
	}

	@Override
	public void setBean(ContentUse bean, int position) {
		ac.setImage(img_use, bean.getLogo_thumb_pic_name());
		name_use.setText(bean.getCompany_name());
		address_use.setText(bean.getAddress());
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

}
