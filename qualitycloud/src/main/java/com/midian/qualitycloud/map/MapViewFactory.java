package com.midian.qualitycloud.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.midian.qualitycloud.R;

/**
 * 创建地图上一个可现实View，默认没有设置图片，当下载完图片后，需要从新获取View赋值
 * 
 * @author admin
 * 
 */
public class MapViewFactory {

	private Context context;

	private static MapViewFactory mapViewFactory;

	// private ImageView ioc_img;
	//
	// private TextView number_tx;
	// 没下载图片下来时的默认图片
	private View defaultView;

	private MapViewFactory(Context mContext) {
		super();
		this.context = mContext.getApplicationContext();
	}

	public static MapViewFactory getInstance(Context contex) {
		if (mapViewFactory == null)
			mapViewFactory = new MapViewFactory(contex.getApplicationContext());
		return mapViewFactory;
	}

	/**
	 * 创建一个View，只能创建一个！！！
	 * 
	 * @param position
	 * @param size
	 * @return
	 */
	public View createView(int position, int size, Bitmap mBitmap) {
		RelativeLayout main_layout = (RelativeLayout) LayoutInflater.from(
				context).inflate(R.layout.item_map_ioc, null);

		ImageView ioc_img = (ImageView) main_layout.findViewById(R.id.ioc_img);

		TextView number_tx = (TextView) main_layout
				.findViewById(R.id.number_tx);

		if (size > 1) {
			number_tx.setText(size + "");
			number_tx.setVisibility(View.VISIBLE);
		} else {
			number_tx.setVisibility(View.GONE);
		}
		main_layout.setTag(position);
		ioc_img.setImageBitmap(mBitmap);
		// 必须设置，否则在小米上面无法添加到地图里面
		main_layout.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));
		return main_layout;
	}

	/**
	 * 返回默认View 当图片没有下载下来的时候，复用同一个View
	 * 
	 * @param position
	 * @param size
	 * @return
	 */
	public View createDefaultView(int position, int size) {
		if (defaultView == null) {
			defaultView = (RelativeLayout) LayoutInflater.from(context)
					.inflate(R.layout.item_map_ioc, null);
		}

		ImageView ioc_img = (ImageView) defaultView.findViewById(R.id.ioc_img);
		ioc_img.setImageResource(R.drawable.icon_map_default);
		TextView number_tx = (TextView) defaultView
				.findViewById(R.id.number_tx);
//		if (size > 1) {
//			number_tx.setText(size + "");
//			number_tx.setVisibility(View.VISIBLE);
//		} else {
//			number_tx.setVisibility(View.GONE);
//		}
//		number_tx.setText(size + "");
		defaultView.setTag(position);
		// 必须设置，否则在小米上面无法添加到地图里面
		defaultView.setLayoutParams(new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));
		return defaultView;
	}
}
