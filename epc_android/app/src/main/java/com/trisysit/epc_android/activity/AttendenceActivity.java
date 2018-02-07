package com.trisysit.epc_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.trisysit.epc_android.R;
import com.trisysit.epc_android.SqliteDatabase.DatabaaseUtils;
import com.trisysit.epc_android.model.AttendanceModel;
import com.trisysit.epc_android.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class AttendenceActivity extends DrawerScreenActivity {

    private View rootView;
    private Toolbar toolbar;
    private ListView attendance_lv;
    private TextView titleTv,no_attendance_msg;
    private List<AttendanceModel> attedndenceList = new ArrayList<AttendanceModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        setData();
        getWidgets();
        setAdapter();
        setSupportActionBar(toolbar);
        setUpNavigationDrawer(this, rootView, toolbar);
        findViewById(R.id.root).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });

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
        rootView = findViewById(R.id.root);
        toolbar = (Toolbar) findViewById(R.id.toolbar_setting);
        titleTv = (TextView) findViewById(R.id.toolbar_title);
        titleTv.setText(getString(R.string.attendence));
        attendance_lv = (ListView) findViewById(R.id.myTask_list);
        no_attendance_msg=(TextView)findViewById(R.id.no_attendance_msg);

    }

    private void setData() {
        attedndenceList = DatabaaseUtils.getAttendanceList(AttendenceActivity.this);

    }

    private void setAdapter() {
        if(attedndenceList.size()>0){
            attendance_lv.setVisibility(View.VISIBLE);
            no_attendance_msg.setVisibility(View.GONE);
            AttendenceAdapter attendenceAdapter = new AttendenceAdapter();
            attendance_lv.setAdapter(attendenceAdapter);
        }
        else {
            attendance_lv.setVisibility(View.GONE);
            no_attendance_msg.setVisibility(View.VISIBLE);
        }



    }

    private class AttendenceAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return attedndenceList.size();
        }

        @Override
        public Object getItem(int position) {
            return attedndenceList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(AttendenceActivity.this);
                convertView = inflater.inflate(R.layout.attendence_list_layout, parent, false);
            }
            TextView attendance_date_tv = (TextView) convertView.findViewById(R.id.attendence_date);
            TextView punch_in_tv = (TextView) convertView.findViewById(R.id.punch_in);
            TextView punch_out_tv=(TextView)convertView.findViewById(R.id.punch_out);
            final AttendanceModel attendanceTable = attedndenceList.get(position);
            String outTime="";
            if (attendanceTable.getDate() != null) {
                String attendaceTime = AppUtils.getFormatedDateForAttendance(attendanceTable.getDate());
                String inTime = attendanceTable.getInTime();
                if(attendanceTable.getOutTime()!=null && !attendanceTable.getOutTime().equalsIgnoreCase("null")){
                    outTime = attendanceTable.getOutTime();
                }
                attendance_date_tv.setText("Date: " + attendaceTime);
                punch_in_tv.setText("Punch In: " + inTime);
                punch_out_tv.setText("Punch Out: "+outTime);
            }


            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AttendenceActivity.this, AttendanceDetailsActivity.class);
                    intent.putExtra("mobileID", attendanceTable.getMobileId());
                    startActivity(intent);
                }
            });


            return convertView;
        }
    }


}




