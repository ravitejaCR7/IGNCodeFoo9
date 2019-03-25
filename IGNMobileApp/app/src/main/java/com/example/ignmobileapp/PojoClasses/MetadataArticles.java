package com.example.ignmobileapp.PojoClasses;

public class MetadataArticles {
    String headline,description,publishDate,slug,state,videoSeries;
    int duration;

    public MetadataArticles()
    {

    }

    public MetadataArticles(String headline, String description, String publishDate, String slug, String state, String videoSeries, int duration) {
        this.headline = headline;
        this.description = description;
        this.publishDate = publishDate;
        this.slug = slug;
        this.state = state;
        this.videoSeries = videoSeries;
        this.duration = duration;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getVideoSeries() {
        return videoSeries;
    }

    public void setVideoSeries(String videoSeries) {
        this.videoSeries = videoSeries;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
