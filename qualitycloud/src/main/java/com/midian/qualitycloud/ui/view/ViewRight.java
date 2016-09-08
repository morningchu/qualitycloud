package com.midian.qualitycloud.ui.view;

import java.util.ArrayList;

import midian.baselib.api.ApiCallback;
import midian.baselib.app.AppContext;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.Func;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.midian.qualitycloud.R;
import com.midian.qualitycloud.bean.CheckQualitiesBean;
import com.midian.qualitycloud.bean.CheckQualitiesBean.ContentCheckQualities;
import com.midian.qualitycloud.ui.adapter.TextAdapter;
import com.midian.qualitycloud.utils.AppUtil;

public class ViewRight extends RelativeLayout implements ViewBaseAction,
		ApiCallback {

	private ListView mListView;
	 private String[] items ;// 显示字段
	 private String[] itemsVaule ;// 隐藏id
	private ArrayList<String> quality_id;
	private ArrayList<String> quality_name;
	private OnSelectListener mOnSelectListener;
	private TextAdapter adapter;
	private String mDistance;
	private String showText = "所属区域";
	private Context mContext;

	public String getShowText() {
		return showText;
	}

	public ViewRight(Context context) {
		super(context);
		init(context);
	}

	public ViewRight(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public ViewRight(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		mContext = context;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.view_distance, this, true);
		// setBackgroundDrawable(getResources().getDrawable(R.drawable.choosearea_bg_right));
		quality_id = new ArrayList<String>();
		quality_name = new ArrayList<String>();
		mListView = (ListView) view.findViewById(R.id.listView);
		TextView footer = new TextView(context);
		footer.setTextColor(getResources().getColor(R.color.text_bg20));
		footer.setPadding(Func.Dp2Px(context, 15), Func.Dp2Px(context, 12), 0, Func.Dp2Px(context, 12));
		footer.setText("全部");
		footer.setTextSize(15);
		footer.setBackgroundResource(android.R.color.white);
		mListView.addHeaderView(footer);
		footer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mOnSelectListener != null) {
					mOnSelectListener.getValue("", "");
				}
			}
		});
		AppUtil.getQualityCloudApiClient(
				(AppContext) context.getApplicationContext())
				.getCheckQualities(this);

		mListView.setDivider(getResources()
				.getDrawable(R.drawable.divider_line));
		mListView.setSelector(getResources().getDrawable(
				R.drawable.c_item_selector));
		mListView.setBackgroundColor(Color.parseColor("#FBFBFB"));
		adapter = new TextAdapter(context, quality_name, R.drawable.choose_item_selecteds,
				R.drawable.choose_eara_item_selector);
		adapter.setTextSize(15);
		if (mDistance != null) {
			for (int i = 0; i < itemsVaule.length; i++) {
				if (itemsVaule[i].equals(mDistance)) {
					adapter.setSelectedPositionNoNotify(i);
					showText = items[i];
					break;
				}
			}
		}
		mListView.setAdapter(adapter);
		adapter.setOnItemClickListener(new TextAdapter.OnItemClickListener() {

			@Override
			public void onItemClick(View view, int position) {

				if (mOnSelectListener != null) {
					showText = items[position];
					mOnSelectListener.getValue(itemsVaule[position],
							items[position]);
				}
			}
		});
	}

	public void setOnSelectListener(OnSelectListener onSelectListener) {
		mOnSelectListener = onSelectListener;
	}

	public interface OnSelectListener {
		public void getValue(String distance, String showText);
	}

	@Override
	public void hide() {

	}

	@Override
	public void show() {

	}

	@Override
	public void onApiStart(String tag) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onApiLoading(long count, long current, String tag) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onApiSuccess(NetResult res, String tag) {
		// TODO Auto-generated method stub
		CheckQualitiesBean checkQualites = (CheckQualitiesBean) res;
		if (res.isOK()) {
			for (ContentCheckQualities item : checkQualites.getContent()) {
				String id = item.getCheck_quality_id();
				String name = item.getCheck_quality_name();
				quality_id.add(id);
				quality_name.add(name);
			}
			items = quality_name.toArray(new String[quality_name.size()]);
			itemsVaule = quality_id.toArray(new String[quality_id.size()]);
			for(String s : items){
			}
			for(String d : itemsVaule){
			}
		}
	}

	@Override
	public void onApiFailure(Throwable t, int errorNo, String strMsg, String tag) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onParseError(String tag) {
		// TODO Auto-generated method stub

	}

}
