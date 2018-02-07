package com.trisysit.epc_android.model;

import java.io.File;

/**
 * Created by trisys on 11/12/17.
 */

public class ImageFIleModel {
    private File file;
    String imageName;
    long imageParam;

    public long getImageParam() {
        return imageParam;
    }

    public void setImageParam(long imageParam) {
        this.imageParam = imageParam;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
