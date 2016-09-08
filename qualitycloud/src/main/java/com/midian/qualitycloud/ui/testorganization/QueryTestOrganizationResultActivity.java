package com.midian.qualitycloud.ui.testorganization;

import java.util.ArrayList;
//import java.util.List;

import midian.baselib.base.BaseListActivity;
import midian.baselib.base.BaseListAdapter;
import midian.baselib.bean.NetResult;
import midian.baselib.shizhefei.view.listviewhelper.IDataSource;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;
import midian.baselib.widget.SearchEditText;
import midian.baselib.widget.SelectFilterView;
import midian.baselib.widget.SelectFilterView.onTabChangeListener;
import android.graphics.Color;
//import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
//import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
//import android.widget.PopupWindow;
import com.midian.qualitycloud.R;
import com.midian.qualitycloud.bean.CheckAreasBean;
import com.midian.qualitycloud.bean.CheckAreasBean.Areas;
import com.midian.qualitycloud.bean.CheckAreasBean.ContentCheckAreas;
import com.midian.qualitycloud.bean.CheckOrgsBean.ContentCheckOrgs;
import com.midian.qualitycloud.bean.CheckQualitiesBean;
import com.midian.qualitycloud.bean.CheckQualitiesBean.ContentCheckQualities;
import com.midian.qualitycloud.datasource.QueryTestOrganizationDatasource;
import com.midian.qualitycloud.itemview.ItemNearSelectViewTpl;
import com.midian.qualitycloud.itemview.QueryTestOrganizationItemTpl;
//import com.midian.qualitycloud.ui.adapter.ClasPopLeftAdapter;
//import com.midian.qualitycloud.ui.adapter.ClasPopRightAdapter;
import com.midian.qualitycloud.ui.customview.DimedView;
import com.midian.qualitycloud.ui.customview.DimedView.OpenCloseListener;
import com.midian.qualitycloud.ui.customview.TwoDimedView;
import com.midian.qualitycloud.ui.main.NearbyElevatorOrPlaygroundMap;
import com.midian.qualitycloud.ui.view.ExpandTabView;
import com.midian.qualitycloud.ui.view.ViewMiddle;
import com.midian.qualitycloud.utils.AppUtil;

/**
 * 查询结果
 * 
 * @author MIDIAN
 * 
 */
public class QueryTestOrganizationResultActivity extends
		BaseListActivity<ContentCheckOrgs> implements onTabChangeListener,
		OpenCloseListener {
	BaseLibTopbarView mBaseLibTopbarView;
	private SelectFilterView mSelectFilterView;
	private DimedView dimeView;
	private ViewMiddle viewMiddle;
	private ExpandTabView expandTabView;
	private ArrayList<View> mViewArray = new ArrayList<View>();
	private TwoDimedView twoDimeView;
	String text = "";
	String field_id;
	SearchEditText mSearchEditText;
	QueryTestOrganizationDatasource organizationDatasource;
	ListView father_lv, son_lv;

	// private List<List<String>> sublist;
	// private PopupWindow ipopupwindow;
	// private ClasPopRightAdapter iclasPopRightAdapter;
	// private ClasPopLeftAdapter iclasPopLeftAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		text = getIntent().getStringExtra("text");
		field_id = getIntent().getStringExtra("field_id");
		organizationDatasource = new QueryTestOrganizationDatasource(
				QueryTestOrganizationResultActivity.this, field_id, "");
		super.onCreate(savedInstanceState);
		initTitle();
		mSearchEditText = (SearchEditText) findViewById(R.id.search);
		mSearchEditText.setText(text);
		// initView();
		initVaule();
		initListener();
	}

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.activity_query_test_organization_result;
	}

	public void initTitle() {
		mBaseLibTopbarView = (BaseLibTopbarView) findViewById(R.id.topbar);
		mBaseLibTopbarView
				.setLeftImageButton(R.drawable.icon_back,
						UIHelper.finish(_activity)).setTitle("查询结果")
				.setMode(BaseLibTopbarView.MODE_1)
				.setRightText("地图模式", new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Bundle bundle = new Bundle();
						bundle.putBoolean("isTest", true);
						UIHelper.jump(_activity,
								NearbyElevatorOrPlaygroundMap.class, bundle);
					}
				}).getTitle_tv().setTextColor(Color.parseColor("#FFFFFF"));
		mBaseLibTopbarView.getRight_tv().setTextColor(
				Color.parseColor("#FFFFFF"));
		mSelectFilterView = (SelectFilterView) findViewById(R.id.filter);
		mSelectFilterView.setOnTabChangeListener(this);
		dimeView = findView(R.id.dimeView);
		dimeView.setOpenCloseListener(this);
	}

	public int getFragmentContentId() {
		return R.id.fl_content;
	}

	int index;

	// dimeView的开关监听
	@Override
	public void open() {
		// TODO Auto-generated method stub
	}

	@Override
	public void close() {
		mSelectFilterView.setchangeState(index, false);
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

	public void refresh(String id) {
		organizationDatasource.setId(id);
		listViewHelper.refresh();
	}

	@Override
	public void onTabChange(int index, boolean isSelect) {
		this.index = index;
		System.out.println("index:::::::::::" + index);
		switch (index) {
		case 0:// 所属区域
			if (isSelect) {
				dimeView.show();
				// dimeView.getListView().setVisibility(View.GONE);
				try {
					AppUtil.getQualityCloudApiClient(ac).getCheckAreas(this);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				dimeView.close();
			}
			break;

		case 1:// 资质条件
			if (isSelect) {
				dimeView.show();
				dimeView.getListView().setVisibility(View.GONE);
				try {
					AppUtil.getQualityCloudApiClient(ac)
							.getCheckQualities(this);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				dimeView.close();
			}
			break;
		}
	}

	@Override
	public void onApiSuccess(NetResult res, String tag) {
		mBaseLibTopbarView.hideProgressBar();
		hideLoadingDlg();
		super.onApiSuccess(res, tag);
		if (res.isOK()) {
			if ("getCheckAreas".equals(tag)) {
				CheckAreasBean areasBean = (CheckAreasBean) res;
				initArea(areasBean.getContent());
			}
			if ("getCheckQualities".equals(tag)) {
				CheckQualitiesBean qualitiesBean = (CheckQualitiesBean) res;
				initAptitudes(qualitiesBean.getContent());
			}
			// if ("getCheckAreas".equals(tag)) {
			// ContentCheckAreas checkAreas = (ContentCheckAreas) res;
			// initAreaTwo(checkAreas.getAreas());
			// }
		}
	}

	@Override
	public void onApiFailure(Throwable t, int errorNo, String strMsg, String tag) {
		super.onApiFailure(t, errorNo, strMsg, tag);
		mBaseLibTopbarView.hideProgressBar();
		hideLoadingDlg();
	}

	public void initArea(ArrayList<ContentCheckAreas> list) {

		ArrayList<Item> morelist = new ArrayList<Item>();

		morelist.add(new Item(null, "全部", 0));

		for (ContentCheckAreas item : list) {
			morelist.add(new Item(item.getCity_id(), item.getCity_name(), 0));
		}
		initItemShow(morelist);
	}

	public void initAreaTwo(ArrayList<Areas> list) {

		ArrayList<Item> morelist = new ArrayList<Item>();

		morelist.add(new Item(null, "全部", 2));

		for (Areas item : list) {
			morelist.add(new Item(item.getArea_id(), item.getArea_name(), 2));
		}
		initItemShowTwo(morelist);
	}

	private void initAptitudes(ArrayList<ContentCheckQualities> contentList) {
		ArrayList<Item> morelist = new ArrayList<Item>();
		morelist.add(new Item(null, "全部", 1));
		for (ContentCheckQualities item : contentList) {
			morelist.add(new Item(item.getCheck_quality_id(), item
					.getCheck_quality_name(), 1));

		}
		initItemShow(morelist);
	}

	private void initItemShowTwo(ArrayList<Item> contentList) {
		final BaseListAdapter<Item> adapter = new BaseListAdapter<Item>(
				twoDimeView.getListView(), _activity, contentList,
				ItemNearSelectViewTpl.class, null);
		twoDimeView.getListView().setVisibility(View.VISIBLE);
		twoDimeView.getListView().setAdapter(adapter);
		twoDimeView.getListView().setOnItemClickListener(
				new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						Item item = adapter.getData().get(arg2);
						String ids = item.getId();
						System.out.println("initItemShow:::---" + ids);
						// if (ids == null) {
						// topbar.showProgressBar();
						// }
						if (item.type == 1) {
							organizationDatasource.setArea_id(ids);
							twoDimeView.close();
						}

						listViewHelper.refresh();
					}
				});
	}

	private void initItemShow(ArrayList<Item> contentList) {
		final BaseListAdapter<Item> adapter = new BaseListAdapter<Item>(
				dimeView.getListView(), _activity, contentList,
				ItemNearSelectViewTpl.class, null);
		dimeView.getListView().setVisibility(View.VISIBLE);
		dimeView.getListView().setAdapter(adapter);
		dimeView.getListView().setOnItemClickListener(
				new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						Item item = adapter.getData().get(arg2);
						String ids = item.getId();
						System.out.println("initItemShow:::---" + ids);
						// if (ids == null) {
						// topbar.showProgressBar();
						// }
						if (item.type == 1) {
							organizationDatasource.setArea_id(ids);
						} else if (item.type == 2) {
							organizationDatasource.setQuality_id(ids);
						}

						listViewHelper.refresh();
						dimeView.close();
					}
				});
	}

	// private void initView() {
	//
	// expandTabView = (ExpandTabView) findViewById(R.id.expandtab_view);
	//
	// }

	private void initVaule() {

		// mViewArray.add(viewLeft);
		mViewArray.add(viewMiddle);
		// mViewArray.add(viewRight);
		ArrayList<String> mTextArray = new ArrayList<String>();
		// mTextArray.add("距离");
		mTextArray.add("区域");
		// mTextArray.add("距离");
		expandTabView.setValue(mTextArray, mViewArray);
		// expandTabView.setTitle(viewLeft.getShowText(), 0);
		// expandTabView.setTitle(viewMiddle.getShowText(), 1);
		// expandTabView.setTitle(viewRight.getShowText(), 2);

	}

	private void initListener() {

		// viewLeft.setOnSelectListener(new ViewLeft.OnSelectListener() {
		//
		// @Override
		// public void getValue(String distance, String showText) {
		// onRefresh(viewLeft, showText);
		// }
		// });

		viewMiddle.setOnSelectListener(new ViewMiddle.OnSelectListener() {

			@Override
			public void getValue(String id,String showText,boolean isGroup) {

				onRefresh(viewMiddle, showText);

			}
		});

		// viewRight.setOnSelectListener(new ViewRight.OnSelectListener() {
		//
		// @Override
		// public void getValue(String distance, String showText) {
		// onRefresh(viewRight, showText);
		// }
		// });

	}

	private void onRefresh(View view, String showText) {

		expandTabView.onPressBack();
		int position = getPositon(view);
		if (position >= 0 && !expandTabView.getTitle(position).equals(showText)) {
			expandTabView.setTitle(showText, position);
		}
		Toast.makeText(this, showText, Toast.LENGTH_SHORT).show();

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

	public class Item extends NetResult {

		private String id;
		private String name;
		int type;

		public Item(String id, String name, int type) {
			super();
			this.id = id;
			this.setName(name);
			this.type = type;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}
}
