package com.midian.qualitycloud.ui.common;

import midian.baselib.base.BaseActivity;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;
import android.os.Bundle;
import android.view.View;

import com.midian.qualitycloud.R;

public class ExposureActivity extends BaseActivity {
	BaseLibTopbarView mBaseLibTopbarView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exposure);
		findViewById(R.id.submit).setOnClickListener(this);
		mBaseLibTopbarView = (BaseLibTopbarView) findViewById(R.id.topbar);
		mBaseLibTopbarView
				.setTitle("曝光台")
				.setLeftText(R.string.back, null)
				.setLeftImageButton(R.drawable.icon_back,
						UIHelper.finish(_activity));
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		super.onClick(arg0);
		switch (arg0.getId()) {
		case R.id.submit:

			break;

		default:
			break;
		}
	}
}
