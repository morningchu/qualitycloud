package com.midian.qualitycloud.ui.fragment;

import java.util.HashMap;
import java.util.Map;

import midian.baselib.api.ApiCallback;
import midian.baselib.base.BaseFragment;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.SearchEditText;
import midian.baselib.widget.SearchEditText.SearchEditTextListener;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.midian.qualitycloud.R;
import com.midian.qualitycloud.bean.GeoProTypesBean;
import com.midian.qualitycloud.bean.GeoProTypesBean.ContentGeoTypes;
import com.midian.qualitycloud.ui.geographical.GeographicalIndicationActivity;
import com.midian.qualitycloud.ui.geographical.QueryGIResultActivity;
import com.midian.qualitycloud.utils.AppUtil;

/**
 * 地理标识类别
 * 
 * @author MIDIAN
 * 
 */
public class QueryGIFragment extends BaseFragment implements OnClickListener,
		ApiCallback, SearchEditTextListener {
	private TextView type1, type2, type3, type4, type5, type6, type7, type8;
	SearchEditText mSearchEditText;

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		try {
			AppUtil.getQualityCloudApiClient(ac).getGeoProTypes(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		View main = inflater.inflate(
				R.layout.fragment_query_geographical_indication, null);
		type1 = (TextView) main.findViewById(R.id.type1);
		type2 = (TextView) main.findViewById(R.id.type2);
		type3 = (TextView) main.findViewById(R.id.type3);
		type4 = (TextView) main.findViewById(R.id.type4);
		type5 = (TextView) main.findViewById(R.id.type5);
		type6 = (TextView) main.findViewById(R.id.type6);
		type7 = (TextView) main.findViewById(R.id.type7);
		type8 = (TextView) main.findViewById(R.id.type8);
		type1.setOnClickListener(this);
		type2.setOnClickListener(this);
		type3.setOnClickListener(this);
		type4.setOnClickListener(this);
		type5.setOnClickListener(this);
		type6.setOnClickListener(this);
		type7.setOnClickListener(this);
		type8.setOnClickListener(this);
		type1.setTag(1);
		type2.setTag(2);
		type3.setTag(3);
		type4.setTag(4);
		type5.setTag(5);
		type6.setTag(6);
		type7.setTag(7);
		type8.setTag(8);
		mSearchEditText = (SearchEditText) main.findViewById(R.id.search);
		// mSearchEditText.getEditText().setFocusable(false);
		// mSearchEditText.setOnClickListener(this);
		// mSearchEditText.getEditText().setOnClickListener(this);
		mSearchEditText.setSearchEditTextListener(this);
		return main;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (arg0 instanceof TextView) {
			TextView tv = (TextView) arg0;
			Bundle b = new Bundle();
			String id = String.valueOf(list.get(tv.getText().toString()));
			b.putString("text", tv.getText().toString());
			b.putString("id", id);
			UIHelper.jump(_activity, QueryGIResultActivity.class, b);
		} else {

			// UIHelper.jump(_activity, QueryGIResultActivity.class);
		}

	}

	@Override
	public void onApiStart(String tag) {
		((GeographicalIndicationActivity) _activity).showLoadingDlg();

	}

	@Override
	public void onApiLoading(long count, long current, String tag) {

	}

	Map<String, String> list = new HashMap<String, String>();

	@Override
	public void onApiSuccess(NetResult res, String tag) {
		((GeographicalIndicationActivity) _activity).hideLoadingDlg();
		if (res.isOK()) {

			GeoProTypesBean typesBean = (GeoProTypesBean) res;
			for (ContentGeoTypes item : typesBean.getContent()) {
				String id = item.getGeo_pro_type_id();
				String name = item.getGeo_pro_type_name();
				list.put(name, id);
			}
		}

	}

	@Override
	public void onApiFailure(Throwable t, int errorNo, String strMsg, String tag) {
		((GeographicalIndicationActivity) _activity).hideLoadingDlg();

	}

	@Override
	public void onParseError(String tag) {
		((GeographicalIndicationActivity) _activity).hideLoadingDlg();

	}

	@Override
	public void search(String key) {
		// TODO Auto-generated method stub
		Bundle b = new Bundle();
		// String id = String.valueOf(list.get(tv.getText().toString()));
		String id = "";
		if (list.containsKey(key)) {
			id = list.get(key);
		}
		b.putString("text", key);
		b.putString("id", id);
		UIHelper.jump(_activity, QueryGIResultActivity.class, b);
	}
}
