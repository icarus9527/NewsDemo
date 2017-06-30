package com.example.superzhang.demonews.data;

/**
 * Created by superzhang on 2017/5/16.
 */

public class News {
    private String title;
    private String content;
    private String date;
    private String author_name;
    private String thumbnail_pic_s;

    public void setTitle(String title){
        this.title = title;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getThumbnail_pic_s() {
        return thumbnail_pic_s;
    }

    public void setThumbnail_pic_s(String thumbnail_pic_s) {
        this.thumbnail_pic_s = thumbnail_pic_s;
    }

    public String getTitle(){
        return  title;
    }

    public void setContent(String content){
        this.content = content;
    }
    public String getContent(){
        return  content;
    }
}
