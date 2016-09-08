package com.midian.qualitycloud.ui.guizhoubrand;

import midian.baselib.base.BaseFragmentActivity;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;
import midian.baselib.widget.SearchEditText;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.midian.configlib.ServerConstant;
import com.midian.qualitycloud.R;
import com.midian.qualitycloud.ui.fragment.BrandAboutFragment;
import com.midian.qualitycloud.ui.fragment.BrandCatalogueFragment;
import com.midian.qualitycloud.ui.fragment.BrandDistributionFragment;
import com.midian.qualitycloud.ui.fragment.BrandGuideFragment;
import com.midian.qualitycloud.ui.fragment.MyAttentionFragment;
import com.midian.qualitycloud.widget.SelectTabWithUpLine;

/**
 * 贵州名牌
 * 
 * @author MIDIAN
 * 
 */
public class GuizhouBrandActivity extends BaseFragmentActivity implements
		SelectTabWithUpLine.onTabChangeListener, OnClickListener {
	BaseLibTopbarView mBaseLibTopbarView;
	SelectTabWithUpLine mSelectTabWithUpLine;
	BrandGuideFragment mBrandGuideFragment;
	BrandAboutFragment mBrandAboutFragment;
	BrandCatalogueFragment mBrandCatalogue;
	BrandDistributionFragment mBrandDistribution;

	int index = 0;
	private String text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guizhou_brand);
		index = getIntent().getIntExtra("index", 0);
		text = getIntent().getStringExtra("text");
		initTitle();
		initFragment();
		init();
	}

	public void init() {
		mSelectTabWithUpLine = (SelectTabWithUpLine) findViewById(R.id.tab);
		mSelectTabWithUpLine.indicatorColor = 0xFF47A0DB;
		mSelectTabWithUpLine.tabTextColor = 0xFF202020;
		mSelectTabWithUpLine.tabSelectColor = 0xFF202020;
		mSelectTabWithUpLine.setTabs(new String[] { "名牌名录", "名牌分布", "申报指南",
				"关于名牌" });
		mSelectTabWithUpLine.setOnTabChangeListener(this);
		mSelectTabWithUpLine.setchangeState(index);
		onTabChange(index, true);
		
		// SearchEditText search = (SearchEditText) findViewById(R.id.search);
		// search.setOnClickListener(this);
	}

	private void initFragment() {
		mBrandGuideFragment = new BrandGuideFragment();
		mBrandAboutFragment = new BrandAboutFragment();
		mBrandCatalogue = new BrandCatalogueFragment();
		Bundle bundle = new Bundle();
		bundle.putString("text", text);
		mBrandCatalogue.setArguments(bundle);
		mBrandDistribution = new BrandDistributionFragment();
		
		Bundle b = new Bundle();
		b.putString("url", ServerConstant.BASEURL + ac.link_brand);
		mBrandAboutFragment.setArguments(b);
	}

	public void initTitle() {
		mBaseLibTopbarView = (BaseLibTopbarView) findViewById(R.id.topbar);
		mBaseLibTopbarView
				.setLeftImageButton(R.drawable.icon_back,
						UIHelper.finish(_activity)).setTitle("贵州省名牌名录")
				.setLeftText("返回", null).setMode(BaseLibTopbarView.MODE_1)
				.getTitle_tv().setTextColor(Color.parseColor("#FFFFFF"));
		mBaseLibTopbarView.getLeft_tv().setTextColor(
				Color.parseColor("#FFFFFF"));
	}

	public int getFragmentContentId() {
		return R.id.fl_content;
	}

	@Override
	public void onTabChange(int index, boolean isSelect) {
		// TODO Auto-generated method stub
		switch (index) {
		case 0:
			switchFragment(mBrandCatalogue);
			break;
		case 1:
			switchFragment(mBrandDistribution);
			break;
		case 2:
			switchFragment(mBrandGuideFragment);
			break;
		case 3:
			switchFragment(mBrandAboutFragment);
			break;

		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {

	}

}
