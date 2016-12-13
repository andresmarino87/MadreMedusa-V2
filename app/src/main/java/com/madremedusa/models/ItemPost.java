package com.madremedusa.models;

/**
 * Created by Andrés Mariño on 4/3/16.
 */
public class ItemPost {
    private String title;
    private String author;
    private String thumbnailUrl;
    private String content;

    public ItemPost(String title,String author,String thumbnailUrl,String content){
        this.title = title;
        this.author = author;
        this.thumbnailUrl=thumbnailUrl;
        this.content=content;
    }

    public String getTitle(){
        return this.title;
    }

    public String getAuthor(){
        return this.author;
    }

    public String getThumbnailUrl(){
        return this.thumbnailUrl;
    }

    public String getContent(){
        return this.content;
    }

}
