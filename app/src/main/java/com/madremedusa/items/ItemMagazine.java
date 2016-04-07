package com.madremedusa.items;

import java.util.ArrayList;

/**
 * Created by Andrés Mariño on 4/2/16.
 */

public class ItemMagazine {
    private String name;
    private ArrayList<String> imageUrl;

    public ItemMagazine(String name, ArrayList<String> imageUrl){
        this.name=name;
        this.imageUrl=imageUrl;
    }

    public String getCoverName(){
        return name;
    }

    public String getCover(){
        if(imageUrl.size()>0){
            return imageUrl.get(0);
        }
        return null;
    }

    public ArrayList<String> getImageUrl(){
        return imageUrl;
    }
}
