package com.midian.qualitycloud.map;

public class ItemBean {
	private String id;
	private String pic;
	private String lat;
	private String lon;
	private String name;

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ItemBean(String id,String pic, String lat, String lon,String name) {
		super();
		this.pic = pic;
		this.lat = lat;
		this.lon = lon;
		this.name=name;
		this.id=id;
	}

	public ItemBean() {
		super();
	}

}