package com.trisysit.epc_android.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.trisysit.epc_android.BuildConfig;
import com.trisysit.epc_android.R;
import com.trisysit.epc_android.EPCApplication;
import com.trisysit.epc_android.model.LoginDataModel;
import com.trisysit.epc_android.network.SyncApiClass;
import com.trisysit.epc_android.serverModel.LoginRequestModel;
import com.trisysit.epc_android.serverModel.LoginResponseModel;
import com.trisysit.epc_android.utils.AppUtils;
import com.trisysit.epc_android.utils.DataBaseManager;
import com.trisysit.epc_android.utils.NetworkUtils;
import com.trisysit.epc_android.utils.SharedPrefHelper;

import java.util.concurrent.TimeUnit;

import dmax.dialog.SpotsDialog;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserLoginActivity extends AppCompatActivity {

    private EditText usernameET, passwordET;
    private Button loginBT;
    private SharedPrefHelper sharedPrefHelper;
    private DataBaseManager dataBaseManager;
    private ProgressDialog progressDialog;
    private AlertDialog dialog;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        sharedPrefHelper = SharedPrefHelper.getInstance(EPCApplication.getContext());
        dataBaseManager = DataBaseManager.getInstance();
        getWIdgets();
        setListener();
    }

    private void setListener() {
        loginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (applyValidations()) {
                    if (AppUtils.isOnline(UserLoginActivity.this)) {
                        String encodedPassword = "";
                        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.USERNAME, usernameET.getText().toString());
                        String password = passwordET.getText().toString();
                        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.PASSWORD, password);
                        try {
                            byte[] encoded = password.getBytes("UTF-8");
                            encodedPassword = Base64.encodeToString(encoded, Base64.DEFAULT);
                            String password1 = encodedPassword.replace("\n", "");
                            sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.ENCODED_PASSWORD, password1);
                            callLoginApi(usernameET.getText().toString(), password1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(UserLoginActivity.this, getString(R.string.no_internet_msg), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        passwordET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                scrolUpScrollView();
            }
        });

    }

    private void callLoginApi(String userName, String encodedPassword) {
        LoginRequestModel model = new LoginRequestModel();
        model.setUsername(userName);
        model.setPassword(encodedPassword);
        HttpLoggingInterceptor interceptor = null;
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .connectTimeout(45, TimeUnit.SECONDS)
                .readTimeout(45, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            client.addInterceptor(interceptor);
        }
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkUtils.SERVER_URL)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SyncApiClass apiClass = retrofit.create(SyncApiClass.class);
        Call<LoginResponseModel> responseCall = apiClass.loginApi(model);
        showProgress();
        responseCall.enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equalsIgnoreCase("200")) {
                        Log.d("Userlogin", "loginSuccess");
                        LoginDataModel dataModel = response.body().getData();
                        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.TOKEN, dataModel.getToken());
                        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.NAME, dataModel.getFirstName() + " " + dataModel.getLastName());
                        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IS_FIRST_TIME_LOGIN, "true");
                        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.USER_ID, dataModel.getId());
                        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.ROLE_NAME, dataModel.getRoleName());
                        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.PUNCHED_IN, dataModel.getPunchedIn().toString());
                        sharedPrefHelper.saveInSharedPrefs(SharedPrefHelper.IS_USER_MULTIPLE_ATT,dataModel.getUserMultipleAttendance());


                        AppUtils.hideSoftKeyboard(UserLoginActivity.this);
                        if(dataModel.getRoleName().equalsIgnoreCase(AppUtils.ROLE_ATTENDANCE_ADMIN)){
                            Intent intent = new Intent(UserLoginActivity.this, AttendaneAdminRecordAttendanceActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Intent intent = new Intent(UserLoginActivity.this, RecordAttendanceActivity.class);
                            startActivity(intent);
                            finish();
                        }

                    }
                    Toast.makeText(UserLoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(UserLoginActivity.this, getString(R.string.server_connection_error_msg), Toast.LENGTH_SHORT).show();
                }
                dismissProgress();
            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                String error = "";
                if (call != null) {
                    error = call.toString();
                }
                dismissProgress();

                Log.i("Error in image upload", error);
            }
        });

    }

    private void showProgress() {
        dialog = new SpotsDialog(UserLoginActivity.this, getString(R.string.please_wait_msg), R.style.Custom);
        dialog.setCancelable(false);
        dialog.show();
    }

    public void dismissProgress() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }

    }

    private void scrolUpScrollView() {
        scrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                View lastChild = scrollView.getChildAt(scrollView.getChildCount() - 1);
                int bottom = lastChild.getBottom() + scrollView.getPaddingBottom();
                int sy = scrollView.getScrollY();
                int sh = scrollView.getHeight();
                int delta = bottom - (sy + sh);
                scrollView.smoothScrollBy(0, delta);

            }
        }, 200);
    }

    private boolean applyValidations() {
        String userName = usernameET.getText().toString();
        String password = passwordET.getText().toString();
        if (userName.equals("")) {
            Toast.makeText(UserLoginActivity.this, "Username should not be empty", Toast.LENGTH_LONG).show();
            return false;
        } else if (password.equals("")) {
            Toast.makeText(UserLoginActivity.this, "Password should not be empty", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    private void getWIdgets() {
        usernameET = (EditText) findViewById(R.id.username_et);
        passwordET = (EditText) findViewById(R.id.password_et);
        loginBT = (Button) findViewById(R.id.login_bt);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
    }
}