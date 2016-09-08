package com.midian.qualitycloud.widget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.midian.configlib.AppConstant;
import com.midian.qualitycloud.R;

public class SelectTabWithUpLine extends LinearLayout implements
		View.OnClickListener {
	private onTabChangeListener onTabChangeListener;
	List<TextView> tabs = new ArrayList<TextView>();
	int curIndex = 0;

	public int tabTextColor = AppConstant.tabTextColor;
	public int tabSelectColor = AppConstant.tabTextColor2;
	public int indicatorColor = AppConstant.tabTextColor2;
	Paint linePaint;
	private int underlineHeight = 2;

	public SelectTabWithUpLine(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public SelectTabWithUpLine(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public SelectTabWithUpLine.onTabChangeListener getOnTabChangeListener() {
		return onTabChangeListener;
	}

	public void setOnTabChangeListener(
			SelectTabWithUpLine.onTabChangeListener onTabChangeListener) {
		this.onTabChangeListener = onTabChangeListener;
	}

	private void init(Context context) {
		setWillNotDraw(false);
		View footer = inflate(context, R.layout.tab_select_filter_with_upline,
				null);
		LayoutParams p = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);

		addView(footer, p);
		tabs.add((TextView) footer.findViewById(R.id.tab1));
		tabs.add((TextView) footer.findViewById(R.id.tab2));
		tabs.add((TextView) footer.findViewById(R.id.tab3));
		tabs.add((TextView) footer.findViewById(R.id.tab4));
		linePaint = new Paint();
		linePaint.setColor(indicatorColor);
		underlineHeight = dp2px(context, 3);
		int i = 0;
		for (View tab : tabs) {
			tab.setOnClickListener(this);
			tab.setTag(i);
			i++;
		}
	}

	public void setTabs(String[] str) {
		int i = 0;
		for (TextView tab : tabs) {
			tab.setText(str[i]);
			i++;
		}
	}

	/**
	 * 把dp转为px
	 */
	public static int dp2px(Context context, float dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

	public interface onTabChangeListener {
		void onTabChange(int index, boolean isSelect);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		int i = (Integer) arg0.getTag();
		if (curIndex != i) {
			if (onTabChangeListener != null) {
				onTabChangeListener.onTabChange(i, true);
			}
			setchangeState(i);
		}

	}

	public void setchangeState(int position) {
		this.curIndex = position;
		for (int i = 0; i < tabs.size(); i++) {

			TextView tab = tabs.get(i);
			if (i == position) {
				tab.setTextColor(tabSelectColor);
			} else {
				tab.setTextColor(tabTextColor);
			}
		}
		invalidate();

	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		int width = tabs.get(curIndex).getWidth();
		int height = tabs.get(curIndex).getHeight();
		int itemoffsetx = curIndex % 4 * width;
		int itemoffsetpadding = width / 8;
		canvas.drawRect(itemoffsetx + itemoffsetpadding, 2, itemoffsetx + width
				- itemoffsetpadding, 2 + underlineHeight, linePaint);

	}
}
