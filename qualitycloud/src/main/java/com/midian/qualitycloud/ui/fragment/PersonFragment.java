package com.midian.qualitycloud.ui.fragment;

import midian.baselib.base.BaseFragment;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.CircleImageView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.midian.login.view.LoginActivity;
import com.midian.login.view.PersonInfoActivity;
import com.midian.qualitycloud.R;
import com.midian.qualitycloud.ui.common.MyMessageActivity;
import com.midian.qualitycloud.ui.setting.AboutUpadateActivity;
import com.midian.qualitycloud.ui.setting.FeedbackActivity;

/**
 * 
 * 个人中心
 * 
 * @author MIDIAN
 * 
 */
public class PersonFragment extends BaseFragment implements OnClickListener {
	private CircleImageView head_iv;
	private TextView userName_tv;
	private LinearLayout personal_ll, setting_ll;
	private LinearLayout head_linear;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View main = inflater.inflate(R.layout.fragment_person, null);
		if (container != null) {
			head_iv = (CircleImageView) main.findViewById(R.id.head_iv);
			userName_tv = (TextView) main.findViewById(R.id.user_name);
			personal_ll = (LinearLayout) main.findViewById(R.id.personal_ll);
			setting_ll = (LinearLayout) main.findViewById(R.id.setting_ll);
			head_linear = (LinearLayout) main.findViewById(R.id.head_linear);
			personal_ll.setOnClickListener(this);
			setting_ll.setOnClickListener(this);
			head_linear.setOnClickListener(this);
			main.findViewById(R.id.message_ll).setOnClickListener(this);
			main.findViewById(R.id.help_ll).setOnClickListener(this);

			main.findViewById(R.id.password_ll).setOnClickListener(this);
			main.findViewById(R.id.about_ll).setOnClickListener(this);
		}
		return main;

	}

	@Override
	public void onResume() {
		super.onResume();

		if (!ac.isAccess()) {
			ac.clearUserInfo();
			userName_tv.setText("未登陆");
		} else {
			userName_tv.setText(ac.name);
			ac.setImage(head_iv, ac.getProperty("head_pic"));
		}

		if (!TextUtils.isEmpty(ac.getProperty("head_pic"))) {
			ac.setImage(head_iv, ac.getProperty("head_pic"));
		} else {
			head_iv.setImageResource(R.drawable.head1);// 如果头像为空则设为默认头像
		}

	}

	@Override
	public void onClick(View v) {
		// if (!ac.isAccess() && v.getId() != R.id.setting_ll) {
		// UIHelper.jumpForResult(_activity, LoginActivity.class, 5000);
		// // _activity.overridePendingTransition(R.anim.activity_open, 0);
		// return;
		// }
		switch (v.getId()) {
		case R.id.head_linear:
			if (!ac.isAccess()) {
				UIHelper.jump(_activity, LoginActivity.class);
			} else {
				head_linear.setClickable(false);
			}
			break;
		case R.id.personal_ll:
			if (ac.isRequireLogin(_activity))
				UIHelper.jump(_activity, PersonInfoActivity.class);
			break;
		// case R.id.setting_ll:
		// // UIHelper.jump(_activity, SetActivity.class);
		// UIHelper.jumpForResult(_activity, SetActivity.class, 10086);
		// break;
		case R.id.about_ll:
			UIHelper.jump(_activity, AboutUpadateActivity.class);
			break;
		case R.id.message_ll:
			if (ac.isRequireLogin(_activity))
				UIHelper.jump(_activity, MyMessageActivity.class);
			break;
		// case R.id.password_ll:// 账号安全
		// if (ac.isRequireLogin(_activity))
		// UIHelper.jump(_activity, SecurityActivity.class);
		// break;

		case R.id.help_ll:// 意见反馈
			UIHelper.jump(_activity, FeedbackActivity.class);
			// WebViewActivity.gotoActivity(_activity, "帮助中心",
			// ServerConstant.BASEURL + ac.link_help);
			break;
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case 10086:
				ac.clearUserInfo();
				break;
			}

		}
	}
}
