package com.midian.qualitycloud.ui.geographical;

import java.util.ArrayList;

import midian.baselib.adapter.PagerTabAdapter;
import midian.baselib.base.BaseFragmentActivity;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;
import midian.baselib.widget.PagerSlidingTabStrip;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.midian.qualitycloud.R;
import com.midian.qualitycloud.ui.fragment.ProductDetailFragment;
import com.midian.qualitycloud.ui.fragment.RelatedRecommendFragment;
import com.midian.qualitycloud.ui.fragment.UseCompanyFragment;

/**
 * 地理标识详情
 * 
 * @author MIDIAN
 * 
 */
public class GIDetailActivity extends BaseFragmentActivity {
	private BaseLibTopbarView mBaseLibTopbarView;
	ViewPager pager;
	PagerSlidingTabStrip mPagerSlidingTabStrip;
	String geo_id, geo_pro_type_id, name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gi_detail);
		geo_id = getIntent().getStringExtra("id");
		name = getIntent().getStringExtra("name");
		geo_pro_type_id = getIntent().getStringExtra("geo_pro_type_id");
		initTitle();
		initViewPager();
	}

	private void initTitle() {
		mBaseLibTopbarView = (BaseLibTopbarView) findViewById(R.id.topbar);
		mBaseLibTopbarView
				.setLeftImageButton(R.drawable.icon_back,
						UIHelper.finish(_activity))
				.setMode(BaseLibTopbarView.MODE_1).setTitle(name)
				.setLeftText("返回", null).getTitle_tv()
				.setTextColor(Color.parseColor("#FFFFFF"));
		mBaseLibTopbarView.getLeft_tv().setTextColor(
				Color.parseColor("#FFFFFF"));
	}

	/**
	 * 
	 * 初始化底部viewpager
	 */
	private void initViewPager() {
		mPagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		ArrayList<Fragment> fragments = new ArrayList<Fragment>();
		ProductDetailFragment detailFragment = new ProductDetailFragment();
		detailFragment.setArguments(new Bundle());
		detailFragment.getArguments().putString("geo_id", geo_id);
		fragments.add(detailFragment);
		UseCompanyFragment companyFragment = new UseCompanyFragment();
		companyFragment.setArguments(new Bundle());
		companyFragment.getArguments().putString("geo_id", geo_id);
		fragments.add(companyFragment);
		RelatedRecommendFragment recommendFragment = new RelatedRecommendFragment();
		recommendFragment.setArguments(new Bundle());
		recommendFragment.getArguments().putString("geo_pro_type_id",
				geo_pro_type_id);
		recommendFragment.getArguments().putString("geo_id", geo_id);
		fragments.add(recommendFragment);
		ArrayList<String> titles = new ArrayList<String>();
		titles.add("产品简介");
		titles.add("使用企业");
		titles.add("相关推荐");
		pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(new PagerTabAdapter(fm, titles, fragments));

		mPagerSlidingTabStrip.indicatorColor = 0xFF47A0DB;
		mPagerSlidingTabStrip.tabTextColor = 0xFF202020;
		mPagerSlidingTabStrip.tabSelectColor = 0xFF202020;
		mPagerSlidingTabStrip.setViewPager(pager);
	}

}
