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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SubTaskActivity extends DrawerScreenActivity {
    ListView subTaskList_lv;
    private View rootView;
    private SubTaskListingAdapter adapter;
    private String taskId,projectId;
    SimpleDateFormat dateFormat;
    private List<ProjectTask> subTaskList=new ArrayList<>();
    private Toolbar toolbar;
    TextView titleTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_task);
        getWidgets();
        dateFormat=new SimpleDateFormat("dd-MM-yyyy");
        setUpNavigationDrawer(SubTaskActivity.this, rootView, toolbar);
        getExtra();
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
    private void getWidgets(){
        subTaskList_lv=(ListView)findViewById(R.id.subTask_list);
        rootView = findViewById(R.id.root);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        titleTv = (TextView) findViewById(R.id.toolbar_title);
        titleTv.setText(getString(R.string.task_listing));
    }
    private void getExtra(){
        taskId=getIntent().getStringExtra("taskId");
        projectId=getIntent().getStringExtra("projectId");

    }
    private void setAdapter(){
        subTaskList= DataBaseManager.getInstance().getSubTaskByTaskId(taskId,projectId);
        adapter=new SubTaskListingAdapter();
        subTaskList_lv.setAdapter(adapter);
    }

    private class SubTaskListingAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return subTaskList.size();
        }

        @Override
        public Object getItem(int position) {
            return subTaskList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                LayoutInflater inflater = LayoutInflater.from(SubTaskActivity.this);
                convertView = inflater.inflate(R.layout.task_listing_layout, parent, false);
            }
            final ProjectTask taskTable=subTaskList.get(position);
            TextView subTask_subject=(TextView)convertView.findViewById(R.id.myTask_text);
            ImageView edit_task_iv=(ImageView)convertView.findViewById(R.id.edit_iv);
            TextView task_start_date=(TextView)convertView.findViewById(R.id.task_start_date) ;
            TextView task_end_date=(TextView)convertView.findViewById(R.id.task_end_date) ;

            if(taskTable.getSubject()!=null && taskTable.getLoa()!=null && taskTable.getUnit()!=null){
                subTask_subject.setText(taskTable.getSubject()+" ( "+taskTable.getLoa()+" "+taskTable.getUnit()+" )");
            }
            else {
                subTask_subject.setText(getString(R.string.no_subject));
            }
            if(taskTable.getTaskStartDate()!=null){
                task_start_date.setText("Start date: "+dateFormat.format(taskTable.getTaskStartDate()));
            }
            if(taskTable.getTaskEndDate()!=null){
                task_end_date.setText("End date: "+dateFormat.format(taskTable.getTaskEndDate()));
            }
            edit_task_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(SubTaskActivity.this,TaskUpdateActivity.class);
                    intent.putExtra("taskId",taskTable.getTaskId());
                    intent.putExtra("projectId",taskTable.getProjectId());
                    startActivity(intent);
                }
            });

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int activityCount=DataBaseManager.getInstance().getActivityCount(taskTable.getTaskId(),taskTable.getProjectId());
                    if(activityCount>0){
                        Intent intent=new Intent(SubTaskActivity.this,ActivityTaskActivity.class);
                        intent.putExtra("taskId",taskTable.getTaskId());
                        intent.putExtra("projectId",taskTable.getProjectId());
                        startActivity(intent);
                    }

                }
            });
            return convertView;
        }
    }
}
