package com.midian.login.view;

import midian.baselib.base.BaseActivity;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.midian.login.R;
import com.midian.login.api.LoginApiClient;
import com.midian.login.utils.ObjectAnimatorTools;
import com.midian.login.utils.ValidateTools;

/**
 * 忘记密码第二步
 * 
 * @author MIDIAN
 * 
 */
public class ForgetPasswordTwoActivity extends BaseActivity implements
		OnClickListener {
	private Button next_btn;
	private EditText password_et, password_again_et;
	private LinearLayout ll_password, ll_assword_again;
	// private ForgetPasswordTwoPresenter mForgetPasswordTwoPresenter;
	private String phone;
	private BaseLibTopbarView topbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_password_two);
		phone = mBundle.getString("phone");
		// initTitleBar(0, R.drawable.icon_back, R.string.forget_password);

		next_btn = (Button) findViewById(R.id.next_btn);
		next_btn.setOnClickListener(this);
		password_et = (EditText) findViewById(R.id.password_et);
		password_again_et = (EditText) findViewById(R.id.password_again_et);
		ll_password = (LinearLayout) findViewById(R.id.ll_password);
		ll_assword_again = (LinearLayout) findViewById(R.id.ll_assword_again);
		// mForgetPasswordTwoPresenter = new ForgetPasswordTwoPresenter(this);
		initTitle();
	}

	private void initTitle() {
		topbar = (BaseLibTopbarView) findViewById(R.id.topbar);
		topbar.setLeftText(R.string.back, null)
				.setLeftImageButton(R.drawable.icon_back,
						UIHelper.finish(_activity))
				.setTitle(R.string.forget_password);
	}

	@Override
	public void onClick(View arg0) {
		String pwd = password_et.getText().toString().trim();
		String pwd2 =password_again_et.getText().toString().trim();
		int id = arg0.getId();
		if (id == R.id.next_btn) {
			if (ValidateTools.isEmptyString(pwd)) {
				ObjectAnimatorTools.onVibrationView(ll_password);
				UIHelper.t(this, R.string.hint_pwd_not_empty);
				return;
			}
			if (!pwd.equals(pwd2) || pwd.length() != pwd2.length()) {
				ObjectAnimatorTools.onVibrationView(ll_assword_again);
				UIHelper.t(this, R.string.hint_pwd2);
				return;
			}

			ac.api.getApiClient(LoginApiClient.class).getFindPwd(phone, pwd, this);// 找回密码
			showLoadingDlg();
			next_btn.setClickable(false);

		} else {
		}
	}

	@Override
	public void onApiSuccess(NetResult res, String tag) {
		super.onApiSuccess(res, tag);
		hideLoadingDlg();
		next_btn.setClickable(true);
		if (res.isOK()) {
			UIHelper.t(_activity, "密码修改成功");
			finish();
		}
	}

	@Override
	public void onApiFailure(Throwable t, int errorNo, String strMsg, String tag) {
		super.onApiFailure(t, errorNo, strMsg, tag);
		hideLoadingDlg();
		next_btn.setClickable(true);

	}

}