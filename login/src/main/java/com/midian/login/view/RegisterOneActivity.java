package com.midian.login.view;

import midian.baselib.base.BaseActivity;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.Func;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.midian.login.R;
import com.midian.login.api.LoginApiClient;
import com.midian.login.utils.ObjectAnimatorTools;
import com.midian.login.utils.ValidateTools;

/**
 * 注册第一步
 * 
 * @author MIDIAN
 * 
 */
public class RegisterOneActivity extends BaseActivity {

	private EditText phone_no_et, password_et, password_again_et, auth_code_et;
	// private RegisterOnePresenter mRegisterOnePresenter;
	private CountDownTimer mCountDownTimer;
	private Button get_auth_code_btn, next_btn;
	private LinearLayout ll_phone, ll_password, ll_assword_again, ll_code;
	private BaseLibTopbarView topbar;
	private String phone, pwd, code;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_one);
		// initTitleBar(BaseActivity.RES_ID, R.drawable.icon_back,
		// R.string.register);
		next_btn = (Button) findViewById(R.id.next_btn);
		next_btn.setOnClickListener(this);
		get_auth_code_btn = (Button) findViewById(R.id.get_auth_code_btn);
		get_auth_code_btn.setOnClickListener(this);
		phone_no_et = (EditText) this.findViewById(R.id.phone_no_et);
		password_et = (EditText) this.findViewById(R.id.password_et);
		password_again_et = (EditText) this
				.findViewById(R.id.password_again_et);
		auth_code_et = (EditText) this.findViewById(R.id.auth_code_et);
		ll_phone = (LinearLayout) this.findViewById(R.id.ll_phone);
		ll_password = (LinearLayout) this.findViewById(R.id.ll_password);
		ll_assword_again = (LinearLayout) this
				.findViewById(R.id.ll_assword_again);
		ll_code = (LinearLayout) this.findViewById(R.id.ll_code);
		initTitle();
	}

	private void initTitle() {
		topbar = (BaseLibTopbarView) findViewById(R.id.topbar);

		topbar.setLeftText(R.string.back, null)
				.setLeftImageButton(R.drawable.icon_back,
						UIHelper.finish(_activity)).setTitle(R.string.register);
	}

	@Override
	public void onClick(View v) {
		phone = phone_no_et.getText().toString().trim();
		// System.out.println("phone" + phone);
		pwd = password_et.getText().toString().trim();
		String pwd2 = password_again_et.getText().toString().trim();
		code = auth_code_et.getText().toString().trim();
		super.onClick(v);
		int id = v.getId();
		if (id == R.id.get_auth_code_btn) {
			if (ValidateTools.isEmptyString(phone)) {
				ObjectAnimatorTools.onVibrationView(ll_phone);
				UIHelper.t(this, R.string.hint_phone_not_empty);
				return;
			}
			if (!ValidateTools.isPhoneNumber(phone)) {
				ObjectAnimatorTools.onVibrationView(ll_phone);
				UIHelper.t(this, R.string.hint_phone_error);
				return;
			}
			String type = "1";// 1：注册，2：忘记密码，3：其他
			ac.api.getApiClient(LoginApiClient.class).sendCode(phone, type,
					this);// 发送验证码
			showLoadingDlg();
			next_btn.setClickable(false);
			downTime();
		} else if (id == R.id.next_btn) {
			if (ValidateTools.isEmptyString(phone)) {
				ObjectAnimatorTools.onVibrationView(ll_phone);
				UIHelper.t(_activity, R.string.hint_phone_not_empty);
				return;
			}
			if (!Func.isMobileNO(phone)) {
				ObjectAnimatorTools.onVibrationView(ll_phone);
				UIHelper.t(_activity, R.string.hint_phone_error);
				return;
			}

			if (ValidateTools.isEmptyString(pwd)) {
				ObjectAnimatorTools.onVibrationView(ll_password);
				UIHelper.t(_activity, R.string.hint_pwd_not_empty);
				return;
			}
			if (pwd.length() < 6 || pwd.length() > 16) {
				ObjectAnimatorTools.onVibrationView(ll_password);
				UIHelper.t(_activity, "密码长度须6到16位");
				return;
			}

			if (!pwd.equals(pwd2)) {
				ObjectAnimatorTools.onVibrationView(ll_assword_again);
				UIHelper.t(_activity, R.string.hint_pwd2);
				return;
			}
			if (ValidateTools.isEmptyString(code)) {
				ObjectAnimatorTools.onVibrationView(ll_code);
				UIHelper.t(_activity, R.string.hint_auth_code_not_empty);
				return;
			}
			ac.api.getApiClient(LoginApiClient.class).validateCode(phone, code,
					this);// 验证验证码
			showLoadingDlg();
			next_btn.setClickable(false);

		}
	}

	@Override
	public void onApiSuccess(NetResult res, String tag) {
		super.onApiSuccess(res, tag);
		System.out.println("1.onApiSuccess");
		hideLoadingDlg();
		next_btn.setClickable(true);
		if (res.isOK()) {
			System.out.println("2.res.isOK");
			if ("sendCode".equals(tag)) {
				UIHelper.t(_activity, "发送成功");
			}
			if ("validateCode".equals(tag)) {
				Bundle mBundle = new Bundle();
				mBundle.putString("phone", phone);
				mBundle.putString("pwd", pwd);
				mBundle.putString("code", code);
				System.out.println("RegisterTwoActivity");
				UIHelper.jump(_activity, RegisterTwoActivity.class, mBundle);
				finish();
			}
		} else {
			ac.handleErrorCode(_activity, res.error_code);
		}
	}

	@Override
	public void onApiFailure(Throwable t, int errorNo, String strMsg, String tag) {
		super.onApiFailure(t, errorNo, strMsg, tag);
		System.out.println("onApiFailure");
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

	// @Override
	// public void onFailure() {
	// hideLoading();
	// next_btn.setClickable(true);
	// ToastTools.show(this, R.string.failure);
	// }
	//
	// @Override
	// public void onLoading(long count, long current) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void onAnalysisFailed() {
	// hideLoading();
	// next_btn.setClickable(true);
	// ToastTools.show(this, R.string.analysis_failed);
	// }
	//
	// @Override
	// public void onReturnCodeVerificationStatus(String status) {
	// hideLoading();
	// next_btn.setClickable(true);
	// if (Constant.ERROR.equals(status)) {
	// ToastTools.show(this, R.string.hint_error);
	// ObjectAnimatorTools.onVibrationView(ll_code);
	// next_btn.setClickable(true);
	// return;
	// }
	// Intent mIntent = new Intent(this, RegisterTwoActivity.class);
	// mIntent.putExtra(Constant.PHONE, phone_no_et.getText().toString());
	// mIntent.putExtra(Constant.PASSWORD,
	// FDMD5Tools.getMD5(password_et.getText().toString()));
	// mIntent.putExtra(Constant.CODE, auth_code_et.getText().toString());
	// gotoActivity(mIntent);
	// }
	//
	// @Override
	// public void onReturnSendCodeStatus(String status) {
	// if (Constant.ERROR.equals(status)) {
	// ToastTools.show(this, R.string.hint_send_code_failed);
	// mCountDownTimer.onFinish();
	// }
	// }

}
