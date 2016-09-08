package com.midian.qualitycloud.ui.testorganization;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import midian.baselib.adapter.CPagerTabAdapter;
import midian.baselib.base.BaseActivity;
import midian.baselib.base.BaseListAdapter;
import midian.baselib.bean.NetResult;
import midian.baselib.shizhefei.view.listviewhelper.IDataSource;
import midian.baselib.shizhefei.view.listviewhelper.ListViewHelper;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;
import midian.baselib.widget.PagerSlidingTabStrip;
import midian.baselib.widget.pulltorefresh.PullToRefreshListView;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.midian.configlib.ServerConstant;
import com.midian.qualitycloud.R;
import com.midian.qualitycloud.bean.CheckOrgDetailBean;
import com.midian.qualitycloud.bean.CheckOrgDetailV2Bean;
import com.midian.qualitycloud.bean.CheckOrgListBean;
import com.midian.qualitycloud.bean.CheckOrgListBean.ContentCheckOrg;
import com.midian.qualitycloud.bean.Check_pros;
import com.midian.qualitycloud.datasource.TestOrganizationDatasource;
import com.midian.qualitycloud.itemview.InformationItem;
import com.midian.qualitycloud.itemview.TestOrganizationTpl;
import com.midian.qualitycloud.itemview.TestOrganizationTypeTpl;
import com.midian.qualitycloud.utils.AppUtil;
import com.midian.qualitycloud.widget.ShareDialog;

/**
 * 测试机构详情
 * 
 * @author MIDIAN
 * 
 */
public class TestOrganizationDetailActivity extends BaseActivity {
	private BaseLibTopbarView mBaseLibTopbarView;

	ListView listview_page_test;
	private TextView name_query_test, distance_query_test, state,
			address_query_test;

	String phone="";
//	CheckOrgDetailBean detailBean;
	PullToRefreshListView mPullToRefreshListView;
//	protected ListViewHelper<ArrayList<ContentCheckOrg>> listViewHelper;
	protected ListView listView;
//	protected TestOrganizationDatasource dataSource;
//	protected ArrayList<ContentCheckOrg> resultList;
	BaseListAdapter mBaseListAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_organization_detail);
		name = getIntent().getStringExtra("name");
		check_org_id = getIntent().getStringExtra("check_org_id");
		
		initTitle();
		initListView();

	}

	public void setState(String state) {
		if ("1".equals(state)) {
			this.state.setText("资质有效");
			this.state.setBackgroundResource(R.drawable.green_oval_bg);
		} else if ("0".equals(state)) {
			this.state.setText("资质超期");
			this.state.setBackgroundResource(R.drawable.red_oval_bg);
		}

	}

	private void initTitle() {
		mBaseLibTopbarView = (BaseLibTopbarView) findViewById(R.id.topbar);
		mBaseLibTopbarView
				.setLeftImageButton(R.drawable.icon_back,
						UIHelper.finish(_activity))
				.setMode(BaseLibTopbarView.MODE_1).setTitle("检验检测能力")
				.setLeftText("返回", null).getTitle_tv()
				.setTextColor(Color.parseColor("#FFFFFF"));
		mBaseLibTopbarView.getLeft_tv().setTextColor(
				Color.parseColor("#FFFFFF"));

		mBaseLibTopbarView.setRightImageButton(R.drawable.share_bg,
				new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						new ShareDialog(_activity).show(url, "检验检测能力",
								"检验检测机构分享", "关注检验检测机构，向亲友友们分享贵州检验检测机构。");
					}
				});

	}

	private void initListView() {

		View head = View.inflate(this, R.layout.page_test_info_head, null);

		mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.pullToRefreshListView);
		initHeadView(head);

		mPullToRefreshListView.getRefreshableView().addHeaderView(head);
		mPullToRefreshListView.setPullRefreshEnabled(false);
		mPullToRefreshListView.setPullLoadEnabled(false);
//		listViewHelper = new ListViewHelper<ArrayList<ContentCheckOrg>>(
//				mPullToRefreshListView);
		// 设置数据源
//		dataSource = new TestOrganizationDatasource(_activity, check_org_id, "");
//		listViewHelper.setDataSource(this.dataSource);

		listView = mPullToRefreshListView.getRefreshableView();
		listView.setDivider(getResources().getDrawable(R.drawable.divider_line));
		// listView.setOnItemClickListener(this);
//		resultList = dataSource.getResultList();
		// 设置适配器
//		checkProsBean.add(new CheckOrgDetailV2Bean.CheckProTypes(check_org_id,"1","机动尾气检测"));
//		checkProsBean.add(new CheckOrgDetailV2Bean.CheckProTypes(check_org_id,"2","机动车灯光检测"));
//		checkProsBean.add(new CheckOrgDetailV2Bean.CheckProTypes(check_org_id,"3","机动车检测"));
//		checkProsBean.add(new CheckOrgDetailV2Bean.CheckProTypes(check_org_id,"2","机动车灯光检测"));
//		checkProsBean.add(new CheckOrgDetailV2Bean.CheckProTypes(check_org_id,"3","机动车检测"));
//		checkProsBean.add(new CheckOrgDetailV2Bean.CheckProTypes(check_org_id,"2","机动车灯光检测"));
//		checkProsBean.add(new CheckOrgDetailV2Bean.CheckProTypes(check_org_id,"3","机动车检测"));
//		checkProsBean.add(new CheckOrgDetailV2Bean.CheckProTypes(check_org_id,"2","机动车灯光检测"));
//		checkProsBean.add(new CheckOrgDetailV2Bean.CheckProTypes(check_org_id,"3","机动车检测"));
//		checkProsBean.add(new CheckOrgDetailV2Bean.CheckProTypes(check_org_id,"2","机动车灯光检测"));
//		checkProsBean.add(new CheckOrgDetailV2Bean.CheckProTypes(check_org_id,"3","机动车检测"));
//		checkProsBean.add(new CheckOrgDetailV2Bean.CheckProTypes(check_org_id,"3","机动车检测"));
//		checkProsBean.add(new CheckOrgDetailV2Bean.CheckProTypes(check_org_id,"2","机动车灯光检测"));
//		checkProsBean.add(new CheckOrgDetailV2Bean.CheckProTypes(check_org_id,"3","机动车检测"));
//		checkProsBean.add(new CheckOrgDetailV2Bean.CheckProTypes(check_org_id,"2","机动车灯光检测"));
//		checkProsBean.add(new CheckOrgDetailV2Bean.CheckProTypes(check_org_id,"3","机动车检测"));
		mBaseListAdapter=new BaseListAdapter(listView, this,
				checkProsBean, TestOrganizationTypeTpl.class, null);
		listView.setAdapter(mBaseListAdapter);
		
//		
//		listViewHelper.setAdapter(new BaseListAdapter(listView, this,
//				resultList, TestOrganizationTypeTpl.class, listViewHelper));
		// adapter.notifyDataSetChanged();
//		listViewHelper.refresh();

		// mPullToRefreshListView.setPullRefreshEnabled(false);
		// mPullToRefreshListView.setPullLoadEnabled(true);
		// name_page_test_org = (TextView) views
		// .findViewById(R.id.name_page_test_org);
		// webView = (WebView) views.findViewById(R.id.content_page_test);
		// webView.setHorizontalScrollBarEnabled(false);
		// webView.setVerticalScrollBarEnabled(false);

		try {
			AppUtil.getQualityCloudApiClient(ac).getCheckOrgDetail(
					check_org_id, this);
			AppUtil.getQualityCloudApiClient(ac).getCheckOrgDetail_V2(
					check_org_id,ac.lon,ac.lat, this);
//			showLoadingDlg();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initHeadView(View footer) {
		footer.findViewById(R.id.phone_query_test_rl).setOnClickListener(this);
		footer.findViewById(R.id.navigation_query_test_rl).setOnClickListener(
				this);
		footer.findViewById(R.id.like_test_img).setOnClickListener(this);
		like_test_img = (ImageView) footer.findViewById(R.id.like_test_img);
		state = (TextView) footer.findViewById(R.id.state);
		name_query_test = (TextView) footer.findViewById(R.id.name_query_test);
		distance_query_test = (TextView) footer
				.findViewById(R.id.distance_query_test);
		address_query_test = (TextView) footer
				.findViewById(R.id.address_query_test);
		setState("2");
	}

	private String getName(String str) {
		String name = "";
		if (str.length() > 16) {
			name = str.substring(0, 16) + "\n"
					+ str.substring(16, str.length());
		} else {
			name = str;
		}
		return name;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		super.onClick(arg0);
		switch (arg0.getId()) {
		case R.id.phone_query_test_rl:
			Intent intent = new Intent(Intent.ACTION_DIAL);
			Uri data = Uri.parse("tel:" + phone);
			intent.setData(data);
			_activity.startActivity(intent);
			break;
		case R.id.navigation_query_test_rl:
			gotoBaiduMap();
			break;
		case R.id.like_test_img:
			String type = "5";
			if (ac.isRequireLogin(_activity)) {

				if ("1".equals(is_collected)) {
					AppUtil.getQualityCloudApiClient(ac).postCancelCollect(id,
							"5", this);
				}
				if ("0".equals(is_collected)) {
					AppUtil.getQualityCloudApiClient(ac).postCollect(id, type,
							this);

				}
				showLoadingDlg();

			}
			break;
		default:
			break;
		}
	}

	private void gotoBaiduMap() {
		try {

			Intent intent = Intent
					.getIntent("intent://map/direction?origin=latlng:"
							+ ac.lat
							+ ","
							+ ac.lon
							+ "|name:我的位置&destination="
							+ lat
							+ ","
							+ lon
							+ "&mode=driving#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
			if (isInstallByread("com.baidu.BaiduMap")) {
				_activity.startActivity(intent); // 启动调用

			} else {
				UIHelper.t(_activity, "请安装百度地图客户端");
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 判断是否安装目标应用
	 * 
	 * @param packageName
	 *            目标应用安装后的包名
	 * @return 是否已安装目标应用
	 */
	private boolean isInstallByread(String packageName) {
		return new File("/data/data/" + packageName).exists();
	}

	private ArrayList checkProsBean = new ArrayList<CheckOrgDetailV2Bean.CheckProTypes>();
	private String name;
	private String check_org_id;
	private String is_collected, lat = "", lon = "";
	private String id;
	private ImageView like_test_img;
	private String url;

	@Override
	public void onApiSuccess(NetResult res, String tag) {
		super.onApiSuccess(res, tag);
		hideLoadingDlg();
		if (res.isOK()) {
			if("getCheckOrgDetail".equals(tag)){
				CheckOrgDetailBean bean=(CheckOrgDetailBean) res;
				is_collected = bean.getContent().getIs_collected();
				if (is_collected.equals("1")) {
					like_test_img
							.setBackgroundResource(R.drawable.icon_brand_like_p);
				} else {
					like_test_img
							.setBackgroundResource(R.drawable.icon_brand_like_n);
				}
			}
			if ("getCheckOrgDetail_V2".equals(tag)) {
				CheckOrgDetailV2Bean detailBean = (CheckOrgDetailV2Bean) res;
				if (detailBean == null || detailBean.getContent() == null)
					return;
				try{
				phone = detailBean.getContent().getTel();
				url = detailBean.getContent().getShare_url();
				checkProsBean.clear();
				for(CheckOrgDetailV2Bean.CheckProTypes item:detailBean.getContent().getCheck_pro_types()){
					item.setCheck_org_id(check_org_id);
				}
				checkProsBean.addAll(detailBean.getContent().getCheck_pro_types());
//				is_collected = detailBean.getContent().getIs_collected();
				id = detailBean.getContent().getCheck_org_id();
				lat = detailBean.getContent().getLat();
				lon = detailBean.getContent().getLon();

				address_query_test
						.setText(detailBean.getContent().getAddress());
				distance_query_test.setText(detailBean.getContent()
						.getDistance());
				name_query_test.setText(getName(detailBean.getContent()
						.getCheck_org_name()));
				setState(detailBean.getContent().getEffective());
			
//				mBaseListAdapter=new BaseListAdapter(listView, this,
//						checkProsBean, TestOrganizationTypeTpl.class, null);
//				
//				mBaseListAdapter.notifyDataSetChanged();
//				if (is_collected.equals("1")) {
//					like_test_img
//							.setBackgroundResource(R.drawable.icon_brand_like_p);
//				} else {
//					like_test_img
//							.setBackgroundResource(R.drawable.icon_brand_like_n);
//				}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			if ("postCollect".equals(tag)) {
				like_test_img
						.setBackgroundResource(R.drawable.icon_brand_like_p);
				is_collected = "1";
				System.out.println("成功收藏后is_collect:::::" + is_collected);
				UIHelper.t(_activity, "已收藏");
			}

			if ("postCancelCollect".equals(tag)) {
				like_test_img
						.setBackgroundResource(R.drawable.icon_brand_like_n);
				is_collected = "0";
				System.out.println("删除收藏后is_collect:::::" + is_collected);
				UIHelper.t(_activity, "已取消收藏");
			} else {
				ac.handleErrorCode(_activity, res.error_code);
			}
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
//		listViewHelper.destory();
	}

}
