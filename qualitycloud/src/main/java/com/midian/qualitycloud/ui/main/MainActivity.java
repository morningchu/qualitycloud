package com.midian.qualitycloud.ui.main;

import midian.baselib.api.ApiCallback;
import midian.baselib.app.AppManager;
import midian.baselib.base.BaseFragmentActivity;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.DateUtil;
import midian.baselib.utils.ScreenUtils;
import midian.baselib.utils.UIHelper;
import midian.baselib.version.VersionUpdateUtil;
import midian.baselib.widget.BaseLibMainFooterView;
import midian.baselib.widget.BaseLibMainFooterView.onTabChangeListener;
import midian.baselib.widget.dialog.ConfirmDialog1;
import midian.baselib.widget.BaseLibTopbarView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.baidu.location.BDLocation;
import com.midian.login.view.LoginActivity;
import com.midian.qualitycloud.R;
import com.midian.qualitycloud.bean.APPBean;
import com.midian.qualitycloud.ui.fragment.HomeFrament;
import com.midian.qualitycloud.ui.fragment.MyAttentionFragment;
import com.midian.qualitycloud.ui.fragment.PersonFragment;
import com.midian.qualitycloud.utils.AppUtil;
import com.midian.qualitycloud.utils.LocationUtil;
import com.midian.qualitycloud.utils.LocationUtil.OneLocationListener;

/**
 * 首页
 * 
 * @author MIDIAN
 * 
 */

public class MainActivity extends BaseFragmentActivity implements
		onTabChangeListener {
	private BaseLibTopbarView mBaseLibTopbarView;
	private BaseLibMainFooterView mFooterView;
	public int[][] iconsArr = {
			{ R.drawable.dp_tab_1_n, R.drawable.dp_tab_1_c },
			{ R.drawable.dp_tab_2_n, R.drawable.dp_tab_2_c },
			{ R.drawable.dp_tab_3_n, R.drawable.dp_tab_3_c } };

	private HomeFrament mHomeFrament;
	private MyAttentionFragment mMyAttentionFragment;
	private PersonFragment mPersonFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mBaseLibTopbarView = (BaseLibTopbarView) findViewById(R.id.topbar);
		mBaseLibTopbarView.recovery().setTitle("")
				.setTitleImage(R.drawable.icon_title)
				.setMode(BaseLibTopbarView.MODE_2);
		View top = findViewById(R.id.rl_titlebar);
		RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) top
				.getLayoutParams();
		p.height = ScreenUtils.dpToPxInt(_activity, 90);
		mFooterView = (BaseLibMainFooterView) findViewById(R.id.footer);
		mFooterView.setOnTabChangeListener(this);
		mFooterView.initTab(new String[] { "首页", "关注", "我的" }, iconsArr);
		initFragment();

		System.out.println("首页");
		new VersionUpdateUtil(_activity).BDCheckUpdate();
		AppUtil.getMAppContext(ac).startPush();
		

		// PushSettings.enableDebugMode(_activity, true);

	}

	protected void onResume() {
		super.onResume();
		LocationUtil.getInstance(_activity).startOneLocation(
				oneLocationListener);
		try {
			// if (DateUtil.oldDate()) {
			// ConfirmDialog1.makeText(_activity, "", "有效期至：" + DateUtil.old
			// + "\n今天" + DateUtil.today() + "使用期限已到", "知道了",
			// new OnClickListener() {
			//
			// @Override
			// public void onClick(View arg0) {
			// // TODO Auto-generated method stub
			//
			// }
			// });
			// // AppManager.getAppManager().appExit(_activity);
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
	};

	OneLocationListener oneLocationListener = new OneLocationListener() {

		@Override
		public void complete(BDLocation location) {
			// TODO Auto-generated method stub
			System.out.println("location::::" + location.getLongitude());
			ac.saveLocation(location.getLongitude() + "",
					location.getLatitude() + "");
		}
	};

	public void onApiSuccess(midian.baselib.bean.NetResult res, String tag) {
		if ("getApp".equals(tag)) {
			APPBean item = (APPBean) res;
			if (res.isOK()) {
				if ("1".equals(item.getContent().getCode())
						&& null != item.getContent()) {

					AppManager.getAppManager().appExit(_activity);
				}
			} else {

			}
		}
	}

	private void initFragment() {
		mHomeFrament = new HomeFrament();
		mMyAttentionFragment = new MyAttentionFragment();
		mPersonFragment = new PersonFragment();
		switchFragment(mHomeFrament);
	}

	@Override
	public int getFragmentContentId() {
		return R.id.fl_content;
	};

	@Override
	public void onTabChange(int index) {
		System.out.println("index::::::::" + index);
		switch (index) {
		case 0:
			switchFragment(mHomeFrament);
			mBaseLibTopbarView.recovery().setTitle("")
					.setTitleImage(R.drawable.icon_title)
					.setMode(BaseLibTopbarView.MODE_2);
			View top = findViewById(R.id.rl_titlebar);
			RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) top
					.getLayoutParams();
			p.height = ScreenUtils.dpToPxInt(_activity, 90);
			break;
		case 1:
			ac.isRequireLogin(_activity);
			switchFragment(mMyAttentionFragment);
			mBaseLibTopbarView.recovery().setTitle("我的关注")
					.setMode(BaseLibTopbarView.MODE_1);
			View top2 = findViewById(R.id.rl_titlebar);
			RelativeLayout.LayoutParams p2 = (RelativeLayout.LayoutParams) top2
					.getLayoutParams();
			p2.height = ScreenUtils.dpToPxInt(_activity, 60);
			break;
		case 2:

			switchFragment(mPersonFragment);
			mBaseLibTopbarView.recovery().setTitle("个人中心")
					.setMode(BaseLibTopbarView.MODE_1);
			View top3 = findViewById(R.id.rl_titlebar);
			RelativeLayout.LayoutParams p3 = (RelativeLayout.LayoutParams) top3
					.getLayoutParams();
			p3.height = ScreenUtils.dpToPxInt(_activity, 60);
			break;
		}

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// AppUtil.getMAppContext(ac).stopPush();
	}

}
