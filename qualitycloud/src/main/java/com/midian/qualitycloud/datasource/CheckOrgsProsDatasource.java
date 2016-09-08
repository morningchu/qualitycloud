package com.midian.qualitycloud.datasource;

import java.util.ArrayList;

import midian.baselib.base.BaseListDataSource;
import android.content.Context;
import android.text.TextUtils;

import com.midian.qualitycloud.bean.CheckOrgListBean;
import com.midian.qualitycloud.bean.Check_pros;
import com.midian.qualitycloud.utils.AppUtil;

public class CheckOrgsProsDatasource extends BaseListDataSource<Check_pros> {
	private String keywords;
	private String id;

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public CheckOrgsProsDatasource(Context context, String id, String keywords) {
		super(context);
		this.id = id;
		this.keywords = keywords;
	}

	@Override
	protected ArrayList<Check_pros> load(int page) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<Check_pros> morelist = new ArrayList<Check_pros>();
		if (TextUtils.isEmpty(id)) {
			hasMore = false;
			return morelist;
		}

		
		CheckOrgListBean checkOrgs = AppUtil.getQualityCloudApiClient(ac)
				.getCheckOrgsPros(id, keywords,""+ (page+1));

		if (checkOrgs.isOK()) {
			// morelist.addAll(checkOrgs.getContent());
			hasMore = checkOrgs.getContent().size() == 10;
			this.page = page;
		} else {
			// hasMore=false;
			ac.handleErrorCode(context, checkOrgs.error_code);
		}

		return morelist;
	}
}
