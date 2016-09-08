package com.midian.qualitycloud.datasource;

import java.util.ArrayList;

import midian.baselib.app.AppContext;
import midian.baselib.base.BaseListDataSource;
import android.content.Context;
import android.text.TextUtils;

import com.midian.qualitycloud.bean.NearFacilitiesBean;
import com.midian.qualitycloud.ui.main.NearbyElevatorPlaygroundListActivity;
import com.midian.qualitycloud.utils.AppUtil;

public class NearbyElevatorPlaygroundDatasource extends
		BaseListDataSource<NearFacilitiesBean.Content> {
	private int type = 0;
	public String keywords;
	String ids = "";

	public NearbyElevatorPlaygroundDatasource(Context context, int type,
			String keywords, String ids) {
		super(context);
		// TODO Auto-generated constructor stub
		this.keywords = keywords;
		this.type = type;
		this.ids = ids;
	}

	public void setKeyWords(String keywords) {
		this.keywords = keywords;
	}

	@Override
	protected ArrayList<NearFacilitiesBean.Content> load(int page)
			throws Exception {
		// TODO Auto-generated method stub

		ArrayList<NearFacilitiesBean.Content> morelist = new ArrayList<NearFacilitiesBean.Content>();

		
		NearFacilitiesBean mNearFacilitiesBean = AppUtil
				.getQualityCloudApiClient(ac).getNearFacilities("" + type,
						keywords, ac.lon, ac.lat, ids, (page+1)+"",AppContext.PAGE_SIZE);

		if (mNearFacilitiesBean.isOK()) {
			morelist.addAll(mNearFacilitiesBean.getContent());
			System.out.println("-----"+mNearFacilitiesBean.getContent().size());
			if(mNearFacilitiesBean.getContent().size() == 0||mNearFacilitiesBean.getContent().size()<20){
				hasMore = false;
			}else{
				this.page=page;
				hasMore = true;
			}

		} else {
			ac.handleErrorCode(context, mNearFacilitiesBean.error_code);
		}
		return morelist;
	}
}
