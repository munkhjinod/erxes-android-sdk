package com.newmedia.erxeslibrary.configuration.state;

import com.newmedia.erxeslibrary.R;
import com.newmedia.erxeslibrary.helper.Helper;

public class UiOptions {
    static private int[] backgrounds ={R.drawable.bitmap1,R.drawable.bitmap2,R.drawable.bitmap3,R.drawable.bitmap4};
    private String logo;
    private String wallpaper;
    private String color;
    private int ColorCode;


    public int getWalpaper(){
        int index = Integer.getInteger(wallpaper,0);
        return backgrounds[index];
    }

    public int getColor() {
        return ColorCode;
    }
}
