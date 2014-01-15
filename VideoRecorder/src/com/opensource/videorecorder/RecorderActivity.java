/*
 * Copyright (C) 2013年12月31日 The Android Open Source Project
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
 * FileName:RecorderActivity.java
 * Date：2013年12月31日
 * Version：v1.0
 */	
package com.opensource.videorecorder;

import java.io.File;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.opensource.videorecorder.utils.DateUtil;
import com.opensource.videorecorder.utils.LogUtil;
import com.opensource.videorecorder.utils.StringUtil;

/**
 * 作用：
 * 
 * @author yinglovezhuzhu@gmail.com
 */
public class RecorderActivity extends BaseActivity implements SurfaceHolder.Callback {
	
	private static final String TAG = RecorderActivity.class.getSimpleName();
	
	private SurfaceView mSurfaceView;
	private ImageButton mIbtnCancel;
	private ImageButton mIbtnOk;
	private Button mButton;
	private TextView mTvTimeCount;
	
	private SurfaceHolder mSurfaceHolder;
	private MediaRecorder mMediaRecorder;
	private Camera mCamera;
	
	private File mOutputFile;
	
	private boolean mIsRecording = false;
	

	private Resources mResources;
	private String mPackageName;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mResources = getResources();
		mPackageName = getPackageName();
		
		int layoutId = mResources.getIdentifier("yuninfo_activity_video_recorder", "layout", mPackageName);
		setContentView(layoutId);
		
		initView();
	}
	
	@SuppressWarnings("deprecation")
	private void initView() {
		
		mSurfaceView = (SurfaceView) findViewById(mResources.getIdentifier("yuninfo_sv_recorder_preview", "id", mPackageName));
		mButton = (Button) findViewById(mResources.getIdentifier("yuninfo_btn_video_record", "id", mPackageName));
		mIbtnCancel = (ImageButton) findViewById(mResources.getIdentifier("yuninfo_ibtn_video_cancel", "id", mPackageName));
		mIbtnOk = (ImageButton) findViewById(mResources.getIdentifier("yuninfo_ibtn_video_ok", "id", mPackageName));
		mIbtnCancel.setOnClickListener(mCancelListener);
		mIbtnOk.setOnClickListener(mOkListener);
		mIbtnCancel.setVisibility(View.INVISIBLE);
		mIbtnOk.setVisibility(View.INVISIBLE);
		mTvTimeCount = (TextView) findViewById(mResources.getIdentifier("yuninfo_tv_recorder_time_count", "id", mPackageName));
		mTvTimeCount.setVisibility(View.INVISIBLE);
		
		mButton.setBackgroundResource(mResources.getIdentifier("yuninfo_btn_video_start", "drawable", mPackageName));
		mButton.setOnClickListener(mBtnListener);
		
		mSurfaceHolder = mSurfaceView.getHolder();
		mSurfaceHolder.addCallback(this);
		
		if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			try {
				mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
			} catch (Exception e) {
				LogUtil.e(TAG, e);
			}
		}
		
	}
	
	
	private void exit(final int resultCode, final Intent data) {
		if(mIsRecording) {
			new AlertDialog.Builder(RecorderActivity.this)
			.setTitle("提示")
			.setMessage("正在录制视频，是否退出？")
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					stopRecord();
					if(mOutputFile != null && mOutputFile.exists()) {
						mOutputFile.delete();
						mOutputFile = null;
					}
					setResult(resultCode, data);
					finish();
				}
			})
			.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			}).show();
			return;
		}
		setResult(resultCode, data);
		finish();
	}
	
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Config.YUNINFO_ID_TIME_COUNT:
//				if(msg.arg1 > 1) {
//					mButton.setEnabled(true);
//				}
				if(mIsRecording) {
					if(msg.arg1 > msg.arg2) {
//					mTvTimeCount.setVisibility(View.INVISIBLE);
						mTvTimeCount.setText("00:00");
						stopRecord();
					} else {
						mTvTimeCount.setText("00:0" + (msg.arg2 - msg.arg1));
						Message msg2 = mHandler.obtainMessage(Config.YUNINFO_ID_TIME_COUNT, msg.arg1 + 1, msg.arg2);
						mHandler.sendMessageDelayed(msg2, 1000);
					}
				}
				break;

			default:
				break;
			}
		};
		
	};
	
	private View.OnClickListener mBtnListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(mIsRecording) {
				stopRecord();
			} else {
				startRecord();
			}
		}
	};
	
	private View.OnClickListener mCancelListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			exit(RESULT_CANCELED, null);
		}
	};
	
	private View.OnClickListener mOkListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent data = new Intent();
			if(mOutputFile != null && !StringUtil.isEmpty(mOutputFile.getAbsolutePath())) {
				data.putExtra(Config.YUNINFO_RESULT_DATA, mOutputFile);
			}
			exit(RESULT_OK, data);
		}
	};
	
	@SuppressLint("NewApi")
	private void openCamera() {
		//Open camera
		try {
			this.mCamera = Camera.open();
			Camera.Parameters parameters = mCamera.getParameters();
			parameters.setRotation(90);
			parameters.set("orientation", "portrait");
//			parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
			mCamera.setParameters(parameters);
			mCamera.lock();
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
				try {
					mCamera.setDisplayOrientation(90);
				} catch (NoSuchMethodError e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			LogUtil.e(TAG, "Open Camera error\n" + e);
		}
	}
	
	@SuppressLint("NewApi")
	private boolean initVideoRecorder() {

		mCamera.unlock();
		mMediaRecorder = new MediaRecorder();

		// Step 1: Unlock and set camera to MediaRecorder
		LogUtil.i("Camera", mCamera);
		LogUtil.i("Camera", mMediaRecorder);
		mMediaRecorder.setCamera(mCamera);
		// Step 2: Set sources
		mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

		try {
			mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			mMediaRecorder.setVideoSize(640, 480); // Its is not on android docs but
			// it needs to be done. (640x480
			// = VGA resolution)
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		// Step 4: Set output file
		mOutputFile = new File(Environment.getExternalStorageDirectory(), "Video_" 
				+ DateUtil.getSystemDate("yyyy_MM_dd_HHmmss") + ".mp4");
		mMediaRecorder.setOutputFile(mOutputFile.getAbsolutePath());

		// Step 5: Set the preview output
		mMediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());
		
//		mMediaRecorder.setVideoFrameRate(20);
		try {
			mMediaRecorder.setMaxDuration(Config.YUNINFO_MAX_VIDEO_DURATION);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			try {
				mMediaRecorder.setOrientationHint(90);
			} catch (NoSuchMethodError e) {
				e.printStackTrace();
			}
		}

		// Step 6: Prepare configured MediaRecorder
		try {
			mMediaRecorder.prepare();
		} catch (IllegalStateException e) {
			Log.d("VideoPreview", "IllegalStateException preparing MediaRecorder: "	+ e.getMessage());
			releaseMediaRecorder();
			return false;
		} catch (IOException e) {
			Log.d("VideoPreview", "IOException preparing MediaRecorder: " + e.getMessage());
			releaseMediaRecorder();
			return false;
		}
		return true;
	}
	
	private void releaseMediaRecorder() {
		if (mMediaRecorder != null) {
			mMediaRecorder.reset(); // clear recorder configuration
			mMediaRecorder.release(); // release the recorder object
			mMediaRecorder = null;
			mCamera.lock(); // lock camera for later use
		}
	}
	
	private void releaseCamera() {
		if (mCamera != null) {
			mCamera.release(); // release the camera for other applications
			mCamera = null;
		}
	}
	
	private void startRecord() {
		try {
			// initialize video camera
			if (initVideoRecorder()) {
				// Camera is available and unlocked, MediaRecorder is prepared,
				// now you can start recording
				mMediaRecorder.start();
				mButton.setBackgroundResource(mResources.getIdentifier("yuninfo_btn_video_stop", "drawable", mPackageName));
//				mButton.setEnabled(false);
			} else {
				// prepare didn't work, release the camera
				releaseMediaRecorder();
				// inform user
				mButton.setBackgroundResource(mResources.getIdentifier("yuninfo_btn_video_start", "drawable", mPackageName));
			}
			mTvTimeCount.setVisibility(View.VISIBLE);
			mTvTimeCount.setText("00:0" + (Config.YUNINFO_MAX_VIDEO_DURATION / 1000));
			Message msg = mHandler.obtainMessage(Config.YUNINFO_ID_TIME_COUNT, 1, Config.YUNINFO_MAX_VIDEO_DURATION / 1000);
			mHandler.sendMessage(msg);
			mIsRecording = true;
		} catch (Exception e) {
			showShortToast("该操作系统不支持此功能");
			e.printStackTrace();
			exit(RESULT_ERROR, null);
		}
	}
	

	private void stopRecord() {
		// stop recording and release camera
		try {
			mMediaRecorder.stop(); // stop the recording
		} catch (Exception e) {
			if(mOutputFile != null && mOutputFile.exists()) {
				mOutputFile.delete();
				mOutputFile = null;
			}
			LogUtil.e(TAG, e.toString());
		}
		releaseMediaRecorder(); // release the MediaRecorder object
		mCamera.lock(); // take camera access back from MediaRecorder
//		releaseCamera(); // release camera
		mButton.setBackgroundResource(mResources.getIdentifier("yuninfo_btn_video_start", "drawable", mPackageName));
		mIsRecording = false;
		
		mButton.setVisibility(View.GONE);
		mIbtnCancel.setVisibility(View.VISIBLE);
		mIbtnOk.setVisibility(View.VISIBLE);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		openCamera();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		releaseCamera();
	}


	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if(mCamera != null) {
			try {
				mCamera.setPreviewDisplay(holder);
				mCamera.startPreview();
			} catch (Exception e) {
				LogUtil.e(TAG, "Error setting camera preview: " + e.toString());
			}
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		if (mCamera != null) {
			try {
				mCamera.stopPreview();
			} catch (Exception e) {
				LogUtil.e(TAG, "Error setting camera preview: " + e.toString());
			}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			exit(RESULT_CANCELED, null);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
