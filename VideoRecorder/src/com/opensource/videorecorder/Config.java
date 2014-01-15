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
 * File: Config.java
 * Date：2014年1月2日
 * Version：v1.0
 */	
package com.opensource.videorecorder;


/**
 * 作用：配置文件
 * @author yinglovezhuzhu@gmail.com
 */
public class Config {

	public static final int YUNINFO_REQUEST_CODE_RECORD_VIDEO = 0x100;
	
	public static final int YUNINFO_REQUEST_CODE_PICK_CONTACT = 0x101;
	
	public static final int YUNINFO_REQUEST_CODE_PLAY_VIDEO = 0x102;

	public static final int YUNINFO_REQUEST_CODE_PICK_VIDEO = 0x103;
	
	public static final String YUNINFO_RESULT_DATA = "yuninfo_result_data";
	
	public static final String YUNINFO_EXTRA_URL = "yuninfo_url";
	
	public static final String YUNINFO_VIDEO_UPLOAD_URL = "http://42.120.19.149/videodemo/uploadFile/upLoadFile.php";
	
	public static final String YUNINFO_SEND_VIDEO_URL = "http://182.140.234.47/video/sendVideoMsg.do";
	
	public static final String YUNINFO_READ_VIDEO_URL = "http://182.140.234.47/video/acceptVideoMsg.do";
	
	public static final String YUNINFO_REQUEST_FORMAT = "json";
	
	public static final String YUNINFO_REQUEST_VERSION = "v1.0";
	
	public static final String YUNINFO_REQUEST_CLIENT_TYPE = "4";
	
	public static final int YUNINFO_MAX_VIDEO_DURATION = 8 * 1000;
	
	/************************* IDs Start *******************************/
	public static final int YUNINFO_ID_TASK_STARTED = 0x1001;
	public static final int YUNINFO_ID_TASK_CANCELED = 0x1002;
	public static final int YUNINFO_ID_TASK_PROGRESS = 0x1003;
	public static final int YUNINFO_ID_TASK_SUCCESSED = 0x1004;
	public static final int YUNINFO_ID_TASK_FAILED = 0x1005;
	
	public static final int YUNINFO_ID_TIME_COUNT = 0x1006;
	/************************* IDs End *************************/
	
}
