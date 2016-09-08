package com.midian.login.itemView;

import midian.baselib.bean.NetResult;
import midian.baselib.utils.UIHelper;
import midian.baselib.view.BaseTpl;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.midian.login.R;

public class ChooseAreaTpl extends BaseTpl<NetResult>implements OnClickListener {

	private TextView areaName_tv;

	public ChooseAreaTpl(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ChooseAreaTpl(Context context) {
		super(context);
	}

	@Override
	protected void initView() {
		areaName_tv = (TextView) findViewById(R.id.area_name);

		root.setOnClickListener(this);

	}

	@Override
	protected int getLayoutId() {
		return R.layout.item_choose_area_tpl;
	}

	@Override
	public void setBean(NetResult bean, int position) {

	}

	@Override
	public void onClick(View v) {
		UIHelper.t(_activity, "地区");
		Bundle bundle = _activity.getIntent().getExtras();
		String city = bundle.getString("city");
		String area = areaName_tv.getText().toString();

		Intent intent = new Intent();
		intent.putExtra("city", city);
		intent.putExtra("area", area);
		_activity.setResult(Activity.RESULT_OK, intent);
		_activity.finish();
		System.out.println("city::::::" + city);
		System.out.println("area::::::" + area);
	}

}
