package com.midian.qualitycloud.utils;

import com.midian.qualitycloud.api.QualityCloudApiClient;
import com.midian.qualitycloud.app.MAppContext;

import midian.baselib.app.AppContext;


/**
 * app工具
 * 
 * @author MIDIAN
 * 
 */
public class AppUtil {
	public static MAppContext getMAppContext(AppContext ac) {
		return (MAppContext) ac;
	}
	
	public static QualityCloudApiClient getQualityCloudApiClient(AppContext ac) {
		return ac.api.getApiClient(QualityCloudApiClient.class);
	}
}
