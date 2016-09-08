package com.pdfread.standardreader.net;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

public class GetTimeUtil {

	private int mONTH;
	private int dAY_OF_MONTH;

	public GetTimeUtil() {
		Calendar mRightNow = Calendar.getInstance();
		mONTH = mRightNow.get(Calendar.MONTH);
		dAY_OF_MONTH = mRightNow.get(Calendar.DAY_OF_MONTH);
	}

	public int getBetweenDayNumber(String dateA) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String systemTime = sdf.format(new Date()).toString();
		if (dateA.equals("0")) {
			dateA = systemTime;
		}

		long dayNumber = 0;
		long DAY = 24L * 60L * 60L * 1000L;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			java.util.Date d1 = df.parse(dateA);
			java.util.Date d2 = df.parse(systemTime);
			dayNumber = (d2.getTime() - d1.getTime()) / DAY;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (int) dayNumber;
	}
	  public  int getBetweenDayNumber(String dateA, String dateB) {
	      long dayNumber = 0;
	      long DAY = 24L * 60L * 60L * 1000L;
	      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	      try {
	       java.util.Date d1 = df.parse(dateA);
	       java.util.Date d2 = df.parse(dateB);
	       dayNumber = (d2.getTime() - d1.getTime()) / DAY;
	      } catch (Exception e) {
	       e.printStackTrace();
	      }
	      return (int) dayNumber;
	     }
	public int getNumber(String s, int type) {
		DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = dateformat.parse(s);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DateFormat dateformat1 = null;
		if (type == 0) {
			dateformat1 = new SimpleDateFormat("dd");
		} else if (type == 1) {
			dateformat1 = new SimpleDateFormat("MM");
		} else {
			dateformat1 = new SimpleDateFormat("yyyy");
		}

		String name = dateformat1.format(date);
		return Integer.parseInt(name);
	}

	public String getDay(String dayStr, int f) {
		// String dayStr = "2013-7-1";
		try {
			// 一天的毫秒
			long dayTime = 24 * 60 * 60 * 1000;

			// 日期格式
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			// 取得设定时间的毫秒数
			long time = dateformat.parse(dayStr).getTime();
			// 增加100天的毫秒
			time += dayTime * (280 + f);

			// 再格式化回yyyy-MM-dd格式
			calendar.setTimeInMillis(time);
			dayStr = dateformat.format(calendar.getTime());

			System.out.println(dayStr);

		} catch (Exception e) {

		}
		return dayStr;
	}
	
	public String getDayForTime(String dayStr, int f) {
		// String dayStr = "2013-7-1";
		try {
			//一天的毫秒
			long dayTime = 24 * 60 * 60 * 1000;

			// 日期格式
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			// 取得设定时间的毫秒数
			long time = dateformat.parse(dayStr).getTime();
			// 增加100天的毫秒
			time += dayTime * (f);

			// 再格式化回yyyy-MM-dd格式
			calendar.setTimeInMillis(time);
			dayStr = dateformat.format(calendar.getTime());

			System.out.println(dayStr);

		} catch (Exception e) {

		}
		return dayStr;
	}
	public int []getBIRDay(String dayStr,int day) {
		// String dayStr = "2013-7-1";
		int days[]=new int[3];
		int	ssday =0;
		try {
			//一天的毫秒
			long dayTime = 24 * 60 * 60 * 1000;
			// 开始时间

			// 日期格式
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			// 取得设定时间的毫秒数
			long time = dateformat.parse(dayStr).getTime();
			// 增加100天的毫秒
			time += dayTime * (day);

			// 再格式化回yyyy-MM-dd格式
			calendar.setTimeInMillis(time);
			
			ssday = calendar.get(Calendar.DAY_OF_MONTH);
			int ssyear=calendar.get(Calendar.YEAR);
			
			int ssmouth=calendar.get(Calendar.MONTH)+1;
			days[0]=ssmouth;
			days[1]=ssday;
			days[2]=ssyear;

		} catch (Exception e) {

		}
		return days;
	}
	public int[] getOvulatorDay(int month, int Tday, int f) {

		int[] times = new int[2];
		int day = dAY_OF_MONTH;
		mONTH = month;
		if (Tday == dAY_OF_MONTH) {
			day = dAY_OF_MONTH + f;
		} else {
			day = Tday + f;
		}

		int max = getLastDayOfMonth(mONTH + 1);
		if (day > max) {
			times[0] = mONTH + 1;
			times[1] = day - max;
		} else {
			times[0] = mONTH ;
			times[1] = day;
		}

		return times;
	}

	public int getLastDayOfMonth(int offset) {

		Date fiducialDate = new Date(System.currentTimeMillis());
		Calendar cal = Calendar.getInstance();
		cal.setTime(fiducialDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		fiducialDate = cal.getTime();

		cal.setTime(fiducialDate);
		cal.add(Calendar.MONTH, offset + 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);

		Date d = cal.getTime();
		DateFormat f = new SimpleDateFormat("dd");
		String day = f.format(d);

		return Integer.parseInt(day);
	}
	
	public String ChageTime(String time, int changeZ) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar gc = new GregorianCalendar();
		Date d = null;
		try {
			d = sf.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gc.setTime(d);
		gc.add(3, +changeZ);

		

		gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH),
				gc.get(Calendar.DATE));
		System.out.println(sf.format(gc.getTime()));
		return sf.format(gc.getTime());
	}
	public String getStringDataFromData(java.util.Date date){
		if(date!=null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String systemTime = sdf.format(date);
			return systemTime;
		}
		return null;
	}
	
	public static String getStringTime(String time) {
		String re_StrTime = null;
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		// 例如：cc_time=1291778220
		long lcc_time = Long.valueOf(time);
		re_StrTime = sdf.format(new Date(lcc_time * 1000L));
		return re_StrTime;
	}
	public String getAudioTime(String path, Context mContext) {
		String time = "";
		MediaPlayer mp = MediaPlayer.create(mContext, Uri.parse(path));
		if(null!=mp){
			int duration = mp.getDuration();//
			 time=duration/1000+"''";
			
				mp.release();
				mp=null;
		}
	
		return time;

	}

	public static String getStringToTime(String cc_time) {
		String re_StrTime = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日HH:mm");
		// 例如：cc_time=1291778220
		long lcc_time = Long.valueOf(cc_time);
		re_StrTime = sdf.format(new Date(lcc_time * 1000L));
		return re_StrTime;
	}
	public static String getStringDate(String time) {
		String re_StrTime = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		// 例如：cc_time=1291778220
		long lcc_time = Long.valueOf(time);
		re_StrTime = sdf.format(new Date(lcc_time * 1000L));
		return re_StrTime;
	}
}
