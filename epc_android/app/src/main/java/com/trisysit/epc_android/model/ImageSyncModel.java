package com.trisysit.epc_android.model;

import java.io.File;
import java.util.List;

/**
 * Created by trisys on 9/12/17.
 */

public class ImageSyncModel {
    private String imageName;
    private List<File> imageFileList;
    private List<ImageFIleModel> imageFIleModels;

    public List<ImageFIleModel> getImageFIleModels() {
        return imageFIleModels;
    }

    public void setImageFIleModels(List<ImageFIleModel> imageFIleModels) {
        this.imageFIleModels = imageFIleModels;
    }

    private File imageFile;

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public List<File> getImageFileList() {
        return imageFileList;
    }

    public void setImageFileList(List<File> imageFileList) {
        this.imageFileList = imageFileList;
    }

    public File getImageFile() {

        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }
}
