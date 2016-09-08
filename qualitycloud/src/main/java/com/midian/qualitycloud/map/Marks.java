package com.midian.qualitycloud.map;

import java.util.List;

import com.baidu.mapapi.model.LatLng;

public class Marks {

	// 显示位置
	private LatLng latLng;

	// 所有的点集合
	private List<Marks> mMarkers;

	private String pic;
	private String name;
	private ItemBean itemBean;

	public LatLng getLatLng() {
		return latLng;
	}

	public void setLatLng(LatLng latLng) {
		this.latLng = latLng;
	}

	public Marks(LatLng latLng) {
		super();
		this.latLng = latLng;
	}

	public LatLng getPosition() {
		return latLng;
	}

	public List<Marks> getmMarkers() {
		return mMarkers;
	}

	public void setmMarkers(List<Marks> mMarkers) {
		this.mMarkers = mMarkers;
	}

	public int getCount() {
		return mMarkers.size();
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public ItemBean getItemBean() {
		return itemBean;
	}

	public void setItemBean(ItemBean itemBean) {
		this.itemBean = itemBean;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
