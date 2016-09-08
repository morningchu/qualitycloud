package com.pdfread.standardreader.utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

public class SoftInputUtils {

	public static void closeSoftInput(Activity activity) {
		try {
			if (activity.getCurrentFocus() != null) {
				((InputMethodManager) activity
						.getSystemService(Context.INPUT_METHOD_SERVICE))
						.hideSoftInputFromWindow(activity.getCurrentFocus()
								.getWindowToken(),
								InputMethodManager.HIDE_NOT_ALWAYS);
			}
		} catch (Exception e) {
		}
	}

	public static void openSoftInput(Activity activity) {
		try {
			InputMethodManager inputMethodManager = (InputMethodManager) activity
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputMethodManager.toggleSoftInput(0,
					InputMethodManager.HIDE_NOT_ALWAYS);
		} catch (Exception e) {
		}
	}

}
