package com.midian.login.view;

import midian.baselib.base.BaseActivity;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.Func;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.midian.login.R;
import com.midian.login.api.LoginApiClient;
import com.midian.login.utils.ObjectAnimatorTools;
import com.midian.login.utils.ValidateTools;

public class NoteVerifyActivity extends BaseActivity implements OnClickListener {
	private CountDownTimer mCountDownTimer;
	private BaseLibTopbarView mBaseLibTopbarView;
	private LinearLayout ll_account_et, ll_auth_code_et;
	private String phone, code;
	private EditText auth_code_et, account_et;
	private Button get_auth_code_btn, next_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note_verify);
		mBaseLibTopbarView = (BaseLibTopbarView) findViewById(R.id.topbar_note);
		mBaseLibTopbarView
				.setLeftText("返回", null)
				.setLeftImageButton(R.drawable.icon_back,
						UIHelper.finish(_activity))
				.setTitle(R.string.reset_password);
		initView();
	}

	private void initView() {
		account_et = (EditText) findViewById(R.id.account_et);
		auth_code_et = (EditText) findViewById(R.id.auth_code_et);
		next_btn = (Button) findViewById(R.id.next_btn);
		get_auth_code_btn = (Button) findViewById(R.id.get_auth_code_btn);
		ll_account_et = (LinearLayout) findViewById(R.id.ll_account_et);
		ll_auth_code_et = (LinearLayout) findViewById(R.id.ll_auth_code_et);
		account_et.setOnClickListener(this);
		auth_code_et.setOnClickListener(this);
		next_btn.setOnClickListener(this);
		get_auth_code_btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		phone = account_et.getText().toString().trim();
		code = auth_code_et.getText().toString().trim();
		// UIHelper.jump(this, ResetPasswordActivity.class);
		int id = v.getId();
		if (id == R.id.next_btn) {// 下一步
			if (ValidateTools.isEmptyString(phone)) {
				ObjectAnimatorTools.onVibrationView(ll_account_et);
				UIHelper.t(this, R.string.hint_phone_not_empty);
				return;
			}
			if (!Func.isMobileNO(phone)) {
				ObjectAnimatorTools.onVibrationView(ll_account_et);
				UIHelper.t(this, R.string.hint_phone_error);
				return;
			}
			if (ValidateTools.isEmptyString(code)) {
				ObjectAnimatorTools.onVibrationView(ll_auth_code_et);
				UIHelper.t(this, R.string.hint_auth_code_not_empty);
				return;
			}
			ac.api.getApiClient(LoginApiClient.class).validateCode(phone, code,
					this);// 验证验证码
			showLoadingDlg();
			next_btn.setClickable(false);
			// UIHelper.jump(_activity, ForgetPasswordTwoActivity.class);
		} else if (id == R.id.get_auth_code_btn) {// 发送验证码
			System.out.println("get_auth_code_btn::::");
			if (ValidateTools.isEmptyString(phone)) {
				ObjectAnimatorTools.onVibrationView(ll_account_et);
				UIHelper.t(this, R.string.hint_phone_not_empty);
				return;
			}
			if (!ValidateTools.isPhoneNumber(phone)) {
				ObjectAnimatorTools.onVibrationView(ll_account_et);
				UIHelper.t(this, R.string.hint_phone_error);
				return;
			}
			String type = "3";
			ac.api.getApiClient(LoginApiClient.class).sendCode(phone, type,
					this);// 发送验证码
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
		if (res.isOK()) {
			if ("sendCode".equals(tag))
				UIHelper.t(_activity, "发送成功");
			else if ("validateCode".equals(tag)) {
				Bundle mBundle = new Bundle();
				mBundle.putString("phone", phone);
				UIHelper.jump(_activity, ResetPasswordActivity.class, mBundle);
				finish();

			}
		} else {
			ac.handleErrorCode(_activity, res.error_code);
		}
	}

	@Override
	public void onApiFailure(Throwable t, int errorNo, String strMsg, String tag) {
		super.onApiFailure(t, errorNo, strMsg, tag);
		hideLoadingDlg();
		next_btn.setClickable(true);
		if ("validate_error".equals(errorNo)) {
			UIHelper.t(_activity, "验证失败");
		}
	}

	private void downTime() {
		mCountDownTimer = new CountDownTimer(59 * 1000, 1000) {
			@Override
			public void onTick(long millisUntilFinished) {
				String timeText = getResources().getString(
						R.string.hint_time_text);
				get_auth_code_btn.setClickable(false);
				get_auth_code_btn
						.setText(millisUntilFinished / 1000 + timeText);
			}

			@Override
			public void onFinish() {
				get_auth_code_btn.setClickable(true);
				get_auth_code_btn.setText(R.string.auth_code);
			}
		};
		mCountDownTimer.start();
	}
}
