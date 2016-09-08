package com.midian.qualitycloud.itemview;

import com.midian.qualitycloud.R;
import com.midian.qualitycloud.ui.testorganization.QueryTestOrganizationResultActivity.Item;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import midian.baselib.view.BaseTpl;

public class ItemNearSelectViewTpl extends BaseTpl<Item> {

	private TextView item_name;

	public ItemNearSelectViewTpl(Context context) {
		super(context);
	}

	public ItemNearSelectViewTpl(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void initView() {

		item_name = findView(R.id.item_name);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.item_near_select_view;
	}

	@Override
	public void setBean(Item bean, int position) {
		item_name.setText(bean.getName());
	}

}
