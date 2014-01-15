/*
 * Copyright (C) 2014年1月1日 The Android Open Source Project
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
 * FileName:LogUtil.java
 * Date：2014年1月1日
 * Version：v1.0
 */	
package com.opensource.videorecorder.utils;

import android.util.Log;

/**
 * 作用：Log print util class.
 * 
 * @author yinglovezhuzhu@gmail.com
 */
public class LogUtil {
	private static final boolean DEBUG_MODE = true;

	private LogUtil() {}
	
	public static void i(String tag, String msg) {
		if(DEBUG_MODE) {
			Log.i(tag, msg);
		}
	}
	
	public static void i(String tag, Object msg) {
		if(DEBUG_MODE) {
			Log.i(tag, msg.toString());
		}
	}
	
	public static void w(String tag, String msg) {
		if(DEBUG_MODE) {
			Log.w(tag, msg);
		}
	}
	
	public static void w(String tag, Object msg) {
		if(DEBUG_MODE) {
			Log.w(tag, msg.toString());
		}
	}
	
	public static void e(String tag, String msg) {
		if(DEBUG_MODE) {
			Log.e(tag, msg);
		}
	}
	
	public static void e(String tag, Object msg) {
		if(DEBUG_MODE) {
			Log.e(tag, msg.toString());
		}
	}
	
	public static void d(String tag, String msg) {
		if(DEBUG_MODE) {
			Log.d(tag, msg);
		}
	}
	
	public static void d(String tag, Object msg) {
		if(DEBUG_MODE) {
			Log.d(tag, msg.toString());
		}
	}
	
	public static void v(String tag, String msg) {
		if(DEBUG_MODE) {
			Log.e(tag, msg);
		}
	}
	
	public static void v(String tag, Object msg) {
		if(DEBUG_MODE) {
			Log.e(tag, msg.toString());
		}
	}
}
