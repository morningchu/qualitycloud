package com.midian.qualitycloud.ui.geographical;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import midian.baselib.base.BaseListActivity;
import midian.baselib.bean.NetResult;
import midian.baselib.shizhefei.view.listviewhelper.IDataSource;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;
import midian.baselib.widget.SearchEditText;
import midian.baselib.widget.SearchEditText.SearchEditTextListener;
import android.content.Entity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.DropBoxManager.Entry;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;

import com.midian.qualitycloud.R;
import com.midian.qualitycloud.bean.GeoProTypesBean;
import com.midian.qualitycloud.bean.GeoProsBean;
import com.midian.qualitycloud.bean.GeoProTypesBean.ContentGeoTypes;
import com.midian.qualitycloud.bean.GeoProsBean.ContentGeo;
import com.midian.qualitycloud.datasource.QueryGIResultDatasource;
import com.midian.qualitycloud.datasource.QueryTestOrganizationDatasource;
import com.midian.qualitycloud.itemview.QueryGIResultItemTpl;
import com.midian.qualitycloud.itemview.QueryTestOrganizationItemTpl;
import com.midian.qualitycloud.utils.AppUtil;

/**
 * 查询地理标志结果
 * 
 * @author MIDIAN
 * 
 */
public class QueryGIResultActivity extends BaseListActivity<ContentGeo>
		implements SearchEditTextListener {
	BaseLibTopbarView mBaseLibTopbarView;

	String text = "";
	SearchEditText mSearchEditText;
	QueryGIResultDatasource mDatasource;
	private String id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		id = getIntent().getStringExtra("id");
		text = getIntent().getStringExtra("text");
		if(TextUtils.isEmpty(id)){
			mDatasource = new QueryGIResultDatasource(QueryGIResultActivity.this,
					"", text);
		}else{
		mDatasource = new QueryGIResultDatasource(QueryGIResultActivity.this,
				id, "");}
		super.onCreate(savedInstanceState);

		initTitle();
		mSearchEditText = (SearchEditText) findViewById(R.id.search);
		mSearchEditText.setText(text);
		mSearchEditText.setSearchEditTextListener(this);

		// String editText = mSearchEditText.getText().toString().trim();

		try {
			AppUtil.getQualityCloudApiClient(ac).getGeoProTypes(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.activity_query_giresult;
	}

	public void initTitle() {
		String name = "中国地理标志保护产品";
		mBaseLibTopbarView = (BaseLibTopbarView) findViewById(R.id.topbar);
		mBaseLibTopbarView
				.setLeftImageButton(R.drawable.icon_back,
						UIHelper.finish(_activity)).setTitle(name)
				.setLeftText("返回", null).setMode(BaseLibTopbarView.MODE_1)
				.getTitle_tv().setTextColor(Color.parseColor("#FFFFFF"));
		mBaseLibTopbarView.getTitle_tv().setTextSize(
				TypedValue.COMPLEX_UNIT_SP, 15);
		mBaseLibTopbarView.getLeft_tv().setTextColor(
				Color.parseColor("#FFFFFF"));
		// String title;
		// if (name.length() > 12) {
		// title = name.substring(0, 12) + "\n"+
		// + name.substring(12, name.length());
		// mBaseLibTopbarView.getTitle_tv().setTextSize(
		// TypedValue.COMPLEX_UNIT_SP, 15);
		// mBaseLibTopbarView.setTitle(title);
		// } else {
		// mBaseLibTopbarView.setTitle(name);
		// }
	}

	@Override
	protected IDataSource<ArrayList<ContentGeo>> getDataSource() {
		// TODO Auto-generated method stub
		return mDatasource;
	}

	Map<String, String> idMap = new HashMap<String, String>();

	@Override
	public void onApiSuccess(NetResult res, String tag) {
		if (res.isOK()) {
			if ("getGeoProTypes".equals(tag)) {
				GeoProTypesBean typesBean = (GeoProTypesBean) res;
				for (ContentGeoTypes item : typesBean.getContent()) {
					idMap.put(item.getGeo_pro_type_name(),
							item.getGeo_pro_type_id());
				}
			}
		} else {
			ac.handleErrorCode(_activity, res.error_code);
		}
	}

	@Override
	protected Class getTemplateClass() {
		// TODO Auto-generated method stub
		return QueryGIResultItemTpl.class;
	}

	@Override
	public void search(String key) {
		// TODO Auto-generated method stub
		if (idMap.containsKey(key)) {
			mDatasource.setSearchKey(idMap.get(key), "");
		} else {
			mDatasource.setSearchKey("", key);
		}
		listViewHelper.refresh();
	}

}
