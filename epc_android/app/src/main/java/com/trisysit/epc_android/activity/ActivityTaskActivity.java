package com.trisysit.epc_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.trisysit.epc_android.R;
import com.trisysit.epc_android.SqliteDatabase.ProjectTask;
import com.trisysit.epc_android.utils.AppUtils;
import com.trisysit.epc_android.utils.DataBaseManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ActivityTaskActivity extends DrawerScreenActivity {
    ListView activity_lv;
    private String taskId;
    private List<ProjectTask> activityTaskList=new ArrayList<>();
    private String projectId;
    ActivityAdapter adapter;
    SimpleDateFormat dateFormat;
    private View rootView;
    private Toolbar toolbar;
    TextView titleTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_list);
        getWidgets();
        dateFormat=new SimpleDateFormat("dd-MM-yyyy");
        setUpNavigationDrawer(ActivityTaskActivity.this, rootView, toolbar);
        getExtra();
        setData();
        setAdapter();
    }
    private void getWidgets(){
        activity_lv =(ListView)findViewById(R.id.activity_listing);
        rootView = findViewById(R.id.root);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        titleTv = (TextView) findViewById(R.id.toolbar_title);
        titleTv.setText(getString(R.string.task_listing));
    }
    protected void onResume() {
        super.onResume();
        if(AppUtils.getSyncDetails(getApplicationContext())){
            findViewById(R.id.snack_bar).setVisibility(View.VISIBLE);
        }
        else {
            findViewById(R.id.snack_bar).setVisibility(View.GONE);
        }
    }
    private void getExtra(){
        taskId=getIntent().getStringExtra("taskId");
        projectId=getIntent().getStringExtra("projectId");

    }
    private void setData(){
        activityTaskList= DataBaseManager.getInstance().getActivityByTaskIdAndProjectId(taskId,projectId);

    }
    private void setAdapter(){
        if(activityTaskList.size()>0){
            adapter=new ActivityAdapter();
            activity_lv.setAdapter(adapter);
        }
    }
    private class ActivityAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            return activityTaskList.size();
        }

        @Override
        public Object getItem(int position) {
            return activityTaskList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(ActivityTaskActivity.this);
                convertView = inflater.inflate(R.layout.task_listing_layout, parent, false);

            }
            TextView activity_subject_tv=(TextView)convertView.findViewById(R.id.myTask_text);
            TextView task_start_date=(TextView)convertView.findViewById(R.id.task_start_date) ;
            TextView task_end_date=(TextView)convertView.findViewById(R.id.task_end_date) ;
            final ProjectTask task=activityTaskList.get(position);
            if(task.getSubject()!=null && task.getLoa()!=null && task.getUnit()!=null){
                activity_subject_tv.setText(task.getSubject()+" ( "+task.getLoa()+" "+task.getUnit()+" )");
            }
            else {
                activity_subject_tv.setText(getString(R.string.no_subject));
            }
            if(task.getTaskStartDate()!=null){
                task_start_date.setText("Start date: "+dateFormat.format(task.getTaskStartDate()));
            }
            else {
                task_start_date.setText("Start date: ");
            }
            if(task.getTaskEndDate()!=null){
                task_end_date.setText("End date: "+dateFormat.format(task.getTaskEndDate()));
            }
            else {
                task_end_date.setText("End date: ");
            }
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int subActivityCount=DataBaseManager.getInstance().getSubActivityCount(task.getTaskId(),task.getProjectId());
                    if(subActivityCount>0){
                        Intent intent=new Intent(ActivityTaskActivity.this,SubActivityTaskActivity.class);
                        intent.putExtra("taskId",task.getTaskId());
                        intent.putExtra("projectId",task.getProjectId());
                        startActivity(intent);
                    }
                }
            });
            return convertView;
        }

    }

}
