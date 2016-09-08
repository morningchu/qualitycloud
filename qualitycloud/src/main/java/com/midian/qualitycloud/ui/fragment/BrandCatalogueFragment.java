package com.midian.qualitycloud.ui.fragment;

import java.util.ArrayList;
import java.util.Calendar;

import midian.baselib.adapter.PagerTabAdapter;
import midian.baselib.base.BaseFragment;
import midian.baselib.widget.PagerSlidingTabStrip;
import midian.baselib.widget.SearchEditText;
import midian.baselib.widget.SearchEditText.SearchEditTextListener;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.midian.qualitycloud.R;

/**
 * 名牌名录
 * 
 * @author MIDIAN
 * 
 */
public class BrandCatalogueFragment extends BaseFragment implements
		SearchEditTextListener {
	ViewPager pager;
	PagerSlidingTabStrip mPagerSlidingTabStrip;
	SearchEditText mSearchEditText;
	private String text;
	private BrandCataloguePageFragment mBrandCataloguePageFragment1;
	private BrandCataloguePageFragment mBrandCataloguePageFragment2;
	private BrandCataloguePageFragment mBrandCataloguePageFragment3;

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View main = inflater.inflate(R.layout.fragment_brand_catalogue, null);
		initViewPager(main);
		return main;
	}

	/**
	 * 初始化底部viewpager
	 */
	private void initViewPager(View main) {
		mPagerSlidingTabStrip = (PagerSlidingTabStrip) main
				.findViewById(R.id.tabs);
		text = getArguments().getString("text");
		mSearchEditText = (SearchEditText) main.findViewById(R.id.search);
		mSearchEditText.setText(text);
		mSearchEditText.setSearchEditTextListener(this);
		ArrayList<Fragment> views = new ArrayList<Fragment>();
		ArrayList<String> titles = new ArrayList<String>();
		System.currentTimeMillis();
		Calendar c = Calendar.getInstance();
		int i = c.get(Calendar.YEAR);
		mBrandCataloguePageFragment1 = new BrandCataloguePageFragment();
		mBrandCataloguePageFragment1.setArguments(new Bundle());
		mBrandCataloguePageFragment1.getArguments().putString("year",
				String.valueOf(i - 3));
		mBrandCataloguePageFragment1.getArguments().putString("keywords", text);
		mBrandCataloguePageFragment2 = new BrandCataloguePageFragment();
		mBrandCataloguePageFragment2.setArguments(new Bundle());
		mBrandCataloguePageFragment2.getArguments().putString("year",
				String.valueOf(i - 2));
		mBrandCataloguePageFragment2.getArguments().putString("keywords", text);

		mBrandCataloguePageFragment3 = new BrandCataloguePageFragment();
		mBrandCataloguePageFragment3.setArguments(new Bundle());
		mBrandCataloguePageFragment3.getArguments().putString("year",
				String.valueOf(i-1));
		mBrandCataloguePageFragment3.getArguments().putString("keywords", text);

		views.add(mBrandCataloguePageFragment1);
		views.add(mBrandCataloguePageFragment2);
		views.add(mBrandCataloguePageFragment3);
		titles.add(i - 3 + "年度");
		titles.add(i - 2 + "年度");
		titles.add(i-1 + "年度");
		pager = (ViewPager) main.findViewById(R.id.pager);
		pager.setAdapter(new PagerTabAdapter(fm, titles, views));

		mPagerSlidingTabStrip.tabTextColor = 0xFF202020;
		mPagerSlidingTabStrip.tabSelectColor = 0xFF202020;
		mPagerSlidingTabStrip.setViewPager(pager);
	}

	@Override
	public void search(String key) {
		// TODO Auto-generated method stub
		mBrandCataloguePageFragment1.search(key);
		mBrandCataloguePageFragment2.search(key);
		mBrandCataloguePageFragment3.search(key);
	}
}
