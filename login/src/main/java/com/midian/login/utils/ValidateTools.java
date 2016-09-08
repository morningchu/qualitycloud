package com.midian.login.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;

/**
 * 验证类，提供对邮箱和电话等的验证
 * 
 * @author leeib
 * 
 */
public class ValidateTools {
	/**
	 * 判断字符串是否为null或空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmptyString(String str) {
		boolean flag = false;

		if (str == null || "".equals(str)) {
			flag = true;
		}

		return flag;
	}

	/**
	 * 判断日期是否为今天，支持yyyyMMdd、yyyy-MM-dd、yyyy/MM/dd三种格式
	 * 
	 * @param date
	 * @return
	 */

	@SuppressLint("SimpleDateFormat")
	public static boolean isToday(String date) {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format3 = new SimpleDateFormat("yyyy/MM/dd");
		Date today = new Date();
		String todayStr1 = format1.format(today);
		String todayStr2 = format2.format(today);
		String todayStr3 = format3.format(today);
		return todayStr1.equals(date) || todayStr2.equals(date) || todayStr3.equals(date);
	}

	/**
	 * 验证邮箱
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {

		boolean tag = true;
		final String pattern1 = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		final Pattern pattern = Pattern.compile(pattern1);
		final Matcher mat = pattern.matcher(email);
		if (!mat.find()) {
			tag = false;
		}
		return tag;
	}

	/**
	 * 验证号码 手机�?固话均可
	 */
	public static boolean isPhoneNumber(String phoneNumber) {
		boolean isValid = false;

		String expression = "((^(13|14|15|17|18)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";
		CharSequence inputStr = phoneNumber;
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}

	/**
	 * 验证是否为中文
	 * 
	 * @param chinese
	 *            �?��验证的中文字符串
	 * @param count
	 *            中文字符串的个数，若传0，表示不判断个数
	 * @return
	 */
	public static boolean isChinese(String chinese, int count) {
		boolean isValid = false;

		String expression = "[\u3400-\uFA29]{" + count + "}";
		if (count <= 0) {
			expression = "[\u3400-\uFA29]+";
		}
		CharSequence inputStr = chinese;

		Pattern pattern = Pattern.compile(expression);

		Matcher matcher = pattern.matcher(inputStr);

		if (matcher.matches()) {
			isValid = true;
		}

		return isValid;

	}

	/**
	 * 验证是否为中英文
	 * 
	 * @param chineseAndLetter
	 *            �?��验证的中英文字符�?
	 * @param count
	 *            中英文字符串的个数，若传0，表示不判断个数
	 * @return
	 */
	public static boolean isChineseAndLetter(String chineseAndLetter, int count) {
		boolean isValid = false;

		String expression = "[a-zA-Z_\u3400-\uFA29]{" + count + "}";
		if (count <= 0) {
			expression = "[a-zA-Z_\u3400-\uFA29]+";
		}
		CharSequence inputStr = chineseAndLetter;

		Pattern pattern = Pattern.compile(expression);

		Matcher matcher = pattern.matcher(inputStr);

		if (matcher.matches()) {
			isValid = true;
		}

		return isValid;

	}

	/**
	 * 验证是否为数字
	 * 
	 * @param number
	 *            �?��验证的数字字符串
	 * @param count
	 *            数字的位数，若传0，表示不判断个数
	 * @return
	 */
	public static boolean isDigit(String number, int count) {
		boolean isValid = false;
		if (number == null) {
			return isValid;
		}

		String expression = "[0-9]{" + count + "}";
		if (count <= 0) {
			expression = "[0-9]+";
		}
		CharSequence inputStr = number;

		Pattern pattern = Pattern.compile(expression);

		Matcher matcher = pattern.matcher(inputStr);

		if (matcher.matches()) {
			isValid = true;
		}

		return isValid;

	}

	/**
	 * 验证是否为数字或小数
	 * 
	 * @param number
	 *            �?��验证的数字字符串
	 * @param count
	 *            数字的位数，若传0，表示不判断个数
	 * @return
	 */
	public static boolean isDecimal(String str) {
		boolean isValid = false;
		if (str == null) {
			return isValid;
		}

		String expression = "[\\-0-9\\.]+";
		CharSequence inputStr = str;

		Pattern pattern = Pattern.compile(expression);

		Matcher matcher = pattern.matcher(inputStr);

		if (matcher.matches()) {
			isValid = true;
		}

		return isValid;

	}

	/**
	 * 验证是否为英文字母
	 * 
	 * @param letter
	 *            �?��验证的字母字符串
	 * @param count
	 *            字母字符串的个数，表示不判断个数
	 * @return
	 */
	public static boolean isLetter(String letter, int count) {
		boolean isValid = false;

		String expression = "[a-zA-Z]{" + count + "}";
		if (count <= 0) {
			expression = "[a-zA-Z]+";
		}
		CharSequence inputStr = letter;

		Pattern pattern = Pattern.compile(expression);

		Matcher matcher = pattern.matcher(inputStr);

		if (matcher.matches()) {
			isValid = true;
		}

		return isValid;

	}

	/**
	 * 验证是否为数字
	 * 
	 * @param letterAndDigit
	 *            �?��验证的英数字符串
	 * @param count
	 *            英数 字符串的位数，若传0，表示不判断个数
	 * @return
	 */
	public static boolean isLetterAndDigit(String letterAndDigit, int count) {
		boolean isValid = false;

		String expression = "[a-zA-Z_0-9]{" + count + "}";
		if (count <= 0) {
			expression = "[a-zA-Z_0-9]+";
		}
		CharSequence inputStr = letterAndDigit;

		Pattern pattern = Pattern.compile(expression);

		Matcher matcher = pattern.matcher(inputStr);

		if (matcher.matches()) {
			isValid = true;
		}

		return isValid;

	}

}
