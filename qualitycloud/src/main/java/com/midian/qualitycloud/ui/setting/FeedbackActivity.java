package com.midian.qualitycloud.ui.setting;

import midian.baselib.base.BaseActivity;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.midian.qualitycloud.R;
import com.midian.qualitycloud.utils.AppUtil;

/**
 * 意见反馈
 * 
 * @author MIDIAN
 * 
 */
public class FeedbackActivity extends BaseActivity {
	BaseLibTopbarView mBaseLibTopbarView;
	EditText content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		mBaseLibTopbarView = (BaseLibTopbarView) findViewById(R.id.topbar);
		mBaseLibTopbarView.setTitle("意见反馈")
				.setLeftImageButton(R.drawable.icon_back, null)
				.setLeftText("返回", UIHelper.finish(_activity));
		content = (EditText) findViewById(R.id.content);
		findViewById(R.id.submit).setOnClickListener(this);

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		super.onClick(arg0);
		switch (arg0.getId()) {
		case R.id.submit:// 提交
			if (TextUtils.isEmpty(content.getText().toString())) {
				UIHelper.t(_activity, "请填写反馈内容再试！");

			} else {
				AppUtil.getQualityCloudApiClient(ac).postAdvice(
						content.getText().toString(), this);
			}

			break;

		default:
			break;
		}
	}

	@Override
	public void onApiSuccess(NetResult res, String tag) {
		// TODO Auto-generated method stub
		super.onApiSuccess(res, tag);
		if (res.isOK()) {
			UIHelper.t(_activity, "非常感谢您的反馈！");
			finish();
		}
		else {
			ac.handleErrorCode(_activity, res.error_code);
		}
	}

}
