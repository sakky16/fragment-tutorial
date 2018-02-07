package com.trisysit.epc_android.fragments;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.silvestrpredko.dotprogressbar.DotProgressBar;
import com.trisysit.epc_android.R;
import com.trisysit.epc_android.activity.ProjectListingActivity;
import com.trisysit.epc_android.utils.AppUtils;
import com.trisysit.epc_android.utils.NetworkUtils;

import java.io.File;
import java.util.List;

import javax.microedition.khronos.opengles.GL;

/**
 * Created by trisys on 10/12/17.
 */

public class MyCustomPagerAdapter extends PagerAdapter {
    Context context;
    int images[];
    String from_page;
    LayoutInflater layoutInflater;
    List<String> imageList;


    public MyCustomPagerAdapter(Context context, List<String> imageList,String from_page) {
        this.context = context;
        this.imageList=imageList;
        this.from_page=from_page;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.image_item, container, false);
        String imagePath="";
        if(from_page.equalsIgnoreCase(AppUtils.ATTENDANCE_DETAILS_PAGE)){
           imagePath=NetworkUtils.IMAGE_PATH+imageList.get(position);
          // imagePath=imagePath.substring(47);
          //imagePath=imageList.get(position);
        }
        else if(from_page.equalsIgnoreCase(AppUtils.RECORD_ATTEN_PAGE_AFTER_SYN)){
            imagePath=NetworkUtils.IMAGE_PATH+imageList.get(position);
        }
        else {
            imagePath=imageList.get(position);

        }
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        final DotProgressBar progressBar=(DotProgressBar) itemView.findViewById(R.id.dot_progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        Glide.with(context).load(imagePath)
                .thumbnail(0.6f)
                .crossFade()
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        if(e!=null){
                            String error=e.toString();
                        }
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .error(R.drawable.no_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);

        container.addView(itemView);

        //listening to image click
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
