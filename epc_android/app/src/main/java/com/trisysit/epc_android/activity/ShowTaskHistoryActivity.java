package com.trisysit.epc_android.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
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

public class ShowTaskHistoryActivity extends DrawerScreenActivity {
    ListView show_history_lv;
    private View rootView;
    private String taskId,projectId,schedule,parentL1Id,schedulePart,loa,subSchedulemultiplier;
    private List<ProjectTask> taskList=new ArrayList<>();
    private Toolbar toolbar;
    private FloatingActionButton actionButton;
    private TaskHistoryAdapter adapter;
    private TextView titleTv,no_history_msg;
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_task_history);
        getWidgets();
        dateFormat=new SimpleDateFormat("dd MMM yyyy");
        setUpNavigationDrawer(ShowTaskHistoryActivity.this, rootView, toolbar);
        getExtra();
        setData();
        setListener();
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
    private void getExtra(){
        projectId=getIntent().getStringExtra("projectId");
        schedule=getIntent().getStringExtra("schedule");
        parentL1Id=getIntent().getStringExtra("parentL1Id");
        schedulePart=getIntent().getStringExtra("schedulePart");
        loa=getIntent().getStringExtra("loa");
        subSchedulemultiplier=getIntent().getStringExtra("subScheduleMultiplier");

    }
    private void setData(){
        taskList=DataBaseManager.getInstance().getDailyTaskUpdateBySchedule(schedule,projectId,schedulePart);
       if(taskList.size()>0){
           no_history_msg.setVisibility(View.GONE);
           show_history_lv.setVisibility(View.VISIBLE);
           adapter=new TaskHistoryAdapter();
           show_history_lv.setAdapter(adapter);
       }
       else {
           no_history_msg.setVisibility(View.VISIBLE);
           show_history_lv.setVisibility(View.GONE);
       }
    }
    private void setListener(){
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ShowTaskHistoryActivity.this,TaskUpdateActivity.class);
                intent.putExtra("projectId",projectId);
                intent.putExtra("parentL1Id",parentL1Id);
                intent.putExtra("schedule",schedule);
                intent.putExtra("schedulePart",schedulePart);
                intent.putExtra("loa",loa);
                intent.putExtra("updateType",AppUtils.DAILY_UPDATE_TYPE);
                intent.putExtra("subScheduleMultiplier",subSchedulemultiplier);
                startActivity(intent);
                finish();
            }
        });
    }

    private void getWidgets(){
        rootView = findViewById(R.id.root);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        titleTv = (TextView) findViewById(R.id.toolbar_title);
        show_history_lv=(ListView)findViewById(R.id.show_history_lv);
        no_history_msg=(TextView)findViewById(R.id.no_history_msg);
        actionButton=(FloatingActionButton)findViewById(R.id.fab_add_task);
        titleTv.setText(getString(R.string.daily_task_update));
    }
    private class TaskHistoryAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return taskList.size();
        }

        @Override
        public Object getItem(int position) {
            return taskList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(ShowTaskHistoryActivity.this);
                convertView = inflater.inflate(R.layout.task_listing_layout, parent, false);
            }
            TextView taskCreatedDate=(TextView)convertView.findViewById(R.id.myTask_text);
            TextView actaul_loa_quantity=(TextView)convertView.findViewById(R.id.task_start_date);
            ProjectTask task=taskList.get(position);
            taskCreatedDate.setText("Date: "+dateFormat.format(task.getTaskStartDate()));
            String workdDone_with_unit="<b>Work Done: </b>"+task.getActualLoa()+"<b> Unit: </b>"+task.getUnit()+"<b> Scope: </b>"+task.getLoa();
            actaul_loa_quantity.setText(Html.fromHtml(workdDone_with_unit));
            return convertView;
        }
    }
}
