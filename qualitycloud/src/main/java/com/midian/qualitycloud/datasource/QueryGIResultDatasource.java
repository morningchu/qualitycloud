package com.midian.qualitycloud.datasource;

import java.util.ArrayList;
import java.util.List;

import com.midian.qualitycloud.bean.GeoProsBean;
import com.midian.qualitycloud.bean.GeoProsBean.ContentGeo;
import com.midian.qualitycloud.utils.AppUtil;

import midian.baselib.app.AppContext;
import midian.baselib.base.BaseListDataSource;
import midian.baselib.bean.NetResult;
import android.content.Context;

public class QueryGIResultDatasource extends BaseListDataSource<ContentGeo> {
	String ids;
	String keywords;

	public QueryGIResultDatasource(Context context, String id, String value) {
		super(context);
		// TODO Auto-generated constructor stub
		this.ids = id;
		this.keywords = value;
	}

	public void setSearchKey(String id, String keywords) {
		this.keywords = keywords;
		this.ids = id;
	}

	@Override
	protected ArrayList<ContentGeo> load(int page) throws Exception {
		// TODO Auto-generated method stub
		ArrayList<ContentGeo> morelist = new ArrayList<ContentGeo>();
		
		GeoProsBean prosBean = AppUtil.getQualityCloudApiClient(ac).getGeoPros(
				keywords, ids, (page+1) + "", AppContext.PAGE_SIZE);
		if (prosBean.isOK()) {
			if (prosBean != null) {
				// ArrayList<ContentGeo> content = prosBean.getContent();
				// ContentGeo contentGeo = content.get(content.size() - 1);
				// contentGeo.setGeo_pro_type_id(ids);
				// morelist.add(contentGeo);
				morelist.addAll(prosBean.getContent());
				if (prosBean.getContent().size() == 0||prosBean.getContent().size()<20) {
					hasMore = false;
				} else {
					hasMore = true;
					this.page = page;
				}

			}
		} else {
			ac.handleErrorCode(context, prosBean.error_code);
		}
		// morelist.add(new NetResult());
		// morelist.add(new NetResult());

		return morelist;
	}
}
