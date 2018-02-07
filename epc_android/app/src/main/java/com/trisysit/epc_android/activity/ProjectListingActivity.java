package com.trisysit.epc_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.trisysit.epc_android.R;
import com.trisysit.epc_android.SqliteDatabase.ProjectTable;
import com.trisysit.epc_android.utils.AppUtils;
import com.trisysit.epc_android.utils.DataBaseManager;

import java.util.ArrayList;
import java.util.List;

public class ProjectListingActivity extends DrawerScreenActivity {
    private View rootView;
    private Toolbar toolbar;
    EditText project_search_et;
    ProjectListAdapter myTaskAdapter;
    private List<ProjectTable> projectTableList = new ArrayList<>();
    private ListView mytasklv;
    List<ProjectTable> projectList = new ArrayList<>();
    TextView titleTv,no_project_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytask);
        getWidgets();
        setData();
        setAdapter();
        setListener();
        setUpNavigationDrawer(ProjectListingActivity.this, rootView, toolbar);
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
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mytasklv = (ListView) findViewById(R.id.myTask_list);
        titleTv = (TextView) findViewById(R.id.toolbar_title);
        titleTv.setText(getString(R.string.projectList));
        project_search_et = (EditText) findViewById(R.id.project_search_et);
        no_project_tv=(TextView)findViewById(R.id.no_project_msg);
    }
    private void setData() {
        projectTableList = DataBaseManager.getInstance().getAllProject();
    }
    private void setAdapter() {
        if(projectTableList.size()>0){
            no_project_tv.setVisibility(View.GONE);
            mytasklv.setVisibility(View.VISIBLE);
            myTaskAdapter = new ProjectListAdapter(projectTableList);
            mytasklv.setAdapter(myTaskAdapter);
        }
        else {
            no_project_tv.setVisibility(View.VISIBLE);
            mytasklv.setVisibility(View.GONE);
        }
    }
    private void setListener() {
        project_search_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(myTaskAdapter!=null){
                    myTaskAdapter.getFilter().filter(s.toString());
                    mytasklv.setAdapter(myTaskAdapter);
                    myTaskAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()==0){
                    projectTableList = DataBaseManager.getInstance().getAllProject();
                    myTaskAdapter = new ProjectListAdapter(projectTableList);
                    mytasklv.setAdapter(myTaskAdapter);
                }
                else {

                }
            }
        });
        findViewById(R.id.snack_bar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AppUtils.isOnline(ProjectListingActivity.this)){
                    Toast.makeText(ProjectListingActivity.this,getString(R.string.Sync_in_progress),Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(ProjectListingActivity.this,getString(R.string.no_internet_msg),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class ProjectListAdapter extends BaseAdapter implements Filterable {
        ItemFilter itemFilter=new ItemFilter();

        ProjectListAdapter(List<ProjectTable> projectTableList) {
            projectList=projectTableList;

        }

        @Override
        public int getCount() {
            return projectTableList.size();
        }

        @Override
        public Object getItem(int position) {
            return projectTableList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(ProjectListingActivity.this);
                convertView = inflater.inflate(R.layout.project_list_layout, parent, false);

            }
            TextView project_name = (TextView) convertView.findViewById(R.id.myTask_text);
            TextView project_start_date = (TextView) convertView.findViewById(R.id.project_start_date);
            ImageView left_arrow_iv=(ImageView)convertView.findViewById(R.id.work_image_iv);
            left_arrow_iv.setVisibility(View.VISIBLE);
            final ProjectTable projectTable = projectTableList.get(position);

            project_name.setText("Project " + (++position) + ": " + projectTable.getName());
            if(projectTable.getProjectStartDate()!=null) {
                project_start_date.setText("Started " + projectTable.getProjectStartDate());
            }
            else {
                project_start_date.setText("Started " );
            }
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(projectTable.getProjectId()!=null){
                        Intent intent=new Intent(ProjectListingActivity.this,TaskActivity.class);
                        intent.putExtra("projectId",projectTable.getProjectId());
                        startActivity(intent);
                    }

                }

            });
            return convertView;
        }

        @Override
        public Filter getFilter() {
            return itemFilter;
        }
    }
    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            final List<ProjectTable> list = projectList;
            String filterString = constraint.toString().toLowerCase();
            if (filterString.equals("")) {
                results.values = projectList;
                results.count = projectList.size();
                return results;
            } else {
                int count = list.size();
                final ArrayList<ProjectTable> nlist = new ArrayList<ProjectTable>(count);
                String filterableString;
                for (int i = 0; i < count; i++) {
                    filterableString = list.get(i).getName();
                    if (filterableString.toLowerCase().contains(filterString)) {
                        nlist.add(list.get(i));
                    }
                }
                results.values = nlist;
                results.count = nlist.size();
                return results;
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            projectTableList = (ArrayList<ProjectTable>) results.values;
            myTaskAdapter.notifyDataSetChanged();
        }


    }

}
