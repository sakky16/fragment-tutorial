package com.trisysit.epc_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.trisysit.epc_android.R;
import com.trisysit.epc_android.SqliteDatabase.ProjectTask;
import com.trisysit.epc_android.utils.AppUtils;
import com.trisysit.epc_android.utils.DataBaseManager;
import com.trisysit.epc_android.utils.SharedPrefHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TaskActivity extends DrawerScreenActivity {
    ListView task_lv;
    TaskListingAdapter adapter;
    private View rootView;
    private Toolbar toolbar;
    String projectId;
    SimpleDateFormat dateFormat;
    TextView titleTv;
    private List<ProjectTask> taskTables = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        getWidgets();
        dateFormat=new SimpleDateFormat("dd-MM-yyyy");
        setUpNavigationDrawer(TaskActivity.this, rootView, toolbar);
        getExtra();
        getData();
        setAdapter();
    }
    @Override
    protected void onResume() {
        super.onResume();
            if(AppUtils.getSyncDetails(getApplicationContext())){
                findViewById(R.id.snack_bar).setVisibility(View.VISIBLE);
            }
            else {
                findViewById(R.id.snack_bar).setVisibility(View.GONE);
        }

    }

    private void getWidgets() {
        task_lv = (ListView) findViewById(R.id.Task_list);
        rootView = findViewById(R.id.root);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        titleTv = (TextView) findViewById(R.id.toolbar_title);
        titleTv.setText(getString(R.string.task_listing));
    }

    private void getExtra() {
        projectId = getIntent().getStringExtra("projectId");
    }

    private void getData() {
        taskTables = DataBaseManager.getInstance().getL1Tasks(projectId);

    }
    private void setAdapter(){

        if(taskTables.size()>0) {
            adapter = new TaskListingAdapter();
            task_lv.setAdapter(adapter);
        }
    }

    private class TaskListingAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return taskTables.size();
        }

        @Override
        public Object getItem(int position) {
            return taskTables.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(TaskActivity.this);
                convertView = inflater.inflate(R.layout.task_listing_layout, parent, false);

            }
            final ProjectTask taskTable=taskTables.get(position);
            TextView task_subject=(TextView)convertView.findViewById(R.id.myTask_text);
            TextView task_start_date=(TextView)convertView.findViewById(R.id.task_start_date) ;
            ImageView edit_iv=(ImageView)convertView.findViewById(R.id.edit_iv);
            if(SharedPrefHelper.getInstance(TaskActivity.this).getFromSharedPrefs(SharedPrefHelper.ROLE_NAME).equalsIgnoreCase(AppUtils.ROLE_FIELD_MANAGER)){
                edit_iv.setVisibility(View.VISIBLE);
            }
            else {
                edit_iv.setVisibility(View.GONE);
            }
            if(taskTable.getSubject()!=null && taskTable.getLoa()!=null && taskTable.getUnit()!=null){
                task_subject.setText(taskTable.getSubject()+" ( "+taskTable.getLoa()+" "+taskTable.getUnit()+" )");
            }
            else {
                task_subject.setText(getString(R.string.no_subject));
            }
            if(taskTable.getSchedule()!=null){
                task_start_date.setText("Schedule : "+taskTable.getSchedule());
            }
            else {
                task_start_date.setText("Schedule: ");
            }
            edit_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(TaskActivity.this,TaskUpdateActivity.class);
                    intent.putExtra("schedule",taskTable.getSchedule());
                    intent.putExtra("parentL1Id",taskTable.getParentL1Id());
                    intent.putExtra("projectId",taskTable.getProjectId());
                    intent.putExtra("updateType",AppUtils.ACTIVITY_UPDATE_TYPE);
                    startActivity(intent);
                    finish();

                }
            });
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(taskTable.getSchedule()!=null){
                            Intent intent=new Intent(TaskActivity.this,SubActivityTaskActivity.class);
                            intent.putExtra("schedule",taskTable.getSchedule());
                            intent.putExtra("loa",taskTable.getLoa());
                            intent.putExtra("parentL1Id",taskTable.getParentL1Id());
                            startActivity(intent);

                    }
                }
            });

            return convertView;
        }
    }

}
