package com.midian.qualitycloud.ui.common;

import java.io.File;
import java.net.URISyntaxException;

import midian.baselib.base.BaseActivity;
import midian.baselib.utils.FDDataUtils;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;
import midian.baselib.widget.dialog.CommonDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatus.Builder;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.midian.qualitycloud.R;
import com.midian.qualitycloud.utils.LocationUtil;

/**
 * 我的位置
 * 
 * @author MIDIAN
 * 
 */
public class MyPostion extends BaseActivity {

	public static final String LON_KEY = "lon";
	public static final String LAT_KEY = "lat";
	public static final String ADDRESS_KEY = "address";
	public static final String TYPE_KEY = "type";
	private String lon;
	private String lat;
	private String address;
	private String type;
	RelativeLayout mposition;
	TextView tipTv;
	ImageView navigation_iv;
	private String curlon = "";
	private String curlat = "";
	BaseLibTopbarView mBaseLibTopbarView;

	public static void gotoMyPostion(Context mContext, String address,
			String lon, String lat, String type) {
		Intent intent = new Intent(mContext, MyPostion.class);
		intent.putExtra(MyPostion.LON_KEY, lon);
		intent.putExtra(MyPostion.LAT_KEY, lat);
		intent.putExtra(MyPostion.ADDRESS_KEY, address);
		intent.putExtra(MyPostion.TYPE_KEY, type);
		mContext.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		setContentView(R.layout.activity_my_postion);

		mBaseLibTopbarView = (BaseLibTopbarView) findViewById(R.id.topbar);
		mBaseLibTopbarView
				.recovery()
				.setLeftImageButton(R.drawable.icon_back,
						UIHelper.finish(_activity)).setTitle("地图")
				.setLeftText("返回", null);

		lon = getIntent().getStringExtra(LON_KEY);
		lat = getIntent().getStringExtra(LAT_KEY);
		type = getIntent().getStringExtra(TYPE_KEY);
		address = getIntent().getStringExtra(ADDRESS_KEY);

		System.out.println("map_type" + type);
		System.out.println("map_lat" + lat);

		mposition = (RelativeLayout) LayoutInflater.from(this).inflate(
				R.layout.item_map_ioc, null);
		ImageView ioc_img = (ImageView) mposition.findViewById(R.id.ioc_img);
		if("1".equals(type)){
			ioc_img.setImageResource(R.drawable.icon_elevator_n);
		}else{
			ioc_img.setImageResource(R.drawable.icon_playground_n);
		}
		TextView number_tx = (TextView) mposition.findViewById(R.id.number_tx);
		TextView title = (TextView) mposition.findViewById(R.id.title);
		title.setText(address);
		number_tx.setVisibility(View.GONE);
		title.setVisibility(View.GONE);

		BDLocation location = LocationUtil.getInstance(getApplicationContext())
				.getLastLocation();
		if (location != null) {
			curlon = location.getLongitude() + "";
			curlat = location.getLatitude() + "";
		}

		initView();
	}

	private MapView mapView;
	private BaiduMap mBaiduMap;
	CommonDialog mCommonDialog;

	private void initView() {

		mapView = (MapView) findViewById(R.id.map_view);

	}

	@Override
	protected void onStart() {

		super.onStart();
		try {
		mBaiduMap = mapView.getMap();
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		mBaiduMap.setTrafficEnabled(false);
		System.out.println("FDDataUtils.getDouble(lon)"
				+ FDDataUtils.getDouble(lon));
		System.out.println("FDDataUtils.getDouble(lat)"
				+ FDDataUtils.getDouble(lat));

		LatLng point = new LatLng(FDDataUtils.getDouble(lat),
				FDDataUtils.getDouble(lon));
		Builder mbuild = new MapStatus.Builder();
		mbuild.target(point);
		mbuild.zoom(15);
		MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory
				.newMapStatus(mbuild.build());

		mBaiduMap.setMapStatus(mapStatusUpdate);

		BitmapDescriptor bitmap = BitmapDescriptorFactory.fromView(mposition);
		OverlayOptions option = new MarkerOptions().position(point)
				.icon(bitmap);

		mBaiduMap.addOverlay(option);

		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker arg0) {
				// TODO Auto-generated method stub
				initDialog();
				return false;
			}
		});
		} catch (Exception e) {
			// TODO: handle exception
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

	private void gotoGoogleMap() {
		Intent intent = new Intent(Intent.ACTION_VIEW,
				Uri.parse("http://ditu.google.cn/maps?f=d&source=s_d&saddr= "
						+ curlat + "," + curlon + "&daddr=" + lat + "," + lon
						+ "&hl=zh"));
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				& Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
		intent.setClassName("com.google.android.apps.maps",
				"com.google.android.maps.MapsActivity");

		if (isInstallByread("com.google.android.maps")) {
			startActivity(intent); // 启动调用
		} else {
		}

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
}
