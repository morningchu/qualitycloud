package com.midian.qualitycloud.bean;

import midian.baselib.bean.NetResult;

/**
 * 检测项目列表
 * 
 * @author MIDIAN
 * 
 */
public class Check_pros extends NetResult {
	private String check_pro_id;//
	private String check_pro_name;//
	private String price;//
	private String standard;//

	public String getCheck_pro_id() {
		return check_pro_id;
	}

	public void setCheck_pro_id(String check_pro_id) {
		this.check_pro_id = check_pro_id;
	}

	public String getCheck_pro_name() {
		return check_pro_name;
	}

	public void setCheck_pro_name(String check_pro_name) {
		this.check_pro_name = check_pro_name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}
}