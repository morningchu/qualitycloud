package com.pdfread.standardreader.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

public class ReadUtil {
	static ProgressDialog mydialog = null;
	public static void read(final Context context, final String path) {
		Intent intent = null;
		
//		FBReader.purchase = new PurchaseUtil();
//		
//		int locktime = 10000;
//		FBReader.locktime = locktime;
//		FBReader.bookID = "123456";
//		FBReader.userId = "";
//		FBReader.probationRate = 0;
//		
//		PdfActivity.bookPath = path;
//		File f = new File(PdfActivity.bookPath);
//		if (!f.exists() || !f.isFile()) {
//			new AlertDialog.Builder(context)
//					/** 设置标题 **/
//					.setTitle("提示")
//					/** 设置icon **/
//					.setIcon(android.R.drawable.ic_menu_more)
//					/** 设置内容 **/
//					.setMessage("打开出错，请删除后重新下载")
//					.setNegativeButton("确定",
//							new DialogInterface.OnClickListener() {
//								public void onClick(DialogInterface dialog,
//										int which) {
//									dialog.dismiss();
//								}
//							}).show();
//			return;
//		}
//
//		PdfActivity.bookTitle = "标准阅读";
//		
//		final Book book=new Book();
//		book.mBookID="123456";
//		book.mName="标准";
//
//		mydialog = ProgressDialog.show(context, "请稍等...", "正在加载...", true);
//		new Thread(new PDFThread(context)).start();
//		new Thread() {
//			public void run() {
//				long time = new Date().getTime();
//				BookDataManager.updateBookReadTime((Activity) context, book, time);
//			}
//		}.start();
		// 合并pdf文件
	}

}

class PDFThread implements Runnable {
	private Context mContext;

	public PDFThread(Context context) {
		mContext = context;
	}

	@Override
	public void run() {
//		Intent intent = new Intent(mContext, PdfActivity.class);
//		mContext.startActivity(intent);
//		ReadUtil.mydialog.dismiss();
	}

}
