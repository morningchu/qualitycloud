package midian.baselib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * 可禁用滚动的ScrollView
 * Created by XuYang on 15/4/22.
 */
public class BaseLibScrollView extends ScrollView {

	private boolean isScrollable = true;

	public BaseLibScrollView(Context context) {
		super(context);
	}

	public BaseLibScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	



	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return this.isScrollable && super.onTouchEvent(event);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		return this.isScrollable && super.onInterceptTouchEvent(event);
	}

	public void setScrollable(boolean isScrollable) {
		this.isScrollable = isScrollable;
	}

}
