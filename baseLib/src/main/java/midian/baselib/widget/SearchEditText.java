package midian.baselib.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.midian.baselib.R;

public class SearchEditText extends LinearLayout {
	LinearLayout conter, bg;
	EditText mEditText;
	TextWatcher mTextWatcher;
	View clear;
	Button search_btn;
	SearchEditTextListener mSearchEditTextListener;

	public SearchEditText(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public SearchEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public void init(Context context) {
		setWillNotDraw(false);
		setOrientation(LinearLayout.HORIZONTAL);
		try {
			conter = (LinearLayout) inflate(context, R.layout.search_edittext,
					null);

			LinearLayout.LayoutParams p = new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			setPadding(dp2px(context, 15), dp2px(context, 15),
					dp2px(context, 15), dp2px(context, 15));
			addView(conter, p);
			bg = (LinearLayout) conter.findViewById(R.id.bg);
			search_btn = (Button) conter.findViewById(R.id.search_btn);
			mEditText = (EditText) conter.findViewById(R.id.edit);
			clear = conter.findViewById(R.id.clear);
			clear.setVisibility(View.INVISIBLE);
			clear.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					setText("");
				}
			});

			mEditText.setOnEditorActionListener(new OnEditorActionListener() {

				@Override
				public boolean onEditorAction(TextView arg0, int actionId,
						KeyEvent arg2) {
					// TODO Auto-generated method stub
					if (actionId == EditorInfo.IME_ACTION_SEARCH) {
						if (mSearchEditTextListener != null)
							mSearchEditTextListener.search(mEditText.getText()
									.toString());
					}
					return false;
				}
			});
			mEditText.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence arg0, int arg1,
						int arg2, int arg3) {
					// TODO Auto-generated method stub
					if (TextUtils.isEmpty(arg0.toString())) {
						clear.setVisibility(View.INVISIBLE);
						// search_btn.setVisibility(View.GONE);
					} else {
						clear.setVisibility(View.VISIBLE);
						// search_btn.setVisibility(View.VISIBLE);
					}

					if (mTextWatcher != null) {
						mTextWatcher.onTextChanged(arg0, arg1, arg2, arg3);
					}
				}

				@Override
				public void beforeTextChanged(CharSequence arg0, int arg1,
						int arg2, int arg3) {
					// TODO Auto-generated method stub
					if (mTextWatcher != null)
						mTextWatcher.beforeTextChanged(arg0, arg1, arg2, arg3);
				}

				@Override
				public void afterTextChanged(Editable arg0) {
					// TODO Auto-generated method stub
					if (mTextWatcher != null)
						mTextWatcher.afterTextChanged(arg0);
				}
			});

			search_btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if (mSearchEditTextListener != null)
						mSearchEditTextListener.search(mEditText.getText()
								.toString());
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		// setbackground(0x550000);
	}

	public void setSearchEditTextListener(
			SearchEditTextListener mSearchEditTextListener) {
		this.mSearchEditTextListener = mSearchEditTextListener;
	}

	public void setTextWatcher(TextWatcher mTextWatcher) {
		this.mTextWatcher = mTextWatcher;
	}

	public void setHint(String hint) {
		if (mEditText != null)
			mEditText.setHint(hint);
	}

	public EditText getEditText() {
		return mEditText;
	}

	public String getText() {
		String text = "";
		if (mEditText != null)
			text = mEditText.getText().toString();
		return text;
	}

	public void setText(String text) {
		if (mEditText != null)
			mEditText.setText(text);
	}

	public void setBackgroundResource(final int color) {
		if (bg != null)
			bg.setBackgroundResource(color);
	}

	public interface SearchEditTextListener {
		public void search(String key);
	}

	/**
	 * 把dp转为px
	 */
	public int dp2px(Context context, float dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}
}
