package com.midian.qualitycloud.ui.main;

import java.util.ArrayList;

import midian.baselib.adapter.PagerTabAdapter;
import midian.baselib.app.AppManager;
import midian.baselib.base.BaseFragmentActivity;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;
import midian.baselib.widget.PagerSlidingTabStrip;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;

import com.midian.qualitycloud.R;
import com.midian.qualitycloud.ui.fragment.NearbyElevatorPlaygroundFragment;

/**
 * 附近电梯游乐场列表(旧写法)
 * 
 * @author MIDIAN
 * 
 */
public class NearbyElevatorPlaygroundActivity extends BaseFragmentActivity
		implements OnClickListener {
	BaseLibTopbarView mBaseLibTopbarView;
	ViewPager mViewPager;
	PagerSlidingTabStrip tabs;

	private int type;//1是电梯；2是游乐设施
	boolean isSwitch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nearby_elevator_playground);
		mBaseLibTopbarView = (BaseLibTopbarView) findViewById(R.id.topbar);

		mBaseLibTopbarView
				.setLeftImageButton(R.drawable.icon_back,
						new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub

								if (isSwitch) {
									AppManager.getAppManager().finishActivity(
											NearbyElevatorPlaygroundMap.class);
								}
								AppManager.getAppManager().finishActivity(
										NearbyElevatorPlaygroundActivity.class);
							}
						}).setLeftText(R.string.back, null).setTitle("");
		type = getIntent().getIntExtra("type", 1);
		isSwitch = getIntent().getBooleanExtra("isSwitch", false);
		findViewById(R.id.map).setOnClickListener(this);
		initpager();

	}

	private void initpager() {
		mViewPager = (ViewPager) findViewById(R.id.pager);
		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		ArrayList<Fragment> fragments = new ArrayList<Fragment>();
		NearbyElevatorPlaygroundFragment all = new NearbyElevatorPlaygroundFragment();
		all.setArguments(new Bundle());
		all.getArguments().putInt("type", 0);

		NearbyElevatorPlaygroundFragment elevator = new NearbyElevatorPlaygroundFragment();
		elevator.setArguments(new Bundle());
		elevator.getArguments().putInt("type", 1);

		NearbyElevatorPlaygroundFragment playground = new NearbyElevatorPlaygroundFragment();
		playground.setArguments(new Bundle());
		playground.getArguments().putInt("type", 2);
		fragments.add(all);
		fragments.add(elevator);
		fragments.add(playground);
		ArrayList<String> titles = new ArrayList<String>();
		titles.add("全部");
		titles.add("电梯");
		titles.add("游乐");

		mViewPager.setAdapter(new PagerTabAdapter(fm, titles, fragments));
		tabs.tabSelectColor = 0xffffffff;
		tabs.tabTextColor = 0xffffffff;
		tabs.setViewPager(mViewPager);
		mViewPager.setCurrentItem(type);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.map:
			Bundle playground = new Bundle();
			playground.putInt("type", mViewPager.getCurrentItem());
			if (isSwitch) {
				setResult(RESULT_OK, playground);
				finish();
			} else {
				playground.putBoolean("isSwitch", true);
				UIHelper.jumpForResult(_activity,
						NearbyElevatorPlaygroundMap.class, playground, 1007);
			}

			break;

		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1007 && RESULT_OK == resultCode)
			mViewPager.setCurrentItem((data.getIntExtra("type", 1)));

	}

}
