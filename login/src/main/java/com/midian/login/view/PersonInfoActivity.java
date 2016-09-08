package com.midian.login.view;

import java.io.File;

import midian.baselib.api.ApiCallback;
import midian.baselib.app.AppContext;
import midian.baselib.base.BasePicActivity;
import midian.baselib.bean.NetResult;
import midian.baselib.tooglebutton.ToggleButton;
import midian.baselib.utils.MyLoadViewHelper;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;
import midian.baselib.widget.CircleImageView;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.midian.configlib.LoginConstant;
import com.midian.login.R;
import com.midian.login.api.LoginApiClient;
import com.midian.login.bean.UpdateUserBean;
import com.midian.login.bean.UserDetailBean;

/**
 * 个人资料
 * 
 * @author chu
 * 
 */
public class PersonInfoActivity extends BasePicActivity implements
		OnClickListener {

	public static final String TYPE = "type";
	public static final String NAME = "name";

	private BaseLibTopbarView topbar;
	private View head_ll, name_ll, phone_ll, sex_ll;
	private CircleImageView head_iv;
	private TextView name_tv, phone_tv, sex_tv;
	protected MyLoadViewHelper loadViewHelper = new MyLoadViewHelper();
	private UserDetailBean mUserDetailBean;
	private File mhead = null;
	private String province_name;
	private String city_name;
	private Button cancel;
	private LinearLayout account_ll;
	private ToggleButton isReceive;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_info);
		initView();
		loadData();

	}

	private void loadData() {
		// TODO Auto-generated method stub
		ac.api.getApiClient(LoginApiClient.class).getUserDetail(this);
	}

	private void initView() {
		topbar = (BaseLibTopbarView) findViewById(R.id.topbar);
		topbar.setTitle("个人信息").setLeftImageButton(R.drawable.icon_back, null)
				.setLeftText("返回", UIHelper.finish(_activity));
		head_iv = (CircleImageView) findViewById(R.id.head_iv);
		name_tv = (TextView) findViewById(R.id.user_name);
		phone_tv = (TextView) findViewById(R.id.user_phone);
		sex_tv = (TextView) findViewById(R.id.sex);
		cancel = (Button) findViewById(R.id.exit);
		cancel.setOnClickListener(this);
		account_ll = (LinearLayout) findViewById(R.id.account_ll);
		isReceive = (ToggleButton) findViewById(R.id.isReceive);
		isReceive.setOnClickListener(this);
		head_ll = findViewById(R.id.head_ll);
		name_ll = findViewById(R.id.name_ll);
		phone_ll = findViewById(R.id.phone_ll);
		sex_ll = findViewById(R.id.sex_ll);
		account_ll.setOnClickListener(this);
		head_ll.setOnClickListener(this);
		name_ll.setOnClickListener(this);
		phone_ll.setOnClickListener(this);
		sex_ll.setOnClickListener(this);
		if (ac.isClosePush) {
			isReceive.toggleOff();
		} else {
			isReceive.toggleOn();
		}
		isReceive.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (ac.isRequireLogin(_activity)) {
					if (ac.isLogin()) {
						ac.api.getApiClient(LoginApiClient.class)
								.getUpdateReceiveStatus(
										ac.isClosePush ? "1" : "0",
										PersonInfoActivity.this);
					}
				}
			}
		});
		loadViewHelper.init(findView(R.id.scrollView), new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				loadData();
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (ac.addresschage) {
			String province_id = ac.p.id;
			province_name = ac.p.name;
			String city_id = ac.city.id;
			city_name = ac.city.name;
			// ac.api.getApiClient(LoginApiClient.class).getUpdateUser(null,
			// null,
			// null, province_id, province_name, city_id, city_name, null,
			// null, null, this);
			showLoadingDlg();

		}
		if (ac.isAccess()) {
			cancel.setText("退出登录");
			PushManager.stopWork(getApplicationContext());
		} else {
			cancel.setText("立即登录");
		}
	}

	@Override
	public void onClick(View v) {
		Bundle mBundle = new Bundle();
		super.onClick(v);
		int id = v.getId();
		if (id == R.id.head_ll) {
			takePhoto();
		} else if (id == R.id.name_ll) {
			mBundle.putString("title", "姓名");
			mBundle.putString("content", name_tv.getText().toString().trim());
			UIHelper.jumpForResult(_activity, EditPersonInfoActivity.class,
					mBundle, 1001);
		} else if (id == R.id.sex_ll) {
			mBundle.putString("title", "性别");
			mBundle.putString("content", sex_tv.getText().toString().trim());
			UIHelper.jumpForResult(_activity, ChooseSexActivity.class, mBundle,
					1003);
		} else if (id == R.id.account_ll) {
			if (ac.isRequireLogin(_activity))
				UIHelper.jump(_activity, SecurityActivity.class);

		} else if (id == R.id.exit) {
			if (ac.isRequireLogin(_activity)) {
				ac.clearUserInfo();
				cancel.setText("立即登陆");
				setResult(RESULT_OK);
				finish();
			}
		}

	}

	private void render(NetResult res) {
		if (res != null) {
			mUserDetailBean = (UserDetailBean) res;
			if (!TextUtils.isEmpty(mUserDetailBean.getContent().getHead_pic())) {
				ac.setImage(head_iv, LoginConstant.BASEFILEURL
						+ mUserDetailBean.getContent().getHead_pic());
			}
			name_tv.setText(mUserDetailBean.getContent().getName());
			phone_tv.setText(mUserDetailBean.getContent().getPhone());

			if ("1".equals(mUserDetailBean.getContent().getSex())) {
				sex_tv.setText("男");
			} else if ("2".equals(mUserDetailBean.getContent().getSex())) {
				sex_tv.setText("女");
			}
			sex_tv.setTag(mUserDetailBean.getContent().getSex());
			province_name = mUserDetailBean.getContent().getProvince_name();
			city_name = mUserDetailBean.getContent().getArea_name();

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		String sex = null;
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 1001:
				// String name = data.getExtras().getString("text");
				name_tv.setText(data.getExtras().getString("text"));
				System.out.println("::::::::::::::"
						+ data.getExtras().getString("text"));
				// System.out.println("name:::" + name);
				ac.api.getApiClient(LoginApiClient.class).postUpdateUser(null,
						data.getExtras().getString("text"), null, null, null,
						null, null, null, null, null, this);
				break;
			case 1003:
				String sexText = data.getExtras().getString("sex");
				if ("男".equals(sexText)) {
					sex = "1";
				} else if ("女".equals(sexText)) {
					sex = "2";
				}
				System.out.println("sex::::" + sex);
				sex_tv.setText(sexText);
				ac.api.getApiClient(LoginApiClient.class).postUpdateUser(null,
						null, sex, null, null, null, null, null, null, null,
						this);
				break;
			}
		}

	}

	@Override
	public void outputBitmap(Bitmap bitmap, String path) {
		super.outputBitmap(bitmap, path);
		head_iv.setImageBitmap(bitmap);
		mhead = new File(path);
		ac.api.getApiClient(LoginApiClient.class).postUpdateUser(mhead, null,
				null, null, null, null, null, null, null, null,
				new ApiCallback() {

					@Override
					public void onParseError(String tag) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onApiSuccess(NetResult res, String tag) {

						if (res.isOK()) {
							UpdateUserBean userBean = (UpdateUserBean) res;
							String head_pic = userBean.getContent()
									.getHead_pic();
							if (!TextUtils.isEmpty(head_pic)) {
								ac.setProperty("head_pic", userBean
										.getContent().getHead_pic());
								ac.setProperty("head_suffix", userBean
										.getContent().getHead_pic_suffix());
							}
						} else {
							ac.handleErrorCode(_activity, res.error_code);
						}
					}

					@Override
					public void onApiStart(String tag) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onApiLoading(long count, long current,
							String tag) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onApiFailure(Throwable t, int errorNo,
							String strMsg, String tag) {
						if (tag.equals("updateUser")) {
							UIHelper.t(_activity, "头像上传失败");
						}
					}
				});
	}

	@Override
	public void onApiStart(String tag) {
		super.onApiStart(tag);
		topbar.showProgressBar();
		hideLoadingDlg();
		if (tag.equals("getUserDetail")) {
			loadViewHelper.showLoading();
		}
	}

	@Override
	public void onApiSuccess(NetResult res, String tag) {
		super.onApiSuccess(res, tag);
		topbar.hideProgressBar();
		hideLoadingDlg();
		if (tag.equals("getUserDetail")) {
			loadViewHelper.restore();
		}
		if (tag.equals("getUpdateReceiveStatus")) {
			ac.isClosePush(!ac.isClosePush);
			if (ac.isClosePush) {
				PushManager.stopWork(getApplicationContext());
				isReceive.toggleOff();
			} else {
				PushManager.startWork(getApplicationContext(),
						PushConstants.LOGIN_TYPE_API_KEY,
						"tkg3nZfd6mTRDY1XR9oPOEw9");
				isReceive.toggleOn();
			}
		}
		hideLoadingDlg();

		if (res instanceof UserDetailBean) {
			if (res.isOK()) {
				render(res);
			} else {
				ac.handleErrorCode(_activity, res.error_code);
			}
		} else if (res instanceof NetResult) {
			if (res.isOK()) {
				if (!ac.name.equals(name_tv.getText().toString().trim())) {
					ac.name = name_tv.getText().toString().trim();
					ac.account = phone_tv.getText().toString().trim();
					ac.head_pic = LoginConstant.BASEURL
							+ mUserDetailBean.getContent().getHead_pic();
				}
			} else {
				ac.handleErrorCode(_activity, res.error_code);
			}
		}

		if (tag.equals("updatePhone")) {
			if (res.isOK()) {
				phone_tv.setText(mUserDetailBean.getContent().getPhone());
			} else {
				ac.handleErrorCode(_activity, res.error_code);
			}
		}

	}

	@Override
	public void onApiFailure(Throwable t, int errorNo, String strMsg, String tag) {
		super.onApiFailure(t, errorNo, strMsg, tag);
		topbar.hideProgressBar();
		hideLoadingDlg();
		if (tag.equals("getUserDetail")) {
			loadViewHelper.showFail();
		}
	}

	@Override
	public void onParseError(String tag) {
		super.onParseError(tag);
		topbar.hideProgressBar();
		hideLoadingDlg();
		if (tag.equals("getUserDetail")) {
			loadViewHelper.showFail();
		}
	}

}
