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

public class SelectFilterTab extends LinearLayout implements
		View.OnClickListener {
	private onTabChangeListener onTabChangeListener;
	List<TextView> tabs = new ArrayList<TextView>();
	int curIndex = 0;
	private int tabTextColor = AppConstant.tabTextColor;
	private int tabSelectTextColor = AppConstant.tabTextColor2;

	Paint linePaint;
	private int underlineHeight = 2;

	public SelectFilterTab(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public SelectFilterTab(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public SelectFilterTab.onTabChangeListener getOnTabChangeListener() {
		return onTabChangeListener;
	}

	public void setOnTabChangeListener(
			SelectFilterTab.onTabChangeListener onTabChangeListener) {
		this.onTabChangeListener = onTabChangeListener;
	}

	private void init(Context context) {
		setWillNotDraw(false);
		View footer = inflate(context, R.layout.tab_select_filter, null);
		addView(footer);
		tabs.add((TextView) footer.findViewById(R.id.tab1));
		tabs.add((TextView) footer.findViewById(R.id.tab2));
		tabs.add((TextView) footer.findViewById(R.id.tab3));
		tabs.add((TextView) footer.findViewById(R.id.tab4));
		tabs.add((TextView) footer.findViewById(R.id.tab5));
		tabs.add((TextView) footer.findViewById(R.id.tab6));
		linePaint = new Paint();
		linePaint.setColor(tabSelectTextColor);
		underlineHeight = dp2px(context, 3);
		int i = 0;
		for (View tab : tabs) {
			tab.setOnClickListener(this);
			tab.setTag(i);
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
				tab.setTextColor(tabSelectTextColor);
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
		int offsetx = width / 6;
		int itemoffsetx = curIndex % 3 * width;
		int itemoffsety = curIndex > 2 ? height : -1;
		canvas.drawRect(offsetx + itemoffsetx, itemoffsety + height
				- underlineHeight, itemoffsetx + width - offsetx, itemoffsety
				+ height, linePaint);

	}
}
