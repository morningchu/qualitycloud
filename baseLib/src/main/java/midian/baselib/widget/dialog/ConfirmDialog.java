package midian.baselib.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.EditText;
import android.widget.TextView;

import com.midian.baselib.R;

public class ConfirmDialog extends Dialog {

	private Context context;
	private View contentView;

	private View.OnClickListener onClickListener;

	private TextView title_tv;
	private TextView message_tv;
	private TextView left_action;
	private TextView right_action;
	private EditText input_et;

	public ConfirmDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		init(context);
	}

	public ConfirmDialog(Context context, int theme) {
		super(context, theme);
		init(context);
	}

	public ConfirmDialog(Context context) {
		super(context);
		init(context);
	}

	public String getInput() {
		if (null != input_et)
			return input_et.getText().toString();
		return "";
	}

	private void init(Context context) {
		this.context = context;
		Window w = this.getWindow();
		LayoutParams lp = w.getAttributes();
		lp.gravity = Gravity.CENTER;
		lp.height = LayoutParams.MATCH_PARENT;
		lp.width = LayoutParams.MATCH_PARENT;
		w.setAttributes(lp);
		this.setCanceledOnTouchOutside(true);

		contentView = View.inflate(context, R.layout.dp_confirm_dialog, null);
		this.setContentView(contentView);

		title_tv = (TextView) findViewById(R.id.title);
		message_tv = (TextView) findViewById(R.id.message);
		left_action = (TextView) findViewById(R.id.left);
		right_action = (TextView) findViewById(R.id.right);
		input_et = (EditText) findViewById(R.id.input);
	}

	public Dialog config(String title, String message,
			View.OnClickListener onClickListener) {
		this.title_tv.setText(title);
		this.message_tv.setText(message);
		this.onClickListener = onClickListener;
		return this;
	}

	public ConfirmDialog setTitleText(String title) {
		title_tv.setText(title);
		return this;
	}

	public ConfirmDialog setTitleText(int resId) {
		title_tv.setText(resId);
		return this;
	}

	public ConfirmDialog setMessageText(String message) {
		message_tv.setText(message);
		return this;
	}

	public ConfirmDialog setMessageText(int resId) {
		message_tv.setText(resId);
		return this;
	}

	public ConfirmDialog setLeft(String left,
			View.OnClickListener onClickListener) {
		left_action.setText(left);
		left_action.setOnClickListener(onClickListener);
		return this;
	}

	public ConfirmDialog setLeft(int resId, View.OnClickListener onClickListener) {
		left_action.setText(resId);
		left_action.setOnClickListener(onClickListener);
		return this;
	}

	public ConfirmDialog setRight(String right,
			View.OnClickListener onClickListener) {
		right_action.setText(right);
		right_action.setOnClickListener(onClickListener);
		return this;
	}

	public ConfirmDialog setRight(int resId,
			View.OnClickListener onClickListener) {
		right_action.setText(resId);
		right_action.setOnClickListener(onClickListener);
		return this;
	}

	public ConfirmDialog toggleInput(boolean isShown) {
		if (isShown) {
			input_et.setVisibility(View.VISIBLE);
		} else {
			input_et.setVisibility(View.GONE);
		}
		return this;
	}

	public ConfirmDialog config(String title, String message, String left,
			String right, View.OnClickListener leftOnClickListener,
			View.OnClickListener rightOnClickListener) {
		return setTitleText(title).setMessageText(message)
				.setLeft(left, leftOnClickListener)
				.setRight(right, rightOnClickListener);
	}

	public ConfirmDialog config(int titleResId, int messageResId,
			int leftResId, int rightResId,
			View.OnClickListener leftOnClickListener,
			View.OnClickListener rightOnClickListener) {
		return setTitleText(titleResId).setMessageText(messageResId)
				.setLeft(leftResId, leftOnClickListener)
				.setRight(rightResId, rightOnClickListener);
	}

	public ConfirmDialog config(String title, String message,
			final String right, final View.OnClickListener rightOnClickListener) {
		return setTitleText(title)
				.setMessageText(message)
				.setLeft(context.getString(R.string.cancel),
						new View.OnClickListener() {
							@Override
							public void onClick(View view) {
								dismiss();
							}
						}).setRight(right, new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						rightOnClickListener.onClick(view);
						dismiss();
					}
				});
	}

	public ConfirmDialog configForCouseDialog(String title, String message,
			final String left, final View.OnClickListener leftOnClickListener,
			final String right, final View.OnClickListener rightOnClickListener) {
		return setTitleText(title).setMessageText(message)
				.setLeft(left, new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if (leftOnClickListener != null)
							leftOnClickListener.onClick(view);
						 dismiss();
					}
				}).setRight(right, new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if (rightOnClickListener != null)
							rightOnClickListener.onClick(view);
						dismiss();
					}
				});
	}

	public static void makeText(Context mContext, String title, String content,
			String rightText, View.OnClickListener rightOnClickListener) {
		final ConfirmDialog dialog = new ConfirmDialog(mContext,
				R.style.confirm_dialog);
		dialog.config(mContext.getString(R.string.warm_prompt), content,
				rightText, rightOnClickListener);
		dialog.show();
	}

	public static void makeTextForCouseDialog(Context mContext, String content,
			String leftText, View.OnClickListener leftOnClickListener,
			String rightText, View.OnClickListener rightOnClickListener) {
		final ConfirmDialog dialog = new ConfirmDialog(mContext,
				R.style.confirm_dialog);
		dialog.configForCouseDialog(mContext.getString(R.string.warm_prompt),
				content, leftText, leftOnClickListener, rightText,
				rightOnClickListener);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
	}

}
