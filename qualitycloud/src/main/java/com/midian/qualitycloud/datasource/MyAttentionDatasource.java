package com.midian.qualitycloud.datasource;

import java.util.ArrayList;

import com.midian.qualitycloud.bean.MyCollectsBean;
import com.midian.qualitycloud.bean.MyCollectsBean.CollectContent;
import com.midian.qualitycloud.utils.AppUtil;

import midian.baselib.app.AppContext;
import midian.baselib.base.BaseListDataSource;
import midian.baselib.bean.NetResult;
import android.content.Context;

public class MyAttentionDatasource extends BaseListDataSource<CollectContent> {
	private String type = "";

	public MyAttentionDatasource(Context context, String type) {
		super(context);
		// TODO Auto-generated constructor stub
		this.type = type;
	}

	public void setType(String type) {
		if ("0".equals(type)) {
			this.type = "";
		} else
			this.type = type;
	}

	@Override
	protected ArrayList<CollectContent> load(int page) throws Exception {
		// TODO Auto-generated method stub
		// ArrayList<NetResult> morelist = new ArrayList<NetResult>();
		ArrayList<CollectContent> morelists = new ArrayList<CollectContent>();
		if (ac.isAccess()) {
			MyCollectsBean myCollects0 = AppUtil.getQualityCloudApiClient(ac)
					.getMyCollects(type, (page + 1) + "", AppContext.PAGE_SIZE);
			if (myCollects0.isOK()) {
				if (myCollects0 != null) {
					morelists.addAll(myCollects0.getContent());
					if (myCollects0.getContent().size() == 20) {
						hasMore = true;
						this.page = page;
					} else {
						hasMore = false;
					}
				}
			} else {
				ac.handleErrorCode(context, myCollects0.error_code);
			}
		}
		
		return morelists;
	}
}
