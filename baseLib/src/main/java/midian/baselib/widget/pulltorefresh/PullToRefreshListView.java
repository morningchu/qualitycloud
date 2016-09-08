package midian.baselib.widget.pulltorefresh;

import midian.baselib.shizhefei.view.vary.VaryViewHelperX;
import midian.baselib.utils.ScreenUtils;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.midian.baselib.R;

/**
 * 这个类实现了ListView下拉刷新，上加载更多和滑到底部自动加载
 * 
 * @author Li Hong
 * @since 2013-8-15
 */
public class PullToRefreshListView extends PullToRefreshBase<ListView> {

	/** ListView */
	private ListView mListView;
	/** 滚动的监听器 */
	private OnScrollListener mScrollListener;

	/**
	 * 构造方法
	 * 
	 * @param context
	 *            context
	 */
	public PullToRefreshListView(Context context) {
		this(context, null);
		init(context);
	}

	/**
	 * 构造方法
	 * 
	 * @param context
	 *            context
	 * @param attrs
	 *            attrs
	 */
	public PullToRefreshListView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		init(context);
	}

	/**
	 * 构造方法
	 * 
	 * @param context
	 *            context
	 * @param attrs
	 *            attrs
	 * @param defStyle
	 *            defStyle
	 */
	public PullToRefreshListView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init(context);
		setPullLoadEnabled(false);
	}

	RelativeLayout ll;
	float oldFirst = -1;

	@SuppressLint("NewApi")
	public void init(Context context) {
		aryViewHelperXm = new VaryViewHelperX(mListView);
		ll = new RelativeLayout(context);
		TextView tv = new TextView(context);

		RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		p.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		p.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		p.bottomMargin = ScreenUtils.dpToPxInt(context, 15);
		p.rightMargin = ScreenUtils.dpToPxInt(context, 15);
		ll.addView(tv, p);
		tv.setId(1);
		tv.setBackgroundResource(R.drawable.bg_top);
		ll.findViewById(1).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (android.os.Build.VERSION.SDK_INT >= 11) {
					mListView.smoothScrollToPositionFromTop(0, 0, 200);
				} else {
					mListView.setSelection(0);
				}
				aryViewHelperXm.restoreView();
			}
		}); 
		mListView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	public void restoreView(){
		if(aryViewHelperXm!=null);
		aryViewHelperXm.restoreView();
	}

	@SuppressLint("NewApi")
	@Override
	public void Scroll(boolean isStart,float y) {
		super.Scroll(isStart,y);
		int i=mListView.getFirstVisiblePosition();
//		System.out.println("yyyyyyyyy------------"+y);
		if(isStart)
			oldFirst=y;
		Boolean show=isFirstItemVisible();
		if (oldFirst-y > 10) {
			oldFirst=y;
			oldFirst = mListView.getFirstVisiblePosition();
			ll.findViewById(1).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if (android.os.Build.VERSION.SDK_INT >= 11) {
						
						mListView.smoothScrollToPositionFromTop(0, 0, 200);
					} else {
						mListView.setSelection(0);
					}
					aryViewHelperXm.restoreView();
				}
			});
			aryViewHelperXm.showLayout(ll);
		} else if(y-oldFirst > 5){
			oldFirst=y;
			if(i==0){
				aryViewHelperXm.restoreView();
			}else{
				aryViewHelperXm.showLayout(ll);
				ll.findViewById(1).setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						if (android.os.Build.VERSION.SDK_INT >= 11) {
							
							mListView.smoothScrollToPositionFromTop(0, 0, 200);
						} else {
							mListView.setSelection(0);
						}
						aryViewHelperXm.restoreView();
					}
				});
			}
		}
		
	}

	@Override
	protected ListView createRefreshableView(Context context, AttributeSet attrs) {
		ListView listView = new ListView(context);
		mListView = listView;
		mListView.setScrollBarStyle(SCROLLBARS_OUTSIDE_OVERLAY);
		mListView.setSelector(R.drawable.c_item_selector);
		mListView.setDivider(getResources()
				.getDrawable(R.drawable.divider_line));
		mListView.setHeaderDividersEnabled(false);
		mListView.setFooterDividersEnabled(false);
			
		return listView;
	}

	VaryViewHelperX aryViewHelperXm;

	/**
	 * 设置滑动的监听器
	 * 
	 * @param l
	 *            监听器
	 */
	public void setOnScrollListener(OnScrollListener l) {
		mScrollListener = l;
	}

	@Override
	protected boolean isReadyForPullUp() {
		return isLastItemVisible();
	}

	@Override
	protected boolean isReadyForPullDown() {
		return isFirstItemVisible();
	}

	/**
	 * 判断第一个child是否完全显示出来
	 * 
	 * @return true完全显示出来，否则false
	 */
	private boolean isFirstItemVisible() {
		final Adapter adapter = mListView.getAdapter();

		if (null == adapter || adapter.isEmpty()) {
			return true;
		}

		int mostTop = (mListView.getChildCount() > 0) ? mListView.getChildAt(0)
				.getTop() : 0;
		if (mostTop >= 0) {
			return true;
		}

		return false;
	}

	/**
	 * 判断最后一个child是否完全显示出来
	 * 
	 * @return true完全显示出来，否则false
	 */
	private boolean isLastItemVisible() {
		final Adapter adapter = mListView.getAdapter();

		if (null == adapter || adapter.isEmpty()) {
			return true;
		}

		final int lastItemPosition = adapter.getCount() - 1;
		final int lastVisiblePosition = mListView.getLastVisiblePosition();
		

		/**
		 * This check should really just be: lastVisiblePosition ==
		 * lastItemPosition, but ListView internally uses a FooterView which
		 * messes the positions up. For me we'll just subtract one to account
		 * for it and rely on the inner condition which checks getBottom().
		 */
		if (lastVisiblePosition >= lastItemPosition - 1) {
			final int childIndex = lastVisiblePosition
					- mListView.getFirstVisiblePosition();
			final int childCount = mListView.getChildCount();
			final int index = Math.min(childIndex, childCount - 1);
			final View lastVisibleChild = mListView.getChildAt(index);
			if (lastVisibleChild != null) {
				return lastVisibleChild.getBottom() <= mListView.getBottom();
			}
		}

		return false;
	}

	public void setMessage(Message msg) {
		mListView.setTag(R.id.listview_msg, msg);
	}
	// @Override
	// protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	// // TODO Auto-generated method stub
	// int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
	// MeasureSpec.AT_MOST);
	// super.onMeasure(widthMeasureSpec, expandSpec);
	// }
}
