package com.midian.qualitycloud.datasource;

import java.util.ArrayList;

import midian.baselib.api.ApiCallback;
import midian.baselib.base.BaseListDataSource;
import com.midian.qualitycloud.utils.AppUtil;
import android.content.Context;

import com.midian.qualitycloud.bean.CheckOrgListBean;
import com.midian.qualitycloud.bean.CheckOrgListBean.ContentCheckOrg;
import com.midian.qualitycloud.itemview.InformationItem;

public class TestOrganizationDatasource extends
		BaseListDataSource<ContentCheckOrg> {
	private String type_id;
	private String id;

	public void setType_id(String type_id) {
		this.type_id = type_id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TestOrganizationDatasource(Context context, String id,
			String type_id) {
		super(context);
		this.id = id;
		this.type_id = type_id;
	}

	public TestOrganizationDatasource(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ArrayList<ContentCheckOrg> load(int page) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<ContentCheckOrg> morelist = new ArrayList<ContentCheckOrg>();
		CheckOrgListBean bean = AppUtil.getQualityCloudApiClient(ac)
				.getCheckOrgsProsContent(id, type_id, "" + (page + 1));
		if (bean.isOK()) {
//			for(ContentCheckOrg item:bean.getContent()){
//				item.setCheck_pro_id(id);
//			};
			morelist.addAll(bean.getContent());
			// hasMore = false;
			
			if (bean.getContent().size() < 20) {
				hasMore = false;
			}else{
				hasMore = true;
				this.page = page;
			}
		} else {
			ac.handleErrorCode(context, bean.error_code);
		}
		return morelist;
	}

}
