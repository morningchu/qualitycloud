package com.midian.qualitycloud.ui.main;

import midian.baselib.app.AppManager;
import midian.baselib.base.BaseFragmentActivity;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;
import midian.baselib.widget.SearchEditText;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.midian.qualitycloud.R;
import com.midian.qualitycloud.ui.fragment.NearbyElevatorPlaygroundFragment;

/**
 * 电梯游乐场列表(地图跳过来的)
 * 
 * @author MIDIAN
 * 
 */
public class ElevatorPlaygroundListForMapActivity extends BaseFragmentActivity
		implements SearchEditText.SearchEditTextListener {
	BaseLibTopbarView mBaseLibTopbarView;

	private int type;// 1是电梯；2是游乐设施
	SearchEditText search;
	NearbyElevatorPlaygroundFragment epFragment;
	String ids;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nearby_elevator_playground_list);
		mBaseLibTopbarView = (BaseLibTopbarView) findViewById(R.id.topbar);
		search = findView(R.id.search);
		search.setBackgroundResource(R.drawable.bg_search_white);
		search.setSearchEditTextListener(this);
		search.setVisibility(View.GONE);
		type = getIntent().getIntExtra("type", 1);
		ids = getIntent().getStringExtra("ids");
		mBaseLibTopbarView
				.setLeftImageButton(R.drawable.icon_back,
						UIHelper.finish(_activity))
				.setLeftText(R.string.back, null)
				.setTitle(type == 1 ? "电梯" : "游乐设施");
		System.out.println("ElevatorPlaygroundListForMapActivity");
		initFragement();

	}

	public int getFragmentContentId() {
		return R.id.fl_content;
	}

	private void initFragement() {
		epFragment = new NearbyElevatorPlaygroundFragment();
		epFragment.setArguments(new Bundle());
		epFragment.getArguments().putInt("type", type);
		epFragment.getArguments().putString("ids", ids);
		switchFragment(epFragment);
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
