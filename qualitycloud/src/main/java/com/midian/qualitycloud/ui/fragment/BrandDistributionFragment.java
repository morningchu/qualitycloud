package com.midian.qualitycloud.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import midian.baselib.api.ApiCallback;
import midian.baselib.base.BaseFragment;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.UIHelper;
import android.R.integer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.midian.qualitycloud.R;
import com.midian.qualitycloud.bean.AreaStatsBean;
import com.midian.qualitycloud.bean.AreaStatsBean.ContentAreaStats;
import com.midian.qualitycloud.ui.guizhoubrand.BrandDistributionDetailActivity;
import com.midian.qualitycloud.ui.guizhoubrand.GuizhouBrandActivity;
import com.midian.qualitycloud.utils.AppUtil;

/**
 * 名牌分布
 * 
 * @author MIDIAN
 * 
 */
public class BrandDistributionFragment extends BaseFragment implements
		OnClickListener, ApiCallback {
	private RelativeLayout city1, city2, city3, city4, city5, city6, city7,
			city8, city9, city10, city11, city12;
	private TextView city1_1, city2_1, city3_1, city4_1, city5_1, city6_1,
			city7_1, city8_1, city9_1, city10_1, city11_1, city12_1;
	private TextView city1_2, city2_2, city3_2, city4_2, city5_2, city6_2,
			city7_2, city8_2, city9_2, city10_2, city11_2, city12_2;
	private String area_name;
	private String count;

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		// TODO Auto-generated method stub
		View main = inflater
				.inflate(R.layout.fragment_brand_distribution, null);
		for (int i = 1; i < 13; i++) {
			int id = ac.resourceUtil.getResId("city" + i + "_2", "id");
			Views.add((TextView) main.findViewById(id));
		}

		for (int i = 1; i < 13; i++) {
			int id = ac.resourceUtil.getResId("city" + i + "_1", "id");
			main.findViewById(id).setOnClickListener(this);
			main.findViewById(id).setTag((i-1));
		}

		names.add("威宁");
		names.add("毕节");
		names.add("贵阳");
		names.add("六盘水");
		names.add("黔西南");
		names.add("黔东南");
		names.add("铜仁");
		names.add("遵义");
		names.add("仁怀");
		names.add("贵安新区");
		names.add("安顺");
		names.add("黔南");

		try {
			AppUtil.getQualityCloudApiClient(ac).getAreaStats(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return main;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (arg0 instanceof TextView) {
			TextView tv = (TextView) arg0;
			Bundle b = new Bundle();
			int index = (Integer) arg0.getTag();
			String name1 = names.get(index);
			b.putString("name", name1);
			UIHelper.jump(_activity, BrandDistributionDetailActivity.class, b);

		}
	}

	class Item {

		public Item(String name, String count) {
			super();
			this.name = name;
			this.count = count;
		}

		public String name;
		public String count;
	}

	List<Item> mlist;

	@Override
	public void onApiStart(String tag) {
		((GuizhouBrandActivity) _activity).showLoadingDlg();

	}

	@Override
	public void onApiLoading(long count, long current, String tag) {
		// TODO Auto-generated method stub

	}

	Map<String, String> list = new HashMap<String, String>();
	List<TextView> Views = new ArrayList<TextView>();
	List<String> names = new ArrayList<String>();

	@Override
	public void onApiSuccess(NetResult res, String tag) {
		((GuizhouBrandActivity) _activity).hideLoadingDlg();
		AreaStatsBean areaStats = (AreaStatsBean) res;
		if (res.isOK()) {
			mlist = new ArrayList<BrandDistributionFragment.Item>();

			for (ContentAreaStats item : areaStats.getContent()) {
				area_name = item.getArea_name();
				count = item.getCount();

				list.put(area_name, count);
			}
			for (int i = 0; i < Views.size(); i++) {

				setCountValue(Views.get(i), names.get(i));
			}
		} else {
			ac.handleErrorCode(_activity, areaStats.error_code);
		}

	}

	public void setCountValue(TextView tv, String key) {
		if (list.containsKey(key)) {
			tv.setText(list.get(key));
		} else {
			tv.setText("0");
		}
	}

	@Override
	public void onApiFailure(Throwable t, int errorNo, String strMsg, String tag) {
		((GuizhouBrandActivity) _activity).hideLoadingDlg();
	}

	@Override
	public void onParseError(String tag) {
		((GuizhouBrandActivity) _activity).hideLoadingDlg();
	}
}
