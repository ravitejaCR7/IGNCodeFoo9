package com.example.ignmobileapp.PojoClasses;

public class ThumbnailsArticles {
    String url,size;
    int width,height;

    public ThumbnailsArticles()
    {

    }

    public ThumbnailsArticles(String url, String size, int width, int height) {
        this.url = url;
        this.size = size;
        this.width = width;
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public String getSize() {
        return size;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
