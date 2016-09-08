package com.midian.qualitycloud.ui.main;

import java.util.ArrayList;
import java.util.List;

import midian.baselib.app.AppManager;
import midian.baselib.base.BaseFragmentActivity;
import midian.baselib.utils.UIHelper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapLoadedCallback;
import com.baidu.mapapi.map.BaiduMap.OnMapStatusChangeListener;
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
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.midian.qualitycloud.R;
import com.midian.qualitycloud.map.Cluster;
import com.midian.qualitycloud.map.ItemBean;
import com.midian.qualitycloud.map.MapViewFactory;
import com.midian.qualitycloud.map.Marks;
import com.midian.qualitycloud.utils.LocationUtil;
import com.midian.qualitycloud.utils.LocationUtil.OneLocationListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * 附近电梯游乐场地图(旧)
 * 
 * @author MIDIAN
 * 
 */
public class NearbyElevatorPlaygroundMap extends BaseFragmentActivity implements
		OnClickListener {
	View top_bar, top_search_bar, elevator, playground, locationIv;
	EditText input;
	int type = -1;
	boolean isSwitch;
	private BaiduMap baiduMap;

	private MapView mMapView;
	MapStatusUpdate eStatus;
	MapStatusUpdate pStatus;
	private Boolean isAverageCenter = false;
	boolean isFirst = true;
	private List<Marks> mMarkersForScenic = new ArrayList<Marks>();
	// 当前的聚合集合
	private List<Marks> currentClustersForScenic = new ArrayList<Marks>();
	private Cluster mCluster;
	private Cluster mECluster;
	private double mDistance = 600000;
	MapViewFactory mapView;

	private List<Marker> markers = new ArrayList<Marker>();

	List<ItemBean> itembeanList = new ArrayList<ItemBean>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ep_map);

		initMap();

		init();
		mapView = MapViewFactory.getInstance(_activity);
		mCluster = new Cluster(baiduMap, isAverageCenter, 80, mDistance);
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

	private void getEData() {
		itembeanList.clear();
		mMarkersForScenic.clear();
		currentClustersForScenic.clear();
		baiduMap.clear();
		markers.clear();
		// itembeanList.add(new ItemBean("1", "26.569269", "116.692965"));
		// itembeanList.add(new ItemBean("1", "24.569269", "125.692965"));
		// itembeanList.add(new ItemBean("1", "26.519269", "112.692965"));
		// itembeanList.add(new ItemBean("1", "26.579269", "90.692965"));
		// itembeanList.add(new ItemBean("1", "23.569269", "116.692965"));
		// itembeanList.add(new ItemBean("1", "24.569269", "105.692965"));
		// itembeanList.add(new ItemBean("1", "22.519269", "102.692965"));
		// itembeanList.add(new ItemBean("1", "21.579269", "100.692965"));
		initMark();
	}

	private void getImage(String url) {

		ac.imageLoader.displayImage(url, new ImageView(_activity),
				new SimpleImageLoadingListener() {

					@Override
					public void onLoadingComplete(String arg0, View arg1,
							Bitmap arg2) {
						// TODO Auto-generated method stub
						// iv.setImageBitmap(arg2);
						System.out.println("arg0::::::" + arg0);
						if (currentClustersForScenic != null) {
							for (int i = 0; i < currentClustersForScenic.size(); i++) {
								if (currentClustersForScenic.get(i).getPic()
										.equals(arg0)) {

									View itemView = mapView.createView(i,
											currentClustersForScenic.get(i)
													.getCount(), arg2);
									BitmapDescriptor bdA = BitmapDescriptorFactory
											.fromView(itemView);
									if (markers != null && markers.size() > i)
										markers.get(i).setIcon(bdA);
									break;
								}
							}
						}
					}
				});
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

	private void init() {
		top_bar = findViewById(R.id.top_bar);
		top_search_bar = findViewById(R.id.top_search_bar);
		elevator = findViewById(R.id.elevator);
		playground = findViewById(R.id.playground);
		input = (EditText) findViewById(R.id.input);
		findViewById(R.id.search).setOnClickListener(this);
		findViewById(R.id.close).setOnClickListener(this);
		elevator.setOnClickListener(this);
		playground.setOnClickListener(this);
		findViewById(R.id.back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (isSwitch) {
					AppManager.getAppManager().finishActivity(
							NearbyElevatorPlaygroundActivity.class);
				}
				AppManager.getAppManager().finishActivity(
						NearbyElevatorPlaygroundMap.class);
			}
		});
		locationIv = findViewById(R.id.location);
		locationIv.setOnClickListener(this);
		findViewById(R.id.refresh).setOnClickListener(this);
		findViewById(R.id.list).setOnClickListener(this);
		findViewById(R.id.navigation).setOnClickListener(this);
		isSwitch = getIntent().getBooleanExtra("isSwitch", false);
		changeTitleState(getIntent().getIntExtra("type", 1));
	}

	private void initMap() {
		mMapView = (MapView) findViewById(R.id.map);
		MapStatus ms = new MapStatus.Builder().overlook(0).zoom(15).build();
		MapStatusUpdate u1 = MapStatusUpdateFactory.newMapStatus(ms);
		eStatus = u1;
		pStatus = u1;
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
		baiduMap.setOnMapStatusChangeListener(changeListener);
		baiduMap.setOnMapLoadedCallback(callback);
		baiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {

			public boolean onMarkerClick(final Marker marker) {
				Bundle b = marker.getExtraInfo();

				System.out.println("b:::::::" + b.getInt("count") + "id"
						+ b.getInt("id"));

				return false;
			}
		});
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
			if (isFirst) {
				isFirst = false;
				pStatus = MapStatusUpdateFactory.newMapStatus(status);
				eStatus = MapStatusUpdateFactory.newMapStatus(status);
			}

			mCluster.setmDistance(getDistance(status.zoom));

			if (type == 2) {// 游乐
				pStatus = MapStatusUpdateFactory.newMapStatus(status);
				getData();
			} else {
				eStatus = MapStatusUpdateFactory.newMapStatus(status);
				getEData();
			}

		}

		@Override
		public void onMapStatusChangeStart(MapStatus arg0) {
			// TODO Auto-generated method stub

		}
	};

	@Override
	public void onResume() {
		try {
			super.onResume();
			mMapView.onResume();

		} catch (Exception e) {
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		mMapView.onPause();

	}

	private void changeTitleState(int i) {
		if (this.type != i) {
			type = i;
			if (i == 2) {
				elevator.setSelected(false);
				playground.setSelected(true);
				// getData();
			} else {
				elevator.setSelected(true);
				playground.setSelected(false);
				// getEData();
			}

		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.search:
			top_bar.setVisibility(View.GONE);
			top_search_bar.setVisibility(View.VISIBLE);
			break;
		case R.id.close:
			top_bar.setVisibility(View.VISIBLE);
			top_search_bar.setVisibility(View.GONE);
			break;
		case R.id.elevator:
			changeTitleState(1);
			baiduMap.setMapStatus(eStatus);
			break;
		case R.id.playground:
			changeTitleState(2);
			baiduMap.setMapStatus(pStatus);
			break;

		case R.id.location:
			System.out.println("location");
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
			Bundle playground = new Bundle();
			playground.putInt("type", type);
			if (isSwitch) {
				setResult(RESULT_OK, playground);
				finish();
			} else {
				playground.putBoolean("isSwitch", true);
				UIHelper.jumpForResult(_activity,
						NearbyElevatorPlaygroundActivity.class, playground,
						1006);
			}

			break;
		case R.id.navigation:

			break;
		default:
			break;
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1006 && RESULT_OK == resultCode)
			changeTitleState(data.getIntExtra("type", 1));

	}

}
