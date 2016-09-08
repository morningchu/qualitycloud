package com.midian.login.view;

import midian.baselib.base.BaseActivity;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.midian.login.R;

/**
 * 安全
 * 
 * @author MIDIAN
 * 
 */
public class SecurityActivity extends BaseActivity {
	BaseLibTopbarView mBaseLibTopbarView;
	EditText content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_security);
		mBaseLibTopbarView = (BaseLibTopbarView) findViewById(R.id.topbar);
		mBaseLibTopbarView.setTitle("账号安全")
				.setLeftImageButton(R.drawable.icon_back, null)
				.setLeftText("返回", UIHelper.finish(_activity));
		findViewById(R.id.modify_password_ll).setOnClickListener(this);
		findViewById(R.id.modify_phone_ll).setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		super.onClick(arg0);
		int id = arg0.getId();
		if (id == R.id.modify_password_ll) {
			if (ac.isRequireLogin(_activity))
				UIHelper.jump(_activity, ForgetPasswordOneActivity.class);
		} else if (id == R.id.modify_phone_ll) {
			if (ac.isRequireLogin(_activity))
				UIHelper.jump(_activity, EditPhoneActivity.class);
		} else {
		}
	}
}
