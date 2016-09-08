package com.midian.qualitycloud.ui.main;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import midian.baselib.app.AppManager;
import midian.baselib.base.BaseFragmentActivity;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.ScreenUtils;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.dialog.CommonDialog;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapLoadedCallback;
import com.baidu.mapapi.map.BaiduMap.OnMapStatusChangeListener;
import com.baidu.mapapi.map.BaiduMap.OnMapTouchListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Projection;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.midian.fastdevelop.afinal.core.AsyncTask;
import com.midian.fastdevelop.afinal.http.HttpHandler;
import com.midian.qualitycloud.R;
import com.midian.qualitycloud.bean.FacilityDetailBean;
import com.midian.qualitycloud.bean.MapCheckOrgsBean;
import com.midian.qualitycloud.bean.MapFacilitiesBean;
import com.midian.qualitycloud.map.Cluster;
import com.midian.qualitycloud.map.ItemBean;
import com.midian.qualitycloud.map.MapViewFactory;
import com.midian.qualitycloud.map.Marks;
import com.midian.qualitycloud.ui.common.ScanResultActivity;
import com.midian.qualitycloud.ui.testorganization.QueryTestOrganizationResultActivity1;
import com.midian.qualitycloud.ui.testorganization.TestOrganizationDetailActivity;
import com.midian.qualitycloud.ui.testorganization.TestOrganizationListActivity;
import com.midian.qualitycloud.utils.AppUtil;
import com.midian.qualitycloud.utils.LocationUtil;
import com.midian.qualitycloud.utils.LocationUtil.OneLocationListener;

/**
 * 附近电梯游乐场地图（新）或者是测试机构地图
 * 
 * @author MIDIAN
 * 
 */
public class NearbyElevatorOrPlaygroundMap extends BaseFragmentActivity
		implements OnClickListener, OnGetPoiSearchResultListener {
	private BaiduMap baiduMap;

	EditText input;
	int type = 1;

	boolean isTest;
	boolean isSwitch;

	private MapView mMapView;
	View locationIv;

	private Boolean isAverageCenter = false;
	boolean isFirst = true;
	private List<Marks> mMarkersForScenic = new ArrayList<Marks>();
	// 当前的聚合集合
	private List<Marks> currentClustersForScenic = new ArrayList<Marks>();
	private Cluster mCluster;
	private Cluster mECluster;
	private double mDistance = 6000000;
	MapViewFactory mapView;
	LatLng latlng;

	// 左上角的经纬度，用于判断地图改变范围
	private LatLng leftTopLatlng;
	// 当前数据的缩放等级
	private float cuttentZoom = 0;
	// 移动一定距离后，才开始load
	private int changeRange;

	private List<Marker> markers = new ArrayList<Marker>();

	List<ItemBean> itembeanList = new ArrayList<ItemBean>();
//	MapFacilitiesBean mMapFacilitiesBean;

	PoiSearch mPoiSearch;
	List<PoiInfo> list;
	String lon, lat, curlat, curlon, address;

	View navigation;
	CommonDialog mCommonDialog;// 导航弹框
	private HttpHandler<Object> currentHttp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_map);
		isSwitch = getIntent().getBooleanExtra("isSwitch", false);
		isTest = getIntent().getBooleanExtra("isTest", false);
		address = "";
		type = getIntent().getIntExtra("type", 1);
		changeRange = ScreenUtils.GetScreenWidthPx(_activity) / 4;
		initMap();
		init();
	}

	private void init() {
		input = (EditText) findViewById(R.id.input);
		input.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView arg0, int actionId,
					KeyEvent arg2) {
				// TODO Auto-generated method stub
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					if (!TextUtils.isEmpty(arg0.getText().toString()))
						search(arg0.getText().toString());
				}
				return false;
			}
		});

		locationIv = findViewById(R.id.location);
		findViewById(R.id.back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (isTest) {
				} else {
					if (isSwitch) {// 电梯
						AppManager.getAppManager().finishActivity(
								NearbyElevatorPlaygroundListActivity.class);
					}
				}
				AppManager.getAppManager().finishActivity(
						NearbyElevatorOrPlaygroundMap.class);
			}
		});
		findViewById(R.id.location).setOnClickListener(this);
		findViewById(R.id.refresh).setOnClickListener(this);
		findViewById(R.id.list).setVisibility(View.GONE);
		findViewById(R.id.navigation).setOnClickListener(this);
		navigation = findViewById(R.id.navigation);
		navigation.setVisibility(View.GONE);
	}

	private void initMap() {
		mMapView = (MapView) findViewById(R.id.map);
		MapStatus ms = new MapStatus.Builder().overlook(0).zoom(15).build();
		MapStatusUpdate u1 = MapStatusUpdateFactory.newMapStatus(ms);
		baiduMap = mMapView.getMap();
		mMapView.showScaleControl(false);
		mMapView.showZoomControls(false);
		UiSettings mapui = baiduMap.getUiSettings();

		baiduMap.setMapStatus(u1);
		mapui.setCompassEnabled(false);
		mapui.setOverlookingGesturesEnabled(false);
		mapui.setZoomGesturesEnabled(true);
		LocationUtil.getInstance(_activity).startOneLocation(
				OneLocationListener);
		baiduMap.setMyLocationEnabled(true);
		baiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
				LocationMode.FOLLOWING, true, null));
		mapView = MapViewFactory.getInstance(_activity);
		mCluster = new Cluster(baiduMap, isAverageCenter, ScreenUtils.dpToPxInt(_activity, 60), mDistance);
		baiduMap.setOnMapStatusChangeListener(changeListener);
		baiduMap.setOnMapLoadedCallback(callback);
		baiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker arg0) {
				// TODO Auto-generated method stub
				Bundle b = arg0.getExtraInfo();
				int i = b.getInt("id");
				int count = b.getInt("count");
				Marks m = currentClustersForScenic.get(i);

				if (count > 1) {
					if (isTest) {
						String ids = "";
						int j = 0;
						for (Marks item : m.getmMarkers()) {
							if (j == 0) {
								ids = item.getItemBean().getId();
							} else {
								ids = ids + "," + item.getItemBean().getId();
							}
//							if (j > 50)
//								break;
							j++;
						}
						Bundle p = new Bundle();
						p.putString("ids", ids);
						UIHelper.jump(_activity,
								TestOrganizationListActivity.class, p);

					} else {
						String ids = "";
						int j = 0;
						for (Marks item : m.getmMarkers()) {
							if (j == 0) {
								ids = item.getItemBean().getId();
							} else {
								ids = ids + "," + item.getItemBean().getId();
							}
//							if (j > 50)
//								break;
							j++;
						}
						Bundle p = new Bundle();
						p.putInt("type", type);
						p.putString("ids", ids);
						UIHelper.jump(_activity,
								ElevatorPlaygroundListForMapActivity.class, p);
					}

				} else {
					if (isTest) {
						Bundle bundle = new Bundle();
						bundle.putString("name", m.getItemBean().getName());
						bundle.putString("check_org_id", m.getItemBean()
								.getId());
						UIHelper.jump(_activity,
								TestOrganizationDetailActivity.class, bundle);
					} else {
						lon = m.getItemBean().getLon();
						lat = m.getItemBean().getLat();
						address = m.getItemBean().getName();
						// navigation.setVisibility(View.VISIBLE);
						AppUtil.getQualityCloudApiClient(ac).getFacilityDetail(
								m.getItemBean().getId(),
								NearbyElevatorOrPlaygroundMap.this);
					}
					// changeState(i);
				}
				return false;
			}
		});
		baiduMap.setOnMapTouchListener(new OnMapTouchListener() {

			@Override
			public void onTouch(MotionEvent arg0) {
				// TODO Auto-generated method stub
				navigation.setVisibility(View.GONE);
			}
		});
		initPoiSearch();
	}

	public void initPoiSearch() {
		mPoiSearch = PoiSearch.newInstance();
		mPoiSearch.setOnGetPoiSearchResultListener(this);
		// mSearch = GeoCoder.newInstance();
		// mSearch.setOnGetGeoCodeResultListener(this);
	}

	OnMapLoadedCallback callback = new OnMapLoadedCallback() {
		/**
		 * 地图加载完成回调函数
		 */
		public void onMapLoaded() {
			changeListener.onMapStatusChangeFinish(baiduMap.getMapStatus());

			// mSearch.reverseGeoCode(new
			// ReverseGeoCodeOption().location(baiduMap
			// .getMapStatus().target));
		}
	};

	/**
	 * 地图状态改变监听器
	 */
	private OnMapStatusChangeListener changeListener = new OnMapStatusChangeListener() {

		@Override
		public void onMapStatusChange(MapStatus arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onMapStatusChangeFinish(MapStatus status) {
			// TODO Auto-generated method stub

			Projection mProjection = baiduMap.getProjection();
			Point pointB = new Point(0, 0);
			LatLng tempLatlbg = mProjection.fromScreenLocation(pointB);

			// 判断
			if (leftTopLatlng != null && cuttentZoom == status.zoom) {
				pointB = mProjection.toScreenLocation(leftTopLatlng);
				if (Math.abs(pointB.x) < changeRange
						&& Math.abs(pointB.y) < changeRange) {
					// 不够范围
					return;
				}
			}
			// System.out.println("结束变化时间2：：" + new Date());
			cuttentZoom = status.zoom;
			System.out.println("cuttentZoom2：：" + cuttentZoom);
			leftTopLatlng = tempLatlbg;
			latlng = status.target;
			mCluster.setmDistance(getDistance(status.zoom));
			try{
			if (isTest) {
				getTestNetData(status.bound.southwest.longitude + "",
						status.bound.northeast.latitude + "",
						status.bound.northeast.longitude + "",
						status.bound.southwest.latitude + "");
			} else {
				getNetData(status.bound.southwest.longitude + "",
						status.bound.northeast.latitude + "",
						status.bound.northeast.longitude + "",
						status.bound.southwest.latitude + "");
			}
			}catch(Exception e){
				e.printStackTrace();
			}

		}

		@Override
		public void onMapStatusChangeStart(MapStatus arg0) {
			// TODO Auto-generated method stub

		}
	};

	private void getTestNetData(String left_top_lon, String left_top_lat,
			String right_bottom_lon, String right_bottom_lat) {
		if (currentHttp != null) {
			currentHttp.close();
			currentHttp = null;
		}
		currentHttp = AppUtil.getQualityCloudApiClient(ac).getmAPCheckOrgs(
				left_top_lon, left_top_lat, right_bottom_lon, right_bottom_lat,
				this);
	}

	private void getNetData(String left_top_lon, String left_top_lat,
			String right_bottom_lon, String right_bottom_lat) {
		if (currentHttp != null) {
			currentHttp.close();
			currentHttp = null;
		}
		currentHttp = AppUtil.getQualityCloudApiClient(ac).getMapFacilities(
				type + "", left_top_lon, left_top_lat, right_bottom_lon,
				right_bottom_lat, this);
	}

	// private void getData() {
	// itembeanList.clear();
	// mMarkersForScenic.clear();
	// currentClustersForScenic.clear();
	// baiduMap.clear();
	// markers.clear();
	// // itembeanList.add(new ItemBean("1", "26.569269", "106.692965"));
	// // itembeanList.add(new ItemBean("1", "24.569269", "105.692965"));
	// // itembeanList.add(new ItemBean("1", "26.519269", "102.692965"));
	// // itembeanList.add(new ItemBean("1", "26.579269", "100.692965"));
	// initMark();
	// }

	@Override
	public void onApiSuccess(NetResult res, String tag) {
		// TODO Auto-generated method stub
		super.onApiSuccess(res, tag);

		if (res.isOK()) {
			if ("getFacilityDetail".equals(tag)) {
				FacilityDetailBean mFacilityDetailBean = (FacilityDetailBean) res;
				Bundle b = new Bundle();
				b.putSerializable("detail", mFacilityDetailBean);
//				int type;
//				if ("1".equals(mFacilityDetailBean.getContent().getStatus())) {
//					type = 1;
//				} else {
//					type = 2;
//				}
//				b.putInt("type", type);
//				b.putString("types", mFacilityDetailBean.getContent().getType());
//				b.putString("facility_id", mFacilityDetailBean.getContent().getFacility_id());
				
				b.putBoolean("isJump", true);
				UIHelper.jump(_activity, ScanResultActivity.class, b);
//				UIHelper.jump(_activity, ScanResultActivity.class, b);
			} else {
				if (currentHttp == null)
					return;
				if(isDealing){
					if(mdealTask!=null){
						mdealTask.cancel(true);
					}
					isDealing=false;
				}
				itembeanList.clear();
				mMarkersForScenic.clear();
				currentClustersForScenic.clear();
				baiduMap.clear();
				markers.clear();
				
				if ("getMapFacilities".equals(tag)) {
					mMapFacilitiesBean = (MapFacilitiesBean) res;
				} else if ("getmAPCheckOrgs".equals(tag)) {
					mMapCheckOrgsBean = (MapCheckOrgsBean) res;
				}
				
//				if ("getMapFacilities".equals(tag)) {
//					MapFacilitiesBean mMapFacilitiesBean = (MapFacilitiesBean) res;
//					for (MapFacilitiesBean.Content item : mMapFacilitiesBean
//							.getContent()) {
//
//						itembeanList.add(new ItemBean(item.getFacility_id(),
//								"", item.getLat(), item.getLon(), item
//										.getUse_company()));
//
//					}
//
//					initMark();
//				} else if ("getmAPCheckOrgs".equals(tag)) {
//					MapCheckOrgsBean mMapCheckOrgsBean = (MapCheckOrgsBean) res;
//
//					for (MapCheckOrgsBean.Content item : mMapCheckOrgsBean
//							.getContent()) {
//
//						itembeanList.add(new ItemBean(item.getCheck_org_id(),
//								"", item.getLat(), item.getLon(), item
//										.getCheck_org_name()));
//
//					}
					initMark();
//				}
			}
		} else {
			ac.handleErrorCode(_activity, res.error_code);
		}

	}
	
	MapFacilitiesBean mMapFacilitiesBean ;
	MapCheckOrgsBean mMapCheckOrgsBean;

	/**
	 * 地图缩放等级
	 * 
	 * @param room
	 * @return
	 */
	private double getDistance(float room) {
		double d = mDistance;

		if (room > 6) {
			d = mDistance;
		} else if (room > 5) {
			d = mDistance * 2;
		} else if (room > 4) {
			d = mDistance * 3;
		} else if (room > 3) {
			d = mDistance * 4;
		} else {
			d = mDistance * 5;
		}

		return d;
	}

	DealTask mdealTask;
	boolean isDealing=false;
	class DealTask extends AsyncTask<Void,Void,Boolean>{
@Override
protected void onPreExecute() {
	// TODO Auto-generated method stub
	super.onPreExecute();
	isDealing=true;
}
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			if(mMapFacilitiesBean!=null){
				for (MapFacilitiesBean.Content item : mMapFacilitiesBean
						.getContent()) {

					itembeanList.add(new ItemBean(item.getFacility_id(),
							"", item.getLat(), item.getLon(), item
									.getUse_company()));

				}
			}else if(mMapCheckOrgsBean!=null){
				for (MapCheckOrgsBean.Content item : mMapCheckOrgsBean
						.getContent()) {

					itembeanList.add(new ItemBean(item.getCheck_org_id(),
							"", item.getLat(), item.getLon(), item
									.getCheck_org_name()));

				}
			}
			
			for (int i = 0; i < itembeanList.size(); i++) {
				ItemBean itembean = itembeanList.get(i);
				LatLng latLng = new LatLng(Double.parseDouble(itembean.getLat()),
						Double.parseDouble(itembean.getLon()));

				Marks marks = new Marks(latLng);
				marks.setName(itembean.getName());
				if (TextUtils.isEmpty(itembean.getPic())) {
					marks.setPic("");
				} else {
					marks.setPic(itembean.getPic());
				}
				marks.setItemBean(itembean);
				mMarkersForScenic.add(marks);
			}

			currentClustersForScenic = mCluster.createCluster(mMarkersForScenic);
			return true;
		}
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			isDealing=false;
			if(result){
			for (int i = 0; i < currentClustersForScenic.size(); i++) {
				Marks m = currentClustersForScenic.get(i);

				View mView = mapView.createDefaultView(i, m.getCount());
				TextView title = (TextView) mView.findViewById(R.id.title);
				ImageView img = (ImageView) mView.findViewById(R.id.ioc_img);
				TextView number_tx = (TextView) mView.findViewById(R.id.number_tx);
				title.setVisibility(View.GONE);
				if (currentClustersForScenic.get(i).getCount() > 1) {
					int  count=currentClustersForScenic.get(i).getCount();
					String str="";
					if(count>99){
						str=99+"+";
					}else{
						str=count+"";
					}
					
					number_tx.setText(str);
//					number_tx.setText(currentClustersForScenic.get(i).getCount()
//							+ "");
					// System.out.println("::count12::"+currentClustersForScenic.get(i).getCount());
					number_tx.setVisibility(View.VISIBLE);
				} else {
					number_tx.setVisibility(View.GONE);
					
					if (isTest) {
						title.setText(currentClustersForScenic.get(i).getItemBean()
								.getName());
						title.setVisibility(View.VISIBLE);
					}
				}
				img.setImageResource(getIconId(false));
				BitmapDescriptor bdA = BitmapDescriptorFactory.fromView(mView);
				OverlayOptions ooA = new MarkerOptions().position(m.getPosition())
						.icon(bdA).zIndex(9);
				Marker marker = (Marker) (baiduMap.addOverlay(ooA));
				markers.add(marker);
				marker.setTitle(m.getName());
				Bundle b = new Bundle();
				b.putInt("id", i);
				b.putInt("count", m.getCount());
				marker.setExtraInfo(b);
				}
			}
		}
	}
	
	private void initMark() {
//   if(mdealTask==null){
	   mdealTask=new DealTask();
//	}
//   if(mdealTask.getStatus())
    mdealTask.execute();
		
	}
	
//	private void initMark() {
////   if(mdealTask==null){
//	   mdealTask=new DealTask();
////	}
////   if(mdealTask.getStatus())
//    mdealTask.execute();
//		
//	}

	public void changeState(int index) {
		for (int i = 0; i < currentClustersForScenic.size(); i++) {
			View itemView = mapView.createDefaultView(i,
					currentClustersForScenic.get(i).getCount());

			TextView title = (TextView) itemView.findViewById(R.id.title);
			ImageView img = (ImageView) itemView.findViewById(R.id.ioc_img);
			TextView number_tx = (TextView) itemView
					.findViewById(R.id.number_tx);
			title.setVisibility(View.GONE);
			if (currentClustersForScenic.get(i).getCount() > 1) {
				int  count=currentClustersForScenic.get(i).getCount();
				String str="";
				if(count>99){
					str=99+"+";
				}else{
					str=count+"";
				}
				
				number_tx.setText(str);
				number_tx.setVisibility(View.VISIBLE);
			} else {
				number_tx.setVisibility(View.GONE);
				if (isTest) {
					title.setText(currentClustersForScenic.get(i).getItemBean()
							.getName());
					title.setVisibility(View.VISIBLE);
				}
			}

			if (i == index) {
				img.setImageResource(getIconId(true));
			} else {
				img.setImageResource(getIconId(false));
			}
			BitmapDescriptor bdA = BitmapDescriptorFactory.fromView(itemView);
			if (markers != null && markers.size() > i)
				markers.get(i).setIcon(bdA);
		}
	}

	public int getIconId(boolean select) {
		int id = -1;
		if (isTest) {
			if (select) {
				id = R.drawable.icon_istest_p;
			} else {
				id = R.drawable.icon_istest_n;
			}
		} else if (type == 1) {
			if (select) {
				id = R.drawable.icon_elevator_p;
			} else {
				id = R.drawable.icon_elevator_n;
			}
		} else {
			if (select) {
				id = R.drawable.icon_playground_p;
			} else {
				id = R.drawable.icon_playground_n;
			}
		}
		return id;
	}

	private OneLocationListener OneLocationListener = new OneLocationListener() {

		@Override
		public void complete(BDLocation location) {
			try {
				// 定位为数据
				if (location != null) {
					MyLocationData locData = new MyLocationData.Builder()
							.direction(100).latitude(location.getLatitude())
							.longitude(location.getLongitude()).build();
					latlng = new LatLng(location.getLatitude(),
							location.getLongitude());

					curlat = location.getLatitude() + "";
					curlon = location.getLongitude() + "";
					baiduMap.setMyLocationData(locData);
					locationIv.setEnabled(true);

					try {
						LocationUtil.getInstance(_activity).stopLocation();
					} catch (Error e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	};

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.navigation:
			gotoBaiduMap();
			break;
		case R.id.location:
			locationIv.setEnabled(false);
			MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
			baiduMap.setMapStatus(msu);
			// 设置定位监听器，其实是启动了定位
			LocationUtil.getInstance(_activity).startOneLocation(
					OneLocationListener);

			break;
		case R.id.refresh:

			break;
		case R.id.list:
			Bundle playground = new Bundle();
			playground.putInt("type", type);
			if (isTest) {// 测试机构
				finish();
			} else {
				if (isSwitch) {
					setResult(RESULT_OK, playground);
					finish();
				} else {
					playground.putBoolean("isSwitch", true);
					UIHelper.jumpForResult(_activity,
							NearbyElevatorPlaygroundListActivity.class,
							playground, 1006);
				}
			}
			break;
		case R.id.search:
			if (!TextUtils.isEmpty(input.getText().toString()))
				search(input.getText().toString());
			break;

		default:
			break;
		}
	}

	private void initDialog() {
		mCommonDialog = new CommonDialog(_activity, R.style.input_bottom_dialog);
		mCommonDialog.setContentID(R.layout.dialog_navigation);
		mCommonDialog.findViewById(R.id.baidu_btn).setOnClickListener(
				mOnClickListener);
		mCommonDialog.findViewById(R.id.gaode_btn).setOnClickListener(
				mOnClickListener);
		mCommonDialog.findViewById(R.id.google_btn).setOnClickListener(
				mOnClickListener);
		mCommonDialog.findViewById(R.id.cancel_btn).setOnClickListener(
				mOnClickListener);
		mCommonDialog.show();
	}

	private OnClickListener mOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.baidu_btn:
				mCommonDialog.dismiss();
				gotoBaiduMap();
				break;
			case R.id.gaode_btn:
				mCommonDialog.dismiss();
				gotoGaodeMap();
				break;
			case R.id.google_btn:
				mCommonDialog.dismiss();
				// gotoGaodeMap();
				break;
			case R.id.cancel_btn:
				mCommonDialog.dismiss();
				break;
			default:
				break;
			}
		}
	};

	private void gotoBaiduMap() {
		try {

			Intent intent = Intent
					.getIntent("intent://map/direction?origin=latlng:"
							+ curlat
							+ ","
							+ curlon
							+ "|name:我的位置&destination="
							+ lat
							+ ","
							+ lon
							+ "&mode=driving#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
			if (isInstallByread("com.baidu.BaiduMap")) {
				startActivity(intent); // 启动调用

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

	private void gotoGaodeMap() {
		Uri uri = Uri
				.parse("androidamap://route?sourceApplication=com.midian.mimi&slat="
						+ curlat
						+ "&slon="
						+ curlon
						+ "&dlat="
						+ lat
						+ "&dlon="
						+ lon
						+ "&dname="
						+ address
						+ "&dev=0&m=0&t=2&showType=1");

		Intent intent = new Intent("android.intent.action.VIEW", uri);
		intent.addCategory("android.intent.category.DEFAULT");
		intent.setPackage("com.autonavi.minimap");
		if (isInstallByread("com.autonavi.minimap")) {
			startActivity(intent); // 启动调用
		} else {
			UIHelper.t(_activity, "请安装高德地图客户端");
		}

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// mSearch.destroy();
		mPoiSearch.destroy();

		if (mCommonDialog != null)
			mCommonDialog.dismiss();
		if(mdealTask!=null){
			mdealTask.cancel(true);
			mdealTask=null;
			}
	}

	private void search(String key) {

		Bundle b=new Bundle();
		b.putString("key", key);
		b.putInt("type", type);
		if(isTest){
			UIHelper.jump(_activity, QueryTestOrganizationResultActivity1.class, b);
		}else{
			UIHelper.jump(_activity, NearbyElevatorPlaygroundListActivity.class, b);
		}
//		Intent intent=new Intent(_activity,NearbyElevatorPlaygroundListActivity.class);
//		intent.putExtras(b);
//		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//		startActivity(intent);
//		UIHelper.jump(_activity, NearbyElevatorPlaygroundListActivity.class, b);
//		if (latlng != null)
//			mPoiSearch.searchNearby(new PoiNearbySearchOption()
//					.location(latlng).radius(2000000).pageNum(0).keyword(key));

	}

	@Override
	public void onGetPoiDetailResult(PoiDetailResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetPoiResult(PoiResult result) {
		// TODO Auto-generated method stub
		if (result == null
				|| result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
			return;
		} else {
			list = result.getAllPoi();
			if (list != null && list.size() > 0) {
				MyLocationData locData = new MyLocationData.Builder()
						.direction(100).latitude(list.get(0).location.latitude)
						.longitude(list.get(0).location.longitude).build();
				baiduMap.setMyLocationData(locData);

				for (PoiInfo item : list) {
					System.out.println("item:::::::" + item.name);
				}
			}
		}
	}

	

}
