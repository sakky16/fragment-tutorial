package com.trisysit.epc_android.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.trisysit.epc_android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by trisys on 7/12/17.
 */

public class ImageDetailsDialogFragment extends DialogFragment {
    private ImageView workImage_iv,left_nav,right_nav;
    private TextView workComment_tv;
    ViewPager viewPager;
    private Button ok_btn;
    MyCustomPagerAdapter myCustomPagerAdapter;





    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View rootView = inflater.inflate(R.layout.work_image_dialog_layout, container, false);
        getWidgets(rootView);
        getData();
        setOnclickListener();
        return rootView;
    }
    @Override
    public void onResume() {
        super.onResume();
        Dialog dialog = getDialog();
        int width = (int) (getActivity().getResources().getDisplayMetrics().widthPixels * 0.90);
        int height = (int) (getActivity().getResources().getDisplayMetrics().heightPixels * 0.70);
        dialog.getWindow().setLayout(width, height);
    }
    private void getWidgets(View view){
        workImage_iv=(ImageView)view.findViewById(R.id.work_image);
        workComment_tv=(TextView) view.findViewById(R.id.comment_tv);
        ok_btn=(Button)view.findViewById(R.id.ok_btn);
        viewPager = (ViewPager)view.findViewById(R.id.viewpager);
        left_nav=(ImageView) view.findViewById(R.id.left_nav);
        right_nav=(ImageView)view.findViewById(R.id.right_nav);

        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });


    }
    private void getData(){
        Bundle bundle = getArguments();
        String imageUrl=bundle.getString("imageUrl");
        String fromPage=bundle.getString("fromPage");
        List<String> imageList=new ArrayList<>();
        List<String> imageList1=new ArrayList<>();
        if(!imageUrl.equalsIgnoreCase("")){
            String imageArray[]=imageUrl.split(",");
            for(int i=0;i<imageArray.length;i++){
                imageList.add(imageArray[i]);
            }


            if(imageList.size()>1){
                right_nav.setVisibility(View.VISIBLE);
            }
            myCustomPagerAdapter = new MyCustomPagerAdapter(getActivity(), imageList,fromPage);
            viewPager.setAdapter(myCustomPagerAdapter);

        }
        else {
            imageList.add("");
            myCustomPagerAdapter = new MyCustomPagerAdapter(getActivity(), imageList,fromPage);
            viewPager.setAdapter(myCustomPagerAdapter);
        }


        if(bundle.getString("comment").equalsIgnoreCase("")){
            workComment_tv.setText("No Comment");
        }
        else {
            workComment_tv.setText(bundle.getString("comment"));
        }

    }

    private void setOnclickListener(){
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        right_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int imageCount=viewPager.getAdapter().getCount();
                int tab=viewPager.getCurrentItem();
                tab++;
                viewPager.setCurrentItem(tab);
                if(tab==imageCount-1){
                    left_nav.setVisibility(View.VISIBLE);
                    right_nav.setVisibility(View.GONE);
                }
                else {
                    left_nav.setVisibility(View.VISIBLE);
                    right_nav.setVisibility(View.VISIBLE);
                }
            }
        });
        left_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int imageCount=viewPager.getChildCount();
                int tab=viewPager.getCurrentItem();
                if(tab>0){
                    tab--;
                    viewPager.setCurrentItem(tab);
                    if(tab==0){
                        right_nav.setVisibility(View.VISIBLE);
                        left_nav.setVisibility(View.GONE);
                    }
                    else {
                        right_nav.setVisibility(View.VISIBLE);
                        left_nav.setVisibility(View.VISIBLE);
                    }
                }

            }
        });

    }

}
