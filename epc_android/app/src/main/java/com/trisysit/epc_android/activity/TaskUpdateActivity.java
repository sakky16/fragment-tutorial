package com.trisysit.epc_android.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.trisysit.epc_android.R;
import com.trisysit.epc_android.SqliteDatabase.LocationMaster;
import com.trisysit.epc_android.SqliteDatabase.ProjectTask;
import com.trisysit.epc_android.SqliteDatabase.SubActivityMaster;
import com.trisysit.epc_android.utils.AppUtils;
import com.trisysit.epc_android.utils.DataBaseManager;
import com.trisysit.epc_android.utils.SharedPrefHelper;
import com.trisysit.epc_android.utils.SyncManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TaskUpdateActivity extends DrawerScreenActivity {
    private DatePickerDialog.OnDateSetListener date_dp;
    private TextView last_updated_project_tv, schedule_tv, schedule_part_tv, act_qnty_tv, subActivity_name_tv, input_date_tv, unit_tv, scope_tv;
    private View rootView;
    private String taskId, projectId, parentL1Id, schedule, schedulePart, loa, subScheduleMultipler, subActivity_loa, update_type;
    private Toolbar toolbar;
    String locationL1Name, locationL2Name, locationL3Name, locationL4Name, locationL5Name;
    private List<String> locationL1List;
    private List<String> locationL2List;
    private List<String> locationL3List;
    private List<String> locationL4List;
    private List<String> locationL5List;
    private String update_date;
    private ProgressBar circularProgressbar;
    private SimpleDateFormat dateFormat;
    private EditText quantity_ev, actual_man_power_et, contractor_et;
    private ProjectTask task;
    private SubActivityMaster subActivityMaster;
    private Button apply_btn;
    private Spinner location1_sp, location2_sp, location3_sp, location4_sp, location5_sp;
    TextView titleTv, progress_tv;
    private ScrollView scrollView;
    private LinearLayout date_ll;
    Calendar calendar;
    Double progress_db=0.0;
    Double scope = 0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_material);
        dateFormat = new SimpleDateFormat("MMM dd ,yyyy");
        getWidgets();
        setUpNavigationDrawer(TaskUpdateActivity.this, rootView, toolbar);
        getExtra();
        setData();
        setDatePicker();
        setLocationSpinner();
        setListener();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (update_type != null) {
            if (update_type.equalsIgnoreCase(AppUtils.DAILY_UPDATE_TYPE)) {
                Intent intent = new Intent(TaskUpdateActivity.this, ShowTaskHistoryActivity.class);
                intent.putExtra("projectId", projectId);
                intent.putExtra("schedule", schedule);
                intent.putExtra("parentL1Id", parentL1Id);
                intent.putExtra("schedulePart", schedulePart);
                intent.putExtra("loa", loa);
                intent.putExtra("subScheduleMultiplier", subScheduleMultipler);
                startActivity(intent);
                finish();
            } else if (update_type.equalsIgnoreCase(AppUtils.ACTIVITY_UPDATE_TYPE)) {
                Intent intent = new Intent(TaskUpdateActivity.this, TaskActivity.class);
                intent.putExtra("projectId", projectId);
                startActivity(intent);
                finish();
            } else if (update_type.equalsIgnoreCase(AppUtils.SUBACTIVITY_UPDATE_TYPE)) {
                Intent intent = new Intent(TaskUpdateActivity.this, SubActivityTaskActivity.class);
                intent.putExtra("schedule", schedule);
                intent.putExtra("loa", loa);
                intent.putExtra("parentL1Id", parentL1Id);
                startActivity(intent);
                finish();
            }
        }


    }

    private void setLocationSpinner() {
        List<LocationMaster> locationMasterList = DataBaseManager.getInstance().getLocationL1(projectId);
        if (locationMasterList.size() > 0) {
            locationL1List = new ArrayList<>();
            locationL1List.add("Select District");
            for (int i = 0; i < locationMasterList.size(); i++) {
                if (!locationL1List.contains(locationMasterList.get(i).getLocationL1())) {
                    locationL1List.add(locationMasterList.get(i).getLocationL1());
                }
            }
            ArrayAdapter<String> locationLAdapter = new ArrayAdapter<String>(TaskUpdateActivity.this, R.layout.location_dropdown, locationL1List);
            locationLAdapter.setDropDownViewResource(R.layout.location_dropdown);
            location1_sp.setAdapter(locationLAdapter);
        }

    }


    private void getWidgets() {
        last_updated_project_tv = (TextView) findViewById(R.id.last_updated_project_tv);
        rootView = findViewById(R.id.root);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        quantity_ev = (EditText) findViewById(R.id.quantity_et);
        input_date_tv = (TextView) findViewById(R.id.input_date);
        input_date_tv.setText(AppUtils.currentDateTime(TaskUpdateActivity.this, "MMM dd ,yyyy"));
        apply_btn = (Button) findViewById(R.id.apply_btn);
        circularProgressbar = (ProgressBar) findViewById(R.id.circularProgressbar);
        titleTv = (TextView) findViewById(R.id.toolbar_title);
        progress_tv = (TextView) findViewById(R.id.progress_tv);
        schedule_tv = (TextView) findViewById(R.id.schedule_tv);
        schedule_part_tv = (TextView) findViewById(R.id.schedulePart_tv);
        act_qnty_tv = (TextView) findViewById(R.id.progress1_tv);
        subActivity_name_tv = (TextView) findViewById(R.id.material_name_tv);
        date_ll = (LinearLayout) findViewById(R.id.date_ll);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        location1_sp = (Spinner) findViewById(R.id.location1_sp);
        location2_sp = (Spinner) findViewById(R.id.location2_sp);
        location3_sp = (Spinner) findViewById(R.id.location3_sp);
        location4_sp = (Spinner) findViewById(R.id.location4_sp);
        location5_sp = (Spinner) findViewById(R.id.location5_sp);
        actual_man_power_et = (EditText) findViewById(R.id.actual_man_power_et);
        contractor_et = (EditText) findViewById(R.id.contractor_et);
        unit_tv = (TextView) findViewById(R.id.unit_tv);
        scope_tv = (TextView) findViewById(R.id.scope_tv);
    }

    private void getExtra() {
        schedule = getIntent().getStringExtra("schedule");
        schedulePart = getIntent().getStringExtra("schedulePart");
        parentL1Id = getIntent().getStringExtra("parentL1Id");
        loa = getIntent().getStringExtra("loa");
        subScheduleMultipler = getIntent().getStringExtra("subScheduleMultiplier");
        projectId = getIntent().getStringExtra("projectId");
        update_type = getIntent().getStringExtra("updateType");

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

    private void setData() {
        if (update_type != null) {
            if (update_type.equalsIgnoreCase(AppUtils.DAILY_UPDATE_TYPE)) {
                titleTv.setText(getString(R.string.task_material));
                subActivityMaster = DataBaseManager.getInstance().getSubActivityByScheduleAndSchedulePart(schedule, schedulePart);
                if (subActivityMaster != null) {
                    if (subActivityMaster.getLongDescription() != null) {
                        subActivity_name_tv.setText(subActivityMaster.getLongDescription());
                    }
                    if (subActivityMaster.getSchedulePart() != null) {
                        String schedule_part = "<b>Schedule part: </b> " + "" + subActivityMaster.getSchedulePart();
                        schedule_part_tv.setText(Html.fromHtml(schedule_part));
                    } else {
                        String schedule_part = "<b>Schedule part: </b> ";
                        schedule_part_tv.setText(Html.fromHtml(schedule_part));
                    }
                    if (subActivityMaster.getSchedule() != null) {
                        String schedule = "<b>Schedule: </b> " + "" + subActivityMaster.getSchedule();
                        schedule_tv.setText(Html.fromHtml(schedule));
                    } else {
                        String schedule = "<b>Schedule: </b> ";
                        schedule_tv.setText(Html.fromHtml(schedule));
                    }
                    if (subActivityMaster.getUnit() != null) {
                        String unit = "<b>Unit: </b> " + "" + subActivityMaster.getUnit();
                        unit_tv.setText(Html.fromHtml(unit));
                    } else {
                        String schedule = "<b>Unit: </b> ";
                        unit_tv.setText(Html.fromHtml(schedule));
                    }
                    Double loa_db = 0.0;
                    Double subScheduleMultiplier_db = 0.0;
                    Double scope = 0.0;
                    if (loa != null && !loa.equalsIgnoreCase("")) {
                        loa_db = Double.parseDouble(loa);
                    }
                    if (subScheduleMultipler != null && !subScheduleMultipler.equalsIgnoreCase("")) {
                        subScheduleMultiplier_db = Double.parseDouble(subScheduleMultipler);
                    }
                    scope = (loa_db * subScheduleMultiplier_db);
                    scope = (double) Math.round(scope * 100) / 100;
                    subActivity_loa = String.valueOf(scope);
                    String sub_activity_loa = "<b>Scope: </b>" + String.valueOf(subActivity_loa);
                    scope_tv.setText(Html.fromHtml(sub_activity_loa));
                    progress_db = getActualLoaQuantity(subActivityMaster.getSchedule(), subActivityMaster.getProjectId(), subActivityMaster.getSchedulePart());
                    String actual_loa_quantity = String.valueOf(progress_db);
                    if (!actual_loa_quantity.equalsIgnoreCase("")) {
                        String actual_quantity = "<b>Progress: </b>" + actual_loa_quantity;
                        act_qnty_tv.setText(Html.fromHtml(actual_quantity));
                    } else {
                        String actual_quantity = "<b>Progress: </b>";
                        act_qnty_tv.setText(Html.fromHtml(actual_quantity));
                    }
                    String lastUpdatedDate = getLastUpdatedDate(subActivityMaster.getSchedule(), subActivityMaster.getProjectId(), subActivityMaster.getSchedulePart());
                    if (!lastUpdatedDate.equalsIgnoreCase("")) {
                        last_updated_project_tv.setText("Last updated on " + lastUpdatedDate);
                    } else {
                        last_updated_project_tv.setText("No updated date found");
                    }
                }
            } else if (update_type.equalsIgnoreCase(AppUtils.ACTIVITY_UPDATE_TYPE)) {
                titleTv.setText(getString(R.string.activity_update));
                ProjectTask task = null;
                List<ProjectTask> projectTaskList = DataBaseManager.getInstance().getActivityByScheduleAndParentL1Id(schedule, parentL1Id);
                if (projectTaskList.size() > 0) {
                    task = projectTaskList.get(0);
                }
                if(task!=null){
                    if (task.getSubject() != null) {
                        subActivity_name_tv.setText(task.getSubject());
                    }
                    if (task.getSchedule() != null) {
                        String schedule = "<b>Schedule: </b> " + "" + task.getSchedule();
                        schedule_tv.setText(Html.fromHtml(schedule));
                    } else {
                        String schedule = "<b>Schedule: </b> ";
                        schedule_tv.setText(Html.fromHtml(schedule));
                    }
                    if (task.getUnit() != null) {
                        String schedule_part = "<b>Unit: </b> " + "" + projectTaskList.get(0).getUnit();
                        schedule_part_tv.setText(Html.fromHtml(schedule_part));
                    } else {
                        String schedule_part = "<b>Unit: </b> ";
                        schedule_part_tv.setText(Html.fromHtml(schedule_part));
                    }
                    if (projectTaskList.size() > 0) {
                        double loa_db = 0.0;
                        double progress_db1 = 0.0;
                        String loa_str = "";
                        for (int i = 0; i < projectTaskList.size(); i++) {
                            if (projectTaskList.get(i).getLoa() != null && !projectTaskList.get(i).getLoa().equalsIgnoreCase("")) {
                                double loa1_db = Double.parseDouble(projectTaskList.get(i).getLoa());
                                loa_db = loa_db + loa1_db;
                            }
                            if (projectTaskList.get(i).getActualLoa() != null && !projectTaskList.get(i).getActualLoa().equalsIgnoreCase("")) {
                                double progress1_db = Double.parseDouble(projectTaskList.get(i).getActualLoa());
                                progress_db1 = progress1_db + progress_db1;
                            }
                        }
                        progress_db = progress_db1;
                        String progress_str = String.valueOf(progress_db1);
                        String actual_loa = "<b>Progress: </b> " + "" + progress_str;
                        act_qnty_tv.setText(Html.fromHtml(actual_loa));
                        loa_db = (double) Math.round(loa_db * 100) / 100;
                        scope = loa_db;
                        loa_str = String.valueOf(loa_db);
                        String activity_scope = "<b>Scope: </b>" + loa_str;
                        scope_tv.setText(Html.fromHtml(activity_scope));
                    }
                    if (task.getTaskUpdatedDate() != null) {
                        Long updated_date_long = Long.parseLong(task.getTaskUpdatedDate());
                        String updated_date = dateFormat.format(updated_date_long);
                        last_updated_project_tv.setText("Last updated on " + updated_date);
                    } else {
                        last_updated_project_tv.setText("No updated date found");
                    }
                }
            } else if (update_type.equalsIgnoreCase(AppUtils.SUBACTIVITY_UPDATE_TYPE)) {
                titleTv.setText(getString(R.string.sub_activity_update));
                List<ProjectTask> projectTaskList = DataBaseManager.getInstance().getSubActivityByScheduleAndSchedulePart(schedule, schedulePart, parentL1Id);
                ProjectTask task = null;
                if (projectTaskList.size() > 0) {
                    task = projectTaskList.get(0);
                }
                if (task != null) {
                    if (task.getSubject() != null) {
                        subActivity_name_tv.setText(task.getSubject());
                    }
                    if (task.getSchedule() != null) {
                        String schedule = "<b>Schedule: </b> " + "" + task.getSchedule();
                        schedule_tv.setText(Html.fromHtml(schedule));
                    } else {
                        String schedule = "<b>Schedule: </b> ";
                        schedule_tv.setText(Html.fromHtml(schedule));
                    }
                    if (task.getSchedulePart() != null) {
                        String schedule_part = "<b>Schedule part: </b> " + "" + task.getSchedulePart();
                        schedule_part_tv.setText(Html.fromHtml(schedule_part));
                    } else {
                        String schedule_part = "<b>Schedule part: </b> ";
                        schedule_part_tv.setText(Html.fromHtml(schedule_part));
                    }
                    if (task.getUnit() != null) {
                        String unit = "<b>Unit: </b> " + "" + task.getUnit();
                        unit_tv.setText(Html.fromHtml(unit));
                    } else {
                        String schedule_part = "<b>Unit: </b> ";
                        unit_tv.setText(Html.fromHtml(schedule_part));
                    }
                    if (projectTaskList.size() > 0) {
                        double loa_db = 0.0;
                        double progress_db1 = 0.0;
                        for (int i = 0; i < projectTaskList.size(); i++) {
                            if (projectTaskList.get(i).getLoa() != null && !projectTaskList.get(i).getLoa().equalsIgnoreCase("")) {
                                double loa1_db = Double.parseDouble(projectTaskList.get(i).getLoa());
                                loa_db = loa_db + loa1_db;
                            }
                            if (projectTaskList.get(i).getActualLoa() != null && !projectTaskList.get(i).getActualLoa().equalsIgnoreCase("")) {
                                double progress1_db = Double.parseDouble(projectTaskList.get(i).getActualLoa());
                                progress_db1 = progress_db1 + progress1_db;
                            }
                        }
                        progress_db = progress_db1;
                        String progress_str = String.valueOf(progress_db);
                        String subActivity_progress = "<b>Progress: </b> " + "" + progress_str;
                        act_qnty_tv.setText(Html.fromHtml(subActivity_progress));
                        loa_db = (double) Math.round(loa_db * 100) / 100;
                        scope = loa_db;
                        String loa_str = String.valueOf(loa_db);
                        String subActivity_scope = "<b>Scope: </b>" + loa_str;
                        scope_tv.setText(Html.fromHtml(subActivity_scope));
                    }
                    if (task.getTaskUpdatedDate() != null) {
                        Long updated_date_long = Long.parseLong(task.getTaskUpdatedDate());
                        String updated_date = dateFormat.format(updated_date_long);
                        last_updated_project_tv.setText("Last updated on " + updated_date);
                    } else {
                        last_updated_project_tv.setText("No updated date found");
                    }
                }
            }
        }

    }

    private String getLastUpdatedDate(String schedule, String projectId, String schedulePart) {
        String updatedDate = "";
        List<ProjectTask> addedTaskList = DataBaseManager.getInstance().getDailyTaskUpdateDescOfCreatedDate(schedule, projectId, schedulePart);
        if (addedTaskList.size() > 0) {
            updatedDate = AppUtils.getFormatedDateForTaskHistory(addedTaskList.get(0).getTaskCreatedDate());
        }
        return updatedDate;
    }

    private Double getActualLoaQuantity(String schedule, String projectId, String schedulePart) {
        Double loa_quantity = 0.0;
        List<ProjectTask> addedTaskList = DataBaseManager.getInstance().getDailyTaskUpdateDescOfCreatedDate(schedule, projectId, schedulePart);
        if (addedTaskList.size() > 0) {
            for (ProjectTask task : addedTaskList) {
                String actual_Quantity = task.getActualLoa();
                if (actual_Quantity != null && !actual_Quantity.equalsIgnoreCase("")) {
                    Double d = Double.parseDouble(actual_Quantity);
                    loa_quantity = loa_quantity + d;
                }
            }
        }
        return loa_quantity;
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

    private void setListener() {
        date_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(TaskUpdateActivity.this, date_dp, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        quantity_ev.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                scrolUpScrollView();
            }
        });

        quantity_ev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity_ev.setFocusable(true);
                scrolUpScrollView();
            }
        });
        location1_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                locationL1Name = (String) parent.getItemAtPosition(position);
                if (!locationL1Name.equalsIgnoreCase("Select District")) {
                    List<LocationMaster> locationMasterList = DataBaseManager.getInstance().getLocationL2(projectId, locationL1Name);
                    locationL2List = new ArrayList<>();
                    if (locationMasterList.size() > 0) {
                        locationL2List.add("Select Town");
                        for (int i = 0; i < locationMasterList.size(); i++) {
                            if (!locationL2List.contains(locationMasterList.get(i).getLocationL2())) {
                                locationL2List.add(locationMasterList.get(i).getLocationL2());
                            }
                        }
                        if (locationL2List.size() > 0) {
                            ArrayAdapter<String> locationLAdapter = new ArrayAdapter<String>(TaskUpdateActivity.this, R.layout.location_dropdown, locationL2List);
                            locationLAdapter.setDropDownViewResource(R.layout.location_dropdown);
                            location2_sp.setAdapter(locationLAdapter);
                        }
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        location2_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                locationL2Name = (String) parent.getItemAtPosition(position);
                if (!locationL2Name.equalsIgnoreCase("Select Town")) {
                    List<LocationMaster> locationMasterList = DataBaseManager.getInstance().getLocationL3(projectId, locationL1Name, locationL2Name);
                    locationL3List = new ArrayList<>();
                    if (locationMasterList.size() > 0) {
                        locationL3List.add("Select Location");
                        for (int i = 0; i < locationMasterList.size(); i++) {
                            if (!locationL3List.contains(locationMasterList.get(i).getLocationL3())) {
                                locationL3List.add(locationMasterList.get(i).getLocationL3());
                            }
                        }
                        if (locationL3List.size() > 0) {
                            ArrayAdapter<String> locationLAdapter = new ArrayAdapter<String>(TaskUpdateActivity.this, R.layout.location_dropdown, locationL3List);
                            locationLAdapter.setDropDownViewResource(R.layout.location_dropdown);
                            location3_sp.setAdapter(locationLAdapter);
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        location3_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                locationL3Name = (String) parent.getItemAtPosition(position);
                if (!locationL3Name.equalsIgnoreCase("Select Location")) {
                    List<LocationMaster> locationMasterList = DataBaseManager.getInstance().getLocationL4(projectId, locationL1Name, locationL2Name, locationL3Name);
                    locationL4List = new ArrayList<>();
                    if (locationMasterList.size() > 0) {
                        locationL4List.add("Select PSS");
                        for (int i = 0; i < locationMasterList.size(); i++) {
                            if (!locationL4List.contains(locationMasterList.get(i).getLocationL4())) {
                                locationL4List.add(locationMasterList.get(i).getLocationL4());
                            }
                        }
                        if (locationL4List.size() > 0) {
                            ArrayAdapter<String> locationLAdapter = new ArrayAdapter<String>(TaskUpdateActivity.this, R.layout.location_dropdown, locationL4List);
                            locationLAdapter.setDropDownViewResource(R.layout.location_dropdown);
                            location4_sp.setAdapter(locationLAdapter);
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        location4_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                locationL4Name = (String) parent.getItemAtPosition(position);
                if (!locationL4Name.equalsIgnoreCase("Select PSS")) {
                    List<LocationMaster> locationMasterList = DataBaseManager.getInstance().getLocationL5List(projectId, locationL1Name, locationL2Name, locationL3Name, locationL4Name);
                    locationL5List = new ArrayList<>();
                    if (locationMasterList.size() > 0) {
                        locationL5List.add("Select Feeder");
                        for (int i = 0; i < locationMasterList.size(); i++) {
                            if (!locationL5List.contains(locationMasterList.get(i).getLocationL5())) {
                                locationL5List.add(locationMasterList.get(i).getLocationL5());
                            }
                        }
                        if (locationL5List.size() > 0) {
                            ArrayAdapter<String> locationLAdapter = new ArrayAdapter<String>(TaskUpdateActivity.this, R.layout.location_dropdown, locationL5List);
                            locationLAdapter.setDropDownViewResource(R.layout.location_dropdown);
                            location5_sp.setAdapter(locationLAdapter);
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        location5_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                locationL5Name = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantity = quantity_ev.getText().toString();
                String update_date = input_date_tv.getText().toString();
                String man_power = actual_man_power_et.getText().toString();
                String contractor = contractor_et.getText().toString();
                if (AppUtils.isEmpty(quantity)) {
                    Toast.makeText(TaskUpdateActivity.this, "Please enter work progress", Toast.LENGTH_SHORT).show();
                } else if (quantity.equalsIgnoreCase("0")) {
                    Toast.makeText(TaskUpdateActivity.this, "Please enter work progress more than 0", Toast.LENGTH_SHORT).show();
                } else {
                    if (update_type != null) {
                        if (update_type.equalsIgnoreCase(AppUtils.DAILY_UPDATE_TYPE)) {
                            double d = Double.parseDouble(quantity);
                            String actual_loa = "";
                            Double progressDb = (progress_db + d);
                            if (Double.parseDouble(subActivity_loa) < progressDb) {
                                Toast.makeText(TaskUpdateActivity.this, "Progress should not be more than the scope", Toast.LENGTH_SHORT).show();

                            } else {
                                subActivityMaster = DataBaseManager.getInstance().getSubActivityByScheduleAndSchedulePart(schedule, schedulePart);
                                if (subActivityMaster != null) {
                                    ProjectTask task1 = new ProjectTask();
                                    Date date = null;
                                    task1.setMobileId(UUID.randomUUID().toString());
                                    task1.setTaskStatus(AppUtils.IN_PROGRESS);
                                    actual_loa = String.valueOf(d);
                                    task1.setActualLoa(actual_loa);
                                    task1.setTaskCreatedDate(AppUtils.currentDateTime(TaskUpdateActivity.this, "yyyy-MM-dd HH:mm:ss"));
                                    try {
                                        date = dateFormat.parse(update_date);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    ProjectTask task2 = DataBaseManager.getInstance().getSubActivityTaskByScheduleAndSchedulePart(subActivityMaster.getSchedule(), subActivityMaster.getSchedulePart(), subActivityMaster.getProjectId());
                                    if (task2 != null) {
                                        task1.setParentTaskId(task2.getTaskId());
                                    }
                                    task1.setTaskStartDate(date.getTime());
                                    task1.setType(AppUtils.DAILY_UPDATE_TYPE);
                                    task1.setProjectId(subActivityMaster.getProjectId());
                                    task1.setSubject(subActivityMaster.getLongDescription());
                                    task1.setUnit(subActivityMaster.getUnit());
                                    task1.setSchedule(subActivityMaster.getSchedule());
                                    task1.setSchedulePart(subActivityMaster.getSchedulePart());
                                    task1.setParentL1Id(parentL1Id);
                                    task1.setLoa(subActivity_loa);
                                    task1.setActualManpower(man_power);
                                    task1.setContractor(contractor);
                                    if (locationL1Name != null) {
                                        if (!locationL1Name.equalsIgnoreCase("Select District")) {
                                            task1.setLocationL1(locationL1Name);
                                        }
                                    }
                                    if (locationL2Name != null) {
                                        if (!locationL2Name.equalsIgnoreCase("Select Town")) {
                                            task1.setLocationL2(locationL2Name);
                                        }
                                    }
                                    if (locationL3Name != null) {
                                        if (!locationL3Name.equalsIgnoreCase("Select Location")) {
                                            task1.setLocationL3(locationL3Name);
                                        }
                                    }
                                    if (locationL4Name != null) {
                                        if (!locationL4Name.equalsIgnoreCase("PSS")) {
                                            task1.setLocationL4(locationL4Name);
                                        }
                                    }
                                    if (locationL5Name != null) {
                                        if (!locationL5Name.equalsIgnoreCase("Select Feeder")) {
                                            task1.setLocationL5(locationL5Name);
                                        }
                                    }
                                    task1.setAssignedTo(SharedPrefHelper.getInstance(TaskUpdateActivity.this).getFromSharedPrefs(SharedPrefHelper.USER_ID));
                                    DataBaseManager.getInstance().insertTaskObject(task1);
                                    if (AppUtils.isOnline(TaskUpdateActivity.this)) {
                                        SyncManager syncManager = new SyncManager(TaskUpdateActivity.this);
                                        syncManager.syncTaskUpdate();
                                    }
                                }
                                quantity_ev.setText("");
                                contractor_et.setText("");
                                actual_man_power_et.setText("");
                                AppUtils.hideSoftKeyboard(TaskUpdateActivity.this);
                                Toast.makeText(TaskUpdateActivity.this, "Task updated successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(TaskUpdateActivity.this, ShowTaskHistoryActivity.class);
                                intent.putExtra("projectId", projectId);
                                intent.putExtra("schedule", schedule);
                                intent.putExtra("parentL1Id", parentL1Id);
                                intent.putExtra("schedulePart", schedulePart);
                                intent.putExtra("loa", loa);
                                intent.putExtra("subScheduleMultiplier", subScheduleMultipler);
                                startActivity(intent);
                                finish();
                            }
                        } else if (update_type.equalsIgnoreCase(AppUtils.ACTIVITY_UPDATE_TYPE)) {
                            double d = Double.parseDouble(quantity);
                            Double progressDb = (progress_db + d);
                            if (scope < progressDb) {
                                Toast.makeText(TaskUpdateActivity.this, "Progress should not be more than the scope", Toast.LENGTH_SHORT).show();
                            } else {
                                List<ProjectTask> taskList = DataBaseManager.getInstance().getActivityByScheduleAndParentL1Id(schedule, parentL1Id);
                                ProjectTask task1 = null;
                                if (taskList.size() > 0) {
                                    task1 = taskList.get(0);
                                }
                                if (task1 != null) {
                                    Date date = null;
                                    task1.setTaskStatus(AppUtils.IN_PROGRESS);
                                    String progress_str = String.valueOf(progressDb);
                                    task1.setActualLoa(progress_str);
                                    //task1.setTaskCreatedDate(AppUtils.currentDateTime(TaskUpdateActivity.this, "yyyy-MM-dd HH:mm:ss"));
                                    try {
                                        date = dateFormat.parse(update_date);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    task1.setActualManpower(man_power);
                                    task1.setContractor(contractor);
                                    if (locationL1Name != null) {
                                        if (!locationL1Name.equalsIgnoreCase("Select District")) {
                                            task1.setLocationL1(locationL1Name);
                                        }
                                    }
                                    if (locationL2Name != null) {
                                        if (!locationL2Name.equalsIgnoreCase("Select Town")) {
                                            task1.setLocationL2(locationL2Name);
                                        }
                                    }
                                    if (locationL3Name != null) {
                                        if (!locationL3Name.equalsIgnoreCase("Select Location")) {
                                            task1.setLocationL3(locationL3Name);
                                        }
                                    }
                                    if (locationL4Name != null) {
                                        if (!locationL4Name.equalsIgnoreCase("PSS")) {
                                            task1.setLocationL4(locationL4Name);
                                        }
                                    }
                                    if (locationL5Name != null) {
                                        if (!locationL5Name.equalsIgnoreCase("Select Feeder")) {
                                            task1.setLocationL5(locationL5Name);
                                        }
                                    }
                                    DataBaseManager.getInstance().insertTaskObject(task1);
                                    if (AppUtils.isOnline(TaskUpdateActivity.this)) {
                                        SyncManager syncManager = new SyncManager(TaskUpdateActivity.this);
                                        syncManager.syncTaskUpdate();
                                    }
                                    quantity_ev.setText("");
                                    contractor_et.setText("");
                                    actual_man_power_et.setText("");
                                    AppUtils.hideSoftKeyboard(TaskUpdateActivity.this);
                                    Toast.makeText(TaskUpdateActivity.this, "Activity updated successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(TaskUpdateActivity.this, TaskActivity.class);
                                    intent.putExtra("projectId", projectId);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(TaskUpdateActivity.this, "Activity not found", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else if (update_type.equalsIgnoreCase(AppUtils.SUBACTIVITY_UPDATE_TYPE)) {
                            //int quant_int = Integer.parseInt(quantity);
                            //double d = (double) quant_int;
                            double d=Double.parseDouble(quantity);
                            Double progressDb = (progress_db + d);
                            if (scope < progressDb) {
                                Toast.makeText(TaskUpdateActivity.this, "Progress should not be more than the scope", Toast.LENGTH_SHORT).show();
                            } else {
                                List<ProjectTask> projectTaskList = DataBaseManager.getInstance().getSubActivityByScheduleAndSchedulePart(schedule, schedulePart, parentL1Id);
                                ProjectTask task1 = null;
                                if (projectTaskList.size() > 0) {
                                    task1 = projectTaskList.get(0);
                                }
                                if (task1 != null) {
                                    Date date = null;
                                    task1.setTaskStatus(AppUtils.IN_PROGRESS);
                                    String progress_str = String.valueOf(progressDb);
                                    task1.setActualLoa(progress_str);
                                    //task1.setTaskCreatedDate(AppUtils.currentDateTime(TaskUpdateActivity.this, "yyyy-MM-dd HH:mm:ss"));
                                    try {
                                        date = dateFormat.parse(update_date);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    task1.setActualManpower(man_power);
                                    task1.setContractor(contractor);
                                    if (locationL1Name != null) {
                                        if (!locationL1Name.equalsIgnoreCase("Select District")) {
                                            task1.setLocationL1(locationL1Name);
                                        }
                                    }
                                    if (locationL2Name != null) {
                                        if (!locationL2Name.equalsIgnoreCase("Select Town")) {
                                            task1.setLocationL2(locationL2Name);
                                        }
                                    }
                                    if (locationL3Name != null) {
                                        if (!locationL3Name.equalsIgnoreCase("Select Location")) {
                                            task1.setLocationL3(locationL3Name);
                                        }
                                    }
                                    if (locationL4Name != null) {
                                        if (!locationL4Name.equalsIgnoreCase("PSS")) {
                                            task1.setLocationL4(locationL4Name);
                                        }
                                    }
                                    if (locationL5Name != null) {
                                        if (!locationL5Name.equalsIgnoreCase("Select Feeder")) {
                                            task1.setLocationL5(locationL5Name);
                                        }
                                    }
                                    DataBaseManager.getInstance().insertTaskObject(task1);
                                    /*if (AppUtils.isOnline(TaskUpdateActivity.this)) {
                                        SyncManager syncManager = new SyncManager(TaskUpdateActivity.this);
                                        syncManager.syncTaskUpdate();
                                    }*/
                                    quantity_ev.setText("");
                                    contractor_et.setText("");
                                    actual_man_power_et.setText("");
                                    AppUtils.hideSoftKeyboard(TaskUpdateActivity.this);
                                    Toast.makeText(TaskUpdateActivity.this, "Sub Activity updated successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(TaskUpdateActivity.this, SubActivityTaskActivity.class);
                                    intent.putExtra("schedule", schedule);
                                    intent.putExtra("loa", loa);
                                    intent.putExtra("parentL1Id", parentL1Id);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(TaskUpdateActivity.this, "Sub Activity not found", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                    }
                }

            }
        });
    }

    private void updateDate() {
        update_date = dateFormat.format(calendar.getTime());
        input_date_tv.setText(update_date);
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
