package com.midian.login.view;

import java.io.File;

import midian.baselib.app.AppManager;
import midian.baselib.base.BasePicActivity;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;
import midian.baselib.widget.CircleImageView;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.midian.login.R;
import com.midian.login.api.LoginApiClient;
import com.midian.login.bean.RegisterBean;
import com.midian.login.utils.Constant;

/**
 * 注册第二步
 * 
 * @author MIDIAN
 * 
 */
public class RegisterTwoActivity extends BasePicActivity {

	// private RegisterTwoPresenter mRegisterTwoPresenter;
	private String phone, password, code;
	private CheckBox man_tv, woman_tv;
	private EditText nick_name_et;
	private CircleImageView iv_circleimage;
	private String sex = "";
	private Button next_btn;
	// private Myapplication myapplication;
	private File mhead = null;
	private BaseLibTopbarView topbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_two);
		// initTitleBar(0, R.drawable.icon_back, R.string.register);
		// myapplication = (Myapplication) getApplication();
		// phone = getIntent().getStringExtra(Constant.PHONE);
		// password = getIntent().getStringExtra(Constant.PASSWORD);
		// code = getIntent().getStringExtra(Constant.CODE);
		iv_circleimage = (CircleImageView) this
				.findViewById(R.id.iv_circleimage);
		man_tv = (CheckBox) this.findViewById(R.id.man_tv);
		woman_tv = (CheckBox) this.findViewById(R.id.woman_tv);
		woman_tv.setOnClickListener(this);
		man_tv.setOnClickListener(this);
		nick_name_et = (EditText) this.findViewById(R.id.nick_name_et);
		next_btn = (Button) findViewById(R.id.next_btn);
		next_btn.setOnClickListener(this);
		iv_circleimage.setOnClickListener(this);
		man_tv.setChecked(true);
		sex = "1";
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
		String phone = mBundle.getString("phone");
		String pwd = mBundle.getString("pwd");
		String code = mBundle.getString("code");
		String name = nick_name_et.getText().toString().trim();
		System.out.println(":::::" + phone + "::" + pwd + "::" + code);
		int id = v.getId();
		if (id == R.id.next_btn) {
			try {
				// showLoading();
				// next_btn.setClickable(false);
				// mRegisterTwoPresenter.onRegister(mhead,
				// nick_name_et.getText().toString(), sex, phone, password,
				// code);
				ac.api.getApiClient(LoginApiClient.class).register(phone, pwd,
						code, mhead, name, sex, ac.device_token, this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (id == R.id.iv_circleimage) {
			takePhoto();
		} else if (id == R.id.man_tv) {
			man_tv.setChecked(true);
			woman_tv.setChecked(false);
			sex = "1";
		} else if (id == R.id.woman_tv) {
			sex = "2";
			woman_tv.setChecked(true);
			man_tv.setChecked(false);
		} else {
		}
	}

	@Override
	public void onApiSuccess(NetResult res, String tag) {
		super.onApiSuccess(res, tag);
		hideLoadingDlg();
		next_btn.setClickable(true);
		if (res.isOK()) {
			RegisterBean registerBean = (RegisterBean) res;
			UIHelper.t(_activity, "注册成功");
			ac.saveUserInfo(registerBean.getContent().getUserid(), registerBean.getContent()
					.getAccess_token(), null, null);
			AppManager.getAppManager()
					.finishActivity(RegisterOneActivity.class);
			AppManager.getAppManager().finishActivity(LoginActivity.class);
			finish();
		} else if (res.error_code.equals(Constant.ACCOUNT_EXIST)) {
			UIHelper.t(_activity, "账号已经存在");
			finish();
		} else if (res.error_code.equals(Constant.CODEERROR)) {
			UIHelper.t(_activity, "验证码错误");
		}
	}

	@Override
	public void outputBitmap(Bitmap bitmap, String path) {
		// TODO Auto-generated method stub
		super.outputBitmap(bitmap, path);
		iv_circleimage.setImageBitmap(bitmap);
		mhead = new File(path);
	}

	// @Override
	// protected void onActivityResult(int arg0, int arg1, Intent arg2) {
	// super.onActivityResult(arg0, arg1, arg2);
	// switch (arg0) {
	// case CameraTools.CAMERA:
	// if (arg2 != null) {
	// CameraTools.openPhotoZoom(this, arg2.getData(), 150, 150);
	// }
	// break;
	// case CameraTools.PHOTO:
	// if (arg2 != null) {
	// CameraTools.openPhotoZoom(this, arg2.getData(), 150, 150);
	// }
	// break;
	// case CameraTools.CUT:
	// if (arg2 != null) {
	// mhead = new File(arg2.getData().getPath());
	// }
	// break;
	// default:
	// break;
	// }
	// }

	// @Override
	// public void onFailure() {
	// hideLoading();
	// next_btn.setClickable(true);
	// ToastTools.show(this, R.string.failure);
	// }
	//
	// @Override
	// public void onLoading(long count, long current) {
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
	// public void onReturnUser(Login mLogin) {
	// hideLoading();
	// myapplication.onPutInformation(mLogin);
	// next_btn.setClickable(true);
	// nextActivity(RegisterThreeActivity.class);
	//
	// }

}