package com.midian.qualitycloud.itemview;

import midian.baselib.utils.UIHelper;
import midian.baselib.view.BaseTpl;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.midian.qualitycloud.R;
import com.midian.qualitycloud.bean.GeoProRecommendationsBean.ContentRecom;
import com.midian.qualitycloud.ui.geographical.GIMoreDetailActivity;

public class RelatedRecommendItemTpl extends BaseTpl<ContentRecom> implements
		OnClickListener {

	private TextView name_relate,code_relate,content_relate;
	private ImageView img_relate;
	private LinearLayout item_relate;
	ContentRecom contentRecom;
	private String geo_pro_id;
	private String name;
	public RelatedRecommendItemTpl(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public RelatedRecommendItemTpl(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initView() {
		img_relate = (ImageView) findViewById(R.id.img_relate);
		name_relate = (TextView) findViewById(R.id.name_relate);
		code_relate = (TextView) findViewById(R.id.code_relate);
		content_relate = (TextView) findViewById(R.id.content_relate);
		item_relate = (LinearLayout) findViewById(R.id.item_relate);
	}

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.item_related_recommend;
	}

	@Override
	public void setBean(ContentRecom bean, int position) {
		// TODO Auto-generated method stub
		this.contentRecom = bean;
		ac.setImage(img_relate, bean.getLogo_pic_thumb_name());
		name_relate.setText(bean.getGeo_pro_name());
		code_relate.setText(bean.getProtect_notice_num());
		content_relate.setText(bean.getArea());
		geo_pro_id = bean.getGeo_pro_id();
		name = bean.getGeo_pro_name();
		item_relate.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Bundle bundle = new Bundle();
		bundle.putString("geo_pro_id", geo_pro_id);
		bundle.putString("name", name);
		UIHelper.jump(_activity, GIMoreDetailActivity.class,bundle);
	}

}
