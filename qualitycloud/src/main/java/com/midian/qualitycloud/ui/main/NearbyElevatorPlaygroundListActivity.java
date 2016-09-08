package com.midian.qualitycloud.ui.main;

import midian.baselib.app.AppManager;
import midian.baselib.base.BaseFragmentActivity;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;
import midian.baselib.widget.SearchEditText;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

import com.midian.qualitycloud.R;
import com.midian.qualitycloud.ui.fragment.NearbyElevatorPlaygroundFragment;

/**
 * 附近电梯游乐场列表(新的)
 * 
 * @author MIDIAN
 * 
 */
public class NearbyElevatorPlaygroundListActivity extends BaseFragmentActivity
		implements SearchEditText.SearchEditTextListener {
	BaseLibTopbarView mBaseLibTopbarView;

	private int type;// 1是电梯；2是游乐设施
	boolean isSwitch;
	SearchEditText search;
	NearbyElevatorPlaygroundFragment epFragment;
	String str;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nearby_elevator_playground_list);
		mBaseLibTopbarView = (BaseLibTopbarView) findViewById(R.id.topbar);
		search = findView(R.id.search);
		search.setBackgroundResource(R.drawable.bg_search_white);
		search.setSearchEditTextListener(this);
		type = getIntent().getIntExtra("type", 1);
		str = getIntent().getStringExtra("key");
		search.setText(str);
		mBaseLibTopbarView
				.setLeftImageButton(R.drawable.icon_back,
						new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								if (TextUtils.isEmpty(str)) {
									if (isSwitch) {
										AppManager
												.getAppManager()
												.finishActivity(
														NearbyElevatorOrPlaygroundMap.class);
									}

									AppManager
											.getAppManager()
											.finishActivity(
													NearbyElevatorPlaygroundListActivity.class);
								} else {
									finish();
								}

							}
						}).setLeftText(R.string.back, null)
				.setTitle(type == 1 ? "电梯" : "游乐设施");
		if (TextUtils.isEmpty(str))
			mBaseLibTopbarView.setRightImageButton(R.drawable.icon_map_1,
					new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							Bundle playground = new Bundle();
							playground.putInt("type", type);
							if (isSwitch) {
								setResult(RESULT_OK, playground);
								finish();
							} else {
								playground.putBoolean("isSwitch", true);
								UIHelper.jumpForResult(_activity,
										NearbyElevatorOrPlaygroundMap.class,
										playground, 1007);
							}
						}
					}).setRightText("地图", new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Bundle playground = new Bundle();
					playground.putInt("type", type);
					if (isSwitch) {
						setResult(RESULT_OK, playground);
						finish();
					} else {
						playground.putBoolean("isSwitch", true);
						UIHelper.jumpForResult(_activity,
								NearbyElevatorOrPlaygroundMap.class,
								playground, 1007);
					}
				}
			});

		isSwitch = getIntent().getBooleanExtra("isSwitch", false);
		initFragement();

	}

	public int getFragmentContentId() {
		return R.id.fl_content;
	}

	private void initFragement() {
		epFragment = new NearbyElevatorPlaygroundFragment();
		epFragment.setArguments(new Bundle());
		epFragment.getArguments().putInt("type", type);
		epFragment.getArguments().putString("key", str);
		switchFragment(epFragment);
		// if(TextUtils.isEmpty(str)){
		//
		// }else{
		// search(str);
		// }
//		09-07 16:03:55.920: I/System.out(31766): Scroll891.0

//		System.out.println("Scroll");
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

	}

	@Override
	public void search(String key) {
		// TODO Auto-generated method stub
		if (epFragment != null)
			epFragment.search(key);
	}

}
