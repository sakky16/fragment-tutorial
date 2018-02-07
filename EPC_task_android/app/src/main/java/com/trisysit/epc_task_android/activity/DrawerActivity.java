package com.trisysit.epc_task_android.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.trisysit.epc_task_android.R;
import com.trisysit.epc_task_android.utils.AppUtils;
import com.trisysit.epc_task_android.utils.DataBaseManager;
import com.trisysit.epc_task_android.utils.SharedPrefHelper;

public class DrawerActivity extends AppCompatActivity {
    public Drawer.Result result;
    public DrawerLayout drawerLayout;
    private AlertDialog progressDialog;
    public ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
    }
    public void setUpNavigationDrawer(final Activity activity, final View rootView, final Toolbar toolbar) {
        toolbar.setBackgroundColor(getResources().getColor(R.color.darkBlue));
        toolbar.setNavigationIcon(R.drawable.ham_new_icon);
        result = new Drawer().withActivity(activity)
                .withHeader(R.layout.header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withIcon(activity.getResources().getDrawable(R.drawable.attendance_list)).withName("Task Update").withTextColor(activity.getResources().getColor(R.color.Black)).withIdentifier(0).withSelectedColor(activity.getResources().getColor(R.color.colorPrimary)).withSelectedTextColor(activity.getResources().getColor(R.color.darkBlue)),
                        new PrimaryDrawerItem().withIcon(activity.getResources().getDrawable(R.drawable.sync_icon)).withName("Sync Information").withTextColor(activity.getResources().getColor(R.color.Black)).withIdentifier(1).withSelectedColor(activity.getResources().getColor(R.color.colorPrimary)).withSelectedTextColor(activity.getResources().getColor(R.color.darkBlue)),
                        new PrimaryDrawerItem().withIcon(activity.getResources().getDrawable(R.drawable.new_logout)).withName("Logout").withTextColor(activity.getResources().getColor(R.color.Black)).withIdentifier(2).withSelectedColor(activity.getResources().getColor(R.color.colorPrimary)).withSelectedTextColor(activity.getResources().getColor(R.color.darkBlue))
                        //new PrimaryDrawerItem().withName("Version 12.2.0.350").setEnabled(false).withTextColor(activity.getResources().getColor(R.color.Gray)).withIdentifier(5).withSelectedColor(activity.getResources().getColor(R.color.colorPrimary)).withSelectedTextColor(activity.getResources().getColor(R.color.darkBlue))
                )
                .withSliderBackgroundColor(getResources().getColor(R.color.White))
                .withFullscreen(true)
                .withHeaderClickable(true)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        if (drawerItem != null) {
                            switch (drawerItem.getIdentifier()) {
                                case 0: {
                                    Intent i = new Intent(DrawerActivity.this, TaskUpdateActivity.class);
                                    startActivity(i);
                                    break;
                                }
                                case 1: {
                                    Intent i = new Intent(DrawerActivity.this, SyncInfoActivity.class);
                                    startActivity(i);
                                    break;
                                }
                                case 2: {
                                    AlertDialog.Builder builder=new AlertDialog.Builder(DrawerActivity.this);
                                    builder.setMessage("Are you sure You want to logout");
                                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if(AppUtils.isOnline(DrawerActivity.this)){
                                                if(AppUtils.getSyncDetails(DrawerActivity.this)){
                                                    Toast.makeText(DrawerActivity.this,"Please sync all the data first before Log out",Toast.LENGTH_SHORT).show();
                                                    Intent intent=new Intent(DrawerActivity.this,SyncInfoActivity.class);
                                                    startActivity(intent);
                                                }
                                                else {
                                                    callLoginActivityAfterLogout();
                                                }
                                            }
                                            else {
                                                Toast.makeText(DrawerActivity.this,getString(R.string.no_internet_msg),Toast.LENGTH_SHORT).show();
                                            }


                                        }
                                    });
                                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                    AlertDialog dialog=builder.create();
                                    dialog.show();
                                    break;
                                }

                            }
                        }
                    }
                })
                .build();
        drawerLayout = result.getDrawerLayout();
        mToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                rootView.setTranslationX(slideOffset * drawerView.getWidth());
                drawerLayout.bringChildToFront(drawerView);
                drawerLayout.requestLayout();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.setDrawerListener(mToggle);
        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.parent);
        TextView name=(TextView)linearLayout.findViewById(R.id.name_tv);
        name.setText(SharedPrefHelper.getInstance(DrawerActivity.this).getFromSharedPrefs(SharedPrefHelper.NAME));

    }
    private void callLoginActivityAfterLogout(){
        removeSharedPrefDuringLogout();
        DataBaseManager.getInstance().clearTable();
        Intent i = new Intent(DrawerActivity.this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }
    private void removeSharedPrefDuringLogout(){
        SharedPrefHelper.getInstance(DrawerActivity.this).removeFromSharedPrefs(SharedPrefHelper.TOKEN);
        SharedPrefHelper.getInstance(DrawerActivity.this).removeFromSharedPrefs(SharedPrefHelper.NAME);
        SharedPrefHelper.getInstance(DrawerActivity.this).removeFromSharedPrefs(SharedPrefHelper.LAST_SYNC_TIME_TASK);
    }
}
