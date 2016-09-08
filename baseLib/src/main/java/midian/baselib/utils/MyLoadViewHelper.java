package midian.baselib.utils;

import midian.baselib.shizhefei.view.vary.VaryViewHelper;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.midian.baselib.R;

public class MyLoadViewHelper {
	private VaryViewHelper helper;
	private OnClickListener onClickRefreshListener;
	private Context context;

	public void init(View content, OnClickListener onClickRefreshListener) {
		helper = new VaryViewHelper(content);
		this.context = content.getContext().getApplicationContext();
		this.onClickRefreshListener = onClickRefreshListener;
	}

	public void restore() {
		helper.restoreView();
	}

	public void showLoading() {
		View layout = helper.inflate(R.layout.load_ing);
		TextView textView = (TextView) layout.findViewById(R.id.textView1);
		textView.setText(context.getResources().getString(R.string.loading));
		helper.showLayout(layout);
	}

	public void tipFail() {
		Toast.makeText(context,
				context.getResources().getString(R.string.loading_fail),
				Toast.LENGTH_SHORT).show();
	}

	public void showFail() {
		View layout = helper.inflate(R.layout.load_error);
		TextView textView = (TextView) layout.findViewById(R.id.textView1);
		textView.setText(context.getResources()
				.getString(R.string.loading_fail));
		Button button = (Button) layout.findViewById(R.id.button1);
		button.setText(context.getResources().getString(R.string.try_again));
		button.setOnClickListener(onClickRefreshListener);
		helper.showLayout(layout);
	}

	public void showEmpty() {
		View layout = helper.inflate(R.layout.load_empty);
		TextView textView = (TextView) layout.findViewById(R.id.textView1);
		textView.setText(context.getResources().getString(R.string.not_data));
		Button button = (Button) layout.findViewById(R.id.button1);
		button.setText(context.getResources().getString(R.string.try_again));
		button.setOnClickListener(onClickRefreshListener);
		helper.showLayout(layout);
	}

}