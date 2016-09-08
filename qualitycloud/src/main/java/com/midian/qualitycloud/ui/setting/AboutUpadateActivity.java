package com.midian.qualitycloud.ui.setting;

import midian.baselib.base.BaseActivity;
import midian.baselib.utils.UIHelper;
import midian.baselib.version.VersionUpdateUtil;
import midian.baselib.widget.BaseLibTopbarView;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.midian.qualitycloud.R;

public class AboutUpadateActivity extends BaseActivity {
	private BaseLibTopbarView mBaseLibTopbarView;
	VersionUpdateUtil mVersionUpdateUtil;
	private LinearLayout feedback_ll, check_update_ll;
	TextView version;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_update);
		mBaseLibTopbarView = (BaseLibTopbarView) findViewById(R.id.topbar);
		mBaseLibTopbarView.setTitle(R.string.setting)
				.setLeftImageButton(R.drawable.icon_back, null)
				.setLeftText("返回", UIHelper.finish(_activity));
		mVersionUpdateUtil = new VersionUpdateUtil(_activity);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		feedback_ll = (LinearLayout) findViewById(R.id.feedback_ll);
		feedback_ll.setOnClickListener(this);
		check_update_ll = (LinearLayout) findViewById(R.id.check_update_ll);
		check_update_ll.setOnClickListener(this);
		version = (TextView) findViewById(R.id.version);
		if (ac.isHasNewVersion) {
			version.setText("有新版本");
			version.setTextColor(getResources().getColor(R.color.red));
		} else {
			version.setText("已经是最新版本");
			version.setTextColor(getResources().getColor(R.color.text_bg90));
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.feedback_ll://关于
			UIHelper.jump(_activity, AboutUsActivity.class);
			break;
		case R.id.check_update_ll://检查更新

			if (ac.isHasNewVersion) {
				mVersionUpdateUtil.BDCheckUpdate();
			} else {
				UIHelper.t(this, "已经是最新版本");
			}
			break;
		default:
			break;
		}
	}
}