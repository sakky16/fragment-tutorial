package com.trisysit.epc_android.utils;

import android.content.Context;
import android.os.AsyncTask;

import com.trisysit.epc_android.BuildConfig;
import com.trisysit.epc_android.SqliteDatabase.DatabaaseUtils;
import com.trisysit.epc_android.model.AttendanceModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tejeshkumardalai on 8/14/17.
 */

public class NetworkUtils {

    public static final String HTTP_OK = "200";

    public static final String HTTP_ERROR = "500";

    public static final String HTTP_PERMISSION_DENIED = "101";

    public static final String HTTP_BAD_REQUEST = "400";

    public static final String HTTP_PAGE_NOT_FOUND = "404";

    public static final String HTTP_UNSUPPORTED_CONTENT_TYPE = "415";

    public static final String HTTP_REQUEST_TIMEOUT = "408";

    public static final String HTTP_FORBIDDEN = "403";

    public static final String HTTP_NO_DATA = "204";

    public static final String HTTP_UPGRADE_REQUIRED = "426";

    public static final String HTTP_SERVER_UNAVAILABLE = "503";

    public static final String HTTP_INVALID_CREDENTIALS = "401";

    public static final String HTTP_NO_REQUEST_METHOD_FOUND = "501";

    public static final String HTTP_MALFORMED_URL = "1001";

    //LOCAL SERVER
    //public static final String SERVER_URL = "http://192.168.1.34:8081/epcnew/";

    //UAT SERVER
    //public static final String SERVER_URL = "http://106.51.126.221:4545/epcnew/";
    //Production Server
    //public static final String SERVER_URL="http://epc.trisysit.com/";

    public static final String SERVER_URL = BuildConfig.SERVER_API_URL;

    public static final String IMAGE_SYNC = "services/api/v0/sync/syncImage";

    public static final String LOGIN = "services/api/v0/user/login";

    public static final String  CHANGE_PASSWORD="services/api/v0/user/changePassword";

    public static final String GET_USER_DETAILS="services/api/v0/user/getUserDetails";

    public static final String LOGOUT = "services/api/v0/user/logout";

    public static final String SYNC_ATTENDANCE = "services/api/v0/sync/syncAttendance";

    public static final String SYNC_TASK = "services/api/v0/sync/syncTasks";

    public static final String UAT_IMAGE_PATH = "http://106.51.126.221:4545/epcnew/image/images";

    public static final String IMAGE_PATH=BuildConfig.IMAGE_PATH;

    public static final String PROD_IMAGE_PATH="http://epc.trisysit.com/image/images";

}
