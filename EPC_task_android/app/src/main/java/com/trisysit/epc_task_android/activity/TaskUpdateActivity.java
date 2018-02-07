package com.trisysit.epc_task_android.activity;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.trisysit.epc_task_android.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TaskUpdateActivity extends DrawerActivity {
    private View rootView;
    private DatePickerDialog.OnDateSetListener date_dp;
    private Toolbar toolbar;
    private ImageView date_picker_iv;
    private String update_date;
    List<String> list=new ArrayList<>();
    private SimpleDateFormat dateFormat;
    private TextView titleTv,no_task_msg,task_list_tv;
    private ListView task_list;
    Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_update);
        getWidgets();
        list.add("1");
        list.add("1");
        list.add("1");
        dateFormat = new SimpleDateFormat("MMM dd ,yyyy");
        setUpNavigationDrawer(this,rootView,toolbar);
        setDatePicker();
        setListener();
        TaskListingAdapter adapter=new TaskListingAdapter();
        task_list.setAdapter(adapter);


    }
    private void setDatePicker() {
        calendar = Calendar.getInstance();
        date_dp = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }
        };
    }
    private void updateDate() {
        update_date = dateFormat.format(calendar.getTime());
        task_list_tv.setText("Task list for "+update_date);
    }
    private void getWidgets(){
        rootView = findViewById(R.id.root);
        toolbar = (Toolbar) findViewById(R.id.toolbar_setting);
        titleTv = (TextView) findViewById(R.id.toolbar_title);
        titleTv.setText(getString(R.string.task));
        task_list=(ListView)findViewById(R.id.task_list);
        task_list_tv=(TextView)findViewById(R.id.task_list_tv);
        date_picker_iv=(ImageView)findViewById(R.id.date_picker_iv);
        no_task_msg=(TextView)findViewById(R.id.no_task_msg);
    }
    private void setListener(){
        date_picker_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(TaskUpdateActivity.this,date_dp,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
    }
    private class TaskListingAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                LayoutInflater inflater=LayoutInflater.from(TaskUpdateActivity.this);
                convertView=inflater.inflate(R.layout.task_listing_layout,parent,false);
            }
            final SwitchCompat switchCompat=(SwitchCompat)convertView.findViewById(R.id.switchButton);
            switchCompat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SwitchCompat switchCompat1=(SwitchCompat)v;
                    if(switchCompat1.isChecked()){
                        Toast.makeText(TaskUpdateActivity.this,"checked at "+position,Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(TaskUpdateActivity.this,"Un checked at "+position,Toast.LENGTH_SHORT).show();
                    }
                }
            });
            return convertView;
        }
    }
}
