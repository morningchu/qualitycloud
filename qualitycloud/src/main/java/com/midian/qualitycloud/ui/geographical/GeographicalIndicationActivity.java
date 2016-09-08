package com.midian.qualitycloud.ui.geographical;

import midian.baselib.base.BaseFragmentActivity;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;

import com.midian.configlib.ServerConstant;
import com.midian.qualitycloud.R;
import com.midian.qualitycloud.ui.fragment.BrandAboutFragment;
import com.midian.qualitycloud.ui.fragment.BrandDistributionFragment;
import com.midian.qualitycloud.ui.fragment.BrandGuideFragment;
import com.midian.qualitycloud.ui.fragment.GIGuideFragment;
import com.midian.qualitycloud.ui.fragment.PoliticDocumentFragment;
import com.midian.qualitycloud.ui.fragment.QueryGIFragment;
import com.midian.qualitycloud.widget.SelectTabWithUpLine;

/**
 * 地理标识
 * 
 * @author MIDIAN
 * 
 */
public class GeographicalIndicationActivity extends BaseFragmentActivity
		implements SelectTabWithUpLine.onTabChangeListener {
	BaseLibTopbarView mBaseLibTopbarView;
	SelectTabWithUpLine mSelectTabWithUpLine;

	GIGuideFragment mGuideFragment;
	BrandAboutFragment mBrandAboutFragment;
	QueryGIFragment mQueryGIFragment;
	PoliticDocumentFragment mFileFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_geographical_indication);
		initTitle();
		initFragment();
		init();
	}

	public void init() {
		mSelectTabWithUpLine = (SelectTabWithUpLine) findViewById(R.id.tab);
		mSelectTabWithUpLine.indicatorColor = 0xFF47A0DB;
		mSelectTabWithUpLine.tabTextColor = 0xFF202020;
		mSelectTabWithUpLine.tabSelectColor = 0xFF202020;
		mSelectTabWithUpLine
				.setTabs(new String[] { "首页", "申报指南", "政策文件", "关于" });
		mSelectTabWithUpLine.setOnTabChangeListener(this);
		mSelectTabWithUpLine.setchangeState(0);
		onTabChange(0, true);
	}

	private void initFragment() {
		mGuideFragment = new GIGuideFragment();
		mBrandAboutFragment = new BrandAboutFragment();
		mQueryGIFragment = new QueryGIFragment();
		mFileFragment = new PoliticDocumentFragment();
		Bundle b = new Bundle();
		b.putString("url", ServerConstant.BASEURL + ac.link_geo_pro);

		mBrandAboutFragment.setArguments(b);
		Bundle f1 = new Bundle();
		f1.putString("url", ServerConstant.BASEURL + ac.link_geo_pro);
		mFileFragment.setArguments(f1);
		// switchFragment(mBrandCatalogue);
	}

	public void initTitle() {
		String name = "中国地理标志保护产品";
		mBaseLibTopbarView = (BaseLibTopbarView) findViewById(R.id.topbar);
		mBaseLibTopbarView
				.setLeftImageButton(R.drawable.icon_back,
						UIHelper.finish(_activity)).setTitle(name)
				.setLeftText("返回", null).setMode(BaseLibTopbarView.MODE_1)
				.getTitle_tv().setTextColor(Color.parseColor("#FFFFFF"));
		mBaseLibTopbarView.getTitle_tv().setTextSize(
				TypedValue.COMPLEX_UNIT_SP, 15);
		mBaseLibTopbarView.getLeft_tv().setTextColor(
				Color.parseColor("#FFFFFF"));
		// String title;
		// if (name.length() > 14) {
		// title = name.substring(0, 14) + "\n"
		// + name.substring(14, name.length());
		// mBaseLibTopbarView.getTitle_tv().setTextSize(
		// TypedValue.COMPLEX_UNIT_SP, 16);
		// mBaseLibTopbarView.setTitle(title);
		// } else {
		// mBaseLibTopbarView.setTitle(name);
		// }
	}

	public int getFragmentContentId() {
		return R.id.fl_content;
	}

	@Override
	public void onTabChange(int index, boolean isSelect) {
		// TODO Auto-generated method stub
		switch (index) {
		case 0:
			switchFragment(mQueryGIFragment);
			break;
		case 1:
			switchFragment(mGuideFragment);
			break;
		case 2:
			switchFragment(mFileFragment);
			break;
		case 3:
			switchFragment(mBrandAboutFragment);
			break;

		default:
			break;
		}
	}
}
