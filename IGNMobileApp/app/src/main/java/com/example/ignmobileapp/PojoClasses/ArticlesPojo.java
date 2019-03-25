package com.example.ignmobileapp.PojoClasses;

import java.util.List;

public class ArticlesPojo {
    String contentId, contentType;
    List<ThumbnailsArticles> thumbnails;
    MetadataArticles metadata;
    List<String> tags;
    List<String> authors;
    int numberOfComments;

    public ArticlesPojo()
    {

    }

    public ArticlesPojo(String contentId, String contentType, List<ThumbnailsArticles> thumbnails, MetadataArticles metadata, List<String> tags, List<String> authors, int numberOfComments) {
        this.contentId = contentId;
        this.contentType = contentType;
        this.thumbnails = thumbnails;
        this.metadata = metadata;
        this.tags = tags;
        this.authors = authors;
        this.numberOfComments = numberOfComments;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public List<ThumbnailsArticles> getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(List<ThumbnailsArticles> thumbnails) {
        this.thumbnails = thumbnails;
    }

    public MetadataArticles getMetadata() {
        return metadata;
    }

    public void setMetadata(MetadataArticles metadata) {
        this.metadata = metadata;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public int getNumberOfComments() {
        return numberOfComments;
    }

    public void setNumberOfComments(int numberOfComments) {
        this.numberOfComments = numberOfComments;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }
}
