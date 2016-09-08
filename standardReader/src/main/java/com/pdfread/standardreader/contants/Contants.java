package com.pdfread.standardreader.contants;

import android.os.Environment;

public class Contants {
	
	// title高度
	public static final int TITLE_HEIGHT = 90;
	// title字体大小
	public static final int TITLE_SIZE = 32;
	// 返回按钮宽高
	public static final int BACKBTN_WIDTH = 49;
	public static final int BACKBTN_HEIGHT = 54;
	// 右侧按钮宽高
	public static final int RIGHTBTN_WIDTH = 98;
	public static final int RIGHTBTN_HEIGHT = 52;
	// 右侧字体大小
	public static final int RIGHT_TITLE_SIZE = 28;
	
	public static final int SRART_DWON = 2201;
	public static final int OVER_DWON = 2202;
	public static final int LOAD_DWON = 2203;
	
	public static final int IS_EXIECT=2205;
	
	public static final int PAUSE_DOWN = 2204;
	
	public static final String book_filter = "com.ndajia.app.action.info";
	
	//
	public static final String BASEURL = "http://cloud.gzqts.gov.cn/dfbz/";
	
	//单个分类列表
	public static final String SINGLE_CATAGORY="http://cloud.gzqts.gov.cn/dfbz/webStandard/index2Page.action?name=%s&&currentPage=%d&&num=%d";
	
	//首页
	public static final String FIRST_PAGE="http://cloud.gzqts.gov.cn/dfbz/webStandard/indexPage.action";
	
	//搜索
	public static final String STANDARD_SEARCH="http://cloud.gzqts.gov.cn/dfbz/webStandard/search.action?keyWords=%s&&currentPage=%d&&currentPage1=%d";

}
