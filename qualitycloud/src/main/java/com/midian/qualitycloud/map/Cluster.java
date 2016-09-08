package com.midian.qualitycloud.map;

import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.utils.DistanceUtil;

public class Cluster {

	private BaiduMap mMapView;
	private Boolean isAverageCenter;
	private int mGridSize;
	private double mDistance;

	private List<ClusterMarker> mMarkers;

	public Cluster(BaiduMap mapView, Boolean isAverageCenter, int mGridSize,
			double mDistance) {
		this.mMapView = mapView;
		this.isAverageCenter = isAverageCenter;
		this.mGridSize = mGridSize;
		this.mDistance = mDistance;
		mMarkers = new ArrayList<ClusterMarker>();
	}
	
	public void setmDistance(double mDistance) {
		this.mDistance = mDistance;
	}

	/**
	 * 创建一个集群
	 * 
	 * @param markerList
	 * @return
	 */
	public List<Marks> createCluster(List<Marks> markerList) {
		this.mMarkers.clear();
		List<Marks> itemList = new ArrayList<Marks>();
		for (int i = 0; i < markerList.size(); i++) {
			addCluster(markerList.get(i));
		}
		for (int i = 0; i < mMarkers.size(); i++) {
			ClusterMarker cm = mMarkers.get(i);
			Marks m = new Marks(cm.getmCenter());
			m.setmMarkers(cm.getmMarkers());
			m.setPic(cm.getmMarkers().get(0).getPic());
			m.setItemBean(cm.getmMarkers().get(0).getItemBean());
			itemList.add(m);
		}
		return itemList;
	}

	private void addCluster(Marks marker) {
		try{
		LatLng markGeo = marker.getPosition();
		if (mMarkers.size() == 0) {
			// 如果是第一个点，加进Mark里面
			ClusterMarker clusterMarker = new ClusterMarker();
			clusterMarker.AddMarker(marker, isAverageCenter);
			LatLngBounds bound;
			LatLngBounds.Builder b = new LatLngBounds.Builder();
			b.include(markGeo);
			bound = b.build();
			bound = MapUtils.getExtendedBounds(mMapView, bound, mGridSize);
			clusterMarker.setmGridBounds(bound);
			mMarkers.add(clusterMarker);
		} else {
			ClusterMarker clusterContain = null;
			double distance = mDistance;
			for (int i = 0; i < mMarkers.size(); i++) {
				ClusterMarker clusterMarker = mMarkers.get(i);
				LatLng center = clusterMarker.getmCenter();
				double d = DistanceUtil.getDistance(center,
						marker.getPosition());
				// 取出最小的距离集合
				if (d < distance) {
					distance = d;
					clusterContain = clusterMarker;
				}
			}
			if (clusterContain == null
					|| !isMarkersInCluster(markGeo,
							clusterContain.getmGridBounds())) {
				ClusterMarker clusterMarker = new ClusterMarker();
				clusterMarker.AddMarker(marker, isAverageCenter);

				LatLngBounds.Builder b = new LatLngBounds.Builder();
				b.include(markGeo);
				LatLngBounds bound = MapUtils.getExtendedBounds(mMapView,
						b.build(), mGridSize);
				clusterMarker.setmGridBounds(bound);

				mMarkers.add(clusterMarker);
			} else {
				clusterContain.AddMarker(marker, isAverageCenter);
			}

		}}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 当前的标志物是否在某个集群内
	 * 
	 * @param markerGeo
	 * @param bound
	 * @return
	 */
	private Boolean isMarkersInCluster(LatLng markerGeo, LatLngBounds bound) {
		if (bound.contains(markerGeo)) {
			return true;
		}
		return false;

	}
}
