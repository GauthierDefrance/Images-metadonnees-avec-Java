package Core;

import com.drew.imaging.ImageProcessingException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class order {
    private ArrayList<Image> ImageList;

    public order(ArrayList<File> images) throws ImageProcessingException, IOException {
        ImageList = new ArrayList<Image>();
        for(File f:images){
            Image tmp = new Image(f.getAbsolutePath());
            tmp.initMetadata();
            ImageList.add(tmp);
        }
    }

    public ArrayList<Image> orderByName(){
        Collections.sort(ImageList, Comparator.comparing(Image::getName));
        return ImageList;
    }

    public ArrayList<Image> orderBySize(){
        Collections.sort(ImageList, Comparator.comparing(Image::getSize));
        return ImageList;
    }

    public ArrayList<Image> orderByDate(){
        Collections.sort(ImageList, Comparator.comparing(Image::getMdateOtherFormat));
        return ImageList;
    }

    public ArrayList<Image> orderByHeight(){
        Collections.sort(ImageList, Comparator.comparing(Image::getHeight));
        return ImageList;
    }

    public ArrayList<Image> orderByWidth(){
        Collections.sort(ImageList, Comparator.comparing(Image::getWidth));
        return ImageList;
    }



}
