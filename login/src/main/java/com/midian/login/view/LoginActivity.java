package com.midian.login.view;

import midian.baselib.app.AppManager;
import midian.baselib.base.BaseActivity;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.midian.login.R;
import com.midian.login.api.LoginApiClient;
import com.midian.login.bean.SaveDevice;
import com.midian.login.bean.Userse;
import com.midian.login.utils.ObjectAnimatorTools;
import com.midian.login.utils.ValidateTools;

/**
 * @author MIDIAN
 * 
 */
public class LoginActivity extends BaseActivity {
	EditText phone_no_et, password_et;
	// private LoginPresenter mLoginPresenter;
	private Button login_btn;
	// private Myapplication mMyapplication;
	private LinearLayout ll_phone, ll_password;
	private float exitTime;
	private BaseLibTopbarView topbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_login);

		super.onCreate(savedInstanceState);
		// LoginApiClient.init(ac);
		// initTitleBar(0, BaseActivity.RES_ID, R.string.login,
		// R.string.register,
		// 0);

		login_btn = (Button) findViewById(R.id.login_btn);
		ll_phone = (LinearLayout) findViewById(R.id.ll_phone);
		ll_password = (LinearLayout) findViewById(R.id.ll_password);
		login_btn.setOnClickListener(this);
		findViewById(R.id.qq).setOnClickListener(this);
		findViewById(R.id.weixin).setOnClickListener(this);
		findViewById(R.id.forget_password_tv).setOnClickListener(this);
		findViewById(R.id.register_btn).setOnClickListener(this);
		phone_no_et = (EditText) findViewById(R.id.phone_no_et);
		password_et = (EditText) findViewById(R.id.password_et);
		// ac.api.getApiClient(LoginApiClient.class).login(phone, pwd, deviceNo,
		// this);
		// mLoginPresenter = new LoginPresenter(this);
		// mMyapplication = (Myapplication) getApplication();

		initTitle();
		if (ac.isAccess()) {
			finish();
		}
	}

	// @Override
	// public void onApiSuccess(NetResult res, String tag) {
	// // TODO Auto-generated method stub
	// super.onApiSuccess(res, tag);
	//
	// if (res.isOK() && tag.equals("login")) {
	// ThirdUserLoginBean item = (ThirdUserLoginBean) res;
	//
	// } else if (res.isOK() && tag.equals("login")) {
	// ThirdUserLoginBean item = (ThirdUserLoginBean) res;
	//
	// } else {
	// ac.handleErrorCode(_activity, res.error_code);
	// }
	//
	// }

	private void initTitle() {
		topbar = (BaseLibTopbarView) findViewById(R.id.topbar);
		try {
			topbar.setLeftText(R.string.back, null)
					.setLeftImageButton(R.drawable.icon_back,
							UIHelper.finish(_activity))
					.setTitle(R.string.login).setMode(BaseLibTopbarView.MODE_2);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// @Override
	// public void onClickRight(View arg0) {
	// super.onClickRight(arg0);
	// nextActivity(RegisterOneActivity.class);
	// }

	@Override
	public void onClick(View v) {
		super.onClick(v);
		try {
			String phone = phone_no_et.getText().toString().trim();
			System.out.println("phone" + phone);
			String pwd = password_et.getText().toString().trim();
			System.out.println("pwd" + pwd);

			int id = v.getId();
			if (id == R.id.login_btn) {
				if (ValidateTools.isEmptyString(phone)) {
					ObjectAnimatorTools.onVibrationView(ll_phone);
					UIHelper.t(this, R.string.hint_account_not_empty);
					return;
				}
				if (!ValidateTools.isPhoneNumber(phone)) {
					ObjectAnimatorTools.onVibrationView(ll_phone);
					UIHelper.t(this, R.string.hint_account_error);
					return;
				}
				if (ValidateTools.isEmptyString(pwd)) {
					ObjectAnimatorTools.onVibrationView(ll_password);
					UIHelper.t(this, R.string.hint_pwd_not_empty);
					return;
				}
				showLoadingDlg();
				login_btn.setClickable(false);
				ac.saveAccount(phone);// 保存账号
				ac.savePassword(pwd);// 保存密码
				ac.api.getApiClient(LoginApiClient.class).login(phone, pwd,
						ac.device_token, this);
				System.out.println("phone+pwd::::::" + phone + pwd);
			} else if (id == R.id.forget_password_tv) {
				UIHelper.jump(_activity, ForgetPasswordOneActivity.class);
			} else if (id == R.id.qq) {// qq登录

			} else if (id == R.id.weixin) {// weixin 登录

			} else if (id == R.id.register_btn) {
				UIHelper.jump(_activity, RegisterOneActivity.class);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onApiSuccess(NetResult res, String tag) {
		super.onApiSuccess(res, tag);
		hideLoadingDlg();
		login_btn.setClickable(true);
		if (res.isOK() && (tag.equals("login") || tag.equals("thirdUserLogin"))) {
			UIHelper.t(_activity, "登陆成功");
			Userse item = (Userse) res;
			ac.saveUserInfo(item.getContent().getUser_id(), item.getContent()
					.getAccess_token(), item.getContent().getName(), item
					.getContent().getHead_pic());
			if (TextUtils.isEmpty(ac.device_token)) {
				setResult(RESULT_OK);
				finish();
			} else {
				ac.api.getApiClient(LoginApiClient.class).saveDevice(
						ac.device_token, this);
			}
		} else if (res.isOK() && tag.equals("saveDevice")) {
			SaveDevice mSaveDevice = (SaveDevice) res;
			ac.isClosePush("1".equals(mSaveDevice.getContent().getIs_receive()));
			if (ac.isClosePush) {
				PushManager.stopWork(getApplicationContext());
			} else {
				PushManager.startWork(getApplicationContext(),
						PushConstants.LOGIN_TYPE_API_KEY,
						"nKGGUGBtOTPyAINAX0HduZHf");
			}
			setResult(RESULT_OK);
			finish();
		} else {
			ac.handleErrorCode(_activity, res.error_code);
		}

	}

	// @Override
	// protected void onActivityResult(int requestCode, int resultCode, Intent
	// data) {
	// super.onActivityResult(requestCode, resultCode, data);
	// UMengLoginUtil.getInstance(this).onActivityResult(requestCode,
	// resultCode, data);
	// if (resultCode != RESULT_OK) {
	// hideLoadingDlg();
	// }
	// }

	@Override
	public void onApiFailure(Throwable t, int errorNo, String strMsg, String tag) {
		super.onApiFailure(t, errorNo, strMsg, tag);

		hideLoadingDlg();
		login_btn.setClickable(true);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void exit() {
		if ((System.currentTimeMillis() - exitTime) > 2000) {
			UIHelper.t(getApplicationContext(), R.string.exit_text);
			exitTime = System.currentTimeMillis();
		} else {
			AppManager.getAppManager().appExit(this);
			finish();
		}
	}

}
