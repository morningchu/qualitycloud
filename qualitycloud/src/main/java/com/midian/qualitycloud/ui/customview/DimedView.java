package com.midian.qualitycloud.ui.customview;

import com.midian.qualitycloud.R;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import midian.baselib.utils.AnimationHelper;
import midian.baselib.utils.Func;

/**
 * Created by XuYang on 15/1/7.
 */
public class DimedView extends LinearLayout implements View.OnClickListener {
	private ListView listView;

	private OpenCloseListener openCloseListener;

	public DimedView(Context context) {
		super(context);
		init(context);
	}

	public DimedView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		setOnClickListener(this);
		setBackgroundColor(Color.parseColor("#99000000"));
		listView = new ListView(context);
		listView.setDivider(getResources().getDrawable(R.drawable.divider_line));
		listView.setSelector(getResources().getDrawable(R.drawable.c_item_selector));
		listView.setBackgroundColor(Color.parseColor("#FBFBFB"));

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		addView(listView, params);
		TextView footer = new TextView(context);
//		footer.setText(getResources().getString(R.string.please_select_your_gateway));
		footer.setTextColor(getResources().getColor(R.color.dp_gray));
		footer.setPadding(0, Func.Dp2Px(context, 40), 0, Func.Dp2Px(context, 10));
		footer.setGravity(Gravity.CENTER);
		footer.setBackgroundResource(android.R.color.white);
		listView.addFooterView(footer);
	}

	@Override
	public void onClick(View v) {
		close();
	}

	public ListView getListView() {
		return listView;
	}

	public void close() {
		if (openCloseListener != null) {
			openCloseListener.close();
		}
		AnimationHelper.getFadeOutAnimator(this).setDuration(200).start();
	}

	public void show() {
		if (openCloseListener != null) {
			openCloseListener.open();
		}
		AnimationHelper.getFadeInAnimator(this).setDuration(200).start();
	}

	public boolean isShowing() {
		return getVisibility() == VISIBLE;
	}

	public void setAdapter(BaseAdapter adapter) {
		listView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	public OpenCloseListener getOpenCloseListener() {
		return openCloseListener;
	}

	public void setOpenCloseListener(OpenCloseListener openCloseListener) {
		this.openCloseListener = openCloseListener;
	}

	public interface OpenCloseListener {
		void open();

		void close();
	}
}
