package com.midian.qualitycloud.datasource;

import java.util.ArrayList;

import midian.baselib.app.AppContext;
import midian.baselib.base.BaseListDataSource;
import android.content.Context;
import android.text.TextUtils;

import com.midian.qualitycloud.bean.CheckOrgsBean;
import com.midian.qualitycloud.bean.CheckOrgsBean.ContentCheckOrgs;
import com.midian.qualitycloud.utils.AppUtil;

public class QueryTestOrganizationDatasource extends
		BaseListDataSource<ContentCheckOrgs> {
	private String field_id;
	private String keywords;
	private String quality_id;
	private String area_id;
	private String id;
	private String type;
	String ids = "";

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getQuality_id() {
		return quality_id;
	}

	public void setQuality_id(String quality_id) {
		this.quality_id = quality_id;
	}

	public String getArea_id() {
		return area_id;
	}

	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}

	public QueryTestOrganizationDatasource(Context context, String field_ids,
			String keywords) {
		super(context);
		this.field_id = field_ids;
		this.keywords = keywords;
	}

	public void setSearchKey(String field_id, String keywords) {
		this.keywords = keywords;
		this.field_id = field_id;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	@Override
	protected ArrayList<ContentCheckOrgs> load(int page) throws Exception {
		// TODO Auto-generated method stub
		
		ArrayList<ContentCheckOrgs> morelist = new ArrayList<ContentCheckOrgs>();
		if (TextUtils.isEmpty(area_id)) {
			area_id = null;
		}
		if (TextUtils.isEmpty(quality_id)) {
			quality_id = null;
		}
		this.page = page;
		CheckOrgsBean checkOrgs = AppUtil.getQualityCloudApiClient(ac)
				.getCheckOrgs(keywords, field_id, area_id, quality_id, ids,
						ac.lat, ac.lon, type, (page+1) + "",
						AppContext.PAGE_SIZE);

		if (checkOrgs.isOK()) {
			if (checkOrgs != null) {
				morelist.addAll(checkOrgs.getContent());
				if (checkOrgs.getContent().size() == 0||checkOrgs.getContent().size()<20) {
					hasMore = false;
				} else {
					hasMore = true;
					this.page=page;
				}
			}
		} else {
			ac.handleErrorCode(context, checkOrgs.error_code);
		}
		return morelist;
	}
}
