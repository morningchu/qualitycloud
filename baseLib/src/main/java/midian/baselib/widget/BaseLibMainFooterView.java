package midian.baselib.widget;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.midian.baselib.R;
import com.midian.configlib.AppConstant;

/**
 * Created by XuYang on 15/4/12.
 */
public class BaseLibMainFooterView extends LinearLayout {
	public int[][] iconsArr = {
			{ R.drawable.dp_tab_1_n, R.drawable.dp_tab_1_c },
			{ R.drawable.dp_tab_2_n, R.drawable.dp_tab_2_c },
			{ R.drawable.dp_tab_3_n, R.drawable.dp_tab_3_c },
			{ R.drawable.dp_tab_4_n, R.drawable.dp_tab_4_c } };

	LinearLayout tabsContainer;
	private ArrayList<LinearLayout> tabViews = new ArrayList<LinearLayout>();
	private onTabChangeListener onTabChangeListener;
	private LinearLayout.LayoutParams defaultTabLayoutParams;
	private LinearLayout.LayoutParams expandedTabLayoutParams;
	private int curIndex = 0;
	Context context;
	private boolean shouldExpand = true;
	private int tabTextColor = AppConstant.tabTextColor;
	private int tabTextSelectColor = AppConstant.tabTextColor2;
	private Paint dividerPaint;
	private int dividerColor = 0x1A000000;
	private int dividerPadding = 12;
	private int tabCount;
	boolean isShowDivier = false;

	public interface onTabChangeListener {
		void onTabChange(int index);
	}

	public BaseLibMainFooterView(Context context) {
		super(context);
		init(context);
	}

	public BaseLibMainFooterView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		this.context = context;
		setWillNotDraw(false);
		tabsContainer = new LinearLayout(context);
		tabsContainer.setOrientation(LinearLayout.HORIZONTAL);
		tabsContainer.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, dp2px(context, 54f)));
		addView(tabsContainer);
		defaultTabLayoutParams = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		expandedTabLayoutParams = new LinearLayout.LayoutParams(0,
				LayoutParams.MATCH_PARENT, 1.0f);
		dividerPadding = dp2px(context, dividerPadding);
		dividerPaint = new Paint();
		dividerPaint.setColor(dividerColor);
	}

	/**
	 * 是否显示分割线
	 * 
	 * @param isShowDivier
	 */
	public void setShowDivier(boolean isShowDivier) {
		this.isShowDivier = isShowDivier;
	}

	/**
	 * 初始化tabs
	 * 
	 * @param strTexts
	 * @param iconsArr
	 */
	public void initTab(String[] strTexts, int[][] iconsArr) {

		tabsContainer.removeAllViews();
		this.iconsArr = iconsArr;
		for (int i = 0; i < strTexts.length; i++) {
			addTab(i, iconsArr[i][0], strTexts[i]);
		}
		setCurIndex(curIndex);
		tabCount = strTexts.length;
	}

	/**
	 * 增加tab
	 * 
	 * @param position
	 * @param id
	 * @param text
	 */

	private void addTab(final int position, int id, String text) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout tab1 = (LinearLayout) inflater.inflate(R.layout.tab_item,
				null);

		tab1.setFocusable(true);
		tab1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setCurIndex(position);
			}
		});

		((ImageView) tab1.getChildAt(0)).setImageResource(id);
		((TextView) tab1.getChildAt(1)).setText(text);
		((TextView) tab1.getChildAt(1)).setTextColor(tabTextColor);
		tab1.setTag(position);
		tabViews.add(tab1);
		tabsContainer
				.addView(tab1, position, shouldExpand ? expandedTabLayoutParams
						: defaultTabLayoutParams);
	}

	public BaseLibMainFooterView.onTabChangeListener getOnTabChangeListener() {
		return onTabChangeListener;
	}

	public void setOnTabChangeListener(
			BaseLibMainFooterView.onTabChangeListener onTabChangeListener) {
		this.onTabChangeListener = onTabChangeListener;
	}

	public int getCurIndex() {
		return curIndex;
	}

	public void setCurIndex(int i) {
		((ImageView) tabViews.get(curIndex).getChildAt(0))
				.setImageResource(iconsArr[this.curIndex][0]);
		((ImageView) tabViews.get(i).getChildAt(0))
				.setImageResource(iconsArr[i][1]);

		((TextView) tabViews.get(curIndex).getChildAt(1))
				.setTextColor(tabTextColor);

		((TextView) tabViews.get(i).getChildAt(1))
				.setTextColor(tabTextSelectColor);
		if (onTabChangeListener != null && curIndex != i) {
			onTabChangeListener.onTabChange(i);
		}
		this.curIndex = i;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (!isShowDivier || tabCount == 0) {
			return;
		}

		final int height = getHeight();

		dividerPaint.setColor(dividerColor);
		for (int i = 0; i < tabCount - 1; i++) {
			View tab = tabsContainer.getChildAt(i);
			canvas.drawLine(tab.getRight(), dividerPadding, tab.getRight(),
					height - dividerPadding, dividerPaint);
		}
	}

	/**
	 * 把dp转为px
	 */
	public static int dp2px(Context context, float dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}
}
