package midian.baselib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.midian.baselib.R;

/**
 * Created by XuYang on 15/4/12.
 */
public class BaseLibTopbarView extends RelativeLayout {

	public static final int MODE_1 = 1; // 主题色
	public static final int MODE_2 = 2; // 模式二
	private ImageView title_iv;// 标题图片
	private TextView title_tv;// 标题文字
	private TextView left_tv;// 左侧文字
	private TextView right_tv;// 右侧文字
	private ImageButton left_ib;// 左侧按钮
	private ImageButton right_ib;// 右侧按钮
	private ImageButton right2_ib;// 右侧按钮
	private ProgressBar progressBar;// 中间加载进度条
	private ImageView line_iv;// 下划线
	LinearLayout title_ll, left_ll;
	private int mode = MODE_1;
	Context context;

	public BaseLibTopbarView(Context context) {
		super(context);
		this.context = context;
		init(context);

	}

	public BaseLibTopbarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init(context);
	}

	private void init(Context context) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.topbar, this);
		title_ll = (LinearLayout) findViewById(R.id.title_ll);
		left_ll = (LinearLayout) findViewById(R.id.left_ll);
		title_tv = (TextView) findViewById(R.id.title_tv);
		title_iv = (ImageView) findViewById(R.id.title_iv);
		left_tv = (TextView) findViewById(R.id.left_tv);
		right_tv = (TextView) findViewById(R.id.right_tv);
		left_ib = (ImageButton) findViewById(R.id.left_ib);
		right_ib = (ImageButton) findViewById(R.id.right_ib);
		right2_ib = (ImageButton) findViewById(R.id.right2_ib);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		line_iv = (ImageView) findViewById(R.id.line);
		setMode(MODE_1);

	}

	public BaseLibTopbarView setTitle(String title) {
		title_tv.setText(title);
		return this;
	}

	public BaseLibTopbarView setTitleImage(int resId) {
		title_iv.setImageResource(resId);
		title_iv.setVisibility(View.VISIBLE);
		return this;
	}

	public BaseLibTopbarView setTitle(int resId) {
		title_tv.setText(resId);
		return this;
	}

	public BaseLibTopbarView setLeftText(String text,
			OnClickListener onclickListener) {
		left_tv.setText(text);
		if (onclickListener != null)
			left_ll.setOnClickListener(onclickListener);
		left_tv.setVisibility(VISIBLE);
		return this;
	}

	public BaseLibTopbarView setLeftText(int resId,
			OnClickListener onclickListener) {
		left_tv.setText(resId);
		if (onclickListener != null)
			left_ll.setOnClickListener(onclickListener);
		left_tv.setVisibility(VISIBLE);
		return this;
	}

	public BaseLibTopbarView setRightText(String text,
			OnClickListener onclickListener) {
		right_tv.setText(text);
		right_tv.setOnClickListener(onclickListener);
		right_tv.setVisibility(VISIBLE);
		return this;
	}

	public BaseLibTopbarView setRightText(int resId,
			OnClickListener onclickListener) {
		right_tv.setText(resId);
		right_tv.setOnClickListener(onclickListener);
		right_tv.setVisibility(VISIBLE);
		return this;
	}

	public BaseLibTopbarView setLeftImageButton(int resId,
			OnClickListener onclickListener) {
		left_ib.setImageResource(resId);
		if (onclickListener != null)
			left_ll.setOnClickListener(onclickListener);
		left_ib.setVisibility(VISIBLE);
		return this;
	}

	public BaseLibTopbarView setRightImageButton(int resId,
			OnClickListener onclickListener) {
		right_ib.setImageResource(resId);
		right_ib.setOnClickListener(onclickListener);
		right_ib.setVisibility(VISIBLE);
		return this;
	}

	public BaseLibTopbarView setRightImageButton(int resId) {
		right_ib.setImageResource(resId);
		right_ib.setVisibility(VISIBLE);
		return this;
	}

	public BaseLibTopbarView setRight2ImageButton(int resId,
			OnClickListener onclickListener) {
		right2_ib.setImageResource(resId);
		right2_ib.setOnClickListener(onclickListener);
		right2_ib.setVisibility(VISIBLE);
		return this;
	}

	public BaseLibTopbarView setRight2ImageButton(int resId) {
		right2_ib.setImageResource(resId);
		right2_ib.setVisibility(VISIBLE);
		return this;
	}

	public BaseLibTopbarView toggleProgressBar(boolean isShow) {
		if (isShow) {
			progressBar.setVisibility(VISIBLE);
		} else {
			progressBar.setVisibility(GONE);
		}
		return this;
	}

	public BaseLibTopbarView recovery() {
		setVisibility(View.VISIBLE);
		title_tv.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
		title_tv.setOnClickListener(null);
		left_tv.setVisibility(GONE);
		right_tv.setVisibility(GONE);
		left_ib.setVisibility(GONE);
		right_ib.setVisibility(GONE);
		right2_ib.setVisibility(GONE);
		title_iv.setVisibility(View.GONE);
		return this;
	}

	public int getMode() {
		return mode;
	}

	public BaseLibTopbarView setMode(int mode) {
		this.mode = mode;
		if (mode == MODE_1) {
			title_tv.setTextColor(getResources().getColor(R.color.topbar_text));
			left_tv.setTextColor(getResources().getColor(R.color.topbar_text));
			right_tv.setTextColor(getResources().getColor(R.color.topbar_text));

			setBackgroundResource(R.color.topbar_bg);

			// setBackgroundColor(getResources().getColor(
			// R.color.topbar_bg));
			// setBackgroundResource(R.drawable.dp_topbar_bg);

		} else if (mode == MODE_2) {
			title_tv.setTextColor(getResources()
					.getColor(android.R.color.white));
			left_tv.setTextColor(getResources().getColor(android.R.color.white));
			right_tv.setTextColor(getResources()
					.getColor(android.R.color.white));
			line_iv.setVisibility(GONE);
			setBackgroundResource(android.R.color.transparent);
		}
		return this;
	}

	/**
	 * 显示标题中的进度条
	 */
	public void showProgressBar() {
		progressBar.setVisibility(VISIBLE);
	}

	/**
	 * 隐藏标题中的进度条
	 */
	public void hideProgressBar() {
		progressBar.setVisibility(GONE);
	}

	public TextView getTitle_tv() {
		return title_tv;
	}

	public TextView getLeft_tv() {
		return left_tv;
	}

	public TextView getRight_tv() {
		return right_tv;
	}

	public ImageButton getLeft_ib() {
		return left_ib;
	}

	public ImageButton getRight_ib() {
		return right_ib;
	}

	public ProgressBar getProgressBar() {
		return progressBar;
	}

	public ImageView getLine_iv() {
		return line_iv;
	}

	public LinearLayout getTitleLl() {
		return title_ll;
	}

}
