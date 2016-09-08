package com.midian.login.view;

import midian.baselib.base.BaseActivity;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.midian.login.R;
import com.midian.login.api.LoginApiClient;
import com.midian.login.utils.ObjectAnimatorTools;
import com.midian.login.utils.ValidateTools;

/**
 * 验证修改手机号
 * 
 * @author chu
 *
 */
public class EditPhoneActivity extends BaseActivity {
	private BaseLibTopbarView topbar;
	private CountDownTimer mCountDownTimer;
	private EditText phone_et, auth_et;
	private Button get_auth_code_btn, next_btn;
	private View phone_ll, code_ll;
	private String phone, code;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_phone);
		topbar = (BaseLibTopbarView) findViewById(R.id.topbar);
		topbar.setLeftText(R.string.back, null).setLeftImageButton(R.drawable.icon_back, UIHelper.finish(_activity))
				.setTitle("修改手机号");

		phone_ll = findView(R.id.phone_ll);
		phone_et = findView(R.id.phone_et);
		code_ll = findView(R.id.get_auth_code_btn);
		auth_et = findView(R.id.auth_et);
		get_auth_code_btn = findView(R.id.get_auth_code_btn);
		next_btn = findView(R.id.next_btn);

		get_auth_code_btn.setOnClickListener(this);
		next_btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		phone = phone_et.getText().toString().trim();
		code = auth_et.getText().toString().trim();
		int id = v.getId();
		if (ValidateTools.isEmptyString(phone)) {
			ObjectAnimatorTools.onVibrationView(phone_ll);
			UIHelper.t(_activity, "手机号码不能为空");
			return;
		}
		if (!ValidateTools.isPhoneNumber(phone)) {
			ObjectAnimatorTools.onVibrationView(phone_ll);
			UIHelper.t(_activity, "手机号码格式不正确");
			return;
		}

		if (id == R.id.next_btn) {// 下一步
			if (ValidateTools.isEmptyString(code)) {
				ObjectAnimatorTools.onVibrationView(code_ll);
				UIHelper.t(_activity, "验证码不能为空");
				return;
			}

			ac.api.getApiClient(LoginApiClient.class).validateCode(phone, code, this);// 验证验证码
			showLoadingDlg();
			next_btn.setClickable(false);

		} else if (id == R.id.get_auth_code_btn) {// 拿验证码
			String type = "3";
			ac.api.getApiClient(LoginApiClient.class).sendCode(phone, type, this);// 发送验证码
			showLoadingDlg();
			next_btn.setClickable(false);

			downTime();
		} else {
		}
	}

	@Override
	public void onApiSuccess(NetResult res, String tag) {
		super.onApiSuccess(res, tag);
		hideLoadingDlg();
		next_btn.setClickable(true);

		if ("validateCode".equals(tag)) {
			if (res.isOK()) {
				Bundle mBundle = new Bundle();
				mBundle.putString("phone", phone);
				UIHelper.jump(_activity, EditPhoneTwoActivity.class, mBundle);
				finish();
			} else {
				ac.handleErrorCode(_activity, res.error_code);
			}
		} else if ("sendCode".equals(tag)) {
			if (res.isOK()) {
				UIHelper.t(_activity, "验证码发送成功");
			} else {
				ac.handleErrorCode(_activity, res.error_code);
			}
		}

	}

	private void downTime() {
		mCountDownTimer = new CountDownTimer(59 * 1000, 1000) {
			@Override
			public void onTick(long millisUntilFinished) {
				String timeText = getResources().getString(R.string.hint_time_text);
				get_auth_code_btn.setClickable(false);
				get_auth_code_btn.setText(millisUntilFinished / 1000 + timeText);
			}

			@Override
			public void onFinish() {
				get_auth_code_btn.setClickable(true);
				get_auth_code_btn.setText("验证码");
			}
		};
		mCountDownTimer.start();
	}
}
