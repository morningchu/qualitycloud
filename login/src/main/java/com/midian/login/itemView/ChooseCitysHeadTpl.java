package com.midian.login.itemView;

import midian.baselib.bean.NetResult;
import midian.baselib.view.BaseTpl;
import android.content.Context;
import android.util.AttributeSet;

import com.midian.login.R;

public class ChooseCitysHeadTpl extends BaseTpl<NetResult> {

	public ChooseCitysHeadTpl(Context context) {
		super(context);
	}

	public ChooseCitysHeadTpl(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void initView() {
	}

	@Override
	protected int getLayoutId() {
		return R.layout.item_choose_citys_head_tpl;
	}

	@Override
	public void setBean(NetResult bean, int position) {

	}

}
