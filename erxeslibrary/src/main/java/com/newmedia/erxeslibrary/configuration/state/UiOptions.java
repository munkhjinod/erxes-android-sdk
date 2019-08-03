package com.newmedia.erxeslibrary.configuration.state;

import android.graphics.Color;

import com.newmedia.erxeslibrary.DataManager;
import com.newmedia.erxeslibrary.R;
import com.newmedia.erxeslibrary.helper.Helper;
import com.newmedia.erxeslibrary.helper.Json;

public class UiOptions {
    static final private int[] backgrounds ={R.drawable.bitmap1,R.drawable.bitmap2,R.drawable.bitmap3,R.drawable.bitmap4};
    private String logo;
    private int wallpaper;
    private int colorCode;
    public void load(DataManager dataManager){
        this.wallpaper = dataManager.getDataI(DataManager.wallpaper) % backgrounds.length;
        this.colorCode = Color.parseColor(dataManager.getDataS(DataManager.color,"#5629B6"));
    }

    public int getWalpaper(){
        return backgrounds[wallpaper];
    }

    public int getColor() {
        return colorCode;
    }

    public void setOptions(Json js, DataManager dataManager){
        if(js == null)
            return;
        dataManager.setData(DataManager.color, js.getString("color"));
        dataManager.setData(DataManager.wallpaper, js.getString("wallpaper"));
        this.load(dataManager);
    }

}

