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
 * File: BaseActivity.java
 * Date：2014年1月3日
 * Version：v1.0
 */	
package com.opensource.videorecorder;

import java.io.Serializable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;

/**
 * 作用：
 * @author yinglovezhuzhu@gmail.com
 */
public class BaseActivity extends Activity {
	
	protected static final int RESULT_ERROR = 0x00000001;
	
	protected Intent mIntent;
	
	//=================  ====================
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mIntent = getIntent();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		mIntent = intent;
	}
	//=================  ====================
	
	
	
	//================= Intent start ====================
	protected boolean hasExtra(String name) {
		if(mIntent == null) {
			return false;
		}
		return mIntent.hasExtra(name);
	}
	
	protected String getStringExtra(String name) {
		if(mIntent == null) {
			return null;
		}
		return mIntent.getStringExtra(name);
	}
	
	protected int getIntExtra(String name) {
		if(mIntent == null) {
			return -1;
		}
		return mIntent.getIntExtra(name, -1);
	}
	
	protected boolean getBooleanExtra(String name) {
		if(mIntent == null) {
			return false;
		}
		return mIntent.getBooleanExtra(name, false);
	}
	
	protected Parcelable getParcelableExtra(String name) {
		if(mIntent == null) {
			return null;
		}
		return mIntent.getParcelableExtra(name);
	}
	
	protected Serializable getSerializableExtra(String name) {
		if(mIntent == null) {
			return null;
		}
		return mIntent.getSerializableExtra(name);
	}
	//======== Intent end =================
	
	//======== Shot toast =================
	protected void showShortToast(int resId) {
		Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
	}
	
	protected void showShortToast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}
	
	protected void showLongToast(int resId) {
		Toast.makeText(this, resId, Toast.LENGTH_LONG).show();
	}
	
	protected void showLongToast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}
	//======== Shot toast end =================
}
