package com.midian.qualitycloud.ui.testorganization;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import midian.baselib.base.BaseFragmentActivity;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.dialog.CommonDialog;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Projection;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.map.BaiduMap.OnMapLoadedCallback;
import com.baidu.mapapi.map.BaiduMap.OnMapStatusChangeListener;
import com.baidu.mapapi.map.BaiduMap.OnMapTouchListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.midian.qualitycloud.R;
import com.midian.qualitycloud.bean.MapFacilitiesBean;
import com.midian.qualitycloud.map.Cluster;
import com.midian.qualitycloud.map.ItemBean;
import com.midian.qualitycloud.map.MapViewFactory;
import com.midian.qualitycloud.map.Marks;
import com.midian.qualitycloud.ui.main.ElevatorPlaygroundListForMapActivity;
import com.midian.qualitycloud.utils.AppUtil;
import com.midian.qualitycloud.utils.LocationUtil;
import com.midian.qualitycloud.utils.LocationUtil.OneLocationListener;

/**
 * 测试机构附近地图
 * 
 * @author MIDIAN
 * 
 */
public class TestOrganizationMap extends BaseFragmentActivity implements
		OnClickListener, OnGetPoiSearchResultListener {
	private BaiduMap baiduMap;

	private MapView mMapView;
	View locationIv;

	private Boolean isAverageCenter = false;
	boolean isFirst = true;
	private List<Marks> mMarkersForScenic = new ArrayList<Marks>();
	// 当前的聚合集合
	private List<Marks> currentClustersForScenic = new ArrayList<Marks>();
	private Cluster mCluster;
	private Cluster mECluster;
	private double mDistance = 600000;
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

	PoiSearch mPoiSearch;
	List<PoiInfo> list;
	String lon, lat, curlat, curlon, address;

	View navigation;
	CommonDialog mCommonDialog;// 导航弹框

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_map);

		initMap();

		init();

	}

	private void init() {
		locationIv = findViewById(R.id.location);
		findViewById(R.id.back).setOnClickListener(UIHelper.finish(_activity));
		findViewById(R.id.location).setOnClickListener(this);
		findViewById(R.id.refresh).setOnClickListener(this);
		findViewById(R.id.list).setOnClickListener(this);
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
				ScenicOneLocationListener);
		baiduMap.setMyLocationEnabled(true);
		baiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
				LocationMode.FOLLOWING, true, null));
		mapView = MapViewFactory.getInstance(_activity);
		mCluster = new Cluster(baiduMap, isAverageCenter, 80, mDistance);
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

					String ids = "";
					int j = 0;
					for (Marks item : m.getmMarkers()) {
						if (j == 0) {
							ids = item.getItemBean().getId();
						} else {
							ids = ids + "," + item.getItemBean().getId();
						}
						j++;
					}
					Bundle p = new Bundle();
					// p.putInt("type", type);
					p.putString("ids", ids);
					UIHelper.jump(_activity,
							ElevatorPlaygroundListForMapActivity.class, p);

				} else {
					lon = m.getItemBean().getLon();
					lat = m.getItemBean().getLat();
					navigation.setVisibility(View.VISIBLE);
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

			getNetData(status.bound.southwest.longitude + "",
					status.bound.northeast.latitude + "",
					status.bound.northeast.longitude + "",
					status.bound.southwest.latitude + "");
		}

		@Override
		public void onMapStatusChangeStart(MapStatus arg0) {
			// TODO Auto-generated method stub

		}
	};

	private void getNetData(String left_top_lon, String left_top_lat,
			String right_bottom_lon, String right_bottom_lat) {
		AppUtil.getQualityCloudApiClient(ac).getmAPCheckOrgs(left_top_lon,
				left_top_lat, right_bottom_lon, right_bottom_lat, this);
	}

	private void getData() {
		itembeanList.clear();
		mMarkersForScenic.clear();
		currentClustersForScenic.clear();
		baiduMap.clear();
		markers.clear();
		// itembeanList.add(new ItemBean("1", "26.569269", "106.692965"));
		// itembeanList.add(new ItemBean("1", "24.569269", "105.692965"));
		// itembeanList.add(new ItemBean("1", "26.519269", "102.692965"));
		// itembeanList.add(new ItemBean("1", "26.579269", "100.692965"));
		initMark();
	}

	@Override
	public void onApiSuccess(NetResult res, String tag) {
		// TODO Auto-generated method stub
		super.onApiSuccess(res, tag);

		if ("getMapFacilities".equals(tag)) {

			if (res.isOK()) {
				MapFacilitiesBean mMapFacilitiesBean = (MapFacilitiesBean) res;
				itembeanList.clear();
				mMarkersForScenic.clear();
				currentClustersForScenic.clear();
				baiduMap.clear();
				markers.clear();
				for (MapFacilitiesBean.Content item : mMapFacilitiesBean
						.getContent()) {

					itembeanList
							.add(new ItemBean(item.getFacility_id(), "", item
									.getLat(), item.getLon(), item
									.getUse_company()));

				}

				initMark();
			} else {
				ac.handleErrorCode(_activity, res.error_code);
			}
		} else {

		}
	}

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

	private void initMark() {

		for (int i = 0; i < itembeanList.size(); i++) {
			ItemBean itembean = itembeanList.get(i);
			LatLng latLng = new LatLng(Double.parseDouble(itembean.getLat()),
					Double.parseDouble(itembean.getLon()));

			Marks marks = new Marks(latLng);

			if (itembean.getPic() != null && !itembean.getPic().isEmpty()) {
				marks.setPic(itembean.getPic());
			} else {
				marks.setPic("");
			}
			marks.setItemBean(itembean);
			mMarkersForScenic.add(marks);
		}

		currentClustersForScenic = mCluster.createCluster(mMarkersForScenic);

		for (int i = 0; i < currentClustersForScenic.size(); i++) {
			Marks m = currentClustersForScenic.get(i);

			View mView = mapView.createDefaultView(i, m.getCount());
			BitmapDescriptor bdA = BitmapDescriptorFactory.fromView(mView);
			OverlayOptions ooA = new MarkerOptions().position(m.getPosition())
					.icon(bdA).zIndex(9);
			Marker marker = (Marker) (baiduMap.addOverlay(ooA));
			markers.add(marker);
			marker.setTitle(m.getPic());
			Bundle b = new Bundle();
			b.putInt("id", i);
			b.putInt("count", m.getCount());
			marker.setExtraInfo(b);
		}
	}

	private OneLocationListener ScenicOneLocationListener = new OneLocationListener() {

		@Override
		public void complete(BDLocation location) {
			try {
				// 定位为数据
				if (location != null) {
					MyLocationData locData = new MyLocationData.Builder()
							.direction(100).latitude(location.getLatitude())
							.longitude(location.getLongitude()).build();
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

			break;
		case R.id.location:
			locationIv.setEnabled(false);
			MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
			baiduMap.setMapStatus(msu);
			// 设置定位监听器，其实是启动了定位
			LocationUtil.getInstance(_activity).startOneLocation(
					ScenicOneLocationListener);

			break;
		case R.id.refresh:

			break;
		case R.id.list:
			finish();
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
	}

	private void search(String key) {

		if (latlng != null)
			mPoiSearch.searchNearby(new PoiNearbySearchOption()
					.location(latlng).radius(2000000).pageNum(0).keyword(key));

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
