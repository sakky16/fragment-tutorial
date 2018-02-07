package com.trisysit.epc_android.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.trisysit.epc_android.R;
import com.trisysit.epc_android.SqliteDatabase.ProjectTask;
import com.trisysit.epc_android.SqliteDatabase.SubActivityMaster;
import com.trisysit.epc_android.utils.AppUtils;
import com.trisysit.epc_android.utils.DataBaseManager;
import com.trisysit.epc_android.utils.SharedPrefHelper;

import java.util.ArrayList;
import java.util.List;

public class SubActivityTaskActivity extends DrawerScreenActivity {
    private View rootView;
    private Toolbar toolbar;
    TextView titleTv;
    private List<SubActivityMaster> subTaskList = new ArrayList<>();
    private String taskId, projectId, loa, parentL1Id;
    private ListView sub_activity_lv;
    private SubActivityAdapter adapter;
    private String schedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_task2);
        getWidgets();
        setUpNavigationDrawer(SubActivityTaskActivity.this, rootView, toolbar);
        getExtra();
        setData();
        setAdapter();
    }

    private void getWidgets() {
        sub_activity_lv = (ListView) findViewById(R.id.sub_activity_listing);
        rootView = findViewById(R.id.root);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        titleTv = (TextView) findViewById(R.id.toolbar_title);
        titleTv.setText(getString(R.string.subActivity_list));
    }

    protected void onResume() {
        super.onResume();
        if (AppUtils.getSyncDetails(getApplicationContext())) {
            findViewById(R.id.snack_bar).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.snack_bar).setVisibility(View.GONE);
        }
    }

    private void getExtra() {
        parentL1Id = getIntent().getStringExtra("parentL1Id");
        loa = getIntent().getStringExtra("loa");
        schedule = getIntent().getStringExtra("schedule");


    }

    private void setData() {
        subTaskList = DataBaseManager.getInstance().getSubActivityBySchedule(schedule);
        if(subTaskList.size()>0){
            for(int i=0;i<subTaskList.size();i++){
                if(subTaskList.get(i).getSchedule().equalsIgnoreCase(subTaskList.get(i).getSchedulePart())){
                    subTaskList.remove(subTaskList.get(i));
                    break;
                }
            }
        }
    }

    private void setAdapter() {
        if (subTaskList.size() > 0) {
            adapter = new SubActivityAdapter(subTaskList);
            sub_activity_lv.setAdapter(adapter);
        }


    }

    private class SubActivityAdapter extends BaseAdapter {
        List<SubActivityMaster> subActivityMasters = new ArrayList<>();

        public SubActivityAdapter(List<SubActivityMaster> subActivityMasters) {
            this.subActivityMasters = subActivityMasters;
        }

        @Override
        public int getCount() {
            return subActivityMasters.size();
        }

        @Override
        public Object getItem(int position) {
            return subActivityMasters.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(SubActivityTaskActivity.this);
                convertView = inflater.inflate(R.layout.sub_task_listing, parent, false);
            }
            TextView subActivity_subject_tv = (TextView) convertView.findViewById(R.id.myTask_text);
            TextView schedule_tv = (TextView) convertView.findViewById(R.id.schedule_tv);
            TextView schedulePart_tv = (TextView) convertView.findViewById(R.id.schedulePart_tv);
            ImageView edit_iv=(ImageView)convertView.findViewById(R.id.edit_iv);
            if(SharedPrefHelper.getInstance(SubActivityTaskActivity.this).getFromSharedPrefs(SharedPrefHelper.ROLE_NAME).equalsIgnoreCase(AppUtils.ROLE_FIELD_MANAGER)){
                edit_iv.setVisibility(View.VISIBLE);
            }
            else {
                edit_iv.setVisibility(View.GONE);
            }
            final SubActivityMaster subActivityMaster = subActivityMasters.get(position);
            Double loa_db = 0.0;
            Double subScheduleMultiplier_db = 0.0;
            Double scope=0.0;
            if (loa != null && !loa.equalsIgnoreCase("")) {
                loa_db = Double.parseDouble(loa);
            }
            if (subActivityMaster.getSubScheduleMultiplier() != null && !subActivityMaster.getSubScheduleMultiplier().equalsIgnoreCase("")) {
                subScheduleMultiplier_db = Double.parseDouble(subActivityMaster.getSubScheduleMultiplier());
            }
            scope=(loa_db*subScheduleMultiplier_db);
            scope=(double) Math.round(scope * 100) / 100;
            if (subActivityMaster.getLongDescription() != null) {
                if(subActivityMaster.getUnit()!=null){
                    subActivity_subject_tv.setText(subActivityMaster.getLongDescription() + " ( " + (scope) + " " + subActivityMaster.getUnit() + " )");
                }
                else {
                    subActivity_subject_tv.setText(subActivityMaster.getLongDescription() + " ( " + (scope) + " )");
                }

            }
            if (subActivityMaster.getSchedule() != null) {
                schedule_tv.setText("Schedule: " + subActivityMaster.getSchedule());
            } else {
                schedule_tv.setText("Schedule: ");
            }
            if (subActivityMaster.getSchedulePart() != null) {
                schedulePart_tv.setText("Schedule Part: " + subActivityMaster.getSchedulePart());
            } else {
                schedulePart_tv.setText("Schedule Part: ");
            }
            edit_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(SubActivityTaskActivity.this,TaskUpdateActivity.class);
                    intent.putExtra("schedule",schedule);
                    intent.putExtra("loa",loa);
                    intent.putExtra("parentL1Id",parentL1Id);
                    intent.putExtra("schedulePart",subActivityMaster.getSchedulePart());
                    intent.putExtra("updateType",AppUtils.SUBACTIVITY_UPDATE_TYPE);
                    intent.putExtra("projectId",subActivityMaster.getProjectId());
                    startActivity(intent);
                    finish();
                }
            });
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(subActivityMaster.getSchedule()!=null && subActivityMaster.getSchedulePart()!=null){
                        if(subActivityMaster.getSchedule().equalsIgnoreCase(subActivityMaster.getSchedulePart())){
                            Toast.makeText(SubActivityTaskActivity.this,"Cannot update work progress for activity level,",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Intent intent = new Intent(SubActivityTaskActivity.this, ShowTaskHistoryActivity.class);
                            intent.putExtra("schedule", subActivityMaster.getSchedule());
                            intent.putExtra("loa",loa);
                            intent.putExtra("subScheduleMultiplier",subActivityMaster.getSubScheduleMultiplier());
                            intent.putExtra("schedulePart", subActivityMaster.getSchedulePart());
                            intent.putExtra("projectId", subActivityMaster.getProjectId());
                            intent.putExtra("parentL1Id", parentL1Id);
                            startActivity(intent);
                        }
                    }


                }
            });
            return convertView;
        }


    }
}
