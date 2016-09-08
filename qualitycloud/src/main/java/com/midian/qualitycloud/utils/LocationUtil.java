package com.midian.qualitycloud.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

/**
 * 定位工具类
 * 
 * @author admin
 * 
 */
public class LocationUtil {

	protected Context mContext;
	private static LocationUtil locationUtil;

	private LocationClient mLocationClient;

	// 定位监听器
	private MyLocationListener mMyLocationListener;

	public static int idleSpan = 5000;
	static BDLocation location = null;
	// 一次定位监听器
	private List<OneLocationListener> oneLocationListeners = new ArrayList<LocationUtil.OneLocationListener>();
	// 多次定位监听器
	private LocationListener locationListener;

	/**
	 * 一次定位监听
	 * 
	 * @author admin
	 * 
	 */
	public interface OneLocationListener {
		void complete(BDLocation location);
	}

//	public GeofenceClient mGeofenceClient;

	/**
	 * 多次定位监听
	 * 
	 * @author admin
	 * 
	 */
	public interface LocationListener {
		void complete(BDLocation location);
	}

	protected LocationUtil(Context mContext) {
		this.mContext = mContext;
		mLocationClient = new LocationClient(mContext);
		mMyLocationListener = new MyLocationListener();

//		mGeofenceClient = new GeofenceClient(mContext);

		mLocationClient.registerLocationListener(mMyLocationListener);

	}

	public static LocationUtil getInstance(Context context) {
		if (locationUtil == null)
			locationUtil = new LocationUtil(context.getApplicationContext());
		return locationUtil;
	}

	/**
	 * 发起一次定位,不能频繁的获取
	 */
	public void startOneLocation(OneLocationListener oneLocationListener) {
		this.idleSpan = -1;// 要设置idleSpan小于1000实现单次定位
		initLocation();
		oneLocationListeners.add(oneLocationListener);
		if (!mLocationClient.isStarted()) {
			mLocationClient.start();
		} else {
			mLocationClient.requestLocation();
		}

	}

	/**
	 * 停止定位
	 */
	public void stopLocation() {
		if (mLocationClient != null && mLocationClient.isStarted()) {
			mLocationClient.stop();
		}
	}

	/**
	 * 启动循环定位
	 */
	public void startLocation(LocationListener locationListener) {
		this.idleSpan = 5000;// 循环定位时间间隔
		initLocation();
		this.locationListener = locationListener;
		mLocationClient.start();
		// System.out.println("启动循环定位start：：："+mLocationClient);
	}

	/**
	 * 返回上一次定位结果
	 * 
	 * @return
	 */
	public BDLocation getLastLocation() {

		if (mLocationClient == null)
			return null;
		BDLocation loc = mLocationClient.getLastKnownLocation();// sdk5.3版bug(每次调用获取上次定位都为null)
		if (loc == null) {
			return location;
		}
		return mLocationClient.getLastKnownLocation();
	}

	/**
	 * 初始化定位
	 */
	private void initLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		// option.setIgnoreKillProcess(false);
		option.setProdName("质量云");
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度，默认值gcj02
		option.setScanSpan(idleSpan);// 设置发起定位请求的间隔时间为5000ms
		option.setNeedDeviceDirect(true);
		option.setIsNeedAddress(true);
		// option.setOpenGps(true);
		mLocationClient.setLocOption(option);
	}

	/**
	 * 定位监听器
	 * 
	 * @author admin
	 * 
	 */
	private class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// System.out.println("onReceiveLocation:::::"+oneLocationListeners+"size"+oneLocationListeners.size());
			//
			// System.out.println("onReceiveLocationlocation:::::::::::"
			// + location.getRadius());
			// System.out.println("onReceiveLocationgetLocType::::::::::::::::"
			// + location.getLocType());
			// System.out.println("onReceiveLocationtime::::::::::::::::"
			// + location.getTime());

			if (oneLocationListeners.size() > 0) {
				// 把上次定位请求抛出去
				for (OneLocationListener oneLocationListener : oneLocationListeners) {
					oneLocationListener.complete(location);
				}
				oneLocationListeners.clear();
			}
			LocationUtil.location = location;
			if (locationListener != null) {
				locationListener.complete(location);
			}
			// if (location.getLocType() == BDLocation.TypeGpsLocation) {
			// } else if (location.getLocType() ==
			// BDLocation.TypeNetWorkLocation) {
			// //wifi定位结果
			// if(location.getNetworkLocationType().equals("wf")){
			// }else{
			// return;
			// }
			// } else {
			// return;
			// }
		}

	}

}
