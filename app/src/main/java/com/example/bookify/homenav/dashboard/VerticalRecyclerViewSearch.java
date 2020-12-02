package com.example.bookify.homenav.dashboard;

public class VerticalRecyclerViewSearch {

    public String title, cover_url, author;

    public VerticalRecyclerViewSearch(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover_url() {
        return cover_url;
    }

    public void setCover_url(String cover_url) {
        this.cover_url = cover_url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public VerticalRecyclerViewSearch(String title, String cover_url, String author) {
        this.title = title;
        this.cover_url = cover_url;
        this.author = author;
    }
}
