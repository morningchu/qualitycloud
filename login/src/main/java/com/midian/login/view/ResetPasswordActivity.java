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
 * 修改密码第二部
 * @author Administrator
 *
 */
public class ResetPasswordActivity extends BaseActivity implements
		OnClickListener {
	private BaseLibTopbarView mBaseLibTopbarView;
	private Button next_btn;
	private EditText password_et, password_again_et;
	private LinearLayout ll_password, ll_assword_again;
	private String phone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reset_password);
		mBaseLibTopbarView = (BaseLibTopbarView) findViewById(R.id.topbar_reset);
		mBaseLibTopbarView
				.setLeftText(R.string.back, null)
				.setLeftImageButton(R.drawable.icon_back,
						UIHelper.finish(_activity))
				.setTitle(R.string.reset_password);
		initView();
	}

	private void initView() {
		phone = mBundle.getString("phone");

		next_btn = (Button) findViewById(R.id.succend_btn);
		next_btn.setOnClickListener(this);
		password_et = (EditText) findViewById(R.id.password);
		password_again_et = (EditText) findViewById(R.id.repetition_password);
		ll_password = (LinearLayout) findViewById(R.id.password_linear);
		ll_assword_again = (LinearLayout) findViewById(R.id.repetition_password_linear);
	}

	@Override
	public void onClick(View v) {
		String pwd = password_et.getText().toString().trim();
		String pwd2 = password_again_et.getText().toString().trim();
		int id = v.getId();
		if (id == R.id.succend_btn) {
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
			ac.api.getApiClient(LoginApiClient.class).updatePwd(pwd, this);
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
