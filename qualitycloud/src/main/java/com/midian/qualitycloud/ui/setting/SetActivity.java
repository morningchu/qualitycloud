package com.midian.qualitycloud.ui.setting;

import midian.baselib.base.BaseActivity;
import midian.baselib.bean.NetResult;
import midian.baselib.tooglebutton.ToggleButton;
import midian.baselib.tooglebutton.ToggleButton.OnToggleChanged;
import midian.baselib.utils.UIHelper;
import midian.baselib.version.VersionUpdateUtil;
import midian.baselib.widget.BaseLibTopbarView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.midian.qualitycloud.R;
import com.midian.qualitycloud.bean.VersionBean;
import com.midian.qualitycloud.utils.AppUtil;

/**
 * 设置
 * 
 * @author Administrator
 * 
 */
public class SetActivity extends BaseActivity implements OnClickListener {

	private BaseLibTopbarView mBaseLibTopbarView;
	private LinearLayout feedback_ll, check_update_ll;
	VersionUpdateUtil mVersionUpdateUtil;
	private Button cancel;
	private ToggleButton isReceive;
	TextView version;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_set);
		mBaseLibTopbarView = (BaseLibTopbarView) findViewById(R.id.topbar);
		mBaseLibTopbarView.setTitle(R.string.setting)
				.setLeftImageButton(R.drawable.icon_back, null)
				.setLeftText("返回", UIHelper.finish(_activity));
		mVersionUpdateUtil = new VersionUpdateUtil(_activity);
		initView();
	}

	private void initView() {
		feedback_ll = (LinearLayout) findViewById(R.id.feedback_ll);
		feedback_ll.setOnClickListener(this);
		check_update_ll = (LinearLayout) findViewById(R.id.check_update_ll);
		check_update_ll.setOnClickListener(this);
		cancel = (Button) findViewById(R.id.exit);
		version = (TextView) findViewById(R.id.version);
		cancel.setOnClickListener(this);
		isReceive = (ToggleButton) findViewById(R.id.isReceive);
		isReceive.setOnClickListener(this);
		if (ac.isHasNewVersion) {
			version.setText("有新版本");
			version.setTextColor(getResources().getColor(R.color.red));
		} else {
			version.setText("已经是最新版本");
			version.setTextColor(getResources().getColor(R.color.text_bg90));
		}

		if (ac.isClosePush) {
			isReceive.toggleOff();
		} else {
			isReceive.toggleOn();
		}
		

	}

	@Override
	public void onApiSuccess(NetResult res, String tag) {
		// TODO Auto-generated method stub
		super.onApiSuccess(res, tag);

		if (res.isOK()) {
			ac.isClosePush(!ac.isClosePush);
			AppUtil.getMAppContext(ac).changePush();

			if (ac.isClosePush) {
				isReceive.toggleOff();
			} else {
				isReceive.toggleOn();
			}
		} else {
			ac.handleErrorCode(_activity, res.error_code);
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (ac.isAccess()) {
			cancel.setText("退出登录");
			AppUtil.getMAppContext(ac).stopPush();
		} else {
			cancel.setText("立即登录");
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.feedback_ll:
			UIHelper.jump(this, FeedbackActivity.class);
			break;
		case R.id.check_update_ll:

			if (ac.isHasNewVersion) {
				mVersionUpdateUtil.BDCheckUpdate();
			} else {
				UIHelper.t(this, "已经是最新版本");
			}
			break;
		case R.id.exit:
			if (ac.isRequireLogin(_activity)) {
				ac.clearUserInfo();
				cancel.setText("立即登陆");
				setResult(RESULT_OK);
				finish();
			}
			break;
		default:
			break;
		}

	}

}
