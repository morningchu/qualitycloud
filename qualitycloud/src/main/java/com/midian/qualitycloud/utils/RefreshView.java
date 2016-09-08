package com.midian.qualitycloud.utils;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.midian.qualitycloud.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Scroller;
import android.widget.TextView;


@SuppressLint("NewApi")
public class RefreshView extends LinearLayout implements OnScrollListener {
	private View headView;
	private ImageView progressBar;
	private TextView tipTv;
	private TextView timeTv;
	private View listview;
	private int startY;
	private int headerHeight;
	private Context mContext;
	boolean isRemark;// 是否在顶端
	private int state;// 当前的状态；
	private final int NONE = 0;// 正常状态；
	private final int PULL = 1;// 提示下拉状态；
	private final int RELESE = 2;// 提示释放状态；
	private final int REFLASHING = 3;// 刷新状态；
	private RotateAnimation animation;
	private float startdrgee;
	private RefreshListener listener;
	private int firstVisibleItem;
	private View topChildView;
	private Scroller mScroller;
	private int topPadding;
	private boolean Net = true;
	private Matrix mMatrix;
	private int mRotationPivotX;
	private int mRotationPivotY;
	private int mTouchSlop;
	private boolean IDLE = false;

	public RefreshView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public RefreshView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		init();

	}

	// public RefreshView(Context context, AttributeSet attrs, int defStyle) {
	// super(context, attrs, defStyle);
	// // TODO Auto-generated constructor stub
	//
	// }

	private void init() {
		//
		mScroller = new Scroller(getContext(), new LinearInterpolator());
		// mScroller.s
		headView = LayoutInflater.from(mContext).inflate(
				R.layout.refreshableview_header, null);
		progressBar = (ImageView) headView.findViewById(R.id.header_progressIv);

		tipTv = (TextView) headView.findViewById(R.id.header_tipTv);
		timeTv = (TextView) headView.findViewById(R.id.header_timeTv);

		// Log.e("getChildCount", getChildCount() + "");

		headerHeight = measure(headView);
		LayoutParams lp = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, headerHeight);
		lp.topMargin = -headerHeight;
		lp.gravity = Gravity.CENTER;
		addView(headView, lp);
		// setHeadViewPadding(-headerHeight);
		Rotate((float) 1);
//		Net = NetUtil.isHasNet(mContext);
		mTouchSlop = ViewConfiguration.get(mContext).getScaledDoubleTapSlop();

	}

	@Override
	public void computeScroll() {
		// TODO Auto-generated method stub
		super.computeScroll();
		if (mScroller.computeScrollOffset()) {
			int i = this.mScroller.getCurrY();
			LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) this.headView
					.getLayoutParams();
			// Log.e("computeScroll", i + "" + (-headerHeight));
			int k = Math.max(i, -headerHeight);

			lp.topMargin = k;

			this.headView.setLayoutParams(lp);
			this.headView.invalidate();
			invalidate();
		}
	}

	private void Rotate(float flag) {
		progressBar.clearAnimation();
		animation = new RotateAnimation(startdrgee, 360 * flag,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		animation.setFillAfter(true);

		startdrgee = 360 * flag;

		animation.setDuration(400);
		animation.setInterpolator(new LinearInterpolator());
		//
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		if (getChildCount() > 1) {
			listview = this.getChildAt(1);
			if (listener != null) {
				((ListView) listview).setOnScrollListener(this);
			}

		}
	}

	@SuppressLint("NewApi")
	private void onMove(MotionEvent motionEvent) {
		if (!isRemark) {
			return;
		}
		// LinearLayout.LayoutParams lp = (LayoutParams) headView
		// .getLayoutParams();
		int tempY = (int) motionEvent.getRawY();
		int space = (int) ((tempY - startY) * 0.3F);
		// int space2 = lp.topMargin+space;
		topPadding = (int) (space - headerHeight);
		switch (state) {
		case NONE:
			if (space > 0) {
				state = PULL;
				reflashViewByState();
			}
			break;
		case PULL:
			if (topPadding <= -headerHeight) {
				setHeadViewPadding(-headerHeight);
			} else {
				setHeadViewPadding(topPadding);
			}
			// progressBar.setRotation(space * 360 / (headerHeight + 30));
			setImageRatation(space * 360 / (headerHeight + 30));

			progressBar.invalidate();
			// progressBar.setRO
			// progressBar.setR
			if (space > headerHeight + 30) {
				state = RELESE;
				reflashViewByState();
				// Rotate(space / (headerHeight + 30));
			}
			break;
		case RELESE:
			setHeadViewPadding(topPadding);
			setImageRatation(space / (headerHeight + 30) * 360);
			// Rotate(space / (headerHeight + 30));
			if (space < headerHeight + 30) {
				state = PULL;
				reflashViewByState();
			} else if (space <= 0) {
				state = NONE;
				isRemark = false;
				reflashViewByState();
			}
			break;
		}

	}

	private void reflashViewByState() {
		switch (state) {
		case NONE:
			returnInitState();
			// setHeadViewPadding(-headerHeight);
			break;
		case PULL:
			tipTv.setText("下拉刷新...");
			// progressBar.clearAnimation();
			// animation.setRepeatCount(-0);
			// progressBar.setAnimation(animation);

			break;
		case RELESE:
			tipTv.setText("放开可以刷新...");
			// progressBar.clearAnimation();
			// animation.setRepeatCount(-0);
			// progressBar.setAnimation(animation);

			break;
		case REFLASHING:
			// setHeadViewPadding(0);
			refresh();
			tipTv.setText("正在载入...");
			progressBar.clearAnimation();
			animation.setRepeatCount(-1);
			progressBar.setAnimation(animation);
			break;

		}

	}

	public void RefreshTime() {
		state = NONE;
		Net = true;
		isRemark = false;
		reflashViewByState();
		SimpleDateFormat format = new SimpleDateFormat("MM月dd日  hh:mm");
		Date date = new Date(System.currentTimeMillis());
		String time = format.format(date);
		timeTv.setText(time);
		progressBar.clearAnimation();
		// mScroller.startScroll(startX, startY, dx, dy)
	}

	// private boolean canScroll() {
	// if (listview instanceof ScrollView) {
	// // Log.e("ScrollView", ((ScrollView) listview).getScrollY() + "");
	// // if (((ScrollView) listview).getScrollY() == 0) {
	//
	// return true;
	// // }
	// }
	// return false;
	// }

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// Log.e("ACTION_DOWN", "ACTION_DOWN");

			topChildView = ((ListView) listview).getChildAt(0);
			if (topChildView != null) {
				if (((ListView) listview).getFirstVisiblePosition() == 0
						&& topChildView.getTop() == 0 && IDLE) {
					isRemark = true;
					startY = (int) ev.getRawY();
				}
			} else if (!Net) {
				isRemark = true;
				startY = (int) ev.getRawY();
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (isRemark && startY - ev.getRawY() < -5) {
				return true;
			}
			return false;
		}

		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:
			onMove(event);
			break;
		case MotionEvent.ACTION_UP:
			MotionUp();
			break;
		}
		return true;
	}

	/*
	 * 松手
	 */
	private void MotionUp() {
		if (state == RELESE) {
			state = REFLASHING;
			// refresh();
			isRemark = false;
			reflashViewByState();
			if (listener != null) {
				listener.onRefresh();
			}
		} else if (state == PULL) {
			state = NONE;
			isRemark = false;
			returnInitState();
		}

	}

	private void refresh() {
		// TODO Auto-generated method stub
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) this.headView
				.getLayoutParams();
		int i = lp.topMargin;
		mScroller.startScroll(0, i, 0, 0 - i, 250);
		invalidate();
	}

	private void returnInitState() {
		// TODO Auto-generated method stub
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) this.headView
				.getLayoutParams();
		int i = lp.topMargin;
		if (topPadding > 0) {
			mScroller.startScroll(0, i, 0, -headerHeight - topPadding, 250);
		} else {
			mScroller.startScroll(0, i, 0, -headerHeight, 250);
		}

		invalidate();
	}

	private void setHeadViewPadding(int topPadding) {

		// headView.setPadding(headView.getPaddingLeft(), topPadding,
		// headView.getPaddingRight(), headView.getPaddingBottom());
		// headView.invalidate();
		LinearLayout.LayoutParams lp = (LayoutParams) headView
				.getLayoutParams();
		lp.topMargin = topPadding;
		headView.setLayoutParams(lp);
		headView.invalidate();
		invalidate();
	}

	private int measure(View view) {
		int w = View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		view.measure(w, h);

		return view.getMeasuredHeight();

	}

	public void setRefreshListener(RefreshListener listener) {
		this.listener = listener;
	}

	public interface RefreshListener {
		public void onRefresh();
	}

	private void setImageRatation(int rotation) {
		if (Build.VERSION.SDK_INT > 11) {
			progressBar.setRotation(rotation);
		} else {
			if (null == mMatrix) {
				mMatrix = new Matrix();

				// 计算旋转的中心点
				Drawable imageDrawable = progressBar.getDrawable();
				if (null != imageDrawable) {
					mRotationPivotX = Math.round(imageDrawable
							.getIntrinsicWidth() / 2f);
					mRotationPivotY = Math.round(imageDrawable
							.getIntrinsicHeight() / 2f);
				}
			}

			mMatrix.setRotate(rotation, mRotationPivotX, mRotationPivotY);
			progressBar.setImageMatrix(mMatrix);
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		this.firstVisibleItem = firstVisibleItem;
	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		// TODO Auto-generated method stub
		if (arg1 == SCROLL_STATE_IDLE) {
			IDLE = true;
		} else {
			IDLE = false;
		}

	}
}
