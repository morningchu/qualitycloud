package com.midian.qualitycloud.ui.view;

import java.util.ArrayList;
import java.util.LinkedList;

import midian.baselib.api.ApiCallback;
import midian.baselib.app.AppContext;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.Func;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.midian.qualitycloud.R;
import com.midian.qualitycloud.bean.CheckAreasBean;
import com.midian.qualitycloud.bean.CheckAreasBean.Areas;
import com.midian.qualitycloud.bean.CheckAreasBean.ContentCheckAreas;
import com.midian.qualitycloud.ui.adapter.TextAdapter;
import com.midian.qualitycloud.utils.AppUtil;

public class ViewMiddle extends LinearLayout implements ViewBaseAction,
		ApiCallback {

	private ListView regionListView;
	private ListView plateListView;
	private ArrayList<String> groups = new ArrayList<String>();
	private ArrayList<String> groupsId = new ArrayList<String>();
	private LinkedList<String> childrenItem = new LinkedList<String>();
	private LinkedList<String> childrenIdItem = new LinkedList<String>();

	private SparseArray<LinkedList<String>> children = new SparseArray<LinkedList<String>>();
	private SparseArray<LinkedList<String>> childrenIds = new SparseArray<LinkedList<String>>();
	private TextAdapter plateListViewAdapter;
	private TextAdapter earaListViewAdapter;
	private OnSelectListener mOnSelectListener;
	private int tEaraPosition = 0;
	private int tBlockPosition = 0;
	private String showString = "不限";
	private String showId = "";
	private String name = "";
	private String id = "";
	private boolean isClick = false;
	private TextView footer1;
	private TextView footer;

	public ViewMiddle(Context context) {
		super(context);
		init(context);
	}

	public ViewMiddle(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public void updateShowText(String showArea, String showBlock) {
		if (showArea == null || showBlock == null) {
			return;
		}
		for (int i = 0; i < groups.size(); i++) {
			if (groups.get(i).equals(showArea)) {
				earaListViewAdapter.setSelectedPosition(i);
				childrenItem.clear();
				childrenIdItem.clear();
				if (i < children.size()) {
					childrenItem.addAll(children.get(i));
					childrenIdItem.addAll(childrenIds.get(i));
				}
				tEaraPosition = i;
				break;
			}
		}
		for (int j = 0; j < childrenItem.size(); j++) {
			if (childrenItem.get(j).replace("不限", "").equals(showBlock.trim())) {
				plateListViewAdapter.setSelectedPosition(j);
				tBlockPosition = j;
				break;
			}
		}
		setDefaultSelect();
	}

	private void init(Context context) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_region, this, true);
		regionListView = (ListView) findViewById(R.id.listView);
		plateListView = (ListView) findViewById(R.id.listView2);
		footer = new TextView(context);

		footer.setTextColor(getResources().getColor(R.color.text_bg20));
		footer.setPadding(Func.Dp2Px(context, 15), Func.Dp2Px(context, 12), 0,
				Func.Dp2Px(context, 12));
		footer.setText("全部");
		footer.setTextSize(15);
		footer1 = new TextView(context);

		footer1.setTextColor(getResources().getColor(R.color.text_bg20));
		footer1.setPadding(Func.Dp2Px(context, 15), Func.Dp2Px(context, 12), 0,
				Func.Dp2Px(context, 12));
		footer1.setText("全部");
		footer1.setTextSize(15);
		// footer1.setBackgroundResource(R.drawable.choose_eara_item_selector);
		regionListView.addHeaderView(footer1);
		footer1.setBackgroundColor(0xFFFFFFFF);
		footer.setBackgroundColor(0xFFFFFFFF);
		footer1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				v.setBackgroundColor(0xFFF4F4F4);
				if (mOnSelectListener != null) {
					 mOnSelectListener.getValue("", "", false);
				}

				earaListViewAdapter.clearSelectedText();
				earaListViewAdapter.notifyDataSetChanged();
				plateListViewAdapter.clear();
			}
		});
		plateListView.addHeaderView(footer);
		// footer.setBackgroundResource(R.drawable.choose_eara_item_selector);
		footer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				v.setBackgroundColor(0xFFF4F4F4);
				footer.setBackgroundColor(0xFFF4F4F4);
				if (mOnSelectListener != null) {
					mOnSelectListener.getValue(id, name, true);
					plateListViewAdapter.clearSelectedText();
					plateListViewAdapter.notifyDataSetChanged();
				}
			}
		});

		setBackgroundColor(Color.parseColor("#00000000"));
		AppUtil.getQualityCloudApiClient(
				(AppContext) context.getApplicationContext()).getCheckAreas(
				this);

		earaListViewAdapter = new TextAdapter(context, groups,
				R.drawable.choose_item_selecteds,
				R.drawable.choose_eara_item_selector);
		earaListViewAdapter.setTextSize(15);
		earaListViewAdapter.setSelectedPositionNoNotify(tEaraPosition);
		regionListView.setAdapter(earaListViewAdapter);
		earaListViewAdapter
				.setOnItemClickListener(new TextAdapter.OnItemClickListener() {

					@Override
					public void onItemClick(View view, int position) {
						footer1.setBackgroundColor(0xFFFFFFFF);
						footer.setBackgroundColor(0xFFFFFFFF);
						if (position < children.size()) {
							name = groups.get(position);
							id = groupsId.get(position);
							childrenItem.clear();
							childrenItem.addAll(children.get(position));
							childrenIdItem.clear();
							childrenIdItem.addAll(childrenIds.get(position));
							plateListViewAdapter.notifyDataSetChanged();
						}
					}
				});
		if (tEaraPosition < children.size())
			childrenItem.addAll(children.get(tEaraPosition));
		plateListViewAdapter = new TextAdapter(context, childrenItem,
				R.drawable.choose_item_selecteds,
				R.drawable.choose_plate_item_selector);
		plateListViewAdapter.setTextSize(15);
		plateListViewAdapter.setSelectedPositionNoNotify(tBlockPosition);
		plateListView.setAdapter(plateListViewAdapter);
		plateListViewAdapter
				.setOnItemClickListener(new TextAdapter.OnItemClickListener() {

					@Override
					public void onItemClick(View view, final int position) {
						footer.setBackgroundColor(0xFFFFFFFF);
						showString = childrenItem.get(position);
						showId = childrenIdItem.get(position);
						if (mOnSelectListener != null) {

							mOnSelectListener.getValue(showId, showString,
									false);
						}

					}
				});
		if (tBlockPosition < childrenItem.size()) {
			showString = childrenItem.get(tBlockPosition);
			showId = childrenIdItem.get(tBlockPosition);
		}
		if (showString.contains("不限")) {
			showString = showString.replace("不限", "");
			showId = "";
		}
		setDefaultSelect();

	}

	public void setDefaultSelect() {
		regionListView.setSelection(tEaraPosition);
		plateListView.setSelection(tBlockPosition);
	}

	public String getShowText() {
		return showString;
	}

	public void setOnSelectListener(OnSelectListener onSelectListener) {
		mOnSelectListener = onSelectListener;
	}

	public interface OnSelectListener {
		public void getValue(String id, String showText, boolean isGroup);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

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
		CheckAreasBean checkAreas = (CheckAreasBean) res;
		if (res.isOK()) {

			int i = 0;
			for (ContentCheckAreas items : checkAreas.getContent()) {
				String city_id = items.getCity_id();
				String city_name = items.getCity_name();
				groups.add(city_name);
				groupsId.add(city_id);
				LinkedList<String> tItem = new LinkedList<String>();
				LinkedList<String> iItem = new LinkedList<String>();
				for (Areas areas : items.getAreas()) {
					String area_id = areas.getArea_id();
					String area_name = areas.getArea_name();
					tItem.add(area_name);
					iItem.add(area_id);
				}
				children.put(i, tItem);
				childrenIds.put(i, iItem);
				i++;
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
