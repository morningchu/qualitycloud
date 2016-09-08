package com.midian.qualitycloud.ui.testorganization;

import midian.baselib.base.BaseFragmentActivity;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;

import com.midian.qualitycloud.R;

import android.os.Bundle;
import android.widget.TextView;

/**
 * 应用标准
 * 
 * @author Administrator
 * 
 */
public class UseStandardDetailActivity extends BaseFragmentActivity {
	private BaseLibTopbarView mBaseLibTopbarView;
	private String name;
	private String standard;
	private TextView title, content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_use_standard_detail);
		name = getIntent().getStringExtra("name");
		standard = getIntent().getStringExtra("standard");
		initTitle();
		initView();
	}

	private void initTitle() {
		mBaseLibTopbarView = (BaseLibTopbarView) findViewById(R.id.use_standard_topbar);
		mBaseLibTopbarView
				.setLeftImageButton(R.drawable.icon_back, UIHelper.finish(this))
				.setTitle("应用标准").setLeftText(R.string.back, null)
				.setMode(BaseLibTopbarView.MODE_1);

	}

	private void initView() {
		title = (TextView) findViewById(R.id.title_use_standard);
		content = (TextView) findViewById(R.id.content_use_standard);
		title.setText(name);
		content.setText(standard);
	}
}
