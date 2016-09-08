package com.midian.login.itemView;

import midian.baselib.bean.NetResult;
import midian.baselib.utils.UIHelper;
import midian.baselib.view.BaseTpl;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.midian.login.R;
import com.midian.login.view.ChooseAreaActivity;

public class ChooseCitysViewTpl extends BaseTpl<NetResult>implements OnClickListener {
	private TextView cityName_tv;

	public ChooseCitysViewTpl(Context context) {
		super(context);
	}

	public ChooseCitysViewTpl(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void initView() {
		cityName_tv = (TextView) findView(R.id.city_name);

		root.setOnClickListener(this);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.item_choose_citys_tpl;
	}

	@Override
	public void setBean(NetResult bean, int position) {

	}

	@Override
	public void onClick(View v) {
		String city = cityName_tv.getText().toString();
		Bundle mBundle = new Bundle();
		mBundle.putString("city", city);
		UIHelper.jump(_activity, ChooseAreaActivity.class, mBundle);
		_activity.finish();
		
	}

}
