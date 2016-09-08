package midian.baselib.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.midian.baselib.R;
import com.midian.configlib.AppConstant;

/**
 * Created by XuYang on 15/4/12.
 */
public class SelectFilterView extends LinearLayout {
	String[] tabs;
	private int tabPadding = 24;
	private onTabChangeListener onTabChangeListener;
	private boolean shouldExpand = true;
	private int curIndex = -1;
	private boolean isSelect = false;
	private LinearLayout.LayoutParams defaultTabLayoutParams;
	private LinearLayout.LayoutParams expandedTabLayoutParams;

	private int tabTextSize = 15;
	private int tabTextColor = AppConstant.tabTextColor;
	private int tabTextColor2 = AppConstant.tabTextColor2;
	// 分隔线颜色
	private int dividerColor = 0x1A000000;
	private Paint dividerPaint;
	private int dividerPadding = 12;

	private Typeface tabTypeface = null;
	private int tabTypefaceStyle = Typeface.NORMAL;
	private int tabCount = 0;

	public interface onTabChangeListener {
		void onTabChange(int index, boolean isSelect);
	}

	public SelectFilterView(Context context) {
		super(context);
		init(context);
	}

	public SelectFilterView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		setWillNotDraw(false);
		this.setBackgroundResource(R.color.white);
		dividerPaint = new Paint(dividerColor);
		dividerPadding = dp2px(context, 12);
		defaultTabLayoutParams = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		expandedTabLayoutParams = new LinearLayout.LayoutParams(0,
				LayoutParams.MATCH_PARENT, 1.0f);
		tabs = getResources().getStringArray(R.array.select_filter_btns);
		tabCount = tabs.length;
		this.removeAllViews();
		for (int i = 0; i < tabs.length; i++)
			addTextTab(i, tabs[i]);
		setCurIndex(curIndex);
	}

	private void addTextTab(final int position, String title) {

		TextView tab = new TextView(getContext());
		tab.setText(title);
		tab.setGravity(Gravity.CENTER);
		tab.setSingleLine();
		tab.setTextSize(TypedValue.COMPLEX_UNIT_SP, tabTextSize);
		addTab(position, tab);
	}

	private void addIconTab(final int position, int resId) {

		ImageButton tab = new ImageButton(getContext());
		tab.setImageResource(resId);

		addTab(position, tab);

	}

	private void addTab(final int position, View tab) {
		tab.setFocusable(true);
		tab.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setCurIndex(position);
			}
		});
		tab.setPadding(tabPadding, 0, tabPadding, 0);
		addView(tab, position, shouldExpand ? expandedTabLayoutParams
				: defaultTabLayoutParams);
	}

	public SelectFilterView.onTabChangeListener getOnTabChangeListener() {
		return onTabChangeListener;
	}

	public void setOnTabChangeListener(
			SelectFilterView.onTabChangeListener onTabChangeListener) {
		this.onTabChangeListener = onTabChangeListener;
	}

	public int getCurIndex() {
		return curIndex;
	}

	public void setCurIndex(int i) {
		if (i == curIndex) {
			isSelect = !isSelect;
		} else {
			isSelect = true;
		}

		changeState(i, isSelect);
		if (onTabChangeListener != null) {
			onTabChangeListener.onTabChange(i, isSelect);
		}
		this.curIndex = i;
	}

	public void changeState(int position, boolean isSelect) {
		for (int i = 0; i < tabCount; i++) {

			View v = getChildAt(i);

			if (v instanceof TextView) {

				TextView tab = (TextView) v;
				if (i == position && isSelect) {
					tab.setTextColor(tabTextColor2);
					addRightIcon(tab, i, true);
				} else {
					tab.setTextColor(tabTextColor);
					addRightIcon(tab, i, false);
				}

			}
		}
	}
	
	public void setchangeState(int position, boolean isSelect) {
		this.curIndex = position;
		this.isSelect=isSelect;
		for (int i = 0; i < tabCount; i++) {

			View v = getChildAt(i);

			if (v instanceof TextView) {

				TextView tab = (TextView) v;
				if (i == position && isSelect) {
					tab.setTextColor(tabTextColor2);
					addRightIcon(tab, i, true);
				} else {
					tab.setTextColor(tabTextColor);
					addRightIcon(tab, i, false);
				}

			}
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (isInEditMode() || tabCount == 0) {
			return;
		}

		final int height = getHeight();
		// draw divider

		dividerPaint.setColor(dividerColor);
		for (int i = 0; i < tabCount - 1; i++) {
			View tab = getChildAt(i);
			canvas.drawLine(tab.getRight(), dividerPadding, tab.getRight(),
					height - dividerPadding, dividerPaint);
		}
	}

	private void addRightIcon(TextView tab, int i, boolean isUp) {
		String text = tabs[i] + "    ";
		SpannableStringBuilder str = new SpannableStringBuilder(text + "[icon]");

		// 获取Drawable资源
		Drawable d = getResources().getDrawable(
				isUp ? R.drawable.icon_tap_up : R.drawable.icon_tap_down);

		d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
		// 创建ImageSpan
		ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
		str.setSpan(span, text.length(), text.length() + "[icon]".length(),
				Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		tab.setText(str);
	}

	/**
	 * 把dp转为px
	 */
	public static int dp2px(Context context, float dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}
}
