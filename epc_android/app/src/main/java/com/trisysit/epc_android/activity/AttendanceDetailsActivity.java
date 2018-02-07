package com.trisysit.epc_android.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.trisysit.epc_android.R;
import com.trisysit.epc_android.SqliteDatabase.DatabaaseUtils;
import com.trisysit.epc_android.fragments.ImageDetailsDialogFragment;
import com.trisysit.epc_android.model.AttendanceModel;
import com.trisysit.epc_android.model.ImageCommentModel;
import com.trisysit.epc_android.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class AttendanceDetailsActivity extends AppCompatActivity {
    private TextView titleTv;
    private AttendanceModel model;
    private ListView attendetails_lv;
    private AttendanceDetailsAdapter adapter;
    private List<ImageCommentModel> imageCommentModelList=new ArrayList<>();
    private String mobileId, date,inTime,outTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_details);
        getWidgets();
        getExtra();
        setData();
        AttendanceAsyncTask attendanceAsyncTask=new AttendanceAsyncTask();
        attendanceAsyncTask.execute();
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
        titleTv = (TextView) findViewById(R.id.toolbar_title);
        attendetails_lv=(ListView)findViewById(R.id.attendanceTetails_list);
    }


    private void getExtra() {
        mobileId = getIntent().getStringExtra("mobileID");
    }

    private void setData() {
        model = DatabaaseUtils.getAttendanceDetailsByMobileId(AttendanceDetailsActivity.this, mobileId);
        if (model != null) {
            date = model.getDate();
            inTime=model.getInTime();
            outTime=model.getOutTime();
            titleTv.setTextSize(15);

            titleTv.setText("Date: "+date+"\nPunch In: "+inTime+"\nPunch Out: "+outTime);

        }

    }
    private class AttendanceAsyncTask extends AsyncTask<String,Void,String>{
        private ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(AttendanceDetailsActivity.this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("getting Details");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            if (!model.getComment1().equalsIgnoreCase("") || !model.getImage1().equalsIgnoreCase("")){
                ImageCommentModel model1=new ImageCommentModel();
                model1.setImageUrl(model.getImage1());
                model1.setComment(model.getComment1());
                model1.setTime(model.getImageTime1());
                imageCommentModelList.add(model1);
            }
            if(!model.getComment2().equalsIgnoreCase("") || !model.getImage2().equalsIgnoreCase("")){
                ImageCommentModel model1=new ImageCommentModel();
                model1.setImageUrl(model.getImage2());
                model1.setComment(model.getComment2());
                model1.setTime(model.getImageTime2());
                imageCommentModelList.add(model1);
            }
            if(!model.getComment3().equalsIgnoreCase("") || !model.getImage3().equalsIgnoreCase("")){
                ImageCommentModel model1=new ImageCommentModel();
                model1.setImageUrl(model.getImage3());
                model1.setComment(model.getComment3());
                model1.setTime(model.getImageTime3());
                imageCommentModelList.add(model1);
            }
            if(!model.getComment4().equalsIgnoreCase("") || !model.getImage4().equalsIgnoreCase("")){
                ImageCommentModel model1=new ImageCommentModel();
                model1.setImageUrl(model.getImage4());
                model1.setComment(model.getComment4());
                model1.setTime(model.getImageTime4());
                imageCommentModelList.add(model1);
            }
            if(!model.getComment5().equalsIgnoreCase("") || !model.getImage5().equalsIgnoreCase("")){
                ImageCommentModel model1=new ImageCommentModel();
                model1.setImageUrl(model.getImage5());
                model1.setComment(model.getComment5());
                model1.setTime(model.getImageTime5());
                imageCommentModelList.add(model1);
            }
            if(!model.getComment6().equalsIgnoreCase("") || !model.getImage6().equalsIgnoreCase("")){
                ImageCommentModel model1=new ImageCommentModel();
                model1.setImageUrl(model.getImage6());
                model1.setComment(model.getComment6());
                model1.setTime(model.getImageTime6());
                imageCommentModelList.add(model1);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(imageCommentModelList.size()>0){
                adapter=new AttendanceDetailsAdapter();
                attendetails_lv.setAdapter(adapter);
            }
            progressDialog.dismiss();

        }
    }

    private class AttendanceDetailsAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return imageCommentModelList.size();
        }

        @Override
        public Object getItem(int position) {
            return imageCommentModelList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(AttendanceDetailsActivity.this);
                convertView = inflater.inflate(R.layout.attendance_details_list, parent, false);
            }
            TextView comment_tv=(TextView)convertView.findViewById(R.id.comment_tv);
            ImageView image_icon=(ImageView)convertView.findViewById(R.id.work_image_iv);
            TextView time_tv=(TextView)convertView.findViewById(R.id.time_tv);
            final ImageCommentModel model=imageCommentModelList.get(position);
            comment_tv.setText(model.getComment());
            if(!model.getImageUrl().equalsIgnoreCase("")){
                image_icon.setVisibility(View.VISIBLE);
            }
            else {
                image_icon.setImageDrawable(getResources().getDrawable(R.drawable.no_image));
            }
            time_tv.setText(model.getTime());
            image_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageDetailsDialogFragment dialogFragment=new ImageDetailsDialogFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("imageUrl",model.getImageUrl());
                    bundle.putString("comment",model.getComment());
                    bundle.putString("fromPage",AppUtils.ATTENDANCE_DETAILS_PAGE);
                    dialogFragment.setArguments(bundle);
                    dialogFragment.show(getFragmentManager(),"");
                }
            });


            return convertView;
        }
    }
}
