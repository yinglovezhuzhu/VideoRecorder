/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Auther：yinglovezhuzhu@gmail.com
 * File: DateUtil.java
 * Date：2014年1月2日
 * Version：v1.0
 */	
package com.opensource.videorecorder.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 作用：时间操作工具类
 * @author yinglovezhuzhu@gmail.com
 */
public class DateUtil {
	private DateUtil() {}
	
	/**
	 * 获取系统当前日期
	 * @param pattern 日期格式
	 * @return
	 */
	public static String getSystemDate(String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
		return dateFormat.format(new Date(System.currentTimeMillis()));
	}
	
	/**
	 * 获取当前年份
	 * @return 当年年份
	 */
	public static int getYear() {
		Calendar c = Calendar.getInstance(Locale.getDefault());
		return c.get(Calendar.YEAR);
	}
	
	/**
	 * 获取日
	 * @param timeSecond 时间，精确到豪秒
	 * @return
	 */
	public static int getDay(long milliseconds) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("d", Locale.getDefault());
		String day = dateFormat.format(new Date(milliseconds));
		try {
			return Integer.valueOf(day);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * The seconds1 is the same day with seconds2.(精确到秒)
	 * @param seconds1
	 * @param seconds2
	 * @return
	 */
	public static boolean isSameDay(long seconds1, long seconds2) {
		return Math.abs(seconds1 - seconds2) < 86400;
	}
	
	/**
	 * 获取目前年份向前多个年份
	 * @param backStep 向前推移的年份数
	 * @param minYear 最小年份，如果向前推移小于这个最小年份，则到这个最小年份为止
	 * @return
	 */
	public static List<Integer> getYears(int backStep, int minYear) {
		List<Integer> years = new ArrayList<Integer>();
		int year = getYear();
		for (int i = 0; i < backStep; i++) {
			if(year - i < minYear) {
				break;
			}
			years.add(year - i);
		}
		return years;
	}
	
	/**
	 * 获取昨日日期
	 * @param pattern
	 * @return
	 */
	public static String getYesterdayDate(String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
		Calendar calendar = Calendar.getInstance(Locale.getDefault());
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
		return dateFormat.format(calendar.getTime());
	}
	
	/**
	 * 转换时间格式，从srcPattern格式转换成distPattern
	 * 说明：转换如果是有时间精度的变化，只支持高精度向低精度的转变，不支持低精度想高精度时间转变，否则获得的结果将会出错误。
	 * 例如，时间格式为“年月日时分秒”，能转换成任何一种比它精度低的格式如“月日时分”、“月日时分秒”，但是却无法把“月日时分”格式时间
	 * 转换成“年月日时分秒”等精度比它高的格式。
	 * @param source
	 * @param srcPattern
	 * @param distPattern
	 * @return 转换成功返回新格式的时间，如果转换发生异常则返回原来格式时间
	 */
	public static String changeFormat(String source, String srcPattern, String distPattern) {
		SimpleDateFormat srcFormt = new SimpleDateFormat(srcPattern, Locale.getDefault());
		SimpleDateFormat distFormt = new SimpleDateFormat(distPattern, Locale.getDefault());
		try {
			return distFormt.format(srcFormt.parse(source));
		} catch (ParseException e) {
			e.printStackTrace();
			return source;
		}
	}
	
	/**
	 * Format date.
	 * @param pattern
	 * @param milliseconds
	 * @return
	 */
	public static String format(String pattern, long milliseconds) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
		try {
			return dateFormat.format(new Date(milliseconds));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
