package com.pdfread.standardreader.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.pdfread.standardreader.R;

/**
 * @author Horner 正在加载dialog
 */
public class LoadingDialog {
	private static Dialog dialog;

	public static void isLoading(Context context) {
		dialog = new Dialog(context, R.style.Translucent_Dialog);
		View view = LayoutInflater.from(context).inflate(
				R.layout.dialog_loading_layout, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.imageView1);
		imageView.setBackgroundResource(R.drawable.loadingani);
		AnimationDrawable background = (AnimationDrawable) imageView
				.getBackground();
		background.start();
		dialog.setContentView(view);
		dialog.show();
	}

	public static void finishLoading() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}
}
