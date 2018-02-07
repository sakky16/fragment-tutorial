package com.trisysit.epc_android.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.trisysit.epc_android.EPCApplication;
import com.trisysit.epc_android.R;
import com.trisysit.epc_android.serverModel.ChangePwrdRequestModel;
import com.trisysit.epc_android.utils.AppUtils;
import com.trisysit.epc_android.utils.SharedPrefHelper;
import com.trisysit.epc_android.utils.SyncManager;

import dmax.dialog.SpotsDialog;

public class ChangePasswordActivity extends DrawerScreenActivity {
    private View rootView;
    private Toolbar toolbar;
    TextView titleTv;
    private ScrollView scrollView;
    private Button change_passwod_btn;
    private EditText old_password_et, new_password_et, confirm_password_et;
    private AlertDialog dialog;
    private SharedPrefHelper sharedPrefHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        getWidgets();
        sharedPrefHelper = SharedPrefHelper.getInstance(EPCApplication.getContext());
        if(sharedPrefHelper.getFromSharedPrefs(SharedPrefHelper.ROLE_NAME).equalsIgnoreCase(AppUtils.ROLE_ATTENDANCE_ADMIN)){
            setUpNavigationDrawerForAttendAdmin(ChangePasswordActivity.this, rootView, toolbar);
        }
        else {
            setUpNavigationDrawer(ChangePasswordActivity.this, rootView, toolbar);
        }

        setListener();
    }
    @Override
    protected void onResume() {
        super.onResume();
            if (AppUtils.getSyncDetails(getApplicationContext())) {
                findViewById(R.id.snack_bar).setVisibility(View.VISIBLE);
            } else {
                findViewById(R.id.snack_bar).setVisibility(View.GONE);
            }
    }

    private void getWidgets() {
        rootView = findViewById(R.id.rootlayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar_report);
        titleTv = (TextView) findViewById(R.id.toolbar_title);
        titleTv.setText(getString(R.string.change_password));
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        old_password_et = (EditText) findViewById(R.id.old_password_et);
        new_password_et = (EditText) findViewById(R.id.new_password_et);
        confirm_password_et = (EditText) findViewById(R.id.confirm_password_et);
        change_passwod_btn = (Button) findViewById(R.id.change_password_btn);
    }

    private void setListener() {
        new_password_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
          scrolUpScrollView();
                return false;
            }
        });
        confirm_password_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrolUpScrollView();
            }
        });
        confirm_password_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                scrolUpScrollView();
            }
        });
        change_passwod_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppUtils.isOnline(ChangePasswordActivity.this)) {
                    String old_password = old_password_et.getText().toString();
                    String new_password = new_password_et.getText().toString();
                    String confirm_password = confirm_password_et.getText().toString();
                    if (AppUtils.isEmpty(old_password)) {
                        Toast.makeText(ChangePasswordActivity.this, "please enter Old password", Toast.LENGTH_LONG).show();
                    } else if (AppUtils.isEmpty(new_password)) {
                        Toast.makeText(ChangePasswordActivity.this, "please enter new password", Toast.LENGTH_LONG).show();
                    } else if (AppUtils.isEmpty(confirm_password)) {
                        Toast.makeText(ChangePasswordActivity.this, "please enter Confirm new password", Toast.LENGTH_LONG).show();
                    } else {
                        String original_old_password = SharedPrefHelper.getInstance(ChangePasswordActivity.this).getFromSharedPrefs(SharedPrefHelper.PASSWORD);
                        if (!original_old_password.equalsIgnoreCase(old_password)) {
                            Toast.makeText(ChangePasswordActivity.this, "please enter old password correctly", Toast.LENGTH_LONG).show();
                        } else if (!new_password.equalsIgnoreCase(confirm_password)) {
                            Toast.makeText(ChangePasswordActivity.this, "Password/Confirm Password should be same", Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                String encodedPassword = "";
                                byte[] encoded = new_password.getBytes("UTF-8");
                                encodedPassword = Base64.encodeToString(encoded, Base64.DEFAULT);
                                String encoded_new_pwrd = encodedPassword.replace("\n", "");
                                ChangePwrdRequestModel model = new ChangePwrdRequestModel();
                                model.setPassword(SharedPrefHelper.getInstance(ChangePasswordActivity.this).getFromSharedPrefs(SharedPrefHelper.ENCODED_PASSWORD));
                                model.setNewPassword(encoded_new_pwrd);
                                AppUtils.hideSoftKeyboard(ChangePasswordActivity.this);
                                showProgress();
                                SyncManager manager = new SyncManager(ChangePasswordActivity.this);
                                manager.syncChangePassword(model);
                                old_password_et.setText("");
                                new_password_et.setText("");
                                confirm_password_et.setText("");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
                else {
                    Toast.makeText(ChangePasswordActivity.this,getString(R.string.no_internet_msg),Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    public void showProgress(){
        dialog=new SpotsDialog(ChangePasswordActivity.this,getString(R.string.please_wait_msg),R.style.Custom);
        dialog.setCancelable(false);
        dialog.show();
    }
    public void dismissProgress(String successMessage){
        if(dialog!=null){
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
        Toast.makeText(ChangePasswordActivity.this,successMessage,Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(ChangePasswordActivity.this,RecordAttendanceActivity.class);
        startActivity(intent);
        finish();


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

}
