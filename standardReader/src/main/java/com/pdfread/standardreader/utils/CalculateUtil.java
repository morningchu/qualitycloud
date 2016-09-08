package com.pdfread.standardreader.utils;

import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hor.common.ScreenAdapter;
import com.pdfread.standardreader.contants.Contants;

public class CalculateUtil {

	/**
	 * 计算View 的宽、高
	 * 
	 * @param view
	 * @param width
	 * @param height
	 */
	public static void calculateViewSize(View view, int width, int height) {
		int cu_width = ScreenAdapter.calcWidth(width);
		int cu_height = ScreenAdapter.calcWidth(height);

		LayoutParams params = view.getLayoutParams();
		params.width = cu_width;
		params.height = cu_height;
		view.setLayoutParams(params);
	}

	/**
	 * 计算文字大小
	 * 
	 * @param view
	 * @param size
	 */
	public static void calculateTextSize(TextView view, int size) {

		int fontSize = ScreenAdapter.calcWidth(size);
		view.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
	}
	/**
	 * @param oriWidth
	 * @param oriHeight
	 * @return 重新计算返回参数
	 */
	public static RelativeLayout.LayoutParams calculateParams(int oriWidth,int oriHeight){
		int width = ScreenAdapter.calcWidth(oriWidth);
		int height = ScreenAdapter.calcWidth(oriHeight);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
		return params;
	}
	public static void setTitleParams(View top_layout){
		int calcWidth = ScreenAdapter.calcWidth(Contants.TITLE_HEIGHT);
		android.widget.RelativeLayout.LayoutParams topParams = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, calcWidth);
		top_layout.setLayoutParams(topParams);
	}
}
