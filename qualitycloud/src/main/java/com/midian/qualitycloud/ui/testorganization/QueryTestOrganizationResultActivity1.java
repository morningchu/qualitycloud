package com.midian.qualitycloud.ui.testorganization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import midian.baselib.app.AppContext;
import midian.baselib.base.BaseListActivity;
import midian.baselib.bean.NetResult;
import midian.baselib.shizhefei.view.listviewhelper.IDataAdapter;
import midian.baselib.shizhefei.view.listviewhelper.IDataSource;
import midian.baselib.utils.ScreenUtils;
import midian.baselib.utils.UIHelper;
import midian.baselib.version.VersionUpdateUtil;
import midian.baselib.widget.BaseLibTopbarView;
import midian.baselib.widget.FlowLayout;
import midian.baselib.widget.SearchEditText;
import midian.baselib.widget.SearchEditText.SearchEditTextListener;
import midian.baselib.widget.pulltorefresh.PullToRefreshListView;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.midian.fastdevelop.afinal.FinalActivity;
import com.midian.fastdevelop.afinal.exception.AfinalException;
import com.midian.qualitycloud.R;
import com.midian.qualitycloud.bean.CheckAreasBean;
import com.midian.qualitycloud.bean.CheckTypesBean;
import com.midian.qualitycloud.bean.CheckTypesBean.ContentCheckTypes;
import com.midian.qualitycloud.bean.GeoProTypesBean;
import com.midian.qualitycloud.bean.CheckAreasBean.Areas;
import com.midian.qualitycloud.bean.CheckAreasBean.ContentCheckAreas;
import com.midian.qualitycloud.bean.CheckOrgsBean.ContentCheckOrgs;
import com.midian.qualitycloud.bean.GeoProTypesBean.ContentGeoTypes;
import com.midian.qualitycloud.datasource.QueryTestOrganizationDatasource;
import com.midian.qualitycloud.itemview.QueryTestOrganizationItemTpl;
import com.midian.qualitycloud.ui.adapter.GirdAdapter;
import com.midian.qualitycloud.ui.main.NearbyElevatorOrPlaygroundMap;
import com.midian.qualitycloud.ui.view.ExpandTabView;
import com.midian.qualitycloud.ui.view.ViewMiddle;
import com.midian.qualitycloud.ui.view.ViewRight;
import com.midian.qualitycloud.utils.AppUtil;
import com.umeng.socialize.utils.Log;

/**
 * 检测服务一级详情
 * 
 * @author Administrator
 * 
 */

public class QueryTestOrganizationResultActivity1 extends
		BaseListActivity<ContentCheckOrgs> implements SearchEditTextListener {
	private ExpandTabView expandTabView;
	private ArrayList<View> mViewArray = new ArrayList<View>();
	private ViewMiddle viewMiddle;
	private ViewRight viewRight;
	private GridView gridView;
	FlowLayout mFlowLayout;
	private TextView grid_tv1, grid_tv2, grid_tv3, grid_tv4, grid_tv5,
			grid_tv6, grid_tv7, grid_tv8, grid_tv9, grid_tv10;
	private List<City> mapLsit = new ArrayList<City>();
	BaseLibTopbarView mBaseLibTopbarView;
	String text = "";
	String field_id;
	SearchEditText mSearchEditText;
	QueryTestOrganizationDatasource organizationDatasource;
	ListView father_lv, son_lv;
	String str = "";
	GirdAdapter mGirdAdapter;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		text = getIntent().getStringExtra("text");
		field_id = getIntent().getStringExtra("field_id");
		str = getIntent().getStringExtra("key");
		if (TextUtils.isEmpty(field_id)) {
			organizationDatasource = new QueryTestOrganizationDatasource(
					QueryTestOrganizationResultActivity1.this, "", text);
		} else {
			organizationDatasource = new QueryTestOrganizationDatasource(
					QueryTestOrganizationResultActivity1.this, field_id, "");
		}
		super.onCreate(savedInstanceState);
		initTitle();
		View head = LayoutInflater.from(_activity).inflate(R.layout.head_test,
				null);
		mSearchEditText = (SearchEditText) head
				.findViewById(R.id.search_expandtab);
		// gridView = (GridView) findViewById(R.id.gridView);
		// gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));

		// gridView.setAdapter(new GirdAdapter());
		mSearchEditText.setText(text);
		if (TextUtils.isEmpty(str)) {
		} else {
			mSearchEditText.setText(str);
		}
		mSearchEditText.setSearchEditTextListener(this);
		try {

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		refreshListView.setPullLoadEnabled(false);
		listView.addHeaderView(head);
		initViews(head);
		initView();
		initVaule();
		initListener();

	}

	public class City {

		public City(String id, String name) {
			super();
			this.id = id;
			this.name = name;
		}

		String id;
		String name;
	}

	private void initViews(View head) {
		gridView = (GridView) head.findViewById(R.id.gridView);
		mFlowLayout=(FlowLayout) head.findViewById(R.id.flowLayout);
		// mapLsit.add(new City("1","贵州"));
		// mapLsit.add(new City("1","贵州"));
		// mapLsit.add(new City("1","贵州"));
		// mapLsit.add(new City("1","贵州"));
		// mapLsit.add(new City("1","贵州贵州3"));
		// mapLsit.add(new City("1","贵州"));
		// mapLsit.add(new City("1","贵州"));
		// mapLsit.add(new City("1","贵州"));
		mGirdAdapter=new GirdAdapter();
//		gridView.setAdapter(mGirdAdapter);
//		gridView.setVisibility(View.GONE);
		// grid_tv1 = (TextView) findViewById(R.id.grid_tv1);
		// grid_tv2 = (TextView) findViewById(R.id.grid_tv2);
		// grid_tv3 = (TextView) findViewById(R.id.grid_tv3);
		// grid_tv4 = (TextView) findViewById(R.id.grid_tv4);
		// grid_tv5 = (TextView) findViewById(R.id.grid_tv5);
		// grid_tv6 = (TextView) findViewById(R.id.grid_tv6);
		// grid_tv7 = (TextView) findViewById(R.id.grid_tv7);
		// grid_tv8 = (TextView) findViewById(R.id.grid_tv8);
		// grid_tv9 = (TextView) findViewById(R.id.grid_tv9);
		// grid_tv10 = (TextView) findViewById(R.id.grid_tv10);
		// grid_tv1.setOnClickListener(this);
		// grid_tv2.setOnClickListener(this);
		// grid_tv3.setOnClickListener(this);
		// grid_tv4.setOnClickListener(this);
		// grid_tv5.setOnClickListener(this);
		// grid_tv6.setOnClickListener(this);
		// grid_tv7.setOnClickListener(this);
		// grid_tv8.setOnClickListener(this);
		// grid_tv9.setOnClickListener(this);
		// grid_tv10.setOnClickListener(this);
	}

	private void initTitle() {
		mBaseLibTopbarView = (BaseLibTopbarView) findViewById(R.id.topbar);
		mBaseLibTopbarView
				.setLeftImageButton(R.drawable.icon_back,
						UIHelper.finish(_activity)).setTitle("查询结果")
				.setLeftText("返回", null).setMode(BaseLibTopbarView.MODE_1)
				.getTitle_tv().setTextColor(Color.parseColor("#FFFFFF"));
		mBaseLibTopbarView.getRight_tv().setTextColor(
				Color.parseColor("#FFFFFF"));
		mBaseLibTopbarView.getLeft_tv().setTextColor(
				Color.parseColor("#FFFFFF"));
		if (TextUtils.isEmpty(str)) {
			mBaseLibTopbarView.setRightText("地图", new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Bundle bundle = new Bundle();
					bundle.putBoolean("isTest", true);
					UIHelper.jump(_activity,
							NearbyElevatorOrPlaygroundMap.class, bundle);
				}
			}).setRightImageButton(R.drawable.icon_map_1,
					new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							Bundle bundle = new Bundle();
							bundle.putBoolean("isTest", true);
							UIHelper.jump(_activity,
									NearbyElevatorOrPlaygroundMap.class, bundle);
						}
					});
		}

	}

	Map<String, String> idMap = new HashMap<String, String>();
	Map<String, String> hashMap = new HashMap<String, String>();

	@Override
	public void onApiSuccess(NetResult res, String tag) {
		if (res.isOK()) {
			if ("getCheckFields".equals(tag)) {
				CheckTypesBean typesBean = (CheckTypesBean) res;
				for (ContentCheckTypes item : typesBean.getContent()) {
					idMap.put(item.getCheck_field_name(),
							item.getCheck_field_id());
				}

				if (TextUtils.isEmpty(str)) {

				} else {
					search(str);
				}
			}

			if ("getCheckAreas".equals(tag)) {
				CheckAreasBean checkAreasBean = (CheckAreasBean) res;
				mapLsit.clear();
				mapLsit.add(new City("", "全省"));
				for (ContentCheckAreas areas : checkAreasBean.getContent()) {
					String city_id = areas.getCity_id();
					String city_name = areas.getCity_name();
					mapLsit.add(new City(city_id, city_name));
					hashMap.put(city_name, city_id);
				}
				// mapLsit.add(new City("1","贵州"));
				// mapLsit.add(new City("1","贵州"));
				// mapLsit.add(new City("1","贵州"));
				// mapLsit.add(new City("1","贵州"));
				// mapLsit.add(new City("1","贵州贵州3"));
				// mapLsit.add(new City("1","贵州"));
				// mapLsit.add(new City("1","贵州"));
				// mapLsit.add(new City("1","贵州"));
//				gridView.setAdapter(new GirdAdapter());
				notifaDatasetChange1();
//				setGridViewHeightBasedOnChildren(_activity,gridView);
//				gridView.setVisibility(View.VISIBLE);

			}
		} else {
			ac.handleErrorCode(_activity, res.error_code);
		}

	}
	
	public void notifaDatasetChange1(){
		mFlowLayout.removeAllViews();
		for(int i=0;i<mGirdAdapter.getCount();i++){
			View view=mGirdAdapter.getView(i, null, null);
			mFlowLayout.addView(view);
		}
	}

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.activity_query_test_result;
	}

	private void initView() {

		expandTabView = (ExpandTabView) findViewById(R.id.expandtab_view);
		viewMiddle = new ViewMiddle(this);
		viewRight = new ViewRight(this);

	}

	private void initVaule() {

		mViewArray.add(viewMiddle);
		mViewArray.add(viewRight);
		ArrayList<String> mTextArray = new ArrayList<String>();
		mTextArray.add("所属区域");
		mTextArray.add("资质条件");
		expandTabView.setValue(mTextArray, mViewArray);

	}

	private void initListener() {

		viewMiddle.setOnSelectListener(new ViewMiddle.OnSelectListener() {

			@Override
			public void getValue(String id, String showText, boolean isGroup) {
				area(id, isGroup ? "1" : "");
				onRefresh(viewMiddle, TextUtils.isEmpty(showText) ? "所属区域"
						: showText);

			}
		});
		viewRight.setOnSelectListener(new ViewRight.OnSelectListener() {

			@Override
			public void getValue(String distance, String showText) {
				quality(distance);
				onRefresh(viewRight, TextUtils.isEmpty(distance) ? "资质条件"
						: distance);
			}
		});

	}

	private void onRefresh(View view, String showText) {

		expandTabView.onPressBack();
		int position = getPositon(view);
		if (position >= 0 && !expandTabView.getTitle(position).equals(showText)) {
			expandTabView.setTitle(showText, position);
		}
	}

	private int getPositon(View tView) {
		for (int i = 0; i < mViewArray.size(); i++) {
			if (mViewArray.get(i) == tView) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public void onBackPressed() {

		if (!expandTabView.onPressBack()) {
			finish();
		}

	}

	@Override
	protected IDataSource<ArrayList<ContentCheckOrgs>> getDataSource() {
		// TODO Auto-generated method stub
		return organizationDatasource;
	}

	@Override
	protected Class getTemplateClass() {
		// TODO Auto-generated method stub
		return QueryTestOrganizationItemTpl.class;
	}

	private void area(String id, String type) {
		// organizationDatasource.setKeywords("");
		organizationDatasource.setArea_id(id);
		organizationDatasource.setType(type);
		// organizationDatasource.setQuality_id("");
		// listView.setSelection(0);
		listViewHelper.refresh();
	}

	private void quality(String id) {
		// organizationDatasource.setKeywords("");
		// organizationDatasource.setArea_id("");
		organizationDatasource.setQuality_id(id);
		listViewHelper.refresh();
	}

	@Override
	public void search(String key) {
		// TODO Auto-generated method stub
		if (idMap.containsKey(key)) {
			organizationDatasource.setSearchKey(idMap.get(key), "");
		} else {
			organizationDatasource.setSearchKey("", key);
		}

		listViewHelper.refresh();
	}

	public static void setGridViewHeightBasedOnChildren(Context mcontext,GridView gridView) {
		// 获取ListView对应的Adapter
		GirdAdapter listAdapter = (GirdAdapter)gridView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		
		int low=listAdapter.getCount()/4;
		low+=listAdapter.getCount()%4>0?1:0;
		for (int i = 0, len = listAdapter.getCount(); i < low; i++) { // listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, gridView);
			listItem.measure(0, 0); // 计算子项View 的宽高
			totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
		}

		ViewGroup.LayoutParams params = gridView.getLayoutParams();
	
		params.height = totalHeight
				+ ((int)ScreenUtils.dpToPx(mcontext, 15)* (low + 1));
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		gridView.setLayoutParams(params);
	}

	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// super.onClick(v);
	// boolean isGroup = true;
	// TextView tv = (TextView) v;
	// switch (v.getId()) {
	// case R.id.grid_tv1:
	// area(hashMap.get(tv.getText().toString()), isGroup ? "1" : "");
	// listViewHelper.refresh();
	// break;
	// case R.id.grid_tv2:
	//
	// break;
	// case R.id.grid_tv3:
	//
	// break;
	// case R.id.grid_tv4:
	//
	// break;
	// case R.id.grid_tv5:
	//
	// break;
	// case R.id.grid_tv6:
	//
	// break;
	// case R.id.grid_tv7:
	//
	// break;
	// case R.id.grid_tv8:
	//
	// break;
	// case R.id.grid_tv9:
	//
	// break;
	// case R.id.grid_tv10:
	//
	// break;
	// default:
	// break;
	// }
	// }

	@Override
	public void onEndLoadMore(
			IDataAdapter<ArrayList<ContentCheckOrgs>> adapter,
			ArrayList<ContentCheckOrgs> result) {
		// TODO Auto-generated method stub
		super.onEndLoadMore(adapter, result);
		listViewHelper.mLoadView.restore();
		// if(isInit){
		if (mapLsit.size() == 0 || mapLsit == null)
			AppUtil.getQualityCloudApiClient(ac).getCheckAreas(
					QueryTestOrganizationResultActivity1.this);
		// }
		// AppUtil.getQualityCloudApiClient(ac).getCheckFields(this);
	}

	boolean isInit;

	@Override
	public void onEndRefresh(IDataAdapter<ArrayList<ContentCheckOrgs>> adapter,
			ArrayList<ContentCheckOrgs> result) {
		// TODO Auto-generated method stub
		super.onEndRefresh(adapter, result);
		listViewHelper.mLoadView.restore();
		if (mapLsit.size() == 0 || mapLsit == null)
			AppUtil.getQualityCloudApiClient(ac).getCheckAreas(
					QueryTestOrganizationResultActivity1.this);
	}

	private ArrayList<String> girdList = new ArrayList<String>();

	class GirdAdapter extends BaseAdapter {
		int select = -1;

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if (mapLsit == null)
				return 0;
			return mapLsit.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mapLsit.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				LayoutInflater inflater = LayoutInflater.from(_activity);
				convertView = inflater.inflate(R.layout.item_grid_view, null);
				holder.tv = (TextView) convertView.findViewById(R.id.grid_tv);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			City item = mapLsit.get(position);
			// map.get(arg0)

			holder.tv.setSelected(select == position);

			String title = item.name;
			holder.tv.setText(title);
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if(select==position)
						return;
					if(select>-1){
					mFlowLayout.getChildAt(select).findViewById(R.id.grid_tv).setSelected(false);
					}
					City item = mapLsit.get(position);
					select = position;
					mFlowLayout.getChildAt(select).findViewById(R.id.grid_tv).setSelected(true);
//				notifyDataSetChanged1();
					area(item.id, "1");
				}
			});
			return convertView;
		}

		class ViewHolder {
			TextView tv;
		}
	}

	public static void setListViewHeightBasedOnChildren(ListView listView) {
		if (listView == null)
			return;

		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

}
