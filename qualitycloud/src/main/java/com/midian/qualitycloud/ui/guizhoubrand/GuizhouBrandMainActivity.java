package com.midian.qualitycloud.ui.guizhoubrand;

import midian.baselib.base.BaseActivity;
import midian.baselib.utils.UIHelper;
import midian.baselib.utils.ViewUtil;
import midian.baselib.widget.BaseLibTopbarView;
import midian.baselib.widget.SearchEditText;
import midian.baselib.widget.SearchEditText.SearchEditTextListener;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.midian.qualitycloud.R;

/**
 * 贵州名牌主页
 * 
 * @author MIDIAN
 * 
 */
public class GuizhouBrandMainActivity extends BaseActivity implements
		SearchEditTextListener {
	BaseLibTopbarView mBaseLibTopbarView;
	SearchEditText mSearchEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guizhou_brand_main);
	
		mSearchEditText = (SearchEditText) findViewById(R.id.search);
		// mSearchEditText.setOnClickListener(this);
		// mSearchEditText.getEditText().setOnClickListener(this);
		// mSearchEditText.getEditText().setFocusable(false);
		mSearchEditText.setSearchEditTextListener(this);
		findViewById(R.id.brand_about).setOnClickListener(this);
		findViewById(R.id.brand_catalogue).setOnClickListener(this);
		findViewById(R.id.brand_distribution).setOnClickListener(this);
		findViewById(R.id.brand_guide).setOnClickListener(this);
		mSearchEditText.setTag(0);
		mSearchEditText.getEditText().setTag(0);
		findViewById(R.id.brand_catalogue).setTag(0);
		findViewById(R.id.brand_distribution).setTag(1);
		findViewById(R.id.brand_guide).setTag(2);
		findViewById(R.id.brand_about).setTag(3);
		initTitle();
	}

	public void initTitle() {
		mBaseLibTopbarView = (BaseLibTopbarView) findViewById(R.id.topbar);
		mBaseLibTopbarView
				.setLeftImageButton(R.drawable.icon_back,
						UIHelper.finish(_activity)).setTitle("贵州省名牌产品名录")
				.setLeftText("返回", null).setMode(BaseLibTopbarView.MODE_1)
				.getTitle_tv().setTextColor(Color.parseColor("#FFFFFF"));
		mBaseLibTopbarView.getLeft_tv().setTextColor(
				Color.parseColor("#FFFFFF"));
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

		Bundle b = new Bundle();
		b.putInt("index", (Integer) arg0.getTag());
		UIHelper.jump(_activity, GuizhouBrandActivity.class, b);
		// if (arg0.getId() == R.id.search || arg0 instanceof EditText) {
		// ViewUtil.hideInputMethod(mSearchEditText.getEditText());
		// }

	}

	@Override
	public void search(String key) {
		// TODO Auto-generated method stub
		Bundle b = new Bundle();
		// b.putInt("index", (Integer) arg0.getTag());
		b.putString("text", key);
		UIHelper.jump(_activity, GuizhouBrandActivity.class, b);
	}
}
