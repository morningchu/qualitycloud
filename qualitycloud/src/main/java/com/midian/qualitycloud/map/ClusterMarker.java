package com.midian.qualitycloud.map;

import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;

public class ClusterMarker {
	private LatLng mCenter;
	private List<Marks> mMarkers;
	private LatLngBounds mGridBounds;
	
	public ClusterMarker() {
		mMarkers = new ArrayList<Marks>();
	}
	
	private LatLng calAverageCenter(){
		double latitude=0,longitude=0;
		int len = mMarkers.size()==0?1:mMarkers.size();
		for(int i=0;i<len;i++){
			latitude = latitude + mMarkers.get(i).getPosition().latitude;
			longitude = longitude + mMarkers.get(i).getPosition().longitude;
		}
		return new LatLng((int)(latitude/len),(int)(longitude/len));
	}
	
	public void AddMarker(Marks marker,Boolean isAverageCenter){
		mMarkers.add(marker);
		if(!isAverageCenter){
			if(mCenter==null)
				this.mCenter = mMarkers.get(0).getPosition();
		}else{
			this.mCenter = calAverageCenter();
		}
	}
	
	public LatLng getmCenter() {
		return this.mCenter;
	}

	public void setmCenter(LatLng mCenter) {
		this.mCenter = mCenter;
	}

	public List<Marks> getmMarkers() {
		return mMarkers;
	}

	public void setmMarkers(List<Marks> mMarkers,Boolean isAverageCenter) {
		this.mMarkers.addAll(mMarkers);
		if(!isAverageCenter){
			if(mCenter==null){
				this.mCenter =  mMarkers.get(0).getPosition();
			}
		}else
			this.mCenter = calAverageCenter();
	}

	public LatLngBounds getmGridBounds() {
		return mGridBounds;
	}

	public void setmGridBounds(LatLngBounds mGridBounds) {
		this.mGridBounds = mGridBounds;
	}

}
